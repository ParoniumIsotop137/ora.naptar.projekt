package ora.naptar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class OraNaptar {

	public JFrame getFrmOra() {
		return frmOra;
	}

	private JFrame frmOra;

	private LocalTime ido;

	private JLabel lblOra;

	private String kiiras;

	private UtilDateModel model;
	private JDatePanelImpl panel;
	private JDatePickerImpl picker;
	private LocalDate datum;
	private Properties p;
	private JLabel lblSzoveg_1;
	private JLabel lblSzoveg_2;
	private JComboBox cmbIdo;

	// a naptár a JDatePickerImpl felhasználásával készült, rövid leírás és letöltő
	// link:
	// https://www.codejava.net/java-se/swing/how-to-use-jdatepicker-to-display-calendar-component
	// hátránya a komponensek szövegformázásának hiánya

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public OraNaptar() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOra = new JFrame();
		frmOra.setIconImage(
				Toolkit.getDefaultToolkit().getImage(OraNaptar.class.getResource("/ora/naptar/ora-icon.png")));
		frmOra.setTitle("Idő és dátum");
		frmOra.setBounds(100, 100, 520, 260);
		frmOra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOra.getContentPane().setLayout(null);

		JButton btnLeallitas = new JButton("Időzítő beállítása");
		btnLeallitas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Idozito();

			}
		});
		btnLeallitas.setBackground(new Color(173, 216, 230));
		btnLeallitas.setForeground(new Color(255, 0, 0));
		btnLeallitas.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnLeallitas.setBounds(315, 171, 150, 30);
		frmOra.getContentPane().add(btnLeallitas);

		lblSzoveg_2 = new JLabel("perc múlva");
		lblSzoveg_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblSzoveg_2.setForeground(Color.RED);
		lblSzoveg_2.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblSzoveg_2.setBounds(300, 127, 170, 30);
		frmOra.getContentPane().add(lblSzoveg_2);

		cmbIdo = new JComboBox();
		cmbIdo.setForeground(new Color(72, 61, 139));
		cmbIdo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		cmbIdo.setModel(new DefaultComboBoxModel(
				new String[] { "2", "5", "10", "15", "15", "30", "45", "60", "90", "120", "180", "240" }));

		cmbIdo.setBackground(new Color(240, 255, 240));
		cmbIdo.setBounds(360, 91, 50, 25);
		frmOra.getContentPane().add(cmbIdo);

		lblSzoveg_1 = new JLabel("Számítógép leállítása:");
		lblSzoveg_1.setForeground(new Color(255, 0, 0));
		lblSzoveg_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSzoveg_1.setFont(new Font("Segoe Print", Font.BOLD, 14));
		lblSzoveg_1.setBounds(281, 50, 200, 30);
		frmOra.getContentPane().add(lblSzoveg_1);

		lblOra = new JLabel("");
		lblOra.setVerticalAlignment(SwingConstants.TOP);
		lblOra.setOpaque(true);
		lblOra.setBackground(new Color(119, 136, 153));
		lblOra.setForeground(new Color(127, 255, 0));
		lblOra.setFont(new Font("Yu Gothic", Font.BOLD, 45));
		lblOra.setHorizontalAlignment(SwingConstants.CENTER);
		lblOra.setBounds(0, 0, 250, 55);
		frmOra.getContentPane().add(lblOra);

		model = new UtilDateModel();
		datum = LocalDate.now();
		model.setDate(datum.getYear(), datum.getMonthValue() - 1, datum.getDayOfMonth());
		model.setSelected(true);
		p = new Properties();

		p.put("text.today", "Ma");

		panel = new JDatePanelImpl(model, p);
		picker = new JDatePickerImpl(panel, new DateLabelFormatter());
		picker.getJFormattedTextField().setBackground(new Color(173, 216, 230));
		picker.setBounds(250, 0, 250, 27);

		frmOra.getContentPane().add(picker);

		JLabel lblHatter = new JLabel("New label");
		lblHatter.setAlignmentX(0.5f);
		lblHatter.setVerticalAlignment(SwingConstants.TOP);
		lblHatter.setHorizontalAlignment(SwingConstants.CENTER);
		lblHatter.setIcon(new ImageIcon(OraNaptar.class.getResource("/ora/naptar/Ora.jpg")));
		lblHatter.setBounds(0, 0, 506, 223);
		frmOra.getContentPane().add(lblHatter);

	}

	private void Idozito() {

		Integer ido = (Integer.parseInt((cmbIdo.getSelectedItem().toString())));

		Object[] opciok = new Object[] { "Igen", "Nem" };

		ImageIcon ikon = new ImageIcon(OraNaptar.class.getResource("/ora/naptar/kerdoJel.png"));

		if (JOptionPane.showOptionDialog(frmOra,
				"A rendszer " + String.valueOf(ido) + " perc múlva automatikusan leáll. Kívánja folytatni?",
				"Leállítás", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, ikon, opciok,
				opciok[1]) == JOptionPane.YES_OPTION) {

			ido = ido * 60;

			try {
				Runtime.getRuntime().exec("shutdown -s -t " + ido);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frmOra, "Sikertelen rendszerleállítási kísérlet!", "Hiba",
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	public void OraKijelzes() {

		while (true) {

			ido = LocalTime.now();
			kiiras = ido.toString().substring(0, 8);
			lblOra.setText(kiiras);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
