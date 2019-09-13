public interface Computer {

    enum State {ON, OFF}

    int getScreenSize();

    int getRAM();

    int getProcessorSpeeed();

    State getState();

    void setState(String to);

    void installGame(String gameName);

    boolean hasGame(String gameName);

    void playGame(String gameName);

}
