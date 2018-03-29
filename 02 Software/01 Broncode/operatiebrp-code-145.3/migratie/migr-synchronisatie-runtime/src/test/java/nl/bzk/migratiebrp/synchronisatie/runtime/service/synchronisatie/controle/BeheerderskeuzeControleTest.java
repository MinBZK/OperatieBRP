/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BeheerdersKeuzeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleUitkomst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BeheerderskeuzeControleTest {

    private static final String PL_ALS_TELETEXT =
            "0028001148011001000000012340210003Jan0240006Jansen03100081970010103200040518033000460300410001M6110001E81"
                    +
                    "10004051881200071-X000185100081970010186100081970010208122091000405990920008019701011010001W1030008019701011110006Straat11200021511600069876A"
                    + "A7210001I851000819700101861000819700102";

    @Mock
    private PlService plService;

    private BeheerderskeuzeControle subject;

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();

        final PersoonslijstVersieControle versieControle = new PersoonslijstVersieControle(plService);
        subject = new BeheerderskeuzeControle(versieControle);
    }

    @Test
    public void testVergelijkingAfkeuren() throws SynchronisatieVerwerkerException {
        final SynchroniseerNaarBrpVerzoekBericht verzoek = maakVerzoek(BeheerdersKeuzeType.AFKEUREN, PL_ALS_TELETEXT, 5L);
        final VerwerkingsContext context = maakVerwerkingsContext(verzoek);
        final BrpPersoonslijst brpPersoonslijst1 = new BrpPersoonslijstBuilder().persoonId(1L).administratieveHandelingId(1L).build();
        final BrpPersoonslijst brpPersoonslijst2 = new BrpPersoonslijstBuilder().persoonId(3423L).administratieveHandelingId(5L).build();

        Mockito.when(plService.zoekPersoonslijstOpTechnischeSleutel(1L)).thenReturn(brpPersoonslijst1);
        Mockito.when(plService.zoekPersoonslijstOpTechnischeSleutel(3423L)).thenReturn(brpPersoonslijst2);

        Assert.assertEquals(ControleUitkomst.AFKEUREN, subject.controle(context));

    }

    @Test
    public void testVergelijkingNegeren() throws SynchronisatieVerwerkerException {
        final SynchroniseerNaarBrpVerzoekBericht verzoek = maakVerzoek(BeheerdersKeuzeType.NEGEREN, PL_ALS_TELETEXT, 5L);
        final VerwerkingsContext context = maakVerwerkingsContext(verzoek);

        final BrpPersoonslijst brpPersoonslijst1 = new BrpPersoonslijstBuilder().persoonId(1L).administratieveHandelingId(1L).build();
        final BrpPersoonslijst brpPersoonslijst2 = new BrpPersoonslijstBuilder().persoonId(3423L).administratieveHandelingId(5L).build();

        Mockito.when(plService.zoekPersoonslijstOpTechnischeSleutel(1L)).thenReturn(brpPersoonslijst1);
        Mockito.when(plService.zoekPersoonslijstOpTechnischeSleutel(3423L)).thenReturn(brpPersoonslijst2);

        Assert.assertEquals(ControleUitkomst.NEGEREN, subject.controle(context));

    }

    @Test
    public void testVergelijkingToevoegen() throws SynchronisatieVerwerkerException {
        final SynchroniseerNaarBrpVerzoekBericht verzoek = maakVerzoek(BeheerdersKeuzeType.TOEVOEGEN, PL_ALS_TELETEXT, 5L);
        final VerwerkingsContext context = maakVerwerkingsContext(verzoek);

        final BrpPersoonslijst brpPersoonslijst1 = new BrpPersoonslijstBuilder().persoonId(1L).administratieveHandelingId(1L).build();
        final BrpPersoonslijst brpPersoonslijst2 = new BrpPersoonslijstBuilder().persoonId(3423L).administratieveHandelingId(5L).build();

        Mockito.when(plService.zoekPersoonslijstOpTechnischeSleutel(1L)).thenReturn(brpPersoonslijst1);
        Mockito.when(plService.zoekPersoonslijstOpTechnischeSleutel(3423L)).thenReturn(brpPersoonslijst2);

        Assert.assertEquals(ControleUitkomst.TOEVOEGEN, subject.controle(context));

    }

    @Test
    public void testVergelijkingVervangen() throws SynchronisatieVerwerkerException {
        final SynchroniseerNaarBrpVerzoekBericht verzoek = maakVerzoek(BeheerdersKeuzeType.VERVANGEN, PL_ALS_TELETEXT, 5L);
        final VerwerkingsContext context = maakVerwerkingsContext(verzoek);

        final BrpPersoonslijst brpPersoonslijst1 = new BrpPersoonslijstBuilder().persoonId(1L).administratieveHandelingId(1L).build();
        final BrpPersoonslijst brpPersoonslijst2 = new BrpPersoonslijstBuilder().persoonId(3423L).administratieveHandelingId(5L).build();

        Mockito.when(plService.zoekPersoonslijstOpTechnischeSleutel(1L)).thenReturn(brpPersoonslijst1);
        Mockito.when(plService.zoekPersoonslijstOpTechnischeSleutel(3423L)).thenReturn(brpPersoonslijst2);

        Assert.assertEquals(ControleUitkomst.VERVANGEN, subject.controle(context));

    }

    @Test
    public void testVergelijkingVersiesOnGelijk() throws SynchronisatieVerwerkerException {
        final SynchroniseerNaarBrpVerzoekBericht verzoek = maakVerzoek(BeheerdersKeuzeType.VERVANGEN, PL_ALS_TELETEXT, 5L);
        final VerwerkingsContext context = maakVerwerkingsContext(verzoek);

        final BrpPersoonslijst brpPersoonslijst1 = new BrpPersoonslijstBuilder().persoonId(1L).administratieveHandelingId(1L).build();
        final BrpPersoonslijst brpPersoonslijst2 = new BrpPersoonslijstBuilder().persoonId(3423L).administratieveHandelingId(2L).build();

        Mockito.when(plService.zoekPersoonslijstOpTechnischeSleutel(1L)).thenReturn(brpPersoonslijst1);
        Mockito.when(plService.zoekPersoonslijstOpTechnischeSleutel(3423L)).thenReturn(brpPersoonslijst2);

        Assert.assertEquals(ControleUitkomst.ONDUIDELIJK, subject.controle(context));
    }

    @Test
    public void testVergelijkingGeenBeheerdersKeuze() throws SynchronisatieVerwerkerException {
        final SynchroniseerNaarBrpVerzoekBericht verzoek = maakVerzoek(null, PL_ALS_TELETEXT, 5L);
        final VerwerkingsContext context = maakVerwerkingsContext(verzoek);

        Assert.assertEquals(ControleUitkomst.ONDUIDELIJK, subject.controle(context));
    }

    private SynchroniseerNaarBrpVerzoekBericht maakVerzoek(
            final BeheerdersKeuzeType beheerderskeuze,
            final String lo3PersoonslijstAlsTeletexString,
            final Long teVervangenPersoonId) {

        final SynchroniseerNaarBrpVerzoekBericht verzoek = new SynchroniseerNaarBrpVerzoekBericht(lo3PersoonslijstAlsTeletexString);
        final List<Kandidaat> kandidaten = new ArrayList<>();
        final Kandidaat kandidaat1 = new Kandidaat();
        final Kandidaat kandidaat2 = new Kandidaat();
        kandidaat1.setPersoonId(1);
        kandidaat1.setVersie(1);
        kandidaat2.setPersoonId(3423L);
        kandidaat2.setVersie(5);
        kandidaten.add(kandidaat1);
        kandidaten.add(kandidaat2);

        if (beheerderskeuze != null) {
            verzoek.setBeheerderKeuze(beheerderskeuze, teVervangenPersoonId, kandidaten);
        }

        return verzoek;
    }

    private VerwerkingsContext maakVerwerkingsContext(final SynchroniseerNaarBrpVerzoekBericht verzoek) {

        final Lo3Bericht logging = Mockito.mock(Lo3Bericht.class);

        return new VerwerkingsContext(verzoek, logging, verzoek.getLo3Persoonslijst(), null);

    }

}
