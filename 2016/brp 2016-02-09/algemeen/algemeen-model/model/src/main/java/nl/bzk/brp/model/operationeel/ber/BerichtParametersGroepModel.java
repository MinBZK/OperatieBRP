/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.StamgegevenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RolAttribuut;
import nl.bzk.brp.model.logisch.ber.BerichtParametersGroep;


/**
 *
 *
 */
@Embeddable
public class
    BerichtParametersGroepModel extends AbstractBerichtParametersGroepModel implements BerichtParametersGroep {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected BerichtParametersGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param verwerkingswijze             verwerkingswijze van Parameters.
     * @param bezienVanuitPersoon          bezienVanuitPersoon van Parameters.
     * @param soortSynchronisatie          soortSynchronisatie van Parameters.
     * @param leveringsautorisatieId                 abonnement van Parameters.
     * @param dienstId              dienstId van Parameters.
     * @param peilmomentMaterieelSelectie  peilmomentMaterieelSelectie van Parameters.
     * @param peilmomentMaterieelResultaat peilmomentMaterieelResultaat van Parameters.
     * @param peilmomentFormeelResultaat   peilmomentFormeelResultaat van Parameters.
     * @param stamgegeven                  stamgegeven van Parameters.
     */
    public BerichtParametersGroepModel(final VerwerkingswijzeAttribuut verwerkingswijze, final SleutelwaardetekstAttribuut bezienVanuitPersoon,
        final RolAttribuut rol,
        final SoortSynchronisatieAttribuut soortSynchronisatie, final Integer leveringsautorisatieId,
        final Integer dienstId, final DatumAttribuut peilmomentMaterieelSelectie,
        final DatumAttribuut peilmomentMaterieelResultaat, final DatumTijdAttribuut peilmomentFormeelResultaat,
        final StamgegevenAttribuut stamgegeven)
    {
        super(verwerkingswijze, bezienVanuitPersoon, rol, soortSynchronisatie, leveringsautorisatieId, dienstId, peilmomentMaterieelSelectie,
            peilmomentMaterieelResultaat, peilmomentFormeelResultaat, stamgegeven);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtParametersGroep te kopieren groep.
     */
    public BerichtParametersGroepModel(final BerichtParametersGroep berichtParametersGroep) {
        super(berichtParametersGroep);
    }

    @Override
    public DatumAttribuut getPeilmomentMaterieelSelectie() {
        throw new IllegalStateException(
            "ermul: handmatige wijziging: nog discussie over, dit moet nog vlakgestreken worden in BMR");
    }
}
