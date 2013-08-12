<%@ page import = "  javax.servlet.*, javax.servlet.http.*, java.io.*, org.apache.lucene.util.Version, org.apache.lucene.store.FSDirectory, org.apache.lucene.analysis.*, org.apache.lucene.analysis.core.*, org.apache.lucene.analysis.standard.StandardAnalyzer, org.apache.lucene.document.*, org.apache.lucene.index.*, org.apache.lucene.search.*, org.apache.lucene.queryparser.classic.*, java.net.URLEncoder" %>

<%
/*
        Author: Andrew C. Oliver, SuperLink Software, Inc. (acoliver2@users.sourceforge.net)

        This jsp page is deliberatly written in the horrible java directly embedded 
        in the page style for an easy and concise demonstration of Lucene.
        Due note...if you write pages that look like this...sooner or later
        you'll have a maintenance nightmare.  If you use jsps...use taglibs
        and beans!  That being said, this should be acceptable for a small
        page demonstrating how one uses Lucene in a web app. 

        This is also deliberately overcommented. ;-)

*/
%>
<%!
public String escapeHTML(String s) {
  s = s.replaceAll("&", "&amp;");
  s = s.replaceAll("<", "&lt;");
  s = s.replaceAll(">", "&gt;");
  s = s.replaceAll("\"", "&quot;");
  s = s.replaceAll("'", "&apos;");
  return s;
}
%>
<%@include file="header.jsp"%>
<%
        boolean error = false;                  //used to control flow for error messages
        String indexName = indexLocation;       //local copy of the configuration variable
        IndexSearcher searcher = null;          //the searcher used to open/search the index
        TopDocs topDocs = null;
        Query query = null;                     //the Query created by the QueryParser
        int startindex = 0;                     //the first index displayed on this page
        int maxpage    = 50;                    //the maximum items displayed on this page
        String queryString = null;              //the query entered in the previous page
        String startVal    = null;              //string version of startindex
        String maxresults  = null;              //string version of maxpage
        int thispage = 0;                       //used for the for/next either maxpage or
                                                //hits.length() - startindex - whichever is
                                                //less

        try {
          IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexName)));
          searcher = new IndexSearcher(reader);      //create an indexSearcher for our page
                                                        //NOTE: this operation is slow for large
                                                        //indices (much slower than the search itself)
                                                        //so you might want to keep an IndexSearcher 
                                                        //open
                                                        
        } catch (Exception e) {                         //any error that happens is probably due
                                                        //to a permission problem or non-existant
                                                        //or otherwise corrupt index
%>
                <p>ERROR opening the Index - contact sysadmin!</p>
                <p>Error message: <%=escapeHTML(e.getMessage())%></p>   
<%                error = true;                                  //don't do anything up to the footer
        }
%>
<%
       if (error == false) {                                           //did we open the index?
                queryString = request.getParameter("query");           //get the search criteria
                startVal    = request.getParameter("startat");         //get the start index
                maxresults  = request.getParameter("maxresults");      //get max results per page
                try {
                        maxpage    = Integer.parseInt(maxresults);    //parse the max results first
                        startindex = Integer.parseInt(startVal);      //then the start index  
                } catch (Exception e) { } //we don't care if something happens we'll just start at 0
                                          //or end at 50

                

                if (queryString == null)
                        throw new ServletException("no query "+       //if you don't have a query then
                                                   "specified");      //you probably played on the 
                                                                      //query string so you get the 
                                                                      //treatment

                Analyzer analyzer = new KeywordAnalyzer();           //construct our usual analyzer
                try {
                        QueryParser qp = new QueryParser(Version.LUCENE_42, "contents", analyzer);
                        query = qp.parse(queryString); //parse the 
                } catch (ParseException e) {                          //query and construct the Query
                                                                      //object
                                                                      //if it's just "operator error"
                                                                      //send them a nice error HTML
                                                                      
%>
                        <p>Error while parsing query: <%=escapeHTML(e.getMessage())%></p>
<%
                        error = true;                                 //don't bother with the rest of
                                                                      //the page
                }
        }
%>
<%
        if (error == false && searcher != null) {                     // if we've had no errors
                                                                      // searcher != null was to handle
                                                                      // a weird compilation bug 
                thispage = maxpage;                                   // default last element to maxpage
                topDocs = searcher.search(query, 100);                        // run the query 
                if (topDocs.totalHits == 0) {                             // if we got no results tell the user
%>
                <p> I'm sorry I couldn't find what you were looking for. </p>
<%
                error = true;                                        // don't bother with the rest of the
                                                                     // page
                }
        }

        if (error == false && searcher != null) {                   
%>
                <table border=1>
                <tr>
                        <td>URI</td>
                        <td>File</td>
                        <td colspan=2>Action</td>
                </tr>
<%
                if ((startindex + maxpage) > topDocs.scoreDocs.length) {
                        thispage = topDocs.scoreDocs.length - startindex;      // set the max index to maxpage or last
                }                                                   // actual search result whichever is less

                for (int i = startindex; i < (thispage + startindex); i++) {  // for each element
%>
                <tr>
<%
                        Document doc = searcher.doc(topDocs.scoreDocs[i].doc);                    //get the next document 
                        String uri = doc.get("uri");                  //get its path field
                        String file = doc.get("file");
                        if (uri != null && uri.startsWith("../webapps/")) { // strip off ../webapps prefix if present
                                uri = uri.substring(10);
                        }
%>
                        <td><%=uri%></td>
                        <td><%=file%></td>
                        <td><a href="show-highlight-code?uri=<%=URLEncoder.encode(uri)%>&query=<%=URLEncoder.encode(queryString)%>>">show highlight snippet</a></td>
                        <td><a href="show-code?uri=<%=URLEncoder.encode(uri)%>&query=<%=URLEncoder.encode(queryString)%>>">show all</a></td>
                </tr>
<%
                }
%>
<%                if ( (startindex + maxpage) < topDocs.scoreDocs.length) {   //if there are more results...display 
                                                                   //the more link

                        String moreurl="results.jsp?query=" + 
                                       URLEncoder.encode(queryString) +  //construct the "more" link
                                       "&amp;maxresults=" + maxpage + 
                                       "&amp;startat=" + (startindex + maxpage);
%>
                <tr>
                        <td></td><td><a href="<%=moreurl%>">More Results>></a></td>
                </tr>
<%
                }
%>
                </table>

<%       }                                            //then include our footer.
%>
<%@include file="footer.jsp"%>        
