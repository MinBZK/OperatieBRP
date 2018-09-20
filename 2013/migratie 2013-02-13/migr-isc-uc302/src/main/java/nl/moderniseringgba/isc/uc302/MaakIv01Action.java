/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Iv01Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerwijzingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak een IV01 bericht.
 */
@Component("uc302MaakIv01Action")
public final class MaakIv01Action implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String PARAMETER_VERHUIS_BERICHT = "input";
    private static final String PARAMETER_IB01_BERICHT = "ib01Bericht";
    private static final String PARAMETER_IV01_HERHALING = "iv01Herhaling";
    private static final String PARAMETER_IV01_BERICHT = "iv01Bericht";

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        // Input
        final VerhuizingVerzoekBericht verhuisBericht =
                (VerhuizingVerzoekBericht) parameters.get(PARAMETER_VERHUIS_BERICHT);
        final Ib01Bericht ib01Bericht = (Ib01Bericht) parameters.get(PARAMETER_IB01_BERICHT);

        final Lo3VerwijzingInhoud.Builder builder = new Lo3VerwijzingInhoud.Builder();
        mapVerwijzing(verhuisBericht, ib01Bericht, builder);

        final Lo3VerwijzingInhoud inhoud = builder.build();
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(0), inhoud.getDatumInschrijving());

        final Lo3Categorie<Lo3VerwijzingInhoud> verwijzing =
                new Lo3Categorie<Lo3VerwijzingInhoud>(inhoud, null, historie, null);

        final Iv01Bericht iv01Bericht = new Iv01Bericht();
        final Object herhaling = parameters.get(PARAMETER_IV01_HERHALING);
        if (herhaling != null) {
            iv01Bericht.setHeader(Lo3HeaderVeld.HERHALING, String.valueOf(herhaling));

            // Herhaal bericht moet zelfde message id hebben
            final Lo3Bericht orgineel = (Lo3Bericht) parameters.get(PARAMETER_IV01_BERICHT);
            iv01Bericht.setMessageId(orgineel.getMessageId());
        }

        iv01Bericht.setVerwijzing(verwijzing);
        iv01Bericht.setCorrelationId(ib01Bericht.getMessageId());
        iv01Bericht.setBronGemeente(verhuisBericht.getBrpGemeente().getFormattedStringCode());
        iv01Bericht.setDoelGemeente(verhuisBericht.getLo3Gemeente().getFormattedStringCode());

        // Output
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(PARAMETER_IV01_BERICHT, iv01Bericht);
        return result;
    }

    private void mapVerwijzing(
            final VerhuizingVerzoekBericht verhuisBericht,
            final Ib01Bericht ib01Bericht,
            final Lo3VerwijzingInhoud.Builder builder) {
        final Lo3Persoonslijst lo3Pl = ib01Bericht.getLo3Persoonslijst();
        final Lo3PersoonInhoud persoon = lo3Pl.getPersoonStapel().getMeestRecenteElement().getInhoud();
        final Lo3InschrijvingInhoud inschrijving = lo3Pl.getInschrijvingStapel().getMeestRecenteElement().getInhoud();

        // 01 Identificatienummers
        builder.setANummer(persoon.getaNummer());
        builder.setBurgerservicenummer(persoon.getBurgerservicenummer());

        // 02 Naam
        builder.setVoornamen(persoon.getVoornamen());
        builder.setAdellijkeTitelPredikaatCode(persoon.getAdellijkeTitelPredikaatCode());
        builder.setVoorvoegselGeslachtsnaam(persoon.getVoorvoegselGeslachtsnaam());
        builder.setGeslachtsnaam(persoon.getGeslachtsnaam());

        // 03 Geboorte
        builder.setGeboortedatum(persoon.getGeboortedatum());
        builder.setGeboorteGemeenteCode(persoon.getGeboorteGemeenteCode());
        builder.setGeboorteLandCode(persoon.getGeboorteLandCode());

        // 09 Gemeente
        builder.setGemeenteInschrijving(new Lo3GemeenteCode(verhuisBericht.getNieuweGemeente()));
        builder.setDatumInschrijving(new Lo3Datum(Integer.valueOf(verhuisBericht.getDatumInschrijving())));

        // 11 Adres
        builder.setStraatnaam(null); // TODO: Straatnaam
        builder.setNaamOpenbareRuimte(verhuisBericht.getNaamOpenbareRuimte());
        if (verhuisBericht.getHuisnummer() != null && !"".equals(verhuisBericht.getHuisnummer())) {

            builder.setHuisnummer(new Lo3Huisnummer(Integer.valueOf(verhuisBericht.getHuisnummer())));
        }

        if (verhuisBericht.getHuisletter() != null && !"".equals(verhuisBericht.getHuisletter())) {
            builder.setHuisletter(verhuisBericht.getHuisletter().charAt(0));
        }
        builder.setHuisnummertoevoeging(verhuisBericht.getHuisnummerToevoeging());
        builder.setAanduidingHuisnummer(null); // TODO: Aanduiding huisnummer
        builder.setPostcode(verhuisBericht.getPostcode());
        builder.setWoonplaatsnaam(verhuisBericht.getWoonplaats());
        builder.setIdentificatiecodeVerblijfplaats(null); // TODO: Identificatiecode verblijfplaats
        builder.setIdentificatiecodeNummeraanduiding(verhuisBericht.getIdNummerAanduiding());

        // 12 Locatie
        builder.setLocatieBeschrijving(verhuisBericht.getLocatieOmschrijving());

        // 70
        builder.setIndicatieGeheimCode(inschrijving.getIndicatieGeheimCode());
    }
}
