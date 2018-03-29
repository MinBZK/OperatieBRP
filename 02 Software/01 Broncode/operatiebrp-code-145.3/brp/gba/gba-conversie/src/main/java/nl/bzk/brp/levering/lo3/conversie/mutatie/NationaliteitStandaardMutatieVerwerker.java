/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.conversie.brpnaarlo3.NationaliteitConverteerder;
import nl.bzk.brp.levering.lo3.mapper.NationaliteitMapper;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in persoon/nationaliteit.
 */
@Component
public final class NationaliteitStandaardMutatieVerwerker extends AbstractMaterieelMutatieVerwerker<Lo3NationaliteitInhoud, BrpNationaliteitInhoud> {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final NationaliteitConverteerder converteerder;

    /**
     * Constructor.
     * @param mapper mapper
     * @param attribuutConverteerder attributen converteerder
     */
    @Inject
    protected NationaliteitStandaardMutatieVerwerker(final NationaliteitMapper mapper, final BrpAttribuutConverteerder attribuutConverteerder) {
        super(mapper, new NationaliteitConverteerder(attribuutConverteerder), attribuutConverteerder, null, NationaliteitMapper.GROEP_ELEMENT, LOGGER);
        converteerder = new NationaliteitConverteerder(attribuutConverteerder);
    }

    @Override
    protected Lo3NationaliteitInhoud verwerkInhoud(final Lo3NationaliteitInhoud lo3Inhoud, final BrpNationaliteitInhoud brpInhoud,
                                                   final MetaRecord brpHistorie, final OnderzoekMapper onderzoekMapper) {
        if (brpInhoud.getRedenVerliesNederlandschapCode() != null) {
            return converteerder.vulInhoud(lo3Inhoud, null, brpInhoud);
        } else {
            return converteerder.vulInhoud(lo3Inhoud, brpInhoud, null);
        }
    }

    @Override
    protected Lo3NationaliteitInhoud verwerkBeeindiging(final Lo3NationaliteitInhoud lo3Inhoud, final BrpNationaliteitInhoud brpInhoud) {
        final Lo3NationaliteitInhoud beeindigdeLo3Inhoud = converteerder.vulInhoud(converteerder.maakNieuweInhoud(), brpInhoud, null);
        final Lo3NationaliteitInhoud.Builder builder = new Lo3NationaliteitInhoud.Builder(lo3Inhoud);
        builder.redenVerliesNederlandschapCode(beeindigdeLo3Inhoud.getRedenVerliesNederlandschapCode());

        return builder.build();
    }
}
