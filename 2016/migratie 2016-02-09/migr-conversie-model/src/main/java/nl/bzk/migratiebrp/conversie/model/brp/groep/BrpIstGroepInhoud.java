/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

/**
 * De BRP groepen die onderdeel zijn van het IST schema. Deze bevatten LO3 informatie zoals categorie, stapelnummer en
 * voorkomennummer.
 * 
 */
public interface BrpIstGroepInhoud extends BrpGroepInhoud {

    /**
     * Geef de waarde van categorie.
     *
     * @return categorie
     */
    Lo3CategorieEnum getCategorie();

    /**
     * Geef de waarde van stapel.
     *
     * @return stapel nummer
     */
    int getStapel();

    /**
     * Geef de waarde van voorkomen.
     *
     * @return voorkomen nummer
     */
    int getVoorkomen();

    /**
     * Geef de waarde van standaard gegevens.
     *
     * @return de inhoud die voor alle IST-stapels standaard is
     */
    BrpIstStandaardGroepInhoud getStandaardGegevens();
}
