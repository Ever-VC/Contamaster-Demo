/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import controllers.RolControlador;

/**
 *
 * @author Ever Vásquez
 */
public class Contador extends Usuario{
    
    public Contador() {
        Rol rolContador = RolControlador.Instancia().GetRolPorId(2); // Busca el rol del administrador (registro con id 1 en la DB)
        this.setIdRolFk(rolContador);
    }
    
}
