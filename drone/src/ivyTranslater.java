/**
 * Yet another Ivy java program example
 *
 * This is the example from the documentation
 *
 * @author Yannick Jestin <jestin@cena.fr>
 *
 * (c) CENA
 *
 * This program is distributed as is
 *
 */
import fr.dgac.ivy.* ;

class ivyTranslater implements IvyMessageListener {

  private Ivy bus;

  ivyTranslater() throws IvyException {
    // initialization, name and ready message
    bus = new Ivy("IvyTranslater","IvyTranslater Ready",null);
    System.out.println("IVYbus créée");
   // bus.bindMsg("^(.*)",this);
    bus.bindMsg("^ground WAYPOINT_MOVED ([0-9]+) ([0-9]+)",this);
    System.out.println("hello");
    bus.bindMsg("^Bye$",new IvyMessageListener() {
      // callback for "Bye" message
      public void receive(IvyClient client, String[] args) {
        bus.stop();
      }
    });
    // starts the bus on the default domain
    bus.start(null);
  }

  // callback associated to the "Hello" messages"
  public void receive(IvyClient client, String[] args) {

      System.out.println(""+((args.length>0)?args[0]:"")+" "+((args.length>1)?args[1]:""));
    
  }

  public static void main(String args[]) throws IvyException {
    new ivyTranslater();
  }
}