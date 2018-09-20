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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AbonnementID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortAbonnement;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Doelbinding;


/**
 * Abonnement.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAbonnementIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected AbonnementID iD;

   @ManyToOne
   @JoinColumn(name = "Doelbinding")
   protected Doelbinding doelbinding;

   @Column(name = "SrtAbonnement")
   protected SoortAbonnement soortAbonnement;


   public AbonnementID getID() {
      return iD;
   }

   public void setID(final AbonnementID iD) {
      this.iD = iD;
   }

   public Doelbinding getDoelbinding() {
      return doelbinding;
   }

   public void setDoelbinding(final Doelbinding doelbinding) {
      this.doelbinding = doelbinding;
   }

   public SoortAbonnement getSoortAbonnement() {
      return soortAbonnement;
   }

   public void setSoortAbonnement(final SoortAbonnement soortAbonnement) {
      this.soortAbonnement = soortAbonnement;
   }



}
