//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.2.5-2 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: PM.10.23 a las 03:08:39 PM CEST 
//




import java.util.ArrayList;
import java.util.List;


public class Configuraciones {

    protected List<Configuraciones.Configuracion> configuracion;

    /**
     * Gets the value of the configuracion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configuracion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfiguracion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Configuraciones.Configuracion }
     * 
     * 
     */
    public List<Configuraciones.Configuracion> getConfiguracion() {
        if (configuracion == null) {
            configuracion = new ArrayList<Configuraciones.Configuracion>();
        }
        return this.configuracion;
    }


  
    public static class Configuracion {

        protected String nombre;
        protected String dificultad;
        protected String modeloNave;
        protected String modeloLuna;
        protected int id;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String value) {
            this.nombre = value;
        }

        public String getDificultad() {
            return dificultad;
        }

        public void setDificultad(String value) {
            this.dificultad = value;
        }


        public String getModeloNave() {
            return modeloNave;
        }

        public void setModeloNave(String value) {
            this.modeloNave = value;
        }

        public String getModeloLuna() {
            return modeloLuna;
        }

        public void setModeloLuna(String value) {
            this.modeloLuna = value;
        }

        public int getId() {
            return id;
        }

        public void setId(int value) {
            this.id = value;
        }

    }

}
