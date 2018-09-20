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
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.RedenBeeindigingRelatie;


/**
 * Relatie.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRelatieStandaard extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanv"))
   protected Datum datumAanvang;

   @ManyToOne
   @JoinColumn(name = "GemAanv")
   protected Gemeente gemeenteAanvang;

   @ManyToOne
   @JoinColumn(name = "WplAanv")
   protected Plaats woonplaatsAanvang;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLPlaatsAanv"))
   protected BuitenlandsePlaats buitenlandsePlaatsAanvang;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLRegioAanv"))
   protected BuitenlandseRegio buitenlandseRegioAanvang;

   @ManyToOne
   @JoinColumn(name = "LandAanv")
   protected Land landAanvang;

   @AttributeOverride(name = "waarde", column = @Column(name = "OmsLocAanv"))
   protected LocatieOmschrijving omschrijvingLocatieAanvang;

   @ManyToOne
   @JoinColumn(name = "RdnEinde")
   protected RedenBeeindigingRelatie redenEinde;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
   protected Datum datumEinde;

   @ManyToOne
   @JoinColumn(name = "GemEinde")
   protected Gemeente gemeenteEinde;

   @ManyToOne
   @JoinColumn(name = "WplEinde")
   protected Plaats woonplaatsEinde;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLPlaatsEinde"))
   protected BuitenlandsePlaats buitenlandsePlaatsEinde;

   @AttributeOverride(name = "waarde", column = @Column(name = "BLRegioEinde"))
   protected BuitenlandseRegio buitenlandseRegioEinde;

   @ManyToOne
   @JoinColumn(name = "LandEinde")
   protected Land landEinde;

   @AttributeOverride(name = "waarde", column = @Column(name = "OmsLocEinde"))
   protected LocatieOmschrijving omschrijvingLocatieEinde;


   public Datum getDatumAanvang() {
      return datumAanvang;
   }

   public void setDatumAanvang(final Datum datumAanvang) {
      this.datumAanvang = datumAanvang;
   }

   public Gemeente getGemeenteAanvang() {
      return gemeenteAanvang;
   }

   public void setGemeenteAanvang(final Gemeente gemeenteAanvang) {
      this.gemeenteAanvang = gemeenteAanvang;
   }

   public Plaats getWoonplaatsAanvang() {
      return woonplaatsAanvang;
   }

   public void setWoonplaatsAanvang(final Plaats woonplaatsAanvang) {
      this.woonplaatsAanvang = woonplaatsAanvang;
   }

   public BuitenlandsePlaats getBuitenlandsePlaatsAanvang() {
      return buitenlandsePlaatsAanvang;
   }

   public void setBuitenlandsePlaatsAanvang(final BuitenlandsePlaats buitenlandsePlaatsAanvang) {
      this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
   }

   public BuitenlandseRegio getBuitenlandseRegioAanvang() {
      return buitenlandseRegioAanvang;
   }

   public void setBuitenlandseRegioAanvang(final BuitenlandseRegio buitenlandseRegioAanvang) {
      this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
   }

   public Land getLandAanvang() {
      return landAanvang;
   }

   public void setLandAanvang(final Land landAanvang) {
      this.landAanvang = landAanvang;
   }

   public LocatieOmschrijving getOmschrijvingLocatieAanvang() {
      return omschrijvingLocatieAanvang;
   }

   public void setOmschrijvingLocatieAanvang(final LocatieOmschrijving omschrijvingLocatieAanvang) {
      this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
   }

   public RedenBeeindigingRelatie getRedenEinde() {
      return redenEinde;
   }

   public void setRedenEinde(final RedenBeeindigingRelatie redenEinde) {
      this.redenEinde = redenEinde;
   }

   public Datum getDatumEinde() {
      return datumEinde;
   }

   public void setDatumEinde(final Datum datumEinde) {
      this.datumEinde = datumEinde;
   }

   public Gemeente getGemeenteEinde() {
      return gemeenteEinde;
   }

   public void setGemeenteEinde(final Gemeente gemeenteEinde) {
      this.gemeenteEinde = gemeenteEinde;
   }

   public Plaats getWoonplaatsEinde() {
      return woonplaatsEinde;
   }

   public void setWoonplaatsEinde(final Plaats woonplaatsEinde) {
      this.woonplaatsEinde = woonplaatsEinde;
   }

   public BuitenlandsePlaats getBuitenlandsePlaatsEinde() {
      return buitenlandsePlaatsEinde;
   }

   public void setBuitenlandsePlaatsEinde(final BuitenlandsePlaats buitenlandsePlaatsEinde) {
      this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
   }

   public BuitenlandseRegio getBuitenlandseRegioEinde() {
      return buitenlandseRegioEinde;
   }

   public void setBuitenlandseRegioEinde(final BuitenlandseRegio buitenlandseRegioEinde) {
      this.buitenlandseRegioEinde = buitenlandseRegioEinde;
   }

   public Land getLandEinde() {
      return landEinde;
   }

   public void setLandEinde(final Land landEinde) {
      this.landEinde = landEinde;
   }

   public LocatieOmschrijving getOmschrijvingLocatieEinde() {
      return omschrijvingLocatieEinde;
   }

   public void setOmschrijvingLocatieEinde(final LocatieOmschrijving omschrijvingLocatieEinde) {
      this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
   }



}
