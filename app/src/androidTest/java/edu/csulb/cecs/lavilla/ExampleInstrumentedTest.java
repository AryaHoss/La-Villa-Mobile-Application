package edu.csulb.cecs.lavilla;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    //@Rule
    //public TestRule rule = new InstantTaskExecutorRule();
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("edu.csulb.cecs.lavilla", appContext.getPackageName());
    }
    @Test
    public void testMyDatabase(){
        MakeOrderViewModel myModel = new MakeOrderViewModel();
        myModel.readData();
        System.out.println(myModel.printData());


    }
}
