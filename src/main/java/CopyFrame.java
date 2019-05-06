import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SpringLayout.EAST;
import static javax.swing.SpringLayout.WEST;
import static javax.swing.SpringLayout.SOUTH;
import static javax.swing.SpringLayout.NORTH;



class CopyFrame extends JFrame {

    private JLabel sourceLable;
    private JLabel destinationlable;
    private JTextField sourceTextField;
    private JTextField destinationTextField;
    private JProgressBar progressBar;
    private JButton selectSourceButton;
    private JButton selectDestinationButton;
    private JButton copyButton;
    private JButton pauseButton;
    private JButton stopButton;
    private FileCopier fileCopier;

    public CopyFrame() {

        buildComponents();
        placecomponents();
        setVisible(true);
        assignHandlers();
        sourceTextField.setText("");
        destinationTextField.setText("");

    }


    public void center() {
        setLocation((int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2),
                (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getHeight()) / 2));
    }

    private void buildComponents() {
        setSize(400, 180);
        setTitle("Copy Frame");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        center();
        sourceLable = new JLabel("Source");
        destinationlable = new JLabel("Destination");
        sourceTextField = new JTextField();
        destinationTextField = new JTextField();
        progressBar = new JProgressBar();
        progressBar.setMaximum(100);
        progressBar.setMinimum(0);
        progressBar.setStringPainted(true);
        selectSourceButton = new JButton("...");
        selectDestinationButton = new JButton("...");
        copyButton = new JButton("Copy");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("stop");
    }

    private void placecomponents() {

        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);
        getContentPane().add(sourceLable);
        getContentPane().add(destinationlable);
        getContentPane().add(sourceTextField);
        getContentPane().add(destinationTextField);
        getContentPane().add(progressBar);
        getContentPane().add(selectSourceButton);
        getContentPane().add(selectDestinationButton);
        getContentPane().add(copyButton);
        getContentPane().add(pauseButton);
        getContentPane().add(stopButton);

        springLayout.putConstraint(EAST, selectSourceButton, -10, EAST, getContentPane());
        springLayout.putConstraint(NORTH, selectSourceButton, 10, NORTH, getContentPane());


        springLayout.putConstraint(EAST, selectDestinationButton, -10, EAST, getContentPane());
        springLayout.putConstraint(NORTH, selectDestinationButton, 10, SOUTH, selectSourceButton);


        springLayout.putConstraint(WEST, sourceLable, 10, WEST, getContentPane());
        springLayout.putConstraint(NORTH, sourceLable, 10, NORTH, getContentPane());
        springLayout.putConstraint(WEST, sourceTextField, 10, EAST, sourceLable);
        springLayout.putConstraint(NORTH, sourceTextField, 10, NORTH, getContentPane());
        springLayout.putConstraint(EAST, sourceTextField, -10, WEST, selectSourceButton);


        springLayout.putConstraint(WEST, destinationlable, 10, WEST, getContentPane());
        springLayout.putConstraint(NORTH, destinationlable, 10, SOUTH, sourceTextField);
        springLayout.putConstraint(WEST, destinationTextField, 10, EAST, destinationlable);
        springLayout.putConstraint(NORTH, destinationTextField, 10, SOUTH, sourceLable);
        springLayout.putConstraint(EAST, destinationTextField, -10, WEST, selectDestinationButton);

        springLayout.putConstraint(WEST, progressBar, 10, WEST, getContentPane());
        springLayout.putConstraint(EAST, progressBar, -10, EAST, getContentPane());
        springLayout.putConstraint(NORTH, progressBar, 10, SOUTH, selectDestinationButton);

        springLayout.putConstraint(EAST, copyButton, -10, EAST, getContentPane());
        springLayout.putConstraint(SOUTH, copyButton, -10, SOUTH, getContentPane());

        springLayout.putConstraint(EAST, pauseButton, -10, WEST, copyButton);
        springLayout.putConstraint(SOUTH, pauseButton, -10, SOUTH, getContentPane());

        springLayout.putConstraint(EAST, stopButton, -10, WEST, pauseButton);
        springLayout.putConstraint(SOUTH, stopButton, -10, SOUTH, getContentPane());


    }

    private void assignHandlers() {
        selectSourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("/home/afsaneh");
                if (fileChooser.showDialog(CopyFrame.this, "Select file") == JFileChooser.APPROVE_OPTION) {
                    sourceTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        selectDestinationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("/home/afsaneh");
                if (fileChooser.showDialog(CopyFrame.this, "Select file") == JFileChooser.APPROVE_OPTION) {
                    destinationTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        fileCopier = new FileCopier(sourceTextField.getText(), destinationTextField.getText(), new FileCopyCallback() {
                            @Override
                            public void started() {
                                progressBar.setValue(0);
                            }

                            @Override
                            public void finished() {
                                progressBar.setValue(100);
                            }

                            @Override
                            public void inProgress(int percentage) {
                                progressBar.setValue(percentage);
                            }

                            @Override
                            public void error(Exception e) {
                                JOptionPane.showMessageDialog(CopyFrame.this, e.getMessage(), "Error!", 0, null);
                                progressBar.setValue(0);

                            }
                        });
                    fileCopier.copy();


                    }
                }).start();
            }

        });
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileCopier.pause();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileCopier.stop();
            }
        });
    }
}


