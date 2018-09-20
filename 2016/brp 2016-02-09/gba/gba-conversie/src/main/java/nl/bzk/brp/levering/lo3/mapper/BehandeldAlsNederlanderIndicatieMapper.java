/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBehandeldAlsNederlanderModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de indicatie behandeld als Nederlander.
 */
@Component
public final class BehandeldAlsNederlanderIndicatieMapper
        extends AbstractMaterieelMapper<PersoonHisVolledig, HisPersoonIndicatieBehandeldAlsNederlanderModel, BrpBehandeldAlsNederlanderIndicatieInhoud>
{
    /**
     * Constructor.
     */
    public BehandeldAlsNederlanderIndicatieMapper() {
        super(ElementEnum.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_DATUMAANVANGGELDIGHEID,
              ElementEnum.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_DATUMEINDEGELDIGHEID,
              ElementEnum.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonIndicatieBehandeldAlsNederlanderModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        if (persoonHisVolledig.getIndicatieBehandeldAlsNederlander() == null) {
            return null;
        } else {
            return persoonHisVolledig.getIndicatieBehandeldAlsNederlander().getPersoonIndicatieHistorie();
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
    public BrpBehandeldAlsNederlanderIndicatieInhoud mapInhoud(
        final HisPersoonIndicatieBehandeldAlsNederlanderModel historie,
        final OnderzoekMapper onderzoekMapper)
    {
        final BrpBoolean indicatieBehandeldAlsNederlander =
                BrpMapperUtil.mapBrpBoolean(
                    historie.getWaarde(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER, true));
        final BrpString migratieRedenOpname = BrpMapperUtil.mapBrpString(historie.getMigratieRedenOpnameNationaliteit(), null);
        final BrpString migratieRedenBeeindigen = BrpMapperUtil.mapBrpString(historie.getMigratieRedenBeeindigenNationaliteit(), null);

        return new BrpBehandeldAlsNederlanderIndicatieInhoud(indicatieBehandeldAlsNederlander, migratieRedenOpname, migratieRedenBeeindigen);
    }
}
