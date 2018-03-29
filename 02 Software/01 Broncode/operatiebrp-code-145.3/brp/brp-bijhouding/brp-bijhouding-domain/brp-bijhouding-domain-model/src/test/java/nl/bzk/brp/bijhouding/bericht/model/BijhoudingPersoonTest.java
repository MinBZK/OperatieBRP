/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.*;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Test;

/**
 * Testen voor {@link BijhoudingPersoon}.
 */
public class BijhoudingPersoonTest extends PersoonElementTest {

    private static final Timestamp TS_19810101 = Timestamp.valueOf("1981-01-01 00:00:00.0");
    private static final Timestamp TS_19810201 = Timestamp.valueOf("1981-02-01 00:00:00.0");
    private static final Timestamp TS_19810301 = Timestamp.valueOf("1981-03-01 00:00:00.0");
    private static final Integer DATUM_19810101 = 19810101;
    private static final LandOfGebied NEDERLAND = new LandOfGebied("6030", "Nederland");
    private static final Partij PARTIJ_1 = new Partij("Partij_1", "000001");
    private static final Partij PARTIJ_2 = new Partij("Partij_2", "000002");
    private static final Partij PARTIJ_3 = new Partij("Partij_3", "000003");
    private static final Gemeente GEMEENTE_1 = new Gemeente((short) 1, "gemeente_1", "0001", PARTIJ_1);
    private static final Gemeente GEMEENTE_2 = new Gemeente((short) 2, "gemeente_2", "0002", PARTIJ_2);
    private static final Gemeente GEMEENTE_3 = new Gemeente((short) 3, "gemeente_3", "0003", PARTIJ_3);

