/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import java.util.List;

/**
 * Persoon Identifier repository voor ophalen van persoon identifiers.
 */
public interface PersoonIdRepository {

    /**
     * Haalt alle persoon ids uit de database.
     *
     * @return de lijst met persoon identifiers
     */
    List<Integer> vindAllePersoonIds();

    /**
     * Haalt persoon id's op uit de database vanaf een bepaald id, tot een bepaald id.
     * @param vanafId Het vanaf id, alles BOVEN dit id zal worden opgehaald.
     * @param aantalIds Het max aantal ids dat opgehaald zal worden.
     * @return De lijst met id's.
     */
    List<Integer> vindPersoonIds(int vanafId, int aantalIds);


    /**
     * Haalt de bijgehouden persoon ids uit de database voor gevraagde aantal laatste uren.
     *
     * @param uren Het aantal uren in verleden waarvoor bijgehouden personen dienen te worden opgehaald
     * @return de lijst met persoon identifiers
     */
    List<Integer> vindBijgehoudenPersoonIds(final Integer uren);
}
