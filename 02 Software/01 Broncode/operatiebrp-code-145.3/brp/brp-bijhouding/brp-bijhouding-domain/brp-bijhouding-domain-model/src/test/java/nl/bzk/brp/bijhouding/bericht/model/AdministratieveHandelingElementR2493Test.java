/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R2493;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdministratieveHandelingElementR2493Test extends AbstractElementTest {
    public static final Partij PARTIJ_GRONINGEN = new Partij("Gemeente Groningen", "000014");

    public static final LandOfGebied NEDERLAND = new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "Nederland");
    final ElementBuilder builder = new ElementBuilder();
    final Gemeente gemeente = new Gemeente(Short.parseShort("17"), "Groningen", "0018", PARTIJ_GRONINGEN);
    final ElementBuilder.AdresElementParameters adresParams = new ElementBuilder.AdresElementParameters("soort", 'A', 20160101, "17");
    final AdresElement adresElement = builder.maakAdres("com_adres", adresParams);
    final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters().adres(adresElement);
    final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("p_comid", "1234", null, persoonParameters);
    final RegistratieAdresActieElement actieElement = builder.maakRegistratieAdresActieElement("reg_verhuiz", 20160101, persoonElement);
    final ElementBuilder.AdministratieveHandelingParameters
            ahParams =
            new ElementBuilder.AdministratieveHandelingParameters().acties(Arrays.asList(actieElement))
                    .soort(AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK).partijCode("17");
    final AdministratieveHandelingElement aHandeling = builder.maakAdministratieveHandelingElement("ahd_comid", ahParams);
    final BijhoudingVerzoekBericht verzoekbericht = mock(BijhoudingVerzoekBericht.class);
    final ElementBuilder.StuurgegevensParameters stuurParams = new ElementBuilder.StuurgegevensParameters().zendendePartij("17").zendendeSysteem("brp")
            .referentienummer("88409eeb-1aa5-43fc-8614-43055123a165").tijdstipVerzending("2016-03-21T09:32:03.234+02:00");
    final StuurgegevensElement stuurgegevens = builder.maakStuurgegevensElement("stuur_comm", stuurParams);

    @Before
    public void setup() throws OngeldigeObjectSleutelException {
        aHandeling.setVerzoekBericht(verzoekbericht);
        actieElement.setVerzoekBericht(verzoekbericht);
        persoonElement.setVerzoekBericht(verzoekbericht);
        PARTIJ_GRONINGEN.addPartijRol(new PartijRol(PARTIJ_GRONINGEN, Rol.BIJHOUDINGSORGAAN_COLLEGE));
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("17")).thenReturn(gemeente);
        when(getDynamischeStamtabelRepository().getPartijByCode("14")).thenReturn(PARTIJ_GRONINGEN);

        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(anyChar())).thenReturn(new RedenWijzigingVerblijf('P', "Persoonlijk"));
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(anyChar())).thenReturn(new RedenWijzigingVerblijf('A', "Ambtshalve"));
        when(getDynamischeStamtabelRepository().getAangeverByCode(anyChar())).thenReturn(new Aangever('C', "Aangever", "test aangever"));
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(anyString())).thenReturn(new Plaats("plaats"));
        when(verzoekbericht.getStuurgegevens()).thenReturn(stuurgegevens);
        when(verzoekbericht.getAdministratieveHandeling()).thenReturn(aHandeling);
        when(verzoekbericht.getZendendePartij()).thenReturn(PARTIJ_GRONINGEN);
        when(getDynamischeStamtabelRepository().getPartijByCode("17")).thenReturn(PARTIJ_GRONINGEN);

    }

    @Test
    public void testOK() {
        final BijhoudingPersoon persoon = createPersoon();
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(0, meldingen.size());
        assertNull(null);
    }

    @Test
    public void testPersoonInschrijving() {
        final BijhoudingPersoon persoon = createPersoon();
        final PersoonInschrijvingHistorie historie = new PersoonInschrijvingHistorie(persoon, 20170202, 1L, new Timestamp(new Date().getTime()));
        persoon.getPersoonInschrijvingHistorieSet().add(historie);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
        assertEquals(aHandeling.getCommunicatieId(), meldingen.get(0).getReferentieId());
    }

    @Test
    public void testPersoonInschrijvingBijCorrectie() {
        final BijhoudingPersoon persoon = createPersoon();
        final PersoonInschrijvingHistorie historie = new PersoonInschrijvingHistorie(persoon, 20170202, 1L, new Timestamp(new Date().getTime()));
        persoon.getPersoonInschrijvingHistorieSet().add(historie);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final ElementBuilder.AdministratieveHandelingParameters
                ahParams =
                new ElementBuilder.AdministratieveHandelingParameters().acties(Arrays.asList(actieElement))
                        .soort(AdministratieveHandelingElementSoort.CORRECTIE_HUWELIJK).partijCode("17");
        final AdministratieveHandelingElement correctieHandeling = builder.maakAdministratieveHandelingElement("ahd_comid_2", ahParams);
        correctieHandeling.setVerzoekBericht(verzoekbericht);
        final List<MeldingElement> meldingen = correctieHandeling.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testPersoonGeboorteDatum() {
        final BijhoudingPersoon persoon = createPersoon();
        final PersoonGeboorteHistorie historie = new PersoonGeboorteHistorie(persoon, 20170202, new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "Nederland"));
        persoon.getDelegates().get(0).getPersoonGeboorteHistorieSet().add(historie);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }

    @Test
    public void testPersoonOverlijden() {
        final BijhoudingPersoon persoon = createPersoon();
        final PersoonOverlijdenHistorie historie = new PersoonOverlijdenHistorie(persoon, 20170202, NEDERLAND);
        persoon.getPersoonOverlijdenHistorieSet().add(historie);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }

    @Test
    public void testPersoonAanvangAdresHouding() {
        final BijhoudingPersoon persoon = createPersoon();
        final PersoonAdres adres = new PersoonAdres(persoon);
        final RedenWijzigingVerblijf reden = new RedenWijzigingVerblijf('o', "ohoh");
        final PersoonAdresHistorie historie = new PersoonAdresHistorie(adres, SoortAdres.WOONADRES, NEDERLAND, reden);
        historie.setDatumAanvangAdreshouding(20170202);
        adres.getPersoonAdresHistorieSet().add(historie);
        persoon.getPersoonAdresSet().add(adres);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }


    @Test
    public void testPersoonVerBlijfsRechtDatumMededeling() {
        final BijhoudingPersoon persoon = createPersoon();
        final Verblijfsrecht vRecht = new Verblijfsrecht("01", "omschr");
        final PersoonVerblijfsrechtHistorie historie = new PersoonVerblijfsrechtHistorie(persoon, vRecht, 20000101, 20170202);
        persoon.getPersoonVerblijfsrechtHistorieSet().add(historie);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }

    @Test
    public void testPersoonDeelNameEuVerkiezing() {
        final BijhoudingPersoon persoon = createPersoon();
        final PersoonDeelnameEuVerkiezingenHistorie historie = new PersoonDeelnameEuVerkiezingenHistorie(persoon, true);
        historie.setDatumAanleidingAanpassingDeelnameEuVerkiezingen(20170202);
        persoon.getPersoonDeelnameEuVerkiezingenHistorieSet().add(historie);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }


    @Test
    public void testPersoonReisDocumentDatumIngang() {
        final BijhoudingPersoon persoon = createPersoon();
        final PersoonReisdocument reisDoc = new PersoonReisdocument(persoon, new SoortNederlandsReisdocument("1", "een"));
        final PersoonReisdocumentHistorie historie = new PersoonReisdocumentHistorie(20170202, 20000101, 20000101, "1234", "auto", reisDoc);
        reisDoc.getPersoonReisdocumentHistorieSet().add(historie);
        persoon.getPersoonReisdocumentSet().add(reisDoc);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }

    @Test
    public void testPersoonReisDocumentDatumUitgifte() {
        final BijhoudingPersoon persoon = createPersoon();
        final PersoonReisdocument reisDoc = new PersoonReisdocument(persoon, new SoortNederlandsReisdocument("1", "een"));
        final PersoonReisdocumentHistorie historie = new PersoonReisdocumentHistorie(20000202, 20170202, 20000101, "1234", "auto", reisDoc);
        reisDoc.getPersoonReisdocumentHistorieSet().add(historie);
        persoon.getPersoonReisdocumentSet().add(reisDoc);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }


    @Test
    public void testPersoonRelatieDatumAanvang() {
        final BijhoudingPersoon persoon = createPersoon();
        final Relatie huwelijk = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie historie = new RelatieHistorie(huwelijk);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk);
        huwelijk.addBetrokkenheid(betrokkenheid);
        historie.setDatumAanvang(20170202);
        huwelijk.getRelatieHistorieSet().add(historie);
        persoon.getBetrokkenheidSet().add(betrokkenheid);

        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }

    @Test
    public void testPersoonRelatieDatumEinde() {
        final BijhoudingPersoon persoon = createPersoon();
        final Relatie huwelijk = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie historie = new RelatieHistorie(huwelijk);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk);
        huwelijk.addBetrokkenheid(betrokkenheid);
        historie.setDatumAanvang(20150202);
        historie.setDatumEinde(20170202);
        huwelijk.getRelatieHistorieSet().add(historie);
        persoon.getBetrokkenheidSet().add(betrokkenheid);

        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }

    @Test
    public void testPersoonPartner() {
        final BijhoudingPersoon partner = createPersoon();
        final PersoonGeboorteHistorie historie = new PersoonGeboorteHistorie(partner, 20170202, NEDERLAND);
        partner.getDelegates().get(0).getPersoonGeboorteHistorieSet().add(historie);

        final BijhoudingPersoon persoon = createPersoon();

        final Relatie huwelijk = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie hisHuwelijk = new RelatieHistorie(huwelijk);

        hisHuwelijk.setDatumAanvang(20010101);
        huwelijk.getRelatieHistorieSet().add(hisHuwelijk);

        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk);
        final Betrokkenheid partnerBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk);

        final BetrokkenheidHistorie hisIkBetrokkenheid = new BetrokkenheidHistorie(ikBetrokkenheid);
        ikBetrokkenheid.getBetrokkenheidHistorieSet().add(hisIkBetrokkenheid);
        final BetrokkenheidHistorie hisOuderBetrokkenheid = new BetrokkenheidHistorie(partnerBetrokkenheid);
        partnerBetrokkenheid.getBetrokkenheidHistorieSet().add(hisOuderBetrokkenheid);

        huwelijk.addBetrokkenheid(ikBetrokkenheid);
        huwelijk.addBetrokkenheid(partnerBetrokkenheid);
        persoon.getBetrokkenheidSet().add(ikBetrokkenheid);
        ikBetrokkenheid.setPersoon(persoon);

        partner.addBetrokkenheid(partnerBetrokkenheid);
        partnerBetrokkenheid.setPersoon(partner);

        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }

    @Test
    public void testPersoonKindGeboorte() {
        final BijhoudingPersoon kind = createPersoon();
        final PersoonGeboorteHistorie historie = new PersoonGeboorteHistorie(kind, 20170202, NEDERLAND);
        kind.getDelegates().get(0).getPersoonGeboorteHistorieSet().add(historie);
        final BijhoudingPersoon persoon = createPersoon();
        final Relatie familie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie hisFamilie = new RelatieHistorie(familie);
        final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, familie);
        final Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, familie);
        final BetrokkenheidHistorie hisKindBetrokkenheid = new BetrokkenheidHistorie(ouderBetrokkenheid);
        ouderBetrokkenheid.getBetrokkenheidHistorieSet().add(hisKindBetrokkenheid);
        final BetrokkenheidHistorie hisOuderBetrokkenheid = new BetrokkenheidHistorie(kindBetrokkenheid);
        kindBetrokkenheid.getBetrokkenheidHistorieSet().add(hisOuderBetrokkenheid);
        familie.addBetrokkenheid(ouderBetrokkenheid);
        familie.addBetrokkenheid(kindBetrokkenheid);
        persoon.getBetrokkenheidSet().add(ouderBetrokkenheid);
        ouderBetrokkenheid.setPersoon(persoon);
        kind.addBetrokkenheid(kindBetrokkenheid);
        kindBetrokkenheid.setPersoon(kind);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }

    @Test
    public void testPersoonOuderGeboorte() {
        final BijhoudingPersoon ouder = createPersoon();
        final PersoonGeboorteHistorie historie = new PersoonGeboorteHistorie(ouder, 20170202, NEDERLAND);
        ouder.getDelegates().get(0).getPersoonGeboorteHistorieSet().add(historie);
        final BijhoudingPersoon persoon = createPersoon();
        final Relatie familie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie hisFamilie = new RelatieHistorie(familie);
        final Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, familie);
        final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, familie);
        final BetrokkenheidHistorie hisKindBetrokkenheid = new BetrokkenheidHistorie(kindBetrokkenheid);
        kindBetrokkenheid.getBetrokkenheidHistorieSet().add(hisKindBetrokkenheid);
        final BetrokkenheidHistorie hisOuderBetrokkenheid = new BetrokkenheidHistorie(ouderBetrokkenheid);
        ouderBetrokkenheid.getBetrokkenheidHistorieSet().add(hisOuderBetrokkenheid);
        familie.addBetrokkenheid(kindBetrokkenheid);
        familie.addBetrokkenheid(ouderBetrokkenheid);
        persoon.getBetrokkenheidSet().add(kindBetrokkenheid);
        kindBetrokkenheid.setPersoon(persoon);
        ouder.addBetrokkenheid(ouderBetrokkenheid);
        ouderBetrokkenheid.setPersoon(ouder);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }

    @Test
    public void testPersoonOuderIdentificatieNummersDAG() {
        final BijhoudingPersoon ouder = createPersoon();
        final PersoonIDHistorie historie = new PersoonIDHistorie(ouder);
        historie.setDatumAanvangGeldigheid(20170202);
        ouder.getDelegates().get(0).getPersoonIDHistorieSet().add(historie);
        final BijhoudingPersoon persoon = createPersoon();
        final Relatie familie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie hisFamilie = new RelatieHistorie(familie);
        final Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, familie);
        final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, familie);
        final BetrokkenheidHistorie hisKindBetrokkenheid = new BetrokkenheidHistorie(kindBetrokkenheid);
        kindBetrokkenheid.getBetrokkenheidHistorieSet().add(hisKindBetrokkenheid);
        final BetrokkenheidHistorie hisOuderBetrokkenheid = new BetrokkenheidHistorie(ouderBetrokkenheid);
        ouderBetrokkenheid.getBetrokkenheidHistorieSet().add(hisOuderBetrokkenheid);
        familie.addBetrokkenheid(kindBetrokkenheid);
        familie.addBetrokkenheid(ouderBetrokkenheid);
        persoon.getBetrokkenheidSet().add(kindBetrokkenheid);
        kindBetrokkenheid.setPersoon(persoon);
        ouder.addBetrokkenheid(ouderBetrokkenheid);
        ouderBetrokkenheid.setPersoon(ouder);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }


    @Test
    public void testPersoonOuderIdentificatieNummersDEG() {
        final BijhoudingPersoon ouder = createPersoon();
        final PersoonIDHistorie historie = new PersoonIDHistorie(ouder);
        historie.setDatumAanvangGeldigheid(20150202);
        historie.setDatumEindeGeldigheid(20170202);
        ouder.getDelegates().get(0).getPersoonIDHistorieSet().add(historie);
        final BijhoudingPersoon persoon = createPersoon();
        final Relatie familie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie hisFamilie = new RelatieHistorie(familie);
        final Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, familie);
        final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, familie);
        final BetrokkenheidHistorie hisKindBetrokkenheid = new BetrokkenheidHistorie(kindBetrokkenheid);
        kindBetrokkenheid.getBetrokkenheidHistorieSet().add(hisKindBetrokkenheid);
        final BetrokkenheidHistorie hisOuderBetrokkenheid = new BetrokkenheidHistorie(ouderBetrokkenheid);
        ouderBetrokkenheid.getBetrokkenheidHistorieSet().add(hisOuderBetrokkenheid);
        familie.addBetrokkenheid(kindBetrokkenheid);
        familie.addBetrokkenheid(ouderBetrokkenheid);
        persoon.getBetrokkenheidSet().add(kindBetrokkenheid);
        kindBetrokkenheid.setPersoon(persoon);
        ouder.addBetrokkenheid(ouderBetrokkenheid);
        ouderBetrokkenheid.setPersoon(ouder);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }

    @Test
    public void testPersoonOuderSamenGesteldeNaamDAG() {
        final BijhoudingPersoon ouder = createPersoon();
        final PersoonSamengesteldeNaamHistorie historie = new PersoonSamengesteldeNaamHistorie(ouder, "stam", true, false);
        historie.setDatumAanvangGeldigheid(20170202);
        ouder.getPersoonSamengesteldeNaamHistorieSet().add(historie);
        final BijhoudingPersoon persoon = createPersoon();
        final Relatie familie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie hisFamilie = new RelatieHistorie(familie);
        final Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, familie);
        final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, familie);
        final BetrokkenheidHistorie hisKindBetrokkenheid = new BetrokkenheidHistorie(kindBetrokkenheid);
        kindBetrokkenheid.getBetrokkenheidHistorieSet().add(hisKindBetrokkenheid);
        final BetrokkenheidHistorie hisOuderBetrokkenheid = new BetrokkenheidHistorie(ouderBetrokkenheid);
        ouderBetrokkenheid.getBetrokkenheidHistorieSet().add(hisOuderBetrokkenheid);
        familie.addBetrokkenheid(kindBetrokkenheid);
        familie.addBetrokkenheid(ouderBetrokkenheid);
        persoon.getBetrokkenheidSet().add(kindBetrokkenheid);
        kindBetrokkenheid.setPersoon(persoon);
        ouder.addBetrokkenheid(ouderBetrokkenheid);
        ouderBetrokkenheid.setPersoon(ouder);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }


    @Test
    public void testPersoonOuderSamenGesteldeNaamDEG() {
        final BijhoudingPersoon ouder = createPersoon();
        final PersoonSamengesteldeNaamHistorie historie = new PersoonSamengesteldeNaamHistorie(ouder, "stam", true, false);
        historie.setDatumAanvangGeldigheid(20150202);
        historie.setDatumEindeGeldigheid(20170202);
        ouder.getPersoonSamengesteldeNaamHistorieSet().add(historie);
        final BijhoudingPersoon persoon = createPersoon();
        final Relatie familie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie hisFamilie = new RelatieHistorie(familie);
        final Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, familie);
        final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, familie);
        final BetrokkenheidHistorie hisKindBetrokkenheid = new BetrokkenheidHistorie(kindBetrokkenheid);
        kindBetrokkenheid.getBetrokkenheidHistorieSet().add(hisKindBetrokkenheid);
        final BetrokkenheidHistorie hisOuderBetrokkenheid = new BetrokkenheidHistorie(ouderBetrokkenheid);
        ouderBetrokkenheid.getBetrokkenheidHistorieSet().add(hisOuderBetrokkenheid);
        familie.addBetrokkenheid(kindBetrokkenheid);
        familie.addBetrokkenheid(ouderBetrokkenheid);
        persoon.getBetrokkenheidSet().add(kindBetrokkenheid);
        kindBetrokkenheid.setPersoon(persoon);
        ouder.addBetrokkenheid(ouderBetrokkenheid);
        ouderBetrokkenheid.setPersoon(ouder);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }


    @Test
    public void testPersoonOuderGeslachtsAanduidingDAG() {
        final BijhoudingPersoon ouder = createPersoon();
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(ouder, Geslachtsaanduiding.MAN);
        historie.setDatumAanvangGeldigheid(20170202);
        ouder.getPersoonGeslachtsaanduidingHistorieSet().add(historie);
        final BijhoudingPersoon persoon = createPersoon();
        final Relatie familie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie hisFamilie = new RelatieHistorie(familie);
        final Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, familie);
        final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, familie);
        final BetrokkenheidHistorie hisKindBetrokkenheid = new BetrokkenheidHistorie(kindBetrokkenheid);
        kindBetrokkenheid.getBetrokkenheidHistorieSet().add(hisKindBetrokkenheid);
        final BetrokkenheidHistorie hisOuderBetrokkenheid = new BetrokkenheidHistorie(ouderBetrokkenheid);
        ouderBetrokkenheid.getBetrokkenheidHistorieSet().add(hisOuderBetrokkenheid);
        familie.addBetrokkenheid(kindBetrokkenheid);
        familie.addBetrokkenheid(ouderBetrokkenheid);
        persoon.getBetrokkenheidSet().add(kindBetrokkenheid);
        kindBetrokkenheid.setPersoon(persoon);
        ouder.addBetrokkenheid(ouderBetrokkenheid);
        ouderBetrokkenheid.setPersoon(ouder);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }

    @Test
    public void testPersoonOuderGeslachtsAanduidingDEG() {
        final BijhoudingPersoon ouder = createPersoon();
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(ouder, Geslachtsaanduiding.MAN);
        historie.setDatumAanvangGeldigheid(20150202);
        historie.setDatumEindeGeldigheid(20170202);
        ouder.getPersoonGeslachtsaanduidingHistorieSet().add(historie);
        final BijhoudingPersoon persoon = createPersoon();
        final Relatie familie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie hisFamilie = new RelatieHistorie(familie);
        final Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, familie);
        final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, familie);
        final BetrokkenheidHistorie hisKindBetrokkenheid = new BetrokkenheidHistorie(kindBetrokkenheid);
        kindBetrokkenheid.getBetrokkenheidHistorieSet().add(hisKindBetrokkenheid);
        final BetrokkenheidHistorie hisOuderBetrokkenheid = new BetrokkenheidHistorie(ouderBetrokkenheid);
        ouderBetrokkenheid.getBetrokkenheidHistorieSet().add(hisOuderBetrokkenheid);
        familie.addBetrokkenheid(kindBetrokkenheid);
        familie.addBetrokkenheid(ouderBetrokkenheid);
        persoon.getBetrokkenheidSet().add(kindBetrokkenheid);
        kindBetrokkenheid.setPersoon(persoon);
        ouder.addBetrokkenheid(ouderBetrokkenheid);
        ouderBetrokkenheid.setPersoon(ouder);
        when(verzoekbericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(persoon);
        final List<MeldingElement> meldingen = aHandeling.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2493, meldingen.get(0).getRegel());
    }


    public BijhoudingPersoon createPersoon() {
        final Persoon delegate = new Persoon(SoortPersoon.INGESCHREVENE);
        delegate.setId(1L);
        final BijhoudingPersoon persoon = new BijhoudingPersoon(delegate);
        persoon.setBijhoudingsaard(Bijhoudingsaard.INGEZETENE);
        final PersoonBijhoudingHistorie historie =
                new PersoonBijhoudingHistorie(persoon, PARTIJ_GRONINGEN, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        persoon.getPersoonBijhoudingHistorieSet().add(historie);
        return persoon;
    }


}
