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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GegevenswaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInTerugmeldingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInTerugmeldingHisVolledigBasis;
import nl.bzk.brp.model.hisvolledig.kern.TerugmeldingHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.AbstractHisVolledigPredikaatView;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Gegeven in terugmelding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigPredikaatViewModelGenerator")
public abstract class AbstractGegevenInTerugmeldingHisVolledigView extends AbstractHisVolledigPredikaatView implements
        GegevenInTerugmeldingHisVolledigBasis, ElementIdentificeerbaar
{

    protected final GegevenInTerugmeldingHisVolledig gegevenInTerugmelding;
    private DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen;

    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param gegevenInTerugmeldingHisVolledig gegevenInTerugmelding
     * @param predikaat predikaat
     */
    public AbstractGegevenInTerugmeldingHisVolledigView(final GegevenInTerugmeldingHisVolledig gegevenInTerugmeldingHisVolledig, final Predicate predikaat)
    {
        this(gegevenInTerugmeldingHisVolledig, predikaat, null);

    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param gegevenInTerugmeldingHisVolledig gegevenInTerugmelding
     * @param predikaat predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public AbstractGegevenInTerugmeldingHisVolledigView(
        final GegevenInTerugmeldingHisVolledig gegevenInTerugmeldingHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(predikaat);
        this.gegevenInTerugmelding = gegevenInTerugmeldingHisVolledig;
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
        return gegevenInTerugmelding.getID();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TerugmeldingHisVolledig getTerugmelding() {
        if (gegevenInTerugmelding.getTerugmelding() != null) {
            return new TerugmeldingHisVolledigView(gegevenInTerugmelding.getTerugmelding(), getPredikaat(), getPeilmomentVoorAltijdTonenGroepen());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ElementAttribuut getElement() {
        return gegevenInTerugmelding.getElement();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GegevenswaardeAttribuut getBetwijfeldeWaarde() {
        return gegevenInTerugmelding.getBetwijfeldeWaarde();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GegevenswaardeAttribuut getVerwachteWaarde() {
        return gegevenInTerugmelding.getVerwachteWaarde();

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
        return ElementEnum.GEGEVENINTERUGMELDING;
    }

}
