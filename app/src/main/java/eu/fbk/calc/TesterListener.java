package eu.fbk.calc;

import android.util.Log;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TesterListener extends RunListener {
private final String LOG_CAT = "TesterListener";
    @Override
    public void testFinished(Description description) throws Exception {
        Log.i(LOG_CAT,"testFinished");
        super.testFinished(description);
    }

    @Override
    public void testStarted(Description description) throws Exception {
        Log.i(LOG_CAT,"testStarted");
        super.testStarted(description);
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        Log.e(LOG_CAT,"testFailure");
        super.testFailure(failure);
    }

}
