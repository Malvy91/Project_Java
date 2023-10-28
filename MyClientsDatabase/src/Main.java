
public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        Clients clients = client;
        
        clients.createNewClient("Malwina", "Wajdzik");
        clients.createNewClient("Krysia", "Tomczak");
        clients.createNewClient("Aleksandra", "Wiktoria");
        clients.activatePremiumAccount("KT1");
        clients.getClientFullName("KT1");
        clients.getClientCreationDate("KT1");
        clients.isPremiumClient("KT1");
        clients.isPremiumClient("MW0");
        clients.getNumberOfClients();
        clients.getNumberOfPremiumClients();

    }
}