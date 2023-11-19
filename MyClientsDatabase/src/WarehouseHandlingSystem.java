import java.time.LocalDate;
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* WarehouseHandlingSystem implements Clients and Warehouse interfaces, what means that
WarehouseHandlingSystem contains the methods implementation for both interfaces.
 */
public class WarehouseHandlingSystem implements Clients, Warehouse{
    private  Client Client = new Client();
    // declaration of variable, where we will deliver client id
    private String clientId;
    // declaration of variable, where we will deliver creation date
    public LocalDate creationDate;
    // declaration of map clientsMap, that contains data about metals and its mass that is delivered by clients to warehouse
    Map<String, Map<SupportedMetalType, Double>> clientsMap = new HashMap<String, Map<SupportedMetalType, Double>>();
    // declaration of max size of warehouse
    double warehouseMaxSize = 10000; //m3
    WarehouseHandlingSystem(){
    }
    public void assertString( String whatString, String string){
        if (string.isEmpty()){
            System.out.println("Please enter " + whatString + ".");
            System. exit(0);
        }
    }
    // method to verify if client has the account in base
    public void verifyClientInMapById(String clientId) {
        if (!Client.clients.containsKey(clientId)){
            throw new ClientNotFoundException("Client not fount in database.");
        }
    }
    /* method to generate the client Id
    - clientId format [XYnum];
        X - first letter of name
        Y - first letter of surname
        num - assigned number to client in hex
     */
    public String generateClientId(String firstName, String lastName, int freeMapIndex){
        String hexValue = Integer.toHexString(freeMapIndex);
        char charIndexFirstName = firstName.charAt(0);
        String indexFirstName = Character.toString(charIndexFirstName);
        char charIndexLastName = lastName.charAt(0);
        String indexLastName = Character.toString(charIndexLastName);
        clientId = indexFirstName + indexLastName + hexValue;
        return clientId;
    }
    // Clients interface implementation
    @Override
    public String createNewClient(String firstName, String lastName){
        assertString("name", firstName);
        assertString("surname", lastName);
        int freeMapIndex = Client.clients.size();
        clientId = generateClientId(firstName, lastName, freeMapIndex);
        creationDate = LocalDate.now();

        Client clientObject = Client.createClientAccount(firstName, lastName, clientId, creationDate);
        Client.clients.put(clientId, clientObject);
        return clientId;
    }
    @Override
    public String activatePremiumAccount(String clientId) {
        verifyClientInMapById(clientId);
        if (Client.getPremium(clientId)) {
            System.out.println("Your client " + clientId + " has premium status.");
        } else {
            Client.setIsPremium(clientId, true);
        }
        return clientId;
    }
    @Override
    public String getClientFullName(String clientId) {
        verifyClientInMapById(clientId);
        String clientFirstName = Client.getFirstName(clientId);
        String clientLastName = Client.getLastName(clientId);
        String clientFirstAndLastName = clientFirstName + " " + clientLastName;
        System.out.println("Clients name and surname: " + clientId + ": " + clientFirstAndLastName );
        return clientFirstAndLastName;
    }
    @Override
    public LocalDate getClientCreationDate(String clientId) {
        verifyClientInMapById(clientId);
        LocalDate creationDate = Client.getCreationDate(clientId);
        return creationDate;
    }
    @Override
    public boolean isPremiumClient(String clientId) {
        verifyClientInMapById(clientId);
        boolean isPremium = Client.getPremium(clientId);
        if (isPremium){
            System.out.println("Client " + clientId + " has premium status." );
        } else {
            System.out.println("Client " + clientId + " has no premium status, yet. Please, suggest him/her available packages." );
        }
        return isPremium;
    }
    @Override
    public int getNumberOfClients(){
        return Client.clients.size();
    }
    @Override
    public int getNumberOfPremiumClients(){
        int premiumClients= 0;
        for (String i : Client.clients.keySet()) {
            if (Client.getPremium(i)) {
                premiumClients++;
            }
        }
        System.out.println("There are " + premiumClients + " clients in database.");
        return premiumClients;
        }

