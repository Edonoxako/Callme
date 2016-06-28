package com.slyfox.recall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.slyfox.recall.list.ContactListAdapter;
import com.slyfox.recall.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.contactList)
    RecyclerView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ContactListAdapter adapter = new ContactListAdapter(this, getContacts());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        contactList.setAdapter(adapter);
        contactList.setLayoutManager(layoutManager);
    }

    private List<ContactModel> getContacts() {
        List<ContactModel> models = new ArrayList<>();
        List<String> numbers = new ArrayList<>();

        numbers.add("+71234567890");

        models.add(new ContactModel(0, "User 1", numbers));
        models.add(new ContactModel(1, "User 2", numbers));
        models.add(new ContactModel(2, "User 3", numbers));
        models.add(new ContactModel(3, "User 4", numbers));

        return models;
    }
}
