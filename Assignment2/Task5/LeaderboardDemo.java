
import java.util.*;
public class Team{
String name;
int points,goalDiff;
Team(String name,int points,int goalDiff){
this.name=name;
this.points=points;
this.goalDiff=goalDiff;
}
public String toString(){
return name+"("+points+","+goalDiff+")";
}
public static void main(String[] args){
Comparator<Team> order=Comparator.comparingInt((Team t)->t.points).reversed().thenComparing(Comparator.comparingInt((Team t)->t.goalDiff).reversed()).thenComparing(t->t.name);
List<Team> teams=new ArrayList<>();
teams.add(new Team("Panthers",9,4));
teams.add(new Team("Eagles",9,6));
teams.add(new Team("Cobras",6,1));
teams.add(new Team("Bisons",9,4));
teams.sort(order);
System.out.println("Team Ranking:");
System.out.println(teams);
}
}

