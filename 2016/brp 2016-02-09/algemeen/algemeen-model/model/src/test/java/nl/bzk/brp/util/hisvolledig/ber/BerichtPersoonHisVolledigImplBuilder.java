/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.hisvolledig.impl.ber.BerichtHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.ber.BerichtPersoonHisVolledigImpl;

/**
 * Builder klasse voor Bericht \ Persoon.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class BerichtPersoonHisVolledigImplBuilder {

    private BerichtPersoonHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param bericht bericht van Bericht \ Persoon.
     * @param persoonId persoonId van Bericht \ Persoon.
     */
    public BerichtPersoonHisVolledigImplBuilder(final BerichtHisVolledigImpl bericht, final Integer persoonId) {
        this.hisVolledigImpl = new BerichtPersoonHisVolledigImpl(bericht, persoonId);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoonId persoonId van Bericht \ Persoon.
     */
    public BerichtPersoonHisVolledigImplBuilder(final Integer persoonId) {
        this.hisVolledigImpl = new BerichtPersoonHisVolledigImpl(null, persoonId);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public BerichtPersoonHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
