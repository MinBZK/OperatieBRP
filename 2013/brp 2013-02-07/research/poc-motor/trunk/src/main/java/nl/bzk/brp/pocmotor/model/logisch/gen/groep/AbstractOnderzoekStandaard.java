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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.OnderzoekOmschrijving;


/**
 * Onderzoek.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractOnderzoekStandaard extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "DatBegin"))
   protected Datum datumBegin;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
   protected Datum datumEinde;

   @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
   protected OnderzoekOmschrijving omschrijving;

   @AttributeOverride(name = "waarde", column = @Column(name = "GegevensInOnderzoek"))
   protected OmschrijvingEnumeratiewaarde gegevensInOnderzoek;


   public Datum getDatumBegin() {
      return datumBegin;
   }

   public void setDatumBegin(final Datum datumBegin) {
      this.datumBegin = datumBegin;
   }

   public Datum getDatumEinde() {
      return datumEinde;
   }

   public void setDatumEinde(final Datum datumEinde) {
      this.datumEinde = datumEinde;
   }

   public OnderzoekOmschrijving getOmschrijving() {
      return omschrijving;
   }

   public void setOmschrijving(final OnderzoekOmschrijving omschrijving) {
      this.omschrijving = omschrijving;
   }

   public OmschrijvingEnumeratiewaarde getGegevensInOnderzoek() {
      return gegevensInOnderzoek;
   }

   public void setGegevensInOnderzoek(final OmschrijvingEnumeratiewaarde gegevensInOnderzoek) {
      this.gegevensInOnderzoek = gegevensInOnderzoek;
   }



}
