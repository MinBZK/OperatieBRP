/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3Formatter;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Formatter (helper).
 */
public final class MutatieOutputter implements Lo3Formatter {
    private final Lo3CategorieWaarde outputCategorie;

    /**
     * Constructor.
     * @param outputCategorie output categorie
     */
    MutatieOutputter(final Lo3CategorieWaarde outputCategorie) {
        this.outputCategorie = outputCategorie;
    }

    @Override
    public void categorie(final Lo3CategorieEnum categorie) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void categorie(final Lo3CategorieEnum categorie, final int stapel, final int voorkomen) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void element(final Lo3ElementEnum element, final String inhoud) {
        if (inhoud == null || "".equals(inhoud)) {
            return;
        }

        outputCategorie.addElement(element, inhoud);
    }
}
