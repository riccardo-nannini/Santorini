package it.polimi.ingsw.PSP13.view.CLI;

import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.view.Input;
import it.polimi.ingsw.PSP13.view.ObservableToController;

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
    public void getNickname()
    {
        System.out.println("Insert your nickname:");
        input = scanner.nextLine();

        super.controller.notifyNickname(input);
    }


    @Override
    public void getGodSelection()
    {
        System.out.println("Challenger, select the Gods for this match:");
        String[] input = new String[0];

        //chiamo dal controller un metodo che mi ritorna la lista di stringhe coi nomi degli dei
        //stampo a video la lista
        //chiedo all'utente di scrivere uno alla volta il nome degli dei


        super.controller.notifyGodSelection(input);
    }


    @Override
    public void getGod()
    {
        System.out.println("Choose your God:");
        input = scanner.nextLine();

        //devo ricevere la lista degli dei possibili
        //stampo la lista
        //l'utente scrive un nome

        super.controller.notifyGod(input);
    }


    @Override
    public void setupBuilder()
    {
        Coords[] coords = new Coords[2];

        //devo ricevere eventuali restrizioni dal controller che invio il setup di Turn

        System.out.println("Choose the position of your first builder:");
        coords[0] = readCoords();


        System.out.println("Choose the position of your second builder:");
        coords[1] = readCoords();

        super.controller.notifySetupBuilder(coords[0], coords[1]);
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
