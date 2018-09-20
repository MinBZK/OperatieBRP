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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.IdComparator;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ErkennerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.InstemmerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamgeverHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.autaut.PersoonAfnemerindicatiePredikaatView;
import nl.bzk.brp.model.logisch.kern.PersoonBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNummerverwijzingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonPersoonskaartModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingKiesrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfsrechtModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * View klasse voor Persoon.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentPredikaatViewModelGenerator")
public abstract class AbstractPersoonPredikaatView implements ModelMoment, PersoonBasis, ElementIdentificeerbaar {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final PersoonHisVolledig persoon;
    private final Predicate predikaat;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoon hisVolledig instantie voor deze view.
     * @param predikaat het predikaat.
     */
    public AbstractPersoonPredikaatView(final PersoonHisVolledig persoon, final Predicate predikaat) {
        this.persoon = persoon;
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
        return this.getAfgeleidAdministratief() != null
               || this.getIdentificatienummers() != null
               || this.getSamengesteldeNaam() != null
               || this.getGeboorte() != null
               || this.getGeslachtsaanduiding() != null
               || this.getInschrijving() != null
               || this.getNummerverwijzing() != null
               || this.getBijhouding() != null
               || this.getOverlijden() != null
               || this.getNaamgebruik() != null
               || this.getMigratie() != null
               || this.getVerblijfsrecht() != null
               || this.getUitsluitingKiesrecht() != null
               || this.getDeelnameEUVerkiezingen() != null
               || this.getPersoonskaart() != null;
    }

    /**
     * Retourneert ID van Persoon.
     *
     * @return ID.
     */
    public final Integer getID() {
        return persoon.getID();
    }

    /**
     * Retourneert Soort van Persoon.
     *
     * @return Soort.
     */
    public final SoortPersoonAttribuut getSoort() {
        return persoon.getSoort();
    }

