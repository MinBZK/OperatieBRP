/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonAfgeleidAdministratiefGroepModel;


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
public class PersoonAfgeleidAdministratiefGroepModel extends AbstractPersoonAfgeleidAdministratiefGroepModel implements
        PersoonAfgeleidAdministratiefGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonAfgeleidAdministratiefGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param tijdstipLaatsteWijziging tijdstipLaatsteWijziging van Afgeleid administratief.
     * @param indicatieGegevensInOnderzoek indicatieGegevensInOnderzoek van Afgeleid administratief.
     */
    public PersoonAfgeleidAdministratiefGroepModel(final DatumTijd tijdstipLaatsteWijziging,
            final JaNee indicatieGegevensInOnderzoek)
    {
        super(tijdstipLaatsteWijziging, indicatieGegevensInOnderzoek);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonAfgeleidAdministratiefGroep te kopieren groep.
     */
    public PersoonAfgeleidAdministratiefGroepModel(
            final PersoonAfgeleidAdministratiefGroep persoonAfgeleidAdministratiefGroep)
    {
        super(persoonAfgeleidAdministratiefGroep);
    }

}
