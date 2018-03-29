/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import java.time.ZonedDateTime;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;

/**
 * AfnemerindicatieRepository.
 */
public interface AfnemerindicatieRepository {

    /**
     * Haal lijst met persoonafnemerindicaties op.
     * @param persoonId persoonsid
     * @return lijst met afnemerindicaties
     */
    List<PersoonAfnemerindicatie> haalAfnemerindicatiesOp(long persoonId);

    /**
     * Plaats een afnemerindicatie in de database.
     * @param persoonId het id van de persoon waarvoor de indicatie wordt geplaatst
     * @param partijId het id van de partij waar de afnemerindicatie wordt geplaatst
     * @param leveringsautorisatieId het id van de leveringsautorisatie
     * @param dienstId de verantwoordingdienst
     * @param datumEindeVolgen datum tot wanneer de afnemerindicatie relevant is
     * @param datumAanvangMaterielePeriode de materiele datum vanaf
     * @param tijdstipRegistratie tijdstip plaatsen afnemerindicatie
     */
    void plaatsAfnemerindicatie(long persoonId, short partijId, int leveringsautorisatieId, int dienstId, Integer datumEindeVolgen,
                                Integer datumAanvangMaterielePeriode, ZonedDateTime tijdstipRegistratie);

    /**
     * Verwijdert een afnemerindicatie.
     * @param persoonId het id van de persoon waarvoor de indicatie wordt verwijderd
     * @param partijId het id van de partij waar de afnemerindicatie wordt geplaatst
     * @param leveringsautorisatieId het id van de leveringsautorisatie
     * @param dienstId de verantwoordingsdienst
     */
    void verwijderAfnemerindicatie(long persoonId, short partijId, int leveringsautorisatieId, int dienstId);

}
