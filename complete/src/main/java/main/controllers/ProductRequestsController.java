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

/**
 * Created by kakha on 12/15/2015.
 */
@Controller
public class ProductRequestsController {




    @RequestMapping("/getallproductrequests")
    @ResponseBody
    public Page<ProductRequest> getAllProductRequests(@CookieValue("projectSessionId") long sessionId,int index){
        Session session=sessionRepository.findOne(sessionId);
        if(session.getUser().getType()== UserType.admin.getCODE()||session.getUser().getType()== UserType.sa.getCODE())
            return productRequestRepository.findByActiveAndAccepted(true,false,constructPageSpecification(index));
        else
            return productRequestRepository.findByFilial(session.getUser().getFilial(),constructPageSpecification(index));
    }


    @ResponseBody
    @RequestMapping("/getacceptedproductrequests")
    public Page<ProductRequest> getAcceptedProductRequest(int index){
        return productRequestRepository.findByAcceptedAndSentToTender(true,false,constructPageSpecification(index));
    }


    @RequestMapping("/requestProducts")
    @ResponseBody
    public boolean requestProducts(@CookieValue("projectSessionId") long sessionId, @RequestParam(value="productRequests") String requests){

        Session session= sessionRepository.findOne(sessionId);
        Filial filial=session.getUser().getFilial();

        ProductRequest productRequest=new ProductRequest(filial);
        productRequestRepository.save(productRequest);
        JsonParser jsonParser=new JsonParser();
        Gson gson = new Gson();
        JsonArray jsonArray=jsonParser.parse(requests).getAsJsonArray();
        for(int i=0;i<jsonArray.size();i++){
            RequestJsonModel requestJsonModel=gson.fromJson(jsonParser.parse(jsonArray.get(i).getAsString()).getAsJsonObject(),RequestJsonModel.class);

            ProductRequestElement productRequestElement=new ProductRequestElement(requestJsonModel.getSum(),productRequest,productRepository.findOne(requestJsonModel.getId()));

            productRequsetElementRepository.save(productRequestElement);

            System.out.println("product "+requestJsonModel.getSum());
        }



        return true;
    }


    @RequestMapping("/confirmrequest")
    @ResponseBody
    public boolean confirmRequest(long id){
        try {
            ProductRequest productRequest =productRequestRepository.findOne(id);
            productRequest.setAccepted(true);
            productRequestRepository.save(productRequest);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    @RequestMapping("/declinerequest")
    @ResponseBody
    public boolean declineRequest(long id,String comment){
        try {
            ProductRequest productRequest = productRequestRepository.findOne(id);
            productRequest.setAccepted(false);
            productRequest.setActive(false);
            productRequest.setComment(comment);
            productRequestRepository.save(productRequest);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
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
