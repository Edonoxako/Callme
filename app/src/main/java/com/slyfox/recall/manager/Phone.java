package com.slyfox.recall.manager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.slyfox.recall.domain.IPhone;

/**
 * Created by edono on 28.08.2016.
 */
public class Phone implements IPhone {

    public static final String URI_SCHEME_PREFIX = "tel:";

    private Context context;

    public Phone(Context context) {
        this.context = context;
    }

    /**
     * Sends request
     * @param number ready to be sent request with phone number and service codes
     */
    @Override
    public void makeRequest(String number) {
        number = number.replace("*", Uri.encode("*")).replace("#", Uri.encode("#"));
        Intent requestIntent = new Intent(Intent.ACTION_DIAL);
        requestIntent.setData(Uri.parse(URI_SCHEME_PREFIX + number));
        context.startActivity(requestIntent);
    }
}
