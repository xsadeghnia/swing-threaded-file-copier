import javax.swing.*;
import java.awt.*;
import static javax.swing.SpringLayout.EAST;
import static javax.swing.SpringLayout.WEST;
import static javax.swing.SpringLayout.SOUTH;
import static javax.swing.SpringLayout.NORTH;

public class CopyExample {
    public static void main(String[] args) {
        new CopyFrame();

    }
}
class CopyFrame extends JFrame implements CopyCallback {

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

    public CopyFrame() {

        buildComponents();
        placecomponents();
        setVisible(true);
        sourceTextField.setText("");
        destinationTextField.setText("");

    }



    public void center(){
        setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2),
                (int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - this.getHeight()) / 2));
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

        springLayout.putConstraint(EAST, selectSourceButton,-10 , EAST,getContentPane());
        springLayout.putConstraint(NORTH,selectSourceButton,10 ,NORTH , getContentPane());


        springLayout.putConstraint(EAST, selectDestinationButton,-10 , EAST,getContentPane());
        springLayout.putConstraint(NORTH,selectDestinationButton,10 ,SOUTH , selectSourceButton);


        springLayout.putConstraint(WEST, sourceLable, 10, WEST, getContentPane());
        springLayout.putConstraint(NORTH, sourceLable, 10, NORTH, getContentPane());
        springLayout.putConstraint(WEST, sourceTextField, 10, EAST, sourceLable);
        springLayout.putConstraint(NORTH, sourceTextField, 10, NORTH, getContentPane());
        springLayout.putConstraint(EAST, sourceTextField, -10, WEST, selectSourceButton);


        springLayout.putConstraint(WEST, destinationlable, 10, WEST, getContentPane());
        springLayout.putConstraint(NORTH, destinationlable, 10, SOUTH, sourceTextField);
        springLayout.putConstraint(WEST, destinationTextField, 10, EAST, destinationlable);
        springLayout.putConstraint(NORTH, destinationTextField, 10, SOUTH, sourceLable);
        springLayout.putConstraint(EAST, destinationTextField, -10, WEST,selectDestinationButton );

        springLayout.putConstraint(WEST,progressBar,10, WEST, getContentPane());
        springLayout.putConstraint(EAST,progressBar,-10, EAST, getContentPane());
        springLayout.putConstraint(NORTH,progressBar,10, SOUTH, selectDestinationButton);

        springLayout.putConstraint(EAST,copyButton, -10,EAST, getContentPane());
        springLayout.putConstraint(SOUTH,copyButton, -10,SOUTH, getContentPane());

        springLayout.putConstraint(EAST,pauseButton, -10, WEST, copyButton);
        springLayout.putConstraint(SOUTH,pauseButton, -10, SOUTH, getContentPane());

        springLayout.putConstraint(EAST, stopButton, -10, WEST, pauseButton);
        springLayout.putConstraint(SOUTH, stopButton, -10, SOUTH, getContentPane());



    }

    @Override
    public void copyStarted() {

    }

    @Override
    public void copyFinished() {

    }

    @Override
    public void copyProgress(int progress) {

    }

    @Override
    public void copyException(Exception e) {

    }
}

interface CopyCallback{

    void copyStarted();

    void copyFinished();

    void copyProgress(int progress);

    void copyException(Exception e);
}

class CopyThread implements Runnable{

    private CopyCallback copyCallback;

    public CopyCallback getCopyCallback() {
        return copyCallback;
    }

    public void setCopyCallback(CopyCallback copyCallback) {
        this.copyCallback = copyCallback;
    }

    @Override
    public void run() {

        if (copyCallback != null){
            copyCallback.copyStarted();

            while (true){
                try {
                    if (copyCallback != null) {
                        copyCallback.copyProgress(0);
                    }
                }catch (Exception e){
                    if (copyCallback != null) {
                        copyCallback.copyException(e);
                    } else
                        throw new RuntimeException(e);


                }

            }
        }
        if (copyCallback != null){
            copyCallback.copyFinished();
        }
    }
}
