package ngo.donate.project.app.donatengo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ngo.donate.project.app.donatengo.controllers.UserDonationAdapter;
import ngo.donate.project.app.donatengo.model.AcceptItems;
import ngo.donate.project.app.donatengo.model.UserDonationDetails;

public class MainUi extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView usernameView;
    UserDonationAdapter userDonationAdapter;
    List<UserDonationDetails> userDonationlist;
    List<AcceptItems> userItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        usernameView = (RecyclerView)findViewById(R.id.usernameList);
        userDonationlist = new ArrayList<>();
        userItems = new ArrayList<>();
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        usernameView.setLayoutManager(lm);
        userDonationAdapter = new UserDonationAdapter(this,userDonationlist);
        usernameView.setAdapter(userDonationAdapter);
        getUserNames();
        userDonationAdapter.notifyDataSetChanged();
        //TODO HARSH


    }

    private void getUserNames() {

        String[] names = {"arup","aman","aakash","harsh","archit"};
       for(int i = 0; i<25; i++){
           AcceptItems it = new AcceptItems(names[i%5],"25-03-2017","pending","dummy Loc",true,i+5);
           userItems.add(it);
           UserDonationDetails x = new UserDonationDetails(names[i%5],"995, sector-37, faridabad",userItems);
           userDonationlist.add(x);
          }
        userDonationAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_ui, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about_app) {
            // Handle the camera action
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this,HistoryNgo.class));
        } else if (id == R.id.nav_dist) {

        }  else if (id == R.id.nav_credits) {

            startActivity(new Intent(this, CreditsUI.class));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
