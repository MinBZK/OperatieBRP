/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.StamgegevenTabel;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.element.SoortInhoud;
import nl.bzk.brp.service.dalapi.StamTabelRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


/**
 * StamTabelCache implementatie.  {@link  StamTabelCache}.
 */
@Component
@Bedrijfsregel(Regel.R1332)
public final class StamTabelCacheImpl implements StamTabelCache {

    /**
     * stamtabel cache naam.
     */
    public static final String CACHE_NAAM = "STAMTABEL_CACHE";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private StamTabelRepository stamTabelRepository;

    @Inject
    private BrpCache brpCache;

    private StamTabelCacheImpl() {
    }


    @Override
    public StamtabelGegevens geefSynchronisatieStamgegevensUitRepository(final String tabelNaam) {
        return getCache().stamgegevens.get(tabelNaam);
    }

    @Override
    public CacheEntry herlaad() {
        return herlaadImpl();
    }

    /**
     * Herlaad de stam tabel.
     */
    private CacheEntry herlaadImpl() {
        LOGGER.debug("Start herladen cache");
        final List<StamgegevenTabel> tabellen = maakStamgegevenTabellen();
        final Map<String, StamtabelGegevens> stamgegevens = new HashMap<>();
        final Map<Integer, Map<Number, String>> stamgegevenidCodeMap = new HashMap<>();
        for (StamgegevenTabel stamgegevenTabel : tabellen) {
            final List<Map<String, Object>> stamgegevensVoorTabel = stamTabelRepository.vindAlleStamgegevensVoorTabel(stamgegevenTabel);
            if (stamgegevensVoorTabel == null) {
                continue;
            }
            //maak id map, voor stamgegevens referentie naar code/naam op id
            final Map<Number, String> idCodeMap = maakIdMap(stamgegevensVoorTabel);
            stamgegevenidCodeMap.put(stamgegevenTabel.getObjectElement().getId(), idCodeMap);
            final StamtabelGegevens stamtabelGegevens = new StamtabelGegevens(stamgegevenTabel, stamgegevensVoorTabel);
            stamgegevens.put(stamgegevenTabel.getNaam() + StamtabelGegevens.TABEL_POSTFIX, stamtabelGegevens);
        }

        overschrijfStamgegevenReferentie(stamgegevens, stamgegevenidCodeMap);

        final Data data = new Data(stamgegevens);
        LOGGER.debug("Einde herladen cache");
        return new CacheEntry(CACHE_NAAM, data);
    }

    private ListMultimap<Integer, AttribuutElement> maakLookupMap() {
        final Set<Integer> alleTeLeverenStamtabellen = new HashSet<>();
        for (ObjectElement objectElement : ElementHelper.getObjecten()) {
            if (StringUtils.isNotEmpty(objectElement.getSoortInhoud()) && !(SoortInhoud.D.getCode().equals(objectElement.getSoortInhoud()))) {
                alleTeLeverenStamtabellen.add(objectElement.getId());
            }
        }

        final ListMultimap<Integer, AttribuutElement> attribuutMultimap = ArrayListMultimap.create();
        for (AttribuutElement attribuutElement : ElementHelper.getAttributen()) {
            if (alleTeLeverenStamtabellen.contains(attribuutElement.getObjectType())) {
                attribuutMultimap.put(attribuutElement.getObjectType(), attribuutElement);
            }
        }
        return attribuutMultimap;
    }

    private List<StamgegevenTabel> maakStamgegevenTabellen() {
        final ListMultimap<Integer, AttribuutElement> attribuutMultimap = maakLookupMap();
        final List<StamgegevenTabel> tabellen = new ArrayList<>();
        for (Integer stamtabelObjectId : attribuutMultimap.keySet()) {
            final ObjectElement objectElement = ElementHelper.getObjectElement(stamtabelObjectId);
            final List<AttribuutElement> stamgegevenAttributenInBericht = new ArrayList<>();
            final List<AttribuutElement> stamgegevenAttributen = new ArrayList<>();
            final List<AttribuutElement> attribuutElements = attribuutMultimap.get(stamtabelObjectId);
            for (AttribuutElement attribuutElement : attribuutElements) {
                voegAttributenToe(stamgegevenAttributenInBericht, stamgegevenAttributen, attribuutElement);
            }
            if (stamgegevenAttributen.isEmpty()) {
                continue;
            }
            stamgegevenAttributenInBericht.sort(Comparator.comparingInt(ElementObject::getVolgnummer));

            final StamgegevenTabel stamgegevenTabel = new StamgegevenTabel(objectElement, stamgegevenAttributenInBericht, stamgegevenAttributen);
            tabellen.add(stamgegevenTabel);
        }
        return tabellen;
    }

