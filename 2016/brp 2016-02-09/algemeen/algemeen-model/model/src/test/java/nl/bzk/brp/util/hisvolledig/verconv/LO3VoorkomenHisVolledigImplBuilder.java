/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.verconv.LO3BerichtHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.verconv.LO3VoorkomenHisVolledigImpl;

/**
 * Builder klasse voor LO3 Voorkomen.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class LO3VoorkomenHisVolledigImplBuilder {

    private LO3VoorkomenHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param lO3Bericht lO3Bericht van LO3 Voorkomen.
     * @param lO3Categorie lO3Categorie van LO3 Voorkomen.
     * @param lO3Stapelvolgnummer lO3Stapelvolgnummer van LO3 Voorkomen.
     * @param lO3Voorkomenvolgnummer lO3Voorkomenvolgnummer van LO3 Voorkomen.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public LO3VoorkomenHisVolledigImplBuilder(
        final LO3BerichtHisVolledigImpl lO3Bericht,
        final LO3CategorieAttribuut lO3Categorie,
        final VolgnummerAttribuut lO3Stapelvolgnummer,
        final VolgnummerAttribuut lO3Voorkomenvolgnummer,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new LO3VoorkomenHisVolledigImpl(lO3Bericht, lO3Categorie, lO3Stapelvolgnummer, lO3Voorkomenvolgnummer);
        if (hisVolledigImpl.getLO3Categorie() != null) {
            hisVolledigImpl.getLO3Categorie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getLO3Stapelvolgnummer() != null) {
            hisVolledigImpl.getLO3Stapelvolgnummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getLO3Voorkomenvolgnummer() != null) {
            hisVolledigImpl.getLO3Voorkomenvolgnummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param lO3Bericht lO3Bericht van LO3 Voorkomen.
     * @param lO3Categorie lO3Categorie van LO3 Voorkomen.
     * @param lO3Stapelvolgnummer lO3Stapelvolgnummer van LO3 Voorkomen.
     * @param lO3Voorkomenvolgnummer lO3Voorkomenvolgnummer van LO3 Voorkomen.
     */
    public LO3VoorkomenHisVolledigImplBuilder(
        final LO3BerichtHisVolledigImpl lO3Bericht,
        final LO3CategorieAttribuut lO3Categorie,
        final VolgnummerAttribuut lO3Stapelvolgnummer,
        final VolgnummerAttribuut lO3Voorkomenvolgnummer)
    {
        this(lO3Bericht, lO3Categorie, lO3Stapelvolgnummer, lO3Voorkomenvolgnummer, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public LO3VoorkomenHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
