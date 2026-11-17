
import java.util.*;
public class Team{
static class Entry{
String name;
int points,goalDiff;
Entry(String name,int points,int goalDiff){
this.name=name;
this.points=points;
this.goalDiff=goalDiff;
}
public String toString(){
return name+"("+points+","+goalDiff+")";
}
}
static Comparator<Entry> order=Comparator.comparingInt((Entry e)->e.points).reversed().thenComparing(Comparator.comparingInt((Entry e)->e.goalDiff).reversed()).thenComparing(e->e.name);
static List<Entry> originalRanking(List<Entry> teams){
List<Entry> result=new ArrayList<>(teams);
result.sort(order);
return result;
}
static void addSorted(List<Entry> teams,Entry entry){
int low=0,high=teams.size();
while(low<high){
int mid=(low+high)/2;
if(order.compare(teams.get(mid),entry)<0)low=mid+1;
else high=mid;
}
teams.add(low,entry);
}
public static void main(String[] args){
int[] sizes={1000,3000,5000};
int queries=200;
for(int size:sizes){
List<Entry> original=new ArrayList<>();
List<Entry> redesigned=new ArrayList<>();
long start=System.nanoTime();
for(int i=0;i<size;i++)original.add(new Entry("Team"+i,(i*37)%101,(i*13)%51-25));
long originalBuild=System.nanoTime()-start;
start=System.nanoTime();
for(int i=0;i<size;i++)addSorted(redesigned,new Entry("Team"+i,(i*37)%101,(i*13)%51-25));
long redesignedBuild=System.nanoTime()-start;
long checksum1=0,checksum2=0;
start=System.nanoTime();
for(int i=0;i<queries;i++)checksum1+=originalRanking(original).size();
long originalQuery=System.nanoTime()-start;
start=System.nanoTime();
for(int i=0;i<queries;i++)checksum2+=redesigned.size();
long redesignedQuery=System.nanoTime()-start;
System.out.println("Teams: "+size);
System.out.println("Original build time (ms): "+originalBuild/1000000.0);
System.out.println("Redesigned build time (ms): "+redesignedBuild/1000000.0);
System.out.println("Original ranking query time (ms): "+originalQuery/1000000.0);
System.out.println("Redesigned ranking query time (ms): "+redesignedQuery/1000000.0);
System.out.println("Rankings valid: "+(originalRanking(original).equals(redesigned)));
System.out.println("--------------------------------");
}
}
}

