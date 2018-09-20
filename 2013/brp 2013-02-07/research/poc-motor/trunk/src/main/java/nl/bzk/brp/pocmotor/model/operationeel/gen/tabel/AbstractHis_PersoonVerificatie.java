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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonVerificatieID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonVerificatie;


/**
 * His Persoon \ Verificatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonVerificatie extends AbstractFormeleHisTabel {

   @Transient
   protected His_PersoonVerificatieID id;

   @ManyToOne
   @JoinColumn(name = "PersVerificatie")
   protected PersoonVerificatie persoonVerificatie;

   @AttributeOverride(name = "waarde", column = @Column(name = "Dat"))
   protected Datum datum;


   @Id
   @SequenceGenerator(name = "seq_His_PersVerificatie", sequenceName = "Kern.seq_His_PersVerificatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersVerificatie")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonVerificatieID();
      }
      id.setWaarde(value);
   }

   public PersoonVerificatie getPersoonVerificatie() {
      return persoonVerificatie;
   }

   public void setPersoonVerificatie(final PersoonVerificatie persoonVerificatie) {
      this.persoonVerificatie = persoonVerificatie;
   }

   public Datum getDatum() {
      return datum;
   }

   public void setDatum(final Datum datum) {
      this.datum = datum;
   }



}
