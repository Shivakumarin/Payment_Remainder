package com.example.subscriptionai.service;


        import com.example.subscriptionai.dto.SubscriptionRegistrationRequest;
        import com.example.subscriptionai.entity.BillingCycle;
        import com.example.subscriptionai.entity.Customer;
        import com.example.subscriptionai.entity.Subscription;
        import com.example.subscriptionai.entity.SubscriptionStatus;
        import com.example.subscriptionai.repository.CustomerRepository;
        import com.example.subscriptionai.repository.SubscriptionRepository;
        import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            CustomerRepository customerRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.customerRepository = customerRepository;
    }

    public Subscription registerSubscription(
            SubscriptionRegistrationRequest request) {

        Customer customer = customerRepository
                .findById(request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Subscription subscription = new Subscription();

        subscription.setPlanName(request.getPlanName());
        subscription.setAmount(request.getAmount());

        subscription.setBillingCycle(
                BillingCycle.valueOf(
                        request.getBillingCycle().name()
                )
        );

        subscription.setStartDate(request.getStartDate());
        subscription.setNextRenewalDate(
                request.getNextRenewalDate()
        );

        subscription.setCustomer(customer);
        subscription.setStatus(SubscriptionStatus.ACTIVE);

        return subscriptionRepository.save(subscription);
    }
}

