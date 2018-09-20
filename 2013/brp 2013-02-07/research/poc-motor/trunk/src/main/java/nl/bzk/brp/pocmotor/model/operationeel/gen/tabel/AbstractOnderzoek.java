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
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.OnderzoekID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.OnderzoekOmschrijving;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;


/**
 * Onderzoek

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractOnderzoek extends AbstractTabel {

   @Transient
   protected OnderzoekID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatBegin"))
   protected Datum datumBegin;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
   protected Datum datumEinde;

   @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
   protected OnderzoekOmschrijving omschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "OnderzoekStatusHis"))
   protected StatusHistorie onderzoekStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Onderzoek", sequenceName = "Kern.seq_Onderzoek")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Onderzoek")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new OnderzoekID();
      }
      id.setWaarde(value);
   }

   public Datum getDatumBegin() {
      return datumBegin;
   }

   public void setDatumBegin(final Datum datumBegin) {
      this.datumBegin = datumBegin;
   }

   public Datum getDatumEinde() {
      return datumEinde;
   }

   public void setDatumEinde(final Datum datumEinde) {
      this.datumEinde = datumEinde;
   }

   public OnderzoekOmschrijving getOmschrijving() {
      return omschrijving;
   }

   public void setOmschrijving(final OnderzoekOmschrijving omschrijving) {
      this.omschrijving = omschrijving;
   }

   public StatusHistorie getOnderzoekStatusHis() {
      return onderzoekStatusHis;
   }

   public void setOnderzoekStatusHis(final StatusHistorie onderzoekStatusHis) {
      this.onderzoekStatusHis = onderzoekStatusHis;
   }



}
