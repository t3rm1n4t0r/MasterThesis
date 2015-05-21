package thesis;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

import java.util.HashMap;

import sfogl.integration.BitmapTexture;
import sfogl2.SFOGLTextureModel;
import shadow.graphics.SFImageFormat;

/**
 * Created by Marco on 18/05/2015.
 */
public class TexturesKeeper {

    private static HashMap<Integer, BitmapTexture> textures = new HashMap<>();

    public static BitmapTexture generateTexture(Context context, int textureId) {
        if (textures.containsKey(textureId))
            return textures.get(textureId);
        else {
            BitmapTexture tex = getBitmapTextureFromResource(context, textureId);
            textures.put(textureId, tex);
            return tex;
        }
    }

    private static BitmapTexture getBitmapTextureFromResource(Context context, int textureId) {
        int textureModel = SFOGLTextureModel.generateTextureObjectModel(SFImageFormat.RGBA, GLES20.GL_REPEAT, GLES20.GL_REPEAT, GLES20.GL_LINEAR, GLES20.GL_LINEAR);
        BitmapTexture tex = BitmapTexture.loadBitmapTexture(BitmapFactory.decodeResource(context.getResources(), textureId), textureModel);
        tex.init();
        return tex;
    }

    public static HashMap<Integer, BitmapTexture> getTextures() {
        return textures;
    }
}
