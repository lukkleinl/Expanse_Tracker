package ui;

import MongoDb.MongoDB;
import javax.swing.JComponent;
import javax.swing.JFrame;
import user.User;

public class UserInterfaceMain {

  public static void main(String[] args) {

    User user = null;
    MongoDB db = new MongoDB();


    LoginPage loginPage = new LoginPage();
    RegistrationPage registrationPage = new RegistrationPage();

    JFrame frame = new JFrame();
    frame.setTitle("Login");
    frame.setSize(loginPage.FRAME_WIDTH, loginPage.FRAME_HEIGHT);
    frame.setLayout(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    for(JComponent comp : loginPage.getComponents()) {
      frame.add(comp);
    }

    frame.validate();
    frame.setVisible(true);

    // PAUL

    AccountTypePage accp = new AccountTypePage();
    frame = accp.configure_frame(frame);

    boolean test = true;
    while(test){

    }


    // PAUL END



    boolean foundUser = false;

    while(!foundUser) {
      //TODO: check if user exists and if password is correct
      if(loginPage.getUser().equals("admin") && loginPage.getPassword().equals("admin")) {
        //TODO: user = db.getUser(userid)
        foundUser = true;
      }
      else if(loginPage.isRegistrationWanted()) {

        //OPEN REGISTRATION PAGE COMPONENTS
        frame.setVisible(false);
        frame.setTitle("Registration");
        frame.getContentPane().removeAll();
        frame.setSize(registrationPage.FRAME_WIDTH, registrationPage.FRAME_HEIGHT);

        for(JComponent comp : registrationPage.getComponents()) {
          frame.add(comp);
        }
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);

        boolean registrationFinished = false;

        while(!registrationFinished) {
          if(registrationPage.isRegistrationComplete()) {
            //TODO: save userdata here
            registrationFinished = true;
            loginPage.registrationFinished();

            try {
              Thread.sleep(1000);
            } catch (Exception e) {
              e.printStackTrace();
            }

            //OPEN LOGIN PAGE COMPONENTS AGAIN
            frame.setVisible(false);
            frame.setTitle("Login");
            frame.getContentPane().removeAll();
            frame.setSize(loginPage.FRAME_WIDTH, loginPage.FRAME_HEIGHT);

            for(JComponent comp: loginPage.getComponents()) {
              frame.add(comp);
            }
            frame.revalidate();
            frame.repaint();
            frame.setVisible(true);
          }
          try {
            Thread.sleep(1);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
      try {
        Thread.sleep(1);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    frame.setVisible(false);
    System.exit(1);

  }

}
