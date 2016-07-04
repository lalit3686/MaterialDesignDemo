package com.example.materialdesigndemo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomOnItemClickListener, View.OnClickListener{

	private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private static Context mContext;
    private FloatingActionButton fab, fab1;
    private BottomSheetBehavior mBottomSheetBehavior;
    private View bottom_sheet_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setStatusBarTransparent();

        setContentView(R.layout.activity_main);

        mContext = this;

        initComponents();
        addListeners();
	}

    private void setStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
    }

    private void initComponents(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1= (FloatingActionButton) findViewById(R.id.fab1);

        bottom_sheet_layout = findViewById(R.id.bottom_sheet_layout);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_layout);
        mBottomSheetBehavior.setPeekHeight(100);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void addListeners(){
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getData(), this);
        mRecyclerView.setAdapter(myRecyclerViewAdapter);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
    }

    private List<String> getData(){
        List<String> mStringList = new ArrayList<String>();
        for(int item = 0; item <= 50; item++){
            mStringList.add("Item - "+item);
        }
        return mStringList;
    }

    public void MyOnClick(View view) {
		switch (view.getId()) {
		case R.id.buttonChangeTheme:
            mRecyclerView.scrollToPosition(20);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
                getWindow().setNavigationBarColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
            }
            else{
                Snackbar.make(view, "Theme not supported in this version", Snackbar.LENGTH_LONG).show();
            }
			break;
		case R.id.buttonAddItem:
            mRecyclerView.smoothScrollToPosition(1);
            myRecyclerViewAdapter.addNewItem("Item Newly Added");
			break;
        case R.id.buttonAddItemAtPosition:
            myRecyclerViewAdapter.addNewItemAtPosition("Item Newly Added ", 10);
            break;
        case R.id.buttonDeleteItem:
            myRecyclerViewAdapter.deleteByItem("Item Newly Added");
            break;
        case R.id.buttonDeleteItemAtPosition:
            myRecyclerViewAdapter.deleteItemAtPosition(10);
            break;
		}
	}

    @Override
    public void onItemClick(View v, int position, Object value) {
        showToast("Item Clicked at position - "+position+" and value - "+value);
        startActivity(new Intent(v.getContext(), NavigationDrawerActivity.class));
    }

    public static void showToast(String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                showSnackBar(v, "This is snack bar on AppBarLayout");
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.fab1:
                showSnackBar(v, "This is snackbar on RecyclerView");
                startActivity(new Intent(v.getContext(), TabLayoutActivity.class));
                break;
        }
    }

    private void showSnackBar(View v, String message){
        Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item_1:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
