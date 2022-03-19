package com.pra.myapplication.UI.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pra.myapplication.R;


/**
 * this class used as abstract class for Activity onCreate - Cointains - also some abstract method
 * also include fragment structure
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context ctx;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResourceId());
        ctx = this;
        initComponents();
        prepareView();
        setActionListeners();

    }

    protected abstract void initComponents();

    protected abstract int getLayoutResourceId();

    protected abstract void prepareView();

    protected abstract void setActionListeners();

}