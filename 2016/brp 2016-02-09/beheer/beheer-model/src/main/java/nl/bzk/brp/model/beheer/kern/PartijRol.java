/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Een van toepassing zijnde combinatie van Partij en Rol.
 *
 * Een Partij kan één of meer Rollen vervullen. Elke Partij/Rol combinatie wordt vastgelegd. Dit betekent dat als
 * bijvoorbeeld de partij "Gemeente X" zowel de rol van "Afnemer" als van "Bijhoudingsorgaan" vervult, er twee
 * exemplaren zijn van Partij/Rol: één voor elke combinatie.
 *
 * Voor b.v. Partij/Rol worden toekomst-mutaties toegestaan (deze rol vervalt op/treedt in werking op). Dat betekent dat
 * een normale 'update via trigger' methode NIET werkt voor de A laag, immers, de actualiteit verandert op het moment
 * dat de geldigheid actueel wordt.
 *
 *
 *
 */
@Entity(name = "beheer.PartijRol")
@Table(schema = "Kern", name = "PartijRol")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class PartijRol {

    @Id
    @SequenceGenerator(name = "PARTIJROL", sequenceName = "Kern.seq_PartijRol")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PARTIJROL")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Partij")
    private Partij partij;

    @Column(name = "Rol")
    @Enumerated
    private Rol rol;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumAttribuut datumEinde;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partijRol", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisPartijRol> hisPartijRolLijst = new HashSet<>();

    /**
     * Retourneert ID van Partij \ Rol.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij van Partij \ Rol.
     *
     * @return Partij.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Rol van Partij \ Rol.
     *
     * @return Rol.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Rol getRol() {
        return rol;
    }

    /**
     * Retourneert Datum ingang van Partij \ Rol.
     *
     * @return Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Partij \ Rol.
     *
     * @return Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Standaard van Partij \ Rol.
     *
     * @return Standaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisPartijRol> getHisPartijRolLijst() {
        return hisPartijRolLijst;
    }

    /**
     * Zet ID van Partij \ Rol.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Partij van Partij \ Rol.
     *
     * @param pPartij Partij.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setPartij(final Partij pPartij) {
        this.partij = pPartij;
    }

    /**
     * Zet Rol van Partij \ Rol.
     *
     * @param pRol Rol.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRol(final Rol pRol) {
        this.rol = pRol;
    }

    /**
     * Zet Datum ingang van Partij \ Rol.
     *
     * @param pDatumIngang Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumIngang(final DatumAttribuut pDatumIngang) {
        this.datumIngang = pDatumIngang;
    }

    /**
     * Zet Datum einde van Partij \ Rol.
     *
     * @param pDatumEinde Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEinde(final DatumAttribuut pDatumEinde) {
        this.datumEinde = pDatumEinde;
    }

}
