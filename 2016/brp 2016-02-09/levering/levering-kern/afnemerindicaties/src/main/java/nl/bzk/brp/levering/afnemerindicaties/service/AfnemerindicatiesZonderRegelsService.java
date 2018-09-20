/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;


/**
 * Met deze service kunnen afnemerindicaties geplaatst en verwijderd worden. Interne services roepen deze functies aan zonder regels.
 */
public interface AfnemerindicatiesZonderRegelsService {

    /**
     * Plaatst een afnemerindicatie zonder uitvoer van bedrijfregels. Gooit een AfnemerindicatieReedsAanwezigExceptie als er al een afnemerindicatie
     * bestaat voor de gegeven input. Let op dat de referenties voor de objecten die meegegeven worden vantevoren gevalideerd dienen te worden, anders is
     * er de kans dat er een OnbekendeReferentieEceptie optreedt, wanneer blijkt dat een gegeven niet vindbaar is.
     *
     * @param toegangLeveringautorisatieId        Het id van de toegangleveringsautorisatie.
     * @param persoonId                    De id van de persoon.
     * @param verantwoordingDienstId                     De dienst welke de wijziging verantwoordt
     * @param datumAanvangMaterielePeriode De datum aanvang materiele periode.
     * @param datumEindeVolgen             Datum einde volgen.
     * @param tijdstipRegistratie          Tijdstip registratie.
     */
    void plaatsAfnemerindicatie(int toegangLeveringautorisatieId,
        int persoonId,
        int verantwoordingDienstId,
        DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        DatumAttribuut datumEindeVolgen,
        DatumTijdAttribuut tijdstipRegistratie);

    /**
     * Plaatst een afnemerindicatie zonder uitvoer van bedrijfregels. Gooit een AfnemerindicatieReedsAanwezigExceptie als er al een afnemerindicatie
     * bestaat voor de gegeven input. Let op dat de referenties voor de objecten die meegegeven worden vantevoren gevalideerd dienen te worden, anders is
     * er de kans dat er een OnbekendeReferentieEceptie optreedt, wanneer blijkt dat een gegeven niet vindbaar is.
     *
     * @param toegangLeveringsautorisatie         De toegangleveringsautorisatie.
     * @param persoonId                    De id van de persoon.
     * @param verantwoordingDienstId                     De dienst welke de wijziging verantwoordt
     * @param datumAanvangMaterielePeriode De datum aanvang materiele periode.
     * @param datumEindeVolgen             Datum einde volgen.
     * @param tijdstipRegistratie          Tijdstip registratie.
     * @return de persoon weaar afnemer indicatie voor geplaatst is
     */
    PersoonAfnemerindicatieHisVolledigImpl plaatsAfnemerindicatie(ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        int persoonId,
        int verantwoordingDienstId,
        DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        DatumAttribuut datumEindeVolgen,
        DatumTijdAttribuut tijdstipRegistratie);

    /**
     * Verwijdert een afnemerindicatie zonder uitvoer van bedrijfregels. Gooit een VerplichteDataNietAanwezigExceptie als er geen afnemerindicatie gevonden
     * kan worden voor de gegeven input. Let op dat de referenties voor de objecten die meegegeven worden vantevoren gevalideerd dienen te worden, anders
     * is er de kans dat er een OnbekendeReferentieEceptie optreedt, wanneer blijkt dat een gegeven niet vindbaar is.
     *
     * TODO dit wordt enkel vanuit test gebruikt
     *
     * @param toegangLeveringsautorisatieId Het id van de toeganleveringsautorisatie.
     * @param persoonId             De id van de persoon.
     * @param verantwoordingDienstId              De dienst welke de wijziging verantwoordt.
     */
    void verwijderAfnemerindicatie(int toegangLeveringsautorisatieId,
        int persoonId,
        int verantwoordingDienstId);

}
