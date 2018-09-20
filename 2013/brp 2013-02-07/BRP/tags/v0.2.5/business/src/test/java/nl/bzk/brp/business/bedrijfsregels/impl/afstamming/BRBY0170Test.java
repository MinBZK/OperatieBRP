/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY0170Test {
    /**
     * BRBY0170: test dat de NietMoeder is van het mannelijk geslacht.
     */
    @Mock
    private PersoonRepository persoonRepository;

    private BRBY0170          brby0170;

    private static final String MOEDER_BSN = "111111111";
    private static final String VADER_BSN = "222222222";

    private PersoonModel moeder;
    private PersoonModel vader;

//    private PersoonBericht berichtMoeder;
//    private PersoonBericht berichtVader;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        brby0170 = new BRBY0170();
        ReflectionTestUtils.setField(brby0170, "persoonRepository", persoonRepository);

        moeder = new PersoonModel(maakMoeder());
        vader = new PersoonModel(maakVader());

        ReflectionTestUtils.setField(moeder, "id", new Integer(3001));
        ReflectionTestUtils.setField(vader,  "id", new Integer(4001));

        when(persoonRepository.findByBurgerservicenummer(
                Matchers.eq(new Burgerservicenummer(MOEDER_BSN)))).thenReturn(moeder);

        when(persoonRepository.haalPersoonSimpel(Matchers.eq(new Integer(3001)))).thenReturn(moeder);
        when(persoonRepository.haalPersoonSimpel(Matchers.eq(new Integer(4001)))).thenReturn(vader);

    }

    @Test
    public void testAlleNull() {
        List<Melding> meldingen = brby0170.executeer(null, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOudNieuwNull() {
        List<Melding> meldingen = brby0170.executeer(null, null, maakStandaardActie());
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testPerfectRelatie() {
        // dit gaat goed, moeder (met indicatie Adresgevend), vader (M, zonder indicatie)
        PersoonBericht berichtMoeder = maakMoeder();
        PersoonBericht berichtVader = maakVader();
        RelatieBuilder relBuilder = new RelatieBuilder();
        RelatieBericht nieuweSituatie = relBuilder
                    .bouwFamilieRechtelijkeBetrekkingRelatie()
                    .voegOuderToe(berichtMoeder).voegOuderToe(berichtVader).voegKindToe(maakKind())
                    .getRelatie();
        zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie, berichtMoeder);
        List<Melding> meldingen = brby0170.executeer(null, nieuweSituatie, maakStandaardActie());
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testRelatieMetVaderMoederZonderIndicatie() {
        // dit gaat fout, omdat we nu een ouder vindt van type 'V' en heeft niet de flag IndicatieAdresHoudernd.
        PersoonBericht berichtMoeder = maakMoeder();
        PersoonBericht berichtVader = maakVader();
        RelatieBuilder relBuilder = new RelatieBuilder();
        RelatieBericht nieuweSituatie = relBuilder
                    .bouwFamilieRechtelijkeBetrekkingRelatie()
                    .voegOuderToe(berichtMoeder).voegOuderToe(berichtVader).voegKindToe(maakKind())
                    .getRelatie();
        List<Melding> meldingen = brby0170.executeer(null, nieuweSituatie, maakStandaardActie());
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0170, meldingen.get(0).getCode());
        Assert.assertEquals(Soortmelding.FOUT, meldingen.get(0).getSoort());
        Assert.assertEquals("id.moeder.pers.geslacht", meldingen.get(0).getVerzendendId());
    }

    @Test
    public void testRelatieMetAlleenVader() {
        PersoonBericht berichtVader = maakVader();
        RelatieBuilder relBuilder = new RelatieBuilder();
        RelatieBericht nieuweSituatie = relBuilder
                    .bouwFamilieRechtelijkeBetrekkingRelatie()
                    .voegOuderToe(berichtVader).voegKindToe(maakKind())
                    .getRelatie();
        List<Melding> meldingen = brby0170.executeer(null, nieuweSituatie, maakStandaardActie());
        Assert.assertEquals(0, meldingen.size());
    }

    private PersoonBericht maakMoeder() {
        PersoonBericht berichtMoeder = PersoonBuilder.bouwPersoon(MOEDER_BSN,
                Geslachtsaanduiding.VROUW, 19360408,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM,
                "moeder", "van", "Houten");
        berichtMoeder.setVerzendendId("id.moeder.pers");
        berichtMoeder.getIdentificatienummers().setVerzendendId("id.moeder.pers.ids");
        berichtMoeder.getGeboorte().setVerzendendId("id.moeder.pers.geboorte");
        berichtMoeder.getGeslachtsaanduiding().setVerzendendId("id.moeder.pers.geslacht");
        return berichtMoeder;
    }

    private PersoonBericht maakVader() {
        PersoonBericht berichtVader = PersoonBuilder.bouwPersoon(VADER_BSN,
                Geslachtsaanduiding.MAN, 19261225,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM,
                "vader", "van", "Houten");
        berichtVader.setVerzendendId("id.moeder.pers");
        berichtVader.getIdentificatienummers().setVerzendendId("id.vader.pers.ids");
        berichtVader.getGeboorte().setVerzendendId("id.vader.pers.geboorte");
        berichtVader.getGeslachtsaanduiding().setVerzendendId("id.vader.pers.geslacht");
        return berichtVader;
    }

    private PersoonBericht maakKind() {
        PersoonBericht k = PersoonBuilder.bouwPersoon("666666666",
                Geslachtsaanduiding.MAN, 20120730,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM,
                "kind", "van", "Houten");
        k.setVerzendendId("id.moeder.pers");
        k.getIdentificatienummers().setVerzendendId("id.kind.pers.ids");
        k.getGeboorte().setVerzendendId("id.kind.pers.geboorte");
        k.getGeslachtsaanduiding().setVerzendendId("id.kind.pers.geslacht");
        return k;
    }

    private void zetOuderMoederAlsIndicatieAdresHoudend(final RelatieBericht nieuweSituatie, final PersoonBericht ouder) {
        // zoek binnen deze relatie, en zet de ouder met bsn
        for (BetrokkenheidBericht betr : nieuweSituatie.getOuderBetrokkenheden()) {
            if (betr.getBetrokkene().getIdentificatienummers().getBurgerservicenummer().getWaarde().equals(
                    ouder.getIdentificatienummers().getBurgerservicenummer().getWaarde()))
            {
                betr.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
                betr.getBetrokkenheidOuderschap().setIndAdresGevend(Ja.Ja);
            }
        }

    }

    private ActieBericht maakStandaardActie() {
        PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setBurgerservicenummer(new Burgerservicenummer("abc"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setGegevens(gegevens);

        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(adressen);
        persoon.setIdentificatienummers(pin);

        ActieBericht actie = new ActieBericht();
        Integer datumAanvangGeldigheid = new Integer(1);
        actie.setDatumAanvangGeldigheid(new Datum(datumAanvangGeldigheid));
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        return actie;
    }

}
