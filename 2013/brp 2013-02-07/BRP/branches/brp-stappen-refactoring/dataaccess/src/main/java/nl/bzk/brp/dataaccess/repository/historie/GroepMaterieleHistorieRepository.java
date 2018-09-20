/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.historie;

import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MaterieleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 * Interface die de juiste afhandeling van historie regelt in de C en D Laag voor groepen.
 *
 * @param <T> A-Laag object waarvoor de historie wordt geregeld. (Het object type)
 * @param <U> De groep die weggeschreven moet worden.
 * @param <Y> De historie records (Het objectHis type)
 */
public interface GroepMaterieleHistorieRepository<
    T extends AbstractDynamischObjectType,
    U extends Groep,
    Y extends MaterieleHistorieEntiteit>
{

    /**
     * Regelt het persisteren van de historie op basis van het aangepaste/aangemaakte persoon object.
     *
     * @param objectType Object type uit de A-Laag
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid Datum aanvang geldigheid van het nieuwe/aangepaste A-Laag record.
     * @param datumEindeGeldigheid Datum einde geldigheid van het nieuwe/aangepaste A-Laag record.
     */
    void persisteerHistorie(final T objectType,
        final ActieModel actie,
        final Datum datumAanvangGeldigheid,
        final Datum datumEindeGeldigheid);

    /**
     * Regelt het persisteren van de historie op basis van het aangepaste/aangemaakte persoon object.
     *
     * @param objectType Object type uit de A-Laag
     * @param groep gegevens die de groep informatie bevat die in de history weggeschreven moet worden.
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid Datum aanvang geldigheid van het nieuwe/aangepaste A-Laag record.
     * @param datumEindeGeldigheid Datum einde geldigheid van het nieuwe/aangepaste A-Laag record.
     */
    void persisteerHistorie(final T objectType, final U groep,
        final ActieModel actie,
        final Datum datumAanvangGeldigheid,
        final Datum datumEindeGeldigheid);

    /**
     * Selecteer een lijst van historie records an een A-laag groep.
     * De lijst is als volgt gesorteerd: <br/>
     * - tijdstipVervallen, (null eerst, dan jongste eerst, oudste laatst). Hierdoor komt de CLaag als eerste.<br/>
     * - datumAanvangGeldigheid (per 'verval tijdmoment, de jongste eerst). <br/>
     * - datumEindeGeldigheid idem.
     * - id als laatste.
     *
     * @param objectType Objecttype uit A laag waarvoor historie records moeten worden geselecteerd.
     * @param inclFormeleHistorie moeten de formele historie ook meegenomen worden.
     * @return de lijst van historie records.
     */
    List<Y> haalopHistorie(final T objectType, final boolean inclFormeleHistorie);

    /**
     * Haal de op dat moment geldige record uit de materiele historie (CLaag) op een bepaal datum.
     * Let op, dat dit null kan zijn. (ie. het peilDatum is voor de eerste aanvangDatum record).
     * Er wordt NIET gekeken naar de DLaag.
     *
     * @param objectType de Object type uit de A-Laag
     * @param peildDatum de peil datum
     * @return het op dat moment geldige historie record.
     */
    Y haalGeldigRecord(final T objectType, final Datum peildDatum);

    /**
     * Haal de op dat moment geldige record uit de materiele historie (CLaag) op het systeem datum.
     * Let op, dat dit null kan zijn. (ie. het peilDatum is voor de eerste aanvangDatum record).
     * Er wordt NIET gekeken naar de DLaag.
     *
     * @param objectType de Object type uit de A-Laag
     * @return het op dat moment geldige historie record.
     */
    Y haalGeldigRecord(final T objectType);
}
