package com.tnj.if_else.utils.helperClasses;

public class Inlines {

    public static void nonNull(Object object , Object value){ if(object != null) object = value; }
    public static void ifNull(Object object , Object value){if(object == null) object = value;}
}
