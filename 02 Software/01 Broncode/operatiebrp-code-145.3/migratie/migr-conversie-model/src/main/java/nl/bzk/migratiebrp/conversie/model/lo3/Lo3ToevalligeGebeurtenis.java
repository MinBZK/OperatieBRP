/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;

/**
 * Lo3 Toevallige Gebeurtenis.
 */
public final class Lo3ToevalligeGebeurtenis {

    private final Lo3GemeenteCode verzendendeGemeente;
    private final Lo3GemeenteCode ontvangendeGemeente;
    private final Lo3String nummerAkte;

    private final Lo3Stapel<Lo3PersoonInhoud> persoon;

    private final Lo3Stapel<Lo3OuderInhoud> ouder1;
    private final Lo3Stapel<Lo3OuderInhoud> ouder2;

    private final Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis;

    private final Lo3Categorie<Lo3OverlijdenInhoud> overlijden;

    /**
     * Constructor.
     * @param verzendendeGemeente verzendende gemeente
     * @param ontvangendeGemeente ontvangende gemeente
     * @param nummerAkte nummer akte
     * @param persoon persoon
     * @param ouder1 ouder 1
     * @param ouder2 ouder 2
     * @param verbintenis verbintenis
     * @param overlijden overlijden
     */
    public Lo3ToevalligeGebeurtenis(
            final Lo3GemeenteCode verzendendeGemeente,
            final Lo3GemeenteCode ontvangendeGemeente,
            final Lo3String nummerAkte,
            final Lo3Stapel<Lo3PersoonInhoud> persoon,
            final Lo3Stapel<Lo3OuderInhoud> ouder1,
            final Lo3Stapel<Lo3OuderInhoud> ouder2,
            final Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis,
            final Lo3Categorie<Lo3OverlijdenInhoud> overlijden) {
        super();
        this.verzendendeGemeente = verzendendeGemeente;
        this.ontvangendeGemeente = ontvangendeGemeente;
        this.nummerAkte = nummerAkte;
        this.persoon = persoon;
        this.ouder1 = ouder1;
        this.ouder2 = ouder2;
        this.verbintenis = verbintenis;
        this.overlijden = overlijden;
    }

    public Lo3GemeenteCode getVerzendendeGemeente() {
        return verzendendeGemeente;
    }

    public Lo3GemeenteCode getOntvangendeGemeente() {
        return ontvangendeGemeente;
    }

    public Lo3String getNummerAkte() {
        return nummerAkte;
    }

    public Lo3Stapel<Lo3PersoonInhoud> getPersoon() {
        return persoon;
    }

    public Lo3Stapel<Lo3OuderInhoud> getOuder1() {
        return ouder1;
    }

    public Lo3Stapel<Lo3OuderInhoud> getOuder2() {
        return ouder2;
    }

    public Lo3Stapel<Lo3HuwelijkOfGpInhoud> getVerbintenis() {
        return verbintenis;
    }

    public Lo3Categorie<Lo3OverlijdenInhoud> getOverlijden() {
        return overlijden;
    }

}
