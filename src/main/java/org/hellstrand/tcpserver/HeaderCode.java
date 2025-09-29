/**
* HeaderCode-klassen används för att fungera som en mall, så att en programmerare skall kunna lägga till
* eller ta bort viktiga element för att få programmet att köras på ett annat sätt än vad som var tänkt.
* Utan att behöva ändra alls för mycket i huvudkoden, som finns under TCP_Server-klassen.
*
* @author (Richard M. Hellstrand)
* @version (8-Juni-2010)
*/
package org.hellstrand.tcpserver;

/**
* Själva HeaderCode-klassen; innehåller sex privata medlemsvariabler, en konstruktor,
* två subklasser och sju publika resp. tio privata metoder (ej inkl. subklassernas)
* för ändringar, returneringar och utskrifter av dessa privata medlemsvariabler.
*/
public class HeaderCode
{
    /**
    * Privata medlemsvariabler, vilket endast kan nås utav de publika/privata metoderna.
    * mHeader - vilket är en privat medlemsvariabel av typen HeaderTemplates.
    * mAllowArray - vilket är en privat medlemsvariabel av typen String[].
    * mAcceptArray - vilket är en privat medlemsvariabel av typen String[].
    * mExtensionArray - vilket är en privat medlemsvariabel av typen String[].
    * mAllow - vilket är en privat medlemsvariabel av typen String.
    * mAccept - vilket är en privat medlemsvariabel av typen String.
    */
    private HeaderTemplates mHeader;
    private String[] mAllowArray;
    private String[] mAcceptArray;
    private String[] mExtensionArray;
    private String mAllow;
    private String mAccept;
    /**
    * En publik konstruktor, vilket anropar setTemplate-metoden.
    */
    public HeaderCode()
    {
        setTemplate();
    }
    /**
    * En publik metod, vilket tilldelar den privata medlemsvariabeln (mHeader) ett HeaderTemplates-objekt.
    */
    public void setTemplate()
    {
	setHeader( new HeaderTemplates() );
    }
    /**
    * En privat metod, vilket tilldelar den privata medlemsvariabeln (mHeader)
    * ett HeaderTemplates-objekt, som tilldelas via en parameter (pHeader).
    * @param pHeader, tilldelar den privata medlemsvariabeln (mHeader) ett objekt via parametern.
    */
    private void setHeader( HeaderTemplates pHeader )
    {
        mHeader = pHeader;
    }
    /**
    * En privat subklass, vilket initierar dom privata medlemsvariabelerna (mAllowArray, mAcceptArray och mExtensionArray)
    * med olika värden och Stringar. Efteråt valideras två av dessa String arrayer, enligt storlek, för att säkerhetsställa
    * en viss funktionalitet, full funktionalitet endast möjligt om programmeraren har angivit rätt värden för dessa arrayer..
    */
    private class HeaderTemplates
    {
        /**
        * En publik konstruktor, vilket initierar och tilldelar värden till dom privata medlemsvariablerna under superklassen (HeaderCode).
        */
        public HeaderTemplates()
        {
            setAllowArray( new String[2] ); /** Här är det VIKTIGT att arrayen är över eller lika med ett. */
            /** Antalet ovan anger hur många av dessa anrop det bör finnas. */
            setAllowElement( 0, "GET" );
            setAllowElement( 1, "HEAD" );
            setAcceptArray( new String[7] ); /** Här är det VIKTIGT att arrayen är LIKA i storlek med arrayen nedan. */
            /**
            * Antalet ovan anger hur många av dessa anrop det bör finnas OCH
            * rätt AcceptElement beskrivning BÖR stämma överens med rätt ExtensionElement filändelse.
            * T.ex: "text/htm" + ".htm" eller "image/jpg" + ".jpg"..
            */
            setAcceptElement( 0, "text/htm" );
            setAcceptElement( 1, "text/html" );
            setAcceptElement( 2, "text/plain" );
            setAcceptElement( 3, "image/gif" );
            setAcceptElement( 4, "image/jpg" );
            setAcceptElement( 5, "image/jpeg" );
            setAcceptElement( 6, "image/png" );
            setExtensionArray( new String[7] ); /** Här är det VIKTIGT att arrayen är LIKA i storlek med arrayen ovan. */
            /**
            * Antalet ovan anger hur många av dessa anrop det bör finnas OCH
            * rätt ExtensionElement filändelse BÖR stämma överens med rätt AcceptElement beskrivning.
            * T.ex: "text/html" + ".html" eller "image/jpeg" + ".jpeg"..
            */
            setExtensionElement( 0, ".htm" );
            setExtensionElement( 1, ".html" );
            setExtensionElement( 2, ".txt" );
            setExtensionElement( 3, ".gif" );
            setExtensionElement( 4, ".jpg" );
            setExtensionElement( 5, ".jpeg" );
            setExtensionElement( 6, ".png" );
            try
            {   /** Stämmer storleken på de två arrayerna, ifall ja så fortsätter programmet.. */
                if( validateArraySizes() )
                {
                    setAllow( 0, "Allow: " + getRequestTypes() + "\r\n" );
                    setAccept( 0, "Accept: " + getAcceptedDescriptions() + "\r\n" );
                }
            }
            catch( InvalidArraySizes pInvalidSize )
            {   /** Ifall nej, så avslutar programmet och kompilatorn får en exception. */
                System.err.println( pInvalidSize );
                System.exit( 0 );
            }
        }
        /**
        * En privat metod, vilket används för att validera storlekarna på de tre arrayerna, två av dom
        * SKALL vara av samma storlek och den tredje SKALL vara över eller likamed ett.
        * @return lSize Vilket returnerar en boolean om resultatet av if-satsen blir sant eller falskt.
        * @exception InvalidArraySizes Denna exception kommer att kastas när och ifall if-satsen blir falskt.
        */
        private boolean validateArraySizes() throws InvalidArraySizes
        {
            boolean lSize = false;

            if( getAcceptArraySize() == getExtensionArraySize() && getAllowArraySize() >= 1 )
                lSize = true;
            else
                throw new InvalidArraySizes();

            return lSize;
        }
        /**
        * En privat klass, vilket innehåller två konstruktorer, vilket kommer att
        * användas för validering av storleken på de två arrayerna.
        * @exception Exception Denna exception är extended för att man skall slippa skriva hela Exception klassen själv.
        */
        private class InvalidArraySizes extends Exception
        {
            public InvalidArraySizes()
            {
                this( "\n¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n" +
                       "The sizes of the arrays: mAcceptArray & mExtensionArray,\n" +
                       " under the HeaderCode class, HAVE TO BE OF EQUAL SIZE!\n" +
                       "   => AND/OR the size of the mAllowArray is zero(0) <=\n" +
                      "¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤" );
            }
            private InvalidArraySizes( String pInvalidSize )
            {
                super( pInvalidSize );
            }
        }
    }
    /**
    * En publik metod, vilket returnerar en lokal variabel, en boolean som används för att avgöra om servern skall
    * fortsätta sin kod eller inte. Genom att avgöra om den metod (GET, HEAD etc) som servern blir anropad av är
    * tillåten, alltså om den finns angiven i den privata medlemsvariabeln (mAllowArray).
    * @param pType En String parameter som kommer att jämföras med vad som finns under den privata medlemsvariabeln (mAllowArray).
    * @return lType, Vilket returnerar en boolean, om metoden är lika med vad som finns angivet under den privata medlemsvariabeln (mAllowArray).
    */
    public boolean checkRequestType( String pType )
    {
        boolean lType = false;

        for( int i = 0; i < getAllowArraySize(); i++ )
            if( getAllowElement( i ).equals( pType ) )
            {
                lType = false;
                break;
            }
            else
                lType = true;

        return lType;
    }
    /**
    * En publik metod, vilket returnerar den lokala variabeln (lTypes), en String som används för att ange alla tillåtna
    * metoder för åtkomst med servern, såsom GET, HEAD, POST, DELETE osv.
    * @return lTypes, Vilket returnerar en String, vilket innehåller allt som finns under den privata medlemsvariabeln (mAllowArray).
    */
    public String getRequestTypes()
    {
        String lTypes = "";

        for( int i = 0; i < getAllowArraySize(); i++ )
            lTypes += getAllowElement( i ) + "; ";

        return lTypes;
    }
    /**
    * En privat metod, vilket tilldelar den privata medlemsvariabeln (mAllowArray)
    * en String som ges utav den parameter som metoden har (pAllow).
    * @param pAllow En parameter av typen String array som kommer initiera den privata medlemsvariabeln (mAllowArray).
    */
    private void setAllowArray( String[] pAllow )
    {
        mAllowArray = pAllow;
    }
    /**
    * En privat metod, vilket returnerar storleken på arrayen av den privata medlemsvariabeln (mAllowArray).
    * @return mAllowArray.length, Vilket returnerar en Integer, alltså storleken på arrayen av den privata medlemsvariabeln (mAllowArray).
    */
    private int getAllowArraySize()
    {
        return mAllowArray.length;
    }
    /**
    * En privat metod, vilket tilldelar den privata medlemsvariabeln (mAllowArray) en String med fixerad Index genom parametrarna (pAllow resp. pIndex).
    * @param pIndex En parameter av typen Integer som kommer att fixera vilket element som kommer få tilldelat den String som ges utav den andra parametern (pAllow).
    * @param pAllow En parameter av typen String som kommer att spara en String under en fixerad Index med hjälp av värdet som ges utav den första parametern (pIndex).
    */
    private void setAllowElement( int pIndex, String pAllow )
    {
        mAllowArray[ pIndex ] = pAllow;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mAllowArray), vilket används för att ange vilka metoder som är tillåtna för servern.
    * @param pIndex En parameter av typen Integer som kommer att fixera vilket String som kommer returneras under den privata medlemsvariabeln (mAllowArray).
    * @return mAllowArray[], Vilket returnerar en String, genom att använda sig av en fixerad Index så hämtar den en String som finns under den privata medlemsvariabeln (mAllowArray).
    */
    private String getAllowElement( int pIndex )
    {
        return mAllowArray[ pIndex ];
    }
    /**
    * En privat metod, vilket tilldelar den privata medlemsvariabeln (mAllow) en String ifrån parametern (pAllow), dessutom så följer det med en
    * Integer som avgör om String bör ersätta vad som finns eller utöka den String som finns den privata medlemsvariabeln (mAllow).
    * @param pIndex En parameter av typen Integer som kommer att avgöra om det skall ersättas eller utöka den String som finns under den privata medlemsvariabeln (mAllow).
    * @param pAllow En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mAllow) en String.
    */
    private void setAllow( int pValue, String pAllow )
    {
        if( pValue == 0 )
            mAllow = pAllow;
        else
            mAllow += pAllow;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mAllow), en String som innehåller alla tillåtna metoder för kommunikation med servern.
    * @return mAllow, Vilket returnerar en String, vilket innehåller allt som finns under den privata medlemsvariabeln (mAllow).
    */
    public String getAllow()
    {
        return mAllow;
    }
    /**
    * En privat metod, vilket returnerar den lokala variabeln (lDescription), en String som används för att ange alla tillåtna filändelser,
    * som man kan begära. En for-slinga utökar den lokala variabeln (lDescription) så länge (i) är mindre än getAcceptArraySize().
    * @return lDescriptions, Vilket returnerar en String, vilket innehåller allt som finns under den privata medlemsvariabeln (mAcceptArray).
    */
    private String getAcceptedDescriptions()
    {
        String lDescriptions = "";

        for( int i = 0; i < getAcceptArraySize(); i++ )
            lDescriptions += getAcceptElement( i ) + "; ";

        return lDescriptions;
    }
    /**
    * En privat metod, vilket tilldelar den privata medlemsvariabeln (mAcceptArray)
    * en String som ges utav den parameter som metoden har (pAccept).
    * @param pAccept En parameter av typen String array som kommer initiera den privata medlemsvariabeln (mAcceptArray).
    */
    private void setAcceptArray( String[] pAccept )
    {
        mAcceptArray = pAccept;
    }
    /**
    * En privat metod, vilket returnerar storleken på arrayen av den privata medlemsvariabeln (mAcceptArray).
    * @return mAcceptArray.length, Vilket returnerar en Integer, alltså storleken på arrayen av den privata medlemsvariabeln (mAcceptArray).
    */
    private int getAcceptArraySize()
    {
        return mAcceptArray.length;
    }
    /**
    * En privat metod, vilket tilldelar den privata medlemsvariabeln (mAcceptArray) en String med fixerad Index genom parametrarna (pAccept resp. pIndex).
    * @param pIndex En parameter av typen Integer som kommer att fixera vilket element som kommer få tilldelat den String som ges utav den andra parametern (pAccept).
    * @param pAccept En parameter av typen String som kommer att spara en String under en fixerad Index med hjälp av värdet som ges utav den första parametern (pIndex).
    */
    private void setAcceptElement( int pIndex, String pAccept )
    {
        mAcceptArray[ pIndex ] = pAccept;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mAcceptArray), vilket används för att ange vilka metoder som är tillåtna för servern.
    * @param pIndex En parameter av typen Integer som kommer att fixera vilket String som kommer returneras under den privata medlemsvariabeln (mAcceptArray).
    * @return mAcceptArray[], Vilket returnerar en String, genom att använda sig av en fixerad Index så hämtar den en String som finns under den privata medlemsvariabeln (mAcceptArray).
    */
    private String getAcceptElement( int pIndex )
    {
        return mAcceptArray[ pIndex ];
    }
    /**
    * En privat metod, vilket tilldelar den privata medlemsvariabeln (mAccept) en String ifrån parametern (pAccept), dessutom så följer det med en
    * Integer som avgör om String bör ersätta vad som finns eller utöka den String som finns den privata medlemsvariabeln (mAccept).
    * @param pIndex En parameter av typen Integer som kommer att avgöra om det skall ersättas eller utöka den String som finns under den privata medlemsvariabeln (mAccept).
    * @param pAccept En parameter av typen String som kommer att tilldela den privata medlemsvariabeln (mAccept) en String.
    */
    public void setAccept( int pValue, String pAccept )
    {
        if( pValue == 0 )
            mAccept = pAccept;
        else
            mAccept += pAccept;
    }
    /**
    * En publik metod, vilket returnerar den privata medlemsvariabeln (mAccept), en String som innehåller alla tillåtna metoder för kommunikation med servern.
    * @return mAccept, Vilket returnerar en String, vilket innehåller allt som finns under den privata medlemsvariabeln (mAccept).
    */
    public String getAccept()
    {
        return mAccept;
    }
    /**
    * En publik metod, vilket returnerar en String, vilket symboliserar vilket format som filändelsen består av. Ifall inget hittas returneras 415 som en String.
    * @param pExtension En parameter av typen String som kommer att sparas under en lokal variabel och sedan kommer den att omvandlas till gemener och sedan görs jämförselsen i en for-loop.
    * @return lExtension, Vilket returnerar en String, vilket innehåller antingen beskrivningen av filändelsen eller 415 i String format.
    */
    public String getExtension( String pExtension )
    {
        String lExtension = "";
        lExtension = pExtension.toLowerCase();

        for( int i = 0; i < getExtensionArraySize(); i++ )
            if( lExtension.endsWith( getExtensionElement( i ) ) )
            {
                lExtension = getAcceptElement( i );
                break;
            }
            else if( i+1 == getExtensionArraySize() )
                lExtension = "415";

        return lExtension;
    }
    /**
    * En privat metod, vilket tilldelar den privata medlemsvariabeln (mExtensionArray)
    * en String som ges utav den parameter som metoden har (pExtension).
    * @param pExtension En parameter av typen String array som kommer initiera den privata medlemsvariabeln (mExtensionArray).
    */
    private void setExtensionArray( String[] pExtension )
    {
        mExtensionArray = pExtension;
    }
    /**
    * En privat metod, vilket returnerar storleken på arrayen av den privata medlemsvariabeln (mExtensionArray).
    * @return mExtensionArray.length, Vilket returnerar en Integer, alltså storleken på arrayen av den privata medlemsvariabeln (mExtensionArray).
    */
    private int getExtensionArraySize()
    {
        return mExtensionArray.length;
    }
    /**
    * En privat metod, vilket tilldelar den privata medlemsvariabeln (mExtensionArray) en String med fixerad Index genom parametrarna (pExtension resp. pIndex).
    * @param pIndex En parameter av typen Integer som kommer att fixera vilket element som kommer få tilldelat den String som ges utav den andra parametern (pExtension).
    * @param pExtension En parameter av typen String som kommer att spara en String under en fixerad Index med hjälp av värdet som ges utav den första parametern (pIndex).
    */
    private void setExtensionElement( int pIndex, String pExtension )
    {
        mExtensionArray[ pIndex ] = pExtension;
    }
    /**
    * En privat metod, vilket returnerar den privata medlemsvariabeln (mExtensionArray), vilket används för att ange vilka metoder som är tillåtna för servern.
    * @param pIndex En parameter av typen Integer som kommer att fixera vilket String som kommer returneras under den privata medlemsvariabeln (mExtensionArray).
    * @return mExtensionArray[], Vilket returnerar en String, genom att använda sig av en fixerad Index så hämtar den en String som finns under den privata medlemsvariabeln (mExtensionArray).
    */
    private String getExtensionElement( int pIndex )
    {
        return mExtensionArray[ pIndex ];
    }
}