package com.miapp.controlador;

import com.miapp.modelo.Estudiante;
import com.miapp.modelo.Curso;
import com.miapp.modelo.Profesor;
import com.miapp.servicios.IBuscador;
import com.miapp.vista.EstudianteView;

import java.util.ArrayList;
import java.util.List;


public class EstudianteController implements IBuscador {

    // ── Constantes finales ────────────────────────────────────────────────────
    private static final int CANTIDAD_ESTUDIANTES_INICIALES = 12;
    private static final String MENSAJE_BUSQUEDA_VACIA = "Por favor ingrese un nombre para buscar.";
    private static final String MENSAJE_BUSQUEDA_CARRERA_VACIA = "Por favor seleccione una carrera para buscar.";
    private static final String MENSAJE_SIN_RESULTADOS = "No se encontraron estudiantes con ese criterio.";

    // ── Vista ─────────────────────────────────────────────────────────────────
    private EstudianteView vista;

    // ── Array de estudiantes (fuente de datos) ────────────────────────────────
    private Estudiante[] estudiantes;
    private List<Profesor> profesores;
    private List<Curso> cursos;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteController(EstudianteView vista) {
        this.vista = vista;
        this.profesores = new ArrayList<>();
        this.cursos = new ArrayList<>();
        // Primero cargar datos (inicializar estudiantes[])
        cargarDatos();
        // Luego asignar controlador a la vista (ahora es seguro acceder a estudiantes[])
        this.vista.setControlador(this);
    }

    // ── Implementación de la interfaz IBuscador ───────────────────────────────

    @Override
    public void cargarDatos() {
        inicializarEstudiantes();
        inicializarCursos();
        inicializarProfesores();
    }

    @Override
    public void buscarEstudiante(String criterio) {
        buscarPorCriterio(criterio);
    }

    @Override
    public void buscarEstudiantePorCarrera(String carrera) {
        buscarPorCarrera(carrera);
    }
    
    @Override
    public void buscarEstudiantePorCurso(String codigoCurso) {
        if(codigoCurso == null || codigoCurso.trim().isEmpty() || codigoCurso.equals("Seleccionar")){
            vista.mostrarError("Por favor seleccione un curso valido.");
            return;
        }
        Curso cursoEncontrado = obtenerCursoPorCodigo(codigoCurso);
        if(cursoEncontrado != null){
            List<Estudiante> inscritos  = cursoEncontrado.getEstudiantes();
            if(inscritos.isEmpty()){
                vista.mostrarMensaje("No hay estudiantes inscritos en el curso "+ codigoCurso);
                vista.mostrarEstudiantes(new ArrayList<>());
            } else {
                vista.mostrarEstudiantes(convertirAFilas(inscritos));
            }
        }
    }

    @Override
    public void buscarEstudiantePorEstado(String estadoMatricula) {
    }
    // ── Carga de datos iniciales ──────────────────────────────────────────────

    private void inicializarEstudiantes() {
        estudiantes = new Estudiante[CANTIDAD_ESTUDIANTES_INICIALES];

        // Reinicia el contador estático de Estudiante antes de cargar nuevos datos
        Estudiante.reiniciarContador();

        estudiantes[0]  = new Estudiante(1,  "Ana ","García",        "Ingeniería de Sistemas",  4.5);
        estudiantes[1]  = new Estudiante(2,  "Carlos"," López",      "Ingeniería Civil",        3.8);
        estudiantes[2]  = new Estudiante(3,  "María", "Rodríguez",   "Medicina",                4.9);
        estudiantes[3]  = new Estudiante(4,  "José ","Martínez",     "Derecho",                 3.5);
        estudiantes[4]  = new Estudiante(5,  "Laura ","Sánchez",     "Administración",          4.1);
        estudiantes[5]  = new Estudiante(6,  "Andrés ","Torres",     "Ingeniería de Sistemas",  3.9);
        estudiantes[6]  = new Estudiante(7,  "Valentina ","Gómez",   "Psicología",              4.3);
        estudiantes[7]  = new Estudiante(8,  "Luis ","Herrera",      "Economía",                3.7);
        estudiantes[8]  = new Estudiante(9,  "Sofía ","Díaz",        "Ingeniería Civil",        4.6);
        estudiantes[9]  = new Estudiante(10, "Juliana ","Morales",   "Medicina",                4.8);
        estudiantes[10] = new Estudiante(11, "Ana Milena ","Ruiz",   "Derecho",                 4.0);
        estudiantes[11] = new Estudiante(12, "Carlos Andrés ","Paz", "Administración",          3.6);

        // Log: informa cuántos estudiantes se cargaron usando static getTotalEstudiantes()
        System.out.println("Total de estudiantes cargados: " + Estudiante.getTotalEstudiantes());
    }
    private void inicializarCursos(){
        cursos.clear();
        cursos.add(new Curso("BDA150", 3));
        cursos.add(new Curso("JDB102", 4));
        cursos.add(new Curso("ARS828", 2));
    }
    
