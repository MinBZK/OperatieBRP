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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonGeslachtsnaamID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonGeslachtsnaamcomponentIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonGeslachtsnaamcomponentStandaard;


/**
 * Persoon \ Geslachtsnaamcomponent

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonGeslachtsnaamcomponent extends AbstractDynamischObjectType {

   // Groepen
   protected PersoonGeslachtsnaamcomponentIdentiteit identiteit = new PersoonGeslachtsnaamcomponentIdentiteit();

   protected PersoonGeslachtsnaamcomponentStandaard standaard;



   // Sets


   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_PersGeslnaamcomp", sequenceName = "Kern.seq_PersGeslnaamcomp")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersGeslnaamcomp")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Long id) {
        if (identiteit == null) {
            identiteit = new PersoonGeslachtsnaamcomponentIdentiteit();
        }
        identiteit.setID(new PersoonGeslachtsnaamID());
        identiteit.getID().setWaarde(id);
   }

   public PersoonGeslachtsnaamcomponentIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final PersoonGeslachtsnaamcomponentIdentiteit identiteit) {
      this.identiteit = identiteit;
   }

   public PersoonGeslachtsnaamcomponentStandaard getStandaard() {
      return standaard;
   }

   public void setStandaard(final PersoonGeslachtsnaamcomponentStandaard standaard) {
      this.standaard = standaard;
   }



   // Getters/Setters Sets


}
