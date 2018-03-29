/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegistratieGeslachtsnaamVoornaamActieElementTest extends AbstractElementTest {

    private ElementBuilder builder;
    private AdministratieveHandeling ah = mock(AdministratieveHandeling.class);
    private BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
    private final Timestamp timestamp = new Timestamp(DatumUtil.nu().getTime());
    private BijhoudingPersoon bijhoudingPersoon;

    @Before
    public void setUp() {
        builder = new ElementBuilder();
        final Persoon delegate = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonGeslachtsnaamcomponent persGeslachtnaam = new PersoonGeslachtsnaamcomponent(delegate, 1);
        persGeslachtnaam.setStam("Stam");
        final PersoonGeslachtsnaamcomponentHistorie gHistorie = new PersoonGeslachtsnaamcomponentHistorie(persGeslachtnaam, "Stam");
        persGeslachtnaam.addPersoonGeslachtsnaamcomponentHistorie(gHistorie);
        delegate.getPersoonGeslachtsnaamcomponentSet().add(persGeslachtnaam);
        voegVoornaamToe(delegate, 1, "Hugo");
        voegVoornaamToe(delegate, 2, "Claus");
        bijhoudingPersoon = new BijhoudingPersoon(delegate);
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(bijhoudingPersoon);
        when(ah.getDatumTijdRegistratie()).thenReturn(timestamp);
        when(ah.getPartij()).thenReturn(new Partij("partij", "100011"));
        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode("0016")).thenReturn(new Nationaliteit("0016", "0016"));
    }

    private void voegVoornaamToe(final Persoon delegate, final int volgnummer, final String voornaam) {
        final PersoonVoornaam persoonVoornaam = new PersoonVoornaam(delegate, volgnummer);
        final PersoonVoornaamHistorie voornaamHistorie = new PersoonVoornaamHistorie(persoonVoornaam, voornaam);
        persoonVoornaam.addPersoonVoornaamHistorie(voornaamHistorie);
        delegate.getPersoonVoornaamSet().add(persoonVoornaam);
    }

    @Test
    public void testActieHeeftInvloedOpGerelateerden() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(false, false, 2012_01_01, false, null);
        assertTrue(actie.heeftInvloedOpGerelateerden());
    }

    @Bedrijfsregel(Regel.R2746)
    @Test
    public void testDagGeslachtsnaamComponentDagHistorieNaDagActie() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(false, false, false, 2012_01_01, true, "1234");
        RegistratieGeboreneActieElement geboreneActieElement = maakActieGeboreneMetAlleenOuwkig(2015_01_01, builder);
        final BijhoudingPersoon mockPersoon = mock(BijhoudingPersoon.class);
        when(mockPersoon.getActueleIndicatieNamenreeks()).thenReturn(true);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(mockPersoon);
        controleerRegels(koppelActiesAanAhEnValideerTestActie(geboreneActieElement, actie), Regel.R2746, Regel.R2748);
    }

    @Bedrijfsregel(Regel.R2747)
    @Test
    public void testDagVoornaamHistorieNaDagActie() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(false, false, true, 2012_01_01, true, "1234");
        final BijhoudingPersoon mockPersoon = mock(BijhoudingPersoon.class);
        PersoonVoornaamHistorie historie1 = new PersoonVoornaamHistorie(mock(PersoonVoornaamHistorie.class));
        historie1.setDatumTijdRegistratie(new Timestamp(DatumUtil.nu().getTime()));
        historie1.setDatumAanvangGeldigheid(20110201);
        PersoonVoornaamHistorie historie2 = new PersoonVoornaamHistorie(mock(PersoonVoornaamHistorie.class));
        historie2.setDatumTijdRegistratie(new Timestamp(DatumUtil.nu().getTime()));
        historie2.setDatumAanvangGeldigheid(20120201);
        List<PersoonVoornaamHistorie> actueleVoornamen = new LinkedList<>();
        actueleVoornamen.add(historie1);
        actueleVoornamen.add(historie2);
        when(mockPersoon.getActueleVoornamen(DatumUtil.vandaag())).thenReturn(actueleVoornamen);
        when(mockPersoon.getActueleIndicatieNamenreeks()).thenReturn(true);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(mockPersoon);
        controleerRegels(koppelActiesAanAhEnValideerTestActie(actie),Regel.R2747);
    }

    @Bedrijfsregel(Regel.R2747)
    @Test
    public void testDagVoornaamHistorieVoorDagActie() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(false, false, true, 2012_01_01, true, "1234");
        final BijhoudingPersoon mockPersoon = mock(BijhoudingPersoon.class);
        PersoonVoornaamHistorie historie1 = new PersoonVoornaamHistorie(mock(PersoonVoornaamHistorie.class));
        historie1.setDatumTijdRegistratie(new Timestamp(DatumUtil.nu().getTime()));
        historie1.setDatumAanvangGeldigheid(20110201);
        PersoonVoornaamHistorie historie2 = new PersoonVoornaamHistorie(mock(PersoonVoornaamHistorie.class));
        historie2.setDatumTijdRegistratie(new Timestamp(DatumUtil.nu().getTime()));
        historie2.setDatumAanvangGeldigheid(20110201);
        List<PersoonVoornaamHistorie> actueleVoornamen = new LinkedList<>();
        actueleVoornamen.add(historie1);
        actueleVoornamen.add(historie2);
        when(mockPersoon.getActueleVoornamen(DatumUtil.vandaag())).thenReturn(actueleVoornamen);
        when(mockPersoon.getActueleIndicatieNamenreeks()).thenReturn(true);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(mockPersoon);
        controleerRegels(koppelActiesAanAhEnValideerTestActie(actie));
    }

    @Bedrijfsregel(Regel.R2747)
    @Test
    public void testDagVoornaamMetSamengesteldeNaamHistorieMetDagNaPeildatum() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(null, false, true, 2010_01_01, true, "1234");
        final BijhoudingPersoon mockPersoon = mock(BijhoudingPersoon.class);
        List<PersoonVoornaamHistorie> actueleVoornamen = new LinkedList<>();
        when(mockPersoon.getActueleVoornamen(DatumUtil.vandaag())).thenReturn(actueleVoornamen);
        final PersoonSamengesteldeNaamHistorie actueleSamengesteldenaam = new PersoonSamengesteldeNaamHistorie(mockPersoon,"stam",false,false);
        actueleSamengesteldenaam.setDatumAanvangGeldigheid(20120201);
        when(mockPersoon.getActuelePersoonSamengesteldeNaamHistorie()).thenReturn(actueleSamengesteldenaam);
        when(mockPersoon.getActueleIndicatieNamenreeks()).thenReturn(true);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(mockPersoon);
        controleerRegels(koppelActiesAanAhEnValideerTestActie(AdministratieveHandelingElementSoort.ERKENNING, actie), Regel.R2747);
    }

    @Bedrijfsregel(Regel.R2747)
    @Test
    public void testDagGeenVoornaamHistorie() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(false, false, true, 2015_01_01, true, "1234");
        final BijhoudingPersoon mockPersoon = mock(BijhoudingPersoon.class);
        List<PersoonVoornaamHistorie> actueleVoornamen = new LinkedList<>();
        when(mockPersoon.getActueleVoornamen(DatumUtil.vandaag())).thenReturn(actueleVoornamen);
        when(mockPersoon.getActueleIndicatieNamenreeks()).thenReturn(true);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(mockPersoon);
        controleerRegels(koppelActiesAanAhEnValideerTestActie(actie));
    }

    @Bedrijfsregel(Regel.R2748)
    @Test
    public void testDagSamengesteldeNaamHistorieMetDagNaPeildatum() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(false, false, false, 2010_01_01, true, "1234");
        final BijhoudingPersoon mockPersoon = mock(BijhoudingPersoon.class);
        List<PersoonVoornaamHistorie> actueleVoornamen = new LinkedList<>();
        when(mockPersoon.getActueleVoornamen(DatumUtil.vandaag())).thenReturn(actueleVoornamen);
        final PersoonSamengesteldeNaamHistorie actueleSamengesteldenaam = new PersoonSamengesteldeNaamHistorie(mockPersoon, "stam", false, false);
        actueleSamengesteldenaam.setDatumAanvangGeldigheid(20120201);
        when(mockPersoon.getActuelePersoonSamengesteldeNaamHistorie()).thenReturn(actueleSamengesteldenaam);
        when(mockPersoon.getActueleIndicatieNamenreeks()).thenReturn(true);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(mockPersoon);
        controleerRegels(koppelActiesAanAhEnValideerTestActie(AdministratieveHandelingElementSoort.ERKENNING, actie), Regel.R2748);
    }

    @Bedrijfsregel(Regel.R2748)
    @Test
    public void testDagSamengesteldeNaamHistorieMetDagNaPeildatumEersteInschrijving() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(false, false, false, 2010_01_01, true, "1234");
        RegistratieGeboreneActieElement geboreneActieElement = maakActieGeboreneMetAlleenOuwkig(2015_01_01, builder);
        final BijhoudingPersoon mockPersoon = mock(BijhoudingPersoon.class);
        when(mockPersoon.getActueleIndicatieNamenreeks()).thenReturn(true);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(mockPersoon);
        controleerRegels(koppelActiesAanAhEnValideerTestActie(geboreneActieElement, actie), Regel.R2746, Regel.R2748);
    }

    @Bedrijfsregel(Regel.R2746)
    @Test
    public void testDagGeslachtsnaamComponentDagHistorieVoorDagActie() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakStandaardActie(2012_01_01, true, "1234");
        final BijhoudingPersoon mockPersoon = mock(BijhoudingPersoon.class);
        final PersoonGeslachtsnaamcomponentHistorie historie = new PersoonGeslachtsnaamcomponentHistorie(new PersoonGeslachtsnaamcomponent(mockPersoon,1),"stam");
        historie.setDatumTijdRegistratie(new Timestamp(DatumUtil.nu().getTime()));
        historie.setDatumAanvangGeldigheid(20111231);
        when(mockPersoon.getActueleGeslachtsnaamComponent(DatumUtil.vandaag())).thenReturn(historie);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(mockPersoon);
        controleerRegels(koppelActiesAanAhEnValideerTestActie(AdministratieveHandelingElementSoort.ERKENNING, actie), Regel.R2471);
    }

    @Bedrijfsregel(Regel.R2471)
    @Test
    public void testVoornaamAanwezigBijWijzigingIndicatie_vanJaNaarNee() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakStandaardActie(2012_01_01, false, null);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerTestActie(actie);
        assertTrue(meldingen.isEmpty());
    }

    @Bedrijfsregel(Regel.R2471)
    @Test
    public void testVoornaamAanwezigBijWijzigingIndicatie_blijftNee() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(false, false, 2012_01_01, false, null);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerTestActie(actie);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2471, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2471)
    @Test
    public void testVoornaamAanwezigBijWijzigingIndicatie_vanNeeNaarJa() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(false, true, 2012_01_01, false, null);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerTestActie(actie);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2471, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2471)
    @Test
    public void testVoornaamAanwezigBijWijzigingIndicatie_blijftJa() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(true, true, 2012_01_01, false, null);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerTestActie(actie);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2471, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2471)
    @Test
    public void testVoornaamAfwezigBijWijzigingIndicatie_blijftJa() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(true, true, false, 2012_01_01, false, null);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerTestActie(actie);
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2471)
    @Test
    public void testVoornaamAfwezigBijWijzigingIndicatie_WijzigtVanJaNaarNee() {
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakActie(true, false, false, 2012_01_01, false, null);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerTestActie(actie);
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2471)
    @Test
    public void testVoornaamAanwezigBijWijzigingIndicatie_vanNeeDatabaseNaarJa() {
        final String objectSleutel = "cafe-efac";

        final ElementBuilder.NaamParameters naamParameters = new ElementBuilder.NaamParameters();
        naamParameters.indicatieNamenreeks(true);
        final SamengesteldeNaamElement samengesteldeNaamElement = builder.maakSamengesteldeNaamElement("samengesteldenaam", naamParameters);
        final VoornaamElement voornaamElement = builder.maakVoornaamElement("voornaam", 0, "Piet");

        final ElementBuilder.PersoonParameters parameters = new ElementBuilder.PersoonParameters();
        parameters.samengesteldeNaam(samengesteldeNaamElement);
        parameters.voornamen(Collections.singletonList(voornaamElement));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persoon", objectSleutel, null, parameters);
        persoonElement.setVerzoekBericht(getBericht());
        final RegistratieGeslachtsnaamVoornaamActieElement actie =
                builder.maakRegistratieGeslachtsnaamVoornaamActieElement("commId", 2012_01_01, Collections.emptyList(), persoonElement);

        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(persoon, "Stam", false, false);
        samengesteldeNaamHistorie.setDatumAanvangGeldigheid(20110101);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectSleutel)).thenReturn(persoon);

        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerTestActie(actie);
        controleerRegels(meldingen, Regel.R2471);
    }

    @Bedrijfsregel(Regel.R2475)
    @Test
    public void testDatumAanvangGelijkAanDatumErkenning() {
        final Integer datumErkenning = 2012_01_01;
        final RegistratieOuderActieElement ouderActie = maakRegistratieOuderActie(datumErkenning, null, COMM_ID_KIND);
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakStandaardActie(datumErkenning, false, null);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerTestActie(ouderActie, actie);
        assertTrue(meldingen.isEmpty());
        assertEquals(datumErkenning, actie.getPeilDatum().getWaarde());
    }

    @Bedrijfsregel(Regel.R2475)
    @Test
    public void testDatumAanvangOnGelijkAanDatumErkenning() {
        final RegistratieOuderActieElement ouderActie = maakRegistratieOuderActie(2012_01_01, null, COMM_ID_KIND);
        final RegistratieGeslachtsnaamVoornaamActieElement actie = maakStandaardActie(2015_01_01, false, null);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerTestActie(ouderActie, actie);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2475, meldingen.get(0).getRegel());
    }

    @Test
    public void verwerkingAlleenSamengesteldeNaamElement() {
        final ElementBuilder.PersoonParameters persoonPara = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParameters = new ElementBuilder.NaamParameters();
        naamParameters.indicatieNamenreeks(false);
        persoonPara.samengesteldeNaam(builder.maakSamengesteldeNaamElement("sn", naamParameters));
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("pers", "1234", null, persoonPara);
        final RegistratieGeslachtsnaamVoornaamActieElement
                regactie =
                builder.maakRegistratieGeslachtsnaamVoornaamActieElement("regactie", 20100101, Collections.emptyList(), persoon);
        persoon.setVerzoekBericht(bericht);
        assertEquals(0, bijhoudingPersoon.getDelegates().get(0).getPersoonSamengesteldeNaamHistorieSet().size());
        regactie.verwerk(bericht, ah);
        final Set<PersoonSamengesteldeNaamHistorie>
                persoonSamengesteldeNaamHistorieSet =
                bijhoudingPersoon.getDelegates().get(0).getPersoonSamengesteldeNaamHistorieSet();
        assertEquals(1, persoonSamengesteldeNaamHistorieSet.size());
        assertNotNull("Stam", persoonSamengesteldeNaamHistorieSet.iterator().next().getGeslachtsnaamstam());
    }

    @Test
    public void verwerkingSamengesteldeNaamElementMetIndicatieNamenReeks() {
        final ElementBuilder.PersoonParameters persoonPara = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParameters = new ElementBuilder.NaamParameters();
        naamParameters.indicatieNamenreeks(true);
        persoonPara.samengesteldeNaam(builder.maakSamengesteldeNaamElement("sn", naamParameters));
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("pers", "1234", null, persoonPara);
        final RegistratieGeslachtsnaamVoornaamActieElement
                regactie =
                builder.maakRegistratieGeslachtsnaamVoornaamActieElement("regactie", 20100101, Collections.emptyList(), persoon);
        persoon.setVerzoekBericht(bericht);
        assertEquals(0, bijhoudingPersoon.getDelegates().get(0).getPersoonSamengesteldeNaamHistorieSet().size());
        regactie.verwerk(bericht, ah);
        final Set<PersoonSamengesteldeNaamHistorie>
                persoonSamengesteldeNaamHistorieSet =
                bijhoudingPersoon.getDelegates().get(0).getPersoonSamengesteldeNaamHistorieSet();
        assertEquals(1, persoonSamengesteldeNaamHistorieSet.size());
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = persoonSamengesteldeNaamHistorieSet.iterator().next();
        assertNotNull("Stam", samengesteldeNaamHistorie.getGeslachtsnaamstam());
        final List<PersoonVoornaam> persoonVoornamen = new ArrayList<>(bijhoudingPersoon.getDelegates().get(0).getPersoonVoornaamSet());
        assertEquals(2, persoonVoornamen.size());
        assertNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonVoornamen.get(0).getPersoonVoornaamHistorieSet()));
        assertNull(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonVoornamen.get(1).getPersoonVoornaamHistorieSet()));
        assertNull(samengesteldeNaamHistorie.getVoornamen());

    }

    @Test
    public void verwerkingSamengesteldeNaamElementEnVoornamen() {
        final ElementBuilder.PersoonParameters persoonPara = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParameters = new ElementBuilder.NaamParameters();
        naamParameters.indicatieNamenreeks(false);
        persoonPara.samengesteldeNaam(builder.maakSamengesteldeNaamElement("sn", naamParameters));
        final List<VoornaamElement> voornamen = new LinkedList<>();
        voornamen.add(builder.maakVoornaamElement("v1", 1, "Piet"));
        voornamen.add(builder.maakVoornaamElement("v2", 2, "Jan"));
        persoonPara.voornamen(voornamen);
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("pers", "1234", null, persoonPara);
        final RegistratieGeslachtsnaamVoornaamActieElement
                regactie =
                builder.maakRegistratieGeslachtsnaamVoornaamActieElement("regactie", 20100101, Collections.emptyList(), persoon);
        persoon.setVerzoekBericht(bericht);
        assertEquals(0, bijhoudingPersoon.getDelegates().get(0).getPersoonSamengesteldeNaamHistorieSet().size());
        assertEquals(2, bijhoudingPersoon.getDelegates().get(0).getPersoonVoornaamSet().size());
        regactie.verwerk(bericht, ah);
        final Set<PersoonSamengesteldeNaamHistorie>
                persoonSamengesteldeNaamHistorieSet =
                bijhoudingPersoon.getDelegates().get(0).getPersoonSamengesteldeNaamHistorieSet();
        assertEquals(1, persoonSamengesteldeNaamHistorieSet.size());
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = persoonSamengesteldeNaamHistorieSet.iterator().next();
        assertNotNull("Stam", samengesteldeNaamHistorie.getGeslachtsnaamstam());
        final List<PersoonVoornaam> persoonVoornamen = new ArrayList<>(bijhoudingPersoon.getDelegates().get(0).getPersoonVoornaamSet());
        assertEquals(2, persoonVoornamen.size());
        assertEquals("Piet",
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonVoornamen.get(0).getPersoonVoornaamHistorieSet()).getNaam());
        assertEquals("Jan", FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonVoornamen.get(1).getPersoonVoornaamHistorieSet()).getNaam());
        assertEquals("Piet Jan", samengesteldeNaamHistorie.getVoornamen());
    }


    @Test
    public void verwerkingSamengesteldeNaamElementEnGeslachtsNaamComponent() {
        final ElementBuilder.PersoonParameters persoonPara = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParameters = new ElementBuilder.NaamParameters();
        naamParameters.indicatieNamenreeks(false);
        persoonPara.samengesteldeNaam(builder.maakSamengesteldeNaamElement("sn", naamParameters));
        final List<GeslachtsnaamcomponentElement> gCompLijst = new LinkedList<>();
        gCompLijst.add(builder.maakGeslachtsnaamcomponentElement("g1", null, null, null, null, "Jansen"));
        persoonPara.geslachtsnaamcomponenten(gCompLijst);
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("pers", "1234", null, persoonPara);
        final RegistratieGeslachtsnaamVoornaamActieElement
                regactie =
                builder.maakRegistratieGeslachtsnaamVoornaamActieElement("regactie", 20100101, Collections.emptyList(), persoon);
        persoon.setVerzoekBericht(bericht);
        assertEquals(0, bijhoudingPersoon.getDelegates().get(0).getPersoonSamengesteldeNaamHistorieSet().size());
        assertEquals(1, bijhoudingPersoon.getDelegates().get(0).getPersoonGeslachtsnaamcomponentSet().size());
        assertEquals(2, bijhoudingPersoon.getDelegates().get(0).getPersoonVoornaamSet().size());
        regactie.verwerk(bericht, ah);
        final Set<PersoonSamengesteldeNaamHistorie>
                persoonSamengesteldeNaamHistorieSet =
                bijhoudingPersoon.getDelegates().get(0).getPersoonSamengesteldeNaamHistorieSet();
        assertEquals(1, persoonSamengesteldeNaamHistorieSet.size());
        assertNotNull("Stam", persoonSamengesteldeNaamHistorieSet.iterator().next().getGeslachtsnaamstam());
        assertEquals(2, bijhoudingPersoon.getDelegates().get(0).getPersoonVoornaamSet().size());
        final Set<PersoonGeslachtsnaamcomponent>
                persoonGeslachtsnaamcomponentSet =
                bijhoudingPersoon.getDelegates().get(0).getPersoonGeslachtsnaamcomponentSet();
        assertEquals(1, persoonGeslachtsnaamcomponentSet.size());
        final Set<PersoonGeslachtsnaamcomponentHistorie>
                persoonGeslachtsnaamcomponentHistorieSet =
                persoonGeslachtsnaamcomponentSet.iterator().next().getPersoonGeslachtsnaamcomponentHistorieSet();
        assertEquals(3, persoonGeslachtsnaamcomponentHistorieSet.size());
        final PersoonGeslachtsnaamcomponentHistorie
                geldigVoorkomenOpPeildatum =
                MaterieleHistorie.getGeldigVoorkomenOpPeildatum(persoonGeslachtsnaamcomponentHistorieSet, DatumUtil.vandaag());
        assertNotNull(geldigVoorkomenOpPeildatum);
        assertEquals("Jansen", geldigVoorkomenOpPeildatum.getStam());
    }

    private List<MeldingElement> koppelActiesAanAhEnValideerTestActie(final ActieElement... acties) {
        return koppelActiesAanAhEnValideerTestActie(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM, acties);
    }

    private List<MeldingElement> koppelActiesAanAhEnValideerTestActie(AdministratieveHandelingElementSoort soortAH, final ActieElement... acties) {
        final List<ActieElement> actiesVoorAh = new LinkedList<>();
        RegistratieGeslachtsnaamVoornaamActieElement testActie = null;
        for (final ActieElement actie : acties) {
            actiesVoorAh.add(actie);
            if (actie instanceof RegistratieGeslachtsnaamVoornaamActieElement) {
                testActie = (RegistratieGeslachtsnaamVoornaamActieElement) actie;
            }
        }

        final ElementBuilder.AdministratieveHandelingParameters ahPara = new ElementBuilder.AdministratieveHandelingParameters();
        ahPara.partijCode(Z_PARTIJ.getCode());
        ahPara.soort(soortAH);
        ahPara.acties(actiesVoorAh);

        when(bericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("comah", ahPara));

        if (testActie == null) {
            throw new AssertionError("Beeindiging Nationaliteit Actie niet meegegeven");
        }
        testActie.setVerzoekBericht(bericht);
        return testActie.valideerSpecifiekeInhoud();
    }

    private RegistratieGeslachtsnaamVoornaamActieElement maakStandaardActie(final int datumAanvangGeldigheid,
                                                                            final boolean voegGeslachtsnaamToeAanPersoonElementActie,
                                                                            final String objectsleutelPersoonBijHoofdActie) {
        return maakActie(true, false, datumAanvangGeldigheid, voegGeslachtsnaamToeAanPersoonElementActie, objectsleutelPersoonBijHoofdActie);
    }

    private RegistratieGeslachtsnaamVoornaamActieElement maakActie(final boolean geboreneIndicatieNamenreeks, final boolean erkenningIndicatieNamenreeks,
                                                                   final int datumAanvangGeldigheid, final boolean voegGeslachtsnaamToeAanPersoonElementActie,
                                                                   final String objectsleutelPersoonBijHoofdActie) {
        return maakActie(geboreneIndicatieNamenreeks, erkenningIndicatieNamenreeks, true, datumAanvangGeldigheid, voegGeslachtsnaamToeAanPersoonElementActie,
                objectsleutelPersoonBijHoofdActie);
    }

    private RegistratieGeslachtsnaamVoornaamActieElement maakActie(final Boolean geboreneIndicatieNamenreeks, final boolean erkenningIndicatieNamenreeks,
                                                                   final boolean metVoornaam, final int datumAanvangGeldigheid,
                                                                   final boolean isHoofdActie, final String objectsleutelPersoonBijHoofdActie) {
        final String commIdPersoon = "persoon";

        // Persoon van geborene actie
        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final GeslachtsnaamcomponentElement geslachtsnaamComponent = builder.maakGeslachtsnaamcomponentElement("geslachtsnaam", null, null, null, null, "Puk");
        if (geboreneIndicatieNamenreeks != null) {
            final ElementBuilder.NaamParameters naamParams = new ElementBuilder.NaamParameters();
            naamParams.indicatieNamenreeks(geboreneIndicatieNamenreeks);
            final SamengesteldeNaamElement samengesteldeNaam = builder.maakSamengesteldeNaamElement("samengesteldeNaam", naamParams);
            persoonParams.samengesteldeNaam(samengesteldeNaam);
        }
        persoonParams.geslachtsnaamcomponenten(Collections.singletonList(geslachtsnaamComponent));

        final PersoonGegevensElement persoon;
        if (isHoofdActie) {
            if (metVoornaam) {
                final VoornaamElement voornaam = builder.maakVoornaamElement("voornaam_hoofd", 0, "Piet");
                persoonParams.voornamen(Collections.singletonList(voornaam));
            }
            persoon = builder.maakPersoonGegevensElement(commIdPersoon, objectsleutelPersoonBijHoofdActie, null, persoonParams);
        } else {
            persoon = builder.maakPersoonGegevensElement(commIdPersoon, null, null, persoonParams);
        }

        // Persoon met referentie in de geslachtsnaam/voornaam actie
        final ElementBuilder.PersoonParameters persoonRefParams = new ElementBuilder.PersoonParameters();
        if (metVoornaam) {
            final VoornaamElement voornaam = builder.maakVoornaamElement("voornaam", 0, "Piet");
            persoonRefParams.voornamen(Collections.singletonList(voornaam));
        }
        final ElementBuilder.NaamParameters refNaamParams = new ElementBuilder.NaamParameters();
        refNaamParams.indicatieNamenreeks(erkenningIndicatieNamenreeks);
        persoonRefParams.samengesteldeNaam(builder.maakSamengesteldeNaamElement("refSamengesteldeNaam", refNaamParams));
        final PersoonGegevensElement persoonRefElement = builder.maakPersoonGegevensElement("persoonRef", null, commIdPersoon, persoonRefParams);

        final Map<String, BmrGroep> commMap = new HashMap<>();
        commMap.put(commIdPersoon, persoon);
        persoonRefElement.initialiseer(commMap);
        final RegistratieGeslachtsnaamVoornaamActieElement actie;
        if (isHoofdActie) {

            actie = builder.maakRegistratieGeslachtsnaamVoornaamActieElement("actie", datumAanvangGeldigheid, Collections.emptyList(), persoon);
        } else {
            actie = builder.maakRegistratieGeslachtsnaamVoornaamActieElement("actie", datumAanvangGeldigheid, Collections.emptyList(), persoonRefElement);
        }
        builder.initialiseerVerzoekBericht(bericht);
        return actie;

    }
}
