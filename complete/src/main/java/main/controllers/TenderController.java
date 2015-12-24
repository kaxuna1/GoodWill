package main.controllers;

import main.Repositorys.*;
import main.models.ProductRequest;
import main.models.Tender;
import main.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by vakhtanggelashvili on 12/24/15.
 */
@Controller
public class TenderController {
    @RequestMapping("/createtender")
    @ResponseBody
    public boolean createTender(@CookieValue("projectSessionId") long sessionId, String name, Date startDate,Date endDate){
        User user=sessionRepository.findOne(sessionId).getUser();
        Tender tender=new Tender(name,startDate,endDate,user);
        try {
            tenderRepository.save(tender);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    @RequestMapping("/addrequesttotender")
    @ResponseBody
    public boolean addProductRequestToTender(long productRequestId,long tenderId){
        try {
            ProductRequest productRequest=productRequestRepository.findOne(productRequestId);
            Tender tender=tenderRepository.findOne(tenderId);
            productRequest.setTender(tender);
            productRequest.setSentToTender(true);
            productRequest.setSentToTenderDate(new Date());
            productRequestRepository.save(productRequest);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @RequestMapping("/getactivetenderslistforadding")
    @ResponseBody
    public List<Tender> getActiveTendersList(){
        return tenderRepository.findByActiveAndStarted(true,false);
    }



    @RequestMapping("/getactivetenders")
    @ResponseBody
    public Page<Tender> getActiveTenders(int index){
        return tenderRepository.findByActive(true,constructPageSpecification(index));
    }

    private Pageable constructPageSpecification(int pageIndex) {
        Pageable pageSpecification = new PageRequest(pageIndex, 30);
        return pageSpecification;
    }

    @Autowired
    private TenderRepository tenderRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRequestRepository productRequestRepository;
    @Autowired
    private SessionRepository sessionRepository;
}
