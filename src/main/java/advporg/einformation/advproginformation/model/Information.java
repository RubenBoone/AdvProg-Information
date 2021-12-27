package advporg.einformation.advproginformation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "information")
public class Information {
    @Id
    private String id;
    private String monuCode;
    private Date buildYear;
    private int avgCustomers;
    private double entryFee;
    private double tourTime;
    private double score;

    public Information() {
    }

    public Information(String id, String monuCode, Date buildYear, int avgCustomers, double entryFee, double tourTime, double score) {
        this.id = id;
        this.monuCode = monuCode;
        this.buildYear = buildYear;
        this.avgCustomers = avgCustomers;
        this.entryFee = entryFee;
        this.tourTime = tourTime;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonuCode() {
        return monuCode;
    }

    public void setMonuCode(String monuCode) {
        this.monuCode = monuCode;
    }

    public Date getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(Date buildYear) {
        this.buildYear = buildYear;
    }

    public int getAvgCustomers() {
        return avgCustomers;
    }

    public void setAvgCustomers(int avgCustomers) {
        this.avgCustomers = avgCustomers;
    }

    public double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(double entryFee) {
        this.entryFee = entryFee;
    }

    public double getTourTime() {
        return tourTime;
    }

    public void setTourTime(double tourTime) {
        this.tourTime = tourTime;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
