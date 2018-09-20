/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess;

import nl.bzk.brp.poc.berichtenverwerker.model.Actie;
import nl.bzk.brp.poc.berichtenverwerker.model.Hispersnation;
import nl.bzk.brp.poc.berichtenverwerker.model.Persnation;


/**
 * Data Access Object voor het vinden, ophalen en aanpassen van de nationaliteit van een persoon.
 */
public interface PersoonsNationaliteitDAO {

    /**
     * Haalt de {@link Persnation} op op basis van de opgegeven id.
     *
     * @param id id van de persoonsnationaliteit.
     * @return de gevonden persoonsnationaliteit.
     */
    Persnation vindPersoonsNationaliteitOpBasisVanId(long id);

    /**
     * Voegt de opgegeven nationaliteit toe aan de betreffende persoon. Tevens wordt er een nieuwe
     * {@link Hispersnation} gecreeerd en opgeslagen.
     *
     * @param persoonsNationaliteit de persoons nationaliteit die moet worden toegevoegd.
     */
    void voegToePersoonsNationaliteit(Persnation persoonsNationaliteit);

    /**
     * Verwijdert de opgegeven nationaliteit van een persoon. Tevens wordt de betreffende historie
     * middels {@link Hispersnation} bijgewerkt.
     *
     * @param actie de actie waar binnen de persoons nationaliteit wordt verwijderd.
     * @param persoonsNationaliteit de persoons nationaliteit die moet worden verwijderd.
     */
    void verwijderPersoonsNationaliteit(Actie actie, Persnation persoonsNationaliteit);

}
