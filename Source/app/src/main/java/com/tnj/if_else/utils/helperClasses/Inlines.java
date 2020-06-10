package com.tnj.if_else.utils.helperClasses;

import com.tnj.if_else.utils.interfaces.ResultSet;

public class Inlines {

    public static void nonNull(ResultSet resultSet, boolean bool){ if(resultSet != null) resultSet.onResult(bool); }
    public static void nonNull(Object object , Object value){ if(object != null) object = value; }
    public static void ifNull(Object object , Object value){if(object == null) object = value;}
}
