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

import org.hibernate.annotations.Immutable;


/**
 * Element is het supertype van alle BMR modelelementen.
 */
@Entity
@Immutable
public class Tuple {

    @Id
    @Column(name = "ID")
    private Long       id;
    @ManyToOne
    @JoinColumn(name = "OBJECTTYPE")
    private ObjectType objectType;
    @Column(name = "RELATIEF_ID")
    private Integer    relatiefId;
    private String     code;
    private String     naam;
    @Column(name = "NAAM_MANNELIJK")
    private String     naamMannelijk;
    @Column(name = "NAAM_VROUWELIJK")
    private String     naamVrouwelijk;
    private String     omschrijving;
    @Column(name = "HEEFT_MATERIELE_HISTORIE")
    private String     heeftMaterieleHistorie;
    @Column(name = "DATUM_AANVANG_GELDIGHEID")
    private Integer    datumAanvang;
    @Column(name = "DATUM_EINDE_GELDIGHEID")
    private Integer    datumEinde;

    public String getCode() {
        return code;
    }

    public Boolean getHeeftMaterieleHistorie() {
        Boolean resultaat = null;

        if ("J".equals(heeftMaterieleHistorie)) {
            resultaat = true;
        } else if ("N".equals(heeftMaterieleHistorie)) {
            resultaat = false;
        }

        return resultaat;
    }

    public Long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getNaamMannelijk() {
        return naamMannelijk;
    }

    public String getNaamVrouwelijk() {
        return naamVrouwelijk;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public Integer getRelatiefId() {
        return relatiefId;
    }

    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    public Integer getDatumEinde() {
        return datumEinde;
    }
}
