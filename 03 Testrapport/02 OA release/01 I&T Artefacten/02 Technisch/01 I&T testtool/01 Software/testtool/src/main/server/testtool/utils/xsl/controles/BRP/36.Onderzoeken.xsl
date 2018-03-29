<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:import href="../../import/plat.xsl"/>

	<xsl:template match="lvg_synVerwerkPersoon">
		<Resultaat>
			<Bericht>
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/onderzoeken/onderzoek" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/onderzoeken/onderzoek/onderzoek" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/onderzoeken/onderzoek/gegevensInOnderzoek" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/onderzoeken/onderzoek/gegevensInOnderzoek/gegevenInOnderzoek" />
			</Bericht>
		</Resultaat>
	</xsl:template>
</xsl:stylesheet>
