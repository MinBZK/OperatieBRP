/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EenheidSelectieInterval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.test.common.dsl.Ref;

/**
 * Representatie van dienst in de dsl.
 */
final class DienstParser {

    /**
     * sectienaam dienst.
     */
    static final String SECTIE_DIENST = "Dienst";

    private final List<Ref<Dienst>> result = Lists.newLinkedList();

    DienstParser() {

    }

    /**
     * Parsed de dienst
     * @param dienstbundel de parent dienstbundel
     * @param regelIterator de totale lijst van te parsen regels
     */
    void parse(final Dienstbundel dienstbundel, final ListIterator<DslSectie> regelIterator) {
        final DslSectie sectie = regelIterator.next();
        sectie.assertMetNaam(SECTIE_DIENST);

        final SoortDienst soortDienst = sectie.geefStringValue("srt")
                .map(DienstParser::geefSoortDienst).map(SoortDienst::parseId).orElseThrow(IllegalArgumentException::new);

        final Dienst dienst = new Dienst(dienstbundel, soortDienst);
        dienst.setDatumIngang(sectie.geefDatumInt("datingang").orElse(DatumUtil.gisteren()));
        dienst.setActueelEnGeldig(sectie.geefBooleanValue("indag").orElse(true));
        dienst.setActueelEnGeldigVoorSelectie(sectie.geefBooleanValue("indagsel").orElse(true));

        //optionals
        sectie.geefDatumInt("dateinde").ifPresent(dienst::setDatumEinde);
        sectie.geefDatumInt("EersteSelDat").ifPresent(dienst::setEersteSelectieDatum);
        sectie.geefBooleanValue("indblok").ifPresent(dienst::setIndicatieGeblokkeerd);
        sectie.geefStringValue("attenderingscriterium").ifPresent(dienst::setAttenderingscriterium);
        sectie.geefInteger("maxaantalzoekresultaten").ifPresent(dienst::setMaximumAantalZoekresultaten);
        sectie.geefInteger("Selinterval").ifPresent(dienst::setSelectieInterval);
        sectie.geefStringValue("EenheidSelinterval").map(EenheidSelectieInterval::valueOf).ifPresent(u -> dienst.setEenheidSelectieInterval(u.getId()));
        sectie.geefStringValue("NadereSelcriterium").ifPresent(dienst::setNadereSelectieCriterium);
        sectie.geefDatumInt("SelPeilmomMaterieelResultaat").ifPresent(dienst::setSelectiePeilmomentMaterieelResultaat);
        sectie.geefDatumInt("SelPeilmomFormeelResultaat").map(integer ->
                DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.vanIntegerNaarLocalDate(integer).atStartOfDay(DatumUtil.BRP_ZONE_ID)));
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

        dienstbundel.getDienstSet().add(dienst);
        sectie.geefStringValue("@ref").ifPresent(refValue -> result.addAll(bepaalRefs(refValue, dienst)));
    }

    private static List<Ref<Dienst>> bepaalRefs(String value, Dienst dienst) {
        StringTokenizer tokenizer = new StringTokenizer(value, ",");
        if (!tokenizer.hasMoreTokens()) {
            return Collections.singletonList(new Ref<>(null, dienst));
        }
        List<Ref<Dienst>> refs = Lists.newArrayList();
        while (tokenizer.hasMoreTokens()) {
            refs.add(new Ref<>(tokenizer.nextToken(), dienst));
        }
        return refs;
    }

    public List<Ref<Dienst>> getResult() {
        return result;
    }

    /**
     * Geeft soortDienst ID obv soortDienst naam
     * @param value dienst naam
     * @return dienst id
     */
    private static int geefSoortDienst(final String value) {
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

        if (EffectAfnemerindicaties.PLAATSING.getNaam().equals(value)) {
            return EffectAfnemerindicaties.PLAATSING;
        } else if (EffectAfnemerindicaties.VERWIJDERING.getNaam().equals(value)) {
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
}
