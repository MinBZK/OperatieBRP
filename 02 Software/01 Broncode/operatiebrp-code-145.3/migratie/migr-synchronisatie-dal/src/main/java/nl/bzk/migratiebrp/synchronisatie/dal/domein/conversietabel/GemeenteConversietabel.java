/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.Collection;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractGemeenteConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Conversietabel voor LO3 naar BRP codering en vice versa.
 */
public final class GemeenteConversietabel extends AbstractGemeenteConversietabel implements Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> {

    private final Collection<String> geldigeCodes;

    /**
     * Constructor.
     * @param geldigeCodes geldige codes.
     */
    public GemeenteConversietabel(final Collection<String> geldigeCodes) {
        this.geldigeCodes = geldigeCodes;
    }

    @Override
    public boolean valideerLo3(final Lo3GemeenteCode input) {
        return !Lo3Validatie.isElementGevuld(input) || geldigeCodes.contains(input.getWaarde());
    }

    @Override
    public boolean valideerBrp(final BrpGemeenteCode input) {
        return !BrpValidatie.isAttribuutGevuld(input) || geldigeCodes.contains(input.getWaarde());
    }

}
