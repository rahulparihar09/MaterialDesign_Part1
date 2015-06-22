package com.example.materialdesignone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.materialdesignone.fragment.FriendsFragment;
import com.example.materialdesignone.fragment.HomeFragment;
import com.example.materialdesignone.fragment.MessagesFragment;
import com.example.materialdesignone.fragment.NavigationDrawerFragment;

public class HomeActivity extends AppCompatActivity implements
        NavigationDrawerFragment.FragmentDrawerListener,
        FragmentManager.OnBackStackChangedListener {

    private static String TAG = HomeActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private NavigationDrawerFragment drawerFragment;
    Fragment fragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment.getFragmentManager().getBackStackEntryCount() > 0)
                    onBackPressed();
                else if (drawerFragment.isDrawerOpen())
                    drawerFragment.closeDrawer();
                else
                    drawerFragment.openDrawer();
                // drawerFragment.getActionBarDrawerToggle().syncState();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),
                    "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new FriendsFragment();
                title = getString(R.string.title_friends);
                break;
            case 2:
                fragment = new MessagesFragment();
                title = getString(R.string.title_messages);
                break;
            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.addOnBackStackChangedListener(this);
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.abc_slide_in_bottom,
                    R.anim.abc_slide_out_top);
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerFragment.isDrawerOpen()) {
            drawerFragment.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerFragment.mDrawerToggle.syncState();
    }

    @Override
    public void onBackStackChanged() {

        drawerFragment.mDrawerToggle.setDrawerIndicatorEnabled(fragmentManager
                .getBackStackEntryCount() == 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(
                fragmentManager.getBackStackEntryCount() > 0);
        drawerFragment.mDrawerToggle.syncState();
    }
}
