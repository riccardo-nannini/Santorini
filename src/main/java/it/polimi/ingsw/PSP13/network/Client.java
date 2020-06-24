package it.polimi.ingsw.PSP13.network;

import it.polimi.ingsw.PSP13.view.CLI.CliInput;
import it.polimi.ingsw.PSP13.view.GUI.Main;
import it.polimi.ingsw.PSP13.view.Input;
import javafx.application.Application;

import java.util.Scanner;

public class Client {

    public static final int PORT=7777;
    private static Scanner scanner = new Scanner(System.in);
    private static Input input;

    public static void main(String[] args) {

        System.out.println("Choose the graphic mode (insert \u001B[1mGUI\u001B[0m or \u001B[1mCLI\u001B[0m):");
        String answer = scanner.nextLine();

        while(!(answer.toLowerCase().equals("cli")|| answer.toLowerCase().equals("gui")))
        {
            System.out.println("\u001B[31mWRONG INPUT, type [gui/cli]\u001B[0m:");
            answer = scanner.nextLine();
        }

        if(answer.toLowerCase().equals("cli")){
            input = new CliInput();
            input.setup();
        }
        else {
            Application.launch(Main.class);
        }
    }

}
