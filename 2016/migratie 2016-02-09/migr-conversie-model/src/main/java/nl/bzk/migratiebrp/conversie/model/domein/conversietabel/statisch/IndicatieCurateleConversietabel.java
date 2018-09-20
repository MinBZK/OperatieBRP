/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieCurateleregisterEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP code.
 */
public final class IndicatieCurateleConversietabel implements Conversietabel<Lo3IndicatieCurateleregister, BrpBoolean> {

    @Override
    public BrpBoolean converteerNaarBrp(final Lo3IndicatieCurateleregister input) {
        return Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.getCode().equals(input == null ? null : input.getIntegerWaarde()) ? new BrpBoolean(
            true,
            input.getOnderzoek()) : null;

    }

    @Override
    public Lo3IndicatieCurateleregister converteerNaarLo3(final BrpBoolean input) {
        return BrpBoolean.isTrue(input) ? new Lo3IndicatieCurateleregister(
            "" + Lo3IndicatieCurateleregisterEnum.CURATOR_AANGESTELD.getCode(),
            input.getOnderzoek()) : null;
    }

    @Override
    public boolean valideerLo3(final Lo3IndicatieCurateleregister input) {
        return !Validatie.isElementGevuld(input) || Lo3IndicatieCurateleregisterEnum.getByCode(input.getIntegerWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpBoolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
