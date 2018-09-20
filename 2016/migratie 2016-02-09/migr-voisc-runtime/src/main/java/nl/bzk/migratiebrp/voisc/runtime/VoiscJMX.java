/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

/**
 * JMX Interface voor remote access.
 */
public interface VoiscJMX {

    /**
     * Object name to bind JMX object to.
     */
    String OBJECT_NAME = "nl.bzk.migratiebrp.voisc:name=VOISC";

    /**
     * Trigger verzenden van berichten naar ISC.
     */
    void berichtenVerzendenNaarIsc();

    /**
     * Trigger verzenden naar en ontvangen van mailbox.
     */
    void berichtenVerzendenNaarEnOntvangenVanMailbox();

    /**
     * Trigger opschonen berichten.
     */
    void opschonenVoiscBerichten();

    /**
     * Trigger herstellen berichten.
     */
    void herstellenVoiscBerichten();

    /**
     * VOISC afsluiten.
     */
    void afsluiten();
}
