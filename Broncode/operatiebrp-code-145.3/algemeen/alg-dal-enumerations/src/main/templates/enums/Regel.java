/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Enumeratie voor statische stamtabel ber.srtber.
 * <p>
 * Om de enumeratie te re-gegereren aan de hand van de database tabel, gebruik het volgende postgreSQL statement: <code>
   select
   format(E'/**%s.*\/\n%s(%s, "%s", SoortMelding.%s, "%s"),', r.code, r.code, r.id, r.code, upper(sm.naam), r.melding) from kern.regel r left join
   kern.srtmelding sm on sm.id = r.srtmelding order by r.id;
 * </code>
 * <p>
 * Gebruik in psql het commando '\a' om de formatting van de output te beperken.
 * <p>
 * Gebruik in psql het commando '\o <bestandsnaam>' om de uitvoer in een bestand op te slaan.
 * <p>
 * Hierna moet over waar 'SoortMelding.,' staat vervangen worden door 'null,'.
 */
public enum Regel implements Enumeratie {

    /** QUERY:
    SELECT code
    || '. ',
    code,
    '('
    || id
    || ', "'
    || code
    || '", '
    || coalesce(srtmelding, 'null')
    || ', "'
    || coalesce(regexp_replace(regexp_replace(melding, '([\\\"])', '\\$1'), '([\&])', '\&amp;'), '')
    || '")'
    FROM ( select id, code, CAST(srtmelding AS VARCHAR(20)) as srtmelding, melding
    from kern.regel ) as regel
    ORDER BY id
    */

    private static final EnumParser<Regel> PARSER = new EnumParser<>(Regel.class);
    private int id;
    private String code;
    private SoortMelding soortMelding;
    private String melding;

    /**
     * Constructor voor de enumeratie.
     *
     * @param id id van de regel
     * @param code code van de regel
     * @param soortMelding soort melding van de regel
     * @param melding melding tekst van de regel
     */
    Regel(final int id, final String code, final Integer soortMelding, final String melding) {
        this.id = id;
        this.code = code;
        this.soortMelding = SoortMelding.parseId(soortMelding);
        this.melding = melding;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id id
     * @return regel
     */
    public static Regel parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code code
     * @return regel
     */
    public static Regel parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /**
     * Bepaal een voorkomen bestaat voor de gegeven code.
     *
     * @param code
     *            code
     * @return true als het voorkomen bestaat anders false
     */
    public static boolean bestaatCode(final String code) {
        return PARSER.heeftCode(code);
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean heeftCode() {
        return true;
    }

    public SoortMelding getSoortMelding() {
        return soortMelding;
    }

    public String getMelding() {
        return melding;
    }

    @Override
    public String getNaam() {
        throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen naam", this.name()));
    }
}
