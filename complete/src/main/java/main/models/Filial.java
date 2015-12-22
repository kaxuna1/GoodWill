package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vakhtanggelashvili on 10/22/15.
 */
@Entity
@Table(name = "Filials")
public class Filial {

    @Id
    @Column(name = "filialId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "filial",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<User> users;

    @ManyToMany
    @JoinTable(name="FILIAL_PRODUCTS")
    private List<Product> products;


    public Filial(String name, String address) {
        this.name = name;
        this.address = address;
        this.users=new ArrayList<User>();
        this.products=new ArrayList<Product>();
    }
    public Filial(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public long getId() {
        return id;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
