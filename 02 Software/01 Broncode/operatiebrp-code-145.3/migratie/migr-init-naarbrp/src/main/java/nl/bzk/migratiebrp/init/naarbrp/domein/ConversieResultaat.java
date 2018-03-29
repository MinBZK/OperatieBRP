/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.domein;

/**
 * Standaard waarden voor het conversie_resultaat veld in de initvullingresult tabel.
 */
public enum ConversieResultaat {
    /**
     * bericht klaar voor verzending.
     */
    TE_VERZENDEN,
    /**
     * bericht is klaar gemaakt voor verzending maar nog niet verzondern.
     */
    IN_VERZENDING,
    /**
     * bericht verzonden, wacht op resultaat van conversie.
     */
    VERZONDEN;
}
