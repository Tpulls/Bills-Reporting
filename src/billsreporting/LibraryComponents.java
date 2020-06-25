/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billsreporting;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

// The LibraryComponents Class is a procedure for the positioning of program features according to SpringLayout
/**
 *
 * @author thomas
 */
public class LibraryComponents
{

    public static JLabel LocateAJLabel(JFrame myJFrame, SpringLayout myJLabelLayout, String JLabelCaption, int x, int y)
    {
        // Instantiate the JLabel
        JLabel myJLabel = new JLabel(JLabelCaption);

        // Add to screen
        myJFrame.add(myJLabel);

        // Set constraints on location
        myJLabelLayout.putConstraint(SpringLayout.WEST, myJLabel, x, SpringLayout.WEST, myJFrame);
        myJLabelLayout.putConstraint(SpringLayout.NORTH, myJLabel, y, SpringLayout.NORTH, myJFrame);

        // Return to calling method
        return myJLabel;
    }

    public static JTextField LocateAJTextField(JFrame myJFrame, KeyListener myKeyLstnr, SpringLayout myJTextFieldLayout, int width, int x, int y)
    {
        // declare the text field
        JTextField myJTextField = new JTextField(width);

        // Add the text field to the Frme
        myJFrame.add(myJTextField);

        // Add the key Listener
        myJTextField.addKeyListener(myKeyLstnr);

        // Put the constraints on the location of the JTextField
        myJTextFieldLayout.putConstraint(SpringLayout.WEST, myJTextField, x, SpringLayout.WEST, myJFrame);
        myJTextFieldLayout.putConstraint(SpringLayout.NORTH, myJTextField, y, SpringLayout.NORTH, myJFrame);

        // Return to the calling method
        return myJTextField;
    }

    public static JButton LocateAJButton(JFrame myJFrame, ActionListener myActLstnr, SpringLayout myJButtonLayout, String JButtonCaption, int x, int y, int w, int h)
    {
        // Declare the button
        JButton myJButton = new JButton(JButtonCaption);

        // Add the button to the frame
        myJFrame.add(myJButton);
        // Add the action Listener
        myJButton.addActionListener(myActLstnr);

        // Put the contraints to the Layout
        myJButtonLayout.putConstraint(SpringLayout.WEST, myJButton, x, SpringLayout.WEST, myJFrame);
        myJButtonLayout.putConstraint(SpringLayout.NORTH, myJButton, y, SpringLayout.NORTH, myJFrame);
        myJButton.setPreferredSize(new Dimension(w, h));
        // Return the JButton to the calling method
        return myJButton;
    }

    public static JTextArea LocateAJTextArea(JFrame myJFrame, SpringLayout myLayout, JTextArea myJTextArea, int x, int y, int w, int h)
    {
        myJTextArea = new JTextArea(w, h);
        myJFrame.add(myJTextArea);
        myLayout.putConstraint(SpringLayout.WEST, myJTextArea, x, SpringLayout.WEST, myJFrame);
        myLayout.putConstraint(SpringLayout.NORTH, myJTextArea, y, SpringLayout.NORTH, myJFrame);
        return myJTextArea;
    }

    public static void clearJTextFieldArray(JTextField[][] JTxtFld, int minX, int minY, int maxX, int maxY)
    {
        for (int y = minY; y < maxY; y++)
        {
            for (int x = minX; x < maxX; x++)
            {
                JTxtFld[x][y].setText("");
            }
        }
    }

    public static int getLargestIndex(int arr[])
    {
        int largestIndex = -1;
        int largestValue = -1;
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] > largestValue)
            {
                largestValue = arr[i];
                largestIndex = i;
            }
        }
        return largestIndex;
    }

    public static int getLargestValue(int arr[])
    {
        int largestValue = -1;
        for (int i = 0; i<arr.length; i++)
        {
            if(largestValue > arr[i])
            {
                largestValue = arr[i];
            }
        }
        return largestValue;
    }
    
    public static String checkInteger(String strValue)
    {
        try
        {
            Integer.parseInt(strValue);
            return strValue;
        }
        catch (Exception e)
        {
            return "0";
        }
    }
}
