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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ElementID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LangeNaamEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortElement;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Element;


/**
 * Element.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractElementIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected ElementID iD;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected LangeNaamEnumeratiewaarde naam;

   @Column(name = "Srt")
   protected SoortElement soort;

   @ManyToOne
   @JoinColumn(name = "Ouder")
   protected Element ouder;


   public ElementID getID() {
      return iD;
   }

   public void setID(final ElementID iD) {
      this.iD = iD;
   }

   public LangeNaamEnumeratiewaarde getNaam() {
      return naam;
   }

   public void setNaam(final LangeNaamEnumeratiewaarde naam) {
      this.naam = naam;
   }

   public SoortElement getSoort() {
      return soort;
   }

   public void setSoort(final SoortElement soort) {
      this.soort = soort;
   }

   public Element getOuder() {
      return ouder;
   }

   public void setOuder(final Element ouder) {
      this.ouder = ouder;
   }



}
