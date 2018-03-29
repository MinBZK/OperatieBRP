/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.dsl.DslSectie;


/**
 */
final class LeveringautorisatieParser {

    private Leveringsautorisatie leveringsautorisatie;

    private List<DienstbundelParser> dienstBundels = new LinkedList<>();

    /**
     * Maakt een leveringautorisatie obv de gegeven sectie
     *
     * @param sectie      een sectie
     * @param idGenerator id generator
     */
    private LeveringautorisatieParser(final DslSectie sectie, final IdGenerator idGenerator) {
        final Stelsel stelsel = sectie.geefStringValue("Stelsel").map(Stelsel::valueOf).orElse(Stelsel.BRP);
        this.leveringsautorisatie = new Leveringsautorisatie(stelsel, true);
        leveringsautorisatie.setId(idGenerator.getLeveringsautorisatieId().incrementAndGet());
        leveringsautorisatie.setActueelEnGeldig(sectie.geefBooleanValue("indag").orElse(true));
        leveringsautorisatie.setProtocolleringsniveau(sectie.geefInteger("protocolleringsniveau")
                .map(Protocolleringsniveau::parseId).orElse(Protocolleringsniveau.GEEN_BEPERKINGEN));
        leveringsautorisatie.setDatumIngang(sectie.geefDatumInt("datingang").orElse(DatumUtil.gisteren()));

        //optionals
        sectie.geefStringValue("naam").ifPresent(leveringsautorisatie::setNaam);
        sectie.geefBooleanValue("indaliassrtadmhndleveren").ifPresent(leveringsautorisatie::setIndicatieAliasSoortAdministratieveHandelingLeveren);
        sectie.geefStringValue("populatiebeperking").ifPresent(leveringsautorisatie::setPopulatiebeperking);
        sectie.geefStringValue("toelichting").ifPresent(leveringsautorisatie::setToelichting);
        sectie.geefBooleanValue("indblok").ifPresent(leveringsautorisatie::setIndicatieGeblokkeerd);
        sectie.geefDatumInt("dateinde").ifPresent(leveringsautorisatie::setDatumEinde);
    }

    /**
     * Parsed de leveringautorisatie.
     *
     * @param regelIterator de totale lijst van te parsen regels
     * @param idGenerator   id generator
     * @return een geparsde dienst
     */
    static LeveringautorisatieParser parse(final ListIterator<DslSectie> regelIterator, final IdGenerator idGenerator) {
        final DslSectie sectie = regelIterator.next();
        sectie.assertMetNaam(AutorisatieDslUtil.SECTIE_LEVERINGAUTORISATIE);
        final LeveringautorisatieParser leveringautorisatie = new LeveringautorisatieParser(sectie, idGenerator);
        while (regelIterator.hasNext()) {
            final DslSectie next = regelIterator.next();
            next.assertMetNaam(AutorisatieDslUtil.SECTIE_DIENSTBUNDEL);
            regelIterator.previous();
            final DienstbundelParser dienstbundel = DienstbundelParser.parse(leveringautorisatie, regelIterator, idGenerator);
            leveringautorisatie.dienstBundels.add(dienstbundel);
        }
        return leveringautorisatie;
    }

    /**
     * Schrijft de leveringautorisatie SQL
     *
     * @param collector de writer om naartoe te schrijven
     */
    public void collect(final AutorisatieData collector) {
        collector.getLeveringsautorisatieEntities().add(leveringsautorisatie);
        for (final DienstbundelParser dienstBundel : dienstBundels) {
            dienstBundel.collect(collector);
        }
    }

    public int getId() {
        return leveringsautorisatie.getId();
    }

    public String getNaam() {
        return leveringsautorisatie.getNaam();
    }

    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }
}
