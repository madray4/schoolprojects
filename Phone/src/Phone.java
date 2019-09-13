public interface Phone {

    String getOwner();

    void call(Phone phone);

    void end();

    void receive(Phone from);

    boolean isBusy();

    void receiveEndSignal(Phone from);

    PhoneNumber number();

}
