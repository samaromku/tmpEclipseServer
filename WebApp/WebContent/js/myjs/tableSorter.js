$(function() {
  // call the tablesorter plugin
  $("table").tablesorter({
    theme : 'blue',

    dateFormat : "mmddyyyy", // set the default date format

    // or to change the format for specific columns, add the dateFormat to the headers option:
    headers: {
      0: { sorter: "shortDate" } //, dateFormat will parsed as the default above
      // 1: { sorter: "shortDate", dateFormat: "ddmmyyyy" }, // set day first format; set using class names
      // 2: { sorter: "shortDate", dateFormat: "yyyymmdd" }  // set year first format; set using data attributes (jQuery data)
    }

  });
});