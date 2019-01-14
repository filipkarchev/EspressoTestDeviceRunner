package com.example.testslibrary;

import android.content.Intent;
import android.util.Log;

import junit.framework.AssertionFailedError;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.util.ArrayList;

public class TesterListener extends RunListener {
private final String LOG_CAT = "TesterListener";
ArrayList<TestResult> results = new ArrayList<>();
    @Override
    public void testFinished(Description description) throws Exception {
        Log.i(LOG_CAT,"testFinished class: " + description.getClassName() + " method: " + description.getMethodName());
        super.testFinished(description);
    }

    @Override
    public void testStarted(Description description) throws Exception {
        Log.i(LOG_CAT,"testStarted");
        TestResult newResult = new TestResult();
        newResult.setClassName(description.getClassName());
        newResult.setMethodName(description.getMethodName());
        results.add(newResult);

        super.testStarted(description);
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        Log.e(LOG_CAT,"testFailure class: " + failure.getDescription().getClassName() + " method: " + failure.getDescription().getMethodName() + " message: " + failure.getMessage() + " toString:" + failure.toString());
        TestResult newResult = new TestResult();
        newResult.setClassName(failure.getDescription().getClassName());
        newResult.setMethodName(failure.getDescription().getMethodName());
        newResult.setErrorMessage(failure.getMessage());

        //Get is it from crash or assertion
        if(failure.getException() instanceof AssertionFailedError)
        {
            newResult.setResultStatus(2);
        }else
        {
            newResult.setResultStatus(3);
        }

        if(results.contains(newResult))
        {
            //Update this element
            int index = results.indexOf(newResult);
            results.set(index,newResult);
        }
        super.testFailure(failure);
    }

    @Override
    public void testRunStarted(Description description) throws Exception {
        Log.e(LOG_CAT,"testRunStarted tests count: " + description.testCount() + " " + description.getClassName());

        super.testRunStarted(description);
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        Log.e(LOG_CAT,"testRunFinished runs: "+ result.getRunCount() + " fails: " + result.getFailureCount());
        super.testRunFinished(result);
        JSONArray report = new JSONArray();
        for(int i=0;i<results.size();i++)
        {
            TestResult testResult = results.get(i);
            report.put(i,testResult.convertToJsonObject());
        }

        Log.i("TesterLister","report: " + report.toString());
        results.clear();

        //After the tests are finished we can send the results to the server
        new SendTestsReport().execute(Constants.reportUrl,report.toString());

        //Run the app
        Intent intent = new Intent("android.intent.category.LAUNCHER");
        intent.setClassName("eu.fbk.calc", "eu.fbk.calc.GaussCalc");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.openAppContext.startActivity(intent);
    }
}
