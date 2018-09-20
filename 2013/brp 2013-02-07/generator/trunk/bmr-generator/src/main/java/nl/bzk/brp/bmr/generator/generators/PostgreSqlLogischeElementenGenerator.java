/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.generators;

import java.util.Date;

import nl.bzk.brp.bmr.generator.Generator;
import nl.bzk.brp.bmr.generator.utils.ArtifactBuilder;
import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.Attribuut;
import nl.bzk.brp.ecore.bmr.Domein;
import nl.bzk.brp.ecore.bmr.InSetOfModel;
import nl.bzk.brp.ecore.bmr.MetaRegister;
import nl.bzk.brp.ecore.bmr.ObjectType;
import nl.bzk.brp.ecore.bmr.Schema;

import org.springframework.stereotype.Component;


/**
 * {@link Generator} implementatie de een PostgreSQL-specifiek database schema
 * genereert.
 */
@Component
public class PostgreSqlLogischeElementenGenerator implements Generator {

    private ArtifactBuilder r;

    public static final int LOGISCHE_LAAG = 1749;

    @Override
    public void generate(final MetaRegister register, final String naam, final FileSystemAccess file) {
        Domein domein = register.getDomein(naam);
        file.generateFile(domein.getNaam().toLowerCase() + "-logischeElementGegevens.sql",
                genereerLogischeElementgegevens(domein));
    }

    /**
     * Genereer logische elementen data.
     *
     * @param domein Domein
     * @return CharSequence met de insert statements
     */
    private CharSequence genereerLogischeElementgegevens(final Domein domein) {
        r = new ArtifactBuilder();
        r.regel();
        r.regel("-- ------------------------------------------------------------------------");
        r.regel("-- Begin van insertcode---------------------------------------------------");
        r.regel("-- ------------------------------------------------------------------------");
        r.regel("-- Gegenereerd door BRP Meta Register.");
        r.regel("-- Gegenereerd op: ", new Date().toString());
        r.regel("-- ------------------------------------------------------------------------");
        r.regel("-- ------------------------------------------------------------------------");
        r.regel("-- ------------------------------------------------------------------------");
        r.regel("-- ------------------------------------------------------------------------");

        // Genereer Element data
        for (Schema schema : domein.getSchemas()) {
            if ("Kern".equals(schema.getNaam())) {
                for (ObjectType objectType : schema.getWerkVersie().getObjectTypes(LOGISCHE_LAAG, InSetOfModel.MODEL)) {
                    r.regel("INSERT INTO Kern.Element (ID, Naam, DatAanvGel, Srt) VALUES(",
                            Integer.toString(objectType.getSyncId()), ", $$", objectType.getNaam(),
                            "$$, 20120101, 1 ) ; ");

                    for (Attribuut attribuut : objectType.getAttributen(InSetOfModel.MODEL)) {
                        r.regel("INSERT INTO Kern.Element (ID, Naam, DatAanvGel, Srt, Ouder ) VALUES(",
                                Integer.toString(attribuut.getSyncId()), ", $$", attribuut.getNaam(),
                                "$$, 20120101, 2, ", Integer.toString(objectType.getSyncId()), "); ");

                    }
                }
            }
        }

        return r;
    }
}
