/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.conversie.zoekvraag;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.ElementBasisType;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.util.conversie.zoekvraag.xml.In0Root;
import nl.bzk.brp.util.conversie.zoekvraag.xml.InElement;
import nl.bzk.brp.util.conversie.zoekvraag.xml.ParameterItem;
import org.apache.commons.lang3.StringUtils;

/**
 */
final class CSVWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String SEPARATOR_CHAR = StringUtils.defaultString(System.getProperty("csvSeparator"), "|");

    private static final RubriekMap rubriekMap = new RubriekMap();

    private final PrintWriter writer;

    CSVWriter(final PrintWriter writer) {
        this.writer = writer;
    }

    void toCsv(final In0Root root) {
        for (InElement inElement : root.getElementList()) {
            try {
                schrijfRegel(inElement);
                writer.println();
            } catch (Exception e) {
                LOGGER.error("fout in conversie", e);
            }
        }
    }

    private void schrijfRegel(final InElement inElement) {
        // indicatieAdresVraag: 1-op-1 overnemen
        writer.print(inElement.getIndicatieAdresVraag());
        writer.print(SEPARATOR_CHAR);

        // zoekBereik: als indicatieZoekenInHistorie=1, dan <brp:zoekbereik>Materiele periode</brp:zoekbereik>
        writer.print("1".equals(inElement.getIndicatieZoekenInHistorie()) ? "Materiele periode" : "Peilmoment");
        writer.print(SEPARATOR_CHAR);

        // scopeElementen: alle items van masker converteren naar Elementen en als scopeElementen opnemen
        for (String maskerItem : inElement.getMasker().getItemList()) {
            final Set<AttribuutElement> attribuutElements = rubriekMap.converteerRubriek(maskerItem);
            for (AttribuutElement attribuutElement : attribuutElements) {
                writer.print("<brp:elementNaam>");
                writer.print(attribuutElement.getNaam());
                writer.print("</brp:elementNaam>");
            }
        }
        writer.print(SEPARATOR_CHAR);

        // zoekcriteria:
        //- Alle items met geconverteerde rubrieken en zoekwaarden.
        //- Als zoekwaarde eindigt op '*', dan optie=Vanaf klein en '*' strippen.
        //- Als alle tekens 'lowercase' zijn, dan optie=Klein
        inElement.getParameters().getItemList().forEach(this::schrijfZoekCriterium);
    }

    private void schrijfZoekCriterium(final ParameterItem parameterItem) {
        final Collection<AttribuutElement> attributen = rubriekMap.converteerRubriek(parameterItem.getRubrieknummer());
        if (attributen.size() > 1) {
            LOGGER.warn("Meer dan één element gevonden voor rubriek: {} ", parameterItem.getRubrieknummer());
            return;
        } else if (attributen.isEmpty()) {
            LOGGER.warn("Geen elementen gevonden voor rubriek: {}", parameterItem.getRubrieknummer());
            return;
        }
        final AttribuutElement attribuutElement = attributen.iterator().next();
        writer.printf("<brp:zoekcriterium brp:objecttype=\"Zoekcriterium\" brp:communicatieID=\"%s\">", UUID.randomUUID());
        writer.printf("<brp:elementNaam>%s</brp:elementNaam>", attribuutElement.getNaam());

        String zoekwaarde = parameterItem.getZoekwaarde();
        String zoekOptie = "Exact";
        boolean zoekwaardeEindigtOpSter = zoekwaarde.endsWith("*");
        if (zoekwaardeEindigtOpSter && zoekwaarde.length() > 1) {
            zoekwaarde = zoekwaarde.substring(0, zoekwaarde.lastIndexOf('*'));
            zoekOptie = "Vanaf klein";
        }

        if (attribuutElement.getMininumLengte() != null) {
            zoekwaarde = StringUtils.leftPad(zoekwaarde, attribuutElement.getMininumLengte(), '0');
        }
        if (attribuutElement.getMaximumLengte() != null && zoekwaarde != null && zoekwaarde.length() > attribuutElement.getMaximumLengte()) {
            zoekwaarde = zoekwaarde.substring(0, attribuutElement.getMaximumLengte());
        }
        if (attribuutElement.getDatatype() == ElementBasisType.DATUM && StringUtils.isNotEmpty(zoekwaarde)) {
            zoekwaarde =
                    DatumFormatterUtil
                            .deelsOnbekendeDatumAlsGetalNaarString(DatumFormatterUtil
                                    .deelsOnbekendeDatumAlsStringNaarGetal(DatumFormatterUtil.datumAlsGetalNaarDatumAlsString(Integer.parseInt(zoekwaarde))));

        }
        if (StringUtils.isNotEmpty(zoekwaarde)) {
            writer.printf("<brp:waarde>%s</brp:waarde>", zoekwaarde);
            boolean allesLowercase = true;
            for (char c : zoekwaarde.toCharArray()) {
                allesLowercase &= Character.isLowerCase(c);
            }
            if (allesLowercase) {
                zoekOptie = "Klein";
            }
        } else {
            zoekOptie = "Leeg";
        }

        writer.printf("<brp:optie>%s</brp:optie>", zoekOptie);
        writer.print("</brp:zoekcriterium>");
    }
}
