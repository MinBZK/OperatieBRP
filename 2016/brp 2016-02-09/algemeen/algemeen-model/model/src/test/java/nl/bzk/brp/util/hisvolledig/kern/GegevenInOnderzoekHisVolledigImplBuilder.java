/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;

/**
 * Builder klasse voor Gegeven in onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class GegevenInOnderzoekHisVolledigImplBuilder {

    private GegevenInOnderzoekHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param onderzoek onderzoek van Gegeven in onderzoek.
     * @param element element van Gegeven in onderzoek.
     * @param objectSleutelGegeven objectSleutelGegeven van Gegeven in onderzoek.
     * @param voorkomenSleutelGegeven voorkomenSleutelGegeven van Gegeven in onderzoek.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public GegevenInOnderzoekHisVolledigImplBuilder(
        final OnderzoekHisVolledigImpl onderzoek,
        final Element element,
        final SleutelwaardeAttribuut objectSleutelGegeven,
        final SleutelwaardeAttribuut voorkomenSleutelGegeven,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl =
                new GegevenInOnderzoekHisVolledigImpl(onderzoek, new ElementAttribuut(element), objectSleutelGegeven, voorkomenSleutelGegeven);
        if (hisVolledigImpl.getElement() != null) {
            hisVolledigImpl.getElement().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getObjectSleutelGegeven() != null) {
            hisVolledigImpl.getObjectSleutelGegeven().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getVoorkomenSleutelGegeven() != null) {
            hisVolledigImpl.getVoorkomenSleutelGegeven().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param onderzoek onderzoek van Gegeven in onderzoek.
     * @param element element van Gegeven in onderzoek.
     * @param objectSleutelGegeven objectSleutelGegeven van Gegeven in onderzoek.
     * @param voorkomenSleutelGegeven voorkomenSleutelGegeven van Gegeven in onderzoek.
     */
    public GegevenInOnderzoekHisVolledigImplBuilder(
        final OnderzoekHisVolledigImpl onderzoek,
        final Element element,
        final SleutelwaardeAttribuut objectSleutelGegeven,
        final SleutelwaardeAttribuut voorkomenSleutelGegeven)
    {
        this(onderzoek, element, objectSleutelGegeven, voorkomenSleutelGegeven, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param element element van Gegeven in onderzoek.
     * @param objectSleutelGegeven objectSleutelGegeven van Gegeven in onderzoek.
     * @param voorkomenSleutelGegeven voorkomenSleutelGegeven van Gegeven in onderzoek.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public GegevenInOnderzoekHisVolledigImplBuilder(
        final Element element,
        final SleutelwaardeAttribuut objectSleutelGegeven,
        final SleutelwaardeAttribuut voorkomenSleutelGegeven,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new GegevenInOnderzoekHisVolledigImpl(null, new ElementAttribuut(element), objectSleutelGegeven, voorkomenSleutelGegeven);
        if (hisVolledigImpl.getElement() != null) {
            hisVolledigImpl.getElement().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getObjectSleutelGegeven() != null) {
            hisVolledigImpl.getObjectSleutelGegeven().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getVoorkomenSleutelGegeven() != null) {
            hisVolledigImpl.getVoorkomenSleutelGegeven().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param element element van Gegeven in onderzoek.
     * @param objectSleutelGegeven objectSleutelGegeven van Gegeven in onderzoek.
     * @param voorkomenSleutelGegeven voorkomenSleutelGegeven van Gegeven in onderzoek.
     */
    public GegevenInOnderzoekHisVolledigImplBuilder(
        final Element element,
        final SleutelwaardeAttribuut objectSleutelGegeven,
        final SleutelwaardeAttribuut voorkomenSleutelGegeven)
    {
        this(null, element, objectSleutelGegeven, voorkomenSleutelGegeven, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public GegevenInOnderzoekHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
