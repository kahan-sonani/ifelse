package com.tnj.if_else.firebaseRepository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;
import com.tnj.if_else.firebaseRepository.schema.FirebaseConfig;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;
import com.tnj.if_else.utils.enums.WorkflowErrorCodes;
import com.tnj.if_else.utils.enums.WorkflowSuccessCodes;
import com.tnj.if_else.utils.helperClasses.Event;
import com.tnj.if_else.utils.helperClasses.Response;
import com.tnj.if_else.utils.helperClasses.WorkflowResponseError;
import com.tnj.if_else.utils.helperClasses.WorkflowResponseSuccess;
import com.tnj.if_else.utils.interfaces.State;

public abstract class FirebaseWorkflowOperations extends BaseOperations{

    public CollectionReference getCollectionReference() {
        return collectionReference;
    }

    public void setCollectionReference(CollectionReference collectionReference) {
        this.collectionReference = collectionReference;
    }

    public <T> LiveData<Response<? extends State>> updateWorkflow(String id, String key, T object, Object... FieldsAndObjects) {

        Event<Response<? extends State>> responseEvent = new Event<>();

        DocumentReference reference = collectionReference.document(id);
        WriteBatch batch = db.batch();
        batch.update(reference, key, object, FieldsAndObjects)
                .commit().addOnSuccessListener(aVoid -> responseEvent.setValue(new WorkflowResponseSuccess<>(WorkflowSuccessCodes.WORKFLOW_UPDATE_SUCCESS).finished(true)))
                .addOnFailureListener(e -> responseEvent.setValue(new WorkflowResponseError<>(WorkflowErrorCodes.WORKFLOW_UPDATE_FAILED).finished(true)));

        return responseEvent;
    }

    public void deleteWorkflow(String[] id) {

        WriteBatch batch = db.batch();

        try {
            for (String documentID : id)
                batch.delete(collectionReference.document(documentID));

            batch.commit()
                    .addOnSuccessListener(aVoid -> new WorkflowResponseSuccess<>(WorkflowSuccessCodes.WORKFLOW_DELETE_SUCCESS).finished(true))
                    .addOnFailureListener(e -> new WorkflowResponseError<>(WorkflowErrorCodes.WORKFLOW_DELETE_ERROR).finished(true));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<Response<? extends State>> disableEnableAllWorkflow(FirebaseConfig.UPDATE update) {

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

        Event<Response<? extends State>> responseEvent = new Event<>();

        collectionReference.whereEqualTo(WorkflowSchema.STATE, oldState).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        responseEvent.setValue(new WorkflowResponseError<>(WorkflowErrorCodes.WORKFLOW_COLLECTION_EMPTY).finished(true));
                    } else {
                        for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                            Log.i("ifelse", (String) ds.get(WorkflowSchema.NAME));
                            updateBatch.update(ds.getReference(), WorkflowSchema.STATE, newState);
                        }
                        updateBatch.commit().addOnSuccessListener(aVoid -> responseEvent.setValue(new WorkflowResponseSuccess<>(WorkflowSuccessCodes.WORKFLOW_UPDATE_SUCCESS).finished(true)))
                                .addOnFailureListener(e -> responseEvent.setValue(new WorkflowResponseError<>(WorkflowErrorCodes.WORKFLOW_UPDATE_FAILED).finished(true)));
                    }
                });
        return responseEvent;
    }

    public ListenerRegistration getWorkflowChangeListener(String workflowId, EventListener<DocumentSnapshot> listener) {
        return collectionReference.document(workflowId)
                .addSnapshotListener(listener);
    }

    public Event<Boolean> isCollectionEmpty(){

        Event<Boolean> isWorkflowBucketEmpty = new Event<>(false);
        collectionReference.get()
                .addOnSuccessListener(queryDocumentSnapshots -> isWorkflowBucketEmpty.setValue(queryDocumentSnapshots.getDocuments().size() == 0))
                .addOnFailureListener(e -> isWorkflowBucketEmpty.setValue(false));
        return isWorkflowBucketEmpty;
    }

    public Query queryOrderByState(boolean ascending) {
        return collectionReference.orderBy(WorkflowSchema.STATE, ascending ? Query.Direction.DESCENDING :Query.Direction.ASCENDING);
    }

    public Query queryOrderByName(boolean ascending){
        return collectionReference.orderBy(WorkflowSchema.NAME, ascending ? Query.Direction.ASCENDING :Query.Direction.DESCENDING);
    }

}
