/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity.Blokkering;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.ToegangLeveringsAutorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;

/**
 * Globale interface voor de Data Access Layer dat de database-entities afschermt voor de client-code. Hierdoor kan er
 * in de rest van de migratie-code gebruik worden gemaakt van de migratiemodellen.
 */
public interface BrpDalService {
    /**
     * Slaat een persoonslijst op in de BRP database.
     *
     * @param brpPersoonslijst
     *            De persoonslijst in het BRP-migratiemodelformaat
     * @param lo3Bericht
     *            lo3 bericht waarop de persoonlijst is gebaseerd
     * @return een {@link nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat}
     */
    PersoonslijstPersisteerResultaat persisteerPersoonslijst(BrpPersoonslijst brpPersoonslijst, final Lo3Bericht lo3Bericht);

    /**
     * Slaat een persoonslijst op in de BRP database waarbij sprake is van een a-nummer wijziging. Omdat de persoon met
     * het meegegeven a-nummer wordt vervangen mag het a-nummer van de nieuwe persoon niet bestaan en MOET het oude
     * a-nummer wel bestaan.
     *
     * @param brpPersoonslijst
     *            De persoonslijst in het BRP-migratiemodelformaat
     * @param aNummerTeVervangenPersoon
     *            het a-nummer van de te vervangen persoon
     * @param isANummerWijziging
     *            geeft aan of deze persoonslijst update als a-nummer wijziging moet worden behandeld
     * @param lo3Bericht
     *            lo3 bericht waarop de persoonslijst is gebaseerd
     * @return een {@link nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat}
     */
    PersoonslijstPersisteerResultaat persisteerPersoonslijst(
        BrpPersoonslijst brpPersoonslijst,
        Long aNummerTeVervangenPersoon,
        boolean isANummerWijziging,
        final Lo3Bericht lo3Bericht);

    /**
     * Vraag een persoonslijst op uit de BRP database voor het gegeven admnistratienummer, deze persoon moet bestaan
     * anders treedt er een fout op.
     *
     * @param administratienummer
     *            het *actuele* administratienummer van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat.
     * @throws IllegalStateException
     *             wanneer geen persoon gevonden is
     */
    BrpPersoonslijst bevraagPersoonslijst(long administratienummer);

    /**
     * Vraag een persoonslijst op uit de BRP database voor de meegegeven technische sleutel, deze persoon moet bestaan
     * anders treedt er een fout op.
     *
     * @param techischesleutel
     *            de technische sleutel van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat.
     * @throws IllegalStateException
     *             wanneer geen persoon gevonden is
     */
    BrpPersoonslijst bevraagPersoonslijstOpTechnischeSleutel(Long techischesleutel);

    /**
     * Zoek een persoonslijst op in de BRP database voor het meegegeven administratienummer.
     *
     * @param administratienummer
     *            het *actuele* administratienummer van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat of null als niet gevonden.
     */
    BrpPersoonslijst zoekPersoonOpAnummer(long administratienummer);

    /**
     * Zoek een persoonslijst op in de BRP database m.u.v. foutief opgeschorte (reden 'F' en 'W') voor het meegegeven
     * administratienummer.
     *
     * @param administratienummer
     *            het *actuele* administratienummer van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat of null als niet gevonden.
     */
    BrpPersoonslijst zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(long administratienummer);

    /**
     * Zoek persoonslijsten op in de BRP database m.u.v. foutief opgeschorte (reden 'F' en 'W') voor het meegegeven
     * administratienummer.
     *
     * @param administratienummer
     *            het *actuele* administratienummer van de op te zoeken personen
     * @return De personen in het BRP-migratiemodelformaat of null als er geen gevonden zijn.
     */
    List<Persoon> zoekPersonenOpAnummerFoutiefOpgeschortUitsluiten(long administratienummer);

    /**
     * Zoek een persoonslijst op in de BRP database voor het meegegeven administratienummer.
     *
     * @param administratienummer
     *            het *historische* administratienummer van de op te zoeken persoon
     * @return De persoonslijst in het BRP-migratiemodelformaat of null als niet gevonden.
     */
    BrpPersoonslijst zoekPersoonOpHistorischAnummer(long administratienummer);

    /**
     * Zoek persoonslijsten in de BRP database voor het meegegeven administratienummer.
     *
     * @param administratienummer
     *            het *historische* administratienummer van de op te zoeken persoon
     * @return De persoonslijsten in het BRP-migratiemodelformaat (lege lijst als niets gevonden)
     */
    List<BrpPersoonslijst> zoekPersonenOpHistorischAnummer(final long administratienummer);

    /**
     * Vraag het laatst toegevoegde Lo3Bericht op voor het meegeven administratienummer.
     *
     * @param administratienummer
     *            het *actuele* administratienummer van de op te zoeken persoon
     * @return De meeste recente Lo3Bericht of null als niet gevonden.
     */
    Lo3Bericht zoekLo3PeroonslijstBerichtOpAnummer(long administratienummer);

    /**
     * Zoekt de berichtLog administratienummers op voor de meegegeven criteria.<br/>
     *
     * @param vanaf
     *            Datum tijd stempel ligt na vanaf.
     * @param tot
     *            Datum tijd stempel ligt voor tot.
     * @return Lijst met BerichtLogs.
     */
    Set<Long> zoekBerichtLogAnrs(Date vanaf, Date tot);

