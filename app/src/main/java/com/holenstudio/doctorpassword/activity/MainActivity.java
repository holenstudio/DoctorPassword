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

import com.holenstudio.doctorpassword.R;
import com.holenstudio.doctorpassword.adapter.PasswordRecyclerAdapter;
import com.holenstudio.doctorpassword.helper.MyItemTouchCallback;
import com.holenstudio.doctorpassword.helper.OnRecyclerItemClickListener;
import com.holenstudio.doctorpassword.model.PasswordInfo;
import com.holenstudio.doctorpassword.util.PasswordUtil;
import com.holenstudio.doctorpassword.util.VibratorUtil;
import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import me.drakeet.materialdialog.MaterialDialog;

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
    private List<PasswordInfo> pswList;
    private PasswordRecyclerAdapter mPswAdapter;
    private String mPasswordKey;
    private Realm realm;
    private RealmResults<PasswordInfo> mResults;
    private int selectCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mPasswordKey = ((App)getApplication()).getInfo().getKey();
        mPasswordKey = "abcdef";
        realm = Realm.getDefaultInstance();
//        initRealmData();
        initView();
        initData();
    }

    private void initRealmData() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String[] sites = {"www.baidu.com", "www.google.com"
                        , "www.arcsoft.com", "www.qq.com", "www.sohu.com"
                        , "www.xiaomi.com", "www.apple.com"
                        , "www.abc.com", "www.sina.com.cn"
                        , "www.yahoo.com.cn", "www.qweasd.com"};
                String[] usernames = {"holen", "asd", "woshishui", "zxcasd", "qaz", "a123456", "yiyiyi", "ssss", "zhegema", "wodenicheng", "hahaha"};
                String[] psws = {"aaa", "abc", "123456", "abc123", "qwertyasdfgh", "qazwsxedc", "sdfsdf", "aaa", "123456789", "mima", "ai123asd"};
                for (int i = 0; i < 20; i++) {
                    PasswordInfo info = realm.createObject(PasswordInfo.class);
                    info.setId(String.valueOf(System.currentTimeMillis()) + "" + ((int) Math.random() * 100));
                    info.setSite(sites[(int)(Math.random() * 10)]);
                    info.setUsername(usernames[(int)(Math.random() * 10)]);
                    info.setPassword(psws[(int)(Math.random() * 10)]);
                    info.setTitle(info.getSite().substring(4));
                    info.setLevel(1);
                    info.setDeletable(0);
                    info.setNote("This is an account for " + info.getTitle());
                }
            }
        });
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
                startActivityForResult(new Intent(MainActivity.this, PasswordDetailActivity.class), REQUEST_RESULT_UPDATE_OR_CREATE_PSW_INFO);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        pswList = new ArrayList<>();
        queryData("");
        mPswAdapter = new PasswordRecyclerAdapter(pswList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mPswRecyclerView.setLayoutManager(mLayoutManager);
        mPswRecyclerView.setHasFixedSize(true);
        mPswRecyclerView.setNestedScrollingEnabled(true);
        mPswRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPswRecyclerView.setAdapter(mPswAdapter);
        mPswRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(mPswAdapter));
        itemTouchHelper.attachToRecyclerView(mPswRecyclerView);
        final int size = pswList.size();
        mPswRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mPswRecyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (selectCount == 0) {
                    selectCount++;
                    mToolbar.getMenu().findItem(R.id.action_search).setIcon(R.drawable.ic_menu_delete);
                    ((PasswordRecyclerAdapter.PasswordViewHoler) vh).setSelectable(true);
                    pswList.get(vh.getAdapterPosition()).setDeletable(1);
                    VibratorUtil.Vibrate(MainActivity.this, 70);   //震动70ms
                }
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                if (selectCount != 0) {
                    if (((PasswordRecyclerAdapter.PasswordViewHoler) vh).getSelectable()) {
                        selectCount--;
                        pswList.get(vh.getAdapterPosition()).setDeletable(0);
                        if (selectCount == 0) {
                            mToolbar.getMenu().findItem(R.id.action_search).setIcon(R.drawable.ic_menu_search);
                        }
                        ((PasswordRecyclerAdapter.PasswordViewHoler) vh).setSelectable(false);
                    } else {
                        selectCount++;
                        pswList.get(vh.getAdapterPosition()).setDeletable(1);
                        ((PasswordRecyclerAdapter.PasswordViewHoler) vh).setSelectable(true);
                    }
                } else {
                    mToPswDetailIntent.putExtra("id", pswList.get(vh.getLayoutPosition()).getId());
                    mToPswDetailIntent.putExtra("title", pswList.get(vh.getLayoutPosition()).getTitle());
                    mToPswDetailIntent.putExtra("username", pswList.get(vh.getLayoutPosition()).getUsername());
                    mToPswDetailIntent.putExtra("password", pswList.get(vh.getLayoutPosition()).getPassword());
                    mToPswDetailIntent.putExtra("site", pswList.get(vh.getLayoutPosition()).getSite());
                    mToPswDetailIntent.putExtra("note", pswList.get(vh.getLayoutPosition()).getNote());
                    mToPswDetailIntent.putExtra("level", pswList.get(vh.getLayoutPosition()).getLevel());
                    startActivityForResult(mToPswDetailIntent, REQUEST_RESULT_UPDATE_OR_CREATE_PSW_INFO);
                }
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
//        mSearchView.setDivider(false);
//        mSearchView.setVoice(false);
//        mSearchView.setVoiceText("Set permission on Android 6+ !");
        mSearchView.setAnimationDuration(SearchView.ANIMATION_DURATION);
        mSearchView.setShadowColor(ContextCompat.getColor(this, R.color.search_shadow_layout));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.close(true);
                queryData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                queryData(newText);
                return true;
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
    }

    private void queryData(String query) {
        if (query.isEmpty()) {
            mResults = realm.where(PasswordInfo.class).findAllAsync();
        } else {
            mResults = realm.where(PasswordInfo.class).contains("mSite", query).or().contains("mTitle", query).findAllAsync();
        }
        mResults.addChangeListener(new RealmChangeListener<RealmResults<PasswordInfo>>() {
            @Override
            public void onChange(RealmResults<PasswordInfo> element) {
                pswList.clear();
                for (PasswordInfo info : element) {
                    PasswordInfo temp = new PasswordInfo();
                    temp.setLevel(info.getLevel());
                    temp.setId(info.getId());
                    temp.setNote(info.getNote());
                    temp.setTitle(info.getTitle());
                    temp.setUsername(info.getUsername());
                    temp.setSite(info.getSite());
                    temp.setPassword(PasswordUtil.getEncryptString(info.getPassword(), mPasswordKey));
                    pswList.add(temp);
                }
                if (mPswAdapter != null) {
                    mPswAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (selectCount != 0) {
            selectCount = 0;
            int index = mPswRecyclerView.getChildCount();
            for (int i = 0; i < index; i++) {
                PasswordRecyclerAdapter.PasswordViewHoler holder = (PasswordRecyclerAdapter.PasswordViewHoler) mPswRecyclerView.getChildViewHolder(mPswRecyclerView.getChildAt(i));
                if (holder.getSelectable()) {
                    holder.setSelectable(false);
                }
            }
            mToolbar.getMenu().findItem(R.id.action_search).setIcon(R.drawable.ic_menu_search);
        } else if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mSearchView != null && mSearchView.isSearchOpen()) {
            mSearchView.close(true);
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
            if (selectCount == 0) {
                mSearchView.open(true);
            } else {
                final MaterialDialog dialog = new MaterialDialog(this);
                dialog.setTitle("提示")
                        .setMessage("确定要删除吗？")
                        .setPositiveButton("是", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                deleteSelectedPassword();
                            }
                        })
                        .setNegativeButton("否", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        }).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteSelectedPassword() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int size = pswList.size();
                for (int i = size; i > 0; i--) {
                    if (pswList.get(i - 1).getDeletable() > 0) {
                        mResults.deleteFromRealm(i - 1);
                        pswList.remove(i - 1);
                        mPswAdapter.notifyItemRemoved(i - 1);
                    }
                }
//                for (int i = 0; i < index; i++) {
//                    PasswordRecyclerAdapter.PasswordViewHoler holder = (PasswordRecyclerAdapter.PasswordViewHoler) mPswRecyclerView.getChildViewHolder(mPswRecyclerView.getChildAt(i));
//                    if (holder.getSelectable()) {
//                        holder.setSelectable(false);
//                        mResults.deleteFromRealm(i);
//                        pswList.remove(i);
//                        mPswAdapter.notifyDataSetChanged();
//                    }
//                }

            }
        });
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

        final Intent dataIntent = data;
        if (resultCode == RESULT_OK && requestCode == REQUEST_RESULT_UPDATE_OR_CREATE_PSW_INFO) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    PasswordInfo info = realm.where(PasswordInfo.class).equalTo("mId", dataIntent.getStringExtra("id")).findFirst();
                    if (null == info) {
                        info = realm.createObject(PasswordInfo.class);
                        info.setId(dataIntent.getStringExtra("id"));
                    }
                    info.setTitle(dataIntent.getStringExtra("title"));
                    info.setUsername(dataIntent.getStringExtra("username"));
                    info.setPassword(dataIntent.getStringExtra("password"));
                    info.setSite(dataIntent.getStringExtra("site"));
                    info.setNote(dataIntent.getStringExtra("note"));
                    info.setLevel(dataIntent.getIntExtra("level", 0));
                }
            });
            mPswAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }

}
