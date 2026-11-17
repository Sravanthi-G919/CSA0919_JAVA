public class Main{
static class SeatBooking{
private final boolean[] booked;
private int bookingsMade=0;
SeatBooking(int seats){booked=new boolean[seats];}
void bookUnsafe(int seat){
if(!booked[seat]){
Thread.yield();
booked[seat]=true;
bookingsMade++;
}
}
synchronized void bookMethod(int seat){
if(!booked[seat]){
booked[seat]=true;
bookingsMade++;
}
}
void bookBlock(int seat){
synchronized(this){
if(!booked[seat]){
booked[seat]=true;
bookingsMade++;
}
}
}
}
static void runUsers(SeatBooking screen,int mode)throws InterruptedException{
Thread[] users=new Thread[100];
for(int i=0;i<users.length;i++){
final int seat=i%10;
if(mode==1)users[i]=new Thread(()->screen.bookUnsafe(seat));
else if(mode==2)users[i]=new Thread(()->screen.bookMethod(seat));
else users[i]=new Thread(()->screen.bookBlock(seat));
users[i].start();
}
for(Thread t:users)t.join();
}
public static void main(String[] args)throws InterruptedException{
SeatBooking a=new SeatBooking(10);
runUsers(a,1);
System.out.println("Unsynchronized seats sold: "+a.bookingsMade);
SeatBooking b=new SeatBooking(10);
runUsers(b,2);
System.out.println("Synchronized method seats sold: "+b.bookingsMade);
SeatBooking c=new SeatBooking(10);
runUsers(c,3);
System.out.println("Synchronized block seats sold: "+c.bookingsMade);
}
}
