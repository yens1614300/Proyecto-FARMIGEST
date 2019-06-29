 
package Layer.ENTITY;
 
public class Producto {
    int idProducto;
    String nombre;
    String descripcion;
    double pre_venta;
    double pre_compra;
    String categoria;
    String marca;
    int stock;

    public Producto() {
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPre_venta() {
        return pre_venta;
    }

    public void setPre_venta(double pre_venta) {
        this.pre_venta = pre_venta;
    }

    public double getPre_compra() {
        return pre_compra;
    }

    public void setPre_compra(double pre_compra) {
        this.pre_compra = pre_compra;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    
    
}
