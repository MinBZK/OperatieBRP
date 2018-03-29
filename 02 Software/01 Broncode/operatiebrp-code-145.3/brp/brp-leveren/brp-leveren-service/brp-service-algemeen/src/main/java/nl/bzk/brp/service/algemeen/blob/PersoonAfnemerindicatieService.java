/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.services.blobber.BlobException;

/**
 * De service die het serialiseren en deserialiseren van afnemerindicaties mogelijk maakt.
 */
public interface PersoonAfnemerindicatieService {

    /**
     * Plaatst een afnemerindicatie zonder uitvoer van bedrijfregels. Gooit een AfnemerindicatieReedsAanwezigExceptie als er al een afnemerindicatie
     * bestaat voor de gegeven input. Let op dat de referenties voor de objecten die meegegeven worden vantevoren gevalideerd dienen te worden, anders is
     * er de kans dat er een OnbekendeReferentieEceptie optreedt, wanneer blijkt dat een gegeven niet vindbaar is.
     * @param afnemerindicatieParameters persoon parameters
     * @param partij De geautoriseerde partij
     * @param leveringsautorisatieId De toegangleveringsautorisatie.
     * @param verantwoordingDienstId De dienst welke de wijziging verantwoordt
     * @param datumAanvangMaterielePeriode De datum aanvang materiele periode.
     * @param datumEindeVolgen Datum einde volgen.
     * @param tsReg tsReg
     **/
    void plaatsAfnemerindicatie(AfnemerindicatieParameters afnemerindicatieParameters, Partij partij,
                                int leveringsautorisatieId,
                                int verantwoordingDienstId, Integer datumAanvangMaterielePeriode,
                                Integer datumEindeVolgen, ZonedDateTime tsReg);


    /**
     * Verwijdert een afnemerindicatie zonder uitvoer van bedrijfregels. Gooit een VerplichteDataNietAanwezigExceptie als er geen afnemerindicatie gevonden
     * kan worden voor de gegeven input. Let op dat de referenties voor de objecten die meegegeven worden vantevoren gevalideerd dienen te worden, anders
     * is er de kans dat er een OnbekendeReferentieExceptie optreedt, wanneer blijkt dat een gegeven niet vindbaar is.
     * @param afnemerindicatieParameters persoon parameters
     * @param partij De geautoriseerde partij
     * @param verantwoordingDienstId De dienst welke de wijziging verantwoordt.
     * @param leveringsautorisatieId Het id van de leveringautorisatie.
     */
    void verwijderAfnemerindicatie(final AfnemerindicatieParameters afnemerindicatieParameters, Partij partij,
                                   int verantwoordingDienstId,
                                   int leveringsautorisatieId);

    /**
     * Maakt een nieuwe afnemerindicatie blob.
     * @param afnemerindicatieParameters afnemerindicatieParameters
     * @throws BlobException als het wegschrijven naar de blob faalt
     */
    void updateAfnemerindicatieBlob(AfnemerindicatieParameters afnemerindicatieParameters) throws BlobException;
}
