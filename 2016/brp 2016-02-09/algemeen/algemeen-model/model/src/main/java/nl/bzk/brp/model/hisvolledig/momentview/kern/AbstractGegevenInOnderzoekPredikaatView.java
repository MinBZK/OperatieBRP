/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.GegevenInOnderzoekBasis;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Gegeven in onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentPredikaatViewModelGenerator")
public abstract class AbstractGegevenInOnderzoekPredikaatView implements ModelMoment, GegevenInOnderzoekBasis, ElementIdentificeerbaar {

    private final GegevenInOnderzoekHisVolledig gegevenInOnderzoek;
    private final Predicate predikaat;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param gegevenInOnderzoek hisVolledig instantie voor deze view.
     * @param predikaat het predikaat.
     */
    public AbstractGegevenInOnderzoekPredikaatView(final GegevenInOnderzoekHisVolledig gegevenInOnderzoek, final Predicate predikaat) {
        this.gegevenInOnderzoek = gegevenInOnderzoek;
        this.predikaat = predikaat;

    }

    /**
     * Retourneert het predikaat voor deze view.
     *
     * @return Het predikaat voor deze view.
     */
    protected final Predicate getPredikaat() {
        return predikaat;
    }

    /**
     * Retourneert ID van Gegeven in onderzoek.
     *
     * @return ID.
     */
    public final Integer getID() {
        return gegevenInOnderzoek.getID();
    }

    /**
     * Retourneert Onderzoek van Gegeven in onderzoek.
     *
     * @return Onderzoek.
     */
    public final OnderzoekPredikaatView getOnderzoek() {
        return new OnderzoekPredikaatView(gegevenInOnderzoek.getOnderzoek(), getPredikaat());
    }

    /**
     * Retourneert Element van Gegeven in onderzoek.
     *
     * @return Element.
     */
    public final ElementAttribuut getElement() {
        return gegevenInOnderzoek.getElement();
    }

    /**
     * Retourneert Object sleutel gegeven van Gegeven in onderzoek.
     *
     * @return Object sleutel gegeven.
     */
    public final SleutelwaardeAttribuut getObjectSleutelGegeven() {
        return gegevenInOnderzoek.getObjectSleutelGegeven();
    }

    /**
     * Retourneert Voorkomen sleutel gegeven van Gegeven in onderzoek.
     *
     * @return Voorkomen sleutel gegeven.
     */
    public final SleutelwaardeAttribuut getVoorkomenSleutelGegeven() {
        return gegevenInOnderzoek.getVoorkomenSleutelGegeven();
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.GEGEVENINONDERZOEK;
    }

}
