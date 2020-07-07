package com.larchsys.lock;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;


public class GlobalAccessibilityService extends AccessibilityService {

    public static GlobalAccessibilityService Instance;

    @Override
    public void onServiceConnected(){
        super.onServiceConnected();
        Instance = this;
    }

    public void lock(){
        this.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        // return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    }

    @Override
    public void onInterrupt() {
    }
}