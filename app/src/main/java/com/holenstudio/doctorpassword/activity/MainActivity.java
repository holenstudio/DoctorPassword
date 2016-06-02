package com.holenstudio.doctorpassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.holenstudio.doctorpassword.App;
import com.holenstudio.doctorpassword.R;
import com.holenstudio.doctorpassword.adapter.PasswordRecyclerAdapter;
import com.holenstudio.doctorpassword.helper.MyItemTouchCallback;
import com.holenstudio.doctorpassword.helper.OnRecyclerItemClickListener;
import com.holenstudio.doctorpassword.model.PasswordInfo;
import com.holenstudio.doctorpassword.util.DocUtil;
import com.holenstudio.doctorpassword.util.PasswordUtil;
import com.holenstudio.doctorpassword.util.VibratorUtil;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    public static final int REQUEST_RESULT_UPDATE_OR_CREATE_PSW_INFO = 100;

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mPswRecyclerView;
    private SearchView mSearchView;
    private TextView textView;

    private Intent mToPswDetailIntent;
    List<PasswordInfo> pswList;
    private PasswordRecyclerAdapter mPswAdapter;
    private String mPasswordKey;
    private Realm realm;
    private RealmResults<PasswordInfo> mResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mPasswordKey = ((App)getApplication()).getInfo().getKey();
        mPasswordKey = "abcdef";
        realm = Realm.getDefaultInstance();
        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mPswRecyclerView = (RecyclerView) findViewById(R.id.psw_recycler_view);
        mSearchView = (SearchView) findViewById(R.id.search_view);
        setSupportActionBar(mToolbar);
    }

    private void initData() {
        mToolbar.setNavigationIcon(R.drawable.ic_menu_return);
        mToPswDetailIntent = new Intent(this, PasswordDetailActivity.class);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        pswList = new ArrayList<>();
        mResults = realm.where(PasswordInfo.class).findAllAsync();
        mResults.addChangeListener(new RealmChangeListener<RealmResults<PasswordInfo>>() {
            @Override
            public void onChange(RealmResults<PasswordInfo> element) {

            }
        });
        for (int i = 0; i < 10; i++) {
            pswList.add(new PasswordInfo("" + i, "site:" + i, "title:" + i, "username:" + i, "password:" + PasswordUtil.getEncryptString(String.valueOf("abc" + i), mPasswordKey), "note:" + i));
        }
//        DocUtil.writePswInfoListToDoc(pswList);
//        DocUtil.getPswInfoListFromDoc();
        mPswAdapter = new PasswordRecyclerAdapter(pswList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mPswRecyclerView.setLayoutManager(mLayoutManager);
        mPswRecyclerView.setHasFixedSize(true);
        mPswRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPswRecyclerView.setAdapter(mPswAdapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(mPswAdapter));
        itemTouchHelper.attachToRecyclerView(mPswRecyclerView);
        final int size = pswList.size();
        mPswRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mPswRecyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() != size) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(MainActivity.this, 70);   //震动70ms
                }
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mToPswDetailIntent.putExtra("password_info", pswList.get(vh.getLayoutPosition()));
                startActivityForResult(mToPswDetailIntent, REQUEST_RESULT_UPDATE_OR_CREATE_PSW_INFO);
            }
        });
        textView = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(toLoginIntent);
            }
        });

        initSearchView();
    }

    private void initSearchView() {
        mSearchView.setVersion(SearchView.VERSION_MENU_ITEM);
        mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_MENU_ITEM);
        mSearchView.setTheme(SearchView.THEME_LIGHT, true);
        mSearchView.setTextSize(16);
        mSearchView.setHint(R.string.search_hint);
        mSearchView.setDivider(true);
        mSearchView.setVoice(false);
        mSearchView.setVoiceText("Set permission on Android 6+ !");
        mSearchView.setAnimationDuration(SearchView.ANIMATION_DURATION);
        mSearchView.setShadowColor(ContextCompat.getColor(this, R.color.search_shadow_layout));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.close(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnOpenCloseListener(new SearchView.OnOpenCloseListener() {
            @Override
            public void onOpen() {
                if (mFab != null) {
                    mFab.hide();
                }
            }

            @Override
            public void onClose() {
                if (mFab != null) {
                    mFab.show();
                }
            }
        });
        List<SearchItem> suggestionsList = new ArrayList<>();
        for (PasswordInfo info : pswList) {
            suggestionsList.add(new SearchItem(info.getSite()));
        }
        SearchAdapter searchAdapter = new SearchAdapter(this, suggestionsList);
        mSearchView.setAdapter(searchAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        mSearchView.close(true);
        mSearchView.open(false);
        return true;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() { // new DrawerLayout.DrawerListener();
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                if (mSearchView != null && mSearchView.isSearchOpen()) {
                    mSearchView.close(true);
                }
                if (mFab != null) {
                    mFab.hide();
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                if (mFab != null) {
                    mFab.show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            mSearchView.open(true);
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

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_RESULT_UPDATE_OR_CREATE_PSW_INFO) {
            PasswordInfo info = (PasswordInfo) data.getSerializableExtra("password_info");
            if (info != null) {
                int size = pswList.size();
                int i = 0;
                for (; i < size; i++) {
                    PasswordInfo temp = pswList.get(i);
                    if (info.getId().equals(temp.getId())) {
                        pswList.set(i, info);
                    }
                }
                if (i == size) {
                    pswList.add(info);
                }
            }
            mPswAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }
}
