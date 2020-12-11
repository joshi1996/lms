package com.freepadhe.View;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.IntentSender;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.freepadhe.R;
import com.freepadhe.databinding.ActivityMainBinding;
import com.freepadhe.utility.FragmentTask;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;

public class MainActivity extends AppCompatActivity{
    ActivityMainBinding mainActivityBinding;
   public static MainActivity activity;
    private int REQ_CODE_VERSION_UPDATE = 11;
    private AppUpdateManager appUpdateManager;

    ReviewManager manager;
    ReviewInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.changeStatuscolor(MainActivity.this);

        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        activity=this;
        mainActivityBinding.navigation.setSelectedItemId(R.id.navigation_classroom);

        checkForAppUpdate();

        ThemeClass.changeNavigationButton(mainActivityBinding.navigation,this);

        manager = ReviewManagerFactory.create(MainActivity.this);
        Task<ReviewInfo> reviewInfoTask = manager.requestReviewFlow();

        reviewInfoTask.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            @Override
            public void onComplete(Task<ReviewInfo> task) {
                if (task.isSuccessful())
                {
                    info = task.getResult();
                    Task<Void> flow = manager.launchReviewFlow(MainActivity.this,info);

                    flow.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void result) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                    FragmentTask.replaceFrgament(new InviteFragment(), getSupportFragmentManager(), R.id.main_framelayout);
                    return true;

                case R.id.navigation_ob:
                    FragmentTask.replaceFrgament(new JobFragment(),getSupportFragmentManager(),R.id.main_framelayout);
                    return true;

                case R.id.navigation_account:
                    FragmentTask.replaceFrgament(new MoreFragment(), getSupportFragmentManager(), R.id.main_framelayout);
                    return true;
            }
            return false;
        }
    };

    private void checkForAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    // Request the update.
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                        try {
                            appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,MainActivity.this,REQ_CODE_VERSION_UPDATE);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode==REQ_CODE_VERSION_UPDATE)
        {
            Toast.makeText(this, "Start Update", Toast.LENGTH_SHORT).show();

            if (resultCode!=RESULT_OK)
            {
                Toast.makeText(this, ""+resultCode, Toast.LENGTH_SHORT).show();
            }
        }
    }


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
