/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRPUC00112Test {
    private final BRPUC00112 brpuc00112 = new BRPUC00112();

    private static final Integer MOEDER_BSN = 111111111;
    private static final Integer VADER_BSN = 222222222;

    private Map<String, PersoonView> bestaandeBetrokkenen;

    @Before
    public void init() {
        final PersoonView moeder =
                new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwGeslachtsaanduidingRecord(19800101, null, 19800101)
                        .geslachtsaanduiding(Geslachtsaanduiding.VROUW)
                        .eindeRecord().build());
        final PersoonView vader =
                new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwGeslachtsaanduidingRecord(19800101, null, 19800101)
                        .geslachtsaanduiding(Geslachtsaanduiding.MAN)
                        .eindeRecord().build());

        bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("moeder.id.sleutel", moeder);
        bestaandeBetrokkenen.put("vader.id.sleutel", vader);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRPUC00112, brpuc00112.getRegel());
    }

    @Test
    public void testPerfecteRelatie() {
        // dit gaat goed, moeder (met indicatie indicatieOuderUitWieKindIsVoortgekomen), vader (M, zonder indicatie)
        final PersoonBericht berichtMoeder = maakMoeder();
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
                new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = relBuilder
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(berichtMoeder).voegOuderToe(maakVader()).voegKindToe(maakKind())
                .getRelatie();
        //Zet indicatie op Moeder
        zetOuderAlsIndicatieAdresHoudend(nieuweSituatie, berichtMoeder);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
                brpuc00112.voerRegelUit(null, nieuweSituatie, null, bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testRelatieMetVaderMoederZonderIndicatie() {
        // dit gaat goed, omdat geen van beide hebben een indicatie.
        final PersoonBericht berichtMoeder = maakMoeder();
        final PersoonBericht berichtVader = maakVader();
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
                new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = relBuilder
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(berichtMoeder).voegOuderToe(berichtVader).voegKindToe(maakKind())
                .getRelatie();

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
                brpuc00112.voerRegelUit(null, nieuweSituatie, null, bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testRelatieMetAlleenVader() {
        // gaat ook goed, omdat vader heeft geen indicatie
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
                new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = relBuilder
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(maakVader()).voegKindToe(maakKind())
                .getRelatie();

        bestaandeBetrokkenen.remove("moeder.id.sleutel");

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
                brpuc00112.voerRegelUit(null, nieuweSituatie, null,
                        bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testRelatieMetAlleenVaderMetIndicatie() {
        // gaat fout, vader heeft indicatie, type 'M'
        final PersoonBericht berichtVader = maakVader();
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
                new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = relBuilder
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(berichtVader).voegKindToe(maakKind())
                .getRelatie();
        //Zet indicatie op vader
        zetOuderAlsIndicatieAdresHoudend(nieuweSituatie, berichtVader);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
                brpuc00112.voerRegelUit(null, nieuweSituatie, null, bestaandeBetrokkenen);
        Assert.assertEquals(1, objectenDieDeRegelOvertreden.size());
        Assert.assertEquals(berichtVader, objectenDieDeRegelOvertreden.get(0));
    }

    @Test
    public void testRelatieMetBeideMetIndicatie() {
        // gaat fout, vader heeft indicatie, type 'M'
        final PersoonBericht berichtMoeder = maakMoeder();
        final PersoonBericht berichtVader = maakVader();

        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
                new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie = relBuilder
                .bouwFamilieRechtelijkeBetrekkingRelatie()
                .voegOuderToe(berichtVader).voegOuderToe(berichtMoeder).voegKindToe(maakKind())
                .getRelatie();
        //Beide met indicatie
        zetOuderAlsIndicatieAdresHoudend(nieuweSituatie, berichtVader);
        zetOuderAlsIndicatieAdresHoudend(nieuweSituatie, berichtMoeder);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden =
                brpuc00112.voerRegelUit(null, nieuweSituatie, null, bestaandeBetrokkenen);
        Assert.assertEquals(1, objectenDieDeRegelOvertreden.size());
        Assert.assertEquals(berichtVader, objectenDieDeRegelOvertreden.get(0));
    }

    private PersoonBericht maakMoeder() {
        final PersoonBericht berichtMoeder = PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, MOEDER_BSN,
                Geslachtsaanduiding.VROUW, 19360408,
                StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                "moeder", "van", "Houten");
        berichtMoeder.setObjectSleutel("moeder.tech.sleutel");
        berichtMoeder.setIdentificerendeSleutel("moeder.id.sleutel");
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
        berichtVader.setObjectSleutel("vader.tech.sleutel");
        berichtVader.setIdentificerendeSleutel("vader.id.sleutel");
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
        k.getIdentificatienummers().setCommunicatieID("id.kind.pers.ids");
        k.getGeboorte().setCommunicatieID("id.kind.pers.geboorte");
        k.getGeslachtsaanduiding().setCommunicatieID("id.kind.pers.geslacht");
        return k;
    }

    private void zetOuderAlsIndicatieAdresHoudend(final FamilierechtelijkeBetrekkingBericht nieuweSituatie,
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
}
