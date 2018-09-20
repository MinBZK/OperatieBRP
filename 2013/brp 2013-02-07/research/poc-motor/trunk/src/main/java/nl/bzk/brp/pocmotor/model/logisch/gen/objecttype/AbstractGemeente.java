/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.objecttype;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.logisch.usr.groep.GemeenteStandaard;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Partij;


/**
 * Gemeente

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractGemeente extends Partij {

   // Groepen
   protected GemeenteStandaard gemeenteStandaard;



   // Sets


   // Getters/Setters Groepen
   public GemeenteStandaard getGemeenteStandaard() {
      return gemeenteStandaard;
   }

   public void setGemeenteStandaard(final GemeenteStandaard gemeenteStandaard) {
      this.gemeenteStandaard = gemeenteStandaard;
   }



   // Getters/Setters Sets


}
