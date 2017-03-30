package tp.rest;

import tp.model.Animal;
import tp.model.Cage;
import tp.model.Center;
import tp.model.Position;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;

public class MyClient {
    private Service service;
    private MyServiceTP my;
    private JAXBContext jc;
    private MyServiceTPDAO daoservice;
    private static final QName qname = new QName("", "");
    private static final String url = "http://localhost:8084/rest-service/zoo-manager";

    private MyClient() throws Exception {
        try {
            this.daoservice = new MyServiceTPDAO();
            this.my = new MyServiceTP();
            jc = JAXBContext.newInstance(Center.class, Cage.class, Animal.class, Position.class);
        } catch (JAXBException je) {
            System.out.println("Cannot create JAXBContext " + je);
        }
    }

    private void get_animals() throws JAXBException {
        sendRequest("/animals", "GET", null);
    }
    
    private void add_cage(Cage cage) throws SQLException{
        this.daoservice.addCage(cage);
    }
    
    private void add_animal(Animal animal) throws JAXBException, SQLException {
        this.daoservice.addAnimal(animal);
        sendRequest("/animals/", "POST", new JAXBSource(jc, animal));
    }

  
    private void remove_animals() throws JAXBException, SQLException {
        this.daoservice.removeAll();
        this.my.removeAll();
        sendRequest("/animals", "DELETE", null);
    }

    private void update_animals(Animal animal) throws JAXBException, SQLException{
        this.daoservice.updateAll(animal);
        this.my.updateAnimal(animal);
        sendRequest("/animals", "PUT", new JAXBSource(jc, animal));
    }

    private void updateAll(Animal animal) throws JAXBException, SQLException {
        this.daoservice.updateAll(animal);
        sendRequest("/animals", "PUT", null);
    }

    private void removeAnimal(String id) throws JAXBException, SQLException {
        this.daoservice.removeAnimal(id);
        sendRequest("/animals/" + id, "DELETE", null);
    }

    private void updateAnimal(Animal animal, String id) throws JAXBException, SQLException {
        this.daoservice.updateAnimalById(animal,id);
        sendRequest("/animals/" + id, "PUT", new JAXBSource(jc, animal));
    }

    private void find_by_name(String name) throws JAXBException, UnsupportedEncodingException {
        sendRequest("/find/byName/" + URLEncoder.encode(name, "UTF-8"), "GET", null);
    }

    private void find_at(Position position) throws Exception {
        sendRequest("/find/at/", "POST", new JAXBSource(jc, position));
    }

    private void find_near(Position position) throws Exception {
        sendRequest("/find/near/", "POST", new JAXBSource(jc, position));
    }

    private void printSource(Source s) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(s, new StreamResult(System.out));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void sendRequest(String urlPrefixe, String method, Source source) {
        try {
            URL mainurl = new URL(url + urlPrefixe);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) mainurl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(method);
            if (source != null) {
                conn.setRequestProperty("Content-Type", "application/xml");
                conn.setRequestProperty("Accept", "application/xml");
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                OutputStream os = conn.getOutputStream();
                transformer.transform(source, new StreamResult(os));
                os.flush();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        MyClient client = new MyClient();
        //client.senario();
        //client.add_cage(new Cage("Rouen", new Position(49.443889,1.103333), 1000));
       //client.add_animal(new Animal("Bob", "Rouen", "Arapaima-gigas", UUID.randomUUID()));
          //client.remove_animals();
         // client.update_animals(new Animal("Mdrrr","Rouen","Arapaima-gigas",UUID.randomUUID()));
//        client.add_animal_with_id(new Animal("Bob", "amazon", "Arapaima gigas", UUID.randomUUID()), UUID.randomUUID().toString());
         client.removeAnimal("2e0cf121-048c-4ba9-b417-99352ff03610");
//        client.update_animal_with_id(new Animal("Bob", "amazon", "Arapaima gigas", UUID.randomUUID()), UUID.randomUUID().toString());
//        client.find_by_name("Tic");
//        client.find_at(new Position(49.305d, 1.2157357d));
//        client.find_near(new Position(49.305d, 1.2157357d));
        //client.senario();
    }

    public void senario() throws Exception {
        MyClient client = new MyClient();
        client.get_animals();print(" ");
        client.remove_animals();print(" ");
        client.get_animals();print(" ");

        client.add_cage(new Cage("Rouen", new Position(49.443889,1.103333), 1000));print(" ");
        client.add_animal(new Animal("Panda", "Rouen", "Panda"));print(" ");

        client.add_cage(new Cage("Paris", new Position(48.856578, 2.351828), 1000));
        client.add_animal(new Animal("Hocco unicorne", "Paris", "Hocco unicorne"));

        client.get_animals();print(" ");

        client.add_cage(new Cage("Paris", new Position(48.856578, 2.351828), 1000));
        client.add_animal(new Animal("Hocco unicorne", "Paris", "Hocco unicorne"));

        client.update_animals(new Animal("Lagotriche", "Rouen", "Lagotriche"));

        client.get_animals();print(" ");

        client.add_cage(new Cage("Somalie", new Position(2.333333, 48.85), 1000));
        client.add_animal(new Animal("Océanite de Matsudaira", "Somalie", "Océanite de Matsudaira"));

        client.add_animal(new Animal("Ara de Spix", "Rouen", "Ara de Spix"));

        client.add_cage(new Cage("Bihorel", new Position(49.455278, 1.116944), 1000));
        client.add_animal(new Animal("Galago de Rondo", "Bihorel", "Galago de Rondo"));

        client.add_cage(new Cage("Londre", new Position( 51.504872, -0.07857), 1000));
        client.add_animal(new Animal("Palette des Sulu", "Londre", "Palette des Sulu"));

        client.add_animal(new Animal("Kouprey", "Paris", "Kouprey"));

        client.add_animal(new Animal("Tuit tuit", "Paris", "Tuit tuit"));

        client.add_cage(new Cage("Canada", new Position( 43.2, -80.38333), 1000));
        client.add_animal(new Animal("Saiga", "Canada", "Saiga"));

        client.add_cage(new Cage("Porto Vecchio", new Position( 41.5895241, 9.2627), 1000));
        client.add_animal(new Animal("Inca de Bonaparte", "Porto Vecchio", "Inca de Bonaparte"));

        client.get_animals();print(" ");

        client.add_cage(new Cage("Montreux", new Position( 46.4307133,  6.9113575), 1000));
        client.add_animal(new Animal("Râle de Zapata", "Montreux", "Râle de Zapata"));

        client.add_cage(new Cage("Villers Bocage", new Position( 50.0218, 2.3261), 1000));
        client.add_animal(new Animal("Rhinocéros de Java", "Villers Bocage", "Rhinocéros de Java"));

        client.add_cage(new Cage("usa", new Position( 49.305d, 1.2157357d), 1000));
        for (int i = 0; i < 101; ++i) {
            client.add_animal(new Animal("Dalmatien"+i, "usa", "Dalmatien"));
        }

        client.get_animals();print(" ");
        client.remove_animals();
        client.get_animals();print(" ");

        //client.find_by_name("Galago de Rondo");
        client.remove_animals();
        client.remove_animals();

        client.get_animals();print(" ");
        client.find_near(new Position(49.443889,1.103333));
        client.find_at(new Position(49.443889,1.103333));


        client.get_animals();print(" ");
    }

    private void print(String msg) {
        System.out.println(msg);
    }
    
   
    
}
