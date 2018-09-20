/**
 * <b>Delegating Meta Class</b>
 * Each groovy object has a metaClass that is used to manage the dynamic nature
 * of the language. This class intercepts calls to groovy objects to ensure
 * that the appropriate grooviness can be added.
 *
 * The idea is that any package.class can have a custom meta class loaded at
 * startup time by placing it into a well known package with a well known name.
 *
 * <pre>groovy.runtime.metaclass.[YOURPACKAGE].[YOURCLASS]MetaClass</pre>
 *
 * So your class Foo in package "bar" could have a custom meta class
 * FooMetaClass in package "groovy.runtime.metaclass.bar".
 *
 * @see <a href="http://groovy.codehaus.org/Using+the+Delegating+Meta+Class">Using the DelegateMetaClass</a>
 * @see <a href="http://mrhaki.blogspot.nl/2011/11/groovy-goodness-magic-package-to-add.html">MrHaki</a>
 *
 * <b>Extension Modules</b>
 *
 * @see <a href="http://docs.codehaus.org/display/GROOVY/Creating+an+extension+module">Creating an extension module</a>
 * @see <a href="http://docs.groovy-lang.org/docs/groovy-2.4.3/html/documentation/#_extension_modules">Extension modules</a>
 * @see <a href="http://blog.andresteingress.com/2012/09/07/groovy-extension-modules/">Groovy Extension modules</a>
 */
package groovy.runtime.metaclass;
