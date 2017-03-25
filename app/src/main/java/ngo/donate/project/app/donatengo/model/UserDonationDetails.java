package ngo.donate.project.app.donatengo.model;

import java.util.List;

/**
 * Created by ArupPc on 25-03-2017.
 */

public class UserDonationDetails {

    String user_name;
    String address;
    List<AcceptItems> itemsList;

    public UserDonationDetails(String user_name, String address, List<AcceptItems> itemsList) {
        this.user_name = user_name;
        this.address = address;
        this.itemsList = itemsList;
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

    public void setItemsList(List<AcceptItems> itemsList) {
        this.itemsList = itemsList;
    }
}
