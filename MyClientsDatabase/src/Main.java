
public class Main {
    public static void main(String[] args) {
        Clients client = new Clients();
        ClientNotFoundException exception = new ClientNotFoundException();

        client.createNewClient("Malwina", "Wajdzik");
        client.createNewClient("Krysia", "Tomczak");
        client.createNewClient("Aleksandra", "Wiktoria");
        client.findClientPositionInArrayById("KT1");
        client.activatePremiumAccount("KT1");
        client.getClientFullName("KT1");
        client.getClientCreationDate("KT1");
        client.isPremiumClient("KT1");
        client.isPremiumClient("MW0");
        client.getNumberOfClients();
        client.getNumberOfPremiumClients();
    }
}