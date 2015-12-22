package main.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import main.Repositorys.*;
import main.models.*;
import main.models.Enum.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kakha on 11/24/2015.
 */
@Controller
public class ProductController {

    @RequestMapping("/createproduct")
    @ResponseBody
    public Product createproduct(@RequestParam(value="name",required = true, defaultValue="")String name,
                                      @RequestParam(value="quantType",required = true, defaultValue="")int quantType){

        Product product=new Product(name,quantType);

        productRepository.save(product);

        return product;
    }




    @RequestMapping("/getproducts")
    @ResponseBody
    public Page<Product> getProducts(@CookieValue("projectSessionId") long sessionId,int index, String search){
        Session session=sessionRepository.findOne(sessionId);
        if(session.getUser().getType()== UserType.sa.getCODE()){
            return productRepository.findByNameAndPage(search,constructPageSpecification(index));
        }else{
            if(session.getUser().getType()== UserType.branch.getCODE()){
                return productRepository.findByFilials(session.getUser().getFilial(),constructPageSpecification(index));
            }else{
                return null;
            }
        }
    }
    @RequestMapping("/getmyfilialproductslist")
    @ResponseBody
    public List<Product> getMyFialialProducts(@CookieValue("projectSessionId") long sessionId){

        return sessionRepository.getOne(sessionId).getUser().getFilial().getProducts();
    }

    @RequestMapping("/givefilialproduct")
    @ResponseBody
    public boolean giveFilialProduct(long filialId,@RequestParam(value="productIds") ArrayList<Long> productIds){

        Filial filial=filialRepository.findOne(filialId);
        for (int i = 0; i < productIds.size(); i++) {
            Product product=productRepository.findOne(productIds.get(i));
            filial.getProducts().add(product);

        }
        filialRepository.save(filial);

        return true;
    }
    @RequestMapping("/removeproductfromfilial")
    @ResponseBody
    public boolean removeProductFromFilial(long filialId,@RequestParam(value="productIds") ArrayList<Long> productIds){
        Filial filial=filialRepository.findOne(filialId);
        for (int i = 0; i < productIds.size(); i++) {
            Product product=productRepository.findOne(productIds.get(i));
            filial.getProducts().remove(product);
        }
        filialRepository.save(filial);

        return true;
    }

    @RequestMapping("/getfilialproducts")
    @ResponseBody
    public List<Product> getFilialProducts(long filialId){
        return filialRepository.getOne(filialId).getProducts();
    }

    @RequestMapping("/getproductsnotinfilial")
    @ResponseBody
    public List<Product> getProductsNotInFilial(long filialId){
        Filial filial=filialRepository.findOne(filialId);

        List<Product> products=productRepository.findAll();

        products.removeAll(filial.getProducts());

        return products;
    }

    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 30);
        return pageSpecification;
    }
    @Autowired
    private FilialRepository filialRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ProductRequestRepository productRequestRepository;
    @Autowired
    private ProductRequsetElementRepository productRequsetElementRepository;
}