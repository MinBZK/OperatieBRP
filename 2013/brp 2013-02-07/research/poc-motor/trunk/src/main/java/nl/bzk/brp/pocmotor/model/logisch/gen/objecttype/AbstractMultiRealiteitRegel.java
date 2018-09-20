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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractDynamischObjectType;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.MultiRealiteitregelID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.MultiRealiteitRegelIdentiteit;


/**
 * Multi-realiteit regel

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractMultiRealiteitRegel extends AbstractDynamischObjectType {

   // Groepen
   protected MultiRealiteitRegelIdentiteit identiteit = new MultiRealiteitRegelIdentiteit();



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_MultiRealiteitRegel", sequenceName = "Kern.seq_MultiRealiteitRegel")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_MultiRealiteitRegel")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Long id) {
        if (identiteit == null) {
            identiteit = new MultiRealiteitRegelIdentiteit();
        }
        identiteit.setID(new MultiRealiteitregelID());
        identiteit.getID().setWaarde(id);
   }

   public MultiRealiteitRegelIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final MultiRealiteitRegelIdentiteit identiteit) {
      this.identiteit = identiteit;
   }



   // Getters/Setters Sets


}
