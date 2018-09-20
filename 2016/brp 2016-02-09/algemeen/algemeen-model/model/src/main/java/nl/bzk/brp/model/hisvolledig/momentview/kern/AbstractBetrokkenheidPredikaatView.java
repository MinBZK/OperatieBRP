/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ErkenningOngeborenVruchtHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamskeuzeOngeborenVruchtHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.BetrokkenheidBasis;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Betrokkenheid.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentPredikaatViewModelGenerator")
public abstract class AbstractBetrokkenheidPredikaatView implements ModelMoment, BetrokkenheidBasis, ElementIdentificeerbaar {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final BetrokkenheidHisVolledig betrokkenheid;
    private final Predicate predikaat;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param betrokkenheid hisVolledig instantie voor deze view.
     * @param predikaat het predikaat.
     */
    public AbstractBetrokkenheidPredikaatView(final BetrokkenheidHisVolledig betrokkenheid, final Predicate predikaat) {
        this.betrokkenheid = betrokkenheid;
        this.predikaat = predikaat;

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
     * Retourneert het predikaat voor deze view.
     *
     * @return Het predikaat voor deze view.
     */
    protected final Predicate getPredikaat() {
        return predikaat;
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
    public final RelatiePredikaatView getRelatie() {
        final RelatieHisVolledig relatie = betrokkenheid.getRelatie();
        RelatiePredikaatView view = null;
        if (relatie instanceof ErkenningOngeborenVruchtHisVolledig) {
            view = new ErkenningOngeborenVruchtPredikaatView((ErkenningOngeborenVruchtHisVolledig) relatie, getPredikaat());
        } else if (relatie instanceof FamilierechtelijkeBetrekkingHisVolledig) {
            view = new FamilierechtelijkeBetrekkingPredikaatView((FamilierechtelijkeBetrekkingHisVolledig) relatie, getPredikaat());
        } else if (relatie instanceof GeregistreerdPartnerschapHisVolledig) {
            view = new GeregistreerdPartnerschapPredikaatView((GeregistreerdPartnerschapHisVolledig) relatie, getPredikaat());
        } else if (relatie instanceof HuwelijkHisVolledig) {
            view = new HuwelijkPredikaatView((HuwelijkHisVolledig) relatie, getPredikaat());
        } else if (relatie instanceof NaamskeuzeOngeborenVruchtHisVolledig) {
            view = new NaamskeuzeOngeborenVruchtPredikaatView((NaamskeuzeOngeborenVruchtHisVolledig) relatie, getPredikaat());
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
    public final PersoonPredikaatView getPersoon() {
        return new PersoonPredikaatView(betrokkenheid.getPersoon(), getPredikaat());
    }

    /**
     * Retourneert Identiteit van Betrokkenheid
     *
     * @return Retourneert Identiteit van Betrokkenheid
     */
    public final HisBetrokkenheidModel getIdentiteit() {
        final List<HisBetrokkenheidModel> geldigRecord =
                new ArrayList(CollectionUtils.select(betrokkenheid.getBetrokkenheidHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor betrokkenheid met id {}. Er zijn meerdere ({}) "
                        + "records geldig. Dit wijst op een fout in de data. Het toegepaste predikaat was {}.",
                getID(),
                geldigRecord.size(),
                getPredikaat().getClass().getName());
            Collections.sort(geldigRecord, new IdComparator());
        }
        if (geldigRecord.size() == 0) {
            return null;
        }
        return geldigRecord.get(0);
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
