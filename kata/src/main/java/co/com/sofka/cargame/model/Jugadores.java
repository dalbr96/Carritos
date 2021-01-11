package co.com.sofka.cargame.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NaturalId;



@Entity
@Table(name = "Jugadores", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})

public class Jugadores implements Serializable{
    
    private static final long serialVersionUID = -2330234481480412281L;

    //Instance Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer id;

    @NaturalId
    @Column(name = "nombre", unique = true, nullable = false)
    public String nombre;

    @Column(name = "puntaje", unique = false, nullable = false)
    public Integer puntaje;

    public Jugadores (Integer id, String nombre, Integer puntaje){
        
        this.id = id;
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

	public Jugadores() {
    }
    
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Integer getPuntaje(){
        return this.puntaje;
    }
    public void setPuntaje(Integer puntaje){
        this.puntaje = puntaje;
    }
}

