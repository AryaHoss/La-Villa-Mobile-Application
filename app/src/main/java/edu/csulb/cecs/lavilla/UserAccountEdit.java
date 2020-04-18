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
    private TextView editFname, setFname;
    private Button access;
    private String updatedFname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_edit);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        /*editFname = findViewById(R.id.email_tv);
        setFname = findViewById(R.id.email_et);
        access = findViewById(R.id.set_button);*/

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = firebaseDatabase.getReference("Users");

        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                editFname.setText(user.getfname());

                access.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        changefName(setFname.getText().toString().trim());

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
