/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt de deelname EU verkiezingen.
 */
@Component
public final class DeelnameEuVerkiezingenMapper extends
        AbstractFormeelMapper<PersoonHisVolledig, HisPersoonDeelnameEUVerkiezingenModel, BrpDeelnameEuVerkiezingenInhoud>
{
    /**
     * Constructor.
     */
    public DeelnameEuVerkiezingenMapper() {
        super(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN_TIJDSTIPREGISTRATIE, ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonDeelnameEUVerkiezingenModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonDeelnameEUVerkiezingenHistorie();
    }

    @Override
    public BrpDeelnameEuVerkiezingenInhoud mapInhoud(final HisPersoonDeelnameEUVerkiezingenModel historie, final OnderzoekMapper onderzoekMapper) {
        final BrpBoolean indicatieDeelnameEuVerkiezingen =
                BrpMapperUtil.mapBrpBoolean(
                    historie.getIndicatieDeelnameEUVerkiezingen(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME, true));

        final BrpDatum datumAanleidingAanpassingDeelnameEuVerkiezingen =
                BrpMapperUtil.mapBrpDatum(
                    historie.getDatumAanleidingAanpassingDeelnameEUVerkiezingen(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING, true));

        final BrpDatum datumVoorzienEindeUitsluitingEuVerkiezingen =
                BrpMapperUtil.mapBrpDatum(
                    historie.getDatumVoorzienEindeUitsluitingEUVerkiezingen(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMVOORZIENEINDEUITSLUITING, true));

        return new BrpDeelnameEuVerkiezingenInhoud(
            indicatieDeelnameEuVerkiezingen,
            datumAanleidingAanpassingDeelnameEuVerkiezingen,
            datumVoorzienEindeUitsluitingEuVerkiezingen);

    }
}
