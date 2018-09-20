/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import java.util.Calendar;
import java.util.Set;

import nl.bzk.brp.preview.model.AbstractBerichtRequest;
import nl.bzk.brp.preview.model.BerichtenResponse;


/**
 * De interface definitie van de service voor berichten van de BRP (opvragen/opslaan).
 */
public interface BerichtenService {

    /**
     * Geef de berichten response (de lijst met berichten en meta informatie over instellingen van het dashboard).
     *
     * @return the berichten response
     */
    BerichtenResponse getBerichten();

    /**
     * Geef de berichten voor partij.
     *
     * @param partij de partij
     * @return de berichten voor partij
     */
    BerichtenResponse getBerichtenVoorPartij(String partij);

    /**
     * Geef de berichten voor partij.
     *
     * @param partij de partij
     * @param vanaf het tijdstip vanaf wanneer de berichten het laatst getoond mogen worden
     * @return de berichten voor partij vanaf het tijdstip
     */
    BerichtenResponse getBerichtenVoorPartijVanaf(String partij, Calendar vanaf);

    /**
     * Geef de berichten response vanaf.
     *
     * @param vanaf de vanaf
     * @return de berichten response vanaf
     */
    BerichtenResponse getBerichtenResponseVanaf(Calendar vanaf);

    /**
     * Zet het maximum aantal berichten tonen.
     *
     * @param intValue de new maximum aantal berichten tonen
     */
    void setMaximumAantalBerichtenTonen(int intValue);

    BerichtenResponse getBerichtenVoorBsn(String bsn);

    Set<Integer> getBsns();

}
