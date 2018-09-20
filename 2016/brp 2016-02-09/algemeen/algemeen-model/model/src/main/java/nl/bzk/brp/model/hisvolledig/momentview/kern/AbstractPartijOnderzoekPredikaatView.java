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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoekBasis;
import nl.bzk.brp.model.operationeel.kern.HisPartijOnderzoekModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Partij \ Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentPredikaatViewModelGenerator")
public abstract class AbstractPartijOnderzoekPredikaatView implements ModelMoment, PartijOnderzoekBasis, ElementIdentificeerbaar {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final PartijOnderzoekHisVolledig partijOnderzoek;
    private final Predicate predikaat;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param partijOnderzoek hisVolledig instantie voor deze view.
     * @param predikaat het predikaat.
     */
    public AbstractPartijOnderzoekPredikaatView(final PartijOnderzoekHisVolledig partijOnderzoek, final Predicate predikaat) {
        this.partijOnderzoek = partijOnderzoek;
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
     * Retourneert ID van Partij \ Onderzoek.
     *
     * @return ID.
     */
    public final Integer getID() {
        return partijOnderzoek.getID();
    }

    /**
     * Retourneert Partij van Partij \ Onderzoek.
     *
     * @return Partij.
     */
    public final PartijAttribuut getPartij() {
        return partijOnderzoek.getPartij();
    }

    /**
     * Retourneert Onderzoek van Partij \ Onderzoek.
     *
     * @return Onderzoek.
     */
    public final OnderzoekPredikaatView getOnderzoek() {
        return new OnderzoekPredikaatView(partijOnderzoek.getOnderzoek(), getPredikaat());
    }

    /**
     * Retourneert Standaard van Partij \ Onderzoek. Deze methode bepaalt met behulp van het predikaat het geldige
     * record. Dit zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere
     * records tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error.
     * Daarnaast kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Standaard van Partij \ Onderzoek
     */
    @Override
    public final HisPartijOnderzoekModel getStandaard() {
        final List<HisPartijOnderzoekModel> geldigRecord =
                new ArrayList(CollectionUtils.select(partijOnderzoek.getPartijOnderzoekHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor partijOnderzoek met id {}. Er zijn meerdere ({}) "
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
        return ElementEnum.PARTIJENINONDERZOEK;
    }

}
