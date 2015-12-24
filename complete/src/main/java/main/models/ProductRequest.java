package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by kakha on 12/15/2015.
 */
@Entity
@Table(name = "ProductRequest")
public class ProductRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productRequestId")
    private long id;


    @OneToMany(mappedBy = "productRequest",cascade = CascadeType.ALL)
    private List<ProductRequestElement> productRequestElements;

    @ManyToOne
    @JoinColumn(name = "filialId")
    private Filial filial;

    @Column
    private boolean active;

    @Column
    private boolean accepted;

    @Column
    private Date requestDate;

    @Column
    private boolean sentToTender;

    @Column
    private Date sentToTenderDate;

    @Column
    private String comment;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "tenderId")
    private Tender tender;

    public ProductRequest(Filial filial) {
        this.filial = filial;
        this.accepted=false;
        this.active=true;
        this.requestDate=new Date();
        this.sentToTender=false;
        this.sentToTenderDate=null;
        this.comment="";
        this.tender=null;


    }
    public ProductRequest() {

    }


    public List<ProductRequestElement> getProductRequestElements() {
        return productRequestElements;
    }

    public void setProductRequestElements(List<ProductRequestElement> productRequestElements) {
        this.productRequestElements = productRequestElements;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public boolean isSentToTender() {
        return sentToTender;
    }

    public void setSentToTender(boolean sentToTender) {
        this.sentToTender = sentToTender;
    }

    public Date getSentToTenderDate() {
        return sentToTenderDate;
    }

    public void setSentToTenderDate(Date sentToTenderDate) {
        this.sentToTenderDate = sentToTenderDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tender getTender() {
        return tender;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }
}
