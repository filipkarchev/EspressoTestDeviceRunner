package com.example.testslibrary;

import org.json.JSONException;
import org.json.JSONObject;

public class TestResult {
    private String className = "";
    private String methodName = "";
    private int resultStatus = 1;
    private String errorMessage = "";

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(int resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!TestResult.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final TestResult other = (TestResult) obj;
        if (this.className.equals(other.getClassName()) && this.methodName.equals(other.getMethodName())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.className != null ? this.className.hashCode() : 0) + (this.methodName !=null ? this.methodName.hashCode() : 0);
        return hash;
    }

    public JSONObject convertToJsonObject() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("className",this.className);
        object.put("methodName",this.methodName);
        object.put("errorMessage",this.errorMessage);
        object.put("resultStatus",this.resultStatus);
        return  object;
    }
}
