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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BijhoudingssituatieID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.CategorieSoortActie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.CategorieSoortDocument;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortActie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Bijhoudingsautorisatie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.SoortDocument;


/**
 * Bijhoudingssituatie.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBijhoudingssituatieIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected BijhoudingssituatieID iD;

   @ManyToOne
   @JoinColumn(name = "Bijhautorisatie")
   protected Bijhoudingsautorisatie bijhoudingsautorisatie;

   @Column(name = "CategorieSrtActie")
   protected CategorieSoortActie categorieSoortActie;

   @Column(name = "SrtActie")
   protected SoortActie soortActie;

   @Column(name = "CategorieSrtDoc")
   protected CategorieSoortDocument categorieSoortDocument;

   @ManyToOne
   @JoinColumn(name = "SrtDoc")
   protected SoortDocument soortDocument;


   public BijhoudingssituatieID getID() {
      return iD;
   }

   public void setID(final BijhoudingssituatieID iD) {
      this.iD = iD;
   }

   public Bijhoudingsautorisatie getBijhoudingsautorisatie() {
      return bijhoudingsautorisatie;
   }

   public void setBijhoudingsautorisatie(final Bijhoudingsautorisatie bijhoudingsautorisatie) {
      this.bijhoudingsautorisatie = bijhoudingsautorisatie;
   }

   public CategorieSoortActie getCategorieSoortActie() {
      return categorieSoortActie;
   }

   public void setCategorieSoortActie(final CategorieSoortActie categorieSoortActie) {
      this.categorieSoortActie = categorieSoortActie;
   }

   public SoortActie getSoortActie() {
      return soortActie;
   }

   public void setSoortActie(final SoortActie soortActie) {
      this.soortActie = soortActie;
   }

   public CategorieSoortDocument getCategorieSoortDocument() {
      return categorieSoortDocument;
   }

   public void setCategorieSoortDocument(final CategorieSoortDocument categorieSoortDocument) {
      this.categorieSoortDocument = categorieSoortDocument;
   }

   public SoortDocument getSoortDocument() {
      return soortDocument;
   }

   public void setSoortDocument(final SoortDocument soortDocument) {
      this.soortDocument = soortDocument;
   }



}
