package ngo.donate.project.app.donatengo.controllers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ngo.donate.project.app.donatengo.R;
import ngo.donate.project.app.donatengo.model.UserDonationDetails;

/**
 * Created by ArupPc on 25-03-2017.
 */

public class UserDonationAdapter extends RecyclerView.Adapter<UserDonationAdapter.userItemsVH> {

 private Context context;
    private List<UserDonationDetails> userList;

    public UserDonationAdapter(Context c, List<UserDonationDetails> list){
        this.context = c;
        this.userList = list;

    }

    @Override
    public userItemsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_name_item,parent,false);
        return new userItemsVH(v);
    }

    @Override
    public void onBindViewHolder(userItemsVH holder, int position) {
        UserDonationDetails d = userList.get(position);
        holder.userName.setText(d.getUser_name());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class userItemsVH extends RecyclerView.ViewHolder{
        TextView userName;

        public userItemsVH(View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.user_name);
        }
    }


}
