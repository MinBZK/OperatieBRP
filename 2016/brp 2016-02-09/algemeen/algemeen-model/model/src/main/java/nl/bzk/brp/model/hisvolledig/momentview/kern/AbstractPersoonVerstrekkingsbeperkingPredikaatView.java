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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonVerstrekkingsbeperkingBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Persoon \ Verstrekkingsbeperking.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentPredikaatViewModelGenerator")
public abstract class AbstractPersoonVerstrekkingsbeperkingPredikaatView implements ModelMoment, PersoonVerstrekkingsbeperkingBasis,
        ElementIdentificeerbaar
{

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperking;
    private final Predicate predikaat;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoonVerstrekkingsbeperking hisVolledig instantie voor deze view.
     * @param predikaat het predikaat.
     */
    public AbstractPersoonVerstrekkingsbeperkingPredikaatView(
        final PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperking,
        final Predicate predikaat)
    {
        this.persoonVerstrekkingsbeperking = persoonVerstrekkingsbeperking;
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
        return this.getIdentiteit() != null;
    }

    /**
     * Retourneert ID van Persoon \ Verstrekkingsbeperking.
     *
     * @return ID.
     */
    public final Integer getID() {
        return persoonVerstrekkingsbeperking.getID();
    }

    /**
     * Retourneert Persoon van Persoon \ Verstrekkingsbeperking.
     *
     * @return Persoon.
     */
    public final PersoonPredikaatView getPersoon() {
        return new PersoonPredikaatView(persoonVerstrekkingsbeperking.getPersoon(), getPredikaat());
    }

    /**
     * Retourneert Partij van Persoon \ Verstrekkingsbeperking.
     *
     * @return Partij.
     */
    public final PartijAttribuut getPartij() {
        return persoonVerstrekkingsbeperking.getPartij();
    }

    /**
     * Retourneert Omschrijving derde van Persoon \ Verstrekkingsbeperking.
     *
     * @return Omschrijving derde.
     */
    public final OmschrijvingEnumeratiewaardeAttribuut getOmschrijvingDerde() {
        return persoonVerstrekkingsbeperking.getOmschrijvingDerde();
    }

    /**
     * Retourneert Gemeente verordening van Persoon \ Verstrekkingsbeperking.
     *
     * @return Gemeente verordening.
     */
    public final PartijAttribuut getGemeenteVerordening() {
        return persoonVerstrekkingsbeperking.getGemeenteVerordening();
    }

    /**
     * Retourneert Identiteit van Persoon \ Verstrekkingsbeperking
     *
     * @return Retourneert Identiteit van Persoon \ Verstrekkingsbeperking
     */
    public final HisPersoonVerstrekkingsbeperkingModel getIdentiteit() {
        final List<HisPersoonVerstrekkingsbeperkingModel> geldigRecord =
                new ArrayList(CollectionUtils.select(
                    persoonVerstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorie().getHistorie(),
                    getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoonVerstrekkingsbeperking met id {}. Er zijn meerdere ({}) "
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
        return ElementEnum.PERSOON_VERSTREKKINGSBEPERKING;
    }

}
