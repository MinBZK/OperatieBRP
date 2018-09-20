/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.logisch.ber.AdministratieveHandelingDocument;
import nl.bzk.brp.model.operationeel.ber.basis.AbstractAdministratieveHandelingDocumentModel;
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
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.4.6.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-18 10:50:13.
 * Gegenereerd op: Tue Dec 18 10:54:29 CET 2012.
 */
@Entity
@Table(schema = "Ber", name = "AdmHndDoc")
public class AdministratieveHandelingDocumentModel extends AbstractAdministratieveHandelingDocumentModel implements
        AdministratieveHandelingDocument
{

    @Id
    @SequenceGenerator(name = "ADMINISTRATIEVEHANDELINGDOCUMENT",
                       sequenceName = "Ber.seq_AdmHndDoc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ADMINISTRATIEVEHANDELINGDOCUMENT")
    private Long iD;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     * 
     */
    protected AdministratieveHandelingDocumentModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     * 
     * @param administratieveHandeling administratieveHandeling van Administratieve handeling \ Document.
     * @param document document van Administratieve handeling \ Document.
     */
    public AdministratieveHandelingDocumentModel(final AdministratieveHandelingModel administratieveHandeling,
            final DocumentModel document)
    {
        super(administratieveHandeling, document);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     * 
     * @param administratieveHandelingDocument Te kopieren object type.
     * @param administratieveHandeling Bijbehorende Administratieve handeling.
     * @param document Bijbehorende Document.
     */
    public AdministratieveHandelingDocumentModel(
            final AdministratieveHandelingDocument administratieveHandelingDocument,
            final AdministratieveHandelingModel administratieveHandeling, final DocumentModel document)
    {
        super(administratieveHandelingDocument, administratieveHandeling, document);
    }

}
