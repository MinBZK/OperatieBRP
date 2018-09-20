/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.prot;

import javax.annotation.Generated;
import nl.bzk.brp.model.hisvolledig.impl.prot.LeveringsaantekeningHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.prot.LeveringsaantekeningPersoonHisVolledigImpl;

/**
 * Builder klasse voor Leveringsaantekening \ Persoon.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class LeveringsaantekeningPersoonHisVolledigImplBuilder {

    private LeveringsaantekeningPersoonHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param leveringsaantekening leveringsaantekening van Leveringsaantekening \ Persoon.
     * @param persoonId persoonId van Leveringsaantekening \ Persoon.
     */
    public LeveringsaantekeningPersoonHisVolledigImplBuilder(final LeveringsaantekeningHisVolledigImpl leveringsaantekening, final Integer persoonId) {
        this.hisVolledigImpl = new LeveringsaantekeningPersoonHisVolledigImpl(leveringsaantekening, persoonId);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public LeveringsaantekeningPersoonHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
