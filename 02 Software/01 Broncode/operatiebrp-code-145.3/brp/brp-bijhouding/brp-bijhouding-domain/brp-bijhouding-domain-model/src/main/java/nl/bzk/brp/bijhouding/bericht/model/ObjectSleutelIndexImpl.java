/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import nl.bzk.brp.bijhouding.dal.BetrokkenheidRepository;
import nl.bzk.brp.bijhouding.dal.OnderzoekRepository;
import nl.bzk.brp.bijhouding.dal.PersoonRepository;
import nl.bzk.brp.bijhouding.dal.RelatieRepository;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Deze class bevat een index om van objectsleutel naar bijbehorende objecten te komen. Daarnaast bevat deze class
 * functies om sleutels te valideren.
 * <p>
 * Deze class is niet threadsafe.
 */
public final class ObjectSleutelIndexImpl implements ObjectSleutelIndex {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final List<BmrEntiteit> elementenMetObjectSleutels = new ArrayList<>();
    private final Map<IndexSleutel, Number> mapObjectSleutelMetEntiteitTypeOpDatabaseId = new HashMap<>();
    private final Map<Class<? extends RootEntiteit>, Map<Number, RootEntiteit>> index = new HashMap<>();

    private boolean isInitialized;

    @Override
    public void voegToe(final Element element) {
        if (isInitialized) {
            throw new IllegalArgumentException("Toevoegen van objectsleutels op een reeds geinitialiseerde index is niet toegestaan.");
        }
        if (element instanceof BmrEntiteit && ((BmrEntiteit) element).heeftObjectSleutel() && ((BmrEntiteit) element).inObjectSleutelIndex()) {
            elementenMetObjectSleutels.add((BmrEntiteit) element);
        }
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public List<MeldingElement> initialize() {
        final List<MeldingElement> result = new ArrayList<>();
        if (!isInitialized) {
            for (final BmrEntiteit elementenMetObjectSleutel : elementenMetObjectSleutels) {
                try {
                    final RootEntiteit rootEntiteit = zoekRootEntiteit(elementenMetObjectSleutel);
                    mapObjectSleutelMetEntiteitTypeOpDatabaseId.put(
                            new IndexSleutel(elementenMetObjectSleutel.getEntiteitType(), elementenMetObjectSleutel.getObjectSleutel()),
                            rootEntiteit.getId());
                    index.putIfAbsent(elementenMetObjectSleutel.getEntiteitType(), new HashMap<>());
                    index.get(elementenMetObjectSleutel.getEntiteitType()).put(rootEntiteit.getId(), rootEntiteit);
                } catch (OngeldigeObjectSleutelException e) {
                    LOGGER.debug("Ongeldige object sleutel", e);
                    result.add(MeldingElement.getInstance(e.getRegel(), elementenMetObjectSleutel));
                }
            }
            isInitialized = true;
        }
        return result;
    }

    @Override
    public <T extends RootEntiteit> T getEntiteitVoorObjectSleutel(final Class<T> entiteitType, final String objectSleutel) {
        return getEntiteitVoorId(entiteitType, mapObjectSleutelMetEntiteitTypeOpDatabaseId.get(new IndexSleutel(entiteitType, objectSleutel)));
    }

    @Override
    public <T extends RootEntiteit> List<T> getEntiteiten(final Class<T> entiteitType) {
        final Map<Number, RootEntiteit> indexPerEntiteitType = getIndexPerEntiteitType(entiteitType);
        return new ArrayList<>(indexPerEntiteitType == null ? Collections.emptyList() : (Collection<T>) indexPerEntiteitType.values());
    }

    private <T extends RootEntiteit> Map<Number, RootEntiteit> getIndexPerEntiteitType(final Class<T> entiteitType) {
        if (!isInitialized) {
            throw new IllegalArgumentException("Entiteiten kunnen niet worden opgevraagd uit een niet geinitialiseerde ObjectSleutelIndex.");
        }
        return index.get(entiteitType);
    }

    @Override
    public <T extends RootEntiteit> T getEntiteitVoorId(final Class<T> entiteitType, final Number databaseId) {
        final Map<Number, RootEntiteit> indexPerEntiteitType = getIndexPerEntiteitType(entiteitType);
        if (databaseId == null || indexPerEntiteitType == null) {
            return null;
        } else {
            return (T) indexPerEntiteitType.get(databaseId);
        }
    }

    @Override
    public RootEntiteit voegToe(final RootEntiteit rootEntiteit) {
        if (!isInitialized) {
            throw new IllegalArgumentException("Toevoegen van root entiteiten op een niet geinitialiseerde index is niet toegestaan.");
        }
        if (rootEntiteit.getId() == null) {
            throw new IllegalArgumentException("Toevoegen van root entiteiten zonder id is niet toegestaan.");
        }
        index.putIfAbsent(rootEntiteit.getClass(), new HashMap<>());
        return index.get(rootEntiteit.getClass()).put(rootEntiteit.getId(), rootEntiteit);
    }

    @Override
    public <T extends RootEntiteit> void vervangEntiteitMetId(final Class<T> entiteitType, final Number databaseId, final T nieuweRootEntiteit) {
        if (!isInitialized) {
            throw new IllegalArgumentException("Vervangen van root entiteiten op een niet geinitialiseerde index is niet toegestaan.");
        }
        if (getEntiteitVoorId(entiteitType, databaseId) != null) {
            index.get(entiteitType).put(databaseId, nieuweRootEntiteit);
        }
    }

    @Override
    public <T extends RootEntiteit> void voegObjectSleutelToe(final String objectSleutel, final Class<T> entiteitType, final Number databaseId) {
        final IndexSleutel indexSleutel = new IndexSleutel(entiteitType, objectSleutel);
        if (mapObjectSleutelMetEntiteitTypeOpDatabaseId.containsKey(indexSleutel)) {
            throw new IllegalArgumentException("Deze sleutel komt al voor in de objectsleutel index.");
        }
        if (getEntiteitVoorId(entiteitType, databaseId) == null) {
            throw new IllegalArgumentException("Er bestaat geen persoon in deze index voor de gegeven type en id.");
        }
        mapObjectSleutelMetEntiteitTypeOpDatabaseId.put(indexSleutel, databaseId);
    }

    @Override
    public int size() {
        int result = 0;
        for (final Map<Number, RootEntiteit> entiteitMap : index.values()) {
            result += entiteitMap.size();
        }
        return result;
    }

    @Bedrijfsregel(Regel.R1835)
    private RootEntiteit zoekRootEntiteit(final BmrEntiteit element) throws OngeldigeObjectSleutelException {
        final RootEntiteit result;
        if (BijhoudingPersoon.class.equals(element.getEntiteitType())) {
            final ObjectSleutel persoonObjectSleutel = getObjectSleutelService().maakPersoonObjectSleutel(element.getObjectSleutel());
            result = BijhoudingPersoon.decorate(getPersoonRepository().findById(persoonObjectSleutel.getDatabaseId()));
            controleerRegel1833(persoonObjectSleutel, (BijhoudingPersoon) result);
        } else if (BijhoudingRelatie.class.equals(element.getEntiteitType())) {
            final long relatieId = getObjectSleutelService().maakRelatieObjectSleutel(element.getObjectSleutel()).getDatabaseId();
            result = BijhoudingRelatie.decorate(getRelatieRepository().findById(relatieId));
            controleerRegel2511((BijhoudingRelatie) result, (RelatieElement) element);
        } else if (BijhoudingBetrokkenheid.class.equals(element.getEntiteitType())) {
            final long betrokkenheidId = getObjectSleutelService().maakBetrokkenheidObjectSleutel(element.getObjectSleutel()).getDatabaseId();
            result = BijhoudingBetrokkenheid.decorate(getBetrokkenheidRepository().findById(betrokkenheidId));
            controleerRegel2512((BijhoudingBetrokkenheid) result, (BetrokkenheidElement) element);
        } else if (BijhoudingOnderzoek.class.equals(element.getEntiteitType())) {
            final long onderzoekId = getObjectSleutelService().maakOnderzoekObjectSleutel(element.getObjectSleutel()).getDatabaseId();
            result = BijhoudingOnderzoek.decorate(getOnderzoekRepository().findById(onderzoekId));
        } else {
            throw new IllegalArgumentException("Het volgende type wordt niet ondersteund door de ObjectSleutelIndex: " + element.getEntiteitType());
        }
        if (result == null) {
            throw new OngeldigeObjectSleutelException(Regel.R1835);
        }
        return result;
    }

    @Bedrijfsregel(Regel.R1833)
    private void controleerRegel1833(final ObjectSleutel persoonObjectSleutel, final BijhoudingPersoon persoon) throws OngeldigeObjectSleutelException {
        if (persoon != null && !persoonObjectSleutel.getVersie().equals(persoon.getLockVersie())) {
            throw new OngeldigeObjectSleutelException(Regel.R1833);
        }
    }

    @Bedrijfsregel(Regel.R2511)
    private void controleerRegel2511(final BijhoudingRelatie relatieEntiteit, final RelatieElement relatieElement) throws OngeldigeObjectSleutelException {
        if (relatieEntiteit != null && !relatieEntiteit.getSoortRelatie().equals(relatieElement.getSoortRelatie())) {
            throw new OngeldigeObjectSleutelException(Regel.R2511);
        }
    }

    @Bedrijfsregel(Regel.R2512)
    private void controleerRegel2512(final BijhoudingBetrokkenheid betrokkenheidEntiteit, final BetrokkenheidElement betrokkenheidElement)
            throws OngeldigeObjectSleutelException {
        if (betrokkenheidEntiteit != null && !betrokkenheidEntiteit.getSoortBetrokkenheid().equals(betrokkenheidElement.getSoort().getSoortBetrokkenheid())) {
            throw new OngeldigeObjectSleutelException(Regel.R2512);
        }
    }

    private PersoonRepository getPersoonRepository() {
        return ApplicationContextProvider.getPersoonRepository();
    }

    private RelatieRepository getRelatieRepository() {
        return ApplicationContextProvider.getRelatieRepository();
    }

    private BetrokkenheidRepository getBetrokkenheidRepository() {
        return ApplicationContextProvider.getBetrokkenheidRepository();
    }

    private OnderzoekRepository getOnderzoekRepository() {
        return ApplicationContextProvider.getOnderzoekRepository();
    }

    private ObjectSleutelService getObjectSleutelService() {
        return ApplicationContextProvider.getObjectSleutelService();
    }

    /**
     * IndexSleutel.
     */
    private static final class IndexSleutel {
        private final Class<? extends RootEntiteit> entiteitType;
        private final String objectSleutel;

        private IndexSleutel(final Class<? extends RootEntiteit> entiteitType, final String objectSleutel) {
            this.entiteitType = entiteitType;
            this.objectSleutel = objectSleutel;
        }

        @Override
        public boolean equals(final Object obj) {

            if (obj == this) {
                return true;
            }
            if (!(obj instanceof IndexSleutel)) {
                return false;
            }

            final IndexSleutel rhs = (IndexSleutel) obj;
            return new EqualsBuilder().append(this.entiteitType, rhs.entiteitType).append(this.objectSleutel, rhs.objectSleutel).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(entiteitType).append(objectSleutel).toHashCode();
        }
    }
}
