package ngo.donate.project.app.donatengo;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        //new BackTask(this).execute();
        adapter.notifyDataSetChanged();


    }

    public int getThumbnailId(String title) {
        int id = 0;
        switch (title.toLowerCase()) {
            case "clothes":
                id = R.mipmap.ic_launcher;
                break;
            case "utensils":
                id = R.mipmap.ic_launcher;
                break;
            case "shoes":
                id = R.mipmap.ic_launcher;
                break;
            case "bags":
                id = R.mipmap.ic_launcher;
                break;
            case "furniture":
                id = R.mipmap.ic_launcher;
                break;
            case "bedsheets":
                id = R.mipmap.ic_launcher;
                break;
            case "toys":
                id = R.mipmap.ic_launcher;
                break;

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
            abc();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            //checkEmpty();
        }
    }


}
