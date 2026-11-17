import java.util.*;
import java.util.concurrent.*;
public class Main{
static class Result{
String fileName,content;
Result(String fileName,String content){
this.fileName=fileName;
this.content=content;
}
}
static class Summary{
int success,failure;
long totalMarks,timeMs;
}
static Summary process(List<Result> files,int poolSize)throws Exception{
ExecutorService executor=Executors.newFixedThreadPool(poolSize);
ConcurrentLinkedQueue<String> successes=new ConcurrentLinkedQueue<>();
ConcurrentLinkedQueue<String> failures=new ConcurrentLinkedQueue<>();
List<Future<Integer>> futures=new ArrayList<>();
long start=System.nanoTime();
try{
for(Result file:files){
Callable<Integer> task=()->{
try{
if(file.content==null)throw new Exception("File is missing");
if(!file.content.startsWith("SCORE:"))throw new Exception("Corrupt file");
int marks=Integer.parseInt(file.content.substring(6));
if(marks<0||marks>100)throw new Exception("Invalid marks");
successes.add(file.fileName);
return marks;
}catch(Exception e){
failures.add(file.fileName+": "+e.getMessage());
return -1;
}
};
futures.add(executor.submit(task));
}
long totalMarks=0;
for(Future<Integer> future:futures){
int marks=future.get();
if(marks>=0)totalMarks+=marks;
}
Summary summary=new Summary();
summary.success=successes.size();
summary.failure=failures.size();
summary.totalMarks=totalMarks;
summary.timeMs=(System.nanoTime()-start)/1000000;
return summary;
}finally{
executor.shutdown();
}
}
public static void main(String[] args)throws Exception{
List<Result> files=new ArrayList<>();
for(int i=1;i<=10000;i++){
String name="answer"+i+".txt";
String content;
if(i%997==0)content=null;
else if(i%251==0)content="INVALID DATA";
else content="SCORE:"+((i*7)%101);
files.add(new Result(name,content));
}
System.out.println("Total files: "+files.size());
for(int poolSize:new int[]{1,2,4,8}){
Summary s=process(files,poolSize);
System.out.println("Thread pool size: "+poolSize);
System.out.println("Successful files: "+s.success);
System.out.println("Failed files: "+s.failure);
System.out.println("Total valid marks: "+s.totalMarks);
System.out.println("Processing time (ms): "+s.timeMs);
System.out.println("-------------------------");
}
}
}
