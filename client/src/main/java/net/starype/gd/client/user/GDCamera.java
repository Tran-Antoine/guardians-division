package net.starype.gd.client.user;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 * @author Antoine Tran
 *
 * Sets up a basic camera replacing {@link com.jme3.input.FlyByCamera}. <br>
 * GDCamera adds the four mouse axis mappings, as well as the ESC key, changing the visibility of the cursor.
 * The camera does not rotate as long as the cursor is visible (similarly to some sort of pause menu)
 */
public class GDCamera implements AnalogListener {

    private Camera source;
    private Vector3f initialUp;
    private InputManager inputManager;

    private int sensitivity = 30;
    private int fov = 70;
    private static final String[] MAPPINGS = {"left", "right", "top", "bottom"};

    public GDCamera(InputManager inputManager, Camera source) {
        this.inputManager = inputManager;
        this.source = source;
        initialUp = source.getUp().clone();
    }

    /**
     * Adds all the necessary mappings then register the camera as a listener
     */
    public void enable() {

        inputManager.addMapping("left", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping("right", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping("top", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping("bottom", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        inputManager.addMapping("esc", new KeyTrigger(KeyInput.KEY_ESCAPE));

        inputManager.addListener((ActionListener) this::onEscapePressed, "esc");
        inputManager.addListener(this, MAPPINGS);
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {

        if(inputManager.isCursorVisible()) {
            return;
        }

        switch (name) {
            case "left":
                rotateCamera(value/10, initialUp);
                break;
            case "right":
                rotateCamera(-value/10, initialUp);
                break;
            case "top":
                rotateCamera(-value/10, source.getLeft());
                break;
            case "bottom":
                rotateCamera(value/10, source.getLeft());
                break;
        }
    }

    // Code from com.jme3.input.FlyByCamera
    private void rotateCamera(float value, Vector3f axis) {

        Matrix3f mat = new Matrix3f();
        mat.fromAngleNormalAxis(sensitivity * value, axis);

        Vector3f up = source.getUp();
        Vector3f left = source.getLeft();
        Vector3f dir = source.getDirection();

        mat.mult(up, up);
        mat.mult(left, left);
        mat.mult(dir, dir);

        Quaternion q = new Quaternion();
        q.fromAxes(left, up, dir);
        q.normalizeLocal();

        source.setAxes(q);
    }

    public Camera getSource() { return source; }

    public int getSensitivity() { return sensitivity; }
    public void setSensitivity(int sensitivity) { this.sensitivity = sensitivity; }
    public int getFov() { return fov; }

    public void changeFOV(int newFov) {
        this.fov = newFov;
        source.setFrustumPerspective(newFov, (float) source.getWidth() / source.getHeight(), 0.001f, 100);
    }

    private void onEscapePressed(String name, boolean keyPressed, float tpf) {
        if(!keyPressed || !name.equals("esc")) {
            return;
        }
        inputManager.setCursorVisible(!inputManager.isCursorVisible());
    }
}