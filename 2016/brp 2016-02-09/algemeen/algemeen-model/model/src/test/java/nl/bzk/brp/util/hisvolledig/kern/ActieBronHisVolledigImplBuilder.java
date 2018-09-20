/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rechtsgrond;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RechtsgrondAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieBronHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;

/**
 * Builder klasse voor Actie \ Bron.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class ActieBronHisVolledigImplBuilder {

    private ActieBronHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param actie actie van Actie \ Bron.
     * @param document document van Actie \ Bron.
     * @param rechtsgrond rechtsgrond van Actie \ Bron.
     * @param rechtsgrondomschrijving rechtsgrondomschrijving van Actie \ Bron.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public ActieBronHisVolledigImplBuilder(
        final ActieHisVolledigImpl actie,
        final DocumentHisVolledigImpl document,
        final Rechtsgrond rechtsgrond,
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new ActieBronHisVolledigImpl(actie, document, new RechtsgrondAttribuut(rechtsgrond), rechtsgrondomschrijving);
        if (hisVolledigImpl.getRechtsgrond() != null) {
            hisVolledigImpl.getRechtsgrond().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getRechtsgrondomschrijving() != null) {
            hisVolledigImpl.getRechtsgrondomschrijving().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param actie actie van Actie \ Bron.
     * @param document document van Actie \ Bron.
     * @param rechtsgrond rechtsgrond van Actie \ Bron.
     * @param rechtsgrondomschrijving rechtsgrondomschrijving van Actie \ Bron.
     */
    public ActieBronHisVolledigImplBuilder(
        final ActieHisVolledigImpl actie,
        final DocumentHisVolledigImpl document,
        final Rechtsgrond rechtsgrond,
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving)
    {
        this(actie, document, rechtsgrond, rechtsgrondomschrijving, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param document document van Actie \ Bron.
     * @param rechtsgrond rechtsgrond van Actie \ Bron.
     * @param rechtsgrondomschrijving rechtsgrondomschrijving van Actie \ Bron.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public ActieBronHisVolledigImplBuilder(
        final DocumentHisVolledigImpl document,
        final Rechtsgrond rechtsgrond,
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new ActieBronHisVolledigImpl(null, document, new RechtsgrondAttribuut(rechtsgrond), rechtsgrondomschrijving);
        if (hisVolledigImpl.getRechtsgrond() != null) {
            hisVolledigImpl.getRechtsgrond().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getRechtsgrondomschrijving() != null) {
            hisVolledigImpl.getRechtsgrondomschrijving().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param document document van Actie \ Bron.
     * @param rechtsgrond rechtsgrond van Actie \ Bron.
     * @param rechtsgrondomschrijving rechtsgrondomschrijving van Actie \ Bron.
     */
    public ActieBronHisVolledigImplBuilder(
        final DocumentHisVolledigImpl document,
        final Rechtsgrond rechtsgrond,
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving)
    {
        this(null, document, rechtsgrond, rechtsgrondomschrijving, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public ActieBronHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
