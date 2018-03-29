/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.afnemerindicatie;

import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;

/**
 * API interface naar onderhoud afnemerindicatie.
 */
public interface OnderhoudAfnemerindicatieApiService {

    /**
     * bsn param.
     */
    String BSN                                              = "bsn";
    /**
     * testAfnemerindicatiePartij params. Nuttig voor monkeytesten waarbij abusievelijk de verkeerde
     * partij wordt gezet.
     */
    String AFNEMERINDICATIE_ANDERE_PARTIJ                   = "testAfnemerindicatiePartij";
    /**
     * datumEindeVolgen param.
     */
    String DATUM_EINDE_VOLGEN = "datumEindeVolgen";
    /**
     * datumAanvangMaterielePeriode params.
     */
    String DATUM_AANVANG_MATERIELE_PERIODE = "datumAanvangMaterielePeriode";

    /**
     * Alle mogelijke keys voor onderhoud afnemerindicatie.
     */
    Set<String> KEYS = Sets.union(VerzoekService.GENERIEKE_KEYS, Sets.newHashSet(BSN, AFNEMERINDICATIE_ANDERE_PARTIJ, DATUM_EINDE_VOLGEN,
        DATUM_AANVANG_MATERIELE_PERIODE));

    /**
     * Voert het technisch verzoek 'verwijder afnemerindicatie' uit.
     *
     * @param map tabel met params.
     */
    void verwijderAfnemerindicatie(Map<String, String> map);

    /**
     * Voert het technisch verzoek 'plaats afnemerindicatie' uit.
     *
     * @param map tabel met params.
     */
    void plaatsAfnemerindicatie(Map<String, String> map);

    /**
     * Voert het XML verzoek 'Plaats Afnemerindicatie' uit.
     *
     * @param xmlVerzoek file met het XML verzoek.
     */
    void plaatsAfnemerindicatieMetXml(XmlVerzoek xmlVerzoek);

    /**
     * Voert het verzoek 'Verwijder Afnemerindicatie' uit.
     *
     * @param xmlVerzoek file met het XML verzoek.
     */
    void verwijderAfnemerindicatieMetXml(XmlVerzoek xmlVerzoek);
}
