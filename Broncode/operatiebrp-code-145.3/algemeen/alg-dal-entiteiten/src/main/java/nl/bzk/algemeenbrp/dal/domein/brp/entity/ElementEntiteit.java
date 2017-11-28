/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

/**
 * De JPA entiteit voor de Elementtabel.
 */
@Table(schema = "Kern", name = "Element")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
public class ElementEntiteit {

    @Id
    private Integer iD;
    @Column(name = "Naam")
    private String naam;
    @Column(name = "Srt")
    private Integer soort;
    @Column(name = "ElementNaam")
    private String elementNaam;
    @Column(name = "Objecttype")
    private Integer objecttype;
    @Column(name = "Groep")
    private Integer groep;
    @Column(name = "Volgnr")
    private Integer volgnummer;
    @Column(name = "AliasVan")
    private Integer aliasVan;
    @Column(name = "Autorisatie")
    private Integer autorisatie;
    @Column(name = "HistoriePatroon")
    private String historiePatroon;
    @Column(name = "VerantwoordingCategorie")
    private String verantwoordingCategorie;
    @Column(name = "Type")
    private Integer type;
    @Column(name = "ExpressieBasistype")
    private String expressieBasistype;
    @Column(name = "SrtInh")
    private String soortInhoud;
    @Column(name = "dataanvgel")
    private Integer datumAanvangGeldigheid;
    @Column(name = "dateindegel")
    private Integer datumEindeGeldigheid;
    @Column(name = "InverseassociatieidentCode")
    private String inverseAssociatieIdentCode;
    @Column(name = "Identexpressie")
    private String identExpressie;
    @Column(name = "Inber")
    private Boolean inber;
    @Column(name = "identXsd")
    private String identXsd;
    @Column(name = "minimumlengte")
    private Integer minimumLengte;
    @Column(name = "agattr")
    private Integer actueelGeldigAttribuut;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected ElementEntiteit() {}

    /**
     * Public constructor.
     *
     * @param iD het element ID
     */
    public ElementEntiteit(final Integer iD) {
        this.iD = iD;
    }

    /**
     * Retourneert ID van Element.
     *
     * @return ID.
     */
    public final Integer getID() {
        return iD;
    }

    /**
     * Retourneert Naam van Element.
     *
     * @return Naam.
     */
    public final String getNaam() {
        return naam;
    }

    /**
     * Retourneert Soort van Element.
     *
     * @return Soort.
     */
    public final Integer getSoort() {
        return soort;
    }

    /**
     * Retourneert Element naam van Element.
     *
     * @return Element naam.
     */
    public final String getElementNaam() {
        return elementNaam;
    }

    /**
     * Retourneert Objecttype van Element.
     *
     * @return Objecttype.
     */
    public final Integer getObjecttype() {
        return objecttype;
    }

    /**
     * Retourneert Groep van Element.
     *
     * @return Groep.
     */
    public final Integer getGroep() {
        return groep;
    }

    /**
     * Retourneert Volgnummer van Element.
     *
     * @return Volgnummer.
     */
    public final Integer getVolgnummer() {
        return volgnummer;
    }

    /**
     * Retourneert Alias van van Element.
     *
     * @return Alias van.
     */
    public final Integer getAliasVan() {
        return aliasVan;
    }

    /**
     * Retourneert Autorisatie van Element.
     *
     * @return Autorisatie.
     */
    public final Integer getAutorisatie() {
        return autorisatie;
    }


    /**
     * Retourneert Historie patroon van Element.
     *
     * @return Historie patroon.
     */
    public final String getHistoriePatroon() {
        return historiePatroon;
    }

    /**
     * Retourneert Verantwoording categorie van Element.
     *
     * @return Verantwoording categorie.
     */
    public final String getVerantwoordingCategorie() {
        return verantwoordingCategorie;
    }

    /**
     * Retourneert Type van Element.
     *
     * @return Type.
     */
    public final Integer getType() {
        return type;
    }

    /**
     * Retourneert Expressie Basistype van Element.
     *
     * @return Expressie Basistype.
     */
    public final String getExpressieBasistype() {
        return expressieBasistype;
    }

    /**
     * Retourneert Soort inhoud van Element.
     *
     * @return Soort inhoud.
     */
    public final String getSoortInhoud() {
        return soortInhoud;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Element.
     *
     * @return Datum aanvang geldigheid voor Element
     */
    public final Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Element.
     *
     * @return Datum einde geldigheid voor Element
     */
    public final Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Retourneert de indentXsd voor Element.
     *
     * @return Identxsd voor Element
     */
    public final String getIdentXsd() {
        return identXsd;
    }

    /**
     * Retourneert de InverseAssociatieIdentCode voor Element.
     *
     * @return InverseAssociatieIdentCode voor Element
     */
    public final String getInverseAssociatieIdentCode() {
        return inverseAssociatieIdentCode;
    }

    /**
     * Retourneert de IdentExpressie voor Element.
     *
     * @return InverseAssociatieIdentCode voor Element
     */
    public final String getIdentExpressie() {
        return identExpressie;
    }

    /**
     * Retourneert de Inber voor Element.
     *
     * @return Inber voor Element
     */
    public final Boolean getInber() {
        return inber;
    }

    /**
     * Retourneert de minimumlengte voor Element.
     *
     * @return minimumlengte voor element
     */
    public final Integer getMinimumLengte() {
        return minimumLengte;
    }

    /**
     * Retourneert het actueel/geldig attribuut voor Element.
     *
     * @return actueel/geldig voor element
     */
    public final Integer getActueelGeldigAttribuut() {
        return actueelGeldigAttribuut;
    }
}

