/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.ist;

import javax.annotation.Generated;
import nl.bzk.brp.model.hisvolledig.impl.ist.StapelHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.ist.StapelRelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;

/**
 * Builder klasse voor Stapel \ Relatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class StapelRelatieHisVolledigImplBuilder {

    private StapelRelatieHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param stapel stapel van Stapel \ Relatie.
     * @param relatie relatie van Stapel \ Relatie.
     */
    public StapelRelatieHisVolledigImplBuilder(final StapelHisVolledigImpl stapel, final RelatieHisVolledigImpl relatie) {
        this.hisVolledigImpl = new StapelRelatieHisVolledigImpl(stapel, relatie);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param relatie relatie van Stapel \ Relatie.
     */
    public StapelRelatieHisVolledigImplBuilder(final RelatieHisVolledigImpl relatie) {
        this.hisVolledigImpl = new StapelRelatieHisVolledigImpl(null, relatie);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public StapelRelatieHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
