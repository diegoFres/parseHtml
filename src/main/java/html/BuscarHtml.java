package html;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;
import java.util.logging.Logger;

public class BuscarHtml {

    // escribir mensajes de error
    public static final Logger LOG = Logger.getLogger(BuscarHtml.class.getName());

    private static String palabraBuscar;

    public static void main(String[] args) {

        String archHtml = "C:\\Users\\diego\\OneDrive\\Documentos\\NetBeansProjects\\memorama\\imagen\\desa3.html";
        palabraBuscar = "desarrollo".toLowerCase(); // Buscamos la palabra 'desarrollo'

        // log para guardar resultados
        String archLog = "file-" + palabraBuscar + ".log";

        try (FileReader lectorArchivo = new FileReader(archHtml);
             BufferedWriter esctrLog = new BufferedWriter(new FileWriter(archLog))) {

            // procesar el archivo HTML
            HTMLEditorKit.Parser parse = new ParserDelegator();

            // buscar la palabra en el HTML
            parse.parse(lectorArchivo, new ManejadorHTML(esctrLog, archHtml), true);

        } catch (FileNotFoundException e) {
            LOG.severe("Error al abrir el archivo html: " + archHtml);
            System.exit(2); // se termina si no se encuentra el archivo
        } catch (IOException e) {
            LOG.severe("Error al procesar el archivo HTML");
            System.exit(3); // se termina si hay problema en el archivo
        }
    }
    static class ManejadorHTML extends HTMLEditorKit.ParserCallback {
        private BufferedWriter esctrLog1;
        private String archivoHTML;
        private int posicion = 0;

        public ManejadorHTML(BufferedWriter escritorLog, String archivoHTML) {
            this.esctrLog1 = escritorLog;
            this.archivoHTML = archivoHTML;
        }
        @Override
        public void handleText(char[] datos, int pos) {
            String texto = new String(datos).toLowerCase(); // convertir texto a minusculas

            int id = texto.indexOf(palabraBuscar);
            while (id != -1) {
                int posicionPalabra = posicion + id;
                System.out.printf("palabra en la posición: %d%n", posicionPalabra);

                // guardar la posicion de la palabra en el log
                try {
                    esctrLog1.write("archivo: " + archivoHTML + ", palabra: '" + palabraBuscar + "', posición: " + posicionPalabra);
                    esctrLog1.newLine();
                } catch (IOException e) {
                    LOG.severe("ha ocurrido un error al escribir en el archivo");
                }
                id = texto.indexOf(palabraBuscar, id + palabraBuscar.length());
            }
            // actualizar la posicion en el archivo
      posicion += datos.length;
        }
    }}
