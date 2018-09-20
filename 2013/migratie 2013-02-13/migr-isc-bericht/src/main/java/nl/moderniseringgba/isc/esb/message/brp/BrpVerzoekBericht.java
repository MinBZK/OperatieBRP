/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp;

/**
 * Identificeerd een verzoek wat ISC van BRP binnen krijgt. Voor de algemene foutafhandeling willen we op een uniforme
 * manier een antwoord bericht kunnen krijgen.
 */
public interface BrpVerzoekBericht extends BrpBericht {

    /**
     * Maak (en correleer) het relevante antwoord bericht.
     * 
     * @return antwoord bericht
     */
    BrpAntwoordBericht maakAntwoordBericht();
}
