package main.controllers;

import main.Repositorys.FilialRepository;
import main.Repositorys.ProductRepository;
import main.models.Filial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by kakha on 11/25/2015.
 */
@Controller
public class FilialController {

    @RequestMapping("/createfilial")
    @ResponseBody
    public Filial createFilial(String name,String address){
        Filial filial=new Filial(name,address);
        filialRepository.save(filial);
        return filial;
    }

    @RequestMapping("/getfilials")
    @ResponseBody
    public List<Filial> getFilials(){
        return filialRepository.findAll();
    }

    @RequestMapping("givefilialproducts")
    @ResponseBody
    public Filial giveFilialProducts(@RequestParam(value="parcelIds") ArrayList<Long> productIds, long filialId){
        Filial filial=filialRepository.findOne(filialId);
        for (Long productId : productIds) {
            filial.getProducts().add(productRepository.findOne(productId));
        }
        return filial;
    }







    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 10);
        return pageSpecification;
    }
    @Autowired
    FilialRepository filialRepository;
    @Autowired
    ProductRepository productRepository;
}
