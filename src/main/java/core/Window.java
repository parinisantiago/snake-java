package core;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

//La representacion de la ventana en la que corre el juego
public class Window {

    private static Window window =  null;
    private int width,height;
    private String title;
    private long glfwWindow;

    private Window(){
        this.width = 800;
        this.height = 600;
        this.title = "snake";
    }

    public static Window get(){
        if(window == null) window = new Window();
        return window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();
    }


    private void init() {
        // Setea el lugar donde va a enviar los errores. en este caso solo hace un log
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); //pone los datos por default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); //hace que la ventana no sea visible hasta que terminemos de crearla
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); //permite cambiar el tamano de la ventana

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL); //devuelve la direccion de memoria de la ventana
        if ( glfwWindow == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        //Opengl context
        glfwMakeContextCurrent(glfwWindow);

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    private void loop() {

        while ( !glfwWindowShouldClose(glfwWindow) ) {
            // Poll for window events.
            glfwPollEvents();

            glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer
            glfwSwapBuffers(glfwWindow); // swap the color buffers

        }
    }
}
