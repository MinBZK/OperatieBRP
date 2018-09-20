/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Een van toepassing zijnde combinatie van Partij en Rol.
 *
 * Een Partij kan ��n of meer Rollen vervullen. Elke Partij/Rol combinatie wordt vastgelegd. Dit betekent dat als
 * bijvoorbeeld de partij "Gemeente X" zowel de rol van "Afnemer" als van "Bijhoudingsorgaan" vervult, er twee
 * exemplaren zijn van Partij/Rol: ��n voor elke combinatie.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractPartijRol extends AbstractStatischObjectType {

    @Id
    private Integer        iD;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    private Partij         partij;

    @Column(name = "Rol")
    private Rol            rol;

    @Type(type = "StatusHistorie")
    @Column(name = "PartijRolStatusHis")
    private StatusHistorie partijRolStatusHis;

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
     * @param partijRolStatusHis partijRolStatusHis van PartijRol.
     */
    protected AbstractPartijRol(final Partij partij, final Rol rol, final StatusHistorie partijRolStatusHis) {
        this.partij = partij;
        this.rol = rol;
        this.partijRolStatusHis = partijRolStatusHis;

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
    public Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Rol van Partij \ Rol.
     *
     * @return Rol.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Retourneert Partij \ Rol StatusHis van Partij \ Rol.
     *
     * @return Partij \ Rol StatusHis.
     */
    public StatusHistorie getPartijRolStatusHis() {
        return partijRolStatusHis;
    }

}
