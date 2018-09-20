/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.List;


/**
 * Interface voor alle bericht entiteiten. Dit zijn objecttypes waarnaar binnen een bericht kan worden gerefereerd en die van een specifiek type zijn.
 * Bericht entiteiten zijn dan ook objecttypes die in een bericht zitten, niet zijnde een super type en/of een koppeling tussen andere objecttypes).
 * <p/>
 * In deze interface worden de bericht specifieke attributen en functies gedefinieerd voor entiteiten in een bericht. Het gaat hierbij met name om
 * generieke attributen die in een bericht aan elementen kunnen worden toegewezen die objecttypes representeren in een bericht. Denk hierbij bijvoorbeeld
 * aan een technische sleutel van het object of de StUF entiteits type.
 */
public interface BerichtEntiteit extends BrpObject, BerichtIdentificeerbaar, MetaIdentificeerbaar {

    /**
     * Entiteit type is een StUF attribuut dat aangeeft om welk soort (Logische Ontwerp) type het gaat.
     *
     * @return de type.
     */
    String getObjecttype();

    /**
     * Getter voor de RefentieId; een bericht specifiek attribuut waarmee binnen een bericht naar een andere bericht entiteit kan worden gerefereerd.
     *
     * @return de id.
     */
    String getReferentieID();

    /**
     * Setter voor de RefentieId; een bericht specifiek attribuut waarmee binnen een bericht naar een andere bericht entiteit kan worden gerefereerd.
     *
     * @param referentieID de te zetten id.
     */
    void setReferentieID(String referentieID);

    /**
     * Getter voor de technische sleutel van het object; de sleutel die direct vertaald kan worden naar de object id van het object in de database.
     *
     * @return de technische sleutel van het object.
     */
    String getObjectSleutel();

    /**
     * Setter voor de technische sleutel van het object; de sleutel die direct vertaald kan worden naar de object id van het object in de database.
     *
     * @param objectSleutel de technische sleutel van het object.
     */
    void setObjectSleutel(String objectSleutel);

    /**
     * Retourneert een lijst van onderliggende {@link BerichtEntiteit} instanties; instanties van alle 1-op-1 gerelateerde berichtentiteiten (objecttypes)
     * en de inverse associaties, dus de 1-op-n gerelateerde berichtentiteiten.
     *
     * @return een lijst van bericht entiteiten (mogelijk leeg, maar altijd wel een lijst).
     */
    List<BerichtEntiteit> getBerichtEntiteiten();

    /**
     * Retourneert een lijst van tot deze BerichtEntiteit behorende {@link BerichtEntiteitGroep} instanties; instanties de tot de entiteit (objecttype)
     * behorende groepen.
     *
     * @return een lijst van bericht entiteit groepen (mogelijk leeg, maar altijd wel een lijst).
     */
    List<BerichtEntiteitGroep> getBerichtEntiteitGroepen();

    /**
     * Retourneert de (bericht) communicatie id van het element zoals geidentificeerd door het Database Object, waarbij ook gekeken wordt naar
     * onderliggende groepen en attributen. Indien het door de Meta IDs (behorende bij het opgegeven database Object) geidentificeerde element niet
     * aanwezig is in dit object, zal gewoon <code>null</code> geretourneerd worden.
     *
     * @param databaseObjectId het Meta identificerend object id dat aangeeft welk object gezocht wordt.
     * @return de communicatie id van het element, indien beschikbaar en element aanwezig is in object.
     */
    String getCommunicatieIdVoorElement(final Integer databaseObjectId);

}
