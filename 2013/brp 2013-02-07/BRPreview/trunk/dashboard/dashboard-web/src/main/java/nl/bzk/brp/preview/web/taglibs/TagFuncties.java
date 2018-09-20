/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.taglibs;


import nl.bzk.brp.model.data.kern.Pers;
import org.apache.commons.lang3.StringUtils;

/**
 * De Class TagFuncties, hierin staan formatteer methoden voor verschillende typen.
 */
public final class TagFuncties {

    /**
     * Instantieert een nieuwe tag functies.
     */
    private TagFuncties() {
        throw new AssertionError("Deze utility class dient u niet te instantieren...");
    }

    /** De Constante HONDERD. */
    public static final int HONDERD = 100;

    /** De Constante TIEN_DUIZEND. */
    public static final int TIEN_DUIZEND = 10000;

    /**
     * Formatteer datum.
     *
     * @param decimalen de decimalen
     * @return de string
     */
    public static String formatteerDatum(final String decimalen) {
        String resultaat;
        if (decimalen == null || decimalen.length() == 0) {
            resultaat = null;
        } else {
            int datum = Integer.parseInt(decimalen);
            int dag = datum % HONDERD;
            int maand = (datum - dag) / HONDERD % HONDERD;
            int jaar = datum / TIEN_DUIZEND;
            resultaat = String.format("%02d-%02d-%04d", dag, maand, jaar);
        }
        return resultaat;
    }

    /**
     * Formatteer boolean naar een Stringm met waarde ja of nee.
     *
     * @param waarde de waarde
     * @return de string
     */
    public static String formatteerBoolean(final Boolean waarde) {
        String booleanWaarde = "nee";
        if (waarde != null && waarde) {
            booleanWaarde = "ja";
        }
        return booleanWaarde;
    }

    /**
     * Formatteer naam.
     *
     * @param persoon de persoon
     * @return de string
     */
    public static String formatteerNaam(final Pers persoon) {
        String samengesteldeNaam = null;
        String voornamen = persoon.getVoornamen();
        String achternaam = persoon.getGeslnaam();
        String voorvoegsel = persoon.getVoorvoegsel();
        String scheidingsteken = persoon.getScheidingsteken();
        if (scheidingsteken == null || StringUtils.isBlank(scheidingsteken)) {
          //de default voor scheidingsteken
            scheidingsteken  = " ";
        }
        if (voorvoegsel == null && StringUtils.isEmpty(voorvoegsel)) {
            samengesteldeNaam = String.format("%s%s%s", voornamen, scheidingsteken, achternaam);
        } else {
            samengesteldeNaam = String.format("%s%s%s%s%s", voornamen, scheidingsteken,
                    voorvoegsel, scheidingsteken, achternaam);
        }

        return samengesteldeNaam;
    }
}
