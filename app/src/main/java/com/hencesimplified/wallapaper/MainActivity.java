package com.hencesimplified.wallapaper;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;
    FloatingActionButton info;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            =  new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment=null;

            switch (item.getItemId()) {
                case R.id.weekly:
                    fragment=new weekly_fg();
                    break;
                case R.id.unlocked:
                    fragment=new unlocked_fg();
                    break;
                case R.id.locked:
                    fragment=new locked_fg();
                    break;
                case R.id.wildlife:
                    fragment=new photographer1_fg();
                    break;
                case R.id.scenes:
                    fragment=new Photographer2_fg();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        info=findViewById(R.id.floatingActionButton);

        SharedPreferences preferences=getApplicationContext().getSharedPreferences("MyPref",0);
        int page=preferences.getInt("Page",-1);

        if(page==1) {
            loadFragment(new weekly_fg());
            navView.setSelectedItemId(R.id.weekly);
        }
        else if(page==2)
        {
            loadFragment(new unlocked_fg());
            navView.setSelectedItemId(R.id.unlocked);
        }
        else if(page==3)
        {
            loadFragment(new photographer1_fg());
            navView.setSelectedItemId(R.id.wildlife);
        }
        else if(page==4)
        {
            loadFragment(new Photographer2_fg());
            navView.setSelectedItemId(R.id.scenes);
        }
        else if(page==5)
        {
            loadFragment(new locked_fg());
            navView.setSelectedItemId(R.id.locked);
        }
        else
        {
            loadFragment(new weekly_fg());
            navView.setSelectedItemId(R.id.weekly);
        }

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent page_intent=new Intent(MainActivity.this,information.class);
                startActivity(page_intent);
            }
        });
    }

    public boolean checkPermission(String permission)
    {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        int check= ContextCompat.checkSelfPermission(this,permission);
        return (check== PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }
    private boolean loadFragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fg_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed()
    {
        final AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage("Are you sure you want to exit?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                Intent ExitIntent=new Intent(Intent.ACTION_MAIN);
                ExitIntent.addCategory(Intent.CATEGORY_HOME);
                ExitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ExitIntent);
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.opt_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int opt_id=item.getItemId();

        switch (opt_id)
        {
            case R.id.opt_remove:
                AlertDialog alert_dia1=new AlertDialog.Builder(MainActivity.this).create();
                alert_dia1.setTitle("Remove Ads!");
                alert_dia1.setMessage("Download the Wall Thing Pro to enjoy Ad-free service!! Download Now?");

                alert_dia1.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.hencesimplified.wallpaperpro")));
                    }
                });
                alert_dia1.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert_dia1.show();

                return true;

            case R.id.opt_rate:
                Intent playStore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.hencesimplified.wallapaper"));
                startActivity(playStore);
                return true;

            case R.id.opt_more:
                try {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/dev?id=7031227816779180923")));
                }
                catch (android.content.ActivityNotFoundException e) {
                    //replace &quot;Unified+Apps&quot; with your developer name
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/search?q=pub:Hence Simplified")));
                }
                return true;

            case R.id.opt_about:
                finish();
                Intent page_intent=new Intent(MainActivity.this,about.class);
                startActivity(page_intent);
                return true;

            case R.id.opt_policy:
                Intent privacyPolicy = new Intent(Intent.ACTION_VIEW, Uri.parse("https://hencesimplified.wixsite.com/website/privacy-policy-wallthing"));
                startActivity(privacyPolicy);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
