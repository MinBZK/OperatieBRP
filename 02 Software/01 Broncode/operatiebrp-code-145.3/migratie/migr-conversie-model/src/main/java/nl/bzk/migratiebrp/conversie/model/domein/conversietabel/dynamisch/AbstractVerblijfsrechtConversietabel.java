/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractAttribuutConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Deze conversietabel mapt een Lo3AanduidingVerblijfstitelCode op de corresponderen BrpVerblijfsrechtCode en vice
 * versa.
 */
public abstract class AbstractVerblijfsrechtConversietabel extends AbstractAttribuutConversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> {

    /**
     * Maakt een VerblijfsrechtConversietabel object.
     * @param conversieLijst de lijst met alle verblijfsrecht conversies
     */
    public AbstractVerblijfsrechtConversietabel(final List<Entry<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode>> conversieLijst) {
        super(conversieLijst);
    }

    @Override
    protected final Lo3AanduidingVerblijfstitelCode voegOnderzoekToeLo3(final Lo3AanduidingVerblijfstitelCode input, final Lo3Onderzoek onderzoek) {
        final Lo3AanduidingVerblijfstitelCode resultaat;
        if (Lo3Validatie.isElementGevuld(input)) {
            resultaat = new Lo3AanduidingVerblijfstitelCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new Lo3AanduidingVerblijfstitelCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    @Override
    protected final BrpVerblijfsrechtCode voegOnderzoekToeBrp(final BrpVerblijfsrechtCode input, final Lo3Onderzoek onderzoek) {
        final BrpVerblijfsrechtCode resultaat;
        if (input != null) {
            resultaat = new BrpVerblijfsrechtCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new BrpVerblijfsrechtCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }
}
