/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.enums;

/**
 * Protocolleringsniveau
  */
public enum Protocolleringsniveau {

   /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
   DUMMY(null, null, null),
   GEEN_BEPERKINGEN("0", "Geen beperkingen", "Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens gelegde afnemersindicaties."),
   GEVOELIG("1", "Gevoelig", "Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens geregistreerde afnemersindicaties. Deze informatie is beperkt tot daartoe expliciet geautoriseerde gemeenteambtenaren."),
   GEHEIM("2", "Geheim", "Aan een burger wordt GEEN inzage verstrekt over de in kader van deze Autorisatie geleverde gegevens. Toegang tot deze gegevens is beperkt tot gemeenteambtenaren die daartoe expliciet zijn gautoriseerd.");


   private final String code;

   private final String naam;

   private final String omschrijving;



   private Protocolleringsniveau(final String code, final String naam, final String omschrijving) {
      this.code = code;
      this.naam = naam;
      this.omschrijving = omschrijving;
   }


   public String getCode() {
      return code;
   }

   public String getNaam() {
      return naam;
   }

   public String getOmschrijving() {
      return omschrijving;
   }



}
