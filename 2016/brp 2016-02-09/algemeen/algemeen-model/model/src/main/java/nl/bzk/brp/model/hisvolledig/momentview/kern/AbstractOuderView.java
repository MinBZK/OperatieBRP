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
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.logisch.kern.OuderBasis;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;

/**
 * View klasse voor Ouder.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractOuderView extends BetrokkenheidView implements ModelMoment, OuderBasis, ElementIdentificeerbaar {

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param betrokkenheid hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractOuderView(final OuderHisVolledig betrokkenheid, final DatumTijdAttribuut formeelPeilmoment, final DatumAttribuut materieelPeilmoment) {
        super(betrokkenheid, formeelPeilmoment, materieelPeilmoment);

    }

    /**
     * Functie die aangeeft of er actuele gegevens zijn in deze view.
     *
     * @return true indien actuele gegevens aanwezig, anders false
     */
    public boolean heeftActueleGegevens() {
        return this.getOuderschap() != null || this.getOuderlijkGezag() != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisOuderOuderschapModel getOuderschap() {
        return ((OuderHisVolledig) getBetrokkenheid()).getOuderOuderschapHistorie().getHistorieRecord(getMaterieelPeilmoment(), getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisOuderOuderlijkGezagModel getOuderlijkGezag() {
        return ((OuderHisVolledig) getBetrokkenheid()).getOuderOuderlijkGezagHistorie()
                                                      .getHistorieRecord(getMaterieelPeilmoment(), getFormeelPeilmoment());
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.OUDER;
    }

}
