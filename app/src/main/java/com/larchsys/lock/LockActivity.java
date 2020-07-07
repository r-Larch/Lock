package com.larchsys.lock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

public class LockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lock(this.getApplicationContext());
    }


    private void lock(Context context){
        if (this.isAccessibilityServiceRegistered(context)) {
            if (GlobalAccessibilityService.Instance != null) {
                GlobalAccessibilityService.Instance.lock();
                this.finish();
            }
        } else {
            // Open Accessibility Settings
            this.startActivityForResult(new Intent("android.settings.ACCESSIBILITY_SETTINGS").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK), 0);
        }
    }


    private boolean isAccessibilityServiceRegistered(Context context){
        String services = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services");

        String name = context.getPackageName() + "/" + GlobalAccessibilityService.class.getName();
        return services.contains(name);

    }
}


