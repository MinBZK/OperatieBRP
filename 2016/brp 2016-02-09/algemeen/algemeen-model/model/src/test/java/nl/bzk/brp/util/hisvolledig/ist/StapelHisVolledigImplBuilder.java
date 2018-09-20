/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.ist;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.ist.StapelHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.ist.StapelRelatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.ist.StapelVoorkomenHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder klasse voor Stapel.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class StapelHisVolledigImplBuilder {

    private StapelHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param persoon persoon van Stapel.
     * @param categorie categorie van Stapel.
     * @param volgnummer volgnummer van Stapel.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public StapelHisVolledigImplBuilder(
        final PersoonHisVolledigImpl persoon,
        final LO3CategorieAttribuut categorie,
        final VolgnummerAttribuut volgnummer,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new StapelHisVolledigImpl(persoon, categorie, volgnummer);
        if (hisVolledigImpl.getCategorie() != null) {
            hisVolledigImpl.getCategorie().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getVolgnummer() != null) {
            hisVolledigImpl.getVolgnummer().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Stapel.
     * @param categorie categorie van Stapel.
     * @param volgnummer volgnummer van Stapel.
     */
    public StapelHisVolledigImplBuilder(final PersoonHisVolledigImpl persoon, final LO3CategorieAttribuut categorie, final VolgnummerAttribuut volgnummer)
    {
        this(persoon, categorie, volgnummer, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public StapelHisVolledigImpl build() {
        return hisVolledigImpl;
    }

    /**
     * Voeg een Stapel \ Relatie toe. Zet tevens de back-reference van Stapel \ Relatie.
     *
     * @param stapelRelatie een Stapel \ Relatie
     * @return his volledig builder
     */
    public StapelHisVolledigImplBuilder voegStapelRelatieToe(final StapelRelatieHisVolledigImpl stapelRelatie) {
        this.hisVolledigImpl.getStapelRelaties().add(stapelRelatie);
        ReflectionTestUtils.setField(stapelRelatie, "stapel", this.hisVolledigImpl);
        return this;
    }

    /**
     * Voeg een Stapel voorkomen toe. Zet tevens de back-reference van Stapel voorkomen.
     *
     * @param stapelVoorkomen een Stapel voorkomen
     * @return his volledig builder
     */
    public StapelHisVolledigImplBuilder voegStapelVoorkomenToe(final StapelVoorkomenHisVolledigImpl stapelVoorkomen) {
        this.hisVolledigImpl.getStapelVoorkomens().add(stapelVoorkomen);
        ReflectionTestUtils.setField(stapelVoorkomen, "stapel", this.hisVolledigImpl);
        return this;
    }

}
