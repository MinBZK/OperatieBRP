/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import nl.bzk.brp.dataaccess.repository.historie.PersoonOpschortingHistorieRepository;
import nl.bzk.brp.model.gedeeld.RedenOpschorting;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class BRAL9003Test {

    @Mock
    private PersoonOpschortingHistorieRepository persoonOpschortingHistorieRepository;

    private BRAL9003                             bral9003;

    @Before
    public void init() {
        bral9003 = new BRAL9003();
        ReflectionTestUtils.setField(bral9003, "persoonOpschortingHistorieRepository",
                persoonOpschortingHistorieRepository);

        Mockito.when(persoonOpschortingHistorieRepository.haalOpActueleDatumOpschorting(1L)).thenReturn(20120505);
        Mockito.when(persoonOpschortingHistorieRepository.haalOpActueleDatumOpschorting(0L)).thenReturn(null);
    }

    @Test
    public void testHuidigeSituatieGeenOpschorting() {
        PersistentPersoon huidigeSituatie = new PersistentPersoon();
        Persoon nieuweSituatie = new Persoon();

        Melding melding = bral9003.executeer(huidigeSituatie, nieuweSituatie, 20150101);

        Mockito.verify(persoonOpschortingHistorieRepository,
                Mockito.times(0)).haalOpActueleDatumOpschorting(Matchers.anyLong());

        Assert.assertNull(melding);
    }

    @Test
    public void testHuidigeSituatieLeeg() {
        Melding melding = bral9003.executeer(null, null, 20150101);

        Mockito.verify(persoonOpschortingHistorieRepository,
                Mockito.times(0)).haalOpActueleDatumOpschorting(Matchers.anyLong());

        Assert.assertNull(melding);
    }

    @Test
    public void testHuidigeSituatieWelOpschortingMaarGeenOpschortingDatum() {
        PersistentPersoon huidigeSituatie = new PersistentPersoon();
        huidigeSituatie.setId(0L);
        huidigeSituatie.setRedenOpschortingBijhouding(RedenOpschorting.MINISTER);

        Persoon nieuweSituatie = new Persoon();

        Melding melding = bral9003.executeer(huidigeSituatie, nieuweSituatie, 20150101);

        Mockito.verify(persoonOpschortingHistorieRepository,
                Mockito.times(1)).haalOpActueleDatumOpschorting(0L);

        Assert.assertNull(melding);
    }

    @Test
    public void testWijzigingDatumVoorOpschortingDatum() {
        PersistentPersoon huidigeSituatie = new PersistentPersoon();
        huidigeSituatie.setId(1L);
        huidigeSituatie.setRedenOpschortingBijhouding(RedenOpschorting.MINISTER);

        Persoon nieuweSituatie = new Persoon();

        Melding melding = bral9003.executeer(huidigeSituatie, nieuweSituatie, 20120504);

        Mockito.verify(persoonOpschortingHistorieRepository,
                Mockito.times(1)).haalOpActueleDatumOpschorting(1L);

        Assert.assertNull(melding);
    }

    @Test
    public void testWijzigingDatumOpOpschortingDatum() {
        PersistentPersoon huidigeSituatie = new PersistentPersoon();
        huidigeSituatie.setId(1L);
        huidigeSituatie.setRedenOpschortingBijhouding(RedenOpschorting.MINISTER);

        Persoon nieuweSituatie = new Persoon();

        Melding melding = bral9003.executeer(huidigeSituatie, nieuweSituatie, 20120505);

        Mockito.verify(persoonOpschortingHistorieRepository,
                Mockito.times(1)).haalOpActueleDatumOpschorting(1L);

        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.BRAL9003, melding.getCode());
    }

    @Test
    public void testWijzigingDatumNaOpschortingDatum() {
        PersistentPersoon huidigeSituatie = new PersistentPersoon();
        huidigeSituatie.setId(1L);
        huidigeSituatie.setRedenOpschortingBijhouding(RedenOpschorting.MINISTER);

        Persoon nieuweSituatie = new Persoon();

        Melding melding = bral9003.executeer(huidigeSituatie, nieuweSituatie, 20120506);

        Mockito.verify(persoonOpschortingHistorieRepository,
                Mockito.times(1)).haalOpActueleDatumOpschorting(1L);

        Assert.assertNotNull(melding);
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, melding.getSoort());
        Assert.assertEquals(MeldingCode.BRAL9003, melding.getCode());
    }

}
