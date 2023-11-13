import java.time.LocalDate;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;

public class Client implements Clients{
    public String firstName;
    public String lastName;
    public String clientId;
    public boolean isPremium;
    public LocalDate creationDate;
    Map<String, Client> clients = new HashMap<String, Client>();
    Client(){
    }
    // utworzenie konstruktora dla obiektu klienta
    Client(String firstName, String lastName, String clientId, LocalDate creationDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientId = clientId;
        this.isPremium = false;
        this.creationDate = creationDate;
    }
    // gettery
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
    // settery
    public void setIsPremium(String clientId, boolean isPremium){
        clients.get(clientId).isPremium = isPremium;
    }
    // assercje i weryfikacje
    public void assertString( String whatString, String string){
        if (string.isEmpty()){
            System.out.println("Proszę podać " + whatString + ".");
            System. exit(0);
        }
    }
    // finder
    public void verifyClientInMapById(String clientId) {
        if (!clients.containsKey(clientId)){
            throw new ClientNotFoundException("Nie znaleziono klienta w bazie danych.");
        }
    }
    // generatory i tworzenie nowej tablicy
    public String generateClientId(String firstName, String lastName, int freeMapIndex){
        String hexValue = Integer.toHexString(freeMapIndex);
        char charIndexFirstName = firstName.charAt(0);
        String indexFirstName = Character.toString(charIndexFirstName);
        char charIndexLastName = lastName.charAt(0);
        String indexLastName = Character.toString(charIndexLastName);
        clientId = indexFirstName + indexLastName + hexValue;
        return clientId;
    }
    // metody głowne
    @Override
    public String createNewClient(String firstName, String lastName){
        assertString("imię", firstName);
        assertString("nazwisko", lastName);
        int freeMapIndex = clients.size();
        clientId = generateClientId(firstName, lastName, freeMapIndex);
        creationDate = LocalDate.now();
        Client client = new Client(firstName, lastName, clientId, creationDate);
        clients.put(clientId, client);
        return clientId;
    }
    @Override
    public String activatePremiumAccount(String clientId) {
        verifyClientInMapById(clientId);
        if (getPremium(clientId)) {
            System.out.println("Twój klient " + clientId + " posiada już pakiet premium.");
        } else {
            setIsPremium(clientId, true);
        }
        System.out.println("Czy klient " + clientId + " posiada premium? " + getPremium(clientId));
        return clientId;
    }
    @Override
    public String getClientFullName(String clientId) {
        verifyClientInMapById(clientId);
        String clientFirstName = getFirstName(clientId);
        String clientLastName = getLastName(clientId);
        String clientFirstAndLastName = clientFirstName + " " + clientLastName;
        System.out.println("Imię i nazwisko klienta " + clientId + ": " + clientFirstAndLastName );
        return clientFirstAndLastName;
    }
    @Override
    public LocalDate getClientCreationDate(String clientId) {
        verifyClientInMapById(clientId);
        LocalDate creationDate = getCreationDate(clientId);
        System.out.println("Data utworzenia wpisu o kliencie " + clientId + ": " + creationDate );
        return creationDate;
    }
    @Override
    public boolean isPremiumClient(String clientId) {
        verifyClientInMapById(clientId);
        boolean isPremium = getPremium(clientId);
        if (isPremium){
            System.out.println("Klient " + clientId + " ma status premium." );
        } else {
            System.out.println("Klient " + clientId + " nie ma jeszcze statusu premium. Zaproponuj zmiane warunków pakietu." );
        }
        return isPremium;
    }
    @Override
    public int getNumberOfClients(){
        return clients.size();
    }
    @Override
    public int getNumberOfPremiumClients(){
        int premiumClients= 0;
        for (String i : clients.keySet()) {
            if (getPremium(i)) {
                premiumClients++;
            }
        }
        System.out.println("Liczba klientów premium w naszej bazie wynosi " + premiumClients );
        return premiumClients;
        }
}
