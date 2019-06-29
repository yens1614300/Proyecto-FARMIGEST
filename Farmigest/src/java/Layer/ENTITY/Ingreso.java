
package Layer.ENTITY;

import java.util.ArrayList;

public class Ingreso {
    int idIngreso;
    String nroDocumento;
    String rucproveedor;
    String fechaIngreso;
    int idEmpleado;
    int idSede;
    String tipoDocumento;
    ArrayList<DetalleIngreso> detalle;

    public Ingreso() {
    }

    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getRucproveedor() {
        return rucproveedor;
    }

    public void setRucproveedor(String rucproveedor) {
        this.rucproveedor = rucproveedor;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public ArrayList<DetalleIngreso> getDetalle() {
        return detalle;
    }

    public void setDetalle(ArrayList<DetalleIngreso> detalle) {
        this.detalle = detalle;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }


    
}
