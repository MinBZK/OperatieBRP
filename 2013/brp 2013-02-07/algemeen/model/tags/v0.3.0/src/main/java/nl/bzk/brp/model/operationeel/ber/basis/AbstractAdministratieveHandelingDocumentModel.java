/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.ber.basis.AdministratieveHandelingDocumentBasis;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;


/**
 * De vastlegging van de in de Administratieve handeling gebruikte Documenten.
 *
 * Om een administratieve handeling te kunnen verantwoorden, kent de BRP een mechanisme waarin Acties via een
 * koppeltabel worden verantwoord door Documenten. Hierbij is het wenselijk dat eventuele nieuwe documenten kunnen
 * worden gescand. Om deze reden is er een vehikel nodig om de (technische) id's van de Documenten terug te leveren, op
 * het niveau van het bericht c.q. de Administratieve handeling, in plaats van het niveau waarin het is vastgelegd,
 * zijnde de Actie. Hierbij zijn de koppelingen tussen Administratieve handeling enerzijds, en Document anderzijds,
 * afleidbaar uit de (wel geadministreerde) koppeltabel tussen Actie en Document.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractAdministratieveHandelingDocumentModel extends AbstractDynamischObjectType implements
        AdministratieveHandelingDocumentBasis
{

    @Transient
    private AdministratieveHandelingModel administratieveHandeling;

    @Transient
    @JsonProperty
    private DocumentModel                 document;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractAdministratieveHandelingDocumentModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param administratieveHandeling administratieveHandeling van Administratieve handeling \ Document.
     * @param document document van Administratieve handeling \ Document.
     */
    public AbstractAdministratieveHandelingDocumentModel(final AdministratieveHandelingModel administratieveHandeling,
            final DocumentModel document)
    {
        this();
        this.administratieveHandeling = administratieveHandeling;
        this.document = document;

    }

    /**
     * Retourneert Administratieve handeling van Administratieve handeling \ Document.
     *
     * @return Administratieve handeling.
     */
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Retourneert Document van Administratieve handeling \ Document.
     *
     * @return Document.
     */
    public DocumentModel getDocument() {
        return document;
    }

}
