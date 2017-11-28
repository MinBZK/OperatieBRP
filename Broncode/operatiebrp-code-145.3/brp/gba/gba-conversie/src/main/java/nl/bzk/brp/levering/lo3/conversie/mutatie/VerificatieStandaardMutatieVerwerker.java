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
import nl.bzk.brp.levering.lo3.mapper.BrpMetaAttribuutMapper;
import nl.bzk.brp.levering.lo3.mapper.VerificatieMapper;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpInschrijvingConverteerder.VerificatieConverteerder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in persoon/verificaties.
 */
@Component
public final class VerificatieStandaardMutatieVerwerker extends AbstractFormeelMutatieVerwerker<Lo3InschrijvingInhoud, BrpVerificatieInhoud> {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Autowired
    private BrpAttribuutConverteerder attribuutConverteerder;

    /**
     * Constructor.
     * @param mapper mapper
     * @param attribuutConverteerder attributen converteerder
     */
    @Inject
    protected VerificatieStandaardMutatieVerwerker(final VerificatieMapper mapper, final BrpAttribuutConverteerder attribuutConverteerder) {
        super(mapper, new VerificatieConverteerder(attribuutConverteerder), attribuutConverteerder, null, VerificatieMapper.GROEP_ELEMENT, LOGGER);
    }

    @Override
    protected Lo3Documentatie verwerkInhoudInDocumentatie(final MetaRecord identiteitRecord, final MetaRecord record, final Lo3Documentatie documentatie) {
        final BrpPartijCode brpPartijCode = BrpMetaAttribuutMapper.mapBrpPartijCode(identiteitRecord.getAttribuut(VerificatieMapper.PARTIJ_ELEMENT), null);
        final Lo3RNIDeelnemerCode rniDeelnemer = attribuutConverteerder.converteerRNIDeelnemer(brpPartijCode);

        return Lo3Documentatie.build(null, null, null, null, null, rniDeelnemer, null);
    }
}
