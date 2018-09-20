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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LeveringBerichtID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.LeveringCommunicatieIdentiteit;


/**
 * Levering \ Communicatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractLeveringCommunicatie extends AbstractStatischObjectType {

   // Groepen
   protected LeveringCommunicatieIdentiteit identiteit = new LeveringCommunicatieIdentiteit();



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_LevCommunicatie", sequenceName = "Lev.seq_LevCommunicatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_LevCommunicatie")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Long id) {
        if (identiteit == null) {
            identiteit = new LeveringCommunicatieIdentiteit();
        }
        identiteit.setID(new LeveringBerichtID());
        identiteit.getID().setWaarde(id);
   }

   public LeveringCommunicatieIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final LeveringCommunicatieIdentiteit identiteit) {
      this.identiteit = identiteit;
   }



   // Getters/Setters Sets


}
