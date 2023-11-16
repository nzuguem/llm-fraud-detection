package me.nzuguem.fraud.detection.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AmountFraudDetection(
        String customerName,
        Double total,
        Boolean fraud,
        List<Double> transactions,
        String explanation,
        String email
) {

    @JsonCreator
    public AmountFraudDetection {
    }

    public static AmountFraudDetection ofFallback(long customerId) {
        return new AmountFraudDetection(
                null, null, null, null,
                "I'm sorry, I cannot perform fraud detection on customer " + customerId,
                null);
    }
}
