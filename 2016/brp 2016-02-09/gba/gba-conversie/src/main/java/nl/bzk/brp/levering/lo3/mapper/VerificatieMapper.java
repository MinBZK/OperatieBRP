/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een verificatie.
 */
@Component
public final class VerificatieMapper extends AbstractFormeelMapper<PersoonVerificatieHisVolledig, HisPersoonVerificatieModel, BrpVerificatieInhoud> {

    /**
     * Constructor.
     */
    public VerificatieMapper() {
        super(ElementEnum.PERSOON_VERIFICATIE_TIJDSTIPREGISTRATIE, ElementEnum.PERSOON_VERIFICATIE_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonVerificatieModel> getHistorieIterable(final PersoonVerificatieHisVolledig persoonVerificatieHisVolledig) {
        return persoonVerificatieHisVolledig.getPersoonVerificatieHistorie();
    }

    @Override
    public BrpVerificatieInhoud mapInhoud(final HisPersoonVerificatieModel historie, final OnderzoekMapper onderzoekMapper) {
        final BrpPartijCode partijCode =
                BrpMapperUtil.mapBrpPartijCode(
                    historie.getPersoonVerificatie().getPartij(),
                    onderzoekMapper.bepaalOnderzoek(historie.getPersoonVerificatie().getID(), ElementEnum.PERSOON_VERIFICATIE_PARTIJCODE, true));
        final BrpString soortVerificatie =
                BrpMapperUtil.mapBrpString(
                    historie.getPersoonVerificatie().getSoort(),
                    onderzoekMapper.bepaalOnderzoek(historie.getPersoonVerificatie().getID(), ElementEnum.PERSOON_VERIFICATIE_SOORT, true));
        final BrpDatum datumVerificatie =
                BrpMapperUtil.mapBrpDatum(
                    historie.getDatum(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_VERIFICATIE_DATUM, true));

        return new BrpVerificatieInhoud(partijCode, soortVerificatie, datumVerificatie);
    }
}
