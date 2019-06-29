
package Layer.ENTITY;

public class DetalleIngreso {
    int idDetalleingreso;
    int idIngreso;
    int idProducto;
    int idSede;
    int cantidad;
    int cantDisponible;
    int cantEntregada;
    Producto producto= new Producto();
    Sede sede=new Sede();
    

    public DetalleIngreso() {
    }

    public int getIdDetalleingreso() {
        return idDetalleingreso;
    }

    public void setIdDetalleingreso(int idDetalleingreso) {
        this.idDetalleingreso = idDetalleingreso;
    }

    public int getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(int idIngreso) {
        this.idIngreso = idIngreso;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantDisponible() {
        return cantDisponible;
    }

    public void setCantDisponible(int cantDisponible) {
        this.cantDisponible = cantDisponible;
    }

    public int getCantEntregada() {
        return cantEntregada;
    }

    public void setCantEntregada(int cantEntregada) {
        this.cantEntregada = cantEntregada;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }
    
    
    
}
