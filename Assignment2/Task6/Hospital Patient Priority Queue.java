import java.util.*;
public class Team{
static class Patient{
String name;
int severity;
int arrival;
Patient(String name,int severity,int arrival){
this.name=name;
this.severity=severity;
this.arrival=arrival;
}
public String toString(){
return name+"(S"+severity+")";
}
}
public static void main(String[] args){
Comparator<Patient> order=Comparator.comparingInt((Patient p)->p.severity).thenComparingInt(p->p.arrival);
PriorityQueue<Patient> queue=new PriorityQueue<>(order);
queue.add(new Patient("Ravi",3,0));
queue.add(new Patient("Meena",1,1));
queue.add(new Patient("John",3,2));
queue.add(new Patient("Asha",5,3));
queue.add(new Patient("Kumar",1,4));
queue.add(new Patient("Lata",2,5));
System.out.println("Treatment Order:");
while(!queue.isEmpty()){
System.out.println(queue.poll());
}
}
}

