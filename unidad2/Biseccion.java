package unidad2;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class Biseccion extends JFrame {

	private String funcion;
	private int n;
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
		
		mdn = new ManejoDeFuncion(funcion, n);
		this.calcularTabla();
	}

	public static void main(String[] args)
	{
		Biseccion frame = new Biseccion();
		frame.setTitle("Método de bisección");
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
			mdn.calcularfXm();
			mdn.calcularfXf();
			mdn.calcularXm();
			mdn.calcularfXm();
			
			if(i==1) {
				Object[][] datos = mdn.getFirstDatosBis(i);
				String[] columnNames = {"Iteración", "Xi", "f(Xi)", "Xf", "f(Xf)", "Xm", "f(Xm)", "|Ea|%"};
				
				dtm = new DefaultTableModel(datos, columnNames);
				final JTable table = new JTable(dtm);
				table.setPreferredScrollableViewportSize(new Dimension(450, 400));
				JScrollPane scrollPane = new JScrollPane(table);
				getContentPane().add(scrollPane, BorderLayout.CENTER);
			} else {
				mdn.calcularEa();
				dtm.addRow(mdn.getDatosBis(i));
			}
			
			mdn.actualizarXmAXiXf();
		}
	}
}
