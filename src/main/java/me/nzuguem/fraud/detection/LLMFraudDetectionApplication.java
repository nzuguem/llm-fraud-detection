package me.nzuguem.fraud.detection;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import me.nzuguem.fraud.detection.entities.Customer;
import me.nzuguem.fraud.detection.entities.Transaction;
import me.nzuguem.fraud.detection.repositories.CustomerRepository;
import me.nzuguem.fraud.detection.repositories.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@ApplicationScoped
public class LLMFraudDetectionApplication {

    private final TransactionRepository transactions;
    private final CustomerRepository customers;

    public static final List<String> CITIES = List.of(
            "Paris", "Lyon", "Marseille", "Bordeaux", "Toulouse", "Nantes", "Brest",
            "Clermont-Ferrand", "La Rochelle", "Lille", "Metz", "Strasbourg", "Nancy", "Valence",
            "Avignon", "Montpellier", "Nime", "Arles", "Nice", "Cannes");

    public static final Random RANDOMIZER = new Random();

    public LLMFraudDetectionApplication(TransactionRepository transactions, CustomerRepository customers) {
        this.transactions = transactions;
        this.customers = customers;
    }

    @Transactional
    void init(@Observes StartupEvent startupEvent) {
        this.loadCustomers();
        this.loadTransactions();
    }

    private void loadCustomers() {
        if (customers.count() == 0) {
            var customer1 = new Customer();
            customer1.name = "Kevin NZUGUEM";
            customer1.email = "nzuguemkevine@gmail.com";

            var customer2 = new Customer();
            customer2.name = "Idriss TAYO";
            customer2.email = "wktayo@gmail.com";

            var customer3 = new Customer();
            customer3.name = "Christian SOH";
            customer3.email = "sohchristian94@gmail.com";

            customers.persist(customer1, customer2, customer3);
        }
    }

    private void loadTransactions() {

        this.transactions.deleteAll();

        var transactionsList = IntStream.rangeClosed(1, 100)
                .mapToObj(__ -> {
                    var transaction = new Transaction();
                    // Get a random customer
                    var idx = RANDOMIZER.nextInt((int) customers.count());
                    transaction.customerId = this.customers.findAll().page(idx, 1).firstResult().id;
                    transaction.amount = RANDOMIZER.nextInt(1000) + 1d;
                    transaction.time = LocalDateTime.now().minusMinutes(RANDOMIZER.nextInt(20));
                    transaction.city = getARandomCity();

                    return transaction;
                })
                .toList();

        this.transactions.persist(transactionsList);

    }

    public static String getARandomCity() {
        return CITIES.get(RANDOMIZER.nextInt(CITIES.size()));
    }

}
