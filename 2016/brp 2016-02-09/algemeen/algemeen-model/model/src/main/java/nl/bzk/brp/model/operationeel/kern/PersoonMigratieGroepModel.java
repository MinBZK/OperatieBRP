/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonMigratieGroep;


/**
 *
 *
 */
@Embeddable
public class PersoonMigratieGroepModel extends AbstractPersoonMigratieGroepModel implements PersoonMigratieGroep {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonMigratieGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param soortMigratie                  soortMigratie van Migratie.
     * @param redenWijzigingMigratie         redenWijzigingMigratie van Migratie.
     * @param aangeverMigratie               aangeverMigratie van Migratie.
     * @param landGebiedMigratie             landGebiedMigratie van Migratie.
     * @param buitenlandsAdresRegel1Migratie buitenlandsAdresRegel1Migratie van Migratie.
     * @param buitenlandsAdresRegel2Migratie buitenlandsAdresRegel2Migratie van Migratie.
     * @param buitenlandsAdresRegel3Migratie buitenlandsAdresRegel3Migratie van Migratie.
     * @param buitenlandsAdresRegel4Migratie buitenlandsAdresRegel4Migratie van Migratie.
     * @param buitenlandsAdresRegel5Migratie buitenlandsAdresRegel5Migratie van Migratie.
     * @param buitenlandsAdresRegel6Migratie buitenlandsAdresRegel6Migratie van Migratie.
     */
    public PersoonMigratieGroepModel(final SoortMigratieAttribuut soortMigratie,
        final RedenWijzigingVerblijfAttribuut redenWijzigingMigratie, final AangeverAttribuut aangeverMigratie,
        final LandGebiedAttribuut landGebiedMigratie, final AdresregelAttribuut buitenlandsAdresRegel1Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel2Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel3Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel4Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel5Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel6Migratie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(soortMigratie, redenWijzigingMigratie, aangeverMigratie, landGebiedMigratie,
            buitenlandsAdresRegel1Migratie, buitenlandsAdresRegel2Migratie, buitenlandsAdresRegel3Migratie,
            buitenlandsAdresRegel4Migratie, buitenlandsAdresRegel5Migratie, buitenlandsAdresRegel6Migratie);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonMigratieGroep te kopieren groep.
     */
    public PersoonMigratieGroepModel(final PersoonMigratieGroep persoonMigratieGroep) {
        super(persoonMigratieGroep);
    }

}
