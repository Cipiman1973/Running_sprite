package Shader;


import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;

public class Shader {

    String vertexShaderSource = "#version 330 core\n"
            + "layout (location = 0) in vec3 aPos;\n"
            + "layout (location = 1) in vec3 aColor;\n"
            + "layout (location = 2) in vec2 aTexCoord;\n"
            + "\n"
            + "out vec3 ourColor;\n"
            + "out vec2 TexCoord;\n"
            + "\n"
            + "//uniform mat4 transformationMatrix;\n"
            + "uniform float animation;\n"
            + "\n"
            + "void main()\n"
            + "{\n"
            + "	gl_Position =vec4(aPos,1.0);\n"
            + "	ourColor = aColor;\n"
            + "	TexCoord = vec2(aTexCoord.x + animation , aTexCoord.y );\n"
            + "}";
    String fragmentShaderSource = "#version 330 core\n"
            + "out vec4 FragColor;\n"
            + "\n"
            + "in vec3 ourColor;\n"
            + "in vec2 TexCoord;\n"
            + "\n"
            + "// texture sampler\n"
            + "uniform sampler2D texture1;\n"
            + "\n"
            + "void main()\n"
            + "{\n"
            + "	FragColor =texture(texture1, TexCoord);\n"
            + "}";

    public int vertexShaderId;
    public int fragmentShaderId;
    public int shaderProgramId;

    float animation = 0.0f;
    float velocity = 0.01f;
    int count = 0;

    public void initShaders() {
        vertexShaderId = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);
        fragmentShaderId = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);

        GL33.glShaderSource(vertexShaderId, vertexShaderSource);
        GL33.glCompileShader(vertexShaderId);

        System.out.println(GL33.glGetShaderInfoLog(vertexShaderId));

        GL33.glShaderSource(fragmentShaderId, fragmentShaderSource);
        GL33.glCompileShader(fragmentShaderId);

        System.out.println(GL33.glGetShaderInfoLog(fragmentShaderId));

        shaderProgramId = GL33.glCreateProgram();
        GL33.glAttachShader(shaderProgramId, vertexShaderId);
        GL33.glAttachShader(shaderProgramId, fragmentShaderId);
        GL33.glLinkProgram(shaderProgramId);

        System.out.println(GL33.glGetProgramInfoLog(shaderProgramId));

        GL33.glDeleteShader(vertexShaderId);
        GL33.glDeleteShader(fragmentShaderId);
        GL33.glUseProgram(shaderProgramId);


    }

    public void setFloat() {
        setAnimation();

        GL20.glUniform1f(GL33.glGetUniformLocation(shaderProgramId, "animation"), animation);

    }

    private void setAnimation() {
        velocity += 0.007f;
        if (velocity > 0.3f) {
            velocity = 0.01f;
            animation += 0.1f;
            count++;
        }
        if (count == 6) {
            animation = 0.0f;
            count = 0;
        }
    }
}
