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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.GegevenselementAbonnementID;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Abonnement;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.DatabaseObject;


/**
 * Abonnement \ Gegevenselement.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAbonnementGegevenselementIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected GegevenselementAbonnementID iD;

   @ManyToOne
   @JoinColumn(name = "Abonnement")
   protected Abonnement abonnement;

   @ManyToOne
   @JoinColumn(name = "Gegevenselement")
   protected DatabaseObject gegevenselement;


   public GegevenselementAbonnementID getID() {
      return iD;
   }

   public void setID(final GegevenselementAbonnementID iD) {
      this.iD = iD;
   }

   public Abonnement getAbonnement() {
      return abonnement;
   }

   public void setAbonnement(final Abonnement abonnement) {
      this.abonnement = abonnement;
   }

   public DatabaseObject getGegevenselement() {
      return gegevenselement;
   }

   public void setGegevenselement(final DatabaseObject gegevenselement) {
      this.gegevenselement = gegevenselement;
   }



}
