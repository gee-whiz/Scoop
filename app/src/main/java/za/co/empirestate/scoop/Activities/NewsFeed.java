package za.co.empirestate.scoop.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import io.codetail.animation.SupportAnimator;
import io.codetail.widget.RevealFrameLayout;
import za.co.empirestate.scoop.Fragments.ExploreFragment;
import za.co.empirestate.scoop.Fragments.ReadLater;
import za.co.empirestate.scoop.Fragments.ReadNow;
import za.co.empirestate.scoop.R;
import za.co.empirestate.scoop.interfaces.ScreenShotable;

public class NewsFeed extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private MenuItem mSearchAction;
    private ReadNow readNow;
    private ExploreFragment exploreFragment;
    private SupportAnimator mAnimator;
    private static NewsFeed newsFeed;
    private ReadLater readLater;
    public static NewsFeed getInstance() {
        return newsFeed;

    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        readNow = new ReadNow();
        readLater = new ReadLater();
        exploreFragment = new ExploreFragment();

     newsFeed = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
      toolbar.setTitle("Scoop");
        toolbar.getMenu();
       toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_black_24dp));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar.inflateMenu(R.menu.news_feed);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, readNow)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, readNow)
                    .commit();


        } else if (id == R.id.nav_gallery) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, exploreFragment)
                    .commit();
        } else if (id == R.id.nav_slideshow) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_content, readLater)
                    .commit();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
