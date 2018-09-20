/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.NaturalId;


@Entity
public class ExportRegel extends NamedModelElement {

    @Column(name = "SOORT")
    private String             soort;

    private Integer            laag;

    @NaturalId
    @Column(name = "INTERNE_IDENTIFIER")
    private String             interneIdentifier;

    @NaturalId
    private Integer            syncId;

    @Column(name = "EXPORT_IDENTIFIER")
    private String             exportIdentifier;

    public static final String II_MODEL                   = "Model";
    public static final String II_SCHEMA                  = "Schema";
    public static final String II_DOMEIN                  = "Domein";

    public static final String II_OBJECTTYPE              = "Objecttype";

    public static final String II_SUPERTYPE               = "Supertype";

    public static final String II_BASISTYPE               = "Basistype";

    public static final String II_ATTRIBUUTTYPE           = "Attribuuttype";

    public static final String II_ATTRIBUUTTYPE_TYPE      = "Attribuuttype_Type";

    public static final String II_ATTRIBUTE               = "Attribute";
    public static final String II_GROEP                   = "Groep";
    public static final String II_ATTRIBUTE_CARD_LO       = "Attribute_Card_Lo";
    public static final String II_ATTRIBUTE_CARD_HI       = "Attribute_Card_Hi";

    public static final String II_INVERSE_ATTRIBUTE       = "InverseAttribute";

    public static final String II_ASSOCIATION_DST_CARD_LO = "Association_Dst_Card_Lo";

    public static final String II_ASSOCIATION_DST_CARD_HI = "Association_Dst_Card_Hi";

    public static final String II_ASSOCIATION_DST         = "Association_Dst";

    public static final String II_ASSOCIATION_SRC_CARD_LO = "Association_Src_Card_Lo";

    public static final String II_ASSOCIATION_SRC_CARD_HI = "Association_Src_Card_Hi";

    public static final String II_ASSOCIATION_SRC         = "Association_Src";

    public static final String II_ASSOCIATION             = "Association";

    public static final String II_BASISTYPEN              = "BasisTypen";

    /**
     * @return the soort
     */
    public String getSoort() {
        return soort;
    }

    /**
     * @return the laag
     */
    public Integer getLaag() {
        return laag;
    }

    /**
     * @return the interneIdentifier
     */
    public String getInterneIdentifier() {
        return interneIdentifier;
    }

    /**
     * @return the syncId
     */
    public Integer getSyncId() {
        return syncId;
    }

    /**
     * @return the exportIdentifier
     */
    public String getExportIdentifier() {
        return exportIdentifier;
    }

    /**
     * @param soort the soort to set
     */
    public void setSoort(final String soort) {
        this.soort = soort;
    }

    /**
     * @param laag the laag to set
     */
    public void setLaag(final Integer laag) {
        this.laag = laag;
    }

    /**
     * @param interneIdentifier the interneIdentifier to set
     */
    public void setInterneIdentifier(final String interneIdentifier) {
        this.interneIdentifier = interneIdentifier;
    }

    /**
     * @param syncId the syncId to set
     */
    public void setSyncId(final Integer syncId) {
        this.syncId = syncId;
    }

    /**
     * @param exportIdentifier the exportIdentifier to set
     */
    public void setExportIdentifier(final String exportIdentifier) {
        this.exportIdentifier = exportIdentifier;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
