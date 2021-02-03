/**
* Stream-klassen används för att samla alla, eller iaf dessa klasser gällande Stream.
* Alltså denna innehåller bl.a. Input/Output och andra för att bearbeta den data som
* servern får via sin socket, på ett lättare sätt.
*
* @author (Richard M. Hellstrand)
* @version (8-Juni-2010)
*/
package public_html;
import java.io.*;
/**
* Själva HeaderCode-klassen; innehåller sex privata medlemsvariabler, en konstruktor,
* två egna klasser och tio publika resp. noll privata metoder (ej inkl. subklassernas)
* för ändringar, returneringar och utskrifter av dessa privata medlemsvariabler.
*/
public class Stream
{
    /**
    * Privata medlemsvariabler, vilket endast kan nås utav de publika/privata metoderna.
    * mFile - vilket är en privat medlemsvariabel av typen File.
    * mFileStream - vilket är en privat medlemsvariabel av typen FileInputStream.
    * mInputStream - vilket är en privat medlemsvariabel av typen InputStream.
    * mOutputStream - vilket är en privat medlemsvariabel av typen DataOutputStream.
    * mBufferedReader - vilket är en privat medlemsvariabel av typen BufferedReader.
    */
    private File mFile;
    private FileInputStream mFileStream;
    private InputStream mInputStream;
    private DataOutputStream mOutputStream;
    private BufferedReader mBufferedReader;
    /**
    * En publik konstruktor, vilket initierar dom fem privata medlemsvariablerna med värdet null.
    */
    public Stream()
    {
        setFile( null );
        setFileStream( null );
        setInputStream( null );
        setOutputStream( null );
        setBufferedReader( null );
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mFile) en File ifrån parametern (pFile). Denna metod används tillsammans
    * med getFile() för att kunna avgöra om den fil som servern bör skicka tillbaka, om möjligt, är antingen en fil eller en mapp.
    * @param pFile En parameter av typen File som kommer att tilldela den privata medlemsvariabeln (mFile) en File.
    */
    public void setFile( File pFile )
    {
        mFile = pFile;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mFile). Denna metod används tillsammans med setFile( File )
    * för att kunna avgöra om den fil som servern bör skicka tillbaka, om möjligt, är antingen en fil eller en mapp.
    * @return mFile, Vilket returnerar en File, alltså innehållet under den privata medlemsvariabeln (mFile).
    */
    public File getFile()
    {
        return mFile;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mFileStream) en FileInputStream ifrån parametern (pFileStream).
    * @param pFileStream En parameter av typen FileInputStream som kommer att tilldela den privata medlemsvariabeln (mFileStream) en FileInputStream.
    */
    public void setFileStream( FileInputStream pFileStream )
    {
        mFileStream = pFileStream;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mFileStream), en FileInputStream används för att läsa rå data ifrån en fil.
    * @return mFileStream, vilket returnerar en FileInputStream, returnering av en fil som har sparats den privata medlemsvariabeln (mFileStream).
    */
    public FileInputStream getFileStream()
    {
        return mFileStream;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mInputStream) en InputStream ifrån parametern (pInputStream).
    * @param pInputStream En parameter av typen InputStream som kommer att tilldela den privata medlemsvariabeln (mInputStream) en InputStream.
    */
    public void setInputStream( InputStream pInputStream )
    {
        mInputStream = pInputStream;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mInputStream), man kan säga en kanal där all input ifrån klienten sparas och används vid behov.
    * @return mInputStream, Vilket returnerar en InputStream, vid behov kommer det som finns under denna kanal att användas.
    */
    public InputStream getInputStream()
    {
        return mInputStream;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mOutputStream) en DataOutputStream ifrån parametern (pOutputStream).
    * @param pOutputStream En parameter av typen DataOutputStream som kommer att tilldela den privata medlemsvariabeln (mOutputStream) en DataOutputStream.
    */
    public void setOutputStream( DataOutputStream pOutputStream )
    {
        mOutputStream = pOutputStream;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mOutputStream), en DataOutputStream innehåller en String som kommer att skickas till klienten.
    * @return mOutputStream, Vilket returnerar en DataOutputStream, används av servern för att kommunicera med klienten som anropade servern.
    */
    public DataOutputStream getOutputStream()
    {
        return mOutputStream;
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mBufferedReader) en BufferedReader ifrån parametern (pBufferedReader).
    * @param pBufferedReader En parameter av typen BufferedReader som kommer att tilldela den privata medlemsvariabeln (mBufferedReader) en BufferedReader.
    */
    public void setBufferedReader( BufferedReader pBufferedReader )
    {
        mBufferedReader = pBufferedReader;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mBufferedReader), en BufferedReader fungerar som en buffer för att spara data tills det är dags att bearbetas.
    * @return mBufferedReader, Vilket returnerar en BufferedReader, används av servern för att samla ihop information av olika slag innan den används på något sätt.
    */
    public BufferedReader getBufferedReader()
    {
        return mBufferedReader;
    }
}