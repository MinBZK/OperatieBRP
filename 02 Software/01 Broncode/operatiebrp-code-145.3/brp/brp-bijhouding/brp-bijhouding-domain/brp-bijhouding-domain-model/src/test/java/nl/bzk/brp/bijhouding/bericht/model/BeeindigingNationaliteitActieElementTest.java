/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BeeindigingNationaliteitActieElementTest extends AbstractElementTest {

    private static final String COMM_ID_NATIONALITEIT_REGISTRATIE = "commId_nat_registratie";
    private static final String COMM_ID_NATIONALITEIT_BEEINDIGING = "commId_nat_beeindiging";
    private static final String COMM_ID_ACTIE_REGISTRATIE = "commId_nat_registratie_actie";
    private static final String COMM_ID_ACTIE_BEEINDIGING = "commId_nat_beeindiging_actie";
    private static final String PERSOON_OBJECTSLEUTEL = "1234";
    private static final String NATIONALITEIT_OBJECT_SLEUTEL = "1";
    private static final String REDEN_VERKRIJGING_ONBEKEND = "000";
    private static final String REDEN_VERLIES = "034";
    private ElementBuilder builder;
    private AdministratieveHandeling ah = mock(AdministratieveHandeling.class);
    private final Timestamp timestamp = new Timestamp(DatumUtil.nu().getTime());
    private BijhoudingPersoon bijhoudingPersoon;

    @Before
    public void setUp() {
        builder = new ElementBuilder();
        bijhoudingPersoon = new BijhoudingPersoon();
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_OBJECTSLEUTEL)).thenReturn(bijhoudingPersoon);
        when(ah.getDatumTijdRegistratie()).thenReturn(timestamp);
        when(ah.getPartij()).thenReturn(new Partij("partij", "100011"));
        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode("0016")).thenReturn(new Nationaliteit("0016", "0016"));
        when(getDynamischeStamtabelRepository().getRedenVerliesNLNationaliteitByCode(REDEN_VERLIES))
                .thenReturn(new RedenVerliesNLNationaliteit(REDEN_VERLIES, "test"));
    }

    @Bedrijfsregel(Regel.R2527)
    @Test
    public void testDatumEindeGeldigheidInToekomst (){
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie("123", 2099_01_01, null);
        controleerRegels(actie.valideerInhoud(), Regel.R2527);
    }

    @Bedrijfsregel(Regel.R2527)
    @Test
    public void testDatumEindeGeldigheidInVerleden (){
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie("123", 2015_01_01, null);
        controleerRegels(actie.valideerInhoud());
    }


    @Bedrijfsregel(Regel.R2522)
    @Test
    public void testBeeindigenNationaliteitMetDatumAanvangOngelijkAanGeboorteDatum() {
        final RegistratieNationaliteitActieElement registratieActie = maakRegistratieNationaliteitActie("0016", REDEN_VERKRIJGING_ONBEKEND, 2001_01_02);
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(registratieActie, 2010_01_01, null);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerBeeindigingActie(registratieActie, actie);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2522, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1689)
    @Test
    public void testNederlandseNationaliteitZonderReden() {
        final RegistratieNationaliteitActieElement registratieActie = maakRegistratieNationaliteitActie("0001", REDEN_VERKRIJGING_ONBEKEND, 2001_01_01);
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(registratieActie, 2010_01_01, null);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerBeeindigingActie(registratieActie, actie);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1689, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2545)
    @Test
    public void testNietNederlandseNationaliteitMetReden() {
        final RegistratieNationaliteitActieElement registratieActie = maakRegistratieNationaliteitActie("0016", REDEN_VERKRIJGING_ONBEKEND, 2001_01_01);
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(registratieActie, 2010_01_01, REDEN_VERLIES);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerBeeindigingActie(registratieActie, actie);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2545, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1658)
    @Test
    public void testDatumEindeVoorDatumAanvangGeldigheid() {
        final RegistratieNationaliteitActieElement registratieActie = maakRegistratieNationaliteitActie("0001", REDEN_VERKRIJGING_ONBEKEND, 2001_01_01);
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(registratieActie, 2000_01_01, REDEN_VERLIES);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerBeeindigingActie(registratieActie, actie);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1658, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1658)
    @Test
    public void testDatumEindeOpDatumAanvangGeldigheid() {
        final int datumaanvang = 2001_01_01;
        final RegistratieNationaliteitActieElement registratieActie = maakRegistratieNationaliteitActie("0001", REDEN_VERKRIJGING_ONBEKEND, datumaanvang);
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(registratieActie, datumaanvang, REDEN_VERLIES);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerBeeindigingActie(registratieActie, actie);
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R1658)
    @Test
    public void testDatumEindeNaDatumAanvangGeldigheid() {
        final RegistratieNationaliteitActieElement registratieActie = maakRegistratieNationaliteitActie("0001", REDEN_VERKRIJGING_ONBEKEND, 2001_01_01);
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(registratieActie, 2010_01_01, REDEN_VERLIES);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerBeeindigingActie(registratieActie, actie);
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2451)
    @Test
    public void testDatumEindeGeldigheid_GelijkAan_DatumErkenning() {
        final int datumErkenning = 2010_01_01;
        final RegistratieNationaliteitActieElement registratieActie = maakRegistratieNationaliteitActie("0001", REDEN_VERKRIJGING_ONBEKEND, 2001_01_01);
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(registratieActie, datumErkenning, REDEN_VERLIES);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerBeeindigingActie(registratieActie, actie);
        assertTrue(meldingen.isEmpty());
    }

    @Bedrijfsregel(Regel.R2451)
    @Test
    public void testDatumEindeGeldigheid_NietGelijkAan_DatumErkenning() {
        final RegistratieOuderActieElement ouderActie = maakRegistratieOuderActie(2010_01_01, null, COMM_ID_KIND);
        final RegistratieNationaliteitActieElement registratieActie = maakRegistratieNationaliteitActie("0001", REDEN_VERKRIJGING_ONBEKEND, 2001_01_01);
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(registratieActie, 2012_01_01, REDEN_VERLIES);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerBeeindigingActie(ouderActie, registratieActie, actie);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2451, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1706)
    @Test
    public void testRedenVerliesCodeNogNietGeldigOpPeildatum() {
        final RegistratieNationaliteitActieElement registratieActie = maakRegistratieNationaliteitActie("0001", REDEN_VERKRIJGING_ONBEKEND, 2001_01_01);
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(registratieActie, 2010_01_01, REDEN_VERLIES);
        RedenVerliesNLNationaliteit redenVerliesNLNationaliteit = new RedenVerliesNLNationaliteit(REDEN_VERLIES, "omschrijving");
        redenVerliesNLNationaliteit.setDatumAanvangGeldigheid(2015_01_01);
        redenVerliesNLNationaliteit.setDatumEindeGeldigheid(2015_01_02);
        when(getDynamischeStamtabelRepository().getRedenVerliesNLNationaliteitByCode(REDEN_VERLIES)).thenReturn(redenVerliesNLNationaliteit);
        final List<MeldingElement> meldingen = koppelActiesAanAhEnValideerBeeindigingActie(registratieActie, actie);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1706, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1845)
    @Test
    public void testNationaliteitIsAanwezigBijPersoon() {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_OBJECTSLEUTEL)).thenReturn(persoon);

        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(NATIONALITEIT_OBJECT_SLEUTEL, 2016_01_01, "017");
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, NEDERLANDS);
        nationaliteit.setId(Long.valueOf(NATIONALITEIT_OBJECT_SLEUTEL));
        final PersoonNationaliteitHistorie nationaliteitHistorie = new PersoonNationaliteitHistorie(nationaliteit);
        nationaliteitHistorie.setDatumAanvangGeldigheid(2000_01_01);
        nationaliteitHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        nationaliteit.addPersoonNationaliteitHistorie(nationaliteitHistorie);

        persoon.addPersoonNationaliteit(nationaliteit);
        actie.getPersoon().postConstruct();
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        controleerRegels(meldingen);
    }

    @Bedrijfsregel(Regel.R1845)
    @Bedrijfsregel(Regel.R2545)
    @Test
    public void testNationaliteitIsNietAanwezigBijPersoon() {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_OBJECTSLEUTEL)).thenReturn(persoon);

        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(NATIONALITEIT_OBJECT_SLEUTEL, 2016_01_01, "017");
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, NEDERLANDS);
        nationaliteit.setId(123L);
        final PersoonNationaliteitHistorie nationaliteitHistorie = new PersoonNationaliteitHistorie(nationaliteit);
        nationaliteitHistorie.setDatumAanvangGeldigheid(2000_01_01);
        nationaliteitHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        nationaliteit.addPersoonNationaliteitHistorie(nationaliteitHistorie);

        persoon.addPersoonNationaliteit(nationaliteit);

        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        controleerRegels(meldingen, Regel.R1845, Regel.R2545);
    }

    @Bedrijfsregel(Regel.R2640)
    @Test
    public void testNationaliteitIsAanwezigEnNietGeldigBijPersoon() {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_OBJECTSLEUTEL)).thenReturn(persoon);

        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(NATIONALITEIT_OBJECT_SLEUTEL, 2016_01_01, REDEN_VERLIES);
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, NEDERLANDS);
        nationaliteit.setId(Long.valueOf(NATIONALITEIT_OBJECT_SLEUTEL));
        final PersoonNationaliteitHistorie nationaliteitHistorie = new PersoonNationaliteitHistorie(nationaliteit);
        nationaliteitHistorie.setDatumAanvangGeldigheid(2000_01_01);
        nationaliteitHistorie.setDatumEindeGeldigheid(2014_01_01);
        nationaliteitHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        nationaliteit.addPersoonNationaliteitHistorie(nationaliteitHistorie);

        persoon.addPersoonNationaliteit(nationaliteit);
        actie.getPersoon().postConstruct();
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        controleerRegels(meldingen, Regel.R2640);
    }

    @Bedrijfsregel(Regel.R2799)
    @Test
    public void testNationaliteitMeerDanEensBeeindigd() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, NEDERLANDS);
        nationaliteit.setId(Long.valueOf(NATIONALITEIT_OBJECT_SLEUTEL));
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(NATIONALITEIT_OBJECT_SLEUTEL, 2016_01_01, REDEN_VERLIES);
        BijhoudingVerzoekBericht bericht = getBericht();
        actie.setVerzoekBericht(bericht);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_OBJECTSLEUTEL)).thenReturn(persoon);
        when(persoon.wordtNationaliteitMeerDanEensBeeindigd(actie.getPersoonElement().getNationaliteit())).thenReturn(true);
        when(persoon.getPersoonNationaliteitSet()).thenReturn(Collections.singleton(nationaliteit));
        actie.getPersoon().postConstruct();
        controleerRegels(actie.valideerActieInhoud(), Regel.R2799);
    }

    @Bedrijfsregel(Regel.R2799)
    @Test
    public void testNationaliteitNietMeerDanEensBeeindigd() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, NEDERLANDS);
        nationaliteit.setId(Long.valueOf(NATIONALITEIT_OBJECT_SLEUTEL));
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(NATIONALITEIT_OBJECT_SLEUTEL, 2016_01_01, REDEN_VERLIES);
        BijhoudingVerzoekBericht bericht = getBericht();
        actie.setVerzoekBericht(bericht);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_OBJECTSLEUTEL)).thenReturn(persoon);
        when(persoon.wordtNationaliteitMeerDanEensBeeindigd(actie.getPersoonElement().getNationaliteit())).thenReturn(false);
        when(persoon.getPersoonNationaliteitSet()).thenReturn(Collections.singleton(nationaliteit));
        actie.getPersoon().postConstruct();
        controleerRegels(actie.valideerActieInhoud());
    }

    @Test
    public void testVerwerkingEindeNationaliteit_RegistratieNationaliteitNietVerwerkt() {
        final RegistratieNationaliteitActieElement registratieActie = maakRegistratieNationaliteitActie("0016", null, 2001_01_01);
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(registratieActie, 2010_01_01, REDEN_VERLIES);
        actie.verwerk(getBericht(), ah);
        assertTrue(bijhoudingPersoon.getPersoonNationaliteitSet().isEmpty());
    }

    @Test
    public void testVerwerking_GeenReferentie() {
        final long nationaliteitId = 1L;
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(bijhoudingPersoon, NEDERLANDS);
        nationaliteit.setId(nationaliteitId);
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(nationaliteit);
        historie.setDatumAanvangGeldigheid(2001_01_01);
        nationaliteit.addPersoonNationaliteitHistorie(historie);
        bijhoudingPersoon.addPersoonNationaliteit(nationaliteit);

        final NationaliteitElement natElement = builder.maakNationaliteitElementVerlies("nat", ""+nationaliteitId, null, REDEN_VERLIES);
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.nationaliteiten(Collections.singletonList(natElement));
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("persoon", PERSOON_OBJECTSLEUTEL, null, persoonParameters);
        persoon.setVerzoekBericht(getBericht());

        final Integer datumEindeGeldigheid = 2015_01_01;
        final BeeindigingNationaliteitActieElement actieElement =
                builder.maakBeeindigNationaliteitActieElement("actie", Collections.emptyList(), persoon, datumEindeGeldigheid);
        actieElement.setVerzoekBericht(getBericht());

        assertEquals(1, nationaliteit.getPersoonNationaliteitHistorieSet().size());
        final BRPActie actie = actieElement.verwerk(getBericht(), ah);
        assertNotNull(actie);
        assertEquals(2, nationaliteit.getPersoonNationaliteitHistorieSet().size());
        assertNotNull(historie.getDatumTijdVerval());
        final Iterator<PersoonNationaliteitHistorie> iterator = nationaliteit.getPersoonNationaliteitHistorieSet().iterator();
        iterator.next();
        final PersoonNationaliteitHistorie nieuweHistorie = iterator.next();
        assertEquals(datumEindeGeldigheid, nieuweHistorie.getDatumEindeGeldigheid());
        assertNotNull(nieuweHistorie.getRedenVerliesNLNationaliteit());
        assertEquals(REDEN_VERLIES, nieuweHistorie.getRedenVerliesNLNationaliteit().getCode());
    }

    @Test
    public void testVerwerking_ObjecsleutelMaarIdNietAanwezig() {
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(bijhoudingPersoon, NEDERLANDS);
        nationaliteit.setId(2L);
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(nationaliteit);
        historie.setDatumAanvangGeldigheid(2001_01_01);
        nationaliteit.addPersoonNationaliteitHistorie(historie);
        bijhoudingPersoon.addPersoonNationaliteit(nationaliteit);

        final NationaliteitElement natElement = builder.maakNationaliteitElementVerlies("nat", "1", null, REDEN_VERLIES);
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.nationaliteiten(Collections.singletonList(natElement));
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("persoon", PERSOON_OBJECTSLEUTEL, null, persoonParameters);
        persoon.setVerzoekBericht(getBericht());

        final Integer datumEindeGeldigheid = 2015_01_01;
        final BeeindigingNationaliteitActieElement actieElement =
                builder.maakBeeindigNationaliteitActieElement("actie", Collections.emptyList(), persoon, datumEindeGeldigheid);
        actieElement.setVerzoekBericht(getBericht());

        assertEquals(1, nationaliteit.getPersoonNationaliteitHistorieSet().size());
        final BRPActie actie = actieElement.verwerk(getBericht(), ah);
        assertNotNull(actie);
        assertEquals(1, nationaliteit.getPersoonNationaliteitHistorieSet().size());
    }

    @Test
    public void testVerwerkingEinde() {
        final RegistratieNationaliteitActieElement registratieNationaliteitActieElement = maakRegistratieNationaliteitActie("0016", null, 2001_01_01);
        final int datumEindeGeldigheid = 2010_01_02;
        final BeeindigingNationaliteitActieElement beeindigingNationaliteitActieElement =
                maakBeeindigingNationaliteitActie(registratieNationaliteitActieElement, datumEindeGeldigheid, REDEN_VERLIES);

        registratieNationaliteitActieElement.verwerk(getBericht(), ah);
        assertEquals(1, bijhoudingPersoon.getPersoonNationaliteitSet().iterator().next().getPersoonNationaliteitHistorieSet().size());

        beeindigingNationaliteitActieElement.verwerk(getBericht(), ah);
        assertEquals(1, bijhoudingPersoon.getPersoonNationaliteitSet().size());

        final Set<PersoonNationaliteitHistorie> persoonNationaliteitHistorieSet =
                bijhoudingPersoon.getPersoonNationaliteitSet().iterator().next().getPersoonNationaliteitHistorieSet();
        assertEquals(2, persoonNationaliteitHistorieSet.size());

        final Iterator<PersoonNationaliteitHistorie> iterator = persoonNationaliteitHistorieSet.iterator();
        iterator.next();
        final PersoonNationaliteitHistorie historie = iterator.next();
        assertEquals(datumEindeGeldigheid, historie.getDatumEindeGeldigheid().intValue());
        assertNotNull(historie.getRedenVerliesNLNationaliteit());
        assertEquals(REDEN_VERLIES, historie.getRedenVerliesNLNationaliteit().getCode());
    }

    @Test
    public void testVerwerkingEindeNietverwerkbarePersoon() {
        final RegistratieNationaliteitActieElement registratieNationaliteitActieElement = maakRegistratieNationaliteitActie("0016", null, 2001_01_01);
        final BeeindigingNationaliteitActieElement actie = maakBeeindigingNationaliteitActie(registratieNationaliteitActieElement, 2012_01_01, null);

        registratieNationaliteitActieElement.verwerk(getBericht(), ah);
        assertEquals(1, bijhoudingPersoon.getPersoonNationaliteitSet().iterator().next().getPersoonNationaliteitHistorieSet().size());

        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.GBA);

        actie.verwerk(getBericht(), ah);
        assertEquals(1, bijhoudingPersoon.getPersoonNationaliteitSet().size());
        final Set<PersoonNationaliteitHistorie> persoonNationaliteitHistorieSet =
                bijhoudingPersoon.getPersoonNationaliteitSet().iterator().next().getPersoonNationaliteitHistorieSet();
        assertEquals(1, persoonNationaliteitHistorieSet.size());
    }

    private List<MeldingElement> koppelActiesAanAhEnValideerBeeindigingActie(final ActieElement... acties) {
        final RegistratieGeboreneActieElement geboreneActie = maakActieGeboreneMetAlleenOuwkig(2001_01_01,builder);

        final List<ActieElement> actiesVoorAh = new LinkedList<>();
        actiesVoorAh.add(geboreneActie);
        BeeindigingNationaliteitActieElement beeindigingActie = null;
        for(final ActieElement actie : acties) {
            actiesVoorAh.add(actie);
            if (actie instanceof BeeindigingNationaliteitActieElement) {
                beeindigingActie = (BeeindigingNationaliteitActieElement) actie;
            }
        }

        final ElementBuilder.AdministratieveHandelingParameters ahPara = new ElementBuilder.AdministratieveHandelingParameters();
        ahPara.partijCode(Z_PARTIJ.getCode());
        ahPara.soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM);
        ahPara.acties(actiesVoorAh);

        when(getBericht().getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("comah", ahPara));

        if (beeindigingActie == null) {
            throw new AssertionError("Beeindiging Nationaliteit Actie niet meegegeven");
        }
        return beeindigingActie.valideerSpecifiekeInhoud();
    }


    private RegistratieNationaliteitActieElement maakRegistratieNationaliteitActie(final String nationaliteitcode, final String redenverkrijgingcode,
                                                                                   final int datumaanvang) {
        final NationaliteitElement nationaliteit = builder.maakNationaliteitElement(COMM_ID_NATIONALITEIT_REGISTRATIE, nationaliteitcode, redenverkrijgingcode);
        final List<NationaliteitElement> nationaliteiten = new LinkedList<>();
        nationaliteiten.add(nationaliteit);

        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.nationaliteiten(nationaliteiten);

        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("actieNPers1", PERSOON_OBJECTSLEUTEL, null, persoonParameters);
        persoon.setVerzoekBericht(getBericht());
        return builder.maakRegistratieNationaliteitActieElement(COMM_ID_ACTIE_REGISTRATIE, datumaanvang, persoon);
    }

    private BeeindigingNationaliteitActieElement maakBeeindigingNationaliteitActie(final RegistratieNationaliteitActieElement registratieActie,
                                                                                   final int datumEindeGeldigheid, final String redenVerlies) {
        final List<NationaliteitElement> nationaliteiten = new LinkedList<>();
        final NationaliteitElement beeindigingNationaliteitElement = builder.maakNationaliteitElementVerlies(COMM_ID_NATIONALITEIT_BEEINDIGING,
                null, COMM_ID_NATIONALITEIT_REGISTRATIE, redenVerlies);
        nationaliteiten.add(beeindigingNationaliteitElement);

        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.nationaliteiten(nationaliteiten);

        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("actieNPers", PERSOON_OBJECTSLEUTEL, null, persoonParameters);

        Map<String, BmrGroep> map = new HashMap<>();
        map.put(COMM_ID_NATIONALITEIT_REGISTRATIE, registratieActie.getPersoon().getNationaliteit());
        beeindigingNationaliteitElement.initialiseer(map);

        final BeeindigingNationaliteitActieElement actie =
                builder.maakBeeindigNationaliteitActieElement(COMM_ID_ACTIE_BEEINDIGING, Collections.emptyList(), persoon, datumEindeGeldigheid);
        persoon.setVerzoekBericht(getBericht());
        actie.setVerzoekBericht(getBericht());
        return actie;
    }

    private BeeindigingNationaliteitActieElement maakBeeindigingNationaliteitActie(final String objectSleutel, final int datumEindeGeldigheid,
                                                                                   final String redenVerlies) {
        final NationaliteitElement beeindigingNationaliteitElement = builder.maakNationaliteitElementVerlies(COMM_ID_NATIONALITEIT_BEEINDIGING,
                objectSleutel, null, redenVerlies);

        beeindigingNationaliteitElement.setVerzoekBericht(getBericht());
        final List<NationaliteitElement> nationaliteiten = new LinkedList<>();
        nationaliteiten.add(beeindigingNationaliteitElement);

        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.nationaliteiten(nationaliteiten);
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("actieNPers", PERSOON_OBJECTSLEUTEL, null, persoonParameters);
        persoon.setVerzoekBericht(getBericht());

        final BeeindigingNationaliteitActieElement actie =
                builder.maakBeeindigNationaliteitActieElement(COMM_ID_ACTIE_BEEINDIGING, Collections.emptyList(), persoon, datumEindeGeldigheid);
        actie.setVerzoekBericht(getBericht());
        return actie;
    }
}
