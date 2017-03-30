package tp.rest;

import java.sql.SQLException;
import tp.model.*;
import javax.xml.bind.JAXBException;
import javax.xml.ws.http.HTTPException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/zoo-manager/")
public class MyServiceTP {
        private MyServiceTPDAO service;
        private Center center;

    public MyServiceTP() throws Exception {
        this.service = new MyServiceTPDAO();
        center = service.findCenter();
        center.getCages().addAll(service.findAllCages());
    }

    /**
     * GET method bound to calls on /animals/{something}
     */
    @GET
    @Path("/animals/{id}/")
    @Produces("application/xml")
    public Animal getAnimal(@PathParam("id") String animal_id) throws JAXBException {
        try {
            return center.findAnimalById(UUID.fromString(animal_id));
        } catch (AnimalNotFoundException e) {
            throw new HTTPException(404);
        }
    }

    /**
     * GET method bound to calls on /animals
     */
    @GET
    @Path("/animals/")
    @Produces("application/xml")
    public Center getAnimals(){
        return this.center;
    }

    /**
     * POST method bound to calls on /animals
     */
    @POST
    @Path("/animals/")
    @Consumes({"application/xml", "application/json" })
    public Center postAnimals(Animal animal) throws JAXBException, SQLException {
        this.center.getCages()
                .stream()
                .filter(cage -> cage.getName().equals(animal.getCage()))
                .findFirst()
                .orElseThrow(() -> new HTTPException(404))
                .getResidents()
                .add(animal);
        
        return this.center;
    }

    @GET
    @Path("/find/byName/{name}/")
    @Produces("application/xml")
    public Animal find_animal_name(@PathParam("name") String name) throws JAXBException {
        try {
            return center.findAnimalByName(name);
        } catch (AnimalNotFoundException e) {
            throw new HTTPException(404);
        }
    }


    @GET
    @Path("/find/near/{latitude}/{longitude}/")
    @Produces("application/xml")
    public Cage find_animal_near(@PathParam("latitude")String latitude,@PathParam("longitude") String longitude) throws JAXBException {
        Position position = new Position();
        position.setLatitude(Double.parseDouble(latitude));
        position.setLongitude(Double.parseDouble(longitude));
        try {
            return this.center.findAnimalNearPosition(position);
        } catch (AnimalNotFoundException e) {
            throw new HTTPException(404);
        }
    }


    @GET
    @Path("/find/at/{latitude}/{longitude}/")
    @Produces("application/xml")
    public Animal find_animal_at(@PathParam("latitude")String latitude,@PathParam("longitude") String longitude) throws JAXBException {
        Position position=new Position();
        position.setLatitude(Double.parseDouble(latitude));
        position.setLongitude(Double.parseDouble(longitude));
        try {
            return this.center.findAnimalByPosition(position);
        } catch (AnimalNotFoundException e) {
            throw new HTTPException(404);
        }
    }


    @DELETE
    @Path(value="animals")
    public void removeAll(){
        Collection<Cage> collection_cages = this.center.getCages();
        Iterator<Cage> iter_cages = collection_cages.iterator();
         /*parcourir chaque cage*/
        while(iter_cages.hasNext()){
            Cage cage = iter_cages.next();
             /*supprimer tout les animaux*/
            cage.getResidents().removeAll(cage.getResidents());
        }

    }



    @DELETE
    @Path(value="animals/{animal_id}")
    public void removeAnimal(@PathParam(value="animal_id")String animal_id){
        {
        	/* recuperer toutes les cages */
            Collection<Cage> cages = this.center.getCages();
            Cage cage;
            Collection<Animal> collection_animals;
            Iterator<Cage> iter_cage = cages.iterator();
            Iterator<Animal> iter_animal;

           /*parcourir la collection des cage*/
            while(iter_cage.hasNext()){
                cage = iter_cage.next();
                collection_animals = cage.getResidents();
                iter_animal = collection_animals.iterator();
            	/*parcourir la listes des animaux par cage jusqu'a trouvï¿½
            	 * l'animal rechercher (ï¿½ supprimer)*/
                while(iter_animal.hasNext()){
                    Animal animal= iter_animal.next();

                    if(animal.getId().equals(UUID.fromString(animal_id))){
                        collection_animals.remove(animal);
                    }
                }
            }
        }
    }

    @DELETE
    @Path(value="animals/remove/{location}")
    public void removeAnimalByLocation(@PathParam("location")String location){
        Collection<Cage> col = this.center.getCages();
        Iterator<Cage> it = col.iterator();
        /*parcourir chaque cage*/
        while(it.hasNext()){
            Cage cage = it.next();
            /*supprimer tout les animaux*/
            if(cage.getName().equals(location))
                cage.getResidents().removeAll(cage.getResidents());
        }
    }

    @PUT
    @Path(value="animals/")
    @Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
    public void updateAnimalById(Animal animal_put){

        Collection<Cage> collection_cages = this.center.getCages();
        Cage cage;
        Collection<Animal> collection_animals;
        Iterator<Cage> iter_cages = collection_cages.iterator();
        Iterator<Animal> iter_animals;

             /*parcourir chaque cage*/
        while(iter_cages.hasNext()){
            cage = iter_cages.next();
            collection_animals = cage.getResidents();
            iter_animals=collection_animals.iterator();

             	/*parcourir les animaux de chaque cage*/
            while(iter_animals.hasNext()){
                Animal animal= iter_animals.next();
                if(animal.getId().equals(animal_put.getId())){
                    animal.setName(animal_put.getName());
                }
            }
        }
    }

    @PUT
    @Path(value="/animals/all")
    public void updateAnimal(Animal animal){
        /* recuperer toutes les cages */
        Collection<Cage> collection_cages = this.center.getCages();
        Collection<Animal> collection_animals;
        Iterator<Cage> iter_cages = collection_cages.iterator();
        Iterator<Animal> iter_animals;

        while(iter_cages.hasNext()){
            Cage cage = iter_cages.next();
            if(cage.getName().equals(animal.getCage())){
                for(Animal a: cage.getResidents()){
                    a.setName(animal.getName());
                }
            }
        }

    }

    @POST
    @Path("animals/cage")
    public Center postCage(Cage cage) throws JAXBException, SQLException {
        this.center.getCages().add(cage);
        return this.center;
    }
}
