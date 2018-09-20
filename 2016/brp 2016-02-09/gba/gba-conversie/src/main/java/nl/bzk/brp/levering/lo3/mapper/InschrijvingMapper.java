/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt de inschrijving.
 */
@Component
public final class InschrijvingMapper extends AbstractFormeelMapper<PersoonHisVolledig, HisPersoonInschrijvingModel, BrpInschrijvingInhoud> {
    /**
     * Constructor.
     */
    public InschrijvingMapper() {
        super(ElementEnum.PERSOON_INSCHRIJVING_TIJDSTIPREGISTRATIE, ElementEnum.PERSOON_INSCHRIJVING_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonInschrijvingModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonInschrijvingHistorie();
    }

    @Override
    public BrpInschrijvingInhoud mapInhoud(final HisPersoonInschrijvingModel historie, final OnderzoekMapper onderzoekMapper) {
        final BrpDatum datumInschrijving =
                BrpMapperUtil.mapBrpDatum(
                    historie.getDatumInschrijving(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_INSCHRIJVING_DATUM, true));
        final BrpLong versienummer =
                BrpMapperUtil.mapBrpLong(
                    historie.getVersienummer(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_INSCHRIJVING_VERSIENUMMER, true));
        final BrpDatumTijd datumtijdstempel =
                BrpMapperUtil.mapBrpDatumTijd(
                    historie.getDatumtijdstempel(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL, true));
        return new BrpInschrijvingInhoud(datumInschrijving, versienummer, datumtijdstempel);
    }

}
