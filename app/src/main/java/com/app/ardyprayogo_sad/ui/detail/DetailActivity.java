package com.app.ardyprayogo_sad.ui.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ardyprayogo_sad.R;
import com.app.ardyprayogo_sad.model.Data;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private TextView tvName, tvLahir, tvJkel, tvEmail, tvAlamat, tvLokasi;
    private ImageView ivPic;
    private Data mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mData = (Data) getIntent().getSerializableExtra("data");
        initUI();
        initContact();
    }

    private void initUI() {
        tvName = findViewById(R.id.tv_name);
        tvLahir = findViewById(R.id.tv_lahir);
        tvJkel = findViewById(R.id.tv_jkel);
        tvEmail = findViewById(R.id.tv_email);
        tvAlamat = findViewById(R.id.tv_alamat);
        tvLokasi = findViewById(R.id.tv_lokasi);
        ivPic = findViewById(R.id.iv_pic);
    }

    private void initContact() {
        String alamat = mData.getLocation().getStreet().getName()
                + " "
                + mData.getLocation().getStreet().getNumber()
                + " "
                + mData.getLocation().getCity()
                + " "
                + mData.getLocation().getCountry();

        String lokasi = mData.getLocation().getCoordinates().getLatitude()
                + " "
                + mData.getLocation().getCoordinates().getLongitude();

        tvName.setText(mData.getName().getFirst() + " " + mData.getName().getLast());
        tvLahir.setText(mData.getDob().getDate());
        tvJkel.setText(mData.getGender());
        tvEmail.setText(mData.getEmail());
        tvAlamat.setText(alamat);
        tvLokasi.setText(lokasi);
        Glide.with(this)
                .load(mData.getPicture().getLarge())
                .into(ivPic);
    }
}