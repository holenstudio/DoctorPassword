package com.holenstudio.doctorpassword.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.holenstudio.doctorpassword.R;
import com.holenstudio.doctorpassword.model.PasswordInfo;

import me.drakeet.materialdialog.MaterialDialog;

public class PasswordDetailActivity extends AppCompatActivity {

    private PasswordInfo mPswInfo;
    private boolean isNewPswInfo;

    private Toolbar mToolbar;
    private TextView mTitleView;
    private TextView mUsernameView;
    private TextView mPasswordView;
    private TextView mSiteView;
    private TextView mNoteView;
    private TextView mLevelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_detail);
        initView();
        setSupportActionBar(mToolbar);
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleView = (TextView) findViewById(R.id.title);
        mUsernameView = (TextView) findViewById(R.id.username);
        mPasswordView = (TextView) findViewById(R.id.password);
        mSiteView = (TextView) findViewById(R.id.site);
        mNoteView = (TextView) findViewById(R.id.note);
        mLevelView = (TextView) findViewById(R.id.level);
    }

    private void initData() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_return);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPswInfo = (PasswordInfo) getIntent().getSerializableExtra("password_info");
        if (mPswInfo == null) {
            mToolbar.setTitle("创建密码");
            mPswInfo = new PasswordInfo();
            mTitleView.setVisibility(View.VISIBLE);
            isNewPswInfo = true;
        } else {
            isNewPswInfo = false;
            mTitleView.setVisibility(View.GONE);
            mToolbar.setTitle(mPswInfo.getTitle());
        }
        mTitleView.setText(mPswInfo.getTitle());
        mUsernameView.setText(mPswInfo.getUsername());
        mPasswordView.setText(mPswInfo.getPassword());
        mSiteView.setText(mPswInfo.getSite());
        mNoteView.setText(mPswInfo.getNote());
        mLevelView.setText(String.valueOf(mPswInfo.getLevel()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_password_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_submit) {
            updatePswInfo();
            if (isNewPswInfo) {
                submit();
            } else {
                final MaterialDialog dialog = new MaterialDialog(this);
                dialog.setTitle("提示")
                        .setMessage("确定要修改吗？")
                        .setPositiveButton("是", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                submit();
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

    private void updatePswInfo() {
        if (mPswInfo.getId() == null || mPswInfo.getId().isEmpty()) {
            mPswInfo.setId(String.valueOf(System.currentTimeMillis()));
        }
        mPswInfo.setTitle(mTitleView.getText().toString());
        mPswInfo.setPassword(mPasswordView.getText().toString());
        mPswInfo.setUsername(mUsernameView.getText().toString());
        mPswInfo.setNote(mNoteView.getText().toString());
        mPswInfo.setSite(mSiteView.getText().toString());
        if (mLevelView.getText() != null) {
            mPswInfo.setLevel(Integer.parseInt(mLevelView.getText().toString()));
        }
    }

    private void submit() {
        Intent intent=new Intent();
        intent.putExtra("password_info", mPswInfo);
        setResult(RESULT_OK, intent);
        finish();
    }
}
