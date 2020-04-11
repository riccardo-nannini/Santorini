package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.model.board.Cell;
import it.polimi.ingsw.PSP13.model.board.Level;
import it.polimi.ingsw.PSP13.model.player.Builder;
import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.view.Immutables.CellView;
import it.polimi.ingsw.PSP13.view.Input;
import it.polimi.ingsw.PSP13.view.ObservableToController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CliInput extends Input {

    private Scanner scanner;
    private String input;

    public CliInput(ObservableToController controller) {
        super(controller);
        scanner = new Scanner(System.in);
    }


    @Override
    public void nicknameInput(boolean error)
    {
        if(!error)
            System.out.println("Insert your nickname:");
        else
            System.out.println("The nickname you have chosen is not available for this match, please insert another nickname:");
        input = scanner.nextLine();

        super.controller.notifyNickname(input);
    }


    @Override
    public void godSelectionInput(String challenger, List<String> chosenGods, int godsNumber)
    {
        String input;
        Pattern p = Pattern.compile("([a-zA-Z]{3,}, )+[a-zA-Z]{3,}");
        System.out.println(challenger + ", please select the Gods for this match.");
        System.out.println("This is the list of all the available gods you can choose from:");
        for(String god : chosenGods)
            System.out.println(god + ", ");
        System.out.println("type the name of the gods you choose separated by a comma (e.g. Zeus, Athena, Apollo)");
        input = scanner.nextLine();
        while(!p.matcher(input).matches())
        {
            System.out.println("wrong format, correct is: Zeus, Athena, Apollo");
            input = scanner.nextLine();
        }

        super.controller.notifyGodSelection(input);
    }


    @Override
    public void godInput(String player, List<String> chosenGods)
    {
        Pattern p = Pattern.compile("[a-zA-Z]{3,}");

        System.out.println(player + ", choose your God:");
        System.out.println("This is the list of the available gods you can choose from for this match:");
        for(String god : chosenGods)
            System.out.println(god + ", ");
        System.out.println("Choose your god: ");
        input = scanner.nextLine();
        while(!p.matcher(input).matches())
        {
            System.out.println("wrong input, choose your god");
            input = scanner.nextLine();
        }

        super.controller.notifyGod(input);
    }


    @Override
    public void builderSetUpInput(String player)
    {
        Coords[] coords = new Coords[2];

        MapPrinter.printMap();
        System.out.println(player + ", place your builder on the map and type the position in the format *row*,*column*:");
        System.out.println("Choose the position of your first builder:");
        coords[0] = readCoords();
        super.controller.notifySetupBuilder(coords[0]);

        System.out.println("Choose the position of your second builder:");
        coords[1] = readCoords();
        super.controller.notifySetupBuilder(coords[1]);
    }


    /**
     * reads an input from console that is in a certain format
     * @return a Coords class based on user input
     */
    private Coords readCoords()
    {
        input = scanner.nextLine();
        Pattern p = Pattern.compile("\\d,\\d");

        while(!p.matcher(input).matches())
        {
            System.out.println("Wrong format, correct is: x,y");
            input = scanner.nextLine();
        }

        String[] dissection = input.split(",");
        int x = Integer.parseInt(dissection[0]);
        int y = Integer.parseInt(dissection[1]);
        return new Coords(x,y);
    }


}
