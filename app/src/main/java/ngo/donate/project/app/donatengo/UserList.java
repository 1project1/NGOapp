package ngo.donate.project.app.donatengo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ngo.donate.project.app.donatengo.controllers.AcceptItemControllers;
import ngo.donate.project.app.donatengo.model.AcceptItems;
import ngo.donate.project.app.donatengo.model.UserDonationDetails;

public class UserList extends AppCompatActivity implements AcceptItemControllers.ItemClickListener {

    RecyclerView userListView;
    List<UserDonationDetails> userListItems;
    List<AcceptItems> userAcceptItems;
    AcceptItemControllers controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userListView = (RecyclerView)findViewById(R.id.userListView);
        controller = new AcceptItemControllers(this,userListItems);
        controller.setListener(this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        userListView.setLayoutManager(lm);
        userListView.setAdapter(controller);
    }

    @Override
    public void onSingleClick(int position) {

    }

    @Override
    public void onLongClick(int position) {

    }
}
