/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.dataaccess;

import nl.bzk.brp.metaregister.model.BasisType;
import nl.bzk.brp.metaregister.model.TypeImpl;

import org.apache.commons.lang3.tuple.MutablePair;


/**
 * Een hulp klasse waarin een basis type en een type impl object zijn opgenomen.
 * Tevens geeft een instantie van deze klasse aan of er sprake was van een overrule.
 * Zie de gebruikende code voor meer informatie.
 */
public class BasisTypeOverrulePaar extends MutablePair<BasisType, TypeImpl> {

    private static final long serialVersionUID = 1L;

    private boolean overrule;

    /**
     * Maak een nieuw paar aan.
     *
     * @param basisType het basis type
     * @param typeImpl de type impl
     */
    public BasisTypeOverrulePaar(final BasisType basisType, final TypeImpl typeImpl) {
        super(basisType, typeImpl);
        this.overrule = false;
    }

    public boolean isOverrule() {
        return overrule;
    }

    public void setOverrule(final boolean overrule) {
        this.overrule = overrule;
    }

    public BasisType getBasisType() {
        return this.getLeft();
    }

    public TypeImpl getTypeImpl() {
        return this.getRight();
    }

}
