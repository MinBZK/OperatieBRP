/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.brp.tooling.apitest.service.basis.Stateful;

/**
 * Service om data mbt Personen te laden.
 */
public interface PersoonDataStubService extends Stateful {

    /**
     * Get de acties behorende bij een gegeven handeling.
     * @param administratieveHandelingId id van de administratieve handeling
     * @return lijst met BRPActie entiteiten
     */
    List<BRPActie> getActiesVanHandeling(Long administratieveHandelingId);

    /**
     * @param bsn de bsn
     * @return Id van de laatste handeling van de persoon
     */
    Long getLaatsteHandelingVanPersoon(final String bsn);

    /**
     * @param administratieveHandelingId id van de administratievehandeling
     * @return lijst met persoonIds
     */
    List<Long> geefPersonenMetHandeling(Long administratieveHandelingId);

    /**
     * @return multimap
     */
    Multimap<String, Long> getBsnIdMap();

    /**
     * @return multimap
     */
    Multimap<String, Long> getAnrIdMap();

    /**
     * @return multimap
     */
    Multimap<Long, String> getIdBsnMap();

    /**
     * @return multimap
     */
    Multimap<Long, String> getIdBsnMapZonderPseudopersonen();

    /**
     * Laad de personen uit de gegeven blob director.
     * @param fileList directory met blobs.
     */
    void laadPersonen(List<String> fileList);

    /**
     * Geeft alle id's van persoon in cache.
     * @return set met id's
     */
    Set<Long> geefAllePersoonIds();

    /**
     * @param persoonId persoonId
     * @return pseudo
     */
    boolean isPseudoPersoon(long persoonId);

    /**
     * Geeft id van de administratieve handeling mbt initiele vulling GBA.
     * @param bsn burgerservicenummer
     * @return id van de administratieve handeling
     */
    Long getInitieleVullingHandelingVanPersoon(String bsn);

    /**
     * @param persoonId persoonId
     * @param persoonBytes persoonBytes
     * @param afnemerIndicatieGegevens afnemerIndicatieGegevens
     */
    void updatePersoonCache(Long persoonId, byte[] persoonBytes, byte[] afnemerIndicatieGegevens);
}
