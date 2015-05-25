package thesis.Graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.GLES20;

import java.util.HashMap;

import sfogl.integration.BitmapTexture;
import sfogl2.SFOGLTextureModel;
import shadow.graphics.SFImageFormat;

/**
 * Created by Marco on 19/05/2015.
 */
public class MonochromaticTexturesKeeper {

    private static HashMap<Integer, BitmapTexture> textures = new HashMap<>();

    public static BitmapTexture generateTexture(Context context, String colorARGB) {
        int textureID = Color.parseColor(colorARGB);
        if (textures.containsKey(textureID))
            return textures.get(textureID);
        else {
            BitmapTexture tex = getBitmapTextureFromColor(textureID);
            textures.put(textureID, tex);
            return tex;
        }
    }

    private static BitmapTexture getBitmapTextureFromColor(int color) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(64, 64, conf);
        bitmap.eraseColor(color);

        int textureModel = SFOGLTextureModel.generateTextureObjectModel(SFImageFormat.RGB, GLES20.GL_REPEAT, GLES20.GL_REPEAT, GLES20.GL_LINEAR, GLES20.GL_LINEAR);
        BitmapTexture tex = BitmapTexture.loadBitmapTexture(bitmap, textureModel);
        tex.init();
        return tex;
    }

    public static HashMap<Integer, BitmapTexture> getTextures() {
        return textures;
    }
}