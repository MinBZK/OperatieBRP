/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.operationeel.actueel.DocumentStandaardGroepModel;
import nl.bzk.brp.model.objecttype.logisch.Document;
import nl.bzk.brp.model.objecttype.operationeel.BronModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortDocument;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;

/**
 * .
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractDocumentModel extends AbstractDynamischObjectType implements Document {

    @Id
    @SequenceGenerator(name = "DOCUMENT", sequenceName = "Kern.seq_Doc")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUMENT")
    private BigInteger                  id;

    @ManyToOne
    @JoinColumn(name = "Srt")
    private SoortDocument               soort;

    @Column(name = "docstatushis")
    private StatusHistorie              docstatushis;

    @Embedded
    private DocumentStandaardGroepModel standaard;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Doc")
    @OrderBy("id")
    @JsonIgnore
    private Set<BronModel>                           bronnen;
    /**
     * .
     */
    protected AbstractDocumentModel() {
        super();
    }

    /**
     * .
     * @param that .
     */
    protected AbstractDocumentModel(final Document that) {
        super();
        if (null != that) {
            soort = that.getSoort();
            if (null != that.getStandaard()) {
                standaard = new DocumentStandaardGroepModel(that.getStandaard());
                docstatushis = StatusHistorie.A;
            }
        }
    }

    /**
     * Retourneert Soort van Document.
     *
     * @return Soort.
     */
    @Override
    public SoortDocument getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Zet Soort van Document.
     *
     * @param soort Soort.
     */
    public void setSoort(final SoortDocument soort) {
        this.soort = soort;
    }

    /**
     * Zet Standaard van Document.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final DocumentStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    public Set<BronModel> getBronnen() {
        return Collections.unmodifiableSet(bronnen);
    }

    /**
     * .
     * @param bron .
     */
    public void voegBronnenToe(final BronModel bron) {
        if (null == bronnen) {
            bronnen = new HashSet<BronModel>();
        }
        bronnen.add(bron);
    }

    public BigInteger getId() {
        return id;
    }

}
