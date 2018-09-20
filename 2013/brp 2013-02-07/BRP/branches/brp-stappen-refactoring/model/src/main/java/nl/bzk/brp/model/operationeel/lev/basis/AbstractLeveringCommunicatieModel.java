/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.lev.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.lev.LeveringCommunicatie;
import nl.bzk.brp.model.logisch.lev.basis.LeveringCommunicatieBasis;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;


/**
 * De communicatie over een Levering, door middel van een bericht.
 *
 * Een Levering is (vaak) aanleiding tot ��n of meer uitgaande berichten: een bericht met de inhoud, en/of een bericht
 * met de melding dat de Levering klaar staat om opgehaald te worden. Al deze situaties worden gegeneraliseerd tot
 * 'communicatie over de leverring', of te wel Levering \ Communicatie.
 *
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
public abstract class AbstractLeveringCommunicatieModel extends AbstractDynamischObjectType implements
        LeveringCommunicatieBasis
{

    @Id
    @SequenceGenerator(name = "LEVERINGCOMMUNICATIE", sequenceName = "Lev.seq_LevCommunicatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LEVERINGCOMMUNICATIE")
    @JsonProperty
    private Long          iD;

    @ManyToOne
    @JoinColumn(name = "Lev")
    @JsonProperty
    private LeveringModel levering;

    @ManyToOne
    @JoinColumn(name = "UitgaandBer")
    @JsonProperty
    private BerichtModel  uitgaandBericht;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractLeveringCommunicatieModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param levering levering van Levering \ Communicatie.
     * @param uitgaandBericht uitgaandBericht van Levering \ Communicatie.
     */
    public AbstractLeveringCommunicatieModel(final LeveringModel levering, final BerichtModel uitgaandBericht) {
        this();
        this.levering = levering;
        this.uitgaandBericht = uitgaandBericht;

    }

    /**
     * Retourneert ID van Levering \ Communicatie.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Levering van Levering \ Communicatie.
     *
     * @return Levering.
     */
    public LeveringModel getLevering() {
        return levering;
    }

    /**
     * Retourneert Uitgaand bericht van Levering \ Communicatie.
     *
     * @return Uitgaand bericht.
     */
    public BerichtModel getUitgaandBericht() {
        return uitgaandBericht;
    }

}
