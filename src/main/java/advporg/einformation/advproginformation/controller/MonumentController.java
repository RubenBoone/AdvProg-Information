package advporg.einformation.advproginformation.controller;

import advporg.einformation.advproginformation.repository.MonumentRepository;
import advporg.einformation.advproginformation.model.Monument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@RestController
public class MonumentController {

    @PostConstruct void fillDb(){
        if (monumentRepository.count() == 0){
            monumentRepository.save(new Monument("1",  "BE19580318","Atomium", "BE", "1958",3.5));
            monumentRepository.save(new Monument("2",  "FR18890331","Eiffel tower", "FR", "1889",4));
            monumentRepository.save(new Monument("3", "US18860901","Statue of Liberty", "US", "1886",3.8));
        }

//        System.out.println("Info test: " + monumentRepository.findMonumentByMonuCode("BE19580318").getName());

    }

    @Autowired
    private MonumentRepository monumentRepository;

    @GetMapping("/monuments")
    public List<Monument> getMonuments(){
        return monumentRepository.findAll();
    }

    @GetMapping("/monuments/{monuCode}")
    public Monument getMonumentByMonuCode(@PathVariable String monuCode){
        return monumentRepository.findMonumentByMonuCode(monuCode);
    }

    @GetMapping("/monuments/buildyear/{year}")
    public List<Monument> getMonumentByBuildYear(@PathVariable String year){
        return monumentRepository.findMonumentByBuildYear(year);
    }

    @GetMapping("monuments/new")
    public List<Monument> getNewest(){
        return  monumentRepository.findAllByOrderByBuildYearDesc();
    }

    @GetMapping("monuments/old")
    public List<Monument> getOldest(){
        return  monumentRepository.findAllByOrderByBuildYearAsc();
    }


    @PostMapping("/monuments")
    public Monument addMonument(@RequestBody Monument monument){
        monumentRepository.save(monument);
        return monument;
    }

    @PutMapping("/monuments")
    public Monument updateMonument(@RequestBody Monument updatedMonument){
        Monument retrievedMonument = monumentRepository.findMonumentByMonuCode(updatedMonument.getMonuCode());

        retrievedMonument.setMonuCode(updatedMonument.getMonuCode());
        retrievedMonument.setBuildYear(updatedMonument.getBuildYear());
        retrievedMonument.setScore(updatedMonument.getScore());
        retrievedMonument.setLocation(updatedMonument.getLocation());
        retrievedMonument.setName(updatedMonument.getName());

        return retrievedMonument;
    }

    @DeleteMapping("/monuments/{monuCode}")
    public ResponseEntity deleteMonument(@PathVariable String monuCode){
        Monument monument = monumentRepository.findMonumentByMonuCode(monuCode);
        if (monument != null){
            monumentRepository.delete(monument);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
