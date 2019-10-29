package persistency;

import java.io.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FileWriter implements Observer {
  private String fileName;

  /**
   * Saves Custom User Object to a File
   *
   * @param list List of Transactions which is going to be stored permanently
   */
  public void writeFile(List<? extends Object> list) {
    Serialize(list);
  }

  private void Serialize(
      List<? extends Object> list) { // takes Object instead of List, might wanna change to List

    try {

      FileOutputStream outputStream = new FileOutputStream(fileName);
      ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);

      objOutputStream.writeObject(list);

      outputStream.close();
      objOutputStream.close();

    } catch (Exception e) {
      // TODO EXCEPTION HANDLING
      System.exit(1); // TODO System exit!?
    }
  }

  /** calls writeFile() */
  public void update(Observable observable, Object o) {
    writeFile((List<? extends Object>) o);
  }
}
