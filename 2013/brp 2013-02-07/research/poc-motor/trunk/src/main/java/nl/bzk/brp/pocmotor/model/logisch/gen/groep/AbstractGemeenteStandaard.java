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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Gemeentecode;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Gemeente;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Partij;


/**
 * Gemeente.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractGemeenteStandaard extends AbstractGroep {

   @ManyToOne
   @JoinColumn(name = "VoortzettendeGem")
   protected Gemeente voortzettendeGemeente;

   @AttributeOverride(name = "waarde", column = @Column(name = "Gemcode"))
   protected Gemeentecode gemeentecode;

   @ManyToOne
   @JoinColumn(name = "OnderdeelVan")
   protected Partij onderdeelVan;


   public Gemeente getVoortzettendeGemeente() {
      return voortzettendeGemeente;
   }

   public void setVoortzettendeGemeente(final Gemeente voortzettendeGemeente) {
      this.voortzettendeGemeente = voortzettendeGemeente;
   }

   public Gemeentecode getGemeentecode() {
      return gemeentecode;
   }

   public void setGemeentecode(final Gemeentecode gemeentecode) {
      this.gemeentecode = gemeentecode;
   }

   public Partij getOnderdeelVan() {
      return onderdeelVan;
   }

   public void setOnderdeelVan(final Partij onderdeelVan) {
      this.onderdeelVan = onderdeelVan;
   }



}
