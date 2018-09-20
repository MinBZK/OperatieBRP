/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieDocumentEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP code.
 */
public final class IndicatieDocumentConversietabel implements Conversietabel<Lo3IndicatieDocument, BrpBoolean> {

    @Override
    public BrpBoolean converteerNaarBrp(final Lo3IndicatieDocument input) {
        final BrpBoolean resultaat;
        if (input == null) {
            resultaat = new BrpBoolean(false);
        } else if (!Validatie.isElementGevuld(input)) {
            resultaat = new BrpBoolean(false, input.getOnderzoek());
        } else {
            resultaat = new BrpBoolean(Lo3IndicatieDocumentEnum.INDICATIE.equalsElement(input), input.getOnderzoek());
        }
        return resultaat;
    }

    @Override
    public Lo3IndicatieDocument converteerNaarLo3(final BrpBoolean input) {
        final Lo3IndicatieDocument resultaat;

        if (input == null) {
            resultaat = null;
        } else if (!nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie.isAttribuutGevuld(input)) {
            resultaat = new Lo3IndicatieDocument(null, input.getOnderzoek());
        } else {
            final Lo3Onderzoek onderzoek = input.getOnderzoek();
            if (input.getWaarde()) {
                resultaat = new Lo3IndicatieDocument("" + Lo3IndicatieDocumentEnum.INDICATIE.getCode(), onderzoek);
            } else if (onderzoek != null) {
                resultaat = new Lo3IndicatieDocument(null, onderzoek);
            } else {
                resultaat = null;
            }
        }
        return resultaat;
    }

    @Override
    public boolean valideerLo3(final Lo3IndicatieDocument input) {
        return !Validatie.isElementGevuld(input) || Lo3IndicatieDocumentEnum.getByCode(input.getIntegerWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpBoolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