    private void voegAttributenToe(List<AttribuutElement> stamgegevenAttributenInBericht, List<AttribuutElement> stamgegevenAttributen,
                                   AttribuutElement attribuutElement) {
        if (StringUtils.isNotEmpty(attribuutElement.getElement().getElementWaarde().getIdentdb())) {
            if (attribuutElement.inBericht()) {
                stamgegevenAttributenInBericht.add(attribuutElement);
            }
            stamgegevenAttributen.add(attribuutElement);
        }
    }

    /**
     * Stamgegevens kunnen zelf ook referenties zijn naar andere stamtabel, element soortElement -> soortElement.
     * @param stamgegevens stamgegevens
     * @param stamgegevenIdLookupMap stamgegevenIdLookupMap
     */
    private void overschrijfStamgegevenReferentie(final Map<String, StamtabelGegevens> stamgegevens,
                                                  final Map<Integer, Map<Number, String>> stamgegevenIdLookupMap) {
        for (StamtabelGegevens stamtabelGegevens : stamgegevens.values()) {
            for (Map<String, Object> stringObjectMap : stamtabelGegevens.getStamgegevens()) {
                for (AttribuutElement attribuutElement : stamtabelGegevens.getStamgegevenTabel().getStamgegevenAttributenInBericht()) {
                    voegStamgegevenReferentieToe(stamgegevens, stamgegevenIdLookupMap, stringObjectMap, attribuutElement);

                }
            }
        }
    }

    private void voegStamgegevenReferentieToe(Map<String, StamtabelGegevens> stamgegevens, Map<Integer, Map<Number, String>> stamgegevenIdLookupMap,
                                              Map<String, Object> stringObjectMap, AttribuutElement attribuutElement) {
        //kijk of attr niet zelf een stamgegeven is, het kan een stamgegeven zijn als attribuutelement type heeft van soort <> D
        if (attribuutElement.isStamgegevenReferentie()) {
            final ElementObject objectElement = ElementHelper.getObjectElement(attribuutElement.getType());
            //is zelf een stamgegeven
            final Number id = (Number) stringObjectMap.get(attribuutElement.getElement().getElementWaarde().getIdentdb().toLowerCase());
            final StamtabelGegevens stamtabelGegevensRef = stamgegevens.get(objectElement.getNaam() + StamtabelGegevens.TABEL_POSTFIX);
            if (stamtabelGegevensRef != null) {
                final Map<Number, String> idLookupMap = stamgegevenIdLookupMap.get(objectElement.getId());
                stringObjectMap.put(attribuutElement.getElement().getElementWaarde().getIdentdb().toLowerCase(), idLookupMap.get(id));
            }
        }
    }

    private Map<Number, String> maakIdMap(final List<Map<String, Object>> stamgegevensVoorTabel) {
        final Map<Number, String> idCodeMap = new HashMap<>();
        for (Map<String, Object> stringObjectMap : stamgegevensVoorTabel) {
            final Object code = stringObjectMap.get("code");
            final Object naam = stringObjectMap.get("naam");
            final String id = "id";
            if (code != null) {
                idCodeMap.put((Number) stringObjectMap.get(id), code.toString());
            } else if (naam != null) {
                idCodeMap.put((Number) stringObjectMap.get(id), naam.toString());
            }
        }
        return idCodeMap;
    }


    private Data getCache() {
        return (Data) this.brpCache.getCache(CACHE_NAAM);
    }

    /**
     * Data. Data holder for swap in
     */
    private static class Data {
        private final Map<String, StamtabelGegevens> stamgegevens;

        Data(final Map<String, StamtabelGegevens> stamgegevens) {
            final ImmutableMap.Builder<String, StamtabelGegevens> stamtabelGegevensBuilder = ImmutableMap.builder();
            for (Map.Entry<String, StamtabelGegevens> stringStamtabelGegevensEntry : stamgegevens.entrySet()) {
                stamtabelGegevensBuilder.put(stringStamtabelGegevensEntry.getKey(), stringStamtabelGegevensEntry.getValue());
            }
            this.stamgegevens = stamtabelGegevensBuilder.build();
        }
    }
}
