/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.text.DecimalFormat;
import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractRedenVerliesNederlanderschapConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;

/**
 * Deze conversietabel mapt een Lo3AanduidingVerblijfstitelCode op de corresponderen BrpVerblijfstitelCode en vice
 * versa.
 */
public final class RedenVerliesNederlanderschapConversietabel extends AbstractRedenVerliesNederlanderschapConversietabel {
    /**
     * Maakt een RedenVerliesNederlanderschapConversietabel object.
     *
     * @param list de lijst met waarden uit de conversietabel.
     */
    public RedenVerliesNederlanderschapConversietabel(final List<RedenVerliesNLNationaliteit> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link RedenVerliesNLNationaliteit} naar een conversie map van de LO3 waarde
     * {@link Lo3RedenNederlandschapCode} en de BRP waarde {@link BrpRedenVerliesNederlandschapCode}.
     */
    private static final class Converter extends
            AbstractLijstConverter<RedenVerliesNLNationaliteit, Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode>
    {
        private static final DecimalFormat REDEN_VERLIES_CODE_FORMAT = new DecimalFormat("000");

        @Override
        protected Lo3RedenNederlandschapCode maakLo3Waarde(final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
            return new Lo3RedenNederlandschapCode(REDEN_VERLIES_CODE_FORMAT.format(redenVerliesNLNationaliteit.getCode().getWaarde()));
        }

        @Override
        protected BrpRedenVerliesNederlandschapCode maakBrpWaarde(final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
            return new BrpRedenVerliesNederlandschapCode(redenVerliesNLNationaliteit.getCode().getWaarde());
        }
    }
}
