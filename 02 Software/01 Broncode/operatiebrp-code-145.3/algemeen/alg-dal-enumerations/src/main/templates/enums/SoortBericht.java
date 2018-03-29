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
 * Enumeratie voor statische stamtabel kern.srtber.
 */
public enum SoortBericht implements Enumeratie {

    /** QUERY:
     SELECT srtber.identifier
     || '. ',
     CASE WHEN identifier = 'Onbekend'
     THEN
     'literal:@JsonEnumDefaultValue\n    ONBEKEND'
     ELSE
     upper(regexp_replace(regexp_replace(regexp_replace(identifier, '([A-Z])', '_$1'), '^_', ''), '__', '_'))
     END,
     '('
     || srtber.id
     || ', "'
     || srtber.identifier
     || '", '
     || coalesce(srtber.module, 'null')
     || ', '
     || srtber.koppelvlak
     || ', '
     || coalesce(srtber.dataanvgel, 'null')
     || ', '
     || coalesce(srtber.dateindegel, 'null')
     || ')'
     FROM ( SELECT id, identifier, CAST(module AS VARCHAR(20)) as module, koppelvlak, CAST(dataanvgel AS VARCHAR(8)) as dataanvgel, CAST(dateindegel AS VARCHAR(8)) as dateindegel
     from kern.srtber ) as srtber
     ORDER BY id
     */

    private static final EnumParser<SoortBericht> PARSER = new EnumParser<>(SoortBericht.class);
    private static final Map<String, SoortBericht> ENUMERATIES_OP_IDENTIFIER = new HashMap<>();

    static {
        for (final SoortBericht sb : SoortBericht.class.getEnumConstants()) {
            ENUMERATIES_OP_IDENTIFIER.put(sb.getIdentifier(), sb);
        }
    }

    private final int id;
    private final String identifier;
    private final BurgerzakenModule module;
    private final Koppelvlak koppelvlak;
    private final Integer datumAanvanGeldigheid;
    private final Integer datumEindeGeldigheid;

    /**
     * Constructor om de enumeratie te maken.
     * @param id id van het bericht
     * @param identifier identifier van het bericht
     * @param module module waar het bericht gebruikt wordt
     * @param koppelvlak koppelvlak waar het bericht uit komt
     * @param datumAanvanGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     */
    SoortBericht(final int id, final String identifier, final Integer module, final int koppelvlak, final Integer datumAanvanGeldigheid,
                 final Integer datumEindeGeldigheid) {
        this.id = id;
        this.identifier = identifier;
        this.module = BurgerzakenModule.parseId(module);
        this.koppelvlak = Koppelvlak.parseId(koppelvlak);
        this.datumAanvanGeldigheid = datumAanvanGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     * @param id id
     * @return soort bericht
     */
    public static SoortBericht parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van de identifier.
     * @param identifier de identifier van het bericht
     * @return soort bericht
     */
    public static SoortBericht parseIdentifier(final String identifier) {
        return ENUMERATIES_OP_IDENTIFIER.get(identifier);
    }

    /**
     * Geef de waarde van id van Enumeratie.
     * @return de waarde van id van Enumeratie
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Geef de waarde van code van Enumeratie.
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
    public String getNaam() {
        throw new UnsupportedOperationException(String.format("De enumeratie %s heeft geen naam", this.name()));
    }

    /**
     * @return de identifier van dit soort bericht.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @return de module terug waar dit berichtsoort toe behoort
     */
    public BurgerzakenModule getModule() {
        return module;
    }

    /**
     * @return het koppelvlak waar dit berichtsoort toe behoort
     */
    public Koppelvlak getKoppelvlak() {
        return koppelvlak;
    }

    /**
     * @return de datum aanvang geldigheid
     */
    public Integer getDatumAanvanGeldigheid() {
        return datumAanvanGeldigheid;
    }

    /**
     * @return de datum einde geldigheid
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }
}
