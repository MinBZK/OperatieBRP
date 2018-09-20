/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.ber;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * Representeert een bericht/communicatie van een levering. Dit geeft dus aan welk uitgaand bericht
 * behoord bij een bepaalde levering (=protocollering).
 */
@Entity
@Table(name = "levcommunicatie", schema = "lev")
@Access(AccessType.FIELD)
public class LeveringCommunicatie implements Serializable {

    @SequenceGenerator(name = "LEVCOMMUNICATIE_SEQUENCE_GENERATOR", sequenceName = "lev.seq_levcommunicatie")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEVCOMMUNICATIE_SEQUENCE_GENERATOR")
    private Long id;

    public Long getId() {
        return id;
    }

    @Column(name = "UitgaandBer", insertable = true, nullable = false, updatable = false)
    private Long     bericht;

    @Column(name = "lev", insertable = true, nullable = false, updatable = false)
    private Long levering;

    /**
     * Retourneert de id van het uitgaande bericht behorende bij de levering.
     * @return de id van het uitgaande bericht.
     */
    public Long getBericht() {
        return bericht;
    }

    /**
     * Zet de id van het uitgaande bericht behorende bij de levering.
     * @param bericht de id van het uitgaande bericht.
     */
    public void setBericht(final Long bericht) {
        this.bericht = bericht;
    }

    /**
     * Retourneert de id van de levering behorend bij het uitgaande bericht.
     * @return de id van de levering.
     */
    public Long getLevering() {
        return levering;
    }

    /**
     * Zet de id van de levering behorend bij het uitgaande bericht.
     * @param levering de id van levering.
     */
    public void setLevering(final Long levering) {
        this.levering = levering;
    }

}
