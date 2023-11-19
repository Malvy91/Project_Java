public class Main {
    public static void main(String[] args) {
        WarehouseHandlingSystem warehouseHandlingSystem = new WarehouseHandlingSystem();
        Clients clients = warehouseHandlingSystem;

        Warehouse warehouse= warehouseHandlingSystem;
        
        clients.createNewClient("Malwina", "Wajdzik");
        clients.createNewClient("Krysia", "Tomczak");
//        clients.createNewClient("Aleksandra", "Wiktoria");
//        clients.activatePremiumAccount("KT1");
//        clients.getClientFullName("KT1");
//        clients.getClientCreationDate("KT1");
//        clients.isPremiumClient("KT1");
//        clients.isPremiumClient("MW0");
//        clients.getNumberOfClients();
//        clients.getNumberOfPremiumClients();

        warehouse.addMetalIngot("KT1", SupportedMetalType.GOLD, 300);
        warehouse.addMetalIngot("KT1", SupportedMetalType.GOLD, 700);
        warehouse.addMetalIngot("MW0", SupportedMetalType.GOLD, 300);
        warehouse.addMetalIngot("MW0", SupportedMetalType.COPPER, 2000);
        warehouse.getMetalTypesToMassStoredByClient("KT1");
        warehouse.getMetalTypesToMassStoredByClient("MW0");
        System.out.println(warehouse.getTotalVolumeOccupiedByClient("KT1") + " m3");
        System.out.println(warehouse.getStoredMetalTypesByClient("MW0"));
    }
}