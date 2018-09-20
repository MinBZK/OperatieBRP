/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.vergrendeling;

import java.util.Collection;

/**
 * Vergrendel service voor het vergrendelen van sleutels.
 */
public interface SleutelVergrendelaar {

    /**
     * Enumeratie die aangeeft wat de vergrendelmode is die gebruikt moet worden.
     */
    public enum VergrendelMode {
        /**
         * Gedeelde vergrendel mode, lees vergrendeling.
         */
        GEDEELD,
        /**
         * Exclusieve vergrendel mode, schrijf vergrendeling.
         */
        EXCLUSIEF
    }

    /**
     * Doormiddel van een commit de vergrendeling van deze thread opheffen.
     */
    void ontgrendel();

    /**
     * Vergrendel de objecten met bijbehorende sleutels door middel van een advisory lock in PostgreSQL.
     *
     * @param sleutels De lijst van sleutels die vergrendelt moeten worden
     * @param referentieNummer Referentienummer van bijvoorbeeld het bijbehorende bericht.
     * @param mode De mode van vergrendeling (voor lezen of voor schrijven)
     * @throws VergrendelFout Exceptie wanneer het fout gaat bij het vergrendelen.
     */
    void vergrendel(final Collection<Integer> sleutels, final String referentieNummer, final VergrendelMode mode)
        throws VergrendelFout;

}