    /**
     * Retourneert Afgeleid administratief van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige
     * record. Dit zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere
     * records tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error.
     * Daarnaast kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Afgeleid administratief van Persoon
     */
    @Override
    public final HisPersoonAfgeleidAdministratiefModel getAfgeleidAdministratief() {
        final List<HisPersoonAfgeleidAdministratiefModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonAfgeleidAdministratiefHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Identificatienummers van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige
     * record. Dit zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere
     * records tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error.
     * Daarnaast kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Identificatienummers van Persoon
     */
    @Override
    public final HisPersoonIdentificatienummersModel getIdentificatienummers() {
        final List<HisPersoonIdentificatienummersModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonIdentificatienummersHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Samengestelde naam van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige record.
     * Dit zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Samengestelde naam van Persoon
     */
    @Override
    public final HisPersoonSamengesteldeNaamModel getSamengesteldeNaam() {
        final List<HisPersoonSamengesteldeNaamModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonSamengesteldeNaamHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Geboorte van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit zou
     * altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Geboorte van Persoon
     */
    @Override
    public final HisPersoonGeboorteModel getGeboorte() {
        final List<HisPersoonGeboorteModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonGeboorteHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Geslachtsaanduiding van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige
     * record. Dit zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere
     * records tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error.
     * Daarnaast kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Geslachtsaanduiding van Persoon
     */
    @Override
    public final HisPersoonGeslachtsaanduidingModel getGeslachtsaanduiding() {
        final List<HisPersoonGeslachtsaanduidingModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonGeslachtsaanduidingHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Inschrijving van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit
     * zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Inschrijving van Persoon
     */
    @Override
    public final HisPersoonInschrijvingModel getInschrijving() {
        final List<HisPersoonInschrijvingModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonInschrijvingHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Nummerverwijzing van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige record.
     * Dit zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Nummerverwijzing van Persoon
     */
    @Override
    public final HisPersoonNummerverwijzingModel getNummerverwijzing() {
        final List<HisPersoonNummerverwijzingModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonNummerverwijzingHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Bijhouding van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit zou
     * altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Bijhouding van Persoon
     */
    @Override
    public final HisPersoonBijhoudingModel getBijhouding() {
        final List<HisPersoonBijhoudingModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonBijhoudingHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Overlijden van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit zou
     * altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Overlijden van Persoon
     */
    @Override
    public final HisPersoonOverlijdenModel getOverlijden() {
        final List<HisPersoonOverlijdenModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonOverlijdenHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Naamgebruik van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit
     * zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Naamgebruik van Persoon
     */
    @Override
    public final HisPersoonNaamgebruikModel getNaamgebruik() {
        final List<HisPersoonNaamgebruikModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonNaamgebruikHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Migratie van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit zou
     * altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Migratie van Persoon
     */
    @Override
    public final HisPersoonMigratieModel getMigratie() {
        final List<HisPersoonMigratieModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonMigratieHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Verblijfsrecht van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit
     * zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Verblijfsrecht van Persoon
     */
    @Override
    public final HisPersoonVerblijfsrechtModel getVerblijfsrecht() {
        final List<HisPersoonVerblijfsrechtModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonVerblijfsrechtHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Uitsluiting kiesrecht van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige
     * record. Dit zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere
     * records tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error.
     * Daarnaast kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Uitsluiting kiesrecht van Persoon
     */
    @Override
    public final HisPersoonUitsluitingKiesrechtModel getUitsluitingKiesrecht() {
        final List<HisPersoonUitsluitingKiesrechtModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonUitsluitingKiesrechtHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Deelname EU verkiezingen van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige
     * record. Dit zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere
     * records tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error.
     * Daarnaast kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Deelname EU verkiezingen van Persoon
     */
    @Override
    public final HisPersoonDeelnameEUVerkiezingenModel getDeelnameEUVerkiezingen() {
        final List<HisPersoonDeelnameEUVerkiezingenModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonDeelnameEUVerkiezingenHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
     * Retourneert Persoonskaart van Persoon. Deze methode bepaalt met behulp van het predikaat het geldige record. Dit
     * zou altijd 1 resultaat op moeten leveren. Als dit niet het geval is, betekent dit dat er meerdere records
     * tegelijkertijd geldig zijn, wat nooit zou mogen gebeuren. In deze situatie loggen we daarom een error. Daarnaast
     * kiezen we het eerste geldige record dat we vinden. Om er zeker van te zijn dat dit eerste record altijd
     * consistent wordt teruggegeven, worden de records eerst gesorteerd.
     *
     * @return Retourneert Persoonskaart van Persoon
     */
    @Override
    public final HisPersoonPersoonskaartModel getPersoonskaart() {
        final List<HisPersoonPersoonskaartModel> geldigRecord =
                new ArrayList(CollectionUtils.select(persoon.getPersoonPersoonskaartHistorie().getHistorie(), getPredikaat()));
        if (geldigRecord.size() > 1) {
            LOGGER.error(
                "Kon geen moment-view bepalen voor persoon met id {}. Er zijn meerdere ({}) "
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
    public Collection<PersoonVoornaamPredikaatView> getVoornamen() {
        final Set<PersoonVoornaamPredikaatView> result = new HashSet<PersoonVoornaamPredikaatView>();
        for (final PersoonVoornaamHisVolledig persoonVoornaamHisVolledig : persoon.getVoornamen()) {
            final PersoonVoornaamPredikaatView view = new PersoonVoornaamPredikaatView(persoonVoornaamHisVolledig, getPredikaat());
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
    public Collection<PersoonGeslachtsnaamcomponentPredikaatView> getGeslachtsnaamcomponenten() {
        final Set<PersoonGeslachtsnaamcomponentPredikaatView> result = new HashSet<PersoonGeslachtsnaamcomponentPredikaatView>();
        for (final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponentHisVolledig : persoon.getGeslachtsnaamcomponenten()) {
            final PersoonGeslachtsnaamcomponentPredikaatView view =
                    new PersoonGeslachtsnaamcomponentPredikaatView(persoonGeslachtsnaamcomponentHisVolledig, getPredikaat());
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
    public Collection<PersoonVerificatiePredikaatView> getVerificaties() {
        final Set<PersoonVerificatiePredikaatView> result = new HashSet<PersoonVerificatiePredikaatView>();
        for (final PersoonVerificatieHisVolledig persoonVerificatieHisVolledig : persoon.getVerificaties()) {
            final PersoonVerificatiePredikaatView view = new PersoonVerificatiePredikaatView(persoonVerificatieHisVolledig, getPredikaat());
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
    public Collection<PersoonNationaliteitPredikaatView> getNationaliteiten() {
        final Set<PersoonNationaliteitPredikaatView> result = new HashSet<PersoonNationaliteitPredikaatView>();
        for (final PersoonNationaliteitHisVolledig persoonNationaliteitHisVolledig : persoon.getNationaliteiten()) {
            final PersoonNationaliteitPredikaatView view = new PersoonNationaliteitPredikaatView(persoonNationaliteitHisVolledig, getPredikaat());
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
    public Collection<PersoonAdresPredikaatView> getAdressen() {
        final Set<PersoonAdresPredikaatView> result = new HashSet<PersoonAdresPredikaatView>();
        for (final PersoonAdresHisVolledig persoonAdresHisVolledig : persoon.getAdressen()) {
            final PersoonAdresPredikaatView view = new PersoonAdresPredikaatView(persoonAdresHisVolledig, getPredikaat());
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
    public Collection<PersoonIndicatiePredikaatView> getIndicaties() {
        final Set<PersoonIndicatiePredikaatView> result = new HashSet<PersoonIndicatiePredikaatView>();
        for (final PersoonIndicatieHisVolledig persoonIndicatieHisVolledig : persoon.getIndicaties()) {
            final PersoonIndicatiePredikaatView view = new PersoonIndicatiePredikaatView(persoonIndicatieHisVolledig, getPredikaat());
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
    public Collection<PersoonReisdocumentPredikaatView> getReisdocumenten() {
        final Set<PersoonReisdocumentPredikaatView> result = new HashSet<PersoonReisdocumentPredikaatView>();
        for (final PersoonReisdocumentHisVolledig persoonReisdocumentHisVolledig : persoon.getReisdocumenten()) {
            final PersoonReisdocumentPredikaatView view = new PersoonReisdocumentPredikaatView(persoonReisdocumentHisVolledig, getPredikaat());
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
    public Collection<BetrokkenheidPredikaatView> getBetrokkenheden() {
        final Set<BetrokkenheidPredikaatView> result = new HashSet<BetrokkenheidPredikaatView>();
        for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : persoon.getBetrokkenheden()) {
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
     * {@inheritDoc}
     */
    @Override
    public Collection<PersoonOnderzoekPredikaatView> getOnderzoeken() {
        final Set<PersoonOnderzoekPredikaatView> result = new HashSet<PersoonOnderzoekPredikaatView>();
        for (final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : persoon.getOnderzoeken()) {
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
    public Collection<PersoonVerstrekkingsbeperkingPredikaatView> getVerstrekkingsbeperkingen() {
        final Set<PersoonVerstrekkingsbeperkingPredikaatView> result = new HashSet<PersoonVerstrekkingsbeperkingPredikaatView>();
        for (final PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperkingHisVolledig : persoon.getVerstrekkingsbeperkingen()) {
            final PersoonVerstrekkingsbeperkingPredikaatView view =
                    new PersoonVerstrekkingsbeperkingPredikaatView(persoonVerstrekkingsbeperkingHisVolledig, getPredikaat());
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
    public Collection<PersoonAfnemerindicatiePredikaatView> getAfnemerindicaties() {
        final Set<PersoonAfnemerindicatiePredikaatView> result = new HashSet<PersoonAfnemerindicatiePredikaatView>();
        for (final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatieHisVolledig : persoon.getAfnemerindicaties()) {
            final PersoonAfnemerindicatiePredikaatView view = new PersoonAfnemerindicatiePredikaatView(persoonAfnemerindicatieHisVolledig, getPredikaat());
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
    public final PersoonIndicatiePredikaatView getIndicatieDerdeHeeftGezag() {
        PersoonIndicatiePredikaatView indicatieDerdeHeeftGezag = null;
        for (PersoonIndicatiePredikaatView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG) {
                indicatieDerdeHeeftGezag = persoonIndicatie;
            }
        }
        return indicatieDerdeHeeftGezag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatiePredikaatView getIndicatieOnderCuratele() {
        PersoonIndicatiePredikaatView indicatieOnderCuratele = null;
        for (PersoonIndicatiePredikaatView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_ONDER_CURATELE) {
                indicatieOnderCuratele = persoonIndicatie;
            }
        }
        return indicatieOnderCuratele;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatiePredikaatView getIndicatieVolledigeVerstrekkingsbeperking() {
        PersoonIndicatiePredikaatView indicatieVolledigeVerstrekkingsbeperking = null;
        for (PersoonIndicatiePredikaatView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING) {
                indicatieVolledigeVerstrekkingsbeperking = persoonIndicatie;
            }
        }
        return indicatieVolledigeVerstrekkingsbeperking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatiePredikaatView getIndicatieVastgesteldNietNederlander() {
        PersoonIndicatiePredikaatView indicatieVastgesteldNietNederlander = null;
        for (PersoonIndicatiePredikaatView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_VASTGESTELD_NIET_NEDERLANDER) {
                indicatieVastgesteldNietNederlander = persoonIndicatie;
            }
        }
        return indicatieVastgesteldNietNederlander;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatiePredikaatView getIndicatieBehandeldAlsNederlander() {
        PersoonIndicatiePredikaatView indicatieBehandeldAlsNederlander = null;
        for (PersoonIndicatiePredikaatView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER) {
                indicatieBehandeldAlsNederlander = persoonIndicatie;
            }
        }
        return indicatieBehandeldAlsNederlander;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatiePredikaatView getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument() {
        PersoonIndicatiePredikaatView indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument = null;
        for (PersoonIndicatiePredikaatView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT) {
                indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument = persoonIndicatie;
            }
        }
        return indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatiePredikaatView getIndicatieStaatloos() {
        PersoonIndicatiePredikaatView indicatieStaatloos = null;
        for (PersoonIndicatiePredikaatView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_STAATLOOS) {
                indicatieStaatloos = persoonIndicatie;
            }
        }
        return indicatieStaatloos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatiePredikaatView getIndicatieBijzondereVerblijfsrechtelijkePositie() {
        PersoonIndicatiePredikaatView indicatieBijzondereVerblijfsrechtelijkePositie = null;
        for (PersoonIndicatiePredikaatView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE) {
                indicatieBijzondereVerblijfsrechtelijkePositie = persoonIndicatie;
            }
        }
        return indicatieBijzondereVerblijfsrechtelijkePositie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON;
    }

}
