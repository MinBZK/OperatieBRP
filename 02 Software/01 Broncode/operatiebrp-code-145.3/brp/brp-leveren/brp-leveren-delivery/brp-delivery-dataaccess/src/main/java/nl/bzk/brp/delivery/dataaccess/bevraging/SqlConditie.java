/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.domain.element.AttribuutElement;

/**
 * SqlConditie.
 */
@Bedrijfsregel(Regel.R2281)
@Bedrijfsregel(Regel.R2291)
@Bedrijfsregel(Regel.R2293)
@Bedrijfsregel(Regel.R2292)
@Bedrijfsregel(Regel.R2294)
final class SqlConditie {
    private List<Object> parameters = new ArrayList<>();
    private String conditie;

    private SqlConditie() {
    }

    /**
     * @return parameters
     */
    public List<Object> getParameters() {
        return parameters;
    }

    /**
     * @return conditie
     */
    String getConditie() {
        return conditie;
    }

    /**
     * @param zoekCriterium ZoekCriterium
     * @param attribuutElement attribuutElement
     * @param alias alias
     * @param kolom kolom
     * @param nietLeeg nietLeeg
     * @return sql conditie
     */
    static SqlConditie maakConditie(final ZoekCriterium zoekCriterium, final AttribuutElement attribuutElement, final String alias,
                                    final String kolom, final boolean nietLeeg) {
        final SqlConditie sqlConditie = new SqlConditie();
        final String conditieStr;
        if (Zoekoptie.LEEG.equals(zoekCriterium.getZoekOptie())) {
            conditieStr = String.format("%s.%s is %s null ", alias, kolom, nietLeeg ? "not" : "");
        } else if (Zoekoptie.VANAF_KLEIN.equals(zoekCriterium.getZoekOptie()) || Zoekoptie.VANAF_EXACT.equals(zoekCriterium.getZoekOptie())) {
            if (attribuutElement.isDatum()) {
                final Integer bovengrens = DatumFormatterUtil.bepaalBovengrensDeelsOnbekendeDatum((Integer) zoekCriterium.getWaarde());
                sqlConditie.parameters.add(zoekCriterium.getWaarde());
                sqlConditie.parameters.add(bovengrens);
                conditieStr = String.format("%s.%s between (?) and (?) ", alias, kolom);
            } else {
                sqlConditie.parameters.add(zoekCriterium.getWaarde() + "%");
                if (Zoekoptie.VANAF_KLEIN.equals(zoekCriterium.getZoekOptie())) {
                    conditieStr = String.format("brp_unaccent(%s.%s) like brp_unaccent(?) and %s.%s is not null ", alias, kolom, alias, kolom);
                } else {
                    //vanaf exact
                    conditieStr = String.format("%s.%s like ? and %s.%s is not null ", alias, kolom, alias, kolom);
                }
            }
        } else if (Zoekoptie.KLEIN.equals(zoekCriterium.getZoekOptie())) {
            sqlConditie.parameters.add(zoekCriterium.getWaarde());
            conditieStr = String.format("brp_unaccent(%s.%s) = brp_unaccent(?) ", alias, kolom);

        } else {
            //exact
            sqlConditie.parameters.add(zoekCriterium.getWaarde());
            //als string
            if (attribuutElement.isString()) {
                sqlConditie.parameters.add(zoekCriterium.getWaarde());
                conditieStr = String.format("brp_unaccent(%s.%s) = brp_unaccent(?) and %s.%s = ? ", alias, kolom, alias, kolom);
            } else {
                conditieStr = String.format("%s.%s = ? ", alias, kolom);
            }
        }
        sqlConditie.conditie = conditieStr;
        return sqlConditie;
    }


}
