import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class WarehouseManagement {
    Client client = new Client();
    Map<String, Map<SupportedMetalType, Double>> clientsMap = new HashMap<String, Map<SupportedMetalType, Double>>();
    double warehouseMaxSize = 10000; //m3
    double currentStockStatus;
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
//    public void verifyMetalCorrectness(SupportedMetalType metalType){
//        for(SupportedMetalType selectedMetalType: SupportedMetalType.values()) {
//            try {
//                SupportedMetalType.valueOf(SupportedMetalType.class, metalType);
//                return true;
//            } catch (IllegalArgumentException e) {
//                return false;
//            }
//        }
//    }
//    void addMetalIngot(String clientId, SupportedMetalType metalType, double mass){
//        client.verifyClientInMapById(clientId);
//        verifyWarehouseCapacity(mass);
//
//
//    }

    Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId){
        return clientsMap.get(clientId);
    }

    double getTotalVolumeOccupiedByClient(String clientId){
        double totalVolumeOccupiedByClient = 0;
        for ( SupportedMetalType i : getMetalTypesToMassStoredByClient(clientId).keySet()) {
            totalVolumeOccupiedByClient += getMetalTypesToMassStoredByClient(clientId).get(i);
        }
        return totalVolumeOccupiedByClient;
    }

    List<SupportedMetalType> getStoredMetalTypesByClient(String clientId){
        return new ArrayList(clientsMap.get(clientId).keySet());
    }

}
