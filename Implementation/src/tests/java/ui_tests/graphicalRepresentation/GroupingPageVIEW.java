package ui_tests.graphicalRepresentation;

import GUI.Main.TestUser;
import GUI.GraphicalPages.GroupingPage;
import user.User;

import javax.swing.*;

public class GroupingPageVIEW {
    public static void main(final String args[]) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        User user = TestUser.getTestUser();
        GroupingPage page = new GroupingPage(user, frame);
        page.configureFrame(frame);
    }
}
