/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.IdComparator;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Relatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentPredikaatViewModelGenerator")
public abstract class AbstractRelatiePredikaatView implements ModelMoment, RelatieBasis, ElementIdentificeerbaar {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final RelatieHisVolledig relatie;
    private final Predicate predikaat;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param relatie hisVolledig instantie voor deze view.
     * @param predikaat het predikaat.
     */
    public AbstractRelatiePredikaatView(final RelatieHisVolledig relatie, final Predicate predikaat) {
        this.relatie = relatie;
        this.predikaat = predikaat;

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
     * Retourneert Standaard van Relatie. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit zou
     * altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Standaard van Relatie
     */
    @Override
    public final HisRelatieModel getStandaard() {
        final List<HisRelatieModel> geldigRecord = new ArrayList(CollectionUtils.select(relatie.getRelatieHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor relatie met id {}. Er zijn meerdere ({}) "
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
     * {@inheritDoc}
     */
    @Override
    public Collection<BetrokkenheidPredikaatView> getBetrokkenheden() {
        final Set<BetrokkenheidPredikaatView> result = new HashSet<BetrokkenheidPredikaatView>();
        for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
            BetrokkenheidPredikaatView view = null;
            if (betrokkenheidHisVolledig instanceof ErkennerHisVolledig) {
                view = new ErkennerPredikaatView((ErkennerHisVolledig) betrokkenheidHisVolledig, getPredikaat());
            } else if (betrokkenheidHisVolledig instanceof InstemmerHisVolledig) {
                view = new InstemmerPredikaatView((InstemmerHisVolledig) betrokkenheidHisVolledig, getPredikaat());
            } else if (betrokkenheidHisVolledig instanceof KindHisVolledig) {
                view = new KindPredikaatView((KindHisVolledig) betrokkenheidHisVolledig, getPredikaat());
            } else if (betrokkenheidHisVolledig instanceof NaamgeverHisVolledig) {
                view = new NaamgeverPredikaatView((NaamgeverHisVolledig) betrokkenheidHisVolledig, getPredikaat());
            } else if (betrokkenheidHisVolledig instanceof OuderHisVolledig) {
                view = new OuderPredikaatView((OuderHisVolledig) betrokkenheidHisVolledig, getPredikaat());
            } else if (betrokkenheidHisVolledig instanceof PartnerHisVolledig) {
                view = new PartnerPredikaatView((PartnerHisVolledig) betrokkenheidHisVolledig, getPredikaat());
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
