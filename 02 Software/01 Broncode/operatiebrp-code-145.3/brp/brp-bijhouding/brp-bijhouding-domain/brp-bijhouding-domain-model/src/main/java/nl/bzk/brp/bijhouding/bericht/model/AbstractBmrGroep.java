/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECT_SLEUTEL_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.REFERENTIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.VOORKOMEN_SLEUTEL_ATTRIBUUT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import nl.bzk.brp.bijhouding.dal.PersoonRepository;

/**
 * Bevat de attributen die op elke groep zitten.
 */
public abstract class AbstractBmrGroep extends AbstractElement implements BmrGroep {

    private final Map<String, String> attributen;

    /**
     * een lege lijst, het resultaat wanneer de validatie OK is.
     */
    static final List<MeldingElement> VALIDATIE_OK = Collections.emptyList();


    /**
     * Maakt een AbstractBmrGroep object.
     * @param attributen de lijst met attributen
     */
    protected AbstractBmrGroep(final Map<String, String> attributen) {
        ValidatieHelper.controleerOpNullWaarde(attributen, "attributen");
        this.attributen = initLinkedHashMap(attributen);
    }

    /**
     * Deze methode kan gebruikt worden in de constructors om een (shallow) kopie te maken van list parameters die om
     * kan gaan met null waarden.
     * @param list de lijst die gekopieerd moet worden, of null
     * @param <T> het type van de lijst
     * @return de kopie of een lege lijst als list null is
     */
    protected final <T> List<T> initArrayList(final List<T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(list);
    }

    /**
     * Deze methode kan gebruikt worden in de constructors om een (shallow) kopie te maken van hashmap parameters die om
     * kan gaan met null waarden.
     * @param map de map die gekopieerd moet worden, of null
     * @param <K> het type van de key uit de map
     * @param <V> het type van de waarde uit de map
     * @return de kopie of een lege map als map null is
     */
    protected final <K, V> Map<K, V> initLinkedHashMap(final Map<K, V> map) {
        if (map == null) {
            return Collections.emptyMap();
        }
        return new LinkedHashMap<>(map);
    }

    /**
     * Valideert de inhoud van deze groep en valideert niet de inhoud van de groep binnen deze groep.
     * @return de lijst met meldingen, wanneer er geen fouten zijn wordt een lege lijst geretoureerd.
     */
    protected abstract List<MeldingElement> valideerInhoud();

    @Override
    public final String getCommunicatieId() {
        return attributen.get(COMMUNICATIE_ID_ATTRIBUUT.toString());
    }

    @Override
    public final String getVoorkomenSleutel() {
        return attributen.get(VOORKOMEN_SLEUTEL_ATTRIBUUT.toString());
    }

    @Override
    public final Map<String, String> getAttributen() {
        return Collections.unmodifiableMap(attributen);
    }

    @Override
    public final List<MeldingElement> valideer() {
        final List<MeldingElement> result = new ArrayList<>();
        final BmrGroepMetaInfo groepMetaInfo = BmrGroepMetaInfo.getInstance(this.getClass());
        for (final BmrFieldMetaInfo fieldMetaInfo : groepMetaInfo.getChildElementMetaInfoLijst()) {
            final Object element = fieldMetaInfo.invokeGetterMethod(this);
            if (element instanceof BmrGroep) {
                result.addAll(((BmrGroep) element).valideer());
            } else if (element instanceof BmrAttribuut) {
                result.addAll(((BmrAttribuut<?>) element).valideer(this));
            } else if (element instanceof Collection) {
                final Collection bmrGroepen = (Collection) element;
                for (final Object groep : bmrGroepen) {
                    result.addAll(((BmrGroep) groep).valideer());
                }
            }
        }
        result.addAll(valideerInhoud());
        return result;
    }

    /**
     * Geef de dynamische stamtabel repository.
     * @return de dynamische stamtabel repository
     */
    protected final DynamischeStamtabelRepository getDynamischeStamtabelRepository() {
        return ApplicationContextProvider.getDynamischeStamtabelRepository();
    }

    /**
     * Geef de Persoon repository.
     * @return de persoon repository
     */
    protected final PersoonRepository getPersoonRepository() {
        return ApplicationContextProvider.getPersoonRepository();
    }

    /**
     * Builder class voor attributen binnen het bijhoudingsmodel.
     */
    public static final class AttributenBuilder {
        private String communicatieId;
        private String referentieId;
        private String objecttype;
        private String objectSleutel;
        private String voorkomenSleutel;

        /**
         * Zet de waarde van communicatieId.
         * @param waarde waarde
         * @return AttributenBuilder
         */
        public AttributenBuilder communicatieId(final String waarde) {
            communicatieId = waarde;
            return this;
        }

        /**
         * Zet de waarde van referentieId.
         * @param waarde waarde
         * @return AttributenBuilder
         */
        public AttributenBuilder referentieId(final String waarde) {
            referentieId = waarde;
            return this;
        }

        /**
         * Zet de waarde van objecttype.
         * @param waarde waarde
         * @return AttributenBuilder
         */
        public AttributenBuilder objecttype(final String waarde) {
            objecttype = waarde;
            return this;
        }

        /**
         * Zet de waarde van objectSleutel.
         * @param waarde waarde
         * @return AttributenBuilder
         */
        public AttributenBuilder objectSleutel(final String waarde) {
            objectSleutel = waarde;
            return this;
        }

        /**
         * Zet de waarde van voorkomenSleutel.
         * @param waarde waarde
         * @return AttributenBuilder
         */
        public AttributenBuilder voorkomenSleutel(final String waarde) {
            voorkomenSleutel = waarde;
            return this;
        }

        /**
         * Maak een Map van attributen.
         * @return de map met attributen
         */
        public Map<String, String> build() {
            final Map<String, String> result = new LinkedHashMap<>();
            if (communicatieId != null) {
                result.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), communicatieId);
            }
            if (referentieId != null) {
                result.put(REFERENTIE_ID_ATTRIBUUT.toString(), referentieId);
            }
            if (objecttype != null) {
                result.put(OBJECTTYPE_ATTRIBUUT.toString(), objecttype);
            }
            if (objectSleutel != null) {
                result.put(OBJECT_SLEUTEL_ATTRIBUUT.toString(), objectSleutel);
            }
            if (voorkomenSleutel != null) {
                result.put(VOORKOMEN_SLEUTEL_ATTRIBUUT.toString(), voorkomenSleutel);
            }
            return result;
        }
    }
}
