/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import connection.Conexion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import models.Cuenta;
import models.Empresa;

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
        return _entityManager.createQuery("SELECT c FROM Cuenta c").getResultList();
    }
    
    public List<Cuenta> GetListaCuentasPorEmpresa(Integer idEmpresa) {
        _entityManager = setEntityManager();  // Inicializa el EntityManager
        String jpql = "SELECT c FROM Cuenta c WHERE c.idEmpresaFk = :empresa";

        List<Cuenta> cuentas = new ArrayList<>();
        try {
            _entityManager.getTransaction().begin();  // Inicia la transacción

            // Busca la empresa por el ID
            Empresa empresa = _entityManager.find(Empresa.class, idEmpresa);

            if (empresa != null) {
                cuentas = _entityManager.createQuery(jpql, Cuenta.class)
                            .setParameter("empresa", empresa)  // Usa la entidad Empresa en lugar de Integer
                            .getResultList();  // Ejecuta la consulta y obtiene el resultado
            }

            _entityManager.getTransaction().commit();  // Hace commit de la transacción
        } catch (Exception ex) {
            if (_entityManager.getTransaction().isActive()) {
                _entityManager.getTransaction().rollback();  // Hace rollback en caso de error
            }
            ex.printStackTrace();  // Imprime el error para depuración
        } finally {
            if (_entityManager != null) {
                _entityManager.close();  // Cierra el EntityManager
            }
        }

        return cuentas;
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
