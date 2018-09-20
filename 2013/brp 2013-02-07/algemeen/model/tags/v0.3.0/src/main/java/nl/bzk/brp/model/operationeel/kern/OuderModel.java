/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractOuderModel;


/**
 * De betrokkenheid van een persoon in de rol van Ouder in een Familierechtelijke betrekking.
 *
 *
 *
 */
@Entity
@DiscriminatorValue(value = "2")
public class OuderModel extends AbstractOuderModel implements Ouder {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected OuderModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Ouder.
     * @param persoon persoon van Ouder.
     */
    public OuderModel(final RelatieModel relatie, final PersoonModel persoon) {
        super(relatie, persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param ouder Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public OuderModel(final Ouder ouder, final RelatieModel relatie, final PersoonModel persoon) {
        super(ouder, relatie, persoon);
    }

    /** {@inheritDoc} */
    @Override
    public FamilierechtelijkeBetrekkingModel getRelatie() {
        // Een ouder zit altijd in een FamilierechtelijkeBetrekking relatie.
        return (FamilierechtelijkeBetrekkingModel) super.getRelatie();
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param relatie relatie die gezet wordt.
     */
    protected void setRelatie(final FamilierechtelijkeBetrekkingModel relatie) {
    }

}
