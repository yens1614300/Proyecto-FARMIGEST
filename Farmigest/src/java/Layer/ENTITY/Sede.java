
package Layer.ENTITY;

public class Sede {
    int idSede;
    String direccion;
    String distrito;
    String departamento;

    public Sede() {
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    

    @Override
    public  String toString(){
        return direccion + " - " + distrito + " - " + departamento;
    }
    
}
