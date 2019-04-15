import com.sun.corba.se.pept.transport.InboundConnectionCache;

public class noMain {
    public static void main(String[] args) {

        Integrante i1 = new Integrante("juan", "bere", "jmasmda@ajsjda.com");
        Integrante mi2 = new Integrante("manu", "dealgi", "jmasmda@ajsjda.com");
        Integrante i3 = new Integrante("rogelio", "martinez", "jmasmda@ajsjda.com");

        Integrante[] vector = new Integrante[3];

        vector[0] = i1 ;
        vector[1] = mi2 ;
        vector[2] = i3 ;

        Integrante max = Operador.getMax(vector);

        System.out.printf("el maximo es: "+ max.toString() + " \n ");

        Integrante min = Operador.getMin(vector) ;

        System.out.printf("el minimo es: " + min.toString()+ " \n ");

        System.out.printf("el indice del integrante rogelio es: " + Operador.getIndexOF(vector,i3));

    }
}
