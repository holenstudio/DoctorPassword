package com.holenstudio.doctorpassword.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.holenstudio.doctorpassword.R;
import com.holenstudio.doctorpassword.adapter.PasswordRecyclerAdapter;
import com.holenstudio.doctorpassword.helper.MyItemTouchCallback;
import com.holenstudio.doctorpassword.helper.OnRecyclerItemClickListener;
import com.holenstudio.doctorpassword.model.PasswordInfo;
import com.holenstudio.doctorpassword.util.VibratorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.psw_recycler_view)
    RecyclerView pswRecyclerView;
    TextView textView;

    private PasswordRecyclerAdapter mPswAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        final List<PasswordInfo> pswList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pswList.add(new PasswordInfo("site:" + i, "title:" + i, "username:" + i, "password:" + i, "note:" + i));
        }
        mPswAdapter = new PasswordRecyclerAdapter(pswList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        pswRecyclerView.setLayoutManager(mLayoutManager);
        pswRecyclerView.setHasFixedSize(true);
        pswRecyclerView.setAdapter(mPswAdapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(mPswAdapter));
        itemTouchHelper.attachToRecyclerView(pswRecyclerView);
        final int size = pswList.size();
        pswRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(pswRecyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() != size) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(MainActivity.this, 70);   //震动70ms
                }
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Snackbar.make(pswRecyclerView, "click:" + pswList.get(vh.getLayoutPosition()), Snackbar.LENGTH_SHORT).show();
            }
        });
        textView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView);
        textView.setText("test");
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
