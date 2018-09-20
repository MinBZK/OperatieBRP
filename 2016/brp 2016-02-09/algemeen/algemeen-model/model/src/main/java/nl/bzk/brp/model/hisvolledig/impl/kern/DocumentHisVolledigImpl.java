/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.DocumentHisVolledig;


/**
 * HisVolledig klasse voor Document.
 */
@Entity
@Table(schema = "Kern", name = "Doc")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "iD", scope = DocumentHisVolledigImpl.class)
public class DocumentHisVolledigImpl extends AbstractDocumentHisVolledigImpl implements HisVolledigImpl,
    DocumentHisVolledig, ALaagAfleidbaar, ElementIdentificeerbaar
{

    /**
     * Default contructor voor JPA.
     */
    protected DocumentHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Document.
     */
    public DocumentHisVolledigImpl(final SoortDocumentAttribuut soort) {
        super(soort);
    }

}
