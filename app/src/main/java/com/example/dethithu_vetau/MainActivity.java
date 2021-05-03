package com.example.dethithu_vetau;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button Add;
    ListView lstDanhSach;
    ArrayList<VeTau> lstVeTau = new ArrayList<>();
    static public int MARK_REQUEST=100;
    int idVeTau = 0;
    MyDBHelper dbHelper;
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.closeDB();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KhaiBao();
        dbHelper = new MyDBHelper(MainActivity.this);
        dbHelper.openDB();
//        Add();
        setList();
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivityForResult(intent,MARK_REQUEST);
                setList();

            }
        });
        lstDanhSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//               dbHelper.Delete((int)id);

                idVeTau=(int)id;
                registerForContextMenu(lstDanhSach);

                return false;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Tùy chọn");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Update");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Delete") {
            // do your coding


        }
        if(item.getTitle()=="Update"){
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            VeTau vetau = new VeTau();
            for(int i=0;i<lstVeTau.size();i++){
                if(lstVeTau.get(i).getId()==idVeTau){
                    vetau = lstVeTau.get(i);
                    break;
                }
            }
            intent.putExtra("ID",idVeTau);
            intent.putExtra("GADI",vetau.getGadi());
            intent.putExtra("GADEN",vetau.getGaden());
            intent.putExtra("DONGIA",vetau.getDongia());
            intent.putExtra("KHUHOI",vetau.isKhuhoi());
            startActivityForResult(intent,MARK_REQUEST);

        }
        else {
            return false;
        }
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==MARK_REQUEST) && (resultCode==RESULT_OK)){
            dbHelper.openDB();
            setList();
        }
    }
    void KhaiBao(){
        Add = findViewById(R.id.buttonAdd);
        lstDanhSach = findViewById(R.id.lstDanhSach);
    }
    void setList(){
        lstVeTau = dbHelper.ListAll();
        Cursor cursor = dbHelper.GetAllRecord();
        VeTauAdapter adapter = new VeTauAdapter(MainActivity.this,R.layout.activity__ve_tau,cursor,0);
        lstDanhSach.setAdapter(adapter);
    }
    void Add(){
        dbHelper.Add("HaNoi","HaiPhong",20000,1);
        dbHelper.Add("HaNoi","HCM",100000,0);
        dbHelper.Add("HaNoi","HCM",100000,1);
    }

}