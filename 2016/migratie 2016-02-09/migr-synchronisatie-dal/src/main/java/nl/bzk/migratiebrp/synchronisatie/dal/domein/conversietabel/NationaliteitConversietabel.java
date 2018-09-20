/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.Collection;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractNationaliteitConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * Conversietabel voor LO3 naar BRP codering en vice versa.
 */
public final class NationaliteitConversietabel extends AbstractNationaliteitConversietabel implements
        Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode>
{

    private final Collection<Short> geldigeCodes;

    /**
     * Constructor.
     * 
     * @param geldigeCodes
     *            geldige codes.
     */
    public NationaliteitConversietabel(final Collection<Short> geldigeCodes) {
        this.geldigeCodes = geldigeCodes;
    }

    @Override
    public boolean valideerLo3(final Lo3NationaliteitCode input) {
        return !Validatie.isElementGevuld(input) || geldigeCodes.contains(Short.valueOf(input.getWaarde()));
    }

    @Override
    public boolean valideerBrp(final BrpNationaliteitCode input) {
        return input == null || geldigeCodes.contains(input.getWaarde());
    }

}
