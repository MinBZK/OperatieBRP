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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RedenBeeindigingRelatieCode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RedenBeeindigingRelatieID;


/**
 * Reden beeindiging relatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRedenBeeindigingRelatie extends AbstractTabel {

   @Transient
   protected RedenBeeindigingRelatieID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
   protected RedenBeeindigingRelatieCode code;

   @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
   protected OmschrijvingEnumeratiewaarde omschrijving;


   @Id
   @SequenceGenerator(name = "seq_RdnBeeindRelatie", sequenceName = "Kern.seq_RdnBeeindRelatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_RdnBeeindRelatie")
   @Access(AccessType.PROPERTY)
   public Short getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Short value) {
      if (id == null) {
          id = new RedenBeeindigingRelatieID();
      }
      id.setWaarde(value);
   }

   public RedenBeeindigingRelatieCode getCode() {
      return code;
   }

   public void setCode(final RedenBeeindigingRelatieCode code) {
      this.code = code;
   }

   public OmschrijvingEnumeratiewaarde getOmschrijving() {
      return omschrijving;
   }

   public void setOmschrijving(final OmschrijvingEnumeratiewaarde omschrijving) {
      this.omschrijving = omschrijving;
   }



}
