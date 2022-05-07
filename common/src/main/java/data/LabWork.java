package data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LabWork implements Comparable<LabWork>, Serializable {
    private Integer id;
    private final String name;
    private final Coordinates coordinates;
    private LocalDateTime creationDate;
    private final Double minimalPoint;
    private final Integer personalQualitiesMinimum;
    private final Difficulty difficulty;
    private final Person author;

    private String user;


    public LabWork(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate, Double minimalPoint,
                   Integer personalQualitiesMinimum, Difficulty difficulty, Person author) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;
        this.personalQualitiesMinimum = personalQualitiesMinimum;
        this.difficulty = difficulty;
        this.author = author;
        user = null;
    }

    public LabWork(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate, Double minimalPoint,
                   Integer personalQualitiesMinimum, Difficulty difficulty, Person author, String user) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;
        this.personalQualitiesMinimum = personalQualitiesMinimum;
        this.difficulty = difficulty;
        this.author = author;
        this.user = user;
    }

    public LabWork(String[] fields) {
        id = Integer.parseInt(fields[0]);
        name = fields[1];
        coordinates = new Coordinates(Integer.parseInt(fields[2]), Integer.parseInt(fields[3]));
        creationDate = LocalDateTime.parse(fields[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        minimalPoint = Double.parseDouble(fields[5]);
        personalQualitiesMinimum = fields[6].equals("") ? null : Integer.parseInt(fields[6]);
        difficulty = fields[7].equals("") ? null : Difficulty.valueOf(fields[7]);
        author = new Person(fields[8], Double.parseDouble(fields[9]), HairColor.valueOf(fields[11]), EyeColor.valueOf(fields[10]), Country.valueOf(fields[12]));


    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCreationDate(LocalDateTime date) {this.creationDate = date;}

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Double getMinimalPoint() {
        return minimalPoint;
    }

    public Integer getPersonalQualitiesMinimum() {
        return personalQualitiesMinimum;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Person getAuthor() {
        return author;
    }

    public String getUser() {return user;}

    public void setUser(String user) {this.user = user;}

    @Override
    public String toString() {
        return "\n" + name + ":" + "\n" +
                "Id                        : " + id + "\n" +
                "Coordinates               : " + coordinates + "\n" +
                "Creation date             : " + creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n" +
                "Minimal point             : " + minimalPoint + "\n" +
                "Personal qualities minimum: " + personalQualitiesMinimum + "\n" +
                "Difficulty                : " + difficulty + "\n" +
                "Author                    : " + author;
    }

    public String toCsv() {
        return id + "," + name + "," + coordinates.toCsv() + ","
                + creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ","
                + minimalPoint + "," + (personalQualitiesMinimum == null ? "" : personalQualitiesMinimum) + ","
                + (difficulty == null ? "" : difficulty) + "," + author.toCsv();
    }

    @Override
    public int compareTo(LabWork labWork) {
        if (labWork.getPersonalQualitiesMinimum() == null) return 1;
        if (personalQualitiesMinimum == null) return -1;
        return personalQualitiesMinimum.compareTo(labWork.getPersonalQualitiesMinimum());
    }

    public int comparePQM(Integer personalQualitiesMinimum) {
        if (personalQualitiesMinimum == null) return 1;
        if (this.personalQualitiesMinimum == null) return -1;
        return this.personalQualitiesMinimum.compareTo(personalQualitiesMinimum);
    }
}
