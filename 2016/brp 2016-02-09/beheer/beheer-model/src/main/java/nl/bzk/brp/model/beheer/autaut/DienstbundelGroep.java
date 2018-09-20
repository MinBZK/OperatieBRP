/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@Entity(name = "beheer.DienstbundelGroep")
@Table(schema = "AutAut", name = "DienstbundelGroep")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class DienstbundelGroep {

    @Id
    @SequenceGenerator(name = "DIENSTBUNDELGROEP", sequenceName = "AutAut.seq_DienstbundelGroep")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DIENSTBUNDELGROEP")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Dienstbundel")
    private Dienstbundel dienstbundel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Groep")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienstbundelGroep", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisDienstbundelGroep> hisDienstbundelGroepLijst = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dienstbundelGroep", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<DienstbundelGroepAttribuut> attributen = new HashSet<>();

    /**
     * Retourneert ID van Dienstbundel \ Groep.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel van Dienstbundel \ Groep.
     *
     * @return Dienstbundel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Retourneert Groep van Dienstbundel \ Groep.
     *
     * @return Groep.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Element getGroep() {
        return groep;
    }

    /**
     * Retourneert Formele historie? van Dienstbundel \ Groep.
     *
     * @return Formele historie?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaNeeAttribuut getIndicatieFormeleHistorie() {
        return indicatieFormeleHistorie;
    }

    /**
     * Retourneert Materiële historie? van Dienstbundel \ Groep.
     *
     * @return Materiële historie?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaNeeAttribuut getIndicatieMaterieleHistorie() {
        return indicatieMaterieleHistorie;
    }

    /**
     * Retourneert Verantwoording? van Dienstbundel \ Groep.
     *
     * @return Verantwoording?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaNeeAttribuut getIndicatieVerantwoording() {
        return indicatieVerantwoording;
    }

    /**
     * Retourneert Standaard van Dienstbundel \ Groep.
     *
     * @return Standaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisDienstbundelGroep> getHisDienstbundelGroepLijst() {
        return hisDienstbundelGroepLijst;
    }

    /**
     * Retourneert de lijst van Dienstbundel \ Groep \ Attributen.
     *
     * @return lijst van Dienstbundel \ Groep \ Attributen
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<DienstbundelGroepAttribuut> getAttributen() {
        return attributen;
    }

    /**
     * Zet ID van Dienstbundel \ Groep.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Dienstbundel van Dienstbundel \ Groep.
     *
     * @param pDienstbundel Dienstbundel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDienstbundel(final Dienstbundel pDienstbundel) {
        this.dienstbundel = pDienstbundel;
    }

    /**
     * Zet Groep van Dienstbundel \ Groep.
     *
     * @param pGroep Groep.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setGroep(final Element pGroep) {
        this.groep = pGroep;
    }

    /**
     * Zet Formele historie? van Dienstbundel \ Groep.
     *
     * @param pIndicatieFormeleHistorie Formele historie?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieFormeleHistorie(final JaNeeAttribuut pIndicatieFormeleHistorie) {
        this.indicatieFormeleHistorie = pIndicatieFormeleHistorie;
    }

    /**
     * Zet Materiële historie? van Dienstbundel \ Groep.
     *
     * @param pIndicatieMaterieleHistorie Materiële historie?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieMaterieleHistorie(final JaNeeAttribuut pIndicatieMaterieleHistorie) {
        this.indicatieMaterieleHistorie = pIndicatieMaterieleHistorie;
    }

    /**
     * Zet Verantwoording? van Dienstbundel \ Groep.
     *
     * @param pIndicatieVerantwoording Verantwoording?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieVerantwoording(final JaNeeAttribuut pIndicatieVerantwoording) {
        this.indicatieVerantwoording = pIndicatieVerantwoording;
    }

}
