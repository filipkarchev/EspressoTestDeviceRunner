package eu.fbk.calc;

import android.util.Log;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TesterListener extends RunListener {
private final String LOG_CAT = "TesterListener";
    @Override
    public void testFinished(Description description) throws Exception {
        Log.i(LOG_CAT,"testFinished class: " + description.getClassName() + " method: " + description.getMethodName());
        super.testFinished(description);
    }

    @Override
    public void testStarted(Description description) throws Exception {
        Log.i(LOG_CAT,"testStarted");
        super.testStarted(description);
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        Log.e(LOG_CAT,"testFailure class: " + failure.getDescription().getClassName() + " method: " + failure.getDescription().getMethodName() + " message: " + failure.getMessage() + " toString:" + failure.toString());

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

        //TODO after the test are finished we can send the results to the server
    }
}
