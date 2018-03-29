/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersoonInschrijvingMapperTest {

    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @Mock
    private BRPActieFactory brpActieFactory;

    @Mock
    private OnderzoekMapper onderzoekMapper;

    @InjectMocks
    private PersoonInschrijvingMapper mapper;

    @Test
    public void testMapVanMigratie() {
        final List<BrpGroep<BrpInschrijvingInhoud>> groepen = new ArrayList<>();

        final BrpGroep<BrpInschrijvingInhoud> groep = maakGroep();
        groepen.add(groep);

        final BrpStapel<BrpInschrijvingInhoud> brpStapel = new BrpStapel<>(groepen);
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);

        mapper.mapVanMigratie(brpStapel, persoon, null);

        // Test historie
        assertEquals(1, persoon.getPersoonInschrijvingHistorieSet().size());
        final PersoonInschrijvingHistorie historie = persoon.getPersoonInschrijvingHistorieSet().iterator().next();
        assertEquals(5, historie.getVersienummer());
        assertEquals(20120101, historie.getDatumInschrijving());
    }

    private BrpGroep<BrpInschrijvingInhoud> maakGroep() {
        final BrpInschrijvingInhoud inhoud =
                new BrpInschrijvingInhoud(new BrpDatum(20120101, null), new BrpLong(5L, null), BrpDatumTijd.fromDatum(20130101, null));
        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20000101, null), null, BrpDatumTijd.fromDatumTijdMillis(20000101121212L, null), null, null);
        final BrpActie actieInhoud =
                new BrpActie(1L, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("000001"), BrpDatumTijd.fromDatumTijdMillis(
                        20000101121212L,
                        null), null, null, 1, null);
        final BrpActie actieVerval = null;
        final BrpActie actieGeldigheid = null;
        return new BrpGroep<>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);
    }
}
