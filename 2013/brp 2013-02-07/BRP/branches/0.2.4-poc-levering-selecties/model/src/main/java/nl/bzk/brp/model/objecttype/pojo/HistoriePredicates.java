/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.pojo;

import java.util.Date;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.FormeleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorie;

/**
 * TODO: [sasme] Add documentation
 */
public final class HistoriePredicates {
    private HistoriePredicates() {
        // geen instantie nodig.
    }

    public static GeldigOp geldigOp(Datum datum) {
        return new GeldigOp(datum);
    }

    public static BekendOp bekendOp(Datum datum) {
        return new BekendOp(datum);
    }

    private static class GeldigOp implements Predicate<MaterieleHistorie> {
        private final Datum peilDatum;

        public GeldigOp(Datum datum) {
            this.peilDatum = datum;
        }

        @Override
        public boolean apply(@Nullable final MaterieleHistorie materieleHistorie) {
            return (materieleHistorie.getDatumAanvangGeldigheid().voor(peilDatum) ||
                    peilDatum.getWaarde().equals(materieleHistorie.getDatumAanvangGeldigheid().getWaarde()))
                    && (materieleHistorie.getDatumEindeGeldigheid() == null ||
                    materieleHistorie.getDatumEindeGeldigheid().na(peilDatum));
        }
    }

    private static class BekendOp implements Predicate<FormeleHistorie> {
        private final Datum peilDatum;

        public BekendOp(Datum datum) {
            this.peilDatum = datum;
        }

        public boolean apply(@Nullable final FormeleHistorie formeleHistorie) {
            Datum aanvangDatum = formeleHistorie.getDatumTijdRegistratie() != null ? new Datum(formeleHistorie.getDatumTijdRegistratie().getWaarde()) : null;
            Datum vervalDatum = formeleHistorie.getDatumTijdVerval() != null ? new Datum(formeleHistorie.getDatumTijdVerval().getWaarde()) : null;

            return (vervalDatum == null || vervalDatum.na(peilDatum)) && (aanvangDatum.voor(peilDatum) || aanvangDatum.getWaarde().equals(peilDatum.getWaarde()));
        }

    }
}
