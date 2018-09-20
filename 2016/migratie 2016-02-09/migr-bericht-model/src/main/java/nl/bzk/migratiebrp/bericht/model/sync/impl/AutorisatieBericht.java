/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.bzk.migratiebrp.bericht.model.lo3.parser.SimpleParser;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 * Initiele vulling van autorisaties.
 */
public final class AutorisatieBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final AutorisatieType autorisatieType;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public AutorisatieBericht() {
        this(new AutorisatieType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     *
     * @param autorisatieType
     *            het autorisatieType type
     */
    public AutorisatieBericht(final AutorisatieType autorisatieType) {
        super("Autorisatie");
        this.autorisatieType = autorisatieType;
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de inhoud van het bericht als een Lo3Autorisatie object.
     *
     * @return Lo3Autorisatie object
     */
    public Lo3Autorisatie getAutorisatie() {
        final Integer afnemerCode = Integer.valueOf(autorisatieType.getAfnemerCode());
        final List<Lo3Categorie<Lo3AutorisatieInhoud>> tabelRegels = new ArrayList<>();

        final List<AutorisatieRecordType> autorisatieTabelRegels = autorisatieType.getAutorisatieTabelRegels();
        Collections.sort(autorisatieTabelRegels, new AutorisatieDatumIngangDescendingComparator());

        int index = 0;
        for (final AutorisatieRecordType record : autorisatieTabelRegels) {
            final Lo3AutorisatieInhoud inhoud = maakAutorisatieInhoud(afnemerCode, record);
            final Lo3Datum datumIngang = inhoud.getDatumIngang();

            final Lo3Herkomst herkomst = new Lo3Herkomst(index == 0 ? Lo3CategorieEnum.CATEGORIE_35 : Lo3CategorieEnum.CATEGORIE_85, 0, index);
            final Lo3Historie historie = new Lo3Historie(null, datumIngang, datumIngang);
            tabelRegels.add(new Lo3Categorie<>(inhoud, null, historie, herkomst));

            index++;
        }

        return new Lo3Autorisatie(new Lo3Stapel<>(tabelRegels));
    }

    private Lo3AutorisatieInhoud maakAutorisatieInhoud(final Integer afnemerCode, final AutorisatieRecordType record) {
        final Lo3AutorisatieInhoud result = new Lo3AutorisatieInhoud();

        result.setAfnemersindicatie(afnemerCode);
        result.setAfnemernaam(record.getAfnemerNaam());
        result.setIndicatieGeheimhouding(SimpleParser.parseLo3IndicatieGeheimCode(String.valueOf(record.getGeheimhoudingInd())));
        result.setVerstrekkingsbeperking(SimpleParser.parseInteger(String.valueOf(record.getVerstrekkingsBeperking())));
        result.setRubrieknummerSpontaan(record.getSpontaanRubrieken());
        result.setVoorwaarderegelSpontaan(record.getSpontaanVoorwaarderegel());
        result.setSleutelrubriek(record.getSleutelRubrieken());
        result.setConditioneleVerstrekking(record.getConditioneleVerstrekking());
        result.setMediumSpontaan(parseEnum(record.getSpontaanMedium()));
        result.setRubrieknummerSelectie(record.getSelectieRubrieken());
        result.setVoorwaarderegelSelectie(record.getSelectieVoorwaarderegel());
        result.setSelectiesoort(record.getSelectieSoort());
        result.setBerichtaanduiding(record.getBerichtAand());
        result.setEersteSelectiedatum(parseLo3Datum(record.getEersteSelectieDatum()));
        result.setSelectieperiode(record.getSelectiePeriode());
        result.setMediumSelectie(parseEnum(record.getSelectieMedium()));
        result.setRubrieknummerAdHoc(record.getAdHocRubrieken());
        result.setVoorwaarderegelAdHoc(record.getAdHocVoorwaarderegel());
        result.setPlaatsingsbevoegdheidPersoonslijst(record.getPlPlaatsingsBevoegdheid());
        result.setAfnemersverstrekking(record.getAfnemersVerstrekkingen());
        result.setAdresvraagBevoegdheid(record.getAdresVraagBevoegdheid());
        result.setMediumAdHoc(parseEnum(record.getAdHocMedium()));
        result.setRubrieknummerAdresgeorienteerd(record.getAdresRubrieken());
        result.setVoorwaarderegelAdresgeorienteerd(record.getAdresVoorwaarderegel());
        result.setMediumAdresgeorienteerd(parseEnum(record.getAdresMedium()));
        result.setDatumIngang(parseLo3Datum(record.getTabelRegelStartDatum()));
        result.setDatumEinde(parseLo3Datum(record.getTabelRegelEindDatum()));

        return result;
    }

    /**
     * Parse een Lo3Datum.
     *
     * @param waarde
     *            waarde
     * @return Lo3Datum of null als de waarde null is
     */
    private Lo3Datum parseLo3Datum(final BigInteger waarde) {
        return waarde == null ? null : new Lo3Datum(waarde.intValue());
    }

    /**
     * Parse een Enum.
     *
     * @param waarde
     *            waarde
     * @return String of null als de waarde null is
     */

    private String parseEnum(final Enum<?> waarde) {
        return waarde == null ? null : waarde.name();
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createAutorisatie(autorisatieType));
    }

    /**
     * Aanname: hoeft niet te sorteren over afnemers heen; alleen binnen een afnemer voor de versies.
     */
    private static class AutorisatieDatumIngangDescendingComparator implements Comparator<AutorisatieRecordType>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final AutorisatieRecordType o1, final AutorisatieRecordType o2) {
            final Lo3Datum o1DatumIngang =
                    SimpleParser.parseLo3Datum(o1.getTabelRegelEindDatum() != null ? o1.getTabelRegelStartDatum().toString() : null);
            final Lo3Datum o2DatumIngang =
                    SimpleParser.parseLo3Datum(o2.getTabelRegelEindDatum() != null ? o2.getTabelRegelStartDatum().toString() : null);
            final Lo3Datum o1DatumEinde = SimpleParser.parseLo3Datum(o1.getTabelRegelEindDatum() != null ? o1.getTabelRegelEindDatum().toString() : null);
            final Lo3Datum o2DatumEinde = SimpleParser.parseLo3Datum(o2.getTabelRegelEindDatum() != null ? o2.getTabelRegelEindDatum().toString() : null);

            // Initieel vergelijken op 99.99 Einddatum.
            int resultaat = new DatumComparator().compare(o1DatumEinde, o2DatumEinde);

            // Indien vergelijken op 99.99 Einddatum gelijk resultaat geeft, extra vergelijken op 99.98 Ingangsdatum.
            if (resultaat == 0) {
                resultaat = new DatumComparator().compare(o1DatumIngang, o2DatumIngang);
            }

            return resultaat;
        }
    }

    /**
     * Vergelijker voor Lo3Datums die kan omgaan met null waarden.
     */
    private static class DatumComparator implements Comparator<Lo3Datum>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Datum o1, final Lo3Datum o2) {

            final int resultaat;

            if (o1 == null) {
                if (o2 == null) {
                    resultaat = 0;
                } else {
                    resultaat = -1;
                }
            } else if (o2 == null) {
                resultaat = 1;
            } else {
                resultaat = o2.compareTo(o1);
            }

            return resultaat;
        }

    }
}
