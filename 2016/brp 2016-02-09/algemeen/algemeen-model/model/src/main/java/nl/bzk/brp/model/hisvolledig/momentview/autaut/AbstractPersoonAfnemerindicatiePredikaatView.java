/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.autaut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonPredikaatView;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieBasis;
import nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Persoon \ Afnemerindicatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentPredikaatViewModelGenerator")
public abstract class AbstractPersoonAfnemerindicatiePredikaatView implements ModelMoment, PersoonAfnemerindicatieBasis {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatie;
    private final Predicate predikaat;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonAfnemerindicatie hisVolledig instantie voor deze view.
     * @param predikaat het predikaat.
     */
    public AbstractPersoonAfnemerindicatiePredikaatView(final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatie, final Predicate predikaat) {
        this.persoonAfnemerindicatie = persoonAfnemerindicatie;
        this.predikaat = predikaat;

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
     * Retourneert ID van Persoon \ Afnemerindicatie.
     *
     * @return ID.
     */
    public final Long getID() {
        return persoonAfnemerindicatie.getID();
    }

    /**
     * Retourneert Persoon van Persoon \ Afnemerindicatie.
     *
     * @return Persoon.
     */
    public final PersoonPredikaatView getPersoon() {
        return new PersoonPredikaatView(persoonAfnemerindicatie.getPersoon(), getPredikaat());
    }

    /**
     * Retourneert Afnemer van Persoon \ Afnemerindicatie.
     *
     * @return Afnemer.
     */
    public final PartijAttribuut getAfnemer() {
        return persoonAfnemerindicatie.getAfnemer();
    }

    /**
     * Retourneert Leveringsautorisatie van Persoon \ Afnemerindicatie.
     *
     * @return Leveringsautorisatie.
     */
    public final LeveringsautorisatieAttribuut getLeveringsautorisatie() {
        return persoonAfnemerindicatie.getLeveringsautorisatie();
    }

    /**
     * Retourneert Standaard van Persoon \ Afnemerindicatie. Deze methode bepaalt met behulp van het predikaat het
     * geldige record. Dit zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er
     * meerdere records tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een
     * error. Daarnaast kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record
     * altijd consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Standaard van Persoon \ Afnemerindicatie
     */
    @Override
    public final HisPersoonAfnemerindicatieModel getStandaard() {
        final List<HisPersoonAfnemerindicatieModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoonAfnemerindicatie.getPersoonAfnemerindicatieHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoonAfnemerindicatie met id {}. Er zijn meerdere ({}) "
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

}
