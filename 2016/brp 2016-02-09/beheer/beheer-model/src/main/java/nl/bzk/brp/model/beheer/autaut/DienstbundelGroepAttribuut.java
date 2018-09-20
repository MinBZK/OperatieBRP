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
import javax.persistence.CascadeType;
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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Het van toepassing zijn van een attribuut bij een abonnement.
 *
 * Een afnemer mag normaliter in kader van een abonnement over bepaalde soorten gegevens beschikken, zoals bijvoorbeeld
 * over het BSN of de geboortedatum. De gegevens waarover de afnemer mag beschikken, worden door elementen vastgelegd.
 * Zo wordt een afnemer geautoriseerd voor het beschikken over het BSN van een persoon door het betreffende abonnement
 * te koppelen aan het element voor BSN.
 *
 *
 *
 */
@Entity(name = "beheer.DienstbundelGroepAttribuut")
@Table(schema = "AutAut", name = "DienstbundelGroepAttr")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class DienstbundelGroepAttribuut {

    @Id
    @SequenceGenerator(name = "DIENSTBUNDELGROEPATTRIBUUT", sequenceName = "AutAut.seq_DienstbundelGroepAttr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DIENSTBUNDELGROEPATTRIBUUT")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DienstbundelGroep")
    private DienstbundelGroep dienstbundelGroep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Attr")
    private Element attribuut;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienstbundelGroepAttribuut", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisDienstbundelGroepAttribuut> hisDienstbundelGroepAttribuutLijst = new HashSet<>();

    /**
     * Retourneert ID van Dienstbundel \ Groep \ Attribuut.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel \ Groep van Dienstbundel \ Groep \ Attribuut.
     *
     * @return Dienstbundel \ Groep.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DienstbundelGroep getDienstbundelGroep() {
        return dienstbundelGroep;
    }

    /**
     * Retourneert Attribuut van Dienstbundel \ Groep \ Attribuut.
     *
     * @return Attribuut.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Element getAttribuut() {
        return attribuut;
    }

    /**
     * Retourneert Standaard van Dienstbundel \ Groep \ Attribuut.
     *
     * @return Standaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisDienstbundelGroepAttribuut> getHisDienstbundelGroepAttribuutLijst() {
        return hisDienstbundelGroepAttribuutLijst;
    }

    /**
     * Zet ID van Dienstbundel \ Groep \ Attribuut.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Dienstbundel \ Groep van Dienstbundel \ Groep \ Attribuut.
     *
     * @param pDienstbundelGroep Dienstbundel \ Groep.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDienstbundelGroep(final DienstbundelGroep pDienstbundelGroep) {
        this.dienstbundelGroep = pDienstbundelGroep;
    }

    /**
     * Zet Attribuut van Dienstbundel \ Groep \ Attribuut.
     *
     * @param pAttribuut Attribuut.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setAttribuut(final Element pAttribuut) {
        this.attribuut = pAttribuut;
    }

}
