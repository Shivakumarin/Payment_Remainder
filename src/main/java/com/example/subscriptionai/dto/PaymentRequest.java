package com.example.subscriptionai.dto;


import jakarta.validation.constraints.*;
        import java.math.BigDecimal;

public class PaymentRequest {

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private PaymentStatus status;

    private String transactionReference;

    @NotNull
    private Long subscriptionId;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public enum PaymentStatus {
        PENDING,
        SUCCESS,
        FAILED,
        REFUNDED
    }
}
