/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.enums;

/**
 * Beperking populatie
  */
public enum BeperkingPopulatie {

   /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
   DUMMY(null, null),
   ONTVANGER("Ontvanger", "Personen waarvan de bijhoudingsverantwoordelijkheid bij het College van B&W ligt van de (autorisatie ontvangende) gemeente."),
   VERSTREKKER("Verstrekker", "Personen waarvan de bijhoudingsverantwoordelijkheid ligt bij de Partij die de autorisatie verstrekt.");


   private final String naam;

   private final String omschrijving;



   private BeperkingPopulatie(final String naam, final String omschrijving) {
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
