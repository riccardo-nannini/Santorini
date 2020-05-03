package it.polimi.ingsw.PSP13.network.client_callback;

public class HearthBeat implements Runnable {

    private ControllerCallback callback;

    public HearthBeat(ControllerCallback callback) {
        this.callback =callback;
    }
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MessageVC ping = new MessageVC(9, null, null,0);
            callback.send(ping);
        }
    }
}
