/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractAttribuutConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * De conversietabel voor het converteren van de 'LO3 aanduiding inhouding dan wel vermissing Nederlands
 * reisdocument'-code naar de 'BRP Aanduiding inhouding/vermissing reisdocument'-code en vice versa.
 */
public abstract class AbstractAanduidingInhoudingVermissingReisdocumentConversietabel extends
        AbstractAttribuutConversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode> {

    /**
     * Maakt een AanduidingInhoudingOfVermissingConversietabel object.
     * @param conversieLijst de lijst met AanduidingInhoudingVermissingReisdocument conversies
     */
    protected AbstractAanduidingInhoudingVermissingReisdocumentConversietabel(
            final List<Entry<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode>> conversieLijst) {
        super(conversieLijst);
    }

    @Override
    protected final Lo3AanduidingInhoudingVermissingNederlandsReisdocument voegOnderzoekToeLo3(
            final Lo3AanduidingInhoudingVermissingNederlandsReisdocument input,
            final Lo3Onderzoek onderzoek) {
        final Lo3AanduidingInhoudingVermissingNederlandsReisdocument resultaat;
        if (!Lo3Validatie.isElementGevuld(input)) {
            if (onderzoek == null) {
                resultaat = null;
            } else {
                resultaat = new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(null, onderzoek);
            }
        } else {
            resultaat = new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(input.getWaarde(), onderzoek);
        }

        return resultaat;
    }

    @Override
    protected final BrpAanduidingInhoudingOfVermissingReisdocumentCode voegOnderzoekToeBrp(
            final BrpAanduidingInhoudingOfVermissingReisdocumentCode input,
            final Lo3Onderzoek onderzoek) {
        final BrpAanduidingInhoudingOfVermissingReisdocumentCode resultaat;
        if (!BrpValidatie.isAttribuutGevuld(input)) {
            if (onderzoek == null) {
                resultaat = null;
            } else {
                resultaat = new BrpAanduidingInhoudingOfVermissingReisdocumentCode(null, onderzoek);
            }
        } else {
            resultaat = new BrpAanduidingInhoudingOfVermissingReisdocumentCode(input.getWaarde(), onderzoek);
        }

        return resultaat;
    }
}
