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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RegelCode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RegelID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Regelspecificatie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortRegel;


/**
 * Regel

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRegel extends AbstractTabel {

   @Transient
   protected RegelID id;

   @Column(name = "Srt")
   protected SoortRegel soort;

   @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
   protected RegelCode code;

   @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
   protected OmschrijvingEnumeratiewaarde omschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "Specificatie"))
   protected Regelspecificatie specificatie;


   @Id
   @SequenceGenerator(name = "seq_Regel", sequenceName = "BRM.seq_Regel")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Regel")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new RegelID();
      }
      id.setWaarde(value);
   }

   public SoortRegel getSoort() {
      return soort;
   }

   public void setSoort(final SoortRegel soort) {
      this.soort = soort;
   }

   public RegelCode getCode() {
      return code;
   }

   public void setCode(final RegelCode code) {
      this.code = code;
   }

   public OmschrijvingEnumeratiewaarde getOmschrijving() {
      return omschrijving;
   }

   public void setOmschrijving(final OmschrijvingEnumeratiewaarde omschrijving) {
      this.omschrijving = omschrijving;
   }

   public Regelspecificatie getSpecificatie() {
      return specificatie;
   }

   public void setSpecificatie(final Regelspecificatie specificatie) {
      this.specificatie = specificatie;
   }



}
