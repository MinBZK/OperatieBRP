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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DoelbindingID;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Autorisatiebesluit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Partij;


/**
 * Doelbinding.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractDoelbindingIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected DoelbindingID iD;

   @ManyToOne
   @JoinColumn(name = "Levsautorisatiebesluit")
   protected Autorisatiebesluit leveringsautorisatiebesluit;

   @ManyToOne
   @JoinColumn(name = "Geautoriseerde")
   protected Partij geautoriseerde;


   public DoelbindingID getID() {
      return iD;
   }

   public void setID(final DoelbindingID iD) {
      this.iD = iD;
   }

   public Autorisatiebesluit getLeveringsautorisatiebesluit() {
      return leveringsautorisatiebesluit;
   }

   public void setLeveringsautorisatiebesluit(final Autorisatiebesluit leveringsautorisatiebesluit) {
      this.leveringsautorisatiebesluit = leveringsautorisatiebesluit;
   }

   public Partij getGeautoriseerde() {
      return geautoriseerde;
   }

   public void setGeautoriseerde(final Partij geautoriseerde) {
      this.geautoriseerde = geautoriseerde;
   }



}
