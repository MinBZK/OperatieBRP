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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AuthorisatiebesluitGegevenselementID;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Bijhoudingsautorisatie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Partij;


/**
 * Uitgeslotene.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractUitgesloteneIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected AuthorisatiebesluitGegevenselementID iD;

   @ManyToOne
   @JoinColumn(name = "Bijhautorisatie")
   protected Bijhoudingsautorisatie bijhoudingsautorisatie;

   @ManyToOne
   @JoinColumn(name = "UitgeslotenPartij")
   protected Partij uitgeslotenPartij;


   public AuthorisatiebesluitGegevenselementID getID() {
      return iD;
   }

   public void setID(final AuthorisatiebesluitGegevenselementID iD) {
      this.iD = iD;
   }

   public Bijhoudingsautorisatie getBijhoudingsautorisatie() {
      return bijhoudingsautorisatie;
   }

   public void setBijhoudingsautorisatie(final Bijhoudingsautorisatie bijhoudingsautorisatie) {
      this.bijhoudingsautorisatie = bijhoudingsautorisatie;
   }

   public Partij getUitgeslotenPartij() {
      return uitgeslotenPartij;
   }

   public void setUitgeslotenPartij(final Partij uitgeslotenPartij) {
      this.uitgeslotenPartij = uitgeslotenPartij;
   }



}
