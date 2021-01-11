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

        //Prueba
        // baseDeDatos.crearJugador("Jorge", 5);
        // baseDeDatos.crearJugador("Kelly", 3);
        // baseDeDatos.crearJugador("Daniel", 0);

        //Ask for number of players.
        System.out.println("Please, insert the number of players");
        int numberOfPlayers = 0;
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
        Boolean validatorKilometers = false;
        int kilometers = 0;
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



        //Generate arrays for cars and carriles.
        Carril[] carriles = new Carril[numberOfPlayers];

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
                if( juego.jugadores().containsKey(jugador.id))
                {
                    System.out.println("The name " + playerName + " is already taken.");
                }
                else{
                    validatorName = true;
                }   

            }
            
            
            //Display colors available 
            Boolean validatorColor = false;

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

            //Generate a new car for the new conductor
            Carro carro = new Carro(colorChoosen);
            carro.asignarConductor(conductor);

            //Generate a carril for the new car
            Posicion posicion = new Posicion(0, juego.pista().kilometros() * 1000);
            Carril carril = new Carril(posicion);
            carril.agregarCarro(carro);
            carriles[index] = carril;
            
            index ++;
            System.out.println("-------------");
        }

        //Start game
        juego.iniciarJuego();

        while(!juego.podio().estaLleno()){

            int indexGame = 0;

            for(Map.Entry<Integer, Jugador> entry : juego.jugadores().entrySet()){

                if(carriles[indexGame].desplazamientoFinal()){
                    indexGame++;
                    continue;
                }

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

                int meters = carriles[indexGame].carro().conductor().lanzarDado() * 100;

                System.out.println(meters + "m");

                carriles[indexGame].moverCarro(meters);

                System.out.println(entry.getValue().nombre() + ", your current position is: " + carriles[indexGame].posicionActual() + "m");
                System.out.println("------------------------");

                carriles[indexGame].alcanzarLaMeta();

                if(carriles[indexGame].desplazamientoFinal()){
                    
                    if(juego.podio().primerLugar() == null)
                    {
                        juego.asignarPrimerLugar(entry.getKey());
                        System.out.println("First Place: " + juego.podio().primerLugar().nombre());
                    }
                    else if(juego.podio().segundoLugar() == null)
                    {
                        juego.asignarSegundoLugar(entry.getKey());
                        System.out.println("Second Place: " + juego.podio().segundoLugar().nombre());
                    }
                    else 
                    {
                        juego.asignarTercerLugar(entry.getKey());
                        System.out.println("Third Place: " + juego.podio().tercerLugar().nombre());
                    }

                    System.out.println("-----------------------");
                }


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

        System.out.println("Score Board: ");
        System.out.println("------------");
        var results = baseDeDatos.getAllPlayers();
        for(var result:results){
            System.out.println(result.getNombre() + ": " + result.getPuntaje());
            System.out.println("------------");
        }

        
    }

    public static void main(String[] args) throws Exception {

        App app = new App();

        Boolean validatorGame = false;
        while(!validatorGame){

            app.startGame();

            System.out.println("Do you want to play again? Yes / No");

            String response = System.console().readLine();
            if(response.equals("No")){
                System.out.println("Thanks for playing!.");
                validatorGame = true;
            }
            
        }

    }
}

