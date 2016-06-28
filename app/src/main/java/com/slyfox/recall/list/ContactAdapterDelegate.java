package com.slyfox.recall.list;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.adapterdelegates2.AdapterDelegate;
import com.slyfox.recall.model.ContactModel;
import com.slyfox.recall.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eugene on 25.06.2016.
 */
public class ContactAdapterDelegate implements AdapterDelegate<List<ContactModel>> {

    private LayoutInflater inflater;
    private Context context;

    public ContactAdapterDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
        context = activity;
    }

    @Override
    public boolean isForViewType(@NonNull List<ContactModel> items, int position) {
        return items.get(position) != null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ContactViewHolder(inflater.inflate(R.layout.contact_list_item, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<ContactModel> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        ContactViewHolder cvh = (ContactViewHolder) holder;
        ContactModel model = items.get(position);

        cvh.contactNameView.setText(model.getName());
        cvh.contactNumberView.setText(model.getNumbers().get(0));

        cvh.askForCallView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Ask for call", Toast.LENGTH_SHORT).show();
            }
        });

        cvh.askForMoneyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Ask for money", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.contactNameView)
        TextView contactNameView;
        @BindView(R.id.contactNumberView)
        TextView contactNumberView;
        @BindView(R.id.askCallButton)
        ImageView askForCallView;
        @BindView(R.id.askMoneyButton)
        ImageView askForMoneyView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
