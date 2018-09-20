/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import nl.bzk.brp.model.logisch.kern.Kind;


/**
 * De betrokkenheid van een persoon in de rol van Kind in een Familierechtelijke betrekking.
 */
@Entity
@DiscriminatorValue(value = "1")
public class KindModel extends AbstractKindModel implements Kind {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected KindModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Kind.
     * @param persoon persoon van Kind.
     */
    public KindModel(final RelatieModel relatie, final PersoonModel persoon) {
        super(relatie, persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param kind    Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public KindModel(final Kind kind, final RelatieModel relatie, final PersoonModel persoon) {
        super(kind, relatie, persoon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilierechtelijkeBetrekkingModel getRelatie() {
        // Geeft de FamilierechtelijkeBetrekking terug. Is de enige mogelijke betrokkenheid voor een Kind.
        return (FamilierechtelijkeBetrekkingModel) super.getRelatie();
    }

}
