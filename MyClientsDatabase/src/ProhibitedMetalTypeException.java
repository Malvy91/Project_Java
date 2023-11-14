public class ProhibitedMetalTypeException extends RuntimeException {
    public ProhibitedMetalTypeException(){
    }
    public ProhibitedMetalTypeException(String comment){
        System.out.println(comment);
        System. exit(0);
    }
}
