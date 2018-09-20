/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.enums;

/**
 * Wijze gebruik geslachtsnaam
  */
public enum WijzeGebruikGeslachtsnaam {

   /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
   DUMMY(null, null, null),
   EIGEN("E", "Eigen", "Eigen geslachtsnaam"),
   PARTNER("P", "Partner", "Geslachtsnaam echtgenoot/geregistreerd partner"),
   PARTNER__EIGEN("V", "Partner, eigen", "Geslachtsnaam echtgenoot/geregistreerd partner voor eigen geslachtsnaam"),
   EIGEN__PARTNER("N", "Eigen, partner", "Geslachtsnaam echtgenoot/geregistreerd partner na eigen geslachtsnaam");


   private final String code;

   private final String naam;

   private final String omschrijving;



   private WijzeGebruikGeslachtsnaam(final String code, final String naam, final String omschrijving) {
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
