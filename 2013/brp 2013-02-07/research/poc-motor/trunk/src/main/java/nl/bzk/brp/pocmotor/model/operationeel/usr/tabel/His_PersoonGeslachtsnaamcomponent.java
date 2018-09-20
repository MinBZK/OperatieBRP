/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.usr.tabel;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.pocmotor.model.operationeel.gen.tabel.AbstractHis_PersoonGeslachtsnaamcomponent;

/**
 * His Persoon \ Geslachtsnaamcomponent
  */
@Entity(name = "His_PersoonGeslachtsnaamcomponentOperationeel")
@Table(schema = "Kern", name = "His_PersGeslnaamcomp")
public class His_PersoonGeslachtsnaamcomponent extends AbstractHis_PersoonGeslachtsnaamcomponent {

    public His_PersoonGeslachtsnaamcomponent clone() {
        final His_PersoonGeslachtsnaamcomponent kopie = new His_PersoonGeslachtsnaamcomponent();
        kopie.setPersoonGeslachtsnaamcomponent(persoonGeslachtsnaamcomponent);
        kopie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        kopie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        kopie.setDatumTijdRegistratie(datumTijdRegistratie);
        kopie.setVoorvoegsel(voorvoegsel);
        kopie.setScheidingsteken(scheidingsteken);
        kopie.setNaam(naam);
        kopie.setPredikaat(predikaat);
        kopie.setAdellijkeTitel(adellijkeTitel);
        return kopie;
    }

}
