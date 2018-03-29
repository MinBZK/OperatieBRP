/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import java.util.Arrays;
import java.util.ListIterator;
import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EenheidSelectieInterval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.test.common.dsl.Ref;
import org.apache.commons.lang.StringUtils;

/**
 * Representatie van dienst in de dsl.
 */
final class DienstParser {

    private Dienst dienst;
    private Optional<String> ref;

    /**
     * Maakt een nieuwe dienst parser
     * @param dienstbundel dienstbundel
     * @param sectie de sectie
     * @param idGenerator de id generator
     */
    private DienstParser(final DienstbundelParser dienstbundel, final DslSectie sectie, final IdGenerator idGenerator) {

        final SoortDienst soortDienst = sectie.geefStringValue("srt")
                .map(DienstParser::geefSoortDienst).map(SoortDienst::parseId).orElseThrow(IllegalArgumentException::new);

        dienst = new Dienst(dienstbundel.getDienstbundel(), soortDienst);
        dienst.setId(idGenerator.getDienstId().incrementAndGet());
        dienst.setDatumIngang(sectie.geefDatumInt("datingang").orElse(DatumUtil.gisteren()));
        dienst.setActueelEnGeldig(sectie.geefBooleanValue("indag").orElse(true));

        //optionals
        sectie.geefStringValue("attenderingscriterium").ifPresent(dienst::setAttenderingscriterium);
        sectie.geefDatumInt("dateinde").ifPresent(dienst::setDatumEinde);
        sectie.geefBooleanValue("indblok").ifPresent(dienst::setIndicatieGeblokkeerd);
        sectie.geefStringValue("attenderingscriterium").ifPresent(dienst::setAttenderingscriterium);
        sectie.geefInteger("maxaantalzoekresultaten").ifPresent(dienst::setMaximumAantalZoekresultaten);
        sectie.geefStringValue("srtsel").map(SoortSelectie::valueOf).ifPresent(soortSelectie -> dienst.setSoortSelectie(soortSelectie.getId()));
        sectie.geefInteger("Selinterval").ifPresent(dienst::setSelectieInterval);
        sectie.geefStringValue("EenheidSelinterval").map(EenheidSelectieInterval::valueOf).ifPresent(u -> dienst.setEenheidSelectieInterval(u.getId()));
        sectie.geefStringValue("NadereSelcriterium").ifPresent(dienst::setNadereSelectieCriterium);
        sectie.geefDatumInt("SelPeilmomMaterieelResultaat").ifPresent(dienst::setSelectiePeilmomentMaterieelResultaat);
        sectie.geefDatumInt("SelPeilmomFormeelResultaat").map(integer ->
                DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.vanIntegerNaarLocalDate(integer).atStartOfDay(DatumUtil.BRP_ZONE_ID)))
                .ifPresent(dienst::setSelectiePeilmomentFormeelResultaat);
        sectie.geefStringValue("HistorievormSel").map(HistorieVorm::getByNaam).
                ifPresent(historieVorm -> dienst.setHistorievormSelectie(historieVorm.getId()));
        sectie.geefBooleanValue("IndSelresultaatControleren").ifPresent(dienst::setIndicatieSelectieresultaatControleren);
        sectie.geefInteger("MaxAantalPersPerSelbestand").ifPresent(dienst::setMaxAantalPersonenPerSelectiebestand);
        sectie.geefInteger("MaxGrootteSelbestand").ifPresent(dienst::setMaxGrootteSelectiebestand);
        sectie.geefBooleanValue("IndVerzVolBerBijWijzAfniNaSe").ifPresent(dienst::setIndVerzVolBerBijWijzAfniNaSelectie);
        sectie.geefStringValue("effectafnemerindicaties").map(DienstParser::geefEffectPlaatsenAfnemerindicatie)
                .ifPresent(dienst::setEffectAfnemerindicaties);
        sectie.geefStringValue("SoortSelectie").map(DienstParser::geefSoortSelectie).map(SoortSelectie::getId)
                .ifPresent(dienst::setSoortSelectie);
        sectie.geefStringValue("EersteSelDat").map(DienstParser::mapEersteSelDat).ifPresent(dienst::setEersteSelectieDatum);
        sectie.geefInteger("SelInterval").ifPresent(dienst::setSelectieInterval);
        sectie.geefStringValue("EenheidSelInterval").map(DienstParser::mapEenheidSelInterval).ifPresent(dienst::setEenheidSelectieInterval);

        ref = sectie.geefStringValue("@ref");

    }

    private static Integer mapEersteSelDat(String eersteSelDat) {
        Integer waarde = null;
        if (StringUtils.isNumeric(eersteSelDat)) {
            waarde = Integer.parseInt(eersteSelDat);
        } else if ("vandaag".equals(eersteSelDat)) {
            waarde = DatumUtil.vandaag();
        }
        return waarde;
    }

    private static Integer mapEenheidSelInterval(String naam) {
        return EenheidSelectieInterval.valueOf(naam).getId();
    }

    /**
     *
     * @param collector
     */
    public void collect(final AutorisatieData collector) {
        collector.getDienstEntities().add(dienst);
        ref.ifPresent(s -> {collector.getDienstRefs().add(new Ref<>(s, dienst));});
    }

    public int getId() {
        return dienst.getId();
    }

    public Dienst getDienst() {
        return dienst;
    }

    /**
     * Geeft soortDienst ID obv soortDienst naam
     * @param value dienst naam
     * @return dienst id
     */
    public static int geefSoortDienst(final String value) {
        final SoortDienst[] values = SoortDienst.values();
        for (final SoortDienst soortDienst : values) {
            if (value.equals(soortDienst.getNaam())) {
                return soortDienst.getId();
            }
        }
        throw new IllegalStateException(value);
    }

    private static EffectAfnemerindicaties geefEffectPlaatsenAfnemerindicatie(final String value) {
        if (value == null) {
            return null;
        }

        if ("Plaatsing".equals(value)) {
            return EffectAfnemerindicaties.PLAATSING;
        } else if ("Verwijdering".equals(value)) {
            return EffectAfnemerindicaties.VERWIJDERING;
        }
        throw new IllegalStateException(String.format("Verkeerde waarde voor effectafnemerindicaties, mogelijke opties %s",
                Arrays.asList(EffectAfnemerindicaties.PLAATSING.getNaam(), EffectAfnemerindicaties.VERWIJDERING.getNaam())));
    }

    private static SoortSelectie geefSoortSelectie(final String value) {
        if (value == null) {
            return null;
        }

        for (final SoortSelectie soortSelectie : SoortSelectie.values()) {
            if (soortSelectie.getNaam().equals(value)) {
                return soortSelectie;
            }
        }
        throw new IllegalStateException(String.format("Verkeerde waarde voor SoortSelectie, mogelijke opties %s",
                Arrays.asList(SoortSelectie.STANDAARD_SELECTIE.getNaam(), SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE.getNaam(),
                        SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE.getNaam())));
    }

    /**
     * Parsed de dienst
     * @param dienstbundel de parent dienstbundel
     * @param regelIterator de totale lijst van te parsen regels
     * @param idGenerator de id generator
     * @return een geparsde dienst
     */
    static DienstParser parse(final DienstbundelParser dienstbundel, final ListIterator<DslSectie> regelIterator, final IdGenerator idGenerator) {
        final DslSectie dienstSectie = regelIterator.next();
        dienstSectie.assertMetNaam(AutorisatieDslUtil.SECTIE_DIENST);
        return new DienstParser(dienstbundel, dienstSectie, idGenerator);
    }
}
