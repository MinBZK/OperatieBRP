/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.repositories.BasisRepository;

/**
 * CRUD functionaliteit voor de {@link Persoon} entiteit.
 */
public interface PersoonRepository extends BasisRepository<Persoon, Long> {

    /**
     * Geeft aan of de persoon geblokkeerd is door een verhuizing.
     * @param persoon de persoon die mogelijke geblokkeerd is
     * @return true als de persoon geblokkeerd is.
     */
    boolean isPersoonGeblokkeerd(Persoon persoon);

    /**
     * Zoekt naar {@link SoortPersoon#PSEUDO_PERSOON} personen waarbij het administratienummer of burgerservicenummer overeenkomen.
     *
     * @param administratienummer het administratienummer
     * @param burgerservicenummer het burgerservicenummer
     * @return de lijst met gevonden {@link SoortPersoon#PSEUDO_PERSOON} personen
     */
    List<Persoon> zoekGerelateerdePseudoPersonen(String administratienummer, String burgerservicenummer);

    /**
     * Controleert of het burgerservicenummer niet voorkomt bij andere personen die ingeschreven zijn en niet de nadere bijhoudingsaard FOUT hebben.
     * @param burgerservicenummer het BSN
     * @return true als het opgegeven BSN bij andere personen voorkomt, die ingeschreven zijn en niet de nadere bijhoudingsaard FOUT hebben.
     */
    boolean komtBsnReedsVoor(final String burgerservicenummer);

    /**
     * Controleert of het burgerservicenummer niet voorkomt bij andere personen die ingeschreven zijn en niet de nadere bijhoudingsaard FOUT hebben.
     * @param administratieNr het Administratienummer
     * @return true als het opgegeven administratienummer bij andere personen voorkomt, die ingeschreven zijn en niet de nadere bijhoudingsaard FOUT hebben.
     */
    boolean komtAdministratienummerReedsVoor(final String administratieNr);

    /**
     * Slaat de persoon entiteit plus de blob op de in de database.
     * @param persoon Persoon.
     */
    void slaPersoonOp(Persoon persoon);

}
