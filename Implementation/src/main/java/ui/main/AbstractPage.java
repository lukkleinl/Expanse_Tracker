package ui.main;

import javax.swing.*;
import java.awt.*;
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
  protected final static Font LABEL_FONT = new Font("Serif", Font.BOLD, 25);
  protected final static Font TEXTFIELD_FONT = new Font("Serif", Font.PLAIN, 20);
  protected final static Font HEADER_FONT = new Font("Serif", Font.BOLD,19);
  protected final static Font BUTTON_FONT = new Font("Serif", Font.BOLD, 20);

  /**
   * This method updates the given JFrame with all of the components.
   *
   * @param frame The JFrame, which components will be updated
   */
  public final void configureFrame(JFrame frame){

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

  protected abstract void resetTitle(JFrame frame);

  protected abstract void createComponents();
}
