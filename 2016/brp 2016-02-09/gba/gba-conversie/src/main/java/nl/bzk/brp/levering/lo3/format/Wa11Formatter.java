/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.springframework.stereotype.Component;

/**
 * 'Normale' Wa11 waarbij het oude a-nummer komt uit 01.20.10 en het nieuwe a-nummer uit 01.01.10.
 */
@Component
public final class Wa11Formatter extends AbstractWa11Formatter implements Formatter {

    @Override
    protected String bepaalOudAnummer(final List<Lo3CategorieWaarde> categorieen) {
        return FormatterUtil.geefElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_2010);
    }

    @Override
    protected String bepaalNieuwAnummer(final List<Lo3CategorieWaarde> categorieen) {
        return FormatterUtil.geefElementWaarde(categorieen, Lo3CategorieEnum.CATEGORIE_01, Lo3ElementEnum.ELEMENT_0110);
    }

}
