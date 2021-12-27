package advporg.einformation.advproginformation.controller;

import advporg.einformation.advproginformation.InfoRepository;
import advporg.einformation.advproginformation.model.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class InfoController {

    @PostConstruct void fillDb(){
        if (infoRepository.count() == 0){
            Date date = new Date();
            date.setYear(1958);
            date.setMonth(Calendar.MARCH);
            date.setDate(18);
            infoRepository.save(new Information("1", "BE19580318", date, 600000, 16,1.5,3.5));
            date.setYear(1889);
            date.setMonth(Calendar.MARCH);
            date.setDate(31);
            infoRepository.save(new Information("2", "FR18890331", date, 620000, 10.50,1.5,4));
            date.setYear(1886);
            date.setMonth(Calendar.SEPTEMBER);
            date.setDate(1);
            infoRepository.save(new Information("3", "US18860901", date, 620000, 21.18,5.5,3.8));
        }

        System.out.println("Info test: " + infoRepository.findInformationByMonuCode("BE19580318").getBuildYear());

    }

    @Autowired
    private InfoRepository infoRepository;

    @GetMapping("/info/{monuCode}")
    public Information getInformationByMonuCode(@PathVariable String monuCode){
        return infoRepository.findInformationByMonuCode(monuCode);
    }

    @GetMapping("/info/buildyear/{year}")
    public List<Information> getInformationByBuildYear(@PathVariable int year){

        Date date = new Date();
        date.setYear(year);

        return infoRepository.findInformationByBuildYear(date);
    }

    @GetMapping("info/top")
    public List<Information> getTop(){
        return  infoRepository.findAllByOrderByScoreDesc();
    }

    @GetMapping("info/new")
    public List<Information> getNewest(){
        return  infoRepository.findAllByOrderByBuildYearDesc();
    }

    @GetMapping("info/old")
    public List<Information> getOldest(){
        return  infoRepository.findAllByOrderByBuildYearAsc();
    }


    @PostMapping("/info")
    public Information addInformation(@RequestBody Information info){
        infoRepository.save(info);
        return info;
    }

    @PutMapping("/info")
    public Information updateInfo(@RequestBody Information updatedInfo){
        Information retrievedInfo = infoRepository.findInformationByMonuCode(
                updatedInfo.getMonuCode());

        retrievedInfo.setMonuCode(updatedInfo.getMonuCode());
        retrievedInfo.setBuildYear(updatedInfo.getBuildYear());
        retrievedInfo.setAvgCustomers(updatedInfo.getAvgCustomers());
        retrievedInfo.setEntryFee(updatedInfo.getEntryFee());
        retrievedInfo.setTourTime(updatedInfo.getTourTime());
        retrievedInfo.setScore(updatedInfo.getScore());

        return retrievedInfo;
    }

    @DeleteMapping("/info/{monuCode}")
    public ResponseEntity deleteInfo(@PathVariable String monuCode){
        Information info = infoRepository.findInformationByMonuCode(monuCode);
        if (info != null){
            infoRepository.delete(info);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
