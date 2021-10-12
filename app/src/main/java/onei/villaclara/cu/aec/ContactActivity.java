package onei.villaclara.cu.aec;

/**
 * AEC
 * @author Abel Alejandro Fleitas Perdomo
 * @since  2019
 **/

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import onei.villaclara.cu.aec.utils.LanzarToast;

public class ContactActivity extends AppCompatActivity {

    LinearLayout email,webpage,tel,fb,twi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        email = (LinearLayout) findViewById(R.id.email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.co_email)});
                emailIntent.putExtra(Intent.EXTRA_CC, new String[]{});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                emailIntent.setType("message/rfc822");
                PackageManager packageManager = getPackageManager();
                List activities = packageManager.queryIntentActivities(emailIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;
                if (isIntentSafe) {
                    startActivity(Intent.createChooser(emailIntent,"Seleccione la app"));
                } else {
                    LanzarToast.showToastOficial(ContactActivity.this, R.string.call_email);
                }
            }
        });
        webpage = (LinearLayout) findViewById(R.id.webpage);
        webpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.co_web)));
                startActivity(i);
            }
        });

        tel = (LinearLayout) findViewById(R.id.tel);
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.DIAL",
                        Uri.parse("tel:" + getString(R.string.co_telreal)));
                PackageManager packageManager = getPackageManager();
                List activities = packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;
                if (isIntentSafe) {
                    startActivity(intent);
                } else {
                    LanzarToast.showToastOficial(ContactActivity.this, R.string.call_email);
                }
            }
        });

        fb = (LinearLayout) findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.co_facebook)));
                startActivity(i);
            }
        });

        /*twi = (LinearLayout) findViewById(R.id.twi);
        twi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.co_facebook)));
                startActivity(i);
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ContactActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
        ContactActivity.this.finish();
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
