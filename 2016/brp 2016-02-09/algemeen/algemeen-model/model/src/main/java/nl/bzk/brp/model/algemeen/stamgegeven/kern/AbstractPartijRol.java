/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

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
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractPartijRol implements ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    private Partij partij;

    @Column(name = "Rol")
    private Rol rol;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumAttribuut datumEinde;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractPartijRol() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param partij partij van PartijRol.
     * @param rol rol van PartijRol.
     * @param datumIngang datumIngang van PartijRol.
     * @param datumEinde datumEinde van PartijRol.
     */
    protected AbstractPartijRol(final Partij partij, final Rol rol, final DatumAttribuut datumIngang, final DatumAttribuut datumEinde) {
        this.partij = partij;
        this.rol = rol;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;

    }

    /**
     * Retourneert ID van Partij \ Rol.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij van Partij \ Rol.
     *
     * @return Partij.
     */
    public final Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Rol van Partij \ Rol.
     *
     * @return Rol.
     */
    public final Rol getRol() {
        return rol;
    }

    /**
     * Retourneert Datum ingang van Partij \ Rol.
     *
     * @return Datum ingang.
     */
    public final DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Partij \ Rol.
     *
     * @return Datum einde.
     */
    public final DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PARTIJROL;
    }

}
