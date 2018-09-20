/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.blobifier.exceptie.PersoonNietAanwezigExceptie;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.levering.afnemerindicaties.stappen.AbstractStappenTest;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HaalBetrokkenPersoonHisVolledigOpStapTest extends AbstractStappenTest {

    private static final String PARTIJ = "AGV";

    @Mock
    private BlobifierService blobifierService;

    @InjectMocks
    private HaalBetrokkenPersoonHisVolledigOpStap stap;

    @Test
    public final void kanPersoonOphalen() throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie {
        // arrange
        maakBericht(123550394, maakDummyLeveringinformatie(), 123, PARTIJ, SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));
        when(blobifierService.leesBlobActievePersoon(any(BurgerservicenummerAttribuut.class)))
            .thenReturn(TestPersoonAntwoordPersoon.maakAntwoordPersoon());

        // act
        final boolean stapResultaat = stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(true));
        assertThat(getBerichtContext().getPersoonHisVolledig(), notNullValue());
        assertThat(getResultaat().getTeArchiverenPersonenIngaandBericht().iterator().next(), notNullValue());
    }


    @Test
    public final void blobifierGooitExceptie() throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie {
        // arrange
        maakBericht(123550394, maakDummyLeveringinformatie(), 123, PARTIJ, SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));
        when(blobifierService.leesBlobActievePersoon(any(BurgerservicenummerAttribuut.class)))
            .thenThrow(new PersoonNietAanwezigExceptie(""));

        // act
        final boolean stapResultaat = stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(false));
        assertThat(getResultaat().getMeldingen().size(), is(1));
    }

    @Test
    public final void blobifierGooitNietUniekeBsnExceptie() throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie {
        // arrange
        maakBericht(123550394, maakDummyLeveringinformatie(), 123, PARTIJ, SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));
        when(blobifierService.leesBlobActievePersoon(any(BurgerservicenummerAttribuut.class)))
            .thenThrow(new NietUniekeBsnExceptie());

        // act
        final boolean stapResultaat = stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(false));
        assertThat(getResultaat().getMeldingen().size(), is(1));
        assertEquals(Regel.BRLV0009, getResultaat().getMeldingen().iterator().next().getRegel());
    }

    @Test
    public final void blobifierGooitPersoonNietGevondenExceptie() throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie {
        // arrange
        maakBericht(123550394, maakDummyLeveringinformatie(), 123, PARTIJ, SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
            new DatumEvtDeelsOnbekendAttribuut(20130101));
        when(blobifierService.leesBlobActievePersoon(any(BurgerservicenummerAttribuut.class)))
            .thenThrow(new PersoonNietAanwezigExceptie());

        // act
        final boolean stapResultaat = stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        // assert
        assertThat(stapResultaat, is(false));
        assertThat(getResultaat().getMeldingen().size(), is(1));
        assertEquals(Regel.BRLV0008, getResultaat().getMeldingen().iterator().next().getRegel());
    }
}
