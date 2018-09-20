/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.ber.BerichtHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.ber.BerichtPersoonHisVolledigImpl;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Bericht.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class BerichtHisVolledigImplBuilder {

    private BerichtHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param soort soort van Bericht.
     * @param richting richting van Bericht.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public BerichtHisVolledigImplBuilder(final SoortBericht soort, final Richting richting, final boolean defaultMagGeleverdWordenVoorAttributen) {
        this.hisVolledigImpl = new BerichtHisVolledigImpl(new SoortBerichtAttribuut(soort), new RichtingAttribuut(richting));
        if (hisVolledigImpl.getSoort() != null) {
            hisVolledigImpl.getSoort().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getRichting() != null) {
            hisVolledigImpl.getRichting().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Bericht.
     * @param richting richting van Bericht.
     */
    public BerichtHisVolledigImplBuilder(final SoortBericht soort, final Richting richting) {
        this(soort, richting, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public BerichtHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Voeg een Bericht \ Persoon toe. Zet tevens de back-reference van Bericht \ Persoon.
     *
     * @param berichtPersoon een Bericht \ Persoon
     * @return his volledig builder
     */
    public BerichtHisVolledigImplBuilder voegBerichtPersoonToe(final BerichtPersoonHisVolledigImpl berichtPersoon) {
        this.hisVolledigImpl.getPersonen().add(berichtPersoon);
        ReflectionTestUtils.setField(berichtPersoon, "bericht", this.hisVolledigImpl);
        return this;
    }

}
