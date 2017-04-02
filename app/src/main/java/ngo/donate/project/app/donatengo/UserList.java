package ngo.donate.project.app.donatengo;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.util.ArrayList;
import java.util.List;

import ngo.donate.project.app.donatengo.controllers.AcceptItemControllers;
import ngo.donate.project.app.donatengo.model.AcceptItems;
import ngo.donate.project.app.donatengo.model.UserDonationDetails;

public class UserList extends AppCompatActivity implements AcceptItemControllers.ItemClickListener {


    private final String our_username = "jeevanmru@gmail.com";
    private final String our_password = "bahubali";
    private  String mail_subject;
    private  String mail_body;

    RecyclerView userListView;
    UserDonationDetails userListItems;
    List<AcceptItems> userAcceptItems;
    AcceptItemControllers controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userAcceptItems = new ArrayList<>();
        userListView = (RecyclerView) findViewById(R.id.userListView);
        controller = new AcceptItemControllers(this, userAcceptItems);
        controller.setListener(this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        userListView.setLayoutManager(lm);
        userListView.setAdapter(controller);
        userListItems = (UserDonationDetails) getIntent().getSerializableExtra("donationList");
        load();
    }


    private void load() {
        userAcceptItems.clear();
        List<AcceptItems> i = userListItems.getItemsList();
        userAcceptItems.addAll(i);
        controller.notifyDataSetChanged();
    }

    @Override
    public void onSingleClick(int position) {
        String details = "Name: " + userListItems.getUser_name() + "\nAddress: " + userListItems.getAddress() +
                "\nPhone: " + userListItems.getContact() + "\nEmail: " + userListItems.getEmail();


        new AlertDialog.Builder(this).setIcon(null).setTitle("User Details")
                .setMessage(details).setPositiveButton("Copy Contact Number", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Allow copy contact
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("ContactCopied", userListItems.getContact());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(UserList.this, "Contact Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Close",null).setCancelable(false).show();
    }

    @Override
    public void onLongClick(int position) {

    }


    public void performAction(View view) {

                //Todo send Email

                String items = "NGO : " + userAcceptItems.get(0).getNgoLocaton();
        for(AcceptItems x: userAcceptItems){
            items += "\nTitle: " + x.getTitle() + " Quantity: " + x.getQuantity();
        }


                    sendConfirmationMail(userListItems.getUser_name(),userAcceptItems.get(0).getNgoLocaton()
                            ,userListItems.getEmail(),items,"Total Items: " + userAcceptItems.size());
                    Toast.makeText(this, "Email sent!!!", Toast.LENGTH_SHORT).show();


        Toast.makeText(this, "This function is also Under Developement", Toast.LENGTH_SHORT).show();

    }

    public void performCancelAction(View view) {
        Toast.makeText(this, "This function is currently under development", Toast.LENGTH_SHORT).show();

    }
    public void sendConfirmationMail(String name,String ngo_name, String user_email,String donated_item_name,String donated_item_quantity) {

        mail_subject = "Donation Successfull - Your Donation with " + ngo_name + " has " +
                "been successfull.";

        mail_body = "Hi " + name + ",\n\nWe are pleased to inform you that your items " +
                "have successfully been donated to the needy people via " + ngo_name + ". This " +
                "successfully completes your donation. Thank you for choosing our service.\n\n"
                + "The summary of items donated is mentioned below.\n\n" + donated_item_name +
                "\n" + donated_item_quantity;

        BackgroundMail.newBuilder(this)
                .withUsername(our_username)
                .withPassword(our_password)
                .withMailto(user_email)
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject(mail_subject)
                .withBody(mail_body)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
    finish();                        // Toast.makeText(Acknowledgement.this, "You will soon get an email about the confirmation", Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                        //Toast.makeText(Acknowledgement.this, "Failed... Try Again", Toast.LENGTH_SHORT).show();
                    }
                })
                .send();
    }
}
