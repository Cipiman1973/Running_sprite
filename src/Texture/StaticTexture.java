package Texture;

import java.io.FileInputStream;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Shader.Shader;

public class StaticTexture {

    public int textureId;
    Shader shader;

    public void Gentexture() {

        Texture t = null;
        try {
            t = TextureLoader.getTexture("PNG",
                    new FileInputStream("Cyborg_run.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("The .png file is not in this directory");
            System.exit(-1);
        }
        textureId = t.getTextureID();
    }

    public StaticTexture(Shader shader) {
        this.shader = shader;
    }
}
