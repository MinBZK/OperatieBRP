/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PartijFiatteringsuitzonderingHisVolledigImpl;

/**
 * Builder klasse voor Partij \ Fiatteringsuitzondering.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class PartijFiatteringsuitzonderingHisVolledigImplBuilder {

    private PartijFiatteringsuitzonderingHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param partij partij van Partij \ Fiatteringsuitzondering.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public PartijFiatteringsuitzonderingHisVolledigImplBuilder(final Partij partij, final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new PartijFiatteringsuitzonderingHisVolledigImpl(new PartijAttribuut(partij));
        if (hisVolledigImpl.getPartij() != null) {
            hisVolledigImpl.getPartij().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij partij van Partij \ Fiatteringsuitzondering.
     */
    public PartijFiatteringsuitzonderingHisVolledigImplBuilder(final Partij partij) {
        this(partij, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public PartijFiatteringsuitzonderingHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
