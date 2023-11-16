package me.nzuguem.fraud.detection.resources;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.annotation.Nonnull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import me.nzuguem.fraud.detection.models.AmountFraudDetection;
import me.nzuguem.fraud.detection.models.DistanceFraudDetection;
import me.nzuguem.fraud.detection.repositories.TransactionRepository;
import me.nzuguem.fraud.detection.services.llm.FraudDetectionService;

@Path("fraud/detection")
@RunOnVirtualThread
public class FraudDetectionResource {

    private final FraudDetectionService service;

    private final TransactionRepository transactions;

    public FraudDetectionResource(FraudDetectionService service, TransactionRepository transactions) {
        this.service = service;
        this.transactions = transactions;
    }

    @GET
    @Path("distance")
    //@Produces(MediaType.APPLICATION_JSON)
    public String detectBasedOnDistance(@Nonnull @QueryParam("customerId") Long customerId) {
        return service.detectDistanceFraudForCustomer(customerId);
    }

    @GET
    @Path("amount")
    @Produces(MediaType.APPLICATION_JSON)
    public AmountFraudDetection detectBaseOnAmount(@Nonnull @QueryParam("customerId") Long customerId) {
        return service.detectAmountFraudForCustomer(customerId);
    }

    @GET
    @Path("verify")
    public double verify(@Nonnull @QueryParam("customerId") Long customerId) {
        return transactions.getAmountForCustomer(customerId);
    }
}
