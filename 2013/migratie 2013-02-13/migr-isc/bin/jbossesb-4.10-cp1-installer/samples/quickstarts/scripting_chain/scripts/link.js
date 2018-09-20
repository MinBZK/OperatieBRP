// Rhino script: link.js
var chain = payloadProxy.getPayload(message) + "~(rhino)";
logger.info(config.getAttribute("action") + ": " + chain);
payloadProxy.setPayload(message, chain);
