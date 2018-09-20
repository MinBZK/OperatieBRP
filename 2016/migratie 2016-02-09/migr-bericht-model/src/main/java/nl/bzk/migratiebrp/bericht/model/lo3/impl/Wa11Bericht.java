/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;

/**
 * Wa11 bericht.
 */
public final class Wa11Bericht extends AbstractParsedLo3Bericht implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(
        Lo3HeaderVeld.RANDOM_KEY,
        Lo3HeaderVeld.BERICHTNUMMER,
        Lo3HeaderVeld.A_NUMMER,
        Lo3HeaderVeld.DATUM);

    private List<Lo3CategorieWaarde> categorieen;

    /**
     * Constructor.
     */
    public Wa11Bericht() {
        super(HEADER, "Wa11", null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3Bericht#getGerelateerdeAnummers()
     */
    @Override
    protected List<String> getGerelateerdeAnummers() {
        return Arrays.asList(
            Lo3CategorieWaardeUtil.getElementWaarde(categorieen, Lo3CategorieEnum.PERSOON, 0, 0, Lo3ElementEnum.ANUMMER),
            getHeader(Lo3HeaderVeld.A_NUMMER));
    }

    /* ************************************************************************************************************* */

    @Override
    protected void parseInhoud(final List<Lo3CategorieWaarde> berichtCategorieen) throws BerichtInhoudException {
        categorieen = berichtCategorieen;
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        return categorieen;
    }

}
