/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Een besluit van de minister van BZK of een College van Burgemeester en Wethouders dat de juridische grondslag vormt
 * voor het autoriseren van Partijen.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Autorisatiebesluit", schema = "AutAut")
@Access(AccessType.FIELD)
public class AutorisatieBesluit implements Serializable {

    @SequenceGenerator(name = "AUTORISATIEBESLUIT_SEQUENCE_GENERATOR", sequenceName = "AutAut.seq_Autorisatiebesluit")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTORISATIEBESLUIT_SEQUENCE_GENERATOR")
    private Long                    id;
    @Column(name = "Srt")
    private SoortAutorisatieBesluit soort;
    @Column(name = "Besluittekst")
    private String                  besluitTekst;
    @ManyToOne
    @JoinColumn(name = "Autoriseerder", insertable = false, updatable = false)
    private Partij                  autoriseerder;
    @Column(name = "IndModelBesluit")
    private Boolean                 modelBesluit;
    @ManyToOne
    @JoinColumn(name = "GebaseerdOp", insertable = false, updatable = false)
    private AutorisatieBesluit      gebaseerdOp;
    @Column(name = "IndIngetrokken")
    private Boolean                 ingetrokken;

    /**
     * No-arg constructor voor JPA.
     */
    protected AutorisatieBesluit() {
    }

    /**
     * Constructor voor programmatische instantiatie, met parameters voor alle verplichte attributen.
     *
     * @param soort soort autorisatiebesluit.
     * @param besluitTekst de tekst behorende bij een autorisatiebesluit.
     * @param autoriseerder de partij die het autorisatiebesluit heeft geautoriseerd.
     */
    public AutorisatieBesluit(final SoortAutorisatieBesluit soort, final String besluitTekst,
            final Partij autoriseerder)
    {
        super();
        this.soort = soort;
        this.besluitTekst = besluitTekst;
        this.autoriseerder = autoriseerder;
    }

    public Long getId() {
        return id;
    }

    /**
     * Er zijn zowel Autorisatiebesluiten waarmee Afnemers in staat worden gesteld gegevens van de BRP te raadplegen,
     * als Autorisatiebesluiten waarmee Bijhoudingsorganen in staat worden gesteld bijhoudingen te doen. De typering
     * van een Autorisatiebesluit gebeurt middels het attribuut 'Soort autorisatiebesluit'.
     *
     * @return het type autorisatiebesluit.
     */
    public SoortAutorisatieBesluit getSoort() {
        return soort;
    }

    /**
     * De tekst uit het Autorisatiebesluit.
     *
     * @return de tekst uit het Autorisatiebesluit.
     */
    public String getBesluitTekst() {
        return besluitTekst;
    }

    /**
     * De Partij, dan wel de verantwoordelijke voor de Partij, die het Autorisatiebesluit heeft genomen.
     *
     * @return de Partij, dan wel de verantwoordelijke voor de Partij, die het Autorisatiebesluit heeft genomen.
     */
    public Partij getAutoriseerder() {
        return autoriseerder;
    }

    /**
     * Indicatie die aangeeft of een Autorisatiebesluit WEL of NIET is gebaseerd op een Model besluit.
     *
     * @return indicatie die aangeeft of een Autorisatiebesluit is gebaseerd op een Model besluit.
     */
    public Boolean isModelBesluit() {
        return modelBesluit;
    }

    /**
     * Het Autorisatiebesluit waarop het onderhavige Autorisatiebesluit gebaseerd is.
     *
     * Een Autorisatiebesluit kan gebaseerd zijn op een Model autorisatiebesluit. In dat geval wijst dit gegeven naar
     * het Autorisatiebesluit dat als Model heeft gefungeerd.
     *
     * @return het Autorisatiebesluit waarop het onderhavige Autorisatiebesluit gebaseerd is.
     */
    public AutorisatieBesluit getGebaseerdOp() {
        return gebaseerdOp;
    }

    /**
     * Indicatie die aangeeft of het Autorisatiebesluit is ingetrokken.
     *
     * @return indicatie die aangeeft of het Autorisatiebesluit is ingetrokken.
     */
    public Boolean isIngetrokken() {
        return ingetrokken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id).append("soort", soort)
                .append("besluitTekst", besluitTekst).append("autoriseerder", autoriseerder).toString();
    }

}
