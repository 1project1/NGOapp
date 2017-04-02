package ngo.donate.project.app.donatengo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ArupPc on 25-03-2017.
 */

public class UserDonationDetails implements Serializable {

    public String user_name;
    public String address;
    public String contact;
    public List<AcceptItems> itemsList;

    String email;
    public UserDonationDetails(String user_name, String address, String contact,String email) {
        this.user_name = user_name;
        this.address = address;
        this.contact = contact;
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<AcceptItems> getItemsList() {
        return itemsList;
    }

    public void setItemsList(final List<AcceptItems> itemsList) {
        this.itemsList = itemsList;
    }

    public String getContact() {
        return contact;
    }
}
