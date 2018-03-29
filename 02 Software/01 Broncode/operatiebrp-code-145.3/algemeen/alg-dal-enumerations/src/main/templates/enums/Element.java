/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

/**
 * De class voor de SoortElement database tabel.
 *
 * Om de enumeratie te re-gegereren aan de hand van de database tabel, gebruik het volgende postgreSQL statement: <code>
 select format(E'/** %s. *\/\n%s(ElementWaarde.getInstance(%s)),',
   e.naam,
   upper(replace(e.naam, '.', '_')),
   e.id)
 from
   kern.element e
   left join kern.element g on e.groep=g.id
   left join kern.srtelement s on e.srt=s.id
 order by
   e.srt, e.naam
 </code>
 *
 * Gebruik in psql het commando '\a' om de formatting van de output te beperken.
 *
 * Gebruik in psql het commando '\o <bestandsnaam>' om de uitvoer in een bestand op te slaan.
 *
 * Voer daarna bovenstaand SQL statement uit.
 *
 *
 * LET OP: Bij aanpassingen aan de id of entiteit naam voor een tabel, of bij nieuwe tabellen, dan moet deze wijziging
 * ook worden doorgevoerd in de entity class GegevenInOnderzoek.
 *
 * LET OP 2: Bij wijzigingen in de element tabel dient ook altijd de ElementWaarde.properties opnieuw te worden
 * gegenereerd.
 */
public enum Element implements Enumeratie {

    /** QUERY:
    SELECT naam 
           || '. ',
           upper(replace(naam, '.', '_')),
           '(ElementWaarde.getInstance('
           || id
           || '))'
    FROM   kern.element
    order by srt, naam
    */

    private static final EnumParser<Element> PARSER = new EnumParser<>(Element.class);

    private final ElementWaarde elementWaarde;

    /**
     * Maak een nieuwe element.
     *
     * @param elementWaarde elementWaarde
     */
    Element(final ElementWaarde elementWaarde) {
        if (elementWaarde == null) {
            throw new IllegalStateException(this + " heeft geen gekoppelde elementWaarde");
        }
        this.elementWaarde = elementWaarde;
    }

    /**
     * Geeft een enumeratiewaarde van type Element terug o.b.v. het database-ID.
     *
     * @param id het database-id van de enumeratie. Mag null zijn, in dat geval wordt ook null geretourneerd.
     * @return Een enumeratiewaarde van type Element, of null.
     * @throws IllegalArgumentException als de enumeratiewaarde met bijbehorend id niet gevonden kon worden.
     */
    public static Element parseId(final Integer id) {
        return PARSER.parseId(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see Enumeratie#getId()
     */
    @Override
    public int getId() {
        return elementWaarde.getId();
    }

    /**
     * Geef de waarde van naam van Element.
     *
     * @return de waarde van naam van Element
     */
    @Override
    public String getNaam() {
        return elementWaarde.getNaam();
    }

    /**
     * Geef de waarde van soort van Element.
     *
     * @return de waarde van soort van Element
     */
    public SoortElement getSoort() {
        return elementWaarde.getSoort();
    }

    /**
     * Geef de waarde van groep van Element.
     *
     * @return de waarde van groep van Element
     */
    public Element getGroep() {
        return SoortElement.ATTRIBUUT.equals(elementWaarde.getSoort()) ? Element.parseId(elementWaarde.getGroep().getId()) : null;
    }

    /**
     * Geef de waarde van objecttype van Element.
     *
     * @return de waarde van objecttype van Element
     */
    public Element getObjecttype() {
        return Element.parseId(elementWaarde.getObjecttype());
    }

    /**
     * Geef de waarde van autorisatie van Element.
     *
     * @return de waarde van autorisatie van Element
     */
    public SoortElementAutorisatie getSoortAutorisatie() {
        return elementWaarde.getSoortAutorisatie();
    }

    /**
     * Geef de waarde van element naam van Element.
     *
     * @return de waarde van element naam van Element
     */
    public String getElementNaam() {
        return elementWaarde.getElementNaam();
    }

    /**
     * Geef het volgnummer.
     *
     * @return het volgnummer van Element
     */

    public Integer getVolgnummer() {
        return elementWaarde.getVolgnummer();
    }

    /**
     * Geef het historie patroon.
     *
     * @return het historie patroon van Element
     */
    public HistoriePatroon getHistoriePatroon() {
        return elementWaarde.getHistoriePatroon();
    }

    /**
     * Geef het type.
     *
     * @return het type van Element
     */
    public Element getType() {
        return elementWaarde.getType() == null ? null : Element.parseId(elementWaarde.getType());
    }

    /**
     * Geef het element waar dit element een alias van is.
     *
     * @return het element waar dit element een alias van is
     */
    public Element getAliasVan() {
        return elementWaarde.getTypeAlias() == null ? null : Element.parseId(elementWaarde.getTypeAlias());
    }

    /**
     * Geef het basis type.
     *
     * @return het basis type van Element
     */
    public ElementBasisType getBasisType() {
        return elementWaarde.getBasisType();
    }

    /*
     * (non-Javadoc)
     * 
     * @see Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie Dbobject heeft geen code");
    }

    /**
     * Geeft de waarden van de enumeratiewaarde.
     *
     * @return het type van Element
     */
    public ElementWaarde getElementWaarde() {
        return elementWaarde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef de waarde van laatste naam deel van Element.
     *
     * @return de waarde van laatste naam deel van Element
     */
    public String getLaatsteNaamDeel() {
        final String[] naamDelen = getNaam().split("\\.");
        return naamDelen[naamDelen.length - 1];
    }
}
