package pack2;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import pack.CommandLineTable;


public class score
{
	final int size=50;
	int[] credits={4,4,4,3,3};
	String[] subjects={"File Structures","Software Testing","Web Technology","open elective","prof elective"};
	Scanner scan = new Scanner(System.in);
	public static void main(String[] args) throws IOException,NullPointerException, InterruptedException
	{
		score obj = new score();
		int choice;
		while(true)
		{
			TimeUnit.SECONDS.sleep(3);
			
			System.out.println("***********************************************");
			System.out.println("*            1.Insert Student Record          *");
			System.out.println("*            2.Class Rankings                 *");
			System.out.println("*            3.Search student record          *");
			System.out.println("*            4.Modify a record                *");
			System.out.println("*            5.Exit                           *");		
			System.out.println("***********************************************");
			System.out.println("Please enter your choice:");
			choice = obj.scan.nextInt();
			obj.scan.nextLine();
			switch(choice)
			{
			case 1:
				obj.pack();
				break;
			case 2:
				obj.sort_file();
				obj.unpack();
				break;
			case 3:
				obj.search();
				break;
			case 4:
				obj.modify();
				break;
			case 5:
				System.out.println("You chose exit!");
				System.exit(0);
			case 6:
				obj.sort_file();
				break;
			default:
				System.out.println("Invalid Option");	
			}
		}
	}
	
	public void pack() throws FileNotFoundException
	{
		System.out.println("Enter Name, USN, Sem, Branch");
		String name = scan.nextLine();
		String usn = scan.nextLine();
		String sem = scan.nextLine(); 
		String branch = scan.nextLine();
		String all_sub=takeinput();
		
		PrintWriter pw = new PrintWriter(new FileOutputStream(new File("student.txt"),true));
		String b = name + "|" + usn + "|" + sem + "|" + branch + all_sub+"|";
		// System.out.println(b);
		int len = b.length();
		String s1 = "-";
		if(len<75)
		{
			for(int j=len;j<=60;j++)
			b = b.concat(s1);
		}
		pw.println(b);
		pw.flush();
		pw.close();
	}

	public String takeinput(){

		// String[] subjects={"sub1","sub2","sub3","sub4","sub5"};
		System.out.println("Enter marks of each subject:");
		String s="";
		int[] m=new int[5];
		int total=0;
		for(int i=0;i<5;i++){
			System.out.println(subjects[i]);
			String sub = scan.nextLine();
			s=s+"|"+sub;
			int n=Integer.parseInt(sub);
			total+=n;
			m[i]=n;

		}
		String x=cal_sgpa(m);
		s=s+"|"+(Integer.toString(total))+"|"+x;
		// System.out.println(s);
		return s;
	}

	public String cal_sgpa(int[] marks){

		int[] grade_points=new int[5];

		for(int i=0;i<5;i++){
			if(marks[i]>=90)
				grade_points[i]=10;
			else if(marks[i]>=80)
				grade_points[i]=9;
			else if(marks[i]>=70)
				grade_points[i]=8;
			else if(marks[i]>=60)
				grade_points[i]=7;
			else if(marks[i]>=50)
				grade_points[i]=6;
			else if(marks[i]>=40)
				grade_points[i]=5;
			else if(marks[i]>=30)
				grade_points[i]=4;
			else if(marks[i]>=20)
				grade_points[i]=3;
			else if(marks[i]>=10)
				grade_points[i]=2;
			else
				grade_points[i]=1;
		}

		float sgpa=0;
		int gxc=0;
		int credit_sum=0;

		for(int i=0;i<5;i++){
			gxc+=(grade_points[i]*credits[i]);
			credit_sum+=credits[i];
		}
		sgpa=(float)gxc/credit_sum;
		double x=Math.round(sgpa*100.0)/100.0;
		return(String.valueOf(x));

	}

	// ***************************************************************
	// SORTING FILE DATA

