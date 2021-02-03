/**
* TCP_ServerMain-klassen används för att initiera själva programmet (ett TCP_Server-objekt)
* och anropa processRequest-metoden som finns under objektet i fråga.
*
* @author (Richard M. Hellstrand)
* @version (8-Juni-2010)
*/
package public_html;
/**
* Själva TCP_ServerMain-klassen; innehåller inga privata medlemsvariabler, ingen konstruktor,
* en publik Main-metod för initiering av ett TCP_Server-objekt och körning av denna.
*/
public class TCP_ServerMain
{
    public static void main( String[] args )
    {
        TCP_Server server = new TCP_Server();
        server.processRequest();
        //TCP_Server servo = new TCP_Server(5000);
        //servo.processRequest();
    }
}