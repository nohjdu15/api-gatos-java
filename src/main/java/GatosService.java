

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GatosService {
    public static void VerGatos() throws IOException {
        OkHttpClient client = new OkHttpClient();
       
        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();
        
        Response response = client.newCall(request).execute();
        
        String elJson = response.body().string();
        // cortar los corchetes que salen en postman
        elJson = elJson.substring(1, elJson.length());
        elJson = elJson.substring(0, elJson.length()-1);
        
        //CONVIRTIENDO EN UN OBJETO TIPO GATOS
        //Crear un objeto de la clase Gson
        
        Gson gson = new Gson(); //creamos el objeto "gson" de la clase o de tipo "Gson"
        Gatos gatos = gson.fromJson(elJson, Gatos.class);//"elJson" es el que nos trea la respuesta y la convierte a tipo "Gatos"
        //ESTO SE HACE PARA QUE LOS PARAMETROS QUE ESTAN EN LA CLASE "GATOS" PUEDAN SER MANIPULADOS
        
        
        //redimensionar la imagen
        @SuppressWarnings("UnusedAssignment")
        Image image = null;// crear un objeto de tipo Image -hay que importar la clase-
        try{
            URL url = new URL(gatos.getUrl());//lepasamos como parametro la url en este caso lo guardamos en el objeto "gatos"
            image = ImageIO.read(url);// en el objeto "image", se le pasa la url para que la pueda convertir a un tipo imagen
            
            ImageIcon fondoGato = new ImageIcon(image);// esto se hace porque el Joptionpane, recibe una imagen de tipo icono
            //y recibe como parametro la imagen que acabamos de crear "image"
            //REDIMENSIONAR LA IMAGEN
            if(fondoGato.getIconWidth()> 800){
                Image fondo = fondoGato.getImage();// Creamos un objeto de tipo "Image" que va a ser igual al objeto "fondoGato"
                Image modificada = fondo.getScaledInstance(600, 400, java.awt.Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
              
            }
            String menu = """
                          Opciones: 
                          1. Ver otra imagen 
                          2. Favorito 
                          3. Volver 
                          """;
            
            String[] botones = {"ver otra imagen", "favorito", "volver"};
            String id_gato = gatos.getId(); //se obtiene el id del gato STRING para poderlo mostrar en la interfaz grafica
            String opcion = (String) JOptionPane.showInputDialog(null,menu,id_gato,JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);
            
            int seleccion = -1;
            for(int i =0; i<botones.length; i++){
               if(opcion.equals(botones[i])){ 
                   seleccion = i;
               }
            }
            
            switch(seleccion){
                case 0 -> VerGatos();
                case 1 -> favoritoGato(gatos);
                default -> {
                }
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    public static void favoritoGato(Gatos gato){
        try{
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n\"image_id\":\""+gato.getId()+"\"\r\n}");
            Request request;
            request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST",body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApikey())//aqui en ves de pasar la apikey, se pasa el objeto "gato" y por medio del metodo
                    //"getApikey" se trae la llave
                    .build();
            Response response = client.newCall(request).execute();
            
        }catch(IOException e){
            System.out.println(e);
        }
        
    }
    
    public static void verFavorito(String apikey) throws IOException {
        OkHttpClient client = new OkHttpClient();
     
        
        Request request = new Request.Builder()
          .url("https://api.thecatapi.com/v1/favourites")
          .get()
          .addHeader("Content-Type", "application/json")
          .addHeader("x-api-key", "live_OETWCi48DTgQWQIt8aFccp0Hxho1Kx29jHjoHlZgESJX3MlyuaTZa8aKMnsf0T84")
          .build();
        
        Response response = client.newCall(request).execute();
        
        String elJson = response.body().string();
        
        Gson gson = new Gson();
        
        GatosFav[]  gatosArray = gson.fromJson(elJson,GatosFav[].class);
        
        if(gatosArray.length >0){
            int min = 1;
            int max = gatosArray.length;
            int aleatorio = (int) (Math.random() *(max-min)+1) + min;
            int indice = aleatorio-1;
            
            GatosFav gatofav = gatosArray[indice];
                //redimensionar la imagen
            @SuppressWarnings("UnusedAssignment")
            Image image = null;// crear un objeto de tipo Image -hay que importar la clase-
            try{
                URL url = new URL(gatofav.image.getUrl());//lepasamos como parametro la url en este caso lo guardamos en el objeto "gatos"
                image = ImageIO.read(url);// en el objeto "image", se le pasa la url para que la pueda convertir a un tipo imagen

                ImageIcon fondoGato = new ImageIcon(image);// esto se hace porque el Joptionpane, recibe una imagen de tipo icono
                //y recibe como parametro la imagen que acabamos de crear "image"
                //REDIMENSIONAR LA IMAGEN
                if(fondoGato.getIconWidth()> 800){
                    Image fondo = fondoGato.getImage();// Creamos un objeto de tipo "Image" que va a ser igual al objeto "fondoGato"
                    Image modificada = fondo.getScaledInstance(600, 400, java.awt.Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(modificada);

                }
                String menu = """
                              Opciones: 
                              1. Ver otra imagen 
                              2. Favorito 
                              3. Volver 
                              """;

                String[] botones = {"ver otra imagen", " eliminar favorito", "volver"};
                String id_gato = gatofav.getId(); //se obtiene el id del gato STRING para poderlo mostrar en la interfaz grafica
                String opcion = (String) JOptionPane.showInputDialog(null,menu,id_gato,JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

                int seleccion = -1;
                for(int i =0; i<botones.length; i++){
                   if(opcion.equals(botones[i])){ 
                       seleccion = i;
                   }
                }

                switch(seleccion){
                    case 0 -> verFavorito(apikey);
                    case 1 -> borrarFavorito(gatofav);
                    default -> {}
                }
            }catch(IOException e){
                System.out.println(e);
            }
        }
    } 
    
    public static void borrarFavorito(GatosFav gatofav) {
        try{
            OkHttpClient client = new OkHttpClient();
            
            Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites/"+gatofav.getId()+"")
                .delete(null)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", gatofav.getApikey())
                .build();
            Response response = client.newCall(request).execute();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
}

    


    