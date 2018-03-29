/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3ReisdocumentFormatter;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Wijzigingen voor categorie 12: reisdocument.
 */
public final class Lo3WijzigingenCategorie12 extends Lo3Wijzigingen<Lo3ReisdocumentInhoud> {

    private static final Lo3ReisdocumentFormatter FORMATTER = new Lo3ReisdocumentFormatter();

    /**
     * Default constructor.
     */
    public Lo3WijzigingenCategorie12() {
        super(Lo3CategorieEnum.CATEGORIE_12, FORMATTER);
    }

    @Override
    protected void vulDefaults(final Lo3CategorieWaarde actueleCategorie, final Lo3CategorieWaarde historischeCategorie) {
        vulDefaults(actueleCategorie);
        vulDefaults(historischeCategorie);
    }

    private void vulDefaults(final Lo3CategorieWaarde categorie) {
        if (categorie.getElement(Lo3ElementEnum.ELEMENT_3610) != null) {
            categorie.addElement(Lo3ElementEnum.ELEMENT_8510, categorie.getElement(Lo3ElementEnum.ELEMENT_8610));
        }
    }
}
