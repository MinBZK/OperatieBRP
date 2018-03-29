/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R2388;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;


public class RegistratieMigratieActieElementTest extends AbstractElementTest {

    private static final LandOfGebied NEDERLAND = new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "NL");
    private static final String PERSOON_18_CURATELE = "5";
    private static final String PERSOON_18 = "4";
    private static final String PERSOON_16_CURATELE = "3";
    private static final String PERSOON_15 = "2";
    private static final String PERSOON_16 = "1";
    private static final String PERSOON_GEBOORTE_ONBEKEND = "0";
    private static final String PERSOON_18_MET_MINDERJARIG_KIND = "6";
    private static final String PERSOON_18_MET_MEERDERJARIG_KIND = "7";
    private static final String PERSOON_18_MET_PARTNER = "8";
    private static final String PERSOON_18_MET_INDICATIE_BVP = "9";
    private BijhoudingVerzoekBericht bericht;

    private ElementBuilder builder;

    @Before
    public void setup() {
        builder = new ElementBuilder();
        setupDynamischeStamTabellen();
        maakBerichtMock();
    }

    @Test(expected = IllegalStateException.class)
    public void valideerInhoudFout() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0000", 'I');
        RegistratieMigratieActieElement actie = maakActie(migElement, "0");
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void valideerInhoudgoed2() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'I');
        RegistratieMigratieActieElement actie = maakActie(migElement, "1");
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R1663_geldigheidLandcode() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0002", 'I');
        RegistratieMigratieActieElement actie = maakActie(migElement, "1");
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1663, meldingen.get(0).getRegel());
    }

    @Test
    public void R2367_leeftijd() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'I');
        RegistratieMigratieActieElement actie = maakActie(migElement, "2");
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2367, meldingen.get(0).getRegel());
    }

    @Test
    public void R2367_Curatele() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'I');
        RegistratieMigratieActieElement actie = maakActie(migElement, "3");
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2367, meldingen.get(0).getRegel());
    }

    @Test
    public void R2368_Leeftijd() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'O');
        RegistratieMigratieActieElement actie = maakActie(migElement, "2");
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2368, meldingen.get(0).getRegel());
    }

    @Test
    public void R2368_geenOuders() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'O');
        RegistratieMigratieActieElement actie = maakActie(migElement, "1");
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2368, meldingen.get(0).getRegel());
    }

    @Test
    public void R2369_MeerderjarigNietCuratele() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'G');
        RegistratieMigratieActieElement actie = maakActie(migElement, "4");
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2369, meldingen.get(0).getRegel());
    }

    @Test
    public void R2369_Curatele() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'G');
        RegistratieMigratieActieElement actie = maakActie(migElement, PERSOON_18_CURATELE);
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(0, meldingen.size());

    }

    @Test
    public void R2369_Minderjarig() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'G');
        RegistratieMigratieActieElement actie = maakActie(migElement, PERSOON_15);
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(0, meldingen.size());

    }

    @Test
    public void R2370_MinderjarigKind() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'K');
        RegistratieMigratieActieElement actie = maakActie(migElement, PERSOON_18_MET_MINDERJARIG_KIND);
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2370, meldingen.get(0).getRegel());
    }

    @Test
    public void R2370_MeerderjarigKind() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'K');
        RegistratieMigratieActieElement actie = maakActie(migElement, PERSOON_18_MET_MEERDERJARIG_KIND);
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R2371_GeenPartner() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'P');
        RegistratieMigratieActieElement actie = maakActie(migElement, PERSOON_18);
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2371, meldingen.get(0).getRegel());
    }

    @Test
    public void R2371_MetPartner() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'P');
        RegistratieMigratieActieElement actie = maakActie(migElement, PERSOON_18_MET_PARTNER);
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R2388_indicatieBVP() throws Exception {
        final MigratieElement migElement = maakMigratieElement("0001", 'I');
        RegistratieMigratieActieElement actie = maakActie(migElement, PERSOON_18_MET_INDICATIE_BVP);
        final List<MeldingElement> meldingen = actie.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2388, meldingen.get(0).getRegel());
    }

    @Test
    public void testVerwerking_BestaandeMigratie_BestaandeDeelnameEu_BijhoudingIngezetene() {
        final Partij partij = new Partij("partij", "000001");
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonMigratieHistorie migratieHistorie = new PersoonMigratieHistorie(persoon, SoortMigratie.EMIGRATIE);

        final PersoonAdres adres = new PersoonAdres(persoon);
        final PersoonAdresHistorie
                adresHistorie =
                new PersoonAdresHistorie(adres, SoortAdres.BRIEFADRES, new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "Nederland"),
                        new RedenWijzigingVerblijf(RedenWijzigingVerblijf.INFRASTRUCTURELE_WIJZIGING, "Infrastructurele wijziging"));
        adres.addPersoonAdresHistorie(adresHistorie);

        final PersoonDeelnameEuVerkiezingenHistorie deelnameEuVerkiezingenHistorie = new PersoonDeelnameEuVerkiezingenHistorie(persoon, true);
        final PersoonBijhoudingHistorie
                bijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);

        persoon.addPersoonAdres(adres);
        persoon.addPersoonMigratieHistorie(migratieHistorie);
        persoon.addPersoonDeelnameEuVerkiezingenHistorie(deelnameEuVerkiezingenHistorie);
        persoon.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        final MigratieElement migratieElement = maakMigratieElement("5095", 'I');
        final String persoonId = "12";
        final RegistratieMigratieActieElement actie = maakActie(migratieElement, persoonId);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, persoonId)).thenReturn(persoon);
        when(getDynamischeStamtabelRepository().getPartijByCode(Partij.PARTIJCODE_MINISTER))
                .thenReturn(new Partij("Minister", Partij.PARTIJCODE_MINISTER));

        actie.getPersoon().getPersoonEntiteit().setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        actie.verwerk(bericht, getAdministratieveHandeling());
        assertEquals(3, persoon.getPersoonMigratieHistorieSet().size());
        assertEquals(1, persoon.getPersoonDeelnameEuVerkiezingenHistorieSet().size());
        final PersoonDeelnameEuVerkiezingenHistorie euVerkiezingenHistorie = persoon.getPersoonDeelnameEuVerkiezingenHistorieSet().iterator().next();
        assertNotNull(euVerkiezingenHistorie.getDatumTijdVerval());

        assertEquals(2, persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().size());
        assertEquals(3, persoon.getPersoonBijhoudingHistorieSet().size());
        final Iterator<PersoonBijhoudingHistorie> iterator = persoon.getPersoonBijhoudingHistorieSet().iterator();
        // Vervallen record
        iterator.next();
        // Beeindigde record
        iterator.next();
        // Nieuwe actuele
        final PersoonBijhoudingHistorie persoonBijhoudingHistorie = iterator.next();
        assertEquals(Bijhoudingsaard.NIET_INGEZETENE, persoonBijhoudingHistorie.getBijhoudingsaard());
        assertEquals(NadereBijhoudingsaard.EMIGRATIE, persoonBijhoudingHistorie.getNadereBijhoudingsaard());
    }

    @Test
    public void testVerwerking_BijhoudingIngezetene_LeegLandOfGebied() {
        final Partij partij = new Partij("partij", "000001");
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);

        persoon.addPersoonAdres(new PersoonAdres(persoon));
        persoon.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        final MigratieElement migratieElement = maakMigratieElement("0000", 'I');
        final String persoonId = "12";
        final RegistratieMigratieActieElement actie = maakActie(migratieElement, persoonId);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, persoonId)).thenReturn(persoon);
        when(getDynamischeStamtabelRepository().getPartijByCode(Partij.PARTIJCODE_MINISTER))
                .thenReturn(new Partij("Minister", Partij.PARTIJCODE_MINISTER));

        actie.getPersoon().getPersoonEntiteit().setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        actie.verwerk(bericht, getAdministratieveHandeling());
        assertEquals(1, persoon.getPersoonMigratieHistorieSet().size());

        assertEquals(0, persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().size());
        assertEquals(3, persoon.getPersoonBijhoudingHistorieSet().size());
        final Iterator<PersoonBijhoudingHistorie> iterator = persoon.getPersoonBijhoudingHistorieSet().iterator();
        // Vervallen record
        iterator.next();
        // Beeindigde record
        iterator.next();
        // Nieuwe actuele
        final PersoonBijhoudingHistorie persoonBijhoudingHistorie = iterator.next();
        assertEquals(Bijhoudingsaard.NIET_INGEZETENE, persoonBijhoudingHistorie.getBijhoudingsaard());
        assertEquals(NadereBijhoudingsaard.VERTROKKEN_ONBEKEND_WAARHEEN, persoonBijhoudingHistorie.getNadereBijhoudingsaard());
    }

    @Test
    public void testVerwerking_BijhoudingIngezetene_RedenWijzigingMinister() {
        final Partij partij = new Partij("partij", "000001");
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);

        persoon.addPersoonAdres(new PersoonAdres(persoon));
        persoon.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        final MigratieElement migratieElement = maakMigratieElement('M', "5058", 'I');
        final String persoonId = "12";
        final RegistratieMigratieActieElement actie = maakActie(migratieElement, persoonId);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, persoonId)).thenReturn(persoon);
        when(getDynamischeStamtabelRepository().getPartijByCode(Partij.PARTIJCODE_MINISTER))
                .thenReturn(new Partij("Minister", Partij.PARTIJCODE_MINISTER));

        actie.getPersoon().getPersoonEntiteit().setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        actie.verwerk(bericht, getAdministratieveHandeling());
        assertEquals(1, persoon.getPersoonMigratieHistorieSet().size());

        assertEquals(0, persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().size());
        assertEquals(3, persoon.getPersoonBijhoudingHistorieSet().size());
        final Iterator<PersoonBijhoudingHistorie> iterator = persoon.getPersoonBijhoudingHistorieSet().iterator();
        // Vervallen record
        iterator.next();
        // Beeindigde record
        iterator.next();
        // Nieuwe actuele
        final PersoonBijhoudingHistorie persoonBijhoudingHistorie = iterator.next();
        assertEquals(Bijhoudingsaard.NIET_INGEZETENE, persoonBijhoudingHistorie.getBijhoudingsaard());
        assertEquals(NadereBijhoudingsaard.BIJZONDERE_STATUS, persoonBijhoudingHistorie.getNadereBijhoudingsaard());
    }

    @Test
    public void testVerwerking_BijhoudingNietIngezetene() {
        final Partij partij = new Partij("partij", "000001");
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.NIET_INGEZETENE, NadereBijhoudingsaard.EMIGRATIE);

        persoon.addPersoonAdres(new PersoonAdres(persoon));
        persoon.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        final MigratieElement migratieElement = maakMigratieElement('M', "5058", 'I');
        final String persoonId = "12";
        final RegistratieMigratieActieElement actie = maakActie(migratieElement, persoonId);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, persoonId)).thenReturn(persoon);
        when(getDynamischeStamtabelRepository().getPartijByCode(Partij.PARTIJCODE_MINISTER))
                .thenReturn(new Partij("Minister", Partij.PARTIJCODE_MINISTER));

        actie.getPersoon().getPersoonEntiteit().setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        actie.verwerk(bericht, getAdministratieveHandeling());
        assertEquals(1, persoon.getPersoonMigratieHistorieSet().size());

        assertEquals(0, persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().size());
        assertEquals(1, persoon.getPersoonBijhoudingHistorieSet().size());
    }

    @Test
    public void testVerwerking_GeenActueleBijhouding() {
        final Partij partij = new Partij("partij", "000001");
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        bijhoudingHistorie.setDatumEindeGeldigheid(20160101);

        persoon.addPersoonAdres(new PersoonAdres(persoon));
        persoon.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        final MigratieElement migratieElement = maakMigratieElement('M', "5058", 'I');
        final String persoonId = "12";
        final RegistratieMigratieActieElement actie = maakActie(migratieElement, persoonId);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, persoonId)).thenReturn(persoon);
        when(getDynamischeStamtabelRepository().getPartijByCode(Partij.PARTIJCODE_MINISTER))
                .thenReturn(new Partij("Minister", Partij.PARTIJCODE_MINISTER));

        actie.getPersoon().getPersoonEntiteit().setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        actie.verwerk(bericht, getAdministratieveHandeling());
        assertEquals(1, persoon.getPersoonMigratieHistorieSet().size());

        assertEquals(0, persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().size());
        assertEquals(1, persoon.getPersoonBijhoudingHistorieSet().size());
    }

    @Test
    public void testVerwerking_PersoonNietVerwerken() {
        final Partij partij = new Partij("partij", "000001");
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        bijhoudingHistorie.setDatumEindeGeldigheid(20160101);

        persoon.addPersoonAdres(new PersoonAdres(persoon));
        persoon.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        final MigratieElement migratieElement = maakMigratieElement('M', "5058", 'I');
        final String persoonId = "12";
        final RegistratieMigratieActieElement actie = maakActie(migratieElement, persoonId);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, persoonId)).thenReturn(persoon);
        when(getDynamischeStamtabelRepository().getPartijByCode(Partij.PARTIJCODE_MINISTER))
                .thenReturn(new Partij("Minister", Partij.PARTIJCODE_MINISTER));

        actie.getPersoon().getPersoonEntiteit().setBijhoudingSituatie(BijhoudingSituatie.GBA);

        actie.verwerk(bericht, getAdministratieveHandeling());
        assertEquals(0, persoon.getPersoonMigratieHistorieSet().size());

        assertEquals(0, persoon.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet().size());
        assertEquals(1, persoon.getPersoonBijhoudingHistorieSet().size());
    }

    private void maakBerichtMock() {
        //gewone persoon
        bericht = mock(BijhoudingVerzoekBericht.class);
        final Persoon delegate = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon persoon = new BijhoudingPersoon(delegate);

        // gewone persoon met geboorte 16

        final BijhoudingPersoon persoon1 = createIngeschrevene(1, 16, null);

        //minderjarige 15jr
        final BijhoudingPersoon persoon2 = createIngeschrevene(2, 15, null);

        // 16 onder curatele
        final BijhoudingPersoon persoon3 = createIngeschrevene(3, 16, SoortIndicatie.ONDER_CURATELE);

        // gewone persoon met geboorte 18
        final BijhoudingPersoon persoon4 = createIngeschrevene(4, 18, null);

        // 18 onder Curatele
        final BijhoudingPersoon persoon5 = createIngeschrevene(5, 18, SoortIndicatie.ONDER_CURATELE);

        // persoon met minderjarig kind
        final BijhoudingPersoon persoon6 = createIngeschrevene(6, 50, null);
        voegKindToeAanPersoon(persoon6, 15);

        // persoon met meerderjarig kind
        final BijhoudingPersoon persoon7 = createIngeschrevene(7, 50, null);
        voegKindToeAanPersoon(persoon7, 19);

        // persoon met Partner
        final BijhoudingPersoon persoon8 = createIngeschrevene(8, 50, null);
        voegPartnerToeAanPersoon(persoon8, 51);

        // persoon met Indicatie BVP
        final BijhoudingPersoon persoon9 = createIngeschrevene(9, 50, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_GEBOORTE_ONBEKEND)).thenReturn(persoon);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_16)).thenReturn(persoon1);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_15)).thenReturn(persoon2);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_16_CURATELE)).thenReturn(persoon3);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_18)).thenReturn(persoon4);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_18_CURATELE)).thenReturn(persoon5);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_18_MET_MINDERJARIG_KIND)).thenReturn(persoon6);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_18_MET_MEERDERJARIG_KIND)).thenReturn(persoon7);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_18_MET_PARTNER)).thenReturn(persoon8);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_18_MET_INDICATIE_BVP)).thenReturn(persoon9);
        when(bericht.getDatumOntvangst()).thenReturn(new DatumElement(DatumUtil.vandaag()));
    }

    private void voegPartnerToeAanPersoon(final BijhoudingPersoon partner, final int leeftijd) {
        Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie relatieHis = new RelatieHistorie(relatie);
        relatie.getRelatieHistorieSet().add(relatieHis);
        Betrokkenheid partner1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final BetrokkenheidHistorie partner1His = new BetrokkenheidHistorie(partner1);
        partner1.addBetrokkenheidHistorie(partner1His);
        Betrokkenheid partner2 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final BetrokkenheidHistorie partner2His = new BetrokkenheidHistorie(partner2);
        partner2.addBetrokkenheidHistorie(partner2His);
        relatie.addBetrokkenheid(partner1);
        relatie.addBetrokkenheid(partner2);
        partner.addBetrokkenheid(partner1);
        partner1.setPersoon(partner);

        Persoon pseudo = new Persoon(SoortPersoon.PSEUDO_PERSOON);
        final PersoonGeboorteHistorie geboorteHistorie = new PersoonGeboorteHistorie(pseudo, DatumUtil.vandaag() - (leeftijd * 10000), NEDERLAND);
        pseudo.getPersoonGeboorteHistorieSet().add(geboorteHistorie);
        pseudo.addBetrokkenheid(partner2);
        partner2.setPersoon(pseudo);
    }


    private void voegKindToeAanPersoon(final BijhoudingPersoon persoon6, final int leeftijd) {
        Relatie relatie6 = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie relatie6His = new RelatieHistorie(relatie6);
        relatie6.getRelatieHistorieSet().add(relatie6His);
        Betrokkenheid ouder6 = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie6);
        final BetrokkenheidHistorie ouder6His = new BetrokkenheidHistorie(ouder6);
        ouder6.addBetrokkenheidHistorie(ouder6His);
        Betrokkenheid kind6 = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie6);
        final BetrokkenheidHistorie kind6His = new BetrokkenheidHistorie(kind6);
        kind6.addBetrokkenheidHistorie(kind6His);
        relatie6.addBetrokkenheid(ouder6);
        relatie6.addBetrokkenheid(kind6);
        persoon6.addBetrokkenheid(ouder6);
        ouder6.setPersoon(persoon6);

        Persoon pseudo = new Persoon(SoortPersoon.PSEUDO_PERSOON);
        final PersoonGeboorteHistorie geboorteHistorie = new PersoonGeboorteHistorie(pseudo, DatumUtil.vandaag() - (leeftijd * 10000), NEDERLAND);
        pseudo.getPersoonGeboorteHistorieSet().add(geboorteHistorie);
        pseudo.addBetrokkenheid(kind6);
        kind6.setPersoon(pseudo);
    }

    private BijhoudingPersoon createIngeschrevene(final int id, final int leeftijd, final SoortIndicatie soort) {
        final Persoon delegate3 = new Persoon(SoortPersoon.INGESCHREVENE);
        delegate3.setId((long) id);
        final BijhoudingPersoon persoon3 = new BijhoudingPersoon(delegate3);
        PersoonGeboorteHistorie his3 = new PersoonGeboorteHistorie(persoon3, DatumUtil.vandaag() - (leeftijd * 10000), NEDERLAND);
        persoon3.getDelegates().get(0).getPersoonGeboorteHistorieSet().add(his3);
        if (soort != null) {
            final PersoonIndicatie indicatie = new PersoonIndicatie(persoon3, soort);
            final PersoonIndicatieHistorie indHis = new PersoonIndicatieHistorie(indicatie, true);
            indicatie.getPersoonIndicatieHistorieSet().add(indHis);
            persoon3.getPersoonIndicatieSet().add(indicatie);
        }
        return persoon3;
    }


    public RegistratieMigratieActieElement maakActie(final MigratieElement migElement, final String persObjSleutel) {
        final ElementBuilder.PersoonParameters pparams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.AdministratieveHandelingParameters ahparams = new ElementBuilder.AdministratieveHandelingParameters();
        RegistratieMigratieActieElement actie = maakMigratieActieElement(pparams, migElement, persObjSleutel);
        ahparams.soort(AdministratieveHandelingElementSoort.VERHUIZING_NAAR_BUITENLAND);
        ahparams.partijCode("123");
        final List<ActieElement> acties = new ArrayList<>();
        acties.add(actie);
        ahparams.acties(acties);
        when(bericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("ah", ahparams));
        return actie;
    }

    public RegistratieMigratieActieElement maakMigratieActieElement(final ElementBuilder.PersoonParameters pparams, final MigratieElement migElement,
                                                                    final String persObjSleutel) {
        pparams.migratie = migElement;
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("pers", persObjSleutel, null, pparams);
        persoon.setVerzoekBericht(bericht);
        final RegistratieMigratieActieElement actie = builder.maakRegistratieMigratieActieElement("mig", 20010101, persoon);
        actie.setVerzoekBericht(bericht);
        return actie;
    }

    private MigratieElement maakMigratieElement(final String landcode, final Character aangever) {
        return maakMigratieElement('P', landcode, aangever);
    }

    private MigratieElement maakMigratieElement(final Character redenWijziging, final String landcode, final Character aangever) {
        final ElementBuilder.MigratieParameters migParams = new ElementBuilder.MigratieParameters(redenWijziging, aangever, landcode);
        return builder.maakMigratie("mig"+redenWijziging+landcode+aangever, migParams);
    }

    private void setupDynamischeStamTabellen() {
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode("0001")).thenReturn(new LandOfGebied("0001", "Duitsland"));
        LandOfGebied dld = new LandOfGebied("0002", "Duitsland");
        dld.setDatumEindeGeldigheid(20000101);
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode("0002")).thenReturn(dld);
    }
}