    @Test
    public void testGetActueleIndicatie() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        assertNull(bijhoudingPersoon.getActueleIndicatie(SoortIndicatie.STAATLOOS));
        final PersoonIndicatie staatloos = new PersoonIndicatie(persoon, SoortIndicatie.STAATLOOS);
        PersoonIndicatieHistorie staatloosHistorie = new PersoonIndicatieHistorie(staatloos, true);
        staatloosHistorie.setDatumAanvangGeldigheid(20010101);
        staatloosHistorie.setActieInhoud(mock(BRPActie.class));
        staatloosHistorie.setDatumTijdRegistratie(TS_19810101);
        MaterieleHistorie.voegNieuweActueleToe(staatloosHistorie, staatloos.getPersoonIndicatieHistorieSet());
        persoon.addPersoonIndicatie(staatloos);
        assertNotNull(bijhoudingPersoon.getActueleIndicatie(SoortIndicatie.STAATLOOS));
        BRPActie actieVerval = mock(BRPActie.class);
        MaterieleHistorie.beeindigActueelVoorkomen(staatloos.getPersoonIndicatieHistorieSet(), actieVerval, TS_19810201, 20010101);
        assertNull(bijhoudingPersoon.getActueleIndicatie(SoortIndicatie.STAATLOOS));
    }

    @Test
    public void testDecorate() {
        assertNull(BijhoudingPersoon.decorate(null));
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.PSEUDO_PERSOON));
        assertNotNull(bijhoudingPersoon);
        assertEquals(SoortPersoon.PSEUDO_PERSOON, bijhoudingPersoon.getSoortPersoon());
        assertFalse(bijhoudingPersoon.isEersteInschrijving());
    }

    @Test
    public void testAantalNationaliteitenOfIndicatieStaatloos() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        assertEquals(0, bijhoudingPersoon.aantalNationaliteitenOfIndicatieStaatloos());
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("nl", Nationaliteit.NEDERLANDSE));
        final PersoonNationaliteitHistorie persoonNationaliteitHistorie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        persoonNationaliteitHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoonNationaliteit.getPersoonNationaliteitHistorieSet().add(persoonNationaliteitHistorie);
        bijhoudingPersoon.addPersoonNationaliteit(persoonNationaliteit);
        assertEquals(1, bijhoudingPersoon.aantalNationaliteitenOfIndicatieStaatloos());
        final PersoonNationaliteit persoonNationaliteit2 = new PersoonNationaliteit(persoon, new Nationaliteit("anders", "0002"));
        final PersoonNationaliteitHistorie persoonNationaliteitHistorie2 = new PersoonNationaliteitHistorie(persoonNationaliteit2);
        persoonNationaliteitHistorie2.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoonNationaliteit2.getPersoonNationaliteitHistorieSet().add(persoonNationaliteitHistorie2);
        bijhoudingPersoon.addPersoonNationaliteit(persoonNationaliteit2);
        assertEquals(2, bijhoudingPersoon.aantalNationaliteitenOfIndicatieStaatloos());
    }

    @Test
    public void testAantalNationaliteitenOfIndicatieStaatloosTrue() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        assertEquals(0, bijhoudingPersoon.aantalNationaliteitenOfIndicatieStaatloos());
        final PersoonIndicatie staatloos2 = new PersoonIndicatie(persoon, SoortIndicatie.STAATLOOS);
        final PersoonIndicatieHistorie staatloos2Historie = new PersoonIndicatieHistorie(staatloos2, true);
        staatloos2.addPersoonIndicatieHistorie(staatloos2Historie);
        bijhoudingPersoon.addPersoonIndicatie(staatloos2);
        assertEquals(1, bijhoudingPersoon.aantalNationaliteitenOfIndicatieStaatloos());
    }

    @Test
    public void testAantalNationaliteitenOfIndicatieStaatloosFalse() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        assertEquals(0, bijhoudingPersoon.aantalNationaliteitenOfIndicatieStaatloos());
        final PersoonIndicatie staatloos2 = new PersoonIndicatie(persoon, SoortIndicatie.STAATLOOS);
        final PersoonIndicatieHistorie staatloos2Historie = new PersoonIndicatieHistorie(staatloos2, false);
        staatloos2.addPersoonIndicatieHistorie(staatloos2Historie);
        bijhoudingPersoon.addPersoonIndicatie(staatloos2);
        assertEquals(0, bijhoudingPersoon.aantalNationaliteitenOfIndicatieStaatloos());
    }

    @Test
    public void testGetActuelePartijGeboorteGemeente() {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        assertNull(bijhoudingPersoon.getActuelePartijGeboorteGemeente());
        final PersoonGeboorteHistorie geboorteHistorie = new PersoonGeboorteHistorie(persoon, 2000_01_01, NEDERLAND);
        geboorteHistorie.setDatumTijdRegistratie(new Timestamp(DatumUtil.nu().getTime() - 1));
        persoon.addPersoonGeboorteHistorie(geboorteHistorie);
        assertNull(bijhoudingPersoon.getActuelePartijGeboorteGemeente());
        final PersoonGeboorteHistorie geboorteHistorie2 = new PersoonGeboorteHistorie(persoon, 2000_01_01, NEDERLAND);
        geboorteHistorie2.setGemeente(GEMEENTE_2);
        geboorteHistorie2.setDatumTijdRegistratie(new Timestamp(DatumUtil.nu().getTime()));
        FormeleHistorieZonderVerantwoording.voegToe(geboorteHistorie2, persoon.getPersoonGeboorteHistorieSet());
        assertEquals("000002", bijhoudingPersoon.getActuelePartijGeboorteGemeente().getCode());
        final Gemeente gemeente = new Gemeente((short) 9, "gemeente", "0009", PARTIJ_2);
        gemeente.setVoortzettendeGemeente(GEMEENTE_3);

        final PersoonGeboorteHistorie geboorteHistorie3 = new PersoonGeboorteHistorie(persoon, 2000_01_01, NEDERLAND);
        geboorteHistorie3.setGemeente(gemeente);
        geboorteHistorie3.setDatumTijdRegistratie(new Timestamp(DatumUtil.nu().getTime()));
        FormeleHistorieZonderVerantwoording.voegToe(geboorteHistorie3, persoon.getPersoonGeboorteHistorieSet());
        assertEquals("000003", bijhoudingPersoon.getActuelePartijGeboorteGemeente().getCode());
    }

    @Test
    public void testGetTijdstipVoorLaatsteWijziging() {
        final BijhoudingPersoon nieuwPersoon = new BijhoudingPersoon();
        assertNull(nieuwPersoon.getTijdstipVoorlaatsteWijziging());

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon persoonGeenAfgeleidAdministratief = BijhoudingPersoon.decorate(persoon);
        assertNull(persoonGeenAfgeleidAdministratief.getTijdstipVoorlaatsteWijziging());

        final Timestamp nu = Timestamp.from(Instant.now());
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(new Partij("partij", "000001"), SoortAdministratieveHandeling.GEBOORTE, nu);
        persoon.addPersoonAfgeleidAdministratiefHistorie(
                new PersoonAfgeleidAdministratiefHistorie(Short.parseShort("1"), persoon, administratieveHandeling, nu));
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        assertEquals(nu, bijhoudingPersoon.getTijdstipVoorlaatsteWijziging());

        final Timestamp straks = Timestamp.from(Instant.now().plus(1, ChronoUnit.HOURS));
        bijhoudingPersoon.addPersoonAfgeleidAdministratiefHistorie(
                new PersoonAfgeleidAdministratiefHistorie(Short.parseShort("1"), persoon, administratieveHandeling, straks));
        assertNotEquals(straks, bijhoudingPersoon.getTijdstipVoorlaatsteWijziging());
    }

    @Test
    public void testRegistreerPersoonElement() {
        final ElementBuilder builder = new ElementBuilder();
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", "1234");
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon();
        assertEquals(0, bijhoudingPersoon.getPersoonElementen().size());

        bijhoudingPersoon.registreerPersoonElement(persoonElement);

        final List<PersoonElement> persoonElementen = bijhoudingPersoon.getPersoonElementen();
        assertEquals(1, persoonElementen.size());
        assertEquals(persoonElement, persoonElementen.get(0));
    }

    @Test
    public void testBijhoudingSituatie() {
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon();
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AANVULLEN_EN_OPNIEUW_INDIENEN);
        assertEquals(BijhoudingSituatie.AANVULLEN_EN_OPNIEUW_INDIENEN, bijhoudingPersoon.getBijhoudingSituatie());
    }

    @Test
    public void testBijhoudingspartijVoorBijhoudingsplan() {
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon();
        final Partij partij = new Partij("partij", "000001");
        bijhoudingPersoon.setBijhoudingspartijVoorBijhoudingsplan(partij);
        assertEquals(partij, bijhoudingPersoon.getBijhoudingspartijVoorBijhoudingsplan());
    }

    @Test
    public void testGetBsnOrNull() {
        final BijhoudingPersoon bijhoudingNieuwPersoon = new BijhoudingPersoon();
        assertNull(bijhoudingNieuwPersoon.getBsnOrNull());

        final String bsn = "123456789";
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(persoon);
        idHistorie.setBurgerservicenummer(bsn);
        persoon.addPersoonIDHistorie(idHistorie);

        final BijhoudingPersoon bijhoudingBestaandPersoon = BijhoudingPersoon.decorate(persoon);
        assertEquals(bsn, bijhoudingBestaandPersoon.getBsnOrNull());
    }

    @Test
    public void testGetActueelBurgerservicenummer_NieuwPersoon() {
        final BijhoudingPersoon bijhoudingNieuwPersoon = new BijhoudingPersoon();
        assertNull(bijhoudingNieuwPersoon.getActueelBurgerservicenummer());

        final String bsn = "123456789";
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters geboorteParams = new ElementBuilder.GeboorteParameters();
        geboorteParams.datum(20160101);
        persoonParameters.geboorte(builder.maakGeboorteElement("geboorte", geboorteParams));
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParameters);
        bijhoudingNieuwPersoon.registreerPersoonElement(persoonElement);
        assertNull(bijhoudingNieuwPersoon.getActueelBurgerservicenummer());

        persoonParameters.identificatienummers(builder.maakIdentificatienummersElement("ident", String.valueOf(bsn), null));
        final PersoonElement persoonElement2 = builder.maakPersoonGegevensElement("persoon2", null, null, persoonParameters);
        bijhoudingNieuwPersoon.registreerPersoonElement(persoonElement2);
        assertEquals(bsn, bijhoudingNieuwPersoon.getActueelBurgerservicenummer());
    }

    @Test
    public void testGetActueelBurgerservicenummer_BestaandPersoon() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        assertNull(bijhoudingPersoon.getActueelBurgerservicenummer());

        final String bsn = "123456789";
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(persoon);
        idHistorie.setBurgerservicenummer(bsn);
        persoon.addPersoonIDHistorie(idHistorie);

        assertEquals(bsn, bijhoudingPersoon.getActueelBurgerservicenummer());
    }

    @Test
    public void testKopieerPersoon() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        final Partij zendendePartij = new Partij("zendende partij", "053001");
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setBijhoudingsaard(Bijhoudingsaard.INGEZETENE);
        persoon.setId(1L);
        persoon.setBijhoudingspartij(zendendePartij);
        voegPersoonIDHistorieToe(persoon, true);
        voegSamengesteldeNaamHisToe(persoon, false);
        voegGeboorteHisToe(persoon);
        voegGeslachtsaanduidingHisToe(persoon, false);

        PersoonIDHistorie persoonIDHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonIDHistorieSet());
        PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonSamengesteldeNaamHistorieSet());
        PersoonGeboorteHistorie persoonGeboorteHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonGeboorteHistorieSet());
        PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonGeslachtsaanduidingHistorieSet());

        final BijhoudingPersoon persoonKopie = BijhoudingPersoon.decorate(persoon).kopieer(20160101, false);

        assertEquals(1, persoonKopie.getPersoonIDHistorieSet().size());
        assertEquals((Integer) 20160101, persoonKopie.getPersoonIDHistorieSet().iterator().next().getDatumAanvangGeldigheid());
        assertNotEquals(persoonIDHistorie.getPersoon(), persoonKopie.getPersoonIDHistorieSet().iterator().next().getPersoon());

        assertEquals(1, persoonKopie.getPersoonSamengesteldeNaamHistorieSet().size());
        assertEquals((Integer) 20160202, persoonKopie.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getDatumAanvangGeldigheid());
        assertNotEquals(
                persoonSamengesteldeNaamHistorie.getPersoon(),
                persoonKopie.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getPersoon());

        assertEquals(1, persoonKopie.getPersoonGeslachtsaanduidingHistorieSet().size());
        assertEquals((Integer) 20160101, persoonKopie.getPersoonGeslachtsaanduidingHistorieSet().iterator().next().getDatumAanvangGeldigheid());
        assertNotEquals(
                persoonGeslachtsaanduidingHistorie.getPersoon(),
                persoonKopie.getPersoonGeslachtsaanduidingHistorieSet().iterator().next().getPersoon());

        assertEquals(1, persoonKopie.getPersoonGeboorteHistorieSet().size());
        assertNotEquals(persoonGeboorteHistorie.getPersoon(), persoonKopie.getPersoonGeboorteHistorieSet().iterator().next().getPersoon());

        assertEquals(SoortPersoon.PSEUDO_PERSOON, persoonKopie.getSoortPersoon());
        assertFalse(persoonKopie.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getIndicatieAfgeleid());

        assertNotNull(persoonKopie);

        controleerPersoon(persoonKopie);

        persoon.getPersoonIDHistorieSet().clear();
        persoon.getPersoonSamengesteldeNaamHistorieSet().clear();
        persoon.getPersoonGeboorteHistorieSet().clear();
        persoon.getPersoonGeslachtsaanduidingHistorieSet().clear();
        voegSamengesteldeNaamHisToe(persoon, true);
        voegGeboorteHisToe(persoon);
        voegPersoonIDHistorieToe(persoon, false);
        voegGeslachtsaanduidingHisToe(persoon, true);

        persoonIDHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonIDHistorieSet());
        persoonSamengesteldeNaamHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonSamengesteldeNaamHistorieSet());
        persoonGeboorteHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonGeboorteHistorieSet());
        persoonGeslachtsaanduidingHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonGeslachtsaanduidingHistorieSet());

        final BijhoudingPersoon persoonKopie2 = BijhoudingPersoon.decorate(persoon).kopieer(20160101, false);

        assertEquals(1, persoonKopie2.getPersoonIDHistorieSet().size());
        assertEquals((Integer) 20160202, persoonKopie2.getPersoonIDHistorieSet().iterator().next().getDatumAanvangGeldigheid());
        assertNotEquals(persoonIDHistorie.getPersoon(), persoonKopie2.getPersoonIDHistorieSet().iterator().next().getPersoon());
        assertEquals(1, persoonKopie2.getPersoonSamengesteldeNaamHistorieSet().size());
        assertEquals((Integer) 20160101, persoonKopie2.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getDatumAanvangGeldigheid());
        assertNotEquals(
                persoonSamengesteldeNaamHistorie.getPersoon(),
                persoonKopie2.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getPersoon());
        assertEquals(1, persoonKopie2.getPersoonGeslachtsaanduidingHistorieSet().size());
        assertEquals((Integer) 20160101, persoonKopie2.getPersoonGeslachtsaanduidingHistorieSet().iterator().next().getDatumAanvangGeldigheid());
        assertNotEquals(
                persoonGeslachtsaanduidingHistorie.getPersoon(),
                persoonKopie2.getPersoonGeslachtsaanduidingHistorieSet().iterator().next().getPersoon());
        assertEquals(1, persoonKopie2.getPersoonGeboorteHistorieSet().size());
        assertNotEquals(persoonGeboorteHistorie.getPersoon(), persoonKopie2.getPersoonGeboorteHistorieSet().iterator().next().getPersoon());
        assertEquals(SoortPersoon.PSEUDO_PERSOON, persoonKopie2.getSoortPersoon());
        assertFalse(persoonKopie2.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getIndicatieAfgeleid());

        assertNotNull(persoonKopie2);

        controleerPersoon(persoonKopie2);
    }

    @Test
    public void testWerkGroepAfgeleidAdministratiefBij() {
        // setup
        final Date nu = DatumUtil.nu();
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final PersoonAfgeleidAdministratiefHistorie vorigVoorkomen =
                new PersoonAfgeleidAdministratiefHistorie(
                        Short.parseShort("1"),
                        persoon.getDelegates().iterator().next(),
                        getAdministratieveHandeling(),
                        new Timestamp(nu.getTime() - 60000));
        vorigVoorkomen.setDatumTijdRegistratie(vorigVoorkomen.getDatumTijdLaatsteWijziging());
        vorigVoorkomen.setDatumTijdLaatsteWijzigingGba(vorigVoorkomen.getDatumTijdLaatsteWijziging());
        persoon.addPersoonAfgeleidAdministratiefHistorie(vorigVoorkomen);
        assertEquals(1, persoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
        // exucute
        persoon.werkGroepAfgeleidAdministratiefBij(getActie());
        // verify
        assertEquals(2, persoon.getPersoonAfgeleidAdministratiefHistorieSet().size());
        final PersoonAfgeleidAdministratiefHistorie afgeleidAdministratiefHistorie =
                new ArrayList<>(persoon.getPersoonAfgeleidAdministratiefHistorieSet()).get(1);
        assertTrue(nu.compareTo(afgeleidAdministratiefHistorie.getDatumTijdRegistratie()) >= 0);
        assertEquals(1, afgeleidAdministratiefHistorie.getSorteervolgorde());
        assertEquals(vorigVoorkomen.getDatumTijdLaatsteWijzigingGba(), afgeleidAdministratiefHistorie.getDatumTijdLaatsteWijzigingGba());
        assertEquals(getActie(), afgeleidAdministratiefHistorie.getActieInhoud());
    }

    @Test
    public void testVoegPersoonBijhoudingHistorieToe() {
        //setup elements
        final ElementBuilder builder = new ElementBuilder();
        final Integer datumAanvangAdreshouding = 20160102;
        final AdresElement
                adresElement =
                builder.maakAdres("ci_adres_1", new ElementBuilder.AdresElementParameters("W", 'A', datumAanvangAdreshouding, "7112"));
        final PersoonElement
                persoonElement =
                builder.maakPersoonGegevensElement("ci_persoon_1", "sleutel_1", null, new ElementBuilder.PersoonParameters().adres(adresElement));
        //setup entiteiten
        final Persoon persoonEntiteit = new Persoon(SoortPersoon.INGESCHREVENE);
        final Partij partijNieuw = new Partij("test partijNieuw", "000000");
        final Partij partijOud = new Partij("test partijOud", "000001");
        final Gemeente gemeente = new Gemeente(Short.parseShort("0"), "test gemeente", "7112", partijNieuw);
        final PersoonBijhoudingHistorie
                actueelBijhoudingVoorkomen =
                new PersoonBijhoudingHistorie(persoonEntiteit, partijOud, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        actueelBijhoudingVoorkomen.setId(1L);
        actueelBijhoudingVoorkomen.setDatumTijdRegistratie(new Timestamp(System.currentTimeMillis()));
        actueelBijhoudingVoorkomen.setDatumAanvangGeldigheid(datumAanvangAdreshouding - 10001);
        persoonEntiteit.addPersoonBijhoudingHistorie(actueelBijhoudingVoorkomen);
        //setup onderzoek
        final Onderzoek onderzoek = new Onderzoek(partijOud, persoonEntiteit);
        final GegevenInOnderzoek
                gegevenInOnderzoekOudVoorkomen =
                new GegevenInOnderzoek(onderzoek, nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON_BIJHOUDING_DATUMAANVANGGELDIGHEID);
        gegevenInOnderzoekOudVoorkomen.setEntiteitOfVoorkomen(actueelBijhoudingVoorkomen);
        onderzoek.addGegevenInOnderzoek(gegevenInOnderzoekOudVoorkomen);
        persoonEntiteit.addOnderzoek(onderzoek);

        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoonEntiteit);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(adresElement.getGemeenteCode().getWaarde())).thenReturn(gemeente);
        //execute
        bijhoudingPersoon.voegPersoonBijhoudingHistorieToe(gemeente, adresElement.getDatumAanvangAdreshouding().getWaarde(), getActie());
        //verify
        assertEquals(3, bijhoudingPersoon.getPersoonBijhoudingHistorieSet().size());
        final PersoonBijhoudingHistorie
                nieuwVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bijhoudingPersoon.getPersoonBijhoudingHistorieSet());

        assertNotNull(nieuwVoorkomen);
        assertEquals(datumAanvangAdreshouding, nieuwVoorkomen.getDatumAanvangGeldigheid());
        assertEquals(partijNieuw.getCode(), bijhoudingPersoon.getActueleBijhoudingspartij().getCode());
        assertEquals(actueelBijhoudingVoorkomen.getBijhoudingsaard(), nieuwVoorkomen.getBijhoudingsaard());
        assertEquals(actueelBijhoudingVoorkomen.getNadereBijhoudingsaard(), nieuwVoorkomen.getNadereBijhoudingsaard());
        //verify kopie onderzoek
        final PersoonBijhoudingHistorie
                eindeGeldigheidVoorkomen =
                bijhoudingPersoon.getPersoonBijhoudingHistorieSet().stream()
                        .filter(voorkomen -> voorkomen.getDatumEindeGeldigheid() != null && voorkomen.getDatumTijdVerval() == null).findFirst().get();
        assertNotNull(eindeGeldigheidVoorkomen);
        final GegevenInOnderzoek
                kopieGegevenInOnderzoek =
                onderzoek.getGegevenInOnderzoekSet().stream().filter(gio -> gio.getEntiteitOfVoorkomen().getId() == null).findFirst().get();
        assertNotNull(kopieGegevenInOnderzoek);
        assertSame(eindeGeldigheidVoorkomen, kopieGegevenInOnderzoek.getEntiteitOfVoorkomen());
        assertEquals(gegevenInOnderzoekOudVoorkomen.getSoortGegeven(), kopieGegevenInOnderzoek.getSoortGegeven());
    }

    @Test
    public void testVerzamelGerelateerden() {
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        assertEquals(0, persoon.verzamelGerelateerden().size());

        final BijhoudingPersoon pseudoPartner = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.PSEUDO_PERSOON));
        maakHuwelijk(persoon, pseudoPartner);
        assertEquals(0, persoon.verzamelGerelateerden().size());

        final BijhoudingPersoon partner = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        maakHuwelijk(persoon, partner);
        assertEquals(1, persoon.verzamelGerelateerden().size());
    }

    @Test
    public void testIsBijhoudingspartij() {
        final String partijNaam = "Partij 1";
        final Partij partij = new Partij(partijNaam, "000001");
        final Partij partij2 = new Partij("Partij 2", "000002");
        final Partij partij3 = new Partij(partijNaam, "000003");

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        assertNull(bijhoudingPersoon.getActueleBijhoudingspartij());
        final PersoonBijhoudingHistorie historie = new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        persoon.addPersoonBijhoudingHistorie(historie);
        assertEquals("000001", bijhoudingPersoon.getActueleBijhoudingspartij().getCode());

        assertTrue(bijhoudingPersoon.isPartijBijhoudingspartij(partij));
        assertFalse(bijhoudingPersoon.isPartijBijhoudingspartij(partij2));
        assertFalse(bijhoudingPersoon.isPartijBijhoudingspartij(partij3));
    }

    @Test
    public void testIsIngeschreveneEnNietFout() {
        assertTrue(new BijhoudingPersoon().isIngeschreveneEnNietOpgeschortMetRedenFoutOfGewist());

        final Persoon bestaandPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Partij partij = new Partij("partij", "000001");
        bestaandPersoon.addPersoonBijhoudingHistorie(
                new PersoonBijhoudingHistorie(bestaandPersoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        assertTrue(BijhoudingPersoon.decorate(bestaandPersoon).isIngeschreveneEnNietOpgeschortMetRedenFoutOfGewist());

        final Persoon pseudoPersoon = new Persoon(SoortPersoon.PSEUDO_PERSOON);
        pseudoPersoon.addPersoonBijhoudingHistorie(
                new PersoonBijhoudingHistorie(pseudoPersoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        assertFalse(BijhoudingPersoon.decorate(pseudoPersoon).isIngeschreveneEnNietOpgeschortMetRedenFoutOfGewist());

        final Persoon geemigreerdPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        geemigreerdPersoon.addPersoonBijhoudingHistorie(
                new PersoonBijhoudingHistorie(geemigreerdPersoon, partij, Bijhoudingsaard.NIET_INGEZETENE, NadereBijhoudingsaard.EMIGRATIE));
        assertTrue(BijhoudingPersoon.decorate(geemigreerdPersoon).isIngeschreveneEnNietOpgeschortMetRedenFoutOfGewist());

        final Persoon foutPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        foutPersoon.addPersoonBijhoudingHistorie(
                new PersoonBijhoudingHistorie(foutPersoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.FOUT));
        assertFalse(BijhoudingPersoon.decorate(foutPersoon).isIngeschreveneEnNietOpgeschortMetRedenFoutOfGewist());
    }

    @Test
    public void testVoegGeboorteHistorieToe_GeenLandInElement() {
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        final ElementBuilder.GeboorteParameters geboorteParams = new ElementBuilder.GeboorteParameters();
        geboorteParams.datum(20160101);
        final GeboorteElement geboorteElement = new ElementBuilder().maakGeboorteElement("geboorte", geboorteParams);
        persoon.voegPersoonGeboorteHistorieToe(geboorteElement, getActie());
        assertEquals(1, persoon.getPersoonGeboorteHistorieSet().size());
        final PersoonGeboorteHistorie historie = persoon.getPersoonGeboorteHistorieSet().iterator().next();
        assertEquals("6030", historie.getLandOfGebied().getCode());
    }

    @Test
    public void testVoegInschrijvingHistorieToe() {
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        assertEquals(0, persoon.getPersoonInschrijvingHistorieSet().size());
        final BRPActie actie = getActie();
        final Timestamp nu = Timestamp.from(Instant.now());
        final long versienummer = 2L;
        final int datumInschrijving = 20160101;
        persoon.voegPersoonInschrijvingHistorieToe(datumInschrijving, versienummer, nu, actie);

        assertEquals(1, persoon.getPersoonInschrijvingHistorieSet().size());
        final PersoonInschrijvingHistorie historie = persoon.getPersoonInschrijvingHistorieSet().iterator().next();
        assertEquals(datumInschrijving, historie.getDatumInschrijving());
        assertEquals(nu, historie.getDatumtijdstempel());
        assertEquals(versienummer, historie.getVersienummer());
        assertEquals(actie, historie.getActieInhoud());
    }

    @Test
    public void testKopieerIndicatie() {
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        final SoortIndicatie soortIndicatie = SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE;
        assertNull(persoon.getPersoonIndicatie(soortIndicatie));
        final PersoonIndicatie indicatie = new PersoonIndicatie(persoon, soortIndicatie);
        final PersoonIndicatieHistorie historie = new PersoonIndicatieHistorie(indicatie, true);
        indicatie.addPersoonIndicatieHistorie(historie);
        final BRPActie actie = getActie();

        persoon.kopieerIndicatie(indicatie, actie);

        final PersoonIndicatie kopie = persoon.getPersoonIndicatie(soortIndicatie);
        assertNotSame(indicatie, kopie);
        assertNotNull(kopie);
        final Set<PersoonIndicatieHistorie> historieSet = kopie.getPersoonIndicatieHistorieSet();
        assertEquals(1, historieSet.size());
        final PersoonIndicatieHistorie kopieHistorie = historieSet.iterator().next();
        assertTrue(kopieHistorie.getWaarde());
        assertEquals(actie, kopieHistorie.getActieInhoud());
    }

    @Test
    public void testKopieerVerstrekkingsbeperking() {
        final BRPActie actie = getActie();
        final Partij partij = actie.getPartij();
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        assertEquals(0, persoon.getPersoonVerstrekkingsbeperkingSet().size());
        final PersoonVerstrekkingsbeperking verstrekkingsbeperking = new PersoonVerstrekkingsbeperking(persoon);
        verstrekkingsbeperking.setGemeenteVerordening(partij);
        verstrekkingsbeperking.setPartij(partij);
        verstrekkingsbeperking.setOmschrijvingDerde("derde");

        persoon.kopieerVerstrekkingsbeperking(verstrekkingsbeperking, actie);
        final Set<PersoonVerstrekkingsbeperking> historieSet = persoon.getPersoonVerstrekkingsbeperkingSet();
        assertEquals(1, historieSet.size());
        final PersoonVerstrekkingsbeperking kopie = historieSet.iterator().next();
        assertNotSame(verstrekkingsbeperking, kopie);
        assertEquals(partij, kopie.getGemeenteVerordening());
        assertEquals(partij, kopie.getPartij());
        assertEquals("derde", kopie.getOmschrijvingDerde());

    }

    @Test
    public void testKopieerAdres() {
        final Persoon anderePersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        assertEquals(0, persoon.getPersoonAdresSet().size());
        final PersoonAdres adres = new PersoonAdres(anderePersoon);
        final PersoonAdresHistorie
                adresHistorie =
                new PersoonAdresHistorie(adres, SoortAdres.BRIEFADRES, NEDERLAND,
                        new RedenWijzigingVerblijf(RedenWijzigingVerblijf.INFRASTRUCTURELE_WIJZIGING, "infra"));
        adres.addPersoonAdresHistorie(adresHistorie)
        ;
        final Integer datum = 20160101;
        final BRPActie actie = getActie();
        final RedenWijzigingVerblijf ambsthalve = new RedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE, "ambsthalve");
        persoon.kopieerAdres(adres, ambsthalve, datum, actie);
        final Set<PersoonAdres> adresSet = persoon.getPersoonAdresSet();
        assertEquals(1, adresSet.size());
        final PersoonAdres kopie = adresSet.iterator().next();
        assertNotSame(adres, kopie);
        final Set<PersoonAdresHistorie> historieSet = kopie.getPersoonAdresHistorieSet();
        assertEquals(1, historieSet.size());
        final PersoonAdresHistorie kopieHistorie = historieSet.iterator().next();
        assertEquals(ambsthalve, kopieHistorie.getRedenWijziging());
        assertEquals(datum, kopieHistorie.getDatumAanvangAdreshouding());
        assertNull(kopieHistorie.getAangeverAdreshouding());
        assertNull(kopieHistorie.getIndicatiePersoonAangetroffenOpAdres());
        assertEquals(kopie, kopieHistorie.getPersoonAdres());
        assertEquals(actie, kopieHistorie.getActieInhoud());
    }

    @Test
    public void testKopieerAdresKopieerNaVerhuizing() {
        final RedenWijzigingVerblijf ambsthalve = new RedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE, "ambsthalve");
        final Persoon anderePersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        assertEquals(0, persoon.getPersoonAdresSet().size());
        final PersoonAdres adres = new PersoonAdres(anderePersoon);
        final PersoonAdresHistorie adresHistorie =
                new PersoonAdresHistorie(adres, SoortAdres.WOONADRES, NEDERLAND, ambsthalve);
        adresHistorie.setWoonplaatsnaam("Volendam");
        adres.addPersoonAdresHistorie(adresHistorie);

        final PersoonAdresHistorie adresHistorieNieuw =
                new PersoonAdresHistorie(adres, SoortAdres.WOONADRES, NEDERLAND, ambsthalve);
        adresHistorieNieuw.setWoonplaatsnaam("Zaandam");
        adresHistorieNieuw.setDatumAanvangAdreshouding(20160101);
        adresHistorieNieuw.setDatumAanvangGeldigheid(20160101);
        adresHistorieNieuw.setDatumTijdRegistratie(new Timestamp((new Date()).getTime()));
        adresHistorieNieuw.setActieInhoud(getActie());
        MaterieleHistorie.voegNieuweActueleToe(adresHistorieNieuw, adres.getPersoonAdresHistorieSet());
        final Integer datum = 20160102;
        final BRPActie actie = getActie();
        persoon.kopieerAdres(adres, ambsthalve, datum, actie);
        final Set<PersoonAdres> adresSet = persoon.getPersoonAdresSet();
        assertEquals(1, adresSet.size());
        final PersoonAdres kopie = adresSet.iterator().next();
        assertEquals(adresHistorieNieuw.getWoonplaatsnaam(), kopie.getPersoonAdresHistorieSet().iterator().next().getWoonplaatsnaam());
    }

    @Test
    public void testKopieerAdresKopieerVoorVerhuizing() {
        final RedenWijzigingVerblijf ambsthalve = new RedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE, "ambsthalve");
        final Persoon anderePersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        assertEquals(0, persoon.getPersoonAdresSet().size());
        final PersoonAdres adres = new PersoonAdres(anderePersoon);
        final PersoonAdresHistorie adresHistorie =
                new PersoonAdresHistorie(adres, SoortAdres.WOONADRES, NEDERLAND, ambsthalve);
        adresHistorie.setWoonplaatsnaam("Volendam");
        adres.addPersoonAdresHistorie(adresHistorie);

        final PersoonAdresHistorie adresHistorieNieuw =
                new PersoonAdresHistorie(adres, SoortAdres.WOONADRES, NEDERLAND, ambsthalve);
        adresHistorieNieuw.setWoonplaatsnaam("Zaandam");
        adresHistorieNieuw.setDatumAanvangAdreshouding(20160102);
        adresHistorieNieuw.setDatumAanvangGeldigheid(20160102);
        adresHistorieNieuw.setDatumTijdRegistratie(new Timestamp((new Date()).getTime()));
        adresHistorieNieuw.setActieInhoud(getActie());
        MaterieleHistorie.voegNieuweActueleToe(adresHistorieNieuw, adres.getPersoonAdresHistorieSet());
        final Integer datum = 20160101;
        final BRPActie actie = getActie();
        persoon.kopieerAdres(adres, ambsthalve, datum, actie);
        final Set<PersoonAdres> adresSet = persoon.getPersoonAdresSet();
        assertEquals(1, adresSet.size());
        final PersoonAdres kopie = adresSet.iterator().next();
        assertEquals(adresHistorie.getWoonplaatsnaam(), kopie.getPersoonAdresHistorieSet().iterator().next().getWoonplaatsnaam());
    }


    @Test
    public void testVoegPersoonVoornamenHistorieToe() {
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        assertEquals(0, persoon.getPersoonVoornaamSet().size());

        final String naam = "Piet";
        final VoornaamElement voornaamElement = new ElementBuilder().maakVoornaamElement("voornaam", 1, naam);
        final BRPActie actie = getActie();
        final int datum = 20160101;
        persoon.voegPersoonVoornamenHistorieToe(Collections.singletonList(voornaamElement), actie, datum);
        final Set<PersoonVoornaam> voornamen = persoon.getPersoonVoornaamSet();
        assertEquals(1, voornamen.size());
        final PersoonVoornaam voornaam = voornamen.iterator().next();
        assertEquals(1, voornaam.getVolgnummer());
        final Set<PersoonVoornaamHistorie> historieSet = voornaam.getPersoonVoornaamHistorieSet();
        assertEquals(1, historieSet.size());
        final PersoonVoornaamHistorie historie = historieSet.iterator().next();
        assertEquals(naam, historie.getNaam());
        assertEquals(actie, historie.getActieInhoud());
    }

    @Test
    public void testVoegPersoonGeslachtsnaamComponentHistorieToe() {
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        assertEquals(0, persoon.getPersoonGeslachtsnaamcomponentSet().size());
        final String stam = "stam";
        final Character scheidingsteken = ' ';
        final String voorvoegsel = "van";
        final String adellijkeTitel = "B";
        final String predicaatCode = "K";
        final GeslachtsnaamcomponentElement element =
                new ElementBuilder().maakGeslachtsnaamcomponentElement("geslnaamcomp", predicaatCode, adellijkeTitel, voorvoegsel, scheidingsteken, stam);
        final int datum = 2016_01_01;
        final BRPActie actie = getActie();
        persoon.voegPersoonGeslachtsnaamComponentHistorieToe(element, actie, datum);
        final Set<PersoonGeslachtsnaamcomponent> geslachtsnaamcomponenten = persoon.getPersoonGeslachtsnaamcomponentSet();
        assertEquals(1, geslachtsnaamcomponenten.size());
        final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = geslachtsnaamcomponenten.iterator().next();
        final Set<PersoonGeslachtsnaamcomponentHistorie> historieSet = geslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet();
        assertEquals((Integer) 1, geslachtsnaamcomponent.getVolgnummer());
        assertEquals(1, historieSet.size());
        final PersoonGeslachtsnaamcomponentHistorie historie = historieSet.iterator().next();
        assertEquals(stam, historie.getStam());
        assertEquals(scheidingsteken, historie.getScheidingsteken());
        assertEquals(AdellijkeTitel.B, historie.getAdellijkeTitel());
        assertEquals(Predicaat.K, historie.getPredicaat());
        assertEquals(actie, historie.getActieInhoud());
    }

    @Test
    public void testVoegPersoonNationaliteitToe_nederlands() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BRPActie actie = getActie();

        // Actuele duits nationaliteit
        final PersoonNationaliteit duits = voegPersoonNationaliteitToe(persoon, new Nationaliteit("Duits", "0055"), false);
        final PersoonNationaliteitHistorie duitsHistorie = duits.getPersoonNationaliteitHistorieSet().iterator().next();
        duitsHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));

        // Vervallen belgische nationaliteit
        final PersoonNationaliteit belgisch = voegPersoonNationaliteitToe(persoon, new Nationaliteit("Belgische", "0052"), true);
        final PersoonNationaliteitHistorie belgischHistorie = belgisch.getPersoonNationaliteitHistorieSet().iterator().next();
        belgischHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));

        // BVP
        final PersoonIndicatie bvpIndicatie = new PersoonIndicatie(persoon, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(bvpIndicatie, true);
        bvpIndicatie.addPersoonIndicatieHistorie(indicatieHistorie);
        persoon.addPersoonIndicatie(bvpIndicatie);

        final ElementBuilder builder = new ElementBuilder();
        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nation", "0001", "001");

        final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit = new RedenVerkrijgingNLNationaliteit("185", "reden");
        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode(anyString())).thenReturn(new Nationaliteit("Nederlands", "0001"));
        when(getDynamischeStamtabelRepository().getRedenVerkrijgingNLNationaliteitByCode(anyString())).thenReturn(redenVerkrijgingNLNationaliteit);

        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        bijhoudingPersoon.voegPersoonNationaliteitToe(nationaliteitElement, actie, 2016_01_01);

        final Set<PersoonNationaliteit> persoonNationaliteitSet = persoon.getPersoonNationaliteitSet();
        assertEquals(3, persoonNationaliteitSet.size());
        final PersoonNationaliteit nederlands = persoonNationaliteitSet.stream().
                filter(persoonNationaliteit -> persoonNationaliteit.getNationaliteit().isNederlandse()).findFirst().orElse(null);

        assertNotNull(nederlands);
        assertEquals(1, nederlands.getPersoonNationaliteitHistorieSet().size());

        final PersoonNationaliteitHistorie nederlandsHistorie = nederlands.getPersoonNationaliteitHistorieSet().iterator().next();
        assertEquals(actie, nederlandsHistorie.getActieInhoud());
        assertEquals(redenVerkrijgingNLNationaliteit, nederlandsHistorie.getRedenVerkrijgingNLNationaliteit());

        // Controleren duitse nationaliteit is vervallen en einde bijhouding
        assertEquals(actie, duitsHistorie.getActieVerval());
        assertEquals(actie.getDatumTijdRegistratie(), duitsHistorie.getDatumTijdVerval());
        assertTrue(duitsHistorie.getIndicatieBijhoudingBeeindigd());

        // Controleren belgische nationaliteit niet aangepast
        assertNotEquals(actie, belgischHistorie.getActieVerval());
        assertNotEquals(actie.getDatumTijdRegistratie(), belgischHistorie.getDatumTijdVerval());
        assertNull(belgischHistorie.getIndicatieBijhoudingBeeindigd());

        // Controleren BVP indicatie is vervallen
        assertEquals(actie, indicatieHistorie.getActieVerval());
        assertEquals(actie.getDatumTijdRegistratie(), indicatieHistorie.getDatumTijdVerval());
    }

    @Test
    public void testVoegPersoonNationaliteitToe_staatloos_deels_overlap() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final ElementBuilder builder = new ElementBuilder();
        final BRPActie actie = getActie();
        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nation", "0185", null);

        final PersoonIndicatie staatloosIndicatie = new PersoonIndicatie(persoon, SoortIndicatie.STAATLOOS);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(staatloosIndicatie, true);
        indicatieHistorie.setDatumAanvangGeldigheid(2015_01_01);
        staatloosIndicatie.addPersoonIndicatieHistorie(indicatieHistorie);
        persoon.addPersoonIndicatie(staatloosIndicatie);

        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode(anyString())).thenReturn(new Nationaliteit("Duits", "0055"));
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        bijhoudingPersoon.voegPersoonNationaliteitToe(nationaliteitElement, actie, 2016_01_01);

        final Set<PersoonNationaliteit> persoonNationaliteitSet = persoon.getPersoonNationaliteitSet();
        assertEquals(1, persoonNationaliteitSet.size());
        final PersoonNationaliteit duits = persoonNationaliteitSet.iterator().next();
        assertNotNull(duits);
        assertEquals(1, duits.getPersoonNationaliteitHistorieSet().size());

        assertEquals(2, staatloosIndicatie.getPersoonIndicatieHistorieSet().size());
        assertEquals(actie, indicatieHistorie.getActieVerval());
        assertEquals(actie.getDatumTijdRegistratie(), indicatieHistorie.getDatumTijdVerval());
    }

    @Test
    public void testVoegPersoonNationaliteitToe_staatloos_volledig_overlap() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final ElementBuilder builder = new ElementBuilder();
        final BRPActie actie = getActie();
        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nation", "0001", "001");

        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode(anyString())).thenReturn(new Nationaliteit("Nederlands", "0001"));

        final int datumAanvangGeldigheid = 2016_01_01;
        final PersoonIndicatie staatloosIndicatie = new PersoonIndicatie(persoon, SoortIndicatie.STAATLOOS);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(staatloosIndicatie, true);
        indicatieHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        staatloosIndicatie.addPersoonIndicatieHistorie(indicatieHistorie);
        persoon.addPersoonIndicatie(staatloosIndicatie);

        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        bijhoudingPersoon.voegPersoonNationaliteitToe(nationaliteitElement, actie, datumAanvangGeldigheid);

        final Set<PersoonNationaliteit> persoonNationaliteitSet = persoon.getPersoonNationaliteitSet();
        assertEquals(1, persoonNationaliteitSet.size());
        final PersoonNationaliteit nederlands = persoonNationaliteitSet.iterator().next();
        assertNotNull(nederlands);
        assertEquals(1, nederlands.getPersoonNationaliteitHistorieSet().size());

        assertEquals(actie, indicatieHistorie.getActieVerval());
        assertEquals(actie.getDatumTijdRegistratie(), indicatieHistorie.getDatumTijdVerval());
    }

    @Test
    public void testVoegPersoonNationaliteitToe_staatloos_vervallen() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final ElementBuilder builder = new ElementBuilder();
        final BRPActie actie = getActie();
        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nation", "0001", "001");

        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode(anyString())).thenReturn(new Nationaliteit("Nederlands", "0001"));

        final int datumAanvangGeldigheid = 2016_01_01;
        // Vervallen staatloos
        final PersoonIndicatie staatloosIndicatie = new PersoonIndicatie(persoon, SoortIndicatie.STAATLOOS);
        final PersoonIndicatieHistorie staatloosIndicatieHistorie = new PersoonIndicatieHistorie(staatloosIndicatie, true);
        staatloosIndicatieHistorie.setDatumTijdVerval(Timestamp.from(Instant.now()));
        staatloosIndicatie.addPersoonIndicatieHistorie(staatloosIndicatieHistorie);
        persoon.addPersoonIndicatie(staatloosIndicatie);

        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        bijhoudingPersoon.voegPersoonNationaliteitToe(nationaliteitElement, actie, datumAanvangGeldigheid);

        final Set<PersoonNationaliteit> persoonNationaliteitSet = persoon.getPersoonNationaliteitSet();
        assertEquals(1, persoonNationaliteitSet.size());
        final PersoonNationaliteit nederlands = persoonNationaliteitSet.iterator().next();
        assertNotNull(nederlands);
        assertEquals(1, nederlands.getPersoonNationaliteitHistorieSet().size());

        // Controleren staatloos indicatie niet aangepast
        assertNull(staatloosIndicatieHistorie.getActieVerval());
    }

    @Test
    public void testMeerderjarig_eersteInschrijving_controleOpLeeftijd() {
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.GeboorteParameters geboorteParameters = new ElementBuilder.GeboorteParameters();
        geboorteParameters.datum(1999_01_01);
        final GeboorteElement geboorteElement = builder.maakGeboorteElement("geboorte", geboorteParameters);
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.geboorte(geboorteElement);
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParameters);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        persoon.registreerPersoonElement(persoonElement);

        assertTrue(persoon.isMeerderjarig(2017_01_01));
        assertFalse(persoon.isMeerderjarig(2016_12_31));
    }

    @Test
    public void testMeerderjarig_eersteInschrijving_geenGeboorteElement() {
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParameters);
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        persoon.registreerPersoonElement(persoonElement);

        assertFalse(persoon.isMeerderjarig(2016_12_31));
    }

    @Test
    public void testMeerderjarig_bestaandePersoon_controleOpLeeftijd() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.addPersoonGeboorteHistorie(new PersoonGeboorteHistorie(persoon, 1999_01_01, NEDERLAND));

        assertTrue(BijhoudingPersoon.decorate(persoon).isMeerderjarig(2017_01_01));
        assertFalse(BijhoudingPersoon.decorate(persoon).isMeerderjarig(2016_12_31));
    }

    @Test
    public void testMeerderjarig_bestaandePersoon_controleOpHuwelijk() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.addPersoonGeboorteHistorie(new PersoonGeboorteHistorie(persoon, 2000_01_01, NEDERLAND));
        assertFalse(BijhoudingPersoon.decorate(persoon).isMeerderjarig(2017_01_01));

        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
        relatieHistorie.setDatumAanvang(2017_01_01);
        relatie.addRelatieHistorie(relatieHistorie);
        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        relatie.addBetrokkenheid(ikBetrokkenheid);
        persoon.addBetrokkenheid(ikBetrokkenheid);
        assertFalse(BijhoudingPersoon.decorate(persoon).isMeerderjarig(2016_12_31));
        assertTrue(BijhoudingPersoon.decorate(persoon).isMeerderjarig(2017_01_02));
    }

    @Test
    public void voegOuderToe() {
        final AdministratieveHandeling administratieveHandeling = getAdministratieveHandeling();
        final Timestamp timestamp1 = Timestamp.from(Instant.now().plus(-2, ChronoUnit.DAYS));
        final Timestamp timestamp2 = Timestamp.from(Instant.now().plus(-1, ChronoUnit.DAYS));
        final BRPActie actie1 = new BRPActie(
                SoortActie.REGISTRATIE_OUDER, administratieveHandeling, administratieveHandeling.getPartij(), timestamp1);
        final BRPActie actie2 = new BRPActie(
                SoortActie.REGISTRATIE_OUDER, administratieveHandeling, administratieveHandeling.getPartij(), timestamp2);

        BijhoudingPersoon persoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));
        BijhoudingPersoon ouder1 = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));
        BijhoudingPersoon ouder2 = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));
        assertEquals(0, persoon.getBetrokkenheidSet().size());
        persoon.voegOuderToe(ouder1, actie1, 20010101);
        assertEquals(1, persoon.getBetrokkenheidSet().size());
        final Set<Betrokkenheid> ouders = persoon.getBetrokkenheidSet().iterator().next().getRelatie().getBetrokkenheidSet(SoortBetrokkenheid.OUDER);
        assertEquals(1, ouders.size());
        assertFalse(ouders.iterator().next().getBetrokkenheidHistorieSet().isEmpty());
        persoon.voegOuderToe(ouder2, actie2, 20010102);
        assertEquals(1, persoon.getBetrokkenheidSet().size());
        final Set<Betrokkenheid> ouders2 = persoon.getBetrokkenheidSet().iterator().next().getRelatie().getBetrokkenheidSet(SoortBetrokkenheid.OUDER);
        assertEquals(2, ouders2.size());
    }

    @Test
    public void testHeeftOfKrijgtNederlandseNationaliteit_eersteInschrijving_krijgtEenAndereMaarHeeftAlNederlands() {
        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nat2", "0039", null);

        final ElementBuilder.PersoonParameters persoonParams2 = new ElementBuilder.PersoonParameters();
        persoonParams2.nationaliteiten(Collections.singletonList(nationaliteitElement));

        final PersoonElement persoonElement2 = builder.maakPersoonGegevensElement("persoon2", null, null, persoonParams2);

        final Persoon persoon = mock(Persoon.class);
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("nl", Nationaliteit.NEDERLANDSE));
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(nationaliteit);
        historie.setDatumAanvangGeldigheid(20000101);
        historie.setDatumTijdRegistratie(new Timestamp(DatumUtil.nu().getTime()));
        nationaliteit.addPersoonNationaliteitHistorie(historie);
        when(persoon.getPersoonNationaliteitSet()).thenReturn(Collections.singleton(nationaliteit));
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        bijhoudingPersoon.registreerPersoonElement(persoonElement2);

        assertTrue(bijhoudingPersoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(peilDatum));
    }

    @Test
    public void testHeeftOfKrijgtNederlandseNationaliteit_eersteInschrijving_krijgtEenAndereMaarHeeftAlNederlandsMaarIsBeeindigd() {
        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nat2", "0039", null);

        final ElementBuilder.PersoonParameters persoonParams2 = new ElementBuilder.PersoonParameters();
        persoonParams2.nationaliteiten(Arrays.asList(nationaliteitElement));

        final PersoonElement persoonElement2 = builder.maakPersoonGegevensElement("persoon2", null, null, persoonParams2);

        final Persoon persoon = mock(Persoon.class);
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("nl", Nationaliteit.NEDERLANDSE));
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(nationaliteit);
        historie.setDatumAanvangGeldigheid(20000101);
        historie.setDatumEindeGeldigheid(20010101);
        historie.setDatumTijdRegistratie(new Timestamp(DatumUtil.nu().getTime()));
        nationaliteit.addPersoonNationaliteitHistorie(historie);
        when(persoon.getPersoonNationaliteitSet()).thenReturn(Collections.singleton(nationaliteit));
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        bijhoudingPersoon.registreerPersoonElement(persoonElement2);

        assertFalse(bijhoudingPersoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(peilDatum));
    }

    @Test
    public void testHeeftOfKrijgtNederlandseNationaliteit_eersteInschrijving_krijgtEenAndereEnNederlandsBeeindigd() {
        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;
        final NationaliteitElement nederlandseNatElement = builder.maakNationaliteitElement("nat", "0001", "001");
        nederlandseNatElement.setDatumAanvangGeldigheidRegistratieNationaliteitActie(2015_01_01);
        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        persoonParams.nationaliteiten(Collections.singletonList(nederlandseNatElement));

        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParams);

        final NationaliteitElement beeindigdNederlandseNatElement = builder.maakNationaliteitElementVerlies("nat2", null, "nat", "034");
        beeindigdNederlandseNatElement.setDatumEindeGeldigheidRegistratieNationaliteitActie(2015_31_12);
        final ElementBuilder.PersoonParameters persoonParams2 = new ElementBuilder.PersoonParameters();
        persoonParams2.nationaliteiten(Collections.singletonList(beeindigdNederlandseNatElement));
        final PersoonElement persoonElement2 = builder.maakPersoonGegevensElement("persoon2", null, null, persoonParams2);

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nat3", "0039", null);
        final ElementBuilder.PersoonParameters persoonParams3 = new ElementBuilder.PersoonParameters();
        persoonParams3.nationaliteiten(Collections.singletonList(nationaliteitElement));
        final PersoonElement persoonElement3 = builder.maakPersoonGegevensElement("persoon3", null, null, persoonParams3);

        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon();
        bijhoudingPersoon.registreerPersoonElement(persoonElement);
        bijhoudingPersoon.registreerPersoonElement(persoonElement2);
        bijhoudingPersoon.registreerPersoonElement(persoonElement3);

        builder.initialiseerVerzoekBericht(getBericht());
        assertFalse(bijhoudingPersoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(peilDatum));
    }

    @Test
    public void testHeeftOfKrijgtNederlandseNationaliteit_eersteInschrijving_AlleenAndereNationaliteit() {
        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nat3", "0039", null);
        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        persoonParams.nationaliteiten(Collections.singletonList(nationaliteitElement));
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParams);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon();
        bijhoudingPersoon.registreerPersoonElement(persoonElement);
        assertFalse(bijhoudingPersoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(peilDatum));
    }

    @Test
    public void testHeeftOfKrijgtNederlandseNationaliteit_bestaandePersoon_isAlNederlander() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, NEDERLANDS);
        persoon.addPersoonNationaliteit(persoonNationaliteit);

        final PersoonNationaliteitHistorie nationaliteitHistorie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        nationaliteitHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        nationaliteitHistorie.setDatumAanvangGeldigheid(2015_01_01);
        persoonNationaliteit.addPersoonNationaliteitHistorie(nationaliteitHistorie);

        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nat", "0039", null);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParams);
        bijhoudingPersoon.registreerPersoonElement(persoonElement);
        assertTrue(bijhoudingPersoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(peilDatum));
    }

    @Test
    public void testHeeftOfKrijgtNederlandseNationaliteit_bestaandePersoon_isGeenNederlanderMeer() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, NEDERLANDS);
        persoon.addPersoonNationaliteit(persoonNationaliteit);

        final PersoonNationaliteitHistorie nationaliteitHistorie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        nationaliteitHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        nationaliteitHistorie.setDatumAanvangGeldigheid(2015_01_01);
        nationaliteitHistorie.setDatumEindeGeldigheid(2015_12_31);
        persoonNationaliteit.addPersoonNationaliteitHistorie(nationaliteitHistorie);

        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nat", "0039", null);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParams);
        bijhoudingPersoon.registreerPersoonElement(persoonElement);
        assertFalse(bijhoudingPersoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(peilDatum));
    }

    @Test
    public void testHeeftOfKrijgtNederlandseNationaliteit_bestaandePersoon_isNietNederlander() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("Estische", "0045"));
        persoon.addPersoonNationaliteit(persoonNationaliteit);

        final PersoonNationaliteitHistorie nationaliteitHistorie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        nationaliteitHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        nationaliteitHistorie.setDatumAanvangGeldigheid(2015_01_01);
        persoonNationaliteit.addPersoonNationaliteitHistorie(nationaliteitHistorie);

        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nat", "0039", null);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        persoonParams.nationaliteiten(Collections.singletonList(nationaliteitElement));
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParams);
        bijhoudingPersoon.registreerPersoonElement(persoonElement);
        assertFalse(bijhoudingPersoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(peilDatum));
    }

    @Test
    public void testHeeftOfKrijgtNederlandseNationaliteit_bestaandePersoon_geenNationaliteiten() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nat", "0039", null);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParams);
        bijhoudingPersoon.registreerPersoonElement(persoonElement);
        assertFalse(bijhoudingPersoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(peilDatum));
    }

    @Test
    public void testHeeftOfKrijgtNederlandseNationaliteit_bestaandePersoon_heeftIndicatie() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIndicatie indicatie = new PersoonIndicatie(persoon, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(indicatie, true);
        indicatieHistorie.setDatumAanvangGeldigheid(2015_01_01);
        indicatie.addPersoonIndicatieHistorie(indicatieHistorie);
        persoon.addPersoonIndicatie(indicatie);

        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nat", "0039", null);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParams);
        bijhoudingPersoon.registreerPersoonElement(persoonElement);
        assertTrue(bijhoudingPersoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(peilDatum));
    }

    @Test
    public void testHeeftOfKrijgtNederlandseNationaliteit_bestaandePersoon_heeftIndicatieNietMeer() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIndicatie indicatie = new PersoonIndicatie(persoon, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(indicatie, true);
        indicatieHistorie.setDatumAanvangGeldigheid(2015_01_01);
        indicatieHistorie.setDatumEindeGeldigheid(2015_12_31);
        indicatie.addPersoonIndicatieHistorie(indicatieHistorie);
        persoon.addPersoonIndicatie(indicatie);

        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nat", "0039", null);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParams);
        bijhoudingPersoon.registreerPersoonElement(persoonElement);

        assertFalse(bijhoudingPersoon.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(peilDatum));
    }

    @Test
    public void testHeeftNationaliteitOpPeildatum_eersteInschrijving_NationaliteitBestaatNogNiet() {
        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;

        final NationaliteitElement nederlandseNatElement = builder.maakNationaliteitElement("nat", "0001", "001");
        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        persoonParams.nationaliteiten(Collections.singletonList(nederlandseNatElement));
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParams);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon();
        bijhoudingPersoon.registreerPersoonElement(persoonElement);

        assertFalse(bijhoudingPersoon.heeftNationaliteitAl(nederlandseNatElement, peilDatum));
    }

    @Test
    public void testHeeftNationaliteitOpPeildatum_eersteInschrijving_NationaliteitBestaatAl() {
        final ElementBuilder builder = new ElementBuilder();
        final int peilDatum = 2016_01_01;

        final NationaliteitElement nationaliteitElement = builder.maakNationaliteitElement("nat", "0001", "001");
        nationaliteitElement.setDatumAanvangGeldigheidRegistratieNationaliteitActie(peilDatum);

        final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
        persoonParams.nationaliteiten(Arrays.asList(nationaliteitElement));
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("persoon", null, null, persoonParams);
        final Persoon persoon = mock(Persoon.class);
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoon, new Nationaliteit("nl", Nationaliteit.NEDERLANDSE));
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(nationaliteit);
        historie.setDatumAanvangGeldigheid(20150101);
        historie.setDatumTijdRegistratie(new Timestamp(DatumUtil.nu().getTime()));
        nationaliteit.addPersoonNationaliteitHistorie(historie);
        when(persoon.getPersoonNationaliteitSet()).thenReturn(Collections.singleton(nationaliteit));
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        bijhoudingPersoon.registreerPersoonElement(persoonElement);

        assertTrue(bijhoudingPersoon.heeftNationaliteitAl(nationaliteitElement, peilDatum));
    }

    @Test
    public void testGetMeestRecenteIndicatie() {
        final BijhoudingPersoon persoon = new BijhoudingPersoon();
        final PersoonIndicatie staatloosIndicatie = new PersoonIndicatie(persoon, SoortIndicatie.STAATLOOS);
        voegHistorieToe(staatloosIndicatie, 2015_01_01, 2014_01_01, 2016_01_01);
        persoon.addPersoonIndicatie(staatloosIndicatie);

        final PersoonIndicatieHistorie meestRecenteIndicatie = persoon.getMeestRecenteIndicatie(SoortIndicatie.STAATLOOS);
        assertNotNull(meestRecenteIndicatie);
        assertEquals((Integer) 2016_01_01, meestRecenteIndicatie.getDatumAanvangGeldigheid());
    }

    private void voegHistorieToe(final PersoonIndicatie indicatie, final int... datumsAanvang) {
        for (final int datumAanvang : datumsAanvang) {
            final PersoonIndicatieHistorie historie = new PersoonIndicatieHistorie(indicatie, true);
            historie.setDatumAanvangGeldigheid(datumAanvang);
            indicatie.addPersoonIndicatieHistorie(historie);
        }

    }

    @Test
    public void testHeeftIndicatieAfgeleidSamengesteldeNaam() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        assertTrue(bijhoudingPersoon.isSamengesteldenaamAfgeleid());

        final PersoonSamengesteldeNaamHistorie historie = new PersoonSamengesteldeNaamHistorie(persoon, "stam", false, true);
        persoon.addPersoonSamengesteldeNaamHistorie(historie);
        assertFalse(bijhoudingPersoon.isSamengesteldenaamAfgeleid());

        final PersoonSamengesteldeNaamHistorie historie2 = new PersoonSamengesteldeNaamHistorie(persoon, "stam", true, true);
        historie.setDatumTijdVerval(Timestamp.from(Instant.now()));
        persoon.addPersoonSamengesteldeNaamHistorie(historie2);
        assertTrue(bijhoudingPersoon.isSamengesteldenaamAfgeleid());
    }

    @Test
    public void testHeeftIndicatieAfgeleidNaamgebruik() {
        final BijhoudingPersoon bijhoudingPersoon = mock(BijhoudingPersoon.class);
        when(bijhoudingPersoon.isNaamgebruikAfgeleid()).thenCallRealMethod();
        final Set<PersoonNaamgebruikHistorie> historieLijst = new HashSet<>();
        when(bijhoudingPersoon.getPersoonNaamgebruikHistorieSet()).thenReturn(historieLijst);
        assertTrue(bijhoudingPersoon.isNaamgebruikAfgeleid());
        final PersoonNaamgebruikHistorie historie = new PersoonNaamgebruikHistorie(bijhoudingPersoon, "stam", false, Naamgebruik.EIGEN);
        historieLijst.add(historie);
        assertFalse(bijhoudingPersoon.isNaamgebruikAfgeleid());
        final PersoonNaamgebruikHistorie historie2 = new PersoonNaamgebruikHistorie(bijhoudingPersoon, "stam", true, Naamgebruik.EIGEN);
        historie.setDatumTijdVerval(Timestamp.from(Instant.now()));
        historieLijst.add(historie2);
        when(bijhoudingPersoon.getPersoonNaamgebruikHistorieSet()).thenReturn(historieLijst);
        assertTrue(bijhoudingPersoon.isNaamgebruikAfgeleid());
    }

    @Test
    public void testMaakNieuweIDHistorieVoorCorrectie() {
        final String bsn = "123456789";
        final String anummer = "1234567890";
        final ElementBuilder builder = new ElementBuilder();
        IdentificatienummersElement element = builder.maakIdentificatienummersElement("idNummers", bsn, anummer);
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        final PersoonIDHistorie persoonIDHistorie = bijhoudingPersoon.maakNieuweIDHistorieVoorCorrectie(element);

        assertNotNull(persoonIDHistorie);
        assertEquals(persoon, persoonIDHistorie.getPersoon());
        assertEquals(bsn, persoonIDHistorie.getBurgerservicenummer());
        assertEquals(anummer, persoonIDHistorie.getAdministratienummer());
    }

    @Test
    public void testMaakNieuwePersoonSamengesteldeNaamHistorieVoorCorrectie() {
        final ElementBuilder builder = new ElementBuilder();
        ElementBuilder.NaamParameters naamParams = new ElementBuilder.NaamParameters();
        final String geslachtnaamStam = "stam";
        final String voornamen = "voornamen";
        final boolean namenreeks = false;
        final String adelijkeTitel = "H";
        final String predicaat = "K";
        final char scheidingsteken = '-';
        final String voorvoegsel = "voorvoegsel";

        naamParams.geslachtsnaamstam(geslachtnaamStam);
        naamParams.indicatieNamenreeks(namenreeks);
        naamParams.adellijkeTitelCode(adelijkeTitel);
        naamParams.voornamen(voornamen);
        naamParams.predicaatCode(predicaat);
        naamParams.scheidingsteken(scheidingsteken);
        naamParams.voorvoegsel(voorvoegsel);

        SamengesteldeNaamElement element = builder.maakSamengesteldeNaamElement("idNummers", naamParams);
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorie = bijhoudingPersoon.maakNieuweSamengesteldeNaamHistorieVoorCorrectie(element);

        assertNotNull(persoonSamengesteldeNaamHistorie);
        assertEquals(persoon, persoonSamengesteldeNaamHistorie.getPersoon());
        assertEquals(geslachtnaamStam, persoonSamengesteldeNaamHistorie.getGeslachtsnaamstam());
        assertEquals(voornamen, persoonSamengesteldeNaamHistorie.getVoornamen());
        assertEquals(namenreeks, persoonSamengesteldeNaamHistorie.getIndicatieNamenreeks());
        assertEquals(adelijkeTitel, persoonSamengesteldeNaamHistorie.getAdellijkeTitel().getCode());
        assertEquals(predicaat, persoonSamengesteldeNaamHistorie.getPredicaat().getCode());
        assertEquals(voorvoegsel, persoonSamengesteldeNaamHistorie.getVoorvoegsel());
        assertTrue(scheidingsteken == persoonSamengesteldeNaamHistorie.getScheidingsteken());

    }

    @Test
    public void testMaakNieuweGeboorteHistorieVoorCorrectie() {
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.GeboorteParameters params = new ElementBuilder.GeboorteParameters();
        final Integer geboortedatum = 2016_01_01;
        final String buitenlandsePlaats = "Gent";
        final String buitenlandseRegio = "Vlaanderen";
        final Gemeente gemeente = new Gemeente((short) 1, "Plaats", "0590", new Partij("Partij", "000001"));
        final LandOfGebied landGebied = new LandOfGebied("6030", "Nederland");
        final String omschrijvingLocatie = "Ergens in een park";
        final String woonplaatsnaam = "Den Haag";
        params.datum(geboortedatum);
        params.buitenlandsePlaats(buitenlandsePlaats);
        params.buitenlandseRegio(buitenlandseRegio);
        params.gemeenteCode(gemeente.getCode());
        params.landGebiedCode(landGebied.getCode());
        params.omschrijvingLocatie(omschrijvingLocatie);
        params.woonplaatsnaam(woonplaatsnaam);

        GeboorteElement element = builder.maakGeboorteElement("geboorte", params);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(gemeente.getCode())).thenReturn(gemeente);
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(landGebied.getCode())).thenReturn(landGebied);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        final PersoonGeboorteHistorie persoonGeboorteHistorie = bijhoudingPersoon.maakNieuweGeboorteHistorieVoorCorrectie(element);

        assertNotNull(persoonGeboorteHistorie);
        assertEquals(persoon, persoonGeboorteHistorie.getPersoon());
        assertEquals(geboortedatum, (Integer) persoonGeboorteHistorie.getDatumGeboorte());
        assertEquals(buitenlandsePlaats, persoonGeboorteHistorie.getBuitenlandsePlaatsGeboorte());
        assertEquals(buitenlandseRegio, persoonGeboorteHistorie.getBuitenlandseRegioGeboorte());
        assertEquals(gemeente, persoonGeboorteHistorie.getGemeente());
        assertEquals(landGebied, persoonGeboorteHistorie.getLandOfGebied());
        assertEquals(omschrijvingLocatie, persoonGeboorteHistorie.getOmschrijvingGeboortelocatie());
        assertEquals(woonplaatsnaam, persoonGeboorteHistorie.getWoonplaatsnaamGeboorte());
    }

    @Test
    public void testGetPersoonIDHistorieSet() {
        final Persoon delegate1 = new Persoon(SoortPersoon.INGESCHREVENE);
        final Persoon delegate2 = new Persoon(SoortPersoon.INGESCHREVENE);

        final PersoonIDHistorie historie1 = new PersoonIDHistorie(delegate1);
        historie1.setDatumAanvangGeldigheid(2010_01_01);
        final PersoonIDHistorie historie2 = new PersoonIDHistorie(delegate2);
        historie2.setDatumAanvangGeldigheid(2016_01_01);

        delegate1.addPersoonIDHistorie(historie1);
        delegate2.addPersoonIDHistorie(historie2);

        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(delegate1);
        bijhoudingPersoon.addDelegate(delegate2);

        assertEquals(1, delegate1.getPersoonIDHistorieSet().size());
        assertEquals(1, delegate2.getPersoonIDHistorieSet().size());

        assertEquals(2, bijhoudingPersoon.getDelegates().size());
        final Set<PersoonIDHistorie> idHistories = bijhoudingPersoon.getPersoonIDHistorieSet();
        assertTrue(idHistories instanceof BijhoudingPersoonGroepSet);
        // Alle delegates hebben als het goed is hetzelfde aantal historie voor de groep
        assertEquals(1, idHistories.size());
    }

    @Test
    public void testGetPersoonGeboorteHistorieSet() {
        final Persoon delegate1 = new Persoon(SoortPersoon.INGESCHREVENE);
        final Persoon delegate2 = new Persoon(SoortPersoon.INGESCHREVENE);

        final PersoonGeboorteHistorie historie1 = new PersoonGeboorteHistorie(delegate1, 2010_01_01, NEDERLAND);
        final PersoonGeboorteHistorie historie2 = new PersoonGeboorteHistorie(delegate2, 2016_01_01, NEDERLAND);

        delegate1.addPersoonGeboorteHistorie(historie1);
        delegate2.addPersoonGeboorteHistorie(historie2);

        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(delegate1);
        bijhoudingPersoon.addDelegate(delegate2);

        assertEquals(1, delegate1.getPersoonGeboorteHistorieSet().size());
        assertEquals(1, delegate2.getPersoonGeboorteHistorieSet().size());

        assertEquals(2, bijhoudingPersoon.getDelegates().size());
        final Set<PersoonGeboorteHistorie> idHistories = bijhoudingPersoon.getPersoonGeboorteHistorieSet();
        assertTrue(idHistories instanceof BijhoudingPersoonGroepSet);
        // Alle delegates hebben als het goed is hetzelfde aantal historie voor de groep
        assertEquals(1, idHistories.size());
    }

    @Test
    public void testGetPersoonGeslachtsaanduidingHistorieSet() {
        final Persoon delegate1 = new Persoon(SoortPersoon.INGESCHREVENE);
        final Persoon delegate2 = new Persoon(SoortPersoon.INGESCHREVENE);

        final PersoonGeslachtsaanduidingHistorie historie1 = new PersoonGeslachtsaanduidingHistorie(delegate1, Geslachtsaanduiding.MAN);
        final PersoonGeslachtsaanduidingHistorie historie2 = new PersoonGeslachtsaanduidingHistorie(delegate2, Geslachtsaanduiding.VROUW);

        delegate1.addPersoonGeslachtsaanduidingHistorie(historie1);
        delegate2.addPersoonGeslachtsaanduidingHistorie(historie2);

        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(delegate1);
        bijhoudingPersoon.addDelegate(delegate2);

        assertEquals(1, delegate1.getPersoonGeslachtsaanduidingHistorieSet().size());
        assertEquals(1, delegate2.getPersoonGeslachtsaanduidingHistorieSet().size());

        assertEquals(2, bijhoudingPersoon.getDelegates().size());
        final Set<PersoonGeslachtsaanduidingHistorie> idHistories = bijhoudingPersoon.getPersoonGeslachtsaanduidingHistorieSet();
        assertTrue(idHistories instanceof BijhoudingPersoonGroepSet);
        // Alle delegates hebben als het goed is hetzelfde aantal historie voor de groep
        assertEquals(1, idHistories.size());
    }

    @Test
    public void testMaakNieuweGeslachtsaanduidingHistorieVoorCorrecties() {
        final ElementBuilder builder = new ElementBuilder();
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        final GeslachtsaanduidingElement element = builder.maakGeslachtsaanduidingElement("element", "M");
        final PersoonGeslachtsaanduidingHistorie historie = bijhoudingPersoon.maakNieuweGeslachtsaanduidingHistorieVoorCorrectie(element);
        assertNotNull(historie);
        assertEquals(persoon, historie.getPersoon());
        assertEquals(Geslachtsaanduiding.MAN, historie.getGeslachtsaanduiding());
    }

    @Test
    public void testZoekRelatieHistorieVoorVoorkomenSleutel() {
        //setup test persoon met groepen voor pseudo personen (resultaat kopie bij ontrelateren)
        final Timestamp tsReg = new Timestamp(System.currentTimeMillis());
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(persoon);
        idHistorie.setId(1L);
        idHistorie.setDatumTijdRegistratie(tsReg);
        idHistorie.setAdministratienummer("1234567890");
        persoon.addPersoonIDHistorie(idHistorie);
        final PersoonGeboorteHistorie geboorteHistorie = new PersoonGeboorteHistorie(persoon, 20000101, new LandOfGebied("1234", "test land"));
        geboorteHistorie.setId(1L);
        geboorteHistorie.setDatumTijdRegistratie(tsReg);
        persoon.addPersoonGeboorteHistorie(geboorteHistorie);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(persoon, "naam", false, false);
        samengesteldeNaamHistorie.setId(1L);
        samengesteldeNaamHistorie.setDatumTijdRegistratie(tsReg);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHistorie);
        final PersoonGeslachtsaanduidingHistorie geslachtsaanduidingHistorie = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        geslachtsaanduidingHistorie.setId(1L);
        geslachtsaanduidingHistorie.setDatumTijdRegistratie(tsReg);
        persoon.addPersoonGeslachtsaanduidingHistorie(geslachtsaanduidingHistorie);
        //decorate persoon
        final BijhoudingPersoon bijhoudingPersoon = BijhoudingPersoon.decorate(persoon);
        //verify zoek
        assertSame(idHistorie, bijhoudingPersoon.zoekRelatieHistorieVoorVoorkomenSleutel(String.valueOf(idHistorie.getId()), PersoonIDHistorie.class));
        assertSame(geboorteHistorie,
                bijhoudingPersoon.zoekRelatieHistorieVoorVoorkomenSleutel(String.valueOf(geboorteHistorie.getId()), PersoonGeboorteHistorie.class));
        assertSame(samengesteldeNaamHistorie, bijhoudingPersoon
                .zoekRelatieHistorieVoorVoorkomenSleutel(String.valueOf(samengesteldeNaamHistorie.getId()), PersoonSamengesteldeNaamHistorie.class));
        assertSame(geslachtsaanduidingHistorie, bijhoudingPersoon
                .zoekRelatieHistorieVoorVoorkomenSleutel(String.valueOf(geslachtsaanduidingHistorie.getId()), PersoonGeslachtsaanduidingHistorie.class));

        assertNotSame(samengesteldeNaamHistorie, bijhoudingPersoon
                .zoekRelatieHistorieVoorVoorkomenSleutel(String.valueOf(geslachtsaanduidingHistorie.getId()), PersoonGeslachtsaanduidingHistorie.class));

        assertEquals(idHistorie.getAdministratienummer(),
                bijhoudingPersoon.zoekRelatieHistorieVoorVoorkomenSleutel(String.valueOf(idHistorie.getId()), PersoonIDHistorie.class)
                        .getAdministratienummer());

        final PersoonIDHistorie idHistorieNieuw = new PersoonIDHistorie(persoon);
        idHistorie.setAdministratienummer("0987654321");
        bijhoudingPersoon.registreerKopieHistorie(idHistorie.getId(), idHistorieNieuw);
        assertNotSame(idHistorie, bijhoudingPersoon.zoekRelatieHistorieVoorVoorkomenSleutel(String.valueOf(idHistorie.getId()), PersoonIDHistorie.class));
        assertSame(idHistorieNieuw, bijhoudingPersoon.zoekRelatieHistorieVoorVoorkomenSleutel(String.valueOf(idHistorie.getId()), PersoonIDHistorie.class));
        assertEquals(idHistorieNieuw.getAdministratienummer(),
                bijhoudingPersoon.zoekRelatieHistorieVoorVoorkomenSleutel(String.valueOf(idHistorie.getId()), PersoonIDHistorie.class)
                        .getAdministratienummer());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZoekRelatieHistorieVoorVoorkomenSleutelFout() {
        BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE)).zoekRelatieHistorieVoorVoorkomenSleutel("1", PersoonVoornaamHistorie.class);
    }

    @Test
    public void testPersoonHeeftOnderzoek() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Onderzoek onderzoek = new Onderzoek(PARTIJ_1, persoon);
        persoon.addOnderzoek(onderzoek);

        final BijhoudingPersoon bijhoudingPersoon = new BijhoudingPersoon(persoon);
        onderzoek.setStatusOnderzoek(StatusOnderzoek.IN_UITVOERING);
        assertTrue(bijhoudingPersoon.heeftLopendOnderzoek());
        onderzoek.setStatusOnderzoek(StatusOnderzoek.GESTAAKT);
        assertTrue(bijhoudingPersoon.heeftLopendOnderzoek());
        onderzoek.setStatusOnderzoek(StatusOnderzoek.AFGESLOTEN);
        assertFalse(bijhoudingPersoon.heeftLopendOnderzoek());
    }

    private PersoonNationaliteit voegPersoonNationaliteitToe(final Persoon persoon, final Nationaliteit nationaliteit, final boolean isVervallen) {
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, nationaliteit);
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));

        if (isVervallen) {
            final AdministratieveHandeling administratieveHandeling = getAdministratieveHandeling();
            final Timestamp timestamp = Timestamp.from(Instant.now().plus(-1, ChronoUnit.DAYS));
            final BRPActie actie =
                    new BRPActie(
                            SoortActie.REGISTRATIE_GEBOORTE,
                            administratieveHandeling,
                            administratieveHandeling.getPartij(),
                            timestamp);

            historie.setActieVerval(actie);
            historie.setDatumTijdVerval(timestamp);
        }
        persoonNationaliteit.addPersoonNationaliteitHistorie(historie);
        persoon.addPersoonNationaliteit(persoonNationaliteit);
        return persoonNationaliteit;
    }

    private void controleerPersoon(final BijhoudingPersoon bijhoudingPersoon)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        final List<String> methodeOverslaan =
                Arrays.asList(
                        "getPersoonIDHistorieSet",
                        "getPersoonGeboorteHistorieSet",
                        "getPersoonGeslachtsaanduidingHistorieSet",
                        "getPersoonSamengesteldeNaamHistorieSet",
                        "getSoortPersoon",
                        "getIndicatieAfgeleid",
                        "getClass",
                        "getSorteervolgorde",
                        "getNietVervallenMaterieleGroepen");
        final Persoon persoon = bijhoudingPersoon.getDelegates().iterator().next();
        final Map<String, Object> persoonWaardesMap = new HashMap<>();
        final PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(persoon.getClass()).getPropertyDescriptors();
        for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getReadMethod() != null && !methodeOverslaan.contains(propertyDescriptor.getReadMethod().getName())) {
                persoonWaardesMap.put(propertyDescriptor.getReadMethod().getName(), propertyDescriptor.getReadMethod().invoke(persoon));
            }
        }

        persoonWaardesMap.entrySet().stream().filter(entry -> !methodeOverslaan.contains(entry.getKey())).forEach(entry -> {
            final Object entryValue = entry.getValue();
            if (entryValue instanceof Set) {
                assertEquals(0, ((Set) entryValue).size());
            } else if (entryValue instanceof Boolean) {
                assertFalse((Boolean) entryValue);
            } else if (entryValue instanceof Map) {
                assertEquals(Collections.emptyMap(), entryValue);
            } else {
                assertNull(entryValue);
            }
        });
    }

    private void voegPersoonIDHistorieToe(final Persoon persoon, final boolean datumAanvangNull) {
        final PersoonIDHistorie persoonIDHistorie = new PersoonIDHistorie(persoon);
        persoonIDHistorie.setId(1L);
        persoonIDHistorie.setBurgerservicenummer("681856841");
        persoonIDHistorie.setAdministratienummer("4829083154");
        if (!datumAanvangNull) {
            persoonIDHistorie.setDatumAanvangGeldigheid(20160202);
        }
        persoon.addPersoonIDHistorie(persoonIDHistorie);
    }

    private void voegSamengesteldeNaamHisToe(final Persoon persoon, final boolean datumAanvangNull) {
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisActueel = new PersoonSamengesteldeNaamHistorie(persoon, "Vlag", true, true);
        samengesteldeNaamHisActueel.setId(1L);
        samengesteldeNaamHisActueel.setVoornamen("Cees");
        samengesteldeNaamHisActueel.setVoorvoegsel("de");
        samengesteldeNaamHisActueel.setAdellijkeTitel(AdellijkeTitel.B);
        samengesteldeNaamHisActueel.setScheidingsteken('-');
        samengesteldeNaamHisActueel.setPredicaat(Predicaat.H);
        samengesteldeNaamHisActueel.setGeslachtsnaamstam("de Prins");
        samengesteldeNaamHisActueel.setDatumTijdRegistratie(TS_19810101);
        if (!datumAanvangNull) {
            samengesteldeNaamHisActueel.setDatumAanvangGeldigheid(20160202);
        }
        samengesteldeNaamHisActueel.setIndicatieVoorkomenTbvLeveringMutaties(true);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHisActueel);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisBeeindigd = new PersoonSamengesteldeNaamHistorie(persoon, "Vlag", true, true);
        samengesteldeNaamHisBeeindigd.setId(2L);
        samengesteldeNaamHisBeeindigd.setDatumTijdRegistratie(TS_19810201);
        samengesteldeNaamHisBeeindigd.setDatumAanvangGeldigheid(19671223);
        samengesteldeNaamHisBeeindigd.setDatumEindeGeldigheid(19671224);
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHisBeeindigd);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisVervallen = new PersoonSamengesteldeNaamHistorie(persoon, "Vlag", true, true);
        samengesteldeNaamHisVervallen.setId(3L);
        samengesteldeNaamHisVervallen.setDatumTijdRegistratie(TS_19810301);
        samengesteldeNaamHisVervallen.setDatumAanvangGeldigheid(19671223);
        samengesteldeNaamHisVervallen.setDatumTijdVerval(TS_19810301);
        samengesteldeNaamHisVervallen.setNadereAanduidingVerval('S');
        persoon.addPersoonSamengesteldeNaamHistorie(samengesteldeNaamHisVervallen);
    }

    public void voegGeslachtsaanduidingHisToe(final Persoon persoon, final boolean datumAanvangNull) {
        final PersoonGeslachtsaanduidingHistorie geslAandHisActueel = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.ONBEKEND);
        geslAandHisActueel.setId(1L);
        geslAandHisActueel.setDatumTijdRegistratie(TS_19810101);
        if (!datumAanvangNull) {
            geslAandHisActueel.setDatumAanvangGeldigheid(19671223);
        }
        geslAandHisActueel.setIndicatieVoorkomenTbvLeveringMutaties(true);
        persoon.addPersoonGeslachtsaanduidingHistorie(geslAandHisActueel);
        final PersoonGeslachtsaanduidingHistorie geslAandHisBeeindigd = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.ONBEKEND);
        geslAandHisBeeindigd.setId(2L);
        geslAandHisBeeindigd.setDatumTijdRegistratie(TS_19810201);
        geslAandHisBeeindigd.setDatumAanvangGeldigheid(19671223);
        geslAandHisBeeindigd.setDatumEindeGeldigheid(19671224);
        persoon.addPersoonGeslachtsaanduidingHistorie(geslAandHisBeeindigd);
        final PersoonGeslachtsaanduidingHistorie geslAandHisVervallen = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.ONBEKEND);
        geslAandHisVervallen.setId(3L);
        geslAandHisVervallen.setDatumTijdRegistratie(TS_19810301);
        geslAandHisVervallen.setDatumAanvangGeldigheid(19671223);
        geslAandHisVervallen.setDatumTijdVerval(TS_19810301);
        geslAandHisVervallen.setNadereAanduidingVerval('S');
        persoon.addPersoonGeslachtsaanduidingHistorie(geslAandHisVervallen);
    }

    public void voegGeboorteHisToe(final Persoon persoon) {
        final LandOfGebied landOfGebied = new LandOfGebied("0001", "6008");
        final Gemeente gemeente = new Gemeente(Short.parseShort("1"), "Gemeente", "0001", new Partij("zendende partij", "053001"));
        final PersoonGeboorteHistorie geboorteHistorieActueel = new PersoonGeboorteHistorie(persoon, DATUM_19810101, landOfGebied);
        geboorteHistorieActueel.setId(1L);
        geboorteHistorieActueel.setGemeente(gemeente);
        geboorteHistorieActueel.setDatumGeboorte(DATUM_19810101);
        geboorteHistorieActueel.setLandOfGebied(landOfGebied);
        geboorteHistorieActueel.setOmschrijvingGeboortelocatie("omschr geboorte locatie Cees");
        geboorteHistorieActueel.setWoonplaatsnaamGeboorte("woonplaats geboorte Cees");
        geboorteHistorieActueel.setBuitenlandsePlaatsGeboorte("buitenlandse plaats geboorte Cees");
        geboorteHistorieActueel.setBuitenlandseRegioGeboorte("buitenlandse regio geboorte Cees");
        geboorteHistorieActueel.setDatumTijdRegistratie(TS_19810101);
        geboorteHistorieActueel.setIndicatieVoorkomenTbvLeveringMutaties(true);
        persoon.addPersoonGeboorteHistorie(geboorteHistorieActueel);
        final PersoonGeboorteHistorie geboorteHistorieVervallen = new PersoonGeboorteHistorie(persoon, DATUM_19810101, landOfGebied);
        geboorteHistorieVervallen.setId(2L);
        geboorteHistorieVervallen.setDatumTijdRegistratie(TS_19810301);
        geboorteHistorieVervallen.setDatumTijdVerval(TS_19810301);
        geboorteHistorieVervallen.setNadereAanduidingVerval('S');
        persoon.addPersoonGeboorteHistorie(geboorteHistorieVervallen);
    }


    private void maakHuwelijk(final BijhoudingPersoon persoon, final BijhoudingPersoon partner) {
        final Relatie huwelijk = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie huwelijkHistorie = new RelatieHistorie(huwelijk);
        huwelijk.addRelatieHistorie(huwelijkHistorie);

        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk);
        final Betrokkenheid partnerBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, huwelijk);
        huwelijk.addBetrokkenheid(ikBetrokkenheid);
        huwelijk.addBetrokkenheid(partnerBetrokkenheid);
        persoon.addBetrokkenheid(ikBetrokkenheid);
        partner.addBetrokkenheid(partnerBetrokkenheid);
    }
}
