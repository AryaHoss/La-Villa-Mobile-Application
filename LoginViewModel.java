package edu.csulb.cecs.lavilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;

import android.app.Application;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;



public class LoginViewModel extends AndroidViewModel {

    public MutableLiveData<user> userLiveData = new MutableLiveData<user>();

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference userDatabase;
    private DatabaseReference adminDatabase;

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
                    public boolean onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // String userType = (String) dataSnapshot.child(uid).getValue();
                        User userType = dataSnapshot.getValue(User.class);
                        String firstName = userType.fname;




                        if(userType.fname.equals("Admin") ){
                            return true;

                        }
                        else{
                            return false;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }

    };

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
}

