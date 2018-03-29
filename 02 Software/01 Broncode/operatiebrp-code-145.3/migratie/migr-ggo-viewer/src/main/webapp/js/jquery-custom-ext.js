// Some extra or overriden jQuery methods


// Extend jQuery.fn with our new method
jQuery.extend( jQuery.fn, {
    // Name of our method & one argument (the parent selector)
    hasParent: function(p) {
        // Returns a subset of items using jQuery.filter
        return this.filter(function(){
            // Return truthy/falsey based on presence in parent
            return $(p).find(this).length;
        });
    }
});			