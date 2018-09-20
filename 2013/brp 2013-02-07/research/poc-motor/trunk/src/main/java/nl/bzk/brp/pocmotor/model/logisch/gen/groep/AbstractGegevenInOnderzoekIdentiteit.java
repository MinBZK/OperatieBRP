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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Sleutelwaarde;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.DatabaseObject;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Onderzoek;


/**
 * Gegeven in onderzoek.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractGegevenInOnderzoekIdentiteit extends AbstractGroep {

   @ManyToOne
   @JoinColumn(name = "Onderzoek")
   protected Onderzoek onderzoek;

   @ManyToOne
   @JoinColumn(name = "SrtGegeven")
   protected DatabaseObject soortGegeven;

   @AttributeOverride(name = "waarde", column = @Column(name = "Ident"))
   protected Sleutelwaarde identificatie;


   public Onderzoek getOnderzoek() {
      return onderzoek;
   }

   public void setOnderzoek(final Onderzoek onderzoek) {
      this.onderzoek = onderzoek;
   }

   public DatabaseObject getSoortGegeven() {
      return soortGegeven;
   }

   public void setSoortGegeven(final DatabaseObject soortGegeven) {
      this.soortGegeven = soortGegeven;
   }

   public Sleutelwaarde getIdentificatie() {
      return identificatie;
   }

   public void setIdentificatie(final Sleutelwaarde identificatie) {
      this.identificatie = identificatie;
   }



}
