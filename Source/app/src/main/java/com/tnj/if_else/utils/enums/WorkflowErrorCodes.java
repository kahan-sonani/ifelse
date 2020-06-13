package com.tnj.if_else.utils.enums;

import android.content.Context;

import com.tnj.if_else.R;
import com.tnj.if_else.utils.interfaces.ErrorResponse;

public enum WorkflowErrorCodes implements ErrorResponse {

    WORKFLOW_GET_ERROR(301, R.string.error_workflow_get),
    WORKFLOW_CREATE_ERROR(302, R.string.error_workflow_create),
    WORKFLOW_UPDATE_FAILED(303,R.string.error_workflow_update),
    WORKFLOW_DELETE_ERROR(304, R.string.error_workflow_delete),
    WORKFLOW_COLLECTION_EMPTY(305,R.string.error_workflow_collection_empty)
    ;


    private int errorCode;
    private int messageId;

    WorkflowErrorCodes(int errorCode , int messageId){
        this.errorCode = errorCode;
        this.messageId = messageId;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage(Context context) {
        return context.getString(messageId);
    }

    @Override
    public String getMessage(Context context, Object... formatArgs) {
        return context.getString(messageId,formatArgs);
    }
}
