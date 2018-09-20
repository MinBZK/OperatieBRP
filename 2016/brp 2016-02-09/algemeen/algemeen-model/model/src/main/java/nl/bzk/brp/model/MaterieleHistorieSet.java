/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.VerantwoordingsEntiteit;


/**
 * Een slimme Set interface voor collections die C/D-laag entiteiten bevatten met materiele historie.
 *
 * @param <T> type historie entiteit wat in deze Set gaat.
 */
public interface MaterieleHistorieSet<T extends MaterieelHistorisch & MaterieelVerantwoordbaar> extends HistorieSet<T> {

    /**
     * Beëindig de historie over de periode van het periodeRecord. Dit houdt in dat de records vervallen die (gedeeltelijk) door deze periode worden
     * overlapt en dat eventuele kop en staart records worden aangemaakt voor de niet overlapte delen van deze records.
     *
     * @param periodeRecord            de betreffende periode
     * @param verantwoordingAanpassing de verantwoording om te gebruiken voor deze beeindiging
     */
    void beeindig(final MaterieleHistorie periodeRecord, final VerantwoordingsEntiteit verantwoordingAanpassing);

    /**
     * Laat het actuele record vervallen. Dit verval heeft geen invloed op andere historie
     * records.
     *
     * @param verantwoordingVerval verantwoordings entiteit dat de historie moet doen vervallen.
     * @param datumTijdVerval      tijdstip van vervallen.
     */
    void vervalActueleRecords(final VerantwoordingsEntiteit verantwoordingVerval, final DatumTijdAttribuut datumTijdVerval);

    /**
     * Laat alle records die dit nog niet zijn vervallen.
     *
     * @param verantwoordingVerval verantwoordings entiteit dat de historie moet doen vervallen.
     * @param datumTijdVerval      tijdstip van vervallen.
     */
    void vervalGeheleHistorie(final VerantwoordingsEntiteit verantwoordingVerval, final DatumTijdAttribuut datumTijdVerval);

    /**
     * Retourneert een niet te modificeren lijst van de niet vervallen historie. Dat wil zeggen de records die geen datum tijd verval hebben. Ook bekend
     * als de de C-laag.
     *
     * @return lijst van de huidige historie.
     */
    Set<T> getNietVervallenHistorie();

    /**
     * Selecteert alle records uit de C-laag (dus nog niet vervallen) die deels of geheel worden overlapt. Indien er geen dergelijke records aanwezig zijn,
     * zal een lege lijst worden geretourneerd.
     *
     * @param historie de historie waar de overlapping van gecheckt wordt
     * @return lijst van records die overlapt worden.
     */
    List<T> selecteerOverlapteActueleRecords(final MaterieleHistorie historie);

    /**
     * Retourneert een niet te modificeren lijst van de vervallen historie. Dat wil zeggen de records die een gevulde datum tijd verval hebben. Ook bekend
     * als de de D-laag.
     *
     * @return lijst van de huidige historie.
     */
    Set<T> getVervallenHistorie();

    /**
     * Retourneert het huidige geldende record voor materiele tijd vandaag en formeel tijdstip nu.
     *
     * @return het geldende record voor materiele tijd vandaag en formeel tijdstip nu.
     */
    @Override
    T getActueleRecord();

}
