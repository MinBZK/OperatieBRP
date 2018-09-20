/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.usr.tabel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.pocmotor.model.operationeel.gen.tabel.AbstractHis_PersoonAdres;

/**
 * His Persoon \ Adres
  */
@Entity(name = "His_PersoonAdresOperationeel")
@Table(schema = "Kern", name = "His_PersAdres")
@Access(AccessType.FIELD)
public class His_PersoonAdres extends AbstractHis_PersoonAdres {

    public His_PersoonAdres clone() {
        His_PersoonAdres kopie = new His_PersoonAdres();
        kopie.aangeverAdreshouding = aangeverAdreshouding;
        kopie.adresseerbaarObject = adresseerbaarObject;
        kopie.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
        kopie.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
        kopie.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
        kopie.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
        kopie.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
        kopie.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
        kopie.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
        kopie.datumAanvangAdreshouding = datumAanvangAdreshouding;
        kopie.datumVertrekUitNederland = datumVertrekUitNederland;
        kopie.gemeente = gemeente;
        kopie.gemeentedeel = gemeentedeel;
        kopie.huisletter = huisletter;
        kopie.huisnummer = huisnummer;
        kopie.huisnummertoevoeging = huisnummertoevoeging;
        kopie.land = land;
        kopie.soort = soort;
        kopie.naamOpenbareRuimte = naamOpenbareRuimte;
        kopie.datumAanvangGeldigheid = datumAanvangGeldigheid;
        kopie.locatieOmschrijving = locatieOmschrijving;
        kopie.locatietovAdres = locatietovAdres;
        kopie.postcode = postcode;
        kopie.redenWijziging = redenWijziging;
        kopie.woonplaats = woonplaats;
        //@TODO DE rest nog....

        kopie.persoonAdres = persoonAdres;
        return kopie;
    }

}
