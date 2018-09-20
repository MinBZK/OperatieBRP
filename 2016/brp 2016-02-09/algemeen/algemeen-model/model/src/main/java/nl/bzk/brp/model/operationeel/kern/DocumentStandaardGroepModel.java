/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.logisch.kern.DocumentStandaardGroep;


/**
 * Vorm van historie: formeel. Motivatie: het objecttype wordt gebruikt voor de verantwoording van een specifieke BRP actie. Denkbaar is dat twee
 * verschillende BRP acties verwijzen naar hetzelfde Document; relevant is welke gegevens er toen geregistreerd stonden bij het Document, vandaar dat
 * formele historie relevant is. NB: dit onderdeel van het model is nog in ontwikkeling. Denkbaar is dat de modellering anders wordt. RvdP 17 jan 2012.
 */
@Embeddable
public class DocumentStandaardGroepModel extends AbstractDocumentStandaardGroepModel implements DocumentStandaardGroep {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected DocumentStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param identificatie identificatie van Standaard.
     * @param aktenummer    aktenummer van Standaard.
     * @param omschrijving  omschrijving van Standaard.
     * @param partij        partij van Standaard.
     */
    public DocumentStandaardGroepModel(final DocumentIdentificatieAttribuut identificatie,
        final AktenummerAttribuut aktenummer, final DocumentOmschrijvingAttribuut omschrijving,
        final PartijAttribuut partij)
    {
        super(identificatie, aktenummer, omschrijving, partij);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param documentStandaardGroep te kopieren groep.
     */
    public DocumentStandaardGroepModel(final DocumentStandaardGroep documentStandaardGroep) {
        super(documentStandaardGroep);
    }

}
