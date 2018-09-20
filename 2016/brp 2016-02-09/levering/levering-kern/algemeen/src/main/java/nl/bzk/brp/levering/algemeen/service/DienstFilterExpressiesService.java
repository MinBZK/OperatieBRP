/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service;

import java.util.List;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;

/**
 * Service waarmee expressies opgehaald kunnen worden voor diensten.
 */
public interface DienstFilterExpressiesService {

    /**
     * Naam van formele historie element.
     */
    String DATUM_TIJD_REGISTRATIE = "Datum/tijd registratie";

    /**
     * Naam van formele historie element.
     */
    String DATUM_TIJD_VERVAL = "Datum/tijd verval";

    /**
     * Naam van materiele historie element.
     */
    String DATUM_EINDE_GELDIGHEID = "Datum einde geldigheid";

    /**
     * Naam van verantwoordingsinformatie element.
     */
    String BRP_ACTIE_INHOUD = "BRP Actie inhoud";

    /**
     * Naam van verantwoordingsinformatie element.
     */
    String BRP_ACTIE_VERVAL = "BRP Actie verval";

    /**
     * Naam van verantwoordingsinformatie element.
     */
    String BRP_ACTIE_AANPASSING_GELDIGHEID = "BRP Actie Aanpassing Geldigheid";

    /**
     * Maakt een lijst-expressie om historie- en verantwoordingattributen te kunnen aanwijzen.
     * Dit is geconfigureerd in de dienstbundelgroep tabel en concreet betekent dit het volgende:
     * <ul>
     * <li> Indien een dienstbundelgroep de indicatie JA heeft voor formele historie worden de expressies van de
     * attributen "Datum/tijd registratie" en  "Datum/tijd verval" geretourneerd.
     * </li>
     * <li> Indien een dienstbundelgroep de indicatie JA heeft voor materiele historie worden de expressies van het
     * attribuut "Datum einde geldigheid" geretourneerd. Attribuutexpressies materieel attribuut "Datum aanvang geldigheid"
     * worden niet geretourneerd, deze worden los geautoriseerd net als alle andere 'normale' attributen.
     * </li>
     * <li>
     * Indien een dienstbundelgroep de indicatie JA heeft voor verantwoording worden de expressies van
     * de attributen "BRP Actie inhoud", "BRP Actie verval" en "BRP Actie Aanpassing Geldigheid"
     * </li>
     * </ul>
     *
     * @param dienst de dienst.
     * @return De geparsde expressie.
     * @throws ExpressieExceptie de expressie exceptie
     */
    Expressie geefExpressiesVoorHistorieEnVerantwoordingAttributen(Dienst dienst) throws ExpressieExceptie;

    /**
     * Maakt een lijst-expressie om historie- en verantwoordingattributen te kunnen aanwijzen.
     * Dit is geconfigureerd in de dienstbundelgroep tabel en concreet betekent dit het volgende:
     * <ul>
     * <li> Indien een dienstbundelgroep de indicatie JA heeft voor formele historie worden de expressies van de
     * attributen "Datum/tijd registratie" en  "Datum/tijd verval" geretourneerd.
     * </li>
     * <li> Indien een dienstbundelgroep de indicatie JA heeft voor materiele historie worden de expressies van het
     * attribuut "Datum einde geldigheid" geretourneerd. Attribuutexpressies materieel attribuut "Datum aanvang geldigheid"
     * worden niet geretourneerd, deze worden los geautoriseerd net als alle andere 'normale' attributen.
     * </li>
     * <li>
     * Indien een dienstbundelgroep de indicatie JA heeft voor verantwoording worden de expressies van
     * de attributen "BRP Actie inhoud", "BRP Actie verval" en "BRP Actie Aanpassing Geldigheid"
     * </li>
     * </ul>
     *
     * @param dienst de dienst.
     * @return De geparsde expressie.
     * @throws ExpressieExceptie de expressie exceptie
     */
    List<String> geefExpressiesVoorHistorieEnVerantwoordingAttributenLijst(Dienst dienst) throws ExpressieExceptie;


    /**
     * Geeft een total lijst van alle dienstbundelgroep expressies, ongeacht hoe dit geconfigureerd is
     * voor een gegeven leveringsautorisatie. Deze kan gebruikt worden om een persoon te 'resetten'.
     *
     * @return De totale lijst van expressies.
     * @throws ExpressieExceptie de expressie exceptie
     */
    Expressie geefAllExpressiesVoorHistorieEnVerantwoordingAttributen() throws ExpressieExceptie;

    /**
     * Geeft een total lijst van alle dienstbundelgroep expressies, ongeacht hoe dit geconfigureerd is
     * voor een gegeven leveringsautorisatie. Deze kan gebruikt worden om een persoon te 'resetten'.
     *
     * @return De totale lijst van expressies.
     * @throws ExpressieExceptie de expressie exceptie
     */
    List<String> geefAllExpressiesVoorHistorieEnVerantwoordingAttributenLijst() throws ExpressieExceptie;
}