    private void inicializarProfesores(){
        profesores.clear();
        profesores.add(new Profesor(3200000.0, "Joseph","Saavedra",4));
        profesores.add(new Profesor(4000000.0,"Alexander","Rodriguez",6));
    }
    
    // --- Metodos para la gestion de cursos ---------------
     public List<Curso> getCursos() {
        return cursos;
    }
     
     public Curso obtenerCursoPorCodigo(String codigo){
         for(Curso c : cursos){
             if(c.getCodigo().equalsIgnoreCase(codigo)){
                 return c;
             }
         }
         return null;
     }

    // --- Metodos para la gestion de profesores -----------
     public List<Profesor> getProfesores() {
        return profesores;
    }
     
     public boolean agregarProfesor(String nombreCompleto, double salarioBase){
         if(nombreCompleto==null || nombreCompleto.trim().isEmpty()){
             vista.mostrarError("El nombre del Profesor no puede estar vacio.");
             return false;
         }
         int idNuevo = profesores.size()+1;
         String[] partes = nombreCompleto.trim().split(" ", 2);
         String nombre  = partes[0];
         String apellido = (partes.length > 1) ? partes[1] : "";
         
         Profesor nuevoProfesor = new Profesor(salarioBase, nombre, apellido, idNuevo);
         profesores.add(nuevoProfesor);
         return true;
     }
     
     public void asignarProfesorACurso(String nombreProfesor, String codigoCurso){
         Profesor prof = encontrarProfesorPorNombre(nombreProfesor);
         Curso curso = obtenerCursoPorCodigo(codigoCurso);
         if(prof != null && curso!=null){
             curso.setProfesorAsignado(prof);
             vista.mostrarMensaje("Profesor "+ prof.getNombre()+" "+prof.getApellido()+" asignado al curso "+curso.getCodigo()+".");
         } else{
             vista.mostrarError("No se pudo realizar el proceso, revise los datos.");
         }
     }
     
     public void buscarCursoPorProfesor(String nombreProfesor){
         Profesor prof= encontrarProfesorPorNombre(nombreProfesor);
         if(prof!=null){
             StringBuilder cursosAsignados = new StringBuilder();
             for(Curso c : cursos){
                 if(c.getProfesorAsignado()!=null && c.getProfesorAsignado().equals(prof)){
                     cursosAsignados.append("- ").append(c.getCodigo()).append(" (").append(c.getCreditos()).append(" creditos)\n");
                 }
             }
             if (cursosAsignados.length() > 0) {
                vista.mostrarMensaje("Cursos asignados a " + prof.getNombre() + " " + prof.getApellido() + ":\n" + cursosAsignados.toString());
             } else {
                vista.mostrarMensaje("El profesor " + prof.getNombre() + " " + prof.getApellido() + " no tiene cursos asignados.");
            }
        } else {
            vista.mostrarError("Profesor no encontrado.");
        }
     }
     public Profesor obtenerProfesorDeCurso(String codigoCurso) {
        Curso c = obtenerCursoPorCodigo(codigoCurso);
        return (c != null) ? c.getProfesorAsignado() : null;
    }
     
     private Profesor encontrarProfesorPorNombre(String nombreCompleto){
         if(nombreCompleto==null) return null;
         for(Profesor p: profesores){
             String completo = (p.getNombre()+ " "+ p.getApellido()).trim();
             if(completo.equalsIgnoreCase(nombreCompleto.trim()) || p.getNombre().equalsIgnoreCase(nombreCompleto.trim())){
                 return p;
             }
         }
         return null;
     }
     
