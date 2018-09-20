/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.mapper.BrpMapperUtil;
import nl.bzk.brp.levering.lo3.mapper.VerificatieMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;
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
public final class VerificatieStandaardMutatieVerwerker
        extends AbstractFormeelMutatieVerwerker<Lo3InschrijvingInhoud, BrpVerificatieInhoud, HisPersoonVerificatieModel>
{

    @Autowired
    private BrpAttribuutConverteerder attribuutConverteerder;

    /**
     * Constructor.
     *
     * @param mapper
     *            mapper
     * @param converteerder
     *            converteerder
     */
    @Autowired
    protected VerificatieStandaardMutatieVerwerker(final VerificatieMapper mapper, final VerificatieConverteerder converteerder) {
        super(mapper, converteerder, null, ElementEnum.PERSOON_VERIFICATIE_STANDAARD);
    }

    @Override
    protected Lo3Documentatie verwerkInhoudInDocumentatie(final HisPersoonVerificatieModel brpHistorie, final Lo3Documentatie documentatie) {
        final BrpPartijCode brpPartijCode = BrpMapperUtil.mapBrpPartijCode(brpHistorie.getPersoonVerificatie().getPartij(), null);
        final Lo3RNIDeelnemerCode rniDeelnemer = attribuutConverteerder.converteerRNIDeelnemer(brpPartijCode);

        return Lo3Documentatie.build(null, null, null, null, null, rniDeelnemer, null);
    }
}
