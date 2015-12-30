package main.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vakhtanggelashvili on 12/24/15.
 */
@Entity
@Table(name = "tenders")
public class Tender {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tenderId")
    private long id;

    @Column
    private Date createDate;

    @Column
    private String name;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private boolean active;

    @Column
    private boolean started;



    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tender",cascade = CascadeType.ALL)
    private List<ProductRequest> productRequests;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tender",cascade = CascadeType.ALL)
    private List<Bid> bids;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private boolean ended;

    public Tender(String name, Date startDate, Date endDate, User user){

        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.createDate=new Date();
        this.active=true;
        this.bids=new ArrayList<Bid>();
        this.productRequests=new ArrayList<ProductRequest>();
        this.ended=false;
        this.started=false;
    }
    public Tender(){}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ProductRequest> getProductRequests() {
        return productRequests;
    }

    public void setProductRequests(List<ProductRequest> productRequests) {
        this.productRequests = productRequests;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
