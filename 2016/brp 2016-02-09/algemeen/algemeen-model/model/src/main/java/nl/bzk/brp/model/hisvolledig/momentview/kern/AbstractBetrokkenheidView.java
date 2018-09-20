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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ErkenningOngeborenVruchtHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamskeuzeOngeborenVruchtHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.BetrokkenheidBasis;
import nl.bzk.brp.model.logisch.kern.HisBetrokkenheidIdentiteitGroep;

/**
 * View klasse voor Betrokkenheid.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractBetrokkenheidView implements ModelMoment, BetrokkenheidBasis, ElementIdentificeerbaar {

    private final BetrokkenheidHisVolledig betrokkenheid;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param betrokkenheid hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractBetrokkenheidView(
        final BetrokkenheidHisVolledig betrokkenheid,
        final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        this.betrokkenheid = betrokkenheid;
        this.formeelPeilmoment = formeelPeilmoment;
        this.materieelPeilmoment = materieelPeilmoment;

    }

    /**
     * Retourneert het proxyobject dat achter deze view hangt.
     *
     * @return het proxyobject dat achter deze view hangt
     */
    protected final BetrokkenheidHisVolledig getBetrokkenheid() {
        return betrokkenheid;
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
     * Functie die aangeeft of er actuele gegevens zijn in deze view.
     *
     * @return true indien actuele gegevens aanwezig, anders false
     */
    public boolean heeftActueleGegevens() {
        return this.getIdentiteit() != null;
    }

    /**
     * Retourneert ID van Betrokkenheid.
     *
     * @return ID.
     */
    public final Integer getID() {
        return betrokkenheid.getID();
    }

    /**
     * Retourneert Relatie van Betrokkenheid.
     *
     * @return Relatie.
     */
    public final RelatieView getRelatie() {
        final RelatieHisVolledig relatie = betrokkenheid.getRelatie();
        RelatieView view = null;
        if (relatie instanceof ErkenningOngeborenVruchtHisVolledig) {
            view = new ErkenningOngeborenVruchtView((ErkenningOngeborenVruchtHisVolledig) relatie, getFormeelPeilmoment(), getMaterieelPeilmoment());
        } else if (relatie instanceof FamilierechtelijkeBetrekkingHisVolledig) {
            view =
                    new FamilierechtelijkeBetrekkingView(
                        (FamilierechtelijkeBetrekkingHisVolledig) relatie,
                        getFormeelPeilmoment(),
                        getMaterieelPeilmoment());
        } else if (relatie instanceof GeregistreerdPartnerschapHisVolledig) {
            view = new GeregistreerdPartnerschapView((GeregistreerdPartnerschapHisVolledig) relatie, getFormeelPeilmoment(), getMaterieelPeilmoment());
        } else if (relatie instanceof HuwelijkHisVolledig) {
            view = new HuwelijkView((HuwelijkHisVolledig) relatie, getFormeelPeilmoment(), getMaterieelPeilmoment());
        } else if (relatie instanceof NaamskeuzeOngeborenVruchtHisVolledig) {
            view = new NaamskeuzeOngeborenVruchtView((NaamskeuzeOngeborenVruchtHisVolledig) relatie, getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return view;

    }

    /**
     * Retourneert Rol van Betrokkenheid.
     *
     * @return Rol.
     */
    public final SoortBetrokkenheidAttribuut getRol() {
        return betrokkenheid.getRol();
    }

    /**
     * Retourneert Persoon van Betrokkenheid.
     *
     * @return Persoon.
     */
    public final PersoonView getPersoon() {
        if (betrokkenheid.getPersoon() != null) {
            return new PersoonView(betrokkenheid.getPersoon(), getFormeelPeilmoment(), getMaterieelPeilmoment());
        }
        return null;
    }

    /**
     * Retourneert Identiteit van Betrokkenheid
     *
     * @return Retourneert Identiteit van Betrokkenheid
     */
    public final HisBetrokkenheidIdentiteitGroep getIdentiteit() {
        return betrokkenheid.getBetrokkenheidHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public ElementEnum getElementIdentificatie() {
        return ElementEnum.BETROKKENHEID;
    }

}
