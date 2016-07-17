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

import com.hannesdorfmann.adapterdelegates2.AdapterDelegate;
import com.slyfox.recall.model.ContactModel;
import com.slyfox.recall.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eugene on 25.06.2016.
 *
 * This delegate handles presentation of a single ContactModel in the RecyclerView
 */
public class ContactAdapterDelegate implements AdapterDelegate<List<ContactModel>> {

    public static final int ASK_FOR_CALL_BUTTON = 0;
    public static final int ASK_FOR_MONEY_BUTTON = 1;

    public interface AskButtonListener {
        void onAskButtonClick(int button, long contactId);
    }

    private LayoutInflater inflater; //is needed to inflate view for list item
    private Context context;
    private AskButtonListener listener;

    public ContactAdapterDelegate(Activity activity, AskButtonListener listener) {
        this.inflater = activity.getLayoutInflater();
        this.context = activity;
        this.listener = listener;
    }

    @Override
    public boolean isForViewType(@NonNull List<ContactModel> items, int position) {
        //We don't have any other item types, so just ensure that item is not null
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
        final ContactModel model = items.get(position);

        cvh.contactNameView.setText(model.getName()); //set contact name

        //Set listener to handle "Ask for call" button click
        cvh.askForCallView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAskButtonClick(ASK_FOR_CALL_BUTTON, model.getId());
            }
        });

        //Set listener to handle "Ask for money" button click
        cvh.askForMoneyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAskButtonClick(ASK_FOR_MONEY_BUTTON, model.getId());
            }
        });
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.contactNameView)
        TextView contactNameView;
        @BindView(R.id.askCallButton)
        ImageView askForCallView;
        @BindView(R.id.askMoneyButton)
        ImageView askForMoneyView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView); //we use ButterKnife to get all item's views
        }
    }
}
