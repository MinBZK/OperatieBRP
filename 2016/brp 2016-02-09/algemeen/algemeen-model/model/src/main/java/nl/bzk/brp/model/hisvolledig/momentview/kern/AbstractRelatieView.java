/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ErkennerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.InstemmerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamgeverHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.RelatieBasis;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;

/**
 * View klasse voor Relatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractRelatieView implements ModelMoment, RelatieBasis, ElementIdentificeerbaar {

    private final RelatieHisVolledig relatie;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param relatie hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractRelatieView(final RelatieHisVolledig relatie, final DatumTijdAttribuut formeelPeilmoment, final DatumAttribuut materieelPeilmoment) {
        this.relatie = relatie;
        this.formeelPeilmoment = formeelPeilmoment;
        this.materieelPeilmoment = materieelPeilmoment;

    }

    /**
     * Retourneert het proxyobject dat achter deze view hangt.
     *
     * @return het proxyobject dat achter deze view hangt
     */
    protected final RelatieHisVolledig getRelatie() {
        return relatie;
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
        return this.getStandaard() != null;
    }

    /**
     * Retourneert ID van Relatie.
     *
     * @return ID.
     */
    public final Integer getID() {
        return relatie.getID();
    }

    /**
     * Retourneert Soort van Relatie.
     *
     * @return Soort.
     */
    public final SoortRelatieAttribuut getSoort() {
        return relatie.getSoort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisRelatieModel getStandaard() {
        return relatie.getRelatieHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<BetrokkenheidView> getBetrokkenheden() {
        final Set<BetrokkenheidView> result = new HashSet<>();
        for (BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
            final BetrokkenheidView view;
            if (betrokkenheidHisVolledig instanceof ErkennerHisVolledig) {
                view = new ErkennerView((ErkennerHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else if (betrokkenheidHisVolledig instanceof InstemmerHisVolledig) {
                view = new InstemmerView((InstemmerHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else if (betrokkenheidHisVolledig instanceof KindHisVolledig) {
                view = new KindView((KindHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else if (betrokkenheidHisVolledig instanceof NaamgeverHisVolledig) {
                view = new NaamgeverView((NaamgeverHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else if (betrokkenheidHisVolledig instanceof OuderHisVolledig) {
                view = new OuderView((OuderHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else if (betrokkenheidHisVolledig instanceof PartnerHisVolledig) {
                view = new PartnerView((PartnerHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else {
                throw new IllegalArgumentException("Onbekend type Betrokkenheid.");
            }
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public ElementEnum getElementIdentificatie() {
        return ElementEnum.RELATIE;
    }

}
