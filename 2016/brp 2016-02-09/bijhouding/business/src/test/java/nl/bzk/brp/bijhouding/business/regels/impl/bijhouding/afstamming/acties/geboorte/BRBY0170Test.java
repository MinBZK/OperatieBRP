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
import org.junit.Test;


public class BRBY0170Test {

    private static final Integer MOEDER_BSN = 111111111;
    private static final Integer VADER_BSN = 222222222;

    private final BRBY0170 brby0170 = new BRBY0170();

    private PersoonBericht berichtMoeder;
    private PersoonBericht berichtVader;

    private Map<String, PersoonView> bestaandeBetrokkenen;

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0170, brby0170.getRegel());
    }

    @Test
    public void testPerfectRelatie() {
        final SoortPersoon soortPersoonVader = SoortPersoon.INGESCHREVENE;
        creeerBeginSituatie(soortPersoonVader);

        // dit gaat goed, moeder (met indicatie indicatieOuderUitWieKindIsVoortgekomen), vader (M, zonder indicatie)
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
                new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie =
                relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(berichtMoeder)
                        .voegOuderToe(berichtVader)
                        .voegKindToe(maakKind()).getRelatie();
        //Zet indicatie op moeder
        zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie, berichtMoeder);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = brby0170.voerRegelUit(null, nieuweSituatie, null,
                bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testVaderNietIngeschrevene() {
        final SoortPersoon soortPersoonVader = SoortPersoon.NIET_INGESCHREVENE;
        creeerBeginSituatie(soortPersoonVader);

        // dit gaat goed, moeder (met indicatie indicatieOuderUitWieKindIsVoortgekomen), vader (M, zonder indicatie)
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht> relBuilder =
                new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie =
                relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(berichtMoeder)
                        .voegOuderToe(berichtVader)
                        .voegKindToe(maakKind()).getRelatie();
        //Zet indicatie op moeder
        zetOuderMoederAlsIndicatieAdresHoudend(nieuweSituatie, berichtMoeder);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = brby0170.voerRegelUit(null, nieuweSituatie, null,
                                                                                   bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testRelatieMetVaderMoederZonderIndicatie() {
        final SoortPersoon soortPersoonVader = SoortPersoon.INGESCHREVENE;
        creeerBeginSituatie(soortPersoonVader);

        // dit gaat fout, omdat we nu een ouder vindt van type 'V' en heeft niet de flag IndicatieAdresHoudernd.
        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht>
                relBuilder = new RelatieBuilder<>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie =
                relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(berichtMoeder)
                        .voegOuderToe(berichtVader)
                        .voegKindToe(maakKind()).getRelatie();

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = brby0170.voerRegelUit(null, nieuweSituatie, null,
                bestaandeBetrokkenen);
        Assert.assertEquals(1, objectenDieDeRegelOvertreden.size());
        Assert.assertEquals(berichtMoeder, objectenDieDeRegelOvertreden.get(0));
    }

    @Test
    public void testRelatieMetAlleenVader() {
        final SoortPersoon soortPersoonVader = SoortPersoon.INGESCHREVENE;
        creeerBeginSituatie(soortPersoonVader);

        final RelatieBuilder<FamilierechtelijkeBetrekkingBericht>
                relBuilder = new RelatieBuilder<>();
        final FamilierechtelijkeBetrekkingBericht nieuweSituatie =
                relBuilder.bouwFamilieRechtelijkeBetrekkingRelatie().voegOuderToe(berichtVader).voegKindToe(maakKind())
                        .getRelatie();
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = brby0170.voerRegelUit(null, nieuweSituatie, null,
                bestaandeBetrokkenen);
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    private void creeerBeginSituatie(final SoortPersoon soortPersoonVader) {
        berichtMoeder = maakMoeder();
        berichtVader = maakVader(soortPersoonVader);

        final PersoonView moeder =
                new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                                        .nieuwGeslachtsaanduidingRecord(19800101, null, 19800101)
                                        .geslachtsaanduiding(Geslachtsaanduiding.VROUW)
                                        .eindeRecord().build());
        final PersoonView vader =
                new PersoonView(new PersoonHisVolledigImplBuilder(soortPersoonVader)
                                        .nieuwGeslachtsaanduidingRecord(19800101, null, 19800101)
                                        .geslachtsaanduiding(Geslachtsaanduiding.MAN)
                                        .eindeRecord().build());

        bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("id.moeder.pers", moeder);
        bestaandeBetrokkenen.put("id.vader.pers", vader);
    }

    private PersoonBericht maakMoeder() {
        final PersoonBericht bMoeder =
                PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, MOEDER_BSN, Geslachtsaanduiding.VROUW, 19360408,
                        StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                        "moeder", "van", "Houten");
        bMoeder.setIdentificerendeSleutel("id.moeder.pers");
        bMoeder.getIdentificatienummers().setCommunicatieID("id.moeder.pers.ids");
        bMoeder.getGeboorte().setCommunicatieID("id.moeder.pers.geboorte");
        bMoeder.getGeslachtsaanduiding().setCommunicatieID("id.moeder.pers.geslacht");
        return bMoeder;
    }

    private PersoonBericht maakVader(final SoortPersoon soortPersoon) {
        final PersoonBericht bVader =
                PersoonBuilder.bouwPersoon(soortPersoon, VADER_BSN, Geslachtsaanduiding.MAN, 19261225,
                        StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde(),
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                        "vader", "van", "Houten");
        bVader.setIdentificerendeSleutel("id.vader.pers");
        bVader.getIdentificatienummers().setCommunicatieID("id.vader.pers.ids");
        bVader.getGeboorte().setCommunicatieID("id.vader.pers.geboorte");
        bVader.getGeslachtsaanduiding().setCommunicatieID("id.vader.pers.geslacht");
        return bVader;
    }

    private PersoonBericht maakKind() {
        final PersoonBericht k =
                PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 666666666, Geslachtsaanduiding.MAN, 20120730,
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
            if (betr.getPersoon().getIdentificatienummers().getBurgerservicenummer().getWaarde()
                    .equals(ouder.getIdentificatienummers().getBurgerservicenummer().getWaarde()))
            {
                betr.setOuderschap(new OuderOuderschapGroepBericht());
                betr.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(JaNeeAttribuut.JA);
            }
        }

    }
}
