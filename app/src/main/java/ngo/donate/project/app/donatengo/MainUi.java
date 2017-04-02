package ngo.donate.project.app.donatengo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ngo.donate.project.app.donatengo.controllers.UserDonationAdapter;
import ngo.donate.project.app.donatengo.model.AcceptItems;
import ngo.donate.project.app.donatengo.model.UserDonationDetails;

public class MainUi extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UserDonationAdapter.ItemClickCallBack {

    ProgressBar pBar;
    RecyclerView usernameView;
    UserDonationAdapter userDonationAdapter;
    List<UserDonationDetails> userDonationlist;
    List<AcceptItems> userItems;
    List<UserDonationDetails> newDList = new ArrayList<UserDonationDetails>();
    //Log out variables
    private static final String LOGIN_FILE = "LogInFile";
    private GoogleApiClient mGoogleApiClient;

    List<String> endUserAddr = new ArrayList<String>() {
    };
    List<String> endUseraPhone = new ArrayList<String>() {
    };
    List<String> endUserId = new ArrayList<String>() {
    };
    List<String> stringList = new ArrayList<String>() {
    };
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String Uid = firebaseUser.getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pBar = (ProgressBar)findViewById(R.id.pBar);
        pBar.setVisibility(View.VISIBLE);
        //intialise google signIn var's
        initGoogle();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        usernameView = (RecyclerView) findViewById(R.id.usernameList);
        userDonationlist = new ArrayList<>();
        userItems = new ArrayList<>();
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        usernameView.setLayoutManager(lm);
        userDonationAdapter = new UserDonationAdapter(this, userDonationlist);
        userDonationAdapter.setItemClickCallBack(this);
        usernameView.setAdapter(userDonationAdapter);
        getUserNames();
        userDonationAdapter.notifyDataSetChanged();

