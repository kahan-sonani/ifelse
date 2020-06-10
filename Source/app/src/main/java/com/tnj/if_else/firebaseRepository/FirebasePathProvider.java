package com.tnj.if_else.firebaseRepository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tnj.if_else.firebaseRepository.schema.FirebaseConfig;

class FirebasePathProvider {

    static CollectionReference builtIn(){
        return FirebaseFirestore.getInstance().collection(FirebaseConfig.Workflows.WORKFLOW)
                .document(FirebaseConfig.Workflows.BuiltIn.BUILT_IN)
                .collection(FirebaseAuth.getInstance().getUid());
    }
    static CollectionReference custom(){
        return FirebaseFirestore.getInstance().collection(FirebaseConfig.Workflows.WORKFLOW)
                .document(FirebaseConfig.Workflows.Custom.CUSTOM)
                .collection(FirebaseAuth.getInstance().getUid());
    }
}

