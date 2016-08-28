package com.slyfox.recall.manager;

import android.content.Context;
import android.widget.Toast;

import com.slyfox.recall.domain.IPhone;

/**
 * Created by edono on 28.08.2016.
 */
public class Phone implements IPhone {

    private Context context;

    public Phone(Context context) {
        this.context = context;
    }

    @Override
    public void makeRequest(String number) {
        Toast.makeText(context, number, Toast.LENGTH_SHORT).show();
    }
}
