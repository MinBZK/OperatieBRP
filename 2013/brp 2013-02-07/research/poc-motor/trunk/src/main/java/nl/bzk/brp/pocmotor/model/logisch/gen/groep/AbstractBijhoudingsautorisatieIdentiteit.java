/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.groep;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BijhoudingsautorisatieID;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Autorisatiebesluit;


/**
 * Bijhoudingsautorisatie.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBijhoudingsautorisatieIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected BijhoudingsautorisatieID iD;

   @ManyToOne
   @JoinColumn(name = "Bijhautorisatiebesluit")
   protected Autorisatiebesluit bijhoudingsautorisatiebesluit;


   public BijhoudingsautorisatieID getID() {
      return iD;
   }

   public void setID(final BijhoudingsautorisatieID iD) {
      this.iD = iD;
   }

   public Autorisatiebesluit getBijhoudingsautorisatiebesluit() {
      return bijhoudingsautorisatiebesluit;
   }

   public void setBijhoudingsautorisatiebesluit(final Autorisatiebesluit bijhoudingsautorisatiebesluit) {
      this.bijhoudingsautorisatiebesluit = bijhoudingsautorisatiebesluit;
   }



}
