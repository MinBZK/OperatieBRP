<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- Groepen zijn alphabetisch gesorteerd zodat de lijst gemakkelijk vergeleken
     |   kan worden met de classes.
     +-->

	<!-- ToegangLeveringsautorisatie -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpToegangLeveringsautorisatieInhoud']"
                  mode="header">
        <th>Afleverpunt</th>
        <th>Geblokkeerd?</th>
        <th>Nadere poulatiebeperking</th>
        <th>Ingangsdatum</th>
        <th>Einddatum</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpToegangLeveringsautorisatieInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="afleverpunt"/>
        </td>
        <td>
            <xsl:value-of select="indicatieGeblokkeerd"/>
        </td>
        <td>
            <xsl:value-of select="naderePopulatiebeperking"/>
        </td>
        <td>
            <xsl:value-of select="datumIngang"/>
        </td>
        <td>
            <xsl:value-of select="datumEinde"/>
        </td>
    </xsl:template>
	

    <!-- Leveringsautorisatie -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud']"
                  mode="header">
        <th>Naam</th>
        <th>Protocolleringsniveau code</th>
        <th>Alias soort AH leveren?</th>
        <th>Geblokkeerd?</th>
        <th>Populatiebeperking volledig geconverteerd?</th>
        <th>Poulatiebeperking</th>
        <th>Ingangsdatum</th>
        <th>Einddatum</th>
        <th>Toelichting</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="naam"/>
        </td>
        <td>
            <xsl:value-of select="protocolleringsniveau"/>
        </td>
        <td>
            <xsl:value-of select="indicatieAliasSoortAdministratieHandelingLeveren"/>
        </td>
        <td>
            <xsl:value-of select="indicatieGeblokkeerd"/>
        </td>
        <td>
            <xsl:value-of select="indicatiePopulatiebeperkingVolledigGeconverteerd"/>
        </td>
        <td>
            <xsl:value-of select="populatiebeperking"/>
        </td>
        <td>
            <xsl:value-of select="datumIngang"/>
        </td>
        <td>
            <xsl:value-of select="datumEinde"/>
        </td>
        <td>
            <xsl:value-of select="toelichting"/>
        </td>
    </xsl:template>

    <!-- Dienstbundel -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud']"
                  mode="header">
        <th>Naam</th>
        <th>Geblokkeerd?</th>
        <th>Nadere populatiebeperking volledig geconverteerd?</th>
        <th>Nadere populatiebeperking</th>
        <th>Ingangsdatum</th>
        <th>Einddatum</th>
        <th>Toelichting</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="naam"/>
        </td>
        <td>
            <xsl:value-of select="geblokkeerd"/>
        </td>
        <td>
            <xsl:value-of select="naderePopulatiebeperking"/>
        </td>
        <td>
            <xsl:value-of select="indicatieNaderePopulatiebeperkingVolledigGeconverteerd"/>
        </td>
        <td>
            <xsl:value-of select="datumIngang"/>
        </td>
        <td>
            <xsl:value-of select="datumEinde"/>
        </td>
        <td>
            <xsl:value-of select="toelichting"/>
        </td>
    </xsl:template>

    <!-- Dienst -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud']"
                  mode="header">
        <th>Geblokkeerd?</th>
        <th>Ingangsdatum</th>
        <th>Einddatum</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="geblokkeerd"/>
        </td>
        <td>
            <xsl:value-of select="datumIngang"/>
        </td>
        <td>
            <xsl:value-of select="datumEinde"/>
        </td>
    </xsl:template>

    <!-- Dienst Attendering -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstAttenderingInhoud']"
                  mode="header">
        <th>Attenderingscriterium</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstAttenderingInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="attenderingscriterium"/>
        </td>
    </xsl:template>

    <!-- Dienst Selectie -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstSelectieInhoud']"
                  mode="header">
        <th>Eerste selectiedatum</th>
        <th>Selectieperiode in maanden</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstSelectieInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="eersteSelectieDatum"/>
        </td>
        <td>
            <xsl:value-of select="selectiePeriodeInMaanden"/>
        </td>
    </xsl:template>

    <!-- Partij -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud']"
                  mode="header">
        <th>Ingangsdatum</th>
        <th>Einddatum</th>
        <th>Verstrekkingsbeperking</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="datumIngang"/>
        </td>
        <td>
            <xsl:value-of select="datumEinde"/>
        </td>
        <td>
            <xsl:value-of select="indicatieVerstrekkingsbeperking"/>
        </td>
    </xsl:template>


    <!-- Dienstbundel LO3 rubriek -->
    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelLo3RubriekInhoud']"
                  mode="header">
        <th>Attribuut</th>
    </xsl:template>

    <xsl:template match="inhoud[@class='nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelLo3RubriekInhoud']"
                  mode="inhoud">
        <td>
            <xsl:value-of select="attr"/>
        </td>
	</xsl:template>

</xsl:stylesheet>
