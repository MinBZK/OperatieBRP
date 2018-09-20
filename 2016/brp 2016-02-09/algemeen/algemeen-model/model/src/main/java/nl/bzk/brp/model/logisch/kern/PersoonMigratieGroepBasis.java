/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonMigratieGroepBasis extends Groep {

    /**
     * Retourneert Soort migratie van Migratie.
     *
     * @return Soort migratie.
     */
    SoortMigratieAttribuut getSoortMigratie();

    /**
     * Retourneert Reden wijziging migratie van Migratie.
     *
     * @return Reden wijziging migratie.
     */
    RedenWijzigingVerblijfAttribuut getRedenWijzigingMigratie();

    /**
     * Retourneert Aangever migratie van Migratie.
     *
     * @return Aangever migratie.
     */
    AangeverAttribuut getAangeverMigratie();

    /**
     * Retourneert Land/gebied migratie van Migratie.
     *
     * @return Land/gebied migratie.
     */
    LandGebiedAttribuut getLandGebiedMigratie();

    /**
     * Retourneert Buitenlands adres regel 1 migratie van Migratie.
     *
     * @return Buitenlands adres regel 1 migratie.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel1Migratie();

    /**
     * Retourneert Buitenlands adres regel 2 migratie van Migratie.
     *
     * @return Buitenlands adres regel 2 migratie.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel2Migratie();

    /**
     * Retourneert Buitenlands adres regel 3 migratie van Migratie.
     *
     * @return Buitenlands adres regel 3 migratie.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel3Migratie();

    /**
     * Retourneert Buitenlands adres regel 4 migratie van Migratie.
     *
     * @return Buitenlands adres regel 4 migratie.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel4Migratie();

    /**
     * Retourneert Buitenlands adres regel 5 migratie van Migratie.
     *
     * @return Buitenlands adres regel 5 migratie.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel5Migratie();

    /**
     * Retourneert Buitenlands adres regel 6 migratie van Migratie.
     *
     * @return Buitenlands adres regel 6 migratie.
     */
    AdresregelAttribuut getBuitenlandsAdresRegel6Migratie();

}
