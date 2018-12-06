package eu.fbk.calc;

import android.test.AndroidTestRunner;
import android.util.Log;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;

public class TesterListener implements TestListener {

    @Override
    public void addError(Test test, Throwable throwable) {
        Log.i("GaussCalcTester","addError");
    }

    @Override
    public void addFailure(Test test, AssertionFailedError assertionFailedError) {
        Log.i("GaussCalcTester","addFailure");
    }

    @Override
    public void endTest(Test test) {
        Log.i("GaussCalcTester","endTest");
    }

    @Override
    public void startTest(Test test) {
        Log.i("GaussCalcTester","startTest");
    }
}
