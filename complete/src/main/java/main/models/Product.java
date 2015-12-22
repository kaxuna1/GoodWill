package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kakha on 11/16/2015.
 */
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productId")
    private long id;

    @Column
    private String name;

    @Column
    private int quantType;


    @ManyToMany
    @JsonIgnore
    @JoinTable(name="FILIAL_PRODUCTS")
    private List<Filial> filials;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductRequestElement> productRequestElements;



    public Product(String name, int quantType) {
        this.name = name;
        this.quantType = quantType;
    }
    public Product(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantType() {
        return quantType;
    }

    public void setQuantType(int quantType) {
        this.quantType = quantType;
    }


    public List<Filial> getFilials() {
        return filials;
    }

    public void setFilials(List<Filial> filials) {
        this.filials = filials;
    }
}
