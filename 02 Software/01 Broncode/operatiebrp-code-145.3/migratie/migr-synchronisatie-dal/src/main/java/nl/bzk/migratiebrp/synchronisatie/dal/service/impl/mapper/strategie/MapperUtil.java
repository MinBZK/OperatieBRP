/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;

/**
 * Util voor gemeenschappelijke methoden voor de diverse Mappers.
 */
public final class MapperUtil {

    private MapperUtil() {
        throw new AssertionError("Er mag geen instantie van een MapperUtil class gemaakt worden.");
    }

    /**
     * Mapped de migratieHistorie op de formele historie attributen van meegegeven brp groep.
     * @param migratieHistorie de migratiemodel-groep.
     * @param brpGroep De BRP-groep.
     * @param onderzoekMapper de mapper voor onderzoeken
     * @param objecttype het objecttype van de gerelateerde persoon. Null als het om de 'eigen' persoon gaat.
     */
    public static void mapFormeleHistorieVanMigratie(
            final BrpHistorie migratieHistorie,
            final FormeleHistorieZonderVerantwoording brpGroep,
            final OnderzoekMapper onderzoekMapper,
            final Element objecttype) {

        brpGroep.setDatumTijdRegistratie(MapperUtil.mapBrpDatumTijdToTimestamp(migratieHistorie.getDatumTijdRegistratie()));
        brpGroep.setDatumTijdVerval(MapperUtil.mapBrpDatumTijdToTimestamp(migratieHistorie.getDatumTijdVerval()));
        if (brpGroep instanceof FormeleHistorie && migratieHistorie.getNadereAanduidingVerval() != null) {
            ((FormeleHistorie) brpGroep).setNadereAanduidingVerval(BrpCharacter.unwrap(migratieHistorie.getNadereAanduidingVerval()));
        }
        if (onderzoekMapper != null) {
            onderzoekMapper.mapOnderzoek(
                    brpGroep,
                    migratieHistorie.getDatumTijdRegistratie(),
                    OnderzoekMapperUtil.bepaalElement(brpGroep, OnderzoekMapperUtil.Historieveldnaam.REGISTRATIE, objecttype));
            onderzoekMapper.mapOnderzoek(
                    brpGroep,
                    migratieHistorie.getDatumTijdVerval(),
                    OnderzoekMapperUtil.bepaalElement(brpGroep, OnderzoekMapperUtil.Historieveldnaam.VERVAL, objecttype));
            onderzoekMapper.mapOnderzoek(
                    brpGroep,
                    migratieHistorie.getNadereAanduidingVerval(),
                    OnderzoekMapperUtil.bepaalElement(brpGroep, OnderzoekMapperUtil.Historieveldnaam.N_A_VERVAL, objecttype));
        }
    }

    /**
     * Mapped de migratieHistorie op de materiële historie attributen van meegegeven brp groep.
     * @param migratieHistorie de migratiemodel-groep.
     * @param historie De BRP-groep.
     * @param onderzoekMapper de mapper voor onderzoeken
     * @param objecttype het objecttype van de gerelateerde persoon. Null als het om de 'eigen' persoon gaat.
     */
    public static void mapHistorieVanMigratie(
            final BrpHistorie migratieHistorie,
            final MaterieleHistorie historie,
            final OnderzoekMapper onderzoekMapper,
            final Element objecttype) {
        mapFormeleHistorieVanMigratie(migratieHistorie, historie, onderzoekMapper, objecttype);

        historie.setDatumAanvangGeldigheid(MapperUtil.mapBrpDatumToInteger(migratieHistorie.getDatumAanvangGeldigheid()));
        historie.setDatumEindeGeldigheid(MapperUtil.mapBrpDatumToInteger(migratieHistorie.getDatumEindeGeldigheid()));

        if (onderzoekMapper != null) {
            onderzoekMapper.mapOnderzoek(
                    historie,
                    migratieHistorie.getDatumAanvangGeldigheid(),
                    OnderzoekMapperUtil.bepaalElement(historie, OnderzoekMapperUtil.Historieveldnaam.AANVANG, objecttype));
            onderzoekMapper.mapOnderzoek(
                    historie,
                    migratieHistorie.getDatumEindeGeldigheid(),
                    OnderzoekMapperUtil.bepaalElement(historie, OnderzoekMapperUtil.Historieveldnaam.EINDE, objecttype));
        }
    }

    /**
     * Mapped een BrpDatum object naar een Integer.
     * @param brpDatum de brpDatum die gemapped moet worden, mag null zijn
     * @return de BrpDatum als Integer of null als de brpDatum null is
     */
    public static Integer mapBrpDatumToInteger(final BrpDatum brpDatum) {
        if (!BrpValidatie.isAttribuutGevuld(brpDatum)) {
            return null;
        }
        return brpDatum.getWaarde();
    }

    /**
     * Mapped een BrpDatum object naar een Timestamp.
     * @param brpDatum de brpDatum die gemapped moet worden, mag null zijn
     * @return de BrpDatum als Timestamp of null als de brpDatum null is
     */
    public static Timestamp mapBrpDatumToTimestamp(final BrpDatum brpDatum) {
        if (!BrpValidatie.isAttribuutGevuld(brpDatum)) {
            return null;
        }
        return new Timestamp(BrpDatumTijd.fromDatum(brpDatum.getWaarde(), brpDatum.getOnderzoek()).getJavaDate().getTime());
    }

    /**
     * Mapped een BrpDatumTijd naar een Timestamp object.
     * @param brpDatumTijd de brpDatumTijd die gemapped moet worden, mag null zijn
     * @return de BrpDatumTijd als timestamp of null als brpDatumTijd null is
     */
    public static Timestamp mapBrpDatumTijdToTimestamp(final BrpDatumTijd brpDatumTijd) {
        if (!BrpValidatie.isAttribuutGevuld(brpDatumTijd)) {
            return null;
        }

        return new Timestamp(brpDatumTijd.getJavaDate().getTime());
    }

    /**
     * Mapped een Integer object naar een BigDecimal en null naar null.
     * @param integer de integer die gemapped moet worden naar BigDecimal, mag null zijn
     * @return de BigDecimal of null
     */
    public static Short mapIntegerToShort(final Integer integer) {
        if (integer == null) {
            return null;
        }
        return integer.shortValue();
    }

    /**
     * Mapped een BrpGeslachtsaanduidingCode naar een Geslachtsaanduiding entiteit.
     * @param brpGeslachtsaanduidingCode {@link BrpGeslachtsaanduidingCode} object
     * @return null als de input null is, anders de bijbehorende {@link Geslachtsaanduiding}
     */
    public static Geslachtsaanduiding mapBrpGeslachtsaanduidingCode(final BrpGeslachtsaanduidingCode brpGeslachtsaanduidingCode) {
        if (!BrpValidatie.isAttribuutGevuld(brpGeslachtsaanduidingCode)) {
            return null;
        }
        return Geslachtsaanduiding.parseCode(brpGeslachtsaanduidingCode.getWaarde());
    }
}
