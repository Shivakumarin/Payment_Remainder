**Subscription and Payment Reminder Platform** :     
**A Java 17 and Spring Boot REST API for managing business owners, customers, subscriptions, payment records, and automatic subscription-renewal email reminders. MySQL stores application data and Gmail SMTP is used for email delivery.
1. What problem does this project solve?
Businesses that sell monthly, quarterly, or yearly services need to know which customers have active subscriptions, when those subscriptions renew, and whether payments have been recorded. This application keeps those records in one place and sends email reminders before renewal dates.

Example: a customer has a ₹499 monthly subscription that renews on the 20th. The scheduler checks subscriptions every day and attempts to email the customer three days before renewal and one day before renewal.

2. Current features
- Business-owner registration: creates a user account and stores the password as a BCrypt hash.
- Customer registration: stores customer name, email, and phone number.
- Subscription management: records a plan name, amount, billing cycle, start date, next renewal date, status, and customer.
- Payment records: stores payment amount, status, transaction reference, and associated subscription. This is record keeping only; it does not charge a card or connect to a payment gateway.
- Email notifications: sends plain-text emails through Spring JavaMailSender and Gmail SMTP.
- Notification history: records attempts in notification_logs, including PENDING, SENT, or FAILED status, sent time, and failure reason.
- Automatic reminders: runs daily at 9:00 AM India time and checks active subscriptions renewing in three days or one day.
- Security configuration: uses Spring Security; routes explicitly listed as public in SecurityConfig are permitted without HTTP Basic authentication, while other routes require it.
Only email delivery is implemented. SMS, WhatsApp, and push notification enum values are not connected to delivery services.

3. How the application works
1. A business owner registers through the user registration API.
2. The business owner registers customers.
3. A subscription is created for a customer, including its next renewal date.
4. Payment transactions can be recorded against the subscription.
5. The scheduled task runs every day at 9:00 AM (Asia/Kolkata).
6. It finds active subscriptions with renewal dates three days or one day away.
7. It asks the notification service to send an email to the customer's email address.
8. The notification service first saves a PENDING log, attempts the email, and then updates the log to SENT or FAILED.
9. A successful reminder for the same subscription on the same day prevents another successful reminder that day.
4. Technology stack
- Java 17
- Spring Boot (version declared in pom.xml)
- Spring Web MVC / REST
- Spring Data JPA and Hibernate
- MySQL
- Spring Security and BCrypt password hashing
- Spring Validation
- Maven
- Lombok
5. Project structure
src/main/java/com/example/subscription/
├── controller/       # REST API endpoints
├── dto/              # Request objects and input validation
├── entity/           # JPA entities and enums mapped to database tables
├── repository/       # Database access using Spring Data JPA
├── scheduler/        # Daily subscription-renewal reminder job
├── security/         # Spring Security configuration
└── service/          # Business logic, payment records, and email delivery

src/main/resources/
└── application.properties
Important classes
Class	Responsibility
UserController / UserService	Business-owner registration
CustomerController / CustomerService	Customer registration and persistence
SubscriptionController / SubscriptionService	Subscription creation and persistence
PaymentTransactionController / PaymentTransactionService	Recording payment transaction details
NotificationController / NotificationService	Creating email notifications and tracking their status
EmailService	Sending the actual email through SMTP
SubscriptionReminderScheduler	Finding subscriptions that are close to renewal
SecurityConfig	Configuring endpoint access and authentication


6. Prerequisites
- JDK 17
- MySQL Server
- Maven, or the included Maven Wrapper
- Gmail account with 2-Step Verification and a Google App Password (if using Gmail SMTP)
7. Database setup
Create a database:
CREATE DATABASE subscription_platform;
Default JDBC URL:
jdbc:mysql://localhost:3306/subscription_platform
Hibernate uses spring.jpa.hibernate.ddl-auto=update to create/update mapped tables at startup. For production, use database migrations such as Flyway or Liquibase instead of relying on automatic schema updates.

