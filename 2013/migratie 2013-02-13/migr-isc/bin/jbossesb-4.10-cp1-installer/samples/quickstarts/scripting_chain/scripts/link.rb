# JRuby script: link.rb
require "java"
chain = $payloadProxy.get_payload($message) + "~(jruby)"
$logger.info($config.get_attribute("action") + ": " + chain)
$payloadProxy.set_payload($message, chain)
