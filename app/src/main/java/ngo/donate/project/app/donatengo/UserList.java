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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ngo.donate.project.app.donatengo.controllers.AcceptItemControllers;
import ngo.donate.project.app.donatengo.model.AcceptItems;
import ngo.donate.project.app.donatengo.model.UserDonationDetails;

public class UserList extends AppCompatActivity implements AcceptItemControllers.ItemClickListener {

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



}
