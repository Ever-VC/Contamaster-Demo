/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package contamasterdemo;

import controllers.UsuarioControlador;
import java.util.Scanner;
import models.Usuario;

/**
 *
 * @author Ever Vásquez
 */
public class ContamasterDemo {

    /**
     * @param args the command line arguments
     */
    private static Scanner entrada = new Scanner(System.in);
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        // UsuarioControlador.Instancia().CrearAdministradorInicial();
        // System.out.println("[EL USUARIO ADMINISTRADOR SE HA CREADO EXITOSAMENETE]");
        MostrarLogin();
        boolean opcionValida = true;
        int opcion = 0;
        do {
            MostrarMenu();
            if (!entrada.hasNextInt()) {
                String texto = entrada.next();
                System.out.println(" |--------------------------------------------------------------------------------------------------| ");
                System.out.println(" |                                        [OPCION NO VALIDA]                                        | ");
                System.out.println(" |--------------------------------------------------------------------------------------------------| ");
            } else {
                opcion = entrada.nextInt();
                switch (opcion) {
                    case 1:
                        MostrarMenuUsuarios();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        System.out.println("GRACIAS POR PREFERIRNOS, HASTA LUEGO :)");
                        opcionValida = false;
                        break;
                    default:
                        System.out.println("[PARECE QUE OCURRIO UN ERROR, ESTAMOS TRABAJANDO EN ELLO]");
                        opcion = 0;
                        break;
                }
            }
        } while (opcionValida);//opcion == 0 || opcion > 5
    }
    
    private static void MostrarLogin() {
        boolean sesionIniciada = false;
        while (!sesionIniciada) {
            Usuario usuarioLogin = new Usuario();
            System.out.println("|----------------------> LOGIN <----------------------|");
            System.out.println("| INGRESE SU NOMBRE DE USUARIO: ");
            usuarioLogin.setUsername(entrada.nextLine());
            System.out.println("| INGRESE SU CONTRASEÑA: ");
            usuarioLogin.setPassword(entrada.nextLine());

            int resultado = UsuarioControlador.Instancia().ValidarLogin(usuarioLogin);

            if (resultado == -1) { // Caso que el usuario no exita
                System.out.println("EL USUARIO NO ESTA REGISTRADO EN LA DB");
            } else if (resultado == -2) { // Contrseña incorrecta
                System.out.println("EL USUARIO Y CONTRASEÑA NO COINCIDEN");
            } else {
                sesionIniciada = true;
            }
        }
    }
    
    private static void MostrarMenu() {
        System.out.println("***************************************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |      BIENVENIDO AL SISTEMA      | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |     [SELECCIONA UNA OPCION]     | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 1 => ADMINISTRAR USUARIOS       | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 2 => ADMINISTRAR EMPRESAS       | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 3 => ADMINI9STRAR CUENTAS       | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 4 => ADMINISTRAXION CONTABLE    | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 5 => CERRAR SESION              | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************");        
    }
    
    private static void MostrarMenuUsuarios() {
        System.out.println("***************************************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |   MENU DE GESTION DE USUARIOS   | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |     [SELECCIONA UNA OPCION]     | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 1 => AGREGAR NUEVO USUARIO      | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 2 => ACTUALIZAR UN USUARIO      | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 3 => ELIMINAR UN USUARIO       | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 4 => VER TODOS LOS USUARIOS    | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 5 => REGRESAR AL MENU PRINCIPAL | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************");  
    }
    
}
