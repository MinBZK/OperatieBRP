/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractAttribuutConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * De Conversietabel voor reden ontbinding huwelijk/geregistreerd partnerschap. Deze mapt een
 * Lo3RedenOntbindingHuwelijkOfGpCode op BrpRedenEindeRelatieCode.
 */
public abstract class AbstractRedenOntbindingHuwelijkPartnerschapConversietabel extends
        AbstractAttribuutConversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode> {

    /**
     * Maakt een RedenOntbindingHuwelijkPartnerschapConversietabel object.
     * @param conversieLijst de lijst met alle reden entries uit de conversietabel
     */
    public AbstractRedenOntbindingHuwelijkPartnerschapConversietabel(
            final List<Entry<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode>> conversieLijst) {
        super(conversieLijst);
    }

    @Override
    protected final Lo3RedenOntbindingHuwelijkOfGpCode voegOnderzoekToeLo3(final Lo3RedenOntbindingHuwelijkOfGpCode input, final Lo3Onderzoek onderzoek) {
        final Lo3RedenOntbindingHuwelijkOfGpCode resultaat;
        if (Lo3Validatie.isElementGevuld(input)) {
            resultaat = new Lo3RedenOntbindingHuwelijkOfGpCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new Lo3RedenOntbindingHuwelijkOfGpCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    @Override
    protected final BrpRedenEindeRelatieCode voegOnderzoekToeBrp(final BrpRedenEindeRelatieCode input, final Lo3Onderzoek onderzoek) {
        final BrpRedenEindeRelatieCode resultaat;
        if (BrpValidatie.isAttribuutGevuld(input)) {
            resultaat = new BrpRedenEindeRelatieCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new BrpRedenEindeRelatieCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }
}
