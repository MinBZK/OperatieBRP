/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle;

import java.util.List;

import nl.moderniseringgba.migratie.controle.rapport.ControleRapport;
import nl.moderniseringgba.migratie.controle.rapport.Opties;

/**
 * Service om 2 PL-en met elkaar te vergelijken en inconsistenties te bepalen.
 */
public interface ControleService {

    /**
     * Controleert PL-en op basis van de meegegeven opties.
     * 
     * @param opties
     *            De opties voor de controle.
     * @param anummers
     *            De anummers die gecontroleerd moeten worden.
     * @param controleRapport
     *            ControleRapport met oa de herstellijst.
     */
    void controleerPLen(List<Long> anummers, Opties opties, ControleRapport controleRapport);
}
