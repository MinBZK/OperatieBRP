/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.lev;

import javax.annotation.Generated;
import nl.bzk.brp.model.hisvolledig.impl.lev.LeveringHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.lev.LeveringPersoonHisVolledigImpl;

/**
 * Builder klasse voor Levering \ Persoon.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class LeveringPersoonHisVolledigImplBuilder {

    private LeveringPersoonHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param levering levering van Levering \ Persoon.
     * @param persoonId persoonId van Levering \ Persoon.
     */
    public LeveringPersoonHisVolledigImplBuilder(final LeveringHisVolledigImpl levering, final Integer persoonId) {
        this.hisVolledigImpl = new LeveringPersoonHisVolledigImpl(levering, persoonId);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public LeveringPersoonHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
