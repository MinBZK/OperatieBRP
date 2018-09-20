/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3CategorieWaardeFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3Format;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3VerwijzingFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3VerwijzingParser;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.serialize.PersoonslijstDecoder;
import nl.bzk.migratiebrp.conversie.model.serialize.PersoonslijstEncoder;

/**
 * Iv01.
 */
public final class Iv01Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.HERHALING);

    private static final Lo3VerwijzingParser VERWIJZING_PARSER = new Lo3VerwijzingParser();

    // Categorie 21: Verwijzing
    private transient Lo3VerwijzingInhoud verwijzing;
    private Lo3Datum ingangsdatumGeldigheid = Lo3Datum.NULL_DATUM;

    /**
     * Constructor.
     */
    public Iv01Bericht() {
        super(HEADER, "Iv01", null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3Bericht#getGerelateerdeAnummers()
     */
    @Override
    protected List<String> getGerelateerdeAnummers() {
        if (verwijzing == null || verwijzing.getANummer() == null || verwijzing.getANummer().getWaarde() == null) {
            return null;
        }

        return Arrays.asList(verwijzing.getANummer().getWaarde());
    }

    /* ************************************************************************************************************* */

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    protected void parseInhoud(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        final Lo3Stapel<Lo3VerwijzingInhoud> stapel;
        try {
            stapel = VERWIJZING_PARSER.parse(categorieen);
        } catch (final Exception e /* Catch all, anders klapt de ESB er lelijk uit */) {
            throw new BerichtInhoudException("Fout bij parsen lo3 verwijzing.", e);
        }

        if (stapel == null || stapel.isEmpty()) {
            throw new BerichtInhoudException("Geen verwijzing gegevens aanwezig.");
        }
        if (stapel.size() > 1) {
            throw new BerichtInhoudException("Alleen actuele gegevens verwacht.");
        }

        final Lo3Categorie<Lo3VerwijzingInhoud> categorie = stapel.get(0);

        verwijzing = categorie.getInhoud();
        ingangsdatumGeldigheid = categorie.getHistorie().getIngangsdatumGeldigheid();
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        final Lo3CategorieWaardeFormatter result = new Lo3CategorieWaardeFormatter();
        result.categorie(Lo3CategorieEnum.CATEGORIE_21);
        final Lo3VerwijzingFormatter formatter = new Lo3VerwijzingFormatter();
        formatter.format(verwijzing, result);
        result.element(Lo3ElementEnum.ELEMENT_8510, Lo3Format.format(ingangsdatumGeldigheid));

        return result.getList();
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van verwijzing.
     *
     * @return verwijzing
     */
    public Lo3VerwijzingInhoud getVerwijzing() {
        return verwijzing;
    }

    /**
     * Zet de waarde van verwijzing.
     *
     * @param verwijzing
     *            verwijzing
     */
    public void setVerwijzing(final Lo3VerwijzingInhoud verwijzing) {
        this.verwijzing = verwijzing;
    }

    /**
     * Geef de waarde van ingangsdatum geldigheid.
     *
     * @return ingangsdatum geldigheid
     */
    public Lo3Datum getIngangsdatumGeldigheid() {
        return ingangsdatumGeldigheid;
    }

    /**
     * Zet de waarde van ingangsdatum geldigheid.
     *
     * @param ingangsdatumGeldigheid
     *            ingangsdatum geldigheid
     */
    public void setIngangsdatumGeldigheid(final Lo3Datum ingangsdatumGeldigheid) {
        this.ingangsdatumGeldigheid = ingangsdatumGeldigheid;
    }

    /* ************************************************************************************************************* */

    private void readObject(final ObjectInputStream is) throws ClassNotFoundException, IOException {
        // always perform the default de-serialization first
        is.defaultReadObject();

        verwijzing = PersoonslijstDecoder.decode(Lo3VerwijzingInhoud.class, is);
    }

    private void writeObject(final ObjectOutputStream os) throws IOException {
        // perform the default serialization for all non-transient, non-static fields
        os.defaultWriteObject();

        PersoonslijstEncoder.encode(verwijzing, os);
    }
}
