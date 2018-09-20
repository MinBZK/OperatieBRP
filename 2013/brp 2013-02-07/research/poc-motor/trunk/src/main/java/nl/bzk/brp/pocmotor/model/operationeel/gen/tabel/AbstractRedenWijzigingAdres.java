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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RedenWijzigingAdresID;


/**
 * Reden wijziging adres

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRedenWijzigingAdres extends AbstractTabel {

   @Transient
   protected RedenWijzigingAdresID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
   protected RedenWijzigingAdresCode code;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected NaamEnumeratiewaarde naam;


   @Id
   @SequenceGenerator(name = "seq_RdnWijzAdres", sequenceName = "Kern.seq_RdnWijzAdres")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_RdnWijzAdres")
   @Access(AccessType.PROPERTY)
   public Short getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Short value) {
      if (id == null) {
          id = new RedenWijzigingAdresID();
      }
      id.setWaarde(value);
   }

   public RedenWijzigingAdresCode getCode() {
      return code;
   }

   public void setCode(final RedenWijzigingAdresCode code) {
      this.code = code;
   }

   public NaamEnumeratiewaarde getNaam() {
      return naam;
   }

   public void setNaam(final NaamEnumeratiewaarde naam) {
      this.naam = naam;
   }



}
