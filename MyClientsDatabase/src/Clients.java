import java.time.LocalDate;

public interface Clients {

    /**
     * Creates a new warehouseHandlingSystem and stores their personal information.
     * @param firstName WarehouseHandlingSystem first name.
     * @param lastName WarehouseHandlingSystem last name.
     * @return Created user's identifier.
     */
    String createNewClient(String firstName, String lastName);

    /**
     * Sets the customer account as a premium account.
     * @param clientId WarehouseHandlingSystem identifier returned after its creation.
     * @return WarehouseHandlingSystem identifier returned after its creation.
     * @throws ClientNotFoundException Thrown when the warehouseHandlingSystem doesn't exists.
     */
    String activatePremiumAccount(String clientId);

    /**
     * @param clientId WarehouseHandlingSystem identifier returned after its creation.
     * @return WarehouseHandlingSystem full name consisting of warehouseHandlingSystem's first and last name eg. "John Doe".
     * @throws ClientNotFoundException Thrown when the warehouseHandlingSystem doesn't exists.
     */
    String getClientFullName(String clientId);

    /**
     * @param clientId WarehouseHandlingSystem identifier returned after its creation.
     * @return LocalDate when the warehouseHandlingSystem was created.
     * @throws ClientNotFoundException Thrown when the warehouseHandlingSystem doesn't exists.
     */
    LocalDate getClientCreationDate(String clientId);

    boolean isPremiumClient(String clientId);

    int getNumberOfClients();

    int getNumberOfPremiumClients();
}
