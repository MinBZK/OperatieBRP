/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.enums;

/**
 * Soort actie
  */
public enum SoortActie {

    /**
     * Dummy value. Echte values beginnen in de database bij 1 ipv 0
     */
    DUMMY(null, null),
    DUMMY_WAARDE("Dummy waarde", "**OEPS**"),
    VERHUIZING("Verhuizing", "Een verhuizing"),
    AANGIFTE_GEBOORTE("Inschrijving door Aangifte geboorte", "Inschrijving door Aangifte geboorte"),
    ERKENNING_GEBOORTE("Erkenning", "Erkenning geboorte kind");


   private final String naam;

   private final String categorieSoortActie;



   private SoortActie(final String naam, final String categorieSoortActie) {
      this.naam = naam;
      this.categorieSoortActie = categorieSoortActie;
   }


   public String getNaam() {
      return naam;
   }

   public String getCategorieSoortActie() {
      return categorieSoortActie;
   }



}
