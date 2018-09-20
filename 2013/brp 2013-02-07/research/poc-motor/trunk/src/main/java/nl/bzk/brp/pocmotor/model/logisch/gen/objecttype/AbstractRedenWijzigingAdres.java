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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RedenWijzigingAdresID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.RedenWijzigingAdresIdentiteit;


/**
 * Reden wijziging adres

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRedenWijzigingAdres extends AbstractStatischObjectType {

   // Groepen
   protected RedenWijzigingAdresIdentiteit identiteit = new RedenWijzigingAdresIdentiteit();



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_RdnWijzAdres", sequenceName = "Kern.seq_RdnWijzAdres")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_RdnWijzAdres")
   @Access(AccessType.PROPERTY)
   public Short getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Short id) {
        if (identiteit == null) {
            identiteit = new RedenWijzigingAdresIdentiteit();
        }
        identiteit.setID(new RedenWijzigingAdresID());
        identiteit.getID().setWaarde(id);
   }

   public RedenWijzigingAdresIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final RedenWijzigingAdresIdentiteit identiteit) {
      this.identiteit = identiteit;
   }



   // Getters/Setters Sets


}
