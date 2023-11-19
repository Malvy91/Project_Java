import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class WarehouseManagement {
    WarehouseHandlingSystem warehouseHandlingSystem = new WarehouseHandlingSystem();
    WarehouseManagement(){
    }
    Map<String, Map<SupportedMetalType, Double>> clientsMap = new HashMap<String, Map<SupportedMetalType, Double>>();
    double warehouseMaxSize = 10000; //m3
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

        warehouseHandlingSystem.verifyClientInMapById(clientId);
        verifyWarehouseCapacity(mass);
        verifyMetalCorrectness(metalType);

        metalTypesToMassMap = getMetalTypesToMassStoredByClient(clientId);
        if (metalTypesToMassMap.containsKey(metalType)){
            totalMass = metalTypesToMassMap.get(metalType) + mass;
            metalTypesToMassMap.replace(metalType, totalMass);
            clientsMap.replace(clientId, metalTypesToMassMap);
        } else {
            metalTypesToMassMap.putIfAbsent(metalType, mass);
        }
    }

    public Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId){
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
