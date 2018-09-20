/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.lev.basis;

import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.logisch.lev.Levering;


/**
 * De communicatie over een Levering, door middel van een bericht.
 *
 * Een Levering is (vaak) aanleiding tot ��n of meer uitgaande berichten: een bericht met de inhoud, en/of een bericht
 * met de melding dat de Levering klaar staat om opgehaald te worden. Al deze situaties worden gegeneraliseerd tot
 * 'communicatie over de leverring', of te wel Levering \ Communicatie.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface LeveringCommunicatieBasis extends ObjectType {

    /**
     * Retourneert Levering van Levering \ Communicatie.
     *
     * @return Levering.
     */
    Levering getLevering();

    /**
     * Retourneert Uitgaand bericht van Levering \ Communicatie.
     *
     * @return Uitgaand bericht.
     */
    Bericht getUitgaandBericht();

}
