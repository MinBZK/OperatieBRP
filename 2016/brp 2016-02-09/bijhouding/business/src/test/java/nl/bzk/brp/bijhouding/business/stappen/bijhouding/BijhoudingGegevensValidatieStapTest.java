/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.actie.BijhoudingRegelService;
import nl.bzk.brp.bijhouding.business.stappen.AbstractStapTest;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.SoortFout;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeAdresseerbaarObjectAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestLandGebiedBuilder;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNaamgebruikBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningNaGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.util.BerichtBuilder;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class BijhoudingGegevensValidatieStapTest extends AbstractStapTest {

    @InjectMocks
    private BijhoudingGegevensValidatieStap bijhoudingGegevensValidatieStap;

    @Mock
    private BijhoudingRegelService bijhoudingRegelService;

    @Mock
    private MeldingFactory meldingFactory;

    @Before
    public void init() {
        when(bijhoudingRegelService.getRegelParametersVoorRegel(any(Regel.class))).thenAnswer(
            new Answer<RegelParameters>() {
                @Override
                public RegelParameters answer(final InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    Regel regel = (Regel) args[0];
                    return new RegelParameters(new MeldingtekstAttribuut(regel.getOmschrijving()),
                        SoortMelding.FOUT, regel, null, SoortFout.VERWERKING_KAN_DOORGAAN);
                }
            });
        when(meldingFactory.maakResultaatMelding(any(Regel.class), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class), any(String.class)))
            .thenReturn(ResultaatMelding.builder().withSoort(SoortMelding.FOUT).withRegel(Regel.ALG0001).build());
    }

    /**
     * Tot nu toe gevalideerde velden: BSN, SoortAdres, Datum
     */
    @Test
    public void testBerichtMetOngeldigeGegevens() {
        BijhoudingsBericht bericht = maakNieuwOngeldigBericht();
        bijhoudingGegevensValidatieStap.voerStapUit(bericht);


        verifieerWillekeurigMeldingToegevoegd(8);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL0012);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL2032);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL2033);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL0102);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL1118);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL2027);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRBY0172);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL0501);
    }

    @Test
    public void testBerichtMetGeldigeGegevens() {
        BijhoudingsBericht bericht = maakNieuwGeldigBericht();

        final Resultaat resultaat = bijhoudingGegevensValidatieStap.voerStapUit(bericht);

        assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetActieZonderRootObject() {
        BijhoudingsBericht bericht = new BijhoudingsBericht(null) {
        };

        ActieBericht actie = new ActieRegistratieNaamgebruikBericht();
        actie.setRootObject(null);
        bericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        bericht.getAdministratieveHandeling().setActies(Arrays.asList(actie));

        final Resultaat resultaat = bijhoudingGegevensValidatieStap.voerStapUit(bericht);

        assertFalse(resultaat.getMeldingen().isEmpty());
        assertEquals(Regel.ALG0002, resultaat.getMeldingen().iterator().next().getRegel());
        assertEquals("Verplicht element niet aanwezig: betrokkenen",
            resultaat.getMeldingen().iterator().next().getMeldingTekst());
    }

    @Test
    public void testVerhuisBerichtValidatieMetFout() {
        // test dat als de verhuisxxx = 'P' dat de aangever verplicht
        // De aangever adreshouding ontbreekt, deze is verplicht als de reden adreswijziging P (Aangifte door persoon)
        RegistreerVerhuizingBericht bericht = bouwRegistreerVerhuizingBericht();
        bijhoudingGegevensValidatieStap.voerStapUit(bericht);

        verifieerWillekeurigMeldingToegevoegd(4);
        // BSN is niet elfproef.
        // postcode is niet geldig
        // verplichte aangever als reden = P
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL0012);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL1118);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL2024);
    }

    @Test
    public void testFoutmeldingCorrectGesorteerdMetFormat() {
        PersoonBericht kind = PersoonBuilder.bouwRefererendPersoon(100012711);
        kind.setVoornamen(new ArrayList<PersoonVoornaamBericht>());
        kind.getVoornamen().add(PersoonBuilder.bouwPersoonVoornaam(1, "zoet gebak"));
        kind.getVoornamen().add(PersoonBuilder.bouwPersoonVoornaam(2, "met slagroom"));
        ActieBericht actieBericht = BerichtBuilder.bouwActieRegistratieGeboorte(20121212, null, null,
            maaktFamilieBericht(kind, PersoonBuilder.bouwRefererendPersoon(100010258)));

        RegistreerGeboorteBericht bericht = new RegistreerGeboorteBericht();
        bericht.getStandaard().setAdministratieveHandeling(BerichtBuilder.bouwHandelingGeboorte(null, null, actieBericht));

        bijhoudingGegevensValidatieStap.voerStapUit(bericht);

        // twee BRAL0501 meldingen
        verifieerWillekeurigMeldingToegevoegd(2);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL0501, 2);
    }


    private RegistreerVerhuizingBericht bouwRegistreerVerhuizingBericht() {
        RegistreerVerhuizingBericht bericht = new RegistreerVerhuizingBericht();
        PersoonAdresBericht pa = PersoonAdresBuilder.bouwWoonadres("Straaat", 123, "a",
            StatischeObjecttypeBuilder.WOONPLAATS_BREDA.getWaarde(),
            StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(), 20100803);
//        pa.getStandaard().setAangeverAdreshoudingCode("P");
//        pa.getStandaard().setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_ADRESHOUDING_PARTNER);
        pa.getStandaard().setRedenWijzigingCode("P");
        pa.getStandaard().setRedenWijziging(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_PERSOON);
        pa.getStandaard().setIdentificatiecodeAdresseerbaarObject(
            new IdentificatiecodeAdresseerbaarObjectAttribuut("test"));
        PersoonBericht teVerhuizenPersoon = PersoonBuilder.bouwRefererendPersoon(234657234);
        teVerhuizenPersoon.setAdressen(Arrays.asList(pa));

        bericht.getStandaard().setAdministratieveHandeling(
            BerichtBuilder.bouwHandelingVerhuizing(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                new Date(),
                BerichtBuilder.bouwActieRegistratieAdres(
                    20100809, null,
                    StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                    teVerhuizenPersoon))
        );
        return bericht;
    }

    @Test
    public void testBerichtFamrechtBetrekking() {
        // test de validatie dat een FamRecht.Betr geen aanvangsdatum mag hebben.
        BijhoudingsBericht bericht = new BijhoudingsBericht(null) {
        };
        RelatieBericht relatie = maaktFamilieBericht(
            bouwPersoon("kind"),
            bouwPersoon("ouder"));

        ActieBericht actie = new ActieRegistratieNaamgebruikBericht();
        actie.setRootObject(relatie);
        bericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        bericht.getAdministratieveHandeling().setActies(Arrays.asList(actie));

        final Resultaat resultaat = bijhoudingGegevensValidatieStap.voerStapUit(bericht);

        assertTrue(resultaat.getMeldingen().isEmpty());
    }

    /**
     * Instantieert een bericht met ongeldige gegevens.
     *
     * @return een nieuw bericht
     */
    private BijhoudingsBericht maakNieuwOngeldigBericht() {
        BijhoudingsBericht bericht = new BijhoudingsBericht(null) {
        };

        LandGebied land = TestLandGebiedBuilder.maker()
            .metCode(6030)
            .metNaam("Nederland")
            .metAlpha2Naam("Nederland")
            .metAanvangGeldigheid(20130101)
            .metEindeGeldigheid(20130201).maak();
        RedenWijzigingVerblijf redenwijziging = new RedenWijzigingVerblijf(
            RedenWijzigingVerblijfCodeAttribuut.PERSOON_REDEN_WIJZIGING_ADRES_CODE, null);

        PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setCommunicatieID("" + (new Date().getTime()));
        pin.setBurgerservicenummer(new BurgerservicenummerAttribuut("123456789"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(10));
        gegevens.setLandGebied(new LandGebiedAttribuut(land));
        gegevens.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(redenwijziging));
        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setStandaard(gegevens);

        PersoonVoornaamStandaardGroepBericht voornaamGegevens = new PersoonVoornaamStandaardGroepBericht();
        voornaamGegevens.setNaam(new VoornaamAttribuut("abc def"));
        PersoonVoornaamBericht persoonVoornaam = new PersoonVoornaamBericht();
        persoonVoornaam.setVolgnummer(new VolgnummerAttribuut(1));
        persoonVoornaam.setStandaard(voornaamGegevens);
        List<PersoonVoornaamBericht> voornamen = new ArrayList<>();
        voornamen.add(persoonVoornaam);

        persoonAdres.setCommunicatieID("" + (new Date().getTime()));
        List<PersoonAdresBericht> adressen = new ArrayList<>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(pin);
        persoon.setAdressen(adressen);
        persoon.setVoornamen(voornamen);

        ActieBericht actie = new ActieRegistratieNaamgebruikBericht();
        actie.setRootObject(persoon);

        bericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        bericht.getAdministratieveHandeling().setActies(Arrays.asList(actie));

        return bericht;
    }

    /**
     * Instantieert een bericht met geldige gegevens.
     *
     * @return een nieuw bericht
     */
    private BijhoudingsBericht maakNieuwGeldigBericht() {
        BijhoudingsBericht bericht = new BijhoudingsBericht(null) {
        };

        RedenWijzigingVerblijfAttribuut redenWijzigingverblijf = StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_PERSOON;
        LandGebiedAttribuut land = StatischeObjecttypeBuilder.LAND_NEDERLAND;

        PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setBurgerservicenummer(new BurgerservicenummerAttribuut("123456782"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(20121231));
        gegevens.setLandGebied(land);
        gegevens.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        gegevens.setRedenWijziging(redenWijzigingverblijf);
        gegevens.setGemeente(StatischeObjecttypeBuilder.GEMEENTE_BREDA);
        gegevens.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_PARTNER);
        gegevens.setIdentificatiecodeAdresseerbaarObject(
            new IdentificatiecodeAdresseerbaarObjectAttribuut("test"));
        gegevens.setWoonplaatsnaam(StatischeObjecttypeBuilder.WOONPLAATS_BREDA);
        gegevens.setIdentificatiecodeNummeraanduiding(new IdentificatiecodeNummeraanduidingAttribuut("1"));
        gegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("een straat"));
        gegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimteAttribuut("een str."));
        gegevens.setHuisnummer(new HuisnummerAttribuut(1));
        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setStandaard(gegevens);

        List<PersoonAdresBericht> adressen = new ArrayList<>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(pin);
        persoon.setAdressen(adressen);

        ActieBericht actie = new ActieRegistratieNaamgebruikBericht();
        actie.setRootObject(persoon);

        bericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        bericht.getAdministratieveHandeling().setActies(Arrays.asList(actie));

        return bericht;
    }


    private FamilierechtelijkeBetrekkingBericht maaktFamilieBericht(final PersoonBericht kind,
        final PersoonBericht ouder)
    {
        return new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>()
            .bouwFamilieRechtelijkeBetrekkingRelatie()
            .voegOuderToe(ouder)
            .voegKindToe(kind)
            .getRelatie();
    }

    private PersoonBericht bouwPersoon(final String naam) {
        return PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456782, Geslachtsaanduiding.MAN,
            20121212, null, null, "vn", null, naam);
    }

    private void verifieerSpecifiekeMeldingToegevoegd(Regel regel) {
        verifieerSpecifiekeMeldingToegevoegd(regel, 1);
    }

    private void verifieerSpecifiekeMeldingToegevoegd(Regel regel, int aantalKeren) {
        verify(meldingFactory, times(aantalKeren))
            .maakResultaatMelding(eq(regel), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class), any(String.class));
    }

    private void verifieerWillekeurigMeldingToegevoegd(int aantalKeren) {
        verify(meldingFactory, times(aantalKeren))
            .maakResultaatMelding(any(Regel.class), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class), any(String.class));
    }


}
