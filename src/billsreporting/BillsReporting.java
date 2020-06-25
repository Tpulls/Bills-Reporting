/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billsreporting;

/**
 *
 * @author thomas
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class BillsReporting extends JFrame implements ActionListener, KeyListener {

    private int totalX = 24;
    private int totalY = 23;
    private JTextField[][] fields;
    int xPos = 0;
    int yPos = 0;
    String[] sortArray;

    private JButton btnSave, btnSaveTable, btnClear, btnFind, btnRAF, btnExit, btnSort;
    private JTextField txtFind;

    private String[] columnHeadings
            = {
                "Bills Reporting", "Answers", "Jane", "Tony", "Ahmed", "Alisha", "Peta", "Jenny", "Hanna", "James", "Terri", "Tom", "Cian",
                "Han", "Mark", "Sian", "Harry", "Ana", "Makin", "Sarah", "Terese", "John", "Mode"
            };

    private String[] rowHeadings
            = {
                "Qns", "Q1", "Q2", "Q3", "Q4", "Q5", "Q6", "Q7", "Q8", "Q9", "Q10", "Result"
            };

    private String dataFileName = "BillsReportingSystem.csv";
    private String tableFileName = "BillsReportingTable.csv";
    private String rafFileName = "BillsReportingRAF.csv";

    // Create a copy of the application and run
    // declare and instantiate the interface
    // declare and instantiate the following: JButton, JTextField, JTextArea, setupTable
    // For each method above: Declare the method, instanties the method, add the method to the interface and add required functions
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        BillsReporting billsApplication = new BillsReporting();
        billsApplication.run();
    }

    private void run() {
        // Declare and instantiate the DisplayGUI interface
        // Declare and instantiate the setFieldProperties
        // Declare and instantiate the setButtonProperties
        // Declare and instantiate the interface
        getScreenDimensions(dataFileName);
        fields = new JTextField[totalX][totalY];
        sortArray = new String[(totalY - 3)];

        setBounds(10, 10, xPos, yPos);
        setTitle("Bills Reporting System");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        DisplayGUI();
        ReadDataFile(dataFileName);
        CalculateStudentResults();
        CalculateStudentAverage();
        CalculateQuestionMode();

        setVisible(true);
        setResizable(false);
    }

    private void getScreenDimensions(String fileName) {
        try {
            int count = 0;
            String line;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                String temp[] = line.split(",");
                count++;
                totalX = temp.length + 1;
            }
            totalY = count + 2;
            xPos = totalX * 65 + 50;
            if (xPos < 825) {
                xPos = 825;
            }
            yPos = totalY * 22 + 120;

            br.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void DisplayGUI() {
        // Handles the display of the program elements: Label, textField, button, textArea
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        displayTextFields(springLayout);
        displayButtons(springLayout);
        displayLabel(springLayout);
        setupTable(springLayout);

    }

    private void displayTextFields(SpringLayout layout) {
        // Enter the details of the textFields according to the LibraryComponents procedure.
        for (int y = 0; y < totalY; y++) {
            for (int x = 0; x < totalX; x++) {
                int xPos = x * 65 + 20;
                int yPos = y * 22 + 20;
                fields[x][y] = LibraryComponents.LocateAJTextField(this, this, layout, 5, xPos, yPos);
            }
        }

    }

    private void displayButtons(SpringLayout layout) {
        int yPos = totalY * 22 + 40;
        int xTension = 0;
        if (totalX > 12) {
            xTension = ((totalX - 12) * 65);
        }
        // Enter the details of the buttons according to the LibraryComponents procedure.
        btnSave = LibraryComponents.LocateAJButton(this, this, layout, "Save", 220, yPos, 80, 25);
        btnSaveTable = LibraryComponents.LocateAJButton(this, this, layout, "Save Table", 100, yPos, 120, 25);
        btnClear = LibraryComponents.LocateAJButton(this, this, layout, "Clear", 20, yPos, 80, 25);
        btnFind = LibraryComponents.LocateAJButton(this, this, layout, "Find", 555 + xTension, yPos, 80, 25);
        btnExit = LibraryComponents.LocateAJButton(this, this, layout, "Exit", 715 + xTension, yPos, 80, 25);
        btnSort = LibraryComponents.LocateAJButton(this, this, layout, "Sort", 300, yPos, 80, 25);
        btnRAF = LibraryComponents.LocateAJButton(this, this, layout, "RAF", 635 + xTension, yPos, 80, 25);
        txtFind = LibraryComponents.LocateAJTextField(this, this, layout, 13, 405 + xTension, yPos + 4);
        txtFind.addActionListener(this);
    }

    private void displayLabel(SpringLayout layout) {

    }

    private void setupTable(SpringLayout layout) {
        // handle the properties of the textFields in this method
        for (int y = 0; y < totalY; y++) {
            fields[0][y].setText(columnHeadings[y]);
            setFieldProperties(0, y, false, 213, 162, 104);
            setFieldProperties(totalX - 1, y, false, 122, 239, 101);
        }

        for (int x = 0; x < totalX; x++) {
            fields[x][0].setText(rowHeadings[x]);
            setFieldProperties(x, 0, false, 117, 166, 219);
            setFieldProperties(x, 1, false, 220, 220, 255);
        }
        setFieldProperties(totalX - 1, 0, false, 122, 239, 101);
        setFieldProperties(totalX - 1, 1, false, 122, 239, 101);
    }

    private void setFieldProperties(int x, int y, boolean editable, int r, int g, int b) {
        fields[x][y].setEditable(editable);
        fields[x][y].setBackground(new Color(r, g, b));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClear) {
            LibraryComponents.clearJTextFieldArray(fields, 2, 1, totalX, totalY);
        }
        if (e.getSource() == btnSaveTable) {
            saveTableToFile(tableFileName);
        }
        if (e.getSource() == btnSave) {
            writeDataFile(dataFileName);
        }

        if (e.getSource() == btnSort) {
            sortStudentRecords();
        }

        if (e.getSource() == btnFind || e.getSource() == txtFind) {
            findStudentRecord();
        }

        if (e.getSource() == btnRAF) {
            writeRandomAccessFile(rafFileName);
            int requiredEntry = Integer.parseInt(LibraryComponents.checkInteger(txtFind.getText()));
            readRandomAcessFile(rafFileName, requiredEntry);
        }

        if (e.getSource() == btnExit) {
            System.exit(0);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        CalculateStudentResults();
        CalculateStudentAverage();
        CalculateQuestionMode();
    }

    public void ReadDataFile(String fileName) {
        try {
            // Open a BufferedReader ready for data input
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            // Loop through each data line and place the corresponding data in a temp array

            for (int y = 1; y < totalY; y++) {
                String temp[] = br.readLine().split(",");
                for (int x = 1; x < totalX - 1; x++) {
                    //Set the fields according to the temp array
                    fields[x][y].setText(temp[x]);
                }
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    private void CalculateStudentResults() {
        int total = 0;

        for (int y = 2; y < totalY - 1; y++) {
            for (int x = 1; x < totalX - 1; x++) {
                if (fields[x][1].getText().equals(fields[x][y].getText())) {
                    total++;
                }
            }
            fields[totalX - 1][y].setText("" + total);
            total = 0;
        }
    }

    private void CalculateStudentAverage() {
        int total = 0;

        for (int y = 2; y < totalY - 1; y++) {
            total = total + Integer.parseInt(fields[totalX - 1][y].getText());
        }
        fields[totalX - 1][totalY - 1].setText("" + String.format("%.2f", ((double) total / (double) (totalY - 3))));
    }

    private void CalculateQuestionMode() {
        int[] qnCount = new int[4];

        for (int x = 1; x < totalX - 1; x++) {
            for (int i = 0; i < 4; i++) {
                qnCount[i] = 0;
            }
            for (int y = 2; y < totalY - 1; y++) {
                if (fields[x][y].getText().equals("A")) {
                    qnCount[0]++;
                }
                if (fields[x][y].getText().equals("B")) {
                    qnCount[1]++;
                }
                if (fields[x][y].getText().equals("C")) {
                    qnCount[2]++;
                }
                if (fields[x][y].getText().equals("D")) {
                    qnCount[3]++;
                }
            }
            int index = LibraryComponents.getLargestIndex(qnCount);

            if (index == 0) {
                fields[x][totalY - 1].setText("A");
            }
            if (index == 1) {
                fields[x][totalY - 1].setText("B");
            }
            if (index == 2) {
                fields[x][totalY - 1].setText("C");
            }
            if (index == 3) {
                fields[x][totalY - 1].setText("D");
            }
        }
    }

    public void sortStudentRecords() {

        // Copy information to a temporary array
        copyToSortTable();
        // Sort the data in the temporary array
        sortTheSortTable();
        // Populate the grid with the sorted information
        displaySortedTable();
    }

    public void copyToSortTable() {
        for (int y = 2; y < totalY - 1; y++) {
            sortArray[y - 2] = "";
            for (int x = 0; x < totalX - 1; x++) {
                sortArray[y - 2] = sortArray[y - 2] + fields[x][y].getText() + ",";
            }
            sortArray[y - 2] = sortArray[y - 2] + fields[totalX - 1][y].getText();
        }
    }

    public void sortTheSortTable() {
        Arrays.sort(sortArray);
    }

    public void displaySortedTable() {
        for (int y = 2; y < totalY - 1; y++) {
            String temp[] = sortArray[y - 2].split(",");
            for (int x = 0; x < totalX; x++) {
                fields[x][y].setText(temp[x]);
            }
        }
    }

    private void saveTableToFile(String fileName) {
        try {
            BufferedWriter outFile = new BufferedWriter(new FileWriter(fileName));
            for (int y = 0; y < totalY; y++) {
                for (int x = 0; x < totalX - 1; x++) {
                    outFile.write(fields[x][y].getText() + ",");
                }
                outFile.write(fields[totalX - 1][y].getText());
                outFile.newLine();
            }
            outFile.close();
            System.out.println("Bills Reporting system TABLE has been saved");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void writeDataFile(String fileName) {
        try {
            BufferedWriter outFile = new BufferedWriter(new FileWriter("BillsReportingSystem_NEW.csv"));
            for (int y = 1; y < totalY - 1; y++) {
                for (int x = 0; x < totalX - 2; x++) {
                    outFile.write(fields[x][y].getText() + ",");
                }
                outFile.write(fields[totalX - 2][y].getText());
                outFile.newLine();
            }
            outFile.close();
            System.out.println("Bills Reporting System data has been saved");
        } catch (Exception e) {
            System.err.println("Error : " + e.getMessage());
        }
    }

    private void findStudentRecord() {
        boolean found = false;
        int y = 2;
        String strFind = txtFind.getText();

        while (y < totalY - 1 && found == false) {
            if (fields[0][y].getText().equalsIgnoreCase(strFind)) {
                found = true;
            }
            y++;
        }
        if (found) {
            for (int x = 0; x < totalX; x++) {
                fields[x][y - 1].setBackground(new Color(255, 217, 200));
            }
            txtFind.setText(txtFind.getText() + "...Found.");
        } else {
            txtFind.setText(txtFind.getText() + "...Not Found.");
        }
    }

    public void writeRandomAccessFile(String fileName) {
        try {
            String str;
            RandomAccessFile rafFile = new RandomAccessFile(fileName, "rw");
            for (int y = 1; y < totalY - 1; y++) {

                str = "";
                for (int x = 0; x < totalX - 1; x++) {
                    str = str + fields[x][y].getText();
                }
                rafFile.writeUTF(str);
            }
            rafFile.close();
            System.out.println("Bills Reporting System RAD data has been saved.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void readRandomAcessFile(String fileName, int index) {
        try {
            String str = "";
            RandomAccessFile rafFile = new RandomAccessFile(fileName, "rw");
            for (int i = 0; i < index; i++) {
                str = rafFile.readUTF();
            }
            System.out.println(str);
            rafFile.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}
