/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bij een bijhouding kunnen documenten betrokken zijn die een soort aanduiding hebben.
 */
@Entity
@Table(name = "srtdoc", schema = "kern")
@Access(value = javax.persistence.AccessType.FIELD)
public class SoortDocument {

    @Id
    private Long id;
    @Column(name = "oms")
    private String omschrijving;
    @Column(name = "categoriesrtdoc")
    private Long categorieSoortDocument;

    /**
     * Standaard constructor.
     */
    public SoortDocument() {
    }

    /**
     * Constructor met veld initialisatie.
     *
     * @param id Id van het soort document.
     * @param omschrijving Omschrijving van het soort document.
     * @param categorieSoortDocument Categorie van het soort document.
     */
    public SoortDocument(final Long id, final String omschrijving, final Long categorieSoortDocument) {
        this.id = id;
        this.omschrijving = omschrijving;
        this.categorieSoortDocument = categorieSoortDocument;
    }

    public Long getId() {
        return id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public Long getCategorieSoortDocument() {
        return categorieSoortDocument;
    }
}
