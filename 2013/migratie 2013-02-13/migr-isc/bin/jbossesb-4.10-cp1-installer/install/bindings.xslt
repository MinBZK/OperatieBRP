<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
    
    <xsl:template match="server[@name='ports-default']">
        <xsl:call-template name="process-server">
            <xsl:with-param name="serverName" select="@name"/>
            <xsl:with-param name="jndiPort" select="1099"/>
            <xsl:with-param name="webPort" select="8080"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="server[@name='ports-01']">
        <xsl:call-template name="process-server">
            <xsl:with-param name="serverName" select="@name"/>
            <xsl:with-param name="jndiPort" select="1199"/>
            <xsl:with-param name="webPort" select="8180"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="server[@name='ports-02']">
        <xsl:call-template name="process-server">
            <xsl:with-param name="serverName" select="@name"/>
            <xsl:with-param name="jndiPort" select="1299"/>
            <xsl:with-param name="webPort" select="8280"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="server[@name='ports-03']">
        <xsl:call-template name="process-server">
            <xsl:with-param name="serverName" select="@name"/>
            <xsl:with-param name="jndiPort" select="1399"/>
            <xsl:with-param name="webPort" select="8380"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="process-server">
        <xsl:param name="serverName"/>
        <xsl:param name="jndiPort"/>
        <xsl:param name="webPort"/>

        <xsl:copy xml:space="preserve"><xsl:apply-templates select="@*|node()"/>
      <xsl:comment> ************ ESB configuration for server <xsl:value-of select="$serverName"/> ************ </xsl:comment><xsl:text>

      </xsl:text>
      <xsl:comment> Mapping for jbossesb-properties.xml </xsl:comment><xsl:text>
</xsl:text>
      <service-config name="jboss.esb:service=PropertyService"
        delegateClass="org.jboss.services.binding.XSLTFileDelegate">
        <delegate-config>
          <xslt-config configName="PropertyFile"><![CDATA[
<xsl:stylesheet
      xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0'>

  <xsl:output method="xml"/>
  <xsl:param name="esbHost"/>
  <xsl:param name="port"/>

  <xsl:template match="/">
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="property[@name='org.jboss.soa.esb.jndi.server.url']">
    <xsl:element name="property">
      <xsl:attribute name="name">org.jboss.soa.esb.jndi.server.url</xsl:attribute>
      <xsl:attribute name="value"><xsl:value-of select='$esbHost'/>:<xsl:value-of select='$port'/></xsl:attribute>
    </xsl:element>
  </xsl:template>

  <xsl:template match="*|@*">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
  ]]>
          </xslt-config>
          <binding host="${{jboss.bind.address}}" port="{$jndiPort}" />
          <xslt-param name="esbHost">${jboss.esb.bind.address}</xslt-param>
        </delegate-config>
      </service-config><xsl:text>
</xsl:text>

      <xsl:comment> Mapping for esb.juddi.xml </xsl:comment><xsl:text>
</xsl:text>
      <service-config name="jboss.esb:service=JuddiRMI"
        delegateClass="org.jboss.services.binding.XSLTFileDelegate">
        <delegate-config>
          <xslt-config configName="PropertiesResourceFile"><![CDATA[
<xsl:stylesheet
      xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0'>

  <xsl:output method="xml"/>
  <xsl:param name="esbHost"/>
  <xsl:param name="port"/>
  <xsl:param name="webPort"/>

  <xsl:template match="/">
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="entry[@key='juddi.server.baseurl']">
    <entry key="juddi.server.baseurl">http://<xsl:value-of select='$esbHost'/>:<xsl:value-of select="$webPort"/></entry>
  </xsl:template>

  <xsl:template match="*|@*">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
</xsl:stylesheet>
]]>
          </xslt-config>
          <binding host="${{jboss.bind.address}}" port="{$jndiPort}" />
          <xslt-param name="esbHost">${jboss.esb.bind.address}</xslt-param>
          <xslt-param name="webPort"><xsl:value-of select="$webPort"/></xslt-param>
        </delegate-config>
      </service-config><xsl:text>
</xsl:text>
        </xsl:copy>
    </xsl:template>
    
    <xsl:template name="identity" match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
