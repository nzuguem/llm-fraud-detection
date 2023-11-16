package me.nzuguem.fraud.detection.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Transaction {


    @Id
    @GeneratedValue
    public Long id;
    public double amount;
    public long customerId;
    public String city;
    public LocalDateTime time;
}
