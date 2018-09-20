/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Autorisatiebesluit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisAutorisatiebesluit {

    @Id
    @SequenceGenerator(name = "HIS_AUTORISATIEBESLUIT", sequenceName = "AutAut.seq_His_Autorisatiebesluit")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_AUTORISATIEBESLUIT")
    @JsonProperty
    private Integer            iD;

    @ManyToOne
    @JoinColumn(name = "Autorisatiebesluit")
    @Fetch(value = FetchMode.JOIN)
    private Autorisatiebesluit autorisatiebesluit;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndIngetrokken"))
    @JsonProperty
    private JaNee              indicatieIngetrokken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatBesluit"))
    @JsonProperty
    private Datum              datumBesluit;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatIngang"))
    @JsonProperty
    private Datum              datumIngang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
    @JsonProperty
    private Datum              datumEinde;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisAutorisatiebesluit() {
    }

    /**
     * Retourneert ID van His Autorisatiebesluit.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Autorisatiebesluit van His Autorisatiebesluit.
     *
     * @return Autorisatiebesluit.
     */
    public Autorisatiebesluit getAutorisatiebesluit() {
        return autorisatiebesluit;
    }

    /**
     * Retourneert Ingetrokken? van His Autorisatiebesluit.
     *
     * @return Ingetrokken?.
     */
    public JaNee getIndicatieIngetrokken() {
        return indicatieIngetrokken;
    }

    /**
     * Retourneert Datum besluit van His Autorisatiebesluit.
     *
     * @return Datum besluit.
     */
    public Datum getDatumBesluit() {
        return datumBesluit;
    }

    /**
     * Retourneert Datum ingang van His Autorisatiebesluit.
     *
     * @return Datum ingang.
     */
    public Datum getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Autorisatiebesluit.
     *
     * @return Datum einde.
     */
    public Datum getDatumEinde() {
        return datumEinde;
    }

}
