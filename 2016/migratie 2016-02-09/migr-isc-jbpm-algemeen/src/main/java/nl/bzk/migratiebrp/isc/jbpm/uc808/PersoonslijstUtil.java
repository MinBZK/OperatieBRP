/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc808;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Utilities voor PL.
 */
public final class PersoonslijstUtil {

    private PersoonslijstUtil() {
        // Niet instantieerbaar
    }

    /**
     * Bepaal het aantal stapels van een bepaalde categorie.
     * 
     * @param categorieWaarden
     *            pl
     * @param categorie
     *            categorie
     * @return aantal stapels
     */
    public static int getAantalStapels(final List<Lo3CategorieWaarde> categorieWaarden, final Lo3CategorieEnum categorie) {
        int result = 0;

        for (final Lo3CategorieWaarde categorieWaarde : categorieWaarden) {
            if (categorieWaarde.getCategorie() == categorie) {
                result++;
            }
        }

        return result;
    }

    /**
     * Bepaal het maximum aantal stapels voor een categorie over een set van pl-en.
     * 
     * @param categorieWaarden
     *            pl-en
     * @param categorie
     *            categorie
     * @return aantal
     */
    public static int getMaxAantalStapels(final List<List<Lo3CategorieWaarde>> categorieWaarden, final Lo3CategorieEnum categorie) {
        int result = 0;

        for (final List<Lo3CategorieWaarde> categorieen : categorieWaarden) {
            final int plResult = getAantalStapels(categorieen, categorie);
            if (plResult > result) {
                result = plResult;
            }
        }

        return result;
    }

    /**
     * Geef de 'zoveelste' stapel van een bepaalde categorie uit een pl.
     * 
     * @param categorieWaarden
     *            pl
     * @param categorie
     *            categorie
     * @param stapel
     *            'zoveelste'
     * 
     * @return stapel (lege lijst als niet gevonden)
     */
    public static List<Lo3CategorieWaarde> getStapel(final List<Lo3CategorieWaarde> categorieWaarden, final Lo3CategorieEnum categorie, final int stapel) {
        final Lo3CategorieEnum historisch = Lo3CategorieEnum.bepaalHistorischeCategorie(categorie);
        final List<Lo3CategorieWaarde> result = new ArrayList<>();

        int huidigeStapel = -1;

        for (final Lo3CategorieWaarde categorieWaarde : categorieWaarden) {
            if (categorieWaarde.getCategorie() == categorie) {
                huidigeStapel++;
            }

            if (huidigeStapel > stapel) {
                // done
                break;
            }

            if (huidigeStapel == stapel && (categorieWaarde.getCategorie() == categorie || categorieWaarde.getCategorie() == historisch)) {
                result.add(categorieWaarde);
            }
        }

        return result;
    }

}
