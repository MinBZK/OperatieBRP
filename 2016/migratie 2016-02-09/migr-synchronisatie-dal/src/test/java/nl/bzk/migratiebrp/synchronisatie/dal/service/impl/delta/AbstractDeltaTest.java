/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Bijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonBijhoudingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeboorteHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIDHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonInschrijvingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonPersoonskaartHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaam;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StapelVoorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.SleutelUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpMapperUtil;

/**
 * Abstract test class voor Strategy tests.
 */
public abstract class AbstractDeltaTest {

    protected static final Timestamp DATUMTIJD_STEMPEL_OUD = Timestamp.valueOf("1990-01-01 01:00:00.0");
    protected static final Timestamp DATUMTIJD_STEMPEL_NIEUW = Timestamp.valueOf("2014-01-01 01:00:00.0");
    protected static final int DATUM_OUD = 19900101;
    protected static final Integer DATUM_NIEUW = 20140101;
    protected static final String TSREG_VELD = "tsreg";
    protected static final long BESTAAND_ADMIN_NUMMER = 1234567890L;
    protected static final long NIEUW_ADMIN_NUMMER = 9876543210L;
    protected static final String HUWELIJK_OF_GP_CATEGORIE = "05";
    protected static final String KIND_CATEGORIE = "09";
    private static final long VERSIENUMMER_1 = 1L;
    private static final long VERSIENUMMER_2 = 2L;
    private static final String VOLGNUMMER = "volgnr";

    protected AdministratieveHandeling maakAdministratieveHandeling(final Timestamp tsRegistratie) {
        return maakAdministratieveHandeling(true, tsRegistratie);
    }

    protected AdministratieveHandeling maakAdministratieveHandeling(final boolean isBestaandPersoon, final Timestamp tsRegistratie) {
        final SoortAdministratieveHandeling soortAdministratieveHandeling;
        if (isBestaandPersoon) {
            soortAdministratieveHandeling = SoortAdministratieveHandeling.GBA_INITIELE_VULLING;
        } else {
            soortAdministratieveHandeling = SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL;
        }
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(maakPartij(), soortAdministratieveHandeling);
        administratieveHandeling.setDatumTijdRegistratie(tsRegistratie);
        return administratieveHandeling;
    }

    public Partij maakPartij() {
        return new Partij("Gemeente 's-Gravenhage", 51801);
    }

    protected PersoonIndicatie maakPersoonIndicatie(final Persoon persoon, final AdministratieveHandeling ah, final boolean isBestaandPersoon) {
        final PersoonIndicatie pi = new PersoonIndicatie(persoon, SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING);
        pi.addPersoonIndicatieHistorie(maakPersoonIndicatieHistorie(pi, ah, isBestaandPersoon));
        return pi;
    }

    protected PersoonIndicatieHistorie maakPersoonIndicatieHistorie(
        final PersoonIndicatie pi,
        final AdministratieveHandeling ah,
        final boolean isBestaandPersoon)
    {
        final PersoonIndicatieHistorie pih = new PersoonIndicatieHistorie(pi, true);
        final Timestamp tijdstempel = isBestaandPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        pih.setDatumTijdRegistratie(tijdstempel);
        pih.setActieInhoud(maakBrpActie(ah, tijdstempel));
        return pih;
    }

    private PersoonInschrijvingHistorie maakPersoonInschrijvingHistorie(
        final Persoon persoon,
        final AdministratieveHandeling ah,
        final boolean isBestaandPersoon)
    {
        final PersoonInschrijvingHistorie pih;
        final int datumInschrijving = 20130801;
        if (isBestaandPersoon) {
            pih = new PersoonInschrijvingHistorie(persoon, datumInschrijving, VERSIENUMMER_1, DATUMTIJD_STEMPEL_OUD);
            pih.setDatumTijdRegistratie(DATUMTIJD_STEMPEL_OUD);
        } else {
            pih = new PersoonInschrijvingHistorie(persoon, datumInschrijving, VERSIENUMMER_2, DATUMTIJD_STEMPEL_NIEUW);
            pih.setDatumTijdRegistratie(DATUMTIJD_STEMPEL_NIEUW);
        }
        pih.setActieInhoud(maakBrpActie(ah, pih.getDatumTijdRegistratie()));
        return pih;
    }

    private PersoonPersoonskaartHistorie maakPersoonskaartHistorie(
        final Persoon persoon,
        final AdministratieveHandeling ah,
        final boolean isBestaandPersoon)
    {
        final PersoonPersoonskaartHistorie pph = new PersoonPersoonskaartHistorie(persoon, true);
        final Timestamp tijdstempel = isBestaandPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        pph.setDatumTijdRegistratie(tijdstempel);
        pph.setActieInhoud(maakBrpActie(ah, tijdstempel));
        return pph;
    }

