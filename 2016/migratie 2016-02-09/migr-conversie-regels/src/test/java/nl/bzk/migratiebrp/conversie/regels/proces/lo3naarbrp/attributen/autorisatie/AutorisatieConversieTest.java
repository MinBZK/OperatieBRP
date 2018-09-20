/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatieTest;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;

import org.junit.Ignore;
import org.junit.Test;

public class AutorisatieConversieTest extends AbstractComponentTest {

    @Inject
    private AutorisatieConversie autorisatieConversie;

    @Test
    @Ignore
    // TODO
    public void test() {

        final BrpAutorisatie input = maakBrpAutorisatie();
        final BrpAutorisatie autorisatieMetAfgeleideGegevens = autorisatieConversie.vulAfgeleideGegevens(input);
        Assert.assertNotNull(autorisatieMetAfgeleideGegevens);

        // vulAfgeleideGegevens verwerkt de stapel met *Inhoud
        // hierbij wordt op inhoudsniveau de historie gezet via DatumAanvangGeldigheid en DatumEindeGeldigheid
        // de historie van de groep bevat geen geldigheid, alleen datum registratie en verval

        // PARTIJ
        Assert.assertNotNull(autorisatieMetAfgeleideGegevens.getPartij());
        final BrpStapel<BrpPartijInhoud> partijStapel = autorisatieMetAfgeleideGegevens.getPartij().getPartijStapel();

        // geen materiele historie
        controleerMaterieleHistorie(partijStapel);
        controleerInhoud(partijStapel.getLaatsteElement(), input.getPartij().getPartijStapel().getLaatsteElement());

        // AUTORISATIE BESLUIT
        Assert.assertNotNull(autorisatieMetAfgeleideGegevens.getLeveringsAutorisatieLijst());
        Assert.assertNotNull(autorisatieMetAfgeleideGegevens.getLeveringsAutorisatieLijst().get(0));
        final BrpStapel<BrpLeveringsautorisatieInhoud> leveringsautorisatieStapel =
                autorisatieMetAfgeleideGegevens.getLeveringsAutorisatieLijst().get(0).getLeveringsautorisatieStapel();

        controleerMaterieleHistorie(leveringsautorisatieStapel);
        controleerInhoud(leveringsautorisatieStapel.getLaatsteElement(), input.getLeveringsAutorisatieLijst()
            .get(0)
            .getLeveringsautorisatieStapel()
            .getLaatsteElement());

        // ABONNEMENTEN
        Assert.assertNotNull(autorisatieMetAfgeleideGegevens.getLeveringsAutorisatieLijst().get(0).getDienstbundels());
        final List<BrpDienstbundel> abonnementen = autorisatieMetAfgeleideGegevens.getLeveringsAutorisatieLijst().get(0).getDienstbundels();

        for (final BrpDienstbundel abo : abonnementen) {
            controleerMaterieleHistorie(abo.getDienstbundelStapel());
            controleerInhoud(abo.getDienstbundelStapel().getLaatsteElement(), input.getLeveringsAutorisatieLijst()
                .get(0)
                .getDienstbundels()
                .get(0)
                .getDienstbundelStapel()
                .getLaatsteElement());

            // for (final BrpAbonnementLo3Rubriek expr : abo.getLo3Rubrieken()) {
            // controleerMaterieleHistorie(expr.getAbonnementLo3RubriekStapel());
            //
            // controleerInhoud(expr.getAbonnementLo3RubriekStapel().getLaatsteElement(),
            // input.getAutorisatieBesluitLijst()
            // .get(0)
            // .getAbonnementen()
            // .get(0)
            // .getAbonnementStapel()
            // .getLaatsteElement());
            // }
            //
        }

    }

    private void controleerInhoud(final BrpGroep<? extends BrpGroepInhoud> output, final BrpGroep<? extends BrpGroepInhoud> input) {
        Assert.assertEquals(input.getHistorie().getDatumTijdRegistratie(), output.getHistorie().getDatumTijdRegistratie());
        Assert.assertEquals(input.getHistorie().getDatumTijdVerval(), output.getHistorie().getDatumTijdVerval());
    }

    private void controleerMaterieleHistorie(final BrpStapel<? extends BrpGroepInhoud> stapel) {

        for (final BrpGroep<? extends BrpGroepInhoud> groep : stapel) {
            Assert.assertNull(groep.getHistorie().getDatumAanvangGeldigheid());
            Assert.assertNull(groep.getHistorie().getDatumEindeGeldigheid());
        }
    }

    private BrpAutorisatie maakBrpAutorisatie() {
        return BrpAutorisatieTest.maak(1);
    }

}
