package advporg.einformation.advproginformation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "monument")
public class Monument {
    @Id
    private String id;
    private String monuCode;
    private String name;
    private String location;
    private String buildYear;
    private double score;

    public Monument() {
    }

    public Monument(String id, String monuCode, String name, String location, String buildYear, double score) {
        this.id = id;
        this.monuCode = monuCode;
        this.name = name;
        this.location = location;
        this.buildYear = buildYear;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
