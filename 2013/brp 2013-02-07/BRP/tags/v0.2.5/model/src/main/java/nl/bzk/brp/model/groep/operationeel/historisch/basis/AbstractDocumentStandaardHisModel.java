/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.historisch.basis;

import java.math.BigInteger;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.FormeleHistorie;
import nl.bzk.brp.model.basis.FormeleHistorieImpl;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.groep.operationeel.AbstractDocumentStandaardGroep;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.DocumentModel;

/**
 * .
 *
 */
@SuppressWarnings("serial")
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractDocumentStandaardHisModel extends AbstractDocumentStandaardGroep
    implements FormeleHistorie
{

    @Id
    @SequenceGenerator(name = "HIS_DOCUMENT", sequenceName = "Kern.seq_His_Doc")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_DOCUMENT")
    private BigInteger            id;

    @ManyToOne
    @JoinColumn(name = "Doc")
    private DocumentModel         document;

    @Embedded
    private FormeleHistorieImpl historie;

    /** Default constructor tbv hibernate. */
    protected AbstractDocumentStandaardHisModel() {
        super();
    }

    /**
     * Retourneert ID van His Document.
     *
     * @return id.
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Retourneert Document van His Document.
     *
     * @return Document.
     */
    public DocumentModel getDocument() {
        return document;
    }

    /**
     * .
     *
     * @param documentStandaardGroep .
     * @param documentModel .
     */
    protected AbstractDocumentStandaardHisModel(final AbstractDocumentStandaardGroep documentStandaardGroep,
        final DocumentModel documentModel)
    {
        super(documentStandaardGroep);
        document = documentModel;
        if (documentStandaardGroep instanceof AbstractDocumentStandaardHisModel) {
            historie = new FormeleHistorieImpl(
                ((AbstractDocumentStandaardHisModel) documentStandaardGroep).getHistorie());
        } else {
            historie = new MaterieleHistorieImpl();
        }
    }

    /**
     * Retourneert een embedded historie object, waarin de formele/materiele historie gegevens staan.
     *
     * @return Historie object.
     */
    public FormeleHistorieImpl getHistorie() {
        return historie;
    }

    @Override
    public DatumTijd getDatumTijdRegistratie() {
        return historie.getDatumTijdRegistratie();
    }

    @Override
    public void setDatumTijdRegistratie(final DatumTijd datumTijdRegistratie) {
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
    }

    @Override
    public DatumTijd getDatumTijdVerval() {
        return historie.getDatumTijdVerval();
    }

    @Override
    public void setDatumTijdVerval(final DatumTijd datumTijdVerval) {
        historie.setDatumTijdVerval(datumTijdVerval);
    }

    @Override
    public ActieModel getActieInhoud() {
        return historie.getActieInhoud();
    }

    @Override
    public void setActieInhoud(final ActieModel actieInhoud) {
        historie.setActieInhoud(actieInhoud);
    }

    @Override
    public ActieModel getActieVerval() {
        return historie.getActieVerval();
    }

    @Override
    public void setActieVerval(final ActieModel actieVerval) {
        historie.setActieVerval(actieVerval);
    }

//    @Override
//    public Datum getDatumAanvangGeldigheid() {
//        return historie.getDatumAanvangGeldigheid();
//    }
//
//    @Override
//    public void setDatumAanvangGeldigheid(final Datum datumAanvangGeldigheid) {
//        historie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
//    }
//
//    @Override
//    public Datum getDatumEindeGeldigheid() {
//        return historie.getDatumEindeGeldigheid();
//    }
//
//    @Override
//    public void setDatumEindeGeldigheid(final Datum datumEindeGeldigheid) {
//        historie.setDatumEindeGeldigheid(datumEindeGeldigheid);
//    }
//
//    @Override
//    public ActieModel getActieAanpassingGeldigheid() {
//        return historie.getActieAanpassingGeldigheid();
//    }
//
//    @Override
//    public void setActieAanpassingGeldigheid(final ActieModel actieAanpassingGeldigheid) {
//        historie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
//    }

}
