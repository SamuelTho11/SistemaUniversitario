package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EstudianteView extends JFrame {

    // ── Constantes finales para dimensiones ────────────────────────────────────
    private static final int ANCHO_VENTANA = 1000;
    private static final int ALTO_VENTANA = 700;
    private static final int ANCHO_CAMPO_BUSQUEDA = 18;
    private static final int ANCHO_CAMPO_AGREGAR = 12;
    private static final int ALTO_FILA_TABLA = 24;

    // ── Constantes finales para textos ─────────────────────────────────────────
    private static final String TITULO_VENTANA = "Gestión de Estudiantes — MVC (Búsqueda + Agregar)";
    private static final String TITULO_PANEL_BUSQUEDA = "Buscar estudiante por nombre";
    private static final String TITULO_PANEL_CARRERA = "Buscar por carrera";
    private static final String TITULO_PANEL_AGREGAR = "Agregar nuevo estudiante";
    private static final String TITULO_PANEL_RESULTADOS = "Resultados";
    private static final String LABEL_NOMBRE = "Nombre:";
    private static final String LABEL_APELLIDO = "Apellido:";
    private static final String LABEL_CARRERA = "Carrera:";
    private static final String LABEL_PROMEDIO = "Promedio:";
    private static final String BOTON_BUSCAR = "Buscar";
    private static final String BOTON_BUSCAR_CARRERA = "Buscar por Carrera";
    private static final String BOTON_LIMPIAR = "Limpiar";
    private static final String BOTON_AGREGAR = "Agregar Estudiante";
    private static final String OPCION_SELECCIONAR = "Seleccionar...";
    private static final String MENSAJE_INICIAL = "Ingrese un nombre o seleccione una carrera y presione Buscar.";
    private static final String MENSAJE_ENCONTRADO_UNO = "Se encontró 1 estudiante.";
    private static final String MENSAJE_ENCONTRADOS_VARIOS = "Se encontraron %d estudiante(s).";
    private static final String MENSAJE_SIN_RESULTADOS = "No se encontraron estudiantes con ese criterio.";
    
    private static final String TITULO_PANEL_CURSO = "Cursos: inscripción y consulta";
    private static final String LABEL_CURSO = "Curso:";
    private static final String BOTON_ESTUDIANTES_CURSO ="Ver estudiantes del curso";
    private static final String BOTON_INSCRIBIR_CURSO = "Inscribir en curso";
    private static final String TITULO_PANEL_PROFESOR = "Profesores: agregar y asignar a curso";
    private static final String LABEL_SALARIOBASE = "Salario base:";
    private static final String BOTON_AGREGAR_PROFESOR = "Agregar Profesor";
    private static final String LABEL_PROFESOR = "Profesor:";
    private static final String BOTON_CURSOS_PROFESOR = "Ver cursos del profesor";
    private static final String LABEL_CURSO_ASIGNAR = "Curso a asignar:";
    private static final String BOTON_ASIGNAR_CURSO = "Asignar a curso";

    // ── Constantes finales para colores ────────────────────────────────────────
    private static final Color COLOR_BOTON_FONDO = new Color(59, 139, 212);
    private static final Color COLOR_BOTON_CARRERA = new Color(76, 175, 80);
    private static final Color COLOR_BOTON_LIMPIAR = new Color(244, 67, 54);
    private static final Color COLOR_BOTON_AGREGAR = new Color(103, 58, 183);
    private static final Color COLOR_BOTON_TEXTO = Color.WHITE;
    private static final Color COLOR_ESTADO_TEXTO = Color.GRAY;
    
    private static final Color COLOR_BOTON_ESTUDIANTES_CURSO = new Color(0,153,123);
    private static final Color COLOR_BOTON_INSCRIBIR_CURSO = new Color(255,189,23);
    private static final Color COLOR_BOTON_AGREGAR_PROFESOR = new Color(46,24,186);

    // ── Columnas de la tabla (constante final) ─────────────────────────────────
    private static final String[] COLUMNAS_TABLA = {"ID", "Nombre", "Apellido", "Carrera", "Promedio", "Estado"};
    private static final int INDICE_PROMEDIO = 4;

    // ── Componentes UI - Búsqueda por nombre ────────────────────────────────────
    private JTextField             txtNombre;
    private JButton                btnBuscar;

    // ── Componentes UI - Búsqueda por carrera ──────────────────────────────────
    private JComboBox<String>      cmbCarrera;
    private JButton                btnBuscarCarrera;
    private JButton                btnLimpiar;

    // ── Componentes UI - Agregar estudiante ────────────────────────────────────
    private JTextField             txtAgregarNombre;
    private JTextField             txtAgregarApellido;
    private JComboBox<String>      cmbAgregarCarrera;
    private JSpinner               spinPromedio;
    private JButton                btnAgregar;
    
    // ---- Componentes UI - Cursos: Inscripcion y consulta
    private JButton btnVerEstudiantesCurso;
    private JButton btnInscribirCurso;
    private JComboBox<String> cmbCurso;
    
    // ---- Componentes UI - Profesores: agregar y asignar a curso
    private JTextField txtNombreProfesor;
    private JSpinner spinSalarioBase;
    private JButton btnAgregarProfesor;
    private JComboBox<String> cmbProfesor;
    private JButton btnCursosProfesor;
    private JComboBox<String> cmbCursoAsignar;
    private JButton btnAsignarCurso;

    // ── Componentes UI - Resultados y Estado ────────────────────────────────────
    private JTable                 tblResultados;
    private DefaultTableModel      modeloTabla;
    private JLabel                 lblEstado;
    private JLabel                 lblTotalEstudiantes;

    // ── Controlador ───────────────────────────────────────────────────────────
    private EstudianteController controlador;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteView() {
        initComponentes();
        initEventos();
    }

    // ── Inicialización de componentes ─────────────────────────────────────────

   
    private void initComponentes() {
        setTitle(TITULO_VENTANA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ────────────────────────────────────────────────────────────────────────
        // PANEL SUPERIOR: Búsqueda y Agregar (con GridLayout)
        // ────────────────────────────────────────────────────────────────────────

        // Panel búsqueda por nombre (Fila 1)
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_BUSQUEDA));

        JLabel lblNombre = new JLabel(LABEL_NOMBRE);
        txtNombre = new JTextField(ANCHO_CAMPO_BUSQUEDA);
        btnBuscar = new JButton(BOTON_BUSCAR);
        btnBuscar.setBackground(COLOR_BOTON_FONDO);
        btnBuscar.setForeground(COLOR_BOTON_TEXTO);
        btnBuscar.setFocusPainted(false);

        panelBusqueda.add(lblNombre);
        panelBusqueda.add(txtNombre);
        panelBusqueda.add(btnBuscar);

        // Panel búsqueda por carrera (Fila 2)
        JPanel panelCarrera = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelCarrera.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_CARRERA));

        JLabel lblCarrera = new JLabel(LABEL_CARRERA);
        cmbCarrera = new JComboBox<>();
        cmbCarrera.addItem(OPCION_SELECCIONAR);
        // Se carga después, cuando el controlador esté disponible

        btnBuscarCarrera = new JButton(BOTON_BUSCAR_CARRERA);
        btnBuscarCarrera.setBackground(COLOR_BOTON_CARRERA);
        btnBuscarCarrera.setForeground(COLOR_BOTON_TEXTO);
        btnBuscarCarrera.setFocusPainted(false);

        btnLimpiar = new JButton(BOTON_LIMPIAR);
        btnLimpiar.setBackground(COLOR_BOTON_LIMPIAR);
        btnLimpiar.setForeground(COLOR_BOTON_TEXTO);
        btnLimpiar.setFocusPainted(false);

        panelCarrera.add(lblCarrera);
        panelCarrera.add(cmbCarrera);
        panelCarrera.add(btnBuscarCarrera);
        panelCarrera.add(btnLimpiar);

        // Panel agregar estudiante (Fila 3)
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelAgregar.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_AGREGAR));

        JLabel lblAgregarNombre = new JLabel(LABEL_NOMBRE);
        txtAgregarNombre = new JTextField(ANCHO_CAMPO_AGREGAR);

        JLabel lblAgregarApellido = new JLabel(LABEL_APELLIDO);
        txtAgregarApellido = new JTextField(ANCHO_CAMPO_AGREGAR);

        JLabel lblAgregarCarrera = new JLabel(LABEL_CARRERA);
        cmbAgregarCarrera = new JComboBox<>();
        cmbAgregarCarrera.addItem(OPCION_SELECCIONAR);
        // Se carga después, cuando el controlador esté disponible

        JLabel lblAgregarPromedio = new JLabel(LABEL_PROMEDIO);
        spinPromedio = new JSpinner(new SpinnerNumberModel(3.0, 0.0, 5.0, 0.1));
        spinPromedio.setPreferredSize(new Dimension(60, 25));

        btnAgregar = new JButton(BOTON_AGREGAR);
        btnAgregar.setBackground(COLOR_BOTON_AGREGAR);
        btnAgregar.setForeground(COLOR_BOTON_TEXTO);
        btnAgregar.setFocusPainted(false);

        panelAgregar.add(lblAgregarNombre);
        panelAgregar.add(txtAgregarNombre);
        panelAgregar.add(lblAgregarApellido);
        panelAgregar.add(txtAgregarApellido);
        panelAgregar.add(lblAgregarCarrera);
        panelAgregar.add(cmbAgregarCarrera);
        panelAgregar.add(lblAgregarPromedio);
        panelAgregar.add(spinPromedio);
        panelAgregar.add(btnAgregar);
        
        // Panel para inscribir y consultar cursos
        JPanel panelCurso = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        panelCurso.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_CURSO));
        
        JLabel lblCurso = new JLabel(LABEL_CURSO);
        cmbCurso = new JComboBox<>();
        btnVerEstudiantesCurso = new JButton(BOTON_ESTUDIANTES_CURSO);
        btnVerEstudiantesCurso.setBackground(COLOR_BOTON_ESTUDIANTES_CURSO);
        btnVerEstudiantesCurso.setForeground(COLOR_BOTON_TEXTO);
        btnVerEstudiantesCurso.setFocusPainted(false);
        btnInscribirCurso = new JButton(BOTON_INSCRIBIR_CURSO);
        btnInscribirCurso.setBackground(COLOR_BOTON_INSCRIBIR_CURSO);
        btnInscribirCurso.setForeground(COLOR_BOTON_TEXTO);
        btnInscribirCurso.setFocusPainted(false);
        JLabel lblAvisoCurso = new JLabel("(primero busque y seleccione un estudiante en la tabla)");
        lblAvisoCurso.setForeground(COLOR_ESTADO_TEXTO);
        
        panelCurso.add(lblCurso);
        panelCurso.add(cmbCurso);
        panelCurso.add(btnVerEstudiantesCurso);
        panelCurso.add(btnInscribirCurso);
        panelCurso.add(lblAvisoCurso);
        
        // Panel para agregar y asignar curso a un profesor
        JPanel panelProfesor = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        panelProfesor.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_PROFESOR));
        
        JLabel lblNombreProfesor = new JLabel(LABEL_NOMBRE);
        txtNombreProfesor = new JTextField(ANCHO_CAMPO_AGREGAR);
        JLabel lblSalarioBase = new JLabel(LABEL_SALARIOBASE);
        spinSalarioBase = new JSpinner(new SpinnerNumberModel(3000000.0, 3000000.0, 5000000.0, 500.0));
        spinSalarioBase.setPreferredSize(new Dimension(90, 25));
        btnAgregarProfesor = new JButton(BOTON_AGREGAR_PROFESOR);
        btnAgregarProfesor.setBackground(COLOR_BOTON_AGREGAR_PROFESOR);
        btnAgregarProfesor.setForeground(COLOR_BOTON_TEXTO);
        btnAgregarProfesor.setFocusPainted(false);
        JLabel lblProfesor = new JLabel(LABEL_PROFESOR);
        cmbProfesor = new JComboBox<>();
        btnCursosProfesor = new JButton(BOTON_CURSOS_PROFESOR);
        btnCursosProfesor.setBackground(COLOR_BOTON_ESTUDIANTES_CURSO);
        btnCursosProfesor.setForeground(COLOR_BOTON_TEXTO);
        btnCursosProfesor.setFocusPainted(false);
        JLabel lblCursoAsignar = new JLabel(LABEL_CURSO_ASIGNAR);
        cmbCursoAsignar = new JComboBox<>();
        btnAsignarCurso = new JButton(BOTON_ASIGNAR_CURSO);
        btnAsignarCurso.setBackground(COLOR_BOTON_AGREGAR_PROFESOR);
        btnAsignarCurso.setForeground(COLOR_BOTON_TEXTO);
        btnAsignarCurso.setFocusPainted(false);
        
        panelProfesor.add(lblNombreProfesor);
        panelProfesor.add(txtNombreProfesor);
        panelProfesor.add(lblSalarioBase);
        panelProfesor.add(spinSalarioBase);
        panelProfesor.add(btnAgregarProfesor);
        panelProfesor.add(lblProfesor);
        panelProfesor.add(cmbProfesor);
        panelProfesor.add(btnCursosProfesor);
        panelProfesor.add(lblCursoAsignar);
        panelProfesor.add(cmbCursoAsignar);
        panelProfesor.add(btnAsignarCurso);

        // Panel superior con GridLayout (5 filas, 1 columna)
        JPanel panelSuperior = new JPanel(new GridLayout(5, 1, 5, 5));
        panelSuperior.add(panelBusqueda);
        panelSuperior.add(panelCarrera);
        panelSuperior.add(panelAgregar);
        panelSuperior.add(panelCurso);
        panelSuperior.add(panelProfesor);

        // ────────────────────────────────────────────────────────────────────────
        // PANEL CENTRAL: Tabla de resultados
        // ────────────────────────────────────────────────────────────────────────

        modeloTabla = new DefaultTableModel(COLUMNAS_TABLA, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblResultados = new JTable(modeloTabla);
        tblResultados.setRowHeight(ALTO_FILA_TABLA);
        tblResultados.getTableHeader().setReorderingAllowed(false);
        tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tblResultados);
        scroll.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_RESULTADOS));

        // ────────────────────────────────────────────────────────────────────────
        // PANEL INFERIOR: Estado y Total de estudiantes
        // ────────────────────────────────────────────────────────────────────────

        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));

        lblEstado = new JLabel(MENSAJE_INICIAL);
        lblEstado.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        lblEstado.setForeground(COLOR_ESTADO_TEXTO);

        lblTotalEstudiantes = new JLabel();
        lblTotalEstudiantes.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        lblTotalEstudiantes.setForeground(Color.BLUE);
        actualizarTotalEstudiantes();

        panelInferior.add(lblEstado, BorderLayout.WEST);
        panelInferior.add(lblTotalEstudiantes, BorderLayout.EAST);

        // ────────────────────────────────────────────────────────────────────────
        // Agregar todo al JFrame
        // ────────────────────────────────────────────────────────────────────────

        add(panelSuperior,    BorderLayout.NORTH);
        add(scroll,           BorderLayout.CENTER);
        add(panelInferior,    BorderLayout.SOUTH);
    }

    // ── Métodos de inicialización ─────────────────────────────────────────────

    /**
     * Carga las carreras disponibles desde el controlador al combo de búsqueda.
     */
    private void cargarCarreras() {
        if (controlador != null) {
            String[] carreras = controlador.obtenerCarrerasUnicas();
            for (String carrera : carreras) {
                cmbCarrera.addItem(carrera);
            }
        }
    }

    /**
     * Carga las carreras disponibles desde el controlador al combo de agregar.
     */
    private void cargarCarrerasAgregar() {
        if (controlador != null) {
            String[] carreras = controlador.obtenerCarrerasUnicas();
            for (String carrera : carreras) {
                cmbAgregarCarrera.addItem(carrera);
            }
        }
    }
    //Cargar los cursos disponibles desde el controlador al combo de busqueda
    private void cargarCursos(){
        if(controlador!=null && cmbCurso != null){
            cmbCurso.removeAllItems();
            cmbCurso.addItem(OPCION_SELECCIONAR);
            
            List<com.miapp.modelo.Curso> listaCursos = controlador.getCursos();
            if(listaCursos != null){
                for(com.miapp.modelo.Curso curso : listaCursos){
                    cmbCurso.addItem(curso.getCodigo());
                }
            }
        }
    }
    //Cargar los profesores registrados
    private void cargarProfesores(){
        if(controlador!=null &&cmbProfesor!=null){
            cmbProfesor.removeAllItems();
            cmbProfesor.addItem(OPCION_SELECCIONAR);
            
            List<com.miapp.modelo.Profesor> listaProfesores = controlador.getProfesores();
            if(listaProfesores!=null){
                for(com.miapp.modelo.Profesor p : listaProfesores){
                    cmbProfesor.addItem(p.getNombre());
                }
            }
        }
    }
    private void cargarCursosAsignar(){
        if(controlador!=null && cmbCursoAsignar!=null){
            cmbCursoAsignar.removeAllItems();
            cmbCursoAsignar.addItem(OPCION_SELECCIONAR);
            
            List<com.miapp.modelo.Curso> listaCursos = controlador.getCursos();
            if(listaCursos!=null){
                for(com.miapp.modelo.Curso curso : listaCursos){
                    cmbCursoAsignar.addItem(curso.getCodigo());
                }
            }
        }
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    /**
     * Método que encapsula la inicialización de eventos.
     */
    private void initEventos() {
        // Evento: buscar por nombre
        btnBuscar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                controlador.buscarEstudiante(txtNombre.getText().trim());
            }
        });

        txtNombre.addActionListener((ActionEvent e) -> btnBuscar.doClick());

        // Evento: buscar por carrera
        btnBuscarCarrera.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String carriSelected = (String) cmbCarrera.getSelectedItem();
                if (carriSelected != null && !carriSelected.equals(OPCION_SELECCIONAR)) {
                    controlador.buscarEstudiantePorCarrera(carriSelected);
                } else {
                    mostrarError("Seleccione una carrera válida.");
                }
            }
        });
        
        //Evento: Ver estudiantes del curso
        btnVerEstudiantesCurso.addActionListener((ActionEvent e) ->{
            if(controlador!=null){
                String cursoSeleccionado = (String) cmbCurso.getSelectedItem();
                if(cursoSeleccionado != null && !cursoSeleccionado.equals(OPCION_SELECCIONAR)){
                    controlador.buscarEstudiantePorCurso(cursoSeleccionado);
                } else {
                    mostrarError("Seleccione un curso valido de la lista.");
                }
            }
        });
        
        btnInscribirCurso.addActionListener((ActionEvent e) ->{
            if(controlador!=null){
                int filaSeleccionada = tblResultados.getSelectedRow();
                String cursoSeleccionado = (String) cmbCurso.getSelectedItem();
                if(filaSeleccionada == -1){
                    mostrarError("Seleccione un estudiante de la tabla");
                    return;
                }
                if(cursoSeleccionado == null || cursoSeleccionado.equals(OPCION_SELECCIONAR)){
                    mostrarError("Debe seleccionar un curso valido.");
                    return;
                }
                int idEstudiante = (int) tblResultados.getValueAt(filaSeleccionada, 0);
                controlador.inscribirEstudianteEnCurso(idEstudiante, cursoSeleccionado);
            }
        });

        // Evento: limpiar búsqueda
        btnLimpiar.addActionListener((ActionEvent e) -> {
            limpiarBusqueda();
        });

        // Evento: agregar nuevo estudiante
        btnAgregar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String nombre = txtAgregarNombre.getText().trim();
                String apellido = txtAgregarApellido.getText().trim();
                String carrera = (String) cmbAgregarCarrera.getSelectedItem();
                double promedio = (double) spinPromedio.getValue();

                if (controlador.agregarEstudiante(nombre, apellido, carrera, promedio)) {
                    // Limpiar formulario
                    txtAgregarNombre.setText("");
                    txtAgregarApellido.setText("");
                    cmbAgregarCarrera.setSelectedIndex(0);
                    spinPromedio.setValue(3.0);
                    actualizarTotalEstudiantes();
                }
            }
        });
        //Evento: Agregar profesor
        btnAgregarProfesor.addActionListener((ActionEvent e) -> {
            if(controlador!=null){
                String nombre = txtNombreProfesor.getText().trim();
                double salarioBase = (double) spinSalarioBase.getValue();
                if(nombre.isEmpty()){
                    mostrarError("Ingrese un nombre valido para profesor.");
                    return;
                }
                if(controlador.agregarProfesor(nombre, salarioBase)){
                    txtNombreProfesor.setText("");
                    spinSalarioBase.setValue(3000000.0);
                    cargarProfesores();
                    mostrarMensaje("Profesor agregado exitosamente.");
                }
            }
        });
        btnAsignarCurso.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String profesorSeleccionado = (String) cmbProfesor.getSelectedItem();
                String cursoSeleccionado = (String) cmbCursoAsignar.getSelectedItem();

            if (profesorSeleccionado == null || profesorSeleccionado.equals(OPCION_SELECCIONAR)) {
                mostrarError("Seleccione un profesor de la lista.");
                return;
             }

            if (cursoSeleccionado == null || cursoSeleccionado.equals(OPCION_SELECCIONAR)) {
                mostrarError("Seleccione un curso para asignar.");
                return;
            }
                controlador.asignarProfesorACurso(profesorSeleccionado, cursoSeleccionado);
            }
        });
        btnCursosProfesor.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String profesorSeleccionado = (String) cmbProfesor.getSelectedItem();
            if (profesorSeleccionado != null && !profesorSeleccionado.equals(OPCION_SELECCIONAR)) {
                controlador.buscarCursoPorProfesor(profesorSeleccionado);
             } else {
                mostrarError("Seleccione un profesor de la lista.");
                }
            }
        });
    }

    public void mostrarEstudiante(Object[] fila) {
        limpiarTabla();
        modeloTabla.addRow(fila);
        setEstado(MENSAJE_ENCONTRADO_UNO);
    }

    public void mostrarEstudiantes(List<Object[]> filas) {
        limpiarTabla();
        if (filas == null || filas.isEmpty()) {
            setEstado(MENSAJE_SIN_RESULTADOS);
            return;
        }
        for (Object[] fila : filas) {
            modeloTabla.addRow(fila);
        }
        setEstado(String.format(MENSAJE_ENCONTRADOS_VARIOS, filas.size()));
    }

    /**
     * Muestra un mensaje de error en la barra de estado.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        setEstado("Error: " + mensaje);
    }

    /**
     * Muestra un mensaje de información/éxito en la barra de estado.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
        setEstado(mensaje);
    }

    /**
     * Devuelve el texto ingresado en el campo de nombre.
     */
    public String getNombreBuscado() {
        return txtNombre.getText().trim();
    }

   
    public void setControlador(EstudianteController controlador) {
        this.controlador = controlador;
        cargarCarreras();
        cargarCarrerasAgregar();
        cargarCursos();
        cargarProfesores();
        cargarCursosAsignar();
        actualizarTotalEstudiantes();
    }


    private void actualizarTotalEstudiantes() {
        int total = (controlador != null) ? controlador.obtenerTotalEstudiantes() : 0;
        lblTotalEstudiantes.setText("Total de estudiantes: " + total);
    }

    /**
     * Limpia todos los campos de búsqueda y la tabla.
     */
    private void limpiarBusqueda() {
        txtNombre.setText("");
        cmbCarrera.setSelectedIndex(0);
        limpiarTabla();
        setEstado(MENSAJE_INICIAL);
    }

    /**
     * Limpia todas las filas de la tabla.
     */
    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    /**
     * Actualiza el texto del label de estado.
     */
    private void setEstado(String texto) {
        lblEstado.setText(texto);
    }
}