    // Warehouse implementation
    public boolean findClientInMap(String clientId){
            return clientsMap.containsKey(clientId);
        }
    public boolean findMetalInClientsMap(String clientId, SupportedMetalType metalType) {
        if (findClientInMap(clientId)) {
            Map<SupportedMetalType, Double> metalsMap = clientsMap.get(clientId);
            return metalsMap.containsKey(metalType);
        }
        return false;
    }
    public double metalMass(Map<SupportedMetalType, Double> metalsMap, SupportedMetalType metalType){
        return metalsMap.get(metalType);
    }
    double getTotalVolumeOccupiedByAllClients(){
        double totalVolumeOccupiedByAllClients = 0;
        for ( String i : clientsMap.keySet()) {
            for ( SupportedMetalType j : getMetalTypesToMassStoredByClient(i).keySet()) {
                totalVolumeOccupiedByAllClients += getMetalTypesToMassStoredByClient(i).get(j);
            }
        }
        return totalVolumeOccupiedByAllClients;
    }
    public void verifyWarehouseCapacity(double mass) {
        if (getTotalVolumeOccupiedByAllClients() < warehouseMaxSize){
            if (mass > (warehouseMaxSize - getTotalVolumeOccupiedByAllClients())){
                throw new FullWarehouseException("Warehouse is full. We cannot accept the goods.");
            }
        }
    }
    public void verifyMetalCorrectness(SupportedMetalType metalType){
        switch (metalType){
            case COPPER, TIN, IRON, LEAD, SILVER, TUNGSTEN, GOLD, PLATINUM:
                break;
            default:
                throw new ProhibitedMetalTypeException("Our warehouse does not support that metal type.");
        }
    }
    public void addMetalIngot(String clientId, SupportedMetalType metalType, double mass){
        Map<SupportedMetalType, Double> metalTypesToMassMap;
        double totalMass;

        verifyClientInMapById(clientId);
        verifyWarehouseCapacity(mass);
        verifyMetalCorrectness(metalType);

        metalTypesToMassMap = getMetalTypesToMassStoredByClient(clientId);
        System.out.println("metoda addMetalIngot");
        System.out.println(metalTypesToMassMap);
        if (metalTypesToMassMap == null){
            Map<SupportedMetalType, Double> newMetalTypesToMassMap = new HashMap<SupportedMetalType, Double>();
            newMetalTypesToMassMap.put(metalType, mass);
            clientsMap.put(clientId, newMetalTypesToMassMap);
            System.out.println(Client.clients);
        } else {
            if (metalTypesToMassMap.containsKey(metalType)){
                totalMass = metalTypesToMassMap.get(metalType) + mass;
                metalTypesToMassMap.replace(metalType, totalMass);
                clientsMap.replace(clientId, metalTypesToMassMap);
            } else {
                metalTypesToMassMap.put(metalType, mass);

            }
        }
    }

    public Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId){
        if (clientsMap == null){
            Map<String, Map<SupportedMetalType, Double>> clientsMap = new HashMap<String, Map<SupportedMetalType, Double>>();
            Map<SupportedMetalType, Double> newMetalTypesToMassMap = new HashMap<SupportedMetalType, Double>();
            clientsMap.put(clientId, newMetalTypesToMassMap);
        }
        System.out.println("metoda getMetalTypesToMassStoredByClient before print");
        System.out.println(clientsMap);
        System.out.println("metoda getMetalTypesToMassStoredByClient after print");
        return clientsMap.get(clientId);
    }

    public double getTotalVolumeOccupiedByClient(String clientId){
        double totalVolumeOccupiedByClient = 0;
        double VolumeOccupiedBySingleMetal = 0;
        double density = 0;
        for ( SupportedMetalType i : getMetalTypesToMassStoredByClient(clientId).keySet()) {
            // d = m/V
            density = switch (i) {
                case COPPER -> SupportedMetalType.COPPER.getDensity();
                case TIN -> SupportedMetalType.TIN.getDensity();
                case IRON -> SupportedMetalType.IRON.getDensity();
                case LEAD -> SupportedMetalType.LEAD.getDensity();
                case SILVER -> SupportedMetalType.SILVER.getDensity();
                case TUNGSTEN -> SupportedMetalType.TUNGSTEN.getDensity();
                case GOLD -> SupportedMetalType.GOLD.getDensity();
                case PLATINUM -> SupportedMetalType.PLATINUM.getDensity();
            };
            VolumeOccupiedBySingleMetal = getMetalTypesToMassStoredByClient(clientId).get(i)/density;
            totalVolumeOccupiedByClient += VolumeOccupiedBySingleMetal;
        }
        return totalVolumeOccupiedByClient;
    }

    public List<SupportedMetalType> getStoredMetalTypesByClient(String clientId){
        return new ArrayList(clientsMap.get(clientId).keySet());
    }

}
