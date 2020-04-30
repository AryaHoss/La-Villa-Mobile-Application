package edu.csulb.cecs.lavilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccountEdit extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private EditText editFname, editLname,editEmail;
    private Button access;
    private String updatedFname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_edit);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = firebaseDatabase.getReference("Users");
        editFname = findViewById(R.id.fname_edit);
        editLname = findViewById(R.id.lname_edit);
        editEmail = findViewById(R.id.email_edit_accountEt);
        access = findViewById(R.id.edit_accountbtn);

        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                editFname.setHint(user.getfname());
                editLname.setHint(user.getlname());
                editEmail.setHint(user.getEmail());

                access.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String eFname = editFname.getText().toString().trim();
                        String eLname = editLname.getText().toString().trim();
                        String eEmail = editEmail.getText().toString().trim();

                        if(!(eFname.isEmpty() || eFname.length() == 0 || eFname.equals("") || eFname == null)){
                            changefName(eFname);
                        }

                        if(!(eLname.isEmpty() || eLname.length() == 0 || eLname.equals("") || eLname == null)){
                            changelName(eLname);
                        }

                        if(!(eEmail.isEmpty() || eEmail.length() == 0 || eEmail.equals("") || eEmail == null)){
                            changeEmail(eEmail);
                        }
                        else{
                            Toast.makeText(UserAccountEdit.this,"Field Not Edited",Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void changefName(String newName){
        firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = firebaseDatabase.getReference("Users");
        ref.child(uid).child("fname").setValue(newName);

    }

    public void changelName(String newName){
        firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = firebaseDatabase.getReference("Users");
        ref.child(uid).child("lname").setValue(newName);

    }

    public void changeEmail(String newName){
        firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = firebaseDatabase.getReference("Users");
        ref.child(uid).child("email").setValue(newName);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(newName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserAccountEdit.this,"Email Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
