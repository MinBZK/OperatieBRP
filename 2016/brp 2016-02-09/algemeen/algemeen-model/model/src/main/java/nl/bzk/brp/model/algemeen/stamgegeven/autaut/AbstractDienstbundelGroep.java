/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * De koppeling tussen abonnement enerzijds en groep anderzijds.
 *
 * Afnemers verkrijgen gegevens uit één of meerdere groepen via een abonnement. Voor elke groep waartoe deze gegevens
 * behoren, wordt aangegeven of er extra informatie mag worden verstrekt in kader van dat abonnement. Zo mag in bepaalde
 * gevallen een ontvanger van gegevens uit de groep samengestelde_naam ook gegevens verkrijgen over de materiële
 * historie hiervan.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractDienstbundelGroep {

    @Id
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Dienstbundel")
    @Fetch(value = FetchMode.JOIN)
    private Dienstbundel dienstbundel;

    @ManyToOne
    @JoinColumn(name = "Groep")
    @Fetch(value = FetchMode.JOIN)
    private Element groep;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndFormeleHistorie"))
    private JaNeeAttribuut indicatieFormeleHistorie;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndMaterieleHistorie"))
    private JaNeeAttribuut indicatieMaterieleHistorie;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndVerantwoording"))
    private JaNeeAttribuut indicatieVerantwoording;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractDienstbundelGroep() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param dienstbundel dienstbundel van DienstbundelGroep.
     * @param groep groep van DienstbundelGroep.
     * @param indicatieFormeleHistorie indicatieFormeleHistorie van DienstbundelGroep.
     * @param indicatieMaterieleHistorie indicatieMaterieleHistorie van DienstbundelGroep.
     * @param indicatieVerantwoording indicatieVerantwoording van DienstbundelGroep.
     */
    protected AbstractDienstbundelGroep(
        final Dienstbundel dienstbundel,
        final Element groep,
        final JaNeeAttribuut indicatieFormeleHistorie,
        final JaNeeAttribuut indicatieMaterieleHistorie,
        final JaNeeAttribuut indicatieVerantwoording)
    {
        this.dienstbundel = dienstbundel;
        this.groep = groep;
        this.indicatieFormeleHistorie = indicatieFormeleHistorie;
        this.indicatieMaterieleHistorie = indicatieMaterieleHistorie;
        this.indicatieVerantwoording = indicatieVerantwoording;

    }

    /**
     * Retourneert ID van Dienstbundel \ Groep.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel van Dienstbundel \ Groep.
     *
     * @return Dienstbundel.
     */
    public final Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Retourneert Groep van Dienstbundel \ Groep.
     *
     * @return Groep.
     */
    public final Element getGroep() {
        return groep;
    }

    /**
     * Retourneert Formele historie? van Dienstbundel \ Groep.
     *
     * @return Formele historie?.
     */
    public final JaNeeAttribuut getIndicatieFormeleHistorie() {
        return indicatieFormeleHistorie;
    }

    /**
     * Retourneert Materiële historie? van Dienstbundel \ Groep.
     *
     * @return Materiële historie?.
     */
    public final JaNeeAttribuut getIndicatieMaterieleHistorie() {
        return indicatieMaterieleHistorie;
    }

    /**
     * Retourneert Verantwoording? van Dienstbundel \ Groep.
     *
     * @return Verantwoording?.
     */
    public final JaNeeAttribuut getIndicatieVerantwoording() {
        return indicatieVerantwoording;
    }

}
