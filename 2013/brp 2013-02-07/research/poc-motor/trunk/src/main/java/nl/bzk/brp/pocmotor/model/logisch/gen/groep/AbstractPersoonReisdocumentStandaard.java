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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LengteInCm;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ReisdocumentNummer;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.AutoriteitVanAfgifteReisdocument;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.RedenVervallenReisdocument;


/**
 * Persoon \ Reisdocument.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonReisdocumentStandaard extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "Nr"))
   protected ReisdocumentNummer nummer;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatUitgifte"))
   protected Datum datumUitgifte;

   @ManyToOne
   @JoinColumn(name = "AutVanAfgifte")
   protected AutoriteitVanAfgifteReisdocument autoriteitVanAfgifte;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatVoorzeEindeGel"))
   protected Datum datumVoorzieneEindeGeldigheid;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatInhingVermissing"))
   protected Datum datumInhoudingVermissing;

   @ManyToOne
   @JoinColumn(name = "RdnVervallen")
   protected RedenVervallenReisdocument redenVervallen;

   @AttributeOverride(name = "waarde", column = @Column(name = "LengteHouder"))
   protected LengteInCm lengteHouder;


   public ReisdocumentNummer getNummer() {
      return nummer;
   }

   public void setNummer(final ReisdocumentNummer nummer) {
      this.nummer = nummer;
   }

   public Datum getDatumUitgifte() {
      return datumUitgifte;
   }

   public void setDatumUitgifte(final Datum datumUitgifte) {
      this.datumUitgifte = datumUitgifte;
   }

   public AutoriteitVanAfgifteReisdocument getAutoriteitVanAfgifte() {
      return autoriteitVanAfgifte;
   }

   public void setAutoriteitVanAfgifte(final AutoriteitVanAfgifteReisdocument autoriteitVanAfgifte) {
      this.autoriteitVanAfgifte = autoriteitVanAfgifte;
   }

   public Datum getDatumVoorzieneEindeGeldigheid() {
      return datumVoorzieneEindeGeldigheid;
   }

   public void setDatumVoorzieneEindeGeldigheid(final Datum datumVoorzieneEindeGeldigheid) {
      this.datumVoorzieneEindeGeldigheid = datumVoorzieneEindeGeldigheid;
   }

   public Datum getDatumInhoudingVermissing() {
      return datumInhoudingVermissing;
   }

   public void setDatumInhoudingVermissing(final Datum datumInhoudingVermissing) {
      this.datumInhoudingVermissing = datumInhoudingVermissing;
   }

   public RedenVervallenReisdocument getRedenVervallen() {
      return redenVervallen;
   }

   public void setRedenVervallen(final RedenVervallenReisdocument redenVervallen) {
      this.redenVervallen = redenVervallen;
   }

   public LengteInCm getLengteHouder() {
      return lengteHouder;
   }

   public void setLengteHouder(final LengteInCm lengteHouder) {
      this.lengteHouder = lengteHouder;
   }



}
