/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.preview.model.Bericht;


/**
 * De Interface voor de DAO op berichten voor het dashboard.
 */
public interface BerichtenDao {

    /**
     * Haal alle berichten op.
     *
     * @return the alle berichten
     */
    List<Bericht> getAlleBerichten();

    /**
     * Haal de berichten op voor een BSN.
     *
     * @param bsn de bsn
     * @return the berichten op bsn
     */
    List<Bericht> getBerichtenOpBsn(Integer bsn);

    /**
     * Haal alle berichten op vanaf het gespecificeerde moment (voor incrementeel ophalen van berichten).
     *
     * @param vanaf het tijdstip vanaf wanneer er berichten moeten worden opgehaald
     * @return de berichten vanaf het gespecificeerde tijdstip
     */
    List<Bericht> getBerichtenVanaf(Calendar vanaf);

    /**
     * Opslaan van een bericht in de database.
     *
     * @param bericht de bericht
     */
    void opslaan(Bericht bericht);

    /**
     * Geef de berichten voor partij.
     *
     * @param partij de partij
     * @return de berichten voor partij
     */
    List<Bericht> getBerichtenVoorPartij(String partij);

    /**
     * Geef de berichten voor partij vanaf.
     *
     * @param partij de partij
     * @param vanaf de vanaf
     * @return de berichten voor partij vanaf
     */
    List<Bericht> getBerichtenVoorPartijVanaf(String partij, Calendar vanaf);

    Set<Integer> getBsns(int aantal);

}
