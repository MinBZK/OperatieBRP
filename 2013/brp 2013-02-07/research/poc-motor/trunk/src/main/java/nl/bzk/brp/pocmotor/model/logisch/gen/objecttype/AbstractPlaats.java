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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PlaatsID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PlaatsIdentiteit;


/**
 * Plaats

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPlaats extends AbstractStatischObjectType {

   // Groepen
   protected PlaatsIdentiteit identiteit = new PlaatsIdentiteit();



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_Plaats", sequenceName = "Kern.seq_Plaats")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Plaats")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Integer id) {
        if (identiteit == null) {
            identiteit = new PlaatsIdentiteit();
        }
        identiteit.setID(new PlaatsID());
        identiteit.getID().setWaarde(id);
   }

   public PlaatsIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final PlaatsIdentiteit identiteit) {
      this.identiteit = identiteit;
   }



   // Getters/Setters Sets


}
