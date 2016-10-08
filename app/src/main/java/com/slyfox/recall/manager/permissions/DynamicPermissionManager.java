package com.slyfox.recall.manager.permissions;

import android.Manifest;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.slyfox.recall.domain.permissions.PermissionManager;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by edono on 08.10.2016.
 */

public class DynamicPermissionManager implements PermissionManager {

    private AppCompatActivity activity;
    private View activityView;

    private Callback callback;

    public DynamicPermissionManager(AppCompatActivity activity, View activityView) {
        this.activity = activity;
        this.activityView = activityView;
        Nammu.init(activity.getApplicationContext());
    }

    @Override
    public boolean contactsPermissionsGranted() {
        return Nammu.checkPermission(READ_CONTACTS);
    }

    private PermissionCallback permissionContactsCallback = new PermissionCallback() {
        @Override
        public void permissionGranted() {
            if (callback != null) {
                callback.permissionGranted();
                callback = null;
            }
        }

        @Override
        public void permissionRefused() {
            if (callback != null) {
                callback.permissionRefused();
                callback = null;
            }
        }
    };

    @Override
    public void resolvePermissions(Callback callback) {
        this.callback = callback;

        if (Nammu.shouldShowRequestPermissionRationale(activity, READ_CONTACTS)) {
            //TODO: normal rationale message
            Snackbar.make(activityView, "Here we explain user why we need to know his/her contacts.",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Nammu.askForPermission(activity, Manifest.permission.READ_CONTACTS, permissionContactsCallback);
                        }
                    }).show();
        } else {
            Nammu.askForPermission(activity, Manifest.permission.READ_CONTACTS, permissionContactsCallback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
