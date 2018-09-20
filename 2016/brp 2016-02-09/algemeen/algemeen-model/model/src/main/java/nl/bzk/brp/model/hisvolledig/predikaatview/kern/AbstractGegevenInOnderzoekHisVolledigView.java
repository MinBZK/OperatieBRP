/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Gegeven in onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractGegevenInOnderzoekHisVolledigView extends AbstractHisVolledigPredikaatView implements GegevenInOnderzoekHisVolledigBasis,
        ElementIdentificeerbaar
{

    protected final GegevenInOnderzoekHisVolledig gegevenInOnderzoek;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param gegevenInOnderzoekHisVolledig gegevenInOnderzoek
     * @param predikaat predikaat
     */
    public AbstractGegevenInOnderzoekHisVolledigView(final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig, final Predicate predikaat) {
        this(gegevenInOnderzoekHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param gegevenInOnderzoekHisVolledig gegevenInOnderzoek
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractGegevenInOnderzoekHisVolledigView(
        final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.gegevenInOnderzoek = gegevenInOnderzoekHisVolledig;
        this.peilmomentVoorAltijdTonenGroepen = peilmomentVoorAltijdTonenGroepen;

    }

    /**
     * Geeft de peilmomentVoorAltijdTonenGroepen terug.
     *
     * @return de peilmomentVoorAltijdTonenGroepen
     */
    public final DatumTijdAttribuut getPeilmomentVoorAltijdTonenGroepen() {
        return peilmomentVoorAltijdTonenGroepen;
    }

    /**
     * Zet de waarde van het veld peilmomentVoorAltijdTonenGroepen.
     *
     * @param peilmomentVoorAltijdTonenGroepen
     */
    public final void setPeilmomentVoorAltijdTonenGroepen(final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen) {
        this.peilmomentVoorAltijdTonenGroepen = peilmomentVoorAltijdTonenGroepen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Integer getID() {
        return gegevenInOnderzoek.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekHisVolledig getOnderzoek() {
        if (gegevenInOnderzoek.getOnderzoek() != null) {
            return new OnderzoekHisVolledigView(gegevenInOnderzoek.getOnderzoek(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ElementAttribuut getElement() {
        return gegevenInOnderzoek.getElement();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SleutelwaardeAttribuut getObjectSleutelGegeven() {
        return gegevenInOnderzoek.getObjectSleutelGegeven();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SleutelwaardeAttribuut getVoorkomenSleutelGegeven() {
        return gegevenInOnderzoek.getVoorkomenSleutelGegeven();

    }

    /**
     *
     *
     * @return Retourneert alle historie records van alle groepen.
     */
    public Set<HistorieEntiteit> getAlleHistorieRecords() {
        final Set<HistorieEntiteit> resultaat = new HashSet<>();
        return resultaat;
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
