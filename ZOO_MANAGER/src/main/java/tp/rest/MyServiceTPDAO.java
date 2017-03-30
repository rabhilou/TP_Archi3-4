package tp.rest;

import tp.model.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.swing.JOptionPane;
import tp.model.Cage;
import tp.model.Center;
import tp.model.Position;

/**
 * Created by spaurgeo on 24/03/17.
 */
public class MyServiceTPDAO {

    private Connection connection;
    private final static String url = "jdbc:mysql://sl-eu-lon-2-portal.1.dblayer.com:17052";
    private final static String user = "admin";
    private final static String pwd = "LREAOQURMKGBDMFV";

    public MyServiceTPDAO() throws Exception {
        Connection connection = null;

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, pwd);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("USE zoo");
        stmt.executeUpdate("DROP TABLE IF EXISTS animals");
        stmt.executeUpdate("DROP TABLE IF EXISTS cages");
        stmt.executeUpdate("CREATE TABLE animals (id varchar(100), name varchar(100), cage varchar(100), species varchar(100))");
    }

    public void addAnimal(Animal animal) throws SQLException {
        Connection con = this.establishConnection();
        Statement state = con.createStatement();
        state.executeUpdate("INSERT INTO animal VALUES('"+animal.getId().toString()+"','"+animal.getName()+"','"+animal.getSpecies()+"',"+this.findCageByName(animal.getCage())+");");
        con.close();
    }
    
    public void removeAnimal(String id) throws SQLException{
        Connection con = this.establishConnection();
        /* Création de l'objet gérant les requêtes */
        Statement statement = con.createStatement();
        /* Exécution d'une requête de lecture */
        statement.executeUpdate("DELETE FROM animal WHERE id = '"+id+"'");
    }
    
    public void removeAnimalByLocation(String location) throws SQLException{
        String id = this.findCageByName(location);
        Connection con = this.establishConnection();
        /* Création de l'objet gérant les requêtes */
        Statement statement = con.createStatement();
        /* Exécution d'une requête de lecture */
        statement.executeUpdate( "DELETE FROM animal WHERE id_cage = "+id);
    }
    
    public void updateAnimalById(Animal animal, String id) throws SQLException{
        Connection con = this.establishConnection();
        /* Création de l'objet gérant les requêtes */
        Statement statement = con.createStatement();
        /* Exécution d'une requête de lecture */
        statement.executeUpdate( "UPDATE animal SET name = '"+animal.getName()+"' WHERE id = '"+id+"'");
    }
    
    public void updateAll(Animal animal) throws SQLException{
        Connection con = this.establishConnection();
        /* Création de l'objet gérant les requêtes */
        Statement statement = con.createStatement();
        /* Exécution d'une requête de lecture */
        statement.executeUpdate( "UPDATE animal SET name = '"+animal.getName()+"'" );
    }
    
    public void removeAll() throws SQLException{
        Connection con = this.establishConnection();
        /* Création de l'objet gérant les requêtes */
        Statement statement = con.createStatement();
        /* Exécution d'une requête de lecture */
        statement.executeUpdate( "DELETE * FROM animal" );
    }
    
    public String findCageByName(String cage) throws SQLException{
        Connection con = this.establishConnection();
        /* Création de l'objet gérant les requêtes */
        Statement statement = con.createStatement();
        /* Exécution d'une requête de lecture */
        ResultSet resultat = statement.executeQuery( "SELECT id  FROM cage where name = '"+cage+"';" );
        resultat.next();
        int lol = resultat.getInt("id");
        return lol+"";
    }
    
    private Connection establishConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoo", "root", "");
        } catch (SQLException sqle) {
            System.err.println("SQL Exception thrown while making connection");
        }
        return conn;
    }

    
    public Center findCenter() throws SQLException{
        Connection con = this.establishConnection();
        /* Création de l'objet gérant les requêtes */
        Statement statement = con.createStatement();
        /* Exécution d'une requête de lecture */
        ResultSet resultat = statement.executeQuery( "SELECT id, name, longitude, latitude  FROM center;" );
        Center center = null;
        /* Récupération des données du résultat de la requête de lecture */
        while ( resultat.next() ) {
            int id = resultat.getInt( "id" );
            String name = resultat.getString( "name" );
            String longitude = resultat.getString( "longitude" );
            String latitude = resultat.getString( "latitude" );
            center = new Center(new LinkedList<>(), new Position(Double.parseDouble(latitude), Double.parseDouble(longitude)), name);
        }
        con.close();
        return center;
    }
    
    public ArrayList<Cage> findAllCages() throws SQLException{
        ArrayList<Cage> cages = new ArrayList();
        LinkedList animals = new LinkedList();
        Connection con = this.establishConnection();
        /* Création de l'objet gérant les requêtes */
        Statement statement = con.createStatement();
        Statement state = con.createStatement();
        /* Exécution d'une requête de lecture */
        ResultSet resultat = statement.executeQuery( "SELECT id, name, capacity, longitude, latitude  FROM cage;" );
        /* Récupération des données du résultat de la requête de lecture */
        while ( resultat.next() ) {
            int id = resultat.getInt( "id" );
            String name = resultat.getString( "name" );
            int capacity = resultat.getInt( "capacity" );
            String longitude = resultat.getString( "longitude" );
            String latitude = resultat.getString( "latitude" );
            ResultSet res = state.executeQuery("SELECT id, name, species FROM animal WHERE id_cage = "+id+";");
            while( res.next() ){
               String id_animal = res.getString("id");
               String name_animal = res.getString("name");
               String species_animal = res.getString("species");
               animals.add(new Animal(name_animal, name ,species_animal, UUID.fromString(id_animal) ));
            }
            Cage cage = new Cage(name,new Position(Double.parseDouble(latitude), Double.parseDouble(longitude)), capacity, animals);
            cages.add(cage);
        }
        con.close();
        return cages;
    }
    
    public void addCage(Cage cage) throws SQLException{
        Connection con = this.establishConnection();
        Statement state = con.createStatement();
        Statement statement = con.createStatement();
        /* Exécution d'une requête de lecture */
        ResultSet resultat = statement.executeQuery( "SELECT name FROM cage" );
        boolean lol = false;
        while(resultat.next()){
            if(cage.getName().equals(resultat.getString("name"))){
                lol = true;
            }
        }
        if(!lol)
            state.executeUpdate("INSERT INTO cage(name, capacity, longitude, latitude, id_center) VALUES('"+cage.getName()+"',"+cage.getCapacity()+",'"+cage.getPosition().getLongitude()+"','"+cage.getPosition().getLatitude()+"',"+1+");");
        else
            JOptionPane.showMessageDialog(null, "Cage déjà existante !", "ERROR", 0);
        con.close();
    }
}
