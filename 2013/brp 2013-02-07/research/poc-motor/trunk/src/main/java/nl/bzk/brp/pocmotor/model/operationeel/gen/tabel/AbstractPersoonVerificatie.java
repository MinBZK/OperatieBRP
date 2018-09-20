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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.VerificatieID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.SoortVerificatie;


/**
 * Persoon \ Verificatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonVerificatie extends AbstractTabel {

   @Transient
   protected VerificatieID id;

   @ManyToOne
   @JoinColumn(name = "Geverifieerde")
   protected Persoon geverifieerde;

   @ManyToOne
   @JoinColumn(name = "Srt")
   protected SoortVerificatie soort;

   @AttributeOverride(name = "waarde", column = @Column(name = "Dat"))
   protected Datum datum;

   @AttributeOverride(name = "waarde", column = @Column(name = "PersVerificatieStatusHis"))
   protected StatusHistorie persoonVerificatieStatusHis;


   @Id
   @SequenceGenerator(name = "seq_PersVerificatie", sequenceName = "Kern.seq_PersVerificatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersVerificatie")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new VerificatieID();
      }
      id.setWaarde(value);
   }

   public Persoon getGeverifieerde() {
      return geverifieerde;
   }

   public void setGeverifieerde(final Persoon geverifieerde) {
      this.geverifieerde = geverifieerde;
   }

   public SoortVerificatie getSoort() {
      return soort;
   }

   public void setSoort(final SoortVerificatie soort) {
      this.soort = soort;
   }

   public Datum getDatum() {
      return datum;
   }

   public void setDatum(final Datum datum) {
      this.datum = datum;
   }

   public StatusHistorie getPersoonVerificatieStatusHis() {
      return persoonVerificatieStatusHis;
   }

   public void setPersoonVerificatieStatusHis(final StatusHistorie persoonVerificatieStatusHis) {
      this.persoonVerificatieStatusHis = persoonVerificatieStatusHis;
   }



}
