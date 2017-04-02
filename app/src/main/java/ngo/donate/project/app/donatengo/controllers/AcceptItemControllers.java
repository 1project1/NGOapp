package ngo.donate.project.app.donatengo.controllers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ngo.donate.project.app.donatengo.R;
import ngo.donate.project.app.donatengo.model.AcceptItems;
import ngo.donate.project.app.donatengo.model.UserDonationDetails;

/**
 * Created by ArupPc on 01-04-2017.
 */

public class AcceptItemControllers extends RecyclerView.Adapter<AcceptItemControllers.UserItemVH> {

    List<AcceptItems> itemList;
    AcceptItems a;
    UserDonationDetails d;
    Context c;
    ItemClickListener listener;

    public AcceptItemControllers(Context c, List<AcceptItems> itemList) {
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
        a = itemList.get(position);

        holder.itemTitle.setText(a.getTitle());
        holder.itemDate.setText("Date: " + a.getDate());
        holder.itemQuantity.setText("Quantity:"+ a.getQuantity());
        holder.itemRequestStatus.setText("Request Status: " + a.getMessage());
        holder.itemIcon.setImageResource(mapIcon(a.getTitle()));
        holder.ngoLocation.setText("Ngo Location: " + a.getNgoLocaton());

    }

    private int mapIcon(String title) {
        int id;
        switch (title.toLowerCase()){
            case "others":
                id = R.drawable.food;
                break;
            case "books":
                id = R.drawable.books;break;
            case "toys":
                id = R.drawable.train;break;
            case "utensils":
                id = R.drawable.fryingpan;break;
            case "shoes":
                id = R.drawable.shoes;
                break;
            default:
                id = R.mipmap.dummy;

        }
        return id;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface ItemClickListener {
         void onSingleClick(int position);

         void onLongClick(int position);
    }

    public class UserItemVH extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView itemTitle, itemQuantity, itemDate, itemRequestStatus, ngoLocation;
        ImageView itemIcon;
        CheckBox selectedItem;
        RelativeLayout parentView;

        public UserItemVH(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            itemQuantity = (TextView) itemView.findViewById(R.id.itemQuantity);
            itemDate = (TextView) itemView.findViewById(R.id.itemDate);
            itemRequestStatus = (TextView) itemView.findViewById(R.id.messageItem);
            ngoLocation = (TextView) itemView.findViewById(R.id.itemNgoLocation);
            itemIcon = (ImageView)itemView.findViewById(R.id.itemIcon);
            selectedItem = (CheckBox)itemView.findViewById(R.id.selectItem);
            parentView = (RelativeLayout)itemView.findViewById(R.id.parentView);
            parentView.setOnClickListener(this);
            parentView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
              if(view.getId() == R.id.selectItem){
                  if(selectedItem.isChecked())
                      selectedItem.setChecked(false);
                  else
                      selectedItem.setChecked(true);

            }
            else if (view.getId() == R.id.parentView && selectedItem.getVisibility() == View.INVISIBLE)
            {
                listener.onSingleClick(getAdapterPosition());
            }

        }

        @Override
        public boolean onLongClick(View view) {
            listener.onSingleClick(getAdapterPosition());
            selectedItem.setVisibility(View.VISIBLE);
            selectedItem.setChecked(true);

            for(AcceptItems x : itemList){

                x.setCheckedAndVisib(selectedItem);
            }
            return true;
        }
    }

    public void setListener(final ItemClickListener itemClickCallBack){
        this.listener = itemClickCallBack;
    }

}
