import java.time.LocalDate;
import java.lang.*;

public class Client implements Clients{
    public String firstName;
    public String lastName;
    public String clientId;
    public boolean isPremium;
    public LocalDate creationDate;
    public Client[] ClientsArray = {};
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
    public boolean getPremium( int clientPositionInArray ){
        isPremium = ClientsArray[clientPositionInArray].isPremium;
        return isPremium;
    }
    public String getClientId( int clientPositionInArray ){
        clientId = ClientsArray[clientPositionInArray].clientId;
        return clientId;
    }
    public String getFirstName( int clientPositionInArray ){
        firstName = ClientsArray[clientPositionInArray].firstName;
        return firstName;
    }
    public String getLastName( int clientPositionInArray ){
        lastName = ClientsArray[clientPositionInArray].lastName;
        return lastName;
    }
    public LocalDate getCreationDate( int clientPositionInArray ){
        creationDate = ClientsArray[clientPositionInArray].creationDate;
        return creationDate;
    }
    // settery
    public void setPremium( int clientPositionInArray){
        ClientsArray[clientPositionInArray].isPremium = true;
    }
    public void setFirstName( int clientPositionInArray, String firstName){
        ClientsArray[clientPositionInArray].firstName = firstName;
    }
    public void setLastName( int clientPositionInArray, String lastName){
        ClientsArray[clientPositionInArray].lastName = lastName;
    }
    public void setClientId( int clientPositionInArray, String clientId){
        ClientsArray[clientPositionInArray].clientId = clientId;
    }
    public void setCreationDate( int clientPositionInArray, LocalDate creationDate){
        ClientsArray[clientPositionInArray].creationDate = creationDate;
    }
    // assercje i weryfikacje
    public void assertString( String whatString, String string){
        if (string.isEmpty()){
            System.out.println("Proszę podać " + whatString + ".");
            System. exit(0);
        }
    }
    // finder
    public int findClientPositionInArrayById( String clientId){
        boolean isFound = false;
        int intIndexValue = 0;
        int arrayLength = ClientsArray.length;
        for (int x=0; x<arrayLength; x++){
            if (getClientId(x).equals(clientId)){
                intIndexValue = x;
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            throw new ClientNotFoundException("Nie znaleziono klienta w bazie danych.");
        } else {
            return intIndexValue;
        }
    }
    // generatory i tworzenie nowej tablicy
    public String generateClientId(String firstName, String lastName, int freeArrayIndex){
        String hexValue = Integer.toHexString(freeArrayIndex);
        char charIndexFirstName = firstName.charAt(0);
        String indexFirstName = Character.toString(charIndexFirstName);
        char charIndexLastName = lastName.charAt(0);
        String indexLastName = Character.toString(charIndexLastName);
        clientId = indexFirstName + indexLastName + hexValue;
        return clientId;
    }
    public static Client[] rewriteArray(Client[] myArray, Client object) {
        Client[] newArray = new Client[myArray.length + 1];
        for (int i = 0; i < myArray.length; i++)
            newArray[i] = myArray[i];

        newArray[myArray.length] = object;
        return newArray;
    }
    // metody głowne
    @Override
    public String createNewClient(String firstName, String lastName){
        assertString("imię", firstName);
        assertString("nazwisko", lastName);
        int freeArrayIndex = ClientsArray.length;
        clientId = generateClientId(firstName, lastName, freeArrayIndex);
        creationDate = LocalDate.now();
        Client client = new Client(firstName, lastName, clientId, creationDate);
        ClientsArray = rewriteArray(ClientsArray, client);
        int freeArrayIndex2 = ClientsArray.length;
        System.out.println(freeArrayIndex2);
        System.out.println(client.firstName);
        System.out.println(client.lastName);
        System.out.println(client.clientId);
        System.out.println(client.creationDate);
        System.out.println(client.isPremium);
        return clientId;
    }
    @Override
    public String activatePremiumAccount(String clientId) {
        int clientPositionInArray = findClientPositionInArrayById(clientId);
        if (getPremium(clientPositionInArray)) {
            System.out.println("Twój klient " + clientId + " posiada już pakiet premium.");
        }
        setPremium(clientPositionInArray);
        System.out.println("Czy klient " + clientId + " posiada premium? " + getPremium(clientPositionInArray));
        return clientId;
    }
    @Override
    public String getClientFullName(String clientId) {
        int clientPositionInArray = findClientPositionInArrayById(clientId);
        String clientFirstName = getFirstName(clientPositionInArray);
        String clientLastName = getLastName(clientPositionInArray);
        String clientFirstAndLastName = clientFirstName + " " + clientLastName;
        System.out.println("Imię i nazwisko klienta " + clientId + ": " + clientFirstAndLastName );
        return clientFirstAndLastName;
    }
    @Override
    public LocalDate getClientCreationDate(String clientId) {
        int clientPositionInArray = findClientPositionInArrayById(clientId);
        LocalDate creationDate = getCreationDate(clientPositionInArray);
        System.out.println("Data utworzenia wpisu o kliencie " + clientId + ": " + creationDate );
        return creationDate;
    }
    @Override
    public boolean isPremiumClient(String clientId) {
        int clientPositionInArray = findClientPositionInArrayById(clientId);
        boolean isPremium = getPremium(clientPositionInArray);
        if (isPremium){
            System.out.println("Klient " + clientId + " ma status premium." );
        } else {
            System.out.println("Klient " + clientId + " nie ma jeszcze statusu premium. Zaproponuj zmiane warunków pakietu." );
        }
        return isPremium;
    }
    @Override
    public int getNumberOfClients(){
        int clientsArrayLenth = ClientsArray.length;
        System.out.println("Liczba klientów w naszej bazie wynosi " + clientsArrayLenth );
        return clientsArrayLenth;
    }
    @Override
    public int getNumberOfPremiumClients(){
        int premiumClients= 0;
        int clientsArrayLenth = ClientsArray.length;
        for (int x=0; x<clientsArrayLenth; x++){
            if (getPremium(x)) {
                premiumClients++;
            }
            }
        System.out.println("Liczba klientów premium w naszej bazie wynosi " + premiumClients );
        return premiumClients;
        }
}
