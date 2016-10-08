package com.slyfox.recall.domain.permissions;

/**
 * Created by edono on 08.10.2016.
 */
public interface PermissionManager {

    interface Callback {
        void permissionGranted();
        void permissionRefused();
    }

    boolean contactsPermissionsGranted();

    void resolvePermissions(Callback callback);

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
}
