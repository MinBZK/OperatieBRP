/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteitAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.OnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummerHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperkingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import org.springframework.util.ReflectionUtils;

/**
 * Util vh maken v/e volledig persoonsbeeld, waarbij het maximale aantal attributen is gevuld (Dwz Alle attributen binnen groepen en objecten mbt
 * hoofdpersoon, inclusief alle mogelijke variaties mbt gerelateerden + alle variaties mbt historiepatronen).
 */
final class MaakPersoonsBeeldUtil {

    private static final Timestamp TS_19810101 = Timestamp.valueOf("1981-01-01 00:00:00.0");
    private static final Timestamp TS_19810201 = Timestamp.valueOf("1981-02-01 00:00:00.0");
    private static final Timestamp TS_19810301 = Timestamp.valueOf("1981-03-01 00:00:00.0");
    private static final Integer DATUM_19810101 = 19810101;
    private static final Integer DATUM_19810201 = 19810201;
    private static final Partij PARTIJ = new Partij("Partij", "000001");
    private static final LandOfGebied LAND_OF_GEBIED = new LandOfGebied("0001", "LandOfGebied");
    private static final Aangever AANGEVER = new Aangever('1', "Aangever", "Omschrijving");
    private static final RedenWijzigingVerblijf RDN_WIJZIGING_VERBLIJF = new RedenWijzigingVerblijf('1', "Reden wijziging verblijf");
    private static final RedenBeeindigingRelatie RDN_BEEINDIGING_RELATIE = new RedenBeeindigingRelatie('1', "Einde");
    private static final Verblijfsrecht VERBLIJFSRECHT = new Verblijfsrecht("01", "Verblijfsrecht");
    private static final Nationaliteit NATIONALITEIT = new Nationaliteit("Nationaliteit", "0001");
    private static final Gemeente GEMEENTE = new Gemeente((short) 1, "Gemeente", "0001", PARTIJ);
    private static final SoortNederlandsReisdocument SRT_NED_REISDOC = new SoortNederlandsReisdocument("1", "SoortNederlandsReisDoc");
    private static final RedenVerliesNLNationaliteit RDN_VERLIES_NL_NAT = new RedenVerliesNLNationaliteit("001", "RedenVerliesNLNationaliteit");

    private static final RedenVerkrijgingNLNationaliteit RDN_VERKRIJGING_NL_NAT = new RedenVerkrijgingNLNationaliteit("001",
            "RedenverkrijgingNLNationaliteit");
    private static final AanduidingInhoudingOfVermissingReisdocument AAND_INH_OF_VERM_REISDOC = new AanduidingInhoudingOfVermissingReisdocument
            ('1', "aanduidingInhoudingOfVermissing");

    private static List<MapInfo> attribuutMappingInfo = new ArrayList<>();

    private static AfnemerindicatiesBlob afnemerindicatiesBlob;
    private static PersoonBlob persoonBlob;
    private static PersoonCache persoonCache;

    private static List<BRPActie> actieLijst = new ArrayList<>();
    private static List<AdministratieveHandeling> admHndLijst = new ArrayList<>();

    private static Persoon persoon;
    private static Persoon gerelateerdeGeregistreerdPartner;
    private static Persoon gerelateerdKind;
    private static Persoon gerelateerdeHuwelijksPartner;
    private static Persoon gerelateerdeOuder1;
    private static Persoon gerelateerdeOuder2;

    enum RecordStatus {ACTUEEL, VERVALLEN, BEEINDIGD, FORMEEL_ACTUEEL, FORMEEL_VERVALLEN, IDENTITEIT, GEEN}

    private MaakPersoonsBeeldUtil() {
    }

    static {
        maakVerantwoording();
        maakPersoonsbeelden();
        try {
            //verblob persoon
            persoonBlob = Blobber.maakBlob(persoon);
            final byte[] bytes = Blobber.toJsonBytes(persoonBlob);
            persoonBlob = Blobber.deserializeNaarPersoonBlob(bytes);
            //verblob afnemerindicaties
            List<PersoonAfnemerindicatie> afnemerindicaties = mapAfnemerIndicaties();
            afnemerindicatiesBlob = Blobber.maakBlob(afnemerindicaties);
            //maak persooncache
            persoonCache = new PersoonCache(persoon, (short) 1);
            persoonCache.setPersoonHistorieVolledigGegevens(Blobber.toJsonBytes(persoonBlob));
            persoonCache.setAfnemerindicatieGegevens(Blobber.toJsonBytes(afnemerindicatiesBlob));
        } catch (BlobException e) {
            throw new RuntimeException("Fout bij maken persoonsbeeld : " + e.getMessage());
        }
    }

    static AfnemerindicatiesBlob geefAfnemerindicatiesBlob() {
        return afnemerindicatiesBlob;
    }

    static PersoonBlob geefPersoonBlob() {
        return persoonBlob;
    }

    static PersoonCache geefPersoonCache() {
        return persoonCache;
    }

    /**
     * Geef meta-info over gemapte attributen.
     * @return lijst met gemapte info objecten
     */
    static List<MapInfo> geefAttribuutMappingInfo() {
        return attribuutMappingInfo;
    }

    private static void maakPersoonsbeelden() {
        //maak persoon en betrokken personen
        persoon = maakPersoon(1);
        gerelateerdeOuder1 = maakGerelateerdeOuder1(2);
        gerelateerdeOuder2 = maakGerelateerdeOuder2(3);
        gerelateerdeGeregistreerdPartner = maakGerelateerdGeregistreerdPartner(4);
        gerelateerdeHuwelijksPartner = maakGerelateerdHuwelijksPartner(5);
        gerelateerdKind = maakGerelateerdKind(6);

        //map groepen binnen persoon
        maakHisAfgeleidAdministratief();
        maakHisBijhouding();
        maakHisGeslachtsaanduiding();
        maakHisDeelnameEUVerkiezingen();
        maakHisGeboorte();
        maakHisIdentificatie();
        maakHisInschrijving();
        maakHisMigratie();
        maakHisNaamgebruik();
        maakHisNummerVerwijzing();
        maakHisOverlijden();
        maakHisPersoonsKaart();
        maakHisUitsluitingKiesrecht();
        maakHisVerblijfsrecht();
        maakHisSamengesteldeNaam();
        mapVerificatie();

        //map objecten
        mapPersoonBuitenlandsPersoonsnummer();
        mapPersoonVerstrekkingsbeperking();
        mapPersoonVoornaam();
        mapPersoonGeslachtsnaamComponent();
        mapPersoonNationaliteit();
        mapPersoonIndicatie();
        mapPersoonAdres();
        mapPersoonReisDocument();
        mapPersoonOnderzoek();

        //map betrokken personen
        mapKindOuderBetrekking();
        mapOuderKindBetrekking();
        mapHuwelijk();
        mapGeregisteerdPartnerschap();
    }

    private static Persoon maakPersoon(int id) {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId((long) id);
        persoon.setTijdstipLaatsteWijziging(Timestamp.valueOf("2005-03-08 00:00:00.0"));
        persoon.setAdministratieveHandeling(admHndLijst.get(0));
        persoon.setSorteervolgorde((short) 1);
        persoon.setBurgerservicenummer("302533928");
        persoon.setAdministratienummer("1268046023");
        persoon.setIndicatieAfgeleid(true);
        persoon.setIndicatieNamenreeks(false);
        persoon.setVoornamen("Cees");
        persoon.setScheidingsteken('-');
        persoon.setGeslachtsnaamstam("Vlag");
        persoon.setDatumGeboorte(DATUM_19810101);
        persoon.setGemeenteGeboorte(GEMEENTE);
        persoon.setWoonplaatsGeboorte("Utrecht");
        persoon.setOmschrijvingGeboortelocatie("omschrijving geboortelocatie Cees");
        persoon.setLandOfGebiedGeboorte(LAND_OF_GEBIED);
        persoon.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        persoon.setDatumInschrijving(DATUM_19810101);
        persoon.setVersienummer(1L);
        persoon.setNaamgebruik(Naamgebruik.EIGEN);
        persoon.setIndicatieNaamgebruikAfgeleid(true);
        persoon.setVoornamenNaamgebruik("Cees");
        persoon.setScheidingstekenNaamgebruik('-');
        persoon.setGeslachtsnaamstamNaamgebruik("Vlag");
        //zet lockversie, heeft geen setter
        Field lockVersieField = ReflectionUtils.findField(Persoon.class, "lockVersie");
        ReflectionUtils.makeAccessible(lockVersieField);
        ReflectionUtils.setField(lockVersieField, persoon, 33L);
        return persoon;
    }

