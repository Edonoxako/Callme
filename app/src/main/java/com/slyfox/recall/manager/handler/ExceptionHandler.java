package com.slyfox.recall.manager.handler;

import android.content.Context;
import android.widget.Toast;

import com.slyfox.recall.domain.IExceptionHandler;
import com.slyfox.recall.exception.UnknownOperatorException;

/**
 * Created by edono on 23.10.2016.
 */

public class ExceptionHandler implements IExceptionHandler {

    private Context context;

    public ExceptionHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleException(Exception e) {
        if (e instanceof UnknownOperatorException) {
            Toast.makeText(context, "Your mobile opearator is not supported yet", Toast.LENGTH_SHORT).show();
        }
    }
}
