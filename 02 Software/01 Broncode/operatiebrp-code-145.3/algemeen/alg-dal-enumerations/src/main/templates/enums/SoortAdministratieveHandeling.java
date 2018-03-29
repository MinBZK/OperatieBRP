/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * Om de enumeratie te re-gegereren aan de hand van de database tabel, gebruik het volgende postgreSQL statement: <code> select format(E'/** %s. *\/\n%s
 * (%s, "%s", %s, %s, %s, %s),', s.naam, regexp_replace(regexp_replace(upper(s.naam), ' |-', '_', 'g'), '\(|\)', '', 'g'),
 * s.id, s.naam, CASE WHEN c.naam is NULL THEN 'null' ELSE 'CategorieAdministratieveHandeling.' || regexp_replace(replace(upper(c.naam), ' ', '_'),
 * '\(|\)', '', 'g') END, module, alias, koppelvlak) from kern.srtadmhnd s left join kern.categorieadmhnd c on s.categorieadmhnd = c.id </code>
 * <p>
 * Gebruik in psql het commando '\a' om de formatting van de output te beperken.
 * <p>
 * Gebruik in psql het commando '\o <bestandsnaam>' om de uitvoer in een bestand op te slaan.
 * <p>
 * Voer daarna bovenstaand SQL statement uit.
 */
public enum SoortAdministratieveHandeling implements Enumeratie {

    /** QUERY:
     SELECT naam
     || '. ',
     naam,
     '('
     || id
     || ', "'
     || naam
     || '", '
     || coalesce(categorieadmhnd, 'null')
     || ', '
     || coalesce(module, 'null')
     || ', '
     || coalesce(alias, 'null')
     || ', '
     || koppelvlak
     || ')'
     FROM  (
     select id, naam, CAST(categorieadmhnd AS VARCHAR(20)) as categorieadmhnd, CAST(module AS VARCHAR(20)) as module, CAST(alias as VARCHAR(20)) as alias, koppelvlak
     from kern.srtadmhnd ) as srtadmhnd
     ORDER BY id
     */

    private static final EnumParser<SoortAdministratieveHandeling> PARSER = new EnumParser<>(SoortAdministratieveHandeling.class);

    private final int                             id;
    private final String                            naam;
    private final CategorieAdministratieveHandeling categorie;
    private final int                               moduleId;
    private final Integer                           alias;
    private final int                               koppelvlakId;

    /**
     * (ID, Code, Naam, CategorieAdmHnd, Module).
     *
     * @param id           id in de database
     * @param naam         naam
     * @param categorieId  categorie AH
     * @param moduleId     module id
     * @param alias        alias
     * @param koppelvlakId koppelvlak id
     */
    SoortAdministratieveHandeling(
        final int id,
        final String naam,
        final Integer categorieId,
        final int moduleId,
        final Integer alias,
        final int koppelvlakId)
    {
        this.id = id;
        this.naam = naam;
        this.categorie = CategorieAdministratieveHandeling.parseId(categorieId);
        this.moduleId = moduleId;
        this.alias = alias;
        this.koppelvlakId = koppelvlakId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see Enumeratie#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id id van soort AH
     * @return de enumeratie waarde die bij het id hoort
     */
    public static SoortAdministratieveHandeling parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortAdministratieve handeling heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef de waarde van naam van SoortAdministratieveHandeling.
     *
     * @return de waarde van naam van SoortAdministratieveHandeling
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van categorie van SoortAdministratieveHandeling.
     *
     * @return de waarde van categorie van SoortAdministratieveHandeling
     */
    public CategorieAdministratieveHandeling getCategorie() {
        return categorie;
    }

    /**
     * Geef de waarde van module van SoortAdministratieveHandeling.
     *
     * @return de waarde van module van SoortAdministratieveHandeling
     */
    public BurgerzakenModule getModule() {
        return BurgerzakenModule.parseId(moduleId);
    }

    /**
     * Geef de waarde van alias van SoortAdministratieveHandeling.
     *
     * @return de waarde van alias van SoortAdministratieveHandeling
     */
    public SoortAdministratieveHandeling getAlias() {
        return alias == null ? null : SoortAdministratieveHandeling.parseId(alias);
    }

    /**
     * Geef de waarde van koppelvlak van SoortAdministratieveHandeling.
     *
     * @return de waarde van koppelvlak van SoortAdministratieveHandeling
     */
    public Koppelvlak getKoppelvlak() {
        return Koppelvlak.parseId(koppelvlakId);
    }
}
