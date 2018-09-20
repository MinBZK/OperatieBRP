/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.enums;

/**
 * Soort indicatie
  */
public enum SoortIndicatie {

   /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
   DUMMY(null, null),
   DERDE_HEEFT_GEZAG_("Derde heeft gezag?", "**OEPS**"),
   ONDER_CURATELE_("Onder curatele?", "**OEPS**"),
   VERSTREKKINGSBEPERKING_("Verstrekkingsbeperking?", "**OEPS**"),
   GEPRIVILEGIEERDE_("Geprivilegieerde?", "**OEPS**"),
   VASTGESTELD_NIET_NEDERLANDER_("Vastgesteld niet Nederlander?", "**OEPS**"),
   BEHANDELD_ALS_NEDERLANDER_("Behandeld als Nederlander?", "**OEPS**"),
   BELEMMERING_VERSTREKKING_REISDO("Belemmering verstrekking reisdocument?", "**OEPS**"),
   BEZIT_BUITENLANDS_REISDOCUMENT_("Bezit buitenlands reisdocument?", "**OEPS**"),
   STATENLOOS_("Statenloos?", "**OEPS**");


   private final String naam;

   private final String indicatieMaterieleHistorieVanToepassing;



   private SoortIndicatie(final String naam, final String indicatieMaterieleHistorieVanToepassing) {
      this.naam = naam;
      this.indicatieMaterieleHistorieVanToepassing = indicatieMaterieleHistorieVanToepassing;
   }


   public String getNaam() {
      return naam;
   }

   public String getIndicatieMaterieleHistorieVanToepassing() {
      return indicatieMaterieleHistorieVanToepassing;
   }



}
