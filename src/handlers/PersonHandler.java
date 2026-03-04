package handlers;

import DAO.PersonDAO;
import DAO.ServiceDAO;
import models.Person;
import models.Service;

public class PersonHandler {

    private final PersonDAO personDAO;
    private final ServiceDAO serviceDAO;

    public PersonHandler(){
        personDAO = new PersonDAO();
        serviceDAO = new ServiceDAO();
    }

    public boolean savePerson(Person person){

        if (person == null) return false;
        if (person.getLastName() == null || person.getLastName().isBlank()) return false;
        if (person.getFirstNames() == null || person.getFirstNames().isBlank()) return false;
        if (person.getFunction() == null || person.getFunction().isBlank()) return false;

        Service service = serviceDAO.getByID(person.getServiceID());
        if (service == null) return false;

        return personDAO.insertPerson(person);

    }

    public Person getPersonByID(int personID){

        Person personToGet = personDAO.getByID(personID);

        if (personToGet == null) return new Person("Personne Inconnue" , "Personne Inconnue");

        return personToGet;

    }

    public int getPersonCountInDB(){ return personDAO.getCountInDB(); }

}
