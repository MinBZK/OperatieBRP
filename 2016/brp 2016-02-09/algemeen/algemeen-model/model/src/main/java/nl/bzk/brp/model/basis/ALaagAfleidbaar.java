/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Klassen die deze interface implementeren beschikken over A-Laag velden die afleidbaar zijn op basis van andere velden in deze Klasse. Via de {@link
 * #leidALaagAf()} methode kunnen dan ook de A-Laag velden van deze Klasse worden afgeleid (en gezet).
 */
public interface ALaagAfleidbaar {

    /**
     * Leidt alle A-Laag velden in deze klasse af van overige (C/D-laag) velden in deze klasse en zet deze naar de juiste actuele waarden.
     */
    void leidALaagAf();

}
