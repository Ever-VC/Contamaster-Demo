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
public class Auxiliar extends Usuario {
    
    public Auxiliar() {
        Rol rolAuxiliar = RolControlador.Instancia().GetRolPorId(3); // Busca el rol del administrador (registro con id 1 en la DB)
        this.setIdRolFk(rolAuxiliar);
    }
}
