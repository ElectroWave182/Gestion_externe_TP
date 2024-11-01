import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

public class Tp3Etape4 extends JFrame implements ActionListener
{
	
	private JTable tableau;
	private MonModeleDeTable modele;

	public Tp3Etape4 ()
	{
		super ();

		setTitle ("JTable avec tous les sports");
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		modele = new MonModeleDeTable (this);
		tableau = new JTable (modele);

		JButton btn = new JButton ("Ajouter");

		btn.addActionListener (this);

		getContentPane ().add (btn, BorderLayout.SOUTH);

		getContentPane ().add (tableau.getTableHeader (), BorderLayout.NORTH);
		getContentPane ().add (tableau, BorderLayout.CENTER);

		pack();
	}

	@Override
	public void actionPerformed (ActionEvent arg0)
	{
		System.out.println ("Ajouter ligne");
		String sport = JOptionPane.showInputDialog ("Entrer le nom du sport");
		if (sport != null)
		{
			// ajouter un sport du modèle de table et de la bdd
			modele.addRow (sport);
		}
	}
	
}