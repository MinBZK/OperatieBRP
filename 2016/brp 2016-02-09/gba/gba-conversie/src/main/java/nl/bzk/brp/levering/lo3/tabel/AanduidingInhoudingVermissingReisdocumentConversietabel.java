/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAanduidingInhoudingVermissingReisdocument;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractAanduidingInhoudingVermissingReisdocumentConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;

/**
 * De conversietabel voor het converteren van de 'LO3 aanduiding inhouding dan wel vermissing Nederlands
 * reisdocument'-code naar de 'BRP Reden vervallen reisdocument'-code en vice versa.
 */
public final class AanduidingInhoudingVermissingReisdocumentConversietabel extends AbstractAanduidingInhoudingVermissingReisdocumentConversietabel {

    /**
     * Maakt een AanduidingInhoudingVermissingReisdocumentConversietabel object.
     *
     * @param list de lijst met waarden uit de conversietabel.
     */
    public AanduidingInhoudingVermissingReisdocumentConversietabel(final List<ConversieAanduidingInhoudingVermissingReisdocument> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link ConversieAanduidingInhoudingVermissingReisdocument} naar een conversie map van de
     * LO3 waarde {@link Lo3AanduidingInhoudingVermissingNederlandsReisdocument} en de BRP waarde
     * {@link BrpAanduidingInhoudingOfVermissingReisdocumentCode}.
     */
    private static final class Converter extends AbstractLijstConverter<ConversieAanduidingInhoudingVermissingReisdocument,
        Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode>
    {

        @Override
        protected Lo3AanduidingInhoudingVermissingNederlandsReisdocument maakLo3Waarde(final ConversieAanduidingInhoudingVermissingReisdocument reden) {
            return new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(
                String.valueOf(reden.getRubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument().getWaarde()));
        }

        @Override
        protected BrpAanduidingInhoudingOfVermissingReisdocumentCode maakBrpWaarde(final ConversieAanduidingInhoudingVermissingReisdocument reden) {
            return new BrpAanduidingInhoudingOfVermissingReisdocumentCode(reden.getAanduidingInhoudingVermissingReisdocument()
                                                                               .getCode()
                                                                               .getWaarde()
                                                                               .charAt(0));
        }
    }
}
