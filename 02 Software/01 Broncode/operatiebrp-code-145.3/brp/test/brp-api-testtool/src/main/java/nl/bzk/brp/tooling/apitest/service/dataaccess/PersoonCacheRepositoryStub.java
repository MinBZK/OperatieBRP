/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.algemeen.blob.VerantwoordingConverter;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import nl.bzk.brp.service.dalapi.PersoonRepository;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.springframework.util.Assert;

/**
 * Stub voor PersoonCacheRepository.
 */
final class PersoonCacheRepositoryStub implements PersoonCacheRepository, PersoonRepository,
        PersoonDataStubService {
    private static final Logger LOGGER = LoggerFactory.getLogger();


    @Inject
    private PartijService partijService;

    private Map<Long, PersoonCache> cacheMap;
    private Multimap<String, Long> bsnIdMap;
    private Multimap<Long, String> idBsnMap;
    private Multimap<String, Long> anrIdMap;
    private ListMultimap<Long, BRPActie> handelingIdActieMap;
    private Map<Long, Long> persIdLaatsteHandelingIdMap;
    private Map<Long, Long> persIdInitieleVullingHandelingIdMap;
    private Multimap<Long, Long> handelingPersIds;
    private Set<Long> pseudoPersonen;

    @Override
    public void reset() {
        cacheMap = null;
        bsnIdMap = null;
        idBsnMap = null;
        anrIdMap = null;
        handelingIdActieMap = null;
        persIdLaatsteHandelingIdMap = null;
        handelingPersIds = null;
        pseudoPersonen = null;
    }

    @Override
    public List<BRPActie> getActiesVanHandeling(final Long administratieveHandelingId) {
        return handelingIdActieMap.get(administratieveHandelingId);
    }

    @Override
    public Long getLaatsteHandelingVanPersoon(final String bsn) {
        final Collection<Long> persIds = geefPersIdsIngeschrevene(bsn);
        final Long aLong = persIdLaatsteHandelingIdMap.get(persIds.iterator().next());
        Assert.notNull(aLong, "Laatste handeling niet gevonden");
        return aLong;
    }

    @Override
    public Long getInitieleVullingHandelingVanPersoon(final String bsn) {
        final Collection<Long> persIds = geefPersIdsIngeschrevene(bsn);
        final Long aLong = persIdInitieleVullingHandelingIdMap.get(persIds.iterator().next());
        Assert.notNull(aLong, "Handeling mbt initiele vulling niet gevonden");
        return aLong;
    }

    @Override
    public void updatePersoonCache(Long persoonId, byte[] persoonBytes, byte[] afnemerIndicatieGegevens) {
        final PersoonCache persoonCache = haalPersoonCacheOp(persoonId);
        if (persoonBytes != null) {
            persoonCache.setPersoonHistorieVolledigGegevens(persoonBytes);
        }
        if (afnemerIndicatieGegevens != null) {
            persoonCache.setAfnemerindicatieGegevens(afnemerIndicatieGegevens);
        }
        cacheMap.put(persoonId, persoonCache);
    }

    @Override
    public List<Long> geefPersonenMetHandeling(final Long administratieveHandelingId) {
        return Lists.newArrayList(handelingPersIds.get(administratieveHandelingId));
    }

    @Override
    public Multimap<String, Long> getBsnIdMap() {
        return bsnIdMap;
    }

    @Override
    public Multimap<String, Long> getAnrIdMap() {
        return anrIdMap;
    }

    @Override
    public Multimap<Long, String> getIdBsnMap() {
        return idBsnMap;
    }

    @Override
    public Multimap<Long, String> getIdBsnMapZonderPseudopersonen() {
        final Multimap<Long, String> idBsnMapExclPseudo = HashMultimap.create();
        for (Map.Entry<Long, String> entry : idBsnMap.entries()) {
            if (!pseudoPersonen.contains(entry.getKey())) {
                idBsnMapExclPseudo.put(entry.getKey(), entry.getValue());
            }
        }
        return idBsnMapExclPseudo;
    }

    @Override
    public void laadPersonen(final List<String> blobDirStringList) {
        reset();

        LOGGER.info("Laad Personen: " + blobDirStringList);
        final List<BlobDirectory> blobdirList;
        try {
            blobdirList = BlobDirectory.map(blobDirStringList);
        } catch (IOException e) {
            throw new TestclientExceptie(e);
        }
        try {

            final BlobData blobData = new BlobData();
            if (blobdirList.size() == 1) {
                final BlobDirectory dir = Iterables.getOnlyElement(blobdirList);
                blobData.persoonBlobMap.putAll(dir.listPersoonBlobs());
                blobData.afnemerindicatiesBlobMap.putAll(dir.listAfnemerindicatieBlobs());
            } else {
                int iteratie = 0;
                for (BlobDirectory blobDir : blobdirList) {
                    final int keyAjustment = iteratie * 1000;
                    final Map<Long, PersoonBlobData> persoonBlobDataMap = blobDir.listPersoonBlobs();
                    for (Map.Entry<Long, PersoonBlobData> entry : persoonBlobDataMap.entrySet()) {
                        PersoonBlobData value = entry.getValue();
                        if (iteratie > 0) {
                            final BlobRoot blobRoot = value.getPersoonBlob().getPersoonsgegevens();
                            for (BlobRecord blobRecord : blobRoot.getRecordList()) {
                                blobRecord.setObjectSleutel(blobRecord.getObjectSleutel() + keyAjustment);
                                if (blobRecord.getParentObjectSleutel() != null) {
                                    blobRecord.setParentObjectSleutel(blobRecord.getParentObjectSleutel() + keyAjustment);
                                }

//                                if (blobRecord.getAttributen().containsKey(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId())) {
//                                    final Number bsnValue = (Number)
//                                        blobRecord.getAttributen().get(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
//                                    blobRecord.getAttributen().put(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(),
//                                        bsnValue.longValue() + iteratie * MULTIBLOB_ID_INCREMENT);
//                                }
                            }

                            final byte[] data = Blobber.toJsonBytes(value.getPersoonBlob());
                            final PersoonBlob persoonBlob = Blobber.deserializeNaarPersoonBlob(data);
                            value = new PersoonBlobData(persoonBlob, data);
                        }
                        final Long key = iteratie == 0 ? entry.getKey() : entry.getKey() + keyAjustment;
                        blobData.persoonBlobMap.put(key, value);
                    }

                    final Map<Long, AfnemerindicatiesBlobData> afnemerindicatiesBlobDataMap = blobDir.listAfnemerindicatieBlobs();
                    for (Map.Entry<Long, AfnemerindicatiesBlobData> entry : afnemerindicatiesBlobDataMap.entrySet()) {
                        final AfnemerindicatiesBlobData value = entry.getValue();
                        final Long key = iteratie == 0 ? entry.getKey() : entry.getKey() + keyAjustment;
                        blobData.afnemerindicatiesBlobMap.put(key, value);
                    }
                    iteratie++;
                }
            }
            setData(blobData);
        } catch (IOException | BlobException e) {
            throw new RuntimeException("Laad Personen mislukt", e);
        }
    }


    @Override
    public PersoonCache haalPersoonCacheOp(long persoonId) {
        return cacheMap.get(persoonId);
    }

    @Override
    public List<PersoonCache> haalPersoonCachesOp(final Set<Long> ids) {
        final List<PersoonCache> cacheList = Lists.newArrayListWithCapacity(ids.size());
        for (final Long id : ids) {
            final PersoonCache persoonCache = cacheMap.get(id);
            if (persoonCache != null) {
                cacheList.add(persoonCache);
            }
        }
        return cacheList;
    }

    @Override
    public Persoon haalPersoonOp(final long persoonId) {
        throw new Error("Persoon moet uit de BLOB komen: " + persoonId);
    }

    @Override
    public void updateAfnemerindicatieBlob(final long persoonId, final Long lockVersiePersoon, final Long afnemerIndicatieLockVersie) {
    }

    @Override
    public Set<Long> geefAllePersoonIds() {
        return behoudIngeschrevene(cacheMap.keySet());
    }

    @Override
    public boolean isPseudoPersoon(final long persoonId) {
        return pseudoPersonen.contains(persoonId);
    }

    private void setData(final BlobData blobData) throws IOException {
        handelingIdActieMap = LinkedListMultimap.create();
        persIdLaatsteHandelingIdMap = Maps.newHashMap();
        persIdInitieleVullingHandelingIdMap = Maps.newHashMap();
        handelingPersIds = HashMultimap.create();
        bsnIdMap = HashMultimap.create();
        idBsnMap = HashMultimap.create();
        anrIdMap = HashMultimap.create();
        pseudoPersonen = Sets.newHashSet();

        Assert.isTrue(!blobData.persoonBlobMap.isEmpty(), "Geen Persoonblob gevonden");
        LOGGER.debug("Aantal gevonden Persoon Blobs: " + blobData.persoonBlobMap.size());
        LOGGER.debug("Aantal gevonden Afnemerindicatie Blobs: " + blobData.afnemerindicatiesBlobMap.size());

        cacheMap = Maps.newHashMap();
        final Map<Long, PersoonBlob> persoonIdBlobMap = Maps.newHashMap();
        final Map<Long, Persoon> persoonIdPersoonMap = Maps.newHashMap();
        for (final Long persId : blobData.persoonBlobMap.keySet()) {
            final PersoonBlobData persoonBlobData = blobData.persoonBlobMap.get(persId);
            final AfnemerindicatiesBlobData afnemerindicatiesBlobData = blobData.afnemerindicatiesBlobMap.get(persId);
            final Persoon persoon = new Persoon(SoortPersoon.PSEUDO_PERSOON);
            persoon.setId(persId);
            final PersoonCache persoonCache = new PersoonCache(persoon, (short) 1);
            persoonCache.setPersoonHistorieVolledigGegevens(persoonBlobData.getData());

            if (afnemerindicatiesBlobData != null) {
                persoonCache.setAfnemerindicatieGegevens(afnemerindicatiesBlobData.getData());
            }
            cacheMap.put(persId, persoonCache);

            mapPersoonInfo(persId, persoonBlobData.getPersoonBlob());
            persoonIdBlobMap.put(persId, persoonBlobData.getPersoonBlob());
            persoonIdPersoonMap.put(persId, persoon);
        }
        mapHandeling(persoonIdBlobMap);

        for (Map.Entry<Long, String> entry : idBsnMap.entries()) {
            LOGGER.debug("Geladen persoon; ID={}    BSN={}  Pseudo={}", entry.getKey(), entry.getValue(), pseudoPersonen.contains(entry.getKey()));
        }
    }

    private void mapHandeling(final Map<Long, PersoonBlob> blobList) {
        final Multimap<AdministratieveHandeling, Actie> handelingActieMultimap = HashMultimap.create();
        for (final Map.Entry<Long, PersoonBlob> entry : blobList.entrySet()) {
            final Set<AdministratieveHandeling> handelingSet = VerantwoordingConverter.map(entry.getValue().getVerantwoording());
            for (final AdministratieveHandeling administratieveHandeling : handelingSet) {
                handelingPersIds.put(administratieveHandeling.getId(), entry.getKey());
                handelingActieMultimap.putAll(administratieveHandeling, administratieveHandeling.getActies());
                if (administratieveHandeling.getSoort() == SoortAdministratieveHandeling.GBA_INITIELE_VULLING) {
                    persIdInitieleVullingHandelingIdMap.put(entry.getKey(), administratieveHandeling.getId());
                }
            }

            for (final BlobRecord blobRecord : entry.getValue().getPersoonsgegevens().getRecordList()) {
                if (Element.PERSOON_AFGELEIDADMINISTRATIEF.getId() == blobRecord.getGroepElementId() && blobRecord.getActieVerval() == null) {
                    final Number o = (Number) blobRecord.getAttributen().get(Element.PERSOON_AFGELEIDADMINISTRATIEF_ADMINISTRATIEVEHANDELING.getId());
                    persIdLaatsteHandelingIdMap.put(entry.getKey(), o.longValue());
                    //break;
                }
            }
        }

        for (final AdministratieveHandeling key : handelingActieMultimap.keySet()) {
            final nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling handelingEntity = new nl.bzk.algemeenbrp.dal.domein.brp.entity
                    .AdministratieveHandeling(
                    partijService.vindPartijOpCode(key.getPartijCode()),
                    key.getSoort(),
                    new Timestamp(key.getTijdstipRegistratie().toInstant().toEpochMilli())
            );
            handelingEntity.setId(key.getId());
            handelingEntity.setToelichtingOntlening(key.getToelichtingOntlening());
            for (Actie actie : handelingActieMultimap.get(key)) {
                final BRPActie actieEntity = new BRPActie(actie.getSoort(), handelingEntity, handelingEntity.getPartij(), handelingEntity
                        .getDatumTijdRegistratie());
                actieEntity.setId(actie.getId());
                handelingEntity.addActie(actieEntity);
                handelingIdActieMap.put(handelingEntity.getId(), actieEntity);
            }
        }
    }

    private Collection<Long> geefPersIdsIngeschrevene(final String bsn) {
        final Collection<Long> persIds = behoudIngeschrevene(new HashSet<>(bsnIdMap.get(bsn)));
        if (persIds.isEmpty()) {
            throw new AssertionError("Persoon niet gevonden met bsn: " + bsn + " in set " + bsnIdMap.keySet());
        } else if (persIds.size() > 1) {
            throw new AssertionError("Meerdere personen gevonden met bsn: " + bsn);
        }
        return persIds;
    }

    private void mapPersoonInfo(final long persId, final PersoonBlob persoonBlob) {

        for (final BlobRecord blobRecord : persoonBlob.getPersoonsgegevens().getRecordList()) {
            if (Element.PERSOON_IDENTITEIT.getId() == blobRecord.getGroepElementId()) {
                final String soortCode = (String) blobRecord.getAttributen().get(Element.PERSOON_SOORTCODE.getId());
                final SoortPersoon soortPersoon = SoortPersoon.parseCode(soortCode);
                if (SoortPersoon.PSEUDO_PERSOON == soortPersoon) {
                    pseudoPersonen.add(persId);
                }
            } else if (Element.PERSOON_IDENTIFICATIENUMMERS.getId() == blobRecord.getGroepElementId()
                    && blobRecord.getActieVerval() == null
                    && blobRecord.getAttributen() != null) {
                final String bsn = "" + blobRecord.getAttributen().get(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId());
                if (bsn != null) {
                    bsnIdMap.put(bsn, persId);
                    idBsnMap.put(persId, bsn);
                }

                final String anr = "" + blobRecord.getAttributen().get(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId());
                if (anr != null) {
                    anrIdMap.put(anr, persId);
                }
            }
        }
    }


    private Set<Long> behoudIngeschrevene( final Set<Long> ids) {
        final HashSet<Long> idsTemp = new HashSet<>(ids);
        idsTemp.removeAll(pseudoPersonen);
        return idsTemp;
    }


}
