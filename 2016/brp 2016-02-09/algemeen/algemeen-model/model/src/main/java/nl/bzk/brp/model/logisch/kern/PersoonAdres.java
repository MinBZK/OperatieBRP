/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;


/**
 * Het adres zoals gedefinieerd in artikel 1.1. van de Wet BRP.
 * <p/>
 * Het adres is in essentie de koppeling "van een aanduiding van een bepaalde plek op aarde waarmee die plek kan worden geadresseerd" en de Persoon.
 * Adresgegevens behoren tot de meest gebruikte gegevens binnen administraties van de overheid en semi-overheid. Bij deze gegevens komt de
 * Basisadministratie Adressen en Gebouwen (BAG) nadrukkelijk in beeld, omdat de BRP verplicht gebruik moet maken van de gegevens in de BAG. Historische
 * adressen die vanuit de GBA-periode opgenomen (moeten) worden en die in de GBA geen BAG-gegevens bevatten, worden zonder BAG-verwijzing opgenomen. De
 * adresgegevens worden overgenomen uit de lokale BAG, en niet uit de centrale LV BAG.
 * <p/>
 * In dit objecttype wordt het adres gekoppeld aan de persoon. Dezelfde "plek op aarde", gekoppeld met twee verschillende Personen, heeft dus twee
 * exemplaren van Adres tot gevolg.
 */
public interface PersoonAdres extends PersoonAdresBasis {

}
