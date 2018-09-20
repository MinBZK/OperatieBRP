/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel;

import java.util.Collection;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch.AbstractPartijConversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;

/**
 * Conversietabel voor LO3 naar BRP codering en vice versa.
 */
public final class PartijConversietabel extends AbstractPartijConversietabel implements
        Conversietabel<Lo3GemeenteCode, BrpPartijCode> {

    private final Collection<Integer> geldigeCodes;

    /**
     * Constructor.
     * 
     * @param geldigeCodes
     *            geldige codes.
     */
    public PartijConversietabel(final Collection<Integer> geldigeCodes) {
        this.geldigeCodes = geldigeCodes;
    }

    @Override
    public boolean valideerLo3(final Lo3GemeenteCode input) {
        return input == null || geldigeCodes.contains(new Integer(input.getCode()));
    }

    @Override
    public boolean valideerBrp(final BrpPartijCode input) {
        return input == null || geldigeCodes.contains(input.getGemeenteCode());
    }

}
