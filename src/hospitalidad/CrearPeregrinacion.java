/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hospitalidad;

import hospitalidad.Gestores.GestionAutobusesBD;
import hospitalidad.Gestores.GestionHabitacionesBD;
import hospitalidad.Gestores.GestionViajesBD;
import hospitalidad.beans.ViajeBean;
import java.awt.Window;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author vPalomo
 */
public class CrearPeregrinacion extends javax.swing.JPanel {

    public static String GUARDAR="guardar";
    public static String CANCELAR="concelar";
    private Principal padre;

    private String opcionBoton;
    /**
     * Creates new form CrearHotel
     */
    public CrearPeregrinacion(Principal padre) {
        initComponents();
        this.padre=padre;
    }
    public CrearPeregrinacion() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Nueva peregrinación");

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Peregrinación:");

        jLabel3.setText("Fecha inicio:");

        jLabel4.setText("Fecha fin:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel5.setText("dd/mm/aaaa");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel6.setText("dd/mm/aaaa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(188, 188, 188))
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(6, 6, 6))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(validarDatos()){
            ViajeBean viaje=new ViajeBean();
            viaje.setNombre(jTextField1.getText());
            
            viaje.setFechaIni(jTextField2.getText());
            viaje.setFechaFin(jTextField3.getText());
            opcionBoton=GUARDAR;
            if(GestionViajesBD.setViaje(viaje)!=0){
                int p1=JOptionPane.showConfirmDialog(padre, "¿Quiere generar los mismos autobuses que el año anterior?", "Copiar autobuses", JOptionPane.YES_NO_OPTION);
                System.out.println("Respuesta: "+p1);
                if(p1==0){
                    copiarAutobuses();
                }
                int p2=JOptionPane.showConfirmDialog(padre, "¿Quiere generar las mismas habitaciones que el año anterior?", "Copiar habitaciones de hotel", JOptionPane.YES_NO_OPTION);
                System.out.println("Respuesta: "+p2);
                if(p2==0){
                    copiarHabitaciones();
                }
                Window w = SwingUtilities.getWindowAncestor(this);
                w.setVisible(false);
                padre.recargarComboViaje();
            }else{
                JOptionPane.showMessageDialog(this, "Se ha producido algún error a la hora de dar de alta la peregrinación y no se ha dado de alta");
            }
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private boolean validarDatos() {
        if("".equals(jTextField1.getText())){
            JOptionPane.showMessageDialog(this, "Debe rellenar el nombre de la peregrinación");
            jTextField1.requestFocus();
            return false;
        }else if("".equals(jTextField2.getText())){
            JOptionPane.showMessageDialog(this, "Debe rellenar fecha de inicio");
            jTextField2.requestFocus();
            return false;
        }else if("".equals(jTextField3.getText())){
            JOptionPane.showMessageDialog(this, "Debe rellenar la fecha de fin");
            jTextField3.requestFocus();
            return false;
        }else{
            return true;
        }
        
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        opcionBoton=CANCELAR;
        Window w = SwingUtilities.getWindowAncestor(this);
        w.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    public static String getGUARDAR() {
        return GUARDAR;
    }

    public static void setGUARDAR(String GUARDAR) {
        CrearPeregrinacion.GUARDAR = GUARDAR;
    }

    public static String getCANCELAR() {
        return CANCELAR;
    }

    public static void setCANCELAR(String CANCELAR) {
        CrearPeregrinacion.CANCELAR = CANCELAR;
    }



    public String getOpcionBoton() {
        return opcionBoton;
    }

    public void setOpcionBoton(String opcionBoton) {
        this.opcionBoton = opcionBoton;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    private void copiarAutobuses() {
        GestionAutobusesBD.copiarAutobusesAnoAnterior();
    }

    private void copiarHabitaciones() {
        GestionHabitacionesBD.copiarHabitacionesAnoAnterior();
    }
}
