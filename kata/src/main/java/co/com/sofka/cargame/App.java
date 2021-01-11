package co.com.sofka.cargame;

import co.com.sofka.cargame.domain.juego.Juego;
import co.com.sofka.cargame.domain.juego.Jugador;
import co.com.sofka.cargame.service.JugadoresService;
import co.com.sofka.cargame.model.Jugadores;
import co.com.sofka.cargame.domain.carro.Conductor;
import co.com.sofka.cargame.domain.carro.Carro;
import co.com.sofka.cargame.domain.juego.Color;
import co.com.sofka.cargame.domain.carril.Carril;
import co.com.sofka.cargame.domain.carril.values.Posicion;
import co.com.sofka.cargame.domain.juego.values.Pista;

import java.util.Map;


public class App {

    public void startGame(){

        // Game Preparation.
        // Create database conection
        JugadoresService baseDeDatos = new JugadoresService();

        //Ask for number of players.
        System.out.println("Please, insert the number of players");
        int numberOfPlayers = 0;

        //Validate the user input.
        Boolean validatorNumberOfPlayers = false;
        while(!validatorNumberOfPlayers){
            try{
                numberOfPlayers = Integer.parseInt(System.console().readLine());
                if(numberOfPlayers > 0){
                    validatorNumberOfPlayers = true;
                }
                else{
                    System.out.println("Please, insert a positive integer");
                }
    
            }
            catch(Exception e){
                System.out.println("Please, insert a positive integer");
            }
        }

        //Ask for size of the track.
        System.out.println("Please, insert the size in km of the track");
        int kilometers = 0;
        
        //Validate user input
        Boolean validatorKilometers = false;
        while(!validatorKilometers){
            try{
                kilometers = Integer.parseInt(System.console().readLine());
                if(kilometers > 0){
                    validatorKilometers = true;
                }
                else{
                    System.out.println("Please, insert a positive integer");
                }
            }
            catch(Exception e){
                System.out.println("Please, insert a positive integer");
            }
        }
        
        //Create a juego object.
        Pista pista = new Pista(kilometers, numberOfPlayers);
        Juego juego = new Juego(pista);

        //Generate arrays for carriles.
        Carril[] carriles = new Carril[numberOfPlayers];

        //Index to have control over the carriles array
        int index = 0;

        for(int i = 0; i < numberOfPlayers; i++){
            
            Boolean validatorName = false;
            Jugadores jugador = null;
            while(!validatorName){

                //Ask for the player name.
                System.out.println("Please, insert the name of player "+ (i + 1) + " ");
                String playerName = System.console().readLine();
                
                //Check if player is already in the database
                jugador = baseDeDatos.getByName(playerName);
                
                //Create an entry in the database if the player does not exist

                if(jugador == null){
                    jugador = baseDeDatos.crearJugador(playerName, 0);
                }

                //Validate that there is not repeated name in the game.
                if( juego.jugadores().containsKey(jugador.id))
                {
                    System.out.println("The name " + playerName + " is already taken.");
                }
                else{
                    validatorName = true;
                }   
            }
                 
            //Ask user to choose over a set of color options.
            Boolean validatorColor = false;
            //Validate user input.
            Color colorChoosen = Color.Negro;
            while(!validatorColor){
                System.out.println("Please, write your color between the ones in the following list.");
            
                for(var color: Color.values()){
                    System.out.println(color.name());
                }
    
                String playerColor = System.console().readLine();
                try{
                    colorChoosen =  Color.valueOf(playerColor);

                    validatorColor = true;
                }
                catch(Exception e){
                    System.out.println("You must select one color from the list. Watch capital letters");
                }
            }       

            juego.crearJugador(jugador.id, jugador.nombre, colorChoosen);
           
            //Generate a new conductor with the name of the new player
            Conductor conductor = new Conductor(jugador.nombre);

            //Generate a new car for the new conductor and the color choosen by the player
            Carro carro = new Carro(colorChoosen);
            carro.asignarConductor(conductor);

            //Generate a carril for the new car
            Posicion posicion = new Posicion(0, juego.pista().kilometros() * 1000);
            Carril carril = new Carril(posicion);
            carril.agregarCarro(carro);

            //Add the new carril to the carriles array.
            carriles[index] = carril;
            
            //Increase carriles index
            index ++;
            System.out.println("-------------");
        }

        //Start game
        juego.iniciarJuego();

        //Repeat over and over until fulfill the podium.
        while(!juego.podio().estaLleno()){

            //Create the index for the carriles array.
            int indexGame = 0;

            //Iterate over the players in the game.
            for(Map.Entry<Integer, Jugador> entry : juego.jugadores().entrySet()){

                //Skip the player if he already get the goal distance.
                if(carriles[indexGame].desplazamientoFinal()){
                    indexGame++;
                    continue;
                }

                //Check if the player is ready to throw the dice
                Boolean playerReady = false;
                while(!playerReady)
                {
                    System.out.println(entry.getValue().nombre() + " Ready to trow the dice? Yes/No");
                    String response = System.console().readLine();
                    if(response.equals("Yes"))
                    {
                        playerReady = true;
                    }            
                }

                //Calculate the distance multiplying the throw result by 100.
                int meters = carriles[indexGame].carro().conductor().lanzarDado() * 100;

                System.out.println(meters + "m");

                //Move the car into the carril.
                carriles[indexGame].moverCarro(meters);

                //Print the carr current distance.
                System.out.println(entry.getValue().nombre() + ", your current position is: " + carriles[indexGame].posicionActual() + "m");
                System.out.println("------------------------");

                //Use alcanzarLaMeta method to update the desplazamientoFinal property.
                carriles[indexGame].alcanzarLaMeta();

                //Check if the player finish.
                if(carriles[indexGame].desplazamientoFinal()){
                    //Asign the first place on the podium if nobody has ended.
                    if(juego.podio().primerLugar() == null)
                    {
                        juego.asignarPrimerLugar(entry.getKey());
                        System.out.println("First Place: " + juego.podio().primerLugar().nombre());
                    }
                    //Asign the second place on the podium if one person has ended.
                    else if(juego.podio().segundoLugar() == null)
                    {
                        juego.asignarSegundoLugar(entry.getKey());
                        System.out.println("Second Place: " + juego.podio().segundoLugar().nombre());
                    }
                    //Asign the thrid place on the podium if two players have ended.
                    else 
                    {
                        juego.asignarTercerLugar(entry.getKey());
                        System.out.println("Third Place: " + juego.podio().tercerLugar().nombre());
                    }

                    System.out.println("-----------------------");
                }

                //Change the index for the carriles array.
                indexGame++;
            }

        }

        //Show the podium
        System.out.println("Podium: ");
        System.out.println("-------------------------");
        System.out.println("First Place: " + juego.podio().primerLugar().nombre());
        System.out.println("-------------------------");
        System.out.println("Second Place: " + juego.podio().segundoLugar().nombre());
        System.out.println("-------------------------");
        System.out.println("Third Place: " + juego.podio().tercerLugar().nombre());
        System.out.println("-------------------------");

        //Save only the winner of the race.
        for(Map.Entry<Integer, Jugador> entry : juego.jugadores().entrySet()){
            if(entry.getValue().equals(juego.podio().primerLugar())){
                baseDeDatos.actualizarPuntuacion(entry.getKey(), 1);
            }
        }

        //Show the scoreboad stored in the data base of the game.
        System.out.println("ScoreBoard: ");
        System.out.println("------------");
        var results = baseDeDatos.getAllPlayers();
        for(var result:results){
            System.out.println(result.getNombre() + ": " + result.getPuntaje());
            System.out.println("------------");
        }

        
    }

    public static void main(String[] args) throws Exception {

        //Create a new app object.
        App app = new App();

        //Validate if players want to play again.
        Boolean validatorGame = false;
        while(!validatorGame){
            //Start the game.
            app.startGame();

            //Ask the user if he wants to play again.
            System.out.println("Do you want to play again? Yes / No");

            //Finish the game if player's answer is No.
            String response = System.console().readLine();
            if(response.equals("No")){
                System.out.println("Thanks for playing!.");
                validatorGame = true;
            }           
        }
    }
}

