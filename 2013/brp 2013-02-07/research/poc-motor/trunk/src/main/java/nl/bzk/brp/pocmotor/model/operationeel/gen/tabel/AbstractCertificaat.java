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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.CertificaatID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Certificaatserial;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Certificaatsubject;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PubliekeSleutel;


/**
 * Certificaat

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractCertificaat extends AbstractTabel {

   @Transient
   protected CertificaatID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Subject"))
   protected Certificaatsubject subject;

   @AttributeOverride(name = "waarde", column = @Column(name = "Serial"))
   protected Certificaatserial serial;

   @AttributeOverride(name = "waarde", column = @Column(name = "Signature"))
   protected PubliekeSleutel signature;


   @Id
   @SequenceGenerator(name = "seq_Certificaat", sequenceName = "AutAut.seq_Certificaat")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Certificaat")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new CertificaatID();
      }
      id.setWaarde(value);
   }

   public Certificaatsubject getSubject() {
      return subject;
   }

   public void setSubject(final Certificaatsubject subject) {
      this.subject = subject;
   }

   public Certificaatserial getSerial() {
      return serial;
   }

   public void setSerial(final Certificaatserial serial) {
      this.serial = serial;
   }

   public PubliekeSleutel getSignature() {
      return signature;
   }

   public void setSignature(final PubliekeSleutel signature) {
      this.signature = signature;
   }



}
