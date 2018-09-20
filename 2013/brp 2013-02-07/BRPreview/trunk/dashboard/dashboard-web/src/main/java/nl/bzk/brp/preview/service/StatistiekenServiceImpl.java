/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.bzk.brp.preview.model.BerichtStatistiek;
import nl.bzk.brp.preview.model.Berichtsoort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * De Class StatistiekenServiceImpl, implementatie van de interface StatistiekenService.
 */
@Service
public class StatistiekenServiceImpl implements StatistiekenService {

    /** De entity manager. */
    @PersistenceContext
    private EntityManager entityManager;

    /** De berichtsoort geboorte. */
    @Value(value = "${statistiek.berichtsoort.geboorte:6}")
    private Integer       berichtsoortGeboorte;

    /** De berichtsoort huwelijk. */
    @Value(value = "${statistiek.berichtsoort.huwelijk:8}")
    private Integer       berichtsoortHuwelijk;

    /** De berichtsoort verhuizing. */
    @Value(value = "${statistiek.berichtsoort.verhuizing:2}")
    private Integer       berichtsoortVerhuizing;

    /** De berichtsoort adrescorrectie. */
    @Value(value = "${statistiek.berichtsoort.adrescorrectie:4}")
    private Integer       berichtsoortAdrescorrectie;

    /* (non-Javadoc)
     * @see nl.bzk.brp.preview.service.StatistiekenService#getStatistiek(java.util.Calendar)
     */
    @Override
    public Map<Berichtsoort, BerichtStatistiek> getStatistiek(final Calendar vanaf) {

        Map<Berichtsoort, BerichtStatistiek> statistieken = new HashMap<Berichtsoort, BerichtStatistiek>();
        statistieken.put(Berichtsoort.GEBOORTE, new BerichtStatistiek(0, 0));
        statistieken.put(Berichtsoort.HUWELIJK, new BerichtStatistiek(0, 0));
        statistieken.put(Berichtsoort.VERHUIZING, new BerichtStatistiek(0, 0));
        statistieken.put(Berichtsoort.ADRESCORRECTIE, new BerichtStatistiek(0, 0));

        String vanafConditie = "";

        if (vanaf != null) {
            vanafConditie = String.format(
                    "where tsverzenden > to_timestamp('%d-%d-%d %d:%d:%d', 'DD-MM-YYYY HH24:MI:SS')",
                    vanaf.get(Calendar.DAY_OF_MONTH), vanaf.get(Calendar.MONTH) + 1, vanaf.get(Calendar.YEAR),
                    vanaf.get(Calendar.HOUR_OF_DAY), vanaf.get(Calendar.MINUTE), vanaf.get(Calendar.SECOND));
        }

        String format = "select count(*), srt, indprevalidatie from ber.ber %s group by srt, indprevalidatie";

        String qlString = String.format(format, vanafConditie);

        Query query = entityManager.createNativeQuery(qlString);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = query.getResultList();

        for (Object[] objects : resultList) {
            Number aantal = (Number) objects[0];
            Number soortId = (Number) objects[1];
            Boolean isPrevalidatie = (Boolean) objects[2];
            if (soortId == null) {
                continue;
            } else if (soortId.intValue() == berichtsoortGeboorte.intValue()) {
                statistieken.get(Berichtsoort.GEBOORTE).set(isPrevalidatie, aantal);
            } else if (soortId.intValue() == berichtsoortHuwelijk.intValue()) {
                statistieken.get(Berichtsoort.HUWELIJK).set(isPrevalidatie, aantal);
            } else if (soortId.intValue() == berichtsoortVerhuizing.intValue()) {
                statistieken.get(Berichtsoort.VERHUIZING).set(isPrevalidatie, aantal);
            } else if (soortId.intValue() == berichtsoortAdrescorrectie.intValue()) {
                statistieken.get(Berichtsoort.ADRESCORRECTIE).set(isPrevalidatie, aantal);
            }
        }

        return statistieken;
    }

}
