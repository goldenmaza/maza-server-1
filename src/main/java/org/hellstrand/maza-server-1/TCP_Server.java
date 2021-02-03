/**
* TCP_Server-klassen är själva hjärtat av servern och det är här allt händer.
* Sockets, buffrar och annat initieras, ändras och användas på olika sätt.
*
* @author (Richard M. Hellstrand)
* @version (8-Juni-2010)
*/
package public_html;
import java.io.*;
import java.net.*;
import java.util.*;
/**
* Själva TCP_Server-klassen; innehåller fjorton privata medlemsvariabler, två konstruktorer och en publik resp.
* trettioett privata metoder för ändringar, returneringar och utskrifter av dessa privata medlemsvariabler.
*/
public class TCP_Server
{
    /**
    * Privata medlemsvariabler, vilket endast kan nås utav de publika/privata metoderna.
    * mCRLF - vilket är en statisk final privat medlemsvariabel av typen String.
    * mPortNumber - vilket är en privat medlemsvariabel av typen Integer.
    * mURL - vilket är en privat medlemsvariabel av typen URL.
    * mURLConnection - vilket är en privat medlemsvariabel av typen URLConnection.
    * mHeaderCode - vilket är en privat medlemsvariabel av typen HeaderCode.
    * mRequestCode - vilket är en privat medlemsvariabel av typen RequestCode.
    * mStream - vilket är en privat medlemsvariabel av typen Stream.
    * mServerSocket - vilket är en privat medlemsvariabel av typen ServerSocket.
    * mSocket - vilket är en privat medlemsvariabel av typen Socket.
    * mToken - vilket är en privat medlemsvariabel av typen StringTokenizer.
    * mRequestLine - vilket är en privat medlemsvariabel av typen String.
    * mMethod - vilket är en privat medlemsvariabel av typen String.
    * mFilename - vilket är en privat medlemsvariabel av typen String.
    * mAddressField - vilket är en privat medlemsvariabel av typen String.
    */
    private final static String mCRLF = "\r\n";
    private int mPortNumber;
    private URL mURL;
    private URLConnection mURLConnection;
    private HeaderCode mHeaderCode;
    private RequestCode mRequestCode;
    private Stream mStream;
    private ServerSocket mServerSocket;
    private Socket mSocket;
    private StringTokenizer mToken;
    private String mRequestLine;
    private String mMethod;
    private String mFilename;
    private String mAddressField;
    /**
    * En publik konstruktor, vilket anropar sin broder konstruktor och tilldelar den en Integer via en parameter.
    */
    public TCP_Server()
    {
        this( 80 );
    }
    /**
    * En publik konstruktor, vilket anropar setPortNumber-metoden och initierar den privata medlemsvariabeln (mPortNumber).
    * @param pPortNumber En parameter av typen Integer som kommer att handhålla setPortNumber-metoden ett värde.
    */
    public TCP_Server( int pPortNumber )
    {
        setPortNumber( pPortNumber );
    }
    /**
    * En publik metod, vilket är själva hjärnan bakom servern, här händer det mesta när det gäller funktionaliteten och
    * ev. begränsningar för att inte den skall fortsätta ifall något har gått fel.
    */
    public void processRequest()
    {
        try
        {
            setHeaderCode( new HeaderCode() ); /** Skapa HeaderCode-objektet, så servern har tillgång till alla filändelser, beskrivningar och metoder för åtkomst. */
            System.out.print( "¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n" );
            setServerSocket( null );
            setServerSocket( new ServerSocket( getPortNumber() ) ); /** Tilldela ett ServerSocket-objekt med tilldelad PortNumber till den privata medlemsvariabeln (mServerSocket). */
            while( true ) /** Endlös while-loop som körs till programmet avslutas. */
            {
                setSocket( null );
                setSocket( getServerSocket().accept() ); /** Tilldela ett ServerSocket-objekt med en förbindelse med en klient till den privata medlemsvariabeln (mSocket). */
                if( getSocket() != null ) /** OM den privata medlemsvariabeln (mSocket) inte innehåller ett ServerSocket-objekt, fortsätt INTE följande kod. */
                {
                    setStream( null );
                    setStream( new Stream() ); /** Tilldela ett Stream-objekt så att servern kan spara och skicka den data som en klient efterfrågar. */
                    /** Tilldela dom privata medlemsvariablerna (mRequestCode, mRequestLine, mMethod, mFilename och mAddressField) ett null värde. */
                    setRequestCode( null );
                    setRequestLine( null );
                    setMethod( null );
                    setFilename( null );
                    setAddressField( null );
                    /**
                    * Tilldelar dom privata medlemsvariablerna (mInputStream, mOutputStream och mBufferedReader)
                    * egna objekt av dess typ och vissa har objekt tilldelade.
                    */
                    getStream().setInputStream( getSocket().getInputStream() );
                    getStream().setOutputStream( new DataOutputStream( getSocket().getOutputStream() ) );
                    getStream().setBufferedReader( new BufferedReader( new InputStreamReader( getStream().getInputStream() ) ) );
                    /**
                    * Tilldelar dom privata medlemsvariablerna (mRequestLine, mToken och mMethod) vilket kommer att användas till
                    * att få tag i adressfältet som klienten anger i sin browser, vilket kommer sedan delas upp i mindre bitar.
                    */
                    setRequestLine( getStream().getBufferedReader().readLine() );
                    setToken( new StringTokenizer( getRequestLine() ) );
                    setMethod( getToken().nextToken() );
                    /**
                    * OM en klient försöker anropa en server med en annan metod än dom som är tillåtna kommer servern att
                    * svara med detta och avsluta förbindelsen.
                    */
                    if( getHeaderCode().checkRequestType( getMethod() ) )
                    {
                        /** Skapa ett RequestCode-objekt med en specificerad kod, vilket blir då 405 (Method Not Allowed)... */
                        setRequestCode( new RequestCode( "405" ) );
                        /** Skapa ett meddelande för kompilatorn, att det inte är möjligt att fortsätta... */
                        getRequestCode().setEntityBody( 0, "Method Not Allowed: Regardless of what you are trying to access, you are trying to access\nthis server with some method that hasn't been accepted! Accepting: " + getHeaderCode().getRequestTypes() + getCRLF() );
                        /** Anropa setRequests-metoden och skapa en del speciella utskrifter för tillståndet då 405 blir sant... */
                        setRequests( false );
                        /** Återställ RequestCode-objektet så att ett nytt anrop till servern kan göras, inte precis trådat men det fungerar för en klient åt gången... */
                        setRequestCode( null );
                    }
                    else /** ANNARS är det okej att fortsätta med begäran ifrån klienten. */
                    {
                        /** Tilldela den privata medlemsvariabeln (mFilename) en String som bör symbolisera ett filnamn. */
                        setFilename( getToken().nextToken() );
                        /**
                        * Tilldela den privata medlemsvariabeln (mAddressField) en String som bör symbolisera vad
                        * klienten angav i adressfältet i sin browser.
                        */
                        setAddressField( "http:/" + getSocket().getInetAddress() + getFilename() );
                        if( getFilename().startsWith( "/" ) ) /** Editera filnamnet om den börjar med ett slash. */
                            setFilename( getFilename().substring( 1, getFilename().length() ) );
                        /** Tilldela den privata medlemsvariabeln (mFile) en allokering av en File som i sig får en String som parameter, genom Stream-objektet. */
                        getStream().setFile( new File( getFilename() ) );
                        try /** Försök att tilldela dom privata medlemsvariablerna (mURL och mURLConnection) en allokering av en URL resp. URLConnection. */
                        {
                            /** Tilldela den privata medlemsvariabeln (mURL) en allokering av en URL som i sig får en String som parameter. */
                            setURL( new URL( getAddressField() ) );
                            setURLConnection( getURL().openConnection() );
                            getURLConnection().setRequestProperty( "Content-Type", getHeaderCode().getExtension( getFilename() ) );
                            getURLConnection().setRequestProperty( "Connection", "Close" );
                        }
                        catch( MalformedURLException pURLException ) /** Ifall det inte är möjligt så kasta denna exception och avsluta programmet. */
                        {
                            System.err.println( pURLException );
                        }
                        /**
                        * OM du vill lägga till en egen RequestCode så är det här dom detta skall göras,
                        * alltså lägg till så många else-if-satser som behövs för att utöka funktionaliteten.
                        */
                        if( getStream().getFile().isDirectory() ) /** OM det är en mapp man försöker begära så händer detta. */
                        {
                            /** Skapa ett RequestCode-objekt med en specificerad kod, vilket blir då 403 (Forbidden)... */
                            setRequestCode( new RequestCode( "403" ) );
                            /** Skapa ett meddelande för kompilatorn, att det inte är möjligt att begära en mapp... */
                            getRequestCode().setEntityBody( 0, getAddressField() + " <=> Forbidden: You might have tried to enter a folder\nlocated on this server, which is forbidden!" + getCRLF() );
                            /** Anropa setRequests-metoden och skapa en del speciella utskrifter för tillståndet då 403 blir sant... */
                            setRequests( true );
                            /** Återställ RequestCode-objektet så att ett nytt anrop till servern kan göras, inte precis trådat men det fungerar för en klient åt gången... */
                            setRequestCode( null );
                        }
                        else if( getHeaderCode().getExtension( getFilename() ).equals( "415" ) ) /** Försöker man begära en fil med en ändelse som inte finns så händer detta. */
                        {
                            /** Skapa ett RequestCode-objekt med en specificerad kod, vilket blir då 415 (Unsupported Media Type)... */
                            setRequestCode( new RequestCode( "415" ) );
                            /** Skapa ett meddelande för kompilatorn, att filen man begärde har fel filändelse och omöjligt att begära... */
                            getRequestCode().setEntityBody( 0, getAddressField() + " <=> Unsupported Type: You have tried to access a file with the extension:\n'" + getFilename() + "' which isn't allowed to access on this server, even if file might be present!" + getCRLF() );
                            /** Anropa setRequests-metoden och skapa en del speciella utskrifter för tillståndet då 415 blir sant... */
                            setRequests( true );
                            /** Återställ RequestCode-objektet så att ett nytt anrop till servern kan göras, inte precis trådat men det fungerar för en klient åt gången... */
                            setRequestCode( null );
                        }
                        else /** I ANNAT fall så händer detta. */
                        {
                            try /** Försök att tilldela den privata medlemsvariabeln (mFileStream) en allokering av en FileInputStream som i sig får en String som parameter, genom Stream-objektet. */
                            {
                                /** Hämta fil om möjligt... */
                                getStream().setFileStream( new FileInputStream( getFilename() ) );
                                /** Skapa ett default RequestCode-objekt, vilket blir då 200 (OK)... */
                                setRequestCode( new RequestCode() );
                                /** Lägg till ett slut på StatusLine headern... */
                                getRequestCode().setStatusLine( 1, getCRLF() );
                                /** Skapa ett meddelande för kompilatorn, att allt gick som det ska... */
                                getRequestCode().setEntityBody( 0, getAddressField() + " <=> OK: File was found and sent back with the response!" + getCRLF() );
                                /** Sätt ContentLength, genom att läsa storleken av filen... */
                                getRequestCode().setContentLength( 1, getStream().getFileStream().available() + getCRLF() );
                                /** Sätt ContentType, genom att hämta rätt Request rad ifrån URLConnection-objektet... */
                                getRequestCode().setContentType( 1, getURLConnection().getRequestProperty( "Content-Type" ) + getCRLF() );
                                /** Sätt Connection, genom att hämta rätt Request rad ifrån URLConnection-objektet... */
                                getRequestCode().setConnection( 1, getURLConnection().getRequestProperty( "Connection" ) + getCRLF() );
                                /** Skriv sedan ut resultatet för kompilatorn... */
                                printRequests( true );
                                /** Skicka sedan alla headerfält genom OutputStream och ge filen till klienten som anropade servern... */
                                writeBytesToClient( true );
                                /** Återställ RequestCode-objektet så att ett nytt anrop till servern kan göras, inte precis trådat men det fungerar för en klient åt gången... */
                                setRequestCode( null );
                            }
                            catch( FileNotFoundException pFileException ) /** Ifall filen inte finns på servern, kasta då en exception och detta händer istället. */
                            {
                                /** Skapa utskrift om exception, i detta fall att filen inte fanns på servern... */
                                System.err.println( pFileException );
                                /** Skapa ett RequestCode-objekt med en specificerad kod, vilket blir då 404 (Not Found)... */
                                setRequestCode( new RequestCode( "404" ) );
                                /** Lägg till ett slut på StatusLine headern... */
                                getRequestCode().setStatusLine( 1, getCRLF() );
                                /** Skapa ett meddelande för kompilatorn, att mappen eller filen inte finns på just denna server... */
                                getRequestCode().setEntityBody( 0, getAddressField() + " <=> Not Found: No file or folder was found, with that name, on this server!" + getCRLF() );
                                /** Sätt ContentLength, genom att läsa av storleken av EntityBody, eftersom ingen fil fanns så tar den detta värde istället (fel meddelandet)... */
                                getRequestCode().setContentLength( 1, getRequestCode().getEntityBody().length() + getCRLF() );
                                /** Sätt ContentType, genom att hämta rätt Request rad ifrån URLConnection-objektet... */
                                getRequestCode().setContentType( 1, getURLConnection().getRequestProperty( "Content-Type" ) + getCRLF() );
                                /** Sätt Connection, genom att hämta rätt Request rad ifrån URLConnection-objektet... */
                                getRequestCode().setConnection( 1, getURLConnection().getRequestProperty( "Connection" ) + getCRLF() );
                                /** Skriv sedan ut resultatet för kompilatorn... */
                                printRequests( true );
                                /** Skicka sedan alla headerfält genom OutputStream och ge filen till klienten som anropade servern... */
                                writeBytesToClient( false );
                                /** Återställ RequestCode-objektet så att ett nytt anrop till servern kan göras, inte precis trådat men det fungerar för en klient åt gången... */
                                setRequestCode( null );
                            }
                        }
                    }
                    /** Stäng dom olika Stream-objekten och själva Socket-objektet. */
                    getStream().getInputStream().close();
                    getStream().getOutputStream().close();
                    getStream().getBufferedReader().close();
                    getSocket().close();
                }
            }
        }
        catch( IOException pIOException ) /** Denna exception kommer att kastas när och ifall något händer med Stream-objektet. */
        {
            System.err.println( pIOException );
        }
    }
    /**
    * En privat metod, vilket tilldelar dom publika medlemsvariablerna under RequestCode-klassen String värden beroende på vilken Requestkod som blir uppfylld.
    * @param pMode En parameter av typen boolean som kommer att avgöra om Connection bör tas ut ur den privata medlemsvariabeln (mURLConnection) eller menuellt angivet.
    * @exception IOException Denna exception kommer att kastas när och ifall något händer med Stream-objektet, under writeBytesToClient-metoden.
    */
    private void setRequests( boolean pMode ) throws IOException
    {
        getRequestCode().setStatusLine( 1, getCRLF() );
        getRequestCode().setContentLength( 1, getRequestCode().getEntityBody().length() + getCRLF() );
        getRequestCode().setContentType( 1, "text/custom_msg" + getCRLF() );
        if( pMode )
            getRequestCode().setConnection( 1, getURLConnection().getRequestProperty( "Connection" ) + getCRLF() );
        else
            getRequestCode().setConnection( 1, "Closed" + getCRLF() );
        printRequests( pMode );
        writeBytesToClient( false );
    }
    /**
    * En privat metod, vilket skriver ut resultatet av allt som har hänt efter servern har bestämt vilket request kod anropet gäller.
    * @param pMode En parameter av typen boolean som kommer att avgöra om Accept eller Allow bör skrivas ut i kompilator fönstret.
    */
    private void printRequests( boolean pMode )
    {
        System.out.print( getRequestCode().getStatusLine() );
        System.out.print( getRequestCode().getContentLength() );
        System.out.print( getRequestCode().getContentType() );
        System.out.print( getRequestCode().getConnection() );
        if( pMode )
            System.out.print( getHeaderCode().getAccept() );
        else
            System.out.print( getHeaderCode().getAllow() );
        System.out.print( getCRLF() );
        System.out.print( getRequestCode().getEntityBody() );
        System.out.print( "¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n" );
    }
    /**
    * En privat metod, vilket skickar alla headers genom Socket, om det är en fil som skall hämtas, i annat fall skicka mindre data igenom DataOutputStream.
    * @param pRequest200 En parameter av typen boolean som kommer att avgöra om extra data bör skickas genom DataOutputStream.
    * @exception IOException Denna exception kommer att kastas när och ifall något händer med Stream-objektet.
    */
    private void writeBytesToClient( boolean pRequest200 ) throws IOException//getStream().getOutputStream().writeBytes( getHeaderCode().getAccept() );
    {
        getStream().getOutputStream().writeBytes( getRequestCode().getStatusLine() );
        if( pRequest200 )
        {
            getStream().getOutputStream().writeBytes( getRequestCode().getContentLength() );
            getStream().getOutputStream().writeBytes( getRequestCode().getContentType() );
            getStream().getOutputStream().writeBytes( getRequestCode().getConnection() );
            getStream().getOutputStream().writeBytes( getCRLF() );
            contentBuffer( getStream().getFileStream(), getStream().getOutputStream() );
        }
        getStream().getOutputStream().writeBytes( getCRLF() );
        if( !pRequest200 )
            getStream().getOutputStream().writeBytes( getRequestCode().getEntityBody() );
    }
    /**
    * En privat statisk metod, vilket lagrar ett antal byte åt gången tills det är dags att skicka det vidare till serverns Socket.
    * @param pFileStream En parameter av typen FileInputStream som kommer att använda sig av dom lokala variablarna för att att senare skicka det vidare.
    * @param pOutputStream En parameter av typen OutputStream som kommer att skicka det data som har sparats under dom lokala variablerna till serverns Socket.
    * @exception IOException Denna exception kommer att kastas när och ifall något händer med Stream-objektet.
    */
    private static void contentBuffer( FileInputStream pFileStream, OutputStream pOutputStream ) throws IOException
    {
        byte[] lBuffer = new byte[1024];
        int lBytes = 0;
        
        while( ( lBytes = pFileStream.read( lBuffer ) ) != -1 )
            pOutputStream.write( lBuffer, 0, lBytes );
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mCRLF), en sträng som används för att symbolisera
    * slutet på ett headerfält eller skapa avstånd mellan alla headerfält och ev. entitybody.
    * @return mCRLF, Vilket returnerar en String, alltså innehållet under den privata medlemsvariabeln (mCRLF).
    */
    private String getCRLF()
    {
        return mCRLF;
    }
    /**
    * En privat metod, vilket tilldelar den privata medlemsvariabeln (mPortNumber) en String ifrån parametern (pPortNumber).
    * @param pPortNumber En parameter av typen Integer som kommer att tilldela den privata medlemsvariabeln (mPortNumber) en Integer.
    */
    private void setPortNumber( int pPortNumber )
    {
        mPortNumber = pPortNumber;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mPortNumber).
    * @return mPortNumber, Vilket returnerar en Integer, alltså innehållet under den privata medlemsvariabeln (mPortNumber).
    */
    private int getPortNumber()
    {
        return mPortNumber;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setURL( URL pURL )
    {
        mURL = pURL;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mURL).
    * @return mURL, Vilket returnerar en URL, alltså innehållet under den privata medlemsvariabeln (mURL).
    */
    private URL getURL()
    {
        return mURL;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setURLConnection( URLConnection pURLConnection )
    {
        mURLConnection = pURLConnection;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mURLConnection).
    * @return mURLConnection, Vilket returnerar en URLConnection, alltså innehållet under den privata medlemsvariabeln (mURLConnection).
    */
    private URLConnection getURLConnection()
    {
        return mURLConnection;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setHeaderCode( HeaderCode pHeaderCode )
    {
        mHeaderCode = pHeaderCode;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (HeaderCode).
    * @return HeaderCode, Vilket returnerar en HeaderCode, alltså innehållet under den privata medlemsvariabeln (HeaderCode).
    */
    private HeaderCode getHeaderCode()
    {
        return mHeaderCode;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setRequestCode( RequestCode pRequestCode )
    {
        mRequestCode = pRequestCode;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mRequestCode).
    * @return mRequestCode, Vilket returnerar en RequestCode, alltså innehållet under den privata medlemsvariabeln (mRequestCode).
    */
    private RequestCode getRequestCode()
    {
        return mRequestCode;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setStream( Stream pStream )
    {
        mStream = pStream;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mStream).
    * @return mStream, Vilket returnerar en Stream, alltså innehållet under den privata medlemsvariabeln (mStream).
    */
    private Stream getStream()
    {
        return mStream;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setServerSocket( ServerSocket pServerSocket )
    {
        mServerSocket = pServerSocket;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mServerSocket).
    * @return mServerSocket, Vilket returnerar en ServerSocket, alltså innehållet under den privata medlemsvariabeln (mServerSocket).
    */
    private ServerSocket getServerSocket()
    {
        return mServerSocket;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setSocket( Socket pSocket )
    {
        mSocket = pSocket;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mSocket).
    * @return mSocket, Vilket returnerar en Socket, alltså innehållet under den privata medlemsvariabeln (mSocket).
    */
    private Socket getSocket()
    {
        return mSocket;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setToken( StringTokenizer pToken )
    {
        mToken = pToken;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mToken).
    * @return mToken, Vilket returnerar en StringTokenizer, alltså innehållet under den privata medlemsvariabeln (mToken).
    */
    private StringTokenizer getToken()
    {
        return mToken;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setRequestLine( String pRequestLine )
    {
        mRequestLine = pRequestLine;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mRequestLine). 
    * @return mRequestLine, Vilket returnerar en String, alltså innehållet under den privata medlemsvariabeln (mRequestLine).
    */
    private String getRequestLine()
    {
        return mRequestLine;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setMethod( String pMethod )
    {
        mMethod = pMethod;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mMethod). Den anger den metod, alltså GET etc, som en klient anropar servern med.
    * @return mMethod, Vilket returnerar en String, alltså innehållet under den privata medlemsvariabeln (mMethod).
    */
    private String getMethod()
    {
        return mMethod;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setFilename( String pFilename )
    {
        mFilename = pFilename;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mFilename). Den anger den fil som användaren begär via en browser.
    * @return mFilename, Vilket returnerar en String, alltså innehållet under den privata medlemsvariabeln (mFilename).
    */
    private String getFilename()
    {
        return mFilename;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mEntityBody) en String ifrån parametern (pEntityBody). Denna metod använder
    * desutom en Integer parameter (pValue) för att avgöra om det skall ersätta eller utöka den String som redan finns sparad.
    * @param pValue En parameter av typen Integer som kommer att avgöra om det skall ersätta eller utöka den sparade String.
    * @param pEntityBody En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mEntityBody) en String.
    */
    private void setAddressField( String pAddressField )
    {
        mAddressField = pAddressField;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mAddressField). Den anger hela adressen som användaren angav i sin browser.
    * @return mAddressField, Vilket returnerar en String, alltså innehållet under den privata medlemsvariabeln (mAddressField).
    */
    private String getAddressField()
    {
        return mAddressField;
    }
}