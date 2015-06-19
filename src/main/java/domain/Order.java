package domain;

/**
 * Created by Ilya on 21.03.2015.
 */
public class Order {
    private String owner;
    private String worker;
    private String title;
    private String city;
    private String address;
    private String link;
    private String date;
    private String comment;
    private String status;
    private String cost;
    private String destination;


    public Order(String owner, String worker, String title, String city, String address, String link, String comment, String status, String cost, String destination, String date) {
        this.owner = owner;
        this.worker = worker;
        this.title = title;
        this.city = city;
        this.address = address;
        this.link = link;
        this.comment = comment;
        this.status = status;
        this.cost = cost;
        this.destination = destination;
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
