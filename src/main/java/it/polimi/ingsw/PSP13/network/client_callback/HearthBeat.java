package it.polimi.ingsw.PSP13.network.client_callback;

import it.polimi.ingsw.PSP13.network.MessageID;

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
            MessageFromViewToController ping = new MessageFromViewToController(MessageID.gameOver, null, null,0);
            callback.send(ping);
        }
    }
}
