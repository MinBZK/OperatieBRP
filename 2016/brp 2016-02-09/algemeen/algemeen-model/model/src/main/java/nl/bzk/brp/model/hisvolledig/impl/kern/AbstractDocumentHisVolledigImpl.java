/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.DocumentHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.DocumentStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Document.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractDocumentHisVolledigImpl implements HisVolledigImpl, DocumentHisVolledigBasis, ALaagAfleidbaar, ElementIdentificeerbaar {

    @Transient
    @JsonProperty
    private Long iD;

    @Embedded
    @AssociationOverride(name = SoortDocumentAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Srt"))
    @JsonProperty
    private SoortDocumentAttribuut soort;

    @Embedded
    private DocumentStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisDocumentModel> hisDocumentLijst;

    @Transient
    private FormeleHistorieSet<HisDocumentModel> documentHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractDocumentHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Document.
     */
    public AbstractDocumentHisVolledigImpl(final SoortDocumentAttribuut soort) {
        this();
        this.soort = soort;

    }

    /**
     * Retourneert ID van Document.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "DOCUMENT", sequenceName = "Kern.seq_Doc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DOCUMENT")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Document.
     *
     * @return Soort.
     */
    public SoortDocumentAttribuut getSoort() {
        return soort;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Long id) {
        this.iD = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leidALaagAf() {
        final HisDocumentModel actueelStandaard = getDocumentHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard =
                    new DocumentStandaardGroepModel(
                        actueelStandaard.getIdentificatie(),
                        actueelStandaard.getAktenummer(),
                        actueelStandaard.getOmschrijving(),
                        actueelStandaard.getPartij());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisDocumentModel> getDocumentHistorie() {
        if (hisDocumentLijst == null) {
            hisDocumentLijst = new HashSet<>();
        }
        if (documentHistorie == null) {
            documentHistorie = new FormeleHistorieSetImpl<HisDocumentModel>(hisDocumentLijst);
        }
        return documentHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.DOCUMENT;
    }

}
