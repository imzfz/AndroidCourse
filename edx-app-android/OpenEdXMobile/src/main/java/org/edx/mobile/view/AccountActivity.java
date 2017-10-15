package org.edx.mobile.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.edx.mobile.base.BaseSingleFragmentActivity;
import org.edx.mobile.logger.Logger;

public class AccountActivity extends BaseSingleFragmentActivity {
    protected Logger logger = new Logger(getClass().getSimpleName());

    public static Intent newIntent(Activity activity) {
        return new Intent(activity, AccountActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureDrawer();
    }

    @Override
    public Fragment getFirstFragment() {
        return new AccountFragment();
    }
}
