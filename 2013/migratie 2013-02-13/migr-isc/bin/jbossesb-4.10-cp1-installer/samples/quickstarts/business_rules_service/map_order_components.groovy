// Need to map down the orderHeader and customer beans onto the message
// to make them available to the ObjectMapper...
message.getBody().add("orderHeader", message.getBody().get().get("orderHeader"));
message.getBody().add("customer", message.getBody().get().get("customer"));