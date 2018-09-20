/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.ActieBronHisVolledig;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat view voor Actie \ Bron.
 */
public final class ActieBronHisVolledigView extends AbstractActieBronHisVolledigView implements ActieBronHisVolledig, ElementIdentificeerbaar {


    /**
     * Constructor die alle klasse variabelen initialiseert, behalve peilmoment.
     *
     * @param actieBronHisVolledig actieBron
     * @param predikaat            predikaat
     */
    public ActieBronHisVolledigView(final ActieBronHisVolledig actieBronHisVolledig, final Predicate predikaat) {
        super(actieBronHisVolledig, predikaat);
    }

    /**
     * Constructor die alle klasse variabelen initialiseert.
     *
     * @param actieBronHisVolledig             actieBron
     * @param predikaat                        predikaat
     * @param peilmomentVoorAltijdTonenGroepen peilmomentVoorAltijdTonenGroepen
     */
    public ActieBronHisVolledigView(
        final ActieBronHisVolledig actieBronHisVolledig,
        final Predicate predikaat,
        final DatumTijdAttribuut peilmomentVoorAltijdTonenGroepen)
    {
        super(actieBronHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);
    }

    /**
     * We hebben hier te maken met een view waarin het onderliggende object een optioneel veld heeft. Document hoeft niet altijd aanwezig te zijn. In
     * plaats daarvaan kunnen we ook een rechtsgrond hebben.
     *
     * @return retourneert het DocumentView indien een Document inderdaad onderdeel van de verantwoording is.
     */
    @Override
    public DocumentHisVolledigView getDocument() {
        if (actieBron.getDocument() != null) {
            return (DocumentHisVolledigView) super.getDocument();
        }
        return null;
    }

    /**
     * Bepaalt een communicatieID voor deze bron, deze functie wordt door Jibx gebruikt om communicatieID's en referentieID's te bepalen bij het marshallen
     * van verantwoordingsinformatie. Let op dat de waardes uniek moeten zijn. Om dit te bewerkstelligen begint de communicatieId met een string dat het
     * type is van de bron.
     *
     * @return een uniek communicatieID
     */
    public String getCommunicatieId() {
        String commId = null;
        if (getDocument() != null) {
            commId = "document" + getDocument().getID();
        } else if (getRechtsgrond() != null) {
            commId = "rechtsgrond" + getRechtsgrond().getWaarde().getID();
        } else if (getRechtsgrondomschrijving() != null) {
            commId = "rechtsgrondoms" + (getRechtsgrondomschrijving().getWaarde().hashCode() + getID().hashCode());
        }
        return commId;
    }
}
