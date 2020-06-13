package com.tnj.if_else.firebaseRepository;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Source;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;
import com.tnj.if_else.utils.enums.WorkflowErrorCodes;
import com.tnj.if_else.utils.enums.WorkflowSuccessCodes;
import com.tnj.if_else.utils.helperClasses.CustomWorkflowParser;
import com.tnj.if_else.utils.helperClasses.Event;
import com.tnj.if_else.utils.helperClasses.Response;
import com.tnj.if_else.utils.helperClasses.WorkflowResponseError;
import com.tnj.if_else.utils.helperClasses.WorkflowResponseSuccess;
import com.tnj.if_else.utils.interfaces.State;

public class FirebaseCustomWorkflowOperations extends FirebaseWorkflowOperations {

    public FirebaseCustomWorkflowOperations(){
        super();
        collectionReference = FirebasePathProvider.custom();
    }

    public LiveData<Response<? extends State>> createWorkflow(CustomWorkflow workflow) {

        Event<Response<? extends State>> responseEvent = new Event<>();

        DocumentReference reference = collectionReference.document();
        workflow.setId(reference.getId());
        collectionReference.document(reference.getId())
                .set(workflow)
                .addOnSuccessListener(aVoid -> responseEvent.setValue(new WorkflowResponseSuccess<>(WorkflowSuccessCodes.WORKFLOW_CREATE_SUCCESS).finished(true)))
                .addOnFailureListener(e -> responseEvent.setValue(new WorkflowResponseError<>(WorkflowErrorCodes.WORKFLOW_CREATE_ERROR).finished(true)));
        return responseEvent;
    }

    public LiveData<Response<?>> getWorkflow(Source source, String id) {
        return getWorkflowPrivate(source , id);
    }
    public LiveData<Response<?>> getWorkflow(String id) {
        return getWorkflowPrivate(Source.SERVER , id);
    }

    private LiveData<Response<?>> getWorkflowPrivate(Source source , String id){
        Event<Response<?>> responseEvent = new Event<>();
        collectionReference
                .document(id)
                .get(source)
                .addOnSuccessListener(documentSnapshot -> responseEvent.setValue(new WorkflowResponseSuccess<>(CustomWorkflowParser.parseSnapshot(documentSnapshot)).finished(true)))
                .addOnFailureListener(aVoid -> responseEvent.setValue(new WorkflowResponseError<>(WorkflowErrorCodes.WORKFLOW_GET_ERROR).finished(true)));
        return responseEvent;
    }
}
