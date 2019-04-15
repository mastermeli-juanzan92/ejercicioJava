public class ApiException extends Exception {

    public ApiException(){
        //llamo al constructor de la clase padre
        super();
    }

    public ApiException(String mensaje) {
        super(mensaje);
    }

}
