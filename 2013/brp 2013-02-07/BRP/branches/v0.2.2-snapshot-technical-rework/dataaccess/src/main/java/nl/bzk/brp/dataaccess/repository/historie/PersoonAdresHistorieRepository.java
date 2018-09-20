/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.historie;

import java.util.List;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonAdresStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresStandaardHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * Repository om de historie op te slaan van PersoonAdres.
 *
 */
public interface PersoonAdresHistorieRepository extends
        GroepHistorieRepository<PersoonAdresModel, AbstractPersoonAdresStandaardGroep, PersoonAdresStandaardHisModel>
{

    /**
     * Haalt alle records in de C-laag op die veranderd zijn door historie aanpassingen.
     *
     * @param persoon de persoon van het adres
     * @param tijdstipRegistratie het moment van registratie, alle records zouden op hetzelfde moment veranderd moeten
     *            zijn
     * @return lijst van aangepast C-Laag records
     */
    List<PersoonAdresStandaardHisModel> haalHistorieGewijzigdeRecordsOp(final PersoonModel persoon,
            final DatumTijd tijdstipRegistratie);
}
