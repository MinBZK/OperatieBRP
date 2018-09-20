/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschapBasis;

/**
 * View klasse voor Huwelijk/Geregistreerd partnerschap.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractHuwelijkGeregistreerdPartnerschapView extends RelatieView implements ModelMoment, HuwelijkGeregistreerdPartnerschapBasis,
        ElementIdentificeerbaar
{

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param relatie hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractHuwelijkGeregistreerdPartnerschapView(
        final HuwelijkGeregistreerdPartnerschapHisVolledig relatie,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        super(relatie, formeelPeilmoment, materieelPeilmoment);

    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public ElementEnum getElementIdentificatie() {
        return ElementEnum.HUWELIJKGEREGISTREERDPARTNERSCHAP;
    }

}
