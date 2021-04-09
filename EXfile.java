import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class EXfile {
	public static void outputToFile(ArrayList<String[]> linedata)
	{

	}
	public static void	Readpdb(String filename)
	{
		try
		{
			boolean loop = true;
			boolean atomcheck = false;
			int p = 0;
			String line = "";
			String split = " ";
			List<String> bbatoms = Arrays.asList(new String[]{"C", "O", "N", "CA"});
			ArrayList<String> atomdata = new ArrayList<String>();
			ArrayList<String[]> linedata = new ArrayList<String[]>();
			String[] pdbline = null;

			BufferedReader pdbRead = new BufferedReader(new FileReader(filename));
			line = pdbRead.readLine();
			while(loop)
			{
				//clears the data to make sure nothing is copied from the last iteration
				atomdata.clear();
				//stores the next line of the file in a variable
				line = pdbRead.readLine();
				//resets the variable p to 0. p is the counter of how many data points have been removed from the line to keep the indexing consistent
				p = 0;
				//reset the atom check to false so that it can be set back to true if a backbone atom is found
				atomcheck = false;
				//split the line from the file by the delimiter (space in this case)
				pdbline = line.split(split);
				//for loop that will go through all the data points from each line that have been stored in the string array
				for(int i = 0; i < pdbline.length; i++)
				{
					//add all the data to an arraylist to allow manipulation
					atomdata.add(pdbline[i]);
					//if statement to check the line if it contains nothing
					if(atomdata.get(i-p).isEmpty())
					{
						//if the space is empty it can be removed from the array list of data which will compress and take away the spaces
						atomdata.remove(i-p);
						//increment p as there has been another data point removed (avoids the out of bounds errors!!)
						p++;
					}
				}
				//first check if we are still looking at the protein and not the water box. This can be determined from the last column in the pdb file
				//WT1 stands for the first water in the molecule which is no longer part of the protein
				if(atomdata.get(9).equals("WT1"))
				{
					//break out of the while loop so set the boolean "loop" to false will break the while
					loop = false;
				}
				//need to loop through each of the atom types to check if the line contains one of the backbone atoms
				for(int f = 0; f < 4; f++)
				{
					//if statement to check for a backbone atom. Atom type is stored in index 2
					if(atomdata.get(2).equals(bbatoms.get(f)))
					{
						//if there is a match we want the line added to the final array so set the atom check variable to true
						atomcheck = true;

					}

				}
				//check the atom check variable to see if the line can be added to the final array
				if(atomcheck == true)
				{
					//add the line to the final array. putting it in as an array dereferences the data so the array can be cleared for the next line and no data will be lost
					linedata.add(atomdata.toArray(new String[10]));
				}
				//call the output method to set up the extrabonds file
				outputToFile(linedata);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}




	}


  public static void main(String[] args)
  {
	try
	{
		//file for dihedral extrabonds needs to be set up in the manner shown below
		//dihedral <atom1> <atom2> <atom3> <atom4> <k> <ref>
		
		int atom1 = 0;
		int atom2 = 0;
		int atom3 = 0;
		int atom4 = 0;
		int k = 0;
		int ref = 0;
		boolean stop = false;
		String file = args[0];
		String dihed = "dihedral";
		String splitBY = ",";
		String line = "";
		String[] data = null;
		
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		//readpdb(file);
		while(stop)
		{
			line = br.readLine();
			data = line.split(splitBY);
			
			atom1 = Integer.parseInt(data[0]);
			atom2 = Integer.parseInt(data[1]);
			atom3 = Integer.parseInt(data[2]);
			atom4 = Integer.parseInt(data[3]);
			k = Integer.parseInt(data[4]);
			ref = Integer.parseInt(data[5]);
			
		}
		
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}	
  }



}
