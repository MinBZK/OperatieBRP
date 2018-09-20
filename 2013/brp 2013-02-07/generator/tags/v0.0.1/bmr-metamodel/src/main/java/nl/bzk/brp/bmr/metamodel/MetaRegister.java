/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import java.util.ArrayList;
import java.util.List;


public class MetaRegister {

    private List<BasisType> basisTypen = new ArrayList<BasisType>();
    private List<Domein>    domeinen   = new ArrayList<Domein>();

    /**
     * @return the basisTypen
     */
    public List<BasisType> getBasisTypen() {
        return basisTypen;
    }

    /**
     * @param basisType the basisType to set
     */
    public void addBasisType(final BasisType basisType) {
        this.basisTypen.add(basisType);
    }

    /**
     * @param domein the basisTypen to set
     */
    public void addDomein(final Domein domein) {
        this.domeinen.add(domein);
    }

    /**
     * @return the domeinen
     */
    public List<Domein> getDomeinen() {
        return domeinen;
    }
}
