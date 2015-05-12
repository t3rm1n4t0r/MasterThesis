package game.graphics;

import android.content.Context;
import game.physics.CollisionBox;
import sfogl.integration.Node;

/**
 * Rappresenta un generico oggetto del labirinto.
 * @author Stefano De Pace
 *
 */
public interface MazeObject {

    /**
     * Restituisce un Node che rappresenta l'oggetto.
     * @param context Context per caricare le risorse necessarie a rappresentare l'oggetto.
     * @return Node che rappresenta l'oggetto.
     */
    public Node getNode(Context context);

    /**
     * Restituisce la CollisionBox per immettere l'oggetto nel sistema di collisioni.
     * @return CollisionBox dell'oggetto; può essere null se l'oggetto non deve
     * entrare nel sistema di collisioni.
     */
    public CollisionBox getBox();

}
