/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.database.utility.stamtabelgenerator;

import java.awt.*;
import javax.swing.*;

public class PropertiesBepaler {
	// Singleton pattern
	private static PropertiesBepaler ref;
	public static boolean woonPlaatsGeneratie = true;

	// Directory waar tijdelijk bestand geschreven wordt en de waar 'ontwerp' uitgecheckt is in SVN
	public static String lokaleWerkdirectoryRoot = "P:/BRP/";

	// Huidige BMR versie
	public static String huidigeBMRVersie = "42";
	
	public static int versieNummerBMRconverted;

	public boolean woonPlaatsGeneratie() {
		return woonPlaatsGeneratie;
	}

	public int versieNummerBMR() {
		return versieNummerBMRconverted;
	}

	private PropertiesBepaler() {
			}

	public static PropertiesBepaler getPropertiesBepaler() {
		if (ref == null) {
			ref = new PropertiesBepaler();
			ref.getWaarden();
		}
		return ref;
	}

	public void getWaarden() {
		JCheckBox genereerWoonplaats = new JCheckBox("Genereren woonplaats?");

		JTextField genereerVersieNummerBMR = new JTextField("Versienr BMR?");
		genereerVersieNummerBMR.setText(huidigeBMRVersie);

		JPanel genereerVragen = new JPanel();
		JPanel generatieVraagReleaseRapport = new JPanel();
		JPanel generatieVraagPlaatsBatbestand = new JPanel();

		JPanel hoofdscherm = new JPanel(new BorderLayout());
		genereerVragen.add(genereerWoonplaats);
		generatieVraagReleaseRapport.add(genereerVersieNummerBMR);
		genereerVragen.setBorder(BorderFactory
				.createTitledBorder("Vink de op te leveren bestanden aan:"));
		generatieVraagReleaseRapport
				.setBorder(BorderFactory
						.createTitledBorder("Geef versienummer BMR release & vink aan of SVN rapport gewenst is:"));

		hoofdscherm.add(genereerVragen, BorderLayout.NORTH);
		hoofdscherm.add(generatieVraagReleaseRapport, BorderLayout.CENTER);

		JOptionPane.showOptionDialog(null, hoofdscherm, "Kies...",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				null, null);
		woonPlaatsGeneratie = genereerWoonplaats.isSelected();
		versieNummerBMRconverted = Integer.valueOf(genereerVersieNummerBMR
				.getText());
	}

}