    private static Persoon maakGerelateerdGeregistreerdPartner(int id) {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId((long) id);
        persoon.setTijdstipLaatsteWijziging(Timestamp.valueOf("2001-03-01 00:00:00.0"));
        persoon.setAdministratieveHandeling(admHndLijst.get(0));
        persoon.setSorteervolgorde((short) 2);
        persoon.setBurgerservicenummer("302533829");
        persoon.setAdministratienummer("0123456789");
        persoon.setIndicatieAfgeleid(true);
        persoon.setIndicatieNamenreeks(false);
        persoon.setVoornamen("Anneke");
        persoon.setScheidingsteken('-');
        persoon.setGeslachtsnaamstam("Bakker");
        persoon.setDatumGeboorte(DATUM_19810201);
        persoon.setGemeenteGeboorte(GEMEENTE);
        persoon.setWoonplaatsGeboorte("Makkum");
        persoon.setOmschrijvingGeboortelocatie("omschrijving geboortelocatie Anneke");
        persoon.setLandOfGebiedGeboorte(LAND_OF_GEBIED);
        persoon.setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);
        persoon.setDatumInschrijving(DATUM_19810101);
        persoon.setVersienummer(1L);
        persoon.setNaamgebruik(Naamgebruik.PARTNER);
        persoon.setIndicatieNaamgebruikAfgeleid(true);
        persoon.setVoornamenNaamgebruik("Anneke");
        persoon.setScheidingstekenNaamgebruik('-');
        persoon.setGeslachtsnaamstamNaamgebruik("Bakker");
        //samengestelde naam historie
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisActueel = new PersoonSamengesteldeNaamHistorie
                (persoon, "Bakker", true, true);
        samengesteldeNaamHisActueel.setId(4L);
        samengesteldeNaamHisActueel.setVoornamen("Anneke");
        samengesteldeNaamHisActueel.setVoorvoegsel("de");
        samengesteldeNaamHisActueel.setAdellijkeTitel(AdellijkeTitel.G);
        samengesteldeNaamHisActueel.setScheidingsteken('-');
        samengesteldeNaamHisActueel.setPredicaat(Predicaat.J);
        samengesteldeNaamHisActueel.setGeslachtsnaamstam("Bakker");
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisBeeindigd = new PersoonSamengesteldeNaamHistorie
                (persoon, "Bakker", true, true);
        samengesteldeNaamHisBeeindigd.setId(5L);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisVervallen = new PersoonSamengesteldeNaamHistorie
                (persoon, "Bakker", true, true);
        samengesteldeNaamHisVervallen.setId(6L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, samengesteldeNaamHisActueel
                        .getGeslachtsnaamstam())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID, samengesteldeNaamHisActueel
                        .getIndicatieAfgeleid())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS, samengesteldeNaamHisActueel
                        .getIndicatieNamenreeks())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN, samengesteldeNaamHisActueel.getVoornamen())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL, samengesteldeNaamHisActueel.getVoorvoegsel())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, samengesteldeNaamHisActueel
                        .getAdellijkeTitel())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN, samengesteldeNaamHisActueel
                        .getScheidingsteken())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, samengesteldeNaamHisActueel.getPredicaat()
                        .getCode())
                .build();
        persoon.addPersoonSamengesteldeNaamHistorie(maakActueel(samengesteldeNaamHisActueel, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakBeeindigd(samengesteldeNaamHisBeeindigd, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakVervallen(samengesteldeNaamHisVervallen, attrWaardenMap));

        //geboortehistorie
        final PersoonGeboorteHistorie geboorteHistorieActueel = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieActueel.setId(3L);
        geboorteHistorieActueel.setGemeente(GEMEENTE);
        geboorteHistorieActueel.setDatumGeboorte(DATUM_19810101);
        geboorteHistorieActueel.setLandOfGebied(LAND_OF_GEBIED);
        geboorteHistorieActueel.setOmschrijvingGeboortelocatie("omschr geboorte locatie Anneke");
        geboorteHistorieActueel.setWoonplaatsnaamGeboorte("woonplaats geboorte Anneke");
        geboorteHistorieActueel.setBuitenlandsePlaatsGeboorte("blplaats geboorte Anneke");
        geboorteHistorieActueel.setBuitenlandseRegioGeboorte("blregio geboorte Anneke");
        final PersoonGeboorteHistorie geboorteHistorieVervallen = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieVervallen.setId(4L);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_GEMEENTECODE, geboorteHistorieActueel.getGemeente().getCode())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_DATUM, geboorteHistorieActueel.getDatumGeboorte())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE, geboorteHistorieActueel.getLandOfGebied().getCode())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE, geboorteHistorieActueel
                        .getOmschrijvingGeboortelocatie())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM, geboorteHistorieActueel.getWoonplaatsnaamGeboorte())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS, geboorteHistorieActueel
                        .getBuitenlandsePlaatsGeboorte())
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO, geboorteHistorieActueel.getBuitenlandseRegioGeboorte
                        ()).build();
        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelActueel(geboorteHistorieActueel, attrWaardenMap));
        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelVervallen(geboorteHistorieVervallen, attrWaardenMap));

        //geslachtsaanduidinghistorie
        final PersoonGeslachtsaanduidingHistorie geslAandHis = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.ONBEKEND);
        geslAandHis.setId(4L);
        final PersoonGeslachtsaanduidingHistorie geslAandHisBeeindigd = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        geslAandHisBeeindigd.setId(5L);
        final PersoonGeslachtsaanduidingHistorie geslAandHisVervallen = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.VROUW);
        geslAandHisVervallen.setId(6L);
        attrWaardenMap = ImmutableMap.of(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE, geslAandHis.getGeslachtsaanduiding());
        persoon.addPersoonGeslachtsaanduidingHistorie(maakActueel(geslAandHis, attrWaardenMap));
        persoon.addPersoonGeslachtsaanduidingHistorie(maakBeeindigd(geslAandHisBeeindigd, attrWaardenMap));
        persoon.addPersoonGeslachtsaanduidingHistorie(maakVervallen(geslAandHisVervallen, attrWaardenMap));

        //identificatienummerhistorie
        final PersoonIDHistorie idHis = new PersoonIDHistorie(persoon);
        idHis.setId(4L);
        idHis.setAdministratienummer("0007654321");
        idHis.setBurgerservicenummer("007654321");
        final PersoonIDHistorie idHisBeeindigd = new PersoonIDHistorie(persoon);
        idHisBeeindigd.setId(5L);
        final PersoonIDHistorie idHisVervallen = new PersoonIDHistorie(persoon);
        idHisVervallen.setId(6L);
        attrWaardenMap = ImmutableMap.of(
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, idHis.getAdministratienummer(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, idHis.getBurgerservicenummer()
        );
        persoon.addPersoonIDHistorie(maakActueel(idHis, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakBeeindigd(idHisBeeindigd, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakVervallen(idHisVervallen, attrWaardenMap));

        return persoon;
    }

    private static Persoon maakGerelateerdHuwelijksPartner(int id) {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId((long) id);
        persoon.setTijdstipLaatsteWijziging(Timestamp.valueOf("2001-03-01 00:00:00.0"));
        persoon.setAdministratieveHandeling(admHndLijst.get(0));
        persoon.setSorteervolgorde((short) 3);
        persoon.setBurgerservicenummer("246810121");
        persoon.setAdministratienummer("0012481632");
        persoon.setIndicatieAfgeleid(true);
        persoon.setIndicatieNamenreeks(false);
        persoon.setVoornamen("Floor");
        persoon.setScheidingsteken('-');
        persoon.setGeslachtsnaamstam("Jansen");
        persoon.setDatumGeboorte(DATUM_19810201);
        persoon.setGemeenteGeboorte(GEMEENTE);
        persoon.setWoonplaatsGeboorte("Joure");
        persoon.setOmschrijvingGeboortelocatie("omschrijving geboortelocatie Floor");
        persoon.setLandOfGebiedGeboorte(LAND_OF_GEBIED);
        persoon.setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);
        persoon.setDatumInschrijving(DATUM_19810101);
        persoon.setVersienummer(1L);
        persoon.setNaamgebruik(Naamgebruik.PARTNER);
        persoon.setIndicatieNaamgebruikAfgeleid(true);
        persoon.setVoornamenNaamgebruik("Floor");
        persoon.setScheidingstekenNaamgebruik('-');
        persoon.setGeslachtsnaamstamNaamgebruik("Jansen");

        //samengestelde naam historie
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisActueel = new PersoonSamengesteldeNaamHistorie
                (persoon, "Jansen", true, true);
        samengesteldeNaamHisActueel.setId(7L);
        samengesteldeNaamHisActueel.setVoornamen("Floor");
        samengesteldeNaamHisActueel.setVoorvoegsel("van");
        samengesteldeNaamHisActueel.setAdellijkeTitel(AdellijkeTitel.H);
        samengesteldeNaamHisActueel.setScheidingsteken('-');
        samengesteldeNaamHisActueel.setPredicaat(Predicaat.K);
        samengesteldeNaamHisActueel.setGeslachtsnaamstam("Jansen");
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisBeeindigd = new PersoonSamengesteldeNaamHistorie
                (persoon, "Jansen", true, true);
        samengesteldeNaamHisBeeindigd.setId(8L);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisVervallen = new PersoonSamengesteldeNaamHistorie
                (persoon, "Jansen", true, true);
        samengesteldeNaamHisVervallen.setId(9L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, samengesteldeNaamHisActueel.getGeslachtsnaamstam())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID, samengesteldeNaamHisActueel.getIndicatieAfgeleid())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS, samengesteldeNaamHisActueel.getIndicatieNamenreeks())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN, samengesteldeNaamHisActueel.getVoornamen())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL, samengesteldeNaamHisActueel.getVoorvoegsel())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, samengesteldeNaamHisActueel
                        .getAdellijkeTitel())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN, samengesteldeNaamHisActueel.getScheidingsteken())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, samengesteldeNaamHisActueel.getPredicaat()
                        .getCode())
                .build();
        persoon.addPersoonSamengesteldeNaamHistorie(maakActueel(samengesteldeNaamHisActueel, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakBeeindigd(samengesteldeNaamHisBeeindigd, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakVervallen(samengesteldeNaamHisVervallen, attrWaardenMap));

        //geboortehistorie
        final PersoonGeboorteHistorie geboorteHistorieActueel = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieActueel.setId(5L);
        geboorteHistorieActueel.setGemeente(GEMEENTE);
        geboorteHistorieActueel.setDatumGeboorte(DATUM_19810101);
        geboorteHistorieActueel.setLandOfGebied(LAND_OF_GEBIED);
        geboorteHistorieActueel.setOmschrijvingGeboortelocatie("omschr geboorte locatie Floor");
        geboorteHistorieActueel.setWoonplaatsnaamGeboorte("woonplaats geboorte Floor");
        geboorteHistorieActueel.setBuitenlandsePlaatsGeboorte("blplaats geboorte Floor");
        geboorteHistorieActueel.setBuitenlandseRegioGeboorte("blregio geboorte Floor");
        final PersoonGeboorteHistorie geboorteHistorieVervallen = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieVervallen.setId(6L);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_GEMEENTECODE, geboorteHistorieActueel.getGemeente().getCode())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_DATUM, geboorteHistorieActueel.getDatumGeboorte())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE, geboorteHistorieActueel.getLandOfGebied().getCode())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE, geboorteHistorieActueel.getOmschrijvingGeboortelocatie())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM, geboorteHistorieActueel.getWoonplaatsnaamGeboorte())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS, geboorteHistorieActueel.getBuitenlandsePlaatsGeboorte())
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO, geboorteHistorieActueel.getBuitenlandseRegioGeboorte
                        ()).build();
        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelActueel(geboorteHistorieActueel, attrWaardenMap));
        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelVervallen(geboorteHistorieVervallen, attrWaardenMap));

        //geslachtsaanduidinghistorie
        final PersoonGeslachtsaanduidingHistorie geslAandHis = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.VROUW);
        geslAandHis.setId(7L);
        final PersoonGeslachtsaanduidingHistorie geslAandHisBeeindigd = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.ONBEKEND);
        geslAandHisBeeindigd.setId(8L);
        final PersoonGeslachtsaanduidingHistorie geslAandHisVervallen = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        geslAandHisVervallen.setId(9L);
        attrWaardenMap = ImmutableMap.of(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE, geslAandHis.getGeslachtsaanduiding());
        persoon.addPersoonGeslachtsaanduidingHistorie(maakActueel(geslAandHis, attrWaardenMap));
        persoon.addPersoonGeslachtsaanduidingHistorie(maakBeeindigd(geslAandHisBeeindigd, attrWaardenMap));
        persoon.addPersoonGeslachtsaanduidingHistorie(maakVervallen(geslAandHisVervallen, attrWaardenMap));

        //identificatienummerhistorie
        final PersoonIDHistorie idHis = new PersoonIDHistorie(persoon);
        idHis.setId(7L);
        idHis.setAdministratienummer("0007654321");
        idHis.setBurgerservicenummer("007654321");
        final PersoonIDHistorie idHisBeeindigd = new PersoonIDHistorie(persoon);
        idHisBeeindigd.setId(8L);
        final PersoonIDHistorie idHisVervallen = new PersoonIDHistorie(persoon);
        idHisVervallen.setId(9L);
        attrWaardenMap = ImmutableMap.of(
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, idHis.getAdministratienummer(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, idHis.getBurgerservicenummer()
        );
        persoon.addPersoonIDHistorie(maakActueel(idHis, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakBeeindigd(idHisBeeindigd, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakVervallen(idHisVervallen, attrWaardenMap));

        return persoon;
    }

    private static Persoon maakGerelateerdKind(final int id) {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId((long) id);
        persoon.setTijdstipLaatsteWijziging(Timestamp.valueOf("2015-03-01 00:00:00.0"));
        persoon.setAdministratieveHandeling(admHndLijst.get(0));
        persoon.setSorteervolgorde((short) 3);
        persoon.setBurgerservicenummer("036912159");
        persoon.setAdministratienummer("0036912987");
        persoon.setIndicatieAfgeleid(true);
        persoon.setIndicatieNamenreeks(false);
        persoon.setVoornamen("Dolfje");
        persoon.setScheidingsteken('-');
        persoon.setGeslachtsnaamstam("Weerwolfje");
        persoon.setDatumGeboorte(DATUM_19810201);
        persoon.setGemeenteGeboorte(GEMEENTE);
        persoon.setWoonplaatsGeboorte("Appingerdam");
        persoon.setOmschrijvingGeboortelocatie("omschrijving geboortelocatie Dolfje");
        persoon.setLandOfGebiedGeboorte(LAND_OF_GEBIED);
        persoon.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
        persoon.setDatumInschrijving(DATUM_19810101);
        persoon.setVersienummer(1L);
        persoon.setNaamgebruik(Naamgebruik.EIGEN);
        persoon.setIndicatieNaamgebruikAfgeleid(true);
        persoon.setVoornamenNaamgebruik("Dolfje");
        persoon.setScheidingstekenNaamgebruik(':');
        persoon.setGeslachtsnaamstamNaamgebruik("Weerwolfje");

        //samengestelde naam historie
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisActueel = new PersoonSamengesteldeNaamHistorie
                (persoon, "Weerwolfje", true, true);
        samengesteldeNaamHisActueel.setId(8L);
        samengesteldeNaamHisActueel.setVoornamen("Dolfje");
        samengesteldeNaamHisActueel.setVoorvoegsel("van der");
        samengesteldeNaamHisActueel.setAdellijkeTitel(AdellijkeTitel.H);
        samengesteldeNaamHisActueel.setScheidingsteken('-');
        samengesteldeNaamHisActueel.setPredicaat(Predicaat.K);
        samengesteldeNaamHisActueel.setGeslachtsnaamstam("Weerwolfje");
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisBeeindigd = new PersoonSamengesteldeNaamHistorie
                (persoon, "Weerwolfje", true, true);
        samengesteldeNaamHisBeeindigd.setId(9L);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisVervallen = new PersoonSamengesteldeNaamHistorie
                (persoon, "Weerwolfje", true, true);
        samengesteldeNaamHisVervallen.setId(10L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, samengesteldeNaamHisActueel.getGeslachtsnaamstam())
                .put(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID, samengesteldeNaamHisActueel.getIndicatieAfgeleid())
                .put(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS, samengesteldeNaamHisActueel.getIndicatieNamenreeks())
                .put(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORNAMEN, samengesteldeNaamHisActueel.getVoornamen())
                .put(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL, samengesteldeNaamHisActueel.getVoorvoegsel())
                .put(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, samengesteldeNaamHisActueel
                        .getAdellijkeTitel())
                .put(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN, samengesteldeNaamHisActueel.getScheidingsteken())
                .put(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, samengesteldeNaamHisActueel.getPredicaat()
                        .getCode())
                .build();
        persoon.addPersoonSamengesteldeNaamHistorie(maakActueel(samengesteldeNaamHisActueel, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakBeeindigd(samengesteldeNaamHisBeeindigd, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakVervallen(samengesteldeNaamHisVervallen, attrWaardenMap));

        //geboortehistorie
        final PersoonGeboorteHistorie geboorteHistorieActueel = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieActueel.setId(7L);
        geboorteHistorieActueel.setGemeente(GEMEENTE);
        geboorteHistorieActueel.setDatumGeboorte(DATUM_19810101);
        geboorteHistorieActueel.setLandOfGebied(LAND_OF_GEBIED);
        geboorteHistorieActueel.setOmschrijvingGeboortelocatie("omschr geboorte locatie Dolfje");
        geboorteHistorieActueel.setWoonplaatsnaamGeboorte("woonplaats geboorte Dolfje");
        geboorteHistorieActueel.setBuitenlandsePlaatsGeboorte("blplaats geboorte Dolfje");
        geboorteHistorieActueel.setBuitenlandseRegioGeboorte("blregio geboorteDolfje");
        final PersoonGeboorteHistorie geboorteHistorieVervallen = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieVervallen.setId(8L);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_GEMEENTECODE, geboorteHistorieActueel.getGemeente().getCode())
                .put(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_DATUM, geboorteHistorieActueel.getDatumGeboorte())
                .put(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_LANDGEBIEDCODE, geboorteHistorieActueel.getLandOfGebied().getCode())
                .put(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE, geboorteHistorieActueel.getOmschrijvingGeboortelocatie())
                .put(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_WOONPLAATSNAAM, geboorteHistorieActueel.getWoonplaatsnaamGeboorte())
                .put(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEPLAATS, geboorteHistorieActueel.getBuitenlandsePlaatsGeboorte())
                .put(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEREGIO, geboorteHistorieActueel.getBuitenlandseRegioGeboorte
                        ()).build();
        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelActueel(geboorteHistorieActueel, attrWaardenMap));
        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelVervallen(geboorteHistorieVervallen, attrWaardenMap));

        //identificatienummerhistorie
        final PersoonIDHistorie idHis = new PersoonIDHistorie(persoon);
        idHis.setId(10L);
        idHis.setAdministratienummer("0001324578");
        idHis.setBurgerservicenummer("978634523");
        final PersoonIDHistorie idHisBeeindigd = new PersoonIDHistorie(persoon);
        idHisBeeindigd.setId(11L);
        final PersoonIDHistorie idHisVervallen = new PersoonIDHistorie(persoon);
        idHisVervallen.setId(12L);
        attrWaardenMap = ImmutableMap.of(
                Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, idHis.getAdministratienummer(),
                Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, idHis.getBurgerservicenummer()
        );
        persoon.addPersoonIDHistorie(maakActueel(idHis, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakBeeindigd(idHisBeeindigd, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakVervallen(idHisVervallen, attrWaardenMap));

        return persoon;
    }

    private static Persoon maakGerelateerdeOuder1(int id) {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId((long) id);
        persoon.setTijdstipLaatsteWijziging(Timestamp.valueOf("2001-03-01 00:00:00.0"));
        persoon.setAdministratieveHandeling(admHndLijst.get(0));
        persoon.setSorteervolgorde((short) 3);
        persoon.setBurgerservicenummer("246810987");
        persoon.setAdministratienummer("0678901423");
        persoon.setIndicatieAfgeleid(true);
        persoon.setIndicatieNamenreeks(false);
        persoon.setVoornamen("Henk");
        persoon.setScheidingsteken('-');
        persoon.setGeslachtsnaamstam("Steur");
        persoon.setDatumGeboorte(DATUM_19810201);
        persoon.setGemeenteGeboorte(GEMEENTE);
        persoon.setWoonplaatsGeboorte("Leeuwarden");
        persoon.setOmschrijvingGeboortelocatie("omschrijving geboortelocatie Ouder1");
        persoon.setLandOfGebiedGeboorte(LAND_OF_GEBIED);
        persoon.setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);
        persoon.setDatumInschrijving(DATUM_19810101);
        persoon.setVersienummer(1L);
        persoon.setNaamgebruik(Naamgebruik.PARTNER);
        persoon.setIndicatieNaamgebruikAfgeleid(true);
        persoon.setVoornamenNaamgebruik("Henk");
        persoon.setScheidingstekenNaamgebruik('-');
        persoon.setGeslachtsnaamstamNaamgebruik("Steur");

        //samengestelde naam historie
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisActueel = new PersoonSamengesteldeNaamHistorie
                (persoon, "Steur", true, true);
        samengesteldeNaamHisActueel.setId(10L);
        samengesteldeNaamHisActueel.setVoornamen("Henk");
        samengesteldeNaamHisActueel.setVoorvoegsel("van de");
        samengesteldeNaamHisActueel.setAdellijkeTitel(AdellijkeTitel.P);
        samengesteldeNaamHisActueel.setScheidingsteken('-');
        samengesteldeNaamHisActueel.setPredicaat(Predicaat.K);
        samengesteldeNaamHisActueel.setGeslachtsnaamstam("Steur");
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisBeeindigd = new PersoonSamengesteldeNaamHistorie
                (persoon, "Steur", true, true);
        samengesteldeNaamHisBeeindigd.setId(11L);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisVervallen = new PersoonSamengesteldeNaamHistorie
                (persoon, "Steur", true, true);
        samengesteldeNaamHisVervallen.setId(12L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, samengesteldeNaamHisActueel.getGeslachtsnaamstam())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID, samengesteldeNaamHisActueel.getIndicatieAfgeleid())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS, samengesteldeNaamHisActueel.getIndicatieNamenreeks())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN, samengesteldeNaamHisActueel.getVoornamen())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL, samengesteldeNaamHisActueel.getVoorvoegsel())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, samengesteldeNaamHisActueel
                        .getAdellijkeTitel())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN, samengesteldeNaamHisActueel.getScheidingsteken())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, samengesteldeNaamHisActueel.getPredicaat()
                        .getCode())
                .build();
        persoon.addPersoonSamengesteldeNaamHistorie(maakActueel(samengesteldeNaamHisActueel, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakBeeindigd(samengesteldeNaamHisBeeindigd, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakVervallen(samengesteldeNaamHisVervallen, attrWaardenMap));

        //geboortehistorie
        final PersoonGeboorteHistorie geboorteHistorieActueel = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieActueel.setId(7L);
        geboorteHistorieActueel.setGemeente(GEMEENTE);
        geboorteHistorieActueel.setDatumGeboorte(DATUM_19810101);
        geboorteHistorieActueel.setLandOfGebied(LAND_OF_GEBIED);
        geboorteHistorieActueel.setOmschrijvingGeboortelocatie("omschr geboorte locatie Henk");
        geboorteHistorieActueel.setWoonplaatsnaamGeboorte("woonplaats geboorte Henk");
        geboorteHistorieActueel.setBuitenlandsePlaatsGeboorte("blplaats geboorte Henk");
        geboorteHistorieActueel.setBuitenlandseRegioGeboorte("blregio geboorte Henk");
        final PersoonGeboorteHistorie geboorteHistorieVervallen = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieVervallen.setId(8L);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_GEMEENTECODE, geboorteHistorieActueel.getGemeente().getCode())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM, geboorteHistorieActueel.getDatumGeboorte())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_LANDGEBIEDCODE, geboorteHistorieActueel.getLandOfGebied().getCode())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE, geboorteHistorieActueel.getOmschrijvingGeboortelocatie())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_WOONPLAATSNAAM, geboorteHistorieActueel.getWoonplaatsnaamGeboorte())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS, geboorteHistorieActueel.getBuitenlandsePlaatsGeboorte())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEREGIO, geboorteHistorieActueel.getBuitenlandseRegioGeboorte
                        ()).build();
        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelActueel(geboorteHistorieActueel, attrWaardenMap));
        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelVervallen(geboorteHistorieVervallen, attrWaardenMap));

        //geslachtsaanduidinghistorie
        final PersoonGeslachtsaanduidingHistorie geslAandHis = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.VROUW);
        geslAandHis.setId(10L);
        final PersoonGeslachtsaanduidingHistorie geslAandHisBeeindigd = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.ONBEKEND);
        geslAandHisBeeindigd.setId(11L);
        final PersoonGeslachtsaanduidingHistorie geslAandHisVervallen = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        geslAandHisVervallen.setId(12L);
        attrWaardenMap = ImmutableMap.of(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_CODE, geslAandHis.getGeslachtsaanduiding());
        persoon.addPersoonGeslachtsaanduidingHistorie(maakActueel(geslAandHis, attrWaardenMap));
        persoon.addPersoonGeslachtsaanduidingHistorie(maakBeeindigd(geslAandHisBeeindigd, attrWaardenMap));
        persoon.addPersoonGeslachtsaanduidingHistorie(maakVervallen(geslAandHisVervallen, attrWaardenMap));

        //identificatienummerhistorie
        final PersoonIDHistorie idHis = new PersoonIDHistorie(persoon);
        idHis.setId(10L);
        idHis.setAdministratienummer("0097654321");
        idHis.setBurgerservicenummer("917654321");
        final PersoonIDHistorie idHisBeeindigd = new PersoonIDHistorie(persoon);
        idHisBeeindigd.setId(11L);
        final PersoonIDHistorie idHisVervallen = new PersoonIDHistorie(persoon);
        idHisVervallen.setId(12L);
        attrWaardenMap = ImmutableMap.of(
                Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, idHis.getAdministratienummer(),
                Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, idHis.getBurgerservicenummer()
        );
        persoon.addPersoonIDHistorie(maakActueel(idHis, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakBeeindigd(idHisBeeindigd, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakVervallen(idHisVervallen, attrWaardenMap));

        return persoon;
    }

    private static Persoon maakGerelateerdeOuder2(int id) {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId((long) id);
        persoon.setTijdstipLaatsteWijziging(Timestamp.valueOf("2001-03-01 00:00:00.0"));
        persoon.setAdministratieveHandeling(admHndLijst.get(0));
        persoon.setSorteervolgorde((short) 3);
        persoon.setBurgerservicenummer("246810987");
        persoon.setAdministratienummer("0678901423");
        persoon.setIndicatieAfgeleid(true);
        persoon.setIndicatieNamenreeks(false);
        persoon.setVoornamen("Henk");
        persoon.setScheidingsteken('-');
        persoon.setGeslachtsnaamstam("Steur");
        persoon.setDatumGeboorte(DATUM_19810201);
        persoon.setGemeenteGeboorte(GEMEENTE);
        persoon.setWoonplaatsGeboorte("Leeuwarden");
        persoon.setOmschrijvingGeboortelocatie("omschrijving geboortelocatie Ouder2");
        persoon.setLandOfGebiedGeboorte(LAND_OF_GEBIED);
        persoon.setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);
        persoon.setDatumInschrijving(DATUM_19810101);
        persoon.setVersienummer(1L);
        persoon.setNaamgebruik(Naamgebruik.PARTNER);
        persoon.setIndicatieNaamgebruikAfgeleid(true);
        persoon.setVoornamenNaamgebruik("Henk");
        persoon.setScheidingstekenNaamgebruik('-');
        persoon.setGeslachtsnaamstamNaamgebruik("Steur");

        //samengestelde naam historie
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisActueel = new PersoonSamengesteldeNaamHistorie
                (persoon, "Steur", true, true);
        samengesteldeNaamHisActueel.setId(13L);
        samengesteldeNaamHisActueel.setVoornamen("Kaatje");
        samengesteldeNaamHisActueel.setVoorvoegsel("van de");
        samengesteldeNaamHisActueel.setAdellijkeTitel(AdellijkeTitel.P);
        samengesteldeNaamHisActueel.setScheidingsteken('-');
        samengesteldeNaamHisActueel.setPredicaat(Predicaat.K);
        samengesteldeNaamHisActueel.setGeslachtsnaamstam("Steur");
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisBeeindigd = new PersoonSamengesteldeNaamHistorie
                (persoon, "Steur", true, true);
        samengesteldeNaamHisBeeindigd.setId(14L);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisVervallen = new PersoonSamengesteldeNaamHistorie
                (persoon, "Steur", true, true);
        samengesteldeNaamHisVervallen.setId(15L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, samengesteldeNaamHisActueel.getGeslachtsnaamstam())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID, samengesteldeNaamHisActueel.getIndicatieAfgeleid())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS, samengesteldeNaamHisActueel.getIndicatieNamenreeks())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN, samengesteldeNaamHisActueel.getVoornamen())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL, samengesteldeNaamHisActueel.getVoorvoegsel())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, samengesteldeNaamHisActueel
                        .getAdellijkeTitel())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN, samengesteldeNaamHisActueel.getScheidingsteken())
                .put(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, samengesteldeNaamHisActueel.getPredicaat()
                        .getCode())
                .build();
        persoon.addPersoonSamengesteldeNaamHistorie(maakActueel(samengesteldeNaamHisActueel, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakBeeindigd(samengesteldeNaamHisBeeindigd, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakVervallen(samengesteldeNaamHisVervallen, attrWaardenMap));

        //geboortehistorie
        final PersoonGeboorteHistorie geboorteHistorieActueel = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieActueel.setId(9L);
        geboorteHistorieActueel.setGemeente(GEMEENTE);
        geboorteHistorieActueel.setDatumGeboorte(DATUM_19810101);
        geboorteHistorieActueel.setLandOfGebied(LAND_OF_GEBIED);
        geboorteHistorieActueel.setOmschrijvingGeboortelocatie("omschr geboorte locatie Kaatje");
        geboorteHistorieActueel.setWoonplaatsnaamGeboorte("woonplaats geboorte Kaatje");
        geboorteHistorieActueel.setBuitenlandsePlaatsGeboorte("blplaats geboorte Kaatje");
        geboorteHistorieActueel.setBuitenlandseRegioGeboorte("blregio geboorte Kaatje");
        final PersoonGeboorteHistorie geboorteHistorieVervallen = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieVervallen.setId(10L);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_GEMEENTECODE, geboorteHistorieActueel.getGemeente().getCode())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM, geboorteHistorieActueel.getDatumGeboorte())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_LANDGEBIEDCODE, geboorteHistorieActueel.getLandOfGebied().getCode())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE, geboorteHistorieActueel.getOmschrijvingGeboortelocatie())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_WOONPLAATSNAAM, geboorteHistorieActueel.getWoonplaatsnaamGeboorte())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS, geboorteHistorieActueel.getBuitenlandsePlaatsGeboorte())
                .put(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEREGIO, geboorteHistorieActueel.getBuitenlandseRegioGeboorte
                        ()).build();
        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelActueel(geboorteHistorieActueel, attrWaardenMap));
        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelVervallen(geboorteHistorieVervallen, attrWaardenMap));

        //geslachtsaanduidinghistorie
        final PersoonGeslachtsaanduidingHistorie geslAandHis = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.VROUW);
        geslAandHis.setId(13L);
        final PersoonGeslachtsaanduidingHistorie geslAandHisBeeindigd = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.ONBEKEND);
        geslAandHisBeeindigd.setId(14L);
        final PersoonGeslachtsaanduidingHistorie geslAandHisVervallen = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        geslAandHisVervallen.setId(15L);
        attrWaardenMap = ImmutableMap.of(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_CODE, geslAandHis.getGeslachtsaanduiding());
        persoon.addPersoonGeslachtsaanduidingHistorie(maakActueel(geslAandHis, attrWaardenMap));
        persoon.addPersoonGeslachtsaanduidingHistorie(maakBeeindigd(geslAandHisBeeindigd, attrWaardenMap));
        persoon.addPersoonGeslachtsaanduidingHistorie(maakVervallen(geslAandHisVervallen, attrWaardenMap));

        //identificatienummerhistorie
        final PersoonIDHistorie idHis = new PersoonIDHistorie(persoon);
        idHis.setId(13L);
        idHis.setAdministratienummer("0097654329");
        idHis.setBurgerservicenummer("917654329");
        final PersoonIDHistorie idHisBeeindigd = new PersoonIDHistorie(persoon);
        idHisBeeindigd.setId(14L);
        final PersoonIDHistorie idHisVervallen = new PersoonIDHistorie(persoon);
        idHisVervallen.setId(15L);
        attrWaardenMap = ImmutableMap.of(
                Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, idHis.getAdministratienummer(),
                Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, idHis.getBurgerservicenummer()
        );
        persoon.addPersoonIDHistorie(maakActueel(idHis, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakBeeindigd(idHisBeeindigd, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakVervallen(idHisVervallen, attrWaardenMap));

        return persoon;
    }

    private static void maakVerantwoording() {
        //adm. handelingen
        AdministratieveHandeling admhnd1 = new AdministratieveHandeling(
                PARTIJ, SoortAdministratieveHandeling.parseId(1), TS_19810101);
        AdministratieveHandeling admhnd2 = new AdministratieveHandeling(PARTIJ, SoortAdministratieveHandeling.parseId
                (1), TS_19810201);
        AdministratieveHandeling admhnd3 = new AdministratieveHandeling(PARTIJ, SoortAdministratieveHandeling.parseId
                (1), TS_19810301);
        admHndLijst = Lists.newArrayList(admhnd1, admhnd2, admhnd3);
        long admHndId = 1;
        for (AdministratieveHandeling admHnd : admHndLijst) {
            admHnd.setId(admHndId++);
        }

        //acties
        actieLijst = Lists.newArrayList(
                new BRPActie(SoortActie.parseId(1), admhnd1, PARTIJ, TS_19810101),
                new BRPActie(SoortActie.parseId(1), admhnd1, PARTIJ, TS_19810101),
                new BRPActie(SoortActie.parseId(1), admhnd1, PARTIJ, TS_19810101),
                new BRPActie(SoortActie.parseId(1), admhnd1, PARTIJ, TS_19810101));
        long actieId = 1;
        for (BRPActie actie : actieLijst) {
            actie.setId(actieId++);
        }
    }

    //autaut : afn.indicaties
    private static List<PersoonAfnemerindicatie> mapAfnemerIndicaties() {
        final Leveringsautorisatie levAut = new Leveringsautorisatie(Stelsel.BRP, false);
        levAut.setId(1);
        levAut.setNaam("levaut1");
        levAut.setPopulatiebeperking("TRUE");
        levAut.setDatumIngang(DATUM_19810101);

        final Partij partij = new Partij("Tegenpartij", "000001");
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final ToegangLeveringsAutorisatie toegangLevAut = new ToegangLeveringsAutorisatie(partijRol, levAut);
        toegangLevAut.setId(1);
        toegangLevAut.setDatumIngang(DATUM_19810101);

        final Dienstbundel dienstbundel = new Dienstbundel(levAut);
        dienstbundel.setId(1);
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.ATTENDERING);
        dienst.setId(1);

        final PersoonAfnemerindicatie persoonAfnemerindicatie = new PersoonAfnemerindicatie(persoon, partij,
                levAut);
        persoonAfnemerindicatie.setId(1L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE, persoonAfnemerindicatie.getPartij().getCode())
                .put(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE, persoonAfnemerindicatie
                        .getLeveringsautorisatie().getId())
                .put(Element.PERSOON_AFNEMERINDICATIE_PERSOON, persoonAfnemerindicatie.getPersoon().getId())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, persoonAfnemerindicatie.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT));

        final PersoonAfnemerindicatieHistorie persoonAfnemerindicatieHistorie = new PersoonAfnemerindicatieHistorie
                (persoonAfnemerindicatie);
        persoonAfnemerindicatieHistorie.setId(1L);
        persoonAfnemerindicatieHistorie.setDienstInhoud(dienst);
        persoonAfnemerindicatieHistorie.setDatumAanvangMaterielePeriode(DATUM_19810101);
        persoonAfnemerindicatieHistorie.setDatumEindeVolgen(DATUM_19810201);
        persoonAfnemerindicatieHistorie.setDatumTijdRegistratie(TS_19810101);
        persoonAfnemerindicatieHistorie.setDienstVerval(dienst);

        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD, persoonAfnemerindicatieHistorie.getDienstInhoud()
                        .getId())
                .put(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE, persoonAfnemerindicatieHistorie
                        .getDatumAanvangMaterielePeriode())
                .put(Element.PERSOON_AFNEMERINDICATIE_DATUMEINDEVOLGEN, persoonAfnemerindicatieHistorie
                        .getDatumEindeVolgen())
                .put(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE, persoonAfnemerindicatieHistorie
                        .getDatumTijdRegistratie())
                .put(Element.PERSOON_AFNEMERINDICATIE_DIENSTVERVAL, persoonAfnemerindicatieHistorie
                        .getDienstVerval().getId())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, persoonAfnemerindicatieHistorie.getId(), RecordStatus
                .GEEN, bepaalGroep(attrWaardenMap)));

        persoonAfnemerindicatie.addPersoonAfnemerindicatieHistorieSet(persoonAfnemerindicatieHistorie);
        return Lists.newArrayList(persoonAfnemerindicatie);
    }

    //afgeleid adm. -- formeel
    private static void maakHisAfgeleidAdministratief() {
        //actueel
        final PersoonAfgeleidAdministratiefHistorie afgeleidAdmHisActueel = new PersoonAfgeleidAdministratiefHistorie(
                (short) 1,
                persoon, admHndLijst.get(0), TS_19810101);
        afgeleidAdmHisActueel.setId(1L);
        afgeleidAdmHisActueel.setDatumTijdLaatsteWijzigingGba(TS_19810101);
        //vervallen
        final PersoonAfgeleidAdministratiefHistorie afgeleidAdmHisVervallen = new PersoonAfgeleidAdministratiefHistorie(
                (short) 1,
                persoon, admHndLijst.get(0), TS_19810101);
        afgeleidAdmHisVervallen.setId(2L);
        afgeleidAdmHisVervallen.setDatumTijdLaatsteWijzigingGba(TS_19810101);

        final Map<Element, Object> attrWaardenMap = ImmutableMap.of(
                Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK, afgeleidAdmHisActueel
                        .getDatumTijdLaatsteWijzigingGba(),
                Element.PERSOON_AFGELEIDADMINISTRATIEF_SORTEERVOLGORDE, afgeleidAdmHisActueel.getSorteervolgorde(),
                Element.PERSOON_AFGELEIDADMINISTRATIEF_ADMINISTRATIEVEHANDELING, afgeleidAdmHisActueel
                        .getAdministratieveHandeling().getId(),
                Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING, afgeleidAdmHisActueel
                        .getDatumTijdLaatsteWijziging());

        persoon.addPersoonAfgeleidAdministratiefHistorie((PersoonAfgeleidAdministratiefHistorie) maakFormeelActueel
                (afgeleidAdmHisActueel, attrWaardenMap));
        persoon.addPersoonAfgeleidAdministratiefHistorie((PersoonAfgeleidAdministratiefHistorie)
                maakFormeelVervallen(afgeleidAdmHisVervallen, attrWaardenMap));
    }

    //bijhouding -- materieel
    private static void maakHisBijhouding() {
        final PersoonBijhoudingHistorie bijhoudingHis =
                new PersoonBijhoudingHistorie(persoon, PARTIJ, Bijhoudingsaard.INGEZETENE,
                        NadereBijhoudingsaard.ACTUEEL);
        bijhoudingHis.setId(1L);
        final PersoonBijhoudingHistorie bijhoudingHisBeeindigd =
                new PersoonBijhoudingHistorie(persoon, PARTIJ, Bijhoudingsaard.INGEZETENE,
                        NadereBijhoudingsaard.ACTUEEL);
        bijhoudingHisBeeindigd.setId(2L);
        final PersoonBijhoudingHistorie bijhoudingHisVervallen =
                new PersoonBijhoudingHistorie(persoon, PARTIJ, Bijhoudingsaard.INGEZETENE,
                        NadereBijhoudingsaard.ACTUEEL);
        bijhoudingHisVervallen.setId(3L);

        final Map<Element, Object> attrWaardenMap = ImmutableMap.of(
                Element.PERSOON_BIJHOUDING_PARTIJCODE, bijhoudingHis.getPartij().getCode(),
                Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE, bijhoudingHis.getBijhoudingsaard().getCode(),
                Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE, bijhoudingHis.getNadereBijhoudingsaard()
                        .getCode());

        persoon.addPersoonBijhoudingHistorie(maakActueel(bijhoudingHis, attrWaardenMap));
        persoon.addPersoonBijhoudingHistorie(maakBeeindigd(bijhoudingHisBeeindigd, attrWaardenMap));
        persoon.addPersoonBijhoudingHistorie(maakVervallen(bijhoudingHisVervallen, attrWaardenMap));
    }

    //geslachtsaanduiding -- materieel
    private static void maakHisGeslachtsaanduiding() {
        final PersoonGeslachtsaanduidingHistorie geslAandHis = new PersoonGeslachtsaanduidingHistorie(persoon,
                Geslachtsaanduiding.ONBEKEND);
        geslAandHis.setId(1L);
        final PersoonGeslachtsaanduidingHistorie geslAandHisBeeindigd = new PersoonGeslachtsaanduidingHistorie
                (persoon,
                        Geslachtsaanduiding.ONBEKEND);
        geslAandHisBeeindigd.setId(2L);
        final PersoonGeslachtsaanduidingHistorie geslAandHisVervallen = new PersoonGeslachtsaanduidingHistorie
                (persoon,
                        Geslachtsaanduiding.VROUW);
        geslAandHisVervallen.setId(3L);

        final Map<Element, Object> attrWaardenMap = ImmutableMap.of(Element.PERSOON_GESLACHTSAANDUIDING_CODE,
                Geslachtsaanduiding.ONBEKEND);

        persoon.addPersoonGeslachtsaanduidingHistorie(maakActueel(geslAandHis, attrWaardenMap));
        persoon.addPersoonGeslachtsaanduidingHistorie(maakBeeindigd(geslAandHisBeeindigd, attrWaardenMap));
        persoon.addPersoonGeslachtsaanduidingHistorie(maakVervallen(geslAandHisVervallen, attrWaardenMap));
    }

    // deelname Eu verk. -- formeel
    private static void maakHisDeelnameEUVerkiezingen() {
        final PersoonDeelnameEuVerkiezingenHistorie deelnameEUVerkHisActueel = new
                PersoonDeelnameEuVerkiezingenHistorie
                (persoon, true);
        deelnameEUVerkHisActueel.setId(1L);
        deelnameEUVerkHisActueel.setDatumAanleidingAanpassingDeelnameEuVerkiezingen(DATUM_19810101);
        deelnameEUVerkHisActueel.setDatumVoorzienEindeUitsluitingEuVerkiezingen(DATUM_19810101);
        final PersoonDeelnameEuVerkiezingenHistorie deelnameEUVerkHisVervallen = new
                PersoonDeelnameEuVerkiezingenHistorie
                (persoon, true);
        deelnameEUVerkHisVervallen.setId(2L);

        final Map<Element, Object> attrWaardenMap = ImmutableMap.of(
                Element.PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME, deelnameEUVerkHisActueel
                        .getIndicatieDeelnameEuVerkiezingen(),
                Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING, deelnameEUVerkHisActueel
                        .getDatumAanleidingAanpassingDeelnameEuVerkiezingen(),
                Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMVOORZIENEINDEUITSLUITING, deelnameEUVerkHisActueel
                        .getDatumVoorzienEindeUitsluitingEuVerkiezingen()
        );

        persoon.addPersoonDeelnameEuVerkiezingenHistorie((PersoonDeelnameEuVerkiezingenHistorie)
                maakFormeelActueel(deelnameEUVerkHisActueel, attrWaardenMap));
        persoon.addPersoonDeelnameEuVerkiezingenHistorie((PersoonDeelnameEuVerkiezingenHistorie)
                maakFormeelVervallen(deelnameEUVerkHisVervallen, attrWaardenMap));
    }

    //geboorte -- formeel
    private static void maakHisGeboorte() {
        final PersoonGeboorteHistorie geboorteHistorieActueel = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieActueel.setId(1L);
        geboorteHistorieActueel.setGemeente(GEMEENTE);
        geboorteHistorieActueel.setDatumGeboorte(DATUM_19810101);
        geboorteHistorieActueel.setLandOfGebied(LAND_OF_GEBIED);
        geboorteHistorieActueel.setOmschrijvingGeboortelocatie("omschr geboorte locatie Cees");
        geboorteHistorieActueel.setWoonplaatsnaamGeboorte("woonplaats geboorte Cees");
        geboorteHistorieActueel.setBuitenlandsePlaatsGeboorte("blplaats geboorte Cees");
        geboorteHistorieActueel.setBuitenlandseRegioGeboorte("blregio geboorte Cees");
        final PersoonGeboorteHistorie geboorteHistorieVervallen = new PersoonGeboorteHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        geboorteHistorieVervallen.setId(2L);

        final Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_GEBOORTE_GEMEENTECODE, geboorteHistorieActueel.getGemeente().getCode())
                .put(Element.PERSOON_GEBOORTE_DATUM, geboorteHistorieActueel.getDatumGeboorte())
                .put(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE, geboorteHistorieActueel.getLandOfGebied().getCode())
                .put(Element.PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE, geboorteHistorieActueel
                        .getOmschrijvingGeboortelocatie())
                .put(Element.PERSOON_GEBOORTE_WOONPLAATSNAAM, geboorteHistorieActueel.getWoonplaatsnaamGeboorte())
                .put(Element.PERSOON_GEBOORTE_BUITENLANDSEPLAATS, geboorteHistorieActueel
                        .getBuitenlandsePlaatsGeboorte())
                .put(Element.PERSOON_GEBOORTE_BUITENLANDSEREGIO, geboorteHistorieActueel.getBuitenlandseRegioGeboorte
                        ()).build();

        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelActueel(geboorteHistorieActueel,
                attrWaardenMap));
        persoon.addPersoonGeboorteHistorie((PersoonGeboorteHistorie) maakFormeelVervallen
                (geboorteHistorieVervallen, attrWaardenMap));
    }

    //identificatie -- materieel
    private static void maakHisIdentificatie() {
        final PersoonIDHistorie idHis = new PersoonIDHistorie(persoon);
        idHis.setId(1L);
        idHis.setAdministratienummer("0001234567");
        idHis.setBurgerservicenummer("001234567");
        final PersoonIDHistorie idHisBeeindigd = new PersoonIDHistorie(persoon);
        idHisBeeindigd.setId(2L);
        final PersoonIDHistorie idHisVervallen = new PersoonIDHistorie(persoon);
        idHisVervallen.setId(3L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.of(
                Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, idHis.getAdministratienummer(),
                Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, idHis.getBurgerservicenummer()
        );
        persoon.addPersoonIDHistorie(maakActueel(idHis, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakBeeindigd(idHisBeeindigd, attrWaardenMap));
        persoon.addPersoonIDHistorie(maakVervallen(idHisVervallen, attrWaardenMap));
    }

    //inschrijving -- formeel
    private static void maakHisInschrijving() {
        final PersoonInschrijvingHistorie inschrijvingHisActueel = new PersoonInschrijvingHistorie(persoon,
                DATUM_19810101,
                1L, TS_19810101);
        inschrijvingHisActueel.setId(1L);
        final PersoonInschrijvingHistorie inschrijvingHisVervallen = new PersoonInschrijvingHistorie(persoon,
                DATUM_19810101, 1L, TS_19810101);
        inschrijvingHisVervallen.setId(2L);

        final Map<Element, Object> attrWaardenMap = ImmutableMap.of(
                Element.PERSOON_INSCHRIJVING_DATUM, inschrijvingHisActueel.getDatumInschrijving(),
                Element.PERSOON_INSCHRIJVING_VERSIENUMMER, inschrijvingHisActueel.getVersienummer(),
                Element.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL, inschrijvingHisActueel.getDatumtijdstempel()
        );

        persoon.addPersoonInschrijvingHistorie((PersoonInschrijvingHistorie) maakFormeelActueel
                (inschrijvingHisActueel, attrWaardenMap));
        persoon.addPersoonInschrijvingHistorie((PersoonInschrijvingHistorie) maakFormeelVervallen
                (inschrijvingHisVervallen, attrWaardenMap));
    }

    //migratie -- materieel
    private static void maakHisMigratie() {
        final PersoonMigratieHistorie migratieHis = new PersoonMigratieHistorie(persoon, SoortMigratie.EMIGRATIE);
        migratieHis.setId(1L);
        migratieHis.setRedenWijzigingMigratie(RDN_WIJZIGING_VERBLIJF);
        migratieHis.setAangeverMigratie(AANGEVER);
        migratieHis.setLandOfGebied(LAND_OF_GEBIED);
        migratieHis.setBuitenlandsAdresRegel1("Buitenlandse adresregel 1");
        migratieHis.setBuitenlandsAdresRegel2("Buitenlandse adresregel 2");
        migratieHis.setBuitenlandsAdresRegel3("Buitenlandse adresregel 3");
        migratieHis.setBuitenlandsAdresRegel4("Buitenlandse adresregel 4");
        migratieHis.setBuitenlandsAdresRegel5("Buitenlandse adresregel 5");
        migratieHis.setBuitenlandsAdresRegel6("Buitenlandse adresregel 6");
        final PersoonMigratieHistorie migratieHisBeeindigd = new PersoonMigratieHistorie(persoon, SoortMigratie
                .EMIGRATIE);
        migratieHisBeeindigd.setId(2L);
        final PersoonMigratieHistorie migratieHisVervallen = new PersoonMigratieHistorie(persoon, SoortMigratie
                .EMIGRATIE);
        migratieHisVervallen.setId(3L);

        final Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_MIGRATIE_SOORTCODE, migratieHis.getSoortMigratie().getCode())
                .put(Element.PERSOON_MIGRATIE_REDENWIJZIGINGCODE, String.valueOf(migratieHis
                        .getRedenWijzigingMigratie().getCode()))
                .put(Element.PERSOON_MIGRATIE_AANGEVERCODE, String.valueOf(migratieHis.getAangeverMigratie().getCode()))
                .put(Element.PERSOON_MIGRATIE_LANDGEBIEDCODE, migratieHis.getLandOfGebied().getCode())
                .put(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1, migratieHis.getBuitenlandsAdresRegel1())
                .put(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL2, migratieHis.getBuitenlandsAdresRegel2())
                .put(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL3, migratieHis.getBuitenlandsAdresRegel3())
                .put(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL4, migratieHis.getBuitenlandsAdresRegel4())
                .put(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL5, migratieHis.getBuitenlandsAdresRegel5())
                .put(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL6, migratieHis.getBuitenlandsAdresRegel6())
                .build();

        persoon.addPersoonMigratieHistorie(maakActueel(migratieHis, attrWaardenMap));
        persoon.addPersoonMigratieHistorie(maakBeeindigd(migratieHisBeeindigd, attrWaardenMap));
        persoon.addPersoonMigratieHistorie(maakVervallen(migratieHisVervallen, attrWaardenMap));
    }

    //naamgebruik -- formeel
    private static void maakHisNaamgebruik() {
        final PersoonNaamgebruikHistorie naamgebruikHisActueel = new PersoonNaamgebruikHistorie(persoon, "Vlag",
                true,
                Naamgebruik.EIGEN);
        naamgebruikHisActueel.setId(1L);
        naamgebruikHisActueel.setPredicaat(Predicaat.H);
        naamgebruikHisActueel.setVoornamenNaamgebruik("Cees");
        naamgebruikHisActueel.setAdellijkeTitel(AdellijkeTitel.B);
        naamgebruikHisActueel.setScheidingstekenNaamgebruik('-');
        naamgebruikHisActueel.setVoorvoegselNaamgebruik("de");
        naamgebruikHisActueel.setGeslachtsnaamstamNaamgebruik("Vlag");

        final Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_NAAMGEBRUIK_CODE, naamgebruikHisActueel.getNaamgebruik().getCode())
                .put(Element.PERSOON_NAAMGEBRUIK_INDICATIEAFGELEID, naamgebruikHisActueel
                        .getIndicatieNaamgebruikAfgeleid())
                .put(Element.PERSOON_NAAMGEBRUIK_PREDICAATCODE, naamgebruikHisActueel.getPredicaat().getCode())
                .put(Element.PERSOON_NAAMGEBRUIK_VOORNAMEN, naamgebruikHisActueel.getVoornamenNaamgebruik())
                .put(Element.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE, naamgebruikHisActueel.getAdellijkeTitel())
                .put(Element.PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN, naamgebruikHisActueel.getScheidingstekenNaamgebruik())
                .put(Element.PERSOON_NAAMGEBRUIK_VOORVOEGSEL, naamgebruikHisActueel.getVoorvoegselNaamgebruik())
                .put(Element.PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM, naamgebruikHisActueel
                        .getGeslachtsnaamstamNaamgebruik())
                .build();
        final PersoonNaamgebruikHistorie naamgebruikHisVervallen = new PersoonNaamgebruikHistorie(persoon,
                "Vlag", true,
                Naamgebruik.EIGEN);
        naamgebruikHisVervallen.setId(2L);

        persoon.addPersoonNaamgebruikHistorie((PersoonNaamgebruikHistorie) maakFormeelActueel(
                naamgebruikHisActueel, attrWaardenMap));
        persoon.addPersoonNaamgebruikHistorie((PersoonNaamgebruikHistorie) maakFormeelVervallen
                (naamgebruikHisVervallen, attrWaardenMap));
    }

    //nummerverwijzing -- materieel
    private static void maakHisNummerVerwijzing() {
        final PersoonNummerverwijzingHistorie nummerVerwHis = new PersoonNummerverwijzingHistorie(persoon);
        nummerVerwHis.setId(1L);
        nummerVerwHis.setVolgendeAdministratienummer("0001234567");
        nummerVerwHis.setVorigeAdministratienummer("0012345678");
        nummerVerwHis.setVolgendeBurgerservicenummer("001234567");
        nummerVerwHis.setVorigeBurgerservicenummer("012345678");
        final PersoonNummerverwijzingHistorie nummerVerwHisBeeindigd = new PersoonNummerverwijzingHistorie(persoon);
        nummerVerwHisBeeindigd.setId(2L);
        final PersoonNummerverwijzingHistorie nummerVerwHisVervallen = new PersoonNummerverwijzingHistorie(persoon);
        nummerVerwHisVervallen.setId(3L);

        final Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER, nummerVerwHis
                        .getVolgendeAdministratienummer())
                .put(Element.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER, nummerVerwHis
                        .getVorigeAdministratienummer())
                .put(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEBURGERSERVICENUMMER, nummerVerwHis
                        .getVolgendeBurgerservicenummer())
                .put(Element.PERSOON_NUMMERVERWIJZING_VORIGEBURGERSERVICENUMMER, nummerVerwHis
                        .getVorigeBurgerservicenummer())
                .build();

        persoon.addPersoonNummerverwijzingHistorie(maakActueel(nummerVerwHis, attrWaardenMap));
        persoon.addPersoonNummerverwijzingHistorie(maakBeeindigd(nummerVerwHisBeeindigd, attrWaardenMap));
        persoon.addPersoonNummerverwijzingHistorie(maakVervallen(nummerVerwHisVervallen, attrWaardenMap));
    }

    //overlijden -- formeel
    private static void maakHisOverlijden() {
        final PersoonOverlijdenHistorie overlijdenHisActueel = new PersoonOverlijdenHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        overlijdenHisActueel.setId(1L);
        overlijdenHisActueel.setDatumOverlijden(DATUM_19810101);
        overlijdenHisActueel.setGemeente(GEMEENTE);
        overlijdenHisActueel.setWoonplaatsnaamOverlijden("Woonplaatsnaam overlijden");
        overlijdenHisActueel.setBuitenlandsePlaatsOverlijden("Buitenlandse plaats overlijden");
        overlijdenHisActueel.setBuitenlandseRegioOverlijden("Buitenlandse regio overlijden");
        overlijdenHisActueel.setOmschrijvingLocatieOverlijden("Omschrijving locatie overlijden");
        final Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_OVERLIJDEN_DATUM, overlijdenHisActueel.getDatumOverlijden())
                .put(Element.PERSOON_OVERLIJDEN_GEMEENTECODE, overlijdenHisActueel.getGemeente().getCode())
                .put(Element.PERSOON_OVERLIJDEN_WOONPLAATSNAAM, overlijdenHisActueel.getWoonplaatsnaamOverlijden())
                .put(Element.PERSOON_OVERLIJDEN_BUITENLANDSEPLAATS, overlijdenHisActueel
                        .getBuitenlandsePlaatsOverlijden())
                .put(Element.PERSOON_OVERLIJDEN_BUITENLANDSEREGIO, overlijdenHisActueel
                        .getBuitenlandseRegioOverlijden())
                .put(Element.PERSOON_OVERLIJDEN_OMSCHRIJVINGLOCATIE, overlijdenHisActueel
                        .getOmschrijvingLocatieOverlijden())
                .build();
        final PersoonOverlijdenHistorie overlijdenHisVervallen = new PersoonOverlijdenHistorie(persoon,
                DATUM_19810101, LAND_OF_GEBIED);
        overlijdenHisVervallen.setId(2L);

        persoon.addPersoonOverlijdenHistorie((PersoonOverlijdenHistorie) maakFormeelActueel(
                overlijdenHisActueel, attrWaardenMap));
        persoon.addPersoonOverlijdenHistorie((PersoonOverlijdenHistorie) maakFormeelVervallen
                (overlijdenHisVervallen, attrWaardenMap));
    }

    //persoonskaart -- formeel
    private static void maakHisPersoonsKaart() {
        final PersoonPersoonskaartHistorie persKaartHisActueel = new PersoonPersoonskaartHistorie(persoon, true);
        persKaartHisActueel.setId(1L);
        persKaartHisActueel.setPartij(PARTIJ);
        final Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_PERSOONSKAART_PARTIJCODE, persKaartHisActueel.getPartij().getCode())
                .put(Element.PERSOON_PERSOONSKAART_INDICATIEVOLLEDIGGECONVERTEERD, persKaartHisActueel
                        .getIndicatiePersoonskaartVolledigGeconverteerd())
                .build();
        final PersoonPersoonskaartHistorie persKaartHisVervallen = new PersoonPersoonskaartHistorie(persoon,
                true);
        persKaartHisVervallen.setId(2L);

        persoon.addPersoonPersoonskaartHistorie((PersoonPersoonskaartHistorie) maakFormeelActueel
                (persKaartHisActueel, attrWaardenMap));
        persoon.addPersoonPersoonskaartHistorie((PersoonPersoonskaartHistorie) maakFormeelVervallen
                (persKaartHisVervallen, attrWaardenMap));
    }

    //uitsluitingkiesrecht -- formeel
    private static void maakHisUitsluitingKiesrecht() {
        final PersoonUitsluitingKiesrechtHistorie uitslKiesrechtHisActueel = new PersoonUitsluitingKiesrechtHistorie
                (persoon, true);
        uitslKiesrechtHisActueel.setId(1L);
        uitslKiesrechtHisActueel.setDatumVoorzienEindeUitsluitingKiesrecht(DATUM_19810101);
        final PersoonUitsluitingKiesrechtHistorie uitslKiesrechtHisVervallen = new
                PersoonUitsluitingKiesrechtHistorie(persoon, true);
        uitslKiesrechtHisVervallen.setId(2L);

        final Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE, uitslKiesrechtHisActueel
                        .getDatumVoorzienEindeUitsluitingKiesrecht())
                .build();

        persoon.addPersoonUitsluitingKiesrechtHistorie((PersoonUitsluitingKiesrechtHistorie) maakFormeelActueel
                (uitslKiesrechtHisActueel, attrWaardenMap));
        persoon.addPersoonUitsluitingKiesrechtHistorie((PersoonUitsluitingKiesrechtHistorie) maakFormeelVervallen
                (uitslKiesrechtHisVervallen, attrWaardenMap));
    }

    //VERBLIJFSRECHT -- formeel
    private static void maakHisVerblijfsrecht() {
        final PersoonVerblijfsrechtHistorie verblijfsrechtHisActueel = new PersoonVerblijfsrechtHistorie(
                persoon, VERBLIJFSRECHT, DATUM_19810101, DATUM_19810201);
        verblijfsrechtHisActueel.setId(1L);
        verblijfsrechtHisActueel.setDatumVoorzienEindeVerblijfsrecht(DATUM_19810201);
        final PersoonVerblijfsrechtHistorie verblijfsrechtHisVervallen = new PersoonVerblijfsrechtHistorie(
                persoon, VERBLIJFSRECHT, DATUM_19810101, DATUM_19810201);
        verblijfsrechtHisVervallen.setId(2L);

        final Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE, verblijfsrechtHisActueel.getVerblijfsrecht()
                        .getCode())
                .put(Element.PERSOON_VERBLIJFSRECHT_DATUMAANVANG, verblijfsrechtHisActueel
                        .getDatumAanvangVerblijfsrecht())
                .put(Element.PERSOON_VERBLIJFSRECHT_DATUMMEDEDELING, verblijfsrechtHisActueel
                        .getDatumMededelingVerblijfsrecht())
                .put(Element.PERSOON_VERBLIJFSRECHT_DATUMVOORZIENEINDE, verblijfsrechtHisActueel
                        .getDatumVoorzienEindeVerblijfsrecht())
                .build();

        persoon.addPersoonVerblijfsrechtHistorie((PersoonVerblijfsrechtHistorie) maakFormeelActueel
                (verblijfsrechtHisActueel, attrWaardenMap));
        persoon.addPersoonVerblijfsrechtHistorie((PersoonVerblijfsrechtHistorie) maakFormeelVervallen
                (verblijfsrechtHisVervallen, attrWaardenMap));
    }


    //samegestelde naam -- materieel
    private static void maakHisSamengesteldeNaam() {
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisActueel = new PersoonSamengesteldeNaamHistorie
                (persoon, "Vlag", true, true);
        samengesteldeNaamHisActueel.setId(1L);
        samengesteldeNaamHisActueel.setVoornamen("Cees");
        samengesteldeNaamHisActueel.setVoorvoegsel("de");
        samengesteldeNaamHisActueel.setAdellijkeTitel(AdellijkeTitel.B);
        samengesteldeNaamHisActueel.setScheidingsteken('-');
        samengesteldeNaamHisActueel.setPredicaat(Predicaat.H);
        samengesteldeNaamHisActueel.setGeslachtsnaamstam("de Prins");
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisBeeindigd = new PersoonSamengesteldeNaamHistorie
                (persoon, "Vlag", true, true);
        samengesteldeNaamHisBeeindigd.setId(2L);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHisVervallen = new PersoonSamengesteldeNaamHistorie
                (persoon, "Vlag", true, true);
        samengesteldeNaamHisVervallen.setId(3L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, samengesteldeNaamHisActueel
                        .getGeslachtsnaamstam())
                .put(Element.PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID, samengesteldeNaamHisActueel
                        .getIndicatieAfgeleid())
                .put(Element.PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS, samengesteldeNaamHisActueel
                        .getIndicatieNamenreeks())
                .put(Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN, samengesteldeNaamHisActueel.getVoornamen())
                .put(Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL, samengesteldeNaamHisActueel.getVoorvoegsel())
                .put(Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, samengesteldeNaamHisActueel
                        .getAdellijkeTitel())
                .put(Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN, samengesteldeNaamHisActueel
                        .getScheidingsteken().toString())
                .put(Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, samengesteldeNaamHisActueel.getPredicaat()
                        .getCode())
                .build();
        persoon.addPersoonSamengesteldeNaamHistorie(maakActueel(samengesteldeNaamHisActueel, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakBeeindigd(samengesteldeNaamHisBeeindigd, attrWaardenMap));
        persoon.addPersoonSamengesteldeNaamHistorie(maakVervallen(samengesteldeNaamHisVervallen, attrWaardenMap));
    }


    private static void mapPersoonBuitenlandsPersoonsnummer() {
        final String autoriteitVanAfgifteCode = "0001";
        final AutoriteitAfgifteBuitenlandsPersoonsnummer autoriteitAfgifteBuitenlandsPersoonsnummer =
                new AutoriteitAfgifteBuitenlandsPersoonsnummer(autoriteitVanAfgifteCode);
        autoriteitAfgifteBuitenlandsPersoonsnummer.setId(1);

        final String nummer = "12";
        final PersoonBuitenlandsPersoonsnummer persoonBlPersnrIdentiteit =
                new PersoonBuitenlandsPersoonsnummer(persoon, autoriteitAfgifteBuitenlandsPersoonsnummer, nummer);
        persoonBlPersnrIdentiteit.setId(14332L);

        Map<Element, Object> attrWaardenMap =
                ImmutableMap.<Element, Object>builder()
                        .put(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE, persoonBlPersnrIdentiteit
                                .getAutoriteitAfgifteBuitenlandsPersoonsnummer().getCode())
                        .put(Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER, persoonBlPersnrIdentiteit.getNummer())
                        .build();

        attribuutMappingInfo.add(
                new MapInfo(
                        attrWaardenMap,
                        persoonBlPersnrIdentiteit.getId(),
                        RecordStatus.IDENTITEIT,
                        Element.PERSOON_BUITENLANDSPERSOONSNUMMER_IDENTITEIT));

        //toevoegen his records
        final PersoonBuitenlandsPersoonsnummerHistorie hisActueel = new PersoonBuitenlandsPersoonsnummerHistorie(persoonBlPersnrIdentiteit);
        hisActueel.setId(1L);
        final PersoonBuitenlandsPersoonsnummerHistorie hisVervallen = new PersoonBuitenlandsPersoonsnummerHistorie(persoonBlPersnrIdentiteit);
        hisVervallen.setId(2L);
        //lege standaard groep!
        attrWaardenMap = null;
        persoonBlPersnrIdentiteit.addPersoonBuitenlandsPersoonsnummerHistorie(
                (PersoonBuitenlandsPersoonsnummerHistorie) maakFormeelActueel(hisActueel, attrWaardenMap));
        persoonBlPersnrIdentiteit.addPersoonBuitenlandsPersoonsnummerHistorie(
                (PersoonBuitenlandsPersoonsnummerHistorie) maakFormeelVervallen(hisVervallen, attrWaardenMap));
        persoon.addPersoonBuitenlandsPersoonsnummer(persoonBlPersnrIdentiteit);

    }

    //persoon reisdoc -- formeel
    private static void mapPersoonReisDocument() {
        final PersoonReisdocument persReisDocumentIdentiteit = new PersoonReisdocument(persoon, SRT_NED_REISDOC);
        persReisDocumentIdentiteit.setId(1L);

        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_REISDOCUMENT_SOORTCODE, persReisDocumentIdentiteit
                        .getSoortNederlandsReisdocument().getCode())
                .build();

        attribuutMappingInfo.add(new MapInfo(
                attrWaardenMap,
                persReisDocumentIdentiteit.getId(),
                RecordStatus.IDENTITEIT,
                Element.PERSOON_REISDOCUMENT_IDENTITEIT));

        final PersoonReisdocumentHistorie persReisdocumentHis =
                new PersoonReisdocumentHistorie(DATUM_19810101, DATUM_19810201, DATUM_19810101, "1",
                        "autoriteitVanAfgifte", persReisDocumentIdentiteit);
        persReisdocumentHis.setId(1L);
        persReisdocumentHis.setDatumInhoudingOfVermissing(DATUM_19810101);
        persReisdocumentHis.setAanduidingInhoudingOfVermissingReisdocument(AAND_INH_OF_VERM_REISDOC);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_REISDOCUMENT_DATUMINHOUDINGVERMISSING, persReisdocumentHis
                        .getDatumInhoudingOfVermissing())
                .put(Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE, persReisdocumentHis.getAutoriteitVanAfgifte())
                .build();

        final PersoonReisdocumentHistorie persReisdocumentHisVervallen =
                new PersoonReisdocumentHistorie(DATUM_19810101, DATUM_19810201, DATUM_19810101, "1",
                        "autoriteitVanAfgifte", persReisDocumentIdentiteit);
        persReisdocumentHisVervallen.setId(2L);
        persReisDocumentIdentiteit.addPersoonReisdocumentHistorieSet((PersoonReisdocumentHistorie) maakFormeelActueel
                (persReisdocumentHis, attrWaardenMap));
        persReisDocumentIdentiteit.addPersoonReisdocumentHistorieSet((PersoonReisdocumentHistorie)
                maakFormeelVervallen(persReisdocumentHisVervallen, attrWaardenMap));
        persoon.addPersoonReisdocument(persReisDocumentIdentiteit);

    }

    //verstr.beperking -- formeel
    private static void mapPersoonVerstrekkingsbeperking() {
        final PersoonVerstrekkingsbeperking persVerstrekkingsBepIdentiteit = new PersoonVerstrekkingsbeperking
                (persoon);
        persVerstrekkingsBepIdentiteit.setId(1L);
        persVerstrekkingsBepIdentiteit.setPartij(PARTIJ);
        persVerstrekkingsBepIdentiteit.setOmschrijvingDerde("OmschrijvingDerde");
        persVerstrekkingsBepIdentiteit.setGemeenteVerordening(PARTIJ);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_VERSTREKKINGSBEPERKING_PARTIJCODE, persVerstrekkingsBepIdentiteit.getPartij()
                        .getCode())
                .put(Element.PERSOON_VERSTREKKINGSBEPERKING_OMSCHRIJVINGDERDE, persVerstrekkingsBepIdentiteit
                        .getOmschrijvingDerde())
                .put(Element.PERSOON_VERSTREKKINGSBEPERKING_GEMEENTEVERORDENINGPARTIJCODE,
                        persVerstrekkingsBepIdentiteit.getGemeenteVerordening().getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, persVerstrekkingsBepIdentiteit.getId(), RecordStatus
                .IDENTITEIT,
                Element.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT));

        final PersoonVerstrekkingsbeperkingHistorie persoonVerstrekkingsbeperkingHis =
                new PersoonVerstrekkingsbeperkingHistorie(persVerstrekkingsBepIdentiteit);
        persoonVerstrekkingsbeperkingHis.setId(1L);
        final PersoonVerstrekkingsbeperkingHistorie persoonVerstrekkingsbeperkingHisVervallen =
                new PersoonVerstrekkingsbeperkingHistorie(persVerstrekkingsBepIdentiteit);
        persoonVerstrekkingsbeperkingHisVervallen.setId(2L);

        persVerstrekkingsBepIdentiteit.addPersoonVerstrekkingsbeperkingHistorie(
                (PersoonVerstrekkingsbeperkingHistorie) maakFormeelActueel(persoonVerstrekkingsbeperkingHis,
                        attrWaardenMap));
        persVerstrekkingsBepIdentiteit.addPersoonVerstrekkingsbeperkingHistorie(
                (PersoonVerstrekkingsbeperkingHistorie) maakFormeelVervallen
                        (persoonVerstrekkingsbeperkingHisVervallen, attrWaardenMap));

        persoon.addPersoonVerstrekkingsbeperking(persVerstrekkingsBepIdentiteit);
    }

    //voornaam -- materieel
    private static void mapPersoonVoornaam() {
        final PersoonVoornaam persVoornaamIdentiteit = new PersoonVoornaam(persoon, 1);
        persVoornaamIdentiteit.setId(1L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_VOORNAAM_VOLGNUMMER, persVoornaamIdentiteit.getVolgnummer())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, persVoornaamIdentiteit.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_VOORNAAM_IDENTITEIT));

        final PersoonVoornaamHistorie persVoornaamHis = new PersoonVoornaamHistorie(persVoornaamIdentiteit, "Naam");
        persVoornaamHis.setId(1L);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_VOORNAAM_NAAM, persVoornaamHis.getNaam())
                .build();

        final PersoonVoornaamHistorie persVoornaamHisBeeindigd = new PersoonVoornaamHistorie(persVoornaamIdentiteit,
                "NaamBeeindigd");
        persVoornaamHisBeeindigd.setId(2L);
        final PersoonVoornaamHistorie persVoornaamHisVervallen = new PersoonVoornaamHistorie(persVoornaamIdentiteit,
                "NaamVervallen");
        persVoornaamHisVervallen.setId(3L);
        persVoornaamIdentiteit.addPersoonVoornaamHistorie(maakActueel(persVoornaamHis, attrWaardenMap));
        persVoornaamIdentiteit.addPersoonVoornaamHistorie(maakBeeindigd(persVoornaamHisBeeindigd, attrWaardenMap));
        persVoornaamIdentiteit.addPersoonVoornaamHistorie(maakVervallen(persVoornaamHisVervallen, attrWaardenMap));
        persoon.addPersoonVoornaam(persVoornaamIdentiteit);
    }

    //gesl.naam.comp -- materieel
    private static void mapPersoonGeslachtsnaamComponent() {
        final PersoonGeslachtsnaamcomponent geslNaamCompIdentiteit = new PersoonGeslachtsnaamcomponent(persoon, 1);
        geslNaamCompIdentiteit.setId(1L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER, geslNaamCompIdentiteit.getVolgnummer())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, geslNaamCompIdentiteit.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT));

        final PersoonGeslachtsnaamcomponentHistorie geslNaamcompHis =
                new PersoonGeslachtsnaamcomponentHistorie(geslNaamCompIdentiteit, "Vlag");
        geslNaamcompHis.setId(1L);
        geslNaamcompHis.setPredicaat(Predicaat.H);
        geslNaamcompHis.setAdellijkeTitel(AdellijkeTitel.B);
        geslNaamcompHis.setVoorvoegsel("de");
        geslNaamcompHis.setScheidingsteken('-');
        geslNaamcompHis.setStam("Vermeer");
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_GESLACHTSNAAMCOMPONENT_PREDICAATCODE, geslNaamcompHis.getPredicaat().getCode())
                .put(Element.PERSOON_GESLACHTSNAAMCOMPONENT_ADELLIJKETITELCODE, geslNaamcompHis.getAdellijkeTitel()
                        .getCode())
                .put(Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOORVOEGSEL, geslNaamcompHis.getVoorvoegsel())
                .put(Element.PERSOON_GESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN, String.valueOf(geslNaamcompHis
                        .getScheidingsteken()))
                .put(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM, geslNaamcompHis.getStam())
                .build();
        final PersoonGeslachtsnaamcomponentHistorie geslNaamcompHisBeeindigd =
                new PersoonGeslachtsnaamcomponentHistorie(geslNaamCompIdentiteit, "Vlag");
        geslNaamcompHisBeeindigd.setId(2L);
        final PersoonGeslachtsnaamcomponentHistorie geslNaamcompHisVervallen =
                new PersoonGeslachtsnaamcomponentHistorie(geslNaamCompIdentiteit, "Vlag");
        geslNaamcompHisVervallen.setId(3L);

        geslNaamCompIdentiteit.addPersoonGeslachtsnaamcomponentHistorie(maakActueel(geslNaamcompHis, attrWaardenMap));
        geslNaamCompIdentiteit.addPersoonGeslachtsnaamcomponentHistorie(maakBeeindigd(geslNaamcompHisBeeindigd, attrWaardenMap));
        geslNaamCompIdentiteit.addPersoonGeslachtsnaamcomponentHistorie(maakVervallen(geslNaamcompHisVervallen, attrWaardenMap));
        persoon.addPersoonGeslachtsnaamcomponent(geslNaamCompIdentiteit);
    }

    //indicaties
    private static void mapPersoonIndicatie() {
        //onder curatele ; materieel
        final PersoonIndicatie persIndicatieOnderCuratele = new PersoonIndicatie(persoon, SoortIndicatie
                .ONDER_CURATELE);
        persIndicatieOnderCuratele.setId(4L);
        attribuutMappingInfo.add(new MapInfo(null, persIndicatieOnderCuratele.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_INDICATIE_ONDERCURATELE_IDENTITEIT));

        final PersoonIndicatieHistorie persIndicatieOnderCurateleHis =
                new PersoonIndicatieHistorie(persIndicatieOnderCuratele, true);
        persIndicatieOnderCurateleHis.setId(1L);
        persIndicatieOnderCurateleHis.setWaarde(true);
        persIndicatieOnderCurateleHis.setMigratieRedenOpnameNationaliteit("mon");
        persIndicatieOnderCurateleHis.setMigratieRedenBeeindigenNationaliteit("mbn");

        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_INDICATIE_ONDERCURATELE_MIGRATIEREDENOPNAMENATIONALITEIT,
                        persIndicatieOnderCurateleHis.getMigratieRedenOpnameNationaliteit())
                .put(Element.PERSOON_INDICATIE_ONDERCURATELE_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                        persIndicatieOnderCurateleHis.getMigratieRedenBeeindigenNationaliteit())
                .put(Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE,
                        persIndicatieOnderCurateleHis.getWaarde())
                .build();
        final PersoonIndicatieHistorie persIndicatieOnderCurateleHisBeeindigd =
                new PersoonIndicatieHistorie(persIndicatieOnderCuratele, true);
        persIndicatieOnderCurateleHisBeeindigd.setId(2L);
        final PersoonIndicatieHistorie persIndicatieOnderCurateleHisVervallen =
                new PersoonIndicatieHistorie(persIndicatieOnderCuratele, true);
        persIndicatieOnderCurateleHisVervallen.setId(3L);

        persIndicatieOnderCuratele.addPersoonIndicatieHistorie(maakActueel(persIndicatieOnderCurateleHis, attrWaardenMap));
        persIndicatieOnderCuratele.addPersoonIndicatieHistorie(maakBeeindigd(persIndicatieOnderCurateleHisBeeindigd, attrWaardenMap));
        persIndicatieOnderCuratele.addPersoonIndicatieHistorie(maakVervallen(persIndicatieOnderCurateleHisVervallen, attrWaardenMap));

        //behandeld als ned ; materieel
        final PersoonIndicatie persIndicatieBehandeldAlsNed = new PersoonIndicatie(persoon, SoortIndicatie
                .BEHANDELD_ALS_NEDERLANDER);
        persIndicatieBehandeldAlsNed.setId(1L);
        attribuutMappingInfo.add(new MapInfo(null, persIndicatieBehandeldAlsNed.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_IDENTITEIT));

        final PersoonIndicatieHistorie persIndicatieBehandeldAlsNedHis =
                new PersoonIndicatieHistorie(persIndicatieBehandeldAlsNed, true);
        persIndicatieBehandeldAlsNedHis.setId(1L);
        persIndicatieBehandeldAlsNedHis.setMigratieRedenOpnameNationaliteit("mon");
        persIndicatieBehandeldAlsNedHis.setMigratieRedenBeeindigenNationaliteit("mbn");
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_MIGRATIEREDENOPNAMENATIONALITEIT,
                        persIndicatieBehandeldAlsNedHis.getMigratieRedenOpnameNationaliteit())
                .put(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                        persIndicatieBehandeldAlsNedHis.getMigratieRedenBeeindigenNationaliteit())
                .put(Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_WAARDE,
                        persIndicatieBehandeldAlsNedHis.getWaarde())
                .build();
        final PersoonIndicatieHistorie persIndicatieBehandeldAlsNedHisBeeindigd =
                new PersoonIndicatieHistorie(persIndicatieBehandeldAlsNed, true);
        persIndicatieBehandeldAlsNedHisBeeindigd.setId(2L);
        final PersoonIndicatieHistorie persIndicatieBehandeldAlsNedHisVervallen =
                new PersoonIndicatieHistorie(persIndicatieBehandeldAlsNed, true);
        persIndicatieBehandeldAlsNedHisVervallen.setId(3L);

        persIndicatieBehandeldAlsNed.addPersoonIndicatieHistorie(maakActueel(persIndicatieBehandeldAlsNedHis, attrWaardenMap));
        persIndicatieBehandeldAlsNed.addPersoonIndicatieHistorie(maakBeeindigd(persIndicatieBehandeldAlsNedHisBeeindigd, attrWaardenMap));
        persIndicatieBehandeldAlsNed.addPersoonIndicatieHistorie(maakVervallen(persIndicatieBehandeldAlsNedHisVervallen, attrWaardenMap));

        //bijz verblijfsrechtelijk pos ; formeel
        final PersoonIndicatie persIndicatieBijzVerblijfsrechtPos = new PersoonIndicatie(persoon, SoortIndicatie
                .BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        persIndicatieBijzVerblijfsrechtPos.setId(2L);
        attribuutMappingInfo.add(new MapInfo(null, persIndicatieBijzVerblijfsrechtPos.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_IDENTITEIT));
        final PersoonIndicatieHistorie persIndicatieBijzVerblijfsrechtPosHis =
                new PersoonIndicatieHistorie(persIndicatieBijzVerblijfsrechtPos, true);
        persIndicatieBijzVerblijfsrechtPosHis.setId(1L);
        persIndicatieBijzVerblijfsrechtPosHis.setWaarde(true);
        persIndicatieBijzVerblijfsrechtPosHis.setMigratieRedenOpnameNationaliteit("mon");
        persIndicatieBijzVerblijfsrechtPosHis.setMigratieRedenBeeindigenNationaliteit("mbn");
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_MIGRATIEREDENOPNAMENATIONALITEIT,
                        persIndicatieBijzVerblijfsrechtPosHis.getMigratieRedenOpnameNationaliteit())
                .put(Element
                                .PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                        persIndicatieBijzVerblijfsrechtPosHis.getMigratieRedenBeeindigenNationaliteit())
                .put(Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_WAARDE,
                        persIndicatieBijzVerblijfsrechtPosHis.getWaarde())
                .build();
        final PersoonIndicatieHistorie persIndicatieBijzVerblijfsrechtPosHisVervallen =
                new PersoonIndicatieHistorie(persIndicatieBijzVerblijfsrechtPos, true);
        persIndicatieBijzVerblijfsrechtPosHisVervallen.setId(2L);

        persIndicatieBijzVerblijfsrechtPos.addPersoonIndicatieHistorie((PersoonIndicatieHistorie)
                maakFormeelActueel(persIndicatieBijzVerblijfsrechtPosHis, attrWaardenMap));
        persIndicatieBijzVerblijfsrechtPos.addPersoonIndicatieHistorie((PersoonIndicatieHistorie)
                maakFormeelVervallen(persIndicatieBijzVerblijfsrechtPosHisVervallen, attrWaardenMap));

        //derde heeft gezag ; materieel
        final PersoonIndicatie persIndicatieDerdeHeeftGezag = new PersoonIndicatie(persoon, SoortIndicatie
                .DERDE_HEEFT_GEZAG);
        persIndicatieDerdeHeeftGezag.setId(3L);
        attribuutMappingInfo.add(new MapInfo(null, persIndicatieDerdeHeeftGezag.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_IDENTITEIT));

        final PersoonIndicatieHistorie persIndicatieDerdeHeeftGezagHis =
                new PersoonIndicatieHistorie(persIndicatieDerdeHeeftGezag, true);
        persIndicatieDerdeHeeftGezagHis.setId(1L);
        persIndicatieDerdeHeeftGezagHis.setWaarde(true);
        persIndicatieDerdeHeeftGezagHis.setMigratieRedenOpnameNationaliteit("mon");
        persIndicatieDerdeHeeftGezagHis.setMigratieRedenBeeindigenNationaliteit("mbn");
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_MIGRATIEREDENOPNAMENATIONALITEIT,
                        persIndicatieDerdeHeeftGezagHis.getMigratieRedenOpnameNationaliteit())
                .put(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                        persIndicatieDerdeHeeftGezagHis.getMigratieRedenBeeindigenNationaliteit())
                .put(Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_WAARDE,
                        persIndicatieDerdeHeeftGezagHis.getWaarde())
                .build();
        final PersoonIndicatieHistorie persIndicatieDerdeHeeftGezagHisBeeindigd =
                new PersoonIndicatieHistorie(persIndicatieDerdeHeeftGezag, true);
        persIndicatieDerdeHeeftGezagHisBeeindigd.setId(2L);
        final PersoonIndicatieHistorie persIndicatieDerdeHeeftGezagHisVervallen =
                new PersoonIndicatieHistorie(persIndicatieDerdeHeeftGezag, true);
        persIndicatieDerdeHeeftGezagHisVervallen.setId(3L);

        persIndicatieDerdeHeeftGezag.addPersoonIndicatieHistorie(maakActueel(persIndicatieDerdeHeeftGezagHis, attrWaardenMap));
        persIndicatieDerdeHeeftGezag.addPersoonIndicatieHistorie(maakBeeindigd(persIndicatieDerdeHeeftGezagHisBeeindigd, attrWaardenMap));
        persIndicatieDerdeHeeftGezag.addPersoonIndicatieHistorie(maakVervallen(persIndicatieDerdeHeeftGezagHisVervallen, attrWaardenMap));

        //sign mbt verstr reisdoc ; formeel
        final PersoonIndicatie persIndicatieSignMbtVerstReisdoc = new PersoonIndicatie(persoon, SoortIndicatie
                .SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT);
        persIndicatieSignMbtVerstReisdoc.setId(5L);
        attribuutMappingInfo.add(new MapInfo(null, persIndicatieSignMbtVerstReisdoc.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_IDENTITEIT));

        final PersoonIndicatieHistorie persIndicatieSignMbtVerstReisdocHis =
                new PersoonIndicatieHistorie(persIndicatieSignMbtVerstReisdoc, true);
        persIndicatieSignMbtVerstReisdocHis.setId(1L);
        persIndicatieSignMbtVerstReisdocHis.setWaarde(true);
        persIndicatieSignMbtVerstReisdocHis.setMigratieRedenOpnameNationaliteit("mon");
        persIndicatieSignMbtVerstReisdocHis.setMigratieRedenBeeindigenNationaliteit("mbn");
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element
                                .PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_MIGRATIEREDENOPNAMENATIONALITEIT,
                        persIndicatieBijzVerblijfsrechtPosHis.getMigratieRedenOpnameNationaliteit())
                .put(Element
                                .PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                        persIndicatieBijzVerblijfsrechtPosHis.getMigratieRedenBeeindigenNationaliteit())
                .put(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_WAARDE,
                        persIndicatieBijzVerblijfsrechtPosHis.getWaarde())
                .build();
        final PersoonIndicatieHistorie persIndicatieSignMbtVerstReisdocHisVervallen =
                new PersoonIndicatieHistorie(persIndicatieSignMbtVerstReisdoc, true);
        persIndicatieSignMbtVerstReisdocHisVervallen.setId(2L);

        persIndicatieSignMbtVerstReisdoc.addPersoonIndicatieHistorie((PersoonIndicatieHistorie)
                maakFormeelActueel(persIndicatieSignMbtVerstReisdocHis, attrWaardenMap));
        persIndicatieSignMbtVerstReisdoc.addPersoonIndicatieHistorie((PersoonIndicatieHistorie)
                maakFormeelVervallen(persIndicatieSignMbtVerstReisdocHisVervallen, attrWaardenMap));

        //staatloos ; materieel
        final PersoonIndicatie persIndicatieStaatloos = new PersoonIndicatie(persoon, SoortIndicatie
                .STAATLOOS);
        persIndicatieStaatloos.setId(6L);
        attribuutMappingInfo.add(new MapInfo(null, persIndicatieStaatloos.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_INDICATIE_STAATLOOS_IDENTITEIT));

        final PersoonIndicatieHistorie persIndicatieStaatloosHis =
                new PersoonIndicatieHistorie(persIndicatieStaatloos, true);
        persIndicatieStaatloosHis.setId(1L);
        persIndicatieStaatloosHis.setWaarde(true);
        persIndicatieStaatloosHis.setMigratieRedenOpnameNationaliteit("mon");
        persIndicatieStaatloosHis.setMigratieRedenBeeindigenNationaliteit("mbn");
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_INDICATIE_STAATLOOS_MIGRATIEREDENOPNAMENATIONALITEIT,
                        persIndicatieDerdeHeeftGezagHis.getMigratieRedenOpnameNationaliteit())
                .put(Element.PERSOON_INDICATIE_STAATLOOS_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                        persIndicatieDerdeHeeftGezagHis.getMigratieRedenBeeindigenNationaliteit())
                .put(Element.PERSOON_INDICATIE_STAATLOOS_WAARDE,
                        persIndicatieDerdeHeeftGezagHis.getWaarde())
                .build();
        final PersoonIndicatieHistorie persIndicatieStaatloosHisBeeindigd =
                new PersoonIndicatieHistorie(persIndicatieStaatloos, true);
        persIndicatieStaatloosHisBeeindigd.setId(2L);
        final PersoonIndicatieHistorie persIndicatieStaatloosHisVervallen =
                new PersoonIndicatieHistorie(persIndicatieStaatloos, true);
        persIndicatieStaatloosHisVervallen.setId(3L);

        persIndicatieStaatloos.addPersoonIndicatieHistorie(maakActueel(persIndicatieStaatloosHis, attrWaardenMap));
        persIndicatieStaatloos.addPersoonIndicatieHistorie(maakBeeindigd(persIndicatieStaatloosHisBeeindigd, attrWaardenMap));
        persIndicatieStaatloos.addPersoonIndicatieHistorie(maakVervallen(persIndicatieStaatloosHisVervallen, attrWaardenMap));

        //vastgesteld niet ned ; materieel
        final PersoonIndicatie persIndicatieVastgesteldNietNed = new PersoonIndicatie(persoon, SoortIndicatie
                .VASTGESTELD_NIET_NEDERLANDER);
        persIndicatieVastgesteldNietNed.setId(7L);
        attribuutMappingInfo.add(new MapInfo(null, persIndicatieVastgesteldNietNed.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_IDENTITEIT));

        final PersoonIndicatieHistorie persIndicatieVastgesteldNietNedHis =
                new PersoonIndicatieHistorie(persIndicatieVastgesteldNietNed, true);
        persIndicatieVastgesteldNietNedHis.setId(1L);
        persIndicatieVastgesteldNietNedHis.setWaarde(true);
        persIndicatieVastgesteldNietNedHis.setMigratieRedenOpnameNationaliteit("mon");
        persIndicatieVastgesteldNietNedHis.setMigratieRedenBeeindigenNationaliteit("mbn");
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_MIGRATIEREDENOPNAMENATIONALITEIT,
                        persIndicatieVastgesteldNietNedHis.getMigratieRedenOpnameNationaliteit())
                .put(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                        persIndicatieVastgesteldNietNedHis.getMigratieRedenBeeindigenNationaliteit())
                .put(Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_WAARDE,
                        persIndicatieVastgesteldNietNedHis.getWaarde())
                .build();
        final PersoonIndicatieHistorie persIndicatieVastgesteldNietNedHisBeeindigd =
                new PersoonIndicatieHistorie(persIndicatieVastgesteldNietNed, true);
        persIndicatieVastgesteldNietNedHisBeeindigd.setId(2L);
        final PersoonIndicatieHistorie persIndicatieVastgesteldNietNedHisVervallen =
                new PersoonIndicatieHistorie(persIndicatieVastgesteldNietNed, true);
        persIndicatieVastgesteldNietNedHisVervallen.setId(3L);

        persIndicatieVastgesteldNietNed.addPersoonIndicatieHistorie(maakActueel(persIndicatieVastgesteldNietNedHis, attrWaardenMap));
        persIndicatieVastgesteldNietNed.addPersoonIndicatieHistorie(maakBeeindigd(persIndicatieVastgesteldNietNedHisBeeindigd, attrWaardenMap));
        persIndicatieVastgesteldNietNed.addPersoonIndicatieHistorie(maakVervallen(persIndicatieVastgesteldNietNedHisVervallen, attrWaardenMap));

        //verstr. beperking ; formeel
        final PersoonIndicatie persIndicatieVolledigeVerstrBep = new PersoonIndicatie(persoon, SoortIndicatie
                .VOLLEDIGE_VERSTREKKINGSBEPERKING);
        persIndicatieVolledigeVerstrBep.setId(8L);
        attribuutMappingInfo.add(new MapInfo(null, persIndicatieVolledigeVerstrBep.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_IDENTITEIT));

        final PersoonIndicatieHistorie persIndicatieVolledigeVerstrBepHis =
                new PersoonIndicatieHistorie(persIndicatieVolledigeVerstrBep, true);
        persIndicatieVolledigeVerstrBepHis.setId(1L);
        persIndicatieVolledigeVerstrBepHis.setWaarde(true);
        persIndicatieVolledigeVerstrBepHis.setMigratieRedenOpnameNationaliteit("mon");
        persIndicatieVolledigeVerstrBepHis.setMigratieRedenBeeindigenNationaliteit("mbn");
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_MIGRATIEREDENOPNAMENATIONALITEIT,
                        persIndicatieBijzVerblijfsrechtPosHis.getMigratieRedenOpnameNationaliteit())
                .put(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                        persIndicatieBijzVerblijfsrechtPosHis.getMigratieRedenBeeindigenNationaliteit())
                .put(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE,
                        persIndicatieBijzVerblijfsrechtPosHis.getWaarde())
                .build();
        final PersoonIndicatieHistorie persIndicatieVolledigeVerstrBepHisVervallen =
                new PersoonIndicatieHistorie(persIndicatieVolledigeVerstrBep, true);
        persIndicatieVolledigeVerstrBepHisVervallen.setId(2L);

        persIndicatieVolledigeVerstrBep.addPersoonIndicatieHistorie((PersoonIndicatieHistorie)
                maakFormeelActueel(persIndicatieVolledigeVerstrBepHis, attrWaardenMap));
        persIndicatieVolledigeVerstrBep.addPersoonIndicatieHistorie((PersoonIndicatieHistorie)
                maakFormeelVervallen(persIndicatieVolledigeVerstrBepHisVervallen, attrWaardenMap));

        //onverwerkt document aanwezig ; formeel
        final PersoonIndicatie persIndicatieOnverwerktDocumentAanwezig = new PersoonIndicatie(persoon, SoortIndicatie
                .ONVERWERKT_DOCUMENT_AANWEZIG);
        persIndicatieOnverwerktDocumentAanwezig.setId(9L);
        attribuutMappingInfo.add(new MapInfo(null, persIndicatieOnverwerktDocumentAanwezig.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_IDENTITEIT));

        final PersoonIndicatieHistorie persIndicatieOnverwerktDocAanwHis =
                new PersoonIndicatieHistorie(persIndicatieOnverwerktDocumentAanwezig, true);
        persIndicatieOnverwerktDocAanwHis.setId(1L);
        persIndicatieOnverwerktDocAanwHis.setWaarde(true);
        persIndicatieOnverwerktDocAanwHis.setMigratieRedenOpnameNationaliteit("mon");
        persIndicatieOnverwerktDocAanwHis.setMigratieRedenBeeindigenNationaliteit("mbn");
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_MIGRATIEREDENOPNAMENATIONALITEIT,
                        persIndicatieBijzVerblijfsrechtPosHis.getMigratieRedenOpnameNationaliteit())
                .put(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_MIGRATIEREDENBEEINDIGENNATIONALITEIT,
                        persIndicatieBijzVerblijfsrechtPosHis.getMigratieRedenBeeindigenNationaliteit())
                .put(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_WAARDE,
                        persIndicatieBijzVerblijfsrechtPosHis.getWaarde())
                .build();
        final PersoonIndicatieHistorie persIndicatieOnverwerktDocAanwHisVervallen =
                new PersoonIndicatieHistorie(persIndicatieOnverwerktDocumentAanwezig, true);
        persIndicatieOnverwerktDocAanwHisVervallen.setId(2L);

        persIndicatieOnverwerktDocumentAanwezig.addPersoonIndicatieHistorie((PersoonIndicatieHistorie)
                maakFormeelActueel(persIndicatieOnverwerktDocAanwHis, attrWaardenMap));
        persIndicatieOnverwerktDocumentAanwezig.addPersoonIndicatieHistorie((PersoonIndicatieHistorie)
                maakFormeelVervallen(persIndicatieOnverwerktDocAanwHisVervallen, attrWaardenMap));

        persoon.addPersoonIndicatie(persIndicatieDerdeHeeftGezag);
        persoon.addPersoonIndicatie(persIndicatieOnderCuratele);
        persoon.addPersoonIndicatie(persIndicatieBehandeldAlsNed);
        persoon.addPersoonIndicatie(persIndicatieBijzVerblijfsrechtPos);
        persoon.addPersoonIndicatie(persIndicatieSignMbtVerstReisdoc);
        persoon.addPersoonIndicatie(persIndicatieStaatloos);
        persoon.addPersoonIndicatie(persIndicatieVastgesteldNietNed);
        persoon.addPersoonIndicatie(persIndicatieVolledigeVerstrBep);
        persoon.addPersoonIndicatie(persIndicatieOnverwerktDocumentAanwezig);
    }

    //persoonadres - materieel
    private static void mapPersoonAdres() {
        final PersoonAdres persoonAdresIdentiteit = new PersoonAdres(persoon);
        persoonAdresIdentiteit.setId(1L);
        attribuutMappingInfo.add(new MapInfo(null, persoonAdresIdentiteit.getId(), RecordStatus.IDENTITEIT,
                Element.PERSOON_ADRES_IDENTITEIT));

        final PersoonAdresHistorie persAdresHis = new PersoonAdresHistorie(persoonAdresIdentiteit, SoortAdres
                .BRIEFADRES, LAND_OF_GEBIED, RDN_WIJZIGING_VERBLIJF);
        persAdresHis.setId(1L);
        persAdresHis.setSoortAdres(SoortAdres.WOONADRES);
        persAdresHis.setAangeverAdreshouding(AANGEVER);
        persAdresHis.setLocatieOmschrijving("Omschrijving locatie");
        persAdresHis.setLocatietovAdres("by");
        persAdresHis.setHuisnummer(111);
        persAdresHis.setHuisletter('a');
        persAdresHis.setHuisnummertoevoeging("z");
        persAdresHis.setIdentificatiecodeAdresseerbaarObject("ident adres");
        persAdresHis.setIdentificatiecodeNummeraanduiding("ident nr");
        persAdresHis.setGemeente(GEMEENTE);
        persAdresHis.setGemeentedeel("gemeentedeel");
        persAdresHis.setDatumAanvangAdreshouding(DATUM_19810101);
        persAdresHis.setNaamOpenbareRuimte("denor");
        persAdresHis.setAfgekorteNaamOpenbareRuimte("dekorteor");
        persAdresHis.setBuitenlandsAdresRegel1("buitenlands adres regel 1");
        persAdresHis.setBuitenlandsAdresRegel2("buitenlands adres regel 2");
        persAdresHis.setBuitenlandsAdresRegel3("buitenlands adres regel 3");
        persAdresHis.setBuitenlandsAdresRegel4("buitenlands adres regel 4");
        persAdresHis.setBuitenlandsAdresRegel5("buitenlands adres regel 5");
        persAdresHis.setBuitenlandsAdresRegel6("buitenlands adres regel 6");
        persAdresHis.setPostcode("3232ZZ");
        persAdresHis.setWoonplaatsnaam("Woonplaatsnaam");
        persAdresHis.setIndicatiePersoonAangetroffenOpAdres(false);
        final Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_ADRES_LANDGEBIEDCODE, persAdresHis.getLandOfGebied().getCode())
                .put(Element.PERSOON_ADRES_REDENWIJZIGINGCODE, String.valueOf(persAdresHis.getRedenWijziging()
                        .getCode()))
                .put(Element.PERSOON_ADRES_SOORTCODE, persAdresHis.getSoortAdres())
                .put(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE, String.valueOf(persAdresHis
                        .getAangeverAdreshouding().getCode()))
                .put(Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING, persAdresHis.getLocatieOmschrijving())
                .put(Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES, persAdresHis.getLocatietovAdres())
                .put(Element.PERSOON_ADRES_HUISNUMMER, persAdresHis.getHuisnummer())
                .put(Element.PERSOON_ADRES_HUISLETTER,
                        String.valueOf(persAdresHis.getHuisletter()))
                .put(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING, persAdresHis.getHuisnummertoevoeging())
                .put(Element.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT, persAdresHis
                        .getIdentificatiecodeAdresseerbaarObject())
                .put(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING, persAdresHis
                        .getIdentificatiecodeNummeraanduiding())
                .put(Element.PERSOON_ADRES_GEMEENTECODE, persAdresHis.getGemeente().getCode())
                .put(Element.PERSOON_ADRES_GEMEENTEDEEL, persAdresHis.getGemeentedeel())
                .put(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING, persAdresHis.getDatumAanvangAdreshouding())
                .put(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE, persAdresHis.getNaamOpenbareRuimte())
                .put(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE, persAdresHis.getAfgekorteNaamOpenbareRuimte())
                .put(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1, persAdresHis.getBuitenlandsAdresRegel1())
                .put(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2, persAdresHis.getBuitenlandsAdresRegel2())
                .put(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3, persAdresHis.getBuitenlandsAdresRegel3())
                .put(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4, persAdresHis.getBuitenlandsAdresRegel4())
                .put(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5, persAdresHis.getBuitenlandsAdresRegel5())
                .put(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6, persAdresHis.getBuitenlandsAdresRegel6())
                .put(Element.PERSOON_ADRES_POSTCODE, persAdresHis.getPostcode())
                .put(Element.PERSOON_ADRES_WOONPLAATSNAAM, persAdresHis.getWoonplaatsnaam())
                .put(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES, persAdresHis
                        .getIndicatiePersoonAangetroffenOpAdres())
                .build();
        final PersoonAdresHistorie persAdresHisBeeindigd = new PersoonAdresHistorie(persoonAdresIdentiteit, SoortAdres
                .BRIEFADRES, LAND_OF_GEBIED, RDN_WIJZIGING_VERBLIJF);
        persAdresHisBeeindigd.setId(2L);
        final PersoonAdresHistorie persAdresHisVervallen = new PersoonAdresHistorie(persoonAdresIdentiteit, SoortAdres
                .BRIEFADRES, LAND_OF_GEBIED, RDN_WIJZIGING_VERBLIJF);
        persAdresHisVervallen.setId(3L);

        persoonAdresIdentiteit.addPersoonAdresHistorie(maakActueel(persAdresHis, attrWaardenMap));
        persoonAdresIdentiteit.addPersoonAdresHistorie(maakBeeindigd(persAdresHisBeeindigd, attrWaardenMap));
        persoonAdresIdentiteit.addPersoonAdresHistorie(maakVervallen(persAdresHisVervallen, attrWaardenMap));
        persoon.addPersoonAdres(persoonAdresIdentiteit);
    }

    //NATIONALITEIT --materieel
    private static void mapPersoonNationaliteit() {
        //identiteit
        final PersoonNationaliteit persNationaliteitIdentiteit = new PersoonNationaliteit(persoon, NATIONALITEIT);
        persNationaliteitIdentiteit.setId(1L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE, persNationaliteitIdentiteit.getNationaliteit()
                        .getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, persNationaliteitIdentiteit.getId(), RecordStatus
                .IDENTITEIT,
                Element.PERSOON_NATIONALITEIT_IDENTITEIT));

        //standaard
        final PersoonNationaliteitHistorie persoonNationaliteitHis = new PersoonNationaliteitHistorie
                (persNationaliteitIdentiteit);
        persoonNationaliteitHis.setId(1L);
        persoonNationaliteitHis.setRedenVerkrijgingNLNationaliteit(RDN_VERKRIJGING_NL_NAT);
        persoonNationaliteitHis.setRedenVerliesNLNationaliteit(RDN_VERLIES_NL_NAT);
        persoonNationaliteitHis.setIndicatieBijhoudingBeeindigd(true);
        persoonNationaliteitHis.setMigratieRedenOpnameNationaliteit("x");
        persoonNationaliteitHis.setMigratieRedenBeeindigenNationaliteit("y");
        persoonNationaliteitHis.setMigratieDatumEindeBijhouding(DATUM_19810101);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE, persoonNationaliteitHis
                        .getRedenVerkrijgingNLNationaliteit().getCode())
                .put(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE, persoonNationaliteitHis
                        .getRedenVerliesNLNationaliteit().getCode())
                .put(Element.PERSOON_NATIONALITEIT_INDICATIEBIJHOUDINGBEEINDIGD, persoonNationaliteitHis
                        .getIndicatieBijhoudingBeeindigd())
                .put(Element.PERSOON_NATIONALITEIT_MIGRATIEREDENOPNAMENATIONALITEIT, persoonNationaliteitHis
                        .getMigratieRedenOpnameNationaliteit())
                .put(Element.PERSOON_NATIONALITEIT_MIGRATIEREDENBEEINDIGENNATIONALITEIT, persoonNationaliteitHis
                        .getMigratieRedenBeeindigenNationaliteit())
                .put(Element.PERSOON_NATIONALITEIT_MIGRATIEDATUMEINDEBIJHOUDING, persoonNationaliteitHis
                        .getMigratieDatumEindeBijhouding())
                .build();
        final PersoonNationaliteitHistorie persoonNationaliteitHisBeeindigd = new PersoonNationaliteitHistorie
                (persNationaliteitIdentiteit);
        persoonNationaliteitHisBeeindigd.setId(2L);
        final PersoonNationaliteitHistorie persoonNationaliteitHisVervallen = new PersoonNationaliteitHistorie
                (persNationaliteitIdentiteit);
        persoonNationaliteitHisVervallen.setId(3L);

        persNationaliteitIdentiteit.addPersoonNationaliteitHistorie(maakActueel(persoonNationaliteitHis, attrWaardenMap));
        persNationaliteitIdentiteit.addPersoonNationaliteitHistorie(maakBeeindigd(persoonNationaliteitHisBeeindigd, attrWaardenMap));
        persNationaliteitIdentiteit.addPersoonNationaliteitHistorie((maakVervallen(persoonNationaliteitHisVervallen, attrWaardenMap)));
        persoon.addPersoonNationaliteit(persNationaliteitIdentiteit);
    }

    //verificatie -- formeel
    private static void mapVerificatie() {
        final PersoonVerificatie persVerificatieIdentiteit = new PersoonVerificatie(persoon, PARTIJ, "V");
        persVerificatieIdentiteit.setId(1L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_VERIFICATIE_SOORT, persVerificatieIdentiteit.getSoortVerificatie())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, persVerificatieIdentiteit.getId(),
                RecordStatus.IDENTITEIT, Element.PERSOON_VERIFICATIE_IDENTITEIT));

        final PersoonVerificatieHistorie persVerificatieHis = new PersoonVerificatieHistorie
                (persVerificatieIdentiteit, DATUM_19810101);
        persVerificatieHis.setId(1L);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_VERIFICATIE_DATUM, persVerificatieHis.getDatum())
                .build();
        final PersoonVerificatieHistorie persVerificatieHisVervallen = new PersoonVerificatieHistorie
                (persVerificatieIdentiteit, DATUM_19810201);
        persVerificatieHisVervallen.setId(2L);

        persVerificatieIdentiteit.addPersoonVerificatieHistorie((PersoonVerificatieHistorie) maakFormeelActueel
                (persVerificatieHis, attrWaardenMap));
        persVerificatieIdentiteit.addPersoonVerificatieHistorie((PersoonVerificatieHistorie) maakFormeelVervallen
                (persVerificatieHisVervallen, attrWaardenMap));
        persoon.addPersoonVerificatie(persVerificatieIdentiteit);
    }

    //kind-ouder betr
    private static void mapKindOuderBetrekking() {
        final Relatie relatieKindOuder = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        relatieKindOuder.setId(1L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE, relatieKindOuder.getSoortRelatie()
                        .getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, relatieKindOuder.getId(), RecordStatus
                .IDENTITEIT, Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT));

        final RelatieHistorie relatieKindOuderHis = new RelatieHistorie(relatieKindOuder);
        relatieKindOuderHis.setId(1L);
        relatieKindOuderHis.setGemeenteAanvang(GEMEENTE);
        relatieKindOuderHis.setWoonplaatsnaamAanvang("woonplaatsnaam aanvang");
        relatieKindOuderHis.setBuitenlandsePlaatsAanvang("buitenl plaats aanvang");
        relatieKindOuderHis.setBuitenlandseRegioAanvang("buitenl regio aanvang");
        relatieKindOuderHis.setOmschrijvingLocatieAanvang("omschr loc aanvang");
        relatieKindOuderHis.setLandOfGebiedAanvang(LAND_OF_GEBIED);
        relatieKindOuderHis.setRedenBeeindigingRelatie(RDN_BEEINDIGING_RELATIE);
        relatieKindOuderHis.setDatumAanvang(DATUM_19810101);
        relatieKindOuderHis.setDatumEinde(DATUM_19810201);
        relatieKindOuderHis.setGemeenteEinde(GEMEENTE);
        relatieKindOuderHis.setWoonplaatsnaamEinde("woonplaatsnaam einde");
        relatieKindOuderHis.setBuitenlandsePlaatsEinde("buitenl plaats einde");
        relatieKindOuderHis.setBuitenlandseRegioEinde("buitenl regio einde");
        relatieKindOuderHis.setOmschrijvingLocatieEinde("omschr loc einde");
        relatieKindOuderHis.setLandOfGebiedEinde(LAND_OF_GEBIED);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.FAMILIERECHTELIJKEBETREKKING_GEMEENTEAANVANGCODE, relatieKindOuderHis.getGemeenteAanvang
                        ().getCode())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_GEMEENTEEINDECODE, relatieKindOuderHis.getGemeenteEinde()
                        .getCode())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAMAANVANG, relatieKindOuderHis
                        .getWoonplaatsnaamAanvang())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAMEINDE, relatieKindOuderHis
                        .getWoonplaatsnaamEinde())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSAANVANG, relatieKindOuderHis
                        .getBuitenlandsePlaatsAanvang())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSEINDE, relatieKindOuderHis
                        .getBuitenlandsePlaatsEinde())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOAANVANG, relatieKindOuderHis
                        .getBuitenlandseRegioAanvang())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOEINDE, relatieKindOuderHis
                        .getBuitenlandseRegioEinde())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVINGLOCATIEAANVANG, relatieKindOuderHis
                        .getOmschrijvingLocatieAanvang())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVINGLOCATIEEINDE, relatieKindOuderHis
                        .getOmschrijvingLocatieEinde())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_LANDGEBIEDAANVANGCODE, relatieKindOuderHis
                        .getLandOfGebiedAanvang().getCode())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_LANDGEBIEDEINDECODE, relatieKindOuderHis
                        .getLandOfGebiedEinde().getCode())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_REDENEINDECODE, String.valueOf(relatieKindOuderHis
                        .getRedenBeeindigingRelatie().getCode()))
                .put(Element.FAMILIERECHTELIJKEBETREKKING_DATUMAANVANG, relatieKindOuderHis.getDatumAanvang())
                .put(Element.FAMILIERECHTELIJKEBETREKKING_DATUMEINDE, relatieKindOuderHis.getDatumEinde())
                .build();
        final RelatieHistorie relatieKindOuderHisVervallen = new RelatieHistorie(relatieKindOuder);
        relatieKindOuderHisVervallen.setId(11L);
        relatieKindOuder.addRelatieHistorie((RelatieHistorie) maakFormeelActueel(relatieKindOuderHis, attrWaardenMap));
        relatieKindOuder.addRelatieHistorie((RelatieHistorie) maakFormeelVervallen(relatieKindOuderHisVervallen,
                attrWaardenMap));

        //kind betrokkenheid incl historie
        final Betrokkenheid betrKind = new Betrokkenheid(SoortBetrokkenheid.KIND, relatieKindOuder);
        betrKind.setId(1L);
        betrKind.setPersoon(persoon);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_KIND_ROLCODE, betrKind.getSoortBetrokkenheid().getCode()).build();
        attribuutMappingInfo
                .add(new MapInfo(attrWaardenMap, relatieKindOuder.getId(), RecordStatus.IDENTITEIT, Element.PERSOON_KIND_IDENTITEIT));
        final BetrokkenheidHistorie betrKindHis = new BetrokkenheidHistorie(betrKind);
        betrKindHis.setId(1L);
        final BetrokkenheidHistorie betrKindHisVervallen = new BetrokkenheidHistorie(betrKind);
        betrKindHisVervallen.setId(11L);
        betrKind.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelActueel(betrKindHis, attrWaardenMap));
        betrKind.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelVervallen(betrKindHisVervallen, attrWaardenMap));

        final Betrokkenheid betrOuder1 = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatieKindOuder);
        betrOuder1.setId(2L);
        betrOuder1.setPersoon(gerelateerdeOuder1);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEOUDER_ROLCODE, betrOuder1.getSoortBetrokkenheid().getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, betrOuder1.getId(), RecordStatus
                .IDENTITEIT, Element.GERELATEERDEOUDER_IDENTITEIT));
        final BetrokkenheidHistorie betrOuder1His = new BetrokkenheidHistorie(betrOuder1);
        betrOuder1His.setId(2L);
        final BetrokkenheidHistorie betrOuder1HisVervallen = new BetrokkenheidHistorie(betrOuder1);
        betrOuder1HisVervallen.setId(21L);
        betrOuder1.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelActueel(betrOuder1His, attrWaardenMap));
        betrOuder1.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelVervallen(betrOuder1HisVervallen, attrWaardenMap));

        final Betrokkenheid betrOuder2 = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatieKindOuder);
        betrOuder2.setId(3L);
        betrOuder2.setPersoon(gerelateerdeOuder2);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEOUDER_ROLCODE, betrOuder2.getSoortBetrokkenheid().getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, betrOuder1.getId(), RecordStatus
                .IDENTITEIT, Element.GERELATEERDEOUDER_IDENTITEIT));
        final BetrokkenheidHistorie betrOuder2His = new BetrokkenheidHistorie(betrOuder2);
        betrOuder2His.setId(3L);
        final BetrokkenheidHistorie betrOuder2HisVervallen = new BetrokkenheidHistorie(betrOuder2);
        betrOuder2HisVervallen.setId(31L);
        betrOuder2.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelActueel(betrOuder2His, attrWaardenMap));
        betrOuder2.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelVervallen(betrOuder2HisVervallen, attrWaardenMap));

        relatieKindOuder.addBetrokkenheid(betrKind);
        relatieKindOuder.addBetrokkenheid(betrOuder1);
        relatieKindOuder.addBetrokkenheid(betrOuder2);

        persoon.addBetrokkenheid(betrKind);

    }

    //ouder-kind betr (incl ouderschap en ouderlijkgezag betr)
    private static void mapOuderKindBetrekking() {
        final Relatie relatieOuderKind = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        relatieOuderKind.setId(4L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE, relatieOuderKind.getSoortRelatie()
                        .getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, relatieOuderKind.getId(), RecordStatus
                .IDENTITEIT, Element.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT));

        final RelatieHistorie relatieOuderKindBetrHis = new RelatieHistorie(relatieOuderKind);
        relatieOuderKindBetrHis.setId(4L);

        relatieOuderKind.addRelatieHistorie((RelatieHistorie) maakFormeelActueel(relatieOuderKindBetrHis, attrWaardenMap));
        relatieOuderKind.addRelatieHistorie((RelatieHistorie) maakFormeelVervallen(relatieOuderKindBetrHis, attrWaardenMap));

        //ouder betrokkenheid
        final Betrokkenheid betrOuder = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatieOuderKind);
        betrOuder.setId(4L);
        betrOuder.setPersoon(persoon);

        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_OUDER_ROLCODE, betrOuder.getSoortBetrokkenheid().getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, betrOuder.getId(), RecordStatus
                .IDENTITEIT, Element.PERSOON_OUDER_IDENTITEIT));

        final BetrokkenheidHistorie betrOuderHis = new BetrokkenheidHistorie(betrOuder);
        betrOuderHis.setId(4L);
        final BetrokkenheidHistorie betrOuderHisVervallen = new BetrokkenheidHistorie(betrOuder);
        betrOuderHisVervallen.setId(41L);
        betrOuder.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelActueel(betrOuderHis, attrWaardenMap));
        betrOuder.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelVervallen(betrOuderHisVervallen, attrWaardenMap));

        //gerelateerde kind betrokkenheid
        final Betrokkenheid betrGerelateerdKind = new Betrokkenheid(SoortBetrokkenheid.KIND, relatieOuderKind);
        betrGerelateerdKind.setId(5L);
        betrGerelateerdKind.setPersoon(gerelateerdKind);
        final BetrokkenheidHistorie betrGerelateerdeKindHis = new BetrokkenheidHistorie(betrGerelateerdKind);
        betrGerelateerdeKindHis.setId(5L);
        final BetrokkenheidHistorie betrGerelateerdeKindHisVervallen = new BetrokkenheidHistorie(betrGerelateerdKind);
        betrGerelateerdeKindHisVervallen.setId(51L);

        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEKIND_ROLCODE, betrGerelateerdKind.getSoortBetrokkenheid().getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, betrGerelateerdKind.getId(), RecordStatus
                .IDENTITEIT, Element.GERELATEERDEKIND_IDENTITEIT));

        betrGerelateerdKind.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelActueel
                (betrGerelateerdeKindHis, attrWaardenMap));
        betrGerelateerdKind.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelVervallen
                (betrGerelateerdeKindHisVervallen, attrWaardenMap));

        //ouderschap betrokkenheid
        final BetrokkenheidOuderHistorie betrokkenheidOuderHistorie = new BetrokkenheidOuderHistorie(betrOuder);
        betrokkenheidOuderHistorie.setId(1L);
        betrokkenheidOuderHistorie.setIndicatieOuderUitWieKindIsGeboren(true);
        final BetrokkenheidOuderHistorie betrokkenheidOuderHistorieBeeindigd = new BetrokkenheidOuderHistorie
                (betrOuder);
        betrokkenheidOuderHistorieBeeindigd.setId(2L);
        final BetrokkenheidOuderHistorie betrokkenheidOuderHistorieVervallen = new BetrokkenheidOuderHistorie
                (betrOuder);
        betrokkenheidOuderHistorieVervallen.setId(3L);

        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN, betrokkenheidOuderHistorie
                        .getIndicatieOuderUitWieKindIsGeboren())
                .build();
        betrOuder.addBetrokkenheidOuderHistorie(maakActueel(betrokkenheidOuderHistorie, attrWaardenMap));
        betrOuder.addBetrokkenheidOuderHistorie(maakBeeindigd(betrokkenheidOuderHistorieBeeindigd, attrWaardenMap));
        betrOuder.addBetrokkenheidOuderHistorie(maakVervallen(betrokkenheidOuderHistorieVervallen, attrWaardenMap));

        relatieOuderKind.addBetrokkenheid(betrOuder);
        relatieOuderKind.addBetrokkenheid(betrGerelateerdKind);
        persoon.addBetrokkenheid(betrOuder);
    }

    private static void mapHuwelijk() {
        final Relatie relatieHuwelijk = new Relatie(SoortRelatie.HUWELIJK);
        relatieHuwelijk.setId(3L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.HUWELIJK_SOORTCODE, relatieHuwelijk.getSoortRelatie().getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, relatieHuwelijk.getId(), RecordStatus
                .IDENTITEIT, Element.HUWELIJK_IDENTITEIT));
        final RelatieHistorie relatieHuwelijkHis = new RelatieHistorie(relatieHuwelijk);
        relatieHuwelijkHis.setId(3L);
        relatieHuwelijkHis.setGemeenteAanvang(GEMEENTE);
        relatieHuwelijkHis.setWoonplaatsnaamAanvang("woonplaatsnaam aanvang");
        relatieHuwelijkHis.setBuitenlandsePlaatsAanvang("buitenl plaats aanvang");
        relatieHuwelijkHis.setBuitenlandseRegioAanvang("buitenl regio aanvang");
        relatieHuwelijkHis.setOmschrijvingLocatieAanvang("omschr loc aanvang");
        relatieHuwelijkHis.setLandOfGebiedAanvang(LAND_OF_GEBIED);
        relatieHuwelijkHis.setRedenBeeindigingRelatie(RDN_BEEINDIGING_RELATIE);
        relatieHuwelijkHis.setDatumAanvang(DATUM_19810101);
        relatieHuwelijkHis.setDatumEinde(DATUM_19810201);
        relatieHuwelijkHis.setGemeenteEinde(GEMEENTE);
        relatieHuwelijkHis.setWoonplaatsnaamEinde("woonplaatsnaam einde");
        relatieHuwelijkHis.setBuitenlandsePlaatsEinde("buitenl plaats einde");
        relatieHuwelijkHis.setBuitenlandseRegioEinde("buitenl regio einde");
        relatieHuwelijkHis.setOmschrijvingLocatieEinde("omschr loc einde");
        relatieHuwelijkHis.setLandOfGebiedEinde(LAND_OF_GEBIED);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.HUWELIJK_GEMEENTEAANVANGCODE, relatieHuwelijkHis.getGemeenteAanvang
                        ().getCode())
                .put(Element.HUWELIJK_GEMEENTEEINDECODE, relatieHuwelijkHis.getGemeenteEinde()
                        .getCode())
                .put(Element.HUWELIJK_WOONPLAATSNAAMAANVANG, relatieHuwelijkHis
                        .getWoonplaatsnaamAanvang())
                .put(Element.HUWELIJK_WOONPLAATSNAAMEINDE, relatieHuwelijkHis
                        .getWoonplaatsnaamEinde())
                .put(Element.HUWELIJK_BUITENLANDSEPLAATSAANVANG, relatieHuwelijkHis
                        .getBuitenlandsePlaatsAanvang())
                .put(Element.HUWELIJK_BUITENLANDSEPLAATSEINDE, relatieHuwelijkHis
                        .getBuitenlandsePlaatsEinde())
                .put(Element.HUWELIJK_BUITENLANDSEREGIOAANVANG, relatieHuwelijkHis
                        .getBuitenlandseRegioAanvang())
                .put(Element.HUWELIJK_BUITENLANDSEREGIOEINDE, relatieHuwelijkHis
                        .getBuitenlandseRegioEinde())
                .put(Element.HUWELIJK_OMSCHRIJVINGLOCATIEAANVANG, relatieHuwelijkHis
                        .getOmschrijvingLocatieAanvang())
                .put(Element.HUWELIJK_OMSCHRIJVINGLOCATIEEINDE, relatieHuwelijkHis
                        .getOmschrijvingLocatieEinde())
                .put(Element.HUWELIJK_LANDGEBIEDAANVANGCODE, relatieHuwelijkHis
                        .getLandOfGebiedAanvang().getCode())
                .put(Element.HUWELIJK_LANDGEBIEDEINDECODE, relatieHuwelijkHis
                        .getLandOfGebiedEinde().getCode())
                .put(Element.HUWELIJK_REDENEINDECODE, String.valueOf(relatieHuwelijkHis
                        .getRedenBeeindigingRelatie().getCode()))
                .put(Element.HUWELIJK_DATUMAANVANG, relatieHuwelijkHis.getDatumAanvang())
                .put(Element.HUWELIJK_DATUMEINDE, relatieHuwelijkHis.getDatumEinde())
                .build();
        final RelatieHistorie relatieHuwelijkHisVervallen = new RelatieHistorie(relatieHuwelijk);
        relatieHuwelijkHisVervallen.setId(31L);
        relatieHuwelijk.addRelatieHistorie((RelatieHistorie) maakFormeelActueel(relatieHuwelijkHis, attrWaardenMap));
        relatieHuwelijk.addRelatieHistorie((RelatieHistorie) maakFormeelVervallen(relatieHuwelijkHisVervallen, attrWaardenMap));

        //huwelijkspartner betrokkenheid
        final Betrokkenheid betrHuwelijksPartner = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatieHuwelijk);
        betrHuwelijksPartner.setId(7L);
        betrHuwelijksPartner.setPersoon(persoon);

        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_PARTNER_ROLCODE, betrHuwelijksPartner.getSoortBetrokkenheid().getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, betrHuwelijksPartner.getId(), RecordStatus
                .IDENTITEIT, Element.PERSOON_PARTNER_IDENTITEIT));

        final BetrokkenheidHistorie betrHuwelijksPartnerHis = new BetrokkenheidHistorie(betrHuwelijksPartner);
        betrHuwelijksPartnerHis.setId(7L);
        final BetrokkenheidHistorie betrHuwelijksPartnerHisVervallen = new BetrokkenheidHistorie(betrHuwelijksPartner);
        betrHuwelijksPartnerHisVervallen.setId(71L);
        betrHuwelijksPartner.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelActueel
                (betrHuwelijksPartnerHis, attrWaardenMap));
        betrHuwelijksPartner.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelVervallen
                (betrHuwelijksPartnerHisVervallen, attrWaardenMap));

        //gerelateerde huwelijkspartner betrokkenheid
        final Betrokkenheid betrGerelateerdeHuwelijksPartner = new Betrokkenheid(SoortBetrokkenheid.PARTNER,
                relatieHuwelijk);
        betrGerelateerdeHuwelijksPartner.setId(6L);
        betrGerelateerdeHuwelijksPartner.setPersoon(gerelateerdeHuwelijksPartner);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEHUWELIJKSPARTNER_ROLCODE, betrGerelateerdeHuwelijksPartner
                        .getSoortBetrokkenheid().getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, betrGerelateerdeHuwelijksPartner.getId(), RecordStatus
                .IDENTITEIT, Element.GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT));
        final BetrokkenheidHistorie betrPartnerHis = new BetrokkenheidHistorie(betrGerelateerdeHuwelijksPartner);
        betrPartnerHis.setId(6L);
        final BetrokkenheidHistorie betrPartnerHisVervallen = new BetrokkenheidHistorie(betrGerelateerdeHuwelijksPartner);
        betrPartnerHisVervallen.setId(61L);
        betrGerelateerdeHuwelijksPartner.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelActueel
                (betrPartnerHis, null));
        betrGerelateerdeHuwelijksPartner.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelVervallen
                (betrPartnerHisVervallen, null));

        relatieHuwelijk.addBetrokkenheid(betrHuwelijksPartner);
        relatieHuwelijk.addBetrokkenheid(betrGerelateerdeHuwelijksPartner);
        persoon.addBetrokkenheid(betrHuwelijksPartner);
    }

    private static void mapGeregisteerdPartnerschap() {
        final Relatie relatieGeregistreerdPartnerschap = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        relatieGeregistreerdPartnerschap.setId(4L);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GEREGISTREERDPARTNERSCHAP_SOORTCODE, relatieGeregistreerdPartnerschap.getSoortRelatie()
                        .getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, relatieGeregistreerdPartnerschap.getId(), RecordStatus
                .IDENTITEIT, Element.GEREGISTREERDPARTNERSCHAP_IDENTITEIT));

        final RelatieHistorie relatieGeregisteerdPartnerHis = new RelatieHistorie(relatieGeregistreerdPartnerschap);
        relatieGeregisteerdPartnerHis.setId(4L);
        relatieGeregisteerdPartnerHis.setGemeenteAanvang(GEMEENTE);
        relatieGeregisteerdPartnerHis.setWoonplaatsnaamAanvang("woonplaatsnaam aanvang");
        relatieGeregisteerdPartnerHis.setBuitenlandsePlaatsAanvang("buitenl plaats aanvang");
        relatieGeregisteerdPartnerHis.setBuitenlandseRegioAanvang("buitenl regio aanvang");
        relatieGeregisteerdPartnerHis.setOmschrijvingLocatieAanvang("omschr loc aanvang");
        relatieGeregisteerdPartnerHis.setLandOfGebiedAanvang(LAND_OF_GEBIED);
        relatieGeregisteerdPartnerHis.setRedenBeeindigingRelatie(RDN_BEEINDIGING_RELATIE);
        relatieGeregisteerdPartnerHis.setDatumAanvang(DATUM_19810101);
        relatieGeregisteerdPartnerHis.setDatumEinde(DATUM_19810201);
        relatieGeregisteerdPartnerHis.setGemeenteEinde(GEMEENTE);
        relatieGeregisteerdPartnerHis.setWoonplaatsnaamEinde("woonplaatsnaam einde");
        relatieGeregisteerdPartnerHis.setBuitenlandsePlaatsEinde("buitenl plaats einde");
        relatieGeregisteerdPartnerHis.setBuitenlandseRegioEinde("buitenl regio einde");
        relatieGeregisteerdPartnerHis.setOmschrijvingLocatieEinde("omschr loc einde");
        relatieGeregisteerdPartnerHis.setLandOfGebiedEinde(LAND_OF_GEBIED);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE, relatieGeregisteerdPartnerHis
                        .getGemeenteAanvang
                                ().getCode())
                .put(Element.GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE, relatieGeregisteerdPartnerHis
                        .getGemeenteEinde()
                        .getCode())
                .put(Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG, relatieGeregisteerdPartnerHis
                        .getWoonplaatsnaamAanvang())
                .put(Element.GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE, relatieGeregisteerdPartnerHis
                        .getWoonplaatsnaamEinde())
                .put(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSAANVANG, relatieGeregisteerdPartnerHis
                        .getBuitenlandsePlaatsAanvang())
                .put(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSEINDE, relatieGeregisteerdPartnerHis
                        .getBuitenlandsePlaatsEinde())
                .put(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG, relatieGeregisteerdPartnerHis
                        .getBuitenlandseRegioAanvang())
                .put(Element.GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE, relatieGeregisteerdPartnerHis
                        .getBuitenlandseRegioEinde())
                .put(Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG, relatieGeregisteerdPartnerHis
                        .getOmschrijvingLocatieAanvang())
                .put(Element.GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE, relatieGeregisteerdPartnerHis
                        .getOmschrijvingLocatieEinde())
                .put(Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE, relatieGeregisteerdPartnerHis
                        .getLandOfGebiedAanvang().getCode())
                .put(Element.GEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE, relatieGeregisteerdPartnerHis
                        .getLandOfGebiedEinde().getCode())
                .put(Element.GEREGISTREERDPARTNERSCHAP_REDENEINDECODE, String.valueOf(relatieGeregisteerdPartnerHis
                        .getRedenBeeindigingRelatie().getCode()))
                .put(Element.GEREGISTREERDPARTNERSCHAP_DATUMAANVANG, relatieGeregisteerdPartnerHis.getDatumAanvang())
                .put(Element.GEREGISTREERDPARTNERSCHAP_DATUMEINDE, relatieGeregisteerdPartnerHis.getDatumEinde())
                .build();
        final RelatieHistorie relatieGeregisteerdPartnerHisVervallen = new RelatieHistorie(relatieGeregistreerdPartnerschap);
        relatieGeregisteerdPartnerHisVervallen.setId(41L);
        relatieGeregistreerdPartnerschap.addRelatieHistorie((RelatieHistorie) maakFormeelActueel
                (relatieGeregisteerdPartnerHis, attrWaardenMap));
        relatieGeregistreerdPartnerschap.addRelatieHistorie((RelatieHistorie) maakFormeelVervallen
                (relatieGeregisteerdPartnerHisVervallen, attrWaardenMap));

        //geregistreerd partner betrokkenheid
        final Betrokkenheid betrGeregistreerdPartner = new Betrokkenheid(SoortBetrokkenheid.PARTNER,
                relatieGeregistreerdPartnerschap);
        betrGeregistreerdPartner.setId(8L);
        betrGeregistreerdPartner.setPersoon(persoon);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.PERSOON_PARTNER_ROLCODE, betrGeregistreerdPartner.getSoortBetrokkenheid().getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, betrGeregistreerdPartner.getId(), RecordStatus
                .IDENTITEIT, Element.PERSOON_PARTNER_IDENTITEIT));
        final BetrokkenheidHistorie betrGeregistreerdPartnerHis = new BetrokkenheidHistorie(betrGeregistreerdPartner);
        betrGeregistreerdPartnerHis.setId(8L);
        final BetrokkenheidHistorie betrGeregistreerdPartnerHisVervallen = new BetrokkenheidHistorie(betrGeregistreerdPartner);
        betrGeregistreerdPartnerHisVervallen.setId(81L);
        betrGeregistreerdPartner.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelActueel
                (betrGeregistreerdPartnerHis, attrWaardenMap));
        betrGeregistreerdPartner.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelVervallen
                (betrGeregistreerdPartnerHisVervallen, attrWaardenMap));

        //gerelateerde geregistreerde partner betrokkenheid
        final Betrokkenheid betrGerelateerdeGeregistreerdPartner = new Betrokkenheid(SoortBetrokkenheid.PARTNER,
                relatieGeregistreerdPartnerschap);
        betrGerelateerdeGeregistreerdPartner.setId(9L);
        betrGerelateerdeGeregistreerdPartner.setPersoon(gerelateerdeGeregistreerdPartner);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GERELATEERDEGEREGISTREERDEPARTNER_ROLCODE, betrGerelateerdeGeregistreerdPartner
                        .getSoortBetrokkenheid().getCode())
                .build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, betrGerelateerdeGeregistreerdPartner.getId(),
                RecordStatus.IDENTITEIT, Element.GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT));
        final BetrokkenheidHistorie betrPartnerHis = new BetrokkenheidHistorie(betrGerelateerdeGeregistreerdPartner);
        betrPartnerHis.setId(9L);
        final BetrokkenheidHistorie betrPartnerHisVervallen = new BetrokkenheidHistorie(betrGerelateerdeGeregistreerdPartner);
        betrPartnerHisVervallen.setId(91L);
        betrGerelateerdeGeregistreerdPartner.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelActueel
                (betrPartnerHis, attrWaardenMap));
        betrGerelateerdeGeregistreerdPartner.addBetrokkenheidHistorie((BetrokkenheidHistorie) maakFormeelVervallen
                (betrPartnerHisVervallen, attrWaardenMap));

        relatieGeregistreerdPartnerschap.addBetrokkenheid(betrGeregistreerdPartner);
        relatieGeregistreerdPartnerschap.addBetrokkenheid(betrGerelateerdeGeregistreerdPartner);
        persoon.addBetrokkenheid(betrGeregistreerdPartner);
    }


    private static void mapPersoonOnderzoek() {
        //onderzoek incl formele historie
        final Onderzoek onderzoek = new Onderzoek(PARTIJ, persoon);
        onderzoek.setId(1);
        Map<Element, Object> attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.ONDERZOEK_PARTIJCODE, onderzoek.getPartij().getCode())
                .build();
        attribuutMappingInfo
                .add(new MapInfo(attrWaardenMap, onderzoek.getId(), RecordStatus.IDENTITEIT, Element.ONDERZOEK_IDENTITEIT));

        final OnderzoekHistorie onderzoekHis = new OnderzoekHistorie(DATUM_19810201, StatusOnderzoek.IN_UITVOERING,
                onderzoek);
        onderzoekHis.setId(1);
        onderzoekHis.setDatumAanvang(DATUM_19810101);
        onderzoekHis.setDatumEinde(DATUM_19810201);
        onderzoekHis.setOmschrijving("omschrijving onderzoek");
        onderzoekHis.setStatusOnderzoek(StatusOnderzoek.IN_UITVOERING);

        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.ONDERZOEK_DATUMAANVANG, onderzoekHis.getDatumAanvang())
                .put(Element.ONDERZOEK_DATUMEINDE, onderzoekHis.getDatumEinde())
                .put(Element.ONDERZOEK_OMSCHRIJVING, onderzoekHis.getOmschrijving())
                .put(Element.ONDERZOEK_STATUSNAAM, onderzoekHis.getStatusOnderzoek().getNaam())
                .build();
        onderzoek.addOnderzoekHistorie((OnderzoekHistorie) maakFormeelActueel(onderzoekHis, attrWaardenMap));
        onderzoek.addOnderzoekHistorie((OnderzoekHistorie) maakFormeelVervallen(onderzoekHis, attrWaardenMap));

        //gegeven in onderzoek -- voorkomen, incl formele historie
        final GegevenInOnderzoek gegevenInOnderzoekVoorkomen = new GegevenInOnderzoek(onderzoek, Element.AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT);
        gegevenInOnderzoekVoorkomen.setId(1L);
        gegevenInOnderzoekVoorkomen.setEntiteitOfVoorkomen(onderzoekHis);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GEGEVENINONDERZOEK_ELEMENTNAAM, gegevenInOnderzoekVoorkomen.getSoortGegeven().getNaam())
                .put(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN, gegevenInOnderzoekVoorkomen.getEntiteitOfVoorkomen().getId()).build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, gegevenInOnderzoekVoorkomen.getId(), RecordStatus.IDENTITEIT,
                Element.GEGEVENINONDERZOEK_IDENTITEIT));

        final GegevenInOnderzoekHistorie
                gioVoorkomenHistorie =
                new GegevenInOnderzoekHistorie(gegevenInOnderzoekVoorkomen, onderzoek.getOnderzoekHistorieSet().iterator().next().getActieInhoud());
        gioVoorkomenHistorie.setId(1L);
        gegevenInOnderzoekVoorkomen.addGegevenInOnderzoekHistorie((GegevenInOnderzoekHistorie) maakFormeelActueel(gioVoorkomenHistorie, attrWaardenMap));
        gegevenInOnderzoekVoorkomen.addGegevenInOnderzoekHistorie((GegevenInOnderzoekHistorie) maakFormeelVervallen(gioVoorkomenHistorie, attrWaardenMap));

        //gegeven in onderzoek -- entiteit, incl formele historie
        final GegevenInOnderzoek gegevenInOnderzoekEntiteit = new GegevenInOnderzoek(onderzoek, Element.AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT);
        gegevenInOnderzoekEntiteit.setId(2L);
        gegevenInOnderzoekEntiteit.setEntiteitOfVoorkomen(gegevenInOnderzoekVoorkomen);
        attrWaardenMap = ImmutableMap.<Element, Object>builder()
                .put(Element.GEGEVENINONDERZOEK_ELEMENTNAAM, gegevenInOnderzoekEntiteit.getSoortGegeven().getNaam())
                .put(Element.GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN, gegevenInOnderzoekEntiteit.getEntiteitOfVoorkomen().getId()).build();
        attribuutMappingInfo.add(new MapInfo(attrWaardenMap, gegevenInOnderzoekEntiteit.getId(), RecordStatus
                .IDENTITEIT, Element.GEGEVENINONDERZOEK_IDENTITEIT));

        final GegevenInOnderzoekHistorie
                gioEntiteitHistorie =
                new GegevenInOnderzoekHistorie(gegevenInOnderzoekEntiteit, onderzoek.getOnderzoekHistorieSet().iterator().next().getActieInhoud());
        gioEntiteitHistorie.setId(2L);
        gegevenInOnderzoekEntiteit.addGegevenInOnderzoekHistorie((GegevenInOnderzoekHistorie) maakFormeelActueel(gioEntiteitHistorie, attrWaardenMap));
        gegevenInOnderzoekEntiteit.addGegevenInOnderzoekHistorie((GegevenInOnderzoekHistorie) maakFormeelVervallen(gioEntiteitHistorie, attrWaardenMap));

        onderzoek.addGegevenInOnderzoek(gegevenInOnderzoekVoorkomen);
        onderzoek.addGegevenInOnderzoek(gegevenInOnderzoekEntiteit);

        persoon.addOnderzoek(onderzoek);
    }


    private static FormeleHistorie maakFormeelActueel(FormeleHistorie entity, Map<Element, Object> waarden) {
        entity.setDatumTijdRegistratie(TS_19810101);
        entity.setActieInhoud(actieLijst.get(0));
        entity.setIndicatieVoorkomenTbvLeveringMutaties(true);
        if (waarden != null) {
            attribuutMappingInfo.add(new MapInfo(waarden, entity.getId(), RecordStatus.FORMEEL_ACTUEEL, bepaalGroep
                    (waarden)));
        }
        return entity;
    }

    private static FormeleHistorie maakFormeelVervallen(FormeleHistorie entity, Map<Element, Object> waarden) {
        entity.setDatumTijdRegistratie(TS_19810301);
        entity.setActieInhoud(actieLijst.get(0));
        entity.setDatumTijdVerval(TS_19810301);
        entity.setNadereAanduidingVerval('S');
        entity.setActieVerval(actieLijst.get(0));
        entity.setActieVervalTbvLeveringMutaties(actieLijst.get(0));
        if (waarden != null) {
            attribuutMappingInfo.add(new MapInfo(null, entity.getId(), RecordStatus
                    .FORMEEL_VERVALLEN, bepaalGroep(waarden)));
        }
        return entity;
    }

    @SuppressWarnings("rawtypes")
    private static <T extends MaterieleHistorie> T maakActueel(T entity, Map<Element, Object> waarden) {
        entity.setDatumTijdRegistratie(TS_19810101);
        entity.setActieInhoud(actieLijst.get(0));
        entity.setDatumAanvangGeldigheid(19671223);
        entity.setIndicatieVoorkomenTbvLeveringMutaties(true);
        // entity.setActieVervalTbvLeveringMutaties(actieLijst.get(0));
        if (waarden != null) {
            attribuutMappingInfo.add(new MapInfo(waarden, entity.getId(), RecordStatus.ACTUEEL,
                    bepaalGroep(waarden)));
        }
        return entity;
    }

    @SuppressWarnings("rawtypes")
    private static <T extends MaterieleHistorie> T maakBeeindigd(T entity, Map<Element, Object> waarden) {
        entity.setDatumTijdRegistratie(TS_19810201);
        entity.setActieInhoud(actieLijst.get(0));
        entity.setDatumAanvangGeldigheid(19671223);
        entity.setDatumEindeGeldigheid(19671224);
        entity.setActieAanpassingGeldigheid(actieLijst.get(0));
        if (waarden != null) {
            attribuutMappingInfo.add(new MapInfo(null, entity.getId(), RecordStatus.BEEINDIGD,
                    bepaalGroep(waarden)));
        }
        return entity;
    }

    @SuppressWarnings("rawtypes")
    private static <T extends MaterieleHistorie> T maakVervallen(T entity, Map<Element, Object> waarden) {
        entity.setDatumTijdRegistratie(TS_19810301);
        entity.setActieInhoud(actieLijst.get(0));
        entity.setDatumAanvangGeldigheid(19671223);
        entity.setDatumTijdVerval(TS_19810301);
        entity.setNadereAanduidingVerval('S');
        entity.setActieVerval(actieLijst.get(0));
        entity.setActieVervalTbvLeveringMutaties(actieLijst.get(0));
        if (waarden != null) {
            attribuutMappingInfo.add(new MapInfo(null, entity.getId(), RecordStatus.VERVALLEN,
                    bepaalGroep(waarden)));
        }
        return entity;
    }

    private static Element bepaalGroep(Map<Element, Object> attrWaardenMap) {
        return attrWaardenMap.entrySet().iterator().next().getKey().getGroep();
    }

    static final class MapInfo {
        Map<Element, Object> attribuutWaarden;
        Number id;
        RecordStatus recordStatus;
        Element groep;

        MapInfo(Map<Element, Object> attribuutWaarden, Number id, RecordStatus recordStatus, Element groep) {
            this.attribuutWaarden = attribuutWaarden;
            this.id = id;
            this.recordStatus = recordStatus;
            this.groep = groep;
        }
    }
}
