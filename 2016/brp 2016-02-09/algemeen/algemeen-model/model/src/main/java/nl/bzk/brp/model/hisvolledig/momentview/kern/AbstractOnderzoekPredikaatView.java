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
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.OnderzoekBasis;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentPredikaatViewModelGenerator")
public abstract class AbstractOnderzoekPredikaatView implements ModelMoment, OnderzoekBasis, ElementIdentificeerbaar {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final OnderzoekHisVolledig onderzoek;
    private final Predicate predikaat;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param onderzoek hisVolledig instantie voor deze view.
     * @param predikaat het predikaat.
     */
    public AbstractOnderzoekPredikaatView(final OnderzoekHisVolledig onderzoek, final Predicate predikaat) {
        this.onderzoek = onderzoek;
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
        return this.getStandaard() != null || this.getAfgeleidAdministratief() != null;
    }

    /**
     * Retourneert ID van Onderzoek.
     *
     * @return ID.
     */
    public final Integer getID() {
        return onderzoek.getID();
    }

    /**
     * Retourneert Standaard van Onderzoek. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit
     * zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Standaard van Onderzoek
     */
    @Override
    public final HisOnderzoekModel getStandaard() {
        final List<HisOnderzoekModel> geldigRecord = new ArrayList(CollectionUtils.select(onderzoek.getOnderzoekHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor onderzoek met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Afgeleid administratief van Onderzoek. Deze methode bepaalt met behulp van het predikaat het geldige
     * record. Dit zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere
     * records tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error.
     * Daarnaast kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Afgeleid administratief van Onderzoek
     */
    @Override
    public final HisOnderzoekAfgeleidAdministratiefModel getAfgeleidAdministratief() {
        final List<HisOnderzoekAfgeleidAdministratiefModel> geldigRecord =
                new ArrayList(CollectionUtils.select(onderzoek.getOnderzoekAfgeleidAdministratiefHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor onderzoek met id {}. Er zijn meerdere ({}) "
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
    public Collection<GegevenInOnderzoekPredikaatView> getGegevensInOnderzoek() {
        final Set<GegevenInOnderzoekPredikaatView> result = new HashSet<GegevenInOnderzoekPredikaatView>();
        for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig : onderzoek.getGegevensInOnderzoek()) {
            final GegevenInOnderzoekPredikaatView view = new GegevenInOnderzoekPredikaatView(gegevenInOnderzoekHisVolledig, getPredikaat());
            result.add(view);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersoonOnderzoekPredikaatView> getPersonenInOnderzoek() {
        final Set<PersoonOnderzoekPredikaatView> result = new HashSet<PersoonOnderzoekPredikaatView>();
        for (final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : onderzoek.getPersonenInOnderzoek()) {
            final PersoonOnderzoekPredikaatView view = new PersoonOnderzoekPredikaatView(persoonOnderzoekHisVolledig, getPredikaat());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PartijOnderzoekPredikaatView> getPartijenInOnderzoek() {
        final Set<PartijOnderzoekPredikaatView> result = new HashSet<PartijOnderzoekPredikaatView>();
        for (final PartijOnderzoekHisVolledig partijOnderzoekHisVolledig : onderzoek.getPartijenInOnderzoek()) {
            final PartijOnderzoekPredikaatView view = new PartijOnderzoekPredikaatView(partijOnderzoekHisVolledig, getPredikaat());
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
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ONDERZOEK;
    }

}