    private PersoonAfgeleidAdministratiefHistorie maakAfgeleidAdministratiefHistorie(
        final Persoon persoon,
        final AdministratieveHandeling ah,
        final boolean isBestaandPersoon)
    {
        final Timestamp tijdstempel = isBestaandPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        final PersoonAfgeleidAdministratiefHistorie paah = new PersoonAfgeleidAdministratiefHistorie((short) 0, persoon, ah, tijdstempel, false);
        paah.setDatumTijdRegistratie(tijdstempel);
        paah.setActieInhoud(maakBrpActie(ah, tijdstempel));
        return paah;
    }

    private PersoonDeelnameEuVerkiezingenHistorie maakDeelnameEuVerkiezingenHistorie(
        final Persoon persoon,
        final AdministratieveHandeling ah,
        final boolean isBestaandPersoon)
    {
        final PersoonDeelnameEuVerkiezingenHistorie pdevh = new PersoonDeelnameEuVerkiezingenHistorie(persoon, true);
        final Timestamp tijdstempel = isBestaandPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        pdevh.setDatumTijdRegistratie(tijdstempel);
        pdevh.setActieInhoud(maakBrpActie(ah, tijdstempel));
        return pdevh;
    }

    /**
     * Maakt een persoon met de basis gegevens die wijziging bij een nieuw inkomende LO3-PL.
     *
     * @param isBestaandePersoon
     *            true als het een bestaand persoon is.
     * @return persoon die de minimale gegevens heeft die nodig zijn voor delta-bepalen.
     */
    protected Persoon maakPersoon(final boolean isBestaandePersoon) {
        final Timestamp timestamp = isBestaandePersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        final long versienummer = isBestaandePersoon ? VERSIENUMMER_1 : VERSIENUMMER_2;

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setTijdstipLaatsteWijziging(timestamp);

        final AdministratieveHandeling administratieveHandeling = maakAdministratieveHandeling(isBestaandePersoon, timestamp);
        persoon.setAdministratieveHandeling(administratieveHandeling);

        persoon.getPersoonAfgeleidAdministratiefHistorieSet()
               .add(maakAfgeleidAdministratiefHistorie(persoon, administratieveHandeling, isBestaandePersoon));
        persoon.getPersoonIndicatieSet().add(maakPersoonIndicatie(persoon, administratieveHandeling, isBestaandePersoon));
        persoon.getPersoonInschrijvingHistorieSet().add(maakPersoonInschrijvingHistorie(persoon, administratieveHandeling, isBestaandePersoon));
        persoon.getPersoonDeelnameEuVerkiezingenHistorieSet()
               .add(maakDeelnameEuVerkiezingenHistorie(persoon, administratieveHandeling, isBestaandePersoon));
        persoon.getPersoonPersoonskaartHistorieSet().add(maakPersoonskaartHistorie(persoon, administratieveHandeling, isBestaandePersoon));

        persoon.setAdministratienummer(BESTAAND_ADMIN_NUMMER);
        persoon.setTijdstipLaatsteWijziging(timestamp);
        persoon.setVersienummer(versienummer);
        persoon.setDatumtijdstempel(timestamp);
        return persoon;
    }

    protected void voegKindToe(final Persoon persoon, final Stapel stapel) {
        // Nodig:
        // 1x Persoon, anders dan persoon
        // 2x Betrokkenheden
        // 1x Relatie

        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        stapel.addRelatie(relatie);

        final Persoon gerelateerdePersoon = maakPersoon(true);
        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
        relatie.addBetrokkenheid(ikBetrokkenheid);
        relatie.addBetrokkenheid(kindBetrokkenheid);

        persoon.addBetrokkenheid(ikBetrokkenheid);
        gerelateerdePersoon.addBetrokkenheid(kindBetrokkenheid);
    }

    protected Set<Verschil> maakStandaardVerschillenGeenDatumtijdstempel() {
        final Set<Verschil> verschillen = new HashSet<>();
        final Verschil persoonTijdstipLaatsteWijziging =
                new Verschil(
                    new EntiteitSleutel(Persoon.class, "tijdstipLaatsteWijziging", null),
                    DATUMTIJD_STEMPEL_OUD,
                    DATUMTIJD_STEMPEL_NIEUW,
                    VerschilType.ELEMENT_AANGEPAST,
                    null,
                    null);
        final Verschil persoonTijdstipLaatsteWijzigingGbaSystematiek =
                new Verschil(
                    new EntiteitSleutel(Persoon.class, "tijdstipLaatsteWijzigingGbaSystematiek", null),
                    DATUMTIJD_STEMPEL_OUD,
                    DATUMTIJD_STEMPEL_NIEUW,
                    VerschilType.ELEMENT_AANGEPAST,
                    null,
                    null);

        verschillen.add(persoonTijdstipLaatsteWijziging);
        verschillen.add(persoonTijdstipLaatsteWijzigingGbaSystematiek);
        return verschillen;
    }

