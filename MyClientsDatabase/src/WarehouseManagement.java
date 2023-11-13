import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class WarehouseManagement {

    // metalsMap stores amount of selected metal type
    Map<String, Map<SupportedMetalType, Double>> clientsMap = new HashMap<String, Map<SupportedMetalType, Double>>();

    public boolean findClientInMap(String clientId){
        return clientsMap.containsKey(clientId);
    }
    public boolean findMetalInClientsMap(String clientId, SupportedMetalType metalType) {
        if (clientsMap.containsKey(clientId)) {
            Map<SupportedMetalType, Double> metalsMap = clientsMap.get(clientId);
            return metalsMap.containsKey(metalType);
        }
        return false;
    }
    public double metalMass(Map<SupportedMetalType, Double> metalsMap, SupportedMetalType metalType){
            return metalsMap.get(metalType);
    }
    void addMetalIngot(String clientId, SupportedMetalType metalType, double mass){

    }

    Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId){

    }

    double getTotalVolumeOccupiedByClient(String clientId){

    }

    List<SupportedMetalType> getStoredMetalTypesByClient(String clientId){

    }

}
