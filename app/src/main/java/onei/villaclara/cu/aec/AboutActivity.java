package onei.villaclara.cu.aec;

/**
 * AEC
 * @author Abel Alejandro Fleitas Perdomo
 * @since  2019
 **/

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        LinearLayout ll=(LinearLayout) findViewById(R.id.linearEmail);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.email_autor)});
                emailIntent.putExtra(Intent.EXTRA_CC, new String[]{});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.error) + " " + getString(R.string.app_name));
                emailIntent.setType("message/rfc822");
                PackageManager packageManager = getPackageManager();
                List activities = packageManager.queryIntentActivities(emailIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;
                if (isIntentSafe) {
                    startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.choose)));
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.noappemail), Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*LinearLayout ll2=(LinearLayout) findViewById(R.id.linearEmailColab);
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.email_colaborador)});
                emailIntent.putExtra(Intent.EXTRA_CC, new String[]{});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.error) + " " + getString(R.string.app_name));
                emailIntent.setType("message/rfc822");
                PackageManager packageManager = getPackageManager();
                List activities = packageManager.queryIntentActivities(emailIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;
                if (isIntentSafe) {
                    startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.choose)));
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.noappemail), Toast.LENGTH_SHORT).show();
                }
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AboutActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
        AboutActivity.this.finish();
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
