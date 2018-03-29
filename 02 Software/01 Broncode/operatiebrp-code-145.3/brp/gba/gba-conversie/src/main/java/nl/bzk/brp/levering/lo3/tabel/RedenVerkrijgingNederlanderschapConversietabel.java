/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractRedenVerkrijgingNederlanderschapConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;

/**
 * Deze conversietabel mapt een Lo3AanduidingVerblijfstitelCode op de corresponderen BrpVerblijfstitelCode en vice
 * versa.
 */
public final class RedenVerkrijgingNederlanderschapConversietabel extends AbstractRedenVerkrijgingNederlanderschapConversietabel {

    /**
     * Maakt een RedenVerkrijgingNederlanderschapConversietabel object.
     * @param list de lijst met waarden uit de conversietabel.
     */
    public RedenVerkrijgingNederlanderschapConversietabel(final List<RedenVerkrijgingNLNationaliteit> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link RedenVerkrijgingNLNationaliteit} naar een conversie map van de LO3 waarde
     * {@link Lo3RedenNederlandschapCode} en de BRP waarde {@link BrpRedenVerkrijgingNederlandschapCode}.
     */
    private static final class Converter
            extends AbstractLijstConverter<RedenVerkrijgingNLNationaliteit, Lo3RedenNederlandschapCode, BrpRedenVerkrijgingNederlandschapCode> {
        @Override
        protected Lo3RedenNederlandschapCode maakLo3Waarde(final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit) {
            return new Lo3RedenNederlandschapCode(redenVerkrijgingNLNationaliteit.getCode());
        }

        @Override
        protected BrpRedenVerkrijgingNederlandschapCode maakBrpWaarde(final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit) {
            return new BrpRedenVerkrijgingNederlandschapCode(redenVerkrijgingNLNationaliteit.getCode());
        }
    }
}
