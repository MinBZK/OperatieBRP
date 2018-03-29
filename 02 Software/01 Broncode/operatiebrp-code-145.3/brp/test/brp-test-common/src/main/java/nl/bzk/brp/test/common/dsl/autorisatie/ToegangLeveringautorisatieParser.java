/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import com.google.common.collect.Lists;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.test.common.dsl.Ref;

/**
 */
final class ToegangLeveringautorisatieParser {

    /**
     * sectienaam toegang leveringsautorisatie.
     */
    static final String SECTIE_TOEGANG_LEVERINGAUTORISATIE = "Toegang Levering autorisatie";

    private final Function<String, Partij> partijResolver;
    private final String afleverpunt;
    private final List<Ref<ToegangLeveringsAutorisatie>> result = Lists.newLinkedList();

    ToegangLeveringautorisatieParser(final Function<String, Partij> partijResolver, final String afleverpunt) {
        this.partijResolver = partijResolver;
        this.afleverpunt = afleverpunt;
    }

    void parse(final Deque<DslSectie> deque, final Leveringsautorisatie leveringsautorisatie) {

        final DslSectie sectie = deque.pop();
        if (!SECTIE_TOEGANG_LEVERINGAUTORISATIE.equals(sectie.getSectieNaam())) {
            throw new IllegalStateException();
        }

        final Partij geautoriseerde = sectie.geefStringValue("geautoriseerde").map(partijResolver).orElseThrow(IllegalArgumentException::new);
        final Rol rol = sectie.geefInteger("rol").map(Rol::parseId).orElse(Rol.AFNEMER);
        final PartijRol partijRol = geautoriseerde.getPartijRolSet().stream().filter(pr -> pr.getRol() == rol)
                .findFirst().orElseThrow(() -> new IllegalArgumentException(
                        String.format("PartijRol niet gevonden met rol %s voor partij '%s", rol, geautoriseerde.getNaam())));

        final ToegangLeveringsAutorisatie toegang = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        toegang.setDatumIngang(sectie.geefDatumInt("datingang").orElse(DatumUtil.gisteren()));
        toegang.setActueelEnGeldig(sectie.geefBooleanValue("indag").orElse(true));
        //optional
        sectie.geefDatumInt("dateinde").ifPresent(toegang::setDatumEinde);
        sectie.geefStringValue("naderepopulatiebeperking").ifPresent(toegang::setNaderePopulatiebeperking);
        sectie.geefBooleanValue("indblok").ifPresent(toegang::setIndicatieGeblokkeerd);
        sectie.geefStringValue("transporteur").map(partijResolver).ifPresent(toegang::setTransporteur);
        sectie.geefStringValue("ondertekenaar").map(partijResolver).ifPresent(toegang::setOndertekenaar);
        if ("geen".equals(sectie.geefStringValue("afleverpunt").orElse(null))) {
            toegang.setAfleverpunt(null);
        } else {
            toegang.setAfleverpunt(afleverpunt);
        }

        result.add(new Ref<>(sectie.geefStringValue("@ref").orElse(null), toegang));
    }

    public List<Ref<ToegangLeveringsAutorisatie>> getResult() {
        return result;
    }
}
