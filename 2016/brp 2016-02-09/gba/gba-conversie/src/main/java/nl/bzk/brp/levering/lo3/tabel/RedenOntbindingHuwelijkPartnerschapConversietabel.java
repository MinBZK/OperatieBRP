/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractRedenOntbindingHuwelijkPartnerschapConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;

/**
 * De Conversietabel voor reden ontbinding huwelijk/geregistreerd partnerschap. Deze mapt een
 * Lo3RedenOntbindingHuwelijkOfGpCode op BrpRedenEindeRelatieCode.
 */
public final class RedenOntbindingHuwelijkPartnerschapConversietabel extends AbstractRedenOntbindingHuwelijkPartnerschapConversietabel {

    /**
     * Maakt een RedenOntbindingHuwelijkPartnerschapConversietabel object.
     *
     * @param list de lijst met waarden uit de conversietabel.
     */
    public RedenOntbindingHuwelijkPartnerschapConversietabel(final List<ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap} naar een conversie map
     * van de LO3 waarde {@link Lo3RedenOntbindingHuwelijkOfGpCode} en de BRP waarde {@link BrpRedenEindeRelatieCode}.
     */
    private static final class Converter
            extends
            AbstractLijstConverter<ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap, Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode>
    {
        @Override
        protected Lo3RedenOntbindingHuwelijkOfGpCode maakLo3Waarde(final ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap reden) {
            return new Lo3RedenOntbindingHuwelijkOfGpCode(reden.getRubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap().getWaarde());
        }

        @Override
        protected BrpRedenEindeRelatieCode maakBrpWaarde(final ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap reden) {
            return new BrpRedenEindeRelatieCode(reden.getRedenEindeRelatie().getCode().getWaarde().charAt(0));
        }
    }
}
