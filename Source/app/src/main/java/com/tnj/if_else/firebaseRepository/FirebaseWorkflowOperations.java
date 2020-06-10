package com.tnj.if_else.firebaseRepository;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.WriteBatch;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.firebaseRepository.schema.FirebaseConfig;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;
import com.tnj.if_else.utils.helperClasses.Inlines;
import com.tnj.if_else.utils.interfaces.ResultSet;

public abstract class FirebaseWorkflowOperations extends BaseOperations{

    public CollectionReference getCollectionReference() {
        return collectionReference;
    }

    public void setCollectionReference(CollectionReference collectionReference) {
        this.collectionReference = collectionReference;
    }

    public <T> void updateWorkflow(String id, ResultSet<Boolean> listener, String key, T object, Object... FieldsAndObjects) {
        DocumentReference reference = collectionReference.document(id);
        WriteBatch batch = db.batch();
        batch.update(reference, key, object, FieldsAndObjects)
                .commit().addOnSuccessListener(aVoid -> Inlines.nonNull(listener, true))
                .addOnFailureListener(e -> {
                    Inlines.nonNull(listener, false);
                    e.printStackTrace();
                });
    }
    public void deleteWorkflow(String[] id) {

        WriteBatch batch = db.batch();

        try {
            for (String documentID : id)
                batch.delete(collectionReference.document(documentID));

            batch.commit()
                    .addOnSuccessListener(aVoid -> Log.i("ifelse","Successful"))
                    .addOnFailureListener(Throwable::printStackTrace);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disableEnableAllWorkflow(FirebaseConfig.UPDATE update, ResultSet<Boolean> listener) {

        WriteBatch updateBatch = db.batch();
        final String oldState;
        final String newState;
        if (update == FirebaseConfig.UPDATE.ENABLE) {
            oldState = Workflow.State.DEACTIVATED;
            newState = Workflow.State.ACTIVATED;
        } else {
            oldState = Workflow.State.ACTIVATED;
            newState = Workflow.State.DEACTIVATED;
        }

        collectionReference.whereEqualTo(WorkflowSchema.STATE, oldState).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Inlines.nonNull(listener, false);
                    } else {
                        for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                            Log.i("ifelse", (String) ds.get(WorkflowSchema.NAME));
                            updateBatch.update(ds.getReference(), WorkflowSchema.STATE, newState);
                        }
                        updateBatch.commit().addOnSuccessListener(aVoid -> Inlines.nonNull(listener, true));
                    }
                });
    }

    public ListenerRegistration getWorkflowChangeListener(String workflowId, EventListener<DocumentSnapshot> listener) {
        return collectionReference.document(workflowId)
                .addSnapshotListener(listener);
    }

}
