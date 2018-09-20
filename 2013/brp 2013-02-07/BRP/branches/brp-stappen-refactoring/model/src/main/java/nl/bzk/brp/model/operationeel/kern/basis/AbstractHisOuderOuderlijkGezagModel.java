/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.OuderModel;
import nl.bzk.brp.model.operationeel.kern.OuderOuderlijkGezagGroepModel;


/**
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
public abstract class AbstractHisOuderOuderlijkGezagModel extends AbstractMaterieleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_OUDEROUDERLIJKGEZAG", sequenceName = "Kern.seq_His_OuderOuderlijkGezag")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_OUDEROUDERLIJKGEZAG")
    @JsonProperty
    private Integer    iD;

    @ManyToOne
    @JoinColumn(name = "Betr")
    @JsonProperty
    private OuderModel betrokkenheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndOuderHeeftGezag"))
    @JsonProperty
    private JaNee      indicatieOuderHeeftGezag;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisOuderOuderlijkGezagModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param ouderModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisOuderOuderlijkGezagModel(final OuderModel ouderModel, final OuderOuderlijkGezagGroepModel groep) {
        this.betrokkenheid = ouderModel;
        this.indicatieOuderHeeftGezag = groep.getIndicatieOuderHeeftGezag();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisOuderOuderlijkGezagModel(final AbstractHisOuderOuderlijkGezagModel kopie) {
        super(kopie);
        betrokkenheid = kopie.getBetrokkenheid();
        indicatieOuderHeeftGezag = kopie.getIndicatieOuderHeeftGezag();

    }

    /**
     * Retourneert ID van His Ouder Ouderlijk gezag.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Betrokkenheid van His Ouder Ouderlijk gezag.
     *
     * @return Betrokkenheid.
     */
    public OuderModel getBetrokkenheid() {
        return betrokkenheid;
    }

    /**
     * Retourneert Ouder heeft gezag? van His Ouder Ouderlijk gezag.
     *
     * @return Ouder heeft gezag?.
     */
    public JaNee getIndicatieOuderHeeftGezag() {
        return indicatieOuderHeeftGezag;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public HisOuderOuderlijkGezagModel kopieer() {
        return new HisOuderOuderlijkGezagModel(this);
    }
}
