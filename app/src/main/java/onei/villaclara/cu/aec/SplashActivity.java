package onei.villaclara.cu.aec;

/**
 * AEC
 * @author Abel Alejandro Fleitas Perdomo
 * @since  2019
 **/

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.github.jorgecastillo.FillableLoader;
import com.github.jorgecastillo.listener.OnStateChangeListener;

import java.util.ArrayList;

import onei.villaclara.cu.aec.basedatos.DataMarcadores;
import onei.villaclara.cu.aec.utils.Book;

public class SplashActivity extends AppCompatActivity {

    FillableLoader loader;
    TextView tvLoading;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_splash);
        tvLoading = (TextView) findViewById(R.id.tvLoading);
        loader = (FillableLoader) findViewById(R.id.fillableLoader);
        getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        this.loader.setSvgPath(getString(R.string.fillable_path));
        changeStatusBarColor();
        SplashActivity.this.init();
    }


    public void init() {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
                return null;
            }

            protected void onPreExecute() {
                SplashActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        SplashActivity.this.loader.start();
                        SplashActivity.this.loader.setOnStateChangeListener(new OnStateChangeListener() {
                            public void onStateChange(int state) {
                                if (state == 2) {
                                    SplashActivity.this.tvLoading.setVisibility(View.VISIBLE);
                                }
                                if (state == 3) {
                                    SplashActivity.this.tvLoading.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });
            }

            protected void onPostExecute(Void aVoid) {
                SplashActivity.this.createBd();
                SplashActivity.this.showSuccessfullySavedMessage();
            }
        }.execute(new Void[0]);
    }


    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void showSuccessfullySavedMessage() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
                SplashActivity.this.finish();
            }
        }, 2500);
    }


    protected void onResume() {
        getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        super.onResume();
    }

    private void createBd(){
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(0,getResources().getString(R.string.c0),0,0));
        books.add(new Book(1,getResources().getString(R.string.c1),0,0));
        books.add(new Book(2,getResources().getString(R.string.c2),0,0));
        books.add(new Book(3,getResources().getString(R.string.c3),0,0));
        books.add(new Book(4,getResources().getString(R.string.c4),0,0));
        books.add(new Book(5,getResources().getString(R.string.c5),0,0));
        books.add(new Book(6,getResources().getString(R.string.c6),0,0));
        books.add(new Book(7,getResources().getString(R.string.c7),0,0));
        books.add(new Book(8,getResources().getString(R.string.c8),0,0));
        books.add(new Book(9,getResources().getString(R.string.c9),0,0));
        books.add(new Book(10,getResources().getString(R.string.c10),0,0));
        books.add(new Book(11,getResources().getString(R.string.c11),0,0));
        books.add(new Book(12,getResources().getString(R.string.c12),0,0));
        books.add(new Book(13,getResources().getString(R.string.c13),0,0));
        books.add(new Book(14,getResources().getString(R.string.c14),0,0));
        books.add(new Book(15,getResources().getString(R.string.c15),0,0));
        books.add(new Book(16,getResources().getString(R.string.c16),0,0));
        books.add(new Book(17,getResources().getString(R.string.c17),0,0));
        books.add(new Book(18,getResources().getString(R.string.c18),0,0));
        books.add(new Book(19,getResources().getString(R.string.c19),0,0));
        books.add(new Book(20,getResources().getString(R.string.c20),0,0));
        books.add(new Book(21,getResources().getString(R.string.c21),0,0));
        books.add(new Book(22,getResources().getString(R.string.c22),0,0));

        DataMarcadores marcadores = new DataMarcadores(getApplicationContext());
        marcadores.insertarData(books);
    }

}

