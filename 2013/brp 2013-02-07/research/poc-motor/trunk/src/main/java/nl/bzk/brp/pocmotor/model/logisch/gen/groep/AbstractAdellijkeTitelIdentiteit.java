/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.groep;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AdellijkeTitelID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamEnumeratiewaarde;


/**
 * Adellijke titel.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAdellijkeTitelIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected AdellijkeTitelID iD;

   @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
   protected AdellijkeTitelCode code;

   @AttributeOverride(name = "waarde", column = @Column(name = "NaamMannelijk"))
   protected NaamEnumeratiewaarde naamMannelijk;

   @AttributeOverride(name = "waarde", column = @Column(name = "NaamVrouwelijk"))
   protected NaamEnumeratiewaarde naamVrouwelijk;


   public AdellijkeTitelID getID() {
      return iD;
   }

   public void setID(final AdellijkeTitelID iD) {
      this.iD = iD;
   }

   public AdellijkeTitelCode getCode() {
      return code;
   }

   public void setCode(final AdellijkeTitelCode code) {
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
