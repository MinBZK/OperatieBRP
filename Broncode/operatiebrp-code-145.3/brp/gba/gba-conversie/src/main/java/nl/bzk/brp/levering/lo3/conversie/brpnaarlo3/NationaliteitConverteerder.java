/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.brpnaarlo3;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpNationaliteitConverteerder.AbstractNationaliteitConverteerder;
import org.springframework.stereotype.Component;

/**
 * Nationaliteit converteerder.
 */
@Component("brpOverrideNationaliteitConverteerder")
public final class NationaliteitConverteerder extends AbstractNationaliteitConverteerder<BrpNationaliteitInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Contructor.
     * @param converteerder brp attribuut converteerder
     */
    @Inject
    public NationaliteitConverteerder(final BrpAttribuutConverteerder converteerder) {
        super(converteerder);
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    public Lo3NationaliteitInhoud vulInhoud(
            final Lo3NationaliteitInhoud lo3Inhoud,
            final BrpNationaliteitInhoud brpInhoud,
            final BrpNationaliteitInhoud brpVorigeInhoud) {
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder(lo3Inhoud);

        if (brpInhoud == null || brpInhoud.isLeeg() && brpInhoud.getRedenVerliesNederlandschapCode() == null) {
            builder.nationaliteitCode(null);
            builder.redenVerkrijgingNederlandschapCode(null);
            builder.redenVerliesNederlandschapCode(null);
        } else {
            builder.nationaliteitCode(getAttribuutConverteerder().converteerNationaliteit(brpInhoud.getNationaliteitCode()));
            builder.redenVerliesNederlandschapCode(getAttribuutConverteerder().converteerRedenNederlanderschap(brpInhoud.getRedenVerliesNederlandschapCode()));
            builder.redenVerkrijgingNederlandschapCode(
                    getAttribuutConverteerder().converteerRedenNederlanderschap(brpInhoud.getRedenVerkrijgingNederlandschapCode()));
        }

        return builder.build();
    }

}
