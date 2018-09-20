/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.enums;

/**
 * Soort bijhouding
  */
public enum SoortBijhouding {

   /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
   DUMMY(null, null),
   BIJHOUDEN("Bijhouden", "Bijhouden vanuit de bijhoudingsverantwoordelijkheid"),
   BIJHOUDINGSVOORSTEL_DOEN("Bijhoudingsvoorstel doen", "Het mogen doen van bijhoudingsvoorstellen, overneembaar door degene die verantwoordelijk is voor de bijhouding."),
   AUTOMATISCH_FIATTEREN("Automatisch fiatteren", "Bijhoudingsvoorstel met fiattering vooraf"),
   MANDAAT("Mandaat", "Gemandateerd zijn om de bijhouding te verrichten.");


   private final String naam;

   private final String omschrijving;



   private SoortBijhouding(final String naam, final String omschrijving) {
      this.naam = naam;
      this.omschrijving = omschrijving;
   }


   public String getNaam() {
      return naam;
   }

   public String getOmschrijving() {
      return omschrijving;
   }



}
