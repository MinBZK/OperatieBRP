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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_UitgesloteneID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Uitgeslotene;


/**
 * His Uitgeslotene

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_Uitgeslotene extends AbstractFormeleHisTabel {

   @Transient
   protected His_UitgesloteneID id;

   @ManyToOne
   @JoinColumn(name = "Uitgeslotene")
   protected Uitgeslotene uitgeslotene;


   @Id
   @SequenceGenerator(name = "seq_His_Uitgeslotene", sequenceName = "AutAut.seq_His_Uitgeslotene")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_Uitgeslotene")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_UitgesloteneID();
      }
      id.setWaarde(value);
   }

   public Uitgeslotene getUitgeslotene() {
      return uitgeslotene;
   }

   public void setUitgeslotene(final Uitgeslotene uitgeslotene) {
      this.uitgeslotene = uitgeslotene;
   }



}
