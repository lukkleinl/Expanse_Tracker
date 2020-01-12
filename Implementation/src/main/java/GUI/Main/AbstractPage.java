package GUI.Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Abstract class for the User Interface
 *
 * @author Patrick Gmasz
 *
 */
public abstract class AbstractPage {

  protected ArrayList<JComponent> components;
  protected static final int FRAME_WIDTH = 1200;
  protected static final int FRAME_HEIGHT = 800;
  protected static final Font LABEL_FONT = new Font("Serif", Font.BOLD, 25);
  protected static final Font TEXTFIELD_FONT = new Font("Serif", Font.PLAIN, 20);
  protected static final Font HEADER_FONT = new Font("Serif", Font.BOLD, 19);
  protected static final Font BUTTON_FONT = new Font("Serif", Font.BOLD, 20);

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
   * Abstract method to reset the title.
   *
   * @param frame The JFrame, where to reset the title.
   */
  protected abstract void resetTitle(JFrame frame);

  /**
   * Abstract method to create the components of the UI page.
   */
  protected abstract void createComponents();
}

