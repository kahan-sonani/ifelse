package com.tnj.if_else.firebaseRepository;

public final class FirebaseRepository {

    private FirebaseBuiltInWorkflowOperations builtInWorkflowOperations;
    private FirebaseCustomWorkflowOperations customWorkflowOperations;

    private static FirebaseRepository repository;

    private FirebaseRepository(){
        builtInWorkflowOperations = new FirebaseBuiltInWorkflowOperations();
        customWorkflowOperations = new FirebaseCustomWorkflowOperations();
    }

    public static FirebaseRepository getInstance(){
        if(repository == null)
            return repository = new FirebaseRepository();
        else
            return repository;
    }

    public FirebaseBuiltInWorkflowOperations builtIn(){
        return builtInWorkflowOperations;
    }
    public FirebaseCustomWorkflowOperations custom(){
        return customWorkflowOperations;
    }
}