If you already created the old subscription_ai database, either create the new database and migrate the data, or explicitly set DB_URL to the existing database. Changing the default database name does not migrate existing records.
8. Configuration
Set these environment variables in your terminal or IDE run configuration. .env.example is a reference file; Spring Boot does not automatically load a .env file unless you add a loader.
Variable	Required	Description
DB_URL	No	JDBC URL; defaults to local subscription_platform database
DB_USERNAME	No	MySQL username; defaults to root
DB_PASSWORD	Yes	MySQL password
MAIL_USERNAME	Yes for email	Gmail sender address
MAIL_PASSWORD	Yes for email	Google App Password, not the normal Gmail password


Never commit real passwords or app passwords to source control. Rotate any credentials that have previously been exposed.
9. Run the application
From the project root:
mvn spring-boot:run
Or build and run the JAR:
mvn clean package
java -jar target/subscription-reminder-platform-0.0.1-SNAPSHOT.jar
The default server port is 8080.
10. REST API endpoints
All endpoints use http://localhost:8080 as the local base URL.
Method	Endpoint	Purpose
POST	/api/users/register	Register a business owner
POST	/api/customers/register	Register a customer
POST	/api/subscriptions/register	Create a subscription for an existing customer
POST	/api/payments/record	Record a payment transaction
POST	/api/email/test?recipient=person@example.com	Send a test email
POST	/api/notifications/create	Save and attempt to send an email notification
POST	/api/reminders/test/{subscriptionId}/{days}	Placeholder endpoint; currently does not send a reminder


Example request bodies
Register a business owner — POST /api/users/register
{
  "name": "Business Owner",
  "email": "owner@example.com",
  "password": "change-me-123"
}
Register a customer — POST /api/customers/register
{
  "name": "Sample Customer",
  "email": "customer@example.com",
  "phoneNumber": "9876543210"
}
Create a subscription — POST /api/subscriptions/register
{
  "planName": "Premium Plan",
  "amount": 499.00,
  "billingCycle": "MONTHLY",
  "startDate": "2026-11-08",
  "nextRenewalDate": "2026-11-11",
  "customerId": 1
}
Supported billing cycles: WEEKLY, MONTHLY, QUARTERLY, HALF_YEARLY, YEARLY.

Record a payment — POST /api/payments/record
{
  "amount": 499.00,
  "status": "SUCCESS",
  "transactionReference": "TXN-10001",
  "subscriptionId": 1
}
Supported payment statuses: PENDING, SUCCESS, FAILED, REFUNDED.

Create an email notification — POST /api/notifications/create
{
  "notificationType": "EMAIL",
  "recipient": "customer@example.com",
  "subject": "Subscription reminder",
  "message": "Your subscription renews soon.",
  "subscriptionId": 1
}
The subject and subscription ID are optional. Only EMAIL delivery is currently supported.
11. Current limitations and next improvements
- Customer registration currently associates the customer with a temporary hard-coded user ID (1L). This should be replaced with the authenticated business owner's ID.
- Payment records are stored, but there is no payment gateway integration, automatic charge, or payment reconciliation.
- The reminder test endpoint is a placeholder and does not trigger the scheduler or send an email.
- SMS, WhatsApp, and push delivery are not implemented.
- Some routes are currently configured as permitAll; review authorization before production deployment.
- Add centralized exception handling, integration tests, database migrations, structured logging, and stronger authorization before production use.
- Protect the test-email endpoint with authorization and rate limiting before deployment.
12. Test email delivery
1. Configure MAIL_USERNAME and MAIL_PASSWORD.
2. Start the application.
3. Send a POST request to:
http://localhost:8080/api/email/test?recipient=your-recipient@example.com
4. Check the recipient inbox and spam folder.
5. To test notification logging, call /api/notifications/create and inspect the notification_logs table for its final status and failure reason.
