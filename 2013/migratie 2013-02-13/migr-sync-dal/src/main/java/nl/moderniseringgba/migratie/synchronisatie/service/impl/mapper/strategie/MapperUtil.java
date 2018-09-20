/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie;

import java.math.BigDecimal;
import java.sql.Timestamp;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.FormeleHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MaterieleHistorie;

/**
 * Util voor gemeenschappelijke methoden voor de diverse Mappers.
 */
public final class MapperUtil {

    private MapperUtil() {
        throw new AssertionError("Er mag geen instantie van een MapperUtil class gemaakt worden.");
    }

    /**
     * Mapped de migratieHistorie op de formele historie attributen van meegegeven brp groep.
     * 
     * @param migratieHistorie
     *            de migratiemodel-groep.
     * @param brpGroep
     *            De BRP-groep.
     */
    public static void mapFormeleHistorieVanMigratie(
            final BrpHistorie migratieHistorie,
            final FormeleHistorie brpGroep) {
        brpGroep.setDatumTijdRegistratie(mapBrpDatumTijdToTimestamp(migratieHistorie.getDatumTijdRegistratie()));
        brpGroep.setDatumTijdVerval(mapBrpDatumTijdToTimestamp(migratieHistorie.getDatumTijdVerval()));
    }

    /**
     * Mapped de migratieHistorie op de materiële historie attributen van meegegeven brp groep.
     * 
     * @param migratieHistorie
     *            de migratiemodel-groep.
     * @param historie
     *            De BRP-groep.
     */
    public static void mapHistorieVanMigratie(final BrpHistorie migratieHistorie, final MaterieleHistorie historie) {
        historie.setDatumTijdRegistratie(mapBrpDatumTijdToTimestamp(migratieHistorie.getDatumTijdRegistratie()));
        historie.setDatumTijdVerval(mapBrpDatumTijdToTimestamp(migratieHistorie.getDatumTijdVerval()));
        historie.setDatumAanvangGeldigheid(mapBrpDatumToBigDecimal(migratieHistorie.getDatumAanvangGeldigheid()));
        historie.setDatumEindeGeldigheid(mapBrpDatumToBigDecimal(migratieHistorie.getDatumEindeGeldigheid()));
    }

    /**
     * Mapped een BrpDatum object naar een BigDecimal.
     * 
     * @param brpDatum
     *            de brpDatum die gemapped moet worden, mag null zijn
     * @return de BrpDatum als BigDecimal of null als de brpDatum null is
     */
    public static BigDecimal mapBrpDatumToBigDecimal(final BrpDatum brpDatum) {
        if (brpDatum == null) {
            return null;
        }
        return BigDecimal.valueOf(brpDatum.getDatum());
    }

    /**
     * Mapped een BrpDatumTijd naar een Timestamp object.
     * 
     * @param brpDatumTijd
     *            de brpDatumTijd die gemapped moet worden, mag null zijn
     * @return de BrpDatumTijd als timestamp of null als brpDatumTijd null is
     */
    public static Timestamp mapBrpDatumTijdToTimestamp(final BrpDatumTijd brpDatumTijd) {
        if (brpDatumTijd == null) {
            return null;
        }

        return new Timestamp(brpDatumTijd.getJavaDate().getTime());
    }

    /**
     * Mapped een Integer object naar een BigDecimal en null naar null.
     * 
     * @param integer
     *            de integer die gemapped moet worden naar BigDecimal, mag null zijn
     * @return de BigDecimal of null
     */
    public static BigDecimal mapIntegerToBigDecimal(final Integer integer) {
        if (integer == null) {
            return null;
        }
        return BigDecimal.valueOf(integer.longValue());
    }
}
