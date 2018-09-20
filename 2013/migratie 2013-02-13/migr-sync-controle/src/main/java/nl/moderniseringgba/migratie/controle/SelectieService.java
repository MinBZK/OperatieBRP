/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle;

import java.util.Set;

import nl.moderniseringgba.migratie.controle.rapport.ControleRapport;
import nl.moderniseringgba.migratie.controle.rapport.Opties;

/**
 * Service om de set met PL-en op te halen en te ontdubbelen.
 */
public interface SelectieService {

    /**
     * Selecteer PL-en op basis van de opties en vaste eisen.
     * 
     * @param opties
     *            Opties die de selectie bepalen.
     * @param controleRapport
     *            het rapport welke uit de controle komt.
     * @return Lijst met anummers welke gecontroleerd moeten worden.
     */
    Set<Long> selecteerPLen(final Opties opties, final ControleRapport controleRapport);
}
