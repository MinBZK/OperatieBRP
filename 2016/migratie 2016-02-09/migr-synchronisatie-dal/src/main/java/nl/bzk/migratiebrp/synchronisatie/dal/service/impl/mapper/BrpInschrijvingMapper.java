/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonInschrijvingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map inschrijving van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpInschrijvingMapper extends AbstractBrpMapper<PersoonInschrijvingHistorie, BrpInschrijvingInhoud> {

    @Override
    protected BrpInschrijvingInhoud mapInhoud(final PersoonInschrijvingHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpDatum datumInschrijving =
                BrpMapperUtil.mapDatum(
                    historie.getDatumInschrijving(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_INSCHRIJVING_DATUM, true));
        final BrpLong versienummer =
                BrpMapperUtil.mapBrpLong(
                    historie.getVersienummer(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_INSCHRIJVING_VERSIENUMMER, true));
        final BrpDatumTijd datumTijdStempel =
                BrpMapperUtil.mapBrpDatumTijd(
                    historie.getDatumtijdstempel(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL, true));
        return new BrpInschrijvingInhoud(datumInschrijving, versienummer, datumTijdStempel);
    }
}
