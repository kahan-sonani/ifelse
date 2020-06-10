package com.tnj.if_else.firebaseRepository;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;
import com.tnj.if_else.utils.firebase.BuiltInWorkflowParser;
import com.tnj.if_else.utils.helperClasses.Inlines;
import com.tnj.if_else.utils.interfaces.ResultSet;

public class FirebaseBuiltInWorkflowOperations extends FirebaseWorkflowOperations {

    public FirebaseBuiltInWorkflowOperations(){
        super();
        this.collectionReference = FirebasePathProvider.builtIn();
    }

    public Query getBuiltInWorkflowQuery(String id) {
        return collectionReference.whereEqualTo(WorkflowSchema.ID, id);
    }

    public void createWorkflow(String id, ResultSet<Boolean> listener) {
        collectionReference.document(id)
                .set(new BuiltInWorkflow(id))
                .addOnSuccessListener(aVoid -> Inlines.nonNull(listener, true))
                .addOnFailureListener(e -> {
                    Inlines.nonNull(listener, false);
                    e.printStackTrace();
                });
    }

    public <T> void updateOrCreateWorkflow(String id, ResultSet<Boolean> listener, String key, T object, Object... FieldsAndObjects) {
        DocumentReference reference = collectionReference.document(id);
        reference.get().addOnSuccessListener(snapshot -> {
            if(snapshot.exists()) {
                reference.update(key,object,FieldsAndObjects)
                        .addOnSuccessListener(aVoid -> Inlines.nonNull(listener,true))
                        .addOnFailureListener(e -> {
                            Inlines.nonNull(listener,false);
                            e.printStackTrace();
                        });
            }else {
                createWorkflow(id, result -> {
                    if(result)
                        reference.update(key,object,FieldsAndObjects)
                                .addOnSuccessListener(aVoid -> Inlines.nonNull(listener,true))
                                .addOnFailureListener(e -> Inlines.nonNull(listener,false));
                });
            }
        }).addOnFailureListener(e -> {
            Inlines.nonNull(listener,false);
            e.printStackTrace();
        });
    }

    public void getWorkflow(Source source, String id, ResultSet<BuiltInWorkflow> result) {

        collectionReference
                .document(id)
                .get(source)
                .addOnSuccessListener(documentSnapshot -> {
                    if (result != null) {
                        if (documentSnapshot.exists())
                            try {
                                result.onResult(BuiltInWorkflowParser.parseSnapshot(documentSnapshot));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        else
                            result.onResult(null);
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace);

    }

    public void getWorkflow(String id, ResultSet<BuiltInWorkflow> result) {

        collectionReference
                .document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (result != null) {
                        if (documentSnapshot.exists())
                            result.onResult(BuiltInWorkflowParser.parseSnapshot(documentSnapshot));
                        else
                            result.onResult(null);
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace);

    }
} 
