/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */



package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;


public class BRBY0168Test {
    private static final Integer MOEDER_BSN = 111111111;

    private static final Integer VADER_BSN = 222222222;

    /**
     * BRBY0168: test dat exact 1 'Moeder' in de relatie staat.
     */
    private BRBY0168 brby0168;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        brby0168 = new BRBY0168();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0168, brby0168.getRegel());
    }

    @Test
    public void testPerfectRelatie() {
        // dit gaat goed, moeder (met indicatie Adresgevend), vader (M, zonder indicatie)
        final PersoonBericht berichtMoeder = maakMoeder();
        final PersoonBericht berichtVader = maakVader();
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht>
                relBuilder = new RelatieBuilder<>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = relBuilder
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(berichtMoeder).voegOuderToe(berichtVader).voegKindToe(maakKind())
                .getRelatie();
        zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie, berichtMoeder);
        final List<BerichtEntiteit> objecten = brby0168.voerRegelUit(null, nieuweSituatie, maakStandaardActie(), null);
        Assert.assertEquals(0, objecten.size());
    }

    @Test
    public void testRelatieMetVaderMoederZonderIndicatie() {
        // dit gaat fout, moeder (zonder indicatie ), vader (M, zonder indicatie) => mist 1 moeder
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht>
                relBuilder = new RelatieBuilder<>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = relBuilder
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(maakMoeder()).voegOuderToe(maakVader()).voegKindToe(maakKind())
                .getRelatie();
        nieuweSituatie.setCommunicatieID("id.relatie");
        final List<BerichtEntiteit> objecten = brby0168.voerRegelUit(null, nieuweSituatie, maakStandaardActie(), null);
        Assert.assertEquals(1, objecten.size());
        Assert.assertEquals(nieuweSituatie, objecten.get(0));
    }

    @Test
    public void testRelatieMetVaderMoederBeideIndicatie() {
        // dit gaat fout, moeder (met indicatie ), vader (M, met indicatie) => 2 'moeders'
        final PersoonBericht berichtMoeder = maakMoeder();
        final PersoonBericht berichtVader = maakVader();
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht>
                relBuilder = new RelatieBuilder<>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = relBuilder
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(berichtMoeder).voegOuderToe(berichtVader).voegKindToe(maakKind())
                .getRelatie();
        nieuweSituatie.setCommunicatieID("id.relatie");
        zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie, berichtMoeder);
        zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie, berichtVader);
        final List<BerichtEntiteit> objecten = brby0168.voerRegelUit(null, nieuweSituatie, maakStandaardActie(), null);
        Assert.assertEquals(1, objecten.size());
        Assert.assertEquals(nieuweSituatie, objecten.get(0));
    }

    @Test
    public void testRelatieMetAlleenVader() {
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht>
                relBuilder = new RelatieBuilder<>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = relBuilder
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(maakVader()).voegKindToe(maakKind())
                .getRelatie();
        nieuweSituatie.setCommunicatieID("id.relatie");
        final List<BerichtEntiteit> objecten = brby0168.voerRegelUit(null, nieuweSituatie, maakStandaardActie(), null);
        Assert.assertEquals(1, objecten.size());
        Assert.assertEquals(nieuweSituatie, objecten.get(0));
    }

    private PersoonBericht maakMoeder() {
        final PersoonBericht berichtMoeder = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, MOEDER_BSN,
                Geslachtsaanduiding.VROUW, 19360408,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                "moeder", "van", "Houten");
        berichtMoeder.setCommunicatieID("id.moeder.pers");
        berichtMoeder.getIdentificatienummers().setCommunicatieID("id.moeder.pers.ids");
        berichtMoeder.getGeboorte().setCommunicatieID("id.moeder.pers.geboorte");
        berichtMoeder.getGeslachtsaanduiding().setCommunicatieID("id.moeder.pers.geslacht");
        return berichtMoeder;
    }

    private PersoonBericht maakVader() {
        final PersoonBericht berichtVader = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, VADER_BSN,
                Geslachtsaanduiding.MAN, 19261225,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                "vader", "van", "Houten");
        berichtVader.setCommunicatieID("id.moeder.pers");
        berichtVader.getIdentificatienummers().setCommunicatieID("id.vader.pers.ids");
        berichtVader.getGeboorte().setCommunicatieID("id.vader.pers.geboorte");
        berichtVader.getGeslachtsaanduiding().setCommunicatieID("id.vader.pers.geslacht");
        return berichtVader;
    }

    private PersoonBericht maakKind() {
        final PersoonBericht k = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 666666666,
                Geslachtsaanduiding.MAN, 20120730,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                "kind", "van", "Houten");
        k.setCommunicatieID("id.moeder.pers");
        k.getIdentificatienummers().setCommunicatieID("id.kind.pers.ids");
        k.getGeboorte().setCommunicatieID("id.kind.pers.geboorte");
        k.getGeslachtsaanduiding().setCommunicatieID("id.kind.pers.geslacht");
        return k;
    }

    private void zetOuderMoederAlsIndicatieAdresHoudend(final FamilierechtelijkeBetrekkingBericht nieuweSituatie,
                                                        final PersoonBericht ouder)
    {
        // zoek binnen deze relatie, en zet de ouder met bsn
        for (final OuderBericht betr : nieuweSituatie.getOuderBetrokkenheden()) {
            if (betr.getPersoon().getIdentificatienummers().getBurgerservicenummer().getWaarde().equals(
                    ouder.getIdentificatienummers().getBurgerservicenummer().getWaarde()))
            {
                betr.setOuderschap(new OuderOuderschapGroepBericht());
                betr.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(JaNeeAttribuut.JA);
            }
        }

    }

    private ActieBericht maakStandaardActie() {
        final PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setBurgerservicenummer(new BurgerservicenummerAttribuut("123"));

        final PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        final PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setStandaard(gegevens);

        final List<PersoonAdresBericht> adressen = new ArrayList<>();
        adressen.add(persoonAdres);

        final PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(adressen);
        persoon.setIdentificatienummers(pin);

        final ActieBericht actie = new ActieRegistratieGeboorteBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(1));
        actie.setRootObject(persoon);

        return actie;
    }

}
