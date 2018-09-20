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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RedenVerkrijgingNLNationaliteitID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.RedenVerkrijgingNLNationaliteitIdentiteit;


/**
 * Reden verkrijging NL nationaliteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRedenVerkrijgingNLNationaliteit extends AbstractStatischObjectType {

   // Groepen
   protected RedenVerkrijgingNLNationaliteitIdentiteit identiteit = new RedenVerkrijgingNLNationaliteitIdentiteit();



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_RdnVerkNLNation", sequenceName = "Kern.seq_RdnVerkNLNation")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_RdnVerkNLNation")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Integer id) {
        if (identiteit == null) {
            identiteit = new RedenVerkrijgingNLNationaliteitIdentiteit();
        }
        identiteit.setID(new RedenVerkrijgingNLNationaliteitID());
        identiteit.getID().setWaarde(id);
   }

   public RedenVerkrijgingNLNationaliteitIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final RedenVerkrijgingNLNationaliteitIdentiteit identiteit) {
      this.identiteit = identiteit;
   }



   // Getters/Setters Sets


}
