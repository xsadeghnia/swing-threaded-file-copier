import java.io.*;

public class FileCopier {
    private boolean stopped;
    private boolean paused;
    private String source;
    private String destination;
    private FileCopyCallback fileCopyCallback;

    public FileCopier(String source, String destination) {
        this(source , destination,null);
    }

    public FileCopier(String source, String destination, FileCopyCallback fileCopyCallback) {
        this.source = source;
        this.destination = destination;
        this.fileCopyCallback = fileCopyCallback;
    }

    public void copy(){
        try {
            if (fileCopyCallback!= null){
                fileCopyCallback.started();
            }
            InputStream inputStream = new FileInputStream(source);
            // If the file doesn't exist, create it.
            File destFile = new File(destination);
            destFile.createNewFile();
            OutputStream outputStream = new FileOutputStream(destination);
            int c = 0;
            int byteCounter = 0;
            long fileLenght = new File(source).length();
            paused = stopped = false;
            while ((c = inputStream.read()) >= 0) {
                if (stopped){
                    break;
                }
                outputStream.write(c);
                byteCounter++;
                if (fileCopyCallback!= null) {
                   fileCopyCallback.inProgress( (int)(100 * (byteCounter / (double) fileLenght)));
                }
            }

            inputStream.close();
            outputStream.close();
            if (fileCopyCallback != null){
                fileCopyCallback.finished();
            }

        } catch (IOException e) {
            if (fileCopyCallback!= null){
                fileCopyCallback.error(e);
            }
        }
    }

    public void  pause(){
        //TODO

    }

    public void stop(){
        //TODO

    }
}
