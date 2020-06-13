package com.tnj.if_else.utils.enums;

import android.content.Context;

import com.tnj.if_else.R;
import com.tnj.if_else.utils.interfaces.SuccessState;

public enum WorkflowSuccessCodes implements SuccessState {


    WORKFLOW_GET_SUCCESS(201, R.string.success_workflow_get),
    WORKFLOW_CREATE_SUCCESS(202, R.string.success_workflow_create),
    WORKFLOW_UPDATE_SUCCESS(203, R.string.success_workflow_field_update),
    WORKFLOW_DELETE_SUCCESS(204,R.string.success_workflow_deletion),
    WORKFLOW_COLLECTION_NOT_EMPTY(205, R.string.success_workflow_collection_not_empty)
    ;

    private int successCode;
    private int messageId;

    WorkflowSuccessCodes(int successCode, int messageId){
        this.successCode = successCode;
        this.messageId = messageId;
    }

    @Override
    public int getSuccessCode() {
        return successCode;
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
