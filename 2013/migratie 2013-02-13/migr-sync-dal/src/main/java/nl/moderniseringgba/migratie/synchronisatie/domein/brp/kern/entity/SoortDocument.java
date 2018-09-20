/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.DynamischeStamtabel;

/**
 * The persistent class for the srtdoc database table.
 * 
 */
@Entity
@Table(name = "srtdoc", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class SoortDocument implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(insertable = false, updatable = false, nullable = false)
    private Integer id;

    @Column(name = "naam", insertable = false, updatable = false, nullable = false, length = 250)
    private String omschrijving;

    @Column(name = "categoriesrtdoc", nullable = false)
    private Integer categorieSoortDocumentId;

    public SoortDocument() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * @return
     */
    public CategorieSoortDocument getCategorieSoortDocument() {
        return CategorieSoortDocument.parseId(categorieSoortDocumentId);
    }

    /**
     * @param categorieSoortDocument
     */
    public void setCategorieSoortDocument(final CategorieSoortDocument categorieSoortDocument) {
        if (categorieSoortDocument == null) {
            categorieSoortDocumentId = null;
        } else {
            categorieSoortDocumentId = categorieSoortDocument.getId();
        }
    }

    /**
     * @param soortDocument
     * @return
     */
    public boolean isInhoudelijkGelijkAan(final SoortDocument soortDocument) {
        if (this == soortDocument) {
            return true;
        }
        if (soortDocument == null) {
            return false;
        }
        if (categorieSoortDocumentId == null) {
            if (soortDocument.categorieSoortDocumentId != null) {
                return false;
            }
        } else if (!categorieSoortDocumentId.equals(soortDocument.categorieSoortDocumentId)) {
            return false;
        }
        if (omschrijving == null) {
            if (soortDocument.omschrijving != null) {
                return false;
            }
        } else if (!omschrijving.equals(soortDocument.omschrijving)) {
            return false;
        }
        return true;
    }

}
