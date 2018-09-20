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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PredikaatCode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PredikaatID;


/**
 * Predikaat

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPredikaat extends AbstractTabel {

   @Transient
   protected PredikaatID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
   protected PredikaatCode code;

   @AttributeOverride(name = "waarde", column = @Column(name = "NaamMannelijk"))
   protected NaamEnumeratiewaarde naamMannelijk;

   @AttributeOverride(name = "waarde", column = @Column(name = "NaamVrouwelijk"))
   protected NaamEnumeratiewaarde naamVrouwelijk;


   @Id
   @SequenceGenerator(name = "seq_Predikaat", sequenceName = "Kern.seq_Predikaat")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Predikaat")
   @Access(AccessType.PROPERTY)
   public Short getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Short value) {
      if (id == null) {
          id = new PredikaatID();
      }
      id.setWaarde(value);
   }

   public PredikaatCode getCode() {
      return code;
   }

   public void setCode(final PredikaatCode code) {
      this.code = code;
   }

   public NaamEnumeratiewaarde getNaamMannelijk() {
      return naamMannelijk;
   }

   public void setNaamMannelijk(final NaamEnumeratiewaarde naamMannelijk) {
      this.naamMannelijk = naamMannelijk;
   }

   public NaamEnumeratiewaarde getNaamVrouwelijk() {
      return naamVrouwelijk;
   }

   public void setNaamVrouwelijk(final NaamEnumeratiewaarde naamVrouwelijk) {
      this.naamVrouwelijk = naamVrouwelijk;
   }



}
