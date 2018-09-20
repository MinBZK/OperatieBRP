/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis.AbstractDoelbindingGegevenselement;


/**
 * Het voorkomen van een gegevenselement in de doelbinding.
 *
 * Bij de Doelbinding wordt vastgelegd welke Gegevenselementen in het kader van de Doelbinding geraakt mogen worden.
 * Simpel gezegd: voor welke Gegevenselementen men geautoriseerd is. Ook weleens aangeduid met 'vertikale autorisatie'.
 * De vertikale autorisatie wordt hierbij 'defensief' gespecificeerd: in kader van een bepaalde Doelbinding mogen alleen
 * d�e Gegevenselementen worden geleverd die door middel van een exemplaar van "Gegevenselement doelbinding" expliciet
 * zijn toegestaan.
 *
 * Bij het objecttype past de naam "Gegevenselement Doelbinding", als een aanzet voor de omschrijving
 * "Het Gegevenselement van de Doelbinding". In de persoonsgegevens ("Kern") is echter gekozen voor een naamgeving
 * "OBJECTTYPE#1/OBJECTTYPE#2", als objecttype #1 de natuurlijke 'ouder' is van het ding. Hier is dat de Doelbinding.
 * Om die reden is de naam zoals die gekozen is: Doelbinding\Gegevenselement.
 * RvdP 22 oktober 2011.
 *
 * Bij de naam '(Gegevens)Element' is de vraag welke elementen worden bedoeld: zijn dit de LOGISCHE elementen, of de
 * representanten hiervan in de database, de DATABASE OBJECTEN.
 * Voor een aantal objecttypen is de hypothese waaronder gewerkt wordt dat het de DATABASE OBJECTEN zijn. Om deze
 * duidelijk te kunnen scheiden van (andere) Elementen, hebben deze een aparte naam gekregen: Databaseobject.
 * In de verwijzing van het attribuut gebruiken we echter nog de naam 'Element': een Databaseobject zou immers kunnen
 * worden beschouwd als een specialisatie van Element. Alleen is die specialisatie in het model niet uitgemodelleerd.
 * RvdP 16 november 2011.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischStamgegevensModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 10:41:47.
 * Gegenereerd op: Tue Nov 27 10:43:33 CET 2012.
 */
@Entity
@Table(schema = "AutAut", name = "DoelbindingGegevenselement")
public class DoelbindingGegevenselement extends AbstractDoelbindingGegevenselement {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected DoelbindingGegevenselement() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param doelbinding doelbinding van DoelbindingGegevenselement.
     * @param gegevenselement gegevenselement van DoelbindingGegevenselement.
     */
    protected DoelbindingGegevenselement(final Doelbinding doelbinding, final DatabaseObject gegevenselement) {
        super(doelbinding, gegevenselement);
    }

}
