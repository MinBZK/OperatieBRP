/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.gen.tabel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RegelimplementatieID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBericht;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Regel;


/**
 * Regelimplementatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRegelimplementatie extends AbstractTabel {

   @Transient
   protected RegelimplementatieID id;

   @ManyToOne
   @JoinColumn(name = "Regel")
   protected Regel regel;

   @Column(name = "SrtBer")
   protected SoortBericht soortBericht;


   @Id
   @SequenceGenerator(name = "seq_Regelimplementatie", sequenceName = "BRM.seq_Regelimplementatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Regelimplementatie")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new RegelimplementatieID();
      }
      id.setWaarde(value);
   }

   public Regel getRegel() {
      return regel;
   }

   public void setRegel(final Regel regel) {
      this.regel = regel;
   }

   public SoortBericht getSoortBericht() {
      return soortBericht;
   }

   public void setSoortBericht(final SoortBericht soortBericht) {
      this.soortBericht = soortBericht;
   }



}
