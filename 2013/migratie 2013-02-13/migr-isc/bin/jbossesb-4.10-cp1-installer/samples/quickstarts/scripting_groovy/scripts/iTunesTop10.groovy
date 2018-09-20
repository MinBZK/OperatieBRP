// You can use the online version of this by uncommenting the URL below,
// but we are using a static version of this file because of CI errors
// during testing of this QS. 
// def url='http://ax.phobos.apple.com.edgesuite.net/WebObjects/MZStore.woa/wpa/MRSS/topalbums/limit=10/rss.xml'
def stream=Thread.currentThread().getContextClassLoader().getResourceAsStream("rss.xml")

def channel = new XmlParser().parse(stream).channel[0]
def items = channel.item

println channel.title.text()
for ( item in items[0..9] )
{
	println item.title.text()
	println item.link.text()
}