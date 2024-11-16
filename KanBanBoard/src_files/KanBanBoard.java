import java.io.*;
import java.util.ArrayList;

class Job {

    private String jobId;
    private String jobName;
    private String Status = "Not Set";
    private String Author;
    private ArrayList<String> Stages = new ArrayList<>();

    public Job(String jobId, String jobName, String Author){
        this.jobId = jobId;
        this.jobName = jobName;
        this.Author = Author; 
    }

    public Job(String jobId, String jobName, String Author, String Status){
        this.jobId = jobId;
        this.jobName = jobName;
        this.Author = Author; 
        this.Status = Status;
    }

    public String getStatus(){
        return this.Status;
    }

    public void setStatus(String Status){
        if (! Stages.contains(Status)){
            System.out.println("That stage is not in the list of options from the referenced board. No changes made.");
        }
        else{
            this.Status = Status;
        }
    }

    public String getId(){
        return this.jobId;
    }

    public void setId(String jobId){
        this.jobId = jobId;
    }

    public String getName(){
        return this.jobName;
    }

    public void setName(String jobName){
        this.jobName = jobName;
    }

    public String getAuthor(){
        return this.Author;
    }

    public void setAuthor(String Author){
        this.Author = Author;
    }

    public void getStages(){
        if (Stages.isEmpty()){
            System.out.println("No stages assigned to this job.  It is not currently on a kanban board.");
        }
        else{
            for (String s:Stages){
                System.out.print(s+" ");
            }
            System.out.println("");
        }
    }

    @Override
    public String toString(){
        return "Id: "+jobId+"\nName: "+jobName+"\nAuthor: "+Author+"\nStatus: "+Status+"\n";
    }

    public void addToBoard(Board B){
        this.Stages = B.Stages;
        B.Items.add(this);
        B.numActiveJobs++;
    }

    public void removeFromBoard(Board B){
        if (B.Items.contains(this)){
            B.Items.remove(this);
            B.numActiveJobs--;
        }
        while (! Stages.isEmpty()){
            Stages.remove(0);
        }
    }


}

class Board {

    String BoardName;
    int numActiveJobs;
    ArrayList<String> Stages = new ArrayList<>();
    ArrayList<Job> Items = new ArrayList<>();

    public Board(String BoardName, String ...Stages){
        this.BoardName = BoardName;
        for (String s: Stages){
            this.Stages.add(s);
        }
        numActiveJobs = this.Stages.size();
    }

    public Board(String BoardName, String FileName) throws Exception{
        this.BoardName = BoardName;
        FileReader fr = new FileReader(FileName);
        BufferedReader br = new BufferedReader(fr);
        String line,Id,Name,Author,Status;
        //Read in stages.
        int n = Integer.valueOf(br.readLine());
        for (int i=0;i<n;i++){
            this.Stages.add(br.readLine());
        }
        do{
            line = br.readLine();
            if (line==null){
                break;
            }
            Id = line.substring(line.indexOf(" ")+1,line.length());
            line = br.readLine();
            Name = line.substring(line.indexOf(" ")+1,line.length());
            line = br.readLine();
            Author = line.substring(line.indexOf(" ")+1,line.length());
            line = br.readLine();
            Status = line.substring(line.indexOf(" ")+1,line.length());
            Job J = new Job(Id,Name,Author,Status);
            //Add to this board.
            this.Items.add(J);
        }while (line != null);

        br.close();
        fr.close();

    }

    public void printBoard(){
        System.out.println("Active Jobs: "+numActiveJobs);
        for (Job j:Items){
            System.out.println(j);
        }
    }

    public void exportBoard() throws Exception{
        FileWriter fw = new FileWriter(BoardName+".txt");
        int n = Stages.size();
        fw.write(Integer.toString(n)+"\n");
        for (String s:Stages){
            fw.write(s+"\n");
        }
        for (Job j:this.Items){
            fw.write(j.toString());
        }

        fw.close();
    }

    //Return first job in board with Id.
    public Job getJobById(String Id){
        int i=0;
        for (Job j: this.Items){
            String jId = j.getId();
            if (jId.equals(Id)){
                //Delete this job from this.Items to ensure that when it is added back
                //to the board, there is no duplicate.
                this.Items.remove(i);
                return new Job(j.getId(),j.getName(),j.getAuthor(),j.getStatus());
            }
            i++;
        }
        
        return new Job("a","b","c","d");
    }

}

class KanBanBoard{

    public static void main(String[] args) throws Exception{

        Board KB = new Board("Board1","stage1","stage2","stage3");
        Job J1 = new Job("123","Job1","Doug");
        Job J2 = new Job("124","Job2","Doug");
        Job J3 = new Job("125","Job3","Greg");
        Job J4 = new Job("127","Job4","Bart J");

        J1.addToBoard(KB);
        J2.addToBoard(KB);
        J3.addToBoard(KB);
        J4.addToBoard(KB);

        KB.exportBoard();

        //Reload Board.
        Board KB2 = new Board("Board1", "Board1.txt");
        System.out.println(KB2.Stages);
        //Add a new job and change status of job with id 123.
        Job J5 = new Job("234","Job5","Marty");
        J5.addToBoard(KB2);

        Job J = KB2.getJobById("123");
        J.addToBoard(KB2);
        J.setStatus("stage2");
        

        KB2.exportBoard();

    }
}