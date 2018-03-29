/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeratie voor statische stamtabel ber.srtber.
 */
public enum SoortBerichtVrijBericht implements Enumeratie {

    /** QUERY:
     SELECT naam
     || '. ',
     naam,
     '('
     || id
     || ', "'
     || naam
     || '", '
     || srtber
     || ')'
     FROM ( SELECT id, naam, CAST(srtber as VARCHAR(20)) as srtber from  beh.srtbervrijber ) as srtbervrijber
     ORDER BY id
     */

    private static final EnumParser<SoortBerichtVrijBericht> PARSER = new EnumParser<>(SoortBerichtVrijBericht.class);

    private final int id;
    private final String naam;
    private final int soortBericht;

    /**
     * Constructor om de enumeratie te maken.
     *
     * @param id id
     * @param naam naam
     * @param soortBericht verwijzing naar soort bericht
     */
    SoortBerichtVrijBericht(
            final int id,
            final String naam,
            final int soortBericht)
    {
        this.id = id;
        this.naam = naam;
        this.soortBericht = soortBericht;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id id
     * @return soort bericht
     */
    public static SoortBerichtVrijBericht parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Geef de waarde van id van Enumeratie.
     *
     * @return de waarde van id van Enumeratie
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Geef de waarde van code van Enumeratie.
     *
     * @return de waarde van code van Enumeratie
     * @throws UnsupportedOperationException als de Enumeratie geen code bevat.
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen code", this.name()));
    }

    /**
     * @return true als deze Enumeratie een code heeft, anders false.
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * @return Geeft de naam terug die gebruikt wordt voor de Enumeratie.
     */
    @Override
    public String getNaam() { return naam; }


    /**
     * @return het soort bericht
     */
    public int getSoortBericht() {
        return soortBericht;
    }
}
