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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AbonnementSoortBerichtID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBericht;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Abonnement;


/**
 * Abonnement \ Soort bericht.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAbonnementSoortBerichtIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected AbonnementSoortBerichtID iD;

   @ManyToOne
   @JoinColumn(name = "Abonnement")
   protected Abonnement abonnement;

   @Column(name = "SrtBer")
   protected SoortBericht soortBericht;


   public AbonnementSoortBerichtID getID() {
      return iD;
   }

   public void setID(final AbonnementSoortBerichtID iD) {
      this.iD = iD;
   }

   public Abonnement getAbonnement() {
      return abonnement;
   }

   public void setAbonnement(final Abonnement abonnement) {
      this.abonnement = abonnement;
   }

   public SoortBericht getSoortBericht() {
      return soortBericht;
   }

   public void setSoortBericht(final SoortBericht soortBericht) {
      this.soortBericht = soortBericht;
   }



}
