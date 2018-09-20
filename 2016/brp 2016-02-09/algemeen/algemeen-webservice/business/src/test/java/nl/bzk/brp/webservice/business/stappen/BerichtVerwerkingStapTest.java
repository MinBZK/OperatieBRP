/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.business.regels.BedrijfsregelManager;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.business.stappen.StappenResultaat;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class BerichtVerwerkingStapTest {

    @InjectMocks
    private AbstractBerichtVerwerkingStap abstractBerichtVerwerkingsStap;

    @Mock
    private BedrijfsregelManager bedrijfsregelManager;

    @Before
    public void init() {
        abstractBerichtVerwerkingsStap = new AbstractBerichtVerwerkingStap() {

            @Override
            public boolean voerStapUit(final BrpObject brpObject, final StappenContext stappenContext,
                                       final StappenResultaat stappenResultaat)
            {
                return true;
            }
        };

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVoegMeldingToeAanResultaat() {
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(new ArrayList<Melding>());
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoonBericht.getIdentificatienummers().setCommunicatieID("COMMID");
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRAL0012)).thenReturn(
                new RegelParameters(
                        new MeldingtekstAttribuut("test"),
                        SoortMelding.DEBLOKKEERBAAR, Regel.BRAL0012,
                        DatabaseObjectKern.PERSOON__BURGERSERVICENUMMER));

        abstractBerichtVerwerkingsStap.voegMeldingToeAanResultaat(Regel.BRAL0012,
                                                                  persoonBericht, resultaat,
                                                                  DatabaseObjectKern.PERSOON__BURGERSERVICENUMMER);

        Assert.assertFalse(resultaat.getMeldingen().isEmpty());

        final Melding melding = resultaat.getMeldingen().get(0);
        Assert.assertEquals("COMMID", melding.getReferentieID());
        Assert.assertEquals("test", melding.getMeldingTekst());
        Assert.assertEquals(Regel.BRAL0012, melding.getRegel());
    }

    @Test
    public void testVoegMeldingToeAanResultaatRegelKentPropertyPaden() {
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(new ArrayList<Melding>());
        final PersoonAdresBericht adres = new PersoonAdresBericht();
        adres.setStandaard(new PersoonAdresStandaardGroepBericht());
        adres.setCommunicatieID("COMMID");

        adres.getStandaard().setPostcode(new PostcodeAttribuut("1234AB"));
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRAL2024)).thenReturn(
                new RegelParameters(
                        new MeldingtekstAttribuut(Regel.BRAL2024.getOmschrijving()),
                        SoortMelding.DEBLOKKEERBAAR, Regel.BRAL2024,
                        DatabaseObjectKern.PERSOON__BURGERSERVICENUMMER));

        abstractBerichtVerwerkingsStap.voegMeldingToeAanResultaat(Regel.BRAL2024,
                                                                  adres, resultaat,
                                                                  DatabaseObjectKern.PERSOON_ADRES__POSTCODE);

        Assert.assertFalse(resultaat.getMeldingen().isEmpty());

        final Melding melding = resultaat.getMeldingen().get(0);
        Assert.assertEquals("COMMID", melding.getReferentieID());
        Assert.assertEquals("Formaat van de Postcode 1234AB is niet juist.", melding.getMeldingTekst());
        Assert.assertEquals(Regel.BRAL2024, melding.getRegel());
    }

    @Test
    public void testVoegMeldingToeAanResultaatObjectDatDeRegelOvertreedtIsNull() {
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(new ArrayList<Melding>());
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRAL0012)).thenReturn(
                new RegelParameters(
                        new MeldingtekstAttribuut("test"),
                        SoortMelding.DEBLOKKEERBAAR, Regel.BRAL0012,
                        DatabaseObjectKern.PERSOON__BURGERSERVICENUMMER));

        abstractBerichtVerwerkingsStap.voegMeldingToeAanResultaat(Regel.BRAL0012,
                                                                  null, resultaat,
                                                                  DatabaseObjectKern.PERSOON__BURGERSERVICENUMMER);

        Assert.assertFalse(resultaat.getMeldingen().isEmpty());

        final Melding melding = resultaat.getMeldingen().get(0);
        // Communicatie is dan natuurlijk null.
        Assert.assertEquals(null, melding.getReferentieID());
        Assert.assertEquals("test", melding.getMeldingTekst());
        Assert.assertEquals(Regel.BRAL0012, melding.getRegel());
    }

    @Test
    public void testVoegMeldingToeAanResultaatDatabaseObjectIsNull() {
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(new ArrayList<Melding>());
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoonBericht.getIdentificatienummers().setCommunicatieID("COMMID");
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRAL0012)).thenReturn(
                new RegelParameters(
                        new MeldingtekstAttribuut("test"),
                        SoortMelding.DEBLOKKEERBAAR, Regel.BRAL0012,
                        DatabaseObjectKern.PERSOON__BURGERSERVICENUMMER));

        abstractBerichtVerwerkingsStap.voegMeldingToeAanResultaat(Regel.BRAL0012,
                                                                  persoonBericht, resultaat,
                                                                  null);

        Assert.assertFalse(resultaat.getMeldingen().isEmpty());

        final Melding melding = resultaat.getMeldingen().get(0);
        Assert.assertEquals("COMMID", melding.getReferentieID());
        Assert.assertEquals("test", melding.getMeldingTekst());
        Assert.assertEquals(Regel.BRAL0012, melding.getRegel());
    }

    @Test
    public void testVoegMeldingenToeAanResultaat() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoonBericht.getIdentificatienummers().setCommunicatieID("COMMID");
        final RegelInterface regel = new RegelInterface() {
            @Override
            public Regel getRegel() {
                return Regel.BRAL0012;
            }
        };

        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRAL0012)).thenReturn(
                new RegelParameters(
                        new MeldingtekstAttribuut("test"),
                        SoortMelding.DEBLOKKEERBAAR, Regel.BRAL0012,
                        DatabaseObjectKern.PERSOON__BURGERSERVICENUMMER));

        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(new ArrayList<Melding>());
        final Map<BerichtEntiteit, Map<RegelInterface, List<BerichtEntiteit>>> falendeRegelsMetEntiteiten =
                new HashMap<BerichtEntiteit, Map<RegelInterface, List<BerichtEntiteit>>>();
        final Map<RegelInterface, List<BerichtEntiteit>> falendeRegelOpBerichtEntiteiten =
                new HashMap<RegelInterface, List<BerichtEntiteit>>();
        final List<BerichtEntiteit> falendeEntiteiten = new ArrayList<>();
        falendeEntiteiten.add(persoonBericht);
        falendeRegelOpBerichtEntiteiten.put(regel, falendeEntiteiten);
        falendeRegelsMetEntiteiten.put(persoonBericht, falendeRegelOpBerichtEntiteiten);

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
        abstractBerichtVerwerkingsStap.voegMeldingenToeAanResultaat(falendeRegelsMetEntiteiten, resultaat);
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());

        final Melding melding = resultaat.getMeldingen().get(0);
        Assert.assertEquals("COMMID", melding.getReferentieID());
        Assert.assertEquals("test", melding.getMeldingTekst());
        Assert.assertEquals(Regel.BRAL0012, melding.getRegel());
    }

    @Test
    public void testVoegMeldingenToeAanResultaatFalendeObjectenIsLeeg() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoonBericht.getIdentificatienummers().setCommunicatieID("COMMID");
        final RegelInterface regel = new RegelInterface() {
            @Override
            public Regel getRegel() {
                return Regel.BRAL0012;
            }
        };

        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRAL0012)).thenReturn(
                new RegelParameters(
                        new MeldingtekstAttribuut("test"),
                        SoortMelding.DEBLOKKEERBAAR, Regel.BRAL0012,
                        DatabaseObjectKern.PERSOON__BURGERSERVICENUMMER));

        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(new ArrayList<Melding>());
        final Map<BerichtEntiteit, Map<RegelInterface, List<BerichtEntiteit>>> falendeRegelsMetEntiteiten =
                new HashMap<>();
        final Map<RegelInterface, List<BerichtEntiteit>> falendeRegelOpBerichtEntiteiten =
                new HashMap<>();

        // Niks toevoegen aan falendeEntiteiten.
        final List<BerichtEntiteit> falendeEntiteiten = new ArrayList<>();

        falendeRegelOpBerichtEntiteiten.put(regel, falendeEntiteiten);
        falendeRegelsMetEntiteiten.put(persoonBericht, falendeRegelOpBerichtEntiteiten);

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
        abstractBerichtVerwerkingsStap.voegMeldingenToeAanResultaat(falendeRegelsMetEntiteiten, resultaat);
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());

        final Melding melding = resultaat.getMeldingen().get(0);

        // Geen communicatie ID omdat de falende entiteiten leeg is

        Assert.assertEquals("test", melding.getMeldingTekst());
        Assert.assertEquals(Regel.BRAL0012, melding.getRegel());
    }
}
