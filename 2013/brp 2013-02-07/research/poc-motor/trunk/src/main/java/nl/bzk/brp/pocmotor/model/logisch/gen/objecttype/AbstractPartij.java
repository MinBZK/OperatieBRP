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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PartijID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PartijIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PartijStandaard;


/**
 * Partij

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPartij extends AbstractStatischObjectType {

   // Groepen
   protected PartijIdentiteit identiteit = new PartijIdentiteit();

   protected PartijStandaard standaard;



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_Partij", sequenceName = "Kern.seq_Partij")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Partij")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Integer id) {
        if (identiteit == null) {
            identiteit = new PartijIdentiteit();
        }
        identiteit.setID(new PartijID());
        identiteit.getID().setWaarde(id);
   }

   public PartijIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final PartijIdentiteit identiteit) {
      this.identiteit = identiteit;
   }

   public PartijStandaard getStandaard() {
      return standaard;
   }

   public void setStandaard(final PartijStandaard standaard) {
      this.standaard = standaard;
   }



   // Getters/Setters Sets


}
