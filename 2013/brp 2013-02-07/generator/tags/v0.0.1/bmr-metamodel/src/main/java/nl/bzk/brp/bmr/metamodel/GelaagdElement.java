/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * Abstract supertype voor die elementen die een laag en een versie (en nog wat attributen) hebben, maar geen eigen
 * 'soort'.
 */
@MappedSuperclass
public class GelaagdElement extends Element {

    @Column(name = "versie_tag")
    private VersieTag versieTag;

    @Column(name = "IDENT_DB")
    private String    identifierDB;
    @Column(name = "IDENT_CODE")
    private String    identifierCode;
    private Integer   volgnummer;

    public String getIdentifierDB() {
        return identifierDB;
    }

    public String getIdentifierCode() {
        return identifierCode;
    }

    public void setIdentifierCode(final String identifierCode) {
        this.identifierCode = identifierCode;
    }

    public VersieTag getVersieTag() {
        return versieTag;
    }

    public Integer getVolgnummer() {
        return volgnummer;
    }

}
