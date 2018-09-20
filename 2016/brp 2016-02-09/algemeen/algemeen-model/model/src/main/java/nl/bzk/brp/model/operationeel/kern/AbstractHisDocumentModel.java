/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.DocumentHisVolledig;
import nl.bzk.brp.model.logisch.kern.DocumentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.DocumentStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3784)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisDocumentModel extends AbstractFormeelHistorischMetActieVerantwoording implements DocumentStandaardGroepBasis,
        ModelIdentificeerbaar<Long>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_DOCUMENT", sequenceName = "Kern.seq_His_Doc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_DOCUMENT")
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DocumentHisVolledigImpl.class)
    @JoinColumn(name = "Doc")
    @JsonBackReference
    private DocumentHisVolledig document;

    @Embedded
    @AttributeOverride(name = DocumentIdentificatieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Ident"))
    @JsonProperty
    private DocumentIdentificatieAttribuut identificatie;

    @Embedded
    @AttributeOverride(name = AktenummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Aktenr"))
    @JsonProperty
    private AktenummerAttribuut aktenummer;

    @Embedded
    @AttributeOverride(name = DocumentOmschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    @JsonProperty
    private DocumentOmschrijvingAttribuut omschrijving;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisDocumentModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param document document van HisDocumentModel
     * @param identificatie identificatie van HisDocumentModel
     * @param aktenummer aktenummer van HisDocumentModel
     * @param omschrijving omschrijving van HisDocumentModel
     * @param partij partij van HisDocumentModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisDocumentModel(
        final DocumentHisVolledig document,
        final DocumentIdentificatieAttribuut identificatie,
        final AktenummerAttribuut aktenummer,
        final DocumentOmschrijvingAttribuut omschrijving,
        final PartijAttribuut partij,
        final ActieModel actieInhoud)
    {
        this.document = document;
        this.identificatie = identificatie;
        this.aktenummer = aktenummer;
        this.omschrijving = omschrijving;
        this.partij = partij;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisDocumentModel(final AbstractHisDocumentModel kopie) {
        super(kopie);
        document = kopie.getDocument();
        identificatie = kopie.getIdentificatie();
        aktenummer = kopie.getAktenummer();
        omschrijving = kopie.getOmschrijving();
        partij = kopie.getPartij();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param documentHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisDocumentModel(final DocumentHisVolledig documentHisVolledig, final DocumentStandaardGroep groep, final ActieModel actieInhoud) {
        this.document = documentHisVolledig;
        this.identificatie = groep.getIdentificatie();
        this.aktenummer = groep.getAktenummer();
        this.omschrijving = groep.getOmschrijving();
        this.partij = groep.getPartij();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Document.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Document van His Document.
     *
     * @return Document.
     */
    public DocumentHisVolledig getDocument() {
        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9701)
    public DocumentIdentificatieAttribuut getIdentificatie() {
        return identificatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9702)
    public AktenummerAttribuut getAktenummer() {
        return aktenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9703)
    public DocumentOmschrijvingAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9704)
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (identificatie != null) {
            attributen.add(identificatie);
        }
        if (aktenummer != null) {
            attributen.add(aktenummer);
        }
        if (omschrijving != null) {
            attributen.add(omschrijving);
        }
        if (partij != null) {
            attributen.add(partij);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.DOCUMENT_STANDAARD;
    }

}
