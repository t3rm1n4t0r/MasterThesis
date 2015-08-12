package dagrada.marco.shariki.animations.utility;

import shadow.math.SFVertex3f;

/**
 * Created by Marco on 12/08/2015.
 */
public class SecondOrderBezierCurve implements BezierCurve{

    SFVertex3f p1, p2, p3;

    public SecondOrderBezierCurve(SFVertex3f p1, SFVertex3f p2, SFVertex3f p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public SFVertex3f getValue(float t) throws ParameterOutOfRangeException{

        if(t > 1 || t < 0)
            throw new ParameterOutOfRangeException("Parameter values range from 0 to 1");
        else{
            SFVertex3f res = new SFVertex3f();
            res.setX((1 - t) * (1 - t) * p1.getX() + 2 * t * (1 - t) * p2.getX() + t * t * p3.getX());
            res.setY((1 - t) * (1 - t) * p1.getY() + 2 * t * (1 - t) * p2.getY() + t * t * p3.getY());
            res.setZ((1-t)*(1-t)*p1.getZ() + 2*t*(1-t)*p2.getZ() + t*t*p3.getZ());

            return res;
        }
    }

}
