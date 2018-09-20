/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.usr.objecttype;

import java.util.TreeSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.logisch.gen.objecttype.AbstractPersoon;

/**
 * Persoon
 */
@Entity
@Table(schema = "Kern", name = "Pers")
@Access(AccessType.FIELD)
public class Persoon extends AbstractPersoon implements RootObject {

    public void voegNationaliteitToe(final PersoonNationaliteit persoonNationaliteit) {
        if (nationaliteiten == null) {
            nationaliteiten = new TreeSet<PersoonNationaliteit>();
        }
        nationaliteiten.add(persoonNationaliteit);
    }

    public void voegVoorNaamToe(final PersoonVoornaam persoonVoornaam) {
         if (voornamen == null) {
             voornamen = new TreeSet<PersoonVoornaam>();
         }
        voornamen.add(persoonVoornaam);
    }

    public void voegGeslachtsNaamToe(PersoonGeslachtsnaamcomponent geslNaam) {
        if (geslachtsnaamcomponenten == null) {
            geslachtsnaamcomponenten = new TreeSet<PersoonGeslachtsnaamcomponent>();
        }
        geslachtsnaamcomponenten.add(geslNaam);
    }
}
