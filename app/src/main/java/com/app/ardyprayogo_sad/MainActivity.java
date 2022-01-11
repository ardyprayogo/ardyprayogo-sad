package com.app.ardyprayogo_sad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.app.ardyprayogo_sad.model.Data;
import com.app.ardyprayogo_sad.network.VolleyResponseListener;
import com.app.ardyprayogo_sad.network.VolleyUtil;
import com.app.ardyprayogo_sad.ui.detail.DetailActivity;
import com.app.ardyprayogo_sad.ui.main.MainAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Data> dataList;
    private MainAdapter mAdapter;
    private RecyclerView rvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
        initUI();
    }

    private void initUI() {
        mAdapter = new MainAdapter(this, new MainAdapter.Click() {
            @Override
            public void onItem(Data data) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });
        LinearLayoutManager lm = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                lm.getOrientation());
        rvMain = findViewById(R.id.rv_main);
        rvMain.setHasFixedSize(false);
        rvMain.setLayoutManager(lm);
        rvMain.setAdapter(mAdapter);
        rvMain.addItemDecoration(dividerItemDecoration);
    }

    private void getData() {
        VolleyUtil.sendGetRequest(this, new VolleyResponseListener() {
            @Override
            public void onError(String error) {

            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray object = response.getJSONArray("results");
                    Log.e("TAG", "onResponse: "+object.toString());
                    Gson gson = new Gson();
                    Type typeList = new TypeToken<ArrayList<Data>>(){}.getType();
                    dataList = gson.fromJson(object.toString(), typeList);
                    mAdapter.setList(dataList);
                    } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}