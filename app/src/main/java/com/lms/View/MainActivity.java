package com.lms.View;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.lms.BuildConfig;
import com.lms.R;
import com.lms.databinding.ActivityMainBinding;
import com.lms.utility.AlertClass;
import com.lms.utility.FragmentTask;
import com.lms.utility.SharePrefs;
import com.lms.utility.ThemeClass;
import com.lms.utility.Utils;

import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity{
    ActivityMainBinding mainActivityBinding;
   public static MainActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.changeStatuscolor(MainActivity.this);

        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        activity=this;
        mainActivityBinding.navigation.setSelectedItemId(R.id.navigation_classroom);

        ThemeClass.changeNavigationButton(mainActivityBinding.navigation,this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_course:
                    FragmentTask.replaceFrgament(new CourselistFragment(), getSupportFragmentManager(), R.id.main_framelayout);
                    return true;
                case R.id.navigation_classroom:
                    Log.e("auth",SharePrefs.getUserdetail(MainActivity.this).getAuthtoken().toString());
                    FragmentTask.replaceFrgament(new ClassRoomFragment(), getSupportFragmentManager(), R.id.main_framelayout);

                    return true;
                case R.id.navigation_invite:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    return true;
                case R.id.navigation_account:
                    FragmentTask.replaceFrgament(new MoreFragment(), getSupportFragmentManager(), R.id.main_framelayout);

                    return true;
            }
            return false;
        }
    };




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
       else
        {
            Fragment current_frag=getCurrentFragment();
           if(current_frag instanceof ProfileFragment){
               getSupportFragmentManager().popBackStack();
               mainActivityBinding.navigation.setSelectedItemId(R.id.navigation_account);
            }

            else if(current_frag instanceof MoreFragment){
                MainActivity.this.finish();
            }
            else if(current_frag instanceof CourselistFragment){
                MainActivity.this.finish();
            }

           /* else if(current_frag instanceof Map_Fragment){
                   enableViews(false);
                   getSupportFragmentManager().popBackStack();

            }
            else if(current_frag instanceof helpFragment){
                MainActivity.this.finish();
            } else if(current_frag instanceof AboutAppFragment){
                MainActivity.this.finish();
            }*/
          else
        super.onBackPressed();
        }
    }

    public Fragment getCurrentFragment() {

        if(getSupportFragmentManager().getBackStackEntryCount()>0){
        int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();
        Fragment current_frag = getSupportFragmentManager().findFragmentByTag(tag);
        return current_frag;
        }
        else
            return null;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


}
