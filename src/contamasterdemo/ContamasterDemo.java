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
        /***
         * LAS DOS SIGUIENTES LINEAS DE COIDIGO COMENTADAS SE DEBEN EJECUTAR UNICAMENTE UNA VEZ, LA PRIMERA VEZ QUE SE EJECUTA EL PROGRAMA
         * @return Administrador
         */
        // UsuarioControlador.Instancia().CrearAdministradorInicial();
        // System.out.println("[EL USUARIO ADMINISTRADOR SE HA CREADO EXITOSAMENETE]");
        
        int idUsuarioLogueado = MostrarLogin();
        boolean opcionValida = true; // Valida la opción del menu principal
        int opcion; // Almacena la opción
        do {
            opcion = 0;
            MostrarMenu();
            if (!entrada.hasNextInt()) { // Valida que el dato ingresado sea un numero
                MostrarOpcionNoValida(); // Indica el mensaje de error
            } else {
                opcion = entrada.nextInt(); // Si todo va bien (ha ingresado corrrectamente un número), almacena el dato
                // Se ejecuta la opción seleccionada
                switch (opcion) {
                    case 1: // Caso de gestión de ususarios
                        boolean opValidaUsusarios = true;
                        int opGestionUsuarios;
                        do {
                            opGestionUsuarios = 0;
                            MostrarMenuUsuarios();
                            // Valida que la entrada sea limpia (un entero)
                            if (!entrada.hasNextInt()) {
                                MostrarOpcionNoValida(); // Indica el mensaje de error
                            } else {
                                opGestionUsuarios = entrada.nextInt(); // Si todo va bien (ha ingresado corrrectamente un número), almacena el dato
                                
                                switch (opGestionUsuarios) {
                                    case 1: // Caso de agregar ususario 
                                        System.out.println("Ingrese el nombre del nuevo usuario: ");
                                        
                                        break;
                                    case 2: // Caso de actualizar datos de un usuario
                                        break;
                                    case 3: // Eliminar un usuario
                                        break;
                                    case 4: // Ver lista de usuarios
                                        break;
                                    case 5: // Regresar al menu principal
                                        opValidaUsusarios = false;
                                        break;
                                    default: // Caso no permitido
                                        break;
                                }
                            }
                        } while (opValidaUsusarios);
                        MostrarMenuUsuarios();
                        break;
                    case 2: // Caso de gestión de empresas
                        break;
                    case 3: // Caso de gestión de cuentas
                        break;
                    case 4: // Caso de administración contable
                        break;
                    case 5: // Cerrar sesión
                        System.out.println("GRACIAS POR PREFERIRNOS, HASTA LUEGO :)");
                        opcionValida = false;
                        break;
                    default: // Opción no válida
                        System.out.println("[PARECE QUE OCURRIO UN ERROR, ESTAMOS TRABAJANDO EN ELLO]");
                        break;
                }
            }
        } while (opcionValida);//opcion == 0 || opcion > 5
    }
    
    private static int MostrarLogin() {
        boolean sesionIniciada = false;
        int resultado = 0;
        while (!sesionIniciada) {
            Usuario usuarioLogin = new Usuario();
            System.out.println("|----------------------> LOGIN <----------------------|");
            System.out.println("| INGRESE SU NOMBRE DE USUARIO: ");
            usuarioLogin.setUsername(entrada.nextLine());
            System.out.println("| INGRESE SU CONTRASEÑA: ");
            usuarioLogin.setPassword(entrada.nextLine());

            resultado = UsuarioControlador.Instancia().ValidarLogin(usuarioLogin);

            if (resultado == -1) { // Caso que el usuario no exita
                System.out.println("EL USUARIO NO ESTA REGISTRADO EN LA DB");
            } else if (resultado == -2) { // Contrseña incorrecta
                System.out.println("EL USUARIO Y CONTRASEÑA NO COINCIDEN");
            } else {
                sesionIniciada = true;
            }
        }
        return resultado;
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
        System.out.println("* | 3 => ADMINISTRAR CUENTAS        | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 4 => ADMINISTRACION CONTABLE    | *");
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
    
    private static void MostrarMenuTipoUsuario() {
         System.out.println("***************************************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |  SELECCIONE EL TIPO DE USUARIO  | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |     [SELECCIONA UNA OPCION]     | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 1 => ADMINISTRADOR              | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 2 => GERENTE                    | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 3 => CONTADOR                   | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 4 => AUXILIAR DE CONTADOR       | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 5 => REGRESAR AL MENU ANTERIOR  | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************"); 
    }
    
    private static void MostrarOpcionNoValida() {
        String texto = entrada.next();
        System.out.println(" |--------------------------------------------------------------------------------------------------| ");
        System.out.println(" |                                        [OPCION NO VALIDA]                                        | ");
        System.out.println(" |--------------------------------------------------------------------------------------------------| ");
    }
    
}
