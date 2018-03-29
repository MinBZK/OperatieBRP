/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.synchronisatie;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import nl.bzk.brp.tooling.apitest.service.basis.VerzoekService;

/**
 * API interface voor module synchronisatie.
 */
public interface SynchronisatieApiService {

    /**
     * stamgegeven param.
     */
    String STAMGEGEVEN                                      = "stamgegeven";
    /**
     * bsn param.
     */
    String BSN                                              = "bsn";

    /**
     * Alle mogelijke keys voor synchroniseer persoon.
     */
    Set<String> KEYS_SYNCHRONISEER_PERSOON = Sets.union(VerzoekService.GENERIEKE_KEYS, Sets.newHashSet(BSN));

    /**
     * Alle mogelijke keys voor synchroniseer stamgegeven.
     */
    Set<String> KEYS_SYNCHRONISEER_STAMGEGEVEN = Sets.union(VerzoekService.GENERIEKE_KEYS, Sets.newHashSet(STAMGEGEVEN));

    /**
     * Voert het technisch verzoek 'Synchroniseer Persoon' uit.
     *
     * @param map tabel met params.
     * @throws IOException als er een fout optreed
     */
    void synchroniseerPersoon(Map<String, String> map) throws IOException;

    /**
     * Voert het technisc verzoek 'Synchroniseer Stamgegeven' uit.
     *
     * @param map tabel met params.
     */
    void synchroniseerStamgegeven(Map<String, String> map);

    /**
     * Voert het XML verzoek 'Synchroniseer Persoon' uit.
     *
     * @param xmlVerzoek file met het XML verzoek.
     */
    void synchroniseerPersoonMetXml(XmlVerzoek xmlVerzoek);

    /**
     * Voert het XML verzoek 'Synchroniseer Stamgegeven' uit.
     *
     * @param xmlVerzoek file met het XML verzoek.
     */
    void synchroniseerStamgegevenMetXml(XmlVerzoek xmlVerzoek);


}
