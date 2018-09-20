/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

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

import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.VerstrekkingDerdeModel;


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
public abstract class AbstractHisVerstrekkingDerdeModel extends AbstractFormeleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_VERSTREKKINGDERDE", sequenceName = "Kern.seq_His_VerstrDerde")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_VERSTREKKINGDERDE")
    @JsonProperty
    private Integer                iD;

    @ManyToOne
    @JoinColumn(name = "VerstrDerde")
    private VerstrekkingDerdeModel verstrekkingDerde;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisVerstrekkingDerdeModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param verstrekkingDerdeModel instantie van A-laag klasse.
     */
    public AbstractHisVerstrekkingDerdeModel(final VerstrekkingDerdeModel verstrekkingDerdeModel) {
        this.verstrekkingDerde = verstrekkingDerdeModel;

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisVerstrekkingDerdeModel(final AbstractHisVerstrekkingDerdeModel kopie) {
        super(kopie);
        verstrekkingDerde = kopie.getVerstrekkingDerde();

    }

    /**
     * Retourneert ID van His Verstrekking derde.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Verstrekking derde van His Verstrekking derde.
     *
     * @return Verstrekking derde.
     */
    public VerstrekkingDerdeModel getVerstrekkingDerde() {
        return verstrekkingDerde;
    }

}
