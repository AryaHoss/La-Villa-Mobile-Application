package edu.csulb.cecs.lavilla.ui.makeorder;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.csulb.cecs.lavilla.User;

public class UserViewModel extends ViewModel {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    User user;


    UserViewModel(){
        user = new User();
    }


}
