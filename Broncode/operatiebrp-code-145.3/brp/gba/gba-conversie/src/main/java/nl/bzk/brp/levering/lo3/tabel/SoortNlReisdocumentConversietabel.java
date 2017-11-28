/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNlReisdocument;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractSoortNlReisdocumentConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;

/**
 * De conversietabel om 'Lo3 Soort Nederlands reisdocument'-code (GBA Tabel 48) te converteren naar de 'BRP Soort
 * Nederlands reisdocument'-code en vice versa.
 */
public final class SoortNlReisdocumentConversietabel extends AbstractSoortNlReisdocumentConversietabel {

    /**
     * Maakt een SoortNlReisdocumentConversietabel object.
     * @param list de lijst met waarden uit de conversietabel.
     */
    public SoortNlReisdocumentConversietabel(final List<SoortNlReisdocument> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link SoortNlReisdocument} naar een conversie map van de LO3 waarde
     * {@link Lo3SoortNederlandsReisdocument} en de BRP waarde {@link BrpSoortNederlandsReisdocumentCode}.
     */
    private static final class Converter
            extends AbstractLijstConverter<SoortNlReisdocument, Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode> {
        @Override
        protected Lo3SoortNederlandsReisdocument maakLo3Waarde(final SoortNlReisdocument soortNlReisdocument) {
            return new Lo3SoortNederlandsReisdocument(soortNlReisdocument.getLo3NederlandsReisdocument());
        }

        @Override
        protected BrpSoortNederlandsReisdocumentCode maakBrpWaarde(final SoortNlReisdocument soortNlReisdocument) {
            return new BrpSoortNederlandsReisdocumentCode(soortNlReisdocument.getSoortNederlandsReisdocument().getCode());
        }
    }
}
