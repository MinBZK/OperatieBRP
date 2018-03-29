/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.dsl.DslSectie;

/**
 * Representatie van dienstbundel in de dsl.
 */
final class DienstbundelParser {
    private       Dienstbundel                                     dienstbundel;
    private final List<DienstbundelGroepParser>          groepen;
    private final List<DienstbundelGroepAttribuutParser> attributen;

    private List<DienstParser> diensten = new LinkedList<>();

    /**
     * Constructor.
     *
     * @param leveringautorisatie leveringautorisatie
     * @param sectie              de dsl sectie
     * @param idGenerator         de id generator
     */
    private DienstbundelParser(final LeveringautorisatieParser leveringautorisatie, final DslSectie sectie, final IdGenerator
        idGenerator)
    {
        dienstbundel = new Dienstbundel(leveringautorisatie.getLeveringsautorisatie());
        dienstbundel.setId(idGenerator.getDienstbundelId().incrementAndGet());
        dienstbundel.setDatumIngang(sectie.geefDatumInt("datingang").orElse(DatumUtil.gisteren()));
        dienstbundel.setActueelEnGeldig(sectie.geefBooleanValue("indag").orElse(true));

        sectie.geefStringValue("naam").ifPresent(dienstbundel::setNaam);
        sectie.geefDatumInt("dateinde").ifPresent(dienstbundel::setDatumEinde);
        sectie.geefStringValue("naderepopulatiebeperking").ifPresent(dienstbundel::setNaderePopulatiebeperking);
        sectie.geefBooleanValue("indnaderepopbeperkingvolconv").ifPresent(dienstbundel::setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd);
        sectie.geefStringValue("toelichting").ifPresent(dienstbundel::setToelichting);
        sectie.geefBooleanValue("indblok").ifPresent(dienstbundel::setIndicatieGeblokkeerd);

        groepen = sectie.geefStringValue("Geautoriseerde Groepen")
                .map(s -> DienstbundelGroepParser.parse(this, s, idGenerator)).orElse(null);
        attributen = sectie.geefStringValue("Geautoriseerde attributen")
                .map(s -> DienstbundelGroepAttribuutParser.parse(this, s, idGenerator)).orElse(null);
    }

    /**
     * @param collector collector
     */
    public void collect(final AutorisatieData collector) {
        collector.getDienstbundelEntities().add(dienstbundel);

        for (final DienstParser dienstParser : diensten) {
            dienstParser.collect(collector);
        }
        for (final DienstbundelGroepParser dienstbundelGroepParser : groepen) {
            dienstbundelGroepParser.collect(collector);
        }
        for (final DienstbundelGroepAttribuutParser attr : attributen) {
            attr.collect(collector);
        }
    }

    public int getId() {
        return dienstbundel.getId();
    }

    public List<DienstbundelGroepParser> getGroepen() {
        return groepen;
    }

    public List<DienstbundelGroepAttribuutParser> getAttributen() {
        return attributen;
    }

    public String getNaam() {
        return dienstbundel.getNaam();
    }

    public List<DienstParser> getDiensten() {
        return diensten;
    }

    public Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Parsed de dienstbundel.
     *
     * @param leveringautorisatie de parent leveringautorisatie
     * @param regelIterator       de totale lijst van te parsen regels
     * @param idGenerator         de id generator
     * @return een geparsde dienstbundel
     */
    static DienstbundelParser parse(final LeveringautorisatieParser leveringautorisatie,
                                    final ListIterator<DslSectie> regelIterator, final IdGenerator idGenerator)
    {
        final DslSectie dienstbundelSectie = regelIterator.next();
        dienstbundelSectie.assertMetNaam(AutorisatieDslUtil.SECTIE_DIENSTBUNDEL);
        final DienstbundelParser dienstbundel = new DienstbundelParser(leveringautorisatie, dienstbundelSectie, idGenerator);
        while (regelIterator.hasNext()) {
            final DslSectie dienstSectie = regelIterator.next();
            if (!AutorisatieDslUtil.SECTIE_DIENST.equals(dienstSectie.getSectieNaam())) {
                regelIterator.previous();
                break;
            }
            dienstSectie.assertMetNaam(AutorisatieDslUtil.SECTIE_DIENST);
            regelIterator.previous();
            final DienstParser dienst = DienstParser.parse(dienstbundel, regelIterator, idGenerator);
            dienstbundel.diensten.add(dienst);
        }
        return dienstbundel;
    }
}
