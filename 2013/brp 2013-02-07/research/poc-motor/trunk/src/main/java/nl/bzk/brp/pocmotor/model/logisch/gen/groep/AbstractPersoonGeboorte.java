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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Gemeente;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Land;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Plaats;


/**
 * Persoon.Geboorte

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonGeboorte extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "DatGeboorte"))
   protected Datum datumGeboorte;

   @ManyToOne
   @JoinColumn(name = "GemGeboorte")
   protected Gemeente gemeenteGeboorte;

   @ManyToOne
   @JoinColumn(name = "WplGeboorte")
   protected Plaats woonplaatsGeboorte;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLGeboorteplaats"))
   protected BuitenlandsePlaats buitenlandseGeboorteplaats;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLRegioGeboorte"))
   protected BuitenlandseRegio buitenlandseRegioGeboorte;

   @ManyToOne
   @JoinColumn(name = "LandGeboorte")
   protected Land landGeboorte;

   @AttributeOverride(name = "waarde", column = @Column(name = "OmsGeboorteloc"))
   protected LocatieOmschrijving omschrijvingGeboortelocatie;


   public Datum getDatumGeboorte() {
      return datumGeboorte;
   }

   public void setDatumGeboorte(final Datum datumGeboorte) {
      this.datumGeboorte = datumGeboorte;
   }

   public Gemeente getGemeenteGeboorte() {
      return gemeenteGeboorte;
   }

   public void setGemeenteGeboorte(final Gemeente gemeenteGeboorte) {
      this.gemeenteGeboorte = gemeenteGeboorte;
   }

   public Plaats getWoonplaatsGeboorte() {
      return woonplaatsGeboorte;
   }

   public void setWoonplaatsGeboorte(final Plaats woonplaatsGeboorte) {
      this.woonplaatsGeboorte = woonplaatsGeboorte;
   }

   public BuitenlandsePlaats getBuitenlandseGeboorteplaats() {
      return buitenlandseGeboorteplaats;
   }

   public void setBuitenlandseGeboorteplaats(final BuitenlandsePlaats buitenlandseGeboorteplaats) {
      this.buitenlandseGeboorteplaats = buitenlandseGeboorteplaats;
   }

   public BuitenlandseRegio getBuitenlandseRegioGeboorte() {
      return buitenlandseRegioGeboorte;
   }

   public void setBuitenlandseRegioGeboorte(final BuitenlandseRegio buitenlandseRegioGeboorte) {
      this.buitenlandseRegioGeboorte = buitenlandseRegioGeboorte;
   }

   public Land getLandGeboorte() {
      return landGeboorte;
   }

   public void setLandGeboorte(final Land landGeboorte) {
      this.landGeboorte = landGeboorte;
   }

   public LocatieOmschrijving getOmschrijvingGeboortelocatie() {
      return omschrijvingGeboortelocatie;
   }

   public void setOmschrijvingGeboortelocatie(final LocatieOmschrijving omschrijvingGeboortelocatie) {
      this.omschrijvingGeboortelocatie = omschrijvingGeboortelocatie;
   }



}
