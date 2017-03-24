package ngo.donate.project.app.donatengo;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ngo.donate.project.app.donatengo.controllers.HistoryItem;
import ngo.donate.project.app.donatengo.controllers.HistoryItemAdapter;
import ngo.donate.project.app.donatengo.model.HistoryItemNgo;

public class HistoryNgo extends AppCompatActivity {
    RecyclerView hist;
    HistoryItemAdapter adapter;
    List<HistoryItemNgo> historyItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_ngo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("History");
        hist = (RecyclerView) findViewById(R.id.hist);
        historyItemList = new ArrayList<>();
        adapter = new HistoryItemAdapter(this, historyItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        hist.setLayoutManager(layoutManager);
        hist.setAdapter(adapter);
        new BackTask(this).execute();
        adapter.notifyDataSetChanged();


    }

    public int getThumbnailId(String title) {
        int id = 0;
        switch (title.toLowerCase()) {
            case "clothes":
                id = R.drawable.uniform;
                break;
            case "utensils":
                id = R.drawable.fryingpan;
                break;
            case "shoes":
                id = R.drawable.shoes;
                break;
            case "books":
                id = R.drawable.books;
                break;
            case "toys":
                id = R.drawable.train;
                break;
            case "food":
                id = R.drawable.food;
                break;
            default: id = R.mipmap.ic_launcher;

        }
        return id;
    }

    void abc() {
        final String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mmyRef = database.getReference("endUsers").child(Uid).child("Donations_item_Details");
        mmyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot postSnapshot : Snapshot.getChildren()) {
                        String t = (String) postSnapshot.child("title").getValue();
                        String m = (String) postSnapshot.child("message").getValue();
                        historyItemList.add(new HistoryItemNgo(t, getThumbnailId(t), "Dummy NGO", "Dummy Address",
                                m, (Long) postSnapshot.child("quantity").getValue()));
                        adapter.notifyDataSetChanged();
//                        Log.i("History" ,Title1.get(i)+"\n"+Message1.get(i)+"\n"+Quantity1.get(i));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public class BackTask extends AsyncTask<Void, Void, Void> {


        Context mContext;

        public BackTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //abc();
            xyz();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            //checkEmpty();
        }
    }
    public void xyz(){
        FirebaseUser firebaseAuth=FirebaseAuth.getInstance().getCurrentUser();
        String Uid=firebaseAuth.getUid();
        DatabaseReference mRef= FirebaseDatabase.getInstance().getReference();
        mRef.child("Ngos").child("NGO1").child("ngoUsers").child(Uid).child("Name").setValue("harsh");

        //final String use[]=new String[2];
        //final int[] i = {0};
        mRef.child("Ngos").child("NGO1").child("endUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    //Toast.makeText(MainUi.this, "" + dataSnapshot1.getKey(), Toast.LENGTH_LONG).show();
                    //use[i[0]]=dataSnapshot1.getKey();

                    //Toast.makeText(MainUi.this, ""+use[i[0]], Toast.LENGTH_LONG).show();
                    //++i[0];

                    //Toast.makeText(HistoryNgo.this, ""+dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference mmRef = database.getReference("endUsers").child(dataSnapshot1.getKey()).child("Donations_item_Details");
                    mmRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //TODO get count particular user detail item
                            Toast.makeText(HistoryNgo.this, ""+dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                //Toast.makeText(MainUi.this, ""+postSnapshot.getKey(), Toast.LENGTH_LONG).show();
                                for(DataSnapshot Snapshot:postSnapshot.getChildren()) {

                                    String t = (String) Snapshot.child("title").getValue();
                                    String m = (String) Snapshot.child("message").getValue();
                                    String ngo = (String) Snapshot.child("ngoLocation").getValue();
                                    String date = (String) Snapshot.child("date").getValue();
                                    //Toast.makeText(HistoryNgo.this, ""+t+"\n"+m+"\n"+ngo+"\n"+date, Toast.LENGTH_LONG).show();

                                    historyItemList.add(new HistoryItemNgo(t,getThumbnailId(t),ngo,"Dummy Address",m,(Long)Snapshot.child("quantity").getValue()));
                                    adapter.notifyDataSetChanged();

                                }
                            }}

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

}
