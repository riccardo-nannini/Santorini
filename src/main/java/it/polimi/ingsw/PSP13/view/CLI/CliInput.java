package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.view.Input;
import it.polimi.ingsw.PSP13.view.ObservableToController;

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
    public void godSelectionInput(String challenger, List<String> chosenGods, int godsNumber, boolean error)
    {
        String input;
        String pattern = "([a-zA-Z]{3,} *, *){"+(godsNumber-1)+"}([a-zA-Z]{3,})";

        if(error)
            System.out.println("There was and error with you last selection, please repeat");

        Pattern p = Pattern.compile("([a-zA-Z]{3,} *, *)+([a-zA-Z]{3,})");
        System.out.println(challenger + ", please select "+ godsNumber +" Gods for this match.");
        System.out.println("This is the list of all the available gods you can choose from:");
        System.out.print(chosenGods.get(0));
        for(int i=1;i<chosenGods.size();i++)
            System.out.print(", " + chosenGods.get(i));
        System.out.println("Type the name of the gods you choose separated by a comma (e.g. Zeus, Athena, Apollo)");
        input = scanner.nextLine();
        while(!p.matcher(input).matches())
        {
            System.out.println("Wrong format, correct is: Zeus, Athena, Apollo");
            input = scanner.nextLine();
        }

        super.controller.notifyGodSelection(input);
    }


    @Override
    public void godInput(String player, List<String> chosenGods, boolean error)
    {
        Pattern p = Pattern.compile("[a-zA-Z]{3,}");

        if(error)
            System.out.println("There was and error with you last selection, please repeat");

        System.out.println(player + ", choose your God:");
        System.out.println("This is the list of the available gods you can choose from for this match:");
        System.out.print(chosenGods.get(0));
        for(int i=1;i<chosenGods.size();i++)
            System.out.print(", " + chosenGods.get(i));
        System.out.print("Choose your god: ");
        input = scanner.nextLine();
        while(!p.matcher(input).matches())
        {
            System.out.println("wrong input, choose your god");
            input = scanner.nextLine();
        }

        super.controller.notifyGod(input);
    }


    @Override
    public void builderSetUpInput(String player, boolean callNumber, boolean error)
    {
        Coords coords;

        MapPrinter.printMap();
        if(error)
            System.out.println("You can't place your builders there, choose again the positions.");
        System.out.println(player + ", place your builder on the map and type the position in the format *row*,*column*:");

        if(!callNumber)
            System.out.println("Choose the position of your first builder:");
        else
            System.out.println("Choose the position of your second builder:");
        coords = readCoords();
        super.controller.notifySetupBuilder(coords);

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

    /**
     * asks the player to choose a build
     * @return a Coords class based on user input
     */
    private Coords chooseBuilder(String player)
    {
        System.out.println(player + ", select a builder. Type the coordinates of your builder in the format *row*,*column*:");
        return readCoords();
    }

    @Override
    public void moveInput(String player, List<Coords> checkMoveCells, boolean error)
    {
        if(error)
            System.out.println("There was an error with your last selection");

        System.out.println(player + ", it is your turn now. You have to move a builder");
        //chiamata alla funzione che manda la lista delle celle da illuminare
        controller.notifyBuilderChoice(chooseBuilder(player));
        //chiamata alla funzione che stampa la mappa con le celle illuminate

        System.out.println("You can choose a cell to build on only from the highlighted cells, type the cell coordinates in the format *row*,*column*:");
        Coords coords = readCoords();

        controller.notifyMoveInput(coords);

    }

    @Override
    public void buildInput(String player, List<Coords> checkBuildCells)
    {
        Coords builderCoords;
        System.out.println(player + ", it is your turn now. You have to build on a cell");
        builderCoords = chooseBuilder(player);


        //chiamata alla funzione che illumina le celle

        System.out.println("You can move your builder only on the highlighted cells, type the arrival position in the format *row*,*column*:");
        Coords coords = readCoords();

        controller.notifyBuildInput(coords);
    }

}
