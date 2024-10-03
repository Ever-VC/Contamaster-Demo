/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import connection.Conexion;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import models.Cuenta;

/**
 *
 * @author ever_vc
 */
public class CuentaControlador {
    private static CuentaControlador _instancia;
    private EntityManager _entityManager;
    
    public static CuentaControlador Instancia() {
        if (_instancia == null) {
            _instancia = new CuentaControlador();
        }
        return _instancia;
    }
    
    private EntityManager setEntityManager() {
        return Conexion.Instancia().getFabrica().createEntityManager();
    }
    
    public void CrearCuenta(Cuenta nuevaCuenta) {
        _entityManager = setEntityManager();
        _entityManager.getTransaction().begin();
        _entityManager.persist(nuevaCuenta);
        _entityManager.getTransaction().commit();
        _entityManager.close();
    }
    
    public Cuenta GetCuentaPorId(Integer id) {
        _entityManager = setEntityManager();
        _entityManager.getTransaction().begin();
        Cuenta cuentaBuscada = _entityManager.find(Cuenta.class, id);
        _entityManager.close();
        return cuentaBuscada;
    }
    
    public List<Cuenta> GetListaCuentas() {
        _entityManager = setEntityManager();
        _entityManager.getTransaction().begin();
        return _entityManager.createQuery("SELECT cuentas FROM Cuenta cuentas").getResultList();
    }
    
    public void ActualizarCuenta(Cuenta cuentaActualizada) {
        _entityManager = setEntityManager();
        _entityManager.getTransaction().begin();
        Cuenta cuentaAActualizar = _entityManager.find(Cuenta.class, cuentaActualizada.getId());
        cuentaAActualizar.setCodigo(cuentaActualizada.getCodigo());
        cuentaAActualizar.setNombre(cuentaActualizada.getNombre());
        cuentaAActualizar.setTipo(cuentaActualizada.getTipo());
        cuentaAActualizar.setSaldo(cuentaActualizada.getSaldo());
        cuentaAActualizar.setIdEmpresaFk(cuentaActualizada.getIdEmpresaFk());
        _entityManager.getTransaction().commit();
        _entityManager.close();
    }
    
    public void EliminarCuenta(Integer idCuentaEliminar) {
        _entityManager = setEntityManager();
        _entityManager.getTransaction().begin();
        Cuenta cuentaEliminar = _entityManager.find(Cuenta.class, idCuentaEliminar);
        _entityManager.remove(_entityManager.merge(cuentaEliminar));
        _entityManager.getTransaction().commit();
        _entityManager.close();
    }
    
}
