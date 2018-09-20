/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Applicatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Organisatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Sleutelwaardetekst;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroep;
import nl.bzk.brp.model.operationeel.ber.basis.AbstractBerichtStuurgegevensGroepModel;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:36 CET 2012.
 */
@Embeddable
public class BerichtStuurgegevensGroepModel extends AbstractBerichtStuurgegevensGroepModel implements
        BerichtStuurgegevensGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected BerichtStuurgegevensGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param organisatie organisatie van Stuurgegevens.
     * @param applicatie applicatie van Stuurgegevens.
     * @param referentienummer referentienummer van Stuurgegevens.
     * @param crossReferentienummer crossReferentienummer van Stuurgegevens.
     */
    public BerichtStuurgegevensGroepModel(final Organisatienaam organisatie, final Applicatienaam applicatie,
            final Sleutelwaardetekst referentienummer, final Sleutelwaardetekst crossReferentienummer)
    {
        super(organisatie, applicatie, referentienummer, crossReferentienummer);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtStuurgegevensGroep te kopieren groep.
     */
    public BerichtStuurgegevensGroepModel(final BerichtStuurgegevensGroep berichtStuurgegevensGroep) {
        super(berichtStuurgegevensGroep);
    }

}
