/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import com.google.common.base.Strings;
import java.util.Arrays;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3HuwelijkOfGpFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Wijzigingen voor categorie 05: Huwelijk.
 */
final class Lo3WijzigingenCategorie05 extends Lo3Wijzigingen<Lo3HuwelijkOfGpInhoud> {

    private static final Lo3HuwelijkOfGpFormatter FORMATTER = new Lo3HuwelijkOfGpFormatter();

    private final String relatieIdentificatie;

    /**
     * Default constructor.
     * @param relatieIdentificatie relatie identificatie (om meerdere relatie samen te voegen in 1 categorie 05)
     */
    Lo3WijzigingenCategorie05(final String relatieIdentificatie) {
        super(Lo3CategorieEnum.CATEGORIE_05, FORMATTER);
        this.relatieIdentificatie = relatieIdentificatie;
    }

    String getRelatieIdentificatie() {
        return relatieIdentificatie;
    }

    @Override
    protected void vulDefaults(final Lo3CategorieWaarde actueleCategorie, final Lo3CategorieWaarde historischeCategorie) {
        vulDefaults(actueleCategorie);
        vulDefaults(historischeCategorie);
    }

    private void vulDefaults(final Lo3CategorieWaarde categorie) {
        if (hasAnyValue(categorie, Lo3ElementEnum.ELEMENT_0710, Lo3ElementEnum.ELEMENT_0720, Lo3ElementEnum.ELEMENT_0730, Lo3ElementEnum.ELEMENT_0740)) {
            categorie.addElement(Lo3ElementEnum.ELEMENT_0610, "");
            categorie.addElement(Lo3ElementEnum.ELEMENT_0620, "");
            categorie.addElement(Lo3ElementEnum.ELEMENT_0630, "");

            if (Strings.isNullOrEmpty(categorie.getElement(Lo3ElementEnum.ELEMENT_8510))) {
                categorie.addElement(Lo3ElementEnum.ELEMENT_8510, categorie.getElement(Lo3ElementEnum.ELEMENT_0710));
            }
        }

        if (hasAnyValue(categorie, Lo3ElementEnum.ELEMENT_0610, Lo3ElementEnum.ELEMENT_0620, Lo3ElementEnum.ELEMENT_0630)
                && Strings.isNullOrEmpty(categorie.getElement(Lo3ElementEnum.ELEMENT_8510))) {
            categorie.addElement(Lo3ElementEnum.ELEMENT_8510, categorie.getElement(Lo3ElementEnum.ELEMENT_0610));
        }
    }

    private boolean hasAnyValue(final Lo3CategorieWaarde categorie, final Lo3ElementEnum... elements) {
        return Arrays.stream(elements).anyMatch(element -> !Strings.isNullOrEmpty(categorie.getElement(element)));
    }
}
