/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.database.utility.stamtabelgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StamtabelVulling {
	private static String lastUsedDirectory = " ";
	private static PrintWriter printerBRP;
	private static PrintWriter debugPrinter, printerSVNbatchRapport;

    public static String bagFileFinder(String boodschap) {
        String defaultOutput = PropertiesBepaler.lokaleWerkdirectoryRoot + "adhoc-store/BAG gegevens";
        JFileChooser fc = new JFileChooser(defaultOutput);

		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setDialogTitle(boodschap);
		FileNameExtensionFilter filterXML = new FileNameExtensionFilter(
				"XML FILES (.xml)", "xml");
		fc.addChoosableFileFilter(filterXML);
		int rc = fc.showDialog(null, "Selecteer XML file");
		if (rc == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
            return file.getAbsolutePath();
		} else
			lastUsedDirectory = System.getProperty("user.home");
		System.out.println("Geen input, zal falen!");
		return "standaard bestandje...";
	}

	public static String outputSQLmaker(String boodschap) {
		String defaultOutput = PropertiesBepaler.lokaleWerkdirectoryRoot + "brp-bmr/Stamgegevens/SQL scripts";
		JFileChooser fc = new JFileChooser(defaultOutput);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int rc = fc.showDialog(null, boodschap);
		if (rc == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String filenamePad = file.getAbsolutePath();
			lastUsedDirectory = filenamePad;
			return filenamePad;
		} else
			System.out
					.println("Aborted by user(outputselection), this may fail, look in: "
							+ System.getProperty("user.home"));
		return System.getProperty("user.home");
	}

	public void maakWoonplaatsen() {
		try {

			/*

De BAG gaat anders om met de geldigheid dan de BRP. Een Status heeft een geldingheid.

Hieronder twee voorbeelden van plaatsen uit de BAG extract.

Vinkeveen 1930 is bedoeld om van 20090409 - 20120202 te bestaan en vanaf 20120202 gaat de status 'Woonplaats ingetrokken' in.

<bag_LVC:Woonplaats>
	<bag_LVC:identificatie>1930</bag_LVC:identificatie>
	<bag_LVC:woonplaatsNaam>Vinkeveen</bag_LVC:woonplaatsNaam>
	<bag_LVC:tijdvakgeldigheid>
		<bagtype:begindatumTijdvakGeldigheid>2009040900000200</bagtype:begindatumTijdvakGeldigheid>
		<bagtype:einddatumTijdvakGeldigheid>2012020200000000</bagtype:einddatumTijdvakGeldigheid>
	</bag_LVC:tijdvakgeldigheid>
	<bag_LVC:bron>
		<bagtype:documentdatum>20090409</bagtype:documentdatum>
		<bagtype:documentnummer>0012/09</bagtype:documentnummer>
	</bag_LVC:bron>
	<bag_LVC:woonplaatsStatus>Woonplaats aangewezen</bag_LVC:woonplaatsStatus>
</bag_LVC:Woonplaats>
<bag_LVC:Woonplaats>
	<bag_LVC:identificatie>1930</bag_LVC:identificatie>
	<bag_LVC:woonplaatsNaam>Vinkeveen</bag_LVC:woonplaatsNaam>
	<bag_LVC:tijdvakgeldigheid>
		<bagtype:begindatumTijdvakGeldigheid>2012020200000000</bagtype:begindatumTijdvakGeldigheid>
	</bag_LVC:tijdvakgeldigheid>
	<bag_LVC:bron>
		<bagtype:documentdatum>20120202</bagtype:documentdatum>
		<bagtype:documentnummer>0073/11</bagtype:documentnummer>
	</bag_LVC:bron>
	<bag_LVC:woonplaatsStatus>Woonplaats ingetrokken</bag_LVC:woonplaatsStatus>
</bag_LVC:Woonplaats>

<bag_LVC:Woonplaats>
	<bag_LVC:identificatie>3550</bag_LVC:identificatie>
	<bag_LVC:woonplaatsNaam>Vinkeveen</bag_LVC:woonplaatsNaam>
	<bag_LVC:tijdvakgeldigheid>
		<bagtype:begindatumTijdvakGeldigheid>2012020200000000</bagtype:begindatumTijdvakGeldigheid>
	</bag_LVC:tijdvakgeldigheid>
	<bag_LVC:bron>
		<bagtype:documentdatum>20120202</bagtype:documentdatum>
		<bagtype:documentnummer>0073/11</bagtype:documentnummer>
	</bag_LVC:bron>
	<bag_LVC:woonplaatsStatus>Woonplaats aangewezen</bag_LVC:woonplaatsStatus>
</bag_LVC:Woonplaats>


Zelfde voor Vinkel;

Vinkel 2749: 200900602 - 20141229
Vinkel 3063: 20100501 -
Vinkel 3612: 20141229 -

Verklaring voor deze situatie
Inmiddels is Vinkel opgedeeld in een groot westelijk deel (BAG-ID 3612) dat sinds 1 januari 2015 bij de gemeente 's-Hertogenbosch hoort, en twee kleine oostelijke delen die onder het gezag van de burgemeester van Bernheze valt. Die oostelijke delen (BAG-ID 3063) lijken een multipolygoon te zijn, maar blijken bij nadere bestudering door een 5 meter brede corridor langs de gemeentegrens met elkaar verbonden te zijn. Een creatieve oplossing!



<bag_LVC:Woonplaats>
	<bag_LVC:identificatie>2749</bag_LVC:identificatie>
	<bag_LVC:woonplaatsNaam>Vinkel</bag_LVC:woonplaatsNaam>
	<bag_LVC:tijdvakgeldigheid>
		<bagtype:begindatumTijdvakGeldigheid>2009060200000000</bagtype:begindatumTijdvakGeldigheid>
		<bagtype:einddatumTijdvakGeldigheid>2014122900000000</bagtype:einddatumTijdvakGeldigheid>
	</bag_LVC:tijdvakgeldigheid>
	<bag_LVC:bron>
		<bagtype:documentdatum>20090602</bagtype:documentdatum>
		<bagtype:documentnummer>Raad/09-00072</bagtype:documentnummer>
	</bag_LVC:bron>
	<bag_LVC:woonplaatsStatus>Woonplaats aangewezen</bag_LVC:woonplaatsStatus>
</bag_LVC:Woonplaats>
<bag_LVC:Woonplaats>
	<bag_LVC:identificatie>2749</bag_LVC:identificatie>
	<bag_LVC:woonplaatsNaam>Vinkel</bag_LVC:woonplaatsNaam>
	<bag_LVC:tijdvakgeldigheid>
		<bagtype:begindatumTijdvakGeldigheid>2014122900000000</bagtype:begindatumTijdvakGeldigheid>
	</bag_LVC:tijdvakgeldigheid>
	<bag_LVC:bron>
		<bagtype:documentdatum>20140924</bagtype:documentdatum>
		<bagtype:documentnummer>BenW/14-00312</bagtype:documentnummer>
	</bag_LVC:bron>
	<bag_LVC:woonplaatsStatus>Woonplaats ingetrokken</bag_LVC:woonplaatsStatus>
</bag_LVC:Woonplaats>

<bag_LVC:Woonplaats>
	<bag_LVC:identificatie>3063</bag_LVC:identificatie>
	<bag_LVC:woonplaatsNaam>Vinkel</bag_LVC:woonplaatsNaam>
	<bag_LVC:tijdvakgeldigheid>
		<bagtype:begindatumTijdvakGeldigheid>2010050100001000</bagtype:begindatumTijdvakGeldigheid>
		<bagtype:einddatumTijdvakGeldigheid>2010080300001400</bagtype:einddatumTijdvakGeldigheid>
	</bag_LVC:tijdvakgeldigheid>
	<bag_LVC:bron>
		<bagtype:documentdatum>20100420</bagtype:documentdatum>
		<bagtype:documentnummer>2010/24532</bagtype:documentnummer>
	</bag_LVC:bron>
	<bag_LVC:woonplaatsStatus>Woonplaats aangewezen</bag_LVC:woonplaatsStatus>
</bag_LVC:Woonplaats>
<bag_LVC:Woonplaats>
	<bag_LVC:identificatie>3063</bag_LVC:identificatie>
	<bag_LVC:woonplaatsNaam>Vinkel</bag_LVC:woonplaatsNaam>
	<bag_LVC:tijdvakgeldigheid>
		<bagtype:begindatumTijdvakGeldigheid>2010080300001400</bagtype:begindatumTijdvakGeldigheid>
		<bagtype:einddatumTijdvakGeldigheid>2010080300001700</bagtype:einddatumTijdvakGeldigheid>
	</bag_LVC:tijdvakgeldigheid>
	<bag_LVC:bron>
		<bagtype:documentdatum>20100803</bagtype:documentdatum>
		<bagtype:documentnummer>2010/42273</bagtype:documentnummer>
	</bag_LVC:bron>
	<bag_LVC:woonplaatsStatus>Woonplaats aangewezen</bag_LVC:woonplaatsStatus>
</bag_LVC:Woonplaats>
<bag_LVC:Woonplaats>
	<bag_LVC:identificatie>3063</bag_LVC:identificatie>
	<bag_LVC:woonplaatsNaam>Vinkel</bag_LVC:woonplaatsNaam>
	<bag_LVC:tijdvakgeldigheid>
		<bagtype:begindatumTijdvakGeldigheid>2010080300001700</bagtype:begindatumTijdvakGeldigheid>
	</bag_LVC:tijdvakgeldigheid>
	<bag_LVC:bron>
		<bagtype:documentdatum>20100803</bagtype:documentdatum>
		<bagtype:documentnummer>2010/42388</bagtype:documentnummer>
	</bag_LVC:bron>
	<bag_LVC:woonplaatsStatus>Woonplaats aangewezen</bag_LVC:woonplaatsStatus>
</bag_LVC:Woonplaats>

<bag_LVC:Woonplaats>
	<bag_LVC:identificatie>3612</bag_LVC:identificatie>
	<bag_LVC:woonplaatsNaam>Vinkel</bag_LVC:woonplaatsNaam>
	<bag_LVC:tijdvakgeldigheid>
		<bagtype:begindatumTijdvakGeldigheid>2014122900000000</bagtype:begindatumTijdvakGeldigheid>
	</bag_LVC:tijdvakgeldigheid>
	<bag_LVC:bron>
		<bagtype:documentdatum>20140924</bagtype:documentdatum>
		<bagtype:documentnummer>BenW/14-00312</bagtype:documentnummer>
	</bag_LVC:bron>
	<bag_LVC:woonplaatsStatus>Woonplaats aangewezen</bag_LVC:woonplaatsStatus>
</bag_LVC:Woonplaats>


Daarom verwerken we alleen records met de status 'Woonplaats aangewezen'.

			 */
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			boolean doorgaan = true;
			String fileNaam = bagFileFinder("Selecteer de eerste XML file met BAG gegevens [overige worden vanzelf gevonden!]");
			String fileBase = fileNaam.substring(0, fileNaam.length() - 5);
			// Aanname: eindigt op 1.xml

			Integer fileTeller = 1;
			File file = new File(fileBase + "1.xml");
			Integer waardeOnthouden = -1;
			while (doorgaan) {
				if (file.exists()) {
					Document doc = db.parse(file);
					Element docEle = doc.getDocumentElement();

					System.out.println("-- Root element van het document: "
							+ docEle.getNodeName());
					debugPrinter.println("-- Bronmap voor conversie: "
							+ fileBase);
					debugPrinter
							.println("-- Datum/tijd aanmaken debug bestand: "
									+ (new Date()).toString());
					printerBRP.println("-- Bron voor plaatsen: "
							+ file.getName());
					printerBRP.println("-- Datum/tijd genereren vulling: "
							+ (new Date()).toString());
					NodeList woonplaatsList = docEle
							.getElementsByTagName("bag_LVC:Woonplaats");

					System.out.println("Totaal aantal elementen: "
							+ woonplaatsList.getLength());

					int waarde;
					if (woonplaatsList != null
							&& woonplaatsList.getLength() > 0) {
						for (int i = 0; i < woonplaatsList.getLength(); i++) {

							Node node = woonplaatsList.item(i);

							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (((Element) node)
										.getElementsByTagName(
												"bag_LVC:aanduidingRecordInactief")
										.item(0).getChildNodes().item(0)
										.getNodeValue().equals("N") &&
										((Element) node)
												.getElementsByTagName(
														"bag_LVC:woonplaatsStatus")
												.item(0).getChildNodes().item(0)
												.getNodeValue().equals("Woonplaats aangewezen")

										) {

									Element e = (Element) node;
									NodeList nodeList = e
											.getElementsByTagName("bag_LVC:identificatie");

									waarde = Integer.parseInt(nodeList.item(0)
											.getChildNodes().item(0)
											.getNodeValue());

									nodeList = e
											.getElementsByTagName("bag_LVC:woonplaatsNaam");

									if (waarde != waardeOnthouden) {
										printerBRP
												.print("INSERT INTO Kern.Plaats (ID, Code, Naam, DatAanvGel) VALUES (");

										printerBRP.print(waarde - 999 + ", "
												+ waarde + ", ");

										nodeList = e
												.getElementsByTagName("bag_LVC:woonplaatsNaam");
										printerBRP.print("'"
												+ nodeList.item(0)
												.getChildNodes()
												.item(0).getNodeValue().replace("'", "''")
												+ "', ");

										nodeList = e
												.getElementsByTagName("bagtype:begindatumTijdvakGeldigheid");
										printerBRP.println(nodeList.item(0)
												.getChildNodes().item(0)
												.getNodeValue().substring(0, 8)
												+ ");");

										/*
										 * Soms is er een
										 * einddatumtijdvakgeldigheid
										 */
										nodeList = e
												.getElementsByTagName("bagtype:einddatumTijdvakGeldigheid");
										if (nodeList.item(0) != null) {
											printerBRP
													.println("UPDATE Kern.Plaats SET DatEindeGel = "
															+ nodeList
																	.item(0)
																	.getChildNodes()
																	.item(0)
																	.getNodeValue()
																	.substring(
																			0,
																			8)
															+ " WHERE Code = "
															+ waarde + ";");
										}

									}

									/* Debugging */
									nodeList = e.getElementsByTagName("*");
									for (int teller = 0; teller < 4; teller++) {
										debugPrinter.println(woonplaatsList
												.item(i).getChildNodes()
												.item(teller).getTextContent());
									}
									for (int teller = 5; teller < 100; teller++) {
										if (woonplaatsList.item(i)
												.getChildNodes().item(teller) != null) {
											debugPrinter.println(woonplaatsList
													.item(i).getChildNodes()
													.item(teller)
													.getTextContent());
										}
									}

									/* Einde debugging */
									if (waarde == waardeOnthouden) {
										printerBRP
												.println("UPDATE Kern.Plaats SET DatEindeGel = NULL "
														+ " WHERE Code = "
														+ waarde + ";");
										//printerBRP
										//		.println("-- in plaats van een UPDATE [kern.plaats (id, code, naam, dataanvgel) values(] nu de volgende syntax:");
										printerBRP
												.print("UPDATE Kern.Plaats SET Naam = ");
										nodeList = e
												.getElementsByTagName("bag_LVC:woonplaatsNaam");
										printerBRP.print("'"
												+ nodeList.item(0)
														.getChildNodes()
														.item(0).getNodeValue().replace("'", "''")
												+ "'");
										printerBRP.println(" WHERE Code = "
												+ waarde + ";");

										/*
										 * Soms is er een
										 * einddatumtijdvakgeldigheid
										 */
										nodeList = e
												.getElementsByTagName("bagtype:einddatumTijdvakGeldigheid");
										if (nodeList.item(0) != null) {
											printerBRP
													.println("UPDATE Kern.Plaats SET DatEindeGel = "
															+ nodeList
																	.item(0)
																	.getChildNodes()
																	.item(0)
																	.getNodeValue()
																	.substring(
																			0,
																			8)
															+ " WHERE Code = "
															+ waarde + ";");
										}

									}
									waardeOnthouden = waarde;

								}
							}
						}

					}
				} else {
					doorgaan = false;
				}
				fileTeller = fileTeller + 1;
				file = new File(fileBase + fileTeller + ".xml");
			}
			// einde whileloop
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(final String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		PropertiesBepaler propertiesBepaler = PropertiesBepaler
				.getPropertiesBepaler();
        boolean woonPlaatsGeneratie = propertiesBepaler.woonPlaatsGeneratie();

        int versieBMR = propertiesBepaler.versieNummerBMR();

		lastUsedDirectory = outputSQLmaker("Selecteer folder voor output SQL ");

		// Code tbv woonplaatstabel
		if (woonPlaatsGeneratie) {
			printerBRP = new PrintWriter(lastUsedDirectory + "/Plaats.sql", "UTF-8");
			printerBRP.write("\uFEFF");

			debugPrinter = new PrintWriter(lastUsedDirectory + "/debug.txt");

			StamtabelVulling parser = new StamtabelVulling();
			parser.maakWoonplaatsen();
			printerBRP.close();
			debugPrinter.close();
		}

		System.out.println("Generatie afgerond.");
	}
}