	class Student
	{
				String name ;
				String usn;
				String sem;
				String branch;
				String s1;
				String s2;
				String s3;
				String s4;
				String s5;
				int total;
				Float sgpa;
	
		public Student(String name,String usn,String sem,String branch,String s1,String s2,String s3,String s4,String s5, int total,float sgpa)
		{
			this.name = name;
			this.usn=usn;
			this.sem=sem;
			this.branch=branch;
			this.s1=s1;
			this.s2=s2;
			this.s3=s3;
			this.s4=s4;
			this.s5=s5;
			this.total=total;
			this.sgpa=sgpa;
		
		}
	}
 
	//nameCompare Class to compare the names
 
	class nameCompare implements Comparator<Student>
	{
		@Override
		public int compare(Student s1, Student s2)
		{
			return s1.name.compareTo(s2.name);
		}
	}
 
	//marksCompare Class to compare the marks
	
	class marksCompare implements Comparator<Student>
	{
		@Override
		public int compare(Student s1, Student s2)
		{
			if(s2.sgpa - s1.sgpa>0){
				return 1;
			}
			else if(s2.sgpa - s1.sgpa<0){
				return -1;
			}
			else{
				if(s2.total>s1.total)
					return 1;
				else if(s2.total<s1.total) 
					return -1;
				else 
					return 0;
			}
		}
	}

		public void sort_file()throws IOException
		{
			//Creating BufferedReader object to read the input text file
	
			BufferedReader reader = new BufferedReader(new FileReader("student.txt"));
	
			//Creating ArrayList to hold Student objects
	
			ArrayList<Student> studentRecords = new ArrayList<Student>();
	
			//Reading Student records one by one
	
			String currentLine = reader.readLine();
	
			while (currentLine != null)
			{
				String[] studentDetail = currentLine.split("\\|");
	
				String name = studentDetail[0];
				String usn=studentDetail[1];
				String sem=studentDetail[2];
				String branch=studentDetail[3];
				String s1=studentDetail[4];
				String s2=studentDetail[5];
				String s3=studentDetail[6];
				String s4=studentDetail[7];
				String s5=studentDetail[8];
				int total=Integer.valueOf(studentDetail[9]);
				Float sgpa=Float.parseFloat(studentDetail[10]);
				// String marks = marks;
	
				// int marks = Integer.valueOf(studentDetail[1]);
	
				//Creating Student object for every student record and adding it to ArrayList
	
				studentRecords.add(new Student(name,usn,sem,branch,s1,s2,s3,s4,s5,total,sgpa));
				currentLine = reader.readLine();
			}
	
			//Sorting ArrayList studentRecords based on marks
			Collections.sort(studentRecords, new marksCompare());
	
			//Creating BufferedWriter object to write into output text file
			BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
	
			//Writing every studentRecords into output text file
			for (Student student : studentRecords)
			{
				String record=student.name+"|"+student.usn+"|"+student.sem+"|"+student.branch+"|"+student.s1+"|"+student.s2+"|"+student.s3+"|"+student.s4+"|"+student.s5+"|"+student.total+"|"+student.sgpa+"|";
				writer.write(record);
	
				// writer.write(" "+student.sgpa);
	
				writer.newLine();
			}
	
			//Closing the resources
	
			reader.close();
	
			writer.close();
		}
	


	// ***************************************************************
	
	public void unpack()throws IOException
	{
		CommandLineTable st = new CommandLineTable();
		st.setShowVerticalLines(true);//if false (default) then no vertical lines are shown
		st.setHeaders("RANK","NAME","USN","SEM","BRANCH","MARKS","SGPA");//optional - if not used then there will be no header and horizontal lines

		String name = "" ,usn = "" ,sem = "" ,branch = "",marks= "",sgpa="", s;
		BufferedReader br = new BufferedReader(new FileReader("output.txt"));
		int i=0;
		while((s = br.readLine())!=null)
		{
			i++;
			String[] result = s.split("\\|");
			name = result[0];
			usn = result[1];
			sem = result[2];
			branch = result[3];
            marks=result[9];
            sgpa=result[10];

			
			st.addRow(Integer.toString(i),name,usn,sem,branch,marks,sgpa);
			// System.out.println("The details are: " + name + " " + usn + " " + sem + " " + branch + " " + marks + " " + sgpa );
		}
		st.print();
		
		br.close();
	}
	
