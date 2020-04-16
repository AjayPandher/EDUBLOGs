package com.ajndroid.edublog.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ajndroid.edublog.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_pdf_files extends AppCompatActivity {
    ListView myPDFlistview;
    DatabaseReference databaseReference;
    List<uploadPDF> uploadPDFS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   setContentView(R.layout.activity_view_pdf_files);
        myPDFlistview= findViewById(R.id.mylistview);
     uploadPDFS=new ArrayList<uploadPDF>();


viewALLfiles();
myPDFlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
uploadPDF uploadPDF =  uploadPDFS.get(position);


        Intent MyIntent = new Intent(Intent.ACTION_VIEW);

        MyIntent.setData(Uri.parse(uploadPDF.getUrl()));

        startActivity(MyIntent);
    }
});


    }

    private void viewALLfiles() {
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    uploadPDF uploadpdf = postSnapshot.getValue(uploadPDF.class);
                    uploadPDFS.add(uploadpdf);
                }
                String[] uploads = new String[uploadPDFS.size()];
                for (int i = 0; i < uploads.length; i++) {
                uploads[i] = uploadPDFS.get(i).getName();

            }
                ArrayAdapter<String> adapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,uploads);



        myPDFlistview.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
