/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.GegevenInOnderzoekBasis;

/**
 * View klasse voor Gegeven in onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractGegevenInOnderzoekView implements ModelMoment, GegevenInOnderzoekBasis, ElementIdentificeerbaar {

    private final GegevenInOnderzoekHisVolledig gegevenInOnderzoek;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param gegevenInOnderzoek hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractGegevenInOnderzoekView(
        final GegevenInOnderzoekHisVolledig gegevenInOnderzoek,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.gegevenInOnderzoek = gegevenInOnderzoek;
        this.formeelPeilmoment = formeelPeilmoment;
        this.materieelPeilmoment = materieelPeilmoment;

    }

    /**
     * Retourneert formeel peilmoment voor deze view.
     *
     * @return Formeel peilmoment voor deze view.
     */
    protected final DatumTijdAttribuut getFormeelPeilmoment() {
        return formeelPeilmoment;
    }

    /**
     * Retourneert materieel peilmoment voor deze view.
     *
     * @return Materieel peilmoment voor deze view.
     */
    protected final DatumAttribuut getMaterieelPeilmoment() {
        return materieelPeilmoment;
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
    public final OnderzoekView getOnderzoek() {
        if (gegevenInOnderzoek.getOnderzoek() != null) {
            return new OnderzoekView(gegevenInOnderzoek.getOnderzoek(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
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