	public void search()throws FileNotFoundException, IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("student.txt"));
		String usn = "", r;
		System.out.println("Enter the usn");
		String usn1 = scan.nextLine();
		while((r= br.readLine()) !=null)
		{
			
			String[] result = r.split("\\|");
		
			usn=result[1];
			
			if(usn.equals(usn1))
			{
				System.out.println("Match found. The details of the record are:");
				System.out.println("---------------------------------------------");
			
					System.out.println("NAME : "+result[0]);
					System.out.println("USN : "+result[1]);
					System.out.println("SEM : "+result[2]);
					System.out.println("BRANCH : "+result[3]);
					System.out.println(subjects[0]+" : "+result[4]);
					System.out.println(subjects[1]+" : "+result[5]);
					System.out.println(subjects[2]+" : "+result[6]);
					System.out.println(subjects[3]+" : "+result[7]);
					System.out.println(subjects[4]+" : "+result[8]);
					
				System.out.println("----------------------------------------------");

				System.out.println("SGPA "+" : "+result[10]);

				System.out.println("----------------------------------------------");
				
				// System.out.println(name + " " + usn + " " + sem + " " + branch + " " + sub1 + " " + sub2+ " " + sub3+ " " + sub4+ " " + sub5 );

				br.close();
				return;
			}
		}
		System.out.println("Record not found");
		br.close();
	}
	
	public void modify() throws FileNotFoundException,IOException,NullPointerException
	{
		String name = "", usn = "", sem = "", branch = "", s1 = "", s2 = "",s3 = "",s4 = "",s5 = "",marks="",sgpa="", r;
		File file = new File("student.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		File temp = new File("temp.txt");
		PrintWriter pw = new PrintWriter(temp);
		System.out.println("Enter usn of the record to be modified");
		String usn1 = scan.nextLine();
		
		while((r= br.readLine())!=null)
		{	
			String[] result = r.split("\\|");
			name = result[0];
			usn = result[1];
			sem = result[2];
			branch = result[3];
			s1=result[4];
			s2=result[5];
			s3=result[6];
			s4=result[7];
			s5=result[8];
            marks=result[9];
            sgpa=result[10];

			if(usn.equals(usn1))
			{
				System.out.println("The details are: \n" +"Name : "+ name + "\n" +"USN : "+ usn + "\n" +"SEM : "+sem + "\n" +"Branch : "+ branch + "\n" + subjects[0] + " :  " + s1 +"\n"+subjects[1] + " :  " + s2 +"\n"+subjects[2] + " :  " + s3 +"\n"+subjects[3] + " :  " + s4 +"\n"+subjects[4] + " :  " + s5 +"\n"+"Total Marks : " +marks+"\n"+"sgpa :  " + sgpa +"\n");
				System.out.println("Enter name, usn,sem, branch : ");
				String name11 = scan.nextLine();
				String usn11 = scan.nextLine();
				String sem11 = scan.nextLine();
				String branch11 = scan.nextLine();
                String sub_all11=takeinput();
				String b = name11+"|"+usn11+"|"+sem11+"|"+branch11+sub_all11+"|";
				int le = b.length();
				String s = "-";
				if(le<75)
				{
					for(int j=le;j<=60;j++)
						b = b.concat(s);
					pw.println(b);
				}
			}
			else
			{
				pw.println(r);
			}
		}
		pw.flush();
		pw.close();
		br.close();
		file.delete();
		temp.renameTo(file);
		System.out.println("File Modified");
	}
}
