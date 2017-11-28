/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.util;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Utility methodes voor PersoonHisVolledig.
 */
public interface PersoonUtil {

    /**
     * Geef het actuele a-nummer.
     * @param persoon persoon
     * @return het actuele a-nummer
     */
    static String getAnummer(final Persoonslijst persoon) {
        return persoon.<String>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()))
                .orElse(null);
    }

    /**
     * Geef het actuele volgende a-nummer.
     * @param persoon persoon
     * @return het actuele volgende a-nummer
     */
    static String getVolgendeAnummer(final Persoonslijst persoon) {
        return persoon.<String>getActueleAttribuutWaarde(
                ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER.getId()))
                .orElse(null);
    }

    /**
     * Geef het actuele volgende a-nummer.
     * @param persoon persoon
     * @return het actuele volgende a-nummer
     */
    static String getBijhoudingPartijcode(final Persoonslijst persoon) {
        return persoon.<String>getActueleAttribuutWaarde(
                ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()))
                .orElse(null);
    }

    /**
     * Geeft aan of persoon een nadere bijhoudingsaard FOUT heeft.
     * @param persoon persoon
     * @return true als persoon een nadere bijhoudingsaard FOUT heeft, anders false
     */
    static boolean isAfgevoerd(final Persoonslijst persoon) {
        return persoon.<String>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId()))
                .map(NadereBijhoudingsaard::parseCode)
                .map(NadereBijhoudingsaard.FOUT::equals)
                .orElse(false);
    }
}
