/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * Het type van een attribuut.
 */
@Entity
@DiscriminatorValue("AT")
public class AttribuutType extends Type {

    @ManyToOne
    @JoinColumn(name = "VERSIE")
    private Versie    versie;

    @ManyToOne
    @JoinColumn(name = "TYPE_")
    private BasisType type;

    @Column(name = "MAXIMUM_LENGTE")
    private Integer   maximumLengte;
    @Column(name = "MINIMUM_LENGTE")
    private Integer   minimumLengte;
    @Column(name = "AANTAL_DECIMALEN")
    private Integer   aantalDecimalen;

    /**
     * @return the aantalDecimalen
     */
    public Integer getAantalDecimalen() {
        return aantalDecimalen;
    }

    /**
     * @return the maximumLengte
     */
    public Integer getMaximumLengte() {
        return maximumLengte;
    }

    /**
     * @return the minimumLengte
     */
    public Integer getMinimumLengte() {
        return minimumLengte;
    }

    public BasisType getType() {
        return type;
    }

    public Versie getVersie() {
        return versie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAttribuutType() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isObjectType() {
        return false;
    }

    /**
     * @param versie the versie to set
     */
    public void setVersie(final Versie versie) {
        this.versie = versie;
    }
}