    protected Set<Verschil> maakInschrijvingVerschillen(final Persoon kluizenaar, final Persoon dbPersoon) {
        final String indicatieHistorieSetVeld = "persoonIndicatieHistorieSet";
        final String inschrijvingHistorieSetVeld = "persoonInschrijvingHistorieSet";
        final String persoonskaartHistorieSetVeld = "persoonPersoonskaartHistorieSet";

        final EntiteitSleutel tijdstipLaatsteWijzigingSleutel = new EntiteitSleutel(Persoon.class, "tijdstipLaatsteWijziging", null);
        final EntiteitSleutel versienummerSleutel = new EntiteitSleutel(Persoon.class, "versienummer", null);
        final EntiteitSleutel datumtijdstempelSleutel = new EntiteitSleutel(Persoon.class, "datumtijdstempel", null);

        final EntiteitSleutel persoonIndicatieSetSleutel = new EntiteitSleutel(Persoon.class, "persoonIndicatieSet", null);

        final EntiteitSleutel indicatieHistorieOudSleutel =
                new EntiteitSleutel(PersoonIndicatie.class, indicatieHistorieSetVeld, persoonIndicatieSetSleutel);
        indicatieHistorieOudSleutel.addSleuteldeel("srt", SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING.getId());
        final EntiteitSleutel indicatieHistorieNieuwSleutel =
                new EntiteitSleutel(PersoonIndicatie.class, indicatieHistorieSetVeld, persoonIndicatieSetSleutel);
        indicatieHistorieNieuwSleutel.addSleuteldeel("srt", SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING.getId());
        final EntiteitSleutel inschrijvingHistorieOudSleutel = new EntiteitSleutel(Persoon.class, inschrijvingHistorieSetVeld, null);
        final EntiteitSleutel inschrijvingHistorieNieuwSleutel = new EntiteitSleutel(Persoon.class, inschrijvingHistorieSetVeld, null);
        final EntiteitSleutel persoonskaartHistorieOudSleutel = new EntiteitSleutel(Persoon.class, persoonskaartHistorieSetVeld, null);
        final EntiteitSleutel persoonskaartHistorieNieuwSleutel = new EntiteitSleutel(Persoon.class, persoonskaartHistorieSetVeld, null);

        indicatieHistorieOudSleutel.addSleuteldeel(TSREG_VELD, DATUMTIJD_STEMPEL_OUD);
        indicatieHistorieNieuwSleutel.addSleuteldeel(TSREG_VELD, DATUMTIJD_STEMPEL_NIEUW);
        inschrijvingHistorieOudSleutel.addSleuteldeel(TSREG_VELD, DATUMTIJD_STEMPEL_OUD);
        inschrijvingHistorieNieuwSleutel.addSleuteldeel(TSREG_VELD, DATUMTIJD_STEMPEL_NIEUW);
        persoonskaartHistorieOudSleutel.addSleuteldeel(TSREG_VELD, DATUMTIJD_STEMPEL_OUD);
        persoonskaartHistorieNieuwSleutel.addSleuteldeel(TSREG_VELD, DATUMTIJD_STEMPEL_NIEUW);

        final Verschil tijdstipLaatsteWijziging =
                new Verschil(tijdstipLaatsteWijzigingSleutel, DATUMTIJD_STEMPEL_OUD, DATUMTIJD_STEMPEL_NIEUW, VerschilType.ELEMENT_AANGEPAST, null, null);
        final Verschil versienummer = new Verschil(versienummerSleutel, 1L, 2L, VerschilType.ELEMENT_AANGEPAST, null, null);
        final Verschil datumtijdstempel =
                new Verschil(datumtijdstempelSleutel, DATUMTIJD_STEMPEL_OUD, DATUMTIJD_STEMPEL_NIEUW, VerschilType.ELEMENT_AANGEPAST, null, null);

        final Verschil indicatieHistorieOud =
                new Verschil(
                    indicatieHistorieOudSleutel,
                    dbPersoon.getPersoonIndicatieSet().iterator().next().getPersoonIndicatieHistorieSet().iterator().next(),
                    null,
                    VerschilType.RIJ_VERWIJDERD,
                    null,
                    null);
        final Verschil indicatieHistorieNieuw =
                new Verschil(
                    indicatieHistorieNieuwSleutel,
                    null,
                    kluizenaar.getPersoonIndicatieSet().iterator().next().getPersoonIndicatieHistorieSet().iterator().next(),
                    VerschilType.RIJ_TOEGEVOEGD,
                    null,
                    null);
        final Verschil inschrijvingHistorieOud =
                new Verschil(
                    inschrijvingHistorieOudSleutel,
                    dbPersoon.getPersoonInschrijvingHistorieSet().iterator().next(),
                    null,
                    VerschilType.RIJ_VERWIJDERD,
                    null,
                    null);
        final Verschil inschrijvingHistorieNieuw =
                new Verschil(
                    inschrijvingHistorieNieuwSleutel,
                    null,
                    kluizenaar.getPersoonInschrijvingHistorieSet().iterator().next(),
                    VerschilType.RIJ_TOEGEVOEGD,
                    null,
                    null);
        final Verschil persoonskaartHistorieOud =
                new Verschil(
                    persoonskaartHistorieOudSleutel,
                    dbPersoon.getPersoonPersoonskaartHistorieSet().iterator().next(),
                    null,
                    VerschilType.RIJ_VERWIJDERD,
                    null,
                    null);
        final Verschil persoonskaartHistorieNieuw =
                new Verschil(
                    persoonskaartHistorieNieuwSleutel,
                    null,
                    kluizenaar.getPersoonPersoonskaartHistorieSet().iterator().next(),
                    VerschilType.RIJ_TOEGEVOEGD,
                    null,
                    null);

        final Set<Verschil> verschillen = new HashSet<>();
        verschillen.add(tijdstipLaatsteWijziging);
        verschillen.add(versienummer);
        verschillen.add(datumtijdstempel);

        verschillen.add(indicatieHistorieOud);
        verschillen.add(indicatieHistorieNieuw);
        verschillen.add(inschrijvingHistorieOud);
        verschillen.add(inschrijvingHistorieNieuw);
        verschillen.add(persoonskaartHistorieOud);
        verschillen.add(persoonskaartHistorieNieuw);

        return verschillen;
    }

