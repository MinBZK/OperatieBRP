/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RegelverantwoordingHisVolledigImpl;

/**
 * Builder klasse voor Regelverantwoording.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class RegelverantwoordingHisVolledigImplBuilder {

    private RegelverantwoordingHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param actie actie van Regelverantwoording.
     * @param regel regel van Regelverantwoording.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public RegelverantwoordingHisVolledigImplBuilder(
        final ActieHisVolledigImpl actie,
        final Regel regel,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new RegelverantwoordingHisVolledigImpl(actie, new RegelAttribuut(regel));
        if (hisVolledigImpl.getRegel() != null) {
            hisVolledigImpl.getRegel().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param actie actie van Regelverantwoording.
     * @param regel regel van Regelverantwoording.
     */
    public RegelverantwoordingHisVolledigImplBuilder(final ActieHisVolledigImpl actie, final Regel regel) {
        this(actie, regel, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public RegelverantwoordingHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
