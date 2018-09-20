/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonMigratieGroep;
import nl.bzk.brp.model.logisch.kern.PersoonMigratieGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersMigratie")
@GroepAccessor(dbObjectId = 3790)
public class HisPersoonMigratieModel extends AbstractHisPersoonMigratieModel implements ModelIdentificeerbaar<Integer>,
    HisPersoonMigratieGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonMigratieModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep              groep
     * @param historie           historie
     * @param actieInhoud        Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonMigratieModel(final PersoonHisVolledig persoonHisVolledig, final PersoonMigratieGroep groep,
        final MaterieleHistorie historie, final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPersoonMigratieModel(final AbstractHisPersoonMigratieModel kopie) {
        super(kopie);
    }

    /**
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoon                        persoon van HisPersoonMigratieModel
     * @param soortMigratie                  soortMigratie van HisPersoonMigratieModel
     * @param redenWijzigingMigratie         redenWijzigingMigratie van HisPersoonMigratieModel
     * @param aangeverMigratie               aangeverMigratie van HisPersoonMigratieModel
     * @param landGebiedMigratie             landGebiedMigratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel1Migratie buitenlandsAdresRegel1Migratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel2Migratie buitenlandsAdresRegel2Migratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel3Migratie buitenlandsAdresRegel3Migratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel4Migratie buitenlandsAdresRegel4Migratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel5Migratie buitenlandsAdresRegel5Migratie van HisPersoonMigratieModel
     * @param buitenlandsAdresRegel6Migratie buitenlandsAdresRegel6Migratie van HisPersoonMigratieModel
     * @param actieInhoud                    Actie inhoud; de actie die leidt tot dit nieuwe record.
     * @param historie                       De groep uit een bericht
     */
    public HisPersoonMigratieModel(final PersoonHisVolledig persoon, final SoortMigratieAttribuut soortMigratie,
        final RedenWijzigingVerblijfAttribuut redenWijzigingMigratie, final AangeverAttribuut aangeverMigratie,
        final LandGebiedAttribuut landGebiedMigratie, final AdresregelAttribuut buitenlandsAdresRegel1Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel2Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel3Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel4Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel5Migratie,
        final AdresregelAttribuut buitenlandsAdresRegel6Migratie, final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(persoon, soortMigratie, redenWijzigingMigratie, aangeverMigratie, landGebiedMigratie,
            buitenlandsAdresRegel1Migratie, buitenlandsAdresRegel2Migratie, buitenlandsAdresRegel3Migratie,
            buitenlandsAdresRegel4Migratie, buitenlandsAdresRegel5Migratie, buitenlandsAdresRegel6Migratie,
            actieInhoud, historie);
    }

}
