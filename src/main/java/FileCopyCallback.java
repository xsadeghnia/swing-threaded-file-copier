public interface FileCopyCallback {

    void started();

    void finished();

    void inProgress(int percentage);

    void error(Exception e);
}
