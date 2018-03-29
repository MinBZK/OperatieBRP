/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingNaamgebruikCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP naamgebruik.
 */
public final class NaamgebruikConversietabel implements Conversietabel<Lo3AanduidingNaamgebruikCode, BrpNaamgebruikCode> {

    @Override
    public Lo3AanduidingNaamgebruikCode converteerNaarLo3(final BrpNaamgebruikCode input) {
        if (input == null) {
            return null;
        }
        return new Lo3AanduidingNaamgebruikCode(input.getWaarde(), input.getOnderzoek());
    }

    @Override
    public BrpNaamgebruikCode converteerNaarBrp(final Lo3AanduidingNaamgebruikCode input) {
        final BrpNaamgebruikCode resultaat;
        if (!Lo3Validatie.isElementGevuld(input)) {
            final Lo3Onderzoek onderzoek = input == null ? null : input.getOnderzoek();
            resultaat = new BrpNaamgebruikCode("E", onderzoek);
        } else {
            resultaat = new BrpNaamgebruikCode(input.getWaarde(), input.getOnderzoek());
        }
        return resultaat;
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingNaamgebruikCode input) {
        return !Lo3Validatie.isElementGevuld(input) || Lo3AanduidingNaamgebruikCodeEnum.getByCode(input.getWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpNaamgebruikCode input) {
        // Enum
        return true;
    }

}
