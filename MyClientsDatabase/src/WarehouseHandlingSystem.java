import java.time.LocalDate;
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseHandlingSystem implements Clients, Warehouse{
    public String firstName;
    public String lastName;
    public String clientId;
    public boolean isPremium;
    public LocalDate creationDate;
    Map<String, WarehouseHandlingSystem> clients = new HashMap<String, WarehouseHandlingSystem>();
    Map<String, Map<SupportedMetalType, Double>> clientsMap = new HashMap<String, Map<SupportedMetalType, Double>>();
    double warehouseMaxSize = 10000; //m3
    WarehouseHandlingSystem(){
    }
    // Clients implementation
    // utworzenie konstruktora dla obiektu klienta
    WarehouseHandlingSystem(String firstName, String lastName, String clientId, LocalDate creationDate){
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
            System.out.println(clients.containsKey(clientId));
            System.out.println(clients.keySet());
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
        WarehouseHandlingSystem warehouseHandlingSystem = new WarehouseHandlingSystem(firstName, lastName, clientId, creationDate);
        clients.put(clientId, warehouseHandlingSystem);
        System.out.println(clients.keySet());
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
                throw new ProhibitedMetalTypeException("Metal nie może być przyjęty do magazynu");
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
            System.out.println(clients);
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
