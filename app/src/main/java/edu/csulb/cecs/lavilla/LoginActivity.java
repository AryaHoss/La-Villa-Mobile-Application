package edu.csulb.cecs.lavilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextView emailtv, password, signUptv;
    Button loginbtn;
    FirebaseAuth mFirebaseAuth;
    String path, userId;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference userDatabase;
    private DatabaseReference adminDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailtv = findViewById(R.id.login_emailTV);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.login_button);
        signUptv = findViewById(R.id.suTV);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null) {
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference ref = database.getReference("Users");


                    ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // String userType = (String) dataSnapshot.child(uid).getValue();
                            User userType = dataSnapshot.getValue(User.class);
                            String firstName = userType.fname;




                            if(userType.fname.equals("Admin") ){
                                Toast.makeText(LoginActivity.this,"Admin Mode", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, AdminView.class));

                            }
                            else{
                                startActivity(new Intent (LoginActivity.this, UserHome.class));
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                else{
                    Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                userDatabase = FirebaseDatabase.getInstance().getReference("Users");
                adminDatabase = FirebaseDatabase.getInstance().getReference("Admin");
                String email = emailtv.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailtv.setError("Please enter email id");
                    emailtv.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DatabaseReference ref = database.getReference("Users");


                                ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        // String userType = (String) dataSnapshot.child(uid).getValue();
                                        User userType = dataSnapshot.getValue(User.class);
                                        String firstName = userType.fname;




                                        if(userType.userType.equals("Admin") ){
                                            Toast.makeText(LoginActivity.this,"Admin Mode", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, AdminView.class));

                                        }
                                        else{
                                            startActivity(new Intent (LoginActivity.this, UserHome.class));
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }

            }
        });

        signUptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
