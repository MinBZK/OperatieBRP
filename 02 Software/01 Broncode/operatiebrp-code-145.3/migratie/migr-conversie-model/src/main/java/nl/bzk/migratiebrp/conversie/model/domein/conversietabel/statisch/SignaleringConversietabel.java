/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SignaleringEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP code.
 */
public final class SignaleringConversietabel implements Conversietabel<Lo3Signalering, BrpBoolean> {

    @Override
    public BrpBoolean converteerNaarBrp(final Lo3Signalering input) {
        BrpBoolean result = null;
        if (input != null && Lo3SignaleringEnum.SIGNALERING.getCode().equals(input.getIntegerWaarde())) {
            result = new BrpBoolean(Boolean.TRUE, input.getOnderzoek());
        }
        return result;
    }

    @Override
    public Lo3Signalering converteerNaarLo3(final BrpBoolean input) {
        Lo3Signalering result = null;
        if (input != null && Boolean.TRUE.equals(input.getWaarde())) {
            result = new Lo3Signalering("" + Lo3SignaleringEnum.SIGNALERING.getCode(), input.getOnderzoek());
        }
        return result;
    }

    @Override
    public boolean valideerLo3(final Lo3Signalering input) {
        return !Lo3Validatie.isElementGevuld(input) || Lo3SignaleringEnum.getByCode(input.getIntegerWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpBoolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
