package main.models;

/**
 * Created by kakha on 12/30/2015.
 */
public class TenderProductLatestBid {
    private Product product;
    private Bid bid;


    public TenderProductLatestBid(Product product, Bid bid) {
        this.product = product;
        this.bid = bid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }
}
