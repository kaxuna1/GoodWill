package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by kakha on 12/15/2015.
 */
@Entity
@Table(name = "ProductRequestElement")
public class ProductRequestElement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productRequestElementId")
    private long id;

    @Column
    private float quantity;

    @ManyToOne
    @JoinColumn(name = "productRequestId")
    @JsonIgnore
    private ProductRequest productRequest;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public ProductRequestElement(float quantity, ProductRequest productRequest, Product product){

        this.quantity = quantity;
        this.productRequest = productRequest;
        this.product = product;
    }
    public ProductRequestElement(){

    }


    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public ProductRequest getProductRequest() {
        return productRequest;
    }

    public void setProductRequest(ProductRequest productRequest) {
        this.productRequest = productRequest;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
