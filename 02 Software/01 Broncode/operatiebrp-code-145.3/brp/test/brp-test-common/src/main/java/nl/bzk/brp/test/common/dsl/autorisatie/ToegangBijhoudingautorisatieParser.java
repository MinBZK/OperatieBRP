/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import java.util.function.Function;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.dsl.DslSectie;

/**
 *
 */
final class ToegangBijhoudingautorisatieParser {


    private static final int DATUM_INGANG_BIJHAUT = 2001_01_01;
    /**
     * sectienaam toegang bijhoudingsautorisatie.
     */
    static final String SECTIE_TOEGANG_BIJHOUDINGAUTORISATIE = "Toegang Bijhouding autorisatie";


    private ToegangBijhoudingautorisatieParser() {}

    static ToegangBijhoudingsautorisatie parse(final DslSectie sectie,
                                               final Function<String, Partij> partijResolver, final Function<String, Partij> partijResolverOpOin,
                                               final String afleverpuntParam) {

        final Partij geautoriseerde = sectie.geefStringValue("geautoriseerde")
                .map(partijResolver).orElseThrow(IllegalArgumentException::new);
        final Rol rol = sectie.geefInteger("rol").map(Rol::parseId).orElse(Rol.BIJHOUDINGSORGAAN_COLLEGE);
        final PartijRol partijRol = geautoriseerde.getPartijRolSet().stream().filter(p -> p.getRol() == rol)
                .findFirst().orElseThrow(() -> new IllegalArgumentException(
                        String.format("PartijRol niet gevonden met rol %s voor partij '%s", rol, geautoriseerde.getNaam())));

        final ToegangBijhoudingsautorisatie tba =
                new ToegangBijhoudingsautorisatie(partijRol, maakBijhoudingsautorisatie(sectie));
        tba.setDatumIngang(sectie.geefDatumInt("datingang").orElse(DatumUtil.gisteren()));
        tba.setActueelEnGeldig(sectie.geefBooleanValue("indag").orElse(true));
        tba.setAfleverpunt(afleverpuntParam);

        //optionals
        sectie.geefDatumInt("dateinde").ifPresent(tba::setDatumEinde);
        sectie.geefBooleanValue("indblok").ifPresent(tba::setIndicatieGeblokkeerd);
        sectie.geefStringValue("transporteur").map(partijResolverOpOin).ifPresent(tba::setTransporteur);
        sectie.geefStringValue("ondertekenaar").map(partijResolverOpOin).ifPresent(tba::setOndertekenaar);

        //toevoegen historie
        ToegangBijhoudingsautorisatieHistorie tbaHis = new ToegangBijhoudingsautorisatieHistorie(tba,
                DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime()), tba.getDatumIngang());
        tbaHis.setDatumEinde(tba.getDatumEinde());
        tbaHis.setAfleverpunt(tba.getAfleverpunt());
        tbaHis.setIndicatieGeblokkeerd(tba.getIndicatieGeblokkeerd());
        tba.addToegangBijhoudingsautorisatieHistorieSet(tbaHis);

        return tba;
    }

    private static Bijhoudingsautorisatie maakBijhoudingsautorisatie(final DslSectie sectie) {
        Bijhoudingsautorisatie ba = new Bijhoudingsautorisatie(true);
        ba.setDatumIngang(DATUM_INGANG_BIJHAUT);
        ba.setDatumEinde(null);
        ba.setIndicatieGeblokkeerd(null);

        ba.setNaam(sectie.geefStringValue("naam").orElse("Huwelijk"));

        //toevoegen historie
        BijhoudingsautorisatieHistorie baHis = new BijhoudingsautorisatieHistorie(ba,
                DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime()), ba.getDatumIngang(), ba.getNaam());
        baHis.setDatumTijdVerval(null);
        baHis.setDatumIngang(DATUM_INGANG_BIJHAUT);
        baHis.setDatumEinde(null);
        baHis.setIndicatieGeblokkeerd(null);
        ba.addBijhoudingsautorisatieHistorieSet(baHis);

        return ba;
    }


}
