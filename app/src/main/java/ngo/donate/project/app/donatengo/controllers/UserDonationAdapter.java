package ngo.donate.project.app.donatengo.controllers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ngo.donate.project.app.donatengo.R;
import ngo.donate.project.app.donatengo.model.UserDonationDetails;

/**
 * Created by ArupPc on 25-03-2017.
 */

public class UserDonationAdapter extends RecyclerView.Adapter<UserDonationAdapter.userItemsVH> {

 private Context context;
    private List<UserDonationDetails> userList;
    UserDonationDetails d;
    private ItemClickCallBack itemClickCallBack;
    public UserDonationAdapter(Context c, List<UserDonationDetails> list){
        this.context = c;
        this.userList = list;

    }

    public void setListData(ArrayList listData) {
        this.userList.clear();
        this.userList.addAll(listData);
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

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class userItemsVH extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView userName;
        LinearLayout ll;
        public userItemsVH(View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.user_name);
            ll = (LinearLayout)itemView.findViewById(R.id.singleItemLayout);
            ll.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.singleItemLayout){
                itemClickCallBack.onItemClick(getAdapterPosition());

            }
        }
    }


     public interface ItemClickCallBack{
        void onItemClick(int position);
        void onSecondaryIconClick(int position);

    }

    public void setItemClickCallBack(final ItemClickCallBack itemClickCallBack){
        this.itemClickCallBack = itemClickCallBack;
    }

}
