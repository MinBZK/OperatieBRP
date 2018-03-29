/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

;

@RunWith(MockitoJUnitRunner.class)
public class PersoonVerificatieMapperTest {

    public static final BrpDatum DATUM_VERIFICATIE = new BrpDatum(1999_12_31, null);
    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @Mock
    private BRPActieFactory brpActieFactory;

    @Mock
    private OnderzoekMapper onderzoekMapper;

    @InjectMocks
    private PersoonVerificatieMapper mapper;

    @Test
    public void testMapVanMigratie() {
        final List<BrpGroep<BrpVerificatieInhoud>> groepen = new ArrayList<>();

        final BrpGroep<BrpVerificatieInhoud> groep = maakGroep();
        groepen.add(groep);

        final BrpStapel<BrpVerificatieInhoud> brpStapel = new BrpStapel<>(groepen);
        final PersoonVerificatie persoonVerificatie =
                new PersoonVerificatie(new Persoon(SoortPersoon.INGESCHREVENE), new Partij("leeg", "000001"), "soort verificatie");

        mapper.mapVanMigratie(brpStapel, persoonVerificatie, null);

        // Test historie
        assertEquals(1, persoonVerificatie.getPersoonVerificatieHistorieSet().size());
        final PersoonVerificatieHistorie historie = persoonVerificatie.getPersoonVerificatieHistorieSet().iterator().next();
        assertEquals(DATUM_VERIFICATIE.getWaarde(), historie.getDatum());
        assertEquals(persoonVerificatie, historie.getPersoonVerificatie());
    }

    private BrpGroep<BrpVerificatieInhoud> maakGroep() {
        final BrpVerificatieInhoud inhoud = new BrpVerificatieInhoud(BrpPartijCode.ONBEKEND, new BrpString("testVerificatie", null), DATUM_VERIFICATIE);
        final BrpHistorie historie = new BrpHistorie(new BrpDatum(2000_01_01, null), null, BrpDatumTijd.fromDatumTijdMillis(2000_01_01121212L, null), null, null);
        final BrpActie actieInhoud =
                new BrpActie(
                        1L,
                        BrpSoortActieCode.CONVERSIE_GBA,
                        new BrpPartijCode("000001"),
                        BrpDatumTijd.fromDatumTijdMillis(2000_01_01121212L, null),
                        null,
                        null,
                        1,
                        null);
        return new BrpGroep<>(inhoud, historie, actieInhoud, null, null);
    }
}
