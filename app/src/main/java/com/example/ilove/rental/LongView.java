package com.example.ilove.rental;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class LongView extends AppCompatActivity implements MyAdapter.OnRecordListener, SearchDialog.SearchDialogListener {

    /*
    Test - is where i do the practice
    Rental - is actual data is stored
     */
    private String version;
    private Query searchHistory;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReference;
    private ArrayList<Record> records;
    private MyAdapter myAdapter;
    private FloatingActionButton searchDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        version = ((MyGlobals)this.getApplication()).getMode();
        searchHistory = ((MyGlobals) this.getApplication()).getSearchHistory();
        records = new ArrayList<Record>();
        //regarding view
        recyclerView = (RecyclerView)findViewById(R.id.myRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        searchDialog = (FloatingActionButton) findViewById(R.id.floatingActionButtonSearch);
        searchDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        //regarding firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child(version);//base query

        //if already has search history in session keep looking at it
        if(searchHistory!=null){searchMyDatabase(searchHistory);}




        /*
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                records.clear();
                for ( DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Record r = dataSnapshot1.getValue(Record.class);
                    records.add(r);
                }
                myAdapter = new MyAdapter(LongView.this,records,LongView.this);
                recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LongView.this,"Error database unavailable",Toast.LENGTH_SHORT).show();
            }
        });   */

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction == ItemTouchHelper.LEFT) {
                    Record toBeDeleted = records.get(viewHolder.getAdapterPosition());
                    deleteRecord(toBeDeleted);
                }
                else if(direction == ItemTouchHelper.RIGHT){
                    Record toBeUpdated = records.get(viewHolder.getAdapterPosition());
                    updateRecord(toBeUpdated);
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c,@NonNull RecyclerView recyclerView,@NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    View itemView = viewHolder.itemView;
                    Paint p = new Paint();
                    Bitmap icon;
                    if (dX > 0 ){ //Edit Swipe right

                        p.setARGB(255,0,255,0);
                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom(), p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.edit_pencil);
                        c.drawBitmap(icon,
                                (float) itemView.getLeft() + convertDpToPx(30),
                                (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - icon.getHeight())/2,
                                p);
                    }
                    else{//Delete Swipe left
                        p.setARGB(255,255,0,0);
                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom(), p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.trash_can);
                        c.drawBitmap(icon,
                                (float) itemView.getRight() - convertDpToPx(30) - icon.getWidth(),
                                (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - icon.getHeight())/2,
                                p);
                    }
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public void onRecordClick(int pos) {//clicking on an item
        Record r = records.get(pos);
        Intent open = new Intent(getApplicationContext(),ViewDetails.class);
        open.putExtra("record",r);
        startActivity(open);
    }


    private int convertDpToPx(int dp){
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    public void deleteRecord(Record r){
        FirebaseDatabase.getInstance().getReference(version).child(r.generatePath()).child(r.getId()).removeValue();

    }

    public void updateRecord(Record r){
        Intent open = new Intent(getApplicationContext(),EditView.class);
        open.putExtra("record", r);
        startActivity(open);
    }

    public void openDialog(){
        SearchDialog searchDialog = new SearchDialog();
        searchDialog.show(getSupportFragmentManager(),"Search");
    }

    public void searchMyDatabase(Query query){
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                records.clear();
                for ( DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Record r = dataSnapshot1.getValue(Record.class);
                    records.add(r);
                }

                myAdapter = new MyAdapter(LongView.this,records,LongView.this);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public String generatePath (String y,String m,String h){//generates a precise path to store the records
        return y+"/"+m+"/"+h;
    }



    @Override
    public void searchFor(String houseNumber, String month, String year) {
        Query searchQuery = null;
        if (houseNumber.equals("reset") && month.equals("reset") && year.equals("reset")) {

        } else {
            searchQuery = databaseReference.child(generatePath(year,month,houseNumber)).orderByChild("roomNumber");
            ((MyGlobals)this.getApplication()).setSearchHistory(searchQuery);
            searchMyDatabase(searchQuery);
        }
    }





}
