/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.objecttype;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractDynamischObjectType;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ActieID;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.BRPActieIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Bron;


/**
 * BRP Actie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBRPActie extends AbstractDynamischObjectType {

   // Groepen
   protected BRPActieIdentiteit identiteit = new BRPActieIdentiteit();



   // Sets
   @OneToMany
   @JoinColumn(name = "Actie")
   protected Set<Bron> bronnen;



   // Getters/Setters Groepen
   @Id
   @SequenceGenerator(name = "seq_Actie", sequenceName = "Kern.seq_Actie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Actie")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (identiteit != null && identiteit.getID() != null) {
         return identiteit.getID().getWaarde();
      }
      return null;
   }

   public void setId(final Long id) {
        if (identiteit == null) {
            identiteit = new BRPActieIdentiteit();
        }
        identiteit.setID(new ActieID());
        identiteit.getID().setWaarde(id);
   }

   public BRPActieIdentiteit getIdentiteit() {
      return identiteit;
   }

   public void setIdentiteit(final BRPActieIdentiteit identiteit) {
      this.identiteit = identiteit;
   }



   // Getters/Setters Sets
   public Set<Bron> getBronnen() {
      return bronnen;
   }

   public void setBronnen(final Set<Bron> bronnen) {
      this.bronnen = bronnen;
   }



}
