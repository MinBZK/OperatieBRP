/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de indicatie verstrekkingsbeperking.
 */
@Component
public final class VerstrekkingsbeperkingIndicatieMapper extends
        AbstractFormeelMapper<PersoonHisVolledig, HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel, BrpVerstrekkingsbeperkingIndicatieInhoud>
{
    /**
     * Constructor.
     */
    public VerstrekkingsbeperkingIndicatieMapper() {
        super(ElementEnum.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        if (persoonHisVolledig.getIndicatieVolledigeVerstrekkingsbeperking() == null) {
            return null;
        } else {
            return persoonHisVolledig.getIndicatieVolledigeVerstrekkingsbeperking().getPersoonIndicatieHistorie();
        }
    }

    /**
     * Map inhoud.
     *
     * @param historie
     *            de historie die gemapt moet worden.
     * @param onderzoekMapper
     *            onderzoek mapper
     * @return de afgeleidAdministratief.
     */
    @Override
    public BrpVerstrekkingsbeperkingIndicatieInhoud mapInhoud(
        final HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel historie,
        final OnderzoekMapper onderzoekMapper)
    {
        final BrpBoolean indicatie =
                BrpMapperUtil.mapBrpBoolean(
                    historie.getWaarde(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING, true));

        return new BrpVerstrekkingsbeperkingIndicatieInhoud(
            indicatie,
            BrpMapperUtil.mapBrpString(historie.getMigratieRedenOpnameNationaliteit(), null),
            BrpMapperUtil.mapBrpString(historie.getMigratieRedenBeeindigenNationaliteit(), null));
    }
}
