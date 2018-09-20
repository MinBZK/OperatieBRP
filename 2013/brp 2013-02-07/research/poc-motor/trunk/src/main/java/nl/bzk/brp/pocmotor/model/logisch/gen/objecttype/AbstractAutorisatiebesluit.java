/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.objecttype;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractStatischObjectType;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AutorisatiebesluitID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.AutorisatiebesluitBijhoudingsautorisatiebesluit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.AutorisatiebesluitIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.AutorisatiebesluitStandaard;


/**
 * Autorisatiebesluit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAutorisatiebesluit extends AbstractStatischObjectType {

   // Groepen
   protected AutorisatiebesluitIdentiteit identiteit = new AutorisatiebesluitIdentiteit();

   protected AutorisatiebesluitStandaard standaard;

   protected AutorisatiebesluitBijhoudingsautorisatiebesluit bijhoudingsautorisatiebesluit;



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_Autorisatiebesluit", sequenceName = "AutAut.seq_Autorisatiebesluit")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Autorisatiebesluit")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Integer id) {
        if (identiteit == null) {
            identiteit = new AutorisatiebesluitIdentiteit();
        }
        identiteit.setID(new AutorisatiebesluitID());
        identiteit.getID().setWaarde(id);
   }

   public AutorisatiebesluitIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final AutorisatiebesluitIdentiteit identiteit) {
      this.identiteit = identiteit;
   }

   public AutorisatiebesluitStandaard getStandaard() {
      return standaard;
   }

   public void setStandaard(final AutorisatiebesluitStandaard standaard) {
      this.standaard = standaard;
   }

   public AutorisatiebesluitBijhoudingsautorisatiebesluit getBijhoudingsautorisatiebesluit() {
      return bijhoudingsautorisatiebesluit;
   }

   public void setBijhoudingsautorisatiebesluit(final AutorisatiebesluitBijhoudingsautorisatiebesluit bijhoudingsautorisatiebesluit) {
      this.bijhoudingsautorisatiebesluit = bijhoudingsautorisatiebesluit;
   }



   // Getters/Setters Sets


}
