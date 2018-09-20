/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuder;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuderAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.verconv.LO3AanduidingOuderHisVolledigImpl;

/**
 * Builder klasse voor LO3 Aanduiding Ouder.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class LO3AanduidingOuderHisVolledigImplBuilder {

    private LO3AanduidingOuderHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param ouder ouder van LO3 Aanduiding Ouder.
     * @param soort soort van LO3 Aanduiding Ouder.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public LO3AanduidingOuderHisVolledigImplBuilder(
        final OuderHisVolledigImpl ouder,
        final LO3SoortAanduidingOuder soort,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new LO3AanduidingOuderHisVolledigImpl(ouder, new LO3SoortAanduidingOuderAttribuut(soort));
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param ouder ouder van LO3 Aanduiding Ouder.
     * @param soort soort van LO3 Aanduiding Ouder.
     */
    public LO3AanduidingOuderHisVolledigImplBuilder(final OuderHisVolledigImpl ouder, final LO3SoortAanduidingOuder soort) {
        this(ouder, soort, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public LO3AanduidingOuderHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
