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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BijhoudingsautorisatieID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.BijhoudingsautorisatieIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.BijhoudingsautorisatieStandaard;


/**
 * Bijhoudingsautorisatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBijhoudingsautorisatie extends AbstractStatischObjectType {

   // Groepen
   protected BijhoudingsautorisatieIdentiteit identiteit = new BijhoudingsautorisatieIdentiteit();

   protected BijhoudingsautorisatieStandaard standaard;



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_Bijhautorisatie", sequenceName = "AutAut.seq_Bijhautorisatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Bijhautorisatie")
   @Access(AccessType.PROPERTY)
   public Short getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Short id) {
        if (identiteit == null) {
            identiteit = new BijhoudingsautorisatieIdentiteit();
        }
        identiteit.setID(new BijhoudingsautorisatieID());
        identiteit.getID().setWaarde(id);
   }

   public BijhoudingsautorisatieIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final BijhoudingsautorisatieIdentiteit identiteit) {
      this.identiteit = identiteit;
   }

   public BijhoudingsautorisatieStandaard getStandaard() {
      return standaard;
   }

   public void setStandaard(final BijhoudingsautorisatieStandaard standaard) {
      this.standaard = standaard;
   }



   // Getters/Setters Sets


}
