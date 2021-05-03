package com.example.dethithu_vetau;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class VeTauAdapter extends ResourceCursorAdapter {
    public VeTauAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String loai="";
        float dongia = cursor.getFloat(cursor.getColumnIndex(MyDBHelper.getDONGIA()));
        TextView gadi = (TextView) view.findViewById(R.id.textViewGaDi);
        TextView gaden = (TextView) view.findViewById(R.id.textViewGaDen);
        TextView khuhoi = (TextView) view.findViewById(R.id.textViewKhuHoi);
        TextView gia = (TextView) view.findViewById(R.id.textViewGia);

        if(cursor.getInt(cursor.getColumnIndex(MyDBHelper.getKHUHOI()))==1){
           dongia = (float) (dongia * 2 * 0.95);
           loai = "Khứ Hồi";
        }
        else{
            loai="Một chiều";
        }


        gadi.setText(cursor.getString(cursor.getColumnIndex(MyDBHelper.getGADI())));
        gaden.setText(cursor.getString(cursor.getColumnIndex(MyDBHelper.getGADEN())));
        khuhoi.setText(loai);
        gia.setText(dongia+"");
    }

}
