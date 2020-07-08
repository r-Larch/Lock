package com.larchsys.lock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.widget.Toast;


public class LockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // just lock..
        this.lock();
    }


    private void lock() {
        Context context = this.getApplicationContext();

        if (!this.isServiceRegistered(context)) {
            // Open Accessibility Settings
            this.startActivityForResult(new Intent("android.settings.ACCESSIBILITY_SETTINGS").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK), 0);
            this.finish();
            return;
        }

        // android has no convenient way of calling a method on an AccessibilityService
        // so we hope it gets initialized before we need it.
        // and if it was too slow, we let the user try again to win time 😥

        if (GlobalAccessibilityService.Instance == null) {
            Toast.makeText(context, "Try again..", Toast.LENGTH_SHORT).show();
            this.finish();
            return;
        }

        // do the lock!
        {
            GlobalAccessibilityService.Instance.lock();
            this.finish();

            // ensure we never see an Activity!
            Process.killProcess(Process.myPid());
        }
    }


    private boolean isServiceRegistered(Context context) {
        String services = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services");

        String name = context.getPackageName() + "/" + GlobalAccessibilityService.class.getName();
        return services.contains(name);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_OK){
            this.lock();
        }
    }
}


