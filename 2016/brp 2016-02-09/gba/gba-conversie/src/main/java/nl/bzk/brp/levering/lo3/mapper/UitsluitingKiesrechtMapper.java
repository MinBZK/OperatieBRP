/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingKiesrechtModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt de uitsluiting kiesrecht.
 */
@Component
public final class UitsluitingKiesrechtMapper extends
        AbstractFormeelMapper<PersoonHisVolledig, HisPersoonUitsluitingKiesrechtModel, BrpUitsluitingKiesrechtInhoud>
{
    /**
     * Constructor.
     */
    public UitsluitingKiesrechtMapper() {
        super(ElementEnum.PERSOON_UITSLUITINGKIESRECHT_TIJDSTIPREGISTRATIE, ElementEnum.PERSOON_UITSLUITINGKIESRECHT_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonUitsluitingKiesrechtModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonUitsluitingKiesrechtHistorie();
    }

    @Override
    public BrpUitsluitingKiesrechtInhoud mapInhoud(final HisPersoonUitsluitingKiesrechtModel historie, final OnderzoekMapper onderzoekMapper) {
        final BrpBoolean indicatieUitsluitingKiesrecht =
                BrpMapperUtil.mapBrpBoolean(
                    historie.getIndicatieUitsluitingKiesrecht(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_UITSLUITINGKIESRECHT_INDICATIE, true));

        final BrpDatum datumVoorzienEindeUitsluitingKiesrecht =
                BrpMapperUtil.mapBrpDatum(
                    historie.getDatumVoorzienEindeUitsluitingKiesrecht(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE, true));

        return new BrpUitsluitingKiesrechtInhoud(indicatieUitsluitingKiesrecht, datumVoorzienEindeUitsluitingKiesrecht);
    }
}
