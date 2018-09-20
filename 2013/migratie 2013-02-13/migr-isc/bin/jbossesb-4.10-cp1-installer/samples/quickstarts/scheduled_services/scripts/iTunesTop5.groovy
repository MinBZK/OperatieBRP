println "My Groovy Script"
/* the section of script below should only be used if you are online */
/*
def url='http://ax.phobos.apple.com.edgesuite.net/WebObjects/MZStore.woa/wpa/MRSS/topalbums/limit=5/rss.xml'
def channel = new XmlParser().parse(url).channel[0]
def items = channel.item

println channel.title.text()
for ( item in items[0..4] )
{
	println item.title.text()
	println item.link.text()
}
*/