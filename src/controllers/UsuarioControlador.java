/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import connection.Conexion;
import java.security.MessageDigest;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import models.Rol;
import models.Usuario;

/**
 *
 * @author Ever Vásquez
 */
public class UsuarioControlador {
    private static UsuarioControlador _instancia;
    private EntityManager _entityManager;
    
    /***
     * Permite acceder de forma estática a los métodos de la clase.
     * @return _instancia
     */
    public static UsuarioControlador Instancia() {
        if (_instancia == null) {
            _instancia = new UsuarioControlador();
        }
        
        return _instancia;
    }
    
    private EntityManager setEntityManager() {
        return Conexion.Instancia().getFabrica().createEntityManager();
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(password.getBytes());
            byte[] rbt = md.digest();
            StringBuilder sb = new StringBuilder();
            
            for (byte b: rbt) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public void CrearAdministradorInicial() {
        Usuario admin = new Usuario();//Crea la instancia del usuario
        //Insertamos la información necesaria
        admin.setNombres("Administrador");
        admin.setApellidos("Sistema");
        admin.setSexo("Masculino");
        admin.setEmail("ever.oficial@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        
        //Crea un objeto del rol de tipo Administrador
        Rol rolAdministrador = RolControlador.Instancia().GetRolPorId(1);
        
        admin.setIdRolFk(rolAdministrador);//Indica el rol
        
        CrearUsuario(admin);//Crea el usuario
    }
    
    public int ValidarLogin(Usuario usuarioLogin) {
        _entityManager = setEntityManager();
        // Crear la consulta JPQL para leer el ID y la contraseña del usuario que coincide con el nombre de usuario
        String jpql = "SELECT u.id, u.password FROM Usuario u WHERE u.username = :nombreUsuario";

        try {
            _entityManager.getTransaction().begin();

            // Ejecutar la consulta para obtener el ID y la contraseña
            Object[] result = _entityManager.createQuery(jpql, Object[].class)
                .setParameter("nombreUsuario", usuarioLogin.getUsername())
                .getSingleResult();

            _entityManager.getTransaction().commit();
            _entityManager.close();

            // Extrae el ID y la contraseña del resultado
            Integer userId = (Integer) result[0];
            String password = (String) result[1];

            // Verificar si la contraseña obtenida coincide con la del objeto usuarioLogin
            if (password.equals(hashPassword(usuarioLogin.getPassword()))) {
                return userId; // Retorna el ID del usuario
            } else {
                return -2; // Contraseña incorrecta
            }
        } catch (NoResultException e) {
            // No se encontró ningún usuario con el nombre de usuario ingresado
            _entityManager.getTransaction().rollback();
            _entityManager.close();
            return -1; // Nombre de usuario no existe
        }
    }
    
    public Usuario GetUsuarioPorId(Integer id) {
        _entityManager = setEntityManager();
        _entityManager.getTransaction().begin();
        Usuario usuarioBuscado = _entityManager.find(Usuario.class, id);
        _entityManager.close();
        return usuarioBuscado;
    }
    
    public void CrearUsuario(Usuario nuevoUsuario) {
        nuevoUsuario.setPassword(hashPassword(nuevoUsuario.getPassword()));
        _entityManager = setEntityManager();
        _entityManager.getTransaction().begin();
        _entityManager.persist(nuevoUsuario);
        _entityManager.getTransaction().commit();
        _entityManager.close();
    }
}