    /**
     * Slaat een Lo3Bericht entiteit op in de BRP database. Als de Lo3Bericht entiteit is geassocieerd met een Persoon
     * dan dient deze als in de database te zijn opgeslagen.
     *
     * @param bericht
     *            de Lo3Bericht
     * @return het Lo3Bericht object dat is opslagen in de database
     */
    Lo3Bericht persisteerLo3Bericht(Lo3Bericht bericht);

    /**
     * Slaat een Blokkering entiteit op in de BRP database.
     *
     * @param blokkering
     *            de blokkering
     * @return het Blokkering object dat is opslagen in de database
     */
    Blokkering persisteerBlokkering(Blokkering blokkering);

    /**
     * Vraagt een Blokkering entiteit op uit de BRP database.
     *
     * @param aNummer
     *            het aNummer van de PL die is geblokkeerd en waarvan we de blokkering willen opvragen.
     * @return het Blokkering object dat is opslagen in de database
     */
    Blokkering vraagOpBlokkering(Long aNummer);

    /**
     * Verwijdert een Blokkering entiteit uit de BRP database.
     *
     * @param blokkering
     *            de blokkering
     */
    void verwijderBlokkering(Blokkering blokkering);

    /**
     * Zoekt naar personen in de BRP database die voldoen aan de gegevens zoals meegegeven in het Persoon-object.
     *
     * @param persoon
     *            De persoongegevens waarop wordt gezocht.
     * @return lijst van gevonden personen die voldoen aan de meegegeven zoekargumenten.
     */
    List<Persoon> zoekPersoon(Persoon persoon);

    /**
     * Zoekt op basis van de meegegeven actuele gegevens personen in de database.
     *
     * @param administratienummer
     *            a-nummer
     * @param burgerservicenummer
     *            bsn
     * @param geslachtsnaamstam
     *            geslachtsnaam
     * @param postcode
     *            postcode
     * @return Lijst van gevonden personen
     */
    List<Persoon> zoekPersonenOpActueleGegevens(Long administratienummer, Integer burgerservicenummer, String geslachtsnaamstam, String postcode);

    /**
     * Zoekt op basis van de meegegeven historische gegevens personen in de database.
     *
     * @param administratienummer
     *            a-nummer
     * @param burgerservicenummer
     *            bsn
     * @param geslachtsnaamstam
     *            geslachtsnaam
     * @return Lijst van gevonden personen
     */
    List<Persoon> zoekPersonenOpHistorischeGegevens(Long administratienummer, Integer burgerservicenummer, String geslachtsnaamstam);

    /**
     * Geef de lijst met gemeenten (en wanneer deze over (zijn ge)gaan naar het BRP stelsel.
     *
     * @return De lijst met gemeenten.
     */
    Collection<Gemeente> geefAlleGemeenten();

    /**
     * Geef de lijst met alle actieve GBA leveringsautorisaties.
     *
     * @return De lijst met alle actieve GBA leveringsautorisaties.
     */
    Collection<Leveringsautorisatie> geefAlleGbaAutorisaties();

    /**
     * Slaat een autorisatie (partij, autoristiebesluit, abonnement) op in de BRP database.
     *
     * @param brpAutorisatie
     *            De autorisatie in het BRP-migratiemodelformaat.
     * @return de opgeslagen abonnementen
     */
    List<ToegangLeveringsAutorisatie> persisteerAutorisatie(BrpAutorisatie brpAutorisatie);

    /**
     * Vraag een autorisatie op uit de BRP database voor de gegeven partijCode, naam en ingangsDatumRegel, deze moet
     * bestaan anders treedt er een fout op.
     *
     * @param partijCode
     *            de partij waarvoor we de autorisatie opzoeken
     * @param naam
     *            de naam van het autorisatiebesluit
     * @param ingangsDatumRegel
     *            de ingangsdatum van de autorisatietabelregel
     * @return De autorisatie in het BRP-migratiemodelformaat.
     * @throws IllegalStateException
     *             als de autorisatie niet gevonden kan worden
     */
    BrpAutorisatie bevraagAutorisatie(final Integer partijCode, String naam, final Integer ingangsDatumRegel);

    /**
     * Slaat afnemersindicaties voor een persoon op in de BRP database, de persoon moet bestaan anders treedt er een
     * fout op.
     *
     * @param brpAfnemersindicaties
     *            de afnemers indicaties
     * @throws IllegalStateException
     *             wanneer de persoon van deze indicatie niet gevonden kan worden in de database
     */
    void persisteerAfnemersindicaties(BrpAfnemersindicaties brpAfnemersindicaties);

    /**
     * Lees afnemersindicaties voor een persoon uit de BRP database, de afnemersindicatie moet bestaan anders treedt er
     * en fout op.
     *
     * @param aNummer
     *            administratienummer van de persoon waarvoor de afnemersindicaties gelezen moeten worden
     * @return brpAfnemersindicaties de afnemers indicaties
     * @throws IllegalStateException
     *             wanneer de afnemersindicatie niet gevonden kan worden voor het gegeven administratienummer
     */
    BrpAfnemersindicaties bevraagAfnemersindicaties(Long aNummer);

    /**
     * @return syncparameters
     */
    SyncParameters getSyncParameters();
}
