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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractBestaansperiodeHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AutoriteitVanAfgifteReisdocumentCode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AutoriteitVanAfgifteReisdocumentID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.OmschrijvingEnumeratiewaarde;


/**
 * Autoriteit van afgifte reisdocument

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAutoriteitVanAfgifteReisdocument extends AbstractBestaansperiodeHisTabel {

   @Transient
   protected AutoriteitVanAfgifteReisdocumentID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
   protected AutoriteitVanAfgifteReisdocumentCode code;

   @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
   protected OmschrijvingEnumeratiewaarde omschrijving;


   @Id
   @SequenceGenerator(name = "seq_AutVanAfgifteReisdoc", sequenceName = "Kern.seq_AutVanAfgifteReisdoc")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_AutVanAfgifteReisdoc")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new AutoriteitVanAfgifteReisdocumentID();
      }
      id.setWaarde(value);
   }

   public AutoriteitVanAfgifteReisdocumentCode getCode() {
      return code;
   }

   public void setCode(final AutoriteitVanAfgifteReisdocumentCode code) {
      this.code = code;
   }

   public OmschrijvingEnumeratiewaarde getOmschrijving() {
      return omschrijving;
   }

   public void setOmschrijving(final OmschrijvingEnumeratiewaarde omschrijving) {
      this.omschrijving = omschrijving;
   }



}
