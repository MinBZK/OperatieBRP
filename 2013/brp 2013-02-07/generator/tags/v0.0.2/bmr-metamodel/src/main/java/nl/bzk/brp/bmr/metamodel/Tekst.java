/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;


/**
 * Element is het supertype van alle BMR modelelementen.
 */
@Entity
@Immutable
public class Tekst {

    @Id
    @Column(name = "ID")
    private Long       id;

    @ManyToOne
    @JoinColumn(name = "ELEMENT")
    @Fetch(FetchMode.JOIN)
    private Element    element;

    private SoortTekst soort;
    private String     tekst;

    @Column(name = "HTML_TEKST")
    private String     htmlTekst;

    public Long getId() {
        return id;
    }

    public Element getElement() {
        return element;
    }

    public SoortTekst getSoort() {
        return soort;
    }

    public String getTekst() {
        return tekst;
    }

    public String getHtmlTekst() {
        return htmlTekst;
    }
}
