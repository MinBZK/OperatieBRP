/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import java.util.ListIterator;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.dsl.DslSectie;

/**
 * Representatie van dienstbundel in de dsl.
 */
final class DienstbundelParser {

    /**
     * sectienaam dienstbundel.
     */
    static final String SECTIE_DIENSTBUNDEL = "DienstenBundel";

    private final DienstbundelGroepParser dienstbundelGroepParser;
    private final DienstbundelGroepAttribuutParser dienstbundelGroepAttribuutParser;
    private final Lo3RubriekParser lo3RubriekParser;
    private final DienstParser dienstParser;

    DienstbundelParser(final DienstbundelGroepParser dienstbundelGroepParser,
                       final DienstbundelGroepAttribuutParser dienstbundelGroepAttribuutParser,
                       final Lo3RubriekParser lo3RubriekParser, final DienstParser dienstParser) {
        this.dienstbundelGroepParser = dienstbundelGroepParser;
        this.dienstbundelGroepAttribuutParser = dienstbundelGroepAttribuutParser;
        this.lo3RubriekParser = lo3RubriekParser;
        this.dienstParser = dienstParser;
    }


    /**
     * Parsed de dienstbundel.
     * @param leveringautorisatie de parent leveringautorisatie
     * @param regelIterator de totale lijst van te parsen regels
     */
    void parse(final Leveringsautorisatie leveringautorisatie,
                      final ListIterator<DslSectie> regelIterator) {
        final DslSectie sectie = regelIterator.next();
        sectie.assertMetNaam(SECTIE_DIENSTBUNDEL);

        final Dienstbundel dienstbundel = new Dienstbundel(leveringautorisatie);
        leveringautorisatie.getDienstbundelSet().add(dienstbundel);

        dienstbundel.setDatumIngang(sectie.geefDatumInt("datingang").orElse(DatumUtil.gisteren()));
        dienstbundel.setActueelEnGeldig(sectie.geefBooleanValue("indag").orElse(true));

        //optionals
        sectie.geefStringValue("naam").ifPresent(dienstbundel::setNaam);
        sectie.geefDatumInt("dateinde").ifPresent(dienstbundel::setDatumEinde);
        sectie.geefStringValue("naderepopulatiebeperking").ifPresent(dienstbundel::setNaderePopulatiebeperking);
        sectie.geefBooleanValue("indnaderepopbeperkingvolconv").ifPresent(dienstbundel::setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd);
        sectie.geefStringValue("toelichting").ifPresent(dienstbundel::setToelichting);
        sectie.geefBooleanValue("indblok").ifPresent(dienstbundel::setIndicatieGeblokkeerd);
        sectie.geefStringValue("Geautoriseerde Groepen").ifPresent(s -> dienstbundelGroepParser.parse(dienstbundel, s));
        sectie.geefStringValue("Geautoriseerde attributen").ifPresent(s -> dienstbundelGroepAttribuutParser.parse(dienstbundel, s));
        sectie.geefStringValue("Rubrieken").ifPresent(s -> lo3RubriekParser.parse(dienstbundel, s));

        while (regelIterator.hasNext()) {
            final DslSectie dienstSectie = regelIterator.next();
            if (!DienstParser.SECTIE_DIENST.equals(dienstSectie.getSectieNaam())) {
                regelIterator.previous();
                break;
            }
            dienstSectie.assertMetNaam(DienstParser.SECTIE_DIENST);
            regelIterator.previous();

            dienstParser.parse(dienstbundel, regelIterator);
        }
    }
}
