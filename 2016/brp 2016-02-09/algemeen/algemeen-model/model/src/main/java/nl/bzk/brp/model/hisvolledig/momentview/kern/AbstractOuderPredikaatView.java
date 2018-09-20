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
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.logisch.kern.OuderBasis;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Ouder.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentPredikaatViewModelGenerator")
public abstract class AbstractOuderPredikaatView extends BetrokkenheidPredikaatView implements ModelMoment, OuderBasis, ElementIdentificeerbaar {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param betrokkenheid hisVolledig instantie voor deze view.
     * @param predikaat het predikaat.
     */
    public AbstractOuderPredikaatView(final OuderHisVolledig betrokkenheid, final Predicate predikaat) {
        super(betrokkenheid, predikaat);

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
     * Retourneert Ouderschap van Ouder. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit zou
     * altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Ouderschap van Ouder
     */
    @Override
    public final HisOuderOuderschapModel getOuderschap() {
        final List<HisOuderOuderschapModel> geldigRecord =
                new ArrayList(CollectionUtils.select(((OuderHisVolledig) getBetrokkenheid()).getOuderOuderschapHistorie().getHistorie(), getPredikaat()));
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
     * Retourneert Ouderlijk gezag van Ouder. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit
     * zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Ouderlijk gezag van Ouder
     */
    @Override
    public final HisOuderOuderlijkGezagModel getOuderlijkGezag() {
        final List<HisOuderOuderlijkGezagModel> geldigRecord =
                new ArrayList(CollectionUtils.select(
                    ((OuderHisVolledig) getBetrokkenheid()).getOuderOuderlijkGezagHistorie().getHistorie(),
                    getPredikaat()));
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
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.OUDER;
    }

}
