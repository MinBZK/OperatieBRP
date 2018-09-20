/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel;

import java.util.Collection;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch.AbstractNationaliteitConversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;

/**
 * Conversietabel voor LO3 naar BRP codering en vice versa.
 */
public final class NationaliteitConversietabel extends AbstractNationaliteitConversietabel implements
        Conversietabel<Lo3NationaliteitCode, BrpNationaliteitCode> {

    private final Collection<Integer> geldigeCodes;

    /**
     * Constructor.
     * 
     * @param geldigeCodes
     *            geldige codes.
     */
    public NationaliteitConversietabel(final Collection<Integer> geldigeCodes) {
        this.geldigeCodes = geldigeCodes;
    }

    @Override
    public boolean valideerLo3(final Lo3NationaliteitCode input) {
        return input == null || geldigeCodes.contains(Integer.valueOf(input.getCode()));
    }

    @Override
    public boolean valideerBrp(final BrpNationaliteitCode input) {
        return input == null || geldigeCodes.contains(input.getCode());
    }

}
