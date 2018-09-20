/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import java.util.Set;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroep;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;

/**
 * Interface voor de archivering service. De archivering service archiveert de berichten en biedt mogelijkheden om gedurende de berichtverwerking een
 * gearchiveerd bericht nog uit te breiden met additionele informatie.
 */
public interface ArchiveringService {

    /**
     * Archiveert een inkomend bericht en maakt een leeg uitgaand bericht aan dat eraan gerelateerd is.
     *
     * @param data het bericht dat gearchiveerd dient te worden.
     * @return een id paar (nooit null) bestaande uit het inkomende bericht id en het uitgaande bericht id.
     */
    BerichtenIds archiveer(String data);

    /**
     * Archiveert een bericht model.
     *
     * @param synchronisatieBerichtGegevens synchronisatie bericht gegevens
     * @return bericht model
     */
    BerichtModel archiveer(SynchronisatieBerichtGegevens synchronisatieBerichtGegevens);

    /**
     * Werk de ingaande bericht info bij in de database. Ingaand bericht is al deels opgeslagen tijdens archivering.
     *
     * @param ingaandBerichtId           Id van het ingaande bericht.
     * @param administratieveHandelingId id van de administratieve handeling
     * @param stuurgegevensIngaand       Bericht stuurgegevens van inkomend bericht
     * @param parametersIngaand          bericht parameters van inkomend bericht
     * @param soortBericht               Het soort binnenkomend bericht.
     * @param teArchiverenPersonen       database id's van personen die gearchiveerd moeten worden bij het ingaande bericht
     */
    void werkIngaandBerichtInfoBij(Long ingaandBerichtId,
        Long administratieveHandelingId,
        BerichtStuurgegevensGroepBericht stuurgegevensIngaand,
        BerichtParametersGroepBericht parametersIngaand,
        SoortBericht soortBericht,
        Set<Integer> teArchiverenPersonen);

    /**
     * Werk de uitgaande bericht info bij in de database. Uitgaand bericht is al deels opgeslagen tijdens archivering.
     *
     * @param uitgaandBerichtId          Id van het uitgaande bericht.
     * @param administratieveHandelingId id van de administratieve handeling
     * @param stuurgegevensGroep         De stuurgegevens van het uitgaande bericht.
     * @param berichtResultaatGroep      Het resultaat van de verwerking.
     * @param soortBericht               Soort van uitgaande bericht.
     * @param la                         de leveringsautorisatie.
     * @param verwerkingswijze           De verwerkingswijze.
     * @param teArchiverenPersonen       database id's van personen die gearchiveerd moeten worden bij het uitgaande bericht
     */
    void werkUitgaandBerichtInfoBij(Long uitgaandBerichtId,
        Long administratieveHandelingId,
        BerichtStuurgegevensGroepBericht stuurgegevensGroep,
        BerichtResultaatGroep berichtResultaatGroep,
        SoortBerichtAttribuut soortBericht,
        Leveringsautorisatie la,
        VerwerkingswijzeAttribuut verwerkingswijze,
        Set<Integer> teArchiverenPersonen);


    /**
     * Werkt de data van een uitgaand bericht data bij in de database.
     *
     * @param uitgaandBerichtId Id van het uitgaande bericht wat bijgewerkt moet worden.
     * @param nieuweData        De data die bij het uitgaande bericht hoort.
     */
    void werkDataBij(Long uitgaandBerichtId, String nieuweData);
}
