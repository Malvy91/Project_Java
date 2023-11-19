public class Main {
    public static void main(String[] args) {
        WarehouseHandlingSystem warehouseHandlingSystem = new WarehouseHandlingSystem();
        Clients clients = warehouseHandlingSystem;

        Warehouse warehouse= warehouseHandlingSystem;

        System.out.println("Clients account created. They client Id: " + clients.createNewClient("Malwina", "Wajdzik"));
        System.out.println("Clients account created. They client Id: " + clients.createNewClient("Krysia", "Tomczak"));
        System.out.println("Clients account created. They client Id: " + clients.createNewClient("Aleksandra", "Wiktoria"));
        System.out.println("Client " + clients.activatePremiumAccount("KT1") + "has a set up premium account");
        System.out.println("Has client premium account: " + clients.isPremiumClient("KT1"));
//        clients.getClientFullName("KT1");
        System.out.println("Client KT1 record was created on: " + clients.getClientCreationDate("KT1") );
//        clients.isPremiumClient("KT1");
//        clients.isPremiumClient("MW0");
//        clients.getNumberOfClients();
//        clients.getNumberOfPremiumClients();

        warehouse.addMetalIngot("KT1", SupportedMetalType.GOLD, 300);
        warehouse.addMetalIngot("KT1", SupportedMetalType.GOLD, 700);
        warehouse.addMetalIngot("MW0", SupportedMetalType.GOLD, 300);
        warehouse.addMetalIngot("MW0", SupportedMetalType.COPPER, 2000);
        System.out.println("Metal and its amount stored by KT1: " + warehouse.getMetalTypesToMassStoredByClient("KT1"));
        System.out.println("Metal and its amount stored by MW0: " + warehouse.getMetalTypesToMassStoredByClient("MW0"));
        System.out.println("Total occupied Valume by KT1 client: " + warehouse.getTotalVolumeOccupiedByClient("KT1") + " m3");
        System.out.println("Total occupied Valume by MW0 client: " + warehouse.getTotalVolumeOccupiedByClient("MW0") + " m3");
        System.out.println("Metal stored by MW0: " + warehouse.getStoredMetalTypesByClient("MW0"));
        System.out.println("Metal stored by KT1: " + warehouse.getStoredMetalTypesByClient("KT1"));
    }
}