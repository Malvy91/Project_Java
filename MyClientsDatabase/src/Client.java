import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Client {
    public String firstName;
    public String lastName;
    public String clientId;
    public boolean isPremium;
    public LocalDate creationDate;
    Map<String, Client> clients = new HashMap<String, Client>();
    // client's objects' constructor
    Client(){
    }
    Client(String firstName, String lastName, String clientId, LocalDate creationDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientId = clientId;
        this.isPremium = false;
        this.creationDate = creationDate;
    }
    /* implementation of access to client's object attributes like: first name, last name,
    clientId, information about premium status and creation Date; following methods allows
    to get value of parameter
     */
    public boolean getPremium(String clientId){
        return clients.get(clientId).isPremium;
    }
    public String getClientId(String clientId){
        return clients.get(clientId).clientId;
    }
    public String getFirstName(String clientId){
        return clients.get(clientId).firstName;
    }
    public String getLastName(String clientId){
        return clients.get(clientId).lastName;
    }
    public LocalDate getCreationDate(String clientId){
        return clients.get(clientId).creationDate;
    }
    /* implementation of access to client's object attributes like: first name, last name,
    clientId, information about premium status and creation Date; following methods allows
    to set value of parameter - because other parameters were not set, there is only one
    set method tht call parameter "isPremium"
     */
    public void setIsPremium(String clientId, boolean isPremium){
        clients.get(clientId).isPremium = isPremium;
    }

    public Client createClientAccount(String firstName, String lastName, String clientId, LocalDate creationDate){
        return new Client(firstName, lastName, clientId, creationDate);
    }
}
