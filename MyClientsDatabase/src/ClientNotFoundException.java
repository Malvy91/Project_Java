public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(){
    }
    public ClientNotFoundException(String comment){
        System.out.println(comment);
        System. exit(0);
    }
}
