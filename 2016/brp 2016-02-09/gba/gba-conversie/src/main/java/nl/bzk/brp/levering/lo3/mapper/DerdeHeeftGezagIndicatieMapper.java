/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieDerdeHeeftGezagModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de indicatie derde heeft gezag.
 */
@Component
public final class DerdeHeeftGezagIndicatieMapper
        extends AbstractMaterieelMapper<PersoonHisVolledig, HisPersoonIndicatieDerdeHeeftGezagModel, BrpDerdeHeeftGezagIndicatieInhoud>
{
    /**
     * Constructor.
     */
    public DerdeHeeftGezagIndicatieMapper() {
        super(ElementEnum.PERSOON_INDICATIE_DERDEHEEFTGEZAG_DATUMAANVANGGELDIGHEID,
              ElementEnum.PERSOON_INDICATIE_DERDEHEEFTGEZAG_DATUMEINDEGELDIGHEID,
              ElementEnum.PERSOON_INDICATIE_DERDEHEEFTGEZAG_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_INDICATIE_DERDEHEEFTGEZAG_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonIndicatieDerdeHeeftGezagModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        if (persoonHisVolledig.getIndicatieDerdeHeeftGezag() == null) {
            return null;
        } else {
            return persoonHisVolledig.getIndicatieDerdeHeeftGezag().getPersoonIndicatieHistorie();
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
    public BrpDerdeHeeftGezagIndicatieInhoud mapInhoud(final HisPersoonIndicatieDerdeHeeftGezagModel historie, final OnderzoekMapper onderzoekMapper) {
        final BrpBoolean indicatie =
                BrpMapperUtil.mapBrpBoolean(
                    historie.getWaarde(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_INDICATIE_DERDEHEEFTGEZAG, true));
        final BrpString migratieRedenOpnameNationaliteit = BrpMapperUtil.mapBrpString(historie.getMigratieRedenOpnameNationaliteit(), null);
        final BrpString migratieRedenBeeindigenNationaliteit = BrpMapperUtil.mapBrpString(historie.getMigratieRedenBeeindigenNationaliteit(), null);

        return new BrpDerdeHeeftGezagIndicatieInhoud(indicatie, migratieRedenOpnameNationaliteit, migratieRedenBeeindigenNationaliteit);
    }
}
