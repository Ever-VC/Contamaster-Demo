/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package contamasterdemo;

import controllers.CuentaControlador;
import controllers.EmpresaControlador;
import controllers.RolControlador;
import controllers.UsuarioControlador;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import models.Asiento;
import models.Cuenta;
import models.DetalleAsiento;
import models.Empresa;
import models.FabUsuario;
import models.Movimiento;
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
    private static Empresa nuevaEmpresa;
    private static Cuenta nuevaCuenta;
    
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
                List<Empresa> lstEmpresas;
                int idEmpresa;
                List<Cuenta> lstCuentas;
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
                                List<Usuario> lstUsuarios = UsuarioControlador.Instancia().GetListaUsuarios();
                                int idUsuario; // Almacena el id del usuario que se desee eliminar o actualizar
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
                                        System.out.println("LISTA DE USUARIOS: ");
                                        for (Usuario _usuario : lstUsuarios) {
                                            MostrarInfoDeUsuario(_usuario);
                                        }
                                        /**
                                         * FALTA VALIDAR EL TIPO DE DATO Y QUE EL ID EXISTA
                                        */
                                        System.out.println("INGRESE EL [ID] DEL USUARIO QUE DESEA MODIFICAR");
                                        idUsuario = entrada.nextInt();
                                        Usuario usuarioEditado = UsuarioControlador.Instancia().GetUsuarioPorId(idUsuario);
                                        usuarioEditado = PedirInformacionDeUsuario(usuarioEditado);
                                        UsuarioControlador.Instancia().ActualizarUsuario(usuarioEditado);
                                        System.out.println("USUARIO ACTUALIZADO EXITOSAMENTE");
                                        break;
                                    case 3: // Eliminar un usuario
                                        System.out.println("LISTA DE USUARIOS: ");
                                        for (Usuario _usuario : lstUsuarios) {
                                            MostrarInfoDeUsuario(_usuario);
                                        }
                                        /**
                                         * FALTA VALIDAR EL TIPO DE DATO Y QUE EL ID EXISTA
                                        */
                                        System.out.println("INGRESE EL [ID] DEL USUARIO QUE DESEA ELIMINAR");
                                        idUsuario = entrada.nextInt();
                                        UsuarioControlador.Instancia().EliminarUsuario(idUsuario);
                                        System.out.println("USUARIO ELIMINADO EXITOSAMENTE");
                                        break;
                                    case 4: // Ver lista de usuarios
                                        System.out.println("LISTA DE USUARIOS: ");
                                        for (Usuario _usuario : lstUsuarios) {
                                            MostrarInfoDeUsuario(_usuario);
                                        }
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
                        break;
                    case 2: // Caso de gestión de empresas
                        boolean opValidaEmpresas = true;
                        int opGestionEmpresas;
                        do {
                            opGestionEmpresas = 0;
                            MostrarMenuEmpresas();
                            // Valida que la entrada sea limpia (un entero)
                            if (!entrada.hasNextInt()) {
                                MostrarOpcionNoValida(); // Indica el mensaje de error
                            } else {
                                opGestionEmpresas = entrada.nextInt(); // Si todo va bien (ha ingresado corrrectamente un número), almacena el dato
                                lstEmpresas = EmpresaControlador.Instancia().GetListaEmpresas();
                                switch (opGestionEmpresas) {
                                    case 1: // Caso de agregar Empresa 
                                        nuevaEmpresa = NuevaEmpresa();
                                        EmpresaControlador.Instancia().CrearEmpresa(nuevaEmpresa);
                                        System.out.println("EMPRESA CREADA EXITOSAMENTE");
                                        break;
                                    case 2: // Caso de actualizar datos de una empresa
                                        System.out.println("LISTA DE EMPRESAS: ");
                                        for (Empresa _empresa : lstEmpresas) {
                                            MostrarInfoEmpresa(_empresa);
                                        }
                                        /**
                                         * FALTA VALIDAR EL TIPO DE DATO Y QUE EL ID EXISTA
                                        */
                                        System.out.println("INGRESE EL [ID] DE LA EMPRESA QUE DESEA MODIFICAR");
                                        idEmpresa = entrada.nextInt();
                                        Empresa empresaEditada = EmpresaControlador.Instancia().GetEmpresaPorId(idEmpresa);
                                        empresaEditada = PedirInformacionDeEmpresa(empresaEditada);
                                        EmpresaControlador.Instancia().ActualizarEmpresa(empresaEditada);
                                        System.out.println("EMPRESA ACTUALIZADA EXITOSAMENTE");
                                        break;
                                    case 3: // Eliminar una empresa
                                        System.out.println("LISTA DE EMPRESAS: ");
                                        for (Empresa _empresa : lstEmpresas) {
                                            MostrarInfoEmpresa(_empresa);
                                        }
                                        /**
                                         * FALTA VALIDAR EL TIPO DE DATO Y QUE EL ID EXISTA
                                        */
                                        System.out.println("INGRESE EL [ID] DE LA EMPRESA QUE DESEA ELIMINAR");
                                        idEmpresa = entrada.nextInt();
                                        EmpresaControlador.Instancia().EliminarEmpresa(idEmpresa);
                                        System.out.println("EMPRESA ELIMINADA EXITOSAMENTE");
                                        break;
                                    case 4: // Ver lista de empresas
                                        System.out.println("LISTA DE EMPRESAS: ");
                                        for (Empresa _empresa : lstEmpresas) {
                                            MostrarInfoEmpresa(_empresa);
                                        }
                                        break;
                                    case 5: // Regresar al menu principal
                                        opValidaEmpresas = false;
                                        break;
                                    default: // Caso no permitido
                                        MostrarOpcionNoValida();
                                        break;
                                }
                            }
                        } while (opValidaEmpresas);
                        break;
                    case 3: // Caso de gestión de cuentas
                        lstEmpresas = EmpresaControlador.Instancia().GetListaEmpresas();
                        if (lstEmpresas.size() < 1) {
                            System.out.println("NO HAY NINGUNA EMPRESA REGISTRADA EN LA BASE DE DATOS");
                            break;
                        }
                        System.out.println("LISTA DE EMPRESAS: ");
                        for (Empresa _empresa : lstEmpresas) {
                            MostrarInfoEmpresa(_empresa);
                        }
                        System.out.println("SELECCIONE EL [ID] DE LA EMPRESA DE LA CUAL SE ADMINISTRARAN SUS CUENTAS");
                        idEmpresa = entrada.nextInt();
                        lstCuentas = CuentaControlador.Instancia().GetListaCuentasPorEmpresa(idEmpresa);
                        boolean opValidaCuentas = true;
                        int opGestionCuentas;
                        do {
                            opGestionCuentas = 0;
                            MostrarMenuCuentas();
                            // Valida que la entrada sea limpia (un entero)
                            if (!entrada.hasNextInt()) {
                                MostrarOpcionNoValida(); // Indica el mensaje de error
                            } else {
                                opGestionCuentas = entrada.nextInt(); // Si todo va bien (ha ingresado corrrectamente un número), almacena el dato
                                int idCuenta; // Almacena el id de la cuenta que se desee eliminar o actualizar
                                switch (opGestionCuentas) {
                                    case 1: // Caso de agregar cuenta 
                                        nuevaCuenta = NuevaCuenta();
                                        nuevaCuenta.setIdEmpresaFk(EmpresaControlador.Instancia().GetEmpresaPorId(idEmpresa));
                                        CuentaControlador.Instancia().CrearCuenta(nuevaCuenta);
                                        System.out.println("EMPRESA CREADA EXITOSAMENTE");
                                        break;
                                    case 2: // Caso de actualizar datos de una cuenta
                                        System.out.println("LISTA DE CUENTAS: ");
                                        for (Cuenta _cuenta : lstCuentas) {
                                            MostrarInfoCuenta(_cuenta);
                                        }
                                        System.out.println("INGRESE EL [ID] DE LA CUENTA QUE DESEA ACTUALIZAR");
                                        idCuenta = entrada.nextInt();
                                        Cuenta cuentaEditada = CuentaControlador.Instancia().GetCuentaPorId(idCuenta);
                                        cuentaEditada = PedirInformacionDeCuenta(cuentaEditada);
                                        CuentaControlador.Instancia().ActualizarCuenta(cuentaEditada);
                                        System.out.println("CUENTA ACTUALIZADA EXITOSAMENTE");
                                        break;
                                    case 3: // Eliminar una cuenta
                                        System.out.println("LISTA DE CUENTAS: ");
                                        for (Cuenta _cuenta : lstCuentas) {
                                            MostrarInfoCuenta(_cuenta);
                                        }
                                        System.out.println("INGRESE EL [ID] DE LA CUENTA QUE DESEA ELIMINAR");
                                        idCuenta = entrada.nextInt();
                                        CuentaControlador.Instancia().EliminarCuenta(idCuenta);
                                        System.out.println("CUENTA ELIMINADA EXITOSAMENTE");
                                        break;
                                    case 4: // Ver lista de cuentas
                                        System.out.println("LISTA DE CUENTAS: ");
                                        for (Cuenta _cuenta : lstCuentas) {
                                            MostrarInfoCuenta(_cuenta);
                                        }
                                        break;
                                    case 5: // Regresar al menu principal
                                        opValidaCuentas = false;
                                        break;
                                    default: // Caso no permitido
                                        MostrarOpcionNoValida();
                                        break;
                                }
                            }
                        } while (opValidaCuentas);
                        break;
                    case 4: // Caso de administración contable
                        lstEmpresas = EmpresaControlador.Instancia().GetListaEmpresas();
                        if (lstEmpresas.size() < 1) {
                            System.out.println("NO HAY NINGUNA EMPRESA REGISTRADA EN LA BASE DE DATOS");
                            break;
                        }
                        System.out.println("LISTA DE EMPRESAS: ");
                        for (Empresa _empresa : lstEmpresas) {
                            MostrarInfoEmpresa(_empresa);
                        }
                        System.out.println("SELECCIONE EL [ID] DE LA EMPRESA DE LA CUAL SE ADMINISTRARAN SUS CUENTAS");
                        idEmpresa = entrada.nextInt();
                        lstCuentas = CuentaControlador.Instancia().GetListaCuentasPorEmpresa(idEmpresa);
                        boolean opValidaAsientos = true;
                        int opAsientos;
                        do {
                            opAsientos = 0;
                            MostrarMenuAdminContable();
                            // Valida que la entrada sea limpia (un entero)
                            if (!entrada.hasNextInt()) {
                                MostrarOpcionNoValida(); // Indica el mensaje de error
                            } else {
                                opAsientos = entrada.nextInt(); // Si todo va bien (ha ingresado corrrectamente un número), almacena el dato
                                switch (opAsientos) {
                                    case 1: // Caso de agregar asiento 
                                        Object[] objeto = CrearAsiento(lstCuentas);
                                        System.out.println("[...MODULO EN DESARROLO...]");
                                        break;
                                    case 2: // Caso de actualizar asiento
                                        System.out.println("[...MODULO EN DESARROLO...]");
                                        break;
                                    case 3: // Eliminar una asiento
                                        System.out.println("[...MODULO EN DESARROLO...]");
                                        break;
                                    case 4: // Ver lista de cuentas
                                        System.out.println("[...MODULO EN DESARROLO...]");
                                        break;
                                    case 5: // ver libro diario
                                        System.out.println("[...MODULO EN DESARROLO...]");
                                        break;
                                    case 6: // Crear libro mayor
                                        System.out.println("[...MODULO EN DESARROLO...]");
                                        break;
                                    case 7: // Regresar al menu principal
                                        opValidaAsientos = false;
                                        break;
                                    default: // Caso no permitido
                                        MostrarOpcionNoValida();
                                        break;
                                }
                            }
                        } while (opValidaAsientos);
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
    
    private static void MostrarInfoDeUsuario(Usuario usuario) {
        System.out.println("--------------------------------------------------------");
        System.out.println("Id de usuario: " + usuario.getId());
        System.out.println("Tipo de usuario: " + usuario.getIdRolFk().getNombre());
        System.out.println("Nombres: " + usuario.getNombres());
        System.out.println("Apellidos: " + usuario.getApellidos());
        System.out.println("Sexo: " + usuario.getSexo());
        System.out.println("Fecha nacimiento: " + (usuario.getFechaNacimiento() != null ? usuario.getFechaNacimiento() : "No definido"));
        System.out.println("Direccion: " + (usuario.getDireccion() != null ? usuario.getDireccion() : "No definido"));
        System.out.println("Email: " + (usuario.getEmail() != null ? usuario.getEmail() : "No definido"));
        System.out.println("Usuario: " + usuario.getUsername());
        System.out.println("--------------------------------------------------------");
    }
    
    private static void MostrarMenuEmpresas() {
        System.out.println("************************** *************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |   MENU DE GESTION DE EMPRESAS   | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |     [SELECCIONA UNA OPCION]     | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 1 => AGREGAR NUEVA EMPRESA      | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 2 => ACTUALIZAR UNA EMPRESA     | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 3 => ELIMINAR UNA EMPRESA       | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 4 => VER TODAS LAS EMPRESAS     | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 5 => REGRESAR AL MENU PRINCIPAL | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************");  
    }

    private static Empresa NuevaEmpresa() {
        return PedirInformacionDeEmpresa(new Empresa());
    }
    
    private static Empresa PedirInformacionDeEmpresa(Empresa _nuevaEmpresa) {
        System.out.println("[TODOS LOS CAMPOS QUE TENGAN UN '*' SON OBLIGATORIOS]");
        entrada.nextLine();
        System.out.println("Ingrese el nombre de la empresa:  [*] ");
        _nuevaEmpresa.setNombre(entrada.nextLine());
        System.out.println("Ingrese la direccion de la empresa:  [*] ");
        _nuevaEmpresa.setDireccion(entrada.nextLine());
        System.out.println("Ingrese el email de la empresa:  [*] ");
        _nuevaEmpresa.setEmail(entrada.next());
        return _nuevaEmpresa;
    }
    
    private static void MostrarInfoEmpresa(Empresa empresa) {
        System.out.println("--------------------------------------------------------");
        System.out.println("Id Empresa: " + empresa.getId());
        System.out.println("Nombre de la empresa: " + empresa.getNombre());
        System.out.println("Direccion: " + empresa.getDireccion());
        System.out.println("Email: " + empresa.getEmail());
        System.out.println("--------------------------------------------------------");
    }
    
    private static void MostrarMenuCuentas() {
        System.out.println("************************** *************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |   MENU DE GESTION DE CUENTAS    | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |     [SELECCIONA UNA OPCION]     | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 1 => AGREGAR NUEVA CUENTA       | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 2 => ACTUALIZAR UNA CUENTA      | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 3 => ELIMINAR UNA CUENTA        | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 4 => VER TODAS LAS CUENTAS      | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 5 => REGRESAR AL MENU PRINCIPAL | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************");
    }
    
    private static Cuenta NuevaCuenta() {
        return PedirInformacionDeCuenta(new Cuenta());
    }
    
    private static Cuenta PedirInformacionDeCuenta(Cuenta _nuevaCuenta) {
        System.out.println("[TODOS LOS CAMPOS QUE TENGAN UN '*' SON OBLIGATORIOS]");
        entrada.nextLine();
        System.out.println("Codigo de la cuenta: ");
        _nuevaCuenta.setCodigo(entrada.nextLine());
        System.out.println("Nombre de la cuenta");
        _nuevaCuenta.setNombre(entrada.nextLine());
        System.out.println("Tipo de cuenta [Activo/Pasivo/Capital/Ingresos/Gastos/Retiros]");
        _nuevaCuenta.setTipo(entrada.nextLine());
        System.out.println("Saldo de la cuenta: ");
        _nuevaCuenta.setSaldo(entrada.nextBigDecimal());
        return _nuevaCuenta;
    }
    
    private static void MostrarInfoCuenta(Cuenta cuenta) {
        System.out.println("--------------------------------------------------------");
        System.out.println("Id: " + cuenta.getId());
        System.out.println("Codigo: " + cuenta.getCodigo());
        System.out.println("Nombre: " + cuenta.getNombre());
        System.out.println("Tipo: " + cuenta.getTipo());
        System.out.println("Saldo: " + cuenta.getSaldo());
        System.out.println("--------------------------------------------------------");
    }
    
    private static void MostrarMenuAdminContable() {
        System.out.println("************************** *************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |    MENU DE GESTION CONTABLE     | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************");
        System.out.println("* |---------------------------------| *");
        System.out.println("* |     [SELECCIONA UNA OPCION]     | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 1 => AGREGAR NUEVO ASIENTO      | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 2 => ACTUALIZAR UN ASIENTO      | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 3 => ELIMINAR UN ASIENTO        | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 4 => VER LIBRO DIARIO           | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 5 => MAYORISAR CUENTAS          | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 6 => BALANZA DE COMPROBACION    | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("* | 7 => REGRESAR AL MENU PRINCIPAL | *");
        System.out.println("* |---------------------------------| *");
        System.out.println("***************************************"); 
    }
    
    private static Object[] CrearAsiento(List<Cuenta> lstCuentas) throws ParseException {
        List<Movimiento> lstMovimientos = new ArrayList();
        return PedirInformacionDeAsiento(new Object[]{new Asiento(), lstMovimientos, new DetalleAsiento(), lstCuentas});
    }
    
    private static Object[] PedirInformacionDeAsiento(Object[] obj) throws ParseException {
        double total_debe = 0.00;
        double total_haber = 0.00;
        Asiento asiento = (Asiento) obj[0];
        List<Movimiento> lstMovimientos = (List<Movimiento>) obj[1];
        DetalleAsiento detalleAsiento = (DetalleAsiento) obj[2];
        List<Cuenta> lstCuentas = (List<Cuenta>) obj[3];
        boolean nuevoMovimiento = true;
        System.out.println("IMPORTANTE: TODOS LOS CAMPOS SON OBLIGATORIOS");
        entrada.nextLine();
        System.out.println("Ingrese la fecha para los movimientos, con el siguienteel formato a usar es el siguiente: [31/12/1999]: ");
        String fecha = entrada.nextLine();
        Date fechaNacimiento = new Date();
        if (!"".equals(fecha)) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            fechaNacimiento = formato.parse(fecha);
        }
        
        
        do {
            Movimiento _nuevoMovimiento = PedirInfoMovimiento(lstCuentas);
            _nuevoMovimiento.setFecha(fechaNacimiento);
            total_debe += _nuevoMovimiento.getDebe().doubleValue();
            total_haber += _nuevoMovimiento.getHaber().doubleValue();
            lstMovimientos.add(_nuevoMovimiento);
            System.out.println("DETALLE DE ASIENTO");
            System.out.println("|-------------------------------------------------------------------------|");
            System.out.println("| FECHA\t\t |         DESCRIPCION\t\t | DEBE\t | HABER\t |");
            System.out.println("|-------------------------------------------------------------------------|");
            for (Movimiento _movto : lstMovimientos) {
                MostrarDetalleMovimiento(_movto);
            }
            
            System.out.println("-----------------------------------------");
            System.out.println("Desea agregar otro movimiento? [Si/No]");
            System.out.println("-----------------------------------------");
            String respuesta = entrada.nextLine();
            if (respuesta.equals("No")) {
                if (total_debe == total_haber) {
                    nuevoMovimiento = false;
                } else {
                    System.out.println("IMPOSIBLE SALIR, NO SE CUMPLE LA IGUALDAD EN EL DEBE Y EL HABER");
                    entrada.nextLine();
                }
                
            }
        } while (nuevoMovimiento);
        
        return null;
    }
    
    private static Movimiento PedirInfoMovimiento(List<Cuenta> lstCuentas) {
        Movimiento _nuevoMovimiento = new Movimiento();
        System.out.println("Ingrese la descipción del movimiento: ");
        _nuevoMovimiento.setDescripcion(entrada.nextLine());
        System.out.println("Ingrese el valor para el debe: ");
        _nuevoMovimiento.setDebe(entrada.nextBigDecimal());
        System.out.println("Ingrese el valor para el haber: ");
        _nuevoMovimiento.setHaber(entrada.nextBigDecimal());
        System.out.println("LISTA DE CUENTAS: ");
        for (Cuenta _cuenta : lstCuentas) {
            System.out.println("ID [" + _cuenta.getId() + "], Nombre: " + _cuenta.getNombre());
        }
        System.out.println("INGRESE EL [ID] DE LA CUENTA A LA QUE PERTENECE EL MOVIMIENTO: ");
        int idCuenta = entrada.nextInt();
        Cuenta cuentaModificada = CuentaControlador.Instancia().GetCuentaPorId(idCuenta);
        _nuevoMovimiento.setIdCuentaFk(cuentaModificada);
        String tipoCuenta = cuentaModificada.getTipo();
        // ESTO DEBERIA HACERSE EN LA MAYORIZACION
        switch (tipoCuenta) {
            case "Activo": // Aumenta en el debe y disminuye en el haber
                break;
            case "Pasivo": // Disminuye en el debe y aumenta en el haber
                break;
            case "Capital": // Disminuye en el debe y aumenta en el haber
                break;
            case "Ingresos": // Disminuye en el debe y aumenta en el haber
                break;
            case "Gastos": // Aumenta en el debe y disminuye en el haber
                break;
            case "Retiros": // Aumenta en el debe y disminuye en el haber
                break;
        }
        return _nuevoMovimiento;
    }
    
    private static void MostrarDetalleMovimiento(Movimiento _movimiento) {
        
        Date fecha = _movimiento.getFecha();
        SimpleDateFormat formatoCorto = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = formatoCorto.format(fecha);
        
        System.out.println("| " + fechaFormateada + "\t | " + _movimiento.getDescripcion() + "\t | " + _movimiento.getDebe() + "\t | " + _movimiento.getHaber() + "\t |");
        System.out.println("|--------------------------------------------------------------------------|");
        entrada.nextLine();

    }
}
