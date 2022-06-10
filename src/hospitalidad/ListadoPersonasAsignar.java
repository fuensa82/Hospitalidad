/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospitalidad;  

import hospitalidad.Gestores.GestionPersonasBD;
import hospitalidad.Gestores.GestionTiposViajeroBD;
import hospitalidad.Gestores.GestionViajesBD;
import hospitalidad.beans.PersonaBean;
import java.awt.Window;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vPalomo
 */
public class ListadoPersonasAsignar extends javax.swing.JPanel {

    private boolean seleccionFila = false;
    //private ArrayList<PersonaBean> listaPersona; prueba
    
    private String idViaje;
    private String boton;//G-> Guardar  C->Cancelar
    private ArrayList<PersonaBean> listaPersonasSelec;
    private String tipoViajero;
    private String modoVentana;
    
    public static final String AsignarABus="AsignarABus";
    public static final String AsignarAViaje="AsignarAViaje";
    public static final String AsignarAHabitacion="AsignarAHabitacion";

    public String getModoVentana() {
        return modoVentana;
    }

    public String getTipoViajero() {
        return tipoViajero;
    }

    public ArrayList<PersonaBean> getListaPersonasSelec() {
        return listaPersonasSelec;
    }

    public String getBoton() {
        return boton;
    }

    public String getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(String idViaje) {
        this.idViaje = idViaje;
    }
    
    
    /**
     * Genera la tabla para poder asignar personas que falta por asignar al viaje, al autobus o al alojamiento.
     * @param idViaje id del viaje a consultar
     * @param modoVentana AsignarABus AsignarAViaje AsignarAAlojamiento son los 3 posibles valores que puede tomar este campo.
     */
    public ListadoPersonasAsignar(String idViaje, String modoVentana) {
        this.idViaje=idViaje;
        this.modoVentana=modoVentana;
        initComponents();
        cargarListaPersonas("0","");
        ponListenerTabla(jTablePersonas);
    }

