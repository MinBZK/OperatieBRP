/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.enums;

/**
 * Toestand
  */
public enum Toestand {

   /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
   DUMMY(null, null),
   CONCEPT("Concept", "Initiele versie, nog niet actief."),
   TE_FIATTEREN("Te fiatteren", "Ingevoerd, maar nog niet door Stelselbeheerder (dan wel tweede paar ogen bij Stelselbeheerder) gefiatteerd."),
   TE_VERBETEREN("Te verbeteren", "Bekeken, maar niet correct bevonden."),
   DEFINITIEF("Definitief", "Definitief, bruikbaar voor toegangsbewaking.");


   private final String naam;

   private final String omschrijving;



   private Toestand(final String naam, final String omschrijving) {
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
