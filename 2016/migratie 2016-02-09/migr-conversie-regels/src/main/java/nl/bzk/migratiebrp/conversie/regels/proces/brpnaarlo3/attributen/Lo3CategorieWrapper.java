/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;

/**
 * Wrapper voor Lo3Categorie om extra bij te houden of de rij onjuist was in de oorspronkelijke Lo3 PL. Deze class is
 * immutable.
 * 
 * @param <T>
 *            categorie inhoud type
 */
final class Lo3CategorieWrapper<T extends Lo3CategorieInhoud> {

    private final Lo3Categorie<T> lo3Categorie;
    private final boolean juistInLo3Bron;

    /**
     * Maak een wrapper instantie aan.
     * 
     * @param lo3Categorie
     *            De Lo3Categorie
     * @param juistInLo3Bron
     *            Was de bron van deze rij in Lo3 oorspronkelijke Onjuist
     */
    Lo3CategorieWrapper(final Lo3Categorie<T> lo3Categorie, final boolean juistInLo3Bron) {
        this.lo3Categorie = lo3Categorie;
        this.juistInLo3Bron = juistInLo3Bron;
    }

    /**
     * Geef de waarde van lo3 categorie.
     *
     * @return lo3 categorie
     */
    public Lo3Categorie<T> getLo3Categorie() {
        return lo3Categorie;
    }

    /**
     * Geef de juist in lo3 bron.
     *
     * @return juist in lo3 bron
     */
    public boolean isJuistInLo3Bron() {
        return juistInLo3Bron;
    }
}
