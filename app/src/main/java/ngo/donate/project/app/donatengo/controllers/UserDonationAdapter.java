package ngo.donate.project.app.donatengo.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ngo.donate.project.app.donatengo.R;
import ngo.donate.project.app.donatengo.model.AcceptItems;
import ngo.donate.project.app.donatengo.model.UserDonationDetails;

/**
 * Created by ArupPc on 25-03-2017.
 */

public class UserDonationAdapter extends RecyclerView.Adapter<UserDonationAdapter.userItemsVH> {

 private Context context;
    private List<UserDonationDetails> userList;
    UserDonationDetails d;
    public UserDonationAdapter(Context c, List<UserDonationDetails> list){
        this.context = c;
        this.userList = list;
        d=null;

    }

    @Override
    public userItemsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_name_item,parent,false);
        return new userItemsVH(v);
    }

    @Override
    public void onBindViewHolder(final userItemsVH holder, int position) {

        d = userList.get(position);
        holder.userName.setText(d.getUser_name());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "User Name: " + holder.userName.getText().toString() + "\nUser Address:" + d.getAddress() + "\n\n";
                List<AcceptItems> l = d.getItemsList();
                for(AcceptItems x:l){
                    data+=  "\nTitle:" + x.getTitle() + "\nDate:" + x.getDate() + "\nMessage:" + x.getMessage() +
                            "\nQuantity:" + x.getQuantity() +
                            "NGO:" + x.getNgoLocaton()+"\n\n";
                }

                new AlertDialog.Builder(context).setIcon(null)
                        .setTitle(holder.userName.getText().toString())
                        .setMessage(data)
                        .setCancelable(true)
                        .setPositiveButton("OK",null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class userItemsVH extends RecyclerView.ViewHolder{
        TextView userName;
        LinearLayout ll;
        public userItemsVH(View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.user_name);
            ll = (LinearLayout)itemView.findViewById(R.id.singleItemLayout);

        }


    }


}
