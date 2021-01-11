package co.com.sofka.cargame.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import co.com.sofka.cargame.hibernate.HibernateUtil;
import co.com.sofka.cargame.model.Jugadores;

public class JugadoresService{

    public Jugadores getById(Integer id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{

            session.beginTransaction();

            var jugador = (Jugadores) session.get(Jugadores.class, id);

            session.getTransaction().commit();
            return jugador;

        }
        catch(HibernateException e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    public Jugadores getByName(String nombre){

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Jugadores jugador = (Jugadores) session.bySimpleNaturalId(Jugadores.class).load(nombre);

        return jugador;

    }


    public Jugadores crearJugador(String nombre, int puntuacion){

        var jugador = this.getByName(nombre);

        if(jugador == null){
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //Add new Employee object
            jugador = new Jugadores();
            jugador.setNombre(nombre);
            jugador.setPuntaje(puntuacion);

            session.save(jugador);

            session.getTransaction().commit();
        }

        return jugador;
    }
    public List<Jugadores> getAllPlayers() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("SELECT a FROM Jugadores a", Jugadores.class).getResultList();  

    }

    public Jugadores actualizarPuntuacion(int id, int puntuacion){

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var jugadores = session.get(Jugadores.class, id);
        jugadores.puntaje += puntuacion;
        session.update(jugadores);

        session.getTransaction().commit();
        return jugadores;
    }
}