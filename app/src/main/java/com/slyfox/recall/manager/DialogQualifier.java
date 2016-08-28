package com.slyfox.recall.manager;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.slyfox.recall.R;
import com.slyfox.recall.domain.INumberQualifier;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by edono on 28.08.2016.
 */
public class DialogQualifier extends DialogFragment implements INumberQualifier {

    public static final String NUMBERS_ARG = "numbers";

    private static FragmentManager fragmentManager;
    private static Callback callback;

    public static DialogQualifier create(FragmentManager fragmentManager) {
        DialogQualifier.fragmentManager = fragmentManager;
        return new DialogQualifier();
    }

    @Override
    public void qualify(Collection<String> numbers, Callback callback) {
        DialogQualifier.callback = callback;

        Bundle args = new Bundle();
        ArrayList<String> numberList = new ArrayList<>(numbers);
        args.putStringArrayList(NUMBERS_ARG, numberList);

        setArguments(args);
        show(fragmentManager, "qualifier");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<String> numberList = getArguments().getStringArrayList(NUMBERS_ARG);

        if (numberList == null) throw new IllegalArgumentException("Phone numbers are not provided for qualifier dialog");

        String[] numberArray = numberList.toArray(new String[numberList.size()]);

        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.qualifier_title)
                .setItems(numberArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.onQualifiedNumber(numberList.get(i));
                    }
                })
                .create();
    }
}
