/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Meldingtekst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.ber.Melding;
import nl.bzk.brp.model.logisch.ber.basis.MeldingBasis;


/**
 * Het optreden van een soort melding naar aanleiding van het controleren van een Regel.
 *
 * Vanuit het oogpunt van het bewaken van de kwaliteit van de gegevens in de BRP, en het kunnen garanderen van een
 * correcte werking van de BRP, worden inkomende berichten gecontroleerd door bepaalde wetmatigheden te controleren:
 * Regels. Als een Regel iets constateerd, zal dat leiden tot een specifieke soort melding, en zal bekend zijn welk
 * attribuut of welke attributen daarbij het probleem veroorzaken.
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
public abstract class AbstractMeldingModel extends AbstractDynamischObjectType implements MeldingBasis {

    @Id
    @SequenceGenerator(name = "MELDING", sequenceName = "Ber.seq_Melding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MELDING")
    @JsonProperty
    private Long         iD;

    @Enumerated
    @Column(name = "Regel")
    @JsonProperty
    private Regel        regel;

    @Enumerated
    @Column(name = "Srt")
    @JsonProperty
    private SoortMelding soort;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Melding"))
    @JsonProperty
    private Meldingtekst melding;

    @Enumerated
    @Column(name = "Attribuut")
    @JsonProperty
    private Element      attribuut;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractMeldingModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param regel regel van Melding.
     * @param soort soort van Melding.
     * @param melding melding van Melding.
     * @param attribuut attribuut van Melding.
     */
    public AbstractMeldingModel(final Regel regel, final SoortMelding soort, final Meldingtekst melding,
            final Element attribuut)
    {
        this();
        this.regel = regel;
        this.soort = soort;
        this.melding = melding;
        this.attribuut = attribuut;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param melding Te kopieren object type.
     */
    public AbstractMeldingModel(final Melding melding) {
        this();
        this.regel = melding.getRegel();
        this.soort = melding.getSoort();
        this.melding = melding.getMelding();
        this.attribuut = melding.getAttribuut();

    }

    /**
     * Retourneert ID van Melding.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Regel van Melding.
     *
     * @return Regel.
     */
    public Regel getRegel() {
        return regel;
    }

    /**
     * Retourneert Soort van Melding.
     *
     * @return Soort.
     */
    public SoortMelding getSoort() {
        return soort;
    }

    /**
     * Retourneert Melding van Melding.
     *
     * @return Melding.
     */
    public Meldingtekst getMelding() {
        return melding;
    }

    /**
     * Retourneert Attribuut van Melding.
     *
     * @return Attribuut.
     */
    public Element getAttribuut() {
        return attribuut;
    }

}
