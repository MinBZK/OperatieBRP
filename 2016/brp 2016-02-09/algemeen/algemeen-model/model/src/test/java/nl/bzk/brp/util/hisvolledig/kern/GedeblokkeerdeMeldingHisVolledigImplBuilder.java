/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.GedeblokkeerdeMeldingHisVolledigImpl;

/**
 * Builder klasse voor Gedeblokkeerde melding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class GedeblokkeerdeMeldingHisVolledigImplBuilder {

    private GedeblokkeerdeMeldingHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param regel regel van Gedeblokkeerde melding.
     * @param melding melding van Gedeblokkeerde melding.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public GedeblokkeerdeMeldingHisVolledigImplBuilder(
        final Regel regel,
        final MeldingtekstAttribuut melding,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new GedeblokkeerdeMeldingHisVolledigImpl(new RegelAttribuut(regel), melding);
        if (hisVolledigImpl.getRegel() != null) {
            hisVolledigImpl.getRegel().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getMelding() != null) {
            hisVolledigImpl.getMelding().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param regel regel van Gedeblokkeerde melding.
     * @param melding melding van Gedeblokkeerde melding.
     */
    public GedeblokkeerdeMeldingHisVolledigImplBuilder(final Regel regel, final MeldingtekstAttribuut melding) {
        this(regel, melding, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public GedeblokkeerdeMeldingHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
