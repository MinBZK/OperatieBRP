/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.dataaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;

/**
 * Object waarmee de filter parameters voor een query naar het BMR kunnen worden gezet en meegegeven aan een aanroep
 * naar het BMR. Indien een waarde/parameter ongelijk is aan <code>null</code> dan zal die waarde worden opgenomen
 * als verplichte waarde voor dat veld en worden dus alleen elementen geretourneerd met de opgegeven waarde voor
 * dat veld; een <code>null</code> betekent dus dat de waarde daarvan niet uitmaakt.
 */
public class BmrElementFilterObject {

    private static final String PARAM_LAAG         = "laag";
    private static final String PARAM_IN_LGM       = "inLgm";
    private static final String PARAM_IN_BERICHT   = "inBer";
    private static final String PARAM_IN_CODE      = "inCode";
    private static final String PARAM_SOORT_INHOUD = "srtInh";

    private BmrLaag laag;
    private Boolean   inLgm;
    private Boolean   inBericht;
    private Boolean   inCode;
    private Character soortInhoud;
    private boolean   soortInhoudExclusief;


    /**
     * Constructor die alle velden van de velden op <code>null</code> zet; indien een waarde naar <code>null</code>
     * wordt gezet, wordt dat gezien als een wildcard en zal dat veld dus geen deel uitmaken van de filter.
     */
    public BmrElementFilterObject() {
        this(null);
    }

    /**
     * Constructor die alleen de laag zet waarin gezocht dient te worden. Alle overige velden worden naar
     * <code>null</code> gezet; indien een waarde naar <code>null</code> wordt gezet, wordt dat gezien als een
     * wildcard en zal dat veld dus geen deel uitmaken van de filter.
     *
     * @param laag de laag waarin gezocht dient te worden.
     */
    public BmrElementFilterObject(final BmrLaag laag) {
        this(laag, null, null, null);
    }

    /**
     * Constructor die direct alle velden initialiseert. Indien een waarde naar <code>null</code> wordt gezet, wordt
     * dat gezien als een wildcard en zal dat veld dus geen deel uitmaken van de filter.
     *
     * @param laag de laag waarin gezocht dient te worden.
     * @param inLgm of de gezochte elementen in het LGM moeten voorkomen of juist niet.
     * @param inBericht of de gezochte elementen in het beroicht moeten voorkomen of juist niet.
     * @param soortInhoud het soort inhoud van de gezochte elementen.
     */
    public BmrElementFilterObject(final BmrLaag laag, final Boolean inLgm, final Boolean inBericht,
        final Character soortInhoud)
    {
        this.laag = laag;
        this.inLgm = inLgm;
        this.inBericht = inBericht;
        this.soortInhoud = soortInhoud;
        this.soortInhoudExclusief = false;
    }

    /**
     * Retourneert de laag van het BMR waarin gezocht dient te worden.
     *
     * @return de laag van het BMR waarin gezocht dient te worden.
     */
    public BmrLaag getLaag() {
        return laag;
    }

    /**
     * Zet de laag van het BMR waarin gezocht dient te worden.
     *
     * @param laag de laag van het BMR waarin gezocht dient te worden.
     */
    public void setLaag(final BmrLaag laag) {
        this.laag = laag;
    }

    /**
     * Indicatie of de gezochte elementen in het LGM moeten voorkomen of juist niet.
     *
     * @return of de gezochte elementen in het LGM moeten voorkomen of juist niet.
     */
    public Boolean getInLgm() {
        return inLgm;
    }

    /**
     * Zet of de gezochte elementen in het LGM moeten voorkomen of juist niet.
     *
     * @param inLgm of de gezochte elementen in het LGM moeten voorkomen of juist niet.
     */
    public void setInLgm(final Boolean inLgm) {
        this.inLgm = inLgm;
    }

    /**
     * Retourneert of de gezochte elementen in het beroicht moeten voorkomen of juist niet.
     *
     * @return of de gezochte elementen in het beroicht moeten voorkomen of juist niet.
     */
    public Boolean getInBericht() {
        return inBericht;
    }

    /**
     * Zet of de gezochte elementen in het beroicht moeten voorkomen of juist niet.
     *
     * @param inBericht of de gezochte elementen in het beroicht moeten voorkomen of juist niet.
     */
    public void setInBericht(final Boolean inBericht) {
        this.inBericht = inBericht;
    }

    public Boolean isInCode() {
        return inCode;
    }

    public void setInCode(final Boolean inCode) {
        this.inCode = inCode;
    }

