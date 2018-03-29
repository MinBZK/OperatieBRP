/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;

/**
 * Globale interface voor de Data Access Layer dat de database-entities afschermt voor de client-code. Hierdoor kan er
 * in de rest van de migratie-code gebruik worden gemaakt van de migratiemodellen.
 */
public interface BrpDalService {

    /**
     * Vraag het laatst toegevoegde Lo3Bericht op voor het meegeven administratienummer.
     * @param administratienummer het *actuele* administratienummer van de op te zoeken persoon
     * @return De meeste recente Lo3Bericht of null als niet gevonden.
     */
    Lo3Bericht zoekLo3PeroonslijstBerichtOpAnummer(String administratienummer);

    /**
     * Zoekt de berichtLog administratienummers op voor de meegegeven criteria.<br/>
     * @param vanaf Datum tijd stempel ligt na vanaf.
     * @param tot Datum tijd stempel ligt voor tot.
     * @return Lijst met BerichtLogs.
     */
    Set<String> zoekBerichtLogAnrs(Date vanaf, Date tot);

    /**
     * Slaat een Lo3Bericht entiteit op in de BRP database. Als de Lo3Bericht entiteit is geassocieerd met een Persoon
     * dan dient deze als in de database te zijn opgeslagen.
     * @param bericht de Lo3Bericht
     * @return het Lo3Bericht object dat is opslagen in de database
     */
    Lo3Bericht persisteerLo3Bericht(Lo3Bericht bericht);

    /**
     * Slaat een Blokkering entiteit op in de BRP database.
     * @param blokkering de blokkering
     * @return het Blokkering object dat is opslagen in de database
     */
    Blokkering persisteerBlokkering(Blokkering blokkering);

    /**
     * Vraagt een Blokkering entiteit op uit de BRP database.
     * @param aNummer het aNummer van de PL die is geblokkeerd en waarvan we de blokkering willen opvragen.
     * @return het Blokkering object dat is opslagen in de database
     */
    Blokkering vraagOpBlokkering(String aNummer);

    /**
     * Verwijdert een Blokkering entiteit uit de BRP database.
     * @param blokkering de blokkering
     */
    void verwijderBlokkering(Blokkering blokkering);

    /**
     * Geef de lijst met gemeenten (en wanneer deze over (zijn ge)gaan naar het BRP stelsel.
     * @return De lijst met gemeenten.
     */
    Collection<Gemeente> geefAlleGemeenten();

    /**
     * Geef de lijst met partijen (en wanneer deze over (zijn ge)gaan naar het BRP stelsel.
     * @return De lijst met partijen.
     */
    Collection<Partij> geefAllePartijen();

    /**
     * Geef aprtij behorende bij de gegeven code.
     * @param partijCode partijCode van de gezochte partij
     * @return de gevonden partij
     */
    Partij geefPartij(BrpPartijCode partijCode);
}
