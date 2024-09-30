/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package contamasterdemo;

import controllers.RolControlador;
import controllers.UsuarioControlador;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import models.FabUsuario;
import models.Rol;
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
    private static Usuario nuevoUsuario;
    
    public static void main(String[] args) throws ParseException {
        // TODO code application logic here
        /***
         * LAS DOS SIGUIENTES LINEAS DE COIDIGO COMENTADAS SE DEBEN EJECUTAR UNICAMENTE UNA VEZ, LA PRIMERA VEZ QUE SE EJECUTA EL PROGRAMA
         * @return Administrador
         */
        //UsuarioControlador.Instancia().CrearAdministradorInicial();
        //System.out.println("[EL USUARIO ADMINISTRADOR SE HA CREADO EXITOSAMENETE]");
        
        int idUsuarioLogueado = MostrarLogin();
        boolean opcionValida = true; // Valida la opción del menu principal
        int opcion; // Almacena la opción
        do {
            opcion = 0;
            System.out.println("----------------------------------------");
            System.out.println("| HA INICIADO SESION COMO: " + UsuarioControlador.Instancia().GetUsuarioPorId(idUsuarioLogueado).getNombres());
            System.out.println("----------------------------------------");
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
                                        boolean opTipoUsuario = true; // Permite verificar si el tipo de usuario está dentro de los permitido
                                        int opcionTipoUsuario;
                                        do {
                                            opcionTipoUsuario = 0;
                                            MostrarMenuTipoUsuario();
                                            // Valida que la entrada sea limpia (un entero)
                                            if (!entrada.hasNextInt()) {
                                                MostrarOpcionNoValida(); // Indica el mensaje de error
                                            } else {
                                                opcionTipoUsuario = entrada.nextInt(); // Si todo va bien (ha ingresado corrrectamente un número), almacena el dato
                                                
                                                if (opcionTipoUsuario == 5) {// Regresar al menu anterior
                                                    opTipoUsuario = false; // Indica que desea salir al menu anterior
                                                } else {
                                                    nuevoUsuario = NuevoUsuario(opcionTipoUsuario);
                                                    System.out.println("Tipo de usuario: " + nuevoUsuario.getIdRolFk().getNombre());
                                                    System.out.println("Nombres: " + nuevoUsuario.getNombres());
                                                    System.out.println("Apellidos: " + nuevoUsuario.getApellidos());
                                                    System.out.println("Sexo: " + nuevoUsuario.getSexo());
                                                    System.out.println("fecha nacimiento: " + nuevoUsuario.getFechaNacimiento());
                                                    System.out.println("Direccion: " + nuevoUsuario.getDireccion());
                                                    System.out.println("Email: " + nuevoUsuario.getEmail());
                                                    System.out.println("Usuario: " + nuevoUsuario.getUsername());
                                                    System.out.println("Password: " + nuevoUsuario.getPassword());
                                                    UsuarioControlador.Instancia().CrearUsuario(nuevoUsuario);
                                                    System.out.println("USUARIO CREADO EXITOSAMENTE, DESEA AGREGAR OTRO USUARIO? [Si/No]");
                                                    String op = entrada.nextLine();
                                                    if (op.equals("No")) {
                                                        opTipoUsuario = false; // Indica que desea salir al menu anterior
                                                    }
                                                }
                                            }
                                        } while (opTipoUsuario);
                                        
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
                                        MostrarOpcionNoValida();
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
                        //System.out.println("[PARECE QUE OCURRIO UN ERROR, ESTAMOS TRABAJANDO EN ELLO]");
                        MostrarOpcionNoValida();
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
        System.out.println("* | 2 => CONTADOR                   | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 3 => AUXILIAR DE CONTADOR       | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 4 => GERENTE                    | *");
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
    
    private static Usuario NuevoUsuario(int tipo) throws ParseException {
        Usuario _nuevoUsuario = new Usuario();
        Rol rol;
        switch (tipo) {
            case 1: // Administrador
                //_nuevoUsuario = FabUsuario.GetUsuario(1);
                //Crea un objeto del rol de tipo Administrador
                rol = RolControlador.Instancia().GetRolPorId(1);
        
                _nuevoUsuario.setIdRolFk(rol);//Indica el rol
                _nuevoUsuario = PedirInformacionDeUsuario(_nuevoUsuario);
                break;
            case 2: // Contador
                //_nuevoUsuario = FabUsuario.GetUsuario(2);
                rol = RolControlador.Instancia().GetRolPorId(2);
        
                _nuevoUsuario.setIdRolFk(rol);//Indica el rol
                _nuevoUsuario = PedirInformacionDeUsuario(_nuevoUsuario);
                break;
            case 3: // Auxiliar de contador
                //_nuevoUsuario = FabUsuario.GetUsuario(3);
                rol = RolControlador.Instancia().GetRolPorId(3);
        
                _nuevoUsuario.setIdRolFk(rol);//Indica el rol
                _nuevoUsuario = PedirInformacionDeUsuario(_nuevoUsuario);
                break;
            case 4: // Gerente
                //_nuevoUsuario = FabUsuario.GetUsuario(4);
                rol = RolControlador.Instancia().GetRolPorId(4);
        
                _nuevoUsuario.setIdRolFk(rol);//Indica el rol
                _nuevoUsuario = PedirInformacionDeUsuario(_nuevoUsuario);
                break;
            default: // Opcion no válida
                MostrarOpcionNoValida();
                break;
        }
        return _nuevoUsuario;
    }
    
    private static Usuario PedirInformacionDeUsuario(Usuario _nuevoUsuario) throws ParseException {
        System.out.println("[TODOS LOS CAMPOS QUE TENGAN UN '*' SON OBLIGATORIOS]");
        entrada.nextLine();
        System.out.println("Ingrese los nombres del nuevo usuario: [*] ");
        _nuevoUsuario.setNombres(entrada.nextLine());
        System.out.println("Ingrese los apellidos del nuevo usuario: [*] ");
        _nuevoUsuario.setApellidos(entrada.nextLine());
        System.out.println("Ingrese el sexo del nuevo usuario [Masculino/Femenino]");
        _nuevoUsuario.setSexo(entrada.nextLine());
        System.out.println("Ingrese la fecha de nacimiento del nuevo usuario con el siguiente formato [31/12/1999]: ");
        String fecha = entrada.nextLine();
        if (!"".equals(fecha)) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNacimiento;
            fechaNacimiento = formato.parse(fecha);
            _nuevoUsuario.setFechaNacimiento(fechaNacimiento);
        }
        System.out.println("Ingrese la direccion del nuevo usuario: ");
        _nuevoUsuario.setDireccion(entrada.nextLine());
        if (_nuevoUsuario.getIdRolFk().getId() == 1) {
            System.out.println("Ingrese el email del nuevo usuario: [*] ");
            _nuevoUsuario.setEmail(entrada.nextLine());
        } else {
            System.out.println("Ingrese el email del nuevo usuario: ");
            String email = entrada.nextLine();
            if (!"".equals(email)) {
                _nuevoUsuario.setEmail(email);
            }   
        }
        System.out.println("Ingrese el username para el nuevo usuario: ");
        _nuevoUsuario.setUsername(entrada.nextLine());
        System.out.println("Ingrese la contraseña para el nuevo usuario: ");
        _nuevoUsuario.setPassword(entrada.nextLine());
        return _nuevoUsuario;
    }
}