        //TODO HARSH


    }

    private void getUserNames() {

        String[] names = {"arup", "aman", "aakash", "harsh", "archit"};
       String[] add = {"ar", "a", "h", "hsh", "ait"};
        String[] titles = {"books", "shoes", "toys", "medicines", "utensils", "clothes", "others"};
       /*for(int i = 0; i<25; i++){
           AcceptItems it = new AcceptItems(titles[i%7],"25-03-2017","pending","dummy Loc",true,i+5);
           userItems.add(it);
           UserDonationDetails x = new UserDonationDetails(names[i%5],"995, sector-37, faridabad",userItems);
           userDonationlist.add(x);
          }
        userDonationAdapter.notifyDataSetChanged();
        */

       /* Random r = new Random();
       int j = 0;
        while(j++ < 5){
            userItems.clear();
            userItems=new ArrayList<>();
            for(int i = 1; i <= r.nextInt(6)+1; i++){
                AcceptItems l = new AcceptItems(titles[r.nextInt(10)%7],"31-03-2017","pending","afasg",false,r.nextInt(16));
                userItems.add(l);
            }
            String data = "";
            List<AcceptItems> newList = new ArrayList<>();
            for(AcceptItems x:userItems){
                data+=  "\nTitle:" + x.getTitle() + "\nDate:" + x.getDate() + "\nMessage:" + x.getMessage() +
                        "\nQuantity:" + x.getQuantity() +
                        "NGO:" + x.getNgoLocaton()+"\n\n";
                newList.add(x);
            }

            new AlertDialog.Builder(MainUi.this).setIcon(null)
                    .setTitle("UserList")
                    .setMessage(data)
                    .setCancelable(true)
                    .setPositiveButton("OKkie",null)
                    .show();

            UserDonationDetails d = new UserDonationDetails(names[r.nextInt(5)],add[r.nextInt(5)]);
            d.setItemsList(newList);
            userDonationlist.add(d);
        }
        userDonationAdapter.notifyDataSetChanged();*/
        abc();

    }

    public void abc() {
        userDonationlist.clear();
        dref.child("Ngos").child("NGO1").child("endUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //Toast.makeText(MainUi.this, "" + dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();
                    endUserId.add(dataSnapshot1.getKey());


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference mmRef = database.getReference("endUsers").child(dataSnapshot1.getKey());
                    mmRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           //Toast.makeText(MainUi.this, "" + dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();

                            for (DataSnapshot postSnapshot : dataSnapshot.child("Donations_item_Details").getChildren()) {
                                userItems.clear();
                                List<AcceptItems> newList = new ArrayList<>();
                                newList.clear();
//                                   Todo get keys here
                                    //Toast.makeText(MainUi.this, "" + postSnapshot.getKey(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(MainUi.this, "" + postSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                                for (DataSnapshot Snapshot : postSnapshot.getChildren()) {
                                    //  Name[i[0]][j[0]++]=(String) Snapshot.child("title").getValue();
                                    String t = (String) Snapshot.child("title").getValue();
                                    //stringList.add(t);
                                    //Name[i[0]][j[0]++]=(String) Snapshot.child("message").getValue();
                                    String m = (String) Snapshot.child("message").getValue();
                                    //stringList.add(m);
                                    //  Name[i[0]][j[0]++]=(String) Snapshot.child("ngoLocation").getValue();
                                    String ngo = (String) Snapshot.child("ngoLocation").getValue();
                                   // stringList.add(ngo);
                                    //  Name[i[0]][j[0]++]=(String) Snapshot.child("date").getValue();
                                    String date = (String) Snapshot.child("date").getValue();
                                    //stringList.add(date);
                                    //Toast.makeText(HistoryNgo.this, ""+t+"\n"+m+"\n"+ngo+"\n"+date, Toast.LENGTH_LONG).show();
                                    AcceptItems it = new AcceptItems(t, date, m, ngo, (Boolean) Snapshot.child("requestPending").getValue(), 5);
                                    userItems.add(it);
                                }

                                for(AcceptItems x:userItems){
                                    newList.add(x);
                                }
                                String name= (String) dataSnapshot.child("User_details").child("name").getValue();
                                String addr= (String) dataSnapshot.child("User_details").child("address").getValue();
                                String phone=(String) dataSnapshot.child("User_details").child("phone").getValue();
                                // TODO get EMALL ...... Toast.makeText(MainUi.this, ""+phone, Toast.LENGTH_SHORT).show();
                                //TODO taken Dummy Value Here
                                UserDonationDetails x = new UserDonationDetails(name,addr,phone,"arup.professional@gmail.com");
                                x.setItemsList(newList);
                               // newList.clear();

                                newDList.clear();
                                newDList.addAll(userDonationlist);
                                newDList.add(x);
                                userDonationlist.clear();
                                userDonationlist.addAll(newDList);
                                userDonationAdapter.notifyDataSetChanged();
                                if(!userDonationlist.isEmpty())pBar.setVisibility(View.GONE);
                                else pBar.setVisibility(View.VISIBLE);

                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            startActivity(new Intent(this, AccountSettings.class));
            return true;
        }
        if(id == R.id.action_refresh) {
            abc();
            userDonationAdapter.notifyDataSetChanged();
        }if (id == R.id.action_logout) {
            SharedPreferences logInPref = getSharedPreferences(LOGIN_FILE, 0);
            SharedPreferences.Editor logInEditor = logInPref.edit();
            logInEditor.clear().putBoolean("isLoggedIn", false).apply();

            //firebase signOut
            FirebaseAuth.getInstance().signOut();

            //Google signOut
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            // aakash updateUI(false);
                            // [END_EXCLUDE]
                        }
                    });

            startActivity(new Intent(getApplicationContext(), LogIn.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
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
            startActivity(new Intent(this, HistoryNgo.class));
        } else if (id == R.id.nav_dist) {

        } else if (id == R.id.nav_credits) {

            startActivity(new Intent(this, CreditsUI.class));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, null /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();


        //Setting Profile Picture and Name on Resume of Activity
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        ((TextView) hView.findViewById(R.id.display_name_drawer)).setText(user.getDisplayName());
        Log.w("Image", "" + user.getPhotoUrl());

        CircleImageView civ = (CircleImageView) hView.findViewById(R.id.profile_picture_drawer);
        if (user.getPhotoUrl() != null)
            Glide.with(this).load(user.getPhotoUrl()).into(civ);
    }

    @Override
    public void onItemClick(int position) {
        UserDonationDetails d = userDonationlist.get(position);
        /*String data = "User Name: " + d.getUser_name() + "\nUser Address:" + d.getAddress() + "\n"+"Contact: "+d.getContact();
        List<AcceptItems> l = d.getItemsList();
        for(AcceptItems x:l){
            data+=  "\nTitle:" + x.getTitle() + "\nDate:" + x.getDate() + "\nMessage:" + x.getMessage() +
                    "\nQuantity:" + x.getQuantity() +
                    "NGO:" + x.getNgoLocaton()+"\n\n";
        }

        new AlertDialog.Builder(MainUi.this).setIcon(null)
                .setTitle(d.getUser_name())
                .setMessage(data)
                .setCancelable(true)
                .setPositiveButton("OK",null)
                .show();
*/
        Intent i = new Intent(this,UserList.class);
        i.putExtra("donationList",d);
        startActivity(i);
    }

    @Override
    public void onSecondaryIconClick(int position) {

    }
}
