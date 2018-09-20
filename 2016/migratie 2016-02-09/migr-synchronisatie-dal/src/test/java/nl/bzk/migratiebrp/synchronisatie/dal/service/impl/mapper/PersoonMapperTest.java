/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.afgeleid;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.groep;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.identificatie;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIDHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapperImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersoonMapperTest {

    private static final Partij PARTIJ_MIGRATIE = new Partij("Migratievoorziening", 199902);
    private static final int PARTIJ_GEMEENTE_CODE = act(1, 1).getPartijCode().getWaarde();
    private static final Partij PARTIJ_GEMEENTE = new Partij("Gemeente", PARTIJ_GEMEENTE_CODE);

    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    private PersoonMapper persoonMapper;

    private Lo3Bericht lo3Bericht;

    @Before
    public void setup() {
        when(dynamischeStamtabelRepository.getPartijByCode(BrpPartijCode.MIGRATIEVOORZIENING.getWaarde())).thenReturn(PARTIJ_MIGRATIE);
        when(dynamischeStamtabelRepository.getPartijByCode(PARTIJ_GEMEENTE_CODE)).thenReturn(PARTIJ_GEMEENTE);
        when(dynamischeStamtabelRepository.getSoortDocumentByNaam("Akte")).thenReturn(new SoortDocument("Akte", "Akte"));
        persoonMapper = new PersoonMapper(dynamischeStamtabelRepository, new SyncParameters(), new OnderzoekMapperImpl(null));
        lo3Bericht =
                new Lo3Bericht(
                    "PersoonMapperTest",
                    Lo3BerichtenBron.INITIELE_VULLING,
                    new Timestamp(System.currentTimeMillis()),
                    "TEST_DATA",
                    true);
    }

    @Test
    public void testJuisteRijInALaag() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        identificatie(6276180257L, 1234567);
        final BrpGroep<BrpIdentificatienummersInhoud> id1 =
                groep(identificatie(6276180257L, 1234567), his(19920808, 20100101, 19940930, null), act(1, 19940930));
        final BrpGroep<BrpIdentificatienummersInhoud> id2 =
                groep(identificatie(8049804065L, 1234568), his(20100101, null, 20130102, null), act(2, 20130102));
        final BrpGroep<BrpIdentificatienummersInhoud> id3 =
                groep(identificatie(1401323649L, 1234569), his(20100101, null, 20100102, 20100102), act(3, 20100102));

        builder.identificatienummersStapel(new BrpStapel<>(Arrays.asList(id1, id2, id3)));

        final BrpPersoonslijst brpPl = builder.build();
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonMapper.mapVanMigratie(brpPl, persoon, lo3Bericht);
        assertNotNull(persoon.getPersoonIDHistorieSet());
        assertEquals(3, persoon.getPersoonIDHistorieSet().size());

        final List<PersoonIDHistorie> idList = new ArrayList<>(persoon.getPersoonIDHistorieSet());
        Collections.sort(idList, new Comparator<PersoonIDHistorie>() {

            @Override
            public int compare(final PersoonIDHistorie his1, final PersoonIDHistorie his2) {
                return his1.getDatumTijdRegistratie().compareTo(his2.getDatumTijdRegistratie());
            }
        });
        assertEquals(6276180257L, idList.get(0).getAdministratienummer().longValue());
        assertEquals(1401323649L, idList.get(1).getAdministratienummer().longValue());
        assertEquals(8049804065L, idList.get(2).getAdministratienummer().longValue());

        assertEquals(8049804065L, persoon.getAdministratienummer().longValue());
    }

    @Test
    public void testFormeleHistorieEnALaagLeeg() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        identificatie(6276180257L, 1234567);
        final BrpGroep<BrpIdentificatienummersInhoud> id1 =
                groep(identificatie(1401323649L, 1234569), his(20100101, null, 20100102, 20100102), act(1, 20100102));

        builder.identificatienummersStapel(new BrpStapel<>(Arrays.asList(id1)));

        final BrpPersoonslijst brpPl = builder.build();
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonMapper.mapVanMigratie(brpPl, persoon, lo3Bericht);
        assertNotNull(persoon.getPersoonIDHistorieSet());
        assertEquals(1, persoon.getPersoonIDHistorieSet().size());

        assertEquals(1401323649L, persoon.getPersoonIDHistorieSet().iterator().next().getAdministratienummer().longValue());

        assertNull(persoon.getAdministratienummer());
    }

    @Test
    public void testMaterieleHistorieEnALaagLeeg() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        identificatie(6276180257L, 1234567);
        final BrpGroep<BrpIdentificatienummersInhoud> id1 =
                groep(identificatie(1401323649L, 1234569), his(20100101, 20100601, 20100102, null), act(1, 20100102));

        builder.identificatienummersStapel(new BrpStapel<>(Arrays.asList(id1)));

        final BrpPersoonslijst brpPl = builder.build();
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonMapper.mapVanMigratie(brpPl, persoon, lo3Bericht);
        assertNotNull(persoon.getPersoonIDHistorieSet());
        assertEquals(1, persoon.getPersoonIDHistorieSet().size());

        assertEquals(1401323649L, persoon.getPersoonIDHistorieSet().iterator().next().getAdministratienummer().longValue());

        assertNull(persoon.getAdministratienummer());
    }

    @Test
    public void testGeenHistorie() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        final BrpPersoonslijst brpPl = builder.build();
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonMapper.mapVanMigratie(brpPl, persoon, lo3Bericht);
        assertNotNull(persoon.getPersoonIDHistorieSet());
        assertTrue(persoon.getPersoonIDHistorieSet().isEmpty());

        assertNull(persoon.getAdministratienummer());
    }

    @Test
    public void testAdministratieveHandelingSync() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final BrpGroep<BrpIdentificatienummersInhoud> id =
                groep(identificatie(8049804065L, 1234568), his(20100101, null, 20130102, null), act(2, 20130102));
        final BrpGroep<BrpPersoonAfgeleidAdministratiefInhoud> aa =
                groep(afgeleid(), his(20100101, null, 20130102, null), act(2, 20130102));

        builder.identificatienummersStapel(new BrpStapel<>(Arrays.asList(id)));
        builder.persoonAfgeleidAdministratiefStapel(new BrpStapel<>(Arrays.asList(aa)));

        final BrpPersoonslijst brpPl = builder.build();
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonMapper.mapVanMigratie(brpPl, persoon, lo3Bericht);

        assertEquals(persoon.getAdministratieveHandeling().getSoort(), SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL);
    }

    @Test
    public void testAdministratieveHandelingInitieel() {
        final SyncParameters syncParameters = new SyncParameters();
        syncParameters.setInitieleVulling(true);
        persoonMapper = new PersoonMapper(dynamischeStamtabelRepository, syncParameters, new OnderzoekMapperImpl(null));

        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final BrpGroep<BrpIdentificatienummersInhoud> id =
                groep(identificatie(8049804065L, 1234568), his(20100101, null, 20130102, null), act(2, 20130102));
        final BrpGroep<BrpPersoonAfgeleidAdministratiefInhoud> aa =
                groep(afgeleid(), his(20100101, null, 20130102, null), act(2, 20130102));

        builder.identificatienummersStapel(new BrpStapel<>(Arrays.asList(id)));
        builder.persoonAfgeleidAdministratiefStapel(new BrpStapel<>(Arrays.asList(aa)));

        final BrpPersoonslijst brpPl = builder.build();
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoonMapper.mapVanMigratie(brpPl, persoon, lo3Bericht);

        assertEquals(persoon.getAdministratieveHandeling().getSoort(), SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
    }
}
