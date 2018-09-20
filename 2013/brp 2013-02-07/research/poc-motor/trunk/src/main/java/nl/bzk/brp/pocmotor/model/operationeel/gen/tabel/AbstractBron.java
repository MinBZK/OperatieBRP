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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BronID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.BRPActie;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Document;


/**
 * Bron

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBron extends AbstractTabel {

   @Transient
   protected BronID id;

   @ManyToOne
   @JoinColumn(name = "Actie")
   protected BRPActie actie;

   @ManyToOne
   @JoinColumn(name = "Doc")
   protected Document document;


   @Id
   @SequenceGenerator(name = "seq_Bron", sequenceName = "Kern.seq_Bron")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Bron")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new BronID();
      }
      id.setWaarde(value);
   }

   public BRPActie getActie() {
      return actie;
   }

   public void setActie(final BRPActie actie) {
      this.actie = actie;
   }

   public Document getDocument() {
      return document;
   }

   public void setDocument(final Document document) {
      this.document = document;
   }



}
