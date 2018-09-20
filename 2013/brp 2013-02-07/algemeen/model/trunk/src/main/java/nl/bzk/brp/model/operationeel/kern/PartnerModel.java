/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import nl.bzk.brp.model.logisch.kern.Partner;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPartnerModel;


/**
 * De betrokkenheid van een persoon in de rol van Partner in een Huwelijk/Geregistreerd partnerschap.
 *
 *
 *
 */
@Entity
@DiscriminatorValue(value = "3")
public class PartnerModel extends AbstractPartnerModel implements Partner {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PartnerModel() {
        super();
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param partner Te kopieren object type.
     * @param relatie Bijbehorende Relatie.
     * @param persoon Bijbehorende Persoon.
     */
    public PartnerModel(final Partner partner, final RelatieModel relatie, final PersoonModel persoon) {
        super(partner, relatie, persoon);
    }

    /**
     * Geeft de HuwelijkModel terug of null als het geen huwelijkRelatie heeft.
     *
     * @return het model
     */
    public HuwelijkModel getHuwelijkRelatie() {
        if (getRelatie() instanceof HuwelijkModel) {
            return (HuwelijkModel) getRelatie();
        } else {
            return null;
        }
    }

    /**
     * Geeft de GeregistreerdPartnerschapModel terug of null als het geen partnerschap heeft.
     *
     * @return het model
     */
    public GeregistreerdPartnerschapModel getPartnerRelatie() {
        if (getRelatie() instanceof GeregistreerdPartnerschapModel) {
            return (GeregistreerdPartnerschapModel) getRelatie();
        } else {
            return null;
        }
    }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param relatie de relatie.
     */
    protected void setHuwelijkRelatie(final HuwelijkModel relatie) { }

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param relatie de relatie.
     */
    protected void setPartnerRelatie(final GeregistreerdPartnerschapModel relatie) { }
}
