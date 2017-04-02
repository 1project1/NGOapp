package ngo.donate.project.app.donatengo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    ProgressBar pHistBar;
    /*final String Name[][]=new String[2][10];
    final int[] i = {0};
    final int[] j = {0};
       */
    List<String> Name=new ArrayList<String>(){};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_ngo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("History");
        pHistBar = (ProgressBar)findViewById(R.id.pHistBar);
        pHistBar.setVisibility(View.VISIBLE);
        hist = (RecyclerView) findViewById(R.id.hist);
        historyItemList = new ArrayList<>();
        adapter = new HistoryItemAdapter(this, historyItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        hist.setLayoutManager(layoutManager);
        hist.setAdapter(adapter);
        xyz();
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
            //xyz();

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
        //mRef.child("Ngos").child("NGO1").child("ngoUsers").child(Uid).child("Name").setValue("harsh");

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
                    //TODO
                    //Toast.makeText(HistoryNgo.this, ""+dataSnapshot1.getKey(), Toast.LENGTH_LONG).show();
                    //Name[i[0]++][j[0]++]=(String)dataSnapshot1.getKey();
                    Name.add(dataSnapshot1.getKey());
                    //Toast.makeText(HistoryNgo.this, "Name:"+Name.get(0), Toast.LENGTH_SHORT).show();
                    //Name[i[0]][j[0]]=dataSnapshot1.getKey();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference mmRef = database.getReference("endUsers").child(dataSnapshot1.getKey()).child("Donations_item_Details");
                    mmRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //TODO get count particular user detail item
                           // Toast.makeText(HistoryNgo.this, ""+dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                            long a=dataSnapshot.getChildrenCount();
                            String str=Long.toString(a);
                            Name.add(str);
                            //++j[0];
                            //Name[i[0]][j[0]++]= String.valueOf(dataSnapshot.getChildren());

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                               // Toast.makeText(HistoryNgo.this, ""+postSnapshot.getKey(), Toast.LENGTH_LONG).show();
                                //Name[i[0]][j[0]++]=(String)postSnapshot.getKey();
                                Name.add(postSnapshot.getKey());
                                for(DataSnapshot Snapshot:postSnapshot.getChildren()) {


                                  //  Name[i[0]][j[0]++]=(String) Snapshot.child("title").getValue();
                                    String t = (String) Snapshot.child("title").getValue();
                                    Name.add(t);
                                    //Name[i[0]][j[0]++]=(String) Snapshot.child("message").getValue();
                                    String m = (String) Snapshot.child("message").getValue();
                                    Name.add(m);
                                    //  Name[i[0]][j[0]++]=(String) Snapshot.child("ngoLocation").getValue();
                                    String ngo = (String) Snapshot.child("ngoLocation").getValue();
                                    Name.add(ngo);
                                    //  Name[i[0]][j[0]++]=(String) Snapshot.child("date").getValue();
                                    String date = (String) Snapshot.child("date").getValue();
                                    Name.add(date);
                                    //Toast.makeText(HistoryNgo.this, ""+t+"\n"+m+"\n"+ngo+"\n"+date, Toast.LENGTH_LONG).show();

                                    historyItemList.add(new HistoryItemNgo(t,getThumbnailId(t),ngo,"Dummy Address",m,(Long)Snapshot.child("quantity").getValue()));
                                    adapter.notifyDataSetChanged();
                                    if(!historyItemList.isEmpty())pHistBar.setVisibility(View.GONE);
                                    else pHistBar.setVisibility(View.VISIBLE);

                                   /* Toast.makeText(HistoryNgo.this, "Name:"+Name.get(0), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(HistoryNgo.this, "Name:"+Name.get(1), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(HistoryNgo.this, "Name:"+Name.get(2), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(HistoryNgo.this, "Name:"+Name.get(3), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(HistoryNgo.this, "Name:"+Name.get(4), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(HistoryNgo.this, "Name:"+Name.get(5), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(HistoryNgo.this, "Name:"+Name.get(6), Toast.LENGTH_SHORT).show();
*/

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
