package ngo.donate.project.app.donatengo.controllers;

/**
 * Created by grant on 19-03-2017.
 */

public class HistoryItem {
    String title, ngo,status,address,date;
    long quantity;
    int thumbnail;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public HistoryItem(String title, int thumbnail, String ngo, String address, String status, long quantity,String date) {
        this.title = title;
        this.ngo = ngo;
        this.status = status;
        this.quantity = quantity;
        this.address = address;
        this.thumbnail = thumbnail;
        this.date = date;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNgo() {
        return ngo;
    }

    public void setNgo(String ngo) {
        this.ngo = ngo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
