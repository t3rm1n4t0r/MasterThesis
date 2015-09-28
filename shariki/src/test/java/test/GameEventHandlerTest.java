package test;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import dagrada.marco.shariki.communicationpackets.ModelUpdatePacket;
import dagrada.marco.shariki.communicationpackets.SwitchDataPacket;
import dagrada.marco.shariki.core.GameModelHandler;
import dagrada.marco.shariki.core.GameEventHandler;
import thesis.Graphics.GraphicsEngine;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Marco on 27/08/2015.
 */
public class GameEventHandlerTest {

    private GameEventHandler stateHandler;

    @Mock
    private Context context;

    @Mock
    private GameModelHandler modelHandler;

    @Mock
    private GraphicsEngine engine;

    @Before
    public void setUp(){
        context = Mockito.mock(Context.class);
        modelHandler = Mockito.mock(GameModelHandler.class);
        when(modelHandler.getMaxHeigth()).thenReturn(5);
        when(modelHandler.getMaxWidth()).thenReturn(5);
        when(modelHandler.isGameEnded()).thenReturn(true);
        engine = Mockito.mock(GraphicsEngine.class);
        stateHandler = new GameEventHandler(context, modelHandler, engine);
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "level0.txt");
        stateHandler.startGame(list);

    }

    @Test
    public void testCorrectCreation(){
        assertTrue(stateHandler.getContext().equals(context));
        assertTrue(stateHandler.getEngine().equals(engine));
        assertTrue(stateHandler.getHandler().equals(modelHandler));
    }

    @Test
    public void testStartGame(){

        assertTrue(stateHandler.isStarted());
    }

    @Test
    public void testChainInitiation(){
        SwitchDataPacket packet = new SwitchDataPacket(1,1, 1, 2);
        stateHandler.startEventChain(packet);

        assertTrue(stateHandler.getQueueManager().getQueueSize() == 1);


    }

    @Test
    public void testChainInitiationWithIncorrectValues(){
        SwitchDataPacket packet = new SwitchDataPacket(1,1, 1, -2);
        stateHandler.startEventChain(packet);


        assertTrue(stateHandler.getQueueManager().getQueueSize() == 0);


    }

    @Test
    public void testChainContinuation(){
        SwitchDataPacket packet = new SwitchDataPacket(1,1, 1, 2);
        stateHandler.startEventChain(packet);

        assertTrue(stateHandler.getQueueManager().getQueueSize() == 1);
        stateHandler.notifyEventConclusion(null, null);
        assertTrue(stateHandler.getQueueManager().getQueueSize() == 0);


    }

    public void testChainContinuation2(){
        SwitchDataPacket packet = new SwitchDataPacket(1,1, 1, 2);
        stateHandler.startEventChain(packet);

        assertTrue(stateHandler.getQueueManager().getQueueSize() == 1);
        stateHandler.notifyEventConclusion(null, null);
        assertTrue(stateHandler.getQueueManager().getQueueSize() == 0);

        ModelUpdatePacket packet1 = new ModelUpdatePacket(true, false);
        stateHandler.notifyEventConclusion(null, packet1);
        assertTrue(stateHandler.getQueueManager().getQueueSize() == 0);


    }
}
