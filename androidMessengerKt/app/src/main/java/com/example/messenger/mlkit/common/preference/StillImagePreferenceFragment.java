package com.example.messenger.mlkit.common.preference;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.messenger.R;

/** Configures still image demo settings. */
public class StillImagePreferenceFragment extends PreferenceFragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preference_still_image);
  }
}
