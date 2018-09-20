/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonCacheHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;

/**
 * Builder klasse voor Persoon cache.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PersoonCacheHisVolledigImplBuilder {

    private PersoonCacheHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoon persoon van Persoon cache.
     */
    public PersoonCacheHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon) {
        this.hisVolledigImpl = new PersoonCacheHisVolledigImpl(persoon);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PersoonCacheHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
