package com.example.subscriptionai.dto;



import jakarta.validation.constraints.*;
        import java.math.BigDecimal;
import java.time.LocalDate;

public class SubscriptionRegistrationRequest {

    @NotBlank
    private String planName;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private BillingCycle billingCycle;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate nextRenewalDate;

    @NotNull
    private Long customerId;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getNextRenewalDate() {
        return nextRenewalDate;
    }

    public void setNextRenewalDate(LocalDate nextRenewalDate) {
        this.nextRenewalDate = nextRenewalDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public enum BillingCycle {
        WEEKLY,
        MONTHLY,
        QUARTERLY,
        HALF_YEARLY,
        YEARLY
    }
}

