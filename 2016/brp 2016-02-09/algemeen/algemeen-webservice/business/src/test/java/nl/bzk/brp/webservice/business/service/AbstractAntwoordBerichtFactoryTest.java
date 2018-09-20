/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaatImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class AbstractAntwoordBerichtFactoryTest {

    private final PartijAttribuut partij1 = new PartijAttribuut(TestPartijBuilder.maker().metCode(123).maak());

    private final AntwoordBericht abstractAntwoordBericht = new AntwoordBericht(
        SoortBericht.BHG_AFS_ACTUALISEER_AFSTAMMING_R)
    {
    };

    private static final String ZENDEND_SYSTEEM  = "SYSTEEMPJE";
    private static final String REFERENTIENUMMER = "refnr123";

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    private final AbstractAntwoordBerichtFactory abstractAntwoordBerichtFactory = new AbstractAntwoordBerichtFactory() {

        @Override
        protected void vulAntwoordBerichtAan(final BerichtVerwerkingsResultaat resultaat,
            final Bericht inkomend,
            final AntwoordBericht antwoord)
        {

        }

        @Override
        protected AntwoordBericht maakInitieelAntwoordBerichtVoorInkomendBericht(final Bericht ingaandBericht) {
            return abstractAntwoordBericht;
        }

        @Override
        protected BerichtResultaatGroepBericht maakInitieelBerichtResultaatGroepBericht(
            final Bericht ingaandBericht, final BerichtVerwerkingsResultaat resultaat)
        {
            return new BerichtResultaatGroepBericht();
        }
    };

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(abstractAntwoordBerichtFactory, "referentieDataRepository",
            referentieDataRepository);
        Mockito.when(referentieDataRepository.vindPartijOpCode(PartijCodeAttribuut.BRP_VOORZIENING))
            .thenReturn(TestPartijBuilder.maker().metCode(PartijCodeAttribuut.BRP_VOORZIENING).maak());
    }

    @Test
    public void testVullingStuurgegevensAntwoordBericht() {
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(new ArrayList<Melding>());
        final BerichtBericht ingaandBericht = maakIngaandTestBericht(ZENDEND_SYSTEEM, partij1, REFERENTIENUMMER,
            Verwerkingswijze.BIJHOUDING);

        final AntwoordBericht abstractAntwoordBerichtLokaal =
            abstractAntwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);

        final BerichtStuurgegevensGroepBericht stuurgegevensAntwoordBericht = abstractAntwoordBerichtLokaal.getStuurgegevens();

        // Ontvangend wordt nooit gevuld
        Assert.assertNull(stuurgegevensAntwoordBericht.getOntvangendeSysteem());
        Assert.assertNull(stuurgegevensAntwoordBericht.getOntvangendePartij());

        // Zendend is de BRP.
        Assert.assertEquals(PartijCodeAttribuut.BRP_VOORZIENING,
            stuurgegevensAntwoordBericht.getZendendePartij().getWaarde().getCode());
        Assert.assertEquals(SysteemNaamAttribuut.BRP, stuurgegevensAntwoordBericht.getZendendeSysteem());

        // Referentienummer controleren
        Assert.assertEquals(REFERENTIENUMMER, stuurgegevensAntwoordBericht.getCrossReferentienummer().getWaarde());
        Assert.assertNotNull(stuurgegevensAntwoordBericht.getReferentienummer().getWaarde());

        // Is het een UUID?
        final UUID uuid = UUID.fromString(stuurgegevensAntwoordBericht.getReferentienummer().getWaarde());
        //Geen exceptie? UUID is geldig! UUID moet random zijn, dus versie 4!
        Assert.assertEquals(4, uuid.version());
    }

    @Test
    public void testResultaatGroepBepalingAntwoordBerichtGeenMeldingen() {
        final List<Melding> meldingen = new ArrayList<>();
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);

        final BerichtBericht ingaandBericht = maakIngaandTestBericht(ZENDEND_SYSTEEM, partij1, REFERENTIENUMMER,
            Verwerkingswijze.BIJHOUDING);

        final AntwoordBericht antwoordBericht = abstractAntwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);

        Assert.assertEquals(SoortMelding.GEEN, antwoordBericht.getResultaat().getHoogsteMeldingsniveau().getWaarde());
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD, antwoordBericht.getResultaat().getVerwerking().getWaarde());
        Assert.assertNull(antwoordBericht.getResultaat().getBijhouding());
    }

    @Test
    public void testResultaatGroepBepalingAntwoordBerichtMetWaarschuwingMelding() {
        final List<Melding> meldingen = new ArrayList<>();
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, Regel.ACT0001, "Pietje viel van een boom."));
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);

        final BerichtBericht ingaandBericht = maakIngaandTestBericht(ZENDEND_SYSTEEM, partij1, REFERENTIENUMMER,
            Verwerkingswijze.BIJHOUDING);

        final AntwoordBericht antwoordBericht = abstractAntwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);

        Assert.assertEquals(SoortMelding.WAARSCHUWING, antwoordBericht.getResultaat().getHoogsteMeldingsniveau().getWaarde());
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD, antwoordBericht.getResultaat().getVerwerking().getWaarde());
        Assert.assertNull(antwoordBericht.getResultaat().getBijhouding());
    }

    @Test
    public void testResultaatGroepBepalingAntwoordBerichtMetWaarschuwingEnFoutMelding() {
        final List<Melding> meldingen = new ArrayList<>();
        meldingen.add(new Melding(SoortMelding.WAARSCHUWING, Regel.ACT0001, "Pietje viel van een boom."));
        meldingen.add(new Melding(SoortMelding.FOUT, Regel.ACT0001, "Pietje viel van een boom."));
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);

        final BerichtBericht ingaandBericht = maakIngaandTestBericht(ZENDEND_SYSTEEM, partij1, REFERENTIENUMMER,
            Verwerkingswijze.BIJHOUDING);

        final AntwoordBericht antwoordBericht = abstractAntwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);

        Assert.assertEquals(SoortMelding.FOUT, antwoordBericht.getResultaat().getHoogsteMeldingsniveau().getWaarde());
        Assert.assertEquals(Verwerkingsresultaat.FOUTIEF, antwoordBericht.getResultaat().getVerwerking().getWaarde());
        Assert.assertNull(antwoordBericht.getResultaat().getBijhouding());
    }

    @Test
    public void testResultaatGroepBepalingAntwoordBerichtMetDeblokkeerBaarEnFoutMelding() {
        final List<Melding> meldingen = new ArrayList<>();
        meldingen.add(new Melding(SoortMelding.DEBLOKKEERBAAR, Regel.ACT0001, "Pietje viel van een boom."));
        meldingen.add(new Melding(SoortMelding.FOUT, Regel.ACT0001, "Pietje viel van een boom."));
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);

        final BerichtBericht ingaandBericht = maakIngaandTestBericht(ZENDEND_SYSTEEM, partij1, REFERENTIENUMMER,
            Verwerkingswijze.BIJHOUDING);

        final AntwoordBericht antwoordBericht = abstractAntwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);

        Assert.assertEquals(SoortMelding.FOUT, antwoordBericht.getResultaat().getHoogsteMeldingsniveau().getWaarde());
        Assert.assertEquals(Verwerkingsresultaat.FOUTIEF, antwoordBericht.getResultaat().getVerwerking().getWaarde());
        Assert.assertNull(antwoordBericht.getResultaat().getBijhouding());

        Assert.assertFalse(antwoordBericht.getMeldingen().isEmpty());
    }

    @Test
    public void testMeldingMetRefernetieID() {
        final List<Melding> meldingen = new ArrayList<>();

        final BerichtIdentificeerbaar identificeerbaar = new PersoonBericht();
        identificeerbaar.setCommunicatieID("communicatieId");

        meldingen.add(new Melding(SoortMelding.FOUT, Regel.ACT0001, "Pietje viel van een boom.", identificeerbaar, null));
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);

        final BerichtBericht ingaandBericht = maakIngaandTestBericht(ZENDEND_SYSTEEM, partij1, REFERENTIENUMMER,
            Verwerkingswijze.BIJHOUDING);

        final AntwoordBericht antwoordBericht = abstractAntwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);

        Assert.assertEquals(SoortMelding.FOUT, antwoordBericht.getResultaat().getHoogsteMeldingsniveau().getWaarde());
        Assert.assertEquals(Verwerkingsresultaat.FOUTIEF, antwoordBericht.getResultaat().getVerwerking().getWaarde());
        Assert.assertFalse(antwoordBericht.getMeldingen().isEmpty());
        Assert.assertEquals("communicatieId", antwoordBericht.getMeldingen().get(0).getMelding().getReferentieID());
        Assert.assertNull(antwoordBericht.getResultaat().getBijhouding());
    }

    @Test
    public void testBerichtResulstaatMetTweeIdentiekeMeldingen() {
        final List<Melding> meldingen = new ArrayList<>();

        final BerichtIdentificeerbaar identificeerbaar = new PersoonBericht();
        identificeerbaar.setCommunicatieID("communicatieId");

        meldingen.add(new Melding(SoortMelding.FOUT, Regel.ACT0001, "Pietje viel van een boom.", identificeerbaar, null));
        meldingen.add(new Melding(SoortMelding.FOUT, Regel.ACT0001, "Pietje viel van een boom.", identificeerbaar, null));
        final BerichtVerwerkingsResultaatImpl resultaat = new BerichtVerwerkingsResultaatImpl(meldingen);

        final BerichtBericht ingaandBericht = maakIngaandTestBericht(ZENDEND_SYSTEEM, partij1, REFERENTIENUMMER,
            Verwerkingswijze.BIJHOUDING);

        final AntwoordBericht antwoordBericht = abstractAntwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);

        Assert.assertEquals(SoortMelding.FOUT, antwoordBericht.getResultaat().getHoogsteMeldingsniveau().getWaarde());
        Assert.assertEquals(Verwerkingsresultaat.FOUTIEF, antwoordBericht.getResultaat().getVerwerking().getWaarde());
        Assert.assertFalse(antwoordBericht.getMeldingen().isEmpty());
        Assert.assertNull(antwoordBericht.getResultaat().getBijhouding());
    }

    private BerichtBericht maakIngaandTestBericht(final String zendendeSysteem,
        final PartijAttribuut zendendePartij,
        final String referentieNummer,
        final Verwerkingswijze verwerkingswijze)
    {
        final BerichtBericht ingaandBericht = Mockito.mock(BerichtBericht.class);
        Mockito.when(ingaandBericht.getAdministratieveHandeling()).thenReturn(new HandelingGeboorteInNederlandBericht());

        final BerichtStuurgegevensGroepBericht stuurgegevensIngaand = new BerichtStuurgegevensGroepBericht();
        stuurgegevensIngaand.setZendendeSysteem(new SysteemNaamAttribuut(zendendeSysteem));
        stuurgegevensIngaand.setZendendePartij(zendendePartij);
        stuurgegevensIngaand.setReferentienummer(new ReferentienummerAttribuut(referentieNummer));
        Mockito.when(ingaandBericht.getStuurgegevens()).thenReturn(stuurgegevensIngaand);

        if (verwerkingswijze != null) {
            final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
            parameters.setVerwerkingswijze(new VerwerkingswijzeAttribuut(verwerkingswijze));
            Mockito.when(ingaandBericht.getParameters()).thenReturn(parameters);
        }
        return ingaandBericht;
    }
}
