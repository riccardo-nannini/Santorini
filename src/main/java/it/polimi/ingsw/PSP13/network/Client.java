package it.polimi.ingsw.PSP13.network;

import it.polimi.ingsw.PSP13.view.CLI.CliInput;
import it.polimi.ingsw.PSP13.view.GUI.Main;
import it.polimi.ingsw.PSP13.view.Input;
import javafx.application.Application;

import java.util.Scanner;

public class Client {

    public static int PORT=7777;
    private static Scanner scanner = new Scanner(System.in);
    private static Input input;

    /**
     * Client main thread, starts with GUI by default
     * @param args eventually starts the CLI
     */
    public static void main(String[] args) {

        if (args.length > 0) {
            if (args[0].equals("--cli") || args[0].equals("-c")) {
                input = new CliInput();
                input.setup();
            }
        } else {
            Application.launch(Main.class);
            System.exit(0);
        }
    }

}
