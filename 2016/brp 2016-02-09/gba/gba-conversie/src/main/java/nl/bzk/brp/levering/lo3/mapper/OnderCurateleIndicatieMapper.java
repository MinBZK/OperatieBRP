/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieOnderCurateleModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de indicatie onder curatele.
 */
@Component
public final class OnderCurateleIndicatieMapper
        extends AbstractMaterieelMapper<PersoonHisVolledig, HisPersoonIndicatieOnderCurateleModel, BrpOnderCurateleIndicatieInhoud>
{
    /**
     * Constructor.
     */
    public OnderCurateleIndicatieMapper() {
        super(ElementEnum.PERSOON_INDICATIE_ONDERCURATELE_DATUMAANVANGGELDIGHEID,
              ElementEnum.PERSOON_INDICATIE_ONDERCURATELE_DATUMEINDEGELDIGHEID,
              ElementEnum.PERSOON_INDICATIE_ONDERCURATELE_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_INDICATIE_ONDERCURATELE_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonIndicatieOnderCurateleModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        if (persoonHisVolledig.getIndicatieOnderCuratele() == null) {
            return null;
        } else {
            return persoonHisVolledig.getIndicatieOnderCuratele().getPersoonIndicatieHistorie();
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
    public BrpOnderCurateleIndicatieInhoud mapInhoud(final HisPersoonIndicatieOnderCurateleModel historie, final OnderzoekMapper onderzoekMapper) {
        final BrpBoolean indicatie =
                BrpMapperUtil.mapBrpBoolean(
                    historie.getWaarde(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_INDICATIE_ONDERCURATELE, true));

        return new BrpOnderCurateleIndicatieInhoud(
            indicatie,
            BrpMapperUtil.mapBrpString(historie.getMigratieRedenOpnameNationaliteit(), null),
            BrpMapperUtil.mapBrpString(historie.getMigratieRedenBeeindigenNationaliteit(), null));
    }
}
