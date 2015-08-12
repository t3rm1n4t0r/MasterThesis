package dagrada.marco.shariki.animations.utility;

import shadow.math.SFVertex3f;

/**
 * Created by Marco on 12/08/2015.
 */
public interface BezierCurve {
    public SFVertex3f getValue(float t) throws ParameterOutOfRangeException;
}
