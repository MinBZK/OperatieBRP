/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.brpnaarlo3;

import nl.bzk.brp.levering.lo3.mapper.AbstractMaterieelMapper;
import nl.bzk.brp.levering.lo3.mapper.BrpMapperUtil;
import nl.bzk.brp.levering.lo3.mapper.OnderzoekMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import org.springframework.stereotype.Component;

/**
 * Mapt een relatie.
 */
@Component
public final class OuderOuderschapMapper
        extends AbstractMaterieelMapper<OuderHisVolledig, HisOuderOuderschapModel, BrpFamilierechtelijkeBetrekkingInhoud>
{

    /**
     * Constructor.
     */
    public OuderOuderschapMapper() {
        super(ElementEnum.OUDER_OUDERSCHAP_DATUMAANVANGGELDIGHEID,
              ElementEnum.OUDER_OUDERSCHAP_DATUMEINDEGELDIGHEID,
              ElementEnum.OUDER_OUDERSCHAP_TIJDSTIPREGISTRATIE,
              ElementEnum.OUDER_OUDERSCHAP_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisOuderOuderschapModel> getHistorieIterable(final OuderHisVolledig hisVolledig) {
        return hisVolledig.getOuderOuderschapHistorie();
    }

    @Override
    public BrpFamilierechtelijkeBetrekkingInhoud mapInhoud(final HisOuderOuderschapModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpFamilierechtelijkeBetrekkingInhoud(
            BrpMapperUtil.mapBrpDatum(
                historie.getDatumAanvangGeldigheid(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.OUDER_OUDERSCHAP_DATUMAANVANGGELDIGHEID, true)));
    }
}
