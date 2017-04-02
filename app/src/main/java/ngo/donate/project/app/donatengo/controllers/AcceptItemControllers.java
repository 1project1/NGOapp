package ngo.donate.project.app.donatengo.controllers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ngo.donate.project.app.donatengo.R;
import ngo.donate.project.app.donatengo.model.AcceptItems;
import ngo.donate.project.app.donatengo.model.UserDonationDetails;

/**
 * Created by ArupPc on 01-04-2017.
 */

public class AcceptItemControllers extends RecyclerView.Adapter<AcceptItemControllers.UserItemVH> {

    List<UserDonationDetails> itemList;
    AcceptItems a;
    UserDonationDetails d;
    Context c;
    ItemClickListener listener;

    public AcceptItemControllers(Context c, List<UserDonationDetails> itemList) {
        this.itemList = itemList;
        this.c = c;
    }

    @Override
    public UserItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.single_accept_item,parent,false);
        return new UserItemVH(v);
    }

    @Override
    public void onBindViewHolder(UserItemVH holder, int position) {
        d = itemList.get(position);

        holder.itemTitle.setText("");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface ItemClickListener {
        public void onSingleClick(int position);

        public void onLongClick(int position);
    }

    public class UserItemVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView itemTitle, itemQuantity, itemDate, itemRequestStatus, ngoLocation;
        ImageView itemIcon;
        CheckBox selectedItem;

        public UserItemVH(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            itemQuantity = (TextView) itemView.findViewById(R.id.itemQuantity);
            itemDate = (TextView) itemView.findViewById(R.id.itemDate);
            itemRequestStatus = (TextView) itemView.findViewById(R.id.messageItem);
            ngoLocation = (TextView) itemView.findViewById(R.id.itemNgoLocation);
            itemIcon = (ImageView)itemView.findViewById(R.id.itemIcon);
            selectedItem = (CheckBox)itemView.findViewById(R.id.selectItem);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.parentView)
            {
                listener.onLongClick(getAdapterPosition());
                listener.onSingleClick(getAdapterPosition());
            }
        }
    }

    public void setListener(final ItemClickListener itemClickCallBack){
        this.listener = itemClickCallBack;
    }

}
