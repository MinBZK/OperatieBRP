/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import nl.bzk.brp.tooling.apitest.autorisatie.Partijen;
import nl.bzk.brp.tooling.apitest.service.basis.BlobMutatieService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.AutorisatieStubService;
import nl.bzk.brp.tooling.apitest.service.dataaccess.PersoonDataStubService;
import org.springframework.util.Assert;

/**
 * Blobmutatieservice impl.
 */
public class BlobMutatieServiceImpl implements BlobMutatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String BSN = "bsn";
    private static final String LEVERING_AUTORISATIE_NAAM = "leveringsautorisatieNaam";
    private static final String PARTIJ_NAAM = "partijNaam";
    private static final String DATUM_EINDE_VOLGEN = "datumEindeVolgen";
    private static final String DATUM_AANVANG_MATERIELE_PERIODE = "datumAanvangMaterielePeriode";
    private static final String TS_REG = "tsReg";
    private static final String DIENST_ID = "dienstId";
    private static final String DIENST_ID_VERVAL = "dienstIdVerval";
    private static final String TS_VERVAL = "tsVerval";
    private static final String TS_LAATSTE_WIJZIGING_GBA_SYS = "tsLaatsteWijzigingGbaSystematiek";
    private static final Set<String> VERWIJDEREN_AFN_INDICATIES_KEYS = Sets.newHashSet(BSN);
    private static final Set<String> WIJZIG_TS_LAATSTE_WIJZ_GBA_SYSTEMATIEK_KEYS = Sets.newHashSet(BSN, TS_LAATSTE_WIJZIGING_GBA_SYS);
    private static final Set<String> TOEVOEGEN_AFN_INDICATIE_KEYS = Sets
            .newHashSet(BSN, LEVERING_AUTORISATIE_NAAM, PARTIJ_NAAM, DATUM_EINDE_VOLGEN,
                    DATUM_AANVANG_MATERIELE_PERIODE, TS_REG, DIENST_ID, DIENST_ID_VERVAL, TS_VERVAL);

    @Inject
    private PersoonDataStubService persoonDataStubService;

    @Inject
    @Named("maakLeveringsautorisatieRepository")
    private AutorisatieStubService autorisatieStubService;

    @Inject
    private PersoonCacheRepository persoonCacheRepository;

    @Override
    public final void zetAfnemerindicatiesOpPersoon(final List<Map<String, String>> params) {
        final List<Map<String, String>> paramValues = converteerParameterTabel(params, TOEVOEGEN_AFN_INDICATIE_KEYS);
        valideerKeys(paramValues.get(0), TOEVOEGEN_AFN_INDICATIE_KEYS);
        final Long persoonId = Iterables.getOnlyElement(persoonDataStubService.getBsnIdMap().get(paramValues.get(0).get(BSN)));
        final List<PersoonAfnemerindicatie> afnemerindicatieLijst = new ArrayList<>();
        for (Map<String, String> map : paramValues) {
            LOGGER.debug("Voeg afnemerindicatie toe aan persoon : " + map.get(BSN));
            afnemerindicatieLijst.add(maakAfnemerIndicatie(map));
        }

        final AfnemerindicatiesBlob afnemerindicatiesBlob = Blobber.maakBlob(Lists.newArrayList(afnemerindicatieLijst));
        final byte[] afnemerIndicatieGegevens;
        try {
            afnemerIndicatieGegevens = Blobber.toJsonBytes(afnemerindicatiesBlob);
        } catch (BlobException e) {
            throw new UnsupportedOperationException(e);
        }
        persoonDataStubService.updatePersoonCache(persoonId, null, afnemerIndicatieGegevens);
    }

    private PersoonAfnemerindicatie maakAfnemerIndicatie(final Map<String, String> paramMap) {
        final Long persoonId = Iterables.getOnlyElement(persoonDataStubService.getBsnIdMap().get(paramMap.get(BSN)));
        final PersoonCache persoonCache = persoonCacheRepository.haalPersoonCacheOp(persoonId);
        final Persoon persoon = persoonCache.getPersoon();
        final Partij partij = Partijen.getPartij(paramMap.get(PARTIJ_NAAM));
        final Leveringsautorisatie leveringsautorisatie = autorisatieStubService.getLeveringsautorisatie(paramMap.get(LEVERING_AUTORISATIE_NAAM));
        final PersoonAfnemerindicatie afnemerindicatie = new PersoonAfnemerindicatie(persoon, partij, leveringsautorisatie);
        afnemerindicatie.setId(1L);
        final Integer datumEindeVolgenWaarde = paramMap.get(DATUM_EINDE_VOLGEN) != null && !paramMap.get(DATUM_EINDE_VOLGEN).isEmpty()
                ? converteerStringDatumNaarInteger(paramMap.get(DATUM_EINDE_VOLGEN)) : null;
        afnemerindicatie.setDatumEindeVolgen(datumEindeVolgenWaarde);
        final Integer datumAanvangMatPeriodeWaarde =
                paramMap.get(DATUM_AANVANG_MATERIELE_PERIODE) != null && !paramMap.get(DATUM_AANVANG_MATERIELE_PERIODE).isEmpty()
                        ? converteerStringDatumNaarInteger(paramMap.get(DATUM_AANVANG_MATERIELE_PERIODE)) : null;
        afnemerindicatie.setDatumAanvangMaterielePeriode(datumAanvangMatPeriodeWaarde);

        final PersoonAfnemerindicatieHistorie afnemerindicatieHistorie = new PersoonAfnemerindicatieHistorie(afnemerindicatie);
        afnemerindicatieHistorie.setId(1L);
        afnemerindicatieHistorie.setDatumEindeVolgen(datumEindeVolgenWaarde);
        afnemerindicatieHistorie.setDatumAanvangMaterielePeriode(datumAanvangMatPeriodeWaarde);
        if (parameterHeeftWaarde(paramMap, TS_REG)) {
            afnemerindicatieHistorie.setDatumTijdRegistratie(converteerStringNaarTimeStamp(paramMap.get(TS_REG)));
        }
        if (parameterHeeftWaarde(paramMap, TS_VERVAL)) {
            afnemerindicatieHistorie.setDatumTijdVerval(converteerStringNaarTimeStamp(paramMap.get(TS_VERVAL)));
        }
        if (parameterHeeftWaarde(paramMap, DIENST_ID)) {
            afnemerindicatieHistorie
                    .setDienstInhoud(autorisatieStubService.getDienstUitLeveringsautorisatie(leveringsautorisatie, Integer.parseInt(paramMap.get(DIENST_ID))));
        }
        if (parameterHeeftWaarde(paramMap, DIENST_ID_VERVAL)) {
            afnemerindicatieHistorie.setDienstVerval(
                    autorisatieStubService.getDienstUitLeveringsautorisatie(leveringsautorisatie, Integer.parseInt(paramMap.get(DIENST_ID_VERVAL))));
        }

        afnemerindicatie.addPersoonAfnemerindicatieHistorieSet(afnemerindicatieHistorie);
        return afnemerindicatie;
    }

    @Override
    public final void verwijderAlleAfnemerindicatiesVanPersoon(final List<Map<String, String>> params) {

        final List<Map<String, String>> paramValues = converteerParameterTabel(params, VERWIJDEREN_AFN_INDICATIES_KEYS);
        valideerKeys(paramValues.get(0), VERWIJDEREN_AFN_INDICATIES_KEYS);
        for (Map<String, String> map : paramValues) {
            LOGGER.debug("Verwijder afnemerindicatie van persoon : " + paramValues.get(0).get(BSN));
            final Long persoonId = Iterables.getOnlyElement(persoonDataStubService.getBsnIdMap().get(map.get(BSN)));
            final AfnemerindicatiesBlob afnemerindicatiesBlob = Blobber.maakBlob(Lists.newArrayList());
            final byte[] afnemerIndicatieGegevens;
            try {
                afnemerIndicatieGegevens = Blobber.toJsonBytes(afnemerindicatiesBlob);
            } catch (BlobException e) {
                throw new UnsupportedOperationException(e);
            }
            persoonDataStubService.updatePersoonCache(persoonId, null, afnemerIndicatieGegevens);
        }
    }

    @Override
    public final void wijzigTijdstipLaatsteWijzigingGBASystematiekVanPersoon(final List<Map<String, String>> params) {

        final List<Map<String, String>> paramValues = converteerParameterTabel(params, WIJZIG_TS_LAATSTE_WIJZ_GBA_SYSTEMATIEK_KEYS);
        paramValues.get(0);
        valideerKeys(paramValues.get(0), WIJZIG_TS_LAATSTE_WIJZ_GBA_SYSTEMATIEK_KEYS);
        for (Map<String, String> map : paramValues) {
            if (parameterHeeftWaarde(map, TS_LAATSTE_WIJZIGING_GBA_SYS)) {
                LOGGER.debug("Wijzig tijdstip laatste wijziging GBA systematiek van persoon : " + map.get(BSN));
                final Long persoonId = Iterables.getOnlyElement(persoonDataStubService.getBsnIdMap().get(map.get(BSN)));
                final PersoonCache persoonCache = persoonCacheRepository.haalPersoonCacheOp(persoonId);
                final byte[] persoonHisVolledig = persoonCache.getPersoonHistorieVolledigGegevens();
                try {
                    final PersoonBlob persoonBlob = Blobber.deserializeNaarPersoonBlob(persoonHisVolledig);
                    final BlobRoot persGegevens = persoonBlob.getPersoonsgegevens();
                    final List<BlobRecord> blobRecordLijst = persGegevens.getRecordList();
                    for (BlobRecord blobRecord : blobRecordLijst) {
                        if (blobRecord.getGroepElementId().equals(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())) {
                            final Map<Integer, Object> attrs = blobRecord.getAttributen();
                            attrs.put(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId(),
                                    converteerStringNaarTimeStamp(map.get(TS_LAATSTE_WIJZIGING_GBA_SYS)));
                        }
                    }
                    final byte[] persoonJsonBytes = Blobber.toJsonBytes(persoonBlob);
                    persoonDataStubService.updatePersoonCache(persoonId, persoonJsonBytes, null);
                } catch (BlobException e) {
                    throw new UnsupportedOperationException(e);
                }
            }
        }
    }

    @Override
    public void pasBlobVoorPersoonEnAttribuutAanMetWaarde(final String bsn, final String attribuut, final String waarde) {
        final Long persoonId = Iterables.getOnlyElement(persoonDataStubService.getBsnIdMap().get(bsn));
        final PersoonCache persoonCache = persoonCacheRepository.haalPersoonCacheOp(persoonId);
        final byte[] persoonHisVolledig = persoonCache.getPersoonHistorieVolledigGegevens();
        try {
            final PersoonBlob persoonBlob = Blobber.deserializeNaarPersoonBlob(persoonHisVolledig);
            final BlobRoot persGegevens = persoonBlob.getPersoonsgegevens();
            final List<BlobRecord> blobRecordLijst = persGegevens.getRecordList();
            final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(attribuut);
            for (final BlobRecord blobRecord : blobRecordLijst) {
                if (blobRecord.getGroepElementId().equals(attribuutElement.getGroepId())) {
                    blobRecord.addAttribuut(attribuutElement.getId(), waarde);
                }
            }
            final byte[] persoonJsonBytes = Blobber.toJsonBytes(persoonBlob);
            persoonDataStubService.updatePersoonCache(persoonId, persoonJsonBytes, null);

        } catch (BlobException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private boolean parameterHeeftWaarde(final Map<String, String> paramMap, final String key) {
        return paramMap.get(key) != null && !paramMap.get(key).isEmpty();
    }

    private Integer converteerStringDatumNaarInteger(final String datum) {
        return Integer.parseInt(datum.replaceAll("-", ""));
    }

    private Timestamp converteerStringNaarTimeStamp(final String timeStampString) {
        return Timestamp.valueOf(timeStampString.replaceAll("T*Z*", ""));
    }

    private void valideerKeys(final Map<String, String> map, final Set<String> keys) {
        final Set<String> keyset = Sets.newHashSet(map.keySet());
        keyset.removeAll(keys);
        Assert.isTrue(keyset.isEmpty(), "Ongeldige keys: " + keyset);
    }

    private List<Map<String, String>> converteerParameterTabel(final List<Map<String, String>> params, final Set<String> keys) {
        final List<Map<String, String>> paramMaps = new ArrayList<>();
        for (final Map<String, String> rowMap : params) {
            final Map<String, String> map = Maps.newHashMap();
            for (String key : keys) {
                map.put(key, rowMap.get(key) != null ? rowMap.get(key).replace("'", "").trim() : null);
            }
            paramMaps.add(map);
        }
        return paramMaps;
    }

}
