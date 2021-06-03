package Main;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import org.lwjgl.opengl.GL33;

import Shader.Shader;
import Texture.StaticTexture;


public class Main {


    public static void main(String[] args) {

        GLFW.glfwInit();

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);

        long window = GLFW.glfwCreateWindow(800, 800, "Running sprite", 0, 0);
        if (window == 0) {
            GLFW.glfwTerminate();
        }
        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();
        GL33.glViewport(0, 0, 800, 800);

        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            GL33.glViewport(0, 0, w, h);
        });

        Shader shader = new Shader();
        shader.initShaders();

        float[] vertices = {
                0.3f, 0.3f, 0.0f,
                0.3f, -0.3f, 0.0f,
                -0.3f, -0.3f, 0.0f,
                -0.3f, 0.3f, 0.0f
        };

        float[] color = {
                1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f,
        };

        float[] textu = {
                0.09f, 0.0f,
                0.09f, 0.8f,
                0.0f, 0.8f,
                0.0f, 0.0f


        };
        int[] indices = {
                0, 1, 3,
                1, 2, 3
        };


        int VBO, VAO, EBO, col, tex;
        VAO = GL33.glGenVertexArrays();
        VBO = GL33.glGenBuffers();
        EBO = GL33.glGenBuffers();
        col = GL33.glGenBuffers();
        tex = GL33.glGenBuffers();

        GL33.glBindVertexArray(VAO);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, VBO);
        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);

        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, EBO);
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, indices, GL33.GL_STATIC_DRAW);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, col);
        FloatBuffer fbc = BufferUtils.createFloatBuffer(color.length).put(color).flip();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fbc, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, tex);
        FloatBuffer fbt = BufferUtils.createFloatBuffer(textu.length).put(textu).flip();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fbt, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(2);

        StaticTexture texture = new StaticTexture(shader);
        texture.Gentexture();
        while (!GLFW.glfwWindowShouldClose(window)) {

            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
                GLFW.glfwSetWindowShouldClose(window, true);


            GL33.glClearColor(0f, 1f, 0f, 1f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);


            GL33.glBindTexture(GL33.GL_TEXTURE_2D, texture.textureId);


            GL33.glUseProgram(shader.shaderProgramId);
            shader.setFloat();
            GL33.glBindVertexArray(VAO);
            GL33.glDrawElements(GL33.GL_TRIANGLES, 6, GL33.GL_UNSIGNED_INT, 0);


            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        GLFW.glfwTerminate();
    }
}
