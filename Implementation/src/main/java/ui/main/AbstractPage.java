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
  protected final static Font HEADER_FONT = new Font("Serif", Font.CENTER_BASELINE,35);

  /**
   * This method updates the given JFrame with all of the components.
   *
   * @param frame The JFrame, which components will be updated
   */
  public final void configureFrame(JFrame frame){

    frame.getContentPane().removeAll();
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    resetTitle(frame);


    try {
      createComponents();
    }catch (Exception e){
      System.out.println("Fehler erkannt, noch kein Error Handling vorhanden:"+e.getMessage());
    }



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


    try {
      createComponents();
    }catch (Exception e){
      System.out.println("Fehler erkannt, noch kein Error Handling vorhanden:"+e.getMessage());
    }


    for (JComponent comp : components) {
      frame.add(comp);
    }

    frame.revalidate();
    frame.repaint();
  }

  protected abstract void resetTitle(JFrame frame);

  protected abstract void createComponents();
}
