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
    private final Client Client = new Client();
    private final VerifyCorrectness verifyCorrectness = new VerifyCorrectness();
    private final ClientIdGenerator clientIdGenerator = new ClientIdGenerator();
    // declaration of variable, where we will deliver creation date
    public LocalDate creationDate;
    WarehouseHandlingSystem(){
    }
    // Clients interface implementation
    @Override
    public String createNewClient(String firstName, String lastName){
        verifyCorrectness.assertString("name", firstName);
        verifyCorrectness.assertString("surname", lastName);
        int freeMapIndex = Client.clients.size();
        String clientId = clientIdGenerator.generateClientId(firstName, lastName, freeMapIndex);
        creationDate = LocalDate.now();

        Client clientObject = Client.createClientObject(firstName, lastName, clientId, creationDate);
        Client.clients.put(clientId, clientObject);
        System.out.println(Client.clients.keySet());
        System.out.println(Client.clients.values());
        return clientId;
    }
    @Override
    public String activatePremiumAccount(String clientId) {
        verifyCorrectness.verifyClientInMapById(clientId, Client.clients);
        if (Client.getPremium(clientId)) {
            System.out.println("Your client " + clientId + " has premium status.");
        } else {
            Client.setIsPremium(clientId, true);
        }
        return clientId;
    }
    @Override
    public String getClientFullName(String clientId) {
        verifyCorrectness.verifyClientInMapById(clientId, Client.clients);
        String clientFirstName = Client.getFirstName(clientId);
        String clientLastName = Client.getLastName(clientId);
        String clientFirstAndLastName = clientFirstName + " " + clientLastName;
        System.out.println("Clients name and surname: " + clientId + ": " + clientFirstAndLastName );
        return clientFirstAndLastName;
    }
    @Override
    public LocalDate getClientCreationDate(String clientId) {
        verifyCorrectness.verifyClientInMapById(clientId, Client.clients);
        LocalDate creationDate = Client.getCreationDate(clientId);
        return creationDate;
    }
    @Override
    public boolean isPremiumClient(String clientId) {
        verifyCorrectness.verifyClientInMapById(clientId, Client.clients);
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
    public void addMetalIngot(String clientId, SupportedMetalType metalType, double mass){
        Map<SupportedMetalType, Double> metalTypesToMassMap;
        double totalMass;

        verifyCorrectness.verifyClientInMapById(clientId, Client.clients);
        verifyCorrectness.verifyWarehouseCapacity(mass);
        verifyCorrectness.verifyMetalCorrectness(metalType);

        metalTypesToMassMap = getMetalTypesToMassStoredByClient(clientId);
        System.out.println("metoda addMetalIngot");
        System.out.println(metalTypesToMassMap);
        if (metalTypesToMassMap == null){
            Map<SupportedMetalType, Double> newMetalTypesToMassMap = new HashMap<SupportedMetalType, Double>();
            newMetalTypesToMassMap.put(metalType, mass);
            verifyCorrectness.clientsMap.put(clientId, newMetalTypesToMassMap);
            System.out.println(Client.clients);
        } else {
            if (metalTypesToMassMap.containsKey(metalType)){
                totalMass = metalTypesToMassMap.get(metalType) + mass;
                metalTypesToMassMap.replace(metalType, totalMass);
                verifyCorrectness.clientsMap.replace(clientId, metalTypesToMassMap);
            } else {
                metalTypesToMassMap.put(metalType, mass);

            }
        }
    }

    public Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId){
        if (verifyCorrectness.clientsMap == null){
            Map<String, Map<SupportedMetalType, Double>> clientsMap = new HashMap<String, Map<SupportedMetalType, Double>>();
            Map<SupportedMetalType, Double> newMetalTypesToMassMap = new HashMap<SupportedMetalType, Double>();
            clientsMap.put(clientId, newMetalTypesToMassMap);
        }
        return verifyCorrectness.clientsMap.get(clientId);
    }

    public double getTotalVolumeOccupiedByClient(String clientId){
        double totalVolumeOccupiedByClient = 0;
        double VolumeOccupiedBySingleMetal;
        double density;
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
        return new ArrayList(verifyCorrectness.clientsMap.get(clientId).keySet());
    }

}
