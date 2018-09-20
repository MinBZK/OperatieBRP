/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import org.apache.commons.collections.Predicate;


/**
 * Predikaat view voor Gegeven in onderzoek.
 */
public final class GegevenInOnderzoekHisVolledigView extends AbstractGegevenInOnderzoekHisVolledigView implements
    GegevenInOnderzoekHisVolledig
{


    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param gegevenInOnderzoekHisVolledig gegevenInOnderzoek
     * @param predikaat                     predikaat
     */
    public GegevenInOnderzoekHisVolledigView(final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig,
        final Predicate predikaat)
    {
        super(gegevenInOnderzoekHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param gegevenInOnderzoekHisVolledig    gegevenInOnderzoek
     * @param predikaat                        predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public GegevenInOnderzoekHisVolledigView(final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig,
        final Predicate predikaat, final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(gegevenInOnderzoekHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    /**
     * @return mag gegeven geleverd worden.
     */
    public boolean magGegevenGeleverdWorden() {
        if (getElement() != null && getElement().isMagGeleverdWorden()) {
            return true;
        }
        if (getVoorkomenSleutelGegeven() != null && getVoorkomenSleutelGegeven().isMagGeleverdWorden()) {
            return true;
        }
        if (getObjectSleutelGegeven() != null && getObjectSleutelGegeven().isMagGeleverdWorden()) {
            return true;
        }
        return false;
    }

    /**
     * Retourneert Object sleutel gegeven van Gegeven in onderzoek. Voor gebruik in binding. Encrypted voor persoon referentie.
     *
     * @return Object sleutel gegeven.
     */
    public String getObjectSleutelGegevenVoorBinding() {
        return ((GegevenInOnderzoekHisVolledigImpl) this.gegevenInOnderzoek).getObjectSleutelGegevenVoorBinding();
    }

    /**
     * @param encryptedObjectSleutelGegeven encryptedObjectSleutelGegeven.
     */
    public void setEncryptedObjectSleutelGegeven(final String encryptedObjectSleutelGegeven) {
        ((GegevenInOnderzoekHisVolledigImpl) this.gegevenInOnderzoek).setEncryptedObjectSleutelGegeven(encryptedObjectSleutelGegeven);
    }
}
