package com.smartload.optimizer.model;

import java.time.LocalDate;

public class Order {
    public String id;
    public long payoutCents;
    public long weightLbs;
    public long volumeCuft;
    public String origin;
    public String destination;
    public LocalDate pickupDate;
    public LocalDate deliveryDate;
    public boolean isHazmat;
}
