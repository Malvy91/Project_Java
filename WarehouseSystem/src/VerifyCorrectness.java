import java.util.HashMap;
import java.util.Map;

public class VerifyCorrectness {
    // declaration of map clientsMap, that contains data about metals and its mass that is delivered by clients to warehouse
    Map<String, Map<SupportedMetalType, Double>> clientsMap = new HashMap<String, Map<SupportedMetalType, Double>>();
    // declaration of max size of warehouse
    double warehouseMaxSize = 10000; //m3
    VerifyCorrectness(){
    }
    public void assertString( String whatString, String string){
        if (string.isEmpty()){
            System.out.println("Please enter " + whatString + ".");
            System. exit(0);
        }
    }
    public void verifyClientInMapById(String clientId, Map<String, Client> map) {
        System.out.println(map.values());
        if (!map.containsKey(clientId)){
            throw new ClientNotFoundException("Client not fount in database.");
        }
    }
    double getTotalVolumeOccupiedByAllClients(){
        double totalVolumeOccupiedByAllClients = 0;
        for ( String i : clientsMap.keySet()) {
            for ( SupportedMetalType j : clientsMap.get(i).keySet()) {
                totalVolumeOccupiedByAllClients += clientsMap.get(i).get(j);
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
}
