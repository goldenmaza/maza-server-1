/**
* RequestCode-klassen används för att fungera som en mall, så att en programmerare skall kunna lägga till
* eller ta bort viktiga element för att få programmet att köras på ett annat sätt än vad som var tänkt.
* Utan att behöva ändra alls för mycket i huvudkoden, som finns under TCP_Server-klassen.
*
* @author (Richard M. Hellstrand)
* @version (8-Juni-2010)
*/
package public_html;
/**
* Själva RequestCode-klassen; innehåller sex privata medlemsvariabler, två konstruktorer,
* en subklass och tio publika resp. två privata metoder (ej inkl. subklassens metod(er))
* för ändringar, returneringar och utskrifter av dessa privata medlemsvariabler.
*/
public class RequestCode
{
    /**
    * Privata medlemsvariabler, vilket endast kan nås utav de publika/privata metoderna.
    * mRequest - vilket är en privat medlemsvariabel av typen RequestTemplates.
    * mStatusLine - vilket är en privat medlemsvariabel av typen String.
    * mContentLength - vilket är en privat medlemsvariabel av typen String.
    * mContentType - vilket är en privat medlemsvariabel av typen String.
    * mConnection - vilket är en privat medlemsvariabel av typen String.
    * mEntityBody - vilket är en privat medlemsvariabel av typen String.
    */
    private RequestTemplates mRequest;
    private String mStatusLine;
    private String mContentLength;
    private String mContentType;
    private String mConnection;
    private String mEntityBody;
    /**
    * En publik default konstruktor, vilket anropar i sig sin broder konstruktor och tilldelar den en String, nämligen '200'.
    */
    public RequestCode()
    {
        this( "200" );
    }
    /**
    * En publik konstruktor, vilket anropar en metod för skapandet och initieringen av ett RequestTemplates-objekt för den privata medlemsvariabeln (mRequest).
    * @param pMode En parameter av typen String som kommer att tilldela setTemplate-metoden en String för skapandet av ett RequestTemplates-objekt.
    */
    public RequestCode( String pMode )
    {
        setTemplate( pMode );
    }
    /**
    * En privat metod, vilket anropar setRequest-metoden för tilldelningen av ett RequestTemplates-objekt.
    * @param pMode En parameter av typen String som kommer att tilldela setRequest-metoden ett RequestTemplates-objekt med en String parameter (pMode).
    */
    private void setTemplate( String pMode )
    {
        setRequest( new RequestTemplates( pMode ) );
    }
    /**
    * En privat metod, vilket tilldelar den privata medlemsvariabeln (mRequest) en RequestTemplates ifrån parametern (pRequest).
    * Denna metod används för att tilldela den privata medlemsvariabeln (mRequest) en RequestTemplates-objekt.
    * @param pRequest En parameter av typen RequestTemplates som kommer att tilldela den privata medlemsvariabeln (mRequest) ett objekt.
    */
    private void setRequest( RequestTemplates pRequest )
    {
        mRequest = pRequest;
    }
    /**
    * En privat subklass, vilket initierar dom privata medlemsvariabelerna (mStatusLine, mContentLength,
    * mContentType, mConnection och mEntityBody) med olika String. En privat konstruktor och en privat metod.
    */
    private class RequestTemplates
    {
        /**
        * En privat konstruktor, vilket anropar setStatusLine, med olika String, beroende på vad pMode är lika med (String).
        * Efter detta anropas setALL-metoden som kommer att tilldela dom andra privata medlemsvariablerna deras String värden.
        */
        private RequestTemplates( String pMode )
        {
            if( pMode.equals( "200" ) )
                setStatusLine( 0, "HTTP/1.1 200 OK" );
            else if( pMode.equals( "403" ) )
                setStatusLine( 0, "HTTP/1.1 403 Forbidden" );
            else if( pMode.equals( "404" ) )
                setStatusLine( 0, "HTTP/1.1 404 Not Found" );
            else if( pMode.equals( "405" ) )
                setStatusLine( 0, "HTTP/1.1 405 Method Not Allowed" );
            else if( pMode.equals( "415" ) )
                setStatusLine( 0, "HTTP/1.1 415 Unsupported Media Type" );
            
            setALL();
        }
        /**
        * En privat metod, vilket anropar fyra metoder för att tilldela början på den String som
        * kommer att skrivas ut, både i kompilatorn och för olika Stream-objekt.
        */
        private void setALL()
        {
            setContentLength( 0, "Content-Length: " );
            setContentType( 0, "Content-Type: " );
            setConnection( 0, "Connection: " );
            setEntityBody( 0, null );
        }
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mStatusLine) en String ifrån parametern (pStatusLine). Denna metod
    * använder desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pStatusLine En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mStatusLine) en String.
    */
    public void setStatusLine( int pValue, String pStatusLine )
    {
        if( pValue == 0 )
            mStatusLine = pStatusLine;
        else
            mStatusLine += pStatusLine;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mStatusLine).
    * @return mStatusLine, Vilket returnerar en String, alltså innehållet under den privata medlemsvariabeln (mStatusLine).
    */
    public String getStatusLine()
    {
        return mStatusLine;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mContentLength) en String ifrån parametern (pContentLength). Denna metod
    * använder desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pContentLength En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mContentLength) en String.
    */
    public void setContentLength( int pValue, String pContentLength )
    {
        if( pValue == 0 )
            mContentLength = pContentLength;
        else
            mContentLength += pContentLength;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mContentLength).
    * @return mContentLength, Vilket returnerar en String, alltså innehållet under den privata medlemsvariabeln (mContentLength).
    */
    public String getContentLength()
    {
        return mContentLength;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mContentType) en String ifrån parametern (pContentType). Denna metod
    * använder desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pContentType En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mContentType) en String.
    */
    public void setContentType( int pValue, String pContentType )
    {
        if( pValue == 0 )
            mContentType = pContentType;
        else
            mContentType += pContentType;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mContentType).
    * @return mContentType, Vilket returnerar en String, alltså innehållet under den privata medlemsvariabeln (mContentType).
    */
    public String getContentType()
    {
        return mContentType;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mConnection) en String ifrån parametern (pConnection). Denna metod
    * använder desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pConnection En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mConnection) en String.
    */
    public void setConnection( int pValue, String pConnection )
    {
        if( pValue == 0 )
            mConnection = pConnection;
        else
            mConnection += pConnection;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mConnection).
    * @return mConnection, Vilket returnerar en String, alltså innehållet under den privata medlemsvariabeln (mConnection).
    */
    public String getConnection()
    {
        return mConnection;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    public void setEntityBody( int pValue, String pEntityBody )
    {
        if( pValue == 0 )
            mEntityBody = pEntityBody;
        else
            mEntityBody += pEntityBody;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mEntityBody).
    * @return mEntityBody, Vilket returnerar en String, alltså innehållet under den privata medlemsvariabeln (mEntityBody).
    */
    public String getEntityBody()
    {
        return mEntityBody;
    }
}