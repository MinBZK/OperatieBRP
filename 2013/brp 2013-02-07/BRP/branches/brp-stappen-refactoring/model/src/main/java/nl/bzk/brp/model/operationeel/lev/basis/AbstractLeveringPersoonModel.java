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
import nl.bzk.brp.model.logisch.lev.LeveringPersoon;
import nl.bzk.brp.model.logisch.lev.basis.LeveringPersoonBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;


/**
 * Het betrokken zijn van een Persoon in een Levering.
 *
 * Bij een Levering van Persoonsgegevens, zijn ��n of meer Personen het "onderwerp" van de Levering. Indien een Persoon
 * onderwerp is van een Levering, dan wordt de koppeling tussen deze Levering en de Persoon vastgelegd.
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
public abstract class AbstractLeveringPersoonModel extends AbstractDynamischObjectType implements LeveringPersoonBasis {

    @Id
    @SequenceGenerator(name = "LEVERINGPERSOON", sequenceName = "Lev.seq_LevPers")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LEVERINGPERSOON")
    @JsonProperty
    private Long          iD;

    @ManyToOne
    @JoinColumn(name = "Lev")
    @JsonProperty
    private LeveringModel levering;

    @ManyToOne
    @JoinColumn(name = "Pers")
    @JsonProperty
    private PersoonModel  persoon;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractLeveringPersoonModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param levering levering van Levering \ Persoon.
     * @param persoon persoon van Levering \ Persoon.
     */
    public AbstractLeveringPersoonModel(final LeveringModel levering, final PersoonModel persoon) {
        this();
        this.levering = levering;
        this.persoon = persoon;

    }

    /**
     * Retourneert ID van Levering \ Persoon.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Levering van Levering \ Persoon.
     *
     * @return Levering.
     */
    public LeveringModel getLevering() {
        return levering;
    }

    /**
     * Retourneert Persoon van Levering \ Persoon.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

}