    // ── Lógica de búsqueda ────────────────────────────────────────────────────
    private void buscarPorCriterio(String criterio) {

        // Validación básica usando constante final
        if (criterio == null || criterio.isEmpty()) {
            vista.mostrarError(MENSAJE_BUSQUEDA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        String criterioBajo = criterio.toLowerCase();

        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null && (e.getNombre().toLowerCase().contains(criterioBajo) ||
                e.getApellido().toLowerCase().contains(criterioBajo))) {
                resultados.add(e);
            }
        }

        if (resultados.isEmpty()) {
            vista.mostrarEstudiantes(new ArrayList<>()); // mostrará mensaje vacío
        } else if (resultados.size() == 1) {
            // Un solo resultado: usar vista.mostrarEstudiante(fila)
            vista.mostrarEstudiante(convertirAFila(resultados.get(0)));
        } else {
            // Varios resultados: mostrar lista completa ya convertida a filas
            vista.mostrarEstudiantes(convertirAFilas(resultados));
        }
    }

   
    private void buscarPorCarrera(String carrera) {
        // Validación básica usando constante final
        if (carrera == null || carrera.isEmpty() || carrera.equals("Seleccionar...")) {
            vista.mostrarError(MENSAJE_BUSQUEDA_CARRERA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();

        // Búsqueda exacta por carrera
        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null && e.getCarrera().equalsIgnoreCase(carrera)) {
                resultados.add(e);
            }
        }

        // Mostrar resultados (ya convertidos a filas, no como Estudiante)
        vista.mostrarEstudiantes(convertirAFilas(resultados));
    }

    
    private Object[] convertirAFila(Estudiante e) {
        return new Object[]{
            e.getId(),
            e.getNombre(),
            e.getApellido(),
            e.getCarrera(),
            String.format("%.2f", e.getPromedio())
        };
    }

  
    private List<Object[]> convertirAFilas(List<Estudiante> lista) {
        List<Object[]> filas = new ArrayList<>();
        for (Estudiante e : lista) {
            filas.add(convertirAFila(e));
        }
        return filas;
    }
    
    public Estudiante buscarEstudiantePorId(int id) {
        if(this.estudiantes != null){
            for(Estudiante e: estudiantes){
                if(e!=null && e.getId() == id){
                    return e;
                }
            }
        }
        return null;
    }
    
    public String[] obtenerCarrerasUnicas() {
        List<String> carreras = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null) {
                String carrera = e.getCarrera();
                if (!carreras.contains(carrera)) {
                    carreras.add(carrera);
                }
            }
        }
        return carreras.toArray(new String[0]);
    }

 
    public final int obtenerTotalEstudiantes() {
        return Estudiante.getTotalEstudiantes();
    }

   
    public boolean agregarEstudiante(String nombre, String apellido, String carrera, double promedio) {
        // Validación de datos
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() ||
            carrera == null || carrera.isEmpty()) {
            vista.mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        // Expandir el array si es necesario antes de agregar
        if (estudiantes.length == Estudiante.getTotalEstudiantes()) {
            // El array está lleno, crear uno más grande
            Estudiante[] nuevoArray = new Estudiante[estudiantes.length + 5];
            System.arraycopy(estudiantes, 0, nuevoArray, 0, estudiantes.length);
            estudiantes = nuevoArray;
        }

        // Obtener el índice donde se guardará el nuevo estudiante
        int indiceNuevoEstudiante = Estudiante.getTotalEstudiantes();

        // Crear nuevo estudiante con ID automático basado en el contador static
        int proximoId = Estudiante.getProximoId();
        Estudiante nuevoEstudiante = new Estudiante(proximoId, nombre, apellido, carrera, promedio);

        // Agregar el nuevo estudiante en la posición correcta
        estudiantes[indiceNuevoEstudiante] = nuevoEstudiante;

        // Mostrar mensaje de éxito
        vista.mostrarMensaje("Estudiante agregado correctamente.\nTotal de estudiantes: " +
                            Estudiante.getTotalEstudiantes());

        return true;
    }

    public void inscribirEstudianteEnCurso(int idEstudiante, String codigoCurso) {
        if(codigoCurso==null || codigoCurso.trim().isEmpty() || codigoCurso.equals("Seleccionar...")){
            vista.mostrarError("Debe seleccionar un curso valido");
            return;
        }
        Estudiante estudianteEncontrado = buscarEstudiantePorId(idEstudiante);
        if(estudianteEncontrado==null){
            vista.mostrarError("No se encontró al estudiante seleccionado");
            return;
        }
        Curso cursoEncontrado = obtenerCursoPorCodigo(codigoCurso);
        if(cursoEncontrado==null){
            vista.mostrarError("No se encontró el curso con codigo: "+codigoCurso);
            return;
        }
        boolean encontrado = estudianteEncontrado.inscribir(cursoEncontrado);
        if(encontrado){
            vista.mostrarMensaje("Estudiante " + estudianteEncontrado.getNombre()+" "+ estudianteEncontrado.getApellido() + " inscrito en el curso "+ codigoCurso+".") ;
            System.out.println("Inscritos en " + cursoEncontrado.getCodigo() + ": " + cursoEncontrado.getEstudiantes().size());
        } else{
            vista.mostrarError("El estudiante ya se encuentra inscrito en este curso o superó el maximo de materias.");
        }
    }
}