package com.tnj.if_else.firebaseRepository;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Source;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;
import com.tnj.if_else.utils.firebase.CustomWorkflowParser;
import com.tnj.if_else.utils.interfaces.ResultSet;

public class FirebaseCustomWorkflowOperations extends FirebaseWorkflowOperations {

    public FirebaseCustomWorkflowOperations(){
        super();
        collectionReference = FirebasePathProvider.custom();
    }

    public void createWorkflow(CustomWorkflow workflow) {

        DocumentReference reference = collectionReference.document();
        workflow.setId(reference.getId());
        collectionReference.document(reference.getId())
                .set(workflow)
                .addOnSuccessListener(aVoid -> Log.i("ifelse", "new workflow added!"))
                .addOnFailureListener(e -> Log.i("ifelse", "Error: -> " + e.getMessage()));
    }

    public void getWorkflow(Source source, String id, ResultSet<CustomWorkflow> result) {

        collectionReference
                .document(id)
                .get(source)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && result != null)
                        result.onResult(CustomWorkflowParser.parseSnapshot(documentSnapshot));
                })
                .addOnFailureListener(Throwable::printStackTrace);

    }
    public void getWorkflow(String id, ResultSet<CustomWorkflow> result) {

        collectionReference
                .document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && result != null)
                        result.onResult(CustomWorkflowParser.parseSnapshot(documentSnapshot));
                })
                .addOnFailureListener(Throwable::printStackTrace);

    }
}
