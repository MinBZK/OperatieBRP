/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.gen.tabel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PartijID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Sector;


/**
 * His Partij

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_Partij extends AbstractFormeleHisTabel {

   @Transient
   protected His_PartijID id;

   @ManyToOne
   @JoinColumn(name = "Partij")
   protected Partij partij;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanv"))
   protected Datum datumAanvang;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
   protected Datum datumEinde;

   @ManyToOne
   @JoinColumn(name = "Sector")
   protected Sector sector;


   @Id
   @SequenceGenerator(name = "seq_His_Partij", sequenceName = "Kern.seq_His_Partij")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_Partij")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PartijID();
      }
      id.setWaarde(value);
   }

   public Partij getPartij() {
      return partij;
   }

   public void setPartij(final Partij partij) {
      this.partij = partij;
   }

   public Datum getDatumAanvang() {
      return datumAanvang;
   }

   public void setDatumAanvang(final Datum datumAanvang) {
      this.datumAanvang = datumAanvang;
   }

   public Datum getDatumEinde() {
      return datumEinde;
   }

   public void setDatumEinde(final Datum datumEinde) {
      this.datumEinde = datumEinde;
   }

   public Sector getSector() {
      return sector;
   }

   public void setSector(final Sector sector) {
      this.sector = sector;
   }



}
