/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.groep;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Geslachtsaanduiding;


/**
 * Persoon.Geslachtsaanduiding

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonGeslachtsaanduiding extends AbstractGroep {

   @Column(name = "Geslachtsaand")
   protected Geslachtsaanduiding geslachtsaanduiding;


   public Geslachtsaanduiding getGeslachtsaanduiding() {
      return geslachtsaanduiding;
   }

   public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
      this.geslachtsaanduiding = geslachtsaanduiding;
   }



}
