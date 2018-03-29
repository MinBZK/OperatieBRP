/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Rechtsgrond;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Testen voor {@link AbstractVervalHuwelijkOfGpActieElement} en sub-types van deze class.
 */
public class VervalHuwelijkOfGpActieElementTest extends AbstractElementTest {

    private Map<String, String> actieAttributen;
    private DatumElement datumAanvangGeldigheid;
    private ElementBuilder builder;

    @Before
    public void setup() {
        actieAttributen = new AbstractBmrGroep.AttributenBuilder().communicatieId("CI_actie").objecttype("Actie").build();
        datumAanvangGeldigheid = new DatumElement(20170330);
        builder = new ElementBuilder();
    }

    @Test
    public void testGetSoortActie() {
        final HuwelijkElement huwelijkElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final VervalHuwelijkActieElement
                actieElement =
                new VervalHuwelijkActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, huwelijkElement);
        assertEquals(SoortActie.VERVAL_HUWELIJK, actieElement.getSoortActie());
    }

    @Test
    public void getHoofdPersonen() {
        final HuwelijkElement huwelijkElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", "1", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, huwelijkElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        huwelijkElement.setVerzoekBericht(bericht);
        final VervalHuwelijkActieElement
                actieElement =
                new VervalHuwelijkActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, huwelijkElement);
        assertEquals(0, actieElement.getHoofdPersonen().size());
    }

    @Test
    public void getPersoonElementen() {
        final HuwelijkElement huwelijkElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final VervalHuwelijkActieElement
                actieElement =
                new VervalHuwelijkActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, huwelijkElement);
        assertTrue(actieElement.getPersoonElementen().isEmpty());
    }

    @Test
    public void testRegel2432() {
        final String rechtsgrondCode = "123";
        final Rechtsgrond rechtsgrond = new Rechtsgrond(rechtsgrondCode, "Omschrijving");
        rechtsgrond.setIndicatieLeidtTotStrijdigheid(false);
        when(getDynamischeStamtabelRepository().getRechtsgrondByCode(rechtsgrondCode)).thenReturn(rechtsgrond);
        final HuwelijkElement huwelijkElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final DocumentElement
                documentElement =
                new DocumentElement(new AbstractBmrGroep.AttributenBuilder().communicatieId("CI_document_1").objecttype("Document").build(),
                        new StringElement("soort"), null, null,
                        new StringElement("123456"));
        final BronElement
                bronElement =
                new BronElement(new AbstractBmrGroep.AttributenBuilder().communicatieId("CI_bron_1").objecttype("Bron").build(), documentElement,
                        new StringElement(rechtsgrondCode));
        final BronReferentieElement
                bronReferentieElement =
                new BronReferentieElement(
                        new AbstractBmrGroep.AttributenBuilder().communicatieId("CI_bronreferentie_1").objecttype("BronReferentie")
                                .referentieId(bronElement.getCommunicatieId()).build());
        final Map<String, BmrGroep> communicatieIdGroepMap = new HashMap<>();
        communicatieIdGroepMap.put(bronElement.getCommunicatieId(), bronElement);
        bronReferentieElement.initialiseer(communicatieIdGroepMap);
        final VervalHuwelijkActieElement
                actieElement =
                new VervalHuwelijkActieElement(actieAttributen, datumAanvangGeldigheid, null, Collections.singletonList(bronReferentieElement), new CharacterElement('S'), huwelijkElement);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, huwelijkElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        actieElement.getRelatieElement().setVerzoekBericht(bericht);
        //execute
        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        //validate
        controleerRegels(meldingen, Regel.R2432);
    }

    @Test
    public void getPeilDatum() {
        final DatumElement vandaag = new DatumElement(DatumUtil.vandaag());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        when(bericht.getDatumOntvangst()).thenReturn(vandaag);
        final HuwelijkElement huwelijkElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final VervalHuwelijkActieElement
                actieElement =
                new VervalHuwelijkActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, huwelijkElement);
        actieElement.setVerzoekBericht(bericht);
        assertEquals(vandaag, actieElement.getPeilDatum());
    }

    @Test
    @Bedrijfsregel(Regel.R2465)
    public void testIstGegevens() {
        final HuwelijkElement huwelijkElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", "1", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();

        final BijhoudingPersoon ingeschrevenPersoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        ingeschrevenPersoon.setId(1L);
        ingeschrevenPersoon.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        final PersoonIDHistorie idHistorieIngeschrevenPersoon = new PersoonIDHistorie(ingeschrevenPersoon.getDelegates().iterator().next());
        idHistorieIngeschrevenPersoon.setId(2L);
        idHistorieIngeschrevenPersoon.setBurgerservicenummer("987654321");
        idHistorieIngeschrevenPersoon.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));
        ingeschrevenPersoon.addPersoonIDHistorie(idHistorieIngeschrevenPersoon);

        final Betrokkenheid betrokkenheidIngeschrevenPersoon = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatieEntiteit.getDelegate());

        ingeschrevenPersoon.addBetrokkenheid(betrokkenheidIngeschrevenPersoon);
        relatieEntiteit.addBetrokkenheid(betrokkenheidIngeschrevenPersoon);

        final Stapel stapel = new Stapel(ingeschrevenPersoon.getDelegates().iterator().next(), "05", 0);
        ingeschrevenPersoon.addStapel(stapel);
        relatieEntiteit.addStapel(stapel);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, huwelijkElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        when(bericht.getEntiteitVoorId(BijhoudingPersoon.class, ingeschrevenPersoon.getId())).thenReturn(ingeschrevenPersoon);
        huwelijkElement.setVerzoekBericht(bericht);
        final VervalHuwelijkActieElement
                actieElement =
                new VervalHuwelijkActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, huwelijkElement);
        actieElement.setVerzoekBericht(bericht);
        assertFalse(ingeschrevenPersoon.getStapels().isEmpty());
        assertFalse(relatieEntiteit.getStapels().isEmpty());
        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        controleerRegels(meldingen, Regel.R2465);
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("test", "000000"), SoortAdministratieveHandeling.ONGEDAANMAKING_HUWELIJK,
                        new Timestamp(System.currentTimeMillis()));
        //execute verwerk
        actieElement.verwerk(bericht, administratieveHandeling);
        assertTrue(ingeschrevenPersoon.getStapels().isEmpty());
        assertTrue(relatieEntiteit.getStapels().isEmpty());
    }

    @Test
    public void bepaalTeVervallenVoorkomens() {
        final HuwelijkElement huwelijkElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", "1", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        final BijhoudingPersoon pseudoPersoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.PSEUDO_PERSOON));
        pseudoPersoon.setId(1L);
        final PersoonIDHistorie idHistoriePseudoPersoon = new PersoonIDHistorie(pseudoPersoon.getDelegates().iterator().next());
        idHistoriePseudoPersoon.setId(1L);
        idHistoriePseudoPersoon.setBurgerservicenummer("123456789");
        idHistoriePseudoPersoon.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));
        pseudoPersoon.addPersoonIDHistorie(idHistoriePseudoPersoon);

        final BijhoudingPersoon ingeschrevenPersoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        ingeschrevenPersoon.setId(2L);
        ingeschrevenPersoon.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        final PersoonIDHistorie idHistorieIngeschrevenPersoon = new PersoonIDHistorie(ingeschrevenPersoon.getDelegates().iterator().next());
        idHistorieIngeschrevenPersoon.setId(2L);
        idHistorieIngeschrevenPersoon.setBurgerservicenummer("987654321");
        idHistorieIngeschrevenPersoon.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));
        ingeschrevenPersoon.addPersoonIDHistorie(idHistorieIngeschrevenPersoon);

        final Betrokkenheid betrokkenheidPseudoPersoon = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatieEntiteit.getDelegate());
        final Betrokkenheid betrokkenheidIngeschrevenPersoon = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatieEntiteit.getDelegate());

        pseudoPersoon.addBetrokkenheid(betrokkenheidPseudoPersoon);
        ingeschrevenPersoon.addBetrokkenheid(betrokkenheidIngeschrevenPersoon);
        relatieEntiteit.addBetrokkenheid(betrokkenheidPseudoPersoon);
        relatieEntiteit.addBetrokkenheid(betrokkenheidIngeschrevenPersoon);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, huwelijkElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        when(bericht.getEntiteitVoorId(BijhoudingPersoon.class, pseudoPersoon.getId())).thenReturn(pseudoPersoon);
        when(bericht.getEntiteitVoorId(BijhoudingPersoon.class, ingeschrevenPersoon.getId())).thenReturn(ingeschrevenPersoon);
        huwelijkElement.setVerzoekBericht(bericht);
        final VervalHuwelijkActieElement
                actieElement =
                new VervalHuwelijkActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, huwelijkElement);
        actieElement.setVerzoekBericht(bericht);
        //verify setup
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()));
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(pseudoPersoon.getPersoonIDHistorieSet()));
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(ingeschrevenPersoon.getPersoonIDHistorieSet()));
        //excute bepaalTeVervallenVoorkomen
        final List<FormeleHistorie> teVervallenVoorkomens = new ArrayList<>(actieElement.bepaalTeVervallenVoorkomens());
        //verify
        assertEquals(2, teVervallenVoorkomens.size());
        final FormeleHistorie voorkomen1 = teVervallenVoorkomens.get(0);
        final FormeleHistorie voorkomen2 = teVervallenVoorkomens.get(1);

        assertTrue((voorkomen1.getId().intValue() == 1 && voorkomen1 instanceof PersoonIDHistorie) || (voorkomen1.getId().intValue() == 2
                && voorkomen1 instanceof RelatieHistorie));
        assertTrue((voorkomen2.getId().intValue() == 1 && voorkomen2 instanceof PersoonIDHistorie) || (voorkomen2.getId().intValue() == 2
                && voorkomen2 instanceof RelatieHistorie));
        assertFalse(voorkomen1.isVervallen());
        assertFalse(voorkomen2.isVervallen());
        //set verwerk
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("test", "000000"), SoortAdministratieveHandeling.ONGEDAANMAKING_HUWELIJK,
                        new Timestamp(System.currentTimeMillis()));
        //execute verwerk
        final BRPActie nieuweActie = actieElement.verwerk(bericht, administratieveHandeling);
        assertNotNull(nieuweActie);
        assertEquals(SoortActie.VERVAL_HUWELIJK, nieuweActie.getSoortActie());
        assertTrue(voorkomen1.isVervallen());
        assertTrue(voorkomen2.isVervallen());
    }

    @Test
    public void testNietAlleHoofdPersonenVerwerkbaar() {
        final HuwelijkElement huwelijkElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", "1", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, huwelijkElement.getObjectSleutel())).thenReturn(relatieEntiteit);
        huwelijkElement.setVerzoekBericht(bericht);
        final VervalHuwelijkActieElement
                actieElement =
                new VervalHuwelijkActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, huwelijkElement);
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("test", "000000"), SoortAdministratieveHandeling.CORRECTIE_HUWELIJK,
                        new Timestamp(System.currentTimeMillis()));
        //execute & verify
        assertNull(actieElement.verwerk(bericht, administratieveHandeling));
    }

    @Test
    public void getNadereAanduidingVerval() {
        final HuwelijkElement huwelijkElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final VervalHuwelijkActieElement
                actieElement =
                new VervalHuwelijkActieElement(actieAttributen, datumAanvangGeldigheid, null, null, new CharacterElement('O'), huwelijkElement);
        assertEquals(Character.valueOf('O'), BmrAttribuut.getWaardeOfNull(actieElement.getNadereAanduidingVerval()));
    }

    private static BijhoudingRelatie maakRelatieEntiteit() {
        final Timestamp moment1 = new Timestamp(System.currentTimeMillis());
        final Timestamp moment2 = new Timestamp(moment1.getTime() + 1_000);
        final Relatie result = new Relatie(SoortRelatie.HUWELIJK);
        result.setId(1L);
        final RelatieHistorie historie1 = new RelatieHistorie(result);
        historie1.setId(1L);
        historie1.setDatumAanvang(20100101);
        historie1.setDatumTijdRegistratie(moment1);
        historie1.setDatumTijdVerval(moment2);
        final RelatieHistorie historie2 = new RelatieHistorie(result);
        historie2.setId(2L);
        historie2.setDatumAanvang(20110202);
        historie2.setDatumTijdRegistratie(moment2);
        result.addRelatieHistorie(historie1);
        result.addRelatieHistorie(historie2);
        return BijhoudingRelatie.decorate(result);
    }
}
