package com.tnj.if_else.firebaseRepository.schema;

public class FirebaseConfig {
    public enum CATEGORY {
        BUILT_IN,
        CUSTOM
    }

    public static class Workflows {
        public static final String WORKFLOW = "concreteWorkflow";

        public static class BuiltIn {
            public static final String BUILT_IN = "built-in";
        }

        public static class Custom {
            public static final String CUSTOM = "custom";
        }
    }

    public enum UPDATE{
        ENABLE,DISABLE
    }
}
