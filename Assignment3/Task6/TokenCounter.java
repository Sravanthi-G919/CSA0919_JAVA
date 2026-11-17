public class Main{
static class Wallet{
final int id;
int balance;
Wallet(int id,int balance){this.id=id;this.balance=balance;}
}
static void transfer(Wallet a,Wallet b,int amount){
Wallet first=a.id<b.id?a:b;
Wallet second=a.id<b.id?b:a;
synchronized(first){
synchronized(second){
if(a.balance>=amount){
a.balance-=amount;
b.balance+=amount;
}
}
}
}
static class TokenCounter{
private final int[] buffer=new int[5];
private int count=0,in=0,out=0;
public synchronized void put(int token)throws InterruptedException{
while(count==buffer.length)wait();
buffer[in]=token;
in=(in+1)%buffer.length;
count++;
notifyAll();
}
public synchronized int take()throws InterruptedException{
while(count==0)wait();
int token=buffer[out];
out=(out+1)%buffer.length;
count--;
notifyAll();
return token;
}
}
public static void main(String[] args)throws InterruptedException{
Wallet a=new Wallet(1,1000);
Wallet b=new Wallet(2,500);
Thread t1=new Thread(()->transfer(a,b,100));
Thread t2=new Thread(()->transfer(b,a,50));
t1.start();
t2.start();
t1.join();
t2.join();
System.out.println("Wallet A balance: "+a.balance);
System.out.println("Wallet B balance: "+b.balance);
TokenCounter counter=new TokenCounter();
int[] collected=new int[2];
Thread cook=new Thread(()->{
try{
for(int t=1;t<=20;t++)counter.put(t);
}catch(InterruptedException e){
Thread.currentThread().interrupt();
}
});
Thread[] students=new Thread[2];
for(int s=0;s<2;s++){
final int id=s;
students[s]=new Thread(()->{
try{
for(int k=0;k<10;k++)collected[id]+=counter.take();
}catch(InterruptedException e){
Thread.currentThread().interrupt();
}
});
}
cook.start();
for(Thread t:students)t.start();
cook.join();
for(Thread t:students)t.join();
System.out.println("Sum of tokens collected: "+(collected[0]+collected[1])+" (expected 210)");
}
}
