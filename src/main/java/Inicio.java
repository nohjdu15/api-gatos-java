
import java.io.IOException;
import javax.swing.JOptionPane;

public class Inicio {

    private static Gatos gato;
    public static void main(String[] args) throws IOException {
        int opcion_menu =-1;
        //MENU PRINCIPAL
        String[] botones = {"1. ver gatos","2. Ver favoritos","3. Salir"};
        
        do{
            String opcion = (String) JOptionPane.showInputDialog(null,"Gatitos en java", "menu principal", JOptionPane.INFORMATION_MESSAGE, 
                    null, botones, botones[0]);
            //VALIDAMOS QUE OPCION SELECCIONA EL USUARIO
            for(int i =0; i<botones.length; i++){
               if(opcion.equals(botones[i])){ 
                   opcion_menu = i;
               }
            }
            switch(opcion_menu){
                case 0 -> GatosService.VerGatos();
                case 1 -> { 
                    Gatos gato = new Gatos();
                    GatosService.verFavorito(gato.getApikey());
                }
                default -> {
                }
            }
        }while(opcion_menu != 1);
    }
}
