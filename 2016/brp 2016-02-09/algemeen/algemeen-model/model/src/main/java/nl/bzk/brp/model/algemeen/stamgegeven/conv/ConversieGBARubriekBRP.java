/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;


/**
 * Conversie tabel ten behoeve van de vertaling van een rubriek (in combinatie met categorie en groep) enerzijds, en een BRP element ('databaseobject') en
 * expressie anderzijds.
 * <p/>
 * Een element uit LO3 (Categorie.Groep.Rubriek) vertaalt zich naar ��n of meer elementen (=databaseobject) uit de BRP. Voor het ophalen van deze gegevens
 * wordt een expressie gebruikt. Het is hierbij mogelijk dat ��n LO3 element bestaat uit een combinatie van meerdere expressies; in dat geval verschillen
 * wel de bijbehorende BRP elementen (lees: databaseobjecten). Derhalve is de combinatie categorie+groep+rubriek+databaseobject uniek, terwijl
 * categorie+groep+rubriek+expressie dat niet hoeft te zijn.
 * <p/>
 * Definitie en toelichting is wat 'los' geformuleerd. Gezien de status van dit objecttype ('concept') is dit nog acceptabel.
 */
public enum ConversieGBARubriekBRP {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY(null, null, null, null, null);

    private final String         categorie;
    private final String         groep;
    private final String         rubriek;
    private final DatabaseObject databaseObject;
    //private final Expressie      expressie;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param categorie      Categorie voor ConversieGBARubriekBRP
     * @param groep          Groep voor ConversieGBARubriekBRP
     * @param rubriek        Rubriek voor ConversieGBARubriekBRP
     * @param databaseObject DatabaseObject voor ConversieGBARubriekBRP
     * @param expressie      Expressie voor ConversieGBARubriekBRP
     */
    private ConversieGBARubriekBRP(final String categorie, final String groep, final String rubriek,
        final DatabaseObject databaseObject, final String expressie)
    {
        this.categorie = categorie;
        this.groep = groep;
        this.rubriek = rubriek;
        this.databaseObject = databaseObject;
        //this.expressie = expressie;
    }

    /**
     * Retourneert Categorie van Conversie GBA rubriek BRP.
     *
     * @return Categorie.
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Retourneert Groep van Conversie GBA rubriek BRP.
     *
     * @return Groep.
     */
    public String getGroep() {
        return groep;
    }

    /**
     * Retourneert Rubriek van Conversie GBA rubriek BRP.
     *
     * @return Rubriek.
     */
    public String getRubriek() {
        return rubriek;
    }

    /**
     * Retourneert Database object van Conversie GBA rubriek BRP.
     *
     * @return Database object.
     */
    public DatabaseObject getDatabaseObject() {
        return databaseObject;
    }

    /**
     * Retourneert Expressie van Conversie GBA rubriek BRP.
     *
     * @return Expressie.
     */
//    public Expressie getExpressie() {
//        return expressie;
//    }

}
