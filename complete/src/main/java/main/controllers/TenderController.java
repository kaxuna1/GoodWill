package main.controllers;

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
    @RequestMapping("/deletetender")
    @ResponseBody
    public boolean deleteTender(@CookieValue("projectSessionId") long sessionId,long id){
        try {
            if(sessionRepository.findOne(sessionId).getUser().getType()==UserType.sa.getCODE()){
                Tender tender=tenderRepository.findOne(id);
                for(int i=0;i<tender.getProductRequests().size();i++){
                    ProductRequest productRequest=tender.getProductRequests().get(i);
                    productRequest.setTender(null);
                    productRequest.setSentToTender(false);
                    productRequest.setSentToTenderDate(null);
                    productRequestRepository.save(productRequest);
                }
                tender.getProductRequests().clear();
                tenderRepository.save(tender);
                tenderRepository.delete(id);
            }
        }catch (Exception e){
            e.printStackTrace();
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
    public Page<Tender> getActiveTenders(@CookieValue("projectSessionId") long sessionId,int index,int type){
        Session session =sessionRepository.findOne(sessionId);
        if(session.getUser().getType()== UserType.sa.getCODE()||session.getUser().getType()== UserType.admin.getCODE()){

            switch (type){
                case 1:return tenderRepository.findByActive(true,constructPageSpecification(index));
                case 2: return tenderRepository.findByStartedAndEnded(true,false,constructPageSpecification(index));
                case 3: return tenderRepository.findByStartedAndEnded(true,true,constructPageSpecification(index));
                default:return null;
            }
        }else{
            switch (type){
                case 2:
                    return tenderRepository.findByStartedAndEnded(true,false,constructPageSpecification(index));
                case 4:
                    return tenderRepository.finMyWond(session.getUser(),constructPageSpecification(index));
                default:return null;
            }
        }
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
