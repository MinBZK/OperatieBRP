/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.mapper.SignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpReisdocumentConverteerder.SignaleringConverteerder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in persoon/indicatie signalering.
 */
@Component
public final class PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMutatieVerwerker extends
        AbstractFormeelMutatieVerwerker<Lo3ReisdocumentInhoud, BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud, HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel>
{
    /**
     * Constructor.
     *
     * @param mapper
     *            mapper
     * @param converteerder
     *            converteerder
     */
    @Autowired
    protected PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentMutatieVerwerker(
        final SignaleringMetBetrekkingTotVerstrekkenReisdocumentMapper mapper,
        final SignaleringConverteerder converteerder)
    {
        super(mapper, converteerder, null, ElementEnum.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_GROEP);
    }

}
