package com.slyfox.recall.domain.permissions;

import com.slyfox.recall.domain.ContactPresenter;

/**
 * Created by edono on 08.10.2016.
 *
 * Filter to resolver android M dynamic permissions.
 * After resolving permissions, delegate all actions
 * to real presenter
 */
public class PermissionsPresenterProxy extends ContactPresenter {
    private final ContactPresenter presenter;
    private final PermissionManager manager;
    private final IPermissionView permissionView;


    private interface DelegateAction {
        void delegate();
    }

    public PermissionsPresenterProxy(ContactPresenter presenter, PermissionManager manager, IPermissionView permissionView) {
        super(null, null, null);
        this.presenter = presenter;
        this.manager = manager;
        this.permissionView = permissionView;
    }

    @Override
    public void loadContacts() {
        resolve(new DelegateAction() {
            @Override
            public void delegate() {
                presenter.loadContacts();
            }
        });
    }

    @Override
    public void askForCall(final long contactId) {
        resolve(new DelegateAction() {
            @Override
            public void delegate() {
                presenter.askForCall(contactId);
            }
        });
    }

    @Override
    public void askForMoney(final long contactId) {
        resolve(new DelegateAction() {
            @Override
            public void delegate() {
                presenter.askForMoney(contactId);
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        manager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void resolve(final DelegateAction delegateAction) {
        if (manager.contactsPermissionsGranted()) {
            delegateAction.delegate();
        } else {
            manager.resolvePermissions(new PermissionManager.Callback() {
                @Override
                public void permissionGranted() {
                    delegateAction.delegate();
                }

                @Override
                public void permissionRefused() {
                    //TODO: normal permissions denied message
                    permissionView.showPermissionsDeniedMessage("Sorry message");
                }
            });
        }
    }
}
