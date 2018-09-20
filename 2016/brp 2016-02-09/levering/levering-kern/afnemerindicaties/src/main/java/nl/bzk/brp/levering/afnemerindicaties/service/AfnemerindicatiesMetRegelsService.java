/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import nl.bzk.brp.levering.afnemerindicaties.model.BewerkAfnemerindicatieResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;


/**
 * Met deze service kunnen afnemerindicaties geplaatst en verwijderd worden. Externe services roepen deze versie met regels aan, zodat de bedrijfregels
 * uitgevoerd worden.
 */
public interface AfnemerindicatiesMetRegelsService {

    /**
     * Plaatst een afnemerindicatie met uitvoer van bedrijfsregels.
     *
     * @param toeganglevingautorisatieId        Het id van de toegangleveringsautorisatie.
     * @param persoonId                    De id van de persoon.
     * @param verantwoordingDienstId                     De dienst welke de afnemerindicatie verantwoordt.
     * @param datumAanvangMaterielePeriode De datum aanvang materiele periode.
     * @param datumEindeVolgen             Datum einde volgen.
     * @return Het resultaat van de actie, met hierin de id van de administratieve handeling en enventuele meldingen.
     */
    BewerkAfnemerindicatieResultaat plaatsAfnemerindicatie(
        int toeganglevingautorisatieId,
        int persoonId,
        int verantwoordingDienstId,
        DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        DatumAttribuut datumEindeVolgen);

    /**
     * Plaatst een afnemerindicatie met uitvoer van bedrijfsregels.
     *
     * @param toegangLeveringsautorisatie         De toegangleveringsautorisatie.
     * @param persoonId                    De id van de persoon.
     * @param verantwoordingDienstId                     De dienst welke de afnemerindicatie verantwoordt.
     * @param datumAanvangMaterielePeriode De datum aanvang materiele periode.
     * @param datumEindeVolgen             Datum einde volgen.
     * @return Het resultaat van de actie, met hierin de id van de administratieve handeling en enventuele meldingen.
     */
    BewerkAfnemerindicatieResultaat plaatsAfnemerindicatie(
        ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        int persoonId,
        int verantwoordingDienstId,
        DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        DatumAttribuut datumEindeVolgen);


    /**
     * Plaatst een afnemerindicatie vanuit gba kanaal met uitvoer van bedrijfsregels.
     *
     * @param toegangLeveringsautorisatie         De toegangleveringsautorisatie.
     * @param persoonId                    De id van de persoon.
     * @param verantwoordingDienstId                     De dienst welke de afnemerindicatie verantwoordt.
     * @param datumAanvangMaterielePeriode De datum aanvang materiele periode.
     * @param datumEindeVolgen             Datum einde volgen.
     * @return Het resultaat van de actie, met hierin de id van de administratieve handeling en enventuele meldingen.
     */
    BewerkAfnemerindicatieResultaat gbaPlaatsAfnemerindicatie(ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        int persoonId,
        int verantwoordingDienstId,
        DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        DatumAttribuut datumEindeVolgen);

    /**
     * Verwijdert een afnemerindicatie met uitvoer van bedrijfsregels.
     *
     * @param toegangleveringautorisatieId  Het id van de toegangleveringsautorisaties.
     * @param persoonId             De id van de persoon.
     * @param verantwoordingDienstId              De dienst welke de afnemerindicatie verantwoordt.
     * @return Het resultaat van de actie, met hierin het id van de administratieve handeling en enventuele meldingen.
     */
    BewerkAfnemerindicatieResultaat verwijderAfnemerindicatie(int toegangleveringautorisatieId,
        int persoonId, int verantwoordingDienstId);

    /**
     * Verwijdert een afnemerindicatie vanuit het gba kanaal met uitvoer van bedrijfsregels.
     *
     * @param toegangLeveringsautorisatie De toegangLeveringsautorisatie.
     * @param persoonId            De id van de persoon.
     * @param verantwoordingDienstId             De dienst welke de afnemerindicatie verantwoordt.
     * @return Het resultaat van de actie, met hierin het id van de administratieve handeling en enventuele meldingen.
     */
    BewerkAfnemerindicatieResultaat gbaVerwijderAfnemerindicatie(ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        int persoonId, int verantwoordingDienstId);

    /**
     * Verwijdert een afnemerindicatie met uitvoer van bedrijfsregels.
     *
     * @param toegangLeveringsautorisatie De toegangLeveringsautorisatie.
     * @param persoonId            De id van de persoon.
     * @param verantwoordingDienstId             De dienst welke de afnemerindicatie verantwoordt.
     * @return Het resultaat van de actie, met hierin het id van de administratieve handeling en enventuele meldingen.
     */
    BewerkAfnemerindicatieResultaat verwijderAfnemerindicatie(ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        int persoonId, int verantwoordingDienstId);

}
