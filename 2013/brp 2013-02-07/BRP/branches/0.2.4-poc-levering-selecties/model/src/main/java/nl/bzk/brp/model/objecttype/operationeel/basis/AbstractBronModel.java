/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.objecttype.logisch.basis.BronBasis;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.DocumentModel;

/**
 * .
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractBronModel extends AbstractDynamischObjectType implements BronBasis {
    @Id
    @SequenceGenerator(name = "BRON", sequenceName = "Kern.seq_Bron")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BRON")
    @JsonProperty
    private Integer                  id;

    @ManyToOne (cascade = { CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "Actie")
    @NotNull
    @JsonIgnore
    private ActieModel    actie;

    @ManyToOne
    @JoinColumn(name = "Doc")
    @NotNull
    @JsonProperty
    private DocumentModel document;

    /**
     * .
     */
    protected AbstractBronModel() {
        super();
    }

    /**
     * .
     * @param bron .
     * @param actieModel .
     */
    protected AbstractBronModel(final BronBasis bron, final ActieModel actieModel) {
        super();
        actie = actieModel;
        if (null != bron && null != bron.getDocument()) {
            document = new DocumentModel(bron.getDocument());
        }
    }

    /**
     * Retourneert Actie van Bron.
     *
     * @return Actie.
     */
    @Override
    public ActieModel getActie() {
        return actie;
    }

    /**
     * Retourneert Document van Bron.
     *
     * @return Document.
     */
    @Override
    public DocumentModel getDocument() {
        return document;
    }

    /**
     * Zet Actie van Bron.
     *
     * @param actie Actie.
     */
    public void setActie(final ActieModel actie) {
        this.actie = actie;
    }

    /**
     * Zet Document van Bron.
     *
     * @param document Document.
     */
    public void setDocument(final DocumentModel document) {
        this.document = document;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }


}
