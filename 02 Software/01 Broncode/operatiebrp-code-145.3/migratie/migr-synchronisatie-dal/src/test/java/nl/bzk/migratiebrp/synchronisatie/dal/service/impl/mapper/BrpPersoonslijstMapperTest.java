/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonPersoonskaartHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonUitsluitingKiesrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * De inhoudelijke attributen, actie en documenten zijn allemaal al getest in de andere (specifieke) tests. Deze test is
 * alleen voor de aantallen records om te testen dat de loops goed gaan.
 */
public class BrpPersoonslijstMapperTest extends BrpAbstractTest {
    private static final Partij ONBEKENDE_PARTIJ = new Partij("?", "000001");

    private static final LandOfGebied NEDERLAND = new LandOfGebied("6030", "Nederland");
    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<>());
    private Timestamp tsRegistratie;
    private Timestamp datumTijdStempel;
    @Inject
    private BrpPersoonslijstMapper mapper;

    @Before
    public void setup() {
        tsRegistratie = timestamp("197001010000000");
        datumTijdStempel = timestamp("201301010000000");
    }

    @Test
    public void testNull() {
        Assert.assertNull(mapper.mapNaarMigratie(null, brpOnderzoekMapper));
    }

    @Test
    public void testEmpty() {
        final BrpPersoonslijst result = mapper.mapNaarMigratie(new Persoon(SoortPersoon.INGESCHREVENE), brpOnderzoekMapper);
        Assert.assertNotNull(result);
    }

    @Test
    public void testBasisStapels() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.addPersoonNaamgebruikHistorie(vul(persoonNaamgebruikHistorie(persoon)));
        persoon.addPersoonNaamgebruikHistorie(vul(persoonNaamgebruikHistorie(persoon)));
        persoon.addPersoonNaamgebruikHistorie(vul(persoonNaamgebruikHistorie(persoon)));
        final PersoonAdres persoonAdres = new PersoonAdres(persoon);
        final RedenWijzigingVerblijf redenWijziging = new RedenWijzigingVerblijf('W', "Wijziging");
        persoonAdres.addPersoonAdresHistorie(
                vul(new PersoonAdresHistorie(persoonAdres, SoortAdres.WOONADRES, new LandOfGebied("0344", "LandOfGebied"), redenWijziging)));
        persoonAdres.addPersoonAdresHistorie(
                vul(new PersoonAdresHistorie(persoonAdres, SoortAdres.WOONADRES, new LandOfGebied("0344", "LandOfGebied"), redenWijziging)));
        persoon.addPersoonAdres(persoonAdres);
        persoon.addPersoonBijhoudingHistorie(
                vul(new PersoonBijhoudingHistorie(persoon, ONBEKENDE_PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL)));
        persoon.addPersoonBijhoudingHistorie(
                vul(new PersoonBijhoudingHistorie(persoon, ONBEKENDE_PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL)));
        persoon.addPersoonDeelnameEuVerkiezingenHistorie(vul(new PersoonDeelnameEuVerkiezingenHistorie(persoon, false)));
        persoon.addPersoonDeelnameEuVerkiezingenHistorie(vul(new PersoonDeelnameEuVerkiezingenHistorie(persoon, false)));
        persoon.addPersoonGeboorteHistorie(vul(new PersoonGeboorteHistorie(persoon, 20000101, NEDERLAND)));
        persoon.addPersoonGeslachtsaanduidingHistorie(vul(new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN)));
        persoon.addPersoonGeslachtsaanduidingHistorie(vul(new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN)));
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent(persoon, 1, "Gesl"));
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent(persoon, 2, "acht"));
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent(persoon, 3, "snaam"));
        persoon.addPersoonIDHistorie(vul(new PersoonIDHistorie(persoon)));
        persoon.addPersoonIDHistorie(vul(new PersoonIDHistorie(persoon)));
        persoon.addPersoonIDHistorie(vul(new PersoonIDHistorie(persoon)));
        persoon.addPersoonIDHistorie(vul(new PersoonIDHistorie(persoon)));
        persoon.addPersoonMigratieHistorie(vul(new PersoonMigratieHistorie(persoon, SoortMigratie.IMMIGRATIE)));
        persoon.addPersoonInschrijvingHistorie(vulInschrijving(new PersoonInschrijvingHistorie(persoon, 20000101, 1, datumTijdStempel), 1L));
        persoon.addPersoonInschrijvingHistorie(vulInschrijving(new PersoonInschrijvingHistorie(persoon, 20000101, 1, datumTijdStempel), 2L));
        persoon.addPersoonInschrijvingHistorie(vulInschrijving(new PersoonInschrijvingHistorie(persoon, 20000101, 1, datumTijdStempel), 3L));
        persoon.addPersoonNummerverwijzingHistorie(vulNummerverwijzing(persoon));
        persoon.addPersoonNationaliteit(nationaliteit(persoon, "Behandeld als Nederlander", "0002"));
        persoon.addPersoonNationaliteit(nationaliteit(persoon, "Burger van de Bondsrepubliek Duitsland", "0055"));
        persoon.addPersoonOverlijdenHistorie(vul(new PersoonOverlijdenHistorie(persoon, 20000101, NEDERLAND)));
        persoon.addPersoonOverlijdenHistorie(vul(new PersoonOverlijdenHistorie(persoon, 20000101, NEDERLAND)));
        persoon.addPersoonOverlijdenHistorie(vul(new PersoonOverlijdenHistorie(persoon, 20000101, NEDERLAND)));
        persoon.addPersoonPersoonskaartHistorie(vul(new PersoonPersoonskaartHistorie(persoon, false)));
        persoon.addPersoonPersoonskaartHistorie(vul(new PersoonPersoonskaartHistorie(persoon, false)));
        persoon.addPersoonReisdocument(reisdocument(persoon, "Reisdoc-1", "Oms1"));
        persoon.addPersoonReisdocument(reisdocument(persoon, "Reisdoc-2", "Oms2"));
        persoon.addPersoonReisdocument(reisdocument(persoon, "Reisdoc-3", "Oms3"));
        persoon.addPersoonSamengesteldeNaamHistorie(vulSamengesteldeNaam(new PersoonSamengesteldeNaamHistorie(persoon, "Jansen", false, false)));
        persoon.addPersoonSamengesteldeNaamHistorie(vulSamengesteldeNaam(new PersoonSamengesteldeNaamHistorie(persoon, "Jansen", false, false)));
        persoon.addPersoonUitsluitingKiesrechtHistorie(vul(new PersoonUitsluitingKiesrechtHistorie(persoon, false)));
        persoon.addPersoonUitsluitingKiesrechtHistorie(vul(new PersoonUitsluitingKiesrechtHistorie(persoon, false)));
        persoon.addPersoonUitsluitingKiesrechtHistorie(vul(new PersoonUitsluitingKiesrechtHistorie(persoon, false)));
        persoon.addPersoonUitsluitingKiesrechtHistorie(vul(new PersoonUitsluitingKiesrechtHistorie(persoon, false)));
        persoon.addPersoonVerblijfsrechtHistorie(
                vul(new PersoonVerblijfsrechtHistorie(persoon, new Verblijfsrecht("00", "Onbekend"), 20000101, 20000101)));
        persoon.addPersoonVerblijfsrechtHistorie(
                vul(new PersoonVerblijfsrechtHistorie(persoon, new Verblijfsrecht("00", "Onbekend"), 20000101, 20000101)));
        persoon.addPersoonVoornaam(voornaam(persoon, 1));
        persoon.addPersoonVoornaam(voornaam(persoon, 2));
        persoon.addPersoonVoornaam(voornaam(persoon, 3));
        persoon.addPersoonVoornaam(voornaam(persoon, 4));
        persoon.addPersoonAfgeleidAdministratiefHistorie(afgeleidAdministratief(persoon, tsRegistratie.getTime()));

        final BrpPersoonslijst result = mapper.mapNaarMigratie(persoon, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.getNaamgebruikStapel().size());
        Assert.assertEquals(2, result.getAdresStapel().size());
        Assert.assertEquals(1, result.getPersoonAfgeleidAdministratiefStapel().size());
        Assert.assertEquals(2, result.getBijhoudingStapel().size());
        Assert.assertEquals(2, result.getDeelnameEuVerkiezingenStapel().size());
        Assert.assertEquals(1, result.getGeboorteStapel().size());
        Assert.assertEquals(2, result.getGeslachtsaanduidingStapel().size());
        Assert.assertEquals(3, result.getGeslachtsnaamcomponentStapels().size());
        Assert.assertEquals(4, result.getIdentificatienummerStapel().size());
        Assert.assertEquals(1, result.getMigratieStapel().size());
        Assert.assertEquals(3, result.getInschrijvingStapel().size());
        Assert.assertEquals(2, result.getNationaliteitStapels().size());
        // Assert.assertEquals(1, result.getNummerverwijzingStapel().size());
        Assert.assertEquals(3, result.getOverlijdenStapel().size());
        Assert.assertEquals(2, result.getPersoonskaartStapel().size());
        Assert.assertEquals(3, result.getReisdocumentStapels().size());
        Assert.assertEquals(2, result.getSamengesteldeNaamStapel().size());
        Assert.assertEquals(4, result.getUitsluitingKiesrechtStapel().size());
        Assert.assertEquals(2, result.getVerblijfsrechtStapel().size());
        Assert.assertEquals(4, result.getVoornaamStapels().size());
        Assert.assertEquals(
                Lo3CategorieEnum.CATEGORIE_01,
                result.getVoornaamStapels().get(0).getLaatsteElement().getActieInhoud().getLo3Herkomst().getCategorie());
        Assert.assertEquals(0, result.getVoornaamStapels().get(0).getLaatsteElement().getActieInhoud().getLo3Herkomst().getVoorkomen());
    }

    private PersoonNaamgebruikHistorie persoonNaamgebruikHistorie(final Persoon persoon) {
        return new PersoonNaamgebruikHistorie(persoon, "Jansen", false, Naamgebruik.EIGEN);
    }

    private PersoonInschrijvingHistorie vulInschrijving(final PersoonInschrijvingHistorie historie, final long versienummer) {
        historie.setVersienummer(versienummer);
        historie.setDatumInschrijving(20040101);
        return vul(historie);
    }

    private PersoonNummerverwijzingHistorie vulNummerverwijzing(final Persoon persoon) {
        final PersoonNummerverwijzingHistorie historie = new PersoonNummerverwijzingHistorie(persoon);
        historie.setVolgendeAdministratienummer("1234567890");
        historie.setVorigeAdministratienummer("9876543210");
        return vul(historie);
    }

    private PersoonGeslachtsnaamcomponent geslachtsnaamcomponent(final Persoon persoon, final int volgnummer, final String naam) {
        final PersoonGeslachtsnaamcomponent result = new PersoonGeslachtsnaamcomponent(persoon, volgnummer);
        final PersoonGeslachtsnaamcomponentHistorie historie = vul(new PersoonGeslachtsnaamcomponentHistorie(result, "Jansen"));
        historie.setStam(naam);
        result.addPersoonGeslachtsnaamcomponentHistorie(historie);
        result.addPersoonGeslachtsnaamcomponentHistorie(historie);
        return result;
    }

    private PersoonNationaliteit nationaliteit(final Persoon persoon, final String naam, final String code) {
        final PersoonNationaliteit result = new PersoonNationaliteit(persoon, new Nationaliteit(naam, code));
        result.addPersoonNationaliteitHistorie(vul(new PersoonNationaliteitHistorie(result)));
        return result;
    }

    private PersoonReisdocument reisdocument(final Persoon persoon, final String soort, final String omschrijving) {
        final PersoonReisdocument result = new PersoonReisdocument(persoon, new SoortNederlandsReisdocument(soort, omschrijving));

        final String autoriteitVanAfgifteReisdocument = "code";
        final PersoonReisdocumentHistorie historie =
                vul(new PersoonReisdocumentHistorie(20000101, 20000101, 20100101, "123", autoriteitVanAfgifteReisdocument, result));
        historie.setNummer("2342-RE-XXX1");
        historie.setDatumIngangDocument(20120101);
        historie.setDatumUitgifte(20120101);
        historie.setAutoriteitVanAfgifte(autoriteitVanAfgifteReisdocument);
        historie.setDatumEindeDocument(20170101);
        result.addPersoonReisdocumentHistorieSet(historie);
        return result;
    }

    private PersoonSamengesteldeNaamHistorie vulSamengesteldeNaam(final PersoonSamengesteldeNaamHistorie historie) {
        historie.setIndicatieAfgeleid(Boolean.TRUE);
        historie.setIndicatieNamenreeks(Boolean.FALSE);
        return vul(historie);
    }

    private PersoonVoornaam voornaam(final Persoon persoon, final int volgnummer) {
        final PersoonVoornaam result = new PersoonVoornaam(persoon, 1);
        result.setVolgnummer(volgnummer);
        final BRPActie brpActie1 =
                new BRPActie(
                        SoortActie.CONVERSIE_GBA,
                        new AdministratieveHandeling(
                                ONBEKENDE_PARTIJ,
                                SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL,
                                new Timestamp(System.currentTimeMillis())),
                        ONBEKENDE_PARTIJ,
                        new Timestamp(20000101));
        brpActie1.setId(1L);
        brpActie1.setDatumTijdRegistratie(tsRegistratie);
        final Partij partij = new Partij("Migratievoorzieningen", "999901");
        brpActie1.setPartij(partij);
        final BRPActie brpActie2 =
                new BRPActie(
                        SoortActie.CONVERSIE_GBA,
                        new AdministratieveHandeling(
                                ONBEKENDE_PARTIJ,
                                SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL,
                                new Timestamp(System.currentTimeMillis())),
                        ONBEKENDE_PARTIJ,
                        new Timestamp(20000101));
        brpActie2.setId(2L);
        brpActie2.setDatumTijdRegistratie(tsRegistratie);
        brpActie2.setPartij(partij);
        final Lo3Bericht lo3Bericht =
                new Lo3Bericht("referentie", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(System.currentTimeMillis()), "DUMMY", true);
        final Lo3Voorkomen lo3Voorkomen1 = new Lo3Voorkomen(lo3Bericht, "01");
        lo3Voorkomen1.setStapelvolgnummer(0);
        lo3Voorkomen1.setVoorkomenvolgnummer(0);
        final Lo3Voorkomen lo3Voorkomen2 = new Lo3Voorkomen(lo3Bericht, "01");
        lo3Voorkomen2.setStapelvolgnummer(0);
        lo3Voorkomen2.setVoorkomenvolgnummer(1);
        brpActie1.setLo3Voorkomen(lo3Voorkomen1);
        brpActie2.setLo3Voorkomen(lo3Voorkomen2);

        final PersoonVoornaamHistorie persoon1 = new PersoonVoornaamHistorie(result, "Jan");
        persoon1.setActieInhoud(brpActie1);
        final PersoonVoornaamHistorie persoon2 = new PersoonVoornaamHistorie(result, "Piet");
        persoon2.setActieInhoud(brpActie1);

        result.addPersoonVoornaamHistorie(vul(persoon1));
        result.addPersoonVoornaamHistorie(vul(persoon2));
        return result;
    }

    private PersoonAfgeleidAdministratiefHistorie afgeleidAdministratief(final Persoon persoon, final long tsLaatsteWijziging) {
        final Partij partij = new Partij("Migratievoorzieningen", "999901");
        final AdministratieveHandeling admhnd =
                new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, new Timestamp(System.currentTimeMillis()));
        final PersoonAfgeleidAdministratiefHistorie result =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, persoon, admhnd, new Timestamp(tsLaatsteWijziging));
        result.setDatumTijdRegistratie(tsRegistratie);
        return result;
    }

    @Test
    public void testIndicatieStapels() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.addPersoonIndicatie(indicatie(persoon, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER, true));
        persoon.addPersoonIndicatie(indicatie(persoon, SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT, true, true));
        persoon.addPersoonIndicatie(indicatie(persoon, SoortIndicatie.DERDE_HEEFT_GEZAG, true, true, true, true));
        persoon.addPersoonIndicatie(indicatie(persoon, SoortIndicatie.ONDER_CURATELE, true, true));
        persoon.addPersoonIndicatie(indicatie(persoon, SoortIndicatie.STAATLOOS, true));
        persoon.addPersoonIndicatie(indicatie(persoon, SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER, true, true));
        persoon.addPersoonIndicatie(indicatie(persoon, SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING, true, true, true));

        final BrpPersoonslijst result = mapper.mapNaarMigratie(persoon, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getBehandeldAlsNederlanderIndicatieStapel().size());
        Assert.assertEquals(2, result.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel().size());
        Assert.assertEquals(4, result.getDerdeHeeftGezagIndicatieStapel().size());
        Assert.assertEquals(2, result.getOnderCurateleIndicatieStapel().size());
        Assert.assertEquals(1, result.getStaatloosIndicatieStapel().size());
        Assert.assertEquals(2, result.getVastgesteldNietNederlanderIndicatieStapel().size());
        Assert.assertEquals(3, result.getVerstrekkingsbeperkingIndicatieStapel().size());
    }

    private PersoonIndicatie indicatie(final Persoon persoon, final SoortIndicatie soortIndicatie, final Boolean... indicatieVoorkomens) {
        final PersoonIndicatie result = new PersoonIndicatie(persoon, soortIndicatie);
        for (final Boolean indicatieVoorkomen : indicatieVoorkomens) {
            result.addPersoonIndicatieHistorie(vul(new PersoonIndicatieHistorie(result, indicatieVoorkomen)));
        }
        return result;
    }

    private <T extends FormeleHistorie> T vul(final T data) {
        data.setDatumTijdRegistratie(tsRegistratie);
        return data;
    }

}
