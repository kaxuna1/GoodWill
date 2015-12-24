package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by vakhtanggelashvili on 12/24/15.
 */
@Entity
@Table(name = "bids")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bidId")
    private long id;

    @Column
    private double bid;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "tenderId")
    private Tender tender;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Bid(double bid, Tender tender, User user) {
        this.bid = bid;
        this.tender = tender;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public Tender getTender() {
        return tender;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
