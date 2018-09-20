/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Aangever;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonMigratieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenWijzigingVerblijf;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortMigratie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersoonMigratieMapperTest {

    private static final char AANGEVER = 'I';
    private static final char REDEN_WIJZIGING = 'P';
    private static final short LAND_OF_GEBIED = (short) 1234;

    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @Mock
    private BRPActieFactory brpActieFactory;

    @Mock
    private OnderzoekMapper onderzoekMapper;

    @InjectMocks
    private PersoonMigratieMapper mapper;

    @Test
    public void testMapVanMigratie() {
        final List<BrpGroep<BrpMigratieInhoud>> groepen = new ArrayList<>();

        final BrpGroep<BrpMigratieInhoud> groep = maakGroep();
        groepen.add(groep);

        final BrpStapel<BrpMigratieInhoud> brpStapel = new BrpStapel<>(groepen);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);

        final RedenWijzigingVerblijf testRedenWijziging = new RedenWijzigingVerblijf(REDEN_WIJZIGING, "Persoon");
        Mockito.when(dynamischeStamtabelRepository.getRedenWijzigingVerblijf(REDEN_WIJZIGING)).thenReturn(testRedenWijziging);
        final Aangever testAangever = new Aangever(AANGEVER, "Ingeschrevene", "Ingeschrevene omschrijving");
        Mockito.when(dynamischeStamtabelRepository.getAangeverByCode(AANGEVER)).thenReturn(testAangever);
        final LandOfGebied testLandOfGebied = new LandOfGebied(LAND_OF_GEBIED, "Test land");
        Mockito.when(dynamischeStamtabelRepository.getLandOfGebiedByCode(LAND_OF_GEBIED)).thenReturn(testLandOfGebied);

        mapper.mapVanMigratie(brpStapel, persoon);

        // Test A-laag
        assertEquals(SoortMigratie.IMMIGRATIE, persoon.getSoortMigratie());
        assertEquals('I', persoon.getAangeverMigratie().getCode());
        assertEquals('P', persoon.getRedenWijzigingMigratie().getCode());
        assertEquals((short) 1234, persoon.getLandOfGebiedMigratie().getCode());

        // Test historie
        assertEquals(1, persoon.getPersoonMigratieHistorieSet().size());
        final PersoonMigratieHistorie historie = persoon.getPersoonMigratieHistorieSet().iterator().next();
        assertEquals(SoortMigratie.IMMIGRATIE, historie.getSoortMigratie());
        assertEquals('I', historie.getAangeverMigratie().getCode());
        assertEquals('P', historie.getRedenWijzigingMigratie().getCode());
        assertEquals((short) 1234, historie.getLandOfGebied().getCode());
    }

    private BrpGroep<BrpMigratieInhoud> maakGroep() {
        final BrpMigratieInhoud inhoud =
                new BrpMigratieInhoud(
                    BrpSoortMigratieCode.IMMIGRATIE,
                    new BrpRedenWijzigingVerblijfCode(REDEN_WIJZIGING),
                    new BrpAangeverCode(AANGEVER),
                    new BrpLandOfGebiedCode(LAND_OF_GEBIED),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
        final BrpHistorie historie =
                new BrpHistorie(new BrpDatum(20000101, null), null, BrpDatumTijd.fromDatumTijdMillis(20000101121212L, null), null, null);
        final BrpActie actieInhoud =
                new BrpActie(1L, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode(1), BrpDatumTijd.fromDatumTijdMillis(
                    20000101121212L,
                    null), null, null, 1, null);
        return new BrpGroep<>(inhoud, historie, actieInhoud, null, null);
    }
}
