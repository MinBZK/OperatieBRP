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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LeveringID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortLevering;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Abonnement;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Authenticatiemiddel;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Bericht;


/**
 * Levering

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractLevering extends AbstractTabel {

   @Transient
   protected LeveringID id;

   @Column(name = "Srt")
   protected SoortLevering soort;

   @ManyToOne
   @JoinColumn(name = "Authenticatiemiddel")
   protected Authenticatiemiddel authenticatiemiddel;

   @ManyToOne
   @JoinColumn(name = "Abonnement")
   protected Abonnement abonnement;

   @AttributeOverride(name = "waarde", column = @Column(name = "TsBesch"))
   protected DatumTijd datumTijdBeschouwing;

   @AttributeOverride(name = "waarde", column = @Column(name = "TsKlaarzettenLev"))
   protected DatumTijd datumTijdKlaarzettenLevering;

   @ManyToOne
   @JoinColumn(name = "GebaseerdOp")
   protected Bericht gebaseerdOp;


   @Id
   @SequenceGenerator(name = "seq_Lev", sequenceName = "Lev.seq_Lev")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Lev")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new LeveringID();
      }
      id.setWaarde(value);
   }

   public SoortLevering getSoort() {
      return soort;
   }

   public void setSoort(final SoortLevering soort) {
      this.soort = soort;
   }

   public Authenticatiemiddel getAuthenticatiemiddel() {
      return authenticatiemiddel;
   }

   public void setAuthenticatiemiddel(final Authenticatiemiddel authenticatiemiddel) {
      this.authenticatiemiddel = authenticatiemiddel;
   }

   public Abonnement getAbonnement() {
      return abonnement;
   }

   public void setAbonnement(final Abonnement abonnement) {
      this.abonnement = abonnement;
   }

   public DatumTijd getDatumTijdBeschouwing() {
      return datumTijdBeschouwing;
   }

   public void setDatumTijdBeschouwing(final DatumTijd datumTijdBeschouwing) {
      this.datumTijdBeschouwing = datumTijdBeschouwing;
   }

   public DatumTijd getDatumTijdKlaarzettenLevering() {
      return datumTijdKlaarzettenLevering;
   }

   public void setDatumTijdKlaarzettenLevering(final DatumTijd datumTijdKlaarzettenLevering) {
      this.datumTijdKlaarzettenLevering = datumTijdKlaarzettenLevering;
   }

   public Bericht getGebaseerdOp() {
      return gebaseerdOp;
   }

   public void setGebaseerdOp(final Bericht gebaseerdOp) {
      this.gebaseerdOp = gebaseerdOp;
   }



}
