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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Aktenummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DocumentID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DocumentIdentificatie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DocumentOmschrijving;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.SoortDocument;


/**
 * Document

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractDocument extends AbstractTabel {

   @Transient
   protected DocumentID id;

   @ManyToOne
   @JoinColumn(name = "Srt")
   protected SoortDocument soort;

   @AttributeOverride(name = "waarde", column = @Column(name = "Ident"))
   protected DocumentIdentificatie identificatie;

   @AttributeOverride(name = "waarde", column = @Column(name = "Aktenr"))
   protected Aktenummer aktenummer;

   @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
   protected DocumentOmschrijving omschrijving;

   @ManyToOne
   @JoinColumn(name = "Partij")
   protected Partij partij;

   @AttributeOverride(name = "waarde", column = @Column(name = "DocStatusHis"))
   protected StatusHistorie documentStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Doc", sequenceName = "Kern.seq_Doc")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Doc")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new DocumentID();
      }
      id.setWaarde(value);
   }

   public SoortDocument getSoort() {
      return soort;
   }

   public void setSoort(final SoortDocument soort) {
      this.soort = soort;
   }

   public DocumentIdentificatie getIdentificatie() {
      return identificatie;
   }

   public void setIdentificatie(final DocumentIdentificatie identificatie) {
      this.identificatie = identificatie;
   }

   public Aktenummer getAktenummer() {
      return aktenummer;
   }

   public void setAktenummer(final Aktenummer aktenummer) {
      this.aktenummer = aktenummer;
   }

   public DocumentOmschrijving getOmschrijving() {
      return omschrijving;
   }

   public void setOmschrijving(final DocumentOmschrijving omschrijving) {
      this.omschrijving = omschrijving;
   }

   public Partij getPartij() {
      return partij;
   }

   public void setPartij(final Partij partij) {
      this.partij = partij;
   }

   public StatusHistorie getDocumentStatusHis() {
      return documentStatusHis;
   }

   public void setDocumentStatusHis(final StatusHistorie documentStatusHis) {
      this.documentStatusHis = documentStatusHis;
   }



}
