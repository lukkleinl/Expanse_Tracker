package ui.main;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Abstract class for the User Interface
 *
 * @author Patrick Gmasz
 */

public abstract class AbstractPage {

  protected ArrayList<JComponent> components;
  protected final static int FRAME_WIDTH = 1200;
  protected final static int FRAME_HEIGHT = 800;

  /**
   * This method updates the given JFrame with all of the components.
   *
   * @param frame The JFrame, which components will be updated
   */
  public final void configureFrame(JFrame frame) {

    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    resetTitle(frame);

    createComponents();
    for (JComponent comp : components) {
      frame.add(comp);
    }

    frame.revalidate();
    frame.repaint();
  }

  /**
   * This is  method updates the given JFrame with all of the components and also sets the frame height and width with given parameters.
   *
   * @param frame The JFrame, which components will be updated
   * @param width The width of the JFrame
   * @param height The height of the JFrame
   */
  public final void configureFrame(JFrame frame, int width, int height) {

    frame.getContentPane().removeAll();
    frame.setSize(width, height);

    resetTitle(frame);

    createComponents();
    for (JComponent comp : components) {
      frame.add(comp);
    }

    frame.revalidate();
    frame.repaint();
  }

  protected abstract void resetTitle(JFrame frame);

  protected abstract void createComponents();
}