    private void cargarListaPersonas(String filtro, String textoFiltro) {
        ArrayList<PersonaBean> listaPersona=null;
        if(AsignarABus.equals(this.modoVentana)){
            listaPersona = GestionPersonasBD.getListaPersonasSinAutobus(idViaje, filtro, textoFiltro);
        }else if(AsignarAViaje.equals(this.modoVentana)){
            if("0".equals(filtro)){
                listaPersona = GestionPersonasBD.getListaPersonasSinViaje(idViaje, true, textoFiltro);
            }else{
                listaPersona = GestionPersonasBD.getListaPersonasSinViajeConEquipoDefinido(idViaje, true, filtro,textoFiltro);
            }
        }else if(AsignarAHabitacion.equals(this.modoVentana)){
            listaPersona = GestionPersonasBD.getListaPersonasSinHabitacion(idViaje, filtro, textoFiltro);
        }
        listaPersona.sort(new Comparator<PersonaBean>() {
            @Override
            public int compare(PersonaBean p1, PersonaBean p2) {
                Collator c = Collator.getInstance(new Locale("es"));
                c.setStrength(Collator.PRIMARY);
                return c.compare(p1.getApellidos(), p2.getApellidos());
            }
        });
        //Primero se borra todo el contenido de la tabla
        DefaultTableModel datosTabla = (DefaultTableModel) jTablePersonas.getModel();
        for (int i = datosTabla.getRowCount(); i > 0; i--) {
            datosTabla.removeRow(i - 1);
        }
        if(!AsignarAViaje.equals(this.modoVentana)){
            listaPersona.forEach((persona) -> {
                datosTabla.addRow(new Object[]{
                    false,
                    persona.getApellidos(),
                    persona.getNombre(),
                    persona.getDNI(),
                    persona.getIdPersona(),
                    persona.getNombreCortoTipoViajero(),
                    persona.getIdTipoViajero()
                });
            });
        }else{
            listaPersona.forEach((persona) -> {
                datosTabla.addRow(new Object[]{
                    false,
                    persona.getApellidos(),
                    persona.getNombre(),
                    persona.getDNI(),
                    persona.getIdPersona(),
                    persona.getNombreCortoTipoViajero(),
                    persona.getIdTipoViajero()
                });
            });
        }
        jLabelTotal.setText(""+listaPersona.size());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePersonas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jTextFiltro = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jTablePersonas.setAutoCreateRowSorter(true);
        jTablePersonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sel.", "Apellidos", "Nombre", "DNI", "Id", "Tipo", "IdTipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePersonas.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTablePersonas);
        if (jTablePersonas.getColumnModel().getColumnCount() > 0) {
            jTablePersonas.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTablePersonas.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTablePersonas.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTablePersonas.getColumnModel().getColumn(3).setPreferredWidth(70);
            jTablePersonas.getColumnModel().getColumn(4).setPreferredWidth(25);
            jTablePersonas.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTablePersonas.getColumnModel().getColumn(6).setPreferredWidth(10);
        }

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton6.setText("Guardar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel1.setText("Filtro: ");

        jComboBox1.setModel(GestionTiposViajeroBD.getModeloComboTipoViajero(true));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Total lista:");

        jLabelTotal.setText("jLabel3");

        jButton2.setText("Burcar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Limpiar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(jTextFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton6)
                    .addComponent(jLabel2)
                    .addComponent(jLabelTotal))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Window w = SwingUtilities.getWindowAncestor(this);
        w.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(!seleccionFila){
//            jTablePersonas.getColumnModel().getColumn(5).setWidth(0);
//            jTablePersonas.getColumnModel().getColumn(5).setMaxWidth(0);
//            jTablePersonas.getColumnModel().getColumn(5).setMinWidth(0);
            jTablePersonas.removeColumn(jTablePersonas.getColumnModel().getColumn(5));
            
            JOptionPane.showMessageDialog(this, "Primero debe seleccionar al menos una persona");
            return;
        }
        //DefaultTableModel datosTabla = (DefaultTableModel) jTablePersonas.getModel();
        listaPersonasSelec = new ArrayList<PersonaBean>();
        for (int i = 0; i < jTablePersonas.getRowCount(); i++) {
            if ((boolean) jTablePersonas.getValueAt(i, 0)) {
                PersonaBean persona = new PersonaBean();
                persona.setIdPersona((String) jTablePersonas.getValueAt(i, 4));
                persona.setNombre((String) jTablePersonas.getValueAt(i, 2));
                persona.setApellidos((String) jTablePersonas.getValueAt(i, 1));
                persona.setIdTipoViajero((String) jTablePersonas.getValueAt(i, 6));
                listaPersonasSelec.add(persona);
            }
        }
        if(listaPersonasSelec.size()<1){
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos una persona");
            return;
        }
        if(ListadoPersonasAsignar.AsignarABus.equals(this.modoVentana)){
           boton="G";
        }else if(ListadoPersonasAsignar.AsignarAHabitacion.equals(this.modoVentana)){
           boton="G";
        }else if(ListadoPersonasAsignar.AsignarAViaje.equals(this.modoVentana)){
            JDialog frame = new JDialog((JFrame) null, "Guardar", true);
            frame.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            OpcionesGuardarPersonaViajeModal ventana = new OpcionesGuardarPersonaViajeModal("2",GestionViajesBD.getViaje(idViaje).getNombre());
            frame.getContentPane().add(ventana);
            frame.pack();
            frame.setLocationRelativeTo(this);
            frame.setVisible(true);
            boton = ventana.getBoton();
            if("G".equalsIgnoreCase(boton)){
                tipoViajero = ventana.getTipoViajero();
                Window w = SwingUtilities.getWindowAncestor(this);
                w.setVisible(false);
            }
        }
        Window w = SwingUtilities.getWindowAncestor(this);
        w.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        //actuador del Combo
        String filtro = jComboBox1.getModel().getElementAt(jComboBox1.getSelectedIndex()).split(" - ")[0];
        cargarListaPersonas(filtro,jTextFiltro.getText());
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Boton de buscar por filtro.
        String filtro = jComboBox1.getModel().getElementAt(jComboBox1.getSelectedIndex()).split(" - ")[0];
        String filtroTexto=jTextFiltro.getText();
        cargarListaPersonas(filtro, filtroTexto);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         //Limpiar
        jTextFiltro.setText("");
        String filtro = jComboBox1.getModel().getElementAt(jComboBox1.getSelectedIndex()).split(" - ")[0];
        String filtroTexto=jTextFiltro.getText();
        cargarListaPersonas(filtro, filtroTexto);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void ponListenerTabla(JTable tabla) {
        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evento) {
                System.out.println("Seleccionado");
                seleccionFila = true;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePersonas;
    private javax.swing.JTextField jTextFiltro;
    // End of variables declaration//GEN-END:variables
}
