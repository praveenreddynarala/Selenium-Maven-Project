package ReportLogging;

public class ListToHtmlTransformer {

	/**
     * @param collection
     *            of report titles.
     * @return string containing source code for HTML5.
     */
    public String render( java.util.Collection< String > reports ) {
        // This source code is not yet tested or made bullet-proof. Only meant for demonstrating concepts.
        // Warning: This code is not thread-safe. Changes must be made before serious use.
        // Warning: This code should be modified to produce proper HTML, such as escaping certain characters.
        // This code generates minimal HTML5 as suggested here: http://www.brucelawson.co.uk/2010/a-minimal-html5-document/
        // Big tip: Note that HTML allows the use of apostrophe (single-quote) in place of double-quote. Mixes better with Java source code.
        // The backslash + 'n' is an escape sequence in Java to generate a linefeed (Ascii/Unicode 10) for use here as a NewLine.
        // In real life, you woud test your rendered HTML with an HTML validator such as:
        // • http://html5.validator.nu/
        // • http://validator.w3.org/
        StringBuilder html = new StringBuilder();
        html.append( "<!doctype html>\n" );
        html.append( "<html lang='en'>\n" );

        html.append( "<head>\n" );
        html.append( "<meta charset='utf-8'>\n" );
        html.append( "<title>Report of Reports</title>\n" );
        html.append( "</head>\n\n" );

        html.append( "<body>\n" );
        html.append( "<h1>List of Reports</h1>\n" );
        // Make a list in HTML
        html.append( "<ul>\n" );
        // Loop the list of reports passed as argument.
        for ( String report : reports ) {
            html.append( "<li>" + report + "</li>\n" );
        }
        html.append( "</ul>\n" );
        html.append( "</body>\n\n" );

        html.append( "</html>" );

        return html.toString();
    }

}
