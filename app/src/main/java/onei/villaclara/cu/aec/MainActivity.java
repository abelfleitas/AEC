package onei.villaclara.cu.aec;

/**
 * AEC
 * @author Abel Alejandro Fleitas Perdomo
 * @since  2019
 **/

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import java.util.ArrayList;
import cn.refactor.lib.colordialog.PromptDialog;
import onei.villaclara.cu.aec.basedatos.DataMarcadores;
import onei.villaclara.cu.aec.utils.ItemMenu;
import onei.villaclara.cu.aec.utils.RecyclerViewAdapterMenu;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DataMarcadores marcadores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        marcadores = new DataMarcadores(getApplicationContext());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        LayoutInflater inflater = LayoutInflater.from(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.addView(onCreateView(inflater,null,savedInstanceState));
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_principal);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapterMenu(getDataMenu(),this);
        mRecyclerView.setAdapter(mAdapter);

        if ((ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_BOOT_COMPLETED) != PackageManager.PERMISSION_GRANTED)
                ||(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INSTALL_SHORTCUT) != PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.WAKE_LOCK,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED,
                    Manifest.permission.INSTALL_SHORTCUT,}, 0);
        }

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.nav_header_principal, null, false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            PromptDialog dialog = new PromptDialog(this);
            dialog.setDialogType(PromptDialog.DIALOG_TYPE_HELP);
            dialog.setAnimationEnable(true);
            dialog.setTitleText((int) R.string.confirm);
            dialog.setContentText((int) R.string.confirm_exit);
            dialog.setCancelable(false);
            dialog.setPositiveListener(this.getString(R.string.aceptar), new PromptDialog.OnPositiveListener() {
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.setNegativeListener(this.getString(R.string.md_cancel_label), new PromptDialog.OnNegativeListener() {
                public void onClick(PromptDialog dialog) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_info) {
            startActivity(new Intent(MainActivity.this, InfoActivity.class));
            overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
            MainActivity.this.finish();
            return true;
        }
        else if (id == R.id.action_contact) {
            startActivity(new Intent(MainActivity.this, ContactActivity.class));
            overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
            MainActivity.this.finish();
            return true;
        }
        else if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
            MainActivity.this.finish();
            return true;
        }
        else if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            overridePendingTransition(R.anim.zoom_back_in,R.anim.zoom_back_out);
            MainActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((RecyclerViewAdapterMenu) mAdapter).setOnItemClickListener(new RecyclerViewAdapterMenu.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(position == 0)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c0));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/territorio.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(0));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 1)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c1));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/medio_ambiente.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(1));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 2)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c2));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/poblacion.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(2));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 3)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c3));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/organizacion_institucional.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(3));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 4)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c4));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/cuentas_nacionales.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(4));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 5)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c5));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/finanzas.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(5));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 6)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c6));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/empleo_y_salarios.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(6));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 7)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c7));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/sector_externo.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(7));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 8)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c8));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/agricultura.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(8));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 9)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c9));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/mineria_y_energia.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(9));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 10)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c10));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/industria_manufacturera.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(10));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 11)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c11));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/construccion_e_inversiones.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(11));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }

                else if(position == 12)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c12));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/transporte.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(12));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 13)
                {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c13));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/comercio_interno.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(13));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 14) {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c14));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/turismo.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(14));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 15) {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c15));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/ciencia_y_tecnologia.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(15));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 16) {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c16));
                    intent.putExtra("pagina",0);
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/tic.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(16));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 17) {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c17));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/educacion.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(17));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 18) {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c18));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/salud_publica.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(18));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 19) {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c19));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/cultura.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(19));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 20) {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c20));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/deporte.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(20));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else if(position == 21) {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c21));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/proceso_electoral.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(21));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }
                else {
                    Intent intent = new Intent(MainActivity.this,PDFViewActivity.class);
                    intent.putExtra("name",getResources().getString(R.string.c22));
                    intent.putExtra("desde","Anuario");
                    intent.putExtra("archivo","anuario_estadisticos/accidentes_del_transito.pdf");
                    intent.putExtra("pagina",marcadores.getPageById(22));
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    finish();
                }

            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public ArrayList<ItemMenu> getDataMenu(){
        ArrayList<ItemMenu> arrayList = new ArrayList<>();
        arrayList.add(0,new ItemMenu(R.drawable.territorio,getString(R.string.c0)));
        arrayList.add(1,new ItemMenu(R.drawable.ma,getString(R.string.c1)));
        arrayList.add(2,new ItemMenu(R.drawable.poblacion,getString(R.string.c2)));
        arrayList.add(3,new ItemMenu(R.drawable.org_inst,getString(R.string.c3)));
        arrayList.add(4,new ItemMenu(R.drawable.cuentas,getString(R.string.c4)));
        arrayList.add(5,new ItemMenu(R.drawable.finanzas,getString(R.string.c5)));
        arrayList.add(6,new ItemMenu(R.drawable.empleo,getString(R.string.c6)));
        arrayList.add(7,new ItemMenu(R.drawable.sector_externo,getString(R.string.c7)));
        arrayList.add(8,new ItemMenu(R.drawable.agricultura,getString(R.string.c8)));
        arrayList.add(9,new ItemMenu(R.drawable.mineria,getString(R.string.c9)));
        arrayList.add(10,new ItemMenu(R.drawable.industria,getString(R.string.c10)));
        arrayList.add(11,new ItemMenu(R.drawable.construccion,getString(R.string.c11)));
        arrayList.add(12,new ItemMenu(R.drawable.avion,getString(R.string.c12)));
        arrayList.add(13,new ItemMenu(R.drawable.ci,getString(R.string.c13)));
        arrayList.add(14,new ItemMenu(R.drawable.turismo,getString(R.string.c14)));
        arrayList.add(15,new ItemMenu(R.drawable.ciencia,getString(R.string.c15)));
        arrayList.add(16,new ItemMenu(R.drawable.tic,getString(R.string.c16)));
        arrayList.add(17,new ItemMenu(R.drawable.educacion,getString(R.string.c17)));
        arrayList.add(18,new ItemMenu(R.drawable.salud,getString(R.string.c18)));
        arrayList.add(19,new ItemMenu(R.drawable.cultura,getString(R.string.c19)));
        arrayList.add(20,new ItemMenu(R.drawable.deporte,getString(R.string.c20)));
        arrayList.add(21,new ItemMenu(R.drawable.elecciones,getString(R.string.c21)));
        arrayList.add(22,new ItemMenu(R.drawable.accidentes,getString(R.string.c22)));
        return arrayList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int [] grantResults){
        switch (requestCode){
            case 0:{
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                }else {
                    MainActivity.super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                }
            }
            break;
            default:
                MainActivity.super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

}
