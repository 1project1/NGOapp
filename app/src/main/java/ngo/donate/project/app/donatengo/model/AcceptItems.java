package ngo.donate.project.app.donatengo.model;

import android.view.View;
import android.widget.CheckBox;

import java.io.Serializable;

/**
 * Created by ArupPc on 25-03-2017.
 */

public class AcceptItems implements Serializable {

    String title;
    String date;
    String message;
    String ngoLocaton;
    boolean requestPending;
    int quantity;

    public AcceptItems(String title, String date, String message, String ngoLocaton, boolean requestPending, int quantity) {
        this.title = title;
        this.date = date;
        this.message = message;
        this.ngoLocaton = ngoLocaton;
        this.requestPending = requestPending;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNgoLocaton() {
        return ngoLocaton;
    }

    public void setNgoLocaton(String ngoLocaton) {
        this.ngoLocaton = ngoLocaton;
    }

    public boolean isRequestPending() {
        return requestPending;
    }

    public void setRequestPending(boolean requestPending) {
        this.requestPending = requestPending;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCheckedAndVisib(CheckBox c) {

        c.setVisibility(View.VISIBLE);

    }
}
