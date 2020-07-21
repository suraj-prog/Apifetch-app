package com.example.apifetch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>
{
    private Context mCtx;
    private List<Contact> contactList;

    public ContactAdapter(Context mCtx, List<Contact> contactList) {
        this.mCtx = mCtx;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.contact_list, null);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact product = contactList.get(position);
        holder.textViewTitle.setText(product.getName());
        holder.textViewShortDesc.setText(product.getEmail());
        holder.textViewRating.setText(String.valueOf(product.getPhone()));
        holder.textViewPrice.setText(String.valueOf(product.getMessage()));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        public ContactViewHolder(View view){
            super(view);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}
