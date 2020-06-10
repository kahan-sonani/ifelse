package com.tnj.if_else.firebaseRepository;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tnj.if_else.firebaseRepository.schema.WorkflowSchema;
import com.tnj.if_else.utils.helperClasses.Inlines;
import com.tnj.if_else.utils.interfaces.ResultSet;

public class HomeFragmentRepository extends BaseOperations {

    public Query getBuiltInWorkflowQuery() {
        return FirebasePathProvider.builtIn().orderBy(WorkflowSchema.STATE, Query.Direction.DESCENDING);
    }

    public Query getCustomWorkflowQuery() {
        return FirebasePathProvider.custom().orderBy(WorkflowSchema.STATE, Query.Direction.DESCENDING);
    }

    public void isListEmpty(ResultSet<Boolean> listener) {
        Task<QuerySnapshot> q = FirebasePathProvider.builtIn().get();

        q.addOnSuccessListener(queryDocumentSnapshots -> {
            if(queryDocumentSnapshots.getDocuments().size() == 0) {
                q.continueWithTask((Continuation<QuerySnapshot, Task<Void>>) task -> {
                    FirebasePathProvider.custom()
                            .get().addOnSuccessListener(queryDocumentSnapshots1 -> {
                                if(queryDocumentSnapshots1.getDocuments().size() == 0)
                                    Inlines.nonNull(listener,true);
                                else
                                    Inlines.nonNull(listener,false);
                            }).addOnFailureListener(e -> Inlines.nonNull(listener,false));
                    return null;
                });
            }else Inlines.nonNull(listener,false);
        }).addOnFailureListener(e -> Inlines.nonNull(listener,false));
    }
}
