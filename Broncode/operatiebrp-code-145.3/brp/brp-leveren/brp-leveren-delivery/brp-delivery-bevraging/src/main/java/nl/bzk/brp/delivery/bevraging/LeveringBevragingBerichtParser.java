/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser;
import nl.bzk.brp.delivery.algemeen.TransformerUtil;
import nl.bzk.brp.delivery.algemeen.VerzoekParseException;
import nl.bzk.brp.delivery.algemeen.VerzoekParser;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import nl.bzk.brp.service.bevraging.geefmedebewoners.GeefMedebewonersVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoonopadres.ZoekPersoonOpAdresVerzoek;
import org.w3c.dom.Node;

/**
 * De parser die van een bevragingsverzoek voor Levering een BevragingVerzoek maakt.
 */
final class LeveringBevragingBerichtParser {

    private static final Map<String, AbstractGeneriekeBerichtParser<? extends BevragingVerzoek>> DIENST_BERICHT_PARSER_MAP =
            Maps.newHashMap();

    static {
        DIENST_BERICHT_PARSER_MAP.put(SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON.getIdentifier(), new GeefDetailsPersoonBerichtParser());
        DIENST_BERICHT_PARSER_MAP.put(SoortBericht.LVG_BVG_ZOEK_PERSOON.getIdentifier(), new ZoekPersoonBerichtParser());
        DIENST_BERICHT_PARSER_MAP.put(SoortBericht.LVG_BVG_ZOEK_PERSOON_OP_ADRES.getIdentifier(), new ZoekPersoonOpAdresBerichtParser());
        DIENST_BERICHT_PARSER_MAP.put(SoortBericht.LVG_BVG_GEEF_MEDEBEWONERS.getIdentifier(), new GeefMedebewonersBerichtParser());
    }

    /**
     * Parse het bevraging bericht naar een {@link BevragingVerzoek} vanuit een {@link DOMSource}.
     * @param source de {@link DOMSource}
     * @return het geparste {@link BevragingVerzoek}
     * @throws TransformerException bij een transformatiefout
     * @throws VerzoekParseException bij een fout met het parsen van het verzoek
     */
    BevragingVerzoek parse(final DOMSource source) throws TransformerException, VerzoekParseException {
        final Node node = TransformerUtil.initializeNode(source);
        return DIENST_BERICHT_PARSER_MAP.get(node.getLocalName()).parse(node);
    }

    /**
     */
    private static final class GeefDetailsPersoonBerichtParser extends AbstractGeneriekeBerichtParser<GeefDetailsPersoonVerzoek> {

        @Override
        protected VerzoekParser<GeefDetailsPersoonVerzoek> geefDienstSpecifiekeParser(final Node node) {
            return new GeefDetailsPersoonVerzoekParser();
        }

        @Override
        protected GeefDetailsPersoonVerzoek maakVerzoek() {
            return new GeefDetailsPersoonVerzoek();
        }
    }

    /**
     */
    private static final class ZoekPersoonBerichtParser extends AbstractGeneriekeBerichtParser<ZoekPersoonVerzoek> {

        @Override
        protected VerzoekParser<ZoekPersoonVerzoek> geefDienstSpecifiekeParser(final Node node) {
            return new ZoekPersoonVerzoekParser();
        }

        @Override
        protected ZoekPersoonVerzoek maakVerzoek() {
            return new ZoekPersoonVerzoek();
        }
    }

    /**
     */
    private static final class ZoekPersoonOpAdresBerichtParser extends AbstractGeneriekeBerichtParser<ZoekPersoonOpAdresVerzoek> {

        @Override
        protected VerzoekParser<ZoekPersoonOpAdresVerzoek> geefDienstSpecifiekeParser(final Node node) {
            return new ZoekPersoonOpAdresVerzoekParser();
        }

        @Override
        protected ZoekPersoonOpAdresVerzoek maakVerzoek() {
            return new ZoekPersoonOpAdresVerzoek();
        }
    }

    /**
     */
    private static final class GeefMedebewonersBerichtParser extends AbstractGeneriekeBerichtParser<GeefMedebewonersVerzoek> {

        @Override
        protected VerzoekParser<GeefMedebewonersVerzoek> geefDienstSpecifiekeParser(final Node node) {
            return new GeefMedebewonersVerzoekParser();
        }

        @Override
        protected GeefMedebewonersVerzoek maakVerzoek() {
            return new GeefMedebewonersVerzoek();
        }
    }
}
