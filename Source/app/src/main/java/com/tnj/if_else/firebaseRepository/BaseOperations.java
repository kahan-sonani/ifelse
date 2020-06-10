package com.tnj.if_else.firebaseRepository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class BaseOperations {

    protected FirebaseAuth mAuth;
    protected FirebaseFirestore db;
    protected CollectionReference collectionReference;

    public BaseOperations(){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }
}
