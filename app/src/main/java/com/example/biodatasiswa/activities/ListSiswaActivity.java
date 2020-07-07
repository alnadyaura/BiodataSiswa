package com.example.biodatasiswa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biodatasiswa.R;
import com.example.biodatasiswa.adapter.SiswaAdapter;
import com.example.biodatasiswa.database.Constant;
import com.example.biodatasiswa.database.SiswaDatabase;
import com.example.biodatasiswa.model.SiswaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListSiswaActivity extends AppCompatActivity {

    @BindView(R.id.listSiswa)
    RecyclerView lstSiswa;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    SiswaDatabase siswaDatabase;
    List<SiswaModel> siswaModelList;
    int id_kelas;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_siswa);

        ButterKnife.bind(this);

        Toolbar tbDetailDokter = findViewById(R.id.toolbar);
        tbDetailDokter.setTitle("Daftar Siswa");
        setSupportActionBar(tbDetailDokter);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            id_kelas = bundle.getInt(Constant.KEY_ID_KELAS);
        }

        siswaDatabase = SiswaDatabase.createDatabase(this);

        siswaModelList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        siswaModelList.clear();

        getData();

        lstSiswa.setLayoutManager(new LinearLayoutManager(this));
        lstSiswa.setAdapter(new SiswaAdapter(this, siswaModelList));
    }

    private void getData() {
        siswaModelList = siswaDatabase.kelasDao().selectSiswa(id_kelas);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivity(new Intent(this, TambahSiswaActivity.class).putExtra(Constant.KEY_ID_KELAS, id_kelas));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