    /**
     * Retourneert het soort inhoud van de gezochte elementen.
     *
     * @return het soort inhoud van de gezochte elementen.
     */
    public Character getSoortInhoud() {
        return soortInhoud;
    }

    /**
     * Zet het soort inhoud van de gezochte elementen.
     *
     * @param soortInhoud het soort inhoud van de gezochte elementen.
     */
    public void setSoortInhoud(final Character soortInhoud) {
        this.soortInhoud = soortInhoud;
    }

    /**
     * Zet een vlag waardoor de soort inhoud juist als 'exclusief' wordt gezien, dus alle elementen die juist niet de
     * opgegeven soort inhoud hebben.
     */
    public void setSoortInhoudExclusief() {
        this.soortInhoudExclusief = true;
    }

    /**
     * Bouwt een lijst van filter statements op basis van de in dit object opgegeven filters.
     *
     * @param elementNaam de naam van het element dat in de statements gebruikt dient te worden.
     * @return de lijst van filter statements.
     */
    public List<String> bouwWhereClauses(final String elementNaam) {
        List<String> whereClauses = new ArrayList<String>();
        if (laag != null) {
            whereClauses.add(String.format("%s.elementByLaag.id = :%s", elementNaam, PARAM_LAAG));
        }
        if (inLgm != null) {
            whereClauses.add(bouwWhereClauseSubStringVoorBooleanString(inLgm, elementNaam, "inLgm", PARAM_IN_LGM));
        }
        if (inBericht != null) {
            whereClauses.add(bouwWhereClauseSubStringVoorBooleanString(inBericht, elementNaam, "inBericht",
                PARAM_IN_BERICHT));
        }
        if (inCode != null) {
            whereClauses.add(bouwWhereClauseSubStringVoorBooleanString(inCode, elementNaam, "inCode",
                PARAM_IN_CODE));
        }
        if (soortInhoud != null) {
            final String operator;
            if (soortInhoudExclusief) {
                operator = "<>";
            } else {
                operator = "=";
            }
            whereClauses.add(String.format("%s.soortInhoud %s :%s", elementNaam, operator, PARAM_SOORT_INHOUD));
        }
        return whereClauses;
    }

    /**
     * Bouwt een map met parameter waardes (met de naam van de parameter als sleutel), voor de gebouwde where
     * clauses.
     *
     * @return een map met parameter namen en hun waardes.
     */
    public Map<String, Object> bouwParametersMap() {
        final Map<String, Object> parameters = new HashMap<String, Object>();
        if (laag != null) {
            parameters.put(PARAM_LAAG, laag.getWaardeInBmr());
        }
        if (inLgm != null) {
            parameters.put(PARAM_IN_LGM, zetBooleanOmNaarCharacter(inLgm));
        }
        if (inBericht != null) {
            parameters.put(PARAM_IN_BERICHT, zetBooleanOmNaarCharacter(inBericht));
        }
        if (soortInhoud != null) {
            parameters.put(PARAM_SOORT_INHOUD, soortInhoud);
        }
        return parameters;
    }

    /**
     * Zet een boolean waarde om naar een tekstuele representatie in het BMR.
     *
     * @param booleanWaarde de boolean waarde die omgezet dient te worden.
     * @return de tekstuele (als karakter) representatie van de boolean waarde: 'J' of 'N'.
     */
    private Character zetBooleanOmNaarCharacter(final boolean booleanWaarde) {
        final Character character;
        if (booleanWaarde) {
            character = 'J';
        } else {
            character = 'N';
        }
        return character;
    }

    /**
     * Bouwt en retourneert een 'where clause' voor een boolean waarde. Dit daar boolean waardes in het BMR een
     * tekstuele representatie hebben, maar ook 'NULL' kunnen zijn en hier dus opgecontroleerd dient te worden.
     *
     * @param booleanWaarde de boolean waarde waarvoor de 'where clause' gegenereerd dient te worden.
     * @param elementNaam de naam van het element dat gezocht wordt.
     * @param propertyNaam de property van het element waarvoor de boolean waarde gecontroleerd dient te worden.
     * @param paramNaam de naam van de parameter die in de 'where clause' gebruikt dient te worden.
     * @return de 'where clause'.
     */
    private String bouwWhereClauseSubStringVoorBooleanString(final boolean booleanWaarde, final String elementNaam,
        final String propertyNaam, final String paramNaam)
    {
        final String whereClause;
        if (booleanWaarde) {
            whereClause = "%1$s.%2$s = :%3$s";
        } else {
            whereClause = "(%1$s.%2$s IS NULL OR %1$s.%2$s = :%3$s)";
        }
        return String.format(whereClause, elementNaam, propertyNaam, paramNaam);
    }
}
