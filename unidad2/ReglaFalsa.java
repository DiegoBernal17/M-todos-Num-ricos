package unidad2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReglaFalsa extends JFrame {
	
	private String funcion;
	private int n, tabular;
	private double Xi, Xf;
	private DefaultTableModel dtm;
	private ManejoDeFuncion mdn;
	
	public void init()
	{	
		do {
			funcion = JOptionPane.showInputDialog("Dame la función");
			n = Integer.parseInt(JOptionPane.showInputDialog("Dame el valor de n:", 0));
		} 
		while(funcion.isEmpty() || n <= 0);
		getContentPane().add(new JLabel("f(x)="+funcion+". n="+n+" c.s."), BorderLayout.NORTH);
		
		tabular = JOptionPane.showConfirmDialog(null, "¿Quieres tabular automaticamente?", "Tabulación", JOptionPane.YES_NO_OPTION);
		if(tabular==0)
		{
			Xi = Double.parseDouble(JOptionPane.showInputDialog("Dame el valor de Xi:", 0));
			Xf = Double.parseDouble(JOptionPane.showInputDialog("Dame el valor de Xf:", 1));
			
			mdn = new ManejoDeFuncion(funcion, n, Xi, Xf);
		} else if(tabular==1)
			mdn = new ManejoDeFuncion(funcion, n);
		this.calcularTabla();
	}

	public static void main(String[] args)
	{
		ReglaFalsa frame = new ReglaFalsa();
		frame.setTitle("Método de la Regla Falsa");
		frame.setBounds(40, 40, 500, 600);
		frame.setLayout(new FlowLayout());
		
		frame.init();
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){System.exit(0);}
		});
	}
	
	public void calcularTabla()
	{
		getContentPane().add(new Label("E.s.= "+mdn.getEs()), BorderLayout.NORTH);
		
		for(int i=1;mdn.getEa()>mdn.getEs();i++)
		{
			mdn.calcularfXi();
			mdn.calcularfXf();
			mdn.calcularXr();
			mdn.calcularfXr();
			
			if(i==1) {
				Object[][] datos = mdn.getFirstDatosRF(i);
				String[] columnNames = {"Iteración", "Xi", "f(Xi)", "Xf", "f(Xf)", "Xr", "f(Xr)", "|Ea|%"};
				
				dtm = new DefaultTableModel(datos, columnNames);
				final JTable table = new JTable(dtm);
				table.setPreferredScrollableViewportSize(new Dimension(450, 400));
				JScrollPane scrollPane = new JScrollPane(table);
				getContentPane().add(scrollPane, BorderLayout.CENTER);
			} else {
				mdn.calcularEa();
				dtm.addRow(mdn.getDatosRF(i));
			}
			
			mdn.actualizarXrAXiXf();
		}
	}
}
