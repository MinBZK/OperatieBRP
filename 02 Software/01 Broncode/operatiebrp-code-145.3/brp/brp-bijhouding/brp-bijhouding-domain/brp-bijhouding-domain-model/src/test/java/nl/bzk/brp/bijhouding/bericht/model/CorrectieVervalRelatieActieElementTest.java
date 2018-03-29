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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
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
 * Testen voor {@link CorrectieVervalRelatieActieElement}.
 */
public class CorrectieVervalRelatieActieElementTest extends AbstractElementTest {

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
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", "1", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final CorrectieVervalRelatieActieElement
                actieElement =
                new CorrectieVervalRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, relatieElement);
        assertEquals(SoortActie.CORRECTIEVERVAL_RELATIE, actieElement.getSoortActie());
    }

    @Test
    public void testGetHoofdPersonen() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", "1", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit(20100101, 20110202);
        when(bericht.getEntiteitVoorObjectSleutel(any(), any())).thenReturn(relatieEntiteit);
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieVervalRelatieActieElement
                actieElement =
                new CorrectieVervalRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, relatieElement);
        assertEquals(0, actieElement.getHoofdPersonen().size());
    }

    @Test
    public void testGetPersoonElementen() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", "1", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final CorrectieVervalRelatieActieElement
                actieElement =
                new CorrectieVervalRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, relatieElement);
        assertTrue(actieElement.getPersoonElementen().isEmpty());
    }

    @Test
    public void testGetPeilDatum() {
        final DatumElement vandaag = new DatumElement(DatumUtil.vandaag());
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        when(bericht.getDatumOntvangst()).thenReturn(vandaag);
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", "1", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final CorrectieVervalRelatieActieElement
                actieElement =
                new CorrectieVervalRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        assertEquals(vandaag, actieElement.getPeilDatum());
    }

    @Test
    public void testBepaalTeVervallenVoorkomen() {
        final String geldigVoorkomen = "2";
        final RelatieHistorie teVervallenVoorkomen = maakActieElement(geldigVoorkomen).bepaalTeVervallenVoorkomen();
        assertNotNull(teVervallenVoorkomen);
        assertEquals(2, teVervallenVoorkomen.getId().intValue());
    }

    @Test
    public void testBepaalTeVervallenVoorkomenNull() {
        final String ongeldigVoorkomen = "3";
        final RelatieHistorie teVervallenVoorkomen = maakActieElement(ongeldigVoorkomen).bepaalTeVervallenVoorkomen();
        assertNull(teVervallenVoorkomen);
    }

    @Test
    public void testBepaalTeVervallenVoorkomenOngeldigeSleutel() {
        final String ongeldigVoorkomen = "a";
        final RelatieHistorie teVervallenVoorkomen = maakActieElement(ongeldigVoorkomen).bepaalTeVervallenVoorkomen();
        assertNull(teVervallenVoorkomen);
    }

    @Test
    public void testValideerRegel1845() {
        final String ongeldigVoorkomen = "3";
        final CorrectieVervalRelatieActieElement actieElement = maakActieElement(ongeldigVoorkomen);
        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1845, meldingen.get(0).getRegel());
    }

    @Test
    public void testValideerRegel2334() {
        final String vervallenVoorkomen = "1";
        final CorrectieVervalRelatieActieElement actieElement = maakActieElement(vervallenVoorkomen);
        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        controleerRegels(meldingen, Regel.R2334);
    }

    @Test
    public void testValideerRegel2432Goed() {
        final String rechtsgrondCode = "123";
        final String geldigVoorkomen = "2";
        final Rechtsgrond rechtsgrond = new Rechtsgrond(rechtsgrondCode, "Omschrijving");
        rechtsgrond.setIndicatieLeidtTotStrijdigheid(true);
        when(getDynamischeStamtabelRepository().getRechtsgrondByCode(rechtsgrondCode)).thenReturn(rechtsgrond);
        final CorrectieVervalRelatieActieElement templateElement = maakActieElement(geldigVoorkomen);
        final CorrectieVervalRelatieActieElement
                actieElementFout =
                new CorrectieVervalRelatieActieElement(templateElement.getAttributen(), templateElement.getDatumAanvangGeldigheid(), null,
                        templateElement.getBronReferenties(), new CharacterElement('S'), templateElement.getRelatie());
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
        final CorrectieVervalRelatieActieElement
                actieElementGoed =
                new CorrectieVervalRelatieActieElement(templateElement.getAttributen(), templateElement.getDatumAanvangGeldigheid(), null,
                        Collections.singletonList(bronReferentieElement), new CharacterElement('S'), templateElement.getRelatie());
        final List<MeldingElement> meldingenFout = actieElementFout.valideerSpecifiekeInhoud();
        controleerRegels(meldingenFout, Regel.R2432);

        final List<MeldingElement> meldingenGoed = actieElementGoed.valideerSpecifiekeInhoud();
        controleerRegels(meldingenGoed);
        rechtsgrond.setIndicatieLeidtTotStrijdigheid(null);
        final List<MeldingElement> meldingenFout2 = actieElementGoed.valideerSpecifiekeInhoud();
        controleerRegels(meldingenFout2, Regel.R2432);
    }

    @Test
    public void testGetIstIngang() {
        final CorrectieVervalRelatieActieElement actie = maakActieElement("3");
        assertEquals(actie.getRelatie(), actie.getIstIngang());
    }

    @Test
    @Bedrijfsregel(Regel.R2465)
    public void testValideerRegel2465EnVerwijderIst() {
        final String geldigVoorkomen = "2";
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.setId(1L);
        persoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit(20100101, 20110202);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatieEntiteit.getDelegate());
        relatieEntiteit.addBetrokkenheid(betrokkenheid);
        persoon.addBetrokkenheid(betrokkenheid);
        final Stapel stapel = new Stapel(persoon.getDelegates().iterator().next(), "05", 0);
        relatieEntiteit.addStapel(stapel);
        persoon.addStapel(stapel);
        final String objectSleutel = "" + relatieEntiteit.getId();
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        when(bericht.getEntiteitVoorId(BijhoudingPersoon.class, persoon.getId())).thenReturn(persoon);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, objectSleutel)).thenReturn(relatieEntiteit);
        actieAttributen = new AbstractBmrGroep.AttributenBuilder().communicatieId("CI_actie").objecttype("Actie").objectSleutel(objectSleutel).build();
        final RelatieElement
                relatieElement =
                builder.maakHuwelijkElement("CI_huwelijk", objectSleutel,
                        builder.maakRelatieGroepElement("CI_relatie", geldigVoorkomen, new ElementBuilder
                                .RelatieGroepParameters()), Collections.emptyList());
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieVervalRelatieActieElement
                actieElement =
                new CorrectieVervalRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2465, meldingen.get(0).getRegel());
        assertFalse(persoon.getStapels().isEmpty());
        assertFalse(relatieEntiteit.getStapels().isEmpty());
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("test", "000000"), SoortAdministratieveHandeling.CORRECTIE_HUWELIJK,
                        new Timestamp(System.currentTimeMillis()));
        //execute
        actieElement.verwerk(bericht, administratieveHandeling);
        assertTrue(persoon.getStapels().isEmpty());
        assertTrue(relatieEntiteit.getStapels().isEmpty());
    }

    @Test
    public void testVerwerk() {
        final String geldigVoorkomen = "2";
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.setId(1L);
        persoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit(20100101, 20110202);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatieEntiteit.getDelegate());
        relatieEntiteit.addBetrokkenheid(betrokkenheid);
        persoon.addBetrokkenheid(betrokkenheid);
        final String objectSleutel = "" + relatieEntiteit.getId();
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        when(bericht.getEntiteitVoorId(BijhoudingPersoon.class, persoon.getId())).thenReturn(persoon);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, objectSleutel)).thenReturn(relatieEntiteit);
        actieAttributen = new AbstractBmrGroep.AttributenBuilder().communicatieId("CI_actie").objecttype("Actie").objectSleutel(objectSleutel).build();
        final RelatieElement
                relatieElement =
                builder.maakHuwelijkElement("CI_huwelijk", objectSleutel,
                        builder.maakRelatieGroepElement("CI_relatie", geldigVoorkomen, new ElementBuilder
                                .RelatieGroepParameters()), Collections.emptyList());
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieVervalRelatieActieElement
                actieElement =
                new CorrectieVervalRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
        assertNotNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()));
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("test", "000000"), SoortAdministratieveHandeling.CORRECTIE_HUWELIJK,
                        new Timestamp(System.currentTimeMillis()));
        //execute
        actieElement.verwerk(bericht, administratieveHandeling);
        assertNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet()));
    }

    private CorrectieVervalRelatieActieElement maakActieElement(final String voorkomenSleutel) {
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit(20100101, 20110202);
        final String objectSleutel = "" + relatieEntiteit.getId();
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, objectSleutel)).thenReturn(relatieEntiteit);
        actieAttributen = new AbstractBmrGroep.AttributenBuilder().communicatieId("CI_actie").objecttype("Actie").objectSleutel(objectSleutel).build();
        final RelatieElement
                relatieElement =
                builder.maakHuwelijkElement("CI_huwelijk", objectSleutel,
                        builder.maakRelatieGroepElement("CI_relatie", voorkomenSleutel, new ElementBuilder
                                .RelatieGroepParameters()), Collections.emptyList());
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieVervalRelatieActieElement
                actieElement =
                new CorrectieVervalRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        return actieElement;
    }

    @Test
    public void getGetOngeldigAangewezenObjectOfVoorkomen() {
        final BijhoudingRelatie relatieEntiteit = maakRelatieEntiteit(20100101, 20110202);
        final String objectSleutel = "" + relatieEntiteit.getId();
        final String ongeldigeVoorkomenSleutel = "3";
        final BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, objectSleutel)).thenReturn(relatieEntiteit);
        actieAttributen = new AbstractBmrGroep.AttributenBuilder().communicatieId("CI_actie").objecttype("Actie").objectSleutel(objectSleutel).build();
        final RelatieElement
                relatieElement =
                builder.maakHuwelijkElement("CI_huwelijk", objectSleutel,
                        builder.maakRelatieGroepElement("CI_relatie", ongeldigeVoorkomenSleutel, new ElementBuilder
                                .RelatieGroepParameters()), Collections.emptyList());
        relatieElement.setVerzoekBericht(bericht);
        final CorrectieVervalRelatieActieElement
                actieElement =
                new CorrectieVervalRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, relatieElement);
        actieElement.setVerzoekBericht(bericht);
        assertSame(relatieElement.getRelatieGroep(), actieElement.getOngeldigAangewezenObjectOfVoorkomen());
    }

    private static BijhoudingRelatie maakRelatieEntiteit(final int datumAanvang1, final int datumAanvang2) {
        final Timestamp moment1 = new Timestamp(System.currentTimeMillis());
        final Timestamp moment2 = new Timestamp(moment1.getTime() + 1_000);
        final Relatie result = new Relatie(SoortRelatie.HUWELIJK);
        result.setId(1L);
        final RelatieHistorie historie1 = new RelatieHistorie(result);
        historie1.setId(1L);
        historie1.setDatumAanvang(datumAanvang1);
        historie1.setDatumTijdRegistratie(moment1);
        historie1.setDatumTijdVerval(moment2);
        final RelatieHistorie historie2 = new RelatieHistorie(result);
        historie2.setId(2L);
        historie2.setDatumAanvang(datumAanvang2);
        historie2.setDatumTijdRegistratie(moment2);
        result.addRelatieHistorie(historie1);
        result.addRelatieHistorie(historie2);
        return BijhoudingRelatie.decorate(result);
    }

    @Test
    public void testGetNadereAanduidingVerval() {
        final CharacterElement nadereAanduidingVerval = new CharacterElement('O');
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", "1", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final CorrectieVervalRelatieActieElement
                actieElement =
                new CorrectieVervalRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, nadereAanduidingVerval, relatieElement);
        assertEquals(nadereAanduidingVerval, actieElement.getNadereAanduidingVerval());
    }

    @Test
    public void testGetNadereAanduidingVervalNull() {
        final RelatieElement relatieElement = builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_relatie", "1", new ElementBuilder
                .RelatieGroepParameters()), Collections.emptyList());
        final CorrectieVervalRelatieActieElement
                actieElement =
                new CorrectieVervalRelatieActieElement(actieAttributen, datumAanvangGeldigheid, null, null, null, relatieElement);
        assertNull(actieElement.getNadereAanduidingVerval());
    }

}
