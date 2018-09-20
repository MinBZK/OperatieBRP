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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RegelimplementatieID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.RegelimplementatieIdentiteit;


/**
 * Regelimplementatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRegelimplementatie extends AbstractStatischObjectType {

   // Groepen
   protected RegelimplementatieIdentiteit identiteit = new RegelimplementatieIdentiteit();



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_Regelimplementatie", sequenceName = "BRM.seq_Regelimplementatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Regelimplementatie")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Integer id) {
        if (identiteit == null) {
            identiteit = new RegelimplementatieIdentiteit();
        }
        identiteit.setID(new RegelimplementatieID());
        identiteit.getID().setWaarde(id);
   }

   public RegelimplementatieIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final RegelimplementatieIdentiteit identiteit) {
      this.identiteit = identiteit;
   }



   // Getters/Setters Sets


}
