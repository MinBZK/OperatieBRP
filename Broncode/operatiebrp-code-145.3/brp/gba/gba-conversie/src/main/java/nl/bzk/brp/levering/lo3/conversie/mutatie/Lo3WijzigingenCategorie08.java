/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3VerblijfplaatsFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Wijzigingen voor categorie 08: verblijfplaats.
 */
public final class Lo3WijzigingenCategorie08 extends Lo3Wijzigingen<Lo3VerblijfplaatsInhoud> {

    private static final Lo3VerblijfplaatsFormatter FORMATTER = new Lo3VerblijfplaatsFormatter();

    /**
     * Default constructor.
     */
    public Lo3WijzigingenCategorie08() {
        super(Lo3CategorieEnum.CATEGORIE_08, FORMATTER);
    }

    @Override
    protected void vulDefaults(final Lo3CategorieWaarde actueleCategorie, final Lo3CategorieWaarde historischeCategorie) {
        vulDefaults(actueleCategorie);
        vulDefaults(historischeCategorie);
    }

    private void vulDefaults(final Lo3CategorieWaarde categorie) {
        if (categorie.getElement(Lo3ElementEnum.ELEMENT_1310) != null && !"".equals(categorie.getElement(Lo3ElementEnum.ELEMENT_1310))) {
            categorie.addElement(Lo3ElementEnum.ELEMENT_1320, categorie.getElement(Lo3ElementEnum.ELEMENT_8510));
        }

        if (categorie.getElement(Lo3ElementEnum.ELEMENT_1410) != null && !"".equals(categorie.getElement(Lo3ElementEnum.ELEMENT_1410))) {
            categorie.addElement(Lo3ElementEnum.ELEMENT_1420, categorie.getElement(Lo3ElementEnum.ELEMENT_8510));
        }

        if (categorie.getElement(Lo3ElementEnum.ELEMENT_7210) == null || "".equals(categorie.getElement(Lo3ElementEnum.ELEMENT_7210))) {
            categorie.addElement(Lo3ElementEnum.ELEMENT_7210, ".");
        }

        categorie.addElement(
                Lo3ElementEnum.ELEMENT_8510,
                grootsteVan(
                        categorie.getElement(Lo3ElementEnum.ELEMENT_0920),
                        categorie.getElement(Lo3ElementEnum.ELEMENT_1320),
                        categorie.getElement(Lo3ElementEnum.ELEMENT_8510)));
    }

    private String grootsteVan(final String... elementen) {
        String result = null;

        for (final String element : elementen) {
            if (element == null) {
                continue;
            }

            if (result == null || element.compareTo(result) > 0) {
                result = element;
            }
        }

        return result;
    }
}
