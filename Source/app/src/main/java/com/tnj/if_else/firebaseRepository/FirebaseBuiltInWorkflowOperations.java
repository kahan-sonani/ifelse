package com.tnj.if_else.firebaseRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;
import com.tnj.if_else.utils.enums.WorkflowErrorCodes;
import com.tnj.if_else.utils.enums.WorkflowSuccessCodes;
import com.tnj.if_else.utils.helperClasses.BuiltInWorkflowParser;
import com.tnj.if_else.utils.helperClasses.Event;
import com.tnj.if_else.utils.helperClasses.FirebaseError;
import com.tnj.if_else.utils.helperClasses.Response;
import com.tnj.if_else.utils.helperClasses.WorkflowResponseError;
import com.tnj.if_else.utils.helperClasses.WorkflowResponseSuccess;
import com.tnj.if_else.utils.interfaces.State;

public class FirebaseBuiltInWorkflowOperations extends FirebaseWorkflowOperations {

    public FirebaseBuiltInWorkflowOperations(){
        super();
        this.collectionReference = FirebasePathProvider.builtIn();
    }

    public Query getBuiltInWorkflowQuery(String id) {
        return collectionReference.whereEqualTo(WorkflowSchema.ID, id);
    }

    public LiveData<Response<? extends State>> createWorkflow(String id) {

        Event<Response<? extends State>> responseMutableLiveData = new Event<>();
        collectionReference.document(id)
                .set(new BuiltInWorkflow(id))
                .addOnSuccessListener(aVoid -> responseMutableLiveData.setValue(
                        new WorkflowResponseSuccess<>(WorkflowSuccessCodes.WORKFLOW_CREATE_SUCCESS).finished(true)))
                .addOnFailureListener(e -> responseMutableLiveData.setValue(new WorkflowResponseError<>(FirebaseError.fromException(e))));
        return responseMutableLiveData;
    }

    public <T> LiveData<Response<? extends State>> updateOrCreateWorkflow(String id, String key, T object, Object... FieldsAndObjects) {

        MutableLiveData<Response<? extends State>> responseMutableLiveData = new MutableLiveData<>();

        DocumentReference reference = collectionReference.document(id);
        reference.get().addOnSuccessListener(snapshot -> {
            if(snapshot.exists()) {
                reference.update(key,object,FieldsAndObjects)
                        .addOnSuccessListener(aVoid -> responseMutableLiveData.setValue(new WorkflowResponseSuccess<>(WorkflowSuccessCodes.WORKFLOW_UPDATE_SUCCESS)
                        .finished(true)))
                        .addOnFailureListener(e -> responseMutableLiveData.setValue(new WorkflowResponseSuccess<>(WorkflowErrorCodes.WORKFLOW_UPDATE_FAILED)
                        .finished(true)));
            }else {
                collectionReference.document(id).set(new BuiltInWorkflow(id))
                        .addOnSuccessListener(aVoid -> {
                            responseMutableLiveData.setValue(new WorkflowResponseSuccess<>(WorkflowSuccessCodes.WORKFLOW_CREATE_SUCCESS));
                            reference.update(key,object,FieldsAndObjects)
                                    .addOnSuccessListener(aVoid1 -> responseMutableLiveData.setValue(
                                            new WorkflowResponseSuccess<>(WorkflowSuccessCodes.WORKFLOW_UPDATE_SUCCESS).finished(true)))
                                    .addOnFailureListener(aVoid3 -> responseMutableLiveData.setValue(
                                            new WorkflowResponseError<>(WorkflowErrorCodes.WORKFLOW_UPDATE_FAILED).finished(true)));
                        })
                        .addOnFailureListener(e -> responseMutableLiveData.setValue(new WorkflowResponseError<>(WorkflowErrorCodes.WORKFLOW_CREATE_ERROR).finished(true)));
            }
        }).addOnFailureListener(e -> responseMutableLiveData.setValue(new WorkflowResponseError<>(WorkflowErrorCodes.WORKFLOW_GET_ERROR).finished(true)));

        return responseMutableLiveData;
    }

    private LiveData<Response<?>> getWorkflowPrivate(Source source, String id) {

        Event<Response<?>> responseMutableLiveData = new Event<>();

        collectionReference
                .document(id)
                .get(source)
                .addOnSuccessListener(documentSnapshot -> responseMutableLiveData.setValue(new WorkflowResponseSuccess<>(BuiltInWorkflowParser.parseSnapshot(documentSnapshot))
                        .setState(WorkflowSuccessCodes.WORKFLOW_GET_SUCCESS).finished(true)))
                .addOnFailureListener(e ->responseMutableLiveData.setValue(new WorkflowResponseError<>(WorkflowErrorCodes.WORKFLOW_GET_ERROR).finished(true)));

        return responseMutableLiveData;
    }

    public LiveData<Response<?>> getWorkflow(String id) {

        return getWorkflowPrivate(Source.SERVER,id);
    }

    public LiveData<Response<?>> getWorkflow(Source source, String id) {
        return getWorkflowPrivate(source, id);
    }
} 
