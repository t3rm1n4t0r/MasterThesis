package test;

import android.os.Handler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import dagrada.marco.runner.core.ItemsGenerator;
import dagrada.marco.runner.interactables.Guitar;
import thesis.Graphics.GraphicsEngine;
import thesis.utils.InteractablesCollector;
import thesis.utils.UpdatablesCollector;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Marco on 02/10/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class ItemsGeneratorTest {

    private ItemsGenerator generator;

    @Mock
    Handler handler;

    @Mock
    private UpdatablesCollector updatablesCollector;

    @Mock
    private InteractablesCollector interactablesCollector;


    @Before
    public void setUp() {
        handler = Mockito.mock(Handler.class);
        updatablesCollector = Mockito.mock(UpdatablesCollector.class);
        interactablesCollector = Mockito.mock(InteractablesCollector.class);

        generator = new ItemsGenerator(handler, 1000, updatablesCollector, interactablesCollector);

    }

    @Test
    public void testStartandStop(){
        assertTrue(generator.isStopped());
        generator.start();
        assertFalse(generator.isStopped());
        generator.stop();
        assertTrue(generator.isStopped());

    }

    @Test
    public void testAlernativeConstructor(){
        assertTrue(generator.getTimeDelay() == 1000);

        generator = new ItemsGenerator(handler, updatablesCollector, interactablesCollector);
        assertTrue(generator.getTimeDelay() == -1);
    }

    @Test
    public void testRun(){
        generator.start();
        generator.run();
        assertTrue(generator.getGenerated() == 1);
        generator.run();
        assertTrue(generator.getGenerated() == 2);

    }

}
