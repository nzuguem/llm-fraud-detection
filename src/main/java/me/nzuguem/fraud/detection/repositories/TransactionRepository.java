package me.nzuguem.fraud.detection.repositories;

import com.speedment.jpastreamer.application.JPAStreamer;
import dev.langchain4j.agent.tool.Tool;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import me.nzuguem.fraud.detection.entities.Transaction;
import me.nzuguem.fraud.detection.entities.Transaction$;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {

    private final JPAStreamer jpaStreamer;

    public TransactionRepository(JPAStreamer jpaStreamer) {
        this.jpaStreamer = jpaStreamer;
    }

    @Tool("Get the transaction for a given customer for the last 15 minutes")
    public List<Transaction> getTransactionsForCustomer(long customerId) {

        return this.jpaStreamer.stream(Transaction.class)
                .filter(Transaction$.customerId.equal(customerId))
                .filter(Transaction$.time.greaterThan(LocalDateTime.now().minusMinutes(15)))
                .toList();
    }

    public double getAmountForCustomer(long customerId) {

        return this.jpaStreamer.stream(Transaction.class)
                .filter(Transaction$.customerId.equal(customerId))
                .filter(Transaction$.time.greaterThan(LocalDateTime.now().minusMinutes(15)))
                .mapToDouble(Transaction$.amount)
                .sum();
    }

    @Tool("Get the city for a given transaction id")
    public String getCityForTransaction(long transactionId) {
        return find("id = ?1", transactionId).firstResult().city;
    }
}
