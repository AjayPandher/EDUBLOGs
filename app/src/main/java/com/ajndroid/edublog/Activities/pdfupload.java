package com.ajndroid.edublog.Activities;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ajndroid.edublog.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class pdfupload extends AppCompatActivity {
   /**EditText mdescription;
Button selectfile,uploadfile,fetch;
TextView notification;
ProgressDialog progressDialog;
Uri pdfUri;
FirebaseStorage mstorage;
FirebaseDatabase mdatabase;*/
   EditText editPDFname;
   Button btn_upload;
   StorageReference storageReference;
   DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfupload);

/*fetch=findViewById(R.id.fetchFiles);
fetch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
 startActivity(new Intent(pdfupload.this,myrecyclerview.class));
    }
});
 mstorage=FirebaseStorage.getInstance();
  mdatabase=FirebaseDatabase.getInstance();
selectfile=findViewById(R.id.selectfile);
uploadfile=findViewById(R.id.uploadfile);
notification=findViewById(R.id.notification);
    mdescription = findViewById(R.id.description);

selectfile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (ContextCompat.checkSelfPermission(pdfupload.this , Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            selectPDF();
        }
        else
        ActivityCompat.requestPermissions(pdfupload.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
    }
});

uploadfile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       // String value =  mdescription.getText().toString();

        if (pdfUri!=null)
        uploadFile(pdfUri);
        else
        Toast.makeText(pdfupload.this,"select file",Toast.LENGTH_SHORT).show();


    }
});
    }

    private void uploadFile(Uri pdfUri) {
        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("uploading file");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName=System.currentTimeMillis()+"";

        StorageReference storageReference= mstorage.getReference();
        storageReference.child("uploads").child(fileName).putFile(pdfUri).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   //     Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        // String url=taskSnapshot.getStorage().toString();
                        Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString(); //here u can get your url
                         String value=  mdescription.getText().toString();


                        DatabaseReference reference = mdatabase.getReference();
                        reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Toast.makeText(pdfupload.this,"file uploaded",Toast.LENGTH_SHORT).show();
                           else
                                Toast.makeText(pdfupload.this,"file not uploaded",Toast.LENGTH_SHORT).show();
                            }
                        });

                            }
                        });





                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(pdfupload.this,"file not uploaded",Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
int currentProgress= (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
progressDialog.setProgress(currentProgress);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
if (requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectPDF();
    }
    else
        Toast.makeText(pdfupload.this,"please provide permission",Toast.LENGTH_SHORT);
    }
    private void selectPDF() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            notification.setText("file is selected:"+data.getData().getLastPathSegment());
        } else {
            Toast.makeText(pdfupload.this, "please select file", Toast.LENGTH_SHORT).show();

        }
        */
editPDFname = findViewById(R.id.description);
btn_upload=findViewById(R.id.uploadfile);

storageReference= FirebaseStorage.getInstance().getReference();
databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
btn_upload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        selectPDFfile();

    }
});
    }

    private void selectPDFfile() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF file"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode == RESULT_OK
        && data !=null && data.getData()!=null){
            uploadPDFdile(data.getData());
        }
    }

    private void uploadPDFdile(Uri data) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("uploading...");
        StorageReference reference = storageReference.child("uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri>uri =taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete());
                Uri url = uri.getResult();
              uploadPDF uploadpdf= new uploadPDF(editPDFname.getText().toString(),url.toString());
              databaseReference.child(databaseReference.push().getKey()).setValue(uploadpdf);
                Toast.makeText(pdfupload.this,"file uploaded",Toast.LENGTH_SHORT).show();
         progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
double progress=(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
         progressDialog.setMessage("Uploaded:"+(int)progress+"%");
            }
        });

    }

    public void btn_action(View view) {
        startActivity(new Intent(getApplicationContext(),View_pdf_files.class));
    }
}
