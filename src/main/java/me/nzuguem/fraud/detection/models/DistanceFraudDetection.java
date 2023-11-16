package me.nzuguem.fraud.detection.models;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public record DistanceFraudDetection(
        String customerName,
        Double firstAmount,
        Double secondAmount,
        String firstCity,
        String secondCity,

        // It could have been an integer, but LLM responses are mostly strings.
        String distance,
        Boolean fraud,
        List<String> cities,
        String explanation,
        String email
) {

    @JsonCreator
    public DistanceFraudDetection {
    }

    public static DistanceFraudDetection ofFallback(long customerId) {
        return new DistanceFraudDetection(
                null, null, null, null, null,
                null, null, null,
                "I'm sorry, I cannot perform fraud detection on customer " + customerId,
                null);
    }

}
