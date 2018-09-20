/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatie;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;


/**
 * De indicatie dat een persoon wordt gevolgd door een afnemer in kader van een abonnement.
 * <p/>
 * Partijen (lees: afnemers) kunnen zich 'abonneren' op een persoon. Dit is normaliter in kader van een specifiek abonnement; voorzien is echter dat niet
 * in alle gevallen het is te relateren aan precies ��n abonnement.
 */
@Entity
@Table(schema = "AutAut", name = "PersAfnemerindicatie")
public class PersoonAfnemerindicatieModel extends AbstractPersoonAfnemerindicatieModel implements
    PersoonAfnemerindicatie, ModelIdentificeerbaar<Long>
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonAfnemerindicatieModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon    persoon van Persoon \ Afnemerindicatie.
     * @param afnemer    afnemer van Persoon \ Afnemerindicatie.
     * @param leveringsautorisatie leveringsautorisatie van Persoon \ Afnemerindicatie.
     */
    public PersoonAfnemerindicatieModel(final PersoonModel persoon, final PartijAttribuut afnemer,
        final LeveringsautorisatieAttribuut leveringsautorisatie)
    {
        super(persoon, afnemer, leveringsautorisatie);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonAfnemerindicatie Te kopieren object type.
     * @param persoon                 Bijbehorende Persoon.
     */
    public PersoonAfnemerindicatieModel(final PersoonAfnemerindicatie persoonAfnemerindicatie,
        final PersoonModel persoon)
    {
        super(persoonAfnemerindicatie, persoon);
    }

}
