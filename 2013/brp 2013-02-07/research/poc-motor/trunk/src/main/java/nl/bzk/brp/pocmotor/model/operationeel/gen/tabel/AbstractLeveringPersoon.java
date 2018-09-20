/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.gen.tabel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LeveringPersoonID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Levering;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;


/**
 * Levering \ Persoon

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractLeveringPersoon extends AbstractTabel {

   @Transient
   protected LeveringPersoonID id;

   @ManyToOne
   @JoinColumn(name = "Lev")
   protected Levering levering;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;


   @Id
   @SequenceGenerator(name = "seq_LevPers", sequenceName = "Lev.seq_LevPers")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_LevPers")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new LeveringPersoonID();
      }
      id.setWaarde(value);
   }

   public Levering getLevering() {
      return levering;
   }

   public void setLevering(final Levering levering) {
      this.levering = levering;
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }



}
