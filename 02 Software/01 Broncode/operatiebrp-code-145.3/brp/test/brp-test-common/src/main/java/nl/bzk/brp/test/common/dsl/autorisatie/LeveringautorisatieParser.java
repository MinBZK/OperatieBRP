/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import java.util.ListIterator;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.dsl.DslSectie;


/**
 */
final class LeveringautorisatieParser {

    /**
     * sectienaam leveringautorisatie.
     */
    private static final String SECTIE_LEVERINGAUTORISATIE = "Levering autorisatie";

    private final DienstbundelParser dienstbundelParser;

    LeveringautorisatieParser(final DienstbundelParser dienstbundelParser) {
        this.dienstbundelParser = dienstbundelParser;
    }

    /**
     * Parsed de leveringautorisatie.
     * @param regelIterator de totale lijst van te parsen regels
     */
    Leveringsautorisatie parse(final ListIterator<DslSectie> regelIterator) {
        final DslSectie sectie = regelIterator.next();
        sectie.assertMetNaam(SECTIE_LEVERINGAUTORISATIE);

        final Stelsel stelsel = sectie.geefStringValue("Stelsel").map(Stelsel::valueOf).orElse(Stelsel.BRP);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(stelsel, true);

        leveringsautorisatie.setDatumIngang(sectie.geefDatumInt("datingang").orElse(DatumUtil.gisteren()));
        leveringsautorisatie.setDatumEinde(sectie.geefDatumInt("dateinde").orElse(DatumUtil.morgen()));
        leveringsautorisatie.setActueelEnGeldig(sectie.geefBooleanValue("indag").orElse(true));
        leveringsautorisatie.setProtocolleringsniveau(sectie.geefInteger("protocolleringsniveau")
                .map(Protocolleringsniveau::parseId).orElse(Protocolleringsniveau.GEEN_BEPERKINGEN));

        //optionals
        sectie.geefStringValue("naam").ifPresent(leveringsautorisatie::setNaam);
        sectie.geefBooleanValue("indaliassrtadmhndleveren").ifPresent(leveringsautorisatie::setIndicatieAliasSoortAdministratieveHandelingLeveren);
        sectie.geefStringValue("populatiebeperking").ifPresent(leveringsautorisatie::setPopulatiebeperking);
        sectie.geefStringValue("toelichting").ifPresent(leveringsautorisatie::setToelichting);
        sectie.geefBooleanValue("indblok").ifPresent(leveringsautorisatie::setIndicatieGeblokkeerd);

        while (regelIterator.hasNext()) {
            final DslSectie next = regelIterator.next();
            next.assertMetNaam(DienstbundelParser.SECTIE_DIENSTBUNDEL);
            regelIterator.previous();
            dienstbundelParser.parse(leveringsautorisatie, regelIterator);
        }
        return leveringsautorisatie;
    }
}
