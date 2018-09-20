/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de indicatie bijzondere verblijfsrechtelijke positie.
 */
@Component
public final class BijzondereVerblijfsrechtelijkePositieIndicatieMapper extends
        AbstractFormeelMapper<PersoonHisVolledig, HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel, BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud>
{
    /**
     * Constructor.
     */
    public BijzondereVerblijfsrechtelijkePositieIndicatieMapper() {
        super(ElementEnum.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        if (persoonHisVolledig.getIndicatieBijzondereVerblijfsrechtelijkePositie() == null) {
            return null;
        } else {
            return persoonHisVolledig.getIndicatieBijzondereVerblijfsrechtelijkePositie().getPersoonIndicatieHistorie();
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
    public BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud mapInhoud(
        final HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel historie,
        final OnderzoekMapper onderzoekMapper)
    {
        return new BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud(
            BrpMapperUtil.mapBrpBoolean(
                historie.getWaarde(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE, true)),
            BrpMapperUtil.mapBrpString(historie.getMigratieRedenOpnameNationaliteit(), null),
            BrpMapperUtil.mapBrpString(historie.getMigratieRedenBeeindigenNationaliteit(), null)

        );
    }
}
