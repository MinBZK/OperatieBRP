/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import nl.bzk.brp.blobifier.exceptie.NietUniekeAnummerExceptie;
import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.blobifier.exceptie.PersoonNietAanwezigExceptie;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.regels.impl.gegevenset.definitieregels.BRBY0002;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.business.stappen.bevraging.TestDataOpvragenPersoonBerichtUitvoerStap;
import nl.bzk.brp.dataaccess.exceptie.PersoonNietGevondenExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenBericht;
import nl.bzk.brp.model.bevraging.bijhouding.ZoekPersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.hisvolledig.util.RelatieHisVolledigUtil;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class BijhoudingBevragingUitvoerStapTest {

    @InjectMocks
    private final BijhoudingBevragingUitvoerStap bijhoudingBevragingUitvoerStap =
        new BijhoudingBevragingUitvoerStap();

    @Mock
    private PersoonRepository persoonRepository;

    @Mock
    private BlobifierService blobifierService;

    @Mock
    private ObjectSleutelService objectSleutelService;

    @Mock
    private BRBY0002 brby0002;

    @Mock
    private BevragingBerichtContextBasis context;

    @Before
    // Zorg ervoor dat er verschillende testpersonen worden geretourneerd.
    public final void init() throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie, NietUniekeAnummerExceptie {
        Mockito.when(blobifierService.leesBlob(
            Matchers.any(BurgerservicenummerAttribuut.class))).thenReturn(
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon());

        Mockito.when(blobifierService.leesBlob(
            Matchers.any(AdministratienummerAttribuut.class))).thenReturn(
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon());

        Mockito.when(blobifierService.leesBlob(
            Matchers.any(Integer.class))).thenReturn(
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon(),
            TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon());

        Mockito.when(blobifierService.leesBlob(
            TestDataOpvragenPersoonBerichtUitvoerStap.ID_PERSOON_PARTNER))
            .thenReturn(RelatieHisVolledigUtil.haalPartnerBetrokkenhedenUitPersoon(
                TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon()).iterator().next().getPersoon());

        Mockito.when(context.getPartij()).thenReturn(
            new PartijAttribuut(TestPartijBuilder.maker().metCode(new PartijCodeAttribuut(1)).maak()));

        Mockito.when(objectSleutelService.genereerObjectSleutelString(Mockito.anyInt(),
            Mockito.anyInt())).thenReturn("foobar");
    }

    @Test
    public final void testVraagOpDetailPersoonMetBsnPersoonGevonden() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        Mockito.when(context.getLeveringinformatie()).thenReturn(new Leveringinformatie(null, null));

        bijhoudingBevragingUitvoerStap.voerStapUit(
            TestDataOpvragenPersoonBerichtUitvoerStap
                .maakGeefDetailsPersoonBerichtMetBsn(), context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVraagOpDetailPersoonMetAnummerPersoonGevonden() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        Mockito.when(context.getLeveringinformatie()).thenReturn(new Leveringinformatie(null, null));
        bijhoudingBevragingUitvoerStap.voerStapUit(
            TestDataOpvragenPersoonBerichtUitvoerStap
                .maakGeefDetailsPersoonBerichtMetAnr(), context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVraagOpDetailPersoonMetAnummerGeenPersoonGevonden() {
        Mockito.when(persoonRepository.zoekIdBijAnummer(
            Matchers.any(AdministratienummerAttribuut.class))).thenThrow(new PersoonNietGevondenExceptie());

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        bijhoudingBevragingUitvoerStap.voerStapUit(
            TestDataOpvragenPersoonBerichtUitvoerStap
                .maakGeefDetailsPersoonBerichtMetAnr(), null, resultaat);

        assertGeenPersoonGevondenMelding(resultaat);
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }


    @Test
    public final void testVraagOpDetailPersoonMetBsnGeenPersoonGevonden() {
        Mockito.when(persoonRepository.zoekIdBijBSN(
            Matchers.any(BurgerservicenummerAttribuut.class))).thenThrow(new PersoonNietGevondenExceptie());

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        bijhoudingBevragingUitvoerStap.voerStapUit(
            TestDataOpvragenPersoonBerichtUitvoerStap
                .maakGeefDetailsPersoonBerichtMetBsn(), null, resultaat);

        assertGeenPersoonGevondenMelding(resultaat);
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVraagOpKandidaatVaderNietGevonden() throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie {
        // fake dat de moeder is gevonden
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwIdentificatienummersRecord(20110101, null, 20110101)
            .burgerservicenummer(1234)
            .eindeRecord()
            .nieuwGeslachtsaanduidingRecord(20110101, null, 20110101)
            .geslachtsaanduiding(Geslachtsaanduiding.VROUW)
            .eindeRecord()
            .build();
        ReflectionTestUtils.setField(moeder, "iD", 1234);

        Mockito.when(blobifierService
            .leesBlob(Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(moeder);

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        bijhoudingBevragingUitvoerStap.voerStapUit(maakVraagKandidaatVaderBericht("1234"), null,
            resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals("Kandidaat-vader kan niet worden bepaald.", resultaat.getMeldingen().get(0)
            .getMeldingTekst());
        Assert.assertEquals(SoortMelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(Regel.BRPUC50110, resultaat.getMeldingen().get(0).getRegel());
        Assert.assertEquals(0, resultaat.getGevondenPersonen().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVraagOpKandidaatVaderBsnIsGeenVrouw() throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie {
        // fake dat de moeder is gevonden
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwIdentificatienummersRecord(20110101, null, 20110101)
            .burgerservicenummer(1234)
            .eindeRecord()
            .nieuwGeslachtsaanduidingRecord(20110101, null, 20110101)
            .geslachtsaanduiding(Geslachtsaanduiding.MAN)
            .eindeRecord()
            .build();
        ReflectionTestUtils.setField(moeder, "iD", 1234);

        Mockito.when(blobifierService
            .leesBlob(Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(moeder);

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        bijhoudingBevragingUitvoerStap.voerStapUit(maakVraagKandidaatVaderBericht("1234"),
            null,
            resultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals("De persoon is niet van het vrouwelijk geslacht.",
            resultaat.getMeldingen().get(0).getMeldingTekst());
        Assert.assertEquals(SoortMelding.FOUT, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(Regel.ALG0001, resultaat.getMeldingen().get(0).getRegel());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVraagOpKandidaatVaderBsnIsNull() throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie {
        // fake dat de moeder is gevonden
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwIdentificatienummersRecord(20110101, null, 20110101)
            .burgerservicenummer(1234)
            .eindeRecord()
            .nieuwGeslachtsaanduidingRecord(20110101, null, 20110101)
            .geslachtsaanduiding(Geslachtsaanduiding.MAN)
            .eindeRecord()
            .build();
        ReflectionTestUtils.setField(moeder, "iD", 1234);

        Mockito.when(blobifierService
            .leesBlob(Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(moeder);

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        final boolean stapResultaat =
            bijhoudingBevragingUitvoerStap.voerStapUit(maakVraagKandidaatVaderBericht(null),
                null,
                resultaat);
        Assert.assertEquals(false, stapResultaat);
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVraagOpKandidaatVaderMoederKanNietGevondenWorden()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        // fake dat de moeder is gevonden
        final PersoonHisVolledigImpl moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwIdentificatienummersRecord(20110101, null, 20110101)
            .burgerservicenummer(1234)
            .eindeRecord()
            .nieuwGeslachtsaanduidingRecord(20110101, null, 20110101)
            .geslachtsaanduiding(Geslachtsaanduiding.MAN)
            .eindeRecord()
            .build();
        ReflectionTestUtils.setField(moeder, "iD", 1234);

        Mockito.when(blobifierService.leesBlob(
            Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(null);

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        final boolean stapResultaat =
            bijhoudingBevragingUitvoerStap.voerStapUit(maakVraagKandidaatVaderBericht("5678"),
                null,
                resultaat);
        Assert.assertEquals(false, stapResultaat);
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVraagPersonenOpAdresViaBsnZonderAdres() throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie {
        final PersoonHisVolledigImpl testPersoon = TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon();
        ReflectionTestUtils.setField(testPersoon, "adressen", new HashSet<PersoonAdresHisVolledigImpl>());
        Mockito.when(blobifierService.leesBlob(
            Matchers.any(BurgerservicenummerAttribuut.class))).thenReturn(testPersoon);

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        bijhoudingBevragingUitvoerStap.voerStapUit(maakVraagPersoonOpAdresBericht(), null, resultaat);

        // Geen adres, dus geen personen gevonden.
        Assert.assertEquals(1, resultaat.getMeldingen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
            .haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                Matchers.any(IdentificatiecodeNummeraanduidingAttribuut.class));
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            Matchers.any(NaamOpenbareRuimteAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class), Matchers.any(NaamEnumeratiewaardeAttribuut.class),
            Matchers.any(Gemeente.class));
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
            Matchers.any(PostcodeAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class));
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVraagPersonenOpAdresViaBsnZonderHoofdpersoon()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        Mockito.when(blobifierService.leesBlob(
            Matchers.any(BurgerservicenummerAttribuut.class))).thenReturn(null);

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        bijhoudingBevragingUitvoerStap.voerStapUit(maakVraagPersoonOpAdresBericht(), null, resultaat);

        // Geen adres, dus geen personen gevonden.
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public void testVraagPersonenOpAdresViaBsnIdMetIdCodeAanduidingStap1() {
        // Geef bewust de partner ook mee als resultaat, zodat die op hetzelfde adres ook meekomt als gevonden persoon.
        Mockito.when(persoonRepository.haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
            new IdentificatiecodeNummeraanduidingAttribuut("abcd"))).thenReturn(
            Arrays.asList(TestDataOpvragenPersoonBerichtUitvoerStap.ID_PERSOON_PERSOON, TestDataOpvragenPersoonBerichtUitvoerStap.ID_PERSOON_PARTNER));

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        bijhoudingBevragingUitvoerStap.voerStapUit(maakVraagPersoonOpAdresBericht(), context,
            resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(2, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            Matchers.any(NaamOpenbareRuimteAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class), Matchers.any(NaamEnumeratiewaardeAttribuut.class),
            Matchers.any(Gemeente.class));
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
            Matchers.any(PostcodeAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class));

        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(2, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is aanwezig en IdentificatiecodeNummeraanduiding levert geen extra resultaten op, er wordt
     * gezocht met volledige adres.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetIdCodeAanduidingStap2()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        final Gemeente gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde();
        final NaamEnumeratiewaardeAttribuut plaats = StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM;

        final PersoonHisVolledigImpl persoon = TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon();
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "naamOpenbareRuimte", new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
            .getActueleRecord(), "woonplaatsnaam", plaats);
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
            .getActueleRecord(), "gemeente", new GemeenteAttribuut(gemeente));

        Mockito.when(
            blobifierService.leesBlob(
                Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(persoon);

        Mockito.when(
            persoonRepository
                .haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                    new IdentificatiecodeNummeraanduidingAttribuut(
                        "abcd"))).thenReturn(Arrays.asList(persoon.getID()));

        Mockito.when(
            persoonRepository.haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
                new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"),
                new HuisnummerAttribuut(12),
                new HuisletterAttribuut("A"),
                null,
                plaats,
                gemeente))
            .thenReturn(
                Arrays.asList(1, 2));

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        bijhoudingBevragingUitvoerStap.voerStapUit(maakVraagPersoonOpAdresBericht(), context,
            resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"), new HuisnummerAttribuut(12),
            new HuisletterAttribuut("A"), null, plaats,
            gemeente);
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
            Matchers.any(PostcodeAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class));

        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is aanwezig en IdentificatiecodeNummeraanduiding levert geen extra resultaten op, er wordt
     * gezocht met volledige adres en volledige adres levert ook geen resultaten op. Er wordt verder gezocht met postcode en huisnummer.
     *
     * @throws PersoonNietAanwezigExceptie de persoon niet aanwezig exceptie
     * @throws NietUniekeBsnExceptie       de niet unieke bsn exceptie
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetIdCodeAanduidingStap2b()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        final Gemeente gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde();
        final NaamEnumeratiewaardeAttribuut plaats = StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM;

        final PersoonHisVolledigImpl persoon = TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon();
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "naamOpenbareRuimte", new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(), "woonplaatsnaam", plaats);
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(), "gemeente",
            new GemeenteAttribuut(gemeente));

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
            blobifierService.leesBlob(
                Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(persoon);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummeraanduiding
        Mockito.when(
            persoonRepository
                .haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                    new IdentificatiecodeNummeraanduidingAttribuut(
                        "abcd"))).thenReturn(Arrays.asList(persoon.getID()));

        Mockito.when(
            persoonRepository.haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
                new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"),
                new HuisnummerAttribuut(12),
                new HuisletterAttribuut("A"),
                null,
                plaats,
                gemeente))
            .thenReturn(
                Arrays.asList(persoon.getID()));

        Mockito.when(
            persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(PostcodeAttribuut.class),
                Matchers.any(HuisnummerAttribuut.class),
                Matchers.any(HuisletterAttribuut.class),
                Matchers.any(HuisnummertoevoegingAttribuut.class)))
            .thenReturn(
                Arrays.asList(1, 2));

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("123455"));

        bijhoudingBevragingUitvoerStap.voerStapUit(bericht, context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"), new HuisnummerAttribuut(12),
            new HuisletterAttribuut("A"), null, plaats,
            gemeente);

        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is aanwezig en IdentificatiecodeNummeraanduiding levert geen extra resultaten op, volledige
     * adres is niet aanwezig, er wordt gezocht met postcode huisnummmer.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetIdCodeAanduidingStap3()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
            blobifierService.leesBlob(
                Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon());

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummeraanduiding
        Mockito.when(
            persoonRepository
                .haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                    new IdentificatiecodeNummeraanduidingAttribuut(
                        "abcd"))).thenReturn(Arrays.asList(1));

        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.when(
            persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(PostcodeAttribuut.class),
                Matchers.any(HuisnummerAttribuut.class),
                Matchers.any(HuisletterAttribuut.class),
                Matchers.any(HuisnummertoevoegingAttribuut.class)))
            .thenReturn(
                Arrays.asList(1, 2));

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("123455"));

        bijhoudingBevragingUitvoerStap.voerStapUit(bericht, context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            Matchers.any(NaamOpenbareRuimteAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class),
            Matchers.any(NaamEnumeratiewaardeAttribuut.class),
            Matchers.any(Gemeente.class));

        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is aanwezig en IdentificatiecodeNummeraanduiding levert geen extra resultaten op, er wordt
     * gezocht met volledige adres en volledige adres levert ook geen resultaten op. Postcode is niet aanwezig. Flow eindigt zonder personen.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetIdCodeAanduidingStap3bGeenResultaat()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        final Gemeente gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde();

        final PersoonHisVolledigImpl persoon = TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon();
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "naamOpenbareRuimte", new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "woonplaatsnaam",
            StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "postcode", null);
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "gemeente",
            new GemeenteAttribuut(gemeente));

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
            blobifierService.leesBlob(
                Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(persoon);

        // Vervolgens wordt met de persoon adres van de persoon gezocht op identificatiecodeNummeraanduiding
        Mockito.when(
            persoonRepository
                .haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                    new IdentificatiecodeNummeraanduidingAttribuut(
                        "abcd"))).thenReturn(Arrays.asList(1));

        Mockito.when(
            persoonRepository.haalPersoonIdsMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimteAttribuut(
                    "naamOpenbareRuimte"), new HuisnummerAttribuut(12), new HuisletterAttribuut("A"), null,
                StatischeObjecttypeBuilder
                    .WOONPLAATS_AMSTERDAM,
                gemeente))
            .thenReturn(Arrays.asList(1));

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("123455"));

        bijhoudingBevragingUitvoerStap.voerStapUit(bericht, context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            Matchers.any(NaamOpenbareRuimteAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class), Matchers.any(NaamEnumeratiewaardeAttribuut.class),
            Matchers.any(Gemeente.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
            Matchers.any(PostcodeAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class));

        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is niet aanwezig. Er wordt gezocht met Volledige adres.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetVolledigeAdresStap1()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        final Gemeente gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde();

        final PersoonHisVolledigImpl persoon = TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon();
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "naamOpenbareRuimte", new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "woonplaatsnaam", StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "gemeente", new GemeenteAttribuut(gemeente));
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "postcode", null);
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
            blobifierService.leesBlob(
                Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(persoon);

        Mockito.when(
            persoonRepository.haalPersoonIdsMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimteAttribuut(
                    "naamOpenbareRuimte"), new HuisnummerAttribuut(12), new HuisletterAttribuut("A"), null,
                StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM,
                gemeente))
            .thenReturn(
                Arrays.asList(1, 2));

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("123455"));

        bijhoudingBevragingUitvoerStap.voerStapUit(bericht, context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
            .haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                Matchers.any(IdentificatiecodeNummeraanduidingAttribuut.class));
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            Matchers.any(NaamOpenbareRuimteAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class), Matchers.any(NaamEnumeratiewaardeAttribuut.class),
            Matchers.any(Gemeente.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
            Matchers.any(PostcodeAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class));

        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is niet aanwezig. Er wordt gezocht met Volledige adres. Volledige adres levert geen
     * resultaten op. Er wordt gezocht met postcode huisnummer.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetVolledigeAdresStap2()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        final Gemeente gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde();

        final PersoonHisVolledigImpl persoon = TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon();
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "naamOpenbareRuimte", new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "woonplaatsnaam", StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "gemeente", new GemeenteAttribuut(gemeente));
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord(),
            "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
            blobifierService.leesBlob(
                Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(persoon);

        Mockito.when(
            persoonRepository.haalPersoonIdsMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimteAttribuut(
                    "naamOpenbareRuimte"), new HuisnummerAttribuut(12), new HuisletterAttribuut("A"), null,
                StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM,
                gemeente))
            .thenReturn(
                Arrays.asList(1));

        Mockito.when(
            persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(Matchers.any(PostcodeAttribuut.class),
                Matchers.any(HuisnummerAttribuut.class),
                Matchers.any(HuisletterAttribuut.class),
                Matchers.any(HuisnummertoevoegingAttribuut.class)))
            .thenReturn(
                Arrays.asList(1, 2));

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("123455"));

        bijhoudingBevragingUitvoerStap.voerStapUit(bericht, context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
            .haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                Matchers.any(IdentificatiecodeNummeraanduidingAttribuut.class));
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            Matchers.any(NaamOpenbareRuimteAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class), Matchers.any(NaamEnumeratiewaardeAttribuut.class),
            Matchers.any(Gemeente.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
            Matchers.any(PostcodeAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class));

        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    /**
     * Test zoeken met BSN en IdentificatiecodeNummeraanduiding is niet aanwezig. Er wordt gezocht met Volledige adres. Volledige adres levert geen
     * resultaten op. Postcode huisnummer niet aanwezig, flow eindigd zonder personen.
     */
    @Test
    public void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetVolledigeAdresStap2b()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        final Gemeente gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde();

        final PersoonHisVolledigImpl persoon = TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon();
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
                .getActueleRecord(),
            "naamOpenbareRuimte", new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
                .getActueleRecord(),
            "woonplaatsnaam", StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
            .getActueleRecord(), "gemeente", new GemeenteAttribuut(gemeente));
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
            .getActueleRecord(), "postcode", null);
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
            .getActueleRecord(), "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
            blobifierService.leesBlob(
                Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(persoon);

        Mockito.when(
            persoonRepository.haalPersoonIdsMetWoonAdresOpViaVolledigAdres(new NaamOpenbareRuimteAttribuut(
                    "naamOpenbareRuimte"), new HuisnummerAttribuut(12), new HuisletterAttribuut("A"), null,
                StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM,
                gemeente))
            .thenReturn(
                Arrays.asList(1));

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("123455"));

        bijhoudingBevragingUitvoerStap.voerStapUit(bericht, context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
            .haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                Matchers.any(IdentificatiecodeNummeraanduidingAttribuut.class));
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            Matchers.any(NaamOpenbareRuimteAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class), Matchers.any(NaamEnumeratiewaardeAttribuut.class),
            Matchers.any(Gemeente.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
            Matchers.any(PostcodeAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class));

        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    /**
     * Test zoeken met BSN, IdentificatiecodeNummeraanduiding en volledige adres niet aanwezig. Er wordt gezocht met postcode.
     */
    @Test
    public final void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetPostcodehuisnummer()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        final PersoonHisVolledigImpl persoon = TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon();
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
            .getActueleRecord(), "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
            blobifierService.leesBlob(
                Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(persoon);

        Mockito.when(
            persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
                Matchers.any(PostcodeAttribuut.class),
                Matchers.any(HuisnummerAttribuut.class),
                Matchers.any(HuisletterAttribuut.class),
                Matchers.any(HuisnummertoevoegingAttribuut.class)))
            .thenReturn(
                Arrays.asList(1, 2));

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("123455"));

        bijhoudingBevragingUitvoerStap.voerStapUit(bericht, context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
            .haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                Matchers.any(IdentificatiecodeNummeraanduidingAttribuut.class));
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            Matchers.any(NaamOpenbareRuimteAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class), Matchers.any(NaamEnumeratiewaardeAttribuut.class),
            Matchers.any(Gemeente.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(1)).haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
            Matchers.any(PostcodeAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class));

        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    /**
     * Test zoeken met BSN, IdentificatiecodeNummeraanduiding en volledige adres niet aanwezig. Er postcode niet aanwezig. Flow eindigd.
     */
    @Test
    public final void testHaalPersonenMetAdresOpViaBurgerservicenummerIdMetPostcodehuisnummerNietAanwezig()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        final PersoonHisVolledigImpl persoon = TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon();
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
            .getActueleRecord(), "postcode", null);
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
            .getActueleRecord(), "identificatiecodeNummeraanduiding", null);

        // Via bsn zoek methode wordt een persoon teruggeven
        Mockito.when(
            blobifierService.leesBlob(
                Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(persoon);

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("123455"));

        bijhoudingBevragingUitvoerStap.voerStapUit(bericht, context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        Mockito.verify(persoonRepository, Mockito.times(0))
            .haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                Matchers.any(IdentificatiecodeNummeraanduidingAttribuut.class));
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
            Matchers.any(NaamOpenbareRuimteAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class), Matchers.any(NaamEnumeratiewaardeAttribuut.class),
            Matchers.any(Gemeente.class));
        // Als er niet meer personen dan hemzelf wordt gevonden, dan wordt er gezocht op een andere manier
        Mockito.verify(persoonRepository, Mockito.times(0)).haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(
            Matchers.any(PostcodeAttribuut.class), Matchers.any(HuisnummerAttribuut.class),
            Matchers.any(HuisletterAttribuut.class),
            Matchers.any(HuisnummertoevoegingAttribuut.class));

        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVraagOpPersonenOpAdresInclusiefBetrokkenhedenMetPostcode() {
        Mockito.when(
            persoonRepository.haalPersoonIdsOpMetAdresViaPostcodeHuisnummer(Matchers.any(PostcodeAttribuut.class),
                Matchers.any(HuisnummerAttribuut.class),
                Matchers.any(HuisletterAttribuut.class),
                Matchers.any(HuisnummertoevoegingAttribuut.class)))
            .thenReturn(Arrays.asList(1));

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setPostcode(new PostcodeAttribuut("1000AB"));
        bericht.getZoekcriteriaPersoon().setHuisnummer(new HuisnummerAttribuut(10));

        bijhoudingBevragingUitvoerStap.voerStapUit(bericht, context, resultaat);

        Assert.assertEquals(0, resultaat.getMeldingen().size());
        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());
        Assert.assertFalse(resultaat.getGevondenPersonen().iterator().next().getObjectSleutel().isEmpty());

        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(1, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVraagOpPersonenOpAdresInclusiefBetrokkenhedenMetGemeentecode() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setGemeenteCode(new GemeenteCodeAttribuut((short) 12));
        bericht.getZoekcriteriaPersoon().setHuisnummer(new HuisnummerAttribuut(10));
        bericht.getZoekcriteriaPersoon().setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("straat"));

        bijhoudingBevragingUitvoerStap.voerStapUit(bericht, context, resultaat);

        // TODO Hosing: repository is nog niet geimplementeerd,
        // tijdelijk geeft deze zoek criteria nog geen resultaat terug.
        assertNietsGevondenMelding(resultaat);

        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVraagOpPersonenOpAdresInclusiefBetrokkenhedenMetOngeldigeZoekCriteria() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();

        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        final BerichtZoekcriteriaPersoonGroepBericht criteria = bericht.getZoekcriteriaPersoon();
        criteria.setGemeenteCode(new GemeenteCodeAttribuut((short) 23));
        criteria.setHuisnummer(new HuisnummerAttribuut(10));
        criteria.setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("straat"));
        criteria.setBurgerservicenummer(new BurgerservicenummerAttribuut("12323"));

        bijhoudingBevragingUitvoerStap.voerStapUit(bericht, context, resultaat);

        // TODO Hosing: repository is nog niet geimplementeerd, tijdelijk geeft deze zoek criteria nog geen resultaat
        // terug.
        assertNietsGevondenMelding(resultaat);

        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenIngaandBericht().size());
        Assert.assertEquals(0, resultaat.getTeArchiverenPersonenUitgaandBericht().size());
    }

    @Test
    public final void testVoerStapUitBerichtIsGeenBevragingsBericht() {
        final boolean stapResultaat = bijhoudingBevragingUitvoerStap.voerStapUit(new BevragingsBericht(
            SoortBericht.DUMMY)
        {

        }, null, new BevragingResultaat(new ArrayList<Melding>()));

        Assert.assertFalse(stapResultaat);
    }

    private void assertGeenPersoonGevondenMelding(final BevragingResultaat resultaat) {
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(Regel.BRBV0006, resultaat.getMeldingen().get(0).getRegel());
        Assert.assertEquals(0, resultaat.getGevondenPersonen().size());
    }

    private void assertNietsGevondenMelding(final BevragingResultaat resultaat) {
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.INFORMATIE, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(Regel.ALG0001, resultaat.getMeldingen().get(0).getRegel());
        Assert.assertEquals(0, resultaat.getGevondenPersonen().size());
    }

    private BepaalKandidaatVaderBericht maakVraagKandidaatVaderBericht(final String bsn) {
        final BepaalKandidaatVaderBericht bericht = new BepaalKandidaatVaderBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        if (bsn != null) {
            bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("1234"));
        }
        bericht.getZoekcriteriaPersoon().setGeboortedatumKind(new DatumEvtDeelsOnbekendAttribuut(20120101));
        return bericht;
    }

    private GeefPersonenOpAdresMetBetrokkenhedenBericht maakVraagPersoonOpAdresBericht() {
        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        final BerichtZoekcriteriaPersoonGroepBericht criteria = bericht.getZoekcriteriaPersoon();
        criteria.setBurgerservicenummer(new BurgerservicenummerAttribuut("1234"));
        return bericht;
    }

    /*
     * Extra tests op protected check methodes, voor coverage verhoging.
     * Test dus niet alle mogelijkheden, maar alleen wat niet afgedekt wordt door de testen hierboven.
     */
    @Test
    public final void testIsBsnCriteria() {
        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht =
            new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isBsnCriteria(bericht));

        final BerichtZoekcriteriaPersoonGroepBericht zoekcriteria = new BerichtZoekcriteriaPersoonGroepBericht();
        bericht.setZoekcriteriaPersoon(zoekcriteria);
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isBsnCriteria(bericht));

        zoekcriteria.setBurgerservicenummer(new BurgerservicenummerAttribuut(1234));
        zoekcriteria.setHuisnummer(null);
        Assert.assertTrue(this.bijhoudingBevragingUitvoerStap.isBsnCriteria(bericht));

        final ZoekPersoonBericht zoekBericht = new ZoekPersoonBericht();
        zoekBericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isBsnCriteria(zoekBericht));

        zoekBericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isBsnCriteria(zoekBericht));

        zoekBericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut(1234));
        zoekBericht.getZoekcriteriaPersoon().setHuisnummer(null);
        Assert.assertTrue(this.bijhoudingBevragingUitvoerStap.isBsnCriteria(zoekBericht));

        final BevragingsBericht anderBericht = Mockito.mock(BevragingsBericht.class);
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isBsnCriteria(anderBericht));
    }

    @Test
    public final void testIsPostcodeCriteria() {
        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht = new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        final BerichtZoekcriteriaPersoonGroepBericht zoekcriteria = new BerichtZoekcriteriaPersoonGroepBericht();
        bericht.setZoekcriteriaPersoon(zoekcriteria);
        zoekcriteria.setPostcode(new PostcodeAttribuut("1234AB"));
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isPostcodeCriteria(bericht));

        zoekcriteria.setHuisnummer(new HuisnummerAttribuut(1));
        zoekcriteria.setBurgerservicenummer(new BurgerservicenummerAttribuut(1234));
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isPostcodeCriteria(bericht));
    }

    @Test
    public final void testIsGemCodeCriteria() {
        final GeefPersonenOpAdresMetBetrokkenhedenBericht bericht = new GeefPersonenOpAdresMetBetrokkenhedenBericht();
        final BerichtZoekcriteriaPersoonGroepBericht zoekcriteria = new BerichtZoekcriteriaPersoonGroepBericht();
        bericht.setZoekcriteriaPersoon(zoekcriteria);
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isGemCodeCriteria(bericht));

        zoekcriteria.setHuisnummer(new HuisnummerAttribuut(1));
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isGemCodeCriteria(bericht));

        zoekcriteria.setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("nob"));
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isGemCodeCriteria(bericht));
    }

    @Test
    public final void testIsZoekbaarMetVolledigAdres() {
        final PersoonAdresStandaardGroepBericht groep = new PersoonAdresStandaardGroepBericht();
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isZoekbaarMetVolledigAdres(groep));

        groep.setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("nob"));
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isZoekbaarMetVolledigAdres(groep));

        groep.setHuisnummer(new HuisnummerAttribuut(null));
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isZoekbaarMetVolledigAdres(groep));

        groep.setHuisnummer(new HuisnummerAttribuut(1));
        Assert.assertFalse(this.bijhoudingBevragingUitvoerStap.isZoekbaarMetVolledigAdres(groep));

        groep.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut("wplnaam"));
        Assert.assertTrue(this.bijhoudingBevragingUitvoerStap.isZoekbaarMetVolledigAdres(groep));
    }

    @Test
    public final void testObjectSleutelVerificatieMetGeefDetailsPersoon() {
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());

        Mockito.when(context.getLeveringinformatie()).thenReturn(new Leveringinformatie(null, null));
        bijhoudingBevragingUitvoerStap.voerStapUit(
            TestDataOpvragenPersoonBerichtUitvoerStap
                .maakGeefDetailsPersoonBerichtMetBsn(), context, resultaat);

        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        //Verifieer aanroep objectSleutel service.
        Mockito.verify(objectSleutelService, Mockito.atLeast(1)).genereerObjectSleutelString(Mockito.anyInt(),
            Mockito.anyInt());

        final PersoonHisVolledigView gevondenPersoon = resultaat.getGevondenPersonen().iterator().next();
        Assert.assertEquals("foobar", gevondenPersoon.getObjectSleutel());

        //Verifieer objectsleutels op de betrokken personen
        int i = 0;
        for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : gevondenPersoon.getBetrokkenheden()) {
            final RelatieHisVolledigView relatie = (RelatieHisVolledigView) betrokkenheidHisVolledigView.getRelatie();
            for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
                Assert.assertEquals("foobar", ((PersoonHisVolledigView) (betrokkenheidHisVolledig.getPersoon()))
                    .getObjectSleutel());
                i++;
            }
        }
        Assert.assertEquals("3 betrokkenheden.", 3, i);
    }

    @Test
    public final void testObjectSleutelsZoekPersoonOpAdresMetBetrokkenheden()
        throws PersoonNietAanwezigExceptie, NietUniekeBsnExceptie
    {
        final Gemeente gemeente = StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde();
        final NaamEnumeratiewaardeAttribuut plaats = StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM;

        final PersoonHisVolledigImpl persoon = TestDataOpvragenPersoonBerichtUitvoerStap.maakTestPersoon();
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
                .getActueleRecord(),
            "naamOpenbareRuimte", new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"));
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
            .getActueleRecord(), "woonplaatsnaam", plaats);
        ReflectionTestUtils.setField(persoon.getAdressen().iterator().next().getPersoonAdresHistorie()
                .getActueleRecord(), "gemeente",
            new GemeenteAttribuut(gemeente));

        Mockito.when(
            blobifierService.leesBlob(
                Matchers.any(BurgerservicenummerAttribuut.class)))
            .thenReturn(persoon);

        Mockito.when(
            persoonRepository
                .haalPersoonIdsMetWoonAdresOpViaIdentificatiecodeNummeraanduiding(
                    new IdentificatiecodeNummeraanduidingAttribuut(
                        "abcd"))).thenReturn(Arrays.asList(persoon.getID()));

        Mockito.when(
            persoonRepository.haalPersoonIdsMetWoonAdresOpViaVolledigAdres(
                new NaamOpenbareRuimteAttribuut("naamOpenbareRuimte"),
                new HuisnummerAttribuut(12),
                new HuisletterAttribuut("A"),
                null,
                plaats,
                gemeente))
            .thenReturn(
                Arrays.asList(1, 2));

        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        bijhoudingBevragingUitvoerStap.voerStapUit(maakVraagPersoonOpAdresBericht(), context,
            resultaat);

        Assert.assertEquals(1, resultaat.getGevondenPersonen().size());

        //Verifieer aanroep objectSleutel service.
        Mockito.verify(objectSleutelService, Mockito.atLeast(1)).genereerObjectSleutelString(Mockito.anyInt(), Mockito.anyInt());

        Assert.assertEquals("foobar", resultaat.getGevondenPersonen().iterator().next().getObjectSleutel());
    }

}
