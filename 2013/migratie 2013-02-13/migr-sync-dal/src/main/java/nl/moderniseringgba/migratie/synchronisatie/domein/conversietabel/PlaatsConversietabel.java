/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel;

import java.util.Collection;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.dynamisch.AbstractPlaatsConversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;

/**
 * Conversietabel voor LO3 naar BRP codering en vice versa.
 */
public final class PlaatsConversietabel extends AbstractPlaatsConversietabel implements
        Conversietabel<String, BrpPlaatsCode> {

    private final Collection<String> geldigeCodes;

    /**
     * Constructor.
     * 
     * @param geldigeCodes
     *            geldige codes.
     */
    public PlaatsConversietabel(final Collection<String> geldigeCodes) {
        this.geldigeCodes = geldigeCodes;
    }

    @Override
    public boolean valideerLo3(final String input) {
        return input == null || geldigeCodes.contains(input);
    }

    @Override
    public boolean valideerBrp(final BrpPlaatsCode input) {
        return input == null || geldigeCodes.contains(input.getNaam());
    }

}