    protected PersoonNationaliteit maakPersoonNationaliteit(
        final Persoon persoon,
        final AdministratieveHandeling administratieveHandeling,
        final Timestamp tijdstempel)
    {
        final Nationaliteit nationaliteit = new Nationaliteit("Nederlands", (short) 1);
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoon, nationaliteit);
        final PersoonNationaliteitHistorie pnh = new PersoonNationaliteitHistorie(persoonNationaliteit);
        final BRPActie actieInhoud = maakBrpActie(administratieveHandeling, tijdstempel);
        pnh.setActieInhoud(actieInhoud);

        persoonNationaliteit.addPersoonNationaliteitHistorie(pnh);
        return persoonNationaliteit;
    }

    protected Lo3Voorkomen maakLo3Voorkomen(final String categorie) {
        return new Lo3Voorkomen(new Lo3Bericht("asdf", Lo3BerichtenBron.SYNCHRONISATIE, new Timestamp(0), "jadajada", false), categorie);
    }

    protected BRPActie maakBrpActie(final AdministratieveHandeling administratieveHandeling, final Timestamp tijdstempel) {
        return new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, maakPartij(), tijdstempel);
    }

    protected void maakDeelnameEuVerkiezingenVerschillen(
        final Persoon kluizenaar,
        final Persoon dbPersoon,
        final Map<EntiteitSleutel, Verschil> verschillen)
    {
        final String deelnameEuVerkieziengenHistorieSetVeld = "persoonDeelnameEuVerkiezingenHistorieSet";
        final EntiteitSleutel deelnameEuVerkieziengenHistorieOudSleutel = new EntiteitSleutel(Persoon.class, deelnameEuVerkieziengenHistorieSetVeld, null);
        final EntiteitSleutel deelnameEuVerkieziengenHistorieNieuwSleutel =
                new EntiteitSleutel(Persoon.class, deelnameEuVerkieziengenHistorieSetVeld, null);
        deelnameEuVerkieziengenHistorieOudSleutel.addSleuteldeel(TSREG_VELD, DATUMTIJD_STEMPEL_OUD);
        deelnameEuVerkieziengenHistorieNieuwSleutel.addSleuteldeel(TSREG_VELD, DATUMTIJD_STEMPEL_NIEUW);
        final Verschil deelnameEuVerkieziengenHistorieOud =
                new Verschil(
                    deelnameEuVerkieziengenHistorieOudSleutel,
                    dbPersoon.getPersoonDeelnameEuVerkiezingenHistorieSet().iterator().next(),
                    null,
                    VerschilType.RIJ_VERWIJDERD,
                    null,
                    null);
        final Verschil deelnameEuVerkieziengenHistorieNieuw =
                new Verschil(
                    deelnameEuVerkieziengenHistorieNieuwSleutel,
                    null,
                    kluizenaar.getPersoonDeelnameEuVerkiezingenHistorieSet().iterator().next(),
                    VerschilType.RIJ_TOEGEVOEGD,
                    null,
                    null);

        verschillen.put(deelnameEuVerkieziengenHistorieOudSleutel, deelnameEuVerkieziengenHistorieOud);
        verschillen.put(deelnameEuVerkieziengenHistorieNieuwSleutel, deelnameEuVerkieziengenHistorieNieuw);
    }

    protected void vulPersoonGroepen(final Persoon persoon, final boolean isBestaandPersoon) {
        final Set<PersoonGeboorteHistorie> geboorteHistorieSet = maakPersoonGeboorteHistorieSet(persoon, isBestaandPersoon);
        persoon.getPersoonGeboorteHistorieSet().addAll(geboorteHistorieSet);

        final Set<PersoonGeslachtsaanduidingHistorie> geslachtsaanduidingHistorieSet =
                maakPersoonGeslachtsaanduidingHistorieSet(persoon, isBestaandPersoon);
        persoon.getPersoonGeslachtsaanduidingHistorieSet().addAll(geslachtsaanduidingHistorieSet);

        final Set<PersoonIDHistorie> persoonIDHistorieSet = maakPersoonIDHistorieSet(persoon, isBestaandPersoon);
        persoon.getPersoonIDHistorieSet().addAll(persoonIDHistorieSet);

        final Set<PersoonSamengesteldeNaamHistorie> persoonSamengesteldeNaamHistorieSet =
                maakPersoonSamengesteldeNaamHistorieSet(persoon, isBestaandPersoon);
        persoon.getPersoonSamengesteldeNaamHistorieSet().addAll(persoonSamengesteldeNaamHistorieSet);

        final PersoonVoornaam persoonVoornaam = maakPersoonVoornaam(persoon, isBestaandPersoon);
        persoon.addPersoonVoornaam(persoonVoornaam);

        final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent = maakPersoonGeslachtsnaamcomponent(persoon, isBestaandPersoon);
        persoon.addPersoonGeslachtsnaamcomponent(persoonGeslachtsnaamcomponent);
    }

    private Set<PersoonGeboorteHistorie> maakPersoonGeboorteHistorieSet(final Persoon persoon, final boolean isBestaandPersoon) {
        final Set<PersoonGeboorteHistorie> historieSet = new HashSet<>();
        historieSet.add(maakPersoonGeboorteHistorie(persoon, isBestaandPersoon));

        if (!isBestaandPersoon) {
            final PersoonGeboorteHistorie historie = maakPersoonGeboorteHistorie(persoon, true);
            historie.setDatumTijdVerval(DATUMTIJD_STEMPEL_NIEUW);
            historieSet.add(historie);
        }
        return historieSet;
    }

    private PersoonGeboorteHistorie maakPersoonGeboorteHistorie(final Persoon persoon, final boolean isBestaandPersoon) {
        final Timestamp timestamp = isBestaandPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        final BRPActie actieInhoud = maakBrpActie(persoon.getAdministratieveHandeling(), timestamp);

        final PersoonGeboorteHistorie historie = new PersoonGeboorteHistorie(persoon, 20120101, maakLandOfGebied());
        historie.setActieInhoud(actieInhoud);
        historie.setDatumTijdRegistratie(timestamp);

        return historie;
    }

    protected LandOfGebied maakLandOfGebied() {
        return new LandOfGebied((short) 1, "één");
    }

    private Set<PersoonGeslachtsaanduidingHistorie> maakPersoonGeslachtsaanduidingHistorieSet(final Persoon persoon, final boolean isBestaandPersoon) {
        final Set<PersoonGeslachtsaanduidingHistorie> historieSet = new HashSet<>();
        historieSet.add(maakPersoonGeslachtsaanduidingHistorie(persoon, isBestaandPersoon));

        if (!isBestaandPersoon) {
            final PersoonGeslachtsaanduidingHistorie historie = maakPersoonGeslachtsaanduidingHistorie(persoon, true);
            historie.setDatumEindeGeldigheid(DATUM_NIEUW);
            historieSet.add(historie);
        }
        return historieSet;
    }

    private PersoonGeslachtsaanduidingHistorie maakPersoonGeslachtsaanduidingHistorie(final Persoon persoon, final boolean isBestaandPersoon) {
        final Timestamp timestamp = isBestaandPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        final BRPActie actieInhoud = maakBrpActie(persoon.getAdministratieveHandeling(), timestamp);

        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(persoon, Geslachtsaanduiding.MAN);
        historie.setActieInhoud(actieInhoud);
        historie.setDatumTijdRegistratie(actieInhoud.getDatumTijdRegistratie());
        historie.setDatumAanvangGeldigheid(DATUM_OUD);

        return historie;
    }

    private Set<PersoonIDHistorie> maakPersoonIDHistorieSet(final Persoon persoon, final boolean isBestaandPersoon) {
        final Set<PersoonIDHistorie> historieSet = new HashSet<>();
        historieSet.add(maakPersoonIDHistorie(persoon, isBestaandPersoon));

        if (!isBestaandPersoon) {
            final PersoonIDHistorie historie = maakPersoonIDHistorie(persoon, true);
            historie.setDatumEindeGeldigheid(DATUM_NIEUW);
            historieSet.add(historie);
        }

        return historieSet;
    }

    private PersoonIDHistorie maakPersoonIDHistorie(final Persoon persoon, final boolean isBestaandPersoon) {
        final Timestamp timestamp = isBestaandPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        final BRPActie actieInhoud = maakBrpActie(persoon.getAdministratieveHandeling(), timestamp);

        final Long administratienummer = BESTAAND_ADMIN_NUMMER;
        final int dag = isBestaandPersoon ? DATUM_OUD : DATUM_NIEUW;

        final PersoonIDHistorie historie = new PersoonIDHistorie(persoon);
        historie.setAdministratienummer(administratienummer);
        historie.setActieInhoud(actieInhoud);
        historie.setDatumTijdRegistratie(actieInhoud.getDatumTijdRegistratie());
        historie.setDatumAanvangGeldigheid(dag);
        return historie;
    }

    private Set<PersoonSamengesteldeNaamHistorie> maakPersoonSamengesteldeNaamHistorieSet(final Persoon persoon, final boolean isBestaandPersoon) {
        final Set<PersoonSamengesteldeNaamHistorie> historieSet = new HashSet<>();
        historieSet.add(maakPersoonSamengesteldeNaamHistorie(persoon, isBestaandPersoon));

        if (!isBestaandPersoon) {
            final PersoonSamengesteldeNaamHistorie historie = maakPersoonSamengesteldeNaamHistorie(persoon, true);
            historie.setDatumEindeGeldigheid(DATUM_NIEUW);
            historieSet.add(historie);
        }
        return historieSet;
    }

     protected PersoonSamengesteldeNaamHistorie maakPersoonSamengesteldeNaamHistorie(final Persoon persoon, final boolean isBestaandPersoon) {
        final Timestamp timestamp = isBestaandPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        final BRPActie actieInhoud = maakBrpActie(persoon.getAdministratieveHandeling(), timestamp);

        final PersoonSamengesteldeNaamHistorie historie = new PersoonSamengesteldeNaamHistorie(persoon, "Boon", false, false);
        historie.setVoornamen("Jan");
        historie.setActieInhoud(actieInhoud);
        historie.setDatumTijdRegistratie(actieInhoud.getDatumTijdRegistratie());
        historie.setDatumAanvangGeldigheid(DATUM_OUD);
        return historie;
    }

    private PersoonVoornaam maakPersoonVoornaam(final Persoon persoon, final boolean isBestaandPersoon) {
        final PersoonVoornaam persoonVoornaam = new PersoonVoornaam(persoon, 1);
        final Set<PersoonVoornaamHistorie> historieSet = persoonVoornaam.getPersoonVoornaamHistorieSet();
        historieSet.add(maakPersoonVoornaamHistorie(persoon, persoonVoornaam, isBestaandPersoon));

        if (!isBestaandPersoon) {
            final PersoonVoornaamHistorie historie = maakPersoonVoornaamHistorie(persoon, persoonVoornaam, true);
            historie.setDatumEindeGeldigheid(DATUM_NIEUW);
            historieSet.add(historie);
        }
        return persoonVoornaam;
    }

    private PersoonVoornaamHistorie maakPersoonVoornaamHistorie(
        final Persoon persoon,
        final PersoonVoornaam persoonVoornaam,
        final boolean isBestaandPersoon)
    {
        final Timestamp timestamp = isBestaandPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        final BRPActie actieInhoud = maakBrpActie(persoon.getAdministratieveHandeling(), timestamp);
        final PersoonVoornaamHistorie historie = new PersoonVoornaamHistorie(persoonVoornaam, "Jan");

        historie.setActieInhoud(actieInhoud);
        historie.setDatumTijdRegistratie(actieInhoud.getDatumTijdRegistratie());
        historie.setDatumAanvangGeldigheid(DATUM_OUD);

        return historie;
    }

    private PersoonGeslachtsnaamcomponent maakPersoonGeslachtsnaamcomponent(final Persoon persoon, final boolean isBestaandPersoon) {
        final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(persoon, 1);
        final Set<PersoonGeslachtsnaamcomponentHistorie> historieSet = persoonGeslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet();
        historieSet.add(maakPersoonGeslachtsnaamcomponentHistorie(persoon, persoonGeslachtsnaamcomponent, isBestaandPersoon));

        if (!isBestaandPersoon) {
            final PersoonGeslachtsnaamcomponentHistorie historie = maakPersoonGeslachtsnaamcomponentHistorie(persoon, persoonGeslachtsnaamcomponent, true);
            historie.setDatumEindeGeldigheid(DATUM_NIEUW);
            historieSet.add(historie);
        }

        return persoonGeslachtsnaamcomponent;
    }

    private PersoonGeslachtsnaamcomponentHistorie maakPersoonGeslachtsnaamcomponentHistorie(
        final Persoon persoon,
        final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent,
        final boolean isBestaandPersoon)
    {
        final Timestamp timestamp = isBestaandPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        final BRPActie actieInhoud = maakBrpActie(persoon.getAdministratieveHandeling(), timestamp);

        final PersoonGeslachtsnaamcomponentHistorie historie = new PersoonGeslachtsnaamcomponentHistorie(persoonGeslachtsnaamcomponent, "Boon");
        historie.setActieInhoud(actieInhoud);
        historie.setDatumTijdRegistratie(actieInhoud.getDatumTijdRegistratie());
        historie.setDatumAanvangGeldigheid(DATUM_OUD);

        return historie;
    }

    private Set<PersoonNummerverwijzingHistorie> maakPersoonNummerverwijzingHistorieSet(final Persoon persoon, final boolean isBestaandPersoon) {
        final Set<PersoonNummerverwijzingHistorie> historieSet = new HashSet<>();
        if (!isBestaandPersoon) {
            historieSet.add(maakPersoonNummerverwijzingHistorie(persoon, false));
        }
        return historieSet;
    }

    protected PersoonNummerverwijzingHistorie maakPersoonNummerverwijzingHistorie(final Persoon persoon, final boolean isBestaandPersoon) {
        final Timestamp timestamp = isBestaandPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        final BRPActie actieInhoud = maakBrpActie(persoon.getAdministratieveHandeling(), timestamp);

        final PersoonNummerverwijzingHistorie historie = new PersoonNummerverwijzingHistorie(persoon);
        historie.setVorigeAdministratienummer(BESTAAND_ADMIN_NUMMER);
        historie.setDatumAanvangGeldigheid(DATUM_NIEUW);
        historie.setActieInhoud(actieInhoud);
        historie.setDatumTijdRegistratie(timestamp);

        return historie;
    }

    protected PersoonBijhoudingHistorie maakPersoonBijhoudingHistorie(
        final Persoon persoon,
        final Bijhoudingsaard nieuweBijhoudingsaard,
        final NadereBijhoudingsaard nieuweNadereBijhoudingsaard,
        final Partij partij,
        final String categorieNummer)
    {
        final PersoonBijhoudingHistorie persoonBijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, nieuweBijhoudingsaard, nieuweNadereBijhoudingsaard, false);
        final BRPActie actieInhoud = maakBrpActie(maakAdministratieveHandeling(DATUMTIJD_STEMPEL_NIEUW), DATUMTIJD_STEMPEL_NIEUW);
        actieInhoud.setLo3Voorkomen(maakLo3Voorkomen(categorieNummer));

        persoonBijhoudingHistorie.setActieInhoud(actieInhoud);
        return persoonBijhoudingHistorie;
    }

    protected void vulCat01VerschillenNieuweRijen(final Persoon kluizenaar, final Set<Verschil> verschillen) throws ReflectiveOperationException {
        final PersoonGeboorteHistorie persoonGeboorteHistorieNieuw = maakPersoonGeboorteHistorie(kluizenaar, false);
        vulRijBeeindigdEnToegevoegd(kluizenaar, null, verschillen, persoonGeboorteHistorieNieuw, "persoonGeboorteHistorieSet");

        final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorieNieuw = maakPersoonGeslachtsaanduidingHistorie(kluizenaar, false);
        vulRijBeeindigdEnToegevoegd(kluizenaar, null, verschillen, persoonGeslachtsaanduidingHistorieNieuw, "persoonGeslachtsaanduidingHistorieSet");

        final PersoonIDHistorie persoonIDHistorieNieuw = maakPersoonIDHistorie(kluizenaar, false);
        vulRijBeeindigdEnToegevoegd(kluizenaar, null, verschillen, persoonIDHistorieNieuw, "persoonIDHistorieSet");

        final PersoonSamengesteldeNaamHistorie persoonSamengesteldeNaamHistorieNieuw = maakPersoonSamengesteldeNaamHistorie(kluizenaar, false);
        vulRijBeeindigdEnToegevoegd(kluizenaar, null, verschillen, persoonSamengesteldeNaamHistorieNieuw, "persoonSamengesteldeNaamHistorieSet");

        final PersoonVoornaam persoonVoornaam = maakPersoonVoornaam(kluizenaar, false);
        final PersoonVoornaamHistorie persoonVoornaamHistorieNieuw = persoonVoornaam.getPersoonVoornaamHistorieSet().iterator().next();
        vulRijBeeindigdEnToegevoegd(
            persoonVoornaam,
            new EntiteitSleutel(Persoon.class, "persoonVoornaamSet", null),
            verschillen,
            persoonVoornaamHistorieNieuw,
            "persoonVoornaamHistorieSet");

        final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent = maakPersoonGeslachtsnaamcomponent(kluizenaar, false);
        final PersoonGeslachtsnaamcomponentHistorie persoonGeslachtsnaamcomponentHistorieNieuw =
                persoonGeslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet().iterator().next();
        vulRijBeeindigdEnToegevoegd(
            persoonGeslachtsnaamcomponent,
            new EntiteitSleutel(Persoon.class, "persoonGeslachtsnaamcomponentSet", null),
            verschillen,
            persoonGeslachtsnaamcomponentHistorieNieuw,
            "persoonGeslachtsnaamcomponentHistorieSet");
    }

    private void vulRijBeeindigdEnToegevoegd(
        final DeltaEntiteit deltaEntiteit,
        final EntiteitSleutel eigenaarSleutel,
        final Set<Verschil> verschillen,
        final DeltaEntiteit nieuweHistorieRij,
        final String historieSetVeld) throws ReflectiveOperationException
    {
        verschillen.add(
            new Verschil(
                SleutelUtil.maakRijSleutel(deltaEntiteit, eigenaarSleutel, nieuweHistorieRij, historieSetVeld),
                null,
                nieuweHistorieRij,
                VerschilType.RIJ_TOEGEVOEGD,
                null,
                null));
        final EntiteitSleutel historieSetSleutel = new EntiteitSleutel(deltaEntiteit.getClass(), historieSetVeld, eigenaarSleutel);
        final String eindeVeldNaam;
        final Object eindeVeldWaarde;
        if (nieuweHistorieRij instanceof MaterieleHistorie) {
            eindeVeldNaam = "datumEindeGeldigheid";
            eindeVeldWaarde = BrpMapperUtil.mapBrpDatumTijd(DATUMTIJD_STEMPEL_NIEUW).converteerNaarLo3Datum().getIntegerWaarde();
        } else {
            eindeVeldNaam = "datumTijdVerval";
            eindeVeldWaarde = DATUMTIJD_STEMPEL_NIEUW;
        }
        final EntiteitSleutel eindeVeldSleutel = new EntiteitSleutel(nieuweHistorieRij.getClass(), eindeVeldNaam, historieSetSleutel);
        eindeVeldSleutel.addSleuteldeel("tsreg", DATUMTIJD_STEMPEL_OUD);
        if (nieuweHistorieRij instanceof MaterieleHistorie) {
            eindeVeldSleutel.addSleuteldeel("dataanvgel", 20120101);
        }
        if (deltaEntiteit instanceof PersoonVoornaam || deltaEntiteit instanceof PersoonGeslachtsnaamcomponent) {
            historieSetSleutel.addSleuteldeel(VOLGNUMMER, 1);
        }
        verschillen.add(new Verschil(eindeVeldSleutel, null, eindeVeldWaarde, VerschilType.ELEMENT_NIEUW, null, null));
    }

    protected void voegIstStapelVoorkomenToe(final Stapel stapel, final boolean isMan, final int volgnr) {
        voegIstStapelVoorkomenToe(stapel, isMan, volgnr, false);
    }

    protected void voegIstStapelVoorkomenToe(final Stapel stapel, final boolean isMan, final int volgnr, final boolean isOnjuist) {
        final Partij partij = maakPartij();
        final Gemeente gemeente = maakGemeente(partij);
        final LandOfGebied landOfGebied = maakLandOfGebied();

        final StapelVoorkomen voorkomen = new StapelVoorkomen(stapel, volgnr, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_NIEUW));

        if (isMan) {
            voorkomen.setAnummer(1234567890L);
            voorkomen.setBsn(987654321);
            voorkomen.setAktenummer("A1 234");
            voorkomen.setDatumGeboorte(20150101);
            voorkomen.setGemeenteGeboorte(gemeente);
            voorkomen.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
            voorkomen.setGeslachtsnaamstam("Puk");
            voorkomen.setLandOfGebiedGeboorte(landOfGebied);
            voorkomen.setPartij(partij);
            voorkomen.setRubriek8510IngangsdatumGeldigheid(20120101);
            voorkomen.setRubriek8610DatumVanOpneming(20120102);
            voorkomen.setVoornamen("Pietje");
        } else {
            voorkomen.setAnummer(1236547890L);
            voorkomen.setBsn(789654123);
            voorkomen.setAktenummer("A1 789");
            voorkomen.setDatumGeboorte(20120101);
            voorkomen.setGemeenteGeboorte(gemeente);
            voorkomen.setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);
            voorkomen.setGeslachtsnaamstam("Puk");
            voorkomen.setLandOfGebiedGeboorte(landOfGebied);
            voorkomen.setPartij(partij);
            voorkomen.setRubriek8510IngangsdatumGeldigheid(20120101);
            voorkomen.setRubriek8610DatumVanOpneming(20120102);
            voorkomen.setVoornamen("Plien");
        }

        if (isOnjuist) {
            voorkomen.setRubriek8410OnjuistOfStrijdigOpenbareOrde('O');
        }
        stapel.addStapelVoorkomen(voorkomen);
    }

    protected void voegIstStapelRelatieToe(final Stapel stapel, final SoortRelatie soortRelatie) {
        final Relatie relatie = new Relatie(soortRelatie);
        switch (soortRelatie) {
            case HUWELIJK:
                relatie.setDatumAanvang(20120101);
                break;
            case GEREGISTREERD_PARTNERSCHAP:
                relatie.setDatumAanvang(20150101);
                break;
            default:
                relatie.setDatumAanvang(null);
        }
        stapel.addRelatie(relatie);
    }

    private Gemeente maakGemeente(final Partij partij) {
        return new Gemeente((short) 0, "Gemeente", (short) 1, partij);
    }
}
