/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Sleutelwaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.Onderzoek;


/**
 * Gegevens waar onderzoek naar gedaan wordt/naar gedaan is.
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
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public interface GegevenInOnderzoekBasis extends ObjectType {

    /**
     * Retourneert Onderzoek van Gegeven in onderzoek.
     *
     * @return Onderzoek.
     */
    Onderzoek getOnderzoek();

    /**
     * Retourneert Soort gegeven van Gegeven in onderzoek.
     *
     * @return Soort gegeven.
     */
    DatabaseObject getSoortGegeven();

    /**
     * Retourneert Identificatie van Gegeven in onderzoek.
     *
     * @return Identificatie.
     */
    Sleutelwaarde getIdentificatie();

}
