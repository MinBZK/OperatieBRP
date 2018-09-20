/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.bzk.brp.preview.model.BerichtStatistiek;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 *
 */
@Service
public class StatistiekenServiceImpl implements StatistiekenService {

    @PersistenceContext
    private EntityManager entityManager;

    @Value(value = "${statistiek.berichtsoort.geboorte:0}")
    private Integer       berichtsoortGeboorte;

    @Value(value = "${statistiek.berichtsoort.huwelijk:0}")
    private Integer       berichtsoortHuwelijk;

    @Value(value = "${statistiek.berichtsoort.verhuizing:0}")
    private Integer       berichtsoortVerhuizing;

    @Value(value = "${statistiek.berichtsoort.adrescorrectie:0}")
    private Integer       berichtsoortAdrescorrectie;

    @Override
    public BerichtStatistiek[] getStatistiek() {
        String basicSelect = "select count(*) from ber.ber where srt = %d and indprevalidatie = ";
        String twinSelect = basicSelect + "false union all " + basicSelect + "true";

        String format =
            twinSelect + " union all " + twinSelect + " union all " + twinSelect + " union all " + twinSelect;

        String qlString =
            String.format(format, berichtsoortGeboorte, berichtsoortGeboorte, berichtsoortHuwelijk,
                    berichtsoortHuwelijk, berichtsoortVerhuizing, berichtsoortVerhuizing, berichtsoortAdrescorrectie,
                    berichtsoortAdrescorrectie);

        Query query = entityManager.createNativeQuery(qlString);

        @SuppressWarnings("unchecked")
        List<BigInteger> resultList = query.getResultList();

        BerichtStatistiek[] statistieken = new BerichtStatistiek[4];

        statistieken[0] = new BerichtStatistiek(resultList.get(0).intValue(), resultList.get(1).intValue());
        statistieken[1] = new BerichtStatistiek(resultList.get(2).intValue(), resultList.get(3).intValue());
        statistieken[2] = new BerichtStatistiek(resultList.get(4).intValue(), resultList.get(5).intValue());
        statistieken[3] = new BerichtStatistiek(resultList.get(6).intValue(), resultList.get(7).intValue());

        return statistieken;
    }

}
