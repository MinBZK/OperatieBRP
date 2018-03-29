/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperkingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.hibernate.mapping.Collection;
import org.junit.Before;
import org.junit.Test;

public class RegistratieGeboreneActieElementTest extends AbstractActieMetGeboreneTest {

    private static final LandOfGebied NEDERLAND = new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "Nederland");
    private static final LandOfGebied DUITS = new LandOfGebied("0123", "Duits");
    private static final RedenWijzigingVerblijf AMBTS = new RedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE, "ambts");

    @Before
    public void setup() {
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(BORGER_GEM_CODE)).thenReturn(GEMEENTE_BORGERODOORN);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(BORGER_GEM_CODE2)).thenReturn(GEMEENTE_BORGERODOORN2);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(BORGER_GEM_CODE3)).thenReturn(GEMEENTE_BORGERODOORN3);
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(BORGER)).thenReturn(new Plaats(BORGER));
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(BORGER2)).thenReturn(new Plaats(BORGER2));
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(BORGER3)).thenReturn(new Plaats(BORGER3));
        SoortDocument soortDoc = mock(SoortDocument.class);
        when(soortDoc.getRegistersoort()).thenReturn('3');
        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(anyString())).thenReturn(soortDoc);
    }

    @Test
    public void testGeenMeldingen() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_2);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1681", new Partij("partij", "001681")));
        final BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);
        final Set<PersoonBijhoudingHistorie> bijHis = new LinkedHashSet<>();
        bijHis.add(new PersoonBijhoudingHistorie(bPersoon, PARTIJ_2, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        when(bPersoon.getPersoonBijhoudingHistorieSet()).thenReturn(bijHis);
        final RegistratieGeboreneActieElement actie = maakActieRegistratieGeborene(DatumUtil.gisteren(), bPersoon, false, true, BORGER_GEM_CODE, null);
        controleerRegels(actie.valideerSpecifiekeInhoud());
    }


    @Bedrijfsregel(Regel.R2487)
    @Test
    public void ouderheeftNederlandsAdresZonderBuitenlandregels() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1681", new Partij("partij", "001681")));
        final BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);
        final Set<PersoonAdres> adresSet = new LinkedHashSet<>();
        final PersoonAdres persoonAdres = new PersoonAdres(bPersoon);
        final PersoonAdresHistorie adresHis = new PersoonAdresHistorie(persoonAdres, SoortAdres.WOONADRES, NEDERLAND, AMBTS);
        persoonAdres.addPersoonAdresHistorie(adresHis);
        adresSet.add(persoonAdres);
        when(bPersoon.getPersoonAdresSet()).thenReturn(adresSet);
        final RegistratieGeboreneActieElement actie = maakActieRegistratieGeborene(DatumUtil.gisteren(), bPersoon, false, true, BORGER_GEM_CODE, null);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2487)
    @Test
    public void ouderheeftNederlandsAdresMetBuitenlandregels() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1681", new Partij("partij", "001681")));
        final BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);
        final Set<PersoonAdres> adresSet = new LinkedHashSet<>();
        final PersoonAdres persoonAdres = new PersoonAdres(bPersoon);
        final PersoonAdresHistorie adresHis = new PersoonAdresHistorie(persoonAdres, SoortAdres.WOONADRES, NEDERLAND, AMBTS);
        adresHis.setBuitenlandsAdresRegel1("Buitenland");
        persoonAdres.addPersoonAdresHistorie(adresHis);
        adresSet.add(persoonAdres);
        when(bPersoon.getPersoonAdresSet()).thenReturn(adresSet);
        final RegistratieGeboreneActieElement actie = maakActieRegistratieGeborene(DatumUtil.gisteren(), bPersoon, false, true, BORGER_GEM_CODE, null);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2487, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2487)
    @Test
    public void ouderheeftDuitsAdresMetBuitenlandregels() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1681", new Partij("partij", "001681")));
        final BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);
        final Set<PersoonAdres> adresSet = new LinkedHashSet<>();
        final PersoonAdres persoonAdres = new PersoonAdres(bPersoon);
        final PersoonAdresHistorie adresHis = new PersoonAdresHistorie(persoonAdres, SoortAdres.WOONADRES, DUITS, AMBTS);
        adresHis.setBuitenlandsAdresRegel1("Buitenland");
        persoonAdres.addPersoonAdresHistorie(adresHis);
        adresSet.add(persoonAdres);
        when(bPersoon.getPersoonAdresSet()).thenReturn(adresSet);
        final RegistratieGeboreneActieElement actie = maakActieRegistratieGeborene(DatumUtil.gisteren(), bPersoon, false, true, BORGER_GEM_CODE, null);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }


    @Bedrijfsregel(Regel.R2408)
    @Test
    public void identificatieNummerVerplicht() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1681", new Partij("partij", "001681")));
        final BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);
        final Set<PersoonBijhoudingHistorie> bijHis = new LinkedHashSet<>();
        bijHis.add(new PersoonBijhoudingHistorie(bPersoon, PARTIJ_1, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        when(bPersoon.getPersoonBijhoudingHistorieSet()).thenReturn(bijHis);
        final RegistratieGeboreneActieElement actie = maakActieRegistratieGeborene(DatumUtil.gisteren(), bPersoon, false, false, BORGER_GEM_CODE, null);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2408, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2409)
    @Test
    public void IdentificatieNummersBijAfwijkendePartij() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1681", new Partij("partij", "001681")));
        final BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);
        final Set<PersoonBijhoudingHistorie> bijHis = new LinkedHashSet<>();
        bijHis.add(new PersoonBijhoudingHistorie(bPersoon, PARTIJ_2, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        when(bPersoon.getPersoonBijhoudingHistorieSet()).thenReturn(bijHis);
        final RegistratieGeboreneActieElement actie = maakActieRegistratieGeborene(DatumUtil.gisteren(), bPersoon, false, true, BORGER_GEM_CODE, null);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2409, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2434)
    @Test
    public void anderePartijDientBijhoudingIn() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_2);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1681", new Partij("partij", "001681")));
        final BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);
        final Set<PersoonBijhoudingHistorie> bijHis = new LinkedHashSet<>();
        bijHis.add(new PersoonBijhoudingHistorie(bPersoon, PARTIJ_1, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        when(bPersoon.getPersoonBijhoudingHistorieSet()).thenReturn(bijHis);
        final RegistratieGeboreneActieElement actie = maakActieRegistratieGeborene(DatumUtil.gisteren(), bPersoon, false, false, BORGER_GEM_CODE, null);
        controleerRegels(actie.valideerSpecifiekeInhoud(), Regel.R2434);
    }

    @Bedrijfsregel(Regel.R2434)
    @Test
    public void anderePartijWelkeNietCollegeRolHeeftDientBijhoudingIn() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_3_NIET_ROL_COLLEGE);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1681", new Partij("partij", "001681")));
        final BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);
        final Set<PersoonBijhoudingHistorie> bijHis = new LinkedHashSet<>();
        bijHis.add(new PersoonBijhoudingHistorie(bPersoon, PARTIJ_1, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        when(bPersoon.getPersoonBijhoudingHistorieSet()).thenReturn(bijHis);
        final RegistratieGeboreneActieElement actie = maakActieRegistratieGeborene(DatumUtil.gisteren(), bPersoon, false, false, BORGER_GEM_CODE, null);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2434)
    @Test
    public void bijhoudingsPartijGeboorteGemeenteDientIn() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1681", new Partij("partij", "001681")));
        final BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);
        final Set<PersoonBijhoudingHistorie> bijHis = new LinkedHashSet<>();
        bijHis.add(new PersoonBijhoudingHistorie(bPersoon, PARTIJ_2, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        when(bPersoon.getPersoonBijhoudingHistorieSet()).thenReturn(bijHis);
        final RegistratieGeboreneActieElement actie = maakActieRegistratieGeborene(DatumUtil.gisteren(), bPersoon, false, false, BORGER_GEM_CODE, null);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());

    }

    @Bedrijfsregel(Regel.R2434)
    @Test
    public void bijhoudingsPartijOuderDientBijhoudingIn() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_2);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "1681", new Partij("partij", "001681")));
        final BijhoudingPersoon bPersoon = mock(BijhoudingPersoon.class);
        final Set<PersoonBijhoudingHistorie> bijHis = new LinkedHashSet<>();
        bijHis.add(new PersoonBijhoudingHistorie(bPersoon, PARTIJ_2, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        when(bPersoon.getPersoonBijhoudingHistorieSet()).thenReturn(bijHis);
        final RegistratieGeboreneActieElement actie = maakActieRegistratieGeborene(DatumUtil.gisteren(), bPersoon, false, true, BORGER_GEM_CODE, null);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());

    }

    @Test
    public void testVerwerking_GeenVerwerkbarePersonen() {
        final RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2016_01_01, null, false, false, BORGER_GEM_CODE, null);
        actieElement.getHoofdPersonen().get(0).setBijhoudingSituatie(BijhoudingSituatie.AANVULLEN_EN_OPNIEUW_INDIENEN);

        final BRPActie actie = actieElement.verwerk(getBericht(), getAdministratieveHandeling());
        assertNull(actie);
    }

    @Test
    public void testVerwerking_WelVerwerking() {
        final LandOfGebied land = new LandOfGebied("0001", "NL");
        final Partij partij = new Partij("partij", "000001");

        final BijhoudingPersoon ouderPersoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));

        final PersoonVerstrekkingsbeperking verstrekkingsbeperking = new PersoonVerstrekkingsbeperking(ouderPersoon);
        verstrekkingsbeperking.setPartij(partij);

        final PersoonVerstrekkingsbeperkingHistorie verstrekkingsbeperkingHistorie = new PersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperking);
        verstrekkingsbeperking.addPersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperkingHistorie);
        ouderPersoon.addPersoonVerstrekkingsbeperking(verstrekkingsbeperking);

        final PersoonIndicatie indicatie = new PersoonIndicatie(ouderPersoon, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        ouderPersoon.addPersoonIndicatie(indicatie);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(indicatie, true);
        indicatie.addPersoonIndicatieHistorie(indicatieHistorie);

        final PersoonAdres ouderAdres = new PersoonAdres(ouderPersoon);
        final PersoonAdresHistorie
                ouderAdresHistorie =
                new PersoonAdresHistorie(ouderAdres, SoortAdres.BRIEFADRES, land,
                        new RedenWijzigingVerblijf(RedenWijzigingVerblijf.INFRASTRUCTURELE_WIJZIGING, "infra"));
        ouderAdres.addPersoonAdresHistorie(ouderAdresHistorie);
        ouderPersoon.addPersoonAdres(ouderAdres);

        final RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2016_01_01, ouderPersoon, false, false, BORGER_GEM_CODE, null);
        final BijhoudingPersoon kind = actieElement.getHoofdPersonen().get(0);
        kind.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        kind.setBijhoudingspartijVoorBijhoudingsplan(partij);

        controleerKindGegevens(kind, true);

        when(getBericht().getTijdstipOntvangst()).thenReturn(new DatumTijdElement(Timestamp.from(Instant.now())));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(anyString())).thenReturn(land);
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE))
                .thenReturn(new RedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE, "ambtshalve"));
        builder.initialiseerVerzoekBericht(getBericht());
        final BRPActie actie = actieElement.verwerk(getBericht(), getAdministratieveHandeling());
        assertNotNull(actie);
        controleerKindGegevens(kind, false);
    }

    @Test
    public void testVerwerking_RegistratieNietNLNatInActieMetBVP() {
        final LandOfGebied land = new LandOfGebied("0001", "NL");
        final Partij partij = new Partij("partij", "000001");

        final BijhoudingPersoon ouderPersoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));

        final PersoonVerstrekkingsbeperking verstrekkingsbeperking = new PersoonVerstrekkingsbeperking(ouderPersoon);
        verstrekkingsbeperking.setPartij(partij);

        final PersoonVerstrekkingsbeperkingHistorie verstrekkingsbeperkingHistorie = new PersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperking);
        verstrekkingsbeperking.addPersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperkingHistorie);
        ouderPersoon.addPersoonVerstrekkingsbeperking(verstrekkingsbeperking);

        final PersoonIndicatie indicatie = new PersoonIndicatie(ouderPersoon, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        ouderPersoon.addPersoonIndicatie(indicatie);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(indicatie, true);
        indicatie.addPersoonIndicatieHistorie(indicatieHistorie);

        final PersoonAdres ouderAdres = new PersoonAdres(ouderPersoon);
        final PersoonAdresHistorie
                ouderAdresHistorie =
                new PersoonAdresHistorie(ouderAdres, SoortAdres.BRIEFADRES, land,
                        new RedenWijzigingVerblijf(RedenWijzigingVerblijf.INFRASTRUCTURELE_WIJZIGING, "infra"));
        ouderAdres.addPersoonAdresHistorie(ouderAdresHistorie);
        ouderPersoon.addPersoonAdres(ouderAdres);

        final ElementBuilder.PersoonParameters natPara = new ElementBuilder.PersoonParameters();
        final List<NationaliteitElement> nat = new LinkedList<>();
        nat.add(builder.maakNationaliteitElement("kindNatp", "0046", null));
        natPara.nationaliteiten(nat);
        final PersoonGegevensElement natPersElement = builder.maakPersoonGegevensElement("nat_pers", null, "kind", natPara);
        final RegistratieNationaliteitActieElement regNatActieElement = builder.maakRegistratieNationaliteitActieElement("nat", 2016_01_01, natPersElement);
        final RegistratieGeboreneActieElement
                actieElement =
                maakActieRegistratieGeborene(2016_01_01, ouderPersoon, false, false, BORGER_GEM_CODE, regNatActieElement);
        final BijhoudingPersoon kind = actieElement.getHoofdPersonen().get(0);
        kind.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        kind.setBijhoudingspartijVoorBijhoudingsplan(partij);

        controleerKindGegevens(kind, true);

        when(getBericht().getTijdstipOntvangst()).thenReturn(new DatumTijdElement(Timestamp.from(Instant.now())));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(anyString())).thenReturn(land);
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE))
                .thenReturn(new RedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE, "ambtshalve"));
        builder.initialiseerVerzoekBericht(getBericht());
        final BRPActie actie = actieElement.verwerk(getBericht(), getAdministratieveHandeling());
        assertNotNull(actie);
        assertNotNull(kind.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE));
    }

    @Test
    public void testVerwerking_RegistratieNLNatInActieMetBVP() {
        final LandOfGebied land = new LandOfGebied("0001", "NL");
        final Partij partij = new Partij("partij", "000001");

        final BijhoudingPersoon ouderPersoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));

        final PersoonVerstrekkingsbeperking verstrekkingsbeperking = new PersoonVerstrekkingsbeperking(ouderPersoon);
        verstrekkingsbeperking.setPartij(partij);

        final PersoonVerstrekkingsbeperkingHistorie verstrekkingsbeperkingHistorie = new PersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperking);
        verstrekkingsbeperking.addPersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperkingHistorie);
        ouderPersoon.addPersoonVerstrekkingsbeperking(verstrekkingsbeperking);

        final PersoonIndicatie indicatie = new PersoonIndicatie(ouderPersoon, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        ouderPersoon.addPersoonIndicatie(indicatie);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(indicatie, true);
        indicatie.addPersoonIndicatieHistorie(indicatieHistorie);

        final PersoonAdres ouderAdres = new PersoonAdres(ouderPersoon);
        final PersoonAdresHistorie
                ouderAdresHistorie =
                new PersoonAdresHistorie(ouderAdres, SoortAdres.BRIEFADRES, land,
                        new RedenWijzigingVerblijf(RedenWijzigingVerblijf.INFRASTRUCTURELE_WIJZIGING, "infra"));
        ouderAdres.addPersoonAdresHistorie(ouderAdresHistorie);
        ouderPersoon.addPersoonAdres(ouderAdres);

        final ElementBuilder.PersoonParameters natPara = new ElementBuilder.PersoonParameters();
        final List<NationaliteitElement> nat = new LinkedList<>();
        nat.add(builder.maakNationaliteitElement("kindNatp", "0001", null));
        natPara.nationaliteiten(nat);
        final PersoonGegevensElement natPersElement = builder.maakPersoonGegevensElement("nat_pers", null, "kind", natPara);
        final RegistratieNationaliteitActieElement regNatActieElement = builder.maakRegistratieNationaliteitActieElement("nat", 2016_01_01, natPersElement);
        final RegistratieGeboreneActieElement
                actieElement =
                maakActieRegistratieGeborene(2016_01_01, ouderPersoon, false, false, BORGER_GEM_CODE, regNatActieElement);
        final BijhoudingPersoon kind = actieElement.getHoofdPersonen().get(0);
        kind.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        kind.setBijhoudingspartijVoorBijhoudingsplan(partij);

        controleerKindGegevens(kind, true);

        when(getBericht().getTijdstipOntvangst()).thenReturn(new DatumTijdElement(Timestamp.from(Instant.now())));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(anyString())).thenReturn(land);
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE))
                .thenReturn(new RedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE, "ambtshalve"));
        builder.initialiseerVerzoekBericht(getBericht());
        final BRPActie actie = actieElement.verwerk(getBericht(), getAdministratieveHandeling());
        assertNotNull(actie);
        assertNull(kind.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE));
    }

    @Test
    public void testVerwerking_WelVerwerking_VerstrekkingsbeperkingPartijNietGeldig() {
        final LandOfGebied land = new LandOfGebied("0001", "NL");
        final Partij partij = new Partij("partij", "000001");

        final BijhoudingPersoon ouderPersoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));

        final PersoonVerstrekkingsbeperking verstrekkingsbeperking = new PersoonVerstrekkingsbeperking(ouderPersoon);
        verstrekkingsbeperking.setPartij(partij);
        partij.setDatumEinde(2015_01_01);

        final PersoonVerstrekkingsbeperkingHistorie verstrekkingsbeperkingHistorie = new PersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperking);
        verstrekkingsbeperking.addPersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperkingHistorie);
        ouderPersoon.addPersoonVerstrekkingsbeperking(verstrekkingsbeperking);

        final RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2016_01_01, ouderPersoon, false, false, BORGER_GEM_CODE, null);
        final BijhoudingPersoon kind = actieElement.getHoofdPersonen().get(0);
        kind.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        kind.setBijhoudingspartijVoorBijhoudingsplan(partij);

        controleerKindGegevens(kind, true);

        when(getBericht().getTijdstipOntvangst()).thenReturn(new DatumTijdElement(Timestamp.from(Instant.now())));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(anyString())).thenReturn(land);
        builder.initialiseerVerzoekBericht(getBericht());
        final BRPActie actie = actieElement.verwerk(getBericht(), getAdministratieveHandeling());
        assertNotNull(actie);
        assertEquals(0, kind.getPersoonVerstrekkingsbeperkingSet().size());
        assertEquals(0, kind.getPersoonAdresSet().size());
        assertNull(kind.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE));
    }

    @Test
    public void testVerwerking_WelVerwerking_VerstrekkingsbeperkingPartijLeeg() {
        final LandOfGebied land = new LandOfGebied("0001", "NL");
        final Partij partij = new Partij("partij", "000001");

        final BijhoudingPersoon ouderPersoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));

        final PersoonVerstrekkingsbeperking verstrekkingsbeperking = new PersoonVerstrekkingsbeperking(ouderPersoon);
        verstrekkingsbeperking.setPartij(null);
        partij.setDatumEinde(2015_01_01);

        final PersoonVerstrekkingsbeperkingHistorie verstrekkingsbeperkingHistorie = new PersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperking);
        verstrekkingsbeperking.addPersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperkingHistorie);
        ouderPersoon.addPersoonVerstrekkingsbeperking(verstrekkingsbeperking);

        final RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2016_01_01, ouderPersoon, false, false, BORGER_GEM_CODE, null);
        final BijhoudingPersoon kind = actieElement.getHoofdPersonen().get(0);
        kind.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        kind.setBijhoudingspartijVoorBijhoudingsplan(partij);

        controleerKindGegevens(kind, true);

        when(getBericht().getTijdstipOntvangst()).thenReturn(new DatumTijdElement(Timestamp.from(Instant.now())));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(anyString())).thenReturn(land);
        builder.initialiseerVerzoekBericht(getBericht());
        final BRPActie actie = actieElement.verwerk(getBericht(), getAdministratieveHandeling());
        assertNotNull(actie);
        assertEquals(1, kind.getPersoonVerstrekkingsbeperkingSet().size());
    }


    @Test
    public void testVerwerking_WelVerwerking_VerstrekkingsbeperkingNietActueel() {
        final LandOfGebied land = new LandOfGebied("0001", "NL");
        final Partij partij = new Partij("partij", "000001");

        final BijhoudingPersoon ouderPersoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));

        final PersoonVerstrekkingsbeperking verstrekkingsbeperking = new PersoonVerstrekkingsbeperking(ouderPersoon);
        verstrekkingsbeperking.setPartij(partij);

        final PersoonVerstrekkingsbeperkingHistorie verstrekkingsbeperkingHistorie = new PersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperking);
        verstrekkingsbeperkingHistorie.setDatumTijdVerval(Timestamp.from(Instant.now()));
        verstrekkingsbeperking.addPersoonVerstrekkingsbeperkingHistorie(verstrekkingsbeperkingHistorie);
        ouderPersoon.addPersoonVerstrekkingsbeperking(verstrekkingsbeperking);

        final RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2016_01_01, ouderPersoon, false, false, BORGER_GEM_CODE, null);
        final BijhoudingPersoon kind = actieElement.getHoofdPersonen().get(0);
        kind.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        kind.setBijhoudingspartijVoorBijhoudingsplan(partij);

        controleerKindGegevens(kind, true);

        when(getBericht().getTijdstipOntvangst()).thenReturn(new DatumTijdElement(Timestamp.from(Instant.now())));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(anyString())).thenReturn(land);

        builder.initialiseerVerzoekBericht(getBericht());
        final BRPActie actie = actieElement.verwerk(getBericht(), getAdministratieveHandeling());
        assertNotNull(actie);
        assertEquals(0, kind.getPersoonVerstrekkingsbeperkingSet().size());
        assertEquals(0, kind.getPersoonAdresSet().size());
        assertNull(kind.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE));
    }

    @Test
    public void testVerwerking_WelVerwerking_MetVerstrekkingsbeperkingActie_AdresVerlopen_BVP_Verlopen() {
        final LandOfGebied land = new LandOfGebied("0001", "NL");
        final Partij partij = new Partij("partij", "000001");

        final BijhoudingPersoon ouderPersoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));

        final PersoonIndicatie indicatie = new PersoonIndicatie(ouderPersoon, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        ouderPersoon.addPersoonIndicatie(indicatie);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(indicatie, true);
        indicatieHistorie.setDatumEindeGeldigheid(2015_01_01);
        indicatie.addPersoonIndicatieHistorie(indicatieHistorie);

        final PersoonAdres ouderAdres = new PersoonAdres(ouderPersoon);
        final PersoonAdresHistorie
                ouderAdresHistorie =
                new PersoonAdresHistorie(ouderAdres, SoortAdres.BRIEFADRES, land,
                        new RedenWijzigingVerblijf(RedenWijzigingVerblijf.INFRASTRUCTURELE_WIJZIGING, "infra"));
        ouderAdresHistorie.setDatumEindeGeldigheid(2015_01_01);
        ouderAdres.addPersoonAdresHistorie(ouderAdresHistorie);
        ouderPersoon.addPersoonAdres(ouderAdres);

        final RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2016_01_01, ouderPersoon, true, false, BORGER_GEM_CODE, null);
        final BijhoudingPersoon kind = actieElement.getHoofdPersonen().get(0);
        kind.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        kind.setBijhoudingspartijVoorBijhoudingsplan(partij);

        controleerKindGegevens(kind, true);

        when(getBericht().getTijdstipOntvangst()).thenReturn(new DatumTijdElement(Timestamp.from(Instant.now())));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(anyString())).thenReturn(land);
        builder.initialiseerVerzoekBericht(getBericht());
        final BRPActie actie = actieElement.verwerk(getBericht(), getAdministratieveHandeling());
        assertNotNull(actie);
        assertEquals(0, kind.getPersoonVerstrekkingsbeperkingSet().size());
        assertEquals(0, kind.getPersoonAdresSet().size());
        assertNull(kind.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE));
    }

    private void controleerKindGegevens(final BijhoudingPersoon kind, final boolean isLeeg) {
        assertEquals(isLeeg, kind.getPersoonInschrijvingHistorieSet().isEmpty());
        assertEquals(isLeeg, kind.getPersoonBijhoudingHistorieSet().isEmpty());
        assertEquals(isLeeg, kind.getPersoonGeslachtsaanduidingHistorieSet().isEmpty());
        assertEquals(isLeeg, kind.getPersoonGeboorteHistorieSet().isEmpty());
        assertEquals(isLeeg, kind.getPersoonVoornaamSet().isEmpty());
        assertEquals(isLeeg, kind.getPersoonGeslachtsnaamcomponentSet().isEmpty());
        assertEquals(isLeeg, kind.getPersoonSamengesteldeNaamHistorieSet().isEmpty());
        assertEquals(isLeeg, kind.getPersoonNaamgebruikHistorieSet().isEmpty());
        assertEquals(isLeeg, kind.getPersoonVerstrekkingsbeperkingSet().isEmpty());
        assertEquals(isLeeg, kind.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE) == null);
        assertEquals(isLeeg, kind.getPersoonAdresSet().isEmpty());
    }

    @Test
    public void testGemeenteGeboorteRegisterGemeente() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);
        BijhoudingPersoon ouder = mock(BijhoudingPersoon.class);

        SoortDocument soortDocument = mock(SoortDocument.class);
        when(soortDocument.getRegistersoort()).thenReturn('3');
        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(anyString())).thenReturn(soortDocument);
        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(anyString()))
                .thenReturn(new Gemeente((short) 3, "naam", "4321", new Partij("partij", "000003")));
        RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2016_01_01, ouder, false, false, "1234", null);
        final List<MeldingElement> meldingen = actieElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2435, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1690)
    @Test
    public void testDerdeGeneratieKind() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);
        BijhoudingPersoon ouder = mock(BijhoudingPersoon.class);
        when(ouder.isIngezeteneOpPeildatum(anyInt())).thenReturn(true);

        final Persoon persoon = mock(Persoon.class);
        final PersoonBijhoudingHistorie persBijhoudingHistorie = new PersoonBijhoudingHistorie(persoon,PARTIJ_1,Bijhoudingsaard.INGEZETENE,NadereBijhoudingsaard.ACTUEEL);
        when(persoon.getPersoonBijhoudingHistorieSet()).thenReturn(Collections.singleton(persBijhoudingHistorie));
        when(ouder.getOuders(anyInt())).thenReturn(Collections.singleton(persoon));


        final ElementBuilder.PersoonParameters natPara = new ElementBuilder.PersoonParameters();
        final List<NationaliteitElement> nat = new LinkedList<>();
        nat.add(builder.maakNationaliteitElement("kindNatp", "0046", null));
        natPara.nationaliteiten(nat);
        final PersoonGegevensElement natPersElement = builder.maakPersoonGegevensElement("nat_pers", null, "kind", natPara);
        final RegistratieNationaliteitActieElement regNatActieElement = builder.maakRegistratieNationaliteitActieElement("nat", 2016_01_01, natPersElement);

        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(anyString())).thenReturn(null);
        RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2017_01_01, ouder, false, false, "1234", regNatActieElement);
        controleerRegels(actieElement.valideerSpecifiekeInhoud(), Regel.R1690);
    }

    @Test
    @Bedrijfsregel(Regel.R1690)
    public void testPersoonIsNederlands() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);
        BijhoudingPersoon ouder = mock(BijhoudingPersoon.class);

        final ElementBuilder.PersoonParameters natPara = new ElementBuilder.PersoonParameters();
        final List<NationaliteitElement> nat = new LinkedList<>();
        nat.add(builder.maakNationaliteitElement("kindNatp", "0001", "001"));
        natPara.nationaliteiten(nat);
        final PersoonGegevensElement natPersElement = builder.maakPersoonGegevensElement("nat_pers", null, KIND_PERSOON_COMM_ID, natPara);
        final RegistratieNationaliteitActieElement regNatActieElement = builder.maakRegistratieNationaliteitActieElement("nat", 2016_01_01, natPersElement);

        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(anyString())).thenReturn(null);
        final RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2017_01_01, ouder, false, false, "1234", regNatActieElement);
        actieElement.getHoofdPersonen().get(0).registreerPersoonElement(natPersElement);
        builder.initialiseerVerzoekBericht(getBericht());
        controleerRegels(actieElement.valideerSpecifiekeInhoud());
    }

    @Test
    @Bedrijfsregel(Regel.R1690)
    public void testOuderIsPseudo() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);

        final ElementBuilder.PersoonParameters natPara = new ElementBuilder.PersoonParameters();
        final List<NationaliteitElement> nat = new LinkedList<>();
        nat.add(builder.maakNationaliteitElement("kindNatp", "0034", null));
        natPara.nationaliteiten(nat);
        final PersoonGegevensElement natPersElement = builder.maakPersoonGegevensElement("nat_pers", null, KIND_PERSOON_COMM_ID, natPara);
        final RegistratieNationaliteitActieElement regNatActieElement = builder.maakRegistratieNationaliteitActieElement("nat", 2016_01_01, natPersElement);

        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(anyString())).thenReturn(null);
        final RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2017_01_01, null, false, false, "1234", regNatActieElement);
        actieElement.getHoofdPersonen().get(0).registreerPersoonElement(natPersElement);
        builder.initialiseerVerzoekBericht(getBericht());

        controleerRegels(actieElement.valideerSpecifiekeInhoud());
    }

    @Test
    @Bedrijfsregel(Regel.R1690)
    public void testOuderIsNietIngezetene() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);
        final BijhoudingPersoon ouder = mock(BijhoudingPersoon.class);
        final ElementBuilder.PersoonParameters natPara = new ElementBuilder.PersoonParameters();
        final List<NationaliteitElement> nat = new LinkedList<>();
        nat.add(builder.maakNationaliteitElement("kindNatp", "0034", null));
        natPara.nationaliteiten(nat);
        final PersoonGegevensElement natPersElement = builder.maakPersoonGegevensElement("nat_pers", null, KIND_PERSOON_COMM_ID, natPara);
        final RegistratieNationaliteitActieElement regNatActieElement = builder.maakRegistratieNationaliteitActieElement("nat", 2016_01_01, natPersElement);

        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(anyString())).thenReturn(null);
        final RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2017_01_01, ouder, false, false, "1234", regNatActieElement);
        actieElement.getHoofdPersonen().get(0).registreerPersoonElement(natPersElement);
        when(ouder.isIngezeteneOpPeildatum(anyInt())).thenReturn(false);
        builder.initialiseerVerzoekBericht(getBericht());

        controleerRegels(actieElement.valideerSpecifiekeInhoud());
    }

    @Test
    @Bedrijfsregel(Regel.R1690)
    public void testGrootOuderNietIngezetene() {
        when(getBericht().getZendendePartij()).thenReturn(PARTIJ_1);
        final BijhoudingPersoon ouder = mock(BijhoudingPersoon.class);
        final ElementBuilder.PersoonParameters natPara = new ElementBuilder.PersoonParameters();
        final List<NationaliteitElement> nat = new LinkedList<>();
        nat.add(builder.maakNationaliteitElement("kindNatp", "0034", null));
        natPara.nationaliteiten(nat);
        final PersoonGegevensElement natPersElement = builder.maakPersoonGegevensElement("nat_pers", null, KIND_PERSOON_COMM_ID, natPara);
        final RegistratieNationaliteitActieElement regNatActieElement = builder.maakRegistratieNationaliteitActieElement("nat", 2016_01_01, natPersElement);

        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(anyString())).thenReturn(null);
        final RegistratieGeboreneActieElement actieElement = maakActieRegistratieGeborene(2017_01_01, ouder, false, false, "1234", regNatActieElement);
        actieElement.getHoofdPersonen().get(0).registreerPersoonElement(natPersElement);
        when(ouder.isIngezeteneOpPeildatum(anyInt())).thenReturn(true);

        final Betrokkenheid grootouderBetrokkenheid = mock(Betrokkenheid.class);
        final Persoon grootouder = mock(Persoon.class);
        final PersoonBijhoudingHistorie bijhoudingHistorie = new PersoonBijhoudingHistorie(grootouder, new Partij("naam", "000000"), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        bijhoudingHistorie.setDatumAanvangGeldigheid(2012_01_01);
        bijhoudingHistorie.setDatumEindeGeldigheid(2016_01_01);
        when(ouder.getActueleOuders()).thenReturn(Collections.singleton(grootouderBetrokkenheid));
        when(grootouderBetrokkenheid.getPersoon()).thenReturn(grootouder);
        when(grootouder.getPersoonBijhoudingHistorieSet()).thenReturn(Collections.singleton(bijhoudingHistorie));

        builder.initialiseerVerzoekBericht(getBericht());

        controleerRegels(actieElement.valideerSpecifiekeInhoud());
    }
}
