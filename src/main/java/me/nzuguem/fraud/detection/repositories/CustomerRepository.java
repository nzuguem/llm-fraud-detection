package me.nzuguem.fraud.detection.repositories;

import dev.langchain4j.agent.tool.Tool;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import me.nzuguem.fraud.detection.entities.Customer;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    @Tool("get the customer name for the given customerId")
    public String getCustomerName(long id) {
        return find("id", id).firstResult().name;
    }

    @Tool("get the customer email for the given customerId")
    public String getCustomerEmail(long id) {
        return find("id", id).firstResult().email;
    }

}
