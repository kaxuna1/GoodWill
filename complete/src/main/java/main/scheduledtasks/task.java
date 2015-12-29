package main.scheduledtasks;

import main.Repositorys.TenderRepository;
import main.models.Tender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
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

    @Scheduled(fixedRate = 5000)
    public void endTenders() {

    }
    @Autowired
    private TenderRepository tenderRepository;
}
