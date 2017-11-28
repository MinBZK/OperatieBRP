/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Dit is de lijst met soort meldingen die mogelijk zijn tijdens de conversie. Deze lijst moet mappen met
 * SoortMeldingCode.
 * <p>
 * Om de enumeratie te re-gegereren aan de hand van de database tabel, gebruik het volgende postgreSQL statement:
 * <code>select format(E'\n/\** %s. *&#47;\n%s(%s, "%s", %s, Lo3CategorieMelding.%s),', code, code, s.id, code,
 * regexp_replace(concat('"',regexp_replace(regexp_replace(oms, '([\\\"])', '\\\1'), E'(.{1,120})', '\1"+"', 'g'),'"'),
 * '\+\"\"$', ''), replace(upper(c.naam),' ','_')) from verconv.lo3srtmelding s left join verconv.lo3categoriemelding c
 * on s.categoriemelding = c.id order by s.id</code>
 * <p>
 * Gebruik in psql het commando '\a' om de formatting van de output te beperken.
 * <p>
 * Gebruik in psql het commando '\o <bestandsnaam>' om de uitvoer in een bestand op te slaan.
 * <p>
 * Voer daarna bovenstaand SQL statement uit.
 */

public enum Lo3SoortMelding implements Enumeratie {

    /** QUERY:
    SELECT code 
           || '. ',
           code,
           '('
           || id
           || ', "'
           || code
           || '", "'
    	   || coalesce(regexp_replace(regexp_replace(oms, '([\\\"])', '\\$1'), '([\&])', '\&amp;'), '')           
           || '", '
           || categoriemelding
           || ')'
    FROM   verconv.lo3srtmelding
    ORDER BY id
    */        

    private static final EnumParser<Lo3SoortMelding> PARSER = new EnumParser<>(Lo3SoortMelding.class);

    private final int id;
    private final String code;
    private final String omschrijving;
    private final Lo3CategorieMelding categorieMelding;

    /**
     * Maak een nieuwe lo3 soort melding.
     *
     * @param meldingId database ID
     * @param meldingCode code van de melding
     * @param meldingOmschrijving Omschrijving van de melding
     * @param meldingCategorie type melding {@link Lo3CategorieMelding}
     */
    Lo3SoortMelding(final int meldingId, final String meldingCode, final String meldingOmschrijving, final int meldingCategorie) {
        id = meldingId;
        code = meldingCode;
        omschrijving = meldingOmschrijving;
        categorieMelding = Lo3CategorieMelding.parseId(meldingCategorie);
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id parsed de ID naar een {@link Lo3SoortMelding}
     * @return de bijbehorende {@link Lo3SoortMelding}
     */
    public static Lo3SoortMelding parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code parsed de code naar een {@link Lo3SoortMelding}
     * @return de bijbehorende {@link Lo3SoortMelding}
     */
    public static Lo3SoortMelding parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getNaam() {
        throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen naam", name()));
    }

    /**
     * Geef de waarde van omschrijving van Lo3SoortMelding.
     *
     * @return de waarde van omschrijving van Lo3SoortMelding
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van categorie melding van Lo3SoortMelding.
     *
     * @return de waarde van categorie melding van Lo3SoortMelding
     */
    public Lo3CategorieMelding getCategorieMelding() {
        return categorieMelding;
    }

    @Override
    public boolean heeftCode() {
        return true;
    }
}
