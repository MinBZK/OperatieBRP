/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * Testen voor {@link CorrectieRegistratieRelatieActieElement}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CorrectieRegistratieRelatieActieElementTest extends AbstractElementTest {

    @Mock
    private ApplicationContext context;
    @Mock
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;
    private Map<String, String> actieAttributen;
    private DatumElement datumAanvangGeldigheid;
    private ElementBuilder builder;

    @Before
    public void setup() {
        final ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(context);
        when(context.getBean(DynamischeStamtabelRepository.class)).thenReturn(dynamischeStamtabelRepository);
        actieAttributen = new AbstractBmrGroep.AttributenBuilder().communicatieId("CI_actie").objecttype("Actie").build();
        datumAanvangGeldigheid = new DatumElement(20170330);
        builder = new ElementBuilder();
    }

    @Test
    public void testGetRelatie() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters().datumAanvang(datumAanvangGeldigheid.getWaarde()).landGebiedAanvangCode("6030")), Collections.emptyList());
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        assertEquals(relatieElement, actieElement.getRelatie());
    }

    @Test
    public void testGetSoortActie() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters().datumAanvang(datumAanvangGeldigheid.getWaarde()).landGebiedAanvangCode("6030")), Collections.emptyList());
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        assertEquals(SoortActie.CORRECTIEREGISTRATIE_RELATIE, actieElement.getSoortActie());
    }

    @Test
    public void testGetHoofdPersonen() {
        final String objectSleutelRelatie = "1";
        final RelatieElement
                relatieElement =
                builder.maakHuwelijkElement("CI_huwelijk", objectSleutelRelatie, builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                        .RelatieGroepParameters().datumAanvang(datumAanvangGeldigheid.getWaarde()).landGebiedAanvangCode("6030")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, objectSleutelRelatie)).thenReturn(relatieEntiteit);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        assertEquals(0, actieElement.getHoofdPersonen().size());
    }

    @Test
    public void testGetPersoonElementen() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        assertTrue(actieElement.getPersoonElementen().isEmpty());
    }

    @Test
    public void testGetPeilDatum() {
        final DatumElement vandaag = new DatumElement(DatumUtil.vandaag());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        when(bericht.getDatumOntvangst()).thenReturn(vandaag);
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        assertEquals(vandaag, actieElement.getPeilDatum());
    }

    @Test
    public void testBepaalSetVoorNieuwVoorkomen() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters().datumAanvang(datumAanvangGeldigheid.getWaarde()).landGebiedAanvangCode("6030")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        assertEquals(1, actieElement.bepaalSetVoorNieuwVoorkomen().size());
        assertSame(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()),
                actieElement.bepaalSetVoorNieuwVoorkomen().iterator().next());
    }

    @Test
    public void testMaakNieuwVoorkomen() {
        final String landGebiedCode = "6030";
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters().datumAanvang(datumAanvangGeldigheid.getWaarde()).landGebiedAanvangCode(landGebiedCode)
                .landGebiedEindeCode(landGebiedCode)), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.setId(1L);
        persoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatieEntiteit.getDelegate());
        relatieEntiteit.addBetrokkenheid(betrokkenheid);
        persoon.addBetrokkenheid(betrokkenheid);
        when(bericht.getEntiteitVoorId(BijhoudingPersoon.class, persoon.getId())).thenReturn(persoon);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        when(dynamischeStamtabelRepository.getLandOfGebiedByCode(landGebiedCode)).thenReturn(new LandOfGebied(landGebiedCode, "test"));
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("test", "000000"), SoortAdministratieveHandeling.CORRECTIE_HUWELIJK,
                        new Timestamp(System.currentTimeMillis()));
        final BRPActie
                actie =
                new BRPActie(SoortActie.CORRECTIEREGISTRATIE_RELATIE, administratieveHandeling, administratieveHandeling.getPartij(),
                        administratieveHandeling.getDatumTijdRegistratie());
        final RelatieHistorie nieuwVoorkomen = actieElement.maakNieuwVoorkomen();
        assertNotNull(nieuwVoorkomen);
        assertEquals(relatieElement.getRelatieGroep().getDatumAanvang().getWaarde(), nieuwVoorkomen.getDatumAanvang());
        assertEquals(relatieElement.getRelatieGroep().getLandGebiedAanvangCode().getWaarde(), nieuwVoorkomen.getLandOfGebiedAanvang().getCode());
        assertEquals(relatieElement.getRelatieGroep().getLandGebiedEindeCode().getWaarde(), nieuwVoorkomen.getLandOfGebiedEinde().getCode());
        assertNull(nieuwVoorkomen.getActieInhoud());
        assertEquals(1, relatieEntiteit.getRelatieHistorieSet().size());
        assertTrue(actieElement.zijnAlleHoofdPersonenVerwerkbaar());
        //test of verwerking wel het nieuwe voorkomen toevoegd aan de relatie
        final BRPActie nieuweActie = actieElement.verwerk(bericht, administratieveHandeling);
        assertNotNull(nieuweActie);
        assertEquals(2, relatieEntiteit.getRelatieHistorieSet().size());
        assertSame(nieuweActie, FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()).getActieInhoud());
    }

    @Test
    public void testNietAlleHoofdPersonenVerwerkbaar() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters().datumAanvang(datumAanvangGeldigheid.getWaarde()).landGebiedAanvangCode("6030")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("test", "000000"), SoortAdministratieveHandeling.CORRECTIE_HUWELIJK,
                        new Timestamp(System.currentTimeMillis()));
        //execute & verify
        assertNull(actieElement.verwerk(bericht, administratieveHandeling));
    }

    @Test
    @Bedrijfsregel(Regel.R1872)
    public void testDatumAanvangRelatieBijHuwelijkOfGp() {
        //setup
        final DatumElement morgen = new DatumElement(DatumUtil.vandaag() + 1);
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters().datumAanvang(morgen.getWaarde()).landGebiedAanvangCode("6030").gemeenteAanvangCode("0521")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        //setup prevalidatie
        when(bericht.isPrevalidatie()).thenReturn(true);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, morgen, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        //execute
        List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        //valideer
        controleerRegels(meldingen, Regel.R1872);
        //setup GEEN prevalidatie
        when(bericht.isPrevalidatie()).thenReturn(false);
        //execute
        meldingen = actieElement.valideerSpecifiekeInhoud();
        //valideer
        controleerRegels(meldingen);
    }

    @Test
    @Bedrijfsregel(Regel.R2478)
    public void testCorrectieEinddatumZonderReden() {
        //setup
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters().datumEinde(datumAanvangGeldigheid.getWaarde()).landGebiedEindeCode("6030").gemeenteEindeCode("0521")
                .landGebiedAanvangCode("6030").gemeenteAanvangCode("0521")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        relatieElement.setVerzoekBericht(bericht);

        final CorrectieRegistratieRelatieActieElement actieElement = new CorrectieRegistratieRelatieActieElement(actieAttributen,
                datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()).setDatumEinde(20000101);
        //execute
        List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        //valideer
        controleerRegels(meldingen, Regel.R2478);
    }

    @Test
    @Bedrijfsregel(Regel.R2478)
    public void testCorrectieEinddatumZonderLandOfGebiedCode() {
        //setup
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters().datumEinde(datumAanvangGeldigheid.getWaarde()).redenEindeCode('C').landGebiedAanvangCode("6030")
                .gemeenteAanvangCode("0521")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        relatieElement.setVerzoekBericht(bericht);

        final CorrectieRegistratieRelatieActieElement actieElement = new CorrectieRegistratieRelatieActieElement(actieAttributen,
                datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()).setDatumEinde(20000101);
        //execute
        List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        //valideer
        controleerRegels(meldingen, Regel.R2478);
    }

    @Test
    @Bedrijfsregel(Regel.R1878)
    public void testCorrectieEindeR1878() {
        //setup
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                        .RelatieGroepParameters().datumEinde(datumAanvangGeldigheid.getWaarde()).redenEindeCode('C').landGebiedEindeCode("6030")
                        .gemeenteEindeCode("0521").landGebiedAanvangCode("6030").gemeenteAanvangCode("0521")),
                Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        //execute
        List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        //valideer
        controleerRegels(meldingen, Regel.R1878);
        //setup einde in bestaande relatie
        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()).setDatumEinde(20000101);
        //execute
        meldingen = actieElement.valideerSpecifiekeInhoud();
        //valideer
        controleerRegels(meldingen);
    }

    @Test
    @Bedrijfsregel(Regel.R2477)
    @Bedrijfsregel(Regel.R2479)
    public void testNietigverklaringR2477Fout1() {
        //setup
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters().datumAanvang(datumAanvangGeldigheid.getWaarde()).redenEindeCode('N').landGebiedAanvangCode("6030")
                .gemeenteAanvangCode("0521")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        //execute
        List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        //valideer
        controleerRegels(meldingen, Regel.R2477, Regel.R2479);
    }

    @Test
    @Bedrijfsregel(Regel.R2477)
    public void testNietigverklaringR2477Fout2() {
        //setup
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters().redenEindeCode('N').landGebiedAanvangCode("6030").gemeenteAanvangCode("0521")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        //execute
        List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        //valideer
        controleerRegels(meldingen, Regel.R2477, Regel.R2479);
    }

    @Test
    @Bedrijfsregel(Regel.R2477)
    public void testNietigverklaringR2477Goed() {
        //setup
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                        .RelatieGroepParameters().datumAanvang(datumAanvangGeldigheid.getWaarde()).datumEinde(datumAanvangGeldigheid.getWaarde())
                        .redenEindeCode('N').landGebiedEindeCode("6030").gemeenteEindeCode("0521").landGebiedAanvangCode("6030").gemeenteAanvangCode("0521")),
                Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        relatieElement.setVerzoekBericht(bericht);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        final CorrectieRegistratieRelatieActieElement actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()).setDatumEinde(20000101);
        //execute
        List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        //valideer
        controleerRegels(meldingen);
    }

    @Test
    @Bedrijfsregel(Regel.R2479)
    public void testBeeindigingGegevensZonderDatumEinde() {
        //setup
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                        .RelatieGroepParameters().omschrijvingLocatieEinde("locatieEinde").landGebiedAanvangCode("6030").gemeenteAanvangCode("0521")),
                Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        //execute
        List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        //valideer
        controleerRegels(meldingen, Regel.R2479);
    }

    @Bedrijfsregel(Regel.R2044)
    @Test
    public void testGemeenteAanvangNietGevuld_LandgebiedAanvangIsNederland() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("huwelijk", "1", builder.maakRelatieGroepElement("relatie", new ElementBuilder
                .RelatieGroepParameters().landGebiedAanvangCode("6030")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        controleerRegels(actieElement.valideerSpecifiekeInhoud(), Regel.R2044);
    }

    @Bedrijfsregel(Regel.R2044)
    @Test
    public void testGemeenteAanvangNietGevuld_LandgebiedAanvangIsNietNederland() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("huwelijk", "1", builder.maakRelatieGroepElement("relatie", new ElementBuilder
                .RelatieGroepParameters().landGebiedAanvangCode("5034")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        controleerRegels(actieElement.valideerSpecifiekeInhoud());
    }


    @Bedrijfsregel(Regel.R2044)
    @Test
    public void testGemeenteAanvangGevuld_LandgebiedAanvangIsNederland() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("huwelijk", "1", builder.maakRelatieGroepElement("relatie", new ElementBuilder
                .RelatieGroepParameters().landGebiedAanvangCode("6030").gemeenteAanvangCode("0521")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        controleerRegels(actieElement.valideerSpecifiekeInhoud());
    }


    @Bedrijfsregel(Regel.R2045)
    @Test
    public void testGemeenteEindeNietGevuld_LandgebiedEindeIsNederland() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("huwelijk", "1", builder.maakRelatieGroepElement("relatie", new ElementBuilder
                .RelatieGroepParameters().landGebiedAanvangCode("6030").gemeenteAanvangCode("0521").redenEindeCode('Z').datumEinde(2016_01_01).landGebiedEindeCode("6030")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);

        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()).setDatumEinde(20000101);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieElement.getObjectSleutel())).thenReturn(relatieEntiteit);

        controleerRegels(actieElement.valideerSpecifiekeInhoud(), Regel.R2045);
    }

    @Bedrijfsregel(Regel.R2045)
    @Test
    public void testGemeenteEindeNietGevuld_LandgebiedEindeIsNietNederland() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("huwelijk", "1", builder.maakRelatieGroepElement("relatie", new ElementBuilder
                .RelatieGroepParameters().landGebiedAanvangCode("6030").gemeenteAanvangCode("0521").redenEindeCode('Z').datumEinde(2016_01_01).landGebiedEindeCode("5034")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);

        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()).setDatumEinde(20000101);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieElement.getObjectSleutel())).thenReturn(relatieEntiteit);

        controleerRegels(actieElement.valideerSpecifiekeInhoud());
    }

    @Bedrijfsregel(Regel.R2045)
    @Test
    public void testGemeenteEindeGevuld_LandgebiedEindeIsNederland() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("huwelijk", "1", builder.maakRelatieGroepElement("relatie", new ElementBuilder
                .RelatieGroepParameters().landGebiedAanvangCode("6030").gemeenteAanvangCode("0521").redenEindeCode('Z').datumEinde(2016_01_01).landGebiedEindeCode("6030").gemeenteEindeCode("0521")), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieRegistratieRelatieActieElement
                actieElement =
                new CorrectieRegistratieRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);

        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()).setDatumEinde(20000101);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieElement.getObjectSleutel())).thenReturn(relatieEntiteit);

        controleerRegels(actieElement.valideerSpecifiekeInhoud());
    }


    private static BijhoudingRelatie maakRelatieEntiteit() {
        final Timestamp moment1 = new Timestamp(System.currentTimeMillis() - 1_000);
        final Relatie result = new Relatie(SoortRelatie.HUWELIJK);
        result.setId(1L);
        final RelatieHistorie historie1 = new RelatieHistorie(result);
        historie1.setId(1L);
        historie1.setDatumAanvang(2010_01_01);
        historie1.setDatumTijdRegistratie(moment1);
        result.addRelatieHistorie(historie1);
        return BijhoudingRelatie.decorate(result);
    }

}
