/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVastgesteldNietNederlanderModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de indicatie vastgesteld niet Nederlander.
 */
@Component
public final class VastgesteldNietNederlanderIndicatieMapper extends
        AbstractMaterieelMapper<PersoonHisVolledig, HisPersoonIndicatieVastgesteldNietNederlanderModel, BrpVastgesteldNietNederlanderIndicatieInhoud>
{
    /**
     * Constructor.
     */
    public VastgesteldNietNederlanderIndicatieMapper() {
        super(ElementEnum.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_DATUMAANVANGGELDIGHEID,
              ElementEnum.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_DATUMEINDEGELDIGHEID,
              ElementEnum.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonIndicatieVastgesteldNietNederlanderModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        if (persoonHisVolledig.getIndicatieVastgesteldNietNederlander() == null) {
            return null;
        } else {
            return persoonHisVolledig.getIndicatieVastgesteldNietNederlander().getPersoonIndicatieHistorie();
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
    public BrpVastgesteldNietNederlanderIndicatieInhoud mapInhoud(
        final HisPersoonIndicatieVastgesteldNietNederlanderModel historie,
        final OnderzoekMapper onderzoekMapper)
    {
        final BrpBoolean indicatie =
                BrpMapperUtil.mapBrpBoolean(
                    historie.getWaarde(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER, true));

        return new BrpVastgesteldNietNederlanderIndicatieInhoud(
            indicatie,
            BrpMapperUtil.mapBrpString(historie.getMigratieRedenOpnameNationaliteit(), null),
            BrpMapperUtil.mapBrpString(historie.getMigratieRedenBeeindigenNationaliteit(), null));
    }
}
