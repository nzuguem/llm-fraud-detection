package me.nzuguem.fraud.detection.resources;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.annotation.Nonnull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import me.nzuguem.fraud.detection.entities.Transaction;
import me.nzuguem.fraud.detection.repositories.TransactionRepository;

import java.util.List;

@Path("transactions")
@RunOnVirtualThread
public class TransactionResource {

    private final TransactionRepository transactions;


    public TransactionResource(TransactionRepository transactions) {
        this.transactions = transactions;
    }

    @GET
    public List<Transaction> list(@Nonnull @QueryParam("customerId") Long customerId) {
        return transactions.getTransactionsForCustomer(customerId);
    }

}
