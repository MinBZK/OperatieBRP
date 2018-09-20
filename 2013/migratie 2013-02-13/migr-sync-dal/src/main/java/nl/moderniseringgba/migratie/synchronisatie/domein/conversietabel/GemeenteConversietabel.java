/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel;

import java.math.BigDecimal;
import java.util.Collection;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch.AbstractGemeenteConversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;

/**
 * Conversietabel voor LO3 naar BRP codering en vice versa.
 */
public final class GemeenteConversietabel extends AbstractGemeenteConversietabel implements
        Conversietabel<Lo3GemeenteCode, BrpGemeenteCode> {

    private final Collection<BigDecimal> geldigeCodes;

    /**
     * Constructor.
     * 
     * @param geldigeCodes
     *            geldige codes.
     */
    public GemeenteConversietabel(final Collection<BigDecimal> geldigeCodes) {
        this.geldigeCodes = geldigeCodes;
    }

    @Override
    public boolean valideerLo3(final Lo3GemeenteCode input) {
        return input == null || geldigeCodes.contains(new BigDecimal(input.getCode()));
    }

    @Override
    public boolean valideerBrp(final BrpGemeenteCode input) {
        return input == null || geldigeCodes.contains(input.getCode());
    }

}
