# Jython script: link.py
chain = payloadProxy.getPayload(message) + "~(jython)"
logger.info(config.getAttribute("action") + ": " + chain)
payloadProxy.setPayload(message, chain)
