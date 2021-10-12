package onei.villaclara.cu.aec;

/**
 * AEC
 * @author Abel Alejandro Fleitas Perdomo
 * @since  2019
 **/

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import onei.villaclara.cu.aec.utils.LanzarToast;
import onei.villaclara.cu.aec.utils.Preferencias;

public class SettingsActivity extends AppCompatActivity {

    private Preference share,shortcut;
    private Preferencias preferencias;
    private SharedPreferences prefShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new GeneralPreferenceFragment())
                .commit();
    }

    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }


    @SuppressLint("ValidFragment")
    private class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferencias);
            setHasOptionsMenu(true);
            onCreateView(getLayoutInflater(),null,savedInstanceState);
            prefShared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            preferencias = new Preferencias(prefShared.getString("orientacion","1"));
            share = findPreference("share_app");
            shortcut = findPreference("pref_homescreen_shortcut");
            bindPreferenceSummaryToValue(findPreference("orientacion"));
            share.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "AEC");
                    String sAux = "\nhttps://www.apklis.cu/es/application/cu.uci.droidlab.onei/3.0 \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i,"Seleccione una opción"));
                    return true;
                }
            });
            shortcut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent shortcutIntent = new Intent(SettingsActivity.this, MainActivity.class);
                    shortcutIntent.setAction("android.intent.action.MAIN");
                    Intent addIntent = new Intent();
                    addIntent.putExtra("android.intent.extra.shortcut.INTENT", shortcutIntent);
                    addIntent.putExtra("android.intent.extra.shortcut.NAME", getResources().getString(R.string.app_name));
                    addIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    addIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    addIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(getApplicationContext(),R.mipmap.ic_launcher));
                    addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
                    getApplicationContext().sendBroadcast(addIntent);
                    LanzarToast.showToastOficial(SettingsActivity.this,R.string.addedHomeScreenShortcout);
                    return true;
                }
            });

        }
    }


    private  void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(),"1"));

    }

    private  Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            if(preference instanceof ListPreference && preference.getKey().equalsIgnoreCase("orientacion")){
                String va = value.toString();
                preferencias.setSumary(va);
                preference.setSummary(preferencias.getSumary());
            }
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
        SettingsActivity.this.finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info_notes, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
