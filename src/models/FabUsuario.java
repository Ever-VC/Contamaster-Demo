/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Ever Vásquez
 */
public class FabUsuario {
    
    public FabUsuario() {}
    
    public static Usuario GetUsuario(int tipo) {
        Usuario usuario = null;
        
        if (tipo == 1) { // Administrador
            usuario = new Administrador();
        } else if (tipo == 2) { // Contador
            usuario = new Contador();
        } else if (tipo == 3) { // Auxiliar
            usuario = new Auxiliar();
        } else { // Gerente
            usuario = new Gerente();
        }
        return usuario;
    }
    
}
