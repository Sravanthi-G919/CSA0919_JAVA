public class Main{
static class StationThread extends Thread{
int scripts;
StationThread(String name,int scripts){
super(name);
this.scripts=scripts;
}
public void run(){
try{
Thread.sleep(200);
System.out.println(getName()+" scanned "+scripts+" scripts.");
}catch(InterruptedException e){
Thread.currentThread().interrupt();
System.out.println(getName()+" interrupted.");
}
}
}
static class RunnableStation implements Runnable{
String name;
int scripts;
RunnableStation(String name,int scripts){
this.name=name;
this.scripts=scripts;
}
public void run(){
try{
Thread.sleep(300);
System.out.println(name+" scanned "+scripts+" scripts.");
}catch(InterruptedException e){
Thread.currentThread().interrupt();
}
}
}
public static void main(String[] args)throws InterruptedException{
Thread s1=new StationThread("Station 1",10);
Thread s2=new StationThread("Station 2",15);
Thread s3=new Thread(new RunnableStation("Station 3",12));
Thread s4=new Thread(new RunnableStation("Station 4",18));
Thread[] stations={s1,s2,s3,s4};
for(Thread t:stations)t.start();
for(Thread t:stations)System.out.println(t.getName()+" alive: "+t.isAlive());
for(Thread t:stations)t.join();
System.out.println("All stations finished.");
System.out.println("Total scripts scanned: "+(10+15+12+18));
}
}
