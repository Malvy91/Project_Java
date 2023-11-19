public class ClientIdGenerator {
    // declaration of variable, where we will deliver client id
    private String clientId;
    ClientIdGenerator(){
    }
    /* method to generate the client Id
   - clientId format [XYnum];
       X - first letter of name
       Y - first letter of surname
       num - assigned number to client in hex
    */
    public String generateClientId(String firstName, String lastName, int freeMapIndex){
        String hexValue = Integer.toHexString(freeMapIndex);
        char charIndexFirstName = firstName.charAt(0);
        String indexFirstName = Character.toString(charIndexFirstName);
        char charIndexLastName = lastName.charAt(0);
        String indexLastName = Character.toString(charIndexLastName);
        clientId = indexFirstName + indexLastName + hexValue;
        return clientId;
    }
}
