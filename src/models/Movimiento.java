/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ever Vásquez
 */
@Entity
@Table(name = "movimiento", catalog = "contamaster", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Movimiento.findAll", query = "SELECT m FROM Movimiento m")})
public class Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "descripcion", length = 100)
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "debe", nullable = false, precision = 10, scale = 2)
    private BigDecimal debe;
    @Basic(optional = false)
    @Column(name = "haber", nullable = false, precision = 10, scale = 2)
    private BigDecimal haber;
    @JoinColumn(name = "id_cuenta_fk", referencedColumnName = "id")
    @ManyToOne
    private Cuenta idCuentaFk;
    @OneToMany(mappedBy = "idMovimientoFk")
    private Collection<DetalleAsiento> detalleAsientoCollection;

    public Movimiento() {
    }

    public Movimiento(Integer id) {
        this.id = id;
    }

    public Movimiento(Integer id, Date fecha, BigDecimal debe, BigDecimal haber) {
        this.id = id;
        this.fecha = fecha;
        this.debe = debe;
        this.haber = haber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public Cuenta getIdCuentaFk() {
        return idCuentaFk;
    }

    public void setIdCuentaFk(Cuenta idCuentaFk) {
        this.idCuentaFk = idCuentaFk;
    }

    public Collection<DetalleAsiento> getDetalleAsientoCollection() {
        return detalleAsientoCollection;
    }

    public void setDetalleAsientoCollection(Collection<DetalleAsiento> detalleAsientoCollection) {
        this.detalleAsientoCollection = detalleAsientoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimiento)) {
            return false;
        }
        Movimiento other = (Movimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Movimiento[ id=" + id + " ]";
    }
    
}
