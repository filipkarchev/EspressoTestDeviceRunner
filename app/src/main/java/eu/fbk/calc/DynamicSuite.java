package eu.fbk.calc;

import android.util.Log;

import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;


public class DynamicSuite extends Suite {

    public DynamicSuite(Class<?> setupClass) throws InitializationError {

        super(setupClass, DynamicSuiteBuilder.suite());
        Log.i("DynamicSuite","Constructor");
    }
}