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
    @JoinColumn(name = "productId")
    private Product product;

    @Column
    private boolean winningBid;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Bid(double bid, Tender tender, User user, Product product) {
        this.bid = bid;
        this.tender = tender;
        this.user = user;
        this.winningBid=false;
        this.product = product;
    }
    public Bid(){

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isWinningBid() {
        return winningBid;
    }

    public void setWinningBid(boolean winningBid) {
        this.winningBid = winningBid;
    }
}
