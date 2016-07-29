package com.augmentis.ayp.keepwalking;

import android.support.v4.app.Fragment;

/**
 * Created by Amita on 7/27/2016.
 */
public class NoteListActivity extends ManuScriptFragmentActivity {

    @Override
    protected Fragment onCreateFragment() {
        return new NoteListFragment();
    }
}
