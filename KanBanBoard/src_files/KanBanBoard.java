import java.io.*;
import java.util.*;

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
            System.out.println("That stage is not in the list of options from the referenced board, ");
            System.out.println("or the job with indicated id does not exist.");
        }
        else{
            this.Status = Status;
        }
    }

    public String getId(){
        return this.jobId;
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
        while (! B.Stages.isEmpty()){
            B.Stages.remove(0);
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
        numActiveJobs = this.Items.size();
    }

    public Board(String BoardName, String FileName) throws Exception{
        this.BoardName = BoardName;
        FileReader fr = new FileReader(FileName);
        BufferedReader br = new BufferedReader(fr);
        String line,Id,Name,Author,Status;
        //Read in stages.
        int n = Integer.valueOf(br.readLine());
        if (n==0){
            System.out.println("Loaded file contains no content.  Please create a new board.");
        }
        else {
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
        
        return new Job("","","","");
    }

}

class KanBanBoard{

    static String[] commands = {"create <board name> stages <list of stages>",
                            "load <board name> <file name>",
                            "add job to <board name> id <job id> name <job name> author <job author>",
                            "drop job from <board name> id <job id>",
                            "save board",
                            "set status <job id> <job status>",
                            "set name <job id> <job name>",
                            "set author <job id> <job author>",
                            "exit"};

    static String command = "default";

    //Standard public references for board and job.
    public static Board B;
    public static Job J;

    static void showCommandOptions(){
        for (String c:commands){
            System.out.println(c);
        }
        System.out.println("");
    }

    static void manageUI(String[] LineList) throws Exception{
        String BoardName;
        String jobName;
        String jobId;
        String Author;
        String FileName;
        String Status;
        switch (LineList[0]){
            case "create":
                BoardName = LineList[1];
                String[] Stages = new String[LineList.length-3];
                for (int i=3;i<LineList.length;i++){
                    Stages[i-3] = LineList[i];
                }
                B = new Board(BoardName, Stages);
                break;
            case "load":
                BoardName = LineList[1];
                FileName = LineList[2];
                B = new Board(BoardName, FileName);
                break;
            case "add":
                BoardName = LineList[3];
                jobId = LineList[5];
                jobName = LineList[7];
                Author = LineList[9];
                J = new Job(jobId, jobName, Author);
                J.addToBoard(B);
                break;
            case "drop":
                BoardName = LineList[3];
                jobId = LineList[5];
                J = B.getJobById(jobId);
                J.removeFromBoard(B);
                break;
            case "save":
                B.exportBoard();
                break;
            case "set":
                if (LineList[1].equals("status")){
                    jobId = LineList[2];
                    Status = LineList[3];
                    J = B.getJobById(jobId);
                    J.setStatus(Status);
                    J.addToBoard(B);
                }
                else if (LineList[1].equals("name")){
                    jobId = LineList[2];
                    jobName = LineList[3];
                    J = B.getJobById(jobId);
                    J.setName(jobName);
                    J.addToBoard(B);
                }
                else if (LineList[1].equals("author")){
                    jobId = LineList[2];
                    Author = LineList[3];
                    J = B.getJobById(jobId);
                    J.setAuthor(Author);
                    J.addToBoard(B);
                }
                break;
            default:
                System.out.println("Command not in list of options.  No changes made.");
        }

    }

    public static void main(String[] args) throws Exception{

        Scanner sc = new Scanner(System.in);
        System.out.println("Wecome to Kanban Board Interface.\n");
        System.out.println("Available commands:\n");
        showCommandOptions();
        System.out.println("");
        
        //Begin running UI.
        String[] currentLineList = sc.nextLine().split(" ");
        command = currentLineList[0];
        while (! command.equals("exit")){
            manageUI(currentLineList);
            //How to create instance of board in static method?
            //Check by printing board stages.
            if (B != null){
                System.out.println(B.BoardName);
            }
            currentLineList = sc.nextLine().split(" ");
            command = currentLineList[0];
        }


    }
}