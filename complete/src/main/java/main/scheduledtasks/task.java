package main.scheduledtasks;

import main.Repositorys.BidRepository;
import main.Repositorys.TenderRepository;
import main.controllers.TenderController;
import main.models.Bid;
import main.models.Product;
import main.models.Tender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kakha on 12/29/2015.
 */
@Component
public class task {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 1000)
    public void startTenders() {
        List<Tender> tenders =tenderRepository.findForStart(true,false,false,new Date());
        for(int i=0;i<tenders.size();i++){
            System.out.println(tenders.get(i).getName());
            Tender tender=tenders.get(i);
            tender.setStarted(true);
            tenderRepository.save(tender);

        }
    }

    @Scheduled(fixedRate = 1000)
    public void endTenders() {
        List<Tender> tenders=tenderRepository.findForEnd(true,true,false,new Date());
        for(int g=0;g<tenders.size();g++){
            Tender tender=tenders.get(g);
            tender.setEnded(true);
            tenderRepository.save(tender);
            long id=tender.getId();
            List<Bid> bids=new ArrayList<Bid>();
            List<Product> productsInTender=new ArrayList<Product>();
            int i=0;
            while (i<tender.getProductRequests().size()) {
                int k=0;
                while (k<tender.getProductRequests().get(i).getProductRequestElements().size()) {
                    if(!productsInTender.contains(tender.getProductRequests().get(i).getProductRequestElements().get(k).getProduct())){

                        productsInTender.add(tender.getProductRequests().get(i).getProductRequestElements().get(k).getProduct());

                    }
                    k++;
                }

                i++;
            }
            i=0;
            while (i<productsInTender.size()){
                List<Bid> bids1=bidRepository.findByTenderAndProductOrderByBidAsc(tender,productsInTender.get(i));
                if(bids1.size()>0){
                    bids.add(bids1.get(0));
                }
                i++;
            }




            for(int k=0;k<bids.size();k++){
                Bid bid=
                bids.get(k);
                bid.setWinningBid(true);
                bidRepository.save(bid);
            }
        }

    }
    @Autowired
    private TenderRepository tenderRepository;
    @Autowired
    private BidRepository bidRepository;
}
