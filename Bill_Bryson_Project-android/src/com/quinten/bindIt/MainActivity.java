package com.quinten.bindIt;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.quinten.bindIt.BillBryson;
import com.quinten.bindIt.Reference;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = Reference.USE_GL_2_0;
        
        initialize(new BillBryson(), cfg);
    }
}