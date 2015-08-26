package test;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import dagrada.marco.shariki.EventQueueManager;
import dagrada.marco.shariki.GameEvent;
import dagrada.marco.shariki.GameModelHandler;
import dagrada.marco.shariki.exceptions.GameEndException;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Marco on 26/08/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventQueueManagerTest {

    EventQueueManager queueManager;

    @Mock
    GameEvent event1, event2, event3;

    @Before
    public void setUp(){
        queueManager = new EventQueueManager();
        event1 = Mockito.mock(GameEvent.class);
        event2 = Mockito.mock(GameEvent.class);

    }

    @Test
    public void testQueueBehaviour(){
        queueManager.addToQueue(event1);
        assertTrue(queueManager.getQueueSize() == 1);
        queueManager.addToQueue(event2);
        assertTrue(queueManager.getQueueSize() == 2);
        assertTrue(queueManager.getNextEvent().equals(event1));
        assertTrue(queueManager.getQueueSize() == 1);
        assertTrue(queueManager.getNextEvent().equals(event2));
        assertTrue(queueManager.getNextEvent() == null);

    }
}
