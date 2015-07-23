package test;

/**
 * Created by Marco on 23/07/2015.
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import dagrada.marco.shariki.MatrixChecker;

//JUnit Suite Test
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MatrixChecker.class ,
})
public class JUnitTestSuite {
